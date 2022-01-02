package com.example.NumericalProject;

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

/***
 * the controller for our main view
 */
public class MainController implements Initializable {

    //the reference for the components in the fxml file
    @FXML
    private ImageView exit, menu;

    @FXML
    private AnchorPane opacityPane, drawerPane, MethodPane, UserManualPane, CurrentMethodPane;

    @FXML
    private JFXButton NaiveButton, JordanButton, LUButton, JacobiButton, SeidelButton, BisectionButton,
            FalsePositionButton, SecantButton, NewtonButton, FixedPointButton, UserButton, LastButton;

    @FXML
    private DialogPane UserManual;

    @FXML
    private ScrollPane Scroll;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //the scroll pane settings
        Scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        Scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        CurrentMethodPane = MakePane("/Methods/LinearEquations/NaiveGauss.fxml");
        MethodPane.getChildren().add(CurrentMethodPane);

        LastButton = NaiveButton;
        LastButton.setStyle("-fx-background-color:rgba(105, 152, 171,1)");

        //load the method's view based on the user's choice and change the color of pressed button
        NaiveButton.setOnMouseClicked(e -> ButtonActivity(NaiveButton, "/Methods/LinearEquations/NaiveGauss.fxml"));
        JordanButton.setOnMouseClicked(e -> ButtonActivity(JordanButton, "/Methods/LinearEquations/GaussJordan.fxml"));
        LUButton.setOnMouseClicked(e -> ButtonActivity(LUButton, "/Methods/LinearEquations/LUDecomposition.fxml"));
        JacobiButton.setOnMouseClicked(e -> ButtonActivity(JacobiButton, "/Methods/LinearEquations/JacobiIteration.fxml"));
        SeidelButton.setOnMouseClicked(e -> ButtonActivity(SeidelButton, "/Methods/LinearEquations/GaussSeidel.fxml"));
        BisectionButton.setOnMouseClicked(e -> ButtonActivity(BisectionButton, "/Methods/NonLinearEquations/Bisection.fxml"));
        FalsePositionButton.setOnMouseClicked(e -> ButtonActivity(FalsePositionButton, "/Methods/NonLinearEquations/FalsePosition.fxml"));
        SecantButton.setOnMouseClicked(e -> ButtonActivity(SecantButton, "/Methods/NonLinearEquations/Secant.fxml"));
        FixedPointButton.setOnMouseClicked(e -> ButtonActivity(FixedPointButton, "/Methods/NonLinearEquations/FixedPoint.fxml"));
        NewtonButton.setOnMouseClicked(e -> ButtonActivity(NewtonButton, "/Methods/NonLinearEquations/NewtonRaphson.fxml"));


        //making the settings for the exit button
        exit.setOnMouseClicked(e -> System.exit(0));
        exit.setOnMouseMoved(e -> exit.setBlendMode(BlendMode.HARD_LIGHT));
        exit.setOnMouseExited(e -> exit.setBlendMode(null));

        //opening the user manual if the user wants it and closing if he pressed OK
        UserManualPane.setVisible(false);
        Node OK = UserManual.lookupButton(ButtonType.OK);
        OK.setOnMouseClicked(e -> UserManualPane.setVisible(false));
        UserButton.setOnMouseClicked(e -> UserManualPane.setVisible(true));

        opacityPane.setVisible(false);

        GUIUtilities.FadeTransition(0.5, 1, 0, opacityPane);
        GUIUtilities.TranslateTransition(0.5, -600, drawerPane);

        //the transitions settings when viewing the menu
        menu.setOnMouseClicked(e -> {
            if (opacityPane.isVisible()) {

                GUIUtilities.FadeTransition(0.5, 0.3, 0, opacityPane);
                GUIUtilities.TranslateTransition(0.5, -600, drawerPane);
                GUIUtilities.RotateTransition(0.4, 90, 0, menu);

            } else {

                UserManualPane.setVisible(false);
                GUIUtilities.FadeTransition(0.5, 0, 0.3, opacityPane);
                opacityPane.setVisible(true);
                GUIUtilities.TranslateTransition(0.5, 600, drawerPane);
                GUIUtilities.RotateTransition(0.4, 0, 90, menu);

            }
        });

        opacityPane.setOnMouseClicked(event -> {

            GUIUtilities.FadeTransition(0.5, 0.3, 0, opacityPane);
            GUIUtilities.TranslateTransition(0.5, -600, drawerPane);
            GUIUtilities.RotateTransition(0.4, 90, 0, menu);

        });
    }

    /***
     * to load the method fxml when calling it
     * @param Path the path of the method's fxml
     * @return the view in  an anchor pane
     */
    private AnchorPane MakePane(String Path) {
        Parent root = null;
        FXMLLoader loader = new FXMLLoader(getClass().getResource(Path));
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return (AnchorPane) root;
    }

    private void ButtonActivity(JFXButton Button, String Path) {
        LastButton.setStyle("-fx-background-color:rgba(105, 152, 171,0)");
        LastButton = Button;
        LastButton.setStyle("-fx-background-color:rgba(105, 152, 171,1)");

        MethodPane.getChildren().remove(0);
        CurrentMethodPane = MakePane(Path);
        MethodPane.getChildren().add(CurrentMethodPane);
    }
}
