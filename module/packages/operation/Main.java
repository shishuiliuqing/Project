package operation;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class Main {
    public static void main(String[] args) throws IOException {
        //答案文本写入对象
        FileWriter fw1 = new FileWriter("D:\\Project\\JAVA_Project\\operationGenerator\\module\\packages\\operation\\Answer.txt");
        //题目文本写入对象
        FileWriter fw2 = new FileWriter("D:\\Project\\JAVA_Project\\operationGenerator\\module\\packages\\operation\\Exercises.txt");
        //题目拼接器用 “=” 分割每一道题目
        StringBuilder sb = new StringBuilder();
        int n = 100;
        int num = 0;
        //生成n道题目
        while (num < n) {
            String answer = generateQuestions(sb);
            if (answer == null) continue;
            num++;
            generateAnswer(fw1, answer, num);
        }
        fw1.close();

        //n到题目
        String[] strings = sb.toString().split("=");
        //生成题目文件
        for (int i = 0; i < strings.length; i++) {
            String str = i + 1 + ".";
            fw2.write(String.format("%-8s%s = \n", str, strings[i]));
        }
        fw2.close();
    }

    //判断strings中的所有内容是否在str存在
    public static boolean isExist(String[] strings, String str) {
        if (str == null) return false;
        for (String string : strings) {
            if (str.contains(string)) return true;
        }
        return false;
    }

    //生成一道题目，返回题目答案
    public static String generateQuestions(StringBuilder sb) {
        //运算符数组
        final String[] OPERATORS = {"+", "−", "÷", "×"};
        //随机数对象
        Random r = new Random();
        //算术表达式对象
        ArithmeticExpression ae = new ArithmeticExpression(true, r.nextInt(3) + 1, OPERATORS[r.nextInt(4)], 10);
        //非负判断
        if (Fraction.isLessThenZero(ae.getAnswer())) return null;
        String[] strings = ae.commutativeLaw();
        //非重复及非交换判断
        if (isExist(strings, sb.toString())) return null;
        sb.append(ae).append("=");
        return ae.getAnswer();
    }

    //生成答案
    public static void generateAnswer(FileWriter fw, String answer, int num) throws IOException {
        String str = num + ".";
        fw.write(String.format("%-8s%s\n", str, answer));
    }
}
