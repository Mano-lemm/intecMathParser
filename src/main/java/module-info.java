module be.personal {
    requires transitive javafx.controls;
    requires javafx.fxml;

    opens be.rekenmachine to javafx.fxml;
    exports be.rekenmachine;
}
