package com.example.NumericalProject.MethodsCalculations.LinearEquations;

import com.example.NumericalProject.Print;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

/***
 * A class that holds all information about the linear equation system
 */
public class Initialization {

    protected final Print print;
    protected final MethodsUtilities methodsUtilities;
    protected final Map<String, ArrayList<BigDecimal>> linearEqns;

    protected int SigFigs = 4;
    protected int Iterations;
    protected BigDecimal tol = BigDecimal.valueOf(0.0001);
    protected double er = 0;
    protected BigDecimal[][] A, U, L;
    protected BigDecimal[] B, s, o, x, y;
    protected int n;

    /***
     * A constructor to initialize the object with the needed parameters
     * @param print an object that holds the steps of the calculations
     * @param methodsUtilities an objects that holds the common methods
     * @param linearEqns a hash map that hold the system variables and coefficients
     */
    public Initialization(Print print, MethodsUtilities methodsUtilities, Map<String, ArrayList<BigDecimal>> linearEqns) {
        this.print = print;
        this.methodsUtilities = methodsUtilities;
        this.linearEqns = linearEqns;
    }

    /***
     * Initialize the matrices and vectors with the needed values
     */
    public void Initialize() {
        int i, j;

        // creating the matrices and vectors with the size of the system
        n = linearEqns.get("B").size();
        A = new BigDecimal[n + 1][n + 1];
        L = new BigDecimal[n + 1][n + 1];
        U = new BigDecimal[n + 1][n + 1];
        B = new BigDecimal[n + 1];
        s = new BigDecimal[n + 1];
        x = new BigDecimal[n + 1];
        y = new BigDecimal[n + 1];
        o = new BigDecimal[n + 1];

        // Initializing Matrix A and B
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

        // Putting the coefficients of the system in matrix A
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

        // Initializing Matrix L and U
        for (i = 0; i <= n; i++) {
            for (j = 0; j <= n; j++) {
                L[i][j] = BigDecimal.ZERO;
                U[i][j] = BigDecimal.ZERO;
            }
        }

        // Initializing Matrix x, y, s, and o
        for (i = 0; i <= n; i++) {
            x[i] = BigDecimal.ZERO;
            s[i] = BigDecimal.ZERO;
            y[i] = BigDecimal.ZERO;
            o[i] = BigDecimal.ZERO;
        }

        // Do scaling for matrix A
        for (i = 1; i <= n; i++) {
            o[i] = BigDecimal.valueOf(i);
            s[i] = A[i][1].abs();
            for (j = 2; j <= n; j++) {
                if (A[i][j].abs().compareTo(s[i]) > 0) {
                    s[i] = A[i][j].abs();
                }
            }
        }

        // Printing first step
        print.MatrixToString(this, A, "Matrix A");
        print.VectorToString(this, B, "Vector B");
    }

    /***
     * Getter for print
     * @return print
     */
    public Print getPrint() {
        return print;
    }

    /***
     * Setter for tolerance
     * @param tol the wanted tolerance
     */
    public void setTol(BigDecimal tol) {
        this.tol = tol;
    }

    /***
     * Setter for significant figures
     * @param sigFigs the wanted significant figures
     */
    public void setSigFigs(int sigFigs) {
        SigFigs = sigFigs;
    }

    /***
     * Getter for n
     * @return n size of the matrix
     */
    public int getN() {
        return n;
    }

    /***
     * Getter for significant figures
     * @return sigFigs the wanted significant figures
     */
    public int getSigFigs() {
        return SigFigs;
    }

    /***
     * Setter for maximum number of iterations
     * @param iterations maximum number of iterations
     */
    public void setIterations(int iterations) {
        Iterations = iterations;
    }

    /***
     * Setter for X vector
     * @param x X vector
     */
    public void setX(BigDecimal[] x) {
        this.x = x;
    }
}