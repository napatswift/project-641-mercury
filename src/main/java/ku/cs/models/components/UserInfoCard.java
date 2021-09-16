package ku.cs.models.components;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Circle;
import ku.cs.models.User;

public class UserInfoCard extends HBox {
    private User user;
    private ImageView imageView;
    private Label usernameLabel;
    private Label nameLabel;

    public UserInfoCard(User user) {
        imageView = new ImageView();

        imageView.setFitHeight(18);
        imageView.setPreserveRatio(true);
        imageView.setClip(new Circle(9, 9, 9));

        usernameLabel = new Label();
        nameLabel = new Label();

        nameLabel.getStyleClass().add("body1");
        usernameLabel.getStyleClass().add("subtitle2");
        usernameLabel.setStyle("-fx-text-fill: on-surface-med-color");

        getChildren().addAll(imageView, nameLabel, usernameLabel);
        setSpacing(7.);
        setAlignment(Pos.CENTER_LEFT);

        setUser(user);
    }

    private void updateUser(){
        imageView.setImage(new Image(user.getPicturePath()));
        nameLabel.setText(user.getName());
        usernameLabel.setText("@" + user.getUsername());
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
        updateUser();
    }
}
