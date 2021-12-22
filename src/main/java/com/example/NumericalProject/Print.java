package com.example.NumericalProject;

import com.example.NumericalProject.MethodsCalculations.Initialization;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Print{
    private String printer = "";

    public void MatrixToString(Initialization initGauss, BigDecimal[][] A, String name) {
        printer = printer.concat("\n" + name + "\n");
        for (int i = 1; i <= initGauss.getN(); i++) {
            for (int j = 1; j <= initGauss.getN(); j++) {
                if (j == 1) printer = printer.concat("|\t");
                if (A[i][j].abs().compareTo(BigDecimal.valueOf(0.001)) < 0) {
                    printer = printer.concat(BigDecimal.valueOf(0).toPlainString() + "\t");
                } else {
                    printer = printer.concat(A[i][j].setScale(initGauss.getSigFigs(), RoundingMode.DOWN).stripTrailingZeros().toPlainString() + "\t");
                }
                if (j == initGauss.getN()) printer = printer.concat("\t|");
            }
            printer = printer.concat("\n");
        }
    }

    public void VectorToString(Initialization initGauss, BigDecimal[] B, String name) {
        printer = printer.concat("\n" + name + "\n" + "[");
        for (int i = 1; i <= initGauss.getN(); i++) {
            if (i != 1) printer = printer.concat(", ");
            if (B[i].abs().compareTo(BigDecimal.valueOf(0.001)) < 0) {
                if (i == initGauss.getN()) printer = printer.concat(BigDecimal.valueOf(0).toPlainString());
                else printer = printer.concat(BigDecimal.valueOf(0).toPlainString() + "  ");
            } else {
                printer = printer.concat(B[i].setScale(initGauss.getSigFigs(), RoundingMode.DOWN).stripTrailingZeros().toPlainString());
            }
        }
        printer = printer.concat("]\n");
    }

    public String getPrinter() {
        return printer;
    }

    public void setPrinter(String printer) {
        this.printer = printer;
    }
}
