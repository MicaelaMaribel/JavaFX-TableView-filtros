package com.mica.demo03_tablas_eventos;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.Arrays;
import java.util.List;

public class TablaPeliculas extends Application {

    private final ObservableList<Pelicula> peliculas = FXCollections.observableArrayList(
            new Pelicula(1, "Inception", "Christopher Nolan", Arrays.asList("Ciencia Ficción", "Acción"), 2010),
            new Pelicula(2, "Titanic", "James Cameron", Arrays.asList("Drama", "Romance"), 1997),
            new Pelicula(3, "The Dark Knight", "Christopher Nolan", Arrays.asList("Acción", "Crimen"), 2008),
            new Pelicula(4, "Forrest Gump", "Robert Zemeckis", Arrays.asList("Drama"), 1994)
    );

    private FilteredList<Pelicula> datosFiltrados;
    private TextField campoBusqueda;

    @Override
    public void start(Stage primaryStage) {
        TableView<Pelicula> tabla = new TableView<>();

        // Columnas
        TableColumn<Pelicula, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Pelicula, String> tituloCol = new TableColumn<>("Título");
        tituloCol.setCellValueFactory(new PropertyValueFactory<>("titulo"));

        TableColumn<Pelicula, String> directorCol = new TableColumn<>("Director");
        directorCol.setCellValueFactory(new PropertyValueFactory<>("director"));

        TableColumn<Pelicula, List<String>> generoCol = new TableColumn<>("Géneros");
        generoCol.setCellValueFactory(new PropertyValueFactory<>("generos"));
        //####
        generoCol.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(List<String> generos, boolean empty) {
                super.updateItem(generos, empty);
                setText(empty || generos == null ? null : String.join(", ", generos));
            }
        });

        TableColumn<Pelicula, Integer> anioCol = new TableColumn<>("Año");
        anioCol.setCellValueFactory(new PropertyValueFactory<>("anio"));

        tabla.getColumns().addAll(idCol, tituloCol, directorCol, generoCol, anioCol);

        // Datos y filtro
        datosFiltrados = new FilteredList<>(peliculas, p -> true);
        tabla.setItems(datosFiltrados);

        // Búsqueda
        campoBusqueda = new TextField();
        campoBusqueda.setPromptText("Buscar por título o director...");
        campoBusqueda.setOnKeyReleased(e -> actualizarFiltros());

        Button reset = new Button("Reset");
        reset.setOnAction(e -> {
            campoBusqueda.clear();
            actualizarFiltros();
        });

        HBox barraBusqueda = new HBox(10, new Label("Buscar:"), campoBusqueda, reset);

        // Evento doble clic
        tabla.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2
                    && !tabla.getSelectionModel().isEmpty()) {
                mostrarDetalles(tabla.getSelectionModel().getSelectedItem(), primaryStage);
            }
        });

        VBox root = new VBox(10, barraBusqueda, tabla);
        root.setPadding(new Insets(10));

        Scene scene = new Scene(root, 650, 400);
        primaryStage.setTitle("Tabla de Películas con Filtros");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void actualizarFiltros() {
        String texto = campoBusqueda.getText().toLowerCase();
        datosFiltrados.setPredicate(p ->
                texto.isEmpty()
                        || p.getTitulo().toLowerCase().contains(texto)
                        || p.getDirector().toLowerCase().contains(texto));
    }

    private void mostrarDetalles(Pelicula peli, Stage owner) {
        Stage modal = new Stage();
        modal.initOwner(owner);
        modal.initModality(Modality.APPLICATION_MODAL);
        modal.setTitle("Detalles de Película");

        VBox vbox = new VBox(10,
                new Label("ID: " + peli.getId()),
                new Label("Título: " + peli.getTitulo()),
                new Label("Director: " + peli.getDirector()),
                new Label("Géneros: " + String.join(", ", peli.getGeneros())),
                new Label("Año: " + peli.getAnio()),
                new Button("Cerrar")
        );
        ((Button) vbox.getChildren().get(vbox.getChildren().size() - 1))
                .setOnAction(e -> modal.close());

        vbox.setPadding(new Insets(15));

        modal.setScene(new Scene(vbox, 300, 200));
        modal.showAndWait();
    }

    public static void main(String[] args) {
        launch();
    }
}