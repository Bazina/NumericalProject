package com.example.NumericalProject.MethodsControllers.NonLinearEquations;

import com.example.NumericalProject.EquationsParser.Numbers;
import com.example.NumericalProject.EquationsParser.SingleEquationParser;
import com.example.NumericalProject.InputHandler;
import com.example.NumericalProject.MethodsCalculations.NonLinearEquations.FalsePositionCalculation;
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

public class FalsePosition implements Initializable {


    @FXML
    private LineChart<Double, Double> lineGraph;

    @FXML
    private Text Output;

    @FXML
    private TextField SigFigs, MaxIterations, EPS, Equation, IntervalFrom, IntervalTo;

    @FXML
    private AnchorPane MainPane;

    private MyGraph mathsGraph;

    @Override
    public void initialize(final URL url, final ResourceBundle rb) {
    }

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
                || InputHandler.TextField(EPS, false) || InputHandler.TextField(IntervalFrom, false)
                || InputHandler.TextField(IntervalTo, false))
            return;

        if (Objects.equals(Equation.getText().strip(), "") || Objects.equals(IntervalFrom.getText().strip(), "")
                || Objects.equals(IntervalTo.getText().strip(), "")) {
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
            FalsePositionCalculation.setMaxIteration(Numbers.ParseInt(MaxIterations));
        if (!Objects.equals(EPS.getText().strip(), "")) FalsePositionCalculation.setEps(BigDecimal.valueOf(Numbers.ParseDouble(EPS)));
        if(!Objects.equals(SigFigs.getText().strip(), "")) SigFigsHandler.setSigFigs(Numbers.ParseInt(SigFigs));

        mathsGraph.plotLine(Numbers.ParseDouble(IntervalFrom), Numbers.ParseDouble(IntervalFrom), 10, -10);
        mathsGraph.plotLine(Numbers.ParseDouble(IntervalTo), Numbers.ParseDouble(IntervalTo), 10, -10);
        FalsePositionCalculation.FalsePosition(BigDecimal.valueOf(Numbers.ParseDouble(IntervalFrom)), BigDecimal.valueOf(Numbers.ParseDouble(IntervalTo)), BigDecimal.ZERO, 0);
        Output.setText(NonLinearPrinter.getResult());

        NonLinearPrinter.Reset();
        SigFigsHandler.Reset() ;
    }
}
