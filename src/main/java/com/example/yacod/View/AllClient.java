package com.example.yacod.View;

import com.example.yacod.DataBase.Postgre;
import com.example.yacod.Front;
import com.example.yacod.Model.COD;
import com.example.yacod.Model.Client;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.SQLException;

public class AllClient {
    public static ScrollPane scrollPane;

    public static Pane getStartFront(int fl) throws FileNotFoundException, SQLException {
        Pane pane = new Pane();
        pane.setLayoutX(0);
        pane.setLayoutY(0);
        FileInputStream Url;
        Url = new FileInputStream("png/allclient.png");
        Image url = new Image(Url);
        ImageView front = new ImageView(url);
        front.setX(0);
        front.setY(0);
        pane.getChildren().add(front);

        Button addPhys = new Button();
        addPhys.setBackground(null);
        addPhys.setLayoutY(720);
        addPhys.setLayoutX(770);
        addPhys.setPrefSize(340,40);
        pane.getChildren().add(addPhys);
        addPhys.setOnAction(t ->{
            AddPhys.addPhys();
        });
        Button addLegal = new Button();
        addLegal.setBackground(null);
        addLegal.setLayoutY(720);
        addLegal.setLayoutX(90);
        addLegal.setPrefSize(340,40);
        pane.getChildren().add(addLegal);
        addLegal.setOnAction(t ->{
            AddLegal.addLegal();
        });
        if(fl==1){
            pane.getChildren().removeAll(addLegal,addPhys);
        }

        Pane paneScroll = Client.getPane(fl);

        scrollPane = new ScrollPane();
        scrollPane.setLayoutX(50);
        scrollPane.setLayoutY(153);
        scrollPane.setMaxHeight(550);
        scrollPane.setMaxWidth(110);
        scrollPane.setMinHeight(550);
        scrollPane.setMinWidth(1100);
        scrollPane.setStyle("-fx-background: rgb(190, 196, 213);-fx-background-color: rgb(190, 196, 213);");
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setFitToWidth(true);
        scrollPane.setContent(paneScroll);
        pane.getChildren().add(scrollPane);


        Button back = new Button();
            back.setBackground(null);
        back.setLayoutX(1145);
        back.setLayoutY(30);
        back.setPrefSize(45,50);
        pane.getChildren().add(back);
        back.setOnAction(t ->{
            try {
                if(fl==0) {
                    Front.root.getChildren().remove(Front.pane);
                    Front.pane = AdminFront.getStartFront();
                    Front.root.getChildren().add(Front.pane);
                }
                else if(fl==1){
                    Front.root.getChildren().remove(Front.pane);
                    Front.pane = AnalystFront.getStartFront();
                    Front.root.getChildren().add(Front.pane);
                }
                else if(fl==2){
                    Front.root.getChildren().remove(Front.pane);
                    Front.pane = ManagerFront.getStartFront();
                    Front.root.getChildren().add(Front.pane);
                }
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        });



        return pane;
    }
}
