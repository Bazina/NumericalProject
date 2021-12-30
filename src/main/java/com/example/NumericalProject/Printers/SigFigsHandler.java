package com.example.NumericalProject.Printers;

import java.math.BigDecimal;
import java.security.PublicKey;

public class SigFigsHandler {
    public static int SignificantFigures = 4 ;

    public static void setSignificantFigures(int num){
        SignificantFigures = num ;
    }

    public static void Reset(){
        SignificantFigures = 4 ;
    }



    public static int getSignificantFigures() {
        return SignificantFigures;
    }
}
