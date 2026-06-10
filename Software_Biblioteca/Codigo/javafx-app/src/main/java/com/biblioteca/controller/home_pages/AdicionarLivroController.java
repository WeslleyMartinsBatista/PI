package com.biblioteca.controller.home_pages;

import com.biblioteca.model.Livro;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.*;
import java.util.ResourceBundle;
import java.util.function.Consumer;

public class AdicionarLivroController implements Initializable {

    @FXML private TextField fieldTitulo;
    @FXML private TextField fieldAutor;
    @FXML private TextField fieldCaminhoArquivo;
    @FXML private TextField fieldCaminhoCapa;
    @FXML private RadioButton radioCopiar;
    @FXML private RadioButton radioMover;

    private File arquivoSelecionado;
    private File capaSelecionada;

    // Callback: quando confirmar, envia o Livro criado de volta ao BibliotecaController
    private Consumer<Livro> onConfirmar;

    // Diretório interno da biblioteca (dentro do projeto/app)
    private static final String DIR_BIBLIOTECA = "biblioteca/livros/";
    private static final String DIR_CAPAS      = "biblioteca/capas/";

    public void setOnConfirmar(Consumer<Livro> callback) {
        this.onConfirmar = callback;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Agrupa os radio buttons
        ToggleGroup group = new ToggleGroup();
        radioCopiar.setToggleGroup(group);
        radioMover.setToggleGroup(group);

        // Cria os diretórios se não existirem
        new File(DIR_BIBLIOTECA).mkdirs();
        new File(DIR_CAPAS).mkdirs();
    }

    @FXML
    private void selecionarArquivo() {
        FileChooser fc = new FileChooser();
        fc.setTitle("Selecionar arquivo");
        fc.getExtensionFilters().add(
            new FileChooser.ExtensionFilter("Documentos", "*.pdf", "*.docx")
        );
        File f = fc.showOpenDialog(fieldCaminhoArquivo.getScene().getWindow());
        if (f != null) {
            arquivoSelecionado = f;
            fieldCaminhoArquivo.setText(f.getAbsolutePath());
            // Preenche o título com o nome do arquivo se estiver vazio
            if (fieldTitulo.getText().isBlank()) {
                fieldTitulo.setText(f.getName().replaceFirst("[.][^.]+$", ""));
            }
        }
    }

    @FXML
    private void selecionarCapa() {
        FileChooser fc = new FileChooser();
        fc.setTitle("Selecionar capa");
        fc.getExtensionFilters().add(
            new FileChooser.ExtensionFilter("Imagens", "*.png", "*.jpg", "*.jpeg")
        );
        File f = fc.showOpenDialog(fieldCaminhoCapa.getScene().getWindow());
        if (f != null) {
            capaSelecionada = f;
            fieldCaminhoCapa.setText(f.getAbsolutePath());
        }
    }

    @FXML
    private void confirmar() {
        if (fieldTitulo.getText().isBlank() || arquivoSelecionado == null) {
            mostrarAlerta("Preencha o título e selecione um arquivo.");
            return;
        }

        try {
            // Copia ou move o arquivo para o diretório da biblioteca
            Path destArquivo = Paths.get(DIR_BIBLIOTECA + arquivoSelecionado.getName());
            Path destCapa    = capaSelecionada != null
                ? Paths.get(DIR_CAPAS + capaSelecionada.getName())
                : null;

            if (radioCopiar.isSelected()) {
                Files.copy(arquivoSelecionado.toPath(), destArquivo,
                           StandardCopyOption.REPLACE_EXISTING);
                if (capaSelecionada != null)
                    Files.copy(capaSelecionada.toPath(), destCapa,
                               StandardCopyOption.REPLACE_EXISTING);
            } else {
                Files.move(arquivoSelecionado.toPath(), destArquivo,
                           StandardCopyOption.REPLACE_EXISTING);
                if (capaSelecionada != null)
                    Files.move(capaSelecionada.toPath(), destCapa,
                               StandardCopyOption.REPLACE_EXISTING);
            }

            Livro livro = new Livro(
                0,
                fieldTitulo.getText().trim(),
                fieldAutor.getText().trim(),
                destArquivo.toAbsolutePath().toString(),
                destCapa != null ? destCapa.toAbsolutePath().toString() : null
            );

            if (onConfirmar != null) onConfirmar.accept(livro);

            fecharModal();

        } catch (IOException e) {
            mostrarAlerta("Erro ao processar arquivo: " + e.getMessage());
        }
    }

    @FXML
    private void cancelar() {
        fecharModal();
    }

    private void fecharModal() {
        ((Stage) fieldTitulo.getScene().getWindow()).close();
    }

    private void mostrarAlerta(String msg) {
        Alert alert = new Alert(Alert.AlertType.WARNING, msg, ButtonType.OK);
        alert.setHeaderText(null);
        alert.showAndWait();
    }
}