
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
        //boolean que indica si el signe es unari o no ho es.
        boolean tkUnari = false;

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
            } else if (c == '+' || c == '-' || c == '*' || c == '/' || c == '^') { // n es el operador unari.

                //Se verifica si el guión está en la posición inicial de la expresión (i == 0),
                // si el último token agregado es una operación (tokens.get(tokens.size() - 1).getTtype() == Toktype.OP)
                // o si el último token agregado es un paréntesis de apertura 
                // Si se cumple alguna de estas condiciones, 
                // se agrega un token para indicar que se trata de una operación unaria (tokOp('$')),
                // se establece una variable booleana isUnary en true 
                // y se continúa con el siguiente carácter en la expresión.
                tkUnari= identificarUnari(tokens,tkUnari,c,i);

                /*
                if ( c == '-') {

                    if (i == 0) {
                        tokens.add(tokOp('$'));
                        tkUnari= true;
                        continue;
                    } else if (tokens.get(tokens.size() -1).getTtype() == Toktype.OP) {
                        tokens.add(tokOp('$'));
                        tkUnari= true;
                        continue;
                    } else if (tokens.get(tokens.size() -1).getTk() == '(') {
                        tokens.add(tokOp('$'));
                        tkUnari= true;
                        continue;
                    }
                }

                 */
                /*
                int següent = i +1;
                int anterior = i -1;
                //Si el valor negatiu esta a la primera posicio
                if (c == '-' && i == 0) {
                    tokOp('$');
                } else if (c == '-' && anterior == '(') {
                    tokOp('$');
                } else if (c == '-' && anterior == '/' || c == '-' && anterior == '*') {
                    tokOp('$');
                }

                 */


                if (!tkUnari) {
                    //Afegeix els tokens que son operadors a tokOp.
                    tokens.add(tokOp(c));
                }


               /*if (tokens.get(i).getValue() == '-') {
                    identificarUnari(tokens,expr,c, i);
                } else {
                    //Afegeix els tokens que son operadors a tokOp.
                    tokens.add(tokOp(c));
                }

                */



            } else if (c == ' ') {
                //Si te un espai hem de cambiar de token
                // ha estat una causa gran de error.
                continue;
            } else {
                throw new RuntimeException("Caracter no identificat.");
            }
        }
        // Versio antes de optimitzar el return :
        //return tokens.toArray((new Token[tokens.size()]));

        /*
        Versio mes optimitzada de el return:
        Perque esta mes optimitzada:
        el mètode toArray determina automàticament la longitud del array necessari,
         i el crea internament.
         En fer-ho, s'elimina la necessitat de crear un nou array de Token i copiar els elements de la llista,
         la qual cosa hauria de millorar el rendiment.
         */
        return tokens.toArray(new Token[0]);
    }

    private static boolean identificarUnari(ArrayList<Token> tokens, boolean tkUnari, char c , int i) {
        // hem de mirar que si el simbol esta a la primera posicio sera '$' i  unari
        // Si antes de el token actual tenim un parentesis = '$' i unari
        // Si el token actual antes tenim un altre operador de multiplicacio o divisio sera el menos sustituit per '$'


        if ( c == '-') {
            if (i == 0 || tokens.get(tokens.size() -1).getTtype() == Toktype.OP || tokens.get(tokens.size() -1).getTk() == '(') {
                tokens.add(tokOp('$'));
                return tkUnari= true;
            } else {
                //si no es cap de els casos anteriors pasa a false
               return tkUnari = false;
            }
        } else {
            //si no es un signe negatiu, no es unari
            return tkUnari = false;
        }

        /*// if (c == '-')
        int següent = i +1;
        int anterior = i -1;
        int tok = tokens.get(i).getValue();
        // hem de mirar que si el simbol esta a la primera posicio sera 'n'
        // Si antes de el token actual tenim un parentesis = 'n'
        // Si el token actual antes tenim un altre operador de multiplicacio o divisio sera el menos sustituit per 'n'

        if (tokens.get(anterior).getValue() == '(') {
            tokens.add(tokOp('$'));
        } else if (tokens.get(anterior).getValue() == '*' ) {
            tokens.add(tokOp('$'));
        } else if (tokens.get(anterior).getValue() == '/') {
            tokens.add(tokOp('$'));
        } else if (i == 0) {
            tokens.add(tokOp('$'));
        } else {
            tokens.add(tokOp('$'));
        }

         */
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
}
