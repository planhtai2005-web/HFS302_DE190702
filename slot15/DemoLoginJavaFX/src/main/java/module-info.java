module com.fu.demologinjavafx {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.fu.demologinjavafx to javafx.fxml;
    exports com.fu.demologinjavafx;

    exports com.fu.ch08.b1;
    opens com.fu.ch08.b1 to javafx.fxml;

    exports com.fu.ch08.b2;
}