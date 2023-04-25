
public class Evaluator {

    public static int calculate(String expr) {
        // Convertim l'string d'entrada en una llista de tokens
        Token[] tokens = Token.getTokens(expr);
        // Efectua el procediment per convertir la llista de tokens en notació RPN
        // Finalment, crida a calcRPN amb la nova llista de tokens i torna el resultat
        return 0;
    }

    public static int calcRPN(Token[] list) {
        // Calcula el valor resultant d'avaluar la llista de tokens
        return 0;
        
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