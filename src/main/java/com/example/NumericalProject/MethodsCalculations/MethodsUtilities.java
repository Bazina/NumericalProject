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
     * @param initGauss an object that hold all the matrices needed for calculations
     * @param k the specified row
     */
    public void Pivoting(Initialization initGauss, int k) {
        int p = k;
        BigDecimal dummy;
        BigDecimal z = (initGauss.A[k][k].divide(initGauss.s[k], initGauss.SigFigs, RoundingMode.DOWN)).abs();
        for (int i = k + 1; i <= initGauss.n; i++) {
            dummy = (initGauss.A[i][k].divide(initGauss.s[i], initGauss.SigFigs, RoundingMode.DOWN)).abs();
            if (dummy.compareTo(z) > 0) {
                z = dummy;
                p = i;
            }
        }
        if (p != k) {
            for (int i = k; i <= initGauss.n; i++) {
                dummy = initGauss.A[p][i];
                initGauss.A[p][i] = initGauss.A[k][i];
                initGauss.A[k][i] = dummy;
            }
            dummy = initGauss.B[p];
            initGauss.B[p] = initGauss.B[k];
            initGauss.B[k] = dummy;
            dummy = initGauss.s[p];
            initGauss.s[p] = initGauss.s[k];
            initGauss.s[k] = dummy;
        }
    }

    /***
     * A method make partial pivoting to the matrix in the lu decomposition
     * @param initGauss an object that hold all the matrices needed for calculations
     * @param k the specified row
     */
    public void LUPivoting(Initialization initGauss, int k) {
        int p = k;
        BigDecimal dummy;
        BigDecimal z = (initGauss.A[initGauss.o[k].intValue()][k].divide(initGauss.s[initGauss.o[k].intValue()], initGauss.SigFigs, RoundingMode.DOWN)).abs();
        for (int i = k + 1; i <= initGauss.n; i++) {
            dummy = (initGauss.A[initGauss.o[i].intValue()][k].divide(initGauss.s[initGauss.o[i].intValue()], initGauss.SigFigs, RoundingMode.DOWN)).abs();
            if (dummy.compareTo(z) > 0) {
                z = dummy;
                p = i;
            }
        }
        dummy = initGauss.o[p];
        initGauss.o[p] = initGauss.o[k];
        initGauss.o[k] = dummy;
    }

    /***
     * A method to transform the A matrix to a dominant one if it is not (for gauss-seidel method)
     * @param r the row in the check now
     * @param V a visited array for the matrix elements
     * @param R the length of the row
     * @param initGauss an object that hold all the matrices needed for calculations
     * @return True if the matrix is transformed to dominant matrix or its dominant and false if not
     */
    private boolean transformToDominant(int r, boolean[] V, int[] R, Initialization initGauss) {
        if (r == initGauss.A.length) {
            BigDecimal[][] T = new BigDecimal[initGauss.n + 1][initGauss.n + 1];
            for (int i = 1; i < R.length; i++) {
                for (int j = 1; j <= initGauss.n; j++) {
                    T[i][j] = initGauss.A[R[i]][j];
                }
            }
            initGauss.A = T;
            return true;
        }
        for (int i = 1; i <= initGauss.n; i++) {
            if (V[i])
                continue;
            BigDecimal sum = BigDecimal.ZERO;
            for (int j = 1; j <= initGauss.n; j++)
                sum = sum.add(initGauss.A[i][j].abs());
            if (initGauss.A[i][r].abs().multiply(BigDecimal.valueOf(2)).compareTo(sum) > 0) {
                // diagonally dominant?
                V[i] = true;
                R[r] = i;
                if (transformToDominant(r + 1, V, R, initGauss))
                    return true;
                V[i] = false;
            }
        }
        return false;
    }

    /***
     * A method that starts the process of checking and converting matrix A to a dominant one
     * @param initGauss an object that hold all the matrices needed for calculations
     * @return True if the mission is done, false if failed to transform the matrix to a dominant one
     */
    public boolean makeDominant(Initialization initGauss) {
        boolean[] visited = new boolean[initGauss.A.length];
        int[] rows = new int[initGauss.A.length];
        Arrays.fill(visited, false);
        return !transformToDominant(1, visited, rows, initGauss);
    }

    /***
     * Do the gauss Elimination
     * @param initGauss an object that hold all the matrices needed for calculations
     */
    public void GaussElimination(Initialization initGauss) {
        for (int k = 1; k <= initGauss.n - 1; k++) {
            initGauss.methodsUtilities.Pivoting(initGauss, k);
            if ((initGauss.A[k][k].divide(initGauss.s[k], initGauss.SigFigs, RoundingMode.DOWN)).abs()
                    .compareTo(BigDecimal.valueOf(Math.pow(10, -initGauss.SigFigs))) < 0) {
                initGauss.er = -1;
                return;
            }
            for (int i = k + 1; i <= initGauss.n; i++) {
                if (i == k) continue;
                BigDecimal factor = initGauss.A[i][k].divide(initGauss.A[k][k], initGauss.SigFigs, RoundingMode.DOWN)
                        .setScale(initGauss.SigFigs, RoundingMode.DOWN);
                for (int j = k; j <= initGauss.n; j++) {
                    initGauss.A[i][j] = initGauss.A[i][j].subtract(factor.multiply(initGauss.A[k][j]))
                            .setScale(initGauss.SigFigs, RoundingMode.DOWN);
                }
                initGauss.B[i] = initGauss.B[i].subtract(factor.multiply(initGauss.B[k]))
                        .setScale(initGauss.SigFigs, RoundingMode.DOWN);
                initGauss.print.MatrixToString(initGauss, initGauss.A, "Matrix A");
                initGauss.print.VectorToString(initGauss, initGauss.B, "Vector B");
            }
        }
        if (initGauss.A[initGauss.n][initGauss.n].divide(initGauss.s[initGauss.n], initGauss.SigFigs, RoundingMode.DOWN)
                .abs().compareTo(BigDecimal.valueOf(Math.pow(10, -initGauss.SigFigs))) < 0) {
            initGauss.er = -1;
        }
    }

    public String CheckConsistency(Initialization initGauss) {
        int Zeros;
        for (int i = 1; i <= initGauss.n; i++) {
            Zeros = 0;
            for (int j = 1; j <= initGauss.n; j++) {
                if (initGauss.A[i][j].abs().compareTo(BigDecimal.valueOf(Math.pow(10, -initGauss.SigFigs))) <= 0)
                    Zeros++;
                if (Zeros == initGauss.n && initGauss.B[i].abs().compareTo(BigDecimal.ZERO) == 0)
                    return "Infinity Solutions";
                else if (Zeros == initGauss.n) return "No Solution";
            }
        }
        return "Unique Solution";
    }

    public String CheckConsistencyLU(Initialization initGauss) {
        BigDecimal[][] tempA = initGauss.A.clone();
        BigDecimal[] tempB = initGauss.B.clone();
        String newPrinter = initGauss.print.getPrinter();
        initGauss.A = initGauss.L.clone();
        String checkConsistency = initGauss.methodsUtilities.CheckConsistency(initGauss);
        String newPrinter2 = newPrinter.concat("\n" + checkConsistency + "\n");
        initGauss.print.setPrinter(newPrinter2);
        if (checkConsistency.equals("No Solution") || checkConsistency.equals("Infinity Solutions")) {
            initGauss.A = tempA;
            return checkConsistency;
        } else {
            initGauss.A = initGauss.U.clone();
            initGauss.B = initGauss.y.clone();
            checkConsistency = initGauss.methodsUtilities.CheckConsistency(initGauss);
            newPrinter2 = newPrinter.concat("\n" + checkConsistency + "\n");
            initGauss.print.setPrinter(newPrinter2);
            initGauss.A = tempA;
            initGauss.B = tempB;
            if (checkConsistency.equals("No Solution") || checkConsistency.equals("Infinity Solutions"))
                return checkConsistency;
        }
        return "Unique Solution";
    }

    public void BackwardSubstitute(Initialization initGauss) {
        initGauss.x[initGauss.n] = initGauss.B[initGauss.n].divide(initGauss.A[initGauss.n][initGauss.n], initGauss.SigFigs, RoundingMode.DOWN);
        for (int i = initGauss.n - 1; i >= 1; i--) {
            BigDecimal sum = BigDecimal.ZERO;
            for (int j = i + 1; j <= initGauss.n; j++) {
                sum = sum.add(initGauss.A[i][j].multiply(initGauss.x[j]));
            }
            initGauss.x[i] = (initGauss.B[i].subtract(sum)).divide(initGauss.A[i][i], initGauss.SigFigs, RoundingMode.DOWN);
        }
    }

    public void LUBackwardSubstitute(Initialization initGauss) {
        initGauss.x[initGauss.n] = initGauss.y[initGauss.n].divide(initGauss.U[initGauss.n][initGauss.n], initGauss.SigFigs, RoundingMode.DOWN);
        for (int i = initGauss.n - 1; i >= 1; i--) {
            BigDecimal sum = BigDecimal.ZERO;
            for (int j = i + 1; j <= initGauss.n; j++) {
                sum = sum.add(initGauss.U[i][j].multiply(initGauss.x[j]));
            }
            initGauss.x[i] = (initGauss.y[i].subtract(sum)).divide(initGauss.U[i][i], initGauss.SigFigs, RoundingMode.DOWN);
        }
        initGauss.print.VectorToString(initGauss, initGauss.x, "Vector X");
    }

    public void LUForwardSubstitute(Initialization initGauss) {
        initGauss.y[1] = initGauss.B[1].divide(initGauss.L[1][1], initGauss.SigFigs, RoundingMode.DOWN);
        for (int i = 2; i <= initGauss.n; i++) {
            BigDecimal sum = initGauss.B[i];
            for (int j = 1; j <= i - 1; j++) {
                sum = sum.subtract(initGauss.L[i][j].multiply(initGauss.y[j]));
            }
            initGauss.y[i] = sum.divide(initGauss.L[i][i], initGauss.SigFigs, RoundingMode.DOWN);
        }
        initGauss.print.VectorToString(initGauss, initGauss.y, "Vector Y");
    }

    public void LUSubstitute(Initialization initGauss) {
        initGauss.y[initGauss.o[1].intValue()] = initGauss.B[initGauss.o[1].intValue()];
        for (int i = 2; i <= initGauss.n; i++) {
            BigDecimal sum = initGauss.B[initGauss.o[i].intValue()];
            for (int j = 1; j <= i - 1; j++) {
                sum = sum.subtract(initGauss.A[initGauss.o[i].intValue()][j].multiply(initGauss.y[initGauss.o[j].intValue()]));
            }
            initGauss.y[initGauss.o[i].intValue()] = sum;
        }
        initGauss.print.VectorToString(initGauss, initGauss.y, "Vector Y");

        initGauss.x[initGauss.n] = initGauss.y[initGauss.o[initGauss.n].intValue()].divide(initGauss.A[initGauss.o[initGauss.n].intValue()][initGauss.n], initGauss.SigFigs, RoundingMode.DOWN);
        for (int i = initGauss.n - 1; i >= 1; i--) {
            BigDecimal sum = BigDecimal.ZERO;
            for (int j = i + 1; j <= initGauss.n; j++) {
                sum = sum.add(initGauss.A[initGauss.o[i].intValue()][j].multiply(initGauss.x[j]));
            }
            initGauss.x[i] = (initGauss.y[initGauss.o[i].intValue()].subtract(sum)).divide(initGauss.A[initGauss.o[i].intValue()][i], initGauss.SigFigs, RoundingMode.DOWN);
        }
        initGauss.print.VectorToString(initGauss, initGauss.x, "Vector X");
    }
}
