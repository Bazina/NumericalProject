package com.example.NumericalProject.MethodsCalculations.LinearEquations;

import java.math.BigDecimal;
import java.math.RoundingMode;

/***
 * This class calculate Gauss-Jacobi Method
 */
public class JacobiCalc {
    private final Initialization Init;

    /***
     * A constructor for initializing the object with the needed parameters
     * @param Init an object that hold all the information needed for calculations
     */
    public JacobiCalc(Initialization Init) {
        this.Init = Init;
    }

    /***
     * A method which starts the calculation of Gauss-Jacobi Method
     */
    public void JacobiInit() {
        Init.Initialize();
        JacobiIterations();
    }

    /***
     * A method that calculates the Gauss-Seidel
     */
    public void JacobiIterations() {
        int iterations = 0;
        BigDecimal[] previousX = Init.x.clone(); // Previous
        String newPrinter = Init.print.getPrinter();
        while (true) {

            // Calculate the new X(i)
            for (int i = 1; i <= Init.n; i++) {
                BigDecimal sum = Init.B[i]; // B(n)
                newPrinter = newPrinter.concat("\nSum = " + "B(" + i + ")" + " - ");
                for (int j = 1; j <= Init.n; j++) {
                    if (j != i) {

                        // Use old X(i) in the formula of new X(i)
                        sum = sum.subtract(Init.A[i][j].multiply(previousX[j]))
                                .setScale(Init.SigFigs, RoundingMode.DOWN);

                        // Print the steps
                        if (Init.n != j)
                            newPrinter = newPrinter.concat("A(" + i + j + ")" + " * " + "X(" + j + ")" + " - ");
                        else
                            newPrinter = newPrinter.concat("A(" + i + j + ")" + " * " + "X(" + j + ")" + "\n");
                    }
                }

                // Update X(i) to use in the next
                // row calculation
                Init.x[i] = BigDecimal.ONE.divide(Init.A[i][i], Init.SigFigs, RoundingMode.DOWN)
                        .multiply(sum).setScale(Init.SigFigs, RoundingMode.DOWN);

                // Print the steps
                newPrinter = newPrinter.concat("X(" + i + ") = " + sum + " / " + Init.A[i][i] + "\n");
            }

            // increment the iteration
            iterations++;

            // Printing steps
            if (iterations == 1) newPrinter = "";
            if (iterations != Init.Iterations + 1)
                newPrinter = newPrinter.concat("Iteration = " + iterations + "\n");
            Init.print.setPrinter(newPrinter);
            Init.print.VectorToString(Init, Init.x, "Vector X");

            // If it is the first iteration, so absolute approximate error can't be calculated
            if (iterations == 1) continue;

            boolean stop = true;

            // Calculate absolute approximate error
            for (int i = 1; i <= Init.n; i++) {
                if (Init.x[i].subtract(previousX[i]).divide(Init.x[i],
                        Init.SigFigs, RoundingMode.DOWN).abs().compareTo(Init.tol) > 0) {
                    stop = false;
                    break;
                }
            }

            // Stop the method if it reached the maximum number of iteration or the required tolerance
            if (stop || iterations == Init.Iterations + 1) break;
            // copy the new X vector to the old one
            previousX = Init.x.clone();
        }
    }
}
