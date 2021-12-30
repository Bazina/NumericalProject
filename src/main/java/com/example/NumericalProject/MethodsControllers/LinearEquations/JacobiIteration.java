package com.example.NumericalProject.MethodsControllers.LinearEquations;

import com.example.NumericalProject.EquationsParser.Numbers;
import com.example.NumericalProject.InputHandler;
import com.example.NumericalProject.MethodsCalculations.LinearEquations.Initialization;
import com.example.NumericalProject.MethodsCalculations.LinearEquations.JacobiCalc;
import com.example.NumericalProject.MethodsCalculations.LinearEquations.MethodsUtilities;
import com.example.NumericalProject.EquationsParser.MultiEquationsParser;
import com.example.NumericalProject.Printers.LinearPrinter;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;

/***
 * class to control the Jacobi Iteration method's view
 */
public class JacobiIteration implements Initializable {
    Map<String, ArrayList<BigDecimal>> dummy = null;

    //the reference for the components in the fxml file
    @FXML
    private TextField SigFigs;
    @FXML
    private TextArea Equations;
    @FXML
    private Text Output;
    @FXML
    private JFXComboBox<String> ChosenCondition ;
    @FXML
    private TextField ConditionValue , InitialGuess ;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ChosenCondition.setItems(FXCollections.observableArrayList("Iterations" , "Relative Error"));
        ChosenCondition.setLabelFloat(true);
    }

    /***
     * to calculate the result for user's input
     */
    public void Calculate(){
        int figures = 0;

        //to check errors in any of user's inputs then calculate the result
        if (InputHandler.TextField(SigFigs, true) || InputHandler.TextArea(Equations) || InputHandler.ComboBox(ChosenCondition, "Please, Select The Stopping Condition"))
            return;

        if(InputHandler.ConditionValue(ConditionValue , ChosenCondition)) return;
        double ConditionNum = Numbers.ParseDouble(ConditionValue);


        BigDecimal[] Guess = new BigDecimal[Equations.getText().strip().split("\n").length+1];
        if(InputHandler.InitialGuess(InitialGuess ,Guess)) return;

        try{
            dummy = MultiEquationsParser.ToEquations(Equations.getText().strip().split("\n"));
        } catch (Exception e) {
            InputHandler.WrongInput("Wrong Data", "Please Write Right Equations");
            return;
        }
        if (!Objects.equals(SigFigs.getText().strip(), "")) figures = Numbers.ParseInt(SigFigs);

        Initialization Init;
        try {

            Init = new Initialization(new LinearPrinter(), new MethodsUtilities(), dummy);
            Init.setX(Guess.clone());

            if(!Objects.equals(SigFigs.getText().strip(), "")) Init.setSigFigs(figures);

            if(ChosenCondition.getValue().equalsIgnoreCase("iterations")){
                Init.setIterations((int)ConditionNum);
                Init.setTol(BigDecimal.valueOf(1E-20));
            }else{
                Init.setTol(BigDecimal.valueOf(ConditionNum));
                Init.setIterations(1000);
            }

            JacobiCalc jacobiCalc = new JacobiCalc(Init);
            jacobiCalc.JacobiInit();

        } catch (Exception e) {
            InputHandler.WrongInput("Wrong Data", "Please Write Right Equations");
            return ;
        }

        //put the result on the screen
        Output.setText(Init.getPrint().getPrinter());
    }
}
