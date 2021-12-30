package com.example.NumericalProject.Printers;

public class NonLinearPrinter {
    private static String Result = "" ;

    public static void Reset(){
        Result = "" ;
    }

    public static void Add(String Value){
        Result += Value ;
    }

    public static String getResult(){
        return Result ;
    }
}
