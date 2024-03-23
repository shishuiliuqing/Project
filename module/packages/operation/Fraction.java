package operation;

public class Fraction {
    private Fraction() {
    }
    //判断一个分数是否小于0
    public static boolean isLessThenZero(String fraction) {
        if (fraction == null) return true;
        fraction = improperFraction(fraction);
        return getNumerator(fraction) < 0;
    }

    //将分数转化为假分数，整数会变成该整数除以 1
    public static String improperFraction(String proper) {
        //无分号时
        if (!proper.contains("’")) {
            //纯整数
            if (!proper.contains("/")) return proper + "/" + "1";
            //已满足 分子/分母 形式
            return proper;
        }
        //按’ / 把一个真分数拆成3段
        String[] strings = proper.split("[’/]");
        //整数部分，分子，分母
        long integer, numerator, denominator;
        //整数
        integer = Integer.parseInt(strings[0]);
        //分子
        numerator = Integer.parseInt(strings[1]);
        //分母
        denominator = Integer.parseInt(strings[2]);
        //整数为 0 , 直接返回分子/分母
        if (integer == 0) return numerator + "/" + denominator;
        //假分数分子
        numerator = (numerator + Math.abs(integer) * denominator) * (integer / Math.abs(integer));
        //分子分母拼接
        return numerator + "/" + denominator;
    }

    //将假分数转化为真分数
    public static String properFraction(String improper) {
        //improper是整数
        if (!improper.contains("/")) return improper;
        //将improper分为分子和分母
        String[] strings = improper.split("/");
        //定义整数，分子，分母
        int integer = 1, numerator, denominator;
        //分子
        numerator = Integer.parseInt(strings[0]);
        //分母
        denominator = Integer.parseInt(strings[1]);
        //分母不能为 0
        if (denominator == 0) return null;
        //将分母的负号移至分子
        if (denominator < 0) {
            numerator *= -1;
            denominator *= -1;
        }
        //分子小于分母时无整数
        if (Math.abs(numerator) < denominator) return numerator + "/" + denominator;
        //将分子的负号移至整数
        if (numerator < 0) {
            integer = -1;
            numerator = Math.abs(numerator);
        }
        //分子是分母的倍数时只有整数
        if (numerator % denominator == 0) return String.valueOf(numerator * integer / denominator);
        //整数部分
        integer = numerator / denominator * integer;
        //分子
        numerator %= denominator;
        //拼接
        return integer + "’" + numerator + "/" + denominator;
    }

    //分数计算
    public static String calculate(String fraction1, String fraction2, String operator) {
        if (fraction1 == null || fraction2 == null) return null;
        //取分子
        long numerator1 = getNumerator(fraction1);
        long numerator2 = getNumerator(fraction2);
        //取分母
        long denominator1 = getDenominator(fraction1);
        long denominator2 = getDenominator(fraction2);
        //分数加
        if (operator.equals(ArithmeticExpression.OPERATORS[0])) {
            if (numerator1 == 0) return fraction2;
            if (numerator2 == 0) return fraction1;
            //取分母最小公倍数
            long l = lcm(denominator1, denominator2);
            numerator1 = l / denominator1 * numerator1;
            numerator2 = l / denominator2 * numerator2;
            if (numerator1 + numerator2 == 0) return "0";
            //rpf(int numerator,int denominator)约分
            return properFraction(rof(numerator1 + numerator2, l));
        }
        //分数减
        if (operator.equals(ArithmeticExpression.OPERATORS[1])) {
            if (numerator1 == 0)
                //返回相反数
                return properFraction(numerator2 * (-1) + "/" + denominator2);
            if (numerator2 == 0) return fraction1;
            //取分母最小公倍数
            long l = lcm(denominator1, denominator2);
            numerator1 = l / denominator1 * numerator1;
            numerator2 = l / denominator2 * numerator2;
            if (numerator1 - numerator2 == 0) return "0";
            //rpf(int numerator,int denominator)约分
            return properFraction(rof(numerator1 - numerator2, l));
        }
        //分数乘
        if (operator.equals(ArithmeticExpression.OPERATORS[3])) {
            //任一分子为零，返回 0
            if (numerator1 == 0 || numerator2 == 0) return "0";
            //分子与分子乘  分母与分母乘
            return properFraction(rof(numerator1 * numerator2, denominator1 * denominator2));
        }
        //分数除
        if (operator.equals(ArithmeticExpression.OPERATORS[2])) {
            //分子为零 返回 0
            if (numerator1 == 0) return "0";
            //分母不为 0
            if (numerator2 == 0) return null;
            return properFraction(rof(numerator1 * denominator2, numerator2 * denominator1));
        }

        return null;
    }

    //对假分数约分
    public static String rof(long numerator, long denominator) {
        //求分子分母最大公约数
        long g = gcd(numerator, denominator);
        return numerator / g + "/" + denominator / g;
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
    public static long gcd(long a, long b) {
        if (a < 0) a = Math.abs(a);
        if (b < 0) b = Math.abs(b);
        if (a < b) {
            long tmp = a;
            a = b;
            b = tmp;
        }
        return (a % b == 0) ? b : gcd(a - b, b);
    }

    //求最小公倍数
    public static long lcm(long a, long b) {
        long c = gcd(a, b);
        return a / c * b;
    }
}
