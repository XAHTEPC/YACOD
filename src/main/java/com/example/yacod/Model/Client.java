package com.example.yacod.Model;

import com.example.yacod.DataBase.Postgre;
import com.example.yacod.Front;
import com.example.yacod.View.AddReceipt;
import com.example.yacod.View.AllCodEmployee;
import com.example.yacod.View.EditLegal;
import com.example.yacod.View.EditPhys;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;

public class Client {
    public static Pane getPane(int fl) throws SQLException {
        Pane pane = new Pane();
        Text num_text = new Text("#");
        num_text.setLayoutX(20);
        num_text.setLayoutY(60);
        num_text.setFont(Font.font("Verdana",13));

        Text name_text = new Text("ФИО");
        name_text.setLayoutX(50);
        name_text.setLayoutY(60);
        name_text.setFont(Font.font("Verdana",13));


        Text tel_text = new Text("Телефон");
        tel_text.setLayoutX(410);
        tel_text.setLayoutY(60);
        tel_text.setFont(Font.font("Verdana",13));

        Text mail_text = new Text("Почта");
        mail_text.setLayoutX(510);
        mail_text.setLayoutY(60);
        mail_text.setFont(Font.font("Verdana",13));

        Text bonus_text = new Text("Бонус");
        bonus_text.setLayoutX(610);
        bonus_text.setLayoutY(60);
        bonus_text.setFont(Font.font("Verdana",13));

        Text status_text = new Text("Статус");
        status_text.setLayoutX(710);
        status_text.setLayoutY(60);
        status_text.setFont(Font.font("Verdana",13));
        if(Front.isStart) {
            Postgre.makeTMP();
            Front.isStart = false;
        }
        ArrayList<LegalClient> mas = Postgre.getAllLegalClient();
        int u = 80;
        for(int i=0; i<mas.size();i++, u+=70){
            TextField num = new TextField();
            num.setEditable(false);
            num.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
            num.setText(mas.get(i).id);
            num.setLayoutX(10);
            num.setLayoutY(0 + u);
            num.setMaxHeight(40);
            num.setMaxWidth(30);
            num.setMinHeight(40);
            num.setMinWidth(30);

            TextField name =new TextField();
            name.setText(mas.get(i).orgname);
            name.setBackground(null);
            name.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
            name.setEditable(false);
            name.setLayoutX(50);
            name.setLayoutY(0 + u);
            name.setMaxHeight(40);
            name.setMaxWidth(350);
            name.setMinWidth(350);

            TextField post = new TextField();
            post.setText(mas.get(i).tel);
            post.setBackground(null);
            post.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
            post.setEditable(false);
            post.setLayoutX(410);
            post.setLayoutY(0 + u);
            post.setMaxHeight(40);
            post.setMaxWidth(80);
            post.setMinHeight(40);
            post.setMinWidth(80);

            TextField exp = new TextField();
            exp.setText(mas.get(i).mail);
            exp.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
            exp.setEditable(false);
            exp.setLayoutX(510);
            exp.setLayoutY(0 + u);
            exp.setMaxHeight(40);
            exp.setMaxWidth(100);
            exp.setMinHeight(40);
            exp.setMinWidth(100);

            TextField sal = new TextField();
            sal.setText(mas.get(i).bonus);
            sal.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
            sal.setEditable(false);
            sal.setLayoutX(620);
            sal.setLayoutY(0 + u);
            sal.setMaxHeight(40);
            sal.setMaxWidth(70);

            TextField age = new TextField();
            age.setText(mas.get(i).status);
            age.setStyle("-fx-background: rgb(190, 196, 213);-fx-background-color: rgb(190, 196, 213);");
            age.setEditable(false);
            age.setLayoutX(720);
            age.setLayoutY(0 + u);
            age.setMaxHeight(40);
            age.setMaxWidth(100);
            age.setMinHeight(40);
            age.setMinWidth(60);
            final String id = mas.get(i).id;
            Button add = new Button("Новый чек");
            add.setLayoutX(850);
            add.setLayoutY(0 + u);
            add.setOnAction(t->{
                try {
                    AddReceipt.addReceipt(true, id);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            });
            pane.getChildren().add(add);
            Button edit = new Button("Изменить");
            edit.setLayoutX(1000);
            edit.setLayoutY(0 + u);
            edit.setOnAction(t->{
                try {
                    EditLegal.editLegal(id,fl);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            });
            pane.getChildren().add(edit);
            pane.getChildren().addAll(num,name,post,exp,sal,age);
            if(fl == 1) {
                pane.getChildren().removeAll(edit,add);
            }
        }
        ArrayList<PhysClient> mas2 = Postgre.getAllPhysClient();
        for(int i=0; i<mas2.size();i++, u+=70){
            TextField num = new TextField();
            num.setEditable(false);
            num.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
            num.setText(mas2.get(i).id);
            num.setLayoutX(10);
            num.setLayoutY(0 + u);
            num.setMaxHeight(40);
            num.setMaxWidth(30);
            num.setMinHeight(40);
            num.setMinWidth(30);

            TextField name =new TextField();
            name.setText(mas2.get(i).name);
            name.setBackground(null);
            name.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
            name.setEditable(false);
            name.setLayoutX(50);
            name.setLayoutY(0 + u);
            name.setMaxHeight(40);
            name.setMaxWidth(350);
            name.setMinWidth(350);

            TextField post = new TextField();
            post.setText(mas2.get(i).tel);
            post.setBackground(null);
            post.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
            post.setEditable(false);
            post.setLayoutX(410);
            post.setLayoutY(0 + u);
            post.setMaxHeight(40);
            post.setMaxWidth(80);
            post.setMinHeight(40);
            post.setMinWidth(80);

            TextField exp = new TextField();
            exp.setText(mas2.get(i).mail);
            exp.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
            exp.setEditable(false);
            exp.setLayoutX(510);
            exp.setLayoutY(0 + u);
            exp.setMaxHeight(40);
            exp.setMaxWidth(100);
            exp.setMinHeight(40);
            exp.setMinWidth(100);

            TextField sal = new TextField();
            sal.setText(mas2.get(i).bonus);
            sal.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
            sal.setEditable(false);
            sal.setLayoutX(620);
            sal.setLayoutY(0 + u);
            sal.setMaxHeight(40);
            sal.setMaxWidth(70);

            TextField age = new TextField();
            age.setText(mas2.get(i).status);
            age.setStyle("-fx-background: rgb(190, 196, 213);-fx-background-color: rgb(190, 196, 213);");
            age.setEditable(false);
            age.setLayoutX(720);
            age.setLayoutY(0 + u);
            age.setMaxHeight(40);
            age.setMaxWidth(100);
            age.setMinHeight(40);
            age.setMinWidth(60);
            final String id = mas2.get(i).id;
            Button add = new Button("Новый чек");
            add.setLayoutX(850);
            add.setLayoutY(0 + u);
            add.setOnAction(t->{
                try {
                    AddReceipt.addReceipt(false, id);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            });
            pane.getChildren().add(add);
            Button edit = new Button("Изменить");
            edit.setLayoutX(1000);
            edit.setLayoutY(0 + u);
            edit.setOnAction(t->{
                try {
                    EditPhys.editPhys(id,fl);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            });
            pane.getChildren().add(edit);
            pane.getChildren().addAll(num,name,post,exp,sal,age);
            if(fl == 1) {
                pane.getChildren().removeAll(edit,add);
            }
        }

        pane.getChildren().addAll(num_text, name_text,  tel_text, mail_text, status_text, bonus_text);
        return pane;
    }
}
