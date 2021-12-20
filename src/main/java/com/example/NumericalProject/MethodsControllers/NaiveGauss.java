package com.example.NumericalProject.MethodsControllers;

import com.example.NumericalProject.InputHandler;
import com.example.NumericalProject.MethodsCalculations.InitGauss;
import com.example.NumericalProject.MethodsCalculations.MethodsUtilities;
import com.example.NumericalProject.MethodsCalculations.NaiveGaussCalc;
import com.example.NumericalProject.Parse;
import com.example.NumericalProject.Print;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;
import java.util.ResourceBundle;

public class NaiveGauss implements Initializable {

    Map<String, ArrayList<BigDecimal>> dummy = null;
    @FXML
    private TextField SigFigs;
    @FXML
    private TextArea Equations;
    @FXML
    private Text Output;
    private InitGauss initGauss;
    private int Figures;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public void Calculate() {

        if (InputHandler.SigsFigs(SigFigs) || InputHandler.TextArea(Equations)) return;

        try {
            dummy = Parse.ToEquations(Equations.getText().split("\n"));
        } catch (Exception e) {
            InputHandler.WrongInput("Wrong Data", "Please Write Right Equations");
            return;
        }
        Figures = Integer.parseInt((SigFigs.getText().strip()));

        try {
            initGauss = new InitGauss(new Print(), new MethodsUtilities(), dummy);
            initGauss.setSigFigs(Figures);
            NaiveGaussCalc naiveGaussCalc = new NaiveGaussCalc(initGauss);
            naiveGaussCalc.NaiveGauss();
        } catch (Exception e) {
            InputHandler.WrongInput("Wrong Data", "Please Write Right Equations");
            return ;
        }

        Output.setText(initGauss.getPrint().getPrinter());
    }

}
