package com.example.NumericalProject.MethodsControllers;

import com.example.NumericalProject.MethodsCalculations.InitGauss;
import com.example.NumericalProject.MethodsCalculations.LUDecompDoolittleCalc;
import com.example.NumericalProject.MethodsCalculations.MethodsUtilities;
import com.example.NumericalProject.Print;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import com.jfoenix.controls.JFXButton;

import java.net.URL;
import java.util.ResourceBundle;

public class NaiveGauss implements Initializable {
    @FXML
    private JFXButton Calc;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Calc.setOnMouseClicked(e -> {
            System.out.println("NaiveGauss");
            //NaiveGaussCalc naiveGaussCalc = new NaiveGaussCalc(new InitGauss(new Print(), new MethodsUtilities()));
            //naiveGaussCalc.NaiveGauss();
            LUDecompDoolittleCalc luDecompDoolittleCalc = new LUDecompDoolittleCalc(new InitGauss(new Print(), new MethodsUtilities()));
            luDecompDoolittleCalc.LUDecompDoolittle();
        });
    }

}
