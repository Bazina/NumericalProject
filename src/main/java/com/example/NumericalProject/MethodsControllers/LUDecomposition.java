package com.example.NumericalProject.MethodsControllers;

import com.example.NumericalProject.MethodsCalculations.InitGauss;
import com.example.NumericalProject.MethodsCalculations.LUDecompCalc;
import com.example.NumericalProject.MethodsCalculations.MethodsUtilities;
import com.example.NumericalProject.Print;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class LUDecomposition implements Initializable {
    @FXML
    private JFXComboBox<String> Forms ;
    @FXML
    private TextField SigFigs ;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Forms.setItems(FXCollections.observableArrayList("DooLittle" , "Crout" , "Cholesky"));
        Forms.setLabelFloat(true);
    }

    public void Calculate(){
        System.out.println("LU Decomposition");
        System.out.println(Forms.getValue());
        System.out.println(SigFigs.getText());
        LUDecompCalc luDecompDoolittleCalc = new LUDecompCalc(new InitGauss(new Print(), new MethodsUtilities()));
        luDecompDoolittleCalc.LUDecomp(Forms.getValue());
    }
}
