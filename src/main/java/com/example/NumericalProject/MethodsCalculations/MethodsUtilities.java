package com.example.NumericalProject.MethodsCalculations;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;

public class MethodsUtilities {
    public void Pivoting(InitGauss initGauss, int k) {
        int p = k;
        BigDecimal dummy;
        BigDecimal z = (initGauss.A[k][k].divide(initGauss.s[k], 20, RoundingMode.DOWN)).abs();
        for (int i = k + 1; i <= initGauss.n; i++) {
            dummy = (initGauss.A[i][k].divide(initGauss.s[i], 20, RoundingMode.DOWN)).abs();
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

    public void LUPivoting(InitGauss initGauss, int k) {
        int p = k;
        BigDecimal dummy;
        BigDecimal z = (initGauss.A[initGauss.o[k].intValue()][k].divide(initGauss.s[initGauss.o[k].intValue()], 20, RoundingMode.DOWN)).abs();
        for (int i = k + 1; i <= initGauss.n; i++) {
            dummy = (initGauss.A[initGauss.o[i].intValue()][k].divide(initGauss.s[initGauss.o[i].intValue()], 20, RoundingMode.DOWN)).abs();
            if (dummy.compareTo(z) > 0) {
                z = dummy;
                p = i;
            }
        }
        dummy = initGauss.o[p];
        initGauss.o[p] = initGauss.o[k];
        initGauss.o[k] = dummy;
    }

    private boolean transformToDominant(int r, boolean[] V, int[] R, InitGauss initGauss) {
        if (r == initGauss.A.length) {
            BigDecimal[][] T = new BigDecimal[initGauss.n + 1][initGauss.n + 1];
            for (int i = 1; i <= R.length; i++) {
                if (initGauss.n + 1 >= 0) System.arraycopy(initGauss.A[R[i]], 1, T[i], 1, initGauss.n + 1);
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

    public boolean makeDominant(InitGauss initGauss) {
        boolean[] visited = new boolean[initGauss.A.length];
        int[] rows = new int[initGauss.A.length];
        Arrays.fill(visited, false);
        return !transformToDominant(0, visited, rows, initGauss);
    }

    public String CheckConsistency(InitGauss initGauss) {
        int Zeros;
        for (int i = 1; i <= initGauss.n; i++) {
            Zeros = 0;
            for (int j = 1; j <= initGauss.n; j++) {
                if (initGauss.A[i][j].compareTo(BigDecimal.valueOf(Math.pow(10, -initGauss.SigFigs))) <= 0) Zeros++;
                if (Zeros == initGauss.n && initGauss.B[i].compareTo(BigDecimal.ZERO) == 0) return "Infinity Solutions";
                else if (Zeros == initGauss.n) return "No Solution";
            }
        }
        return "Unique Solution";
    }

    public void BackwardSubstitute(InitGauss initGauss) {
        initGauss.x[initGauss.n] = initGauss.B[initGauss.n].divide(initGauss.A[initGauss.n][initGauss.n], 20, RoundingMode.DOWN);
        for (int i = initGauss.n - 1; i >= 1; i--) {
            BigDecimal sum = BigDecimal.ZERO;
            for (int j = i + 1; j <= initGauss.n; j++) {
                sum = sum.add(initGauss.A[i][j].multiply(initGauss.x[j]));
            }
            initGauss.x[i] = (initGauss.B[i].subtract(sum)).divide(initGauss.A[i][i], 20, RoundingMode.DOWN);
        }
    }

    public void LUBackwardSubstitute(InitGauss initGauss) {
        initGauss.x[initGauss.n] = initGauss.y[initGauss.n].divide(initGauss.U[initGauss.n][initGauss.n], 20, RoundingMode.DOWN);
        for (int i = initGauss.n - 1; i >= 1; i--) {
            BigDecimal sum = BigDecimal.ZERO;
            for (int j = i + 1; j <= initGauss.n; j++) {
                sum = sum.add(initGauss.U[i][j].multiply(initGauss.x[j]));
            }
            initGauss.x[i] = (initGauss.y[i].subtract(sum)).divide(initGauss.U[i][i], 20, RoundingMode.DOWN);
        }
        initGauss.print.VectorToString(initGauss, initGauss.x);
    }

    public void LUForwardSubstitute(InitGauss initGauss) {
        initGauss.y[1] = initGauss.B[1].divide(initGauss.L[1][1], 20, RoundingMode.DOWN);
        for (int i = 2; i <= initGauss.n; i++) {
            BigDecimal sum = initGauss.B[i];
            for (int j = 1; j <= i - 1; j++) {
                sum = sum.subtract(initGauss.L[i][j].multiply(initGauss.y[j]));
            }
            initGauss.y[i] = sum.divide(initGauss.L[i][i], 20, RoundingMode.DOWN);
        }
        initGauss.print.VectorToString(initGauss, initGauss.y);
    }

    public void LUSubstitute(InitGauss initGauss) {
        initGauss.y[initGauss.o[1].intValue()] = initGauss.B[initGauss.o[1].intValue()];
        for (int i = 2; i <= initGauss.n; i++) {
            BigDecimal sum = initGauss.B[initGauss.o[i].intValue()];
            for (int j = 1; j <= i - 1; j++) {
                sum = sum.subtract(initGauss.A[initGauss.o[i].intValue()][j].multiply(initGauss.y[initGauss.o[j].intValue()]));
            }
            initGauss.y[initGauss.o[i].intValue()] = sum;
        }
        initGauss.print.VectorToString(initGauss, initGauss.y);

        initGauss.x[initGauss.n] = initGauss.y[initGauss.o[initGauss.n].intValue()].divide(initGauss.A[initGauss.o[initGauss.n].intValue()][initGauss.n], 20, RoundingMode.DOWN);
        for (int i = initGauss.n - 1; i >= 1; i--) {
            BigDecimal sum = BigDecimal.ZERO;
            for (int j = i + 1; j <= initGauss.n; j++) {
                sum = sum.add(initGauss.A[initGauss.o[i].intValue()][j].multiply(initGauss.x[j]));
            }
            initGauss.x[i] = (initGauss.y[initGauss.o[i].intValue()].subtract(sum)).divide(initGauss.A[initGauss.o[i].intValue()][i], 20, RoundingMode.DOWN);
        }
        initGauss.print.VectorToString(initGauss, initGauss.x);
    }
}
