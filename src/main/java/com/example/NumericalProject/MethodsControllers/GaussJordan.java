package com.example.NumericalProject.MethodsControllers;

import com.example.NumericalProject.InputHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class GaussJordan implements Initializable {
    @FXML
    private TextField SigFigs ;
    @FXML
    private TextArea Equations ;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void Calculate(){
        System.out.println("Gauss Jordan");
        System.out.println(SigFigs.getText());
        System.out.println(Equations.getText());

        if(InputHandler.SigsFigs(SigFigs) || InputHandler.TextArea(Equations)) return;
    }
}
