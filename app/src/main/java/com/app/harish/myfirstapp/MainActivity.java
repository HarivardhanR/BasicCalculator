package com.app.harish.myfirstapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private String solution = "";
    int y=1,operandNumber=0;
    boolean isFirstDigit = true,isFirstOperand=true,toMultiply=false,toSubtract=false,toDivide=false,toAdd=false;
    boolean isBeforeOperator = true,isOperationCompled=false,decimalON=false;
    double[] operands = new double[16];
    double i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void clear(View view) {
        solution="";
        TextView solutionTV = findViewById(R.id.txtSolution);
        solutionTV.setText(solution);
        for(int x = 0;x<operands.length;x++){
            operands[x]=0;
        }
        isBeforeOperator=true;
        operandNumber=1;
        isFirstDigit=true;
        isFirstOperand=true;
        display();
    }
    public void clickedSeven(View view) {
        check();
        operand(7);
        solution+="7";
        display();
        isBeforeOperator=false;
    }
    public void clickedEight(View view) {
        check();
        operand(8);
        solution+="8";
        display();
        isBeforeOperator=false;
    }
    public void clickedNine(View view) {
        check();
        operand(9);
        solution+="9";
        display();
        isBeforeOperator=false;
    }
    public void clickedSix(View view) {
        check();
        operand(6);
        solution+="6";
        display();
        isBeforeOperator=false;
    }
    public void clickedFive(View view) {
        check();
        operand(5);
        solution+="5";
        display();
        isBeforeOperator=false;
    }
    public void clickedFour(View view) {
        check();
        operand(4);
        solution+="4";
        display();
        isBeforeOperator=false;
    }
    public void clickedThree(View view) {
        check();
        operand(3);
        solution+="3";
        display();
        isBeforeOperator=false;
    }
    public void clickedTwo(View view) {
        check();
        operand(2);
        solution+="2";
        display();
        isBeforeOperator=false;
    }
    public void clickedOne(View view) {
        check();
        operand(1);
        solution+="1";
        display();
        isBeforeOperator=false;
    }
    public void clickedZero(View view) {
        check();
        operand(0);
        solution+="0";
        display();
        isBeforeOperator=false;
    }
    public void clickedPlus(View view) {
        if(isBeforeOperator) {
            Toast.makeText(getApplicationContext(), "invalid operation", Toast.LENGTH_SHORT).show();
        }
        else if(operandNumber<14){
            operation(i);
            toAdd = true;
            operandNumber++;
            solution += "+";
            display();
            isFirstDigit = true;
            isBeforeOperator=true;
            isOperationCompled=false;
        }else {
            Toast.makeText(getApplicationContext(), "max 15 operations", Toast.LENGTH_SHORT).show();
        }

        }
    public void clickedSubtract(View view) {
        if(isBeforeOperator) {
            Toast.makeText(getApplicationContext(), "invalid operation", Toast.LENGTH_SHORT).show();
        }
        else if(operandNumber<14) {
            operation(i);
            toSubtract = true;
            operandNumber++;
            solution += "-";
            display();
            isFirstDigit = true;
            isBeforeOperator=true;
            isOperationCompled=false;
        }else {
            Toast.makeText(getApplicationContext(), "max 15 operations", Toast.LENGTH_SHORT).show();
        }
    }
    public void clickedDivide(View view) {
        if(isBeforeOperator) {
            Toast.makeText(getApplicationContext(), "invalid operation", Toast.LENGTH_SHORT).show();
        }
        else if(operandNumber<14) {
            operation(i);
            toDivide = true;
            solution += "/";
            display();
            isFirstDigit = true;
            isBeforeOperator=true;
            isOperationCompled=false;
        }else {
            Toast.makeText(getApplicationContext(), "max 15 operations", Toast.LENGTH_SHORT).show();
        }
    }
    public void clickedMultiply(View view) {
        if(isBeforeOperator) {
            Toast.makeText(getApplicationContext(), "invalid operation", Toast.LENGTH_SHORT).show();
        }
        else if(operandNumber<14) {
            operation(i);
            toMultiply = true;
            solution += "*";
            display();
            isFirstDigit = true;
            isBeforeOperator=true;
            isOperationCompled=false;
        }else {
            Toast.makeText(getApplicationContext(), "max 15 operations", Toast.LENGTH_SHORT).show();
        }
    }
    public void clickedEquals(View view) {
        if(isBeforeOperator) {
            Toast.makeText(getApplicationContext(), "invalid operation", Toast.LENGTH_SHORT).show();
        }
        else {
            operation(i);
            double soln = 0;
            for (double x : operands) {
                soln = Math.round((soln + x)*100.00)/100.00;
            }

            TextView solutionTV = findViewById(R.id.txtSolution);
            solutionTV.setText(String.valueOf(soln));
            isOperationCompled=true;
        }
    }
    public void clickedDecimal(View view) {
        if(isBeforeOperator) {
            Toast.makeText(getApplicationContext(), "invalid operation", Toast.LENGTH_SHORT).show();
        }else {
            decimalON=true;
            isBeforeOperator=true;
            solution += ".";
            display();
        }
    }
    protected void display()
    {
        TextView expressionTV = findViewById(R.id.txtInput);
        expressionTV.setText(solution);

    }

    protected void operand(int x)
    {
        if (isFirstDigit) {
            i = x;
            isFirstDigit = false;
            decimalON=false;
            y=1;
        } else if(decimalON) {
            i =  (i+x/Math.pow(10,y));
            y++;
        }
        else {
            i = i * 10 + x;
        }
    }

    protected void operation(double i){

        if(toAdd || isFirstOperand)
        {
            operands[operandNumber] = i;
            toAdd=false;
            isFirstOperand = false;
        }
        else if(toSubtract) {
            operands[operandNumber] = -i;
            toSubtract = false;
        }
        else if(toMultiply) {
            operands[operandNumber] = Math.round(operands[operandNumber] * i *100.00)/100.00;
            toMultiply = false;
        }

        else if(toDivide){
            operands[operandNumber] = Math.round((operands[operandNumber] / i)*100.00)/100.00;
            toDivide = false;
        }

    }

    protected void check(){
        if(isOperationCompled)
        {
            solution="";
            TextView solutionTV = findViewById(R.id.txtSolution);
            solutionTV.setText(solution);
            for(int x = 0;x<operands.length;x++){
                operands[x]=0;
            }
            isBeforeOperator=true;
            operandNumber=1;
            isFirstDigit=true;
            isFirstOperand=true;
            display();
            isOperationCompled=false;
        }
    }
}