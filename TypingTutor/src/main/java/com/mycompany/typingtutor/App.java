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
        Map<KeyCode, Button> keyMap = new HashMap<>();
        GridPane grid = new GridPane();
        grid.setHgap(4);
        grid.setVgap(4);

        String[][] rows = {
            {"Q","W","E","R","T","Y","U","I","O","P"},
            {"A","S","D","F","G","H","J","K","L"},
            {"Z","X","C","V","B","N","M",},
            {"Shift","Space",".","Backspace"}
        };
        
        for (int row = 0; row < rows.length; row++) {
            for (int col = 0; col < rows[row].length; col++) {
                String label = rows[row][col];
                Button button = new Button(label);
                button.setPrefWidth(40);
                button.setFocusTraversable(false); //stops error for space
                grid.add(button, col, row);
                
                KeyCode code;
                switch (label) {
                    case "Shift":
                        code = KeyCode.SHIFT;
                        break;
                    case "Space":
                        code = KeyCode.SPACE;
                        break;
                    case ".":
                        code = KeyCode.PERIOD;
                        break;
                    case "Backspace":
                        code = KeyCode.BACK_SPACE;
                        break;
                    default:
                        code = KeyCode.valueOf(label);
                        break;
                }
                keyMap.put(code, button);
            }
        }
        
        VBox root = new VBox(10, grid);
        Scene scene = new Scene(root, 600, 400);
        //style sets
        scene.setOnKeyPressed(event -> {
            KeyCode code = event.getCode();
            Button button = keyMap.get(code);
            button.setStyle("-fx-background-color: yellow;");
        });
        
        scene.setOnKeyReleased(event -> {
            KeyCode code = event.getCode();
            Button button = keyMap.get(code);
            button.setStyle(""); //default
        });
        
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}