package com.example.NumericalProject.MethodsCalculations;

import java.math.BigDecimal;
import java.math.RoundingMode;

/***
 * This class calculate Gauss-Jordan Elimination
 */
public class GaussJordanCalc {
    private final Initialization Init;

    /***
     * A constructor for initializing the object with the needed parameters
     * @param Init an object that hold all the information needed for calculations
     */
    public GaussJordanCalc(Initialization Init) {
        this.Init = Init;
    }

    /***
     * A method which starts the calculation of Gauss-Jordan Elimination
     */
    public void GaussJordan() {
        Init.Initialize();
        Eliminate(Init.n);
        String checkConsistency = Init.methodsUtilities.CheckConsistency(Init);
        String newPrinter = Init.print.getPrinter().concat("\n" + checkConsistency + "\n");
        Init.print.setPrinter(newPrinter);

        // Check if the system is consistent
        if (checkConsistency.equals("No Solution") || checkConsistency.equals("Infinity Solutions")) return;

        //
        if (Init.er != -1) {
            // Calculating and printing the final matrices and answer
            if (Init.n >= 0) System.arraycopy(Init.B, 1, Init.x, 1, Init.n);
            Init.print.VectorToString(Init, Init.x, "Vector X");
        }
    }

    /***
     * Do Gauss-Jordan Elimination on A matrix
     * @param n size of the matrix
     */
    public void Eliminate(int n) {

        // Gauss-Jordan elimination on the linear system of equations
        for (int k = 1; k <= n; k++) {

            // Making partial pivoting and scaling
            Init.methodsUtilities.Pivoting(Init, k);
            if ((Init.A[k][k].divide(Init.s[k], Init.SigFigs, RoundingMode.DOWN)).abs()
                    .compareTo(BigDecimal.valueOf(Math.pow(10, -Init.SigFigs))) < 0) {
                Init.er = -1;
                return;
            }

            // The elimination
            for (int i = 1; i <= n; i++) {
                if (i == k) continue;
                BigDecimal factor = Init.A[i][k].divide(Init.A[k][k], Init.SigFigs, RoundingMode.DOWN)
                        .setScale(Init.SigFigs, RoundingMode.DOWN);
                for (int j = 1; j <= n; j++) {
                    Init.A[i][j] = Init.A[i][j].subtract(factor.multiply(Init.A[k][j]))
                            .setScale(Init.SigFigs, RoundingMode.DOWN);
                }
                Init.B[i] = Init.B[i].subtract(factor.multiply(Init.B[k]))
                        .setScale(Init.SigFigs, RoundingMode.DOWN);
                Init.print.MatrixToString(Init, Init.A, "Matrix A");
                Init.print.VectorToString(Init, Init.B, "Vector B");
            }
        }

        if (Init.A[n][n].divide(Init.s[n], Init.SigFigs, RoundingMode.DOWN).abs()
                .compareTo(BigDecimal.valueOf(Math.pow(10, -Init.SigFigs))) < 0) {
            Init.er = -1;
        }

        // Calculate B matrix
        for (int i = 1; i <= n; i++) {
            Init.B[i] = Init.B[i].divide(Init.A[i][i], Init.SigFigs, RoundingMode.DOWN)
                    .setScale(Init.SigFigs, RoundingMode.DOWN);
        }

        // Finishing matrix A
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                if (i == j) Init.A[i][j] = BigDecimal.valueOf(1);
            }
        }

        // Printing steps
        Init.print.MatrixToString(Init, Init.A, "Matrix A");
        Init.print.VectorToString(Init, Init.B, "Vector B");
    }
}
