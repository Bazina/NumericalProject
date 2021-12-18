package com.example.NumericalProject.MethodsControllers;

import com.example.NumericalProject.MethodsCalculations.GaussJordanCalc;
import com.example.NumericalProject.MethodsCalculations.InitGauss;
import com.example.NumericalProject.MethodsCalculations.MethodsUtilities;
import com.example.NumericalProject.Print;
import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class GaussJordan implements Initializable {
    @FXML
    private TextField SigFigs ;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void Calculate(){
        System.out.println("Gauss Jordan");
        System.out.println(SigFigs.getText());
    }
}
