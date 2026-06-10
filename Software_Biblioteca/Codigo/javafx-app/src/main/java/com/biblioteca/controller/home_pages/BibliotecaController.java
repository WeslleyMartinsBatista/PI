package com.biblioteca.controller.home_pages;

import com.biblioteca.controller.home_pages.components.CardLivroController;
import com.biblioteca.model.Livro;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class BibliotecaController implements Initializable {

    @FXML private BorderPane mainPane;
    @FXML private ImageView backgroundImage;
    @FXML private FlowPane flowPaneLivros;

    // Futuramente vem do banco — por ora lista em memória
    private List<Livro> livros = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        backgroundImage.setEffect(new BoxBlur(20, 20, 3));
        Platform.runLater(() -> mainPane.requestFocus());
    }

    @FXML
    public void voltarHome(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/com/biblioteca/view/Home.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.getScene().setRoot(root);
    }

    @FXML
    public void adicionarLivro(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(
            getClass().getResource("/com/biblioteca/view/home_pages/AdicionarLivroModal.fxml")
        );
        Parent modal = loader.load();

        AdicionarLivroController ctrl = loader.getController();
        ctrl.setOnConfirmar(livro -> {
            livros.add(livro);
            adicionarCardNaGrade(livro);
        });

        Stage modalStage = new Stage();
        modalStage.setTitle("Adicionar Livro");
        modalStage.setScene(new Scene(modal));
        modalStage.initModality(Modality.WINDOW_MODAL);
        modalStage.initOwner(((Node) event.getSource()).getScene().getWindow());
        modalStage.setResizable(false);
        modalStage.show();
    }

    private void adicionarCardNaGrade(Livro livro) {
        try {
            FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/com/biblioteca/view/home_pages/components/CardLivro.fxml")
            );
            VBox card = loader.load();
            CardLivroController ctrl = loader.getController();
            ctrl.setLivro(livro);
            flowPaneLivros.getChildren().add(card);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}