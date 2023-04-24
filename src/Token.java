
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
        Token t = Token.tokNumber(value);
        return t;
    }

    // Torna un token de tipus "OP"
    static Token tokOp(char c) {
        //Toktype op = Token.tokOp(c);
        return null;
    }

    // Torna un token de tipus "PAREN"
    static Token tokParen(char c) {
        return null;
    }

    // Mostra un token (conversió a String)
    public String toString() {
        return "";
    }

    // Mètode equals. Comprova si dos objectes Token són iguals
    public boolean equals(Object o) {

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
            }

            if (!caracter) {
                guardaDigits(number,tokens);
                number="";
            }


        }

        return null;
    }

    private static void guardaDigits(String number, ArrayList<Token> tokens) {
        tokens.add(Token.tokNumber(number.charAt(Integer.parseInt(number))));
    }


}


/*

public class Token {
    private enum TokenType {tokNumber, tokOps, tokParen};

    private TokenType type;
    private String value;

    // Constructor privado. Evita que se pueda instanciar.
    private Token(TokenType type, String value) {
        this.type = type;
        this.value = value;
    }

    // Factory method: Torna un token de tipus número
    public static Token tokNumber(int value) {
        return new Token(TokenType.tokNumber, Integer.toString(value));
    }

    // Factory method: Torna un token de tipus operador
    public static Token tokOp(char op) {
        return new Token(TokenType.tokOps, Character.toString(op));
    }

    // Factory method: Torna un token de tipus parèntesi
    public static Token tokParen(char paren) {
        return new Token(TokenType.tokParen, Character.toString(paren));
    }

    // Retorna una representació String de l'objecte Token
    public String toString() {
        return value;
    }

    // Comprova si dos objectes Token són iguals
    public boolean equals(Object o) {
        if (!(o instanceof Token)) {
            return false;
        }
        Token otherToken = (Token) o;
        return this.type == otherToken.type && this.value.equals(otherToken.value);
    }

    // Separa un String en Tokens
    public static Token[] getTokens(String s) {
        List<Token> tokens = new ArrayList<>();
        int i = 0;
        while (i < s.length()) {
            char c = s.charAt(i);
            if (Character.isDigit(c)) {
                // Token de tipo número
                int start = i;
                while (i < s.length() && Character.isDigit(s.charAt(i))) {
                    i++;
                }
                int end = i;
                String number = s.substring(start, end);
                tokens.add(tokNumber(Integer.parseInt(number)));
            } else if (c == '+' || c == '-' || c == '*' || c == '/') {
                // Token de tipo operador
                tokens.add(tokOp(c));
                i++;
            } else if (c == '(' || c == ')') {
                // Token de tipo paréntesis
                tokens.add(tokParen(c));
                i++;
            } else {
                // Carácter desconocido
                i++;
            }
        }
        return tokens.toArray(new Token[tokens.size()]);
    }
}

 */