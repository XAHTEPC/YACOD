package com.example.yacod.View;

import com.example.yacod.DataBase.Postgre;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.SQLException;

public class AddLegal {
    public static void addLegal(){
        Group root_add = new Group();
        Scene scene_add = new Scene(root_add, 450, 529);
        Stage newWindow = new Stage();
        newWindow.initStyle(StageStyle.DECORATED);
        Pane pane1 = new Pane();
        pane1.setPrefSize(450,529);
        pane1.setLayoutX(0);
        pane1.setLayoutY(0);
        root_add.getChildren().addAll(pane1);

        FileInputStream Url1;

        try {
            Url1 = new FileInputStream("png/addLegal.png");
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
        name.setLayoutX(195);
        name.setLayoutY(95);
        name.setPrefSize(215,32);
        pane1.getChildren().add(name);

        TextField orgName =  new TextField();
        orgName.setBackground(null);
        orgName.setFont(Font.font("STXihei", 16));
        orgName.setLayoutX(195);
        orgName.setLayoutY(139);
        orgName.setPrefSize(215,32);
        pane1.getChildren().add(orgName);

        TextField inn = new TextField();
        inn.setBackground(null);
        inn.setFont(Font.font("STXihei", 16));
        inn.setLayoutX(195);
        inn.setLayoutY(183);
        inn.setPrefSize(215,32);
        pane1.getChildren().add(inn);

        TextField kpp =new TextField();
        kpp.setBackground(null);
        kpp.setFont(Font.font("STXihei", 16));
        kpp.setLayoutX(195);
        kpp.setLayoutY(226);
        kpp.setPrefSize(215,32);
        pane1.getChildren().add(kpp);

        TextField ogrn = new TextField();
        ogrn.setBackground(null);
        ogrn.setFont(Font.font("STXihei", 16));
        ogrn.setLayoutX(195);
        ogrn.setLayoutY(271);
        ogrn.setPrefSize(215,32);
        pane1.getChildren().add(ogrn);

        TextField tel =new TextField();
        tel.setBackground(null);
        tel.setFont(Font.font("STXihei", 16));
        tel.setLayoutX(195);
        tel.setLayoutY(315);
        tel.setPrefSize(215,32);
        pane1.getChildren().add(tel);

        TextField mail =new TextField();
        mail.setBackground(null);
        mail.setFont(Font.font("STXihei", 16));
        mail.setLayoutX(195);
        mail.setLayoutY(359);
        mail.setPrefSize(215,32);
        pane1.getChildren().add(mail);

        TextField address =new TextField();
        address.setBackground(null);
        address.setFont(Font.font("STXihei", 16));
        address.setLayoutX(195);
        address.setLayoutY(403);
        address.setPrefSize(215,32);
        pane1.getChildren().add(address);

        Button save = new Button();
        save.setBackground(null);
        save.setLayoutX(97);
        save.setLayoutY(487);
        save.setPrefSize(113,14);
        pane1.getChildren().add(save);
        save.setOnAction(t->{
            String name_text = name.getText();
            String orgName_text = orgName.getText();
            String inn_text = inn.getText();
            String kpp_text = kpp.getText();
            String ogrn_text = ogrn.getText();
            String tel_text = tel.getText();
            String mail_text = mail.getText();
            String address_text = address.getText();
            if(!name_text.isEmpty()&&!orgName_text.isEmpty()&&!inn_text.isEmpty()
                &&!kpp_text.isEmpty()&&!ogrn_text.isEmpty()&&!tel_text.isEmpty()&&
                !mail_text.isEmpty()&&!address_text.isEmpty()){
                try {
                    Postgre.addLegalClient(name_text,orgName_text,inn_text,kpp_text,ogrn_text,tel_text,mail_text,address_text);
                    newWindow.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

            }
        });

        Button back = new Button();
        back.setBackground(null);
        back.setLayoutX(273);
        back.setLayoutY(487);
        back.setPrefSize(80,14);
        pane1.getChildren().add(back);
        back.setOnAction(t->{
            newWindow.close();
        });


        newWindow.setTitle("Добавление Юр лица");
        newWindow.setScene(scene_add);
        newWindow.show();

    }

}
