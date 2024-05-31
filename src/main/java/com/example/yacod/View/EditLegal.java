package com.example.yacod.View;

import com.example.yacod.DataBase.Postgre;
import com.example.yacod.Front;
import com.example.yacod.Model.LegalClient;
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

public class EditLegal {
    public static void editLegal(String id_legal, int fl) throws SQLException {
        LegalClient legalClient = Postgre.getLegalClientbyId(id_legal);
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
            Url1 = new FileInputStream("png/editLegal.png");
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
        name.setText(legalClient.name);
        name.setFont(Font.font("STXihei", 16));
        name.setLayoutX(195);
        name.setLayoutY(95);
        name.setPrefSize(215,32);
        pane1.getChildren().add(name);

        TextField orgName =  new TextField();
        orgName.setText(legalClient.orgname);
        orgName.setBackground(null);
        orgName.setFont(Font.font("STXihei", 16));
        orgName.setLayoutX(195);
        orgName.setLayoutY(139);
        orgName.setPrefSize(215,32);
        pane1.getChildren().add(orgName);

        TextField inn = new TextField();
        inn.setText(legalClient.inn);
        inn.setBackground(null);
        inn.setFont(Font.font("STXihei", 16));
        inn.setLayoutX(195);
        inn.setLayoutY(183);
        inn.setPrefSize(215,32);
        pane1.getChildren().add(inn);

        TextField kpp =new TextField();
        kpp.setText(legalClient.kpp);
        kpp.setBackground(null);
        kpp.setFont(Font.font("STXihei", 16));
        kpp.setLayoutX(195);
        kpp.setLayoutY(226);
        kpp.setPrefSize(215,32);
        pane1.getChildren().add(kpp);

        TextField ogrn = new TextField();
        ogrn.setText(legalClient.ogrn);
        ogrn.setBackground(null);
        ogrn.setFont(Font.font("STXihei", 16));
        ogrn.setLayoutX(195);
        ogrn.setLayoutY(271);
        ogrn.setPrefSize(215,32);
        pane1.getChildren().add(ogrn);

        TextField tel =new TextField();
        tel.setText(legalClient.tel);
        tel.setBackground(null);
        tel.setFont(Font.font("STXihei", 16));
        tel.setLayoutX(195);
        tel.setLayoutY(315);
        tel.setPrefSize(215,32);
        pane1.getChildren().add(tel);

        TextField mail =new TextField();
        mail.setText(legalClient.mail);
        mail.setBackground(null);
        mail.setFont(Font.font("STXihei", 16));
        mail.setLayoutX(195);
        mail.setLayoutY(359);
        mail.setPrefSize(215,32);
        pane1.getChildren().add(mail);

        TextField address =new TextField();
        address.setText(legalClient.address);
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
                    Postgre.updateLegalClient(id_legal,name_text,orgName_text,inn_text,kpp_text,ogrn_text,tel_text,mail_text,address_text);
                    Front.root.getChildren().remove(Front.pane);
                    Front.pane = AllClient.getStartFront(fl);
                    Front.root.getChildren().add(Front.pane);
                    newWindow.close();
                } catch (SQLException | FileNotFoundException e) {
                    throw new RuntimeException(e);
                }

            }
        });

        Button delete = new Button();
        delete.setBackground(null);
        delete.setLayoutX(273);
        delete.setLayoutY(487);
        delete.setPrefSize(88,14);
        pane1.getChildren().add(delete);
        delete.setOnAction(t->{
            try {
                Postgre.deleteLegalClient(id_legal);
                Front.root.getChildren().remove(Front.pane);
                Front.pane = AllClient.getStartFront(fl);
                Front.root.getChildren().add(Front.pane);
                newWindow.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
        newWindow.setTitle("Добавление Юр лица");
        newWindow.setScene(scene_add);
        newWindow.show();

    }
}
