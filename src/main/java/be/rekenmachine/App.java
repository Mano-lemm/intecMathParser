package be.rekenmachine;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {

    @FXML
    public Button DIV;
    @FXML
    private Button MINUS;
    @FXML 
    private Button PLUS;
    @FXML
    private Button MULT;
    @FXML
    private Button SOLVE;
    @FXML
    private AnchorPane textPane;
    @FXML
    private GridPane gridPane;

    private static Scene scene;

    private void addText(String text){
        Label label = (Label) textPane.getChildren().get(0);
        label.setText(label.getText() + text);
        textPane.getChildren().clear();
        textPane.getChildren().add(label);
    }

    @FXML
    private void clearText(){
        Label text = new Label("");
        text.setPrefHeight(20);
        text.setPrefWidth(400);
        text.setAlignment(Pos.CENTER);
        textPane.getChildren().clear();
        textPane.getChildren().add(text);
    }

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("rekenmachine"), 640, 480);
        stage.setTitle("Rekenmachine");
        stage.setScene(scene);
        stage.show();
    }

    public void initialize(){
        clearText();

        DIV.setOnAction(e -> {
            addText(" /");
        });
        PLUS.setOnAction(e -> {
            addText(" +");
        });
        MULT.setOnAction(e ->{
            addText(" *");
        });
        MINUS.setOnAction(e -> {
            addText(" -");
        });
        SOLVE.setOnAction(e -> {
            Label text = (Label) textPane.getChildren().get(0);
            Label nText = new Label(String.valueOf((int) (double) MathParser.parse(text.getText())));
            nText.prefHeight(text.getPrefHeight());
            nText.prefWidth(text.getPrefWidth());
            nText.setAlignment(text.getAlignment());
            textPane.getChildren().clear();
            textPane.getChildren().add(nText);
        });
    
        for (int i = 0; i < 10; i++) {
            Button b = (Button) gridPane.lookup("#button"+ i);
            b.setOnAction(e -> {
                addText(String.valueOf(b.getId().charAt(b.getId().length() - 1)));
            });
        }
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}