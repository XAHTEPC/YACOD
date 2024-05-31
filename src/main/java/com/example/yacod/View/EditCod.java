package com.example.yacod.View;

import com.example.yacod.DataBase.Postgre;
import com.example.yacod.Front;
import com.example.yacod.Model.COD;
import javafx.geometry.Pos;
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

public class EditCod {
    public static void editCOD(String id, int fl) throws SQLException {
        COD cod = Postgre.getCODbyID(id);
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
            Url1 = new FileInputStream("png/editcod.png");
        } catch (
                FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        Image url1 = new Image(Url1);
        ImageView front1 = new ImageView(url1);
        front1.setX(0);
        front1.setY(0);
        pane1.getChildren().add(front1);


        TextField address = new TextField();
        address.setBackground(null);
        address.setText(cod.address);
        address.setFont(Font.font("STXihei", 16));
        address.setLayoutX(154);
        address.setLayoutY(95);
        address.setPrefSize(215,32);
        pane1.getChildren().add(address);

        TextField index =  new TextField();
        index.setText(cod.index);
        index.setBackground(null);
        index.setFont(Font.font("STXihei", 16));
        index.setLayoutX(154);
        index.setLayoutY(139);
        index.setPrefSize(215,32);
        pane1.getChildren().add(index);

        TextField tel = new TextField();
        tel.setText(cod.tel);
        tel.setBackground(null);
        tel.setFont(Font.font("STXihei", 16));
        tel.setLayoutX(154);
        tel.setLayoutY(183);
        tel.setPrefSize(215,32);
        pane1.getChildren().add(tel);

        TextField kol =new TextField();
        kol.setText(cod.num_empl);
        kol.setBackground(null);
        kol.setFont(Font.font("STXihei", 16));
        kol.setLayoutX(154);
        kol.setLayoutY(226);
        kol.setPrefSize(215,32);
        pane1.getChildren().add(kol);

        Button save = new Button();
        save.setBackground(null);
        save.setLayoutX(89);
        save.setLayoutY(309);
        save.setPrefSize(113,14);
        pane1.getChildren().add(save);
        save.setOnAction(t->{
            String address_text = address.getText();
            String index_text = index.getText();
            String tel_text = tel.getText();
            String kol_text = kol.getText();
            if(!address_text.isEmpty()&&!index_text.isEmpty()&&!tel_text.isEmpty()
                    &&!kol_text.isEmpty()){
                try {
                    Postgre.updateCOD(cod.id,address_text,index_text,tel_text,kol_text);
                    Front.root.getChildren().remove(Front.pane);
                    Front.pane = AllCod.getStartFront(fl);
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
                Postgre.deleteCOD(cod.id);
                Front.root.getChildren().remove(Front.pane);
                Front.pane = AllCod.getStartFront(fl);
                Front.root.getChildren().add(Front.pane);
                newWindow.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        });


        newWindow.setTitle("Добавление Физ лица");
        newWindow.setScene(scene_add);
        newWindow.show();

    }
}
