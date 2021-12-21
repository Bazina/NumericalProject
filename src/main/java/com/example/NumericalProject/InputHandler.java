package com.example.NumericalProject;

import com.jfoenix.controls.JFXComboBox;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Modality;

import java.math.BigDecimal;

public class InputHandler {
    public static void WrongInput(String title ,String msg){
        Alert.AlertType type = Alert.AlertType.WARNING ;
        Alert alert = new Alert(type , msg) ;
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.setHeaderText(title);
        alert.showAndWait();
    }

    public static Boolean TextArea(TextArea Equations){
        if(Equations.getText().strip().equalsIgnoreCase("") || Equations.getText().strip().split("\n").length == 1){
            WrongInput("Missing Data" , "Please Write All Inputs");
            return true ;
        }

        return false ;
    }

    public static Boolean SigsFigs(TextField SigFigs){
        double num ;
        if (SigFigs.getText().equals("")) return false;
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
        return false;
    }

    public static Boolean ConditionValue(TextField ConditionValue ,JFXComboBox<String> Condition){
        double num ;
        try {
            num = Double.parseDouble(ConditionValue.getText().strip()) ;
        }catch (Exception e){
            WrongInput("Wrong Data" , "Please Write Consistent Data");
            return true ;
        }

        if(Condition.getValue().equalsIgnoreCase("iterations")){
            if(num % 1 != 0){
                WrongInput("Wrong Data" , "Please Write an Integer in Condition of Iterations");
                return true;
            }
        }
        return false;
    }

    public static Boolean InitialGuess(TextField InitialGuess , BigDecimal[] Guess){
        if(InitialGuess.getText().strip().split(" ").length < Guess.length-1){
            WrongInput("Missing Data" , "Please Write All Inputs");
            return true ;
        }

        Guess[0] = BigDecimal.ZERO ;
        for (int i = 1; i < Guess.length; i++) {
            try {
                Guess[i] = BigDecimal.valueOf(Double.parseDouble(InitialGuess.getText().strip().split(" ")[i-1])) ;
            }catch (Exception e){
                WrongInput("Wrong Data" , "Please Write Consistent Data");
                return true ;
            }
        }

        return false ;
    }
}
