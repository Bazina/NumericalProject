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
            initGauss.print.MatrixToString(initGauss, initGauss.L, "Matrix L");
            initGauss.print.MatrixToString(initGauss, initGauss.U, "Matrix U");
            initGauss.print.VectorToString(initGauss, initGauss.x, "Vector X");
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
            if (((initGauss.A[initGauss.o[k].intValue()][k].abs())
                    .divide(initGauss.s[initGauss.o[k].intValue()], initGauss.SigFigs, RoundingMode.DOWN))
                    .compareTo(BigDecimal.valueOf(Math.pow(10, -initGauss.SigFigs))) < 0) {
                initGauss.er = -1;
                return;
            }
            for (int i = k + 1; i <= initGauss.n; i++) {
                BigDecimal factor = initGauss.A[initGauss.o[i].intValue()][k]
                        .divide(initGauss.A[initGauss.o[k].intValue()][k], initGauss.SigFigs, RoundingMode.DOWN)
                        .setScale(initGauss.SigFigs, RoundingMode.DOWN);
                initGauss.A[initGauss.o[i].intValue()][k] = factor;
                for (int j = k + 1; j <= initGauss.n; j++) {
                    initGauss.A[initGauss.o[i].intValue()][j] = initGauss.A[initGauss.o[i].intValue()][j]
                            .subtract(factor.multiply(initGauss.A[initGauss.o[k].intValue()][j]))
                            .setScale(initGauss.SigFigs, RoundingMode.DOWN);
                }
                initGauss.print.MatrixToString(initGauss, initGauss.A, "Matrix A");
                initGauss.print.VectorToString(initGauss, initGauss.B, "Vector B");
            }
        }
        for (int i = 1; i <= initGauss.n; i++) {
            for (int j = 1; j <= initGauss.n; j++) {
                if (j > i) initGauss.U[i][j] = initGauss.A[i][j].setScale(initGauss.SigFigs, RoundingMode.DOWN);
                else if (j < i)
                    initGauss.L[i][j] = initGauss.A[i][j].setScale(initGauss.SigFigs, RoundingMode.DOWN);
                else {
                    initGauss.U[i][j] = initGauss.A[i][j].setScale(initGauss.SigFigs, RoundingMode.DOWN);
                    initGauss.L[i][j] = BigDecimal.ONE;
                }
            }
        }
        String temp = initGauss.methodsUtilities.CheckConsistencyLU(initGauss);
        if (temp.equals("No Solution") || temp.equals("Infinity Solutions"))
            return;
        if (((initGauss.A[initGauss.o[initGauss.n].intValue()][initGauss.n].abs())
                .divide(initGauss.s[initGauss.o[initGauss.n].intValue()], initGauss.SigFigs, RoundingMode.DOWN))
                .compareTo(BigDecimal.valueOf(Math.pow(10, -initGauss.SigFigs))) < 0) {
            initGauss.er = -1;
        }
        if (initGauss.er != -1) {
            initGauss.methodsUtilities.LUSubstitute(initGauss);
        }
    }

    public void CroutDecompose() {
        BigDecimal sum;
        for (int i = 1; i <= initGauss.n; i++) {
            initGauss.U[i][i] = BigDecimal.ONE;
        }

        for (int j = 1; j <= initGauss.n; j++) {
            String newPrinter = initGauss.print.getPrinter();
            for (int i = j; i <= initGauss.n; i++) {
                sum = BigDecimal.ZERO;
                newPrinter = newPrinter.concat("\nSum = ");
                for (int k = 1; k <= j; k++) {
                    sum = sum.add(initGauss.L[i][k].multiply(initGauss.U[k][j]))
                            .setScale(initGauss.SigFigs, RoundingMode.DOWN);
                    if (k != j)
                        newPrinter = newPrinter.concat("L(" + i + k + ")" + " * " + "U(" + k + j + ")" + " + ");
                    else
                        newPrinter = newPrinter.concat("L(" + i + k + ")" + " * " + "U(" + k + j + ")" + "\n");
                }
                initGauss.L[i][j] = initGauss.A[i][j].subtract(sum)
                        .setScale(initGauss.SigFigs, RoundingMode.DOWN);
                newPrinter = newPrinter.concat("L(" + i + j + ") = " + "U(" + i + j + ")" + " - " + sum + "\n");
            }
            for (int i = j + 1; i <= initGauss.n; i++) {
                sum = BigDecimal.ZERO;
                newPrinter = newPrinter.concat("\nSum = ");
                for (int k = 1; k <= initGauss.n; k++) {
                    sum = sum.add(initGauss.L[j][k].multiply(initGauss.U[k][i]))
                            .setScale(initGauss.SigFigs, RoundingMode.DOWN);
                    if (k != j)
                        newPrinter = newPrinter.concat("L(" + j + k + ")" + " * " + "U(" + k + j + ")" + " + ");
                    else
                        newPrinter = newPrinter.concat("L(" + j + k + ")" + " * " + "U(" + k + j + ")" + "\n");
                }
                if (initGauss.L[j][j].compareTo(BigDecimal.ZERO) == 0) {
                    initGauss.er = -1;
                }
                initGauss.U[j][i] = (initGauss.A[j][i].subtract(sum))
                        .divide(initGauss.L[j][j], initGauss.SigFigs, RoundingMode.DOWN)
                        .setScale(initGauss.SigFigs, RoundingMode.DOWN);
                newPrinter = newPrinter.concat("U(" + j + i + ") = " + "( " + initGauss.A[j][i] + " - " + sum + " )"
                        + " / " + initGauss.L[j][j] + "\n");
            }
        }
        String temp = initGauss.methodsUtilities.CheckConsistencyLU(initGauss);
        if (temp.equals("No Solution") || temp.equals("Infinity Solutions"))
            return;
        if (initGauss.er != -1) {
            initGauss.methodsUtilities.LUForwardSubstitute(initGauss);
            initGauss.methodsUtilities.LUBackwardSubstitute(initGauss);
        }
    }

    public void CholeskyDecompose() {
        MathContext mc = new MathContext(initGauss.SigFigs);
        for (int i = 1; i <= initGauss.n; i++) {
            for (int j = 1; j <= initGauss.n; j++) {
                if (initGauss.A[i][j].compareTo(initGauss.A[j][i]) != 0) {
                    initGauss.print.setPrinter("Not Symmetric");
                    return;
                }
            }
        }
        for (int i = 1; i <= initGauss.n; i++) {
            String newPrinter = initGauss.print.getPrinter();
            for (int j = 1; j <= i; j++) {
                BigDecimal sum = BigDecimal.ZERO;
                if (j == i) {
                    newPrinter = newPrinter.concat("\nSumL = ");
                    for (int k = 1; k <= j; k++) {
                        sum = sum.add(initGauss.L[j][k].multiply(initGauss.L[j][k]))
                                .setScale(initGauss.SigFigs, RoundingMode.DOWN);
                        if (k != j)
                            newPrinter = newPrinter.concat("L(" + j + k + ")" + " * " + "L(" + j + k + ")" + " + ");
                        else
                            newPrinter = newPrinter.concat("L(" + j + k + ")" + " * " + "L(" + j + k + ")" + "\n");
                    }
                    initGauss.L[j][j] = (initGauss.A[j][j].subtract(sum)).sqrt(mc);
                    newPrinter = newPrinter.concat("L(" + j + j + ") = " + "Sqrt(" +
                            initGauss.A[j][j].toPlainString() + " - " + sum.toPlainString() + ")" + "\n");
                } else {
                    newPrinter = newPrinter.concat("\nSumU = ");
                    for (int k = 1; k <= j; k++) {
                        sum = sum.add(initGauss.L[i][k].multiply(initGauss.L[j][k]))
                                .setScale(initGauss.SigFigs, RoundingMode.DOWN);
                        if (k != j)
                            newPrinter = newPrinter.concat("L(" + i + k + ")" + " * " + "L(" + j + k + ")" + " + ");
                        else
                            newPrinter = newPrinter.concat("L(" + i + k + ")" + " * " + "L(" + j + k + ")" + "\n");
                    }
                    initGauss.L[i][j] = (initGauss.A[i][j].subtract(sum))
                            .divide(initGauss.L[j][j], initGauss.SigFigs, RoundingMode.DOWN)
                            .setScale(initGauss.SigFigs, RoundingMode.DOWN);
                    newPrinter = newPrinter.concat("L(" + i + j + ") = " + "( " + initGauss.A[i][j] + " - "
                            + sum + " )" + " / " + initGauss.L[j][j] + "\n");

                }
                initGauss.print.setPrinter(newPrinter);
            }
        }
        for (int i = 1; i <= initGauss.n; i++)
            for (int j = 1; j <= initGauss.n; j++)
                initGauss.U[i][j] = initGauss.L[j][i].setScale(initGauss.SigFigs, RoundingMode.DOWN);

        String temp = initGauss.methodsUtilities.CheckConsistencyLU(initGauss);
        if (temp.equals("No Solution") || temp.equals("Infinity Solutions"))
            return;
        if (initGauss.er != -1) {

            initGauss.methodsUtilities.LUForwardSubstitute(initGauss);
            initGauss.methodsUtilities.LUBackwardSubstitute(initGauss);
        }
    }


}
