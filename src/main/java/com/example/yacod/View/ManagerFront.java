package com.example.yacod.View;

import com.example.yacod.Front;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class ManagerFront {
    public static Pane getStartFront() throws FileNotFoundException {
        Pane pane = new Pane();
        pane.setLayoutX(0);
        pane.setLayoutY(0);
        FileInputStream Url;
        Url = new FileInputStream("png/manager.png");
        Image url = new Image(Url);
        ImageView front = new ImageView(url);
        front.setX(0);
        front.setY(0);
        pane.getChildren().add(front);

        Button exit = new Button();
        exit.setBackground(null);
        exit.setLayoutX(1147);
        exit.setLayoutY(30);
        exit.setPrefSize(45,50);
        pane.getChildren().add(exit);
        exit.setOnAction(t ->{
            try {
                Front.exit();
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        });

        Button allClient = new Button();
        allClient.setBackground(null);
        allClient.setLayoutX(400);
        allClient.setLayoutY(260);
        allClient.setPrefSize(400,63);
        pane.getChildren().add(allClient);
        allClient.setOnAction(t1 -> {
            try {
                Front.root.getChildren().remove(Front.pane);
                Front.pane = AllClient.getStartFront(2);
                Front.root.getChildren().add(Front.pane);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        Button allreceipt = new Button();
        allreceipt.setBackground(null);
        allreceipt.setLayoutX(400);
        allreceipt.setLayoutY(360);
        allreceipt.setPrefSize(400,63);
        pane.getChildren().add(allreceipt);
        allreceipt.setOnAction(t1 -> {
            try {
                Front.root.getChildren().remove(Front.pane);
                Front.pane = AllReceipt.getStartFront(2);
                Front.root.getChildren().add(Front.pane);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });


        return pane;
    }
}
