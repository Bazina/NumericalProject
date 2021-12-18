package com.example.NumericalProject.MethodsCalculations;

import com.example.NumericalProject.Print;

import java.math.BigDecimal;

public class InitGauss {
    protected final BigDecimal tol = BigDecimal.valueOf(0.0001);
    protected final Print print;
    protected final MethodsUtilities methodsUtilities;
    protected double er = 0;
    protected BigDecimal[][] A;
    protected BigDecimal[] B, s, o, x, y;
    protected int n;

    public InitGauss(Print print, MethodsUtilities methodsUtilities) {
        this.print = print;
        this.methodsUtilities = methodsUtilities;
    }

    public void gauss() {
        n = 3;
        A = new BigDecimal[][]{{BigDecimal.valueOf(0), BigDecimal.valueOf(0), BigDecimal.valueOf(0), BigDecimal.valueOf(0)},
                {BigDecimal.valueOf(0), BigDecimal.valueOf(8), BigDecimal.valueOf(4), BigDecimal.valueOf(-1)},
                {BigDecimal.valueOf(0), BigDecimal.valueOf(-2), BigDecimal.valueOf(3), BigDecimal.valueOf(1)},
                {BigDecimal.valueOf(0), BigDecimal.valueOf(2), BigDecimal.valueOf(-1), BigDecimal.valueOf(6)}};
        B = new BigDecimal[]{BigDecimal.valueOf(0), BigDecimal.valueOf(11), BigDecimal.valueOf(4), BigDecimal.valueOf(7)};
        s = new BigDecimal[n + 1];
        x = new BigDecimal[]{BigDecimal.valueOf(0), BigDecimal.valueOf(0), BigDecimal.valueOf(0), BigDecimal.valueOf(0)};
        y = new BigDecimal[]{BigDecimal.valueOf(0), BigDecimal.valueOf(0), BigDecimal.valueOf(0), BigDecimal.valueOf(0)};
        o = new BigDecimal[]{BigDecimal.valueOf(0), BigDecimal.valueOf(0), BigDecimal.valueOf(0), BigDecimal.valueOf(0)};
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
