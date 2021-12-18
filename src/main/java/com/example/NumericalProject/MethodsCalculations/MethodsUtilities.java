package com.example.NumericalProject.MethodsCalculations;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MethodsUtilities {
    public void Pivoting(BigDecimal[][] A, BigDecimal[] B, BigDecimal[] s, int n, int k) {
        int p = k;
        BigDecimal dummy;
        BigDecimal z = (A[k][k].divide(s[k], 10, RoundingMode.DOWN)).abs();
        for (int i = k + 1; i <= n; i++) {
            dummy = (A[i][k].divide(s[i], 10, RoundingMode.DOWN)).abs();
            if (dummy.compareTo(z) > 0) {
                z = dummy;
                p = i;
            }
        }
        if (p != k) {
            for (int i = k; i <= n; i++) {
                dummy = A[p][i];
                A[p][i] = A[k][i];
                A[k][i] = dummy;
            }
            dummy = B[p];
            B[p] = B[k];
            B[k] = dummy;
            dummy = s[p];
            s[p] = s[k];
            s[k] = dummy;
        }
    }
    public void Substitute(BigDecimal[][] A, BigDecimal[] B, BigDecimal[] x, int n) {
        x[n] = B[n].divide(A[n][n], 10, RoundingMode.DOWN);
        for (int i = n - 1; i >= 1; i--) {
            BigDecimal sum = BigDecimal.valueOf(0);
            for (int j = i + 1; j <= n; j++) {
                sum = sum.add(A[i][j].multiply(x[j]));
            }
            x[i] = (B[i].subtract(sum)).divide(A[i][i], 10, RoundingMode.DOWN);
        }
    }
}
