package com.example.NumericalProject.MethodsCalculations;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class LUDecompDoolittleCalc {
    private final InitGauss initGauss;

    public LUDecompDoolittleCalc(InitGauss initGauss) {
        this.initGauss = initGauss;
    }

    public void LUDecompDoolittle() {
        initGauss.gauss();
        Decompose(initGauss.n);
        if (initGauss.er != -1) {
            initGauss.methodsUtilities.LUSubstitute(initGauss);
            initGauss.print.VectorToString(initGauss.tol, initGauss.x, initGauss.n);
        }
    }

    public void Decompose(int n) {
        for (int i = 1; i <= n; i++) {
            initGauss.o[i] = BigDecimal.valueOf(i);
            initGauss.s[i] = initGauss.A[i][1].abs();
            for (int j = 2; j <= n; j++) {
                if (initGauss.A[i][j].abs().compareTo(initGauss.s[i]) > 0) {
                    initGauss.s[i] = initGauss.A[i][j].abs();
                }
            }
        }
        for (int k = 1; k <= n - 1; k++) {
            initGauss.methodsUtilities.LUPivoting(initGauss, k);
            if (((initGauss.A[initGauss.o[k].intValue()][k].abs()).divide(initGauss.s[initGauss.o[k].intValue()], 20, RoundingMode.DOWN)).compareTo(initGauss.tol) < 0) {
                initGauss.er = -1;
                return;
            }
            for (int i = k + 1; i <= n; i++) {
                BigDecimal factor = initGauss.A[initGauss.o[i].intValue()][k].divide(initGauss.A[initGauss.o[k].intValue()][k], 20, RoundingMode.DOWN);
                initGauss.A[initGauss.o[i].intValue()][k] = factor;
                for (int j = k + 1; j <= n; j++) {
                    initGauss.A[initGauss.o[i].intValue()][j] = initGauss.A[initGauss.o[i].intValue()][j].subtract(factor.multiply(initGauss.A[initGauss.o[k].intValue()][j]));
                }
                initGauss.print.MatrixToString(initGauss.tol, initGauss.A, n);
                initGauss.print.VectorToString(initGauss.tol, initGauss.B, n);
            }
        }
        if (((initGauss.A[initGauss.o[n].intValue()][n].abs()).divide(initGauss.s[initGauss.o[n].intValue()], 20, RoundingMode.DOWN)).compareTo(initGauss.tol) < 0) {
            initGauss.er = -1;
        }
    }
}
