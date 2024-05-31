package com.example.yacod.View;

import com.example.yacod.DataBase.Postgre;
import com.example.yacod.Front;
import com.example.yacod.Model.ReceiptServ;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
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
import java.util.ArrayList;

public class AddService {
    public static void addService(String id_cod){
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
            Url1 = new FileInputStream("png/addService.png");
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
        cpu.setBackground(null);
        cpu.setFont(Font.font("STXihei", 16));
        cpu.setLayoutX(103);
        cpu.setLayoutY(74);
        cpu.setPrefSize(308,32);
        pane1.getChildren().add(cpu);

        TextField ram =  new TextField();
        ram.setBackground(null);
        ram.setFont(Font.font("STXihei", 16));
        ram.setLayoutX(103);
        ram.setLayoutY(118);
        ram.setPrefSize(308,32);
        pane1.getChildren().add(ram);

        TextField os = new TextField();
        os.setBackground(null);
        os.setFont(Font.font("STXihei", 16));
        os.setLayoutX(103);
        os.setLayoutY(162);
        os.setPrefSize(308,32);
        pane1.getChildren().add(os);

        TextField hdd =new TextField();
        hdd.setBackground(null);
        hdd.setFont(Font.font("STXihei", 16));
        hdd.setLayoutX(103);
        hdd.setLayoutY(206);
        hdd.setPrefSize(308,32);
        pane1.getChildren().add(hdd);

        TextField price =new TextField();
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
                    Postgre.addService(id_cod,os_text,price_text,hdd_text,cpu_text,ram_text);
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
    static String id_cod;
    static String id_serv;
    static ComboBox<String> comboBox_serv;
    static ArrayList<ReceiptServ> arrayList;

    public static void addService2(boolean fl) throws SQLException {
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
            Url1 = new FileInputStream("png/addreceiptservice.png");
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


        comboBox_cod.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                String address_cod = (comboBox_cod.getSelectionModel().getSelectedItem());
                System.out.println("NAme:" + address_cod);
                String id_cod = null;
                try {
                    id_cod = Postgre.getCodIDbyAddress(address_cod);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                String[] newmas_serv = new String[0];
                try {
                    arrayList = Postgre.getServiceName(id_cod);
                    newmas_serv = new String[arrayList.size()];
                    for(int i=0;i<arrayList.size();i++){
                        newmas_serv[i] =arrayList.get(i).name;
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                String[] masserv = newmas_serv;
                ObservableList<String> serv = FXCollections.observableArrayList(masserv);
                pane1.getChildren().remove(comboBox_serv);
                comboBox_serv = new ComboBox<String>(serv);
                comboBox_serv.setMaxWidth(253);
                comboBox_serv.setEditable(false);
                comboBox_serv.setMinWidth(253);
                comboBox_serv.setBackground(null);
                comboBox_serv.setLayoutX(157);
                comboBox_serv.setLayoutY(138);
                pane1.getChildren().add(comboBox_serv);
            }
        });

        Button save = new Button();
        save.setBackground(null);
        save.setLayoutX(97);
        save.setLayoutY(224);
        save.setPrefSize(113,14);
        pane1.getChildren().add(save);
        save.setOnAction(t->{
            String name = comboBox_serv.getSelectionModel().getSelectedItem();
            String id = "";
            String price = "";
            for(int i=0;i<arrayList.size();i++){
                if(arrayList.get(i).name.equals(name)){
                    id = arrayList.get(i).id_serv;
                    price = arrayList.get(i).summ;
                }
            }
            ReceiptServ receiptServ = new ReceiptServ(id,price,name);
            try {
                if(fl){
                    AddReceipt.addPos(receiptServ);
                }
                else {
                    EditReceipt.addPos(receiptServ);
                }

            } catch (FileNotFoundException e) {
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
