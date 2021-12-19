package com.example.NumericalProject.MethodsCalculations;

import com.example.NumericalProject.Print;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

public class InitGauss {

    protected final Print print;
    protected final MethodsUtilities methodsUtilities;
    protected final Map<String, ArrayList<BigDecimal>> linearEqns;

    protected int SigFigs;
    protected BigDecimal tol = BigDecimal.valueOf(0.0001);
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
        int i, j;
        n = linearEqns.get("B").size();
        A = new BigDecimal[n + 1][n + 1];
        U = new BigDecimal[n + 1][n + 1];
        L = new BigDecimal[n + 1][n + 1];
        B = new BigDecimal[n + 1];
        s = new BigDecimal[n + 1];
        x = new BigDecimal[n + 1];
        y = new BigDecimal[n + 1];
        o = new BigDecimal[n + 1];


        for (i = 0; i <= n; i++) {
            for (j = 0; j <= n; j++) {
                if (j == 0 || i == 0) A[i][j] = BigDecimal.ZERO;
            }
        }
        B[0] = BigDecimal.ZERO;

        i = 1;
        for (BigDecimal num : linearEqns.get("B")) {
            B[i] = num;
            i++;
        }

        i = 1;
        j = 1;
        for (Map.Entry<String, ArrayList<BigDecimal>> entry : linearEqns.entrySet()) {
            ArrayList<BigDecimal> nums = entry.getValue();
            if (Objects.equals(entry.getKey(), "B")) continue;
            for (BigDecimal num : nums) {
                A[i][j] = num;
                i++;
                if (j == n + 1) j = 1;
                if (i == n + 1) {
                    j++;
                    i = 1;
                }
            }
        }

        for (i = 0; i <= n; i++) {
            for (j = 0; j <= n; j++) {
                L[i][j] = BigDecimal.ZERO;
                U[i][j] = BigDecimal.ZERO;
            }
        }

        for (i = 0; i <= n; i++) {
            x[i] = BigDecimal.ZERO;
            s[i] = BigDecimal.ZERO;
            y[i] = BigDecimal.ZERO;
            o[i] = BigDecimal.ZERO;
        }

        for (i = 1; i <= n; i++) {
            s[i] = A[i][1].abs();
            for (j = 2; j <= n; j++) {
                if (A[i][j].abs().compareTo(s[i]) > 0) {
                    s[i] = A[i][j].abs();
                }
            }
        }
        print.MatrixToString(SigFigs, A, n);
        print.VectorToString(SigFigs, B, n);
    }

    public Print getPrint() {
        return print;
    }

    public void setTol(BigDecimal tol) {
        this.tol = tol;
    }

    public void setSigFigs(int sigFigs) {
        SigFigs = sigFigs;
    }
}