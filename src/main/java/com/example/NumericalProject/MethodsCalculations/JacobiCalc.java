package com.example.NumericalProject.MethodsCalculations;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class JacobiCalc {
    private final InitGauss initGauss;

    public JacobiCalc(InitGauss initGauss) {
        this.initGauss = initGauss;
    }

    public void JacobiInit() {
        initGauss.gauss();
        if (initGauss.methodsUtilities.makeDominant(initGauss)) {
            initGauss.print.setPrinter(
                    "The system isn't diagonally dominant: "
                            + "The method cannot guarantee convergence.");
            return;
        }
        JacobiIterations();
        if (initGauss.er != -1) {
            System.out.println(initGauss.print.getPrinter());
        }
    }

    public void JacobiIterations() {
        int iterations = 0, MAX_ITERATIONS = 3;
        BigDecimal[] previousX = initGauss.x.clone(); // Prev
        while (true) {
            for (int i = 1; i <= initGauss.n; i++) {
                BigDecimal sum = initGauss.B[i]; // b_n
                for (int j = 1; j <= initGauss.n; j++) {
                    if (j != i) sum = sum.subtract(initGauss.A[i][j].multiply(previousX[j]))
                            .setScale(initGauss.SigFigs, RoundingMode.DOWN);
                }
                // Update xi to use in the next
                // row calculation
                initGauss.x[i] = BigDecimal.ONE.divide(initGauss.A[i][i], initGauss.SigFigs, RoundingMode.DOWN)
                        .multiply(sum).setScale(initGauss.SigFigs, RoundingMode.DOWN);
            }

            initGauss.print.VectorToString(initGauss, initGauss.x);

            iterations++;
            previousX = initGauss.x.clone();
            if (iterations == 1) continue;

            boolean stop = true;
            for (int i = 1; i <= initGauss.n; i++) {
                if (initGauss.x[i].subtract(previousX[i]).abs().compareTo(initGauss.tol) > 0) {
                    stop = false;
                    break;
                }
            }

            if (stop || iterations == MAX_ITERATIONS) break;
        }
    }
}
