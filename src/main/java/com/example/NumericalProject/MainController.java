package com.example.NumericalProject;

import com.example.NumericalProject.MethodsControllers.NaiveGauss;
import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    private ImageView exit , menu ;

    @FXML
    private AnchorPane opacityPane, drawerPane , MethodPane , UserManualPane , CurrentMethodPane;

    @FXML
    private JFXButton NaiveButton , JordanButton , LUButton ,JacobiButton , SeidelButton , UserButton , LastButton;

    @FXML
    private DialogPane UserManual ;

    @FXML
    private ScrollPane Scroll ;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        Scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        CurrentMethodPane = MakePane("/Methods/NaiveGauss.fxml");
        MethodPane.getChildren().add(CurrentMethodPane) ;

        LastButton = NaiveButton ;
        LastButton.setStyle("-fx-background-color:rgba(105, 152, 171,1)");

        NaiveButton.setOnMouseClicked(e -> {
            LastButton.setStyle("-fx-background-color:rgba(105, 152, 171,0)");
            LastButton = NaiveButton ;
            LastButton.setStyle("-fx-background-color:rgba(105, 152, 171,1)");

            MethodPane.getChildren().remove(0);
            CurrentMethodPane = MakePane("/Methods/NaiveGauss.fxml");
            MethodPane.getChildren().add(CurrentMethodPane) ;
        });
        JordanButton.setOnMouseClicked(e -> {
            LastButton.setStyle("-fx-background-color:rgba(105, 152, 171,0)");
            LastButton = JordanButton ;
            LastButton.setStyle("-fx-background-color:rgba(105, 152, 171,1)");

            MethodPane.getChildren().remove(0);
            CurrentMethodPane = MakePane("/Methods/GaussJordan.fxml");
            MethodPane.getChildren().add(CurrentMethodPane) ;
        });
        LUButton.setOnMouseClicked(e -> {
            LastButton.setStyle("-fx-background-color:rgba(105, 152, 171,0)");
            LastButton = LUButton ;
            LastButton.setStyle("-fx-background-color:rgba(105, 152, 171,1)");

            MethodPane.getChildren().remove(0);
            CurrentMethodPane = MakePane("/Methods/LUDecomposition.fxml");
            MethodPane.getChildren().add(CurrentMethodPane) ;
        });
        JacobiButton.setOnMouseClicked(e -> {
            LastButton.setStyle("-fx-background-color:rgba(105, 152, 171,0)");
            LastButton = JacobiButton ;
            LastButton.setStyle("-fx-background-color:rgba(105, 152, 171,1)");

            MethodPane.getChildren().remove(0);
            CurrentMethodPane = MakePane("/Methods/JacobiIteration.fxml");
            MethodPane.getChildren().add(CurrentMethodPane) ;
        });
        SeidelButton.setOnMouseClicked(e -> {
            LastButton.setStyle("-fx-background-color:rgba(105, 152, 171,0)");
            LastButton = SeidelButton ;
            LastButton.setStyle("-fx-background-color:rgba(105, 152, 171,1)");

            MethodPane.getChildren().remove(0);
            CurrentMethodPane = MakePane("/Methods/GaussSeidel.fxml");
            MethodPane.getChildren().add(CurrentMethodPane) ;
        });

        exit.setOnMouseClicked(e -> System.exit(0));
        exit.setOnMouseMoved(e -> exit.setBlendMode(BlendMode.HARD_LIGHT));
        exit.setOnMouseExited(e -> exit.setBlendMode(null));

        UserManualPane.setVisible(false);
        Node OK = UserManual.lookupButton(ButtonType.OK);
        OK.setOnMouseClicked(e -> UserManualPane.setVisible(false));
        UserButton.setOnMouseClicked(e -> UserManualPane.setVisible(true));

        opacityPane.setVisible(false);

        GUIutilities.FadeTransition(0.5 , 1 , 0 , opacityPane);
        GUIutilities.TranslateTransition(0.5 , -600 , drawerPane);

        menu.setOnMouseClicked(e ->{
            if(opacityPane.isVisible()){

                GUIutilities.FadeTransition(0.5 , 0.3 , 0 , opacityPane);
                GUIutilities.TranslateTransition(0.5 , -600 , drawerPane);
                GUIutilities.RotateTransition(0.4 , 90 , 0 , menu);

            }else{

                UserManualPane.setVisible(false);
                GUIutilities.FadeTransition(0.5 , 0 , 0.3 , opacityPane);
                opacityPane.setVisible(true);
                GUIutilities.TranslateTransition(0.5 , 600 , drawerPane);
                GUIutilities.RotateTransition(0.4 , 0 , 90 , menu);

            }
        });

        opacityPane.setOnMouseClicked(event -> {

            GUIutilities.FadeTransition(0.5 , 0.3 , 0 , opacityPane);
            GUIutilities.TranslateTransition(0.5 , -600 , drawerPane);
            GUIutilities.RotateTransition(0.4 , 90 , 0 , menu);

        });
    }

    private AnchorPane MakePane(String Path){
        Parent root = null;
        FXMLLoader loader = new FXMLLoader(NaiveGauss.class.getResource(Path));
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return (AnchorPane) root ;
    }
}
