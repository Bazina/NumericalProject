package com.example.NumericalProject;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Print{
    private String printer = "";

    public void MatrixToString(int SigFig,BigDecimal[][] A, int n) {
        printer = printer.concat("\n");
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                if (j == 1) printer = printer.concat("|\t");
                if (A[i][j].abs().compareTo(BigDecimal.valueOf(0.001)) < 0) {
                    printer = printer.concat(BigDecimal.valueOf(0).toPlainString() + "\t");
                } else {
                    printer = printer.concat(A[i][j].setScale(5, RoundingMode.DOWN).stripTrailingZeros().toPlainString() + "\t");
                }
                if (j == n) printer = printer.concat("\t|");
            }
            printer = printer.concat("\n");
        }
    }

    public void VectorToString(int SigFig, BigDecimal[] B, int n) {
        printer = printer.concat("\n" + "[");
        for (int i = 1; i <= n; i++) {
            if (i != 1) printer = printer.concat(", ");
            if (B[i].abs().compareTo(BigDecimal.valueOf(0.001)) < 0) {
                printer = printer.concat(BigDecimal.valueOf(0).toPlainString() + "  ");
            } else {
                printer = printer.concat(B[i].setScale(5, RoundingMode.DOWN).stripTrailingZeros().toPlainString());
            }
        }
        printer = printer.concat("]\n");
    }

    public String getPrinter() {
        return printer;
    }
}
