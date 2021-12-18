package com.example.NumericalProject.MethodsCalculations;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class GaussJordanCalc {
    private final InitGauss initGauss;

    public GaussJordanCalc(InitGauss initGauss) {
        this.initGauss = initGauss;
    }

    //BigDecimal[][] A, BigDecimal[] B, int n
    public void GaussJordan() {
        initGauss.gauss();
        Eliminate(initGauss.n);
        if (initGauss.er != -1) {
            if (initGauss.n >= 0) System.arraycopy(initGauss.B, 1, initGauss.x, 1, initGauss.n);
            initGauss.print.VectorToString(initGauss.tol, initGauss.x, initGauss.n);
        }
    }

    public void Eliminate(int n) {
        for (int k = 1; k <= n; k++) {
            initGauss.methodsUtilities.Pivoting(initGauss.A, initGauss.B, initGauss.s, n, k);
            if ((initGauss.A[k][k].divide(initGauss.s[k], 20, RoundingMode.DOWN)).abs().compareTo(initGauss.tol) < 0) {
                initGauss.er = -1;
                return;
            }
            for (int i = 1; i <= n; i++) {
                if (i == k) continue;
                BigDecimal factor = initGauss.A[i][k].divide(initGauss.A[k][k], 20, RoundingMode.DOWN).stripTrailingZeros();
                for (int j = 1; j <= n; j++) {
                    initGauss.A[i][j] = initGauss.A[i][j].subtract(factor.multiply(initGauss.A[k][j]));
                }
                initGauss.B[i] = initGauss.B[i].subtract(factor.multiply(initGauss.B[k]));
                initGauss.print.MatrixToString(initGauss.tol, initGauss.A, n);
                initGauss.print.VectorToString(initGauss.tol, initGauss.B, n);
            }
        }
        if (initGauss.A[n][n].divide(initGauss.s[n], 20, RoundingMode.DOWN).abs().compareTo(initGauss.tol) < 0) {
            initGauss.er = -1;
        }
        for (int i = 1; i <= n; i++) {
            initGauss.B[i] = initGauss.B[i].divide(initGauss.A[i][i], 20, RoundingMode.DOWN);
        }
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                if (i == j) initGauss.A[i][j] = BigDecimal.valueOf(1);
            }
        }
        initGauss.print.MatrixToString(initGauss.tol, initGauss.A, initGauss.n);
        initGauss.print.VectorToString(initGauss.tol, initGauss.B, initGauss.n);
    }
}
