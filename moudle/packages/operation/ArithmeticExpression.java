package operation;

import java.util.Random;

public class ArithmeticExpression {
    //常量：表示算术表达式
    public static final int EXPRESSION = 1;
    //常量：表示数字
    public static final int DIGIT = 0;
    //常量：加减乘除符号
    public static final String[] OPERATORS = {"➕", "➖", "➗", "✖"};

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

    //返回元素
    public String getElement() {
        return element;
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

        ae.setFlag(EXPRESSION);
        ae.setElement(operator);
        ae.setNum(randomNum(num, false));

        int x, operatorIndex;
        ArithmeticExpression p;
        num -= this.num;
        x = randomNum(num, true);
        operatorIndex = generateOperator(this.head);
        p = new ArithmeticExpression(false, x, OPERATORS[operatorIndex], range);
        ae.setFirstChild(p);
        for (int i = 0; i < this.num; i++) {
            num -= x;
            if (i == this.num - 1) x = num;
            else x = randomNum(num, true);
            operatorIndex = generateOperator(false);
            p.setNext(new ArithmeticExpression(false, x, OPERATORS[operatorIndex], range));
            p = p.getNext();
        }
    }

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

    private int randomNum(int num, boolean needZero) {
        Random r = new Random();
        if (num == 0) return 0;
        if (needZero) return r.nextInt(num + 1);
        return r.nextInt(num) + 1;
    }

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

    @Override
    public String toString() {
        if (this.flag == DIGIT) return this.element;
        StringBuilder sb = new StringBuilder();
        ArithmeticExpression ae = this.firstChild;
        if(ae.addBracket(this.element,true)) {
            sb.append("(").append(ae.toString()).append(")");
        } else {
            sb.append(ae.toString());
        }
        for (ae = ae.getNext(); ae != null; ae = ae.getNext()) {
            if(ae.addBracket(this.element,false)) {
                sb.append(" ").append(this.element).append(" ");
                sb.append("(").append(ae.toString()).append(")");
            } else {
                sb.append(" ").append(this.element).append(" ").append(ae.toString());
            }
        }
        return sb.toString();
    }

    private boolean addBracket(String lastElement, boolean first) {
        if(this.flag == DIGIT) return false;
        if (lastElement.equals("➖")) {
            return !first && (this.element.equals("➕") || this.element.equals("➖"));
        }
        if (lastElement.equals("✖")) {
            return this.element.equals("➖") || this.element.equals("➕");
        }
        if (lastElement.equals("➗")) {
            return !first || this.element.equals("➖") || this.element.equals("➕");
        }
        return false;
    }
}
