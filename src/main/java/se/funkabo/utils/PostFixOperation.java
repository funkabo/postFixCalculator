package se.funkabo.utils;

import java.util.*;

/**
 * ┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓
 * ┃                            Exercise 1                        ┃
 * ┃      title: Postfix Calculator                               ┃
 * ┃    version: 1.0                                              ┃
 * ┃     author: Federico Sanders <federico.sanders@hotmail.com>  ┃
 * ┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛
 */

public class PostFixOperation {

    private PostFixOperation() {}


	private static Map<String, Operators> OPERATOR = new HashMap<String, Operators>() {{
        put("+", Operators.ADDITION);
        put("-", Operators.SUBTRACTION);
        put("*", Operators.MULTIPLICATION);
        put("/", Operators.DIVISION);
    }};

    public static void run(String[] a){
        String[] postFixExpression = toPostFix(a);
        int result = resolvePostFix(postFixExpression);
        System.out.print("\tRESULT: " + result + "\n\n");
    }

    private static int resolvePostFix(String[] input) {

        Stack<Integer> result = new Stack<>();

        for(int i = 0; i <input.length; i++){
            if(isOperator(input[i])){
                int operandOne = result.pop();
                int operandTwo = result.pop();
                switch(input[i]){
                    case "+": result.push(operandTwo + operandOne);
                        break;
                    case "-": result.push(operandTwo - operandOne);
                        break;
                    case "*": result.push(operandTwo * operandOne);
                        break;
                    case "/": result.push(operandTwo / operandOne);
                        break;
                }
            } else {
                int operandThree = Integer.parseInt(input[i]);
                result.push(operandThree);
            }
        }
        return result.pop();
    }

    private static String[] toPostFix(String[] input){

        final Stack<String> postFixResult = new Stack<>();
        final Stack<String> operatorStack = new Stack<>();

        for(String index : input) {
            if(isOperator(index)){
                while (!operatorStack.isEmpty() && hasPrecedence(index, operatorStack.peek()))
                    postFixResult.add(operatorStack.pop());
                operatorStack.push(index);
            } else if(isLeftParenthesis(index)){
                operatorStack.push(index);
            } else if (isRightParenthesis(index)){
                while (!operatorStack.peek().equals("("))
                    postFixResult.add(operatorStack.pop());
                operatorStack.pop();
            } else {
                postFixResult.add(index);
            }
        }
        while (!operatorStack.isEmpty())
            postFixResult.add(operatorStack.pop());
        return postFixResult.stream().toArray(String[]::new);
    }

    private static boolean isOperator(String index){
        return !isNull(index) && OPERATOR.containsKey(index);
    }

    private static boolean isLeftParenthesis(String index){
        return !isNull(index) && index.equals("(");
    }

    private static boolean isRightParenthesis(String index){
        return !isNull(index) && index.equals(")");
    }

    private static boolean hasPrecedence(String index, String stack){
        return OPERATOR.containsKey(stack) && OPERATOR.get(stack).precedence >= OPERATOR.get(index).precedence;
    }

    private static boolean isNull(String index){
        return index.length() == 0;
    }

}
