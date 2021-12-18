package com.example.NumericalProject;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Print {
    public void MatrixToString(BigDecimal tol, BigDecimal[][] A, int n) {
        System.out.print("\n");
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                if (A[i][j].abs().compareTo(tol) < 0) System.out.print(BigDecimal.valueOf(0).toPlainString() + "  ");
                else
                    System.out.print(A[i][j].setScale(5, RoundingMode.DOWN).stripTrailingZeros().toPlainString() + "  ");
            }
            System.out.print("\n");
        }
    }

    public void VectorToString(BigDecimal tol, BigDecimal[] B, int n) {
        System.out.print("\n");
        for (int i = 1; i <= n; i++) {
            if (B[i].abs().compareTo(tol) < 0) System.out.println(BigDecimal.valueOf(0).toPlainString());
            else System.out.println(B[i].setScale(5, RoundingMode.DOWN).stripTrailingZeros().toPlainString());
        }
    }
}
