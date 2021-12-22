package com.example.NumericalProject;

import java.math.BigDecimal;
import java.util.*;

/***
 * A class that parse the provided equations from the user
 */
public class Parse {

    /***
     * A method that turns the linear equations into coefficients
     * @param Input system of linear equations
     * @return hash map for the coefficients
     */
    public static Map<String, ArrayList<BigDecimal>> ToEquations(String[] Input) {
        // Strip all the spaces in the start and end of the string
        for (int i = 0; i < Input.length ; i++) {
            Input[i] = Input[i].strip() ;
        }

        Map<String, ArrayList<BigDecimal>> linearEqn = new HashMap<>();
        Set<String> variablesSet = new HashSet<>();

        // Get the variable names from the system
        for (int i = 0; i < Input.length; i++) {
            Input[i] = EqnHandler(Input[i]);
            String[] Eqn = Input[i].split(" \\+ | = |-");
            for (String s : Eqn) {
                var y = s.split("(?=\\D)(?<=\\d)");
                for (String x : y) {
                    if (x.matches(".*[a-zA-Z]+.*")) {
                        variablesSet.add(x);
                    }
                }
            }
        }
        ArrayList<BigDecimal> B = new ArrayList<>();

        // Loop on the equations and parse it
        for (String Eqn : Input) {
            ArrayList<BigDecimal> old;

            // Put the coefficient with 0 if the variable not in this equations
            for (String variable : variablesSet) {
                if (!Eqn.contains(variable)) {
                    if (linearEqn.get(variable) != null) old = linearEqn.get(variable);
                    else {
                        old = new ArrayList<>();
                        linearEqn.put(variable, old);
                    }
                    old.add(BigDecimal.valueOf(0));
                }
            }

            // Split the equation with + and =
            String[] newEqn = Eqn.split(" \\+ | = ");

            // Put the constants in B vector
            B.add(BigDecimal.valueOf(Double.parseDouble(newEqn[newEqn.length - 1])));
            linearEqn.put("B", B);

            // Put the coefficients of the variable in its array
            for (String value : newEqn) {
                String[] newValue = value.split("(?=\\D)(?<=\\d)");
                String variable;
                BigDecimal num;

                if (newValue.length == 1 && !newValue[0].matches(".*[a-zA-Z]+.*")) continue;
                else if (newValue.length != 1) {
                    variable = newValue[1];
                    if (newValue[0].equals("-")) num = BigDecimal.valueOf(-1);
                    else num = BigDecimal.valueOf(Double.parseDouble(newValue[0]));
                } else if (newValue[0].length() == 2 && newValue[0].contains("-")) {
                    variable = String.valueOf(newValue[0].charAt(1));
                    num = BigDecimal.valueOf(-1);
                } else {
                    variable = newValue[0];
                    num = BigDecimal.valueOf(1);
                }

                if (linearEqn.get(variable) != null) old = linearEqn.get(variable);
                else {
                    old = new ArrayList<>();
                    linearEqn.put(variable, old);
                }
                old.add(num);
            }
        }
        return linearEqn;
    }

    /***
     * Put the equation in a general form for the parser
     * @param x the equation string
     * @return the equation in the general form
     */
    private static String EqnHandler(String x) {
        if (x.charAt(0) == '-' && Character.isAlphabetic(x.charAt(1))) x = "-1" + x.substring(1);
        for (int i = 1; i < x.length(); i++) {
            if (x.charAt(i) == '+' || x.charAt(i) == '-') {
                if (x.charAt(i + 1) != ' ') x = x.substring(0, i + 1) + " " + x.substring(i + 1);
                if (x.charAt(i - 1) != ' ') x = x.substring(0, i) + " " + x.substring(i);
            }
            if (x.charAt(i) == '=') {
                if (x.charAt(i + 1) != ' ') x = x.substring(0, i + 1) + " " + x.substring(i + 1);
                if (x.charAt(i - 1) != ' ') x = x.substring(0, i) + " " + x.substring(i);
                break;
            }
            if (x.charAt(i) == '-') {
                x = x.substring(0, i - 1) + " + -" + x.substring(i + 2);
                i += 3;
            }
        }
        return x;
    }
}

