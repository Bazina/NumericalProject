package com.example.NumericalProject.MethodsControllers.NonLinearEquations;

import com.example.NumericalProject.EquationsParser.SingleEquationParser;
import javafx.scene.chart.XYChart;

import java.math.BigDecimal;


/**
 * Class for the graph plotting
 */
public class MyGraph {

    private XYChart<Double, Double> graph;
    private double range;

    public MyGraph(final XYChart<Double, Double> graph, final double range) {
        this.graph = graph;
        this.range = range;
    }

    /**
     * to plot the original function in the single parser class
     */
    public void plotFunction() {
        final XYChart.Series<Double, Double> series = new XYChart.Series<>();
        for (int x = 0; x <= 2000; x += 1) {
            plotPoint(-range + x * 0.1, SingleEquationParser.Evaluate(BigDecimal.valueOf(-range + x * 0.1)), series);
        }
        graph.getData().add(series);
    }

    /**
     * to plot the derivative of the function in the single parser class
     */
    public void plotDerivative() {
        final XYChart.Series<Double, Double> series = new XYChart.Series<>();
        for (int x = 0; x <= 2000; x += 1) {
            plotPoint(-range + x * 0.1, SingleEquationParser.EvaluateDerivative(BigDecimal.valueOf(-range + x * 0.1)), series);
        }
        graph.getData().add(series);
    }

    /**
     * to plot the desired line
     * @param X1,Y1 Co-ordinates for the first point
     * @param X2,Y2 Coordinates for the second point
     */
    public void plotLine(double X1, double X2, double Y1, double Y2) {
        final XYChart.Series<Double, Double> series = new XYChart.Series<>();

        plotPoint(X1, Y1, series);
        plotPoint(X2, Y2, series);

        graph.getData().add(series);
    }

    /**
     * to plot single point
     * @param x,y Coordinates of the point
     * @param series The series to put the point in
     */
    private void plotPoint(final double x, final double y,
                           final XYChart.Series<Double, Double> series) {
        series.getData().add(new XYChart.Data<>(x, y));
    }


    /**
     * clear any plotting in the graph
     */
    public void clear() {
        graph.getData().clear();
    }
}