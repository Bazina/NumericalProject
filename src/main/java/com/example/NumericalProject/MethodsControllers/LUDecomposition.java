package com.example.NumericalProject.MethodsControllers;

import com.example.NumericalProject.InputHandler;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class LUDecomposition implements Initializable {
    @FXML
    private JFXComboBox<String> Forms;
    @FXML
    private TextField SigFigs;
    @FXML
    private TextArea Equations;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Forms.setItems(FXCollections.observableArrayList("DooLittle", "Crout", "Cholesky"));
        Forms.setLabelFloat(true);
    }

    public void Calculate() {
        System.out.println("LU Decomposition");
        System.out.println(Forms.getValue());
        System.out.println(SigFigs.getText());

        if (InputHandler.SigsFigs(SigFigs) || InputHandler.ComboBox(Forms, "Please Select a Form") || InputHandler.TextArea(Equations))
            return;
    }
}
