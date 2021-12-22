package com.example.NumericalProject.MethodsCalculations;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;

/***
 * A class that holds all the common calculations that take place on solving linear equations
 */
public class MethodsUtilities {

    /***
     * A method to make partial pivoting to the matrix in the gauss-jordan and naive gauss
     * @param Init an object that hold all the information needed for calculations
     * @param k the specified row
     */
    public void Pivoting(Initialization Init, int k) {
        int p = k;
        BigDecimal dummy;
        BigDecimal z = (Init.A[k][k].divide(Init.s[k], Init.SigFigs, RoundingMode.DOWN)).abs();
        for (int i = k + 1; i <= Init.n; i++) {
            dummy = (Init.A[i][k].divide(Init.s[i], Init.SigFigs, RoundingMode.DOWN)).abs();
            if (dummy.compareTo(z) > 0) {
                z = dummy;
                p = i;
            }
        }
        if (p != k) {
            for (int i = k; i <= Init.n; i++) {
                dummy = Init.A[p][i];
                Init.A[p][i] = Init.A[k][i];
                Init.A[k][i] = dummy;
            }
            dummy = Init.B[p];
            Init.B[p] = Init.B[k];
            Init.B[k] = dummy;
            dummy = Init.s[p];
            Init.s[p] = Init.s[k];
            Init.s[k] = dummy;
        }
    }

    /***
     * A method make partial pivoting to the matrix in the lu decomposition
     * @param Init an object that hold all the information needed for calculations
     * @param k the specified row
     */
    public void LUPivoting(Initialization Init, int k) {
        int p = k;
        BigDecimal dummy;
        BigDecimal z = (Init.A[Init.o[k].intValue()][k].divide(Init.s[Init.o[k].intValue()], Init.SigFigs, RoundingMode.DOWN)).abs();
        for (int i = k + 1; i <= Init.n; i++) {
            dummy = (Init.A[Init.o[i].intValue()][k].divide(Init.s[Init.o[i].intValue()], Init.SigFigs, RoundingMode.DOWN)).abs();
            if (dummy.compareTo(z) > 0) {
                z = dummy;
                p = i;
            }
        }
        dummy = Init.o[p];
        Init.o[p] = Init.o[k];
        Init.o[k] = dummy;
    }

    /***
     * A method to transform the A matrix to a dominant one if it is not (for gauss-seidel method)
     * @param r the row in the check now
     * @param V a visited array for the matrix elements
     * @param R the length of the row
     * @param Init an object that hold all the information needed for calculations
     * @return True if the matrix is transformed to dominant matrix or its dominant and false if not
     */
    private boolean transformToDominant(int r, boolean[] V, int[] R, Initialization Init) {
        if (r == Init.A.length) {
            BigDecimal[][] T = new BigDecimal[Init.n + 1][Init.n + 1];
            for (int i = 1; i < R.length; i++) {
                for (int j = 1; j <= Init.n; j++) {
                    T[i][j] = Init.A[R[i]][j];
                }
            }
            Init.A = T;
            return true;
        }
        for (int i = 1; i <= Init.n; i++) {
            if (V[i])
                continue;
            BigDecimal sum = BigDecimal.ZERO;
            for (int j = 1; j <= Init.n; j++)
                sum = sum.add(Init.A[i][j].abs());
            if (Init.A[i][r].abs().multiply(BigDecimal.valueOf(2)).compareTo(sum) > 0) {
                // diagonally dominant?
                V[i] = true;
                R[r] = i;
                if (transformToDominant(r + 1, V, R, Init))
                    return true;
                V[i] = false;
            }
        }
        return false;
    }

    /***
     * A method that starts the process of checking and converting matrix A to a dominant one
     * @param Init an object that hold all the information needed for calculations
     * @return True if the mission is done, false if failed to transform the matrix to a dominant one
     */
    public boolean makeDominant(Initialization Init) {
        boolean[] visited = new boolean[Init.A.length];
        int[] rows = new int[Init.A.length];
        Arrays.fill(visited, false);
        return !transformToDominant(1, visited, rows, Init);
    }

    /***
     * Do the gauss Elimination
     * @param Init an object that hold all the information needed for calculations
     */
    public void GaussElimination(Initialization Init) {
        for (int k = 1; k <= Init.n - 1; k++) {
            Init.methodsUtilities.Pivoting(Init, k);
            if ((Init.A[k][k].divide(Init.s[k], Init.SigFigs, RoundingMode.DOWN)).abs()
                    .compareTo(BigDecimal.valueOf(Math.pow(10, -Init.SigFigs))) < 0) {
                Init.er = -1;
                return;
            }
            for (int i = k + 1; i <= Init.n; i++) {
                if (i == k) continue;
                BigDecimal factor = Init.A[i][k].divide(Init.A[k][k], Init.SigFigs, RoundingMode.DOWN)
                        .setScale(Init.SigFigs, RoundingMode.DOWN);
                for (int j = k; j <= Init.n; j++) {
                    Init.A[i][j] = Init.A[i][j].subtract(factor.multiply(Init.A[k][j]))
                            .setScale(Init.SigFigs, RoundingMode.DOWN);
                }
                Init.B[i] = Init.B[i].subtract(factor.multiply(Init.B[k]))
                        .setScale(Init.SigFigs, RoundingMode.DOWN);
                Init.print.MatrixToString(Init, Init.A, "Matrix A");
                Init.print.VectorToString(Init, Init.B, "Vector B");
            }
        }
        if (Init.A[Init.n][Init.n].divide(Init.s[Init.n], Init.SigFigs, RoundingMode.DOWN)
                .abs().compareTo(BigDecimal.valueOf(Math.pow(10, -Init.SigFigs))) < 0) {
            Init.er = -1;
        }
    }

    /***
     * Check if the matrix A is consistent or not
     * @param Init an object that hold all the information needed for calculations
     * @return a string that describes the consistency of the system
     */
    public String CheckConsistency(Initialization Init) {
        int Zeros;
        for (int i = 1; i <= Init.n; i++) {
            Zeros = 0;
            for (int j = 1; j <= Init.n; j++) {
                if (Init.A[i][j].abs().compareTo(BigDecimal.valueOf(Math.pow(10, -Init.SigFigs))) <= 0)
                    Zeros++;
                if (Zeros == Init.n && Init.B[i].abs().compareTo(BigDecimal.ZERO) == 0)
                    return "Infinity Solutions";
                else if (Zeros == Init.n) return "No Solution";
            }
        }
        return "Unique Solution";
    }

    /***
     * Check if the L and U are consistent
     * @param Init an object that hold all the information needed for calculations
     * @return a string that describes the consistency of the system
     */
    public String CheckConsistencyLU(Initialization Init) {
        BigDecimal[][] tempA = Init.A.clone();
        BigDecimal[] tempB = Init.B.clone();
        String newPrinter = Init.print.getPrinter();
        Init.A = Init.L.clone();
        String checkConsistency = Init.methodsUtilities.CheckConsistency(Init);
        String newPrinter2 = newPrinter.concat("\n" + checkConsistency + "\n");
        Init.print.setPrinter(newPrinter2);
        if (checkConsistency.equals("No Solution") || checkConsistency.equals("Infinity Solutions")) {
            Init.A = tempA;
            return checkConsistency;
        } else {
            Init.A = Init.U.clone();
            Init.B = Init.y.clone();
            checkConsistency = Init.methodsUtilities.CheckConsistency(Init);
            newPrinter2 = newPrinter.concat("\n" + checkConsistency + "\n");
            Init.print.setPrinter(newPrinter2);
            Init.A = tempA;
            Init.B = tempB;
            if (checkConsistency.equals("No Solution") || checkConsistency.equals("Infinity Solutions"))
                return checkConsistency;
        }
        return "Unique Solution";
    }

    /***
     * A method that do backward substitution and save the result in X vector
     * @param Init an object that hold all the information needed for calculations
     */
    public void BackwardSubstitute(Initialization Init) {
        Init.x[Init.n] = Init.B[Init.n].divide(Init.A[Init.n][Init.n], Init.SigFigs, RoundingMode.DOWN);
        for (int i = Init.n - 1; i >= 1; i--) {
            BigDecimal sum = BigDecimal.ZERO;
            for (int j = i + 1; j <= Init.n; j++) {
                sum = sum.add(Init.A[i][j].multiply(Init.x[j]));
            }
            Init.x[i] = (Init.B[i].subtract(sum)).divide(Init.A[i][i], Init.SigFigs, RoundingMode.DOWN);
        }
    }

    /***
     * A method that do backward substitution for LU Decomposition and save the result in Y vector
     * @param Init an object that hold all the information needed for calculations
     */
    public void LUBackwardSubstitute(Initialization Init) {
        Init.x[Init.n] = Init.y[Init.n].divide(Init.U[Init.n][Init.n], Init.SigFigs, RoundingMode.DOWN);
        for (int i = Init.n - 1; i >= 1; i--) {
            BigDecimal sum = BigDecimal.ZERO;
            for (int j = i + 1; j <= Init.n; j++) {
                sum = sum.add(Init.U[i][j].multiply(Init.x[j]));
            }
            Init.x[i] = (Init.y[i].subtract(sum)).divide(Init.U[i][i], Init.SigFigs, RoundingMode.DOWN);
        }
        Init.print.VectorToString(Init, Init.x, "Vector X");
    }

    /***
     * A method that do forward substitution for LU Decomposition and save the result in X vector (Doolittle method)
     * @param Init an object that hold all the information needed for calculations
     */
    public void LUForwardSubstitute(Initialization Init) {
        Init.y[1] = Init.B[1].divide(Init.L[1][1], Init.SigFigs, RoundingMode.DOWN);
        for (int i = 2; i <= Init.n; i++) {
            BigDecimal sum = Init.B[i];
            for (int j = 1; j <= i - 1; j++) {
                sum = sum.subtract(Init.L[i][j].multiply(Init.y[j]));
            }
            Init.y[i] = sum.divide(Init.L[i][i], Init.SigFigs, RoundingMode.DOWN);
        }
        Init.print.VectorToString(Init, Init.y, "Vector Y");
    }

    /***
     * A method that do backward and forward substitution for LU Decomposition and save the result in Y and X vectors
     * For crout and cholesky methods
     * @param Init an object that hold all the information needed for calculations
     */
    public void LUSubstitute(Initialization Init) {
        // Backward substitution
        Init.y[Init.o[1].intValue()] = Init.B[Init.o[1].intValue()];
        for (int i = 2; i <= Init.n; i++) {
            BigDecimal sum = Init.B[Init.o[i].intValue()];
            for (int j = 1; j <= i - 1; j++) {
                sum = sum.subtract(Init.A[Init.o[i].intValue()][j].multiply(Init.y[Init.o[j].intValue()]));
            }
            Init.y[Init.o[i].intValue()] = sum;
        }
        Init.print.VectorToString(Init, Init.y, "Vector Y");

        // Forward substitution
        Init.x[Init.n] = Init.y[Init.o[Init.n].intValue()].divide(Init.A[Init.o[Init.n].intValue()][Init.n], Init.SigFigs, RoundingMode.DOWN);
        for (int i = Init.n - 1; i >= 1; i--) {
            BigDecimal sum = BigDecimal.ZERO;
            for (int j = i + 1; j <= Init.n; j++) {
                sum = sum.add(Init.A[Init.o[i].intValue()][j].multiply(Init.x[j]));
            }
            Init.x[i] = (Init.y[Init.o[i].intValue()].subtract(sum)).divide(Init.A[Init.o[i].intValue()][i], Init.SigFigs, RoundingMode.DOWN);
        }
        Init.print.VectorToString(Init, Init.x, "Vector X");
    }
}
