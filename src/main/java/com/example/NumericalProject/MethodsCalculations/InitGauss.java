package com.example.NumericalProject.MethodsCalculations;

import com.example.NumericalProject.Print;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Map;

public class InitGauss {
    protected final BigDecimal tol = BigDecimal.valueOf(0.0001);
    protected final Print print;
    protected final MethodsUtilities methodsUtilities;
    protected final Map<String, ArrayList<BigDecimal>> linearEqns;
    protected double er = 0;
    protected BigDecimal[][] A, U, L;
    protected BigDecimal[] B, s, o, x, y;
    protected int n;

    public InitGauss(Print print, MethodsUtilities methodsUtilities, Map<String, ArrayList<BigDecimal>> linearEqns) {
        this.print = print;
        this.methodsUtilities = methodsUtilities;
        this.linearEqns = linearEqns;
    }

    public void gauss() {
        n = linearEqns.get("B").size() - 1;
        A = new BigDecimal[n + 1][n + 1];
        U = new BigDecimal[n + 1][n + 1];
        L = new BigDecimal[n + 1][n + 1];
        B = new BigDecimal[n + 1];
        s = new BigDecimal[n + 1];
        x = new BigDecimal[n + 1];
        y = new BigDecimal[n + 1];
        o = new BigDecimal[n + 1];
        for (Map.Entry<String, ArrayList<BigDecimal>> entry : linearEqns.entrySet()) {
            ArrayList<BigDecimal> nums = entry.getValue();
            System.out.println(entry.getKey());
            for (BigDecimal num : nums) {
                for (int i = 0; i <= n; i++) {
                    for (int j = 0; j <= n; j++) {
                        if (j == 0 || i == 0) A[i][j] = BigDecimal.ZERO;
                        else A[i][j] = num;
                    }
                }
            }
        }

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
