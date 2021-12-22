package com.example.NumericalProject.MethodsCalculations;

import java.math.BigDecimal;
import java.math.RoundingMode;

/***
 * This class calculate Gauss-Jordan Elimination
 */
public class GaussJordanCalc {
    private final InitGauss initGauss;

    /***
     * A constructor for initializing the object with the needed parameters
     * @param initGauss an object that hold all the matrices needed for calculations
     */
    public GaussJordanCalc(InitGauss initGauss) {
        this.initGauss = initGauss;
    }

    /***
     * A method which starts the calculation of Gauss-Jordan Elimination
     */
    public void GaussJordan() {
        initGauss.gauss();
        Eliminate(initGauss.n);
        String checkConsistency = initGauss.methodsUtilities.CheckConsistency(initGauss);
        String newPrinter = initGauss.print.getPrinter().concat("\n" + checkConsistency + "\n");
        initGauss.print.setPrinter(newPrinter);

        // Check if the system is consistent
        if (checkConsistency.equals("No Solution") || checkConsistency.equals("Infinity Solutions")) return;

        //
        if (initGauss.er != -1) {
            // Calculating and printing the final matrices and answer
            if (initGauss.n >= 0) System.arraycopy(initGauss.B, 1, initGauss.x, 1, initGauss.n);
            initGauss.print.VectorToString(initGauss, initGauss.x, "Vector X");
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
            initGauss.methodsUtilities.Pivoting(initGauss, k);
            if ((initGauss.A[k][k].divide(initGauss.s[k], initGauss.SigFigs, RoundingMode.DOWN)).abs()
                    .compareTo(BigDecimal.valueOf(Math.pow(10, -initGauss.SigFigs))) < 0) {
                initGauss.er = -1;
                return;
            }

            // The elimination
            for (int i = 1; i <= n; i++) {
                if (i == k) continue;
                BigDecimal factor = initGauss.A[i][k].divide(initGauss.A[k][k], initGauss.SigFigs, RoundingMode.DOWN)
                        .setScale(initGauss.SigFigs, RoundingMode.DOWN);
                for (int j = 1; j <= n; j++) {
                    initGauss.A[i][j] = initGauss.A[i][j].subtract(factor.multiply(initGauss.A[k][j]))
                            .setScale(initGauss.SigFigs, RoundingMode.DOWN);
                }
                initGauss.B[i] = initGauss.B[i].subtract(factor.multiply(initGauss.B[k]))
                        .setScale(initGauss.SigFigs, RoundingMode.DOWN);
                initGauss.print.MatrixToString(initGauss, initGauss.A, "Matrix A");
                initGauss.print.VectorToString(initGauss, initGauss.B, "Vector B");
            }
        }

        if (initGauss.A[n][n].divide(initGauss.s[n], initGauss.SigFigs, RoundingMode.DOWN).abs()
                .compareTo(BigDecimal.valueOf(Math.pow(10, -initGauss.SigFigs))) < 0) {
            initGauss.er = -1;
        }

        // Calculate B matrix
        for (int i = 1; i <= n; i++) {
            initGauss.B[i] = initGauss.B[i].divide(initGauss.A[i][i], initGauss.SigFigs, RoundingMode.DOWN)
                    .setScale(initGauss.SigFigs, RoundingMode.DOWN);
        }

        // Finishing matrix A
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                if (i == j) initGauss.A[i][j] = BigDecimal.valueOf(1);
            }
        }

        // Printing steps
        initGauss.print.MatrixToString(initGauss, initGauss.A, "Matrix A");
        initGauss.print.VectorToString(initGauss, initGauss.B, "Vector B");
    }
}
