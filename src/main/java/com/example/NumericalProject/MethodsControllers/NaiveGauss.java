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
import java.util.Objects;
import java.util.ResourceBundle;

public class NaiveGauss implements Initializable {

    Map<String, ArrayList<BigDecimal>> dummy = null;
    @FXML
    private TextField SigFigs;
    @FXML
    private TextArea Equations;
    @FXML
    private Text Output;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public void Calculate() {
        int figures = 0;

        if (InputHandler.SigsFigs(SigFigs) || InputHandler.TextArea(Equations)) return;

        try {
            dummy = Parse.ToEquations(Equations.getText().split("\n"));
        } catch (Exception e) {
            InputHandler.WrongInput("Wrong Data", "Please Write Right Equations");
            return;
        }
        if (!Objects.equals(SigFigs.getText(), "")) figures = Integer.parseInt((SigFigs.getText().strip()));

        InitGauss initGauss;
        try {
            initGauss = new InitGauss(new Print(), new MethodsUtilities(), dummy);
            if (!Objects.equals(SigFigs.getText(), "")) initGauss.setSigFigs(figures);
            NaiveGaussCalc naiveGaussCalc = new NaiveGaussCalc(initGauss);
            naiveGaussCalc.NaiveGauss();
        } catch (Exception e) {
            InputHandler.WrongInput("Wrong Data", "Please Write Right Equations");
            return ;
        }

        Output.setText(initGauss.getPrint().getPrinter());
    }

}
