package com.example.yacod.View;

import com.example.yacod.DataBase.Postgre;
import com.example.yacod.Front;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.SQLException;

public class AddWork {
    public static void addWork(String id_cod) throws SQLException {
        Group root_add = new Group();
        Scene scene_add = new Scene(root_add, 450, 255);
        Stage newWindow = new Stage();
        newWindow.initStyle(StageStyle.DECORATED);
        Pane pane1 = new Pane();
        pane1.setPrefSize(450,255);
        pane1.setLayoutX(0);
        pane1.setLayoutY(0);
        root_add.getChildren().addAll(pane1);

        FileInputStream Url1;

        try {
            Url1 = new FileInputStream("png/addWork.png");
        } catch (
                FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        Image url1 = new Image(Url1);
        ImageView front1 = new ImageView(url1);
        front1.setX(0);
        front1.setY(0);
        pane1.getChildren().add(front1);

        String[] mascod = Postgre.getCODAddress();
        ObservableList<String> work = FXCollections.observableArrayList(mascod);
        ComboBox<String> comboBox_cod = new ComboBox<String>(work);
        comboBox_cod.setMaxWidth(253);
        comboBox_cod.setEditable(false);
        comboBox_cod.setMinWidth(253);
        comboBox_cod.setBackground(null);
        comboBox_cod.setLayoutX(157);
        comboBox_cod.setLayoutY(95);
        pane1.getChildren().add(comboBox_cod);


        String[] masempl = Postgre.getEmployeeNameMin();
        ObservableList<String> empl = FXCollections.observableArrayList(masempl);
        ComboBox<String> combobox_empl = new ComboBox<String>(empl);
        combobox_empl.setMaxWidth(253);
        combobox_empl.setEditable(false);
        combobox_empl.setMinWidth(253);
        combobox_empl.setBackground(null);
        combobox_empl.setLayoutX(157);
        combobox_empl.setLayoutY(139);
        pane1.getChildren().add(combobox_empl);

        Button save = new Button();
        save.setBackground(null);
        save.setLayoutX(97);
        save.setLayoutY(224);
        save.setPrefSize(113,14);
        pane1.getChildren().add(save);
        save.setOnAction(t->{
            String  employee = combobox_empl.getSelectionModel().getSelectedItem();
            String cod = comboBox_cod.getSelectionModel().getSelectedItem();

            try {
                Postgre.addWork(cod,employee);
                Front.root.getChildren().remove(Front.pane);
                Front.pane = AllCodEmployee.getStartFront(id_cod);
                Front.root.getChildren().add(Front.pane);
            } catch (SQLException e) {
                newWindow.close();
                throw new RuntimeException(e);
            } catch (FileNotFoundException e) {
                newWindow.close();
                throw new RuntimeException(e);
            }
            newWindow.close();
        });

        Button back = new Button();
        back.setBackground(null);
        back.setLayoutX(273);
        back.setLayoutY(224);
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