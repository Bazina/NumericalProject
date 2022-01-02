package com.example.NumericalProject.MethodsControllers.NonLinearEquations;

import com.example.NumericalProject.EquationsParser.SingleEquationParser;
import javafx.scene.chart.XYChart;

import java.math.BigDecimal;


public class MyGraph {

    private XYChart<Double, Double> graph;
    private double range;

    public MyGraph(final XYChart<Double, Double> graph, final double range) {
        this.graph = graph;
        this.range = range;
    }

    public void plotFunction() {
        final XYChart.Series<Double, Double> series = new XYChart.Series<>();
        for (int x = 0; x <= 2000; x+=1) {
            plotPoint(-range + x*0.1, SingleEquationParser.Evaluate(BigDecimal.valueOf(-range + x*0.1) ), series);
        }
        graph.getData().add(series);
    }

    public void plotDerivative() {
        final XYChart.Series<Double, Double> series = new XYChart.Series<>();
        for (int x = 0; x <= 2000; x+=1) {
            plotPoint(-range + x*0.1, SingleEquationParser.EvaluateDerivative(BigDecimal.valueOf(-range + x*0.1) ), series);
        }
        graph.getData().add(series);
    }

    public void plotLine(double X) {
        final XYChart.Series<Double, Double> series = new XYChart.Series<>();

        plotPoint(X, -10, series);
        plotPoint(X, 10, series);

        graph.getData().add(series);
    }

    private void plotPoint(final double x, final double y,
                           final XYChart.Series<Double, Double> series) {
        series.getData().add(new XYChart.Data<>(x, y));
    }



    public void clear() {
        graph.getData().clear();
    }
}