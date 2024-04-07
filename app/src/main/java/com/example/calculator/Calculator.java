package com.example.calculator;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

import java.text.DecimalFormat;

public class Calculator {

    public Calculator(){}
    public String evaluate(String expression, String operation){
        String result = "";
        switch (operation){
            case "+":
            case "-":
            case "/":
            case "*":
                break;
            case "PM":
                expression = expression + "*(-1)";
                break;
            case "SQ":
                expression = expression + "*" + expression;
                break;
            case "SQRT":
                expression = "Math.sqrt(" + expression + ")";
                break;
            case "Sin":
                expression = "Math.sin(" + expression + ")";
                break;
            case "Cos":
                expression = "Math.cos(" + expression + ")";
                break;
            default:
                break;
        }
        result = calculate(expression);
        return result;
    }


    public String calculate(String data){
        try{
            Context context = Context.enter();
            context.setOptimizationLevel(-1);
            Scriptable scriptable = context.initStandardObjects();
            String result = context.evaluateString(scriptable, data, "Javascript",1,null).toString();
            return formatResult(result);
        } catch (Exception e){
            return "Error";
        }
    }

    public String formatResult(String input){

        String result;
        Context context = Context.enter();
        context.setOptimizationLevel(-1);
        Scriptable scriptable = context.initStandardObjects();

        double number = Double.parseDouble(input);

        if (Math.abs(number) >= 1e10 || Math.abs(number) <= 1e-5){
            String expr = "var num = " + number + "; num.toExponential(2);";
            result = context.evaluateString(scriptable, expr, "Javascript",1,null).toString();
        } else {
            DecimalFormat df = new DecimalFormat("#.#####");
            result = df.format(number);
        }
        return result;
    }


}
