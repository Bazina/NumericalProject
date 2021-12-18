package com.example.NumericalProject.MethodsControllers;

import com.example.NumericalProject.MethodsCalculations.GaussJordanCalc;
import com.example.NumericalProject.MethodsCalculations.InitGauss;
import com.example.NumericalProject.MethodsCalculations.MethodsUtilities;
import com.example.NumericalProject.Print;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class LUDecomposition implements Initializable {
    @FXML
    private JFXButton Calc ;
    @FXML
    private JFXComboBox<String> Forms ;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Calc.setOnMouseClicked(e->{
            System.out.println("LU Decomposition");
            System.out.println(Forms.getValue());
        });

        Forms.setItems(FXCollections.observableArrayList("DooLittle" , "Crout" , "Cholesky"));
        Forms.setLabelFloat(true);
    }
}
