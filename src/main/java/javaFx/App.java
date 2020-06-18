package javaFx;

import dataBase.Connector;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent firstPaneLoader = FXMLLoader.load(getClass().getResource("/LogIn.fxml"));
        Scene firstScene = new Scene(firstPaneLoader);
        primaryStage.setScene(firstScene);
        primaryStage.setTitle("Sundhedsplatformen ");
        primaryStage.show();
        primaryStage.setOnCloseRequest(event -> {
            Connector.colseConn();
            Platform.exit();
            System.exit(0);
        });
    }
}