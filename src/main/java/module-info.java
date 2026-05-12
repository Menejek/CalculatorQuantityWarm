module org.example.localcalc {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens org.example.localcalc to javafx.fxml;
    exports org.example.localcalc;
}