import java.awt.Font;
import java.awt.event.ActionListener;
import java.util.regex.Pattern;
import javax.swing.*;

public class SimpleCalculator {

    private static final int WINDOW_WIDTH = 400;
    private static final int WINDOW_HEIGHT = 600;
    private static final int BUTTON_WIDTH = 80;
    private static final int BUTTON_HEIGHT = 70;
    private static final int MARGIN_X = 20;
    private static final int MARGIN_Y = 60;

    private JFrame window;
    private JTextField inText;
    private JButton btnC, btnBack, btnMod, btnDiv, btnMul, btnSub, btnAdd,
            btn0, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9,
            btnPoint, btnEqual;

    private char opt = ' ';
    private boolean go = true;
    private boolean addWrite = true;
    private double val = 0;

    public SimpleCalculator() {
        window = new JFrame("Simple Calculator");
        window.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        window.setLocationRelativeTo(null);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        int[] x = {MARGIN_X, MARGIN_X + 90, MARGIN_X + 180, MARGIN_X + 270};
        int[] y = {MARGIN_Y, MARGIN_Y + 100, MARGIN_Y + 180, MARGIN_Y + 260, MARGIN_Y + 340, MARGIN_Y + 420};

        inText = new JTextField("0");
        inText.setBounds(x[0], y[0], 340, 70);
        inText.setEditable(false);
        inText.setFont(new Font("Arial", Font.PLAIN, 30));
        window.add(inText);

        btnC = initBtn("AC", x[0], y[1], event -> resetCalculator());
        btnBack = initBtn("C", x[1], y[1], event -> backspace());
        btnMod = initBtn("%", x[2], y[1], event -> performOperation('%'));
        btnDiv = initBtn("/", x[3], y[1], event -> performOperation('/'));

        btn7 = initBtn("7", x[0], y[2], event -> appendNumber("7"));
        btn8 = initBtn("8", x[1], y[2], event -> appendNumber("8"));
        btn9 = initBtn("9", x[2], y[2], event -> appendNumber("9"));
        btnMul = initBtn("*", x[3], y[2], event -> performOperation('*'));

        btn4 = initBtn("4", x[0], y[3], event -> appendNumber("4"));
        btn5 = initBtn("5", x[1], y[3], event -> appendNumber("5"));
        btn6 = initBtn("6", x[2], y[3], event -> appendNumber("6"));
        btnSub = initBtn("-", x[3], y[3], event -> performOperation('-'));

        btn1 = initBtn("1", x[0], y[4], event -> appendNumber("1"));
        btn2 = initBtn("2", x[1], y[4], event -> appendNumber("2"));
        btn3 = initBtn("3", x[2], y[4], event -> appendNumber("3"));
        btnAdd = initBtn("+", x[3], y[4], event -> performOperation('+'));

        btn0 = initBtn("0", x[1], y[5], event -> appendNumber("0"));
        btnPoint = initBtn(".", x[0], y[5], event -> appendDecimal());
        btnEqual = initBtn("=", x[2], y[5], event -> calculateResult());
        btnEqual.setSize(2 * BUTTON_WIDTH + 10, BUTTON_HEIGHT);

        window.setLayout(null);
        window.setResizable(false);
        window.setVisible(true);
    }

    private JButton initBtn(String label, int x, int y, ActionListener event) {
        JButton btn = new JButton(label);
        btn.setBounds(x, y, BUTTON_WIDTH, BUTTON_HEIGHT);
        btn.setFont(new Font("Arial", Font.PLAIN, 24));
        btn.addActionListener(event);
        window.add(btn);

        return btn;
    }

    private void resetCalculator() {
        inText.setText("0");
        opt = ' ';
        val = 0;
    }

    private void backspace() {
        String str = inText.getText();
        if (str.length() > 1) {
            inText.setText(str.substring(0, str.length() - 1));
        } else {
            inText.setText("0");
        }
    }

    private void appendNumber(String num) {
        if (addWrite) {
            if (Pattern.matches("[0]*", inText.getText())) {
                inText.setText(num);
            } else {
                inText.setText(inText.getText() + num);
            }
        } else {
            inText.setText(num);
            addWrite = true;
        }
        go = true;
    }

    private void appendDecimal() {
        if (addWrite && !inText.getText().contains(".")) {
            inText.setText(inText.getText() + ".");
        } else if (!addWrite) {
            inText.setText("0.");
            addWrite = true;
        }
        go = true;
    }

    private void performOperation(char operation) {
        if (Pattern.matches("([-]?\\d+[.]\\d*)|(\\d+)", inText.getText())) {
            if (go) {
                val = calculate(val, inText.getText(), opt);
                inText.setText(String.valueOf(val));
                opt = operation;
                go = false;
                addWrite = false;
            } else {
                opt = operation;
            }
        }
    }

    private void calculateResult() {
        if (Pattern.matches("([-]?\\d+[.]\\d*)|(\\d+)", inText.getText()) && go) {
            val = calculate(val, inText.getText(), opt);
            inText.setText(String.valueOf(val));
            opt = '=';
            addWrite = false;
        }
    }

    private double calculate(double x, String input, char opt) {
        double y = Double.parseDouble(input);
        switch (opt) {
            case '+':
                return x + y;
            case '-':
                return x - y;
            case '*':
                return x * y;
            case '/':
                return x / y;
            case '%':
                return x % y;
            default:
                return y;
        }
    }

    public static void main(String[] args) {
        new SimpleCalculator();
    }
}