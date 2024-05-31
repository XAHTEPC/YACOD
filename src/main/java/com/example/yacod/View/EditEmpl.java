package com.example.yacod.View;

import com.example.yacod.DataBase.Postgre;
import com.example.yacod.Front;
import com.example.yacod.Model.Employee;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
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

public class EditEmpl {
    public static void editEmpl(String id_empl, int fl) throws SQLException {
        Employee employee = Postgre.getEmployeebyId(id_empl);
        Group root_add = new Group();
        Scene scene_add = new Scene(root_add, 450, 392);
        Stage newWindow = new Stage();
        newWindow.initStyle(StageStyle.DECORATED);
        Pane pane1 = new Pane();
        pane1.setPrefSize(450,392);
        pane1.setLayoutX(0);
        pane1.setLayoutY(0);
        root_add.getChildren().addAll(pane1);

        FileInputStream Url1;

        try {
            Url1 = new FileInputStream("png/editEmpl.png");
        } catch (
                FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        Image url1 = new Image(Url1);
        ImageView front1 = new ImageView(url1);
        front1.setX(0);
        front1.setY(0);
        pane1.getChildren().add(front1);


        TextField name = new TextField();
        name.setText(employee.name);
        name.setBackground(null);
        name.setFont(Font.font("STXihei", 16));
        name.setLayoutX(195);
        name.setLayoutY(95);
        name.setPrefSize(215,32);
        pane1.getChildren().add(name);

        TextField exp =  new TextField();
        exp.setText(employee.exp);
        exp.setBackground(null);
        exp.setFont(Font.font("STXihei", 16));
        exp.setLayoutX(195);
        exp.setLayoutY(139);
        exp.setPrefSize(215,32);
        pane1.getChildren().add(exp);

        TextField age = new TextField();
        age.setText(employee.age);
        age.setBackground(null);
        age.setFont(Font.font("STXihei", 16));
        age.setLayoutX(195);
        age.setLayoutY(183);
        age.setPrefSize(215,32);
        pane1.getChildren().add(age);

        TextField dolz = new TextField();
        dolz.setText(employee.post);
        dolz.setBackground(null);
        dolz.setFont(Font.font("STXihei", 16));
        dolz.setLayoutX(195);
        dolz.setLayoutY(227);
        dolz.setPrefSize(215,32);
        pane1.getChildren().add(dolz);

        TextField login =new TextField();
        login.setText(employee.login);
        login.setBackground(null);
        login.setFont(Font.font("STXihei", 16));
        login.setLayoutX(195);
        login.setLayoutY(271);
        login.setPrefSize(215,32);
        pane1.getChildren().add(login);

        PasswordField pass =new PasswordField();
        pass.setBackground(null);
        pass.setFont(Font.font("STXihei", 16));
        pass.setLayoutX(195);
        pass.setLayoutY(315);
        pass.setPrefSize(215,32);
        pane1.getChildren().add(pass);

        Button save = new Button();
        save.setBackground(null);
        save.setLayoutX(93);
        save.setLayoutY(369);
        save.setPrefSize(113,14);
        pane1.getChildren().add(save);
        save.setOnAction(t->{
            String name_text = name.getText();
            String exp_text = exp.getText();
            String age_text = age.getText();
            String login_text = login.getText();
            String pass_text = pass.getText();
            String dolz_text = dolz.getText();
            if(!name_text.isEmpty()&&!exp_text.isEmpty()&&!age_text.isEmpty()
                    &&!dolz_text.isEmpty()){
                try {
                    String zp = "45000";
                    if(dolz_text.equals("Администратор"))
                        zp = "70000";
                    else if(dolz_text.equals("Инженер"))
                        zp = "55000";
                    else if(dolz_text.equals("Аналитик"))
                        zp = "50000";



                    Postgre.updateEmpl(id_empl,employee.post,dolz_text, name_text,exp_text,age_text,login_text,pass_text,zp);
                    Front.root.getChildren().remove(Front.pane);
                    Front.pane = AllEmpl.getStartFront(fl);
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
        delete.setLayoutX(269);
        delete.setLayoutY(369);
        delete.setPrefSize(80,14);
        pane1.getChildren().add(delete);
        delete.setOnAction(t->{
            try {
                Postgre.deleteEmpl(id_empl);
                Front.root.getChildren().remove(Front.pane);
                Front.pane = AllEmpl.getStartFront(fl);
                Front.root.getChildren().add(Front.pane);
                newWindow.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        });


        newWindow.setTitle("Добавление сотрудника");
        newWindow.setScene(scene_add);
        newWindow.show();

    }
}
