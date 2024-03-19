package operation;

public class Main {
    public static void main(String[] args) {
//        ArithmeticExpression ae = new ArithmeticExpression(true,8,"➖",10);
//        System.out.println(ae.toString());
//        System.out.println("\'");
        String str1 = "7’1/5";
        String str2 = "5’1/5";
        System.out.println(Fraction.improperFraction(str1));
        System.out.println(Fraction.improperFraction(str2));
        System.out.println(Fraction.calculate(str1, str2,"➗"));
//        System.out.println(Integer.parseInt("-35"));
    }
}
