package com.mycompany.typingtutor;

import java.util.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;



public class App extends Application {

    @Override
    public void start(Stage stage) {
        //buttons
        Button nextButton = new Button("Next"); //stops error for space
        nextButton.setFocusTraversable(false);
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
        Label sampleLabel = new Label(sampleTexts[0]);           
        Label responseLabel = new Label();    
        Label keyLabel = new Label("Key pressed: ");   
        Label counterLabel = new Label("1 of 6");        
        Label scoreLabel = new Label(); 
        VBox textBox = new VBox(10, scoreLabel, counterLabel, keyLabel, 
                sampleLabel, responseLabel);
        //keyboard
        Map<KeyCode, Button> keyMap = new HashMap<>();
        GridPane grid = new GridPane();
        grid.setHgap(4);
        grid.setVgap(4);

        String[][] rows = {
            {"Q","W","E","R","T","Y","U","I","O","P"},
            {"A","S","D","F","G","H","J","K","L"},
            {"Z","X","C","V","B","N","M",},
            {"Shift","Space",".","<-"}
        }; //key names for text on button
        
        for (int row = 0; row < rows.length; row++) {
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
        //style sets
        scene.setOnKeyPressed(event -> {
            KeyCode code = event.getCode();
            Button button = keyMap.get(code); //finds button on map and sets it to key pressed
            if (button != null) { //null needed for extra keys
                button.setStyle("-fx-background-color: yellow;");
                keyLabel.setText("Key pressed: " + code.getName());
            }
        });
        
        scene.setOnKeyReleased(event -> {
            KeyCode code = event.getCode();
            Button button = keyMap.get(code);
            if (button != null) { 
                button.setStyle(""); //default
            }
        });
        
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}