package com.biblioteca.controller.home_pages.components;

import com.biblioteca.model.Livro;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

public class CardLivroController {

    @FXML private VBox cardRoot;
    @FXML private ImageView imageCapa;
    @FXML private Label labelTitulo;

    public void setLivro(Livro livro) {
        labelTitulo.setText(livro.getTitulo());

        // Carrega capa ou imagem padrão
        if (livro.getCaminhoCapa() != null) {
            File img = new File(livro.getCaminhoCapa());
            if (img.exists()) {
                imageCapa.setImage(new Image(img.toURI().toString()));
            }
        } else {
            var url = getClass().getResource("/com/biblioteca/image/icons/livro_padrao.png");
            if (url != null) imageCapa.setImage(new Image(url.toString()));
        }

        // Clique abre o arquivo
        cardRoot.setOnMouseClicked(e -> {
            try {
                Desktop.getDesktop().open(new File(livro.getCaminhoArquivo()));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }
}