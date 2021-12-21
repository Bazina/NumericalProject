package com.example.NumericalProject.MethodsControllers;

import com.example.NumericalProject.InputHandler;
import com.example.NumericalProject.MethodsCalculations.InitGauss;
import com.example.NumericalProject.MethodsCalculations.JacobiCalc;
import com.example.NumericalProject.MethodsCalculations.MethodsUtilities;
import com.example.NumericalProject.MethodsCalculations.NaiveGaussCalc;
import com.example.NumericalProject.Parse;
import com.example.NumericalProject.Print;
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

public class JacobiIteration implements Initializable {
    Map<String, ArrayList<BigDecimal>> dummy = null;

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

    public void Calculate(){
        int figures = 0;

        if (InputHandler.SigsFigs(SigFigs) || InputHandler.TextArea(Equations) || InputHandler.ComboBox(ChosenCondition, "Please, Select The Stopping Condition"))
            return;

        if(InputHandler.ConditionValue(ConditionValue , ChosenCondition)) return;
        double ConditionNum = Double.parseDouble(ConditionValue.getText().strip());


        BigDecimal[] Guess = new BigDecimal[Equations.getText().strip().split("\n").length+1];
        if(InputHandler.InitialGuess(InitialGuess ,Guess)) return;

        try{
            dummy = Parse.ToEquations(Equations.getText().strip().split("\n"));
        } catch (Exception e) {
            InputHandler.WrongInput("Wrong Data", "Please Write Right Equations");
            return;
        }
        if (!Objects.equals(SigFigs.getText().strip(), "")) figures = Integer.parseInt((SigFigs.getText().strip()));

        InitGauss initGauss;
        try {

            initGauss = new InitGauss(new Print(), new MethodsUtilities(), dummy);
            initGauss.setX(Guess.clone());

            if(!Objects.equals(SigFigs.getText().strip(), "")) initGauss.setSigFigs(figures);

            if(ChosenCondition.getValue().equalsIgnoreCase("iterations")){
                initGauss.setIterations((int)ConditionNum);
                initGauss.setTol(BigDecimal.valueOf(1E-20));
            }else{
                initGauss.setTol(BigDecimal.valueOf(ConditionNum));
                initGauss.setIterations(1000);
            }

            JacobiCalc jacobiCalc = new JacobiCalc(initGauss);
            jacobiCalc.JacobiInit();

        } catch (Exception e) {
            InputHandler.WrongInput("Wrong Data", "Please Write Right Equations");
            return ;
        }

        Output.setText(initGauss.getPrint().getPrinter());
    }
}
