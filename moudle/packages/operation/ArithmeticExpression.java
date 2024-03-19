package operation;

import java.util.Random;

public class ArithmeticExpression {
    public static final int EXPRESSION = 1;
    public static final int DIGIT = 0;
    public static final String[] OPERATORS = {"➕", "➖", "➗", "✖"};

    private boolean head;
    private int flag;
    private String element;
    private int num;

    private ArithmeticExpression next = null;
    private ArithmeticExpression firstChild = null;
    private String answer;

    public ArithmeticExpression() {
    }

    public ArithmeticExpression(boolean head, int num, String operator, int range) {
        this.head = head;
        init(this, num, operator, range);
    }

    public String getElement() {
        return element;
    }

    public ArithmeticExpression getNext() {
        return next;
    }

    public ArithmeticExpression getFirstChild() {
        return firstChild;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public void setElement(String element) {
        this.element = element;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public void setNext(ArithmeticExpression next) {
        this.next = next;
    }

    public void setFirstChild(ArithmeticExpression firstChild) {
        this.firstChild = firstChild;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public void init(ArithmeticExpression ae, int num, String operator, int range) {
        Random r = new Random();
        if (num == 0) {
            ae.setFlag(DIGIT);
            ae.setNum(0);
            ae.setElement(String.valueOf(r.nextInt(range - 1) + 1));
            ae.setNext(null);
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
        if (this.flag == DIGIT) return this.element + " ";
        StringBuilder sb = new StringBuilder();
        ArithmeticExpression ae;
        for (ae = this.firstChild; ae != null; ae = ae.getNext()) {
            sb.append(ae.toString());
            if (ae.getNext() != null) sb.append(this.element + " ");
        }
        return sb.toString();
    }
}
