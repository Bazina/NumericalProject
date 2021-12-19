package com.example.NumericalProject;

import com.jfoenix.controls.JFXComboBox;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Modality;

public class InputHandler {
    private static void WrongInput(String title ,String msg){
        Alert.AlertType type = Alert.AlertType.WARNING ;
        Alert alert = new Alert(type , msg) ;
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.setHeaderText(title);
        alert.showAndWait();
    }

    public static Boolean TextArea(TextArea Equations){
        if(Equations.getText().strip().equalsIgnoreCase("")){
            WrongInput("Missing Data" , "Please Write All Inputs");
            return true ;
        }
        return false ;
    }

    public static Boolean SigsFigs(TextField SigFigs){
        double num ;
        if(SigFigs.getText().strip().equalsIgnoreCase("")){
            WrongInput("Missing Data" , "Please Write All Inputs");
            return true;
        }
        try {
            num = Double.parseDouble(SigFigs.getText().strip()) ;
        }catch (Exception e){
            WrongInput("Wrong Data" , "Please Number Of Significant Figures");
            return true ;
        }
        if(num % 1 != 0){
            WrongInput("Wrong Data" , "Please Write an Integer in Significant Figures");
            return true;
        }
        return false ;
    }

    public static Boolean ComboBox(JFXComboBox<String> Select , String msg){
        if(Select.getValue() == null){
            WrongInput("Missing Data" , msg);
            return true;
        }
        return true;
    }
}
