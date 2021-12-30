package com.example.NumericalProject.MethodsCalculations.LinearEquations;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

/***
 * This class calculate the LU Decomposition with 3 different methods
 */
public class LUDecompCalc {
    private final Initialization Init;

    /***
     * A constructor for initializing the object with the needed parameters
     * @param Init an object that hold all the information needed for calculations
     */
    public LUDecompCalc(Initialization Init) {
        this.Init = Init;
    }

    /***
     * A method which starts the calculation with the specified type of LU Decomposition
     * @param type a string that hold the type of LU Decomposition
     */
    public void LUDecomp(String type) {
        Init.Initialize();
        switch (type.toLowerCase()) {
            case "doolittle" -> DoolittleDecompose();
            case "crout" -> CroutDecompose();
            case "cholesky" -> CholeskyDecompose();
        }
        if (Init.er != -1) {
            // Printing the final matrices and answer
            Init.linearPrinter.MatrixToString(Init, Init.L, "Matrix L");
            Init.linearPrinter.MatrixToString(Init, Init.U, "Matrix U");
            Init.linearPrinter.VectorToString(Init, Init.x, "Vector X");
        }
    }

    /***
     * A method which decomposes the Matrix by doolittle method
     */
    public void DoolittleDecompose() {

        // Making gauss elimination to calculate the U matrix
        for (int k = 1; k <= Init.n - 1; k++) {
            Init.methodsUtilities.LUPivoting(Init, k);
            if (((Init.A[Init.o[k].intValue()][k].abs())
                    .divide(Init.s[Init.o[k].intValue()], Init.SigFigs, RoundingMode.DOWN))
                    .compareTo(BigDecimal.valueOf(Math.pow(10, -Init.SigFigs))) < 0) {
                Init.er = -1;
                return;
            }
            for (int i = k + 1; i <= Init.n; i++) {
                BigDecimal factor = Init.A[Init.o[i].intValue()][k]
                        .divide(Init.A[Init.o[k].intValue()][k], Init.SigFigs, RoundingMode.DOWN)
                        .setScale(Init.SigFigs, RoundingMode.DOWN);
                Init.A[Init.o[i].intValue()][k] = factor;
                for (int j = k + 1; j <= Init.n; j++) {
                    Init.A[Init.o[i].intValue()][j] = Init.A[Init.o[i].intValue()][j]
                            .subtract(factor.multiply(Init.A[Init.o[k].intValue()][j]))
                            .setScale(Init.SigFigs, RoundingMode.DOWN);
                }
                Init.linearPrinter.MatrixToString(Init, Init.A, "Matrix A");
                Init.linearPrinter.VectorToString(Init, Init.B, "Vector B");
            }
        }

        // Calculate the L Matrix
        for (int i = 1; i <= Init.n; i++) {
            for (int j = 1; j <= Init.n; j++) {
                if (j > i) Init.U[i][j] = Init.A[i][j].setScale(Init.SigFigs, RoundingMode.DOWN);
                else if (j < i)
                    Init.L[i][j] = Init.A[i][j].setScale(Init.SigFigs, RoundingMode.DOWN);
                else {
                    Init.U[i][j] = Init.A[i][j].setScale(Init.SigFigs, RoundingMode.DOWN);
                    Init.L[i][j] = BigDecimal.ONE;
                }
            }
        }

        // Check if the system is consistent
        String temp = Init.methodsUtilities.CheckConsistencyLU(Init);
        if (temp.equals("No Solution") || temp.equals("Infinity Solutions"))
            return;
        if (((Init.A[Init.o[Init.n].intValue()][Init.n].abs())
                .divide(Init.s[Init.o[Init.n].intValue()], Init.SigFigs, RoundingMode.DOWN))
                .compareTo(BigDecimal.valueOf(Math.pow(10, -Init.SigFigs))) < 0) {
            Init.er = -1;
        }

        // Calculate the answer if there is no errors
        if (Init.er != -1) {
            Init.methodsUtilities.LUSubstitute(Init);
        }
    }

    /***
     * A method which decomposes the Matrix by crout method
     */
    public void CroutDecompose() {
        BigDecimal sum;
        // Puts the diagonal of U matrix with ones
        for (int i = 1; i <= Init.n; i++) {
            Init.U[i][i] = BigDecimal.ONE;
        }

        for (int j = 1; j <= Init.n; j++) {
            // Initialize the steps printer
            String newPrinter = Init.linearPrinter.getPrinter();

            // Calculate the L matrix
            for (int i = j; i <= Init.n; i++) {
                sum = BigDecimal.ZERO;
                newPrinter = newPrinter.concat("\nSum = ");
                for (int k = 1; k <= j; k++) {
                    sum = sum.add(Init.L[i][k].multiply(Init.U[k][j]))
                            .setScale(Init.SigFigs, RoundingMode.DOWN);

                    // Print the steps
                    if (k != j)
                        newPrinter = newPrinter.concat("L(" + i + k + ")" + " * " + "U(" + k + j + ")" + " + ");
                    else
                        newPrinter = newPrinter.concat("L(" + i + k + ")" + " * " + "U(" + k + j + ")" + "\n");
                }
                Init.L[i][j] = Init.A[i][j].subtract(sum)
                        .setScale(Init.SigFigs, RoundingMode.DOWN);

                // Print the steps
                newPrinter = newPrinter.concat("L(" + i + j + ") = " + "U(" + i + j + ")" + " - " + sum + "\n");
            }

            // Calculate the U matrix
            for (int i = j + 1; i <= Init.n; i++) {
                sum = BigDecimal.ZERO;
                newPrinter = newPrinter.concat("\nSum = ");
                for (int k = 1; k <= Init.n; k++) {
                    sum = sum.add(Init.L[j][k].multiply(Init.U[k][i]))
                            .setScale(Init.SigFigs, RoundingMode.DOWN);

                    // Print the steps
                    if (k != j)
                        newPrinter = newPrinter.concat("L(" + j + k + ")" + " * " + "U(" + k + j + ")" + " + ");
                    else
                        newPrinter = newPrinter.concat("L(" + j + k + ")" + " * " + "U(" + k + j + ")" + "\n");
                }
                if (Init.L[j][j].compareTo(BigDecimal.ZERO) == 0) {
                    Init.er = -1;
                    return;
                }
                Init.U[j][i] = (Init.A[j][i].subtract(sum))
                        .divide(Init.L[j][j], Init.SigFigs, RoundingMode.DOWN)
                        .setScale(Init.SigFigs, RoundingMode.DOWN);

                // Print the steps
                newPrinter = newPrinter.concat("U(" + j + i + ") = " + "( " + Init.A[j][i] + " - " + sum + " )"
                        + " / " + Init.L[j][j] + "\n");
            }
        }

        // Check if the system is consistent
        String temp = Init.methodsUtilities.CheckConsistencyLU(Init);
        if (temp.equals("No Solution") || temp.equals("Infinity Solutions"))
            return;

        // Calculate the answer if there is no errors
        if (Init.er != -1) {
            Init.methodsUtilities.LUForwardSubstitute(Init);
            Init.methodsUtilities.LUBackwardSubstitute(Init);
        }
    }

    /***
     * A method which decomposes the Matrix by cholesky method
     */
    public void CholeskyDecompose() {

        // Check if the matrix is positive definite or not using eigen values test using gauss elimination (lambda > 0)
        BigDecimal[][] tempA = new BigDecimal[Init.n + 1][Init.n + 1];
        for (int i = 0; i <= Init.n; i++) {
            if (Init.n + 1 >= 0) System.arraycopy(Init.A[i], 0, tempA[i], 0, Init.n + 1);
        }
        Init.methodsUtilities.GaussElimination(Init);
        for (int i = 1; i <= Init.n; i++) {
            if (Init.A[i][i].compareTo(BigDecimal.ZERO) < 0) {
                Init.linearPrinter.setPrinter("Not Positive Definite");
                Init.er = -1;
                return;
            }
        }
        Init.A = tempA.clone();

        // Check if the matrix is symmetric or not
        MathContext mc = new MathContext(Init.SigFigs);
        for (int i = 1; i <= Init.n; i++) {
            for (int j = 1; j <= Init.n; j++) {
                if (Init.A[i][j].compareTo(Init.A[j][i]) != 0) {
                    Init.linearPrinter.setPrinter("Not Symmetric");
                    Init.er = -1;
                    return;
                }
            }
        }

        // Calculate L matrix
        for (int i = 1; i <= Init.n; i++) {
            String newPrinter = Init.linearPrinter.getPrinter();
            for (int j = 1; j <= i; j++) {
                BigDecimal sum = BigDecimal.ZERO;
                if (j == i) {
                    newPrinter = newPrinter.concat("\nSumL = ");
                    for (int k = 1; k <= j; k++) {
                        sum = sum.add(Init.L[j][k].multiply(Init.L[j][k]))
                                .setScale(Init.SigFigs, RoundingMode.DOWN);

                        // Print the steps
                        if (k != j)
                            newPrinter = newPrinter.concat("L(" + j + k + ")" + " * " + "L(" + j + k + ")" + " + ");
                        else
                            newPrinter = newPrinter.concat("L(" + j + k + ")" + " * " + "L(" + j + k + ")" + "\n");
                    }
                    Init.L[j][j] = (Init.A[j][j].subtract(sum)).sqrt(mc);

                    // Print the steps
                    newPrinter = newPrinter.concat("L(" + j + j + ") = " + "Sqrt(" +
                            Init.A[j][j].toPlainString() + " - " + sum.toPlainString() + ")" + "\n");
                } else {
                    newPrinter = newPrinter.concat("\nSumU = ");
                    for (int k = 1; k <= j; k++) {
                        sum = sum.add(Init.L[i][k].multiply(Init.L[j][k]))
                                .setScale(Init.SigFigs, RoundingMode.DOWN);

                        // Print the steps
                        if (k != j)
                            newPrinter = newPrinter.concat("L(" + i + k + ")" + " * " + "L(" + j + k + ")" + " + ");
                        else
                            newPrinter = newPrinter.concat("L(" + i + k + ")" + " * " + "L(" + j + k + ")" + "\n");
                    }
                    Init.L[i][j] = (Init.A[i][j].subtract(sum))
                            .divide(Init.L[j][j], Init.SigFigs, RoundingMode.DOWN)
                            .setScale(Init.SigFigs, RoundingMode.DOWN);

                    // Print the steps
                    newPrinter = newPrinter.concat("L(" + i + j + ") = " + "( " + Init.A[i][j] + " - "
                            + sum + " )" + " / " + Init.L[j][j] + "\n");

                }
                Init.linearPrinter.setPrinter(newPrinter);
            }
        }

        // Calculate U matrix by transposing L matrix
        for (int i = 1; i <= Init.n; i++)
            for (int j = 1; j <= Init.n; j++)
                Init.U[i][j] = Init.L[j][i].setScale(Init.SigFigs, RoundingMode.DOWN);

        // Check if the system is consistent
        String temp = Init.methodsUtilities.CheckConsistencyLU(Init);
        if (temp.equals("No Solution") || temp.equals("Infinity Solutions"))
            return;

        // Calculate the answer if there is no errors
        if (Init.er != -1) {
            Init.methodsUtilities.LUForwardSubstitute(Init);
            Init.methodsUtilities.LUBackwardSubstitute(Init);
        }
    }
}
