package operation;

public class Fraction {
    private Fraction() {
    }

    //将分数转化为假分数，整数会变成该整数除以 1
    public static String improperFraction(String proper) {
        if (!proper.contains("’")) return proper + "/" + "1";
        String[] strings = proper.split("[’/]");
        int integer, numerator, denominator;
        integer = Integer.parseInt(strings[0]);
        if (strings.length <= 2) return String.valueOf(integer);
        numerator = Integer.parseInt(strings[1]);
        denominator = Integer.parseInt(strings[2]);
        numerator = (numerator + Math.abs(integer) * denominator) * (integer / Math.abs(integer));
        return String.valueOf(numerator) + "/" + String.valueOf(denominator);
    }

    //将假分数转化为真分数
    public static String properFraction(String improper) {
        if (!improper.contains("/")) return improper;
        String[] strings = improper.split("/");
        int integer = 1, numerator, denominator;
        numerator = Integer.parseInt(strings[0]);
        denominator = Integer.parseInt(strings[1]);
        if (numerator < 0) {
            integer = -1;
            numerator = Math.abs(numerator);
        }
        if (numerator < denominator) return improper;
        if (numerator % denominator == 0) return String.valueOf(numerator / denominator);
        integer = numerator / denominator * integer;
        numerator %= denominator;
        return String.valueOf(integer) + "’" + String.valueOf(numerator) + "/" + String.valueOf(denominator);
    }

    //分数计算
    public static String calculate(String fraction1, String fraction2, String operator) {
        int numerator1 = getNumerator(fraction1);
        int numerator2 = getNumerator(fraction2);
        int denominator1 = getDenominator(fraction1);
        int denominator2 = getDenominator(fraction2);
        if (operator.equals("➕")) {
            int l = lcm(denominator1, denominator2);
            numerator1 = l / denominator1 * numerator1;
            numerator2 = l / denominator2 * numerator2;
            return properFraction(rof(numerator1 + numerator2, l));
        }
        if (operator.equals("➖")) {
            int l = lcm(denominator1, denominator2);
            numerator1 = l / denominator1 * numerator1;
            numerator2 = l / denominator2 * numerator2;
            return properFraction(rof(numerator1 - numerator2, l));
        }
        if (operator.equals("✖")) {
            return properFraction(rof(numerator1 * numerator2, denominator1 * denominator2));
        }
        if (operator.equals("➗")) {
            return properFraction(rof(numerator1 * denominator2, numerator2 * denominator1));
        }
        return null;
    }

    //对假分数约分
    public static String rof(int numerator, int denominator) {
        int g = gcd(numerator, denominator);
        return String.valueOf(numerator / g) + "/" + String.valueOf(denominator / g);
    }

    //获取一个分数的分子，真分数会转化为假分数
    public static int getNumerator(String str) {
        str = improperFraction(str);
        return Integer.parseInt(str.split("/")[0]);
    }

    //获取一个分数的分母，真分数会转化为假分数,若为整数，分母为 1
    public static int getDenominator(String str) {
        str = improperFraction(str);
        return Integer.parseInt(str.split("/")[1]);
    }

    //求最大公约数
    public static int gcd(int a, int b) {//调用函数递归 更相减损法 简易写法
        if (a < 0) a = Math.abs(a);
        if (b < 0) b = Math.abs(b);
        if (a < b) {
            int tmp = a;
            a = b;
            b = tmp;
        }
        return (a % b == 0) ? b : gcd(a - b, b);
    }

    //求最小公倍数
    public static int lcm(int a, int b) {
        int c = gcd(a, b);
        return a / c * b;
    }
}
