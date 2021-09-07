module ku.cs {
    requires javafx.controls;
    requires javafx.fxml;
    //requires org.junit.jupiter.api;
    requires com.opencsv;

    opens ku.cs to javafx.fxml;
    exports ku.cs;

    exports ku.cs.controllers;
    opens ku.cs.controllers to javafx.fxml;
    exports ku.cs.controllers.signup;
    opens ku.cs.controllers.signup to javafx.fxml;

}
