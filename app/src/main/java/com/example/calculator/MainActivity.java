package com.example.calculator;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText editText;
    StringBuilder currentNumber;
    double operand1 = Double.NaN;
    double operand2 = Double.NaN;
    char operator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editText);
        currentNumber = new StringBuilder();

        setupButtonListeners();
    }

    private void setupButtonListeners() {
        Button buttonClear = findViewById(R.id.buttonClear);
        buttonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clear();
            }
        });

        Button buttonEquals = findViewById(R.id.buttonEquals);
        buttonEquals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculate();
            }
        });

        // Обработчики для кнопок цифр
        for (int i = 0; i <= 9; i++) {
            final int digit = i;
            int buttonId = getResources().getIdentifier("button" + digit, "id", getPackageName());
            Button button = findViewById(buttonId);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addDigit(Character.forDigit(digit, 10));
                }
            });
        }

        // Обработчики для кнопок операций
        Button buttonPlus = findViewById(R.id.buttonPlus);
        buttonPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setOperator('+');
            }
        });

        Button buttonMinus = findViewById(R.id.buttonMinus);
        buttonMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setOperator('-');
            }
        });

        Button buttonMultiply = findViewById(R.id.buttonMultiply);
        buttonMultiply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setOperator('*');
            }
        });

        Button buttonDivide = findViewById(R.id.buttonDivide);
        buttonDivide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setOperator('/');
            }
        });
    }

    private void addDigit(char digit) {
        currentNumber.append(digit);
        editText.setText(currentNumber.toString());
    }

    private void clear() {
        currentNumber.setLength(0);
        operand1 = Double.NaN;
        operand2 = Double.NaN;
        operator = '\0';
        editText.setText("");
    }

    private void setOperator(char op) {
        if (currentNumber.length() > 0) {
            if (!Double.isNaN(operand1)) {
                operand2 = Double.parseDouble(currentNumber.toString());
                calculate();
            } else {
                operand1 = Double.parseDouble(currentNumber.toString());
            }
            operator = op;
            currentNumber.setLength(0);
        }
    }

    private void calculate() {
        if (!Double.isNaN(operand1) && !Double.isNaN(operand2)) {
            double result = 0;
            switch (operator) {
                case '+':
                    result = operand1 + operand2;
                    break;
                case '-':
                    result = operand1 - operand2;
                    break;
                case '*':
                    result = operand1 * operand2;
                    break;
                case '/':
                    if (operand2 != 0) {
                        result = operand1 / operand2;
                    } else {
                        Toast.makeText(this, "Ошибка: деление на ноль", Toast.LENGTH_SHORT).show();
                        clear();
                        return;
                    }
                    break;
            }
            editText.setText(String.valueOf(result));
            operand1 = result;
            operand2 = Double.NaN;
            currentNumber.setLength(0);
            currentNumber.append(result);
        } else {
            // Если нет второго операнда, ничего не делаем
            if (!Double.isNaN(operand1)) {
                operand2 = Double.parseDouble(currentNumber.toString());
                calculate();
            }
        }
    }
}
