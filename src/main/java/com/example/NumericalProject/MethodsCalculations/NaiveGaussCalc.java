package com.example.NumericalProject.MethodsCalculations;

/***
 * This class calculate Naive Gauss Elimination
 */
public class NaiveGaussCalc {
    private final InitGauss initGauss;

    /***
     * A constructor for initializing the object with the needed parameters
     * @param initGauss an object that hold all the matrices needed for calculations
     */
    public NaiveGaussCalc(InitGauss initGauss) {
        this.initGauss = initGauss;
    }

    /***
     * A method which starts the calculation of Naive Gauss Elimination
     */
    public void NaiveGauss() {
        initGauss.gauss();
        Eliminate(initGauss);
        String checkConsistency = initGauss.methodsUtilities.CheckConsistency(initGauss);
        String newPrinter = initGauss.print.getPrinter().concat("\n" + checkConsistency + "\n");
        initGauss.print.setPrinter(newPrinter);

        // Check if the system is consistent
        if (checkConsistency.equals("No Solution") || checkConsistency.equals("Infinity Solutions")) return;
        if (initGauss.er != -1) {
            // Calculating and printing the final matrices and answer
            initGauss.methodsUtilities.BackwardSubstitute(initGauss);
            initGauss.print.VectorToString(initGauss, initGauss.x, "Vector X");
        }
    }

    /***
     * Make Gauss elimination on the given matrix
     * @param initGauss an object that hold all the matrices needed for calculations
     */
    public void Eliminate(InitGauss initGauss) {
        initGauss.methodsUtilities.GaussElimination(initGauss);
    }
}
