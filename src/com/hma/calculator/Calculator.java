package com.hma.calculator;


import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class Calculator implements Initializable {

    //fxml components
    @FXML
    private Label temp;
    @FXML
    private Label result;
    @FXML
    private GridPane grid;


    static boolean finish = false;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        List<Node> nodes = grid.getChildren();

        ButtonAction action = new ButtonAction();

        for (Node node : nodes) {
            if(node instanceof Button) {
                Button btn = (Button) node;
                btn.setOnAction(action);
            }
        }

        clear();
    }

    //for pressing button
    private void pressButton(String value) {

        switch (value) {
            case "C":
                clear();
                break;
            case "+/-":
                doNegative();
                break;
            case "%":
                doPercent();
                break;
            case "=":
                calculate();

                break;
            case ".":
                doDecimal();
                break;
            case "+":
            case "-":
            case "x":
            case "/":
                pressOperator(value);
                break;
            default:
                pressNumber(value);
                break;
        }

    }

    //when press cancel
    private void clear() {
        temp.setText("");
        result.setText("0");
        finish=false;
    }

    //when press +/-
    private void doNegative() {
        String value = result.getText();
        if(!"0".equals(value)) {
            if(value.startsWith("-")) {
                result.setText(value.substring(1));
            } else {
                result.setText("-".concat(value));
            }
        }
    }

    //when press number
    private void pressNumber(String value) {

        String resVal = result.getText();

        if("0".equals(resVal)) {
            result.setText(value);
        } else {
            if(finish==true){
                result.setText(value);
                finish=false;
            }
            else{
                result.setText(resVal.concat(value));
            }
        }

    }

    //
    private void doDecimal() {
        String resVal = result.getText();
        if(!resVal.contains(".")) {
            result.setText(resVal.concat("."));
        }
    }

    //when press operators
    private void pressOperator(String ope) {
        String resVal = result.getText();
        String tmpVal = temp.getText();
        String var = null;
        if(tmpVal.isEmpty()) {
            // set temp with result and Operator
            var = String.format("%s %s", resVal, ope);
        } else {
            // calculate with temp and result
            String [] array = tmpVal.split(" ");
            String tmpResult = calculate(array[0], resVal, array[1]);
            // set result to temp and operator
            var = String.format("%s %s", tmpResult, ope);
        }
        // set temp
        temp.setText(var);
        // set result with 0
        result.setText("0");
    }

    //when press =
    private String calculate(String s1, String s2, String ope) {
        Double d1 = Double.valueOf(s1);
        Double d2 = Double.valueOf(s2);
        Double d3 = 0d;

        switch (ope) {
            case "+":
                d3 = d1 + d2;
                break;
            case "-":
                d3 = d1 - d2;
                break;
            case "x":
                d3 = d1 * d2;
                break;
            case "/":
                d3 = d1 / d2;
                break;

            default:
                break;
        }
        return d3.toString();
    }

    private void calculate() {
        String resVal = result.getText();
        String tmpVal = temp.getText();
        String var = null;
        if(tmpVal.isEmpty()) {

            var = resVal;
        } else {

            String [] array = tmpVal.split(" ");
            String tmpResult = calculate(array[0], resVal, array[1]);



            var = String.format("%s", tmpResult);
        }

        temp.setText("");

        result.setText(var);
        finish=true;
    }

    //press %
    private void doPercent() {
        String resVal = result.getText();
        Double d = Double.valueOf(resVal);
        Double perVal=d/100;
        result.setText(String.format("%s",perVal));
        finish=true;
    }

    class ButtonAction implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            Button b = (Button) event.getSource();
            String value = b.getText();
            pressButton(value);
        }

    }

}
