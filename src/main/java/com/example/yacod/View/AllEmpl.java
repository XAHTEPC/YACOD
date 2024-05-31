package com.example.yacod.View;

import com.example.yacod.Front;
import com.example.yacod.Model.COD;
import com.example.yacod.Model.Employee;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.SQLException;

public class AllEmpl {
    public static ScrollPane scrollPane;

    public static Pane getStartFront(int fl) throws FileNotFoundException, SQLException {
        Pane pane = new Pane();
        pane.setLayoutX(0);
        pane.setLayoutY(0);
        FileInputStream Url;
        Url = new FileInputStream("png/allempl.png");
        Image url = new Image(Url);
        ImageView front = new ImageView(url);
        front.setX(0);
        front.setY(0);
        pane.getChildren().add(front);

        Button addEmpl = new Button();
        addEmpl.setBackground(null);
        addEmpl.setLayoutY(713);
        addEmpl.setLayoutX(762);
        addEmpl.setPrefSize(369,70);
        pane.getChildren().add(addEmpl);
        addEmpl.setOnAction(t ->{
            AddEmpl.addEmpl();
        });
        if(fl==1){
            pane.getChildren().removeAll(addEmpl);
        }

        Pane paneScroll = Employee.getPane2(fl);

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
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        });



        return pane;
    }
}
