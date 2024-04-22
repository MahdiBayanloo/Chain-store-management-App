package com.example.test;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class AddGoodsController extends AdminPageController implements Initializable {
    @FXML
    public Button logout_btn;
    @FXML
    private Label AdStore_name;

    @FXML
    public Button finishButton;
    @FXML
    public TextField tf_gdName;
    @FXML
    public TextField tf_gdPrice;
    @FXML
    public TextField tf_gdNumber;

    @FXML
    public RadioButton rb_grain;
    @FXML
    public RadioButton rb_proteins;
    @FXML
    public RadioButton rb_dairy;
    @FXML
    public RadioButton rb_vegetables;
    @FXML
    public RadioButton rb_fruits;
    @FXML
    public RadioButton rb_drinks;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ToggleGroup toggleGroup = new ToggleGroup();
        rb_grain.setToggleGroup(toggleGroup);
        rb_proteins.setToggleGroup(toggleGroup);
        rb_dairy.setToggleGroup(toggleGroup);
        rb_vegetables.setToggleGroup(toggleGroup);
        rb_fruits.setToggleGroup(toggleGroup);
        rb_drinks.setToggleGroup(toggleGroup);

        logout_btn.setOnAction(event -> DBUtils.changeScene(event, "Admin.fxml", "Admin", null, null));


        AdStore_name.setText(static_store_name.getText());
        finishButton.setOnAction(event -> {
            String toggleName = ((RadioButton) toggleGroup.getSelectedToggle()).getText();
            if (!tf_gdName.getText().trim().isEmpty() && !tf_gdPrice.getText().trim().isEmpty() && !tf_gdNumber.getText().trim().isEmpty()) {
                AdminPageController x = new AdminPageController();
                x.setUserInformation(null, AdStore_name.getText());
                DBUtils.AddGoods(event, tf_gdName.getText(), Integer.parseInt(tf_gdPrice.getText()) , Integer.parseInt(tf_gdNumber.getText()), toggleName, AdStore_name.getText());
            } else {
                System.out.println("please fill all information");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("please fill all information");
                alert.show();
            }
        });
    }
}
