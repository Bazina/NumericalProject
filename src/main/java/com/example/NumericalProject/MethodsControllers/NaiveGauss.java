package com.example.NumericalProject.MethodsControllers;

import com.example.NumericalProject.MethodsCalculations.InitGauss;
import com.example.NumericalProject.MethodsCalculations.MethodsUtilities;
import com.example.NumericalProject.MethodsCalculations.NaiveGaussCalc;
import com.example.NumericalProject.Print;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class NaiveGauss implements Initializable {

    @FXML
    private TextField SigFigs ;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void Calculate(){
        System.out.println("Naive Gauss");
        System.out.println(SigFigs.getText());
        NaiveGaussCalc naiveGaussCalc = new NaiveGaussCalc(new InitGauss(new Print(), new MethodsUtilities()));
        naiveGaussCalc.NaiveGauss();
    }

}
