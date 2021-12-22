package com.example.NumericalProject;

import com.jfoenix.controls.JFXComboBox;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Modality;

import java.math.BigDecimal;

/***
 * this class to handle all the inputs and data that coming from user
 */
public class InputHandler {

    /***
     *shows a warning message on the screen when called
     * @param title the title of the warning
     * @param msg any instruction to put in the body of the message
     */
    public static void WrongInput(String title ,String msg){
        Alert.AlertType type = Alert.AlertType.WARNING ;
        Alert alert = new Alert(type , msg) ;

        //to prevent user's default actions like mouse right-click
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.setHeaderText(title);
        alert.showAndWait();
    }

    /***
     * to handle inputs of text area as writing one equation or not writing at all
     * @param Equations the text area to be handled
     * @return boolean false if there is no errors
     */
    public static Boolean TextArea(TextArea Equations){
        if(Equations.getText().strip().equalsIgnoreCase("") || Equations.getText().strip().split("\n").length == 1){
            WrongInput("Missing Data" , "Please Write All Inputs");
            return true ;
        }
        return false ;
    }

    /***
     * to handle the significant figures input from user
     * @param SigFigs the text field of significant figures to handle its text
     * @return boolean false if there is no errors
     */
    public static Boolean SigsFigs(TextField SigFigs){
        double num ;

        //if the user didn't write anything then use the default and there is no error
        if (SigFigs.getText().equals("")) return false;

        //if the user entered non-numbers like characters
        try {
            num = Double.parseDouble(SigFigs.getText().strip()) ;
        }catch (Exception e){
            WrongInput("Wrong Data" , "Please Number Of Significant Figures");
            return true ;
        }

        //if the user entered double-type number then it is an error
        if(num % 1 != 0){
            WrongInput("Wrong Data" , "Please Write an Integer in Significant Figures");
            return true;
        }
        return false ;
    }


    /***
     * to handle only if the user didn't make a choice in  a combo box
     * @param Select the combo box to handle it
     * @param msg the message to show to user if he didn't make a choice
     * @return boolean false if there is no errors
     */
    public static Boolean ComboBox(JFXComboBox<String> Select , String msg){
        if(Select.getValue() == null){
            WrongInput("Missing Data" , msg);
            return true;
        }
        return false;
    }

    /***
     * to handle the text fields of stopping condition combo boxes
     * @param ConditionValue the value which the user entered for his choice
     * @param Condition the combo box to know the user choice to compare it with the user's input
     * @return boolean false if there is no errors
     */
    public static Boolean ConditionValue(TextField ConditionValue ,JFXComboBox<String> Condition){
        double num ;

        //to check for entering non-numbers value
        try {
            num = Double.parseDouble(ConditionValue.getText().strip()) ;
        }catch (Exception e){
            WrongInput("Wrong Data" , "Please Write Consistent Data");
            return true ;
        }

        //if the user's choice was iteration then the user should write an integer otherwise is an error
        if(Condition.getValue().equalsIgnoreCase("iterations")){
            if(num % 1 != 0){
                WrongInput("Wrong Data" , "Please Write an Integer in Condition of Iterations");
                return true;
            }
        }
        return false;
    }


    /***
     * to check from the user's input for initial guesses in jacobi iteration and gauss seidel
     * @param InitialGuess the text field which contains the user's input
     * @param Guess the array which will contain the user's input after parsing if correct
     * @return boolean false if there is no errors
     */
    public static Boolean InitialGuess(TextField InitialGuess , BigDecimal[] Guess){

        //check if the number of initial guesses was'nt equal to number of variables
        if(InitialGuess.getText().strip().split(" ").length != Guess.length-1){
            WrongInput("Missing Data" , "Please Write All Inputs");
            return true ;
        }

        //check for entering non-numbers value
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
