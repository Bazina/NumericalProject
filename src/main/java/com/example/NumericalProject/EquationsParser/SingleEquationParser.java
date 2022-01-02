package com.example.NumericalProject.EquationsParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;

public class SingleEquationParser {
    private static String Equation;
    private static String RegularBuffer , DerivativeBuffer;

    public static void SetEquation(String Input) throws IOException {
        if(Input.contains("="))Input = HandleEquals(Input).replaceAll(" " , "") ;

        Runtime rt = Runtime.getRuntime();
        String[] commands = {"python", "M:\\CSED\\YEAR 2\\Numerical\\ProjectFX\\src\\main\\java\\com\\example\\NumericalProject\\Derivative.py", Input.replaceAll("\\^", "**")};
        Process proc = rt.exec(commands);

        BufferedReader stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream()));

        // Read the output from the command
        String s;
        while ((s = stdInput.readLine()) != null) {
            DerivativeBuffer = s.replaceAll("\\*\\*" , "^").replaceAll("e" , String.valueOf(Math.exp(1))) ;
        }
        RegularBuffer = Input.replaceAll("e" , String.valueOf(Math.exp(1)));
    }

    public static double Evaluate(BigDecimal Value) {
        return Evaluate(Value , true) ;
    }

    public static double EvaluateDerivative(BigDecimal Value) {
        return Evaluate(Value , false) ;
    }


    private static double Evaluate(BigDecimal Value , boolean Regular) {
        if (Regular) Equation = RegularBuffer.replaceAll("x", "(" + Value.toPlainString() +")");
        else Equation = DerivativeBuffer.replaceAll("x", "(" + Value.toPlainString() +")");

        return new Object() {
            int pos = -1, ch;

            void nextChar() {
                ch = (++pos < Equation.length()) ? Equation.charAt(pos) : -1;
            }

            boolean eat(int charToEat) {
                while (ch == ' ') nextChar();
                if (ch == charToEat) {
                    nextChar();
                    return true;
                }
                return false;
            }

            double parse() {
                nextChar();
                double x = parseExpression();
                if (pos < Equation.length())
                    throw new RuntimeException("Unexpected: " + (char) ch);
                return x;
            }

            // Grammar:
            // expression = term | expression `+` term | expression `-` term
            // term = factor | term `*` factor | term `/` factor
            // factor = `+` factor | `-` factor | `(` expression `)`
            //        | number | functionName factor | factor `^` factor

            double parseExpression() {
                double x = parseTerm();
                for (; ; ) {
                    if (eat('+')) x += parseTerm(); // addition
                    else if (eat('-')) x -= parseTerm(); // subtraction
                    else return x;
                }
            }

            double parseTerm() {
                double x = parseFactor();
                for (; ; ) {
                    if (eat('*')) x *= parseFactor(); // multiplication
                    else if (eat('/')) x /= parseFactor(); // division
                    else return x;
                }
            }

            double parseFactor() {
                if (eat('+')) return parseFactor(); // unary plus
                if (eat('-')) return -parseFactor(); // unary minus

                double x;
                int startPos = this.pos;
                if (eat('(')) { // parentheses
                    x = parseExpression();
                    eat(')');
                } else if ((ch >= '0' && ch <= '9') || ch == '.') { // numbers
                    while ((ch >= '0' && ch <= '9') || ch == '.') nextChar();
                    x = Double.parseDouble(Equation.substring(startPos, this.pos));
                } else if (ch >= 'a' && ch <= 'z') { // functions
                    while (ch >= 'a' && ch <= 'z') nextChar();
                    String func = Equation.substring(startPos, this.pos);
                    x = parseFactor();
                    x = switch (func) {
                        case "log" -> Math.log(x) ;
                        case "sqrt" -> Math.sqrt(x);
                        case "sin" -> Math.sin(x);
                        case "cos" -> Math.cos(x);
                        case "tan" -> Math.tan(x);
                        default -> throw new RuntimeException("Unknown function: " + func);
                    };
                } else {
                    throw new RuntimeException("Unexpected: " + (char) ch);
                }

                if (eat('^')) x = Math.pow(x, parseFactor()); // exponentiation

                return x;
            }
        }.parse();
    }

    private static String HandleEquals(String input){
        String[] Equation = input.split("=") ;

        return Equation[0]+"-(" +Equation[1]+")"  ;
    }
}
