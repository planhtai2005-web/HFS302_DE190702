module com.fu.demologinjavafx {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.fu.demologinjavafx to javafx.fxml;
    exports com.fu.demologinjavafx;

    exports com.fu.ch08.b1;
    exports com.fu.ch08.b2;
    exports com.fu.ch08.b3;
}
