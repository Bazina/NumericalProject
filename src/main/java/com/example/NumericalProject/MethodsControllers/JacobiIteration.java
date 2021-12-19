package com.example.NumericalProject.MethodsControllers;

import com.example.NumericalProject.MethodsCalculations.InitGauss;
import com.example.NumericalProject.MethodsCalculations.JacobiCalc;
import com.example.NumericalProject.MethodsCalculations.MethodsUtilities;
import com.example.NumericalProject.Print;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;
import java.util.ResourceBundle;

public class JacobiIteration implements Initializable {
    @FXML
    private JFXComboBox<String> ChosenCondition ;
    @FXML
    private TextField Condition , InitialGuess ;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ChosenCondition.setItems(FXCollections.observableArrayList("Iterations" , "Relative Error"));
        ChosenCondition.setLabelFloat(true);
    }

    public void Calculate(){
        System.out.println("JacobiIteration");
        System.out.println(ChosenCondition.getValue());
        System.out.println(Condition.getText());
        System.out.println(InitialGuess.getText());
        Map<String, ArrayList<BigDecimal>> dummy = null;

        InitGauss initGauss = new InitGauss(new Print(), new MethodsUtilities(), dummy);
        JacobiCalc jacobiCalc = new JacobiCalc(initGauss);
        jacobiCalc.JacobiInit();
    }
}
