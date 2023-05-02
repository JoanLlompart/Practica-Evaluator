public class Provas {
    public static void main(String[] args) {
        String expr1 = "01234swjdi56789";
        for (int i = 0; i < expr1.length(); i++) {
            char c = expr1.charAt(i);
            //int asciiValue = (int) c;
            boolean caracter = Character.isDigit(c);
            System.out.println(caracter);
        }
    }
}
/*

 */
