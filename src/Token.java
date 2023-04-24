
import java.util.ArrayList;

import java.util.regex.Matcher;

public class Token {
    enum Toktype {
        NUMBER, OP, PAREN
    }

    // Pensa a implementar els "getters" d'aquests atributs.
    private Toktype ttype; //opercio,parentesis,numero
    private int value;
    private char tk;

    public Toktype getTtype() {
        return ttype;
    }

    public int getValue() {
        return value;
    }

    public char getTk() {
        return tk;
    }

    // Constructor privat. Evita que es puguin construir objectes Token externament
    private Token() {

    }

    // Torna un token de tipus "NUMBER"
    static Token tokNumber(int value) {
        Token t = new Token();
        t.ttype = Toktype.NUMBER;
        t.value = value;
        return t;
    }

    // Torna un token de tipus "OP"
    static Token tokOp(char c) {
        //Toktype op = Token.tokOp(c);
        //return new Token(Toktype.OP,Character.toString(c));
        Token t = new Token();
        t.ttype = Toktype.OP;
        t.tk = c;
        return t;
    }

    // Torna un token de tipus "PAREN"
    static Token tokParen(char c) {
        Token t = new Token();
        t.ttype = Toktype.OP;
        t.tk = c;
        return t;
    }

    // Mostra un token (conversió a String)
    public String toString() {
        if (ttype == Toktype.NUMBER) return String.valueOf(value);
        return String.valueOf(tk);
    }

    // Mètode equals. Comprova si dos objectes Token són iguals
    public boolean equals(Object o) {
        if (o instanceof Token) {
            //Lo guardamos en token other
            Token other = (Token) o;
            //Y devolvemos true si todos los atributos son iguales
            return this.ttype == other.ttype && this.value == other.value && this.tk == other.tk;
        }
        return false;
    }

    // A partir d'un String, torna una llista de tokens
    public static Token[] getTokens(String expr) {
        ArrayList<Token> tokens = new ArrayList<>();
        /*
        for (int i = 0; i < expr.length(); i++) {
            char c = expr.charAt(i);
            if (Character.isDigit(c)) {
                //si es un numeero etrara
            }
         */

        String number = "";

        for (int i = 0; i < expr.length(); i++) {
            char c = expr.charAt(i);
            //int asciiValue = (int) c;
            boolean caracter = Character.isDigit(c);
            System.out.println(caracter);

            if (caracter) {
                // SI es un DIGIT juntar concatenar a un String temporal,
                // Una vegada tenim tots els Digits consecutius  els pasam a TokNumber i
                // despres ficar a una posicio de el ArrayList
               number += c;
                // Token de tipo número
               tokens.add(Token.tokNumber(Integer.parseInt(number)));
            }else if (c == '(' || c ==')') {
                number = "";
                //guardaDigits(number,tokens);
                tokens.add(tokParen(c));
                //reinicia la variable number.
            } else if (c == '+' || c == '-' || c == '*' || c == '/') {
                //crear tokOp
                tokens.add(tokOp(c));
            } else {
                throw new RuntimeException("Caracter no identificat.");
            }

        }
        return tokens.toArray(tokens.toArray(new Token[tokens.size()]));
    }

    private static void guardaDigits(String number, ArrayList<Token> tokens) {
        tokens.add(Token.tokNumber(number.charAt(Integer.parseInt(number))));
    }


}

