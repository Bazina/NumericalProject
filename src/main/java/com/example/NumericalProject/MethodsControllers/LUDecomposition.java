package com.example.NumericalProject.MethodsControllers;

import com.example.NumericalProject.InputHandler;
import com.example.NumericalProject.MethodsCalculations.Initialization;
import com.example.NumericalProject.MethodsCalculations.LUDecompCalc;
import com.example.NumericalProject.MethodsCalculations.MethodsUtilities;
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

public class LUDecomposition implements Initializable {
    Map<String, ArrayList<BigDecimal>> dummy = null;
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

    public void Calculate() {

        int figures = 0;

        if (InputHandler.SigsFigs(SigFigs) || InputHandler.TextArea(Equations) || InputHandler.ComboBox(Forms, "Please, Select The Output Form"))
            return;

        try {
            dummy = Parse.ToEquations(Equations.getText().strip().split("\n"));
        } catch (Exception e) {
            InputHandler.WrongInput("Wrong Data", "Please Write Right Equations");
            return;
        }
        if (!Objects.equals(SigFigs.getText().strip(), "")) figures = Integer.parseInt((SigFigs.getText().strip()));

        Initialization Init;
        try {
            Init = new Initialization(new Print(), new MethodsUtilities(), dummy);
            if (!Objects.equals(SigFigs.getText().strip(), "")) Init.setSigFigs(figures);
            LUDecompCalc luDecompDoolittleCalc = new LUDecompCalc(Init);
            luDecompDoolittleCalc.LUDecomp(Forms.getValue());
        } catch (Exception e) {
            InputHandler.WrongInput("Wrong Data", "Not Positive Definite Matrix");
            return;
        }
        Output.setText(Init.getPrint().getPrinter());
    }
}
