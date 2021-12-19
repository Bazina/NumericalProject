package com.example.NumericalProject.MethodsCalculations;

import java.math.BigDecimal;
import java.math.RoundingMode;

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
        initGauss.print.VectorToString(initGauss.tol, initGauss.x, initGauss.n);
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
        initGauss.print.VectorToString(initGauss.tol, initGauss.y, initGauss.n);
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
        initGauss.print.VectorToString(initGauss.tol, initGauss.y, initGauss.n);

        initGauss.x[initGauss.n] = initGauss.y[initGauss.o[initGauss.n].intValue()].divide(initGauss.A[initGauss.o[initGauss.n].intValue()][initGauss.n], 20, RoundingMode.DOWN);
        for (int i = initGauss.n - 1; i >= 1; i--) {
            BigDecimal sum = BigDecimal.ZERO;
            for (int j = i + 1; j <= initGauss.n; j++) {
                sum = sum.add(initGauss.A[initGauss.o[i].intValue()][j].multiply(initGauss.x[j]));
            }
            initGauss.x[i] = (initGauss.y[initGauss.o[i].intValue()].subtract(sum)).divide(initGauss.A[initGauss.o[i].intValue()][i], 20, RoundingMode.DOWN);
        }
        initGauss.print.VectorToString(initGauss.tol, initGauss.x, initGauss.n);
    }
}
