package com.example.NumericalProject.MethodsControllers.LinearEquations;

import com.example.NumericalProject.EquationsParser.Numbers;
import com.example.NumericalProject.InputHandler;
import com.example.NumericalProject.MethodsCalculations.LinearEquations.Initialization;
import com.example.NumericalProject.MethodsCalculations.LinearEquations.LUDecompCalc;
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
 * class to control the LU Decomposition method's view
 */
public class LUDecomposition implements Initializable {
    Map<String, ArrayList<BigDecimal>> dummy = null;

    //the reference for the components in the fxml file
    @FXML
    private JFXComboBox<String> Forms;
    @FXML
    private TextField SigFigs;
    @FXML
    private TextArea Equations;
    @FXML
    private Text Output;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Forms.setItems(FXCollections.observableArrayList("DooLittle", "Crout", "Cholesky"));
        Forms.setLabelFloat(true);

    }

    /***
     * to calculate the result for user's input
     */
    public void Calculate() {
        int figures = 0;

        //to check errors in any of user's inputs then calculate the result
        if (InputHandler.TextField(SigFigs, true) || InputHandler.TextArea(Equations) || InputHandler.ComboBox(Forms, "Please, Select The Output Form"))
            return;


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
            if (!Objects.equals(SigFigs.getText().strip(), "")) Init.setSigFigs(figures);
            LUDecompCalc luDecompDoolittleCalc = new LUDecompCalc(Init);
            luDecompDoolittleCalc.LUDecomp(Forms.getValue());
        } catch (Exception e) {
            InputHandler.WrongInput("Wrong Data", "Not Positive Definite Matrix");
            return;
        }

        //put the result on the screen
        Output.setText(Init.getPrint().getPrinter());
    }
}
