package com.example.NumericalProject.MethodsControllers.NonLinearEquations;

import com.example.NumericalProject.EquationsParser.Numbers;
import com.example.NumericalProject.EquationsParser.SingleEquationParser;
import com.example.NumericalProject.InputHandler;
import com.example.NumericalProject.MethodsCalculations.NonLinearEquations.NewtonCalculation;
import com.example.NumericalProject.Printers.NonLinearPrinter;
import com.example.NumericalProject.Printers.SigFigsHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

/***
 * class to control the newton raphson method's view
 */
public class NewtonRaphson implements Initializable {


    @FXML
    private LineChart<Double, Double> lineGraph;

    @FXML
    private Text Output;

    @FXML
    private TextField SigFigs, MaxIterations, EPS, Equation, IntervalFrom;

    @FXML
    private AnchorPane MainPane;

    private MyGraph mathsGraph;

    @Override
    public void initialize(final URL url, final ResourceBundle rb) {
    }

    /***
     * to calculate the result for user's input
     */
    public void Calculate() throws IOException {
        lineGraph.setVisible(false);
        if (mathsGraph != null) mathsGraph.clear();
        MainPane.setMinHeight(AnchorPane.USE_PREF_SIZE);
        SingleEquationParser.SetEquation(Equation.getText().strip());

        try {
            SingleEquationParser.Evaluate(BigDecimal.valueOf(1));
        } catch (Exception e) {
            InputHandler.WrongInput("Wrong Equation", "Please Write Right Equation");
            return;
        }

        if (InputHandler.TextField(SigFigs, true) || InputHandler.TextField(MaxIterations, true)
                || InputHandler.TextField(EPS, false) || InputHandler.TextField(IntervalFrom, false))
            return;

        if (Objects.equals(Equation.getText().strip(), "") || Objects.equals(IntervalFrom.getText().strip(), "")) {
            InputHandler.WrongInput("Missing Data", "Please Write all Inputs");
            return;
        }

        lineGraph.setVisible(true);
        try {
            mathsGraph = new MyGraph(lineGraph, 10);
            mathsGraph.plotFunction();
        } catch (Exception e) {
            InputHandler.WrongInput("Missing Data", "Please Write Interval Boundaries");
        }
        MainPane.setMinHeight(AnchorPane.USE_COMPUTED_SIZE);

        if (!Objects.equals(MaxIterations.getText().strip(), ""))
            NewtonCalculation.setMaxIteration(Numbers.ParseInt(MaxIterations));
        if (!Objects.equals(EPS.getText().strip(), ""))
            NewtonCalculation.setEps(BigDecimal.valueOf(Numbers.ParseDouble(EPS)));
        if (!Objects.equals(SigFigs.getText().strip(), "")) SigFigsHandler.setSigFigs(Numbers.ParseInt(SigFigs));

        mathsGraph.plotDerivative();

        long time = System.currentTimeMillis();
        NewtonCalculation.Newton(BigDecimal.valueOf(Numbers.ParseDouble(IntervalFrom)), 0);
        NonLinearPrinter.Add("\nCalculation Time: " + (System.currentTimeMillis() - time) + " ms");
        Output.setText(NonLinearPrinter.getResult());

        NonLinearPrinter.Reset();
        SigFigsHandler.Reset();
    }
}
