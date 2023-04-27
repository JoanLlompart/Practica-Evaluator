
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
        t.ttype = Toktype.PAREN;
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
        if (this == o) return true;
        if (!(o instanceof Token)) return false;
        Token token = (Token) o;
        return ttype == token.ttype && value == token.value && tk == token.tk;
    }

    /*
    public boolean equals(Object o) {
            //Lo guardamos en token other
            Token other =  o;
           if (this.ttype != other.ttype) {
               return false;
           }
           if (this.ttype == Toktype.OP) {
               return this.tk == other.tk;
           }
           if (this.ttype == Toktype.PAREN) {
               return this.tk == other.tk;
           }
           if (this.ttype == Toktype.NUMBER) {
               return this.value == other.value;
           }
        return false;

            //Y devolvemos true si todos los atributos son iguales
            //return this.ttype == other.ttype && this.value == other.value && this.tk == other.tk;



    }

     */

    // A partir d'un String, torna una llista de tokens
    public static Token[] getTokens(String expr) {
        //Elimina els espais en blanc de el String
        //expr = expr.replace(" ","");
        ArrayList<Token> tokens = new ArrayList<>();

        String number;

        for (int i = 0; i < expr.length(); i++) {
            char c = expr.charAt(i);
            //int asciiValue = (int) c;
            boolean caracter = Character.isDigit(c);
            //Reiniciam la variable number per poder guardar el proxim nombre sencer
            number = "";


            if (caracter) {
                // SI es un DIGIT juntar concatenar a un String temporal,
                // Una vegada tenim tots els Digits consecutius  els pasam a TokNumber i
                // despres ficar a una posicio de el ArrayList


                String[] arTemp = afegirNumeros(caracter, i, number, c, expr);
                i = Integer.parseInt(arTemp[1]);
                number = arTemp[0];


                //Anteriorment a la funcio afegirNumeros li hem incrementat per mirar la seguent
                //posicio i ara li decrementam, per no saltar caracters.
                i--;
                // Afegim a el la llista tokens el Token de tipo número
                tokens.add(tokNumber(Integer.parseInt(number)));

            } else if (c == '(' || c == ')') {
                //guardaDigits(number,tokens);
                tokens.add(tokParen(c));
                //reinicia la variable number.
            } else if (c == '+' || c == '-' || c == '*' || c == '/' || c == '^') {
                //crear tokOp
                tokens.add(tokOp(c));
            } else if (c == ' ') {
                //Si te un espai hem de cambiar de token
                // ha estat una causa gran de error.
                continue;
            } else {
                throw new RuntimeException("Caracter no identificat.");
            }

        }
        return tokens.toArray(tokens.toArray(new Token[tokens.size()]));
    }

    private static String[] afegirNumeros(boolean caracter, int i, String number, char c, String expr) {
        //Menter caracter sigui True continua iterant el bucle.
        while (Character.isDigit(expr.charAt(i))) {
            //Afegim el caracter.
            number += c;
            //Increment de i per mirar si el següent number char es un digit.
            i++;
            if (i < expr.length()) {
                c = expr.charAt(i);
            } else {
                //Sortida de el bucle
                break;
            }
        }
        return new String[]{number, String.valueOf(i)};
    }

    private static void guardaDigits(String number, ArrayList<Token> tokens) {
        tokens.add(Token.tokNumber(number.charAt(Integer.parseInt(number))));
    }


}

