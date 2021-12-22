package com.example.NumericalProject.MethodsCalculations;

import java.math.BigDecimal;
import java.math.RoundingMode;

/***
 * This class calculate Gauss-Seidel Method
 */
public class GaussSeidelCalc {
    private final Initialization initGauss;

    /***
     * A constructor for initializing the object with the needed parameters
     * @param initGauss an object that hold all the matrices needed for calculations
     */
    public GaussSeidelCalc(Initialization initGauss) {
        this.initGauss = initGauss;
    }

    /***
     * A method which starts the calculation of Gauss-Seidel Method
     */
    public void GaussSeidelInit() {
        initGauss.gauss();

        // Check if the matrix A is dominant or not and transform it to dominant if possible
        if (initGauss.methodsUtilities.makeDominant(initGauss)) {
            initGauss.print.setPrinter(
                    "The system isn't diagonally dominant: "
                            + "The method cannot guarantee convergence.");
            return;
        }
        GaussSeidelIterations();
    }

    /***
     * A method that calculates the Gauss-Seidel
     */
    public void GaussSeidelIterations() {
        int iterations = 0;
        BigDecimal[] previousX = initGauss.x.clone(); // Previous
        String newPrinter = initGauss.print.getPrinter();
        while (true) {

            // Calculate the new X(i)
            for (int i = 1; i <= initGauss.n; i++) {
                BigDecimal sum = initGauss.B[i]; // B(n)
                newPrinter = newPrinter.concat("\nSum = " + "B(" + i + ")" + " - ");
                for (int j = 1; j <= initGauss.n; j++) {
                    if (j != i) {

                        // Use the last X(i) in the formula of new X(i)
                        sum = sum.subtract(initGauss.A[i][j]
                                .multiply(initGauss.x[j])).setScale(initGauss.SigFigs, RoundingMode.DOWN);

                        // Print the steps
                        if (initGauss.n != j)
                            newPrinter = newPrinter.concat("A(" + i + j + ")" + " * " + "X(" + j + ")" + " - ");
                        else
                            newPrinter = newPrinter.concat("A(" + i + j + ")" + " * " + "X(" + j + ")" + "\n");
                    }
                }

                // Update X(i) to use in the next
                // row calculation
                initGauss.x[i] = BigDecimal.ONE.divide(initGauss.A[i][i], initGauss.SigFigs, RoundingMode.DOWN)
                        .multiply(sum).setScale(initGauss.SigFigs, RoundingMode.DOWN);

                // Print the steps
                newPrinter = newPrinter.concat("X(" + i + ") = " + sum + " / " + initGauss.A[i][i] + "\n");
            }

            // increment the iteration
            iterations++;

            // Printing steps
            if (iterations == 1) newPrinter = "";
            if (iterations != initGauss.Iterations + 1)
                newPrinter = newPrinter.concat("Iteration = " + iterations + "\n");
            initGauss.print.setPrinter(newPrinter);
            initGauss.print.VectorToString(initGauss, initGauss.x, "Vector X");

            // If it is the first iteration, so absolute approximate error can't be calculated
            if (iterations == 1) continue;

            boolean stop = true;

            // Calculate absolute approximate error
            for (int i = 1; i <= initGauss.n; i++) {
                if (initGauss.x[i].subtract(previousX[i]).abs().compareTo(initGauss.tol) > 0) {
                    stop = false;
                    break;
                }
            }

            // Stop the method if it reached the maximum number of iteration or the required tolerance
            if (stop || iterations == initGauss.Iterations + 1) break;

            // copy the new X vector to the old one
            previousX = initGauss.x.clone();
        }
    }
}
