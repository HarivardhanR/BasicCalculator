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

    public int evaluate(String expression){
        //Stack for Numbers
        Stack<Integer> numbers = new Stack<>();

        //Stack for operators
        Stack<Character> operations = new Stack<>();

        for(int i=0; i<expression.length();i++) {
            char c = expression.charAt(i);
            //check if it is number
            if(Character.isDigit(c)){
                //Entry is Digit, it could be greater than one digit number
                int num = 0;
                while (Character.isDigit(c)) {
                    num = num*10 + (c-'0');
                    i++;
                    if(i < expression.length())
                        c = expression.charAt(i);
                    else
                        break;
                }
                i--;
                //push it into stack
                numbers.push(num);
            }else if(c=='('){
                //push it to operators stack
                operations.push(c);
            }
            //Closed brace, evaluate the entire brace
            else if(c==')') {
                while(operations.peek()!='('){
                    int output = performOperation(numbers, operations);
                    //push it back to stack
                    numbers.push(output);
                }
                operations.pop();
            }
            // current character is operator
            else if(isOperator(c)){
                //1. If current operator has higher precedence than operator on top of the stack,
                //the current operator can be placed in stack
                // 2. else keep popping operator from stack and perform the operation in  numbers stack till
                //either stack is not empty or current operator has higher precedence than operator on top of the stack
                while(!operations.isEmpty() && precedence(c)<precedence(operations.peek())){
                    int output = performOperation(numbers, operations);
                    //push it back to stack
                    numbers.push(output);
                }
                //now push the current operator to stack
                operations.push(c);
            }
        }
        //If here means entire expression has been processed,
        //Perform the remaining operations in stack to the numbers stack

        while(!operations.isEmpty()){
            int output = performOperation(numbers, operations);
            //push it back to stack
            numbers.push(output);
        }
        return numbers.pop();
    }

    static int precedence(char c){
        switch (c){
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
                return 2;
            case '^':
                return 3;
        }
        return -1;
    }

    public int performOperation(Stack<Integer> numbers, Stack<Character> operations) {
        int a = numbers.pop();
        int b = numbers.pop();
        char operation = operations.pop();
        switch (operation) {
            case '+':
                return a + b;
            case '-':
                return b - a;
            case '*':
                return a * b;
            case '^':
                return (int)Math.pow(b,a);
            case '/':
                if (a == 0)
                    Toast.makeText(this,"Cannot divide by zero!",Toast.LENGTH_SHORT).show();
                else
                    return b / a;
        }
        return 0;
    }

    public boolean isOperator(char c){
        return (c=='+'||c=='-'||c=='/'||c=='*'||c=='^');
    }
}
