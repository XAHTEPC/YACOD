package com.example.yacod.View;

import com.example.yacod.DataBase.Postgre;
import com.example.yacod.Front;
import com.example.yacod.Model.PhysClient;
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

public class EditPhys {
    public static void editPhys(String id_phys, int fl) throws SQLException {
        PhysClient physClient = Postgre.getPhysClientbyId(id_phys);
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
            Url1 = new FileInputStream("png/editPhys.png");
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
        name.setText(physClient.name);
        name.setBackground(null);
        name.setFont(Font.font("STXihei", 16));
        name.setLayoutX(154);
        name.setLayoutY(95);
        name.setPrefSize(215,32);
        pane1.getChildren().add(name);

        TextField mail =  new TextField();
        mail.setText(physClient.mail);
        mail.setBackground(null);
        mail.setFont(Font.font("STXihei", 16));
        mail.setLayoutX(154);
        mail.setLayoutY(139);
        mail.setPrefSize(215,32);
        pane1.getChildren().add(mail);

        TextField tel = new TextField();
        tel.setText(physClient.tel);
        tel.setBackground(null);
        tel.setFont(Font.font("STXihei", 16));
        tel.setLayoutX(154);
        tel.setLayoutY(183);
        tel.setPrefSize(215,32);
        pane1.getChildren().add(tel);

        TextField inn =new TextField();
        inn.setText(physClient.inn);
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
                    Postgre.updatePhysClient(id_phys,name_text,tel_text,inn_text,mail_text);
                    Front.root.getChildren().remove(Front.pane);
                    Front.pane = AllClient.getStartFront(fl);
                    Front.root.getChildren().add(Front.pane);
                    newWindow.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }

            }
        });

        Button delete = new Button();
        delete.setBackground(null);
        delete.setLayoutX(265);
        delete.setLayoutY(309);
        delete.setPrefSize(80,14);
        pane1.getChildren().add(delete);
        delete.setOnAction(t->{
            try {
                Postgre.deletePhysClient(id_phys);
                Front.root.getChildren().remove(Front.pane);
                Front.pane = AllClient.getStartFront(fl);
                Front.root.getChildren().add(Front.pane);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }

            newWindow.close();
        });

        newWindow.setTitle("Добавление Физ лица");
        newWindow.setScene(scene_add);
        newWindow.show();

    }
}
