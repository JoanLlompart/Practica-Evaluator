import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;


public class Evaluator {

    public static int calculate(String expr) {

        // Llista de tokens de sortida (output)
        ArrayList<Token> sortida = new ArrayList<>();

        // Pila (Stack) d'operandor.
        Stack<Token> operandorStack = new Stack<>();

        // Convertim l'string d'entrada en una llista de tokens
        Token[] tokens = Token.getTokens(expr);

        // Efectua el procediment per convertir la llista de tokens en notació RPN(NOTACIO POLACA INVERSA)
        for (Token t:tokens) {

            if (t.getTtype()== Token.Toktype.NUMBER) {

                //afegim el nombre a la cua de sortida
                sortida.add(t);
            } else if (t.getTtype() == Token.Toktype.OP ) {
                while (!operandorStack.isEmpty() && preferencia(t) <= preferencia(operandorStack.peek()) ) {
                    // Afegim a sortida el operador amb el metode pop
                    // que extreu el operador que esta al cap i el elimina.
                    sortida.add(operandorStack.pop());
                }
                //si no hi ha cap operador a el stack
                //operador enviat a el stack operadorStack si
                operandorStack.push(t);

            } else if (t.getTtype()== Token.Toktype.PAREN) {
                //Entra si es un parentesis, es a dir,Toktype de tipus PAREN.
                parentFunction(t,operandorStack,sortida);
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

    private static void parentFunction(Token t, Stack<Token> operandorStack, ArrayList<Token> sortida) {
        //Si es una obertura de parentesis
        if (t.getTk() == '(') {
            //afegim el parentesis a el Stack
            operandorStack.push(t);
        } else if (t.getTk() == ')') {
            //Si el parentesis tanca , hem de treure operadors fins que
            // trobam al cap un parentesis que el tanca(esquerra)
            // i fins que no hi ha operadors en operadorStack
            while (t.getTk() != '(' && !operandorStack.isEmpty()) {
                //variable caDePila que guarda el proxim token de el Stack
                char capDePila = operandorStack.peek().getTk();
                //Si el Stack te dos o mes elements podrem mirar el token que tenim a la segona posicio de
                // el cap. Per poder eliminar valos i tornalos a ficar
                if (operandorStack.size() >= 2) {
                    operandorStack.get(1).getTk();
                }
                //si el proxim token es un parentesis  de obertura el eliminarem
                if (capDePila == '(' || capDePila == ')') {
                    operandorStack.pop();
                } else {
                    // de el contrari agafam el token i el pasam a el ArrayList de sortida.
                    sortida.add(operandorStack.pop());
                }
            }

        }
    }

    private static int preferencia(Token op) {
        //switch amb preferencias per el operadors
        //Com mes gran es el nombre de retorn mes preferencia tendra.
        switch (op.getTk()) {
            // sumes i restes tenen la preferencia mes baixa
            case '+','-':
                return 1;
            //MultiplicacioI  divisio tenen preferencia de valor 2
            case '*','/':
                return 2;
            //els elevats tenen preferencia de valor 3.
            case '^':
                return 3;

            //Els parentesis com se han de eliminar tenen valor de preferencia 0 perque agafi el altre
            case '(',')':
                return 0;
            //El $ te la maxima preferencia i ja que se ha de asignar el valor negatiu.
            case '$' :
                return 4;


            default:
                throw new RuntimeException("No se ha pogut determinar la preferencia perque el operador no es valid");
        }

    }

    public static int calcRPN(Token[] list) {
        // Calcula el valor resultant d'avaluar la llista de tokens
        // Stack de Integer per realitzar les operacions de Notacio Polaca Inversa
        Stack<Integer> pila = new Stack<>();

        //Variable per anar guardant el resultat
        int res = 0;
        int tempPila;
        for (Token t: list) {

            if (pila.isEmpty() && t.getTtype() == Token.Toktype.OP && t.getTk() != '$') {
                throw new IllegalArgumentException("Falta operador per el operand");
            }
            if (pila.size() == 1 && t.getTtype() == Token.Toktype.OP) {
                //tempPila guarda el numero que queda a la pila i elimina el valor de la pila.
                tempPila =pila.pop();
                //amb la pila buida afegim el element de OP en aquest cas nomes pot ser (-+)
                if (t.getTk() == '$') {
                    if (pila.isEmpty()) {
                        // si la pila esta buida
                        //return res = tempPila * -1;
                        res = tempPila * -1;
                        //torna a afegir el element que eliminam amb el pop (mirar si amb peek puc eliminar aixo)
                        pila.push(tempPila * -1);
                        pila.push(res);
                        continue;

                    } else {
                        pila.pop();
                        res = tempPila * -1;
                        pila.add(res);

                    }
                }

            }
            if (t.getTtype() == Token.Toktype.NUMBER) {
                //afegim directament el valor a la pila
                pila.push(t.getValue());
            } else if (t.getTk() == '$' && pila.size() >1) {
                int elDret = pila.pop();
                //pasam el numero a negatiu multiplicantlo per menys un.
                pila.push(elDret * -1);

            } else {
                //Si no es un NUMBER, es de tipus operador.
                //Amb pop() treim el element de amunt de la pila i el guada a la variable elDret/Esq.
                int elDret = pila.pop();
                if (pila.isEmpty()) {
                    // res guarda el resultat de calOper,
                    // que se encarrega de transformar RPN a normal i calcula el resultat.
                    res += elDret;
                } else {
                    int elEsq = pila.pop();
                    // res guarda el resultat de calOper,
                    // que se encarrega de transformar RPN a normal i calcula el resultat.
                    res = calcOper(t,res,elEsq,elDret);
                }

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
            //revisar si res elimina el valor restant en el cas de 3
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