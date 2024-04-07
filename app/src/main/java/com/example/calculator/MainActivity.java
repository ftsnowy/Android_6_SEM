package com.example.calculator;

import android.annotation.SuppressLint;
import android.hardware.SensorManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;


public class MainActivity extends AppCompatActivity implements View.OnClickListener, SensorEventListener {

    private SensorManager sensorManager;
    private Sensor gyroscope;

    private long lastRotationTime = 0;


    ConstraintLayout screenLayout;
    Button btn0, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btnPlus, btnMinus, btnMult,
           btnDiv, btnEquals, btnPM, btnC, btnSquare, btnRoot, btnSin, btnCos, btnDot;

    TextView expression;
    private String currentExpr = "";
    private int currentOp = 0;

    private Calculator calculator = new Calculator();


    private GestureDetector gestureDetector;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findXML();
        setListener();
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        gyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, gyroscope, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        sensorManager.unregisterListener(this);
        super.onPause();
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor == gyroscope) {
            long currentTime = System.currentTimeMillis();
            if (currentTime - lastRotationTime > 200) { // Ограничиваем частоту обработки поворотов
                lastRotationTime = currentTime;

                float[] angularVelocity = event.values;

                // Проверяем, произошел ли быстрый поворот
                if (Math.abs(angularVelocity[1]) > Math.PI) { // Проверяем угловую скорость вокруг оси Y
                    clearExpression();
                }
            }
        }
    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }


    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.btnC){

            if (currentExpr != null && currentExpr.length() > 0) {
                char lastChar = currentExpr.charAt(currentExpr.length() - 1);

                if (currentExpr.length()==2 && currentExpr.charAt(0)=='-'){
                    currentExpr = "";
                    updateExpression();
                    return;
                }

                if (currentExpr.length()>=2){
                    if (lastChar == '+' || lastChar == '-' && currentExpr.charAt(currentExpr.length() - 2) == 'e'){
                        currentExpr = currentExpr.substring(0, currentExpr.length() - 1);
                        updateExpression();
                        return;
                    }
                }

                if (lastChar == '+' || lastChar == '-' || lastChar == '*' || lastChar == '/') {
                    currentOp--;
                    currentExpr = currentExpr.substring(0, currentExpr.length() - 1);
                    updateExpression();
                    return;
                }

                currentExpr = currentExpr.substring(0, currentExpr.length() - 1);
                updateExpression();

            }

        }

        if (id == R.id.btn0){
            if (checkZeroInput()){
                currentExpr = currentExpr.substring(0, currentExpr.length() - 1);
            }
            if (checkExprLength() ){
                currentExpr += "0";
            }
            updateExpression();
        }

        if (id == R.id.btn1){
            if (checkZeroInput()){
                currentExpr = currentExpr.substring(0, currentExpr.length() - 1);
            }
            if (checkExprLength()){
                currentExpr += "1";
            }
            updateExpression();
        }

        if (id == R.id.btn2){
            if (checkZeroInput()){
                currentExpr = currentExpr.substring(0, currentExpr.length() - 1);
            }
            if (checkExprLength()){
                currentExpr += "2";
            }
            updateExpression();
        }

        if (id == R.id.btn3){
            if (checkZeroInput()){
                currentExpr = currentExpr.substring(0, currentExpr.length() - 1);
            }
            if (checkExprLength()){
                currentExpr += "3";
            }
            updateExpression();
        }

        if (id == R.id.btn4){
            if (checkZeroInput()){
                currentExpr = currentExpr.substring(0, currentExpr.length() - 1);
            }
            if (checkExprLength()){
                currentExpr += "4";
            }
            updateExpression();
        }

        if (id == R.id.btn5){
            if (checkZeroInput()){
                currentExpr = currentExpr.substring(0, currentExpr.length() - 1);
            }
            if (checkExprLength()){
                currentExpr += "5";
            }
            updateExpression();
        }

        if (id == R.id.btn6){
            if (checkZeroInput()){
                currentExpr = currentExpr.substring(0, currentExpr.length() - 1);
            }
            if (checkExprLength()){
                currentExpr += "6";
            }
            updateExpression();
        }

        if (id == R.id.btn7){
            if (checkZeroInput()){
                currentExpr = currentExpr.substring(0, currentExpr.length() - 1);
            }
            if (checkExprLength()){
                currentExpr += "7";
            }
            updateExpression();
        }

        if (id == R.id.btn8){
            if (checkZeroInput()){
                currentExpr = currentExpr.substring(0, currentExpr.length() - 1);
            }
            if (checkExprLength()){
                currentExpr += "8";
            }
            updateExpression();
        }

        if (id == R.id.btn9){
            if (checkZeroInput()){
                currentExpr = currentExpr.substring(0, currentExpr.length() - 1);
            }
            if (checkExprLength()){
                currentExpr += "9";
            }
            updateExpression();
        }

        if (id == R.id.btnPlus){
            if (currentExpr.length()==0){
                return;
            }
            char lastChar = currentExpr.charAt(currentExpr.length() - 1);
            if (lastChar == '+' || lastChar == '-' || lastChar == '*' || lastChar == '/') {
                return;
            }
            if (currentOp == 0){
                currentOp++;
                currentExpr += "+";
                updateExpression();
            } else {
                String result = calculator.evaluate(currentExpr, "+");
                if (result != "Error"){
                    currentExpr = result;
                    currentOp = 0;
                    currentOp++;
                    currentExpr += "+";
                    updateExpression();
                } else {
                    Toast.makeText(getApplicationContext(), "Error!", Toast.LENGTH_SHORT).show();
                }
            }
        }

        if (id == R.id.btnMinus){
            if (currentExpr.length()==0){
                return;
            }
            char lastChar = currentExpr.charAt(currentExpr.length() - 1);
            if (lastChar == '+' || lastChar == '-' || lastChar == '*' || lastChar == '/') {
                return;
            }
            if (currentOp == 0){
                currentOp++;
                currentExpr += "-";
                updateExpression();
            } else {
                String result = calculator.evaluate(currentExpr, "-");
                if (result != "Error"){
                    currentExpr = result;
                    currentOp = 0;
                    currentOp++;
                    currentExpr += "-";
                    updateExpression();
                } else {
                    Toast.makeText(getApplicationContext(), "Error!", Toast.LENGTH_SHORT).show();
                }
            }
        }

        if (id == R.id.btnMult){
            if (currentExpr.length()==0){
                return;
            }
            char lastChar = currentExpr.charAt(currentExpr.length() - 1);
            if (lastChar == '+' || lastChar == '-' || lastChar == '*' || lastChar == '/') {
                return;
            }
            if (currentOp == 0){
                currentOp++;
                currentExpr += "*";
                updateExpression();
            } else {
                String result = calculator.evaluate(currentExpr, "*");
                if (result != "Error"){
                    currentExpr = result;
                    currentOp = 0;
                    currentOp++;
                    currentExpr += "*";
                    updateExpression();
                } else {
                    Toast.makeText(getApplicationContext(), "Error!", Toast.LENGTH_SHORT).show();
                }
            }
        }
        if (id == R.id.btnDiv){
            if (currentExpr.length()==0){
                return;
            }
            char lastChar = currentExpr.charAt(currentExpr.length() - 1);
            if (lastChar == '+' || lastChar == '-' || lastChar == '*' || lastChar == '/') {
                return;
            }
            if (currentOp == 0){
                currentOp++;
                currentExpr += "/";
                updateExpression();
            } else {
                String result = calculator.evaluate(currentExpr, "/");
                if (result != "Error"){
                    currentExpr = result;
                    currentOp = 0;
                    currentOp++;
                    currentExpr += "/";
                    updateExpression();
                } else {
                    Toast.makeText(getApplicationContext(), "Error!", Toast.LENGTH_SHORT).show();
                }
            }
        }
        if (id == R.id.btnEquals){
            if (currentExpr.length()==0){
                return;
            }
            char lastChar = currentExpr.charAt(currentExpr.length() - 1);
            if (lastChar == '+' || lastChar == '-' || lastChar == '*' || lastChar == '/') {
                return;
            }
            if (currentOp == 0){
                return;
            } else {
                String result = calculator.evaluate(currentExpr, "=");
                if (result != "Error"){
                    currentExpr = result;
                    currentOp = 0;
                    updateExpression();
                } else {
                    Toast.makeText(getApplicationContext(), "Error!", Toast.LENGTH_SHORT).show();
                }
            }
        }
        if (id == R.id.btnDot){
            if (expression.length()==0){
                return;
            }
            char lastChar = currentExpr.charAt(currentExpr.length() - 1);
            if (lastChar == '+' || lastChar == '-' || lastChar == '*' || lastChar == '/' || lastChar=='.') {
                return;
            }
            currentExpr += ".";
            updateExpression();
        }

        if (id == R.id.btnPM){
            if (currentOp != 0){
                return;
            }
            String result = calculator.evaluate(currentExpr, "PM");
            if (result != "Error"){
                currentExpr = result;
                updateExpression();
            } else {
                Toast.makeText(getApplicationContext(), "Error!", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        if (id == R.id.btnSquare){
            if (currentOp != 0){
                return;
            }
            String result = calculator.evaluate(currentExpr, "SQ");
            if (result!="Error"){
                currentExpr = result;
                updateExpression();
            } else {
                Toast.makeText(getApplicationContext(), "Error!", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        if (id == R.id.btnRoot){
            if (currentOp != 0){
                return;
            }
            String result = calculator.evaluate(currentExpr, "SQRT");
            if (result!="Error"){
                currentExpr = result;
                updateExpression();
            } else {
                return;
            }
        }

        if (id == R.id.btnSin){
            if (currentOp != 0){
                return;
            }
            String result = calculator.evaluate(currentExpr, "Sin");
            if (result!="Error"){
                currentExpr = result;
                updateExpression();
            } else {
                return;
            }
        }

        if (id == R.id.btnCos){
            if (currentOp != 0){
                return;
            }
            String result = calculator.evaluate(currentExpr, "Cos");
            if (result!="Error"){
                currentExpr = result;
                updateExpression();
            } else {
                return;
            }
        }

    }


    public void findXML(){
        btn0 = findViewById(R.id.btn0);
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);
        btn4 = findViewById(R.id.btn4);
        btn5 = findViewById(R.id.btn5);
        btn6 = findViewById(R.id.btn6);
        btn7 = findViewById(R.id.btn7);
        btn8 = findViewById(R.id.btn8);
        btn9 = findViewById(R.id.btn9);
        btnDot = findViewById(R.id.btnDot);
        btnPlus = findViewById(R.id.btnPlus);
        btnMinus = findViewById(R.id.btnMinus);
        btnMult = findViewById(R.id.btnMult);
        btnDiv = findViewById(R.id.btnDiv);
        btnEquals = findViewById(R.id.btnEquals);
        btnPM = findViewById(R.id.btnPM);
        btnC = findViewById(R.id.btnC);
        btnSquare = findViewById(R.id.btnSquare);
        btnSin = findViewById(R.id.btnSin);
        btnCos = findViewById(R.id.btnCos);
        btnRoot = findViewById(R.id.btnRoot);
        expression = findViewById(R.id.expression);
        screenLayout = findViewById(R.id.screenLayout);
    }

    @SuppressLint("ClickableViewAccessibility")
    public void setListener(){
        btn0.setOnClickListener(this);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
        btn6.setOnClickListener(this);
        btn7.setOnClickListener(this);
        btn8.setOnClickListener(this);
        btn9.setOnClickListener(this);
        btnDot.setOnClickListener(this);
        btnPlus.setOnClickListener(this);
        btnMinus.setOnClickListener(this);
        btnMult.setOnClickListener(this);
        btnDiv.setOnClickListener(this);
        btnEquals.setOnClickListener(this);
        btnPM.setOnClickListener(this);
        btnC.setOnClickListener(this);

        btnSquare.setOnClickListener(this);
        btnRoot.setOnClickListener(this);
        btnSin.setOnClickListener(this);
        btnCos.setOnClickListener(this);
    }


    public boolean checkExprLength(){
        if (currentExpr.length()<50){
            return true;
        } else {
            Toast.makeText(getApplicationContext(), "Max expression length exceeded", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public boolean checkZeroInput(){
        if (currentExpr.length()==0){
            return false;
        }
        if (currentExpr.length() == 1 && currentExpr.charAt(0) == '0') {
            return true;
        } else {
            char lastChar = currentExpr.charAt(currentExpr.length() - 1);
            if (lastChar == '0' && "+-*/".contains(String.valueOf(currentExpr.charAt(currentExpr.length() - 2)))) {
                return true;
            } else {
                return false;
            }
        }
    }

    public void updateExpression(){
        expression.setText(currentExpr);
        btnSquare.setOnClickListener(this);
        btnRoot.setOnClickListener(this);
        btnSin.setOnClickListener(this);
        btnCos.setOnClickListener(this);
    }

    public void clearExpression(){
        currentExpr = "";
        currentOp = 0;
        updateExpression();
    }


    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {

        savedInstanceState.putString("currentExpr", currentExpr);
        savedInstanceState.putInt("currentOp", currentOp);
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {

        super.onRestoreInstanceState(savedInstanceState);
        currentExpr = savedInstanceState.getString("currentExpr");
        currentOp = savedInstanceState.getInt("currentOp");
        updateExpression();
    }




}