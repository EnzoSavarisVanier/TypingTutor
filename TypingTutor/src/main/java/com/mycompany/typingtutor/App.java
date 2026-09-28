/**
 *
 * @author Enzo Savaris
 * Assignment_01: Typing tutor
 * 28/09/2026
 */
package com.mycompany.typingtutor;

import java.util.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;



public class App extends Application {
    private int correctCount = 0;
    private int incorrectCount = 0;
    private int sampleIndex = 0;

    @Override
    public void start(Stage stage) {
        //buttons
        Button nextButton = new Button("Next");
        nextButton.setFocusTraversable(false); //stops error for space
        Button resetButton = new Button("Reset");
        resetButton.setFocusTraversable(false);
        VBox buttonsBox = new VBox(10, nextButton, resetButton);
        //labels
        String[] sampleTexts = {
            "Try typing this text. Do it as quickly and accurately as you can.",
            "Next type another line of input data.",
            "The quick brown fox jumps over the lazy dog.",
            "Five big quacking zephyrs jolt my wax bed.",
            "Sympathizing would fix Quaker objectives.",
            "A large fawn jumped quickly over white zinc boxes."
        };
        Label sampleLabel = new Label(sampleTexts[sampleIndex]);           
        Label responseLabel = new Label();    
        Label keyLabel = new Label("Key pressed: ");   
        Label counterLabel = new Label("1 of 6");        
        Label scoreLabel = new Label("Correct: 0 Incorrect: 0"); 
        VBox textBox = new VBox(10, scoreLabel, counterLabel, keyLabel, 
                sampleLabel, responseLabel);
        //keyboard
        Map<KeyCode, Button> keyMap = new HashMap<>();
        GridPane grid = new GridPane();
        grid.setHgap(4);
        grid.setVgap(4);

        String[][] rows = { //key names for text on button
            {"Q","W","E","R","T","Y","U","I","O","P"},
            {"A","S","D","F","G","H","J","K","L"},
            {"Z","X","C","V","B","N","M",},
            {"Shift","Space",".","<-"}
        };
        
        for (int row = 0; row < rows.length; row++) { //assign letters to keyboard buttons
            for (int col = 0; col < rows[row].length; col++) {
                String label = rows[row][col];
                Button button = new Button(label);
                button.setPrefWidth(50);
                button.setFocusTraversable(false);
                grid.add(button, col, row); //adds buttons with names
                
                KeyCode code;
                switch (label) { //key text becomes KeyCode value for detecting button presses
                    case "Shift":
                        code = KeyCode.SHIFT;
                        break;
                    case "Space":
                        code = KeyCode.SPACE;
                        break;
                    case ".":
                        code = KeyCode.PERIOD;
                        break;
                    case "<-":
                        code = KeyCode.BACK_SPACE;
                        break;
                    default:
                        code = KeyCode.valueOf(label);
                        break;
                }
                keyMap.put(code, button); //map stores all key buttons
            }
        }
        //scene
        VBox root = new VBox(10, buttonsBox, textBox, grid);
        Scene scene = new Scene(root, 600, 400);
        //keys and typed text
        StringBuilder typedText = new StringBuilder();
        scene.setOnKeyPressed(event -> {
            KeyCode code = event.getCode(); //gets key
            Button button = keyMap.get(code); //finds key button on map and sets it to key pressed
            if (button != null) { //null needed for extra keys
                button.setStyle("-fx-background-color: yellow;");
                keyLabel.setText("Key pressed: " + code.getName());
                keyLabel.setStyle(""); //default for not red
                switch (code) { //add text typed by the keyboard to responseLabel
                    case SHIFT: //empty shift needed to stop it from spelling "SHIFT"
                        break;
                    case BACK_SPACE:
                        if (typedText.length() > 0) {
                            if (typedText.length() - 1 < sampleTexts[sampleIndex].length()) { //text outside sample text scope is not correct or incorrect
                                if (typedText.charAt(typedText.length() - 1) == sampleTexts[sampleIndex].charAt(typedText.length() - 1)) {
                                    correctCount--;
                                } else {
                                    incorrectCount--;
                                }
                            }
                            typedText.deleteCharAt(typedText.length() - 1);
                        }
                        break;
                    case SPACE:
                        typedText.append(" "); //adds text
                        if (typedText.length() - 1 < sampleTexts[sampleIndex].length()) { //changes score
                            if (typedText.charAt(typedText.length() - 1) == sampleTexts[sampleIndex].charAt(typedText.length() - 1)) {
                                correctCount++;
                            } else {
                                incorrectCount++;
                            }
                        }
                        break;
                    case PERIOD:
                        typedText.append(".");
                        if (typedText.length() - 1 < sampleTexts[sampleIndex].length()) {
                            if (typedText.charAt(typedText.length() - 1) == sampleTexts[sampleIndex].charAt(typedText.length() - 1)) {
                                correctCount++;
                            } else {
                                incorrectCount++;
                            }
                        }
                        break;
                    default:
                        String letter = code.getName();
                        typedText.append(event.isShiftDown() ? letter.toUpperCase() : letter.toLowerCase());
                        if (typedText.length() - 1 < sampleTexts[sampleIndex].length()) {
                            if (typedText.charAt(typedText.length() - 1) == sampleTexts[sampleIndex].charAt(typedText.length() - 1)) {
                                correctCount++;
                            } else {
                                incorrectCount++;
                            }
                        }
                        break;
                }
                responseLabel.setText(typedText.toString()); //typed text under sample
                scoreLabel.setText("Correct: " + correctCount + " Incorrect: " + incorrectCount);
            } else { //when text not in case options
                keyLabel.setText("Not handled");
                keyLabel.setStyle("-fx-text-fill: red;");
            }
        });
        
        scene.setOnKeyReleased(event -> {
            KeyCode code = event.getCode();
            Button button = keyMap.get(code);
            if (button != null) { 
                button.setStyle(""); //removes yellow color
            }
        });
        //buttons
        nextButton.setOnAction(e -> {
            if (sampleIndex < sampleTexts.length - 1) { //does not go past 6
                sampleIndex++; //next sample text
                sampleLabel.setText(sampleTexts[sampleIndex]);
                typedText.setLength(0); //resets typed text
                responseLabel.setText("");
                counterLabel.setText((sampleIndex + 1) + " of " + sampleTexts.length);
                correctCount = 0;
                incorrectCount = 0;
                scoreLabel.setText("Correct: 0  Incorrect: 0");
                keyLabel.setText("Key pressed: ");
            }
        });
        resetButton.setOnAction(e -> {
            sampleIndex = 0; //first sample text
            sampleLabel.setText(sampleTexts[sampleIndex]);
            typedText.setLength(0);
            responseLabel.setText("");
            counterLabel.setText((sampleIndex + 1) + " of " + sampleTexts.length);
            correctCount = 0;
            incorrectCount = 0;
            scoreLabel.setText("Correct: 0  Incorrect: 0");
            keyLabel.setText("Key pressed: ");
        });
        
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}