package com.example.yacod.View;

import com.example.yacod.DataBase.Postgre;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.SQLException;

public class AddPhys {
    public static void addPhys(){
        Group root_add = new Group();
        Scene scene_add = new Scene(root_add, 450, 342);
        Stage newWindow = new Stage();
        newWindow.initStyle(StageStyle.DECORATED);
        Pane pane1 = new Pane();
        pane1.setPrefSize(450,342);
        pane1.setLayoutX(0);
        pane1.setLayoutY(0);
        root_add.getChildren().addAll(pane1);

        FileInputStream Url1;

        try {
            Url1 = new FileInputStream("png/addPhys.png");
        } catch (
                FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        Image url1 = new Image(Url1);
        ImageView front1 = new ImageView(url1);
        front1.setX(0);
        front1.setY(0);
        pane1.getChildren().add(front1);


        TextField name = new TextField();
        name.setBackground(null);
        name.setFont(Font.font("STXihei", 16));
        name.setLayoutX(154);
        name.setLayoutY(95);
        name.setPrefSize(215,32);
        pane1.getChildren().add(name);

        TextField mail =  new TextField();
        mail.setBackground(null);
        mail.setFont(Font.font("STXihei", 16));
        mail.setLayoutX(154);
        mail.setLayoutY(139);
        mail.setPrefSize(215,32);
        pane1.getChildren().add(mail);

        TextField tel = new TextField();
        tel.setBackground(null);
        tel.setFont(Font.font("STXihei", 16));
        tel.setLayoutX(154);
        tel.setLayoutY(183);
        tel.setPrefSize(215,32);
        pane1.getChildren().add(tel);

        TextField inn =new TextField();
        inn.setBackground(null);
        inn.setFont(Font.font("STXihei", 16));
        inn.setLayoutX(154);
        inn.setLayoutY(226);
        inn.setPrefSize(215,32);
        pane1.getChildren().add(inn);

        Button save = new Button();
        save.setBackground(null);
        save.setLayoutX(89);
        save.setLayoutY(309);
        save.setPrefSize(113,14);
        pane1.getChildren().add(save);
        save.setOnAction(t->{
            String name_text = name.getText();
            String mail_text = mail.getText();
            String tel_text = tel.getText();
            String inn_text = inn.getText();
            if(!name_text.isEmpty()&&!mail_text.isEmpty()&&!tel_text.isEmpty()
                    &&!inn_text.isEmpty()){
                try {
                    Postgre.addPhysClient(name_text,tel_text,inn_text,mail_text);
                    newWindow.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

            }
        });

        Button back = new Button();
        back.setBackground(null);
        back.setLayoutX(265);
        back.setLayoutY(309);
        back.setPrefSize(80,14);
        pane1.getChildren().add(back);
        back.setOnAction(t->{
            newWindow.close();
        });


        newWindow.setTitle("Добавление Физ лица");
        newWindow.setScene(scene_add);
        newWindow.show();

    }
}
