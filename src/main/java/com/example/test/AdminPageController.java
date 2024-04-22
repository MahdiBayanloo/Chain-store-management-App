package com.example.test;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.awt.event.MouseEvent;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import java.util.function.Predicate;

public class AdminPageController implements Initializable {

    @FXML
    public TableColumn coNumber;
    @FXML
    public TableColumn coGroup;
    @FXML
    public TableColumn coPrice;
    @FXML
    public TableColumn coName;
    @FXML
    public TableColumn coRow;
    @FXML
    public TableView <TableModel> tblGoods;
    @FXML
    private TextField txtSearchBar;


    @FXML
    private Button logout_btn;
    @FXML
    private Button searchButton;
    @FXML
    private Button AddGoodsButton;
    @FXML
    private Label admin_user;
    @FXML
    private Label store_name;
    public static Label static_store_name;

    final ObservableList<TableModel> goodsList= FXCollections.observableArrayList();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        logout_btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBUtils.changeScene(event, "sample.fxml", "log in!", null, null);
            }
        });

        Goods();

        AddGoodsButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                static_store_name = store_name;
                DBUtils.changeScene(event, "Add_Goods.fxml", "Add Goods", null, null);
            }
        });

        coRow.setCellValueFactory(new PropertyValueFactory<>("gdId"));
        coName.setCellValueFactory(new PropertyValueFactory<>("gdName"));
        coPrice.setCellValueFactory(new PropertyValueFactory<>("gdPrice"));
        coNumber.setCellValueFactory(new PropertyValueFactory<>("gdNumber"));
        coGroup.setCellValueFactory(new PropertyValueFactory<>("gdGroup"));


        tblGoods.setItems(goodsList);

        searchFilter();
    }

    private void searchFilter() {
        FilteredList<TableModel> filterData= new FilteredList<>(goodsList, e->true);
        txtSearchBar.setOnKeyReleased(e->{


            txtSearchBar.textProperty().addListener((observable, oldValue, newValue) -> {
                filterData.setPredicate((Predicate<? super TableModel >) Table->{
                    if(newValue==null){
                        return true;
                    }
                    String toLowerCaseFilter = newValue.toLowerCase();
                    if(Table.getGdID().contains(newValue)){
                        return true;
                    }else  if(Table.getGdName().toLowerCase().contains(toLowerCaseFilter)){
                        return true;
                    }else  if(Table.getGdPrice().toLowerCase().contains(toLowerCaseFilter)){
                        return true;
                    }else  if(Table.getGdNumber().toLowerCase().contains(toLowerCaseFilter)){
                        return true;
                    }else  if(Table.getGdGroup().toLowerCase().contains(toLowerCaseFilter)){
                        return true;
                    }

                    return false;
                });
            });

            final SortedList<TableModel> customers = new SortedList<>(filterData);
            customers.comparatorProperty().bind(tblGoods.comparatorProperty());
            tblGoods.setItems(customers);

        });

    }



    public void setUserInformation(String Username, String StoreName) {

        admin_user.setText(Username + " خوش آمدید");
        store_name.setText(StoreName);
    }
    @FXML
    public void buttonSHow(ActionEvent event) {
        TableModel selectedgood = tblGoods.getSelectionModel().getSelectedItem();
        if(selectedgood == null) {
            System.out.println("fill the blink please!");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("the product is not found in list!");
            alert.show();
        } else if(selectedgood.getGdName().equals(String.valueOf(txtSearchBar.getText()))){
            DBUtils.changeScene(event, "Search_Result.fxml", "Search Result", null, null);
        }
    }
    // get data from db, return object List<Person> from DB
    @FXML
    public void clickedColumn(javafx.scene.input.MouseEvent mouseEvent) {
        TableModel selectedgood = tblGoods.getSelectionModel().getSelectedItem();
        txtSearchBar.setText(selectedgood.getGdName());
    }


    public void Goods() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        PreparedStatement goodsExist = null;
        ResultSet resultSet = null;
        ResultSet resultSetgd = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/javaproject", "root", "");
            preparedStatement = connection.prepareStatement("SELECT * FROM goods");
            resultSet = preparedStatement.executeQuery();

            //goodsExist = connection.prepareStatement("SELECT gdName FROM goods WHERE gdName = ?");
            //goodsExist.setString(1,txtSearchBar.getText());
            //resultSetgd = goodsExist.executeQuery();
            while (resultSet.next()) {
                String gdID = resultSet.getString("gdID");
                String gdName = resultSet.getString("gdName");
                String gdPrice = resultSet.getString("gdPrice");
                String gdNumber = resultSet.getString("gdNumber");
                String gdGroup = resultSet.getString("gdGroup");
                String gdStoreName = resultSet.getString("gdStoreName");

                goodsList.add(new TableModel(gdID, gdName, gdPrice, gdNumber, gdGroup, gdStoreName));

            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

    }

}