package com.example.NumericalProject.Printers;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class SigFigsHandler {
    private static int SigFigs = 4 ;
    private static BigDecimal er = BigDecimal.valueOf(0.005);

    public static void setSigFigs(int num){
        SigFigs = num ;
        ChangeError();
    }

    public static void Reset(){
        SigFigs = 4 ;
        ChangeError();
    }

    public static int getSigFigs() {
        return SigFigs;
    }

    private static void ChangeError(){
        er = BigDecimal.valueOf(0.5).multiply(BigDecimal.valueOf(10).pow(2-getSigFigs() , new MathContext(4 , RoundingMode.HALF_UP))) ;
    }

    public static BigDecimal getEr() {
        return er;
    }
}
