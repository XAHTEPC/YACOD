package com.example.yacod.View;

import com.example.yacod.Front;
import com.example.yacod.Logic.Toast;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.SQLException;

public class IntroFront {
    static Button enter;
    public static PasswordField password;
    public static TextField login;
    public static Pane getStartFront() throws FileNotFoundException {
        Pane pane = new Pane();
        pane.setLayoutX(0);
        pane.setLayoutY(0);
        FileInputStream Url;
        Url = new FileInputStream("png/login.png");
        Image url = new Image(Url);
        ImageView front = new ImageView(url);
        front.setX(0);
        front.setY(0);
        pane.getChildren().add(front);

        login = new TextField();
        login.setPrefSize(380,63);
        login.setLayoutX(410);
        login.setLayoutY(260);
        login.setPromptText("login");
        login.setFont(Font.font("STXihei", 20));
        login.setBackground(null);
        pane.getChildren().add(login);

        password = new PasswordField();
        password.setPrefSize(380,63);
        password.setBackground(null);
        password.setPromptText("password");
        password.setLayoutX(410);
        password.setLayoutY(375);
        password.setFont(Font.font("STXihei", 20));
        pane.getChildren().add(password);

        enter = new Button();
        enter.setBackground(null);
        enter.setLayoutX(400);
        enter.setLayoutY(473);
        enter.setPrefSize(400,63);
        enter.setOnAction(t->{
            try {
                Front.login(password.getText(),login.getText());

            } catch (SQLException | FileNotFoundException e) {
                System.out.println("ERROR");
                password.setText("");
                Toast.show("Неверный логин или пароль",pane,232,700);
            }
        });
        pane.getChildren().add(enter);

        return pane;
    }
}

