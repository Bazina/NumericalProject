package com.example.NumericalProject;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Print {
    public String printer = "";
    public void MatrixToString(BigDecimal tol, BigDecimal[][] A, int n) {
        System.out.print("\n");
        printer = printer.concat("\n");
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                if (j == 1) printer = printer.concat("|\t");
                if (A[i][j].abs().compareTo(tol) < 0) {
                    System.out.print(BigDecimal.valueOf(0).toPlainString() + "\t");
                    printer = printer.concat(BigDecimal.valueOf(0).toPlainString() + "\t");
                } else {
                    System.out.print(A[i][j].setScale(5, RoundingMode.DOWN).stripTrailingZeros().toPlainString() + "\t");
                    printer = printer.concat(A[i][j].setScale(5, RoundingMode.DOWN).stripTrailingZeros().toPlainString() + "\t");
                }
                if (j == n) printer = printer.concat("\t|");
            }
            System.out.print("\n");
            printer = printer.concat("\n");
        }
    }

    public void VectorToString(BigDecimal tol, BigDecimal[] B, int n) {
        System.out.print("\n");
        printer = printer.concat("\n" + "[");
        for (int i = 1; i <= n; i++) {
            if (i != 1) printer = printer.concat(", ");
            if (B[i].abs().compareTo(tol) < 0) {
                System.out.println(BigDecimal.valueOf(0).toPlainString());
                printer = printer.concat(BigDecimal.valueOf(0).toPlainString() + "  ");
            } else {
                System.out.println(B[i].setScale(5, RoundingMode.DOWN).stripTrailingZeros().toPlainString());
                printer = printer.concat(B[i].setScale(5, RoundingMode.DOWN).stripTrailingZeros().toPlainString());
            }
        }
        printer = printer.concat("]\n");
    }
}
