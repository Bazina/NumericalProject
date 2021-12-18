package com.example.NumericalProject.MethodsControllers;

import com.example.NumericalProject.MethodsCalculations.GaussJordanCalc;
import com.example.NumericalProject.MethodsCalculations.InitGauss;
import com.example.NumericalProject.MethodsCalculations.MethodsUtilities;
import com.example.NumericalProject.Print;
import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class GaussJordan implements Initializable {
    @FXML
    private JFXButton Calc ;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Calc.setOnMouseClicked(e->{
            System.out.println("GaussJordan");
            GaussJordanCalc gaussJordanCalc = new GaussJordanCalc(new InitGauss(new Print(), new MethodsUtilities()));
            gaussJordanCalc.GaussJordan();
        });
    }
}
