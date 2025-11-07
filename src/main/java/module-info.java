module com.example.doangamecolat {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.example.doangamecolat to javafx.fxml;
    exports com.example.doangamecolat;
}