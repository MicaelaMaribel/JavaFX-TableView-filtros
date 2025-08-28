module com.mica.demo03_tablas_eventos {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.mica.demo03_tablas_eventos to javafx.fxml;
    exports com.mica.demo03_tablas_eventos;
}