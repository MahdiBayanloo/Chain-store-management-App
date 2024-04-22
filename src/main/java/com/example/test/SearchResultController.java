package com.example.test;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class SearchResultController extends AdminPageController implements Initializable {
    @FXML
    public Button logout_btn;
    @FXML
    public Button sellButton;
    @FXML
    public Label name;
    @FXML
    public Label price;
    @FXML
    public Label number;
    @FXML
    public Label group;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        logout_btn.setOnAction(event -> DBUtils.changeScene(event, "Admin.fxml", "Admin", null, null));
    }
}
