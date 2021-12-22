package com.example.NumericalProject;

import com.example.NumericalProject.MethodsCalculations.Initialization;

import java.math.BigDecimal;
import java.math.RoundingMode;

/***
 * A class that save the steps in a string
 */
public class Print{
    private String printer = "";

    /***
     * A method to convert matrix into a fancy string
     * @param Init an object that hold all the matrices needed for calculations
     * @param A the matrix to be converted
     * @param name the name of the matrix
     */
    public void MatrixToString(Initialization Init, BigDecimal[][] A, String name) {
        printer = printer.concat("\n" + name + "\n");
        for (int i = 1; i <= Init.getN(); i++) {
            for (int j = 1; j <= Init.getN(); j++) {
                if (j == 1) printer = printer.concat("|\t");
                if (A[i][j].abs().compareTo(BigDecimal.valueOf(0.001)) < 0) {
                    printer = printer.concat(BigDecimal.valueOf(0).toPlainString() + "\t");
                } else {
                    printer = printer.concat(A[i][j].setScale(Init.getSigFigs(), RoundingMode.DOWN).stripTrailingZeros().toPlainString() + "\t");
                }
                if (j == Init.getN()) printer = printer.concat("\t|");
            }
            printer = printer.concat("\n");
        }
    }

    /***
     * A method to convert vector into a fancy string
     * @param Init an object that hold all the matrices needed for calculations
     * @param B the vector to be converted
     * @param name the name of the vector
     */
    public void VectorToString(Initialization Init, BigDecimal[] B, String name) {
        printer = printer.concat("\n" + name + "\n" + "[");
        for (int i = 1; i <= Init.getN(); i++) {
            if (i != 1) printer = printer.concat(", ");
            if (B[i].abs().compareTo(BigDecimal.valueOf(0.001)) < 0) {
                if (i == Init.getN()) printer = printer.concat(BigDecimal.valueOf(0).toPlainString());
                else printer = printer.concat(BigDecimal.valueOf(0).toPlainString() + "  ");
            } else {
                printer = printer.concat(B[i].setScale(Init.getSigFigs(), RoundingMode.DOWN).stripTrailingZeros().toPlainString());
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
