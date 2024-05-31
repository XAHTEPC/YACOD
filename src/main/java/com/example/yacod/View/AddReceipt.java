package com.example.yacod.View;

import com.example.yacod.DataBase.Postgre;
import com.example.yacod.Front;
import com.example.yacod.Model.LegalClient;
import com.example.yacod.Model.PhysClient;
import com.example.yacod.Model.ReceiptServ;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
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
import java.time.LocalDate;
import java.util.ArrayList;

public class AddReceipt {
    public static ArrayList<ReceiptServ> arrayList;
   public static ScrollPane scrollPane;
    public static void addReceipt(boolean fl, String id_client) throws SQLException {
        arrayList = new ArrayList<>();
        Group root_add = new Group();
        Scene scene_add = new Scene(root_add, 450, 460);
        Stage newWindow = new Stage();
        newWindow.initStyle(StageStyle.DECORATED);
        Pane pane1 = new Pane();
        pane1.setPrefSize(450,460);
        pane1.setLayoutX(0);
        pane1.setLayoutY(0);
        root_add.getChildren().addAll(pane1);

        FileInputStream Url1;

        try {
            Url1 = new FileInputStream("png/addReceipt.png");
        } catch (
                FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        Image url1 = new Image(Url1);
        ImageView front1 = new ImageView(url1);
        front1.setX(0);
        front1.setY(0);
        pane1.getChildren().add(front1);


        TextField nameEmpl = new TextField();
        String name = Postgre.getEmployeeNamebyLogin();
        nameEmpl.setText(name);
        nameEmpl.setEditable(false);
        nameEmpl.setBackground(null);
        nameEmpl.setFont(Font.font("STXihei", 16));
        nameEmpl.setLayoutX(195);
        nameEmpl.setLayoutY(95);
        nameEmpl.setPrefSize(215,32);
        pane1.getChildren().add(nameEmpl);

        TextField type =  new TextField();
        if(fl){
            type.setText("Юридическое лицо");
        } else {
            type.setText("Физическое лицо");
        }
        type.setEditable(false);
        type.setBackground(null);
        type.setFont(Font.font("STXihei", 16));
        type.setLayoutX(195);
        type.setLayoutY(139);
        type.setPrefSize(215,32);
        pane1.getChildren().add(type);

        TextField cliName = new TextField();
        if(fl){
            LegalClient legalClient = Postgre.getLegalClientbyId(id_client);
            cliName.setText(legalClient.orgname);
        }else {
            PhysClient physClient = Postgre.getPhysClientbyId(id_client);
            cliName.setText(physClient.name);
        }
        cliName.setBackground(null);
        cliName.setFont(Font.font("STXihei", 16));
        cliName.setLayoutX(195);
        cliName.setEditable(false);
        cliName.setLayoutY(183);
        cliName.setPrefSize(215,32);
        pane1.getChildren().add(cliName);

        TextField date =new TextField();
        LocalDate currentDate = LocalDate.now();
        date.setText(currentDate.toString());
        date.setBackground(null);
        date.setFont(Font.font("STXihei", 16));
        date.setLayoutX(195);
        date.setLayoutY(226);
        date.setPrefSize(215,32);
        pane1.getChildren().add(date);

        Pane paneScroll = new Pane();
        scrollPane = new ScrollPane();
        scrollPane.setLayoutX(195);
        scrollPane.setLayoutY(270);
        scrollPane.setMaxHeight(76);
        scrollPane.setMaxWidth(215);
        scrollPane.setMinHeight(76);
        scrollPane.setMinWidth(215);
        scrollPane.setStyle("-fx-background: transparent ;-fx-background-color: transparent;");
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setFitToWidth(true);
        scrollPane.setContent(paneScroll);
        pane1.getChildren().add(scrollPane);

        Button addServ = new Button();
        addServ.setBackground(null);
        addServ.setLayoutX(213);
        addServ.setLayoutY(359);
        addServ.setPrefSize(178,14);
        pane1.getChildren().add(addServ);
        addServ.setOnAction(t->{
            try {
                AddService.addService2(true);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });


        Button save = new Button();
        save.setBackground(null);
        save.setLayoutX(81);
        save.setLayoutY(417);
        save.setPrefSize(113,14);
        pane1.getChildren().add(save);
        save.setOnAction(t->{
            String id_empl = null;
            try {
                id_empl = Postgre.getEmployeeIDbyLogin();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            String summ ="0";
            for(int i=0;i<arrayList.size();i++){
                int a = Integer.parseInt(summ);
                int b = Integer.parseInt(arrayList.get(i).summ);
                a = a+b;
                summ = String.valueOf(a);
            }
            try {
                Postgre.addReceipt(arrayList,id_client,id_empl,summ,type.getText());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            newWindow.close();
        });

        Button back = new Button();
        back.setBackground(null);
        back.setLayoutX(257);
        back.setLayoutY(417);
        back.setPrefSize(80,14);
        pane1.getChildren().add(back);
        back.setOnAction(t->{
            newWindow.close();
        });


        newWindow.setTitle("Добавление Физ лица");
        newWindow.setScene(scene_add);
        newWindow.show();
    }
    public static void addPos(ReceiptServ receiptServ) throws FileNotFoundException {
        arrayList.add(receiptServ);
        Pane pane = new Pane();
        int u=0;
        for(int i=0;i<arrayList.size();i++, u+=30){
            FileInputStream Url = new FileInputStream("png/bin.png");
            Image url = new Image(Url);
            ImageView pen = new ImageView(url);

            Button edit = new Button();
            edit.setGraphic(pen);
            edit.setLayoutX(0);
            edit.setLayoutY(0 + u);
            edit.setStyle("-fx-background: transparent ;-fx-background-color: transparent;");
            pane.getChildren().add(edit);
            final ReceiptServ receiptServ1 = arrayList.get(i);
            edit.setOnAction(t->{
                try {
                    delPos(receiptServ1);
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
            });

            TextField name = new TextField();
            name.setText(arrayList.get(i).name);
            name.setStyle("-fx-background: transparent ;-fx-background-color: transparent;");
            name.setPrefSize(170,30);
            name.setLayoutX(30);
            name.setLayoutY(0+u);
            pane.getChildren().add(name);
        }
        scrollPane.setContent(pane);
    }
    public static void delPos(ReceiptServ receiptServ) throws FileNotFoundException {
        arrayList.remove(receiptServ);
        Pane pane = new Pane();
        int u=0;
        for(int i=0;i<arrayList.size();i++, u+=30){
            FileInputStream Url = new FileInputStream("png/bin.png");
            Image url = new Image(Url);
            ImageView pen = new ImageView(url);

            Button edit = new Button();
            edit.setGraphic(pen);
            edit.setLayoutX(0);
            edit.setLayoutY(0 + u);
            edit.setStyle("-fx-background: transparent ;-fx-background-color: transparent;");
            pane.getChildren().add(edit);
            final ReceiptServ receiptServ1 = arrayList.get(i);
            edit.setOnAction(t->{
                try {
                    delPos(receiptServ1);
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
            });


            TextField name = new TextField();
            name.setText(arrayList.get(i).name);
            name.setStyle("-fx-background: transparent ;-fx-background-color: transparent;");
            name.setPrefSize(170,30);
            name.setLayoutX(30);
            name.setLayoutY(0+u);
            pane.getChildren().add(name);
        }
        scrollPane.setContent(pane);
    }

}
