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

    opens com.example.NumericalProject.MethodsControllers to javafx.fxml;
    exports com.example.NumericalProject.MethodsControllers;
}