package com.example.yacod.Model;

import com.example.yacod.DataBase.Postgre;
import com.example.yacod.Front;
import com.example.yacod.View.AllCodService;
import com.example.yacod.View.EditCod;
import com.example.yacod.View.EditService;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;

public class Service {
    public String id;
    public String os;
    public String ram;
    public String hdd;
    public String cpu;
    public String price;

    public Service(String id, String os, String ram, String hdd, String cpu, String price) {
        this.id = id;
        this.os = os;
        this.ram = ram;
        this.hdd = hdd;
        this.cpu = cpu;
        this.price = price;
    }
    public static Pane getPane(String id_cod) throws SQLException {
        Pane pane = new Pane();
        Text num_text = new Text("#");
        num_text.setLayoutX(20);
        num_text.setLayoutY(60);
        num_text.setFont(Font.font("Verdana",13));

        Text os_text = new Text("ОС");
        os_text.setLayoutX(50);
        os_text.setLayoutY(60);
        os_text.setFont(Font.font("Verdana",13));


        Text cpu_text = new Text("ЦПУ");
        cpu_text.setLayoutX(450);
        cpu_text.setLayoutY(60);
        cpu_text.setFont(Font.font("Verdana",13));

        Text ram_text = new Text("ОЗУ");
        ram_text.setLayoutX(550);
        ram_text.setLayoutY(60);
        ram_text.setFont(Font.font("Verdana",13));

        Text hdd_text = new Text("Жесткий диск");
        hdd_text.setLayoutX(650);
        hdd_text.setLayoutY(60);
        hdd_text.setFont(Font.font("Verdana",13));

        Text price_text = new Text("Цена");
        price_text.setLayoutX(750);
        price_text.setLayoutY(60);
        price_text.setFont(Font.font("Verdana",13));

        ArrayList<Service> mas = Postgre.getAllService(id_cod);
        int u = 80;
        for(int i=0; i<mas.size();i++, u+=70){
            TextField num = new TextField();
            num.setEditable(false);
//            num.setBackground(null);
            num.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
//            num.setStyle("-fx-background: rgb(190, 196, 213);-fx-background-color: rgb(190, 196, 213);");
            num.setText(mas.get(i).id);
            num.setLayoutX(10);
            num.setLayoutY(0 + u);
            num.setMaxHeight(40);
            num.setMaxWidth(30);
            num.setMinHeight(40);
            num.setMinWidth(30);

            TextField os =new TextField();
            os.setText(mas.get(i).os);
            os.setBackground(null);
            os.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
            os.setEditable(false);
            os.setLayoutX(50);
            os.setLayoutY(0 + u);
            os.setMaxHeight(40);
            os.setMaxWidth(350);
            os.setMinWidth(350);

            TextField cpu = new TextField();
            cpu.setText(mas.get(i).cpu);
            cpu.setBackground(null);
            cpu.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
            cpu.setEditable(false);
            cpu.setLayoutX(450);
            cpu.setLayoutY(0 + u);
            cpu.setMaxHeight(40);
            cpu.setMaxWidth(80);
            cpu.setMinHeight(40);
            cpu.setMinWidth(80);

            TextField ram = new TextField();
            ram.setText(mas.get(i).ram);
            ram.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
            ram.setEditable(false);
            ram.setLayoutX(550);
            ram.setLayoutY(0 + u);
            ram.setMaxHeight(40);
            ram.setMaxWidth(100);
            ram.setMinHeight(40);
            ram.setMinWidth(100);

            TextField hdd = new TextField();
            hdd.setText(mas.get(i).hdd);
            hdd.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
            hdd.setEditable(false);
            hdd.setLayoutX(660);
            hdd.setLayoutY(0 + u);
            hdd.setMaxHeight(40);
            hdd.setMaxWidth(70);

            TextField price = new TextField();
            price.setText(mas.get(i).price);
            price.setStyle("-fx-background: rgb(190, 196, 213);-fx-background-color: rgb(190, 196, 213);");
            price.setEditable(false);
            price.setLayoutX(760);
            price.setLayoutY(0 + u);
            price.setMaxHeight(40);
            price.setMaxWidth(100);
            price.setMinHeight(40);
            price.setMinWidth(60);
            final String id = mas.get(i).id;
            Button edit = new Button("Изменить");
            edit.setLayoutX(910);
            edit.setLayoutY(0 + u);
            edit.setOnAction(t->{
                try {
                    EditService.editService(id,id_cod);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            });
            pane.getChildren().add(edit);

            pane.getChildren().addAll(num,os,cpu,ram,hdd,price);
        }

        pane.getChildren().addAll(num_text, os_text,  cpu_text, ram_text, price_text, hdd_text);
        return pane;
    }
}
