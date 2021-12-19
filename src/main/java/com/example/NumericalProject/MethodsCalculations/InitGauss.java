package com.example.NumericalProject.MethodsCalculations;

import com.example.NumericalProject.Print;

import java.math.BigDecimal;

public class InitGauss {
    protected final BigDecimal tol = BigDecimal.valueOf(0.0001);
    protected final Print print;
    protected final MethodsUtilities methodsUtilities;
    protected double er = 0;
    protected BigDecimal[][] A, U, L;
    protected BigDecimal[] B, s, o, x, y;
    protected int n;

    public InitGauss(Print print, MethodsUtilities methodsUtilities) {
        this.print = print;
        this.methodsUtilities = methodsUtilities;
    }

    public void gauss() {
        n = 3;
        A = new BigDecimal[][]{{BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO},
                {BigDecimal.ZERO, BigDecimal.valueOf(6), BigDecimal.valueOf(15), BigDecimal.valueOf(55)},
                {BigDecimal.ZERO, BigDecimal.valueOf(15), BigDecimal.valueOf(55), BigDecimal.valueOf(225)},
                {BigDecimal.ZERO, BigDecimal.valueOf(55), BigDecimal.valueOf(225), BigDecimal.valueOf(979)}};
        U = new BigDecimal[][]{{BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO},
                {BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO},
                {BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO},
                {BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO}};
        L = new BigDecimal[][]{{BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO},
                {BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO},
                {BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO},
                {BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO}};
        B = new BigDecimal[]{BigDecimal.ZERO, BigDecimal.valueOf(11), BigDecimal.valueOf(4), BigDecimal.valueOf(7)};
        s = new BigDecimal[n + 1];
        x = new BigDecimal[]{BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO};
        y = new BigDecimal[]{BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO};
        o = new BigDecimal[]{BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO};
        for (int i = 1; i <= n; i++) {
            s[i] = A[i][1].abs();
            for (int j = 2; j <= n; j++) {
                if (A[i][j].abs().compareTo(s[i]) > 0) {
                    s[i] = A[i][j].abs();
                }
            }
        }
        print.MatrixToString(tol, A, n);
        print.VectorToString(tol, B, n);
    }
}
