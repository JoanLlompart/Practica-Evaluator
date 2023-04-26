import java.util.ArrayList;
import java.util.List;
import java.util.Stack;


public class Evaluator {

    public static int calculate(String expr) {
        // Convertim l'string d'entrada en una llista de tokens
        //Token[] tokens = Token.getTokens(expr);
        //List<Token> tokens = new ArrayList<>();
        // Efectua el procediment per convertir la llista de tokens en notació RPN(NOTACIO POLACA INVERSA)
        //Stack<String> pila = new Stack<>();


        // Finalment, crida a calcRPN amb la nova llista de tokens i torna el resultat
        //calcRPN();
        return 0;
    }

    public static int calcRPN(Token[] list) {
        // Calcula el valor resultant d'avaluar la llista de tokens

        Stack<Integer> pila = new Stack<>();
        Token t;


        return 0;
        
    }
}


/*

public class Evaluator {

    public static int calcRPN(Token[] tokens) {
        Stack<Integer> stack = new Stack<>();

        for (Token token : tokens) {
            if (token.getTtype() == Token.Toktype.NUMBER) {
                stack.push(token.getValue());
            } else {
                int operand2 = stack.pop();
                int operand1 = stack.pop();
                int result = 0;

                switch (token.getTk()) {
                    case '+':
                        result = operand1 + operand2;
                        break;
                    case '-':
                        result = operand1 - operand2;
                        break;
                    case '*':
                        result = operand1 * operand2;
                        break;
                    case '/':
                        result = operand1 / operand2;
                        break;
                    default:
                        throw new IllegalArgumentException("Invalid operator: " + token.getTk());
                }

                stack.push(result);
            }
        }

        return stack.pop();
    }

    public static int calculate(String expr) {
        Stack<Token> operatorStack = new Stack<>();
        ArrayList<Token> outputQueue = new ArrayList<>();

        Token[] tokens = Token.getTokens(expr);

        for (Token token : tokens) {
            if (token.getTtype() == Token.Toktype.NUMBER) {
                outputQueue.add(token);
            } else if (token.getTk() == '(') {
                operatorStack.push(token);
            } else if (token.getTk() == ')') {
                while (!operatorStack.isEmpty() && operatorStack.peek().getTk() != '(') {
                    outputQueue.add(operatorStack.pop());
                }
                if (!operatorStack.isEmpty() && operatorStack.peek().getTk() != '(') {
                    throw new IllegalArgumentException("Mismatched parentheses");
                } else {
                    operatorStack.pop();
                }
            } else if (token.getTtype() == Token.Toktype.OP) {
                while (!operatorStack.isEmpty() && precedence(token) <= precedence(operatorStack.peek())) {
                    outputQueue.add(operatorStack.pop());
                }
                operatorStack.push(token);
            }
        }

        while (!operatorStack.isEmpty()) {
            outputQueue.add(operatorStack.pop());
        }

        Token[] postfixTokens = outputQueue.toArray(new Token[outputQueue.size()]);
        return calcRPN(postfixTokens);
    }

    private static int precedence(Token op) {
        switch (op.getTk()) {
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
                return 2;
            default:
                throw new IllegalArgumentException("Invalid operator: " + op.getTk());
        }
    }
}

 */