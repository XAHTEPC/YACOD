package com.example.yacod.View;

import com.example.yacod.DataBase.Postgre;
import com.example.yacod.Front;
import com.example.yacod.Model.Service;
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

public class EditService {
    public static void editService(String id_service, String id_cod) throws SQLException {
        Service service = Postgre.getServicebyID(id_service);
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
            Url1 = new FileInputStream("png/editService.png");
        } catch (
                FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        Image url1 = new Image(Url1);
        ImageView front1 = new ImageView(url1);
        front1.setX(0);
        front1.setY(0);
        pane1.getChildren().add(front1);


        TextField cpu = new TextField();
        cpu.setText(service.cpu);
        cpu.setBackground(null);
        cpu.setFont(Font.font("STXihei", 16));
        cpu.setLayoutX(103);
        cpu.setLayoutY(74);
        cpu.setPrefSize(308,32);
        pane1.getChildren().add(cpu);

        TextField ram =  new TextField();
        ram.setText(service.ram);
        ram.setBackground(null);
        ram.setFont(Font.font("STXihei", 16));
        ram.setLayoutX(103);
        ram.setLayoutY(118);
        ram.setPrefSize(308,32);
        pane1.getChildren().add(ram);

        TextField os = new TextField();
        os.setText(service.os);
        os.setBackground(null);
        os.setFont(Font.font("STXihei", 16));
        os.setLayoutX(103);
        os.setLayoutY(162);
        os.setPrefSize(308,32);
        pane1.getChildren().add(os);

        TextField hdd =new TextField();
        hdd.setText(service.hdd);
        hdd.setBackground(null);
        hdd.setFont(Font.font("STXihei", 16));
        hdd.setLayoutX(103);
        hdd.setLayoutY(206);
        hdd.setPrefSize(308,32);
        pane1.getChildren().add(hdd);

        TextField price =new TextField();
        price.setText(service.price);
        price.setBackground(null);
        price.setFont(Font.font("STXihei", 16));
        price.setLayoutX(103);
        price.setLayoutY(250);
        price.setPrefSize(308,32);
        pane1.getChildren().add(price);

        Button save = new Button();
        save.setBackground(null);
        save.setLayoutX(89);
        save.setLayoutY(309);
        save.setPrefSize(113,14);
        pane1.getChildren().add(save);
        save.setOnAction(t->{
            String cpu_text = cpu.getText();
            String ram_text = ram.getText();
            String os_text = os.getText();
            String price_text = price.getText();
            String hdd_text = hdd.getText();
            if(!cpu_text.isEmpty()&&!ram_text.isEmpty()&&!os_text.isEmpty()
                    &&!price_text.isEmpty()&&!hdd_text.isEmpty()){
                try {
                    Postgre.updateService(id_service,os_text,price_text,hdd_text,cpu_text,ram_text);
                    Front.root.getChildren().remove(Front.pane);
                    Front.pane = AllCodService.getStartFront(id_cod);
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
                Postgre.deleteService(id_service);
                Front.root.getChildren().remove(Front.pane);
                Front.pane = AllCodService.getStartFront(id_cod);
                Front.root.getChildren().add(Front.pane);
                newWindow.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        });


        newWindow.setTitle("Удаление услуги");
        newWindow.setScene(scene_add);
        newWindow.show();

    }
}
