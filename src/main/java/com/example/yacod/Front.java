package com.example.yacod;

import com.example.yacod.DataBase.Postgre;
import com.example.yacod.View.AdminFront;
import com.example.yacod.View.AnalystFront;
import com.example.yacod.View.IntroFront;
import com.example.yacod.View.ManagerFront;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

public class Front  extends Application {
    public static String password = "";
    public static String login ="";
    public static String lvl ="";
    public static boolean isStart = true;

    public static Pane pane;
    public static Group root;
    public static Scene scene;


    @Override
    public void start(Stage stage) throws FileNotFoundException {
        root = new Group();
        scene = new Scene(root, 1200, 800);
        stage.initStyle(StageStyle.DECORATED);
        pane = IntroFront.getStartFront();
        root.getChildren().add(pane);
        stage.setTitle("YACOD");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException, IOException, ParseException {
        launch();
    }

    public static void login(String pass, String login) throws SQLException, FileNotFoundException {
        System.out.println("login:" + login);
        System.out.println("pass:" + pass);
//        login = "NovikovMihail@yacod.com";
//        pass = "t7vPZC";
        Postgre postgre = new Postgre(login,pass);
        Front.login = login;
        Front.password = pass;
//        Front.login = "NovikovMihail@yacod.com";
//        Front.password = "t7vPZC";
        Front.lvl = postgre.getRole();
        System.out.println(Front.lvl);
        System.out.println("OK");
        root.getChildren().remove(pane);
        pane = AdminFront.getStartFront();
        root.getChildren().add(pane);
        if(Front.lvl.equals("CODAdmin")){
            root.getChildren().remove(pane);
            pane = AdminFront.getStartFront();
            root.getChildren().add(pane);
        }
        else if (Front.lvl.equals("CODAnalyst")) {
            root.getChildren().remove(pane);
            pane = AnalystFront.getStartFront();
            root.getChildren().add(pane);
        }
        else if (Front.lvl.equals("CODManager")){
            root.getChildren().remove(pane);
            pane = ManagerFront.getStartFront();
            root.getChildren().add(pane);
        }
    }
    public static void exit() throws FileNotFoundException {
        root.getChildren().remove(pane);
        pane = IntroFront.getStartFront();
        root.getChildren().add(pane);
    }
}