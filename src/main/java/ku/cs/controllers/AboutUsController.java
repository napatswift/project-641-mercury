package ku.cs.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import com.github.saacsos.FXRouter;
import javafx.event.ActionEvent;
import ku.cs.models.Organizer;
import ku.cs.service.OrganizerDataSource;

import java.io.IOException;
import java.util.ArrayList;

public class AboutUsController {
    OrganizerDataSource list = new OrganizerDataSource();
    ArrayList<Organizer> dataSource = list.getList();

    @FXML Label nameLabel, nameLabel1, nameLabel2, nameLabel3;
    @FXML Label nicknameLabel, nicknameLabel1, nicknameLabel2, nicknameLabel3;
    @FXML Label idLabel, idLabel1, idLabel2, idLabel3;
    @FXML Label githubLabel, githubLabel1, githubLabel2, githubLabel3;
    @FXML ImageView imageIV, imageIV1, imageIV2, imageIV3;

    @FXML
    public void handleBack(ActionEvent event){
        try {
            FXRouter.goTo("login");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า login ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }


    @FXML
    public void initialize(){
        setNameLabel();
        setNicknameLabel();
        setIdLabel();
        setGithubLabel();
        setImageIV();
    }


    public void setNameLabel(){
        nameLabel.setText(dataSource.get(0).getName());
        nameLabel1.setText(dataSource.get(1).getName());
        nameLabel2.setText(dataSource.get(2).getName());
        nameLabel3.setText(dataSource.get(3).getName());
    }

    public void setNicknameLabel(){
        nicknameLabel.setText(dataSource.get(0).getNickname());
        nicknameLabel1.setText(dataSource.get(1).getNickname());
        nicknameLabel2.setText(dataSource.get(2).getNickname());
        nicknameLabel3.setText(dataSource.get(3).getNickname());
    }

    public void setIdLabel(){
        idLabel.setText(dataSource.get(0).getStudentId());
        idLabel1.setText(dataSource.get(1).getStudentId());
        idLabel2.setText(dataSource.get(2).getStudentId());
        idLabel3.setText(dataSource.get(3).getStudentId());
    }

    public void setGithubLabel(){
        githubLabel.setText(dataSource.get(0).getGithubId());
        githubLabel1.setText(dataSource.get(1).getGithubId());
        githubLabel2.setText(dataSource.get(2).getGithubId());
        githubLabel3.setText(dataSource.get(3).getGithubId());
    }

    public void setImageIV(){
        String path0 = getClass().getResource(dataSource.get(0).getImagePath()).toExternalForm();
        imageIV.setImage(new Image(path0));
        String path1 = getClass().getResource(dataSource.get(1).getImagePath()).toExternalForm();
        imageIV1.setImage(new Image(path1));
        String path2 = getClass().getResource(dataSource.get(2).getImagePath()).toExternalForm();
        imageIV2.setImage(new Image(path2));
        String path3 = getClass().getResource(dataSource.get(3).getImagePath()).toExternalForm();
        imageIV3.setImage(new Image(path3));
    }
}
