package com.example.NumericalProject.MethodsCalculations.NonLinearEquations;

import com.example.NumericalProject.EquationsParser.SingleEquationParser;
import com.example.NumericalProject.Printers.NonLinearPrinter;
import com.example.NumericalProject.Printers.SigFigsHandler;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

import static java.lang.Math.abs;

public class FalsePositionCalculation {
    public static BigDecimal ea = BigDecimal.ONE, eps = BigDecimal.valueOf(0.00001);
    public static int MaxIteration = 50;

    public static void setEps(BigDecimal value) {
        eps = value;
    }

    public static void setMaxIteration(int value) {
        MaxIteration = value;
    }

    public static void FalsePosition(BigDecimal xl, BigDecimal xu, BigDecimal Oldxr, int loops) {

        if (loops != 0 && (eps.compareTo(ea) > 0 || loops >= MaxIteration)) {
            if(eps.compareTo(BigDecimal.valueOf(abs(SingleEquationParser.Evaluate(Oldxr)))) > 0){
                NonLinearPrinter.Add("Total No of Iterations = " + (loops) + "\nThere is no Root in This Interval\n");

            }else{
                NonLinearPrinter.Add("The Root = " + Oldxr+ "\nThe Relative Error = "
                        + ea +"\nSignificant Figures = " + SigFigsHandler.getSigFigs() +"\nTotal No of Iterations = " + (loops) + "\n");
            }

            ea = BigDecimal.ONE;
            eps = BigDecimal.valueOf(0.00001);
            MaxIteration = 50;
            return;
        }

        BigDecimal xr = (xl.multiply(BigDecimal.valueOf(SingleEquationParser.Evaluate(xu))).subtract(xu.multiply(BigDecimal.valueOf(SingleEquationParser.Evaluate(xl)))).
                        divide(BigDecimal.valueOf((SingleEquationParser.Evaluate(xu))).subtract(BigDecimal.valueOf(SingleEquationParser.Evaluate(xl))) , MathContext.DECIMAL128)).
                round(new MathContext(SigFigsHandler.getSigFigs(), RoundingMode.HALF_UP));
        if(eps.compareTo(xr.abs()) > 0) xr = BigDecimal.ZERO ;

        NonLinearPrinter.Add("#" + (loops + 1) + " Iteration\nXu = " + xu + "\nXl = " +
               xl+ "\nXr = " + xr + "\n");
        if (loops > 0) {
            if(xr.compareTo(BigDecimal.ZERO) != 0) {
                ea = (xr.subtract(Oldxr).divide(xr , MathContext.DECIMAL128)).abs().round(new MathContext(SigFigsHandler.getSigFigs(), RoundingMode.HALF_UP));
                NonLinearPrinter.Add("Relative Error = " + ea+ "\n");
            }
        }
        NonLinearPrinter.Add("\n");

        if (SingleEquationParser.Evaluate(xr) * SingleEquationParser.Evaluate(xl) <= 0) {
            FalsePosition(xl, xr, xr, loops + 1);
        } else if (SingleEquationParser.Evaluate(xr) * SingleEquationParser.Evaluate(xl) > 0) {
            FalsePosition(xr, xu, xr, loops + 1);
        }
    }
}
