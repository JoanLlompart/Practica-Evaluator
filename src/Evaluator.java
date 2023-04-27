import java.util.ArrayList;
import java.util.Stack;


public class Evaluator {

    public static int calculate(String expr) {

        // Llista de tokens de sortida (output)
        ArrayList<Token> sortida = new ArrayList<>();

        // Pila (Stack) d'operandor.
        Stack<Token> operandorStack = new Stack<>();

        // Convertim l'string d'entrada en una llista de tokens
        Token[] tokens = Token.getTokens(expr);

        //char temp guardara si hi ha un parentesis obert
        char temp;

        //Guarderem els parentesis en una llista aposta
        Stack<Token> parenStack = new Stack<>();

        // Efectua el procediment per convertir la llista de tokens en notació RPN(NOTACIO POLACA INVERSA)
        for (Token t:tokens) {
            if (t.getTtype()== Token.Toktype.NUMBER) {
                //afegim el nombre a la cua de sortida
                sortida.add(t);
            } else if (t.getTtype()== Token.Toktype.OP ) {
                while (!operandorStack.isEmpty() && preferencia(t) <= preferencia(operandorStack.peek())) {
                    // Afegim a sortida el operador amb el metode pop
                    // que extreu el operador que esta al cap i el elimina.

                    if (operandorStack.peek().getTtype() == Token.Toktype.OP) {
                        if (operandorStack.peek().getTtype() == Token.Toktype.PAREN ) {
                            //Si el proxim token de el cap de el Stack NO es un parentesis, fica el operador a sortida
                            //sortida.add(operandorStack.pop());
                            temp = operandorStack.pop().getTk(); //temp guarda el parentesis per ficarlo despres.
                            sortida.add(operandorStack.pop());
                            operandorStack.push(Token.tokParen(temp));
                        }


                    }

                    sortida.add(operandorStack.pop());

                }
                //si no hi ha cap operador a el stack
                //operador enviat a el stack operadorStack si
                operandorStack.push(t);
            } else if (t.getTtype()== Token.Toktype.PAREN) {
                //Entra si es un parentesis.
                //Si es una obertura de parentesis
                if (t.equals('(')) {
                    //afegim el parentesis a el Stack
                    operandorStack.push(t);
                } else if (t.equals(')')) {
                    //Si el parentesis tanca , hem de treure operadors fins que
                    // trobam al cap un parentesis que el tanca(esquerra)
                    // i fins que no hi ha operadors en operadorStack
                    while (!t.equals('(') && !operandorStack.isEmpty()) {
                        sortida.add(operandorStack.pop());
                    }

                    // Si trobam un parentesis a la esquerra,
                    //treu el parèntesi esquerra ('(') de la pila d'operadors
                    if (t.equals('(')) operandorStack.pop();

                }

            }


        }
        //Una vegada ha acabat el bucle no hi ha mes Tokens de entrada
        // Bucle while que combroba que el Stack de operador hi ha operadors i les afegeix a la sortida.
        while (!operandorStack.isEmpty()) {
            //pop agafa el primer valor de el Stack i
            // el pasa a el final de sortida
            sortida.add(operandorStack.pop());
        }
        // Finalment, crida a calcRPN amb la nova llista de tokens i torna el resultat
        Token[] list = sortida.toArray(new Token[sortida.size()]);

        //Variable que agafa el valor de el calcul de la funcio calcRPN.
        int result =calcRPN(list);

        return result;
    }

    private static int preferencia(Token op) {
        //switch amb preferencias per el operadors
        //Com mes gran es el nombre de retorn mes preferencia tendra.
        switch (op.getTk()) {
            // sumes i restes tenen la preferencia mes baixa
            case '+','-':
                return 1;

                //Multiplicacio divisio i porcentatge tenen preferencia de valor 2
            case '*','%','/':
                return 2;
                //els elevats tenen preferencia de valor 3.
            case '^':
                return 3;
            case '(',')':
                return 4;
            default:
                throw new RuntimeException("No se ha pogut determinar la preferencia perque el operador no es valid");
        }

    }

    public static int calcRPN(Token[] list) {
        // Calcula el valor resultant d'avaluar la llista de tokens

        Stack<Integer> pila = new Stack<>();

        for (Token t: list) {
            if (t.getTtype() == Token.Toktype.NUMBER) {
                pila.push(t.getValue());
            } else {
                //Si no es un NUMBER, es de tipus operador.
                //Amb pop() treim el element de amunt de la pila i el guada a la variable elDret/Esq.
                int elDret = pila.pop();
                int elEsq = pila.pop();
                //Variable per anar guardant el resultat
                int res = 0;

                // res guarda el resultat de calOper,
                // que se encarrega de transformar RPN a normal i calcula el resultat.
                res = calcOper(t,res,elEsq,elDret);
                //Realitza push a la pila del valor resultat
                pila.push(res);
            }
        }
        //Treu els valors que queden a la pila que sera el resultat.
        return pila.pop();
    }

    private static int calcOper(Token t, int res, int elEsq, int elDret) {
        switch (t.getTk()) {
            case '+':
                //realitza la suma de forma normal
                res = elEsq + elDret;
                break;
            case '-':
                res = elEsq - elDret;
                break;
            case '*':
                res = elEsq * elDret;
                break;
            case '/':
                res = elEsq / elDret;
                break;
            case '^':
                //Math pow retorna un double per aixo mateix feim el cast a int
                res = (int) Math.pow(elEsq,elDret);
                break;
            default:
                throw new RuntimeException("Operador no reconegut");
        }
        return res;
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