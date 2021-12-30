package com.example.NumericalProject.MethodsControllers.LinearEquations;

import com.example.NumericalProject.EquationsParser.Numbers;
import com.example.NumericalProject.InputHandler;
import com.example.NumericalProject.MethodsCalculations.LinearEquations.Initialization;
import com.example.NumericalProject.MethodsCalculations.LinearEquations.MethodsUtilities;
import com.example.NumericalProject.MethodsCalculations.LinearEquations.NaiveGaussCalc;
import com.example.NumericalProject.EquationsParser.MultiEquationsParser;
import com.example.NumericalProject.Printers.LinearPrinter;
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
 * class to control the Naive Gauss method's view
 */
public class NaiveGauss implements Initializable {

    Map<String, ArrayList<BigDecimal>> dummy = null;

    //the reference for the components in the fxml file
    @FXML
    private TextField SigFigs;
    @FXML
    private TextArea Equations;
    @FXML
    private Text Output;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    /***
     * to calculate the result for user's input
     */
    public void Calculate() {
        int figures = 0;

        //to check errors in any of user's inputs then calculate the result
        if (InputHandler.TextField(SigFigs, true) || InputHandler.TextArea(Equations)) return;

        try {
            dummy = MultiEquationsParser.ToEquations(Equations.getText().strip().split("\n"));
        } catch (Exception e) {
            InputHandler.WrongInput("Wrong Data", "Please Write Right Equations");
            return;
        }
        if (!Objects.equals(SigFigs.getText(), "")) figures = Numbers.ParseInt(SigFigs);

        Initialization Init;
        try {
            Init = new Initialization(new LinearPrinter(), new MethodsUtilities(), dummy);
            if (!Objects.equals(SigFigs.getText(), "")) Init.setSigFigs(figures);
            NaiveGaussCalc naiveGaussCalc = new NaiveGaussCalc(Init);
            naiveGaussCalc.NaiveGauss();
        } catch (Exception e) {
            InputHandler.WrongInput("Wrong Data", "Please Write Right Equations");
            return ;
        }

        //put the result on the screen
        Output.setText(Init.getPrint().getPrinter());
    }

}
