package com.example.NumericalProject.MethodsCalculations.NonLinearEquations;

import com.example.NumericalProject.EquationsParser.SingleEquationParser;
import com.example.NumericalProject.Printers.NonLinearPrinter;
import com.example.NumericalProject.Printers.SigFigsHandler;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

/***
 * This class calculate bisection method for non-linear equations.
 */
public class BisectionCalculation {
    public static BigDecimal ea = BigDecimal.ONE, eps = BigDecimal.valueOf(0.00001);
    public static int MaxIteration = 50;

    public static void setEps(BigDecimal value) {
        eps = value;
    }

    public static void setMaxIteration(int value) {
        MaxIteration = value;
    }

    /***
     * Calculating the root using bisection.
     * @param xl this is the lower bound.
     * @param xu this is the upper bound.
     * @param Oldxr this is Xi-1
     * @param loops num. of current iteration.
     */
    public static void BiSection(BigDecimal xl, BigDecimal xu, BigDecimal Oldxr, int loops) {
        if (loops == MaxIteration) {
            return;
        }

        if (loops != 0 && (eps.compareTo(ea) > 0 || loops >= MaxIteration)) {
            NonLinearPrinter.Add("The Root = " + Oldxr + "\nThe Relative Error = "
                    + ea + "\nSignificant Figures = " + SigFigsHandler.getSigFigs() + "\nTotal No of Iterations = " + (loops) + "\n");

            ea = BigDecimal.ONE;
            eps = BigDecimal.valueOf(0.00001);
            MaxIteration = 50;
            return;
        }

        BigDecimal xr = (xl.add(xu)).divide(BigDecimal.valueOf(2), MathContext.DECIMAL128).round(new MathContext(SigFigsHandler.getSigFigs(), RoundingMode.HALF_UP));
        if (eps.compareTo(xr.abs()) > 0) xr = BigDecimal.ZERO;

        NonLinearPrinter.Add("#" + (loops + 1) + " Iteration\nXu = " + xu + "\nXl = " +
                xl + "\nXr = " + xr + "\n");
        if (loops > 0) {
            if (xr.compareTo(BigDecimal.ZERO) != 0) {
                ea = (xr.subtract(Oldxr).divide(xr, MathContext.DECIMAL128)).abs().round(new MathContext(SigFigsHandler.getSigFigs(), RoundingMode.HALF_UP));
                NonLinearPrinter.Add("Relative Error = " + ea + "\n");
            } else if (xr.subtract(Oldxr).abs().compareTo(eps) < 0) {
                ea = BigDecimal.ZERO;
                NonLinearPrinter.Add("Relative Error = " + ea + "\n");
            }
        }
        NonLinearPrinter.Add("\n");

        if (SingleEquationParser.Evaluate(xr) * SingleEquationParser.Evaluate(xl) <= 0) {
            BiSection(xl, xr, xr, loops + 1);
        } else if (SingleEquationParser.Evaluate(xr) * SingleEquationParser.Evaluate(xu) <= 0) {
            BiSection(xr, xu, xr, loops + 1);
        } else {
            NonLinearPrinter.Add("Total No of Iterations = " + (loops + 1) + "\nThere is no Root in This Interval or There is Multiple Roots\n");
        }
    }
}
