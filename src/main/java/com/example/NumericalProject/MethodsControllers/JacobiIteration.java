package com.example.NumericalProject.MethodsControllers;

import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.net.URL;
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
    }
}
