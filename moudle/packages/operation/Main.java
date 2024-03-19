package operation;

import java.util.Random;

public class Main {
    public static void main(String[] args) {
        final String[] OPERATORS = {"➕", "➖", "➗", "✖"};
        Random r = new Random();
        for (int i = 0; i < 10; i++) {
            ArithmeticExpression ae = new ArithmeticExpression(true, r.nextInt(6) + 1, OPERATORS[r.nextInt(4)], 10);
            System.out.println(ae.toString() + " = " + ae.getAnswer());
        }
//        System.out.println("\'");
//        String str1 = "-1’2/3";
//        String str2 = "5";
//        System.out.println(Fraction.improperFraction(str1));
//        System.out.println(Fraction.improperFraction(str2));
//        System.out.println(Fraction.calculate(str1, str2,"➗"));
//        System.out.println(Integer.parseInt("-35"));
    }
}
