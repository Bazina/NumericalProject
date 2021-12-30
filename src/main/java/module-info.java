module com.example.java_fx_tut {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.jfoenix;

    opens com.example.NumericalProject to javafx.fxml;
    exports com.example.NumericalProject;

    opens com.example.NumericalProject.MethodsControllers.LinearEquations to javafx.fxml;
    exports com.example.NumericalProject.MethodsControllers.LinearEquations;

    opens com.example.NumericalProject.MethodsControllers.NonLinearEquations to javafx.fxml;
    exports com.example.NumericalProject.MethodsControllers.NonLinearEquations;

    opens com.example.NumericalProject.EquationsParser to javafx.fxml;
    exports com.example.NumericalProject.EquationsParser;
    exports com.example.NumericalProject.Printers;
    opens com.example.NumericalProject.Printers to javafx.fxml;

}