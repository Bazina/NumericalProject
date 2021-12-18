package com.example.NumericalProject.MethodsCalculations;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class NaiveGaussCalc {
    private final InitGauss initGauss;

    public NaiveGaussCalc(InitGauss initGauss) {
        this.initGauss = initGauss;
    }

    //BigDecimal[][] A, BigDecimal[] B, int n
    public void NaiveGauss() {
        initGauss.gauss();
        Eliminate(initGauss.n);
        if (initGauss.er != -1) {
            initGauss.methodsUtilities.Substitute(initGauss.A, initGauss.B, initGauss.x, initGauss.n);
            initGauss.print.VectorToString(initGauss.tol, initGauss.x, initGauss.n);
        }
    }

    public void Eliminate(int n) {
        for (int k = 1; k <= n - 1; k++) {
            initGauss.methodsUtilities.Pivoting(initGauss.A, initGauss.B, initGauss.s, n, k);
            if ((initGauss.A[k][k].divide(initGauss.s[k], 20, RoundingMode.DOWN)).abs().compareTo(initGauss.tol) < 0) {
                initGauss.er = -1;
                return;
            }
            for (int i = k + 1; i <= n; i++) {
                if (i == k) continue;
                BigDecimal factor = initGauss.A[i][k].divide(initGauss.A[k][k], 20, RoundingMode.DOWN).stripTrailingZeros();
                for (int j = k; j <= n; j++) {
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
    }
}
