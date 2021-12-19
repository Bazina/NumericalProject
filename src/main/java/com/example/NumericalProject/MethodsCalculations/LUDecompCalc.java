package com.example.NumericalProject.MethodsCalculations;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class LUDecompCalc {
    private final InitGauss initGauss;

    public LUDecompCalc(InitGauss initGauss) {
        this.initGauss = initGauss;
    }

    public void LUDecomp(String type) {
        initGauss.gauss();
        switch (type.toLowerCase()) {
            case "doolittle" -> DoolittleDecompose();
            case "crout" -> CroutDecompose();
            case "cholesky" -> CholeskyDecompose();
        }
        if (initGauss.er != -1) {
            initGauss.print.MatrixToString(initGauss.tol, initGauss.L, initGauss.n);
            initGauss.print.MatrixToString(initGauss.tol, initGauss.U, initGauss.n);
        }
    }

    public void DoolittleDecompose() {
        for (int i = 1; i <= initGauss.n; i++) {
            initGauss.o[i] = BigDecimal.valueOf(i);
            initGauss.s[i] = initGauss.A[i][1].abs();
            for (int j = 2; j <= initGauss.n; j++) {
                if (initGauss.A[i][j].abs().compareTo(initGauss.s[i]) > 0) {
                    initGauss.s[i] = initGauss.A[i][j].abs();
                }
            }
        }
        for (int k = 1; k <= initGauss.n - 1; k++) {
            initGauss.methodsUtilities.LUPivoting(initGauss, k);
            if (((initGauss.A[initGauss.o[k].intValue()][k].abs()).divide(initGauss.s[initGauss.o[k].intValue()], 20, RoundingMode.DOWN)).compareTo(initGauss.tol) < 0) {
                initGauss.er = -1;
                return;
            }
            for (int i = k + 1; i <= initGauss.n; i++) {
                BigDecimal factor = initGauss.A[initGauss.o[i].intValue()][k].divide(initGauss.A[initGauss.o[k].intValue()][k], 20, RoundingMode.DOWN);
                initGauss.A[initGauss.o[i].intValue()][k] = factor;
                for (int j = k + 1; j <= initGauss.n; j++) {
                    initGauss.A[initGauss.o[i].intValue()][j] = initGauss.A[initGauss.o[i].intValue()][j].subtract(factor.multiply(initGauss.A[initGauss.o[k].intValue()][j]));
                }
                initGauss.print.MatrixToString(initGauss.tol, initGauss.A, initGauss.n);
                initGauss.print.VectorToString(initGauss.tol, initGauss.B, initGauss.n);
            }
        }
        if (((initGauss.A[initGauss.o[initGauss.n].intValue()][initGauss.n].abs()).divide(initGauss.s[initGauss.o[initGauss.n].intValue()], 20, RoundingMode.DOWN)).compareTo(initGauss.tol) < 0) {
            initGauss.er = -1;
        }
        if (initGauss.er != -1) {
            initGauss.methodsUtilities.LUSubstitute(initGauss);
            initGauss.print.VectorToString(initGauss.tol, initGauss.x, initGauss.n);
        }
    }

    public void CroutDecompose() {
        BigDecimal sum;
        for (int i = 1; i <= initGauss.n; i++) {
            initGauss.U[i][i] = BigDecimal.ONE;
        }

        for (int j = 1; j <= initGauss.n; j++) {
            for (int i = j; i <= initGauss.n; i++) {
                sum = BigDecimal.ZERO;
                for (int k = 1; k <= j; k++) {
                    sum = sum.add(initGauss.L[i][k].multiply(initGauss.U[k][j]));
                }
                initGauss.L[i][j] = initGauss.A[i][j].subtract(sum);
            }
            for (int i = j + 1; i <= initGauss.n; i++) {
                sum = BigDecimal.ZERO;
                for (int k = 1; k <= initGauss.n; k++) {
                    sum = sum.add(initGauss.L[j][k].multiply(initGauss.U[k][i]));
                }
                if (initGauss.L[j][j].compareTo(BigDecimal.ZERO) == 0) {
                    initGauss.er = -1;
                }
                initGauss.U[j][i] = (initGauss.A[j][i].subtract(sum)).divide(initGauss.L[j][j], 20, RoundingMode.DOWN);
            }
        }
        if (initGauss.er != -1) {
            initGauss.methodsUtilities.LUForwardSubstitute(initGauss);
            initGauss.methodsUtilities.LUBackwardSubstitute(initGauss);
        }
    }

    public void CholeskyDecompose() {
        MathContext mc = new MathContext(20);
        for (int i = 1; i <= initGauss.n; i++) {
            for (int j = 1; j <= initGauss.n; j++) {
                if (initGauss.A[i][j].compareTo(initGauss.A[j][i]) != 0) {
                    System.out.println("Not Symmetric");
                    return;
                }
            }
        }
        for (int i = 1; i <= initGauss.n; i++) {
            for (int j = 1; j <= i; j++) {
                BigDecimal sum = BigDecimal.ZERO;
                if (j == i) {
                    for (int k = 1; k <= j; k++) {
                        sum = sum.add(initGauss.L[j][k].multiply(initGauss.L[j][k]));
                    }
                    initGauss.L[j][j] = (initGauss.A[j][j].subtract(sum)).sqrt(mc);
                } else {
                    for (int k = 1; k <= j; k++) {
                        sum = sum.add(initGauss.L[i][k].multiply(initGauss.L[j][k]));
                    }
                    initGauss.L[i][j] = (initGauss.A[i][j].subtract(sum)).divide(initGauss.L[j][j], 20, RoundingMode.DOWN);
                }
            }
        }
        for (int i = 1; i <= initGauss.n; i++)
            for (int j = 1; j <= initGauss.n; j++)
                initGauss.U[i][j] = initGauss.L[j][i];

        if (initGauss.er != -1) {
            initGauss.methodsUtilities.LUForwardSubstitute(initGauss);
            initGauss.methodsUtilities.LUBackwardSubstitute(initGauss);
        }
    }
}
