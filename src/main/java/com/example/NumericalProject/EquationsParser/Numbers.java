package com.example.NumericalProject.EquationsParser;

import javafx.scene.control.TextInputControl;

public class Numbers {
    public static int ParseInt(TextInputControl input){
        return Integer.parseInt(input.getText().strip()) ;
    }

    public static double ParseDouble(TextInputControl input){
        return Double.parseDouble(input.getText().strip()) ;
    }
}
