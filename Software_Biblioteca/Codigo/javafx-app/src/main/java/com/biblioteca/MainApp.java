package com.biblioteca;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.stage.Stage;

import java.sql.Connection;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
    	
        Parent root = FXMLLoader.load(
                getClass().getResource("/com/biblioteca/view/Login.fxml")
        );

        Scene scene = new Scene(root);

        stage.setTitle("Biblioteca");
        stage.setScene(scene);
        stage.setResizable(true);
        stage.setMaximized(true);
        stage.show();
    }

    public static void main(String[] args) {
    	System.setProperty("prism.order", "sw"); //tratamento do erro de MESA/ZINK no Linux
    	
        launch(args);
    }
}