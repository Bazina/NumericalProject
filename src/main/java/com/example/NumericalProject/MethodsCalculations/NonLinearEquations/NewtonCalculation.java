package com.example.NumericalProject.MethodsCalculations.NonLinearEquations;

import com.example.NumericalProject.EquationsParser.SingleEquationParser;
import com.example.NumericalProject.Printers.NonLinearPrinter;
import com.example.NumericalProject.Printers.SigFigsHandler;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
public class NewtonCalculation {
    public static BigDecimal ea = BigDecimal.ONE, eps = BigDecimal.valueOf(0.00001);
    public static int MaxIteration = 50;

    public static void setEps(BigDecimal value) {
        eps = value;
    }
    public static void setMaxIteration(int value) {
        MaxIteration = value;
    }
    public static void Newton(BigDecimal Xcurr, int loops) {
        BigDecimal Xnew;
        Xnew = Xcurr.subtract(BigDecimal.valueOf(SingleEquationParser.Evaluate(Xcurr)).
                divide(BigDecimal.valueOf(SingleEquationParser.EvaluateDerivative(Xcurr))));

        ea = ((Xnew.subtract(Xcurr)).divide(Xnew, MathContext.DECIMAL128)).multiply(BigDecimal.valueOf(100)).abs().round(new MathContext(SigFigsHandler.getSigFigs(), RoundingMode.HALF_UP));

        NonLinearPrinter.Add("#" + (loops + 1) + " Iteration\n" + "\nXi = " +
                Xcurr+ "\nXi+1 = " + Xnew + "\n");
        NonLinearPrinter.Add("Relative Error = " + ea+ "\n\n");
        if (ea.compareTo(eps) > 0 & loops < MaxIteration) {
            Xcurr = Xnew;
            Newton(Xcurr, loops+1);
        } else {
            if(ea.compareTo(eps)>0 & loops == MaxIteration){
                NonLinearPrinter.Add("There is no root");
                return;
            }
            NonLinearPrinter.Add("The Root = " + Xnew+ "\nThe Relative Error = "
                    + ea +"\nSignificant Figures = " + SigFigsHandler.getSigFigs() +"\nTotal No of Iterations = " + (loops) + "\n");

            eps = BigDecimal.valueOf(0.00001);
            MaxIteration = 50;
        }

    }

}
