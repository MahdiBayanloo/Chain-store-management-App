package com.example.test;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

public class BossPageController implements Initializable {
    @FXML
    public Button AddStore;
    @FXML
    public Button AddAdmin;
    @FXML
    private Button logout_btn;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        logout_btn.setOnAction(event -> DBUtils.changeScene(event, "sample.fxml", "log in!", null, null));

        AddAdmin.setOnAction(event -> DBUtils.changeScene(event, "Add_Admin.fxml", "AddUser", null, null));
        AddStore.setOnAction(event -> DBUtils.changeScene(event, "Add_Admin.fxml", "AddStore", null, null));
    }

}
