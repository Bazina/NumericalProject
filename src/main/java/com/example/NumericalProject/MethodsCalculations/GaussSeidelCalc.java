package com.example.NumericalProject.MethodsCalculations;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class GaussSeidelCalc {
    private final InitGauss initGauss;

    public GaussSeidelCalc(InitGauss initGauss) {
        this.initGauss = initGauss;
    }

    public void GaussSeidelInit() {
        initGauss.gauss();
        GaussSeidelIterations();
        if (initGauss.er != -1) {
            System.out.println(initGauss.print.getPrinter());
        }
    }

    public void GaussSeidelIterations() {
        int iterations = 0, MAX_ITERATIONS = 3;
        BigDecimal[] previousX = initGauss.x.clone(); // Prev
        while (true) {
            for (int i = 1; i <= initGauss.n; i++) {
                BigDecimal sum = initGauss.B[i]; // b_n
                for (int j = 1; j <= initGauss.n; j++) {
                    if (j != i) sum = sum.subtract(initGauss.A[i][j].multiply(initGauss.x[j]));
                }
                // Update xi to use in the next
                // row calculation
                initGauss.x[i] = BigDecimal.ONE.divide(initGauss.A[i][i], 3, RoundingMode.DOWN).multiply(sum);
            }

            initGauss.print.VectorToString(initGauss.SigFigs, initGauss.x, initGauss.n);

            iterations++;
            if (iterations == 1) continue;

            boolean stop = true;
            for (int i = 1; i <= initGauss.n; i++) {
                if (initGauss.x[i].subtract(previousX[i]).abs().compareTo(initGauss.tol) > 0) {
                    stop = false;
                    break;
                }
            }

            if (stop || iterations == MAX_ITERATIONS) break;
            previousX = initGauss.x.clone();
        }
    }
}
