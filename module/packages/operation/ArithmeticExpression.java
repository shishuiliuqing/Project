package operation;

import java.util.Random;

public class ArithmeticExpression {
    //常量：表示算术表达式
    public static final int EXPRESSION = 1;
    //常量：表示数字
    public static final int DIGIT = 0;
    //常量：加减乘除符号
    public static final String[] OPERATORS = {"+", "−", "÷", "×"};

    //是否为头结点对象
    private final boolean head;
    //标记是数字还是算术表达式
    private int flag;
    //元素（可以是数字，也可以是运算符）
    private String element;
    //运算符个数，若该对象为数字，则为 0
    private int num;
    //下一个对象
    private ArithmeticExpression next = null;
    //第一个孩子对象
    private ArithmeticExpression firstChild = null;
    //算术表达式结果 或 数字数值
    private String answer;

    //对象初始化
    public ArithmeticExpression(boolean head, int num, String operator, int range) {
        //是否为头结点对象
        this.head = head;
        //初始化
        init(this, num, operator, range);
        //求结果
        initAnswer();
    }

    //返回结果
    public String getAnswer() {
        return answer;
    }

    //返回下一对象
    public ArithmeticExpression getNext() {
        return next;
    }

    //设置标记
    public void setFlag(int flag) {
        this.flag = flag;
    }

    //设置元素
    public void setElement(String element) {
        this.element = element;
    }

    //设置运算符个数
    public void setNum(int num) {
        this.num = num;
    }

    //设置下一对象
    public void setNext(ArithmeticExpression next) {
        this.next = next;
    }

    //设置第一个孩子对象
    public void setFirstChild(ArithmeticExpression firstChild) {
        this.firstChild = firstChild;
    }

    //初始化
    public void init(ArithmeticExpression ae, int num, String operator, int range) {
        Random r = new Random();
        if (num == 0) {
            //数字
            ae.setFlag(DIGIT);
            //运算符个数
            ae.setNum(0);
            //数字数值
            ae.setElement(String.valueOf(r.nextInt(range - 1) + 1));
            //无
            ae.setNext(null);
            //无
            ae.setFirstChild(null);
            return;
        }
        //算术表达式
        ae.setFlag(EXPRESSION);
        //运算符
        ae.setElement(operator);
        //运算符个数
        ae.setNum(randomNum(num, false));
        //首孩子的运算符以及运算符个数
        int x, operatorIndex;
        ArithmeticExpression p;
        num -= this.num;
        x = randomNum(num, true);
        operatorIndex = generateOperator(this.head);
        p = new ArithmeticExpression(false, x, OPERATORS[operatorIndex], range);
        ae.setFirstChild(p);
        //为每个孩子添加运算符以及运算符个数
        for (int i = 0; i < this.num; i++) {
            num -= x;
            if (i == this.num - 1) x = num;
            else x = randomNum(num, true);
            operatorIndex = generateOperator(false);
            p.setNext(new ArithmeticExpression(false, x, OPERATORS[operatorIndex], range));
            p = p.getNext();
        }
    }

    //初始化答案，使用递归算法
    private void initAnswer() {
        if (this.flag == DIGIT) {
            this.answer = this.element;
            return;
        }

        ArithmeticExpression ae = firstChild;
        this.answer = ae.getAnswer();
        for (int i = 0; i < this.num; i++) {
            ae = ae.getNext();
            this.answer = Fraction.calculate(this.answer, ae.getAnswer(), this.element);
//            System.out.println(this.answer);
        }
    }

    //生成一个到num的整数，是否从零开始
    private int randomNum(int num, boolean needZero) {
        Random r = new Random();
        if (num == 0) return 0;
        if (needZero) return r.nextInt(num + 1);
        return r.nextInt(num) + 1;
    }

    //随机生成一个运算符
    private int generateOperator(boolean head) {
        Random r = new Random();
        if (head) {
            if (this.element.equals(OPERATORS[0])) return r.nextInt(3) + 1;
            if (this.element.equals(OPERATORS[1])) return r.nextInt(3) + 1;
            if (this.element.equals(OPERATORS[2])) return r.nextInt(3);
            if (this.element.equals(OPERATORS[3])) return r.nextInt(3);
        }
        return r.nextInt(4);
    }

    //打印题目，使用递归算法
    @Override
    public String toString() {
        if (this.flag == DIGIT) return this.element;
        StringBuilder sb = new StringBuilder();
        ArithmeticExpression ae = this.firstChild;
        if (ae.addBracket(this.element, true)) {
            sb.append("(").append(ae).append(")");
        } else {
            sb.append(ae);
        }
        for (ae = ae.getNext(); ae != null; ae = ae.getNext()) {
            if (ae.addBracket(this.element, false)) {
                sb.append(" ").append(this.element).append(" ");
                sb.append("(").append(ae).append(")");
            } else {
                sb.append(" ").append(this.element).append(" ").append(ae);
            }
        }
        return sb.toString();
    }

    //对必要情况添加括号
    private boolean addBracket(String lastElement, boolean first) {
        if (this.flag == DIGIT) return false;
        if (lastElement.equals(OPERATORS[1])) {
            return !first && (this.element.equals(OPERATORS[0]) || this.element.equals(OPERATORS[1]));
        }
        if (lastElement.equals(OPERATORS[3])) {
            return this.element.equals(OPERATORS[1]) || this.element.equals(OPERATORS[0]);
        }
        if (lastElement.equals(OPERATORS[2])) {
            return !first || this.element.equals(OPERATORS[1]) || this.element.equals(OPERATORS[0]);
        }
        return false;
    }

    //交换律，返回一个字符串数组，里面包含自身与有限交换的所有结果
    public String[] commutativeLaw() {
        if (this.element.equals("-") || this.element.equals("÷")) {
            String[] strings = new String[1];
            strings[0] = this.toString();
            return strings;
        }

        String[] strings = new String[this.num + 1];
        ArithmeticExpression ae = this.firstChild;
        while (ae.getNext() != null) {
            ae = ae.getNext();
        }
        strings[0] = this.toString();
        for (int i = 1; i < strings.length; i++) {
            ae = exchange(ae);
            strings[i] = this.toString();
        }
        exchange(ae);
        return strings;
    }

    //交换头孩子与尾孩子的位置
    private ArithmeticExpression exchange(ArithmeticExpression ae) {
        ae.setNext(this.firstChild);
        this.setFirstChild(this.firstChild.getNext());
        ae.getNext().setNext(null);
        ae = ae.getNext();
        return ae;
    }

    //题目解析
    public static String problemAnalysis(String exercise) {
        if (exercise == null) return null;
        if (exercise.equals("")) return null;
        exercise = exercise.replaceAll("[\\s=]", "");
        String[] strings = exercise.split("[()]");
        return bracketAnalysis(strings, 0, false);
    }

    //题目解析--括号
    private static String bracketAnalysis(String[] strings, int begin, boolean isBracket) {
        StringBuilder answer = new StringBuilder();
        if (isBracket) {
            if (tailIsNumber(strings[begin])) return addAnalysis(strings[begin]);
            answer.append(strings[begin]).append(bracketAnalysis(strings, begin + 1, true));
            return addAnalysis(answer.toString());
        }
        for (int i = begin; i < strings.length; i++) {
            if (strings[i].equals("")) continue;
            if (tailIsNumber(strings[i])) answer = new StringBuilder(addAnalysis(answer + strings[i]));
            else {
                answer.append(strings[i]).append(bracketAnalysis(strings, i + 1, true));
                while (!tailIsNumber(strings[i])) {
                    i++;
                }
            }
        }
        return addAnalysis(answer.toString());
    }

    //题目解析--加号
    private static String addAnalysis(String exercise) {
        if (exercise.length() == 1) return exercise;
        if (exercise.equals("")) return "";
        String[] strings = exercise.split("\\+");
        String answer = "0";
        for (String string : strings) {
            if (isNumber(string)) answer = Fraction.calculate(answer, string, OPERATORS[0]);
            else answer = Fraction.calculate(answer, subAnalysis(string), OPERATORS[0]);
        }
        return answer;
    }

    //题目解析--减号
    private static String subAnalysis(String exercise) {
        String[] strings = exercise.split("−");
        String answer;
        if (isNumber(strings[0])) answer = strings[0];
        else answer = mulAnalysis(strings[0]);
        for (int i = 1; i < strings.length; i++) {
            if (isNumber(strings[i])) answer = Fraction.calculate(answer, strings[i], OPERATORS[1]);
            else answer = Fraction.calculate(answer, mulAnalysis(strings[i]), OPERATORS[1]);
        }
        return answer;
    }

    //题目解析--乘号
    private static String mulAnalysis(String exercise) {
        String[] strings = exercise.split("×");
        String answer = "1";
        for (String string : strings) {
            if (isNumber(string)) answer = Fraction.calculate(answer, string, OPERATORS[3]);
            else answer = Fraction.calculate(answer, divAnalysis(string), OPERATORS[3]);
        }
        return answer;
    }

    //题目解析--除号
    private static String divAnalysis(String exercise) {
        String[] strings = exercise.split("÷");
        String answer = strings[0];
        for (int i = 1; i < strings.length; i++) {
            answer = Fraction.calculate(answer, strings[i], OPERATORS[2]);
        }
        return answer;
    }

    //题目解析--判断是否为纯数字
    private static boolean isNumber(String exercise) {
        for (int i = 0; i < 4; i++) {
            if (exercise.contains(OPERATORS[i])) return false;
        }
        return true;
    }

    //判断尾字符是否为数字
    private static boolean tailIsNumber(String exercise) {
        if (exercise.equals("")) return false;
        exercise = exercise.substring(exercise.length() - 1);
        for (String operator : OPERATORS) {
            if (exercise.equals(operator)) return false;
        }
        return true;
    }
}
