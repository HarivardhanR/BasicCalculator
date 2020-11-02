package com.app.harish.myfirstapp;

import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Stack;

public class MainActivity extends AppCompatActivity {

    private String expression = "";
    private boolean isBeforeOperator = true;
    private int leftBracketCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void clickedNumber(View view) {
        if (expression.length() > 1) {
            if (expression.charAt(expression.length() - 1) != ')') {
                expression += String.valueOf(view.getTag());
                display();
                isBeforeOperator = false;
            }else{
                Toast.makeText(this,"operator expected",Toast.LENGTH_SHORT).show();
            }
        }else{
            expression += String.valueOf(view.getTag());
            display();
            isBeforeOperator = false;
        }
    }

    public void clickedOperator(View view) {
        if (!isBeforeOperator) {
            String operator = String.valueOf(view.getTag());
            switch (operator) {
                case "add":
                    expression += "+";
                    break;
                case "mul":
                    expression += "*";
                    break;
                case "sub":
                    expression += "-";
                    break;
                case "div":
                    expression += "/";
                    break;
                case "pow":
                    expression += "^";
                    break;

            }
            display();
            isBeforeOperator=true;
        }else{
            Toast.makeText(this,"invalid!",Toast.LENGTH_SHORT).show();
        }

    }

    public void clickedEquals(View view) {
        if (!isBeforeOperator && leftBracketCount == 0) {
            TextView solutionTextView = findViewById(R.id.textView2);
            solutionTextView.setText(String.valueOf(evaluate(expression)));
        } else {
            Toast.makeText(this,"Invalid Expression!",Toast.LENGTH_SHORT).show();
        }

    }

    public void clickedClear(View view) {
        expression = "";
        isBeforeOperator = true;
        leftBracketCount = 0;
        display();
    }

    public void clickedLeftBracket(View view) {
        if (isBeforeOperator) {
            expression += "(";
            leftBracketCount++;
            display();
        }else{
            Toast.makeText(this,"operator expected",Toast.LENGTH_SHORT).show();
        }
    }

    public void clickedRightBracket(View view) {
        if (leftBracketCount>0 && !isBeforeOperator) {
            expression += ")";
            leftBracketCount--;
            display();
        }else {
            Toast.makeText(this,"Invalid Expression!",Toast.LENGTH_SHORT).show();
        }
    }

    public void clickedBackspace(View view) {
        if (expression != null && expression.length() > 0) {
            char lastChar = expression.charAt(expression.length()-1);
            if (lastChar == ')'){
                leftBracketCount++;
            }else if (lastChar == '('){
                leftBracketCount--;
            } else if (lastChar == '+' || lastChar == '-' || lastChar == '*' || lastChar == '/' || lastChar == '^'){
                isBeforeOperator = false;
            } else if (Character.isDigit(lastChar)){
                if (expression.length() == 1){
                    isBeforeOperator = true;
                }else if (expression.length() > 1){
                    char lastButOneChar = expression.charAt(expression.length()-2);
                    isBeforeOperator = !Character.isDigit(lastButOneChar);
                }

            }
            expression = expression.substring(0, expression.length() - 1);
        }
        display();
    }

    protected void display()
    {
        TextView inputTextView = findViewById(R.id.textView);
        inputTextView.setText(expression);

    }

    public int evaluate(String expression)
    {
        char[] tokens = expression.toCharArray();

        // Stack for numbers
        Stack<Integer> values = new
                Stack<Integer>();

        // Stack for Operators
        Stack<Character> ops = new
                Stack<Character>();

        for (int i = 0; i < tokens.length; i++)
        {
            //check if it is number
            if (Character.isDigit(tokens[i]))
            {
                StringBuffer sbuf = new
                        StringBuffer();

                // There may be more than one
                // digits in number
                while (i < tokens.length &&
                        tokens[i] >= '0' &&
                        tokens[i] <= '9')
                    sbuf.append(tokens[i++]);
                values.push(Integer.parseInt(sbuf.
                        toString()));

                i--;
            }

            // Current token is an opening brace,
            // push it to 'ops'
            else if (tokens[i] == '(')
                ops.push(tokens[i]);

                // Closing brace encountered,
                // solve entire brace
            else if (tokens[i] == ')')
            {
                while (ops.peek() != '(')
                    values.push(performOperation(ops.pop(),
                            values.pop(),
                            values.pop()));
                ops.pop();
            }

            // Current token is an operator.
            else if (tokens[i] == '+' ||
                    tokens[i] == '-' ||
                    tokens[i] == '*' ||
                    tokens[i] == '/' ||
                    tokens[i] == '^')
            {
                // While top of 'ops' has same
                // or greater precedence to current
                // token, which is an operator.
                // perform operation on top of 'ops'
                // to top two elements in values stack
                while (!ops.empty() &&
                        hasPrecedence(tokens[i],
                                ops.peek()))
                    values.push(performOperation(ops.pop(),
                            values.pop(),
                            values.pop()));

                // Push current token to 'ops'.
                ops.push(tokens[i]);
            }
        }

        // Entire expression has been
        // parsed at this point, apply remaining
        // ops to remaining values
        while (!ops.empty())
            values.push(performOperation(ops.pop(),
                    values.pop(),
                    values.pop()));

        // Top of 'values' contains
        // result, return it
        return values.pop();
    }

    // Returns true if 'op2' has higher
    // or same precedence as 'op1',
    // otherwise returns false.
    public static boolean hasPrecedence(
            char op1, char op2)
    {
        if (op2 == '(' || op2 == ')')
            return false;
        if ((op1 == '*' || op1 == '/') &&
                (op2 == '+' || op2 == '-'))
            return false;
        if ((op2 == '*' || op2 == '/' || op2 == '+' || op2 == '-') && (op1 == '^'))
            return false;
        else
            return true;
    }

    public int performOperation(char op,
                                int b, int a)
    {
        switch (op)
        {
            case '+':
                return a + b;
            case '-':
                return a - b;
            case '*':
                return a * b;
            case '^':
                return (int)Math.pow(a,b);
            case '/':
                if (b == 0)
                    Toast.makeText(getApplicationContext(),"Cannot divide by zero!",Toast.LENGTH_SHORT).show();
                else
                    return a / b;
        }
        return 0;
    }

}
