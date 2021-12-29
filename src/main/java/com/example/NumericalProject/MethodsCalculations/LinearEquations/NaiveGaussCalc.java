package com.example.NumericalProject.MethodsCalculations.LinearEquations;

/***
 * This class calculate Naive Gauss Elimination
 */
public class NaiveGaussCalc {
    private final Initialization Init;

    /***
     * A constructor for initializing the object with the needed parameters
     * @param Init an object that hold all the information needed for calculations
     */
    public NaiveGaussCalc(Initialization Init) {
        this.Init = Init;
    }

    /***
     * A method which starts the calculation of Naive Gauss Elimination
     */
    public void NaiveGauss() {
        Init.Initialize();
        Eliminate(Init);
        String checkConsistency = Init.methodsUtilities.CheckConsistency(Init);
        String newPrinter = Init.print.getPrinter().concat("\n" + checkConsistency + "\n");
        Init.print.setPrinter(newPrinter);

        // Check if the system is consistent
        if (checkConsistency.equals("No Solution") || checkConsistency.equals("Infinity Solutions")) return;
        if (Init.er != -1) {
            // Calculating and printing the final matrices and answer
            Init.methodsUtilities.BackwardSubstitute(Init);
            Init.print.VectorToString(Init, Init.x, "Vector X");
        }
    }

    /***
     * Make Gauss elimination on the given matrix
     * @param Init an object that hold all the information needed for calculations
     */
    public void Eliminate(Initialization Init) {
        Init.methodsUtilities.GaussElimination(Init);
    }
}
