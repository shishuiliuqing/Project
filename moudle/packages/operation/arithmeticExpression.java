package operation;

import java.util.Random;

public class arithmeticExpression {
    public static final int OPERATOR = 1;
    public static final int DIGIT = 0;
    public static final String[] OPERATORS = {"➕", "➖", "✖", "➗"};

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public void setElement(String element) {
        this.element = element;
    }

    private int flag;
    private String element;

    public void setLeft(arithmeticExpression left) {
        this.left = left;
    }

    public void setRight(arithmeticExpression right) {
        this.right = right;
    }

    private arithmeticExpression left;
    private arithmeticExpression right;

    private String answer;

    public arithmeticExpression(int len, int range) {
        init(this, len, range);
        initAnswer(this);
    }

    public void init(arithmeticExpression ae, int len, int range) {
        Random r = new Random();
        if (len == 0) {
            ae = null;
            return;
        }

        if (len == 1) {
            ae.setFlag(DIGIT);
            ae.setElement(String.valueOf(r.nextInt(range - 1) + 1));
            ae.setLeft(null);
            ae.setRight(null);
            return;
        }

        ae.setFlag(OPERATOR);
        ae.setElement(OPERATORS[r.nextInt(4)]);

        ae.setLeft(new arithmeticExpression(len - len / 2, range));
        ae.setRight(new arithmeticExpression(len / 2, range));
        return;
    }

    private void initAnswer(arithmeticExpression ae) {
        return;
    }

    @Override
    public String toString() {
        String s1 = "";
        String s2 = "";
        if(left != null) {
            s1 = left.toString();
        }
        if(right != null) {
            s2 = right.toString();
        }
        return  s1 + " " + element + " " + s2;
    }
}
