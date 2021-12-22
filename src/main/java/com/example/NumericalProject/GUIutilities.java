package com.example.NumericalProject;

import javafx.animation.FadeTransition;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.util.Duration;

/***
 * collection of transition for the app
 */
public class GUIutilities {
    //flag to prevent overlapping transitions
    static boolean closing = false;

    /***
     * Fade Transition
     * @param time the time for transition
     * @param From value of the transition to start from
     * @param To value of the transition to end at
     * @param Node the node to make the transition on
     */
    public static void FadeTransition(double time, double From, double To, Node Node) {
        if (closing) return;

        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(time), Node);
        fadeTransition.setFromValue(From);
        fadeTransition.setToValue(To);
        fadeTransition.play();

        if (Node.isVisible()) {
            fadeTransition.setOnFinished(e -> Node.setVisible(false));
        }
    }

    /***
     * Translate Transition
     * @param time the time for transition
     * @param XVal the new value on the X-axis
     * @param Node the node to make the transition
     */
    public static void TranslateTransition(double time, double XVal, Node Node) {
        if (closing) return;

        TranslateTransition translateTransition1 = new TranslateTransition(Duration.seconds(time), Node);
        translateTransition1.setByX(XVal);
        translateTransition1.play();
    }

    /***
     * Rotate Transition
     * @param time the time for transition
     * @param From value of the transition to start from
     * @param To value of the transition to end at
     * @param Node the node to make the transition on
     */
    public static void RotateTransition(double time, double From, double To, Node Node) {
        if (closing) return;
        closing = true;

        RotateTransition rotateTransition = new RotateTransition(Duration.seconds(time), Node);
        rotateTransition.setFromAngle(From);
        rotateTransition.setToAngle(To);
        rotateTransition.play();

        rotateTransition.setOnFinished(e -> closing = false);
    }
}
