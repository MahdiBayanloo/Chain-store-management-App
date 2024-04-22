package com.example.test;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;


public class AddAdminController implements Initializable {
    @FXML
    private Button logout_btn;
    @FXML
    private TextField tf_storeName;
    @FXML
    private TextField tf_password;
    @FXML
    private Button finishButton;
    @FXML
    private TextField tf_username;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        logout_btn.setOnAction(event -> DBUtils.changeScene(event, "Boss.fxml", "Boss", null, null));

        finishButton.setOnAction(event -> {
            if (!tf_username.getText().trim().isEmpty() && !tf_password.getText().trim().isEmpty() && !tf_storeName.getText().trim().isEmpty()) {
                DBUtils.signUpUser(event, tf_username.getText(), tf_password.getText(), tf_storeName.getText());
            } else {
                System.out.println("please fill all information");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("please fill all information");
                alert.show();
            }
        });
    }
}
