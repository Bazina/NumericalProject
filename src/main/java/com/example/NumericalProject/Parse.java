package com.example.NumericalProject;

import java.math.BigDecimal;
import java.util.*;

public class Parse {
    public static void main(String[] args) {
        Map<String, ArrayList<BigDecimal>> linearEqn = new HashMap<>();
        Set<String> variablesSet = new HashSet<>();
        String[] Eqns = new String[]{"-6x - 4y-9z=0", "3y - 6z = -9", "3x + 7y - 100z = 55"};
        for (String Eqn : Eqns) {
            var y = Eqn.split("[0-9+-= ]+");
            for (String x : y) {
                if (!Objects.equals(x, "")) {
                    variablesSet.add(x);
                }
            }
        }
        ArrayList<BigDecimal> B = new ArrayList<>();
        for (String Eqn : Eqns) {
            if (Eqn.contains("-")) Eqn = EqnHandler(Eqn);
            for (String variable : variablesSet) {
                if (!Eqn.contains(variable)) {
                    ArrayList<BigDecimal> old = new ArrayList<>();
                    if (linearEqn.get(variable) != null) old = linearEqn.get(variable);
                    old.add(BigDecimal.valueOf(0));
                }
            }
            String[] newEqn = Eqn.split(" \\+ | = ");
            B.add(BigDecimal.valueOf(Double.parseDouble(newEqn[newEqn.length - 1])));
            linearEqn.put("B", B);
            for (String value : newEqn) {
                String[] newValue = value.split("(?=\\D)(?<=\\d)");
                String variable;
                BigDecimal num;

                if (newValue.length == 1 && !newValue[0].matches("[a-zA-Z]")) continue;
                else if (newValue.length != 1) {
                    variable = newValue[1];
                    if (newValue[0].equals("-")) num = BigDecimal.valueOf(-1);
                    else num = BigDecimal.valueOf(Double.parseDouble(newValue[0]));
                } else {
                    variable = newValue[0];
                    num = BigDecimal.valueOf(1);
                }

                ArrayList<BigDecimal> old = new ArrayList<>();
                if (linearEqn.get(variable) != null) old = linearEqn.get(variable);
                old.add(num);
                linearEqn.put(variable, old);
            }
        }
        for (Map.Entry<String, ArrayList<BigDecimal>> entry : linearEqn.entrySet()) {
            ArrayList<BigDecimal> nums = entry.getValue();
            System.out.println(entry.getKey());
            for (BigDecimal num : nums) {
                System.out.println(num);
            }
        }
    }

    public static String EqnHandler(String x) {
        if (x.charAt(0) == '-' && Character.isAlphabetic(x.charAt(1))) x = "-1" + x.substring(1);
        if (!x.substring(1).split(" = ")[0].contains("-")) return x;
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
                i += 4;
            }
        }
        return x;
    }
}

