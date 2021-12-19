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
        Eliminate(initGauss);
        if (initGauss.er != -1) {
            initGauss.methodsUtilities.BackwardSubstitute(initGauss);
            initGauss.print.VectorToString(initGauss.SigFigs, initGauss.x, initGauss.n);
        }
    }

    public void Eliminate(InitGauss initGauss) {
        for (int k = 1; k <= initGauss.n - 1; k++) {
            initGauss.methodsUtilities.Pivoting(initGauss, k);
            if ((initGauss.A[k][k].divide(initGauss.s[k], initGauss.SigFigs, RoundingMode.DOWN)).abs().compareTo(initGauss.tol) < 0) {
                initGauss.er = -1;
                return;
            }
            for (int i = k + 1; i <= initGauss.n; i++) {
                if (i == k) continue;
                BigDecimal factor = initGauss.A[i][k].divide(initGauss.A[k][k], initGauss.SigFigs, RoundingMode.DOWN);
                for (int j = k; j <= initGauss.n; j++) {
                    initGauss.A[i][j] = initGauss.A[i][j].subtract(factor.multiply(initGauss.A[k][j]));
                }
                initGauss.B[i] = initGauss.B[i].subtract(factor.multiply(initGauss.B[k]));
                initGauss.print.MatrixToString(initGauss.SigFigs, initGauss.A, initGauss.n);
                initGauss.print.VectorToString(initGauss.SigFigs, initGauss.B, initGauss.n);
            }
        }
        if (initGauss.A[initGauss.n][initGauss.n].divide(initGauss.s[initGauss.n], initGauss.SigFigs, RoundingMode.DOWN).abs().compareTo(initGauss.tol) < 0) {
            initGauss.er = -1;
        }
    }
}
