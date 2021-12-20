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
        String checkConsistency = initGauss.methodsUtilities.CheckConsistency(initGauss);
        String newPrinter = initGauss.print.getPrinter().concat("\n" + checkConsistency + "\n");
        initGauss.print.setPrinter(newPrinter);
        if (checkConsistency.equals("No Solution") || checkConsistency.equals("Infinity Solutions")) return;
        if (initGauss.er != -1) {
            if (initGauss.n >= 0) System.arraycopy(initGauss.B, 1, initGauss.x, 1, initGauss.n);
            initGauss.print.VectorToString(initGauss, initGauss.x);
        }
    }

    public void Eliminate(int n) {
        for (int k = 1; k <= n; k++) {
            initGauss.methodsUtilities.Pivoting(initGauss, k);
            if ((initGauss.A[k][k].divide(initGauss.s[k], initGauss.SigFigs, RoundingMode.DOWN)).abs()
                    .compareTo(initGauss.tol) < 0) {
                initGauss.er = -1;
                return;
            }
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
                initGauss.print.MatrixToString(initGauss, initGauss.A);
                initGauss.print.VectorToString(initGauss, initGauss.B);
            }
        }
        if (initGauss.A[n][n].divide(initGauss.s[n], initGauss.SigFigs, RoundingMode.DOWN).abs()
                .compareTo(initGauss.tol) < 0) {
            initGauss.er = -1;
        }
        for (int i = 1; i <= n; i++) {
            initGauss.B[i] = initGauss.B[i].divide(initGauss.A[i][i], initGauss.SigFigs, RoundingMode.DOWN)
                    .setScale(initGauss.SigFigs, RoundingMode.DOWN);
        }
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                if (i == j) initGauss.A[i][j] = BigDecimal.valueOf(1);
            }
        }
        initGauss.print.MatrixToString(initGauss, initGauss.A);
        initGauss.print.VectorToString(initGauss, initGauss.B);
    }
}
