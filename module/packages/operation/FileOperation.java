package operation;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class FileOperation {
    //题目文件
    private final String exerciseFile;
    //答案文件
    private final String answerFile;

    public FileOperation(String exerciseFile, String answerFile) {
        this.exerciseFile = exerciseFile;
        this.answerFile = answerFile;
    }

    //生成n到题目及答案，生成题目文件和答案文件
    public void generateQuestionsAndAnswer(int n, int range) throws IOException {
        //答案文本写入对象
        FileWriter fw1 = new FileWriter(answerFile);
        //题目文本写入对象
        FileWriter fw2 = new FileWriter(exerciseFile);
        //题目拼接器用 “=” 分割每一道题目
        StringBuilder sb = new StringBuilder();
        int num = 0;
        //生成n道题目
        while (num < n) {
            String answer = generateQuestions(sb, range);
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
    private boolean isExist(String[] strings, String str) {
        if (str == null) return false;
        for (String string : strings) {
            if (str.contains(string)) return true;
        }
        return false;
    }

    //生成一道题目，返回题目答案
    private String generateQuestions(StringBuilder sb, int range) {
        //运算符数组
        final String[] OPERATORS = {"+", "−", "÷", "×"};
        //随机数对象
        Random r = new Random();
        //算术表达式对象
        ArithmeticExpression ae = new ArithmeticExpression(true, r.nextInt(3) + 1, OPERATORS[r.nextInt(4)], range);
        //非负判断
        if (Fraction.isLessThenZero(ae.getAnswer())) return null;
        String[] strings = ae.commutativeLaw();
        //非重复及非交换判断
        if (isExist(strings, sb.toString())) return null;
        sb.append(ae).append("=");
        return ae.getAnswer();
    }

    //生成答案
    private void generateAnswer(FileWriter fw, String answer, int num) throws IOException {
        String str = num + ".";
        fw.write(String.format("%-8s%s\n", str, answer));
    }

    //比对结果打印
    public void compareResult(FileWriter fw) throws IOException {
        String[] strings = compareAnswer();
        if (strings == null) return;
        String[] correct = strings[0].split(", ");
        String[] wrong = strings[1].split(", ");
        int correctNum = correct.length;
        if (correct[0].equals("")) correctNum = 0;
        int wrongNum = wrong.length;
        if (wrong[0].equals("")) wrongNum = 0;
        fw.write("Correct: " + correctNum + " (" + strings[0] + ")\n");
        fw.write("Wrong: " + wrongNum + " (" + strings[1] + ")\n");
        fw.close();
    }

    //题目与答案比对
    private String[] compareAnswer() {
        //正确答案
        String[] rightAnswers = calculateAnswers();
        //答案文件答案
        String[] answers = obtainAnswers();

        if (rightAnswers == null || answers == null) {
            return null;
        }

        StringBuilder sb1 = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();
        boolean result;
        String[] strings = new String[2];
        //答案比对
        for (int i = 0; i < rightAnswers.length; i++) {
            if (i >= answers.length) {
                result = false;
            } else {
                result = rightAnswers[i].equals(answers[i]);
            }

            if (result) {
                if (sb1.isEmpty()) sb1.append(i + 1);
                else sb1.append(", ").append(i + 1);
            } else {
                if (sb2.isEmpty()) sb2.append(i + 1);
                else sb2.append(", ").append(i + 1);
            }
        }
        //正确答案
        strings[0] = sb1.toString();
        //错误答案
        strings[1] = sb2.toString();
        return strings;
    }

    //计算获题目文件每一道题目的答案
    private String[] calculateAnswers() {
        StringBuilder sb;
        //读取题目文本
        try (FileReader fr = new FileReader(exerciseFile)) {
            int ch;
            sb = new StringBuilder();
            while ((ch = fr.read()) != -1) {
                sb.append((char) ch);
            }
        } catch (Exception e) {
            System.out.println("地址错误");
            return null;
        }
        String exercise = sb.toString();
        //按回车分割每一道题目
        String[] strings = exercise.split("\n+");
        for (int i = 0; i < strings.length; i++) {
            if (strings[i].equals("")) continue;
            String str = strings[i].replaceAll("\\d+\\.", "");
            strings[i] = ArithmeticExpression.problemAnalysis(str);
            //System.out.println(strings[i]);
        }
        return strings;
    }

    //获取答案文件答案
    private String[] obtainAnswers() {
        StringBuilder sb = new StringBuilder();
        //读取answer文件
        try (FileReader fr = new FileReader(answerFile)) {
            int ch;
            while ((ch = fr.read()) != -1) {
                sb.append((char) ch);
            }
        } catch (Exception e) {
            System.out.println("地址错误");
            return null;
        }
        //按回车分割每题答案
        String[] strings = sb.toString().split("\n+");
        for (int i = 0; i < strings.length; i++) {
            //删除题号，空格和.
            strings[i] = strings[i].replaceAll("(\\d+\\.)|(\\s)", "");
            //System.out.println(strings[i]);
        }
        return strings;
    }
}
