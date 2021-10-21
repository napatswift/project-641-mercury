package ku.cs.models.components.listCell;

import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.SVGPath;
import ku.cs.models.coupon.Coupon;
import ku.cs.models.coupon.CouponList;
import ku.cs.models.coupon.CouponType;

public class PromotionListCell extends ListCell<Coupon> {
    private final CouponList couponList;
    private final GridPane gridPane;
    private final Label codeLabel;
    private final Label descriptionLabel;
    private final SVGPath statusToggleBtnSvgPath;

    public PromotionListCell(CouponList couponList) {
        this.couponList = couponList;
        codeLabel = new Label();
        Label codeTextLabel = new Label("code");
        descriptionLabel = new Label();
        statusToggleBtnSvgPath = new SVGPath();
        Button activeStatusToggleButton = new Button();
        activeStatusToggleButton.setGraphic(statusToggleBtnSvgPath);
        activeStatusToggleButton.setOnAction(this::handleToggleBtn);
        Button deleteButton = new Button();
        SVGPath deleteSvgPath = new SVGPath();
        statusToggleBtnSvgPath.setContent("M17 7H7c-2.76 0-5 2.24-5 5s2.24 5 5 5h10c2.76 0 5-2.24 5-5s-2.24-5-5-5zm0 8c-1.66 0-3-1.34-3-3s1.34-3 3-3 3 1.34 3 3-1.34 3-3 3z");
        statusToggleBtnSvgPath.setStyle("-fx-fill: on-surface-color");
        deleteSvgPath.setContent("M6 19c0 1.1.9 2 2 2h8c1.1 0 2-.9 2-2V7H6v12zM19 4h-3.5l-1-1h-5l-1 1H5v2h14V4z");
        deleteSvgPath.setStyle("-fx-fill: on-surface-color");
        deleteButton.setGraphic(deleteSvgPath);
        deleteButton.setOnAction(this::handleDeleteCoupon);

        getStylesheets().add("promotion-list-cell");
        codeLabel.getStyleClass().add("h6");
        codeTextLabel.getStyleClass().add("caption");
        descriptionLabel.getStyleClass().add("body1");

        gridPane = new GridPane();
        ColumnConstraints tCol1 = new ColumnConstraints(); tCol1.setPercentWidth(25);
        ColumnConstraints tCol2 = new ColumnConstraints(); tCol2.setPercentWidth(60);
        ColumnConstraints tCol3 = new ColumnConstraints(); tCol3.setPercentWidth(15);
        gridPane.getColumnConstraints().addAll(tCol1, tCol2, tCol3);
        VBox codeVBox = new VBox(codeTextLabel, codeLabel);
        VBox descVBox = new VBox(descriptionLabel);
        HBox statusHBox = new HBox(activeStatusToggleButton, deleteButton);
        statusHBox.setAlignment(Pos.CENTER_RIGHT);

        gridPane.add(codeVBox, 0, 0);
        gridPane.add(descVBox, 1, 0);
        gridPane.add(statusHBox, 2, 0);
        setPadding(new Insets(10));
    }

    private void handleDeleteCoupon(ActionEvent event){
        couponList.removeCoupon((CouponType) getItem());
    }

    private void handleToggleBtn(ActionEvent event) {
        Coupon coupon = getItem();
        coupon.setStatus(!getItem().isActive());
    }

    private void toggle(boolean isActive){
        if (isActive) {
            statusToggleBtnSvgPath.setContent("M17 7H7c-2.76 0-5 2.24-5 5s2.24 5 5 5h10c2.76 0 5-2.24 5-5s-2.24-5-5-5zm0 8c-1.66 0-3-1.34-3-3s1.34-3 3-3 3 1.34 3 3-1.34 3-3 3z");
            gridPane.setOpacity(1);
        } else {
            statusToggleBtnSvgPath.setContent("M17 7H7c-2.76 0-5 2.24-5 5s2.24 5 5 5h10c2.76 0 5-2.24 5-5s-2.24-5-5-5zM7 15c-1.66 0-3-1.34-3-3s1.34-3 3-3 3 1.34 3 3-1.34 3-3 3z");
            gridPane.setOpacity(0.6);
        }
    }

    private void updateInfo(){
        codeLabel.setText(getItem().getCode());
        descriptionLabel.setText(getItem().toString());
    }

    @Override
    public void updateSelected(boolean selected) {
        super.updateSelected(false);
    }

    @Override
    protected void updateItem(Coupon item, boolean empty) {
        super.updateItem(item, empty);
        if (item != null && !empty) {
            setGraphic(gridPane);
            updateInfo();
            toggle(item.isActive());
            setStyle("-fx-border-width: 0 0 1 0; -fx-border-color: surface-overlay");
        } else{
            setGraphic(null);
            setStyle(null);
        }
    }
}
