package com.example.NumericalProject.MethodsControllers;

import com.example.NumericalProject.InputHandler;
import com.example.NumericalProject.MethodsCalculations.InitGauss;
import com.example.NumericalProject.MethodsCalculations.MethodsUtilities;
import com.example.NumericalProject.MethodsCalculations.NaiveGaussCalc;
import com.example.NumericalProject.Print;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;
import java.util.ResourceBundle;

public class NaiveGauss implements Initializable {

    @FXML
    private TextField SigFigs ;
    @FXML
    private TextArea Equations ;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public void Calculate(){
        System.out.println("Naive Gauss");
        System.out.println(SigFigs.getText());
        System.out.println(Equations.getText());

        Map<String, ArrayList<BigDecimal>> dummy = null;

        if (InputHandler.SigsFigs(SigFigs) || InputHandler.TextArea(Equations)) return;
        InitGauss initGauss = new InitGauss(new Print(), new MethodsUtilities(), dummy);
        NaiveGaussCalc naiveGaussCalc = new NaiveGaussCalc(initGauss);
        naiveGaussCalc.NaiveGauss();
    }

}
