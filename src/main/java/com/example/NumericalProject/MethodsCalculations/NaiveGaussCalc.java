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
        String checkConsistency = initGauss.methodsUtilities.CheckConsistency(initGauss);
        String newPrinter = initGauss.print.getPrinter().concat("\n" + checkConsistency + "\n");
        initGauss.print.setPrinter(newPrinter);
        if (checkConsistency.equals("No Solution") || checkConsistency.equals("Infinity Solutions")) return;
        if (initGauss.er != -1) {
            initGauss.methodsUtilities.BackwardSubstitute(initGauss);
            initGauss.print.VectorToString(initGauss, initGauss.x);
        }
    }

    public void Eliminate(InitGauss initGauss) {
        for (int k = 1; k <= initGauss.n - 1; k++) {
            initGauss.methodsUtilities.Pivoting(initGauss, k);
            if ((initGauss.A[k][k].divide(initGauss.s[k], initGauss.SigFigs, RoundingMode.DOWN)).abs()
                    .compareTo(initGauss.tol) < 0) {
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
                initGauss.print.MatrixToString(initGauss, initGauss.A);
                initGauss.print.VectorToString(initGauss, initGauss.B);
            }
        }
        if (initGauss.A[initGauss.n][initGauss.n].divide(initGauss.s[initGauss.n], initGauss.SigFigs, RoundingMode.DOWN)
                .abs().compareTo(initGauss.tol) < 0) {
            initGauss.er = -1;
        }
    }
}
