package com.example.test;


import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.sql.*;
import java.io.IOException;
import java.util.Objects;

public class DBUtils extends javax.swing.JFrame {


    public static void changeScene(ActionEvent event, String fxmlfile, String title, String username, String storname){
        Parent root = null;
        if(username != null){
            try{
                FXMLLoader loader = new FXMLLoader(DBUtils.class.getResource(fxmlfile));
                root = loader.load();
                AdminPageController adminPageController = loader.getController();
                adminPageController.setUserInformation(username, storname);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {
            try {
                root = FXMLLoader.load(Objects.requireNonNull(DBUtils.class.getResource(fxmlfile)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle(title);
        stage.setScene(new Scene(root, 600, 400));
        stage.show();

    }

    public static void AddGoods(ActionEvent event, String gdName, int gdPrice, int gdNumber, String gdGroup, String storeName){
        Connection connection = null;
        PreparedStatement psInsert = null;
        PreparedStatement psCheckExists = null;
        ResultSet resultSet = null;
        try{
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/javaproject", "root", "");
            psCheckExists = connection.prepareStatement("SELECT * FROM goods WHERE gdName = ?");
            psCheckExists.setString(1,gdName);
            resultSet = psCheckExists.executeQuery();


            if(resultSet.isBeforeFirst()){
                System.out.println("Product already exists!");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Product already exists!");
                alert.show();
            } else {
                psInsert = connection.prepareStatement("INSERT INTO goods (gdName, gdPrice, gdNumber, gdGroup, gdStoreName) VALUES (?,?,?,?,?)");
                psInsert.setString(1,gdName);
                psInsert.setInt(2,gdPrice);
                psInsert.setInt(3,gdNumber);
                psInsert.setString(4,gdGroup);
                psInsert.setString(5,storeName);
                psInsert.executeUpdate();

                System.out.println("New Product is added!");

                changeScene(event,"Admin.fxml","Admin",null, storeName);


            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null){
                try{
                    resultSet.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if(psCheckExists != null){
                try{
                    psCheckExists.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if(psInsert != null){
                try{
                    psInsert.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if(connection != null){
                try{
                    connection.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }
    }

    public static void signUpUser(ActionEvent event, String username, String password, String storeName){
        Connection connection = null;
        PreparedStatement psInsert = null;
        PreparedStatement psCheckExists = null;
        ResultSet resultSet = null;
        try{
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/javaproject", "root", "");
            psCheckExists = connection.prepareStatement("SELECT * FROM user WHERE Username = ?");
            psCheckExists.setString(1,username);
            resultSet = psCheckExists.executeQuery();


            if(resultSet.isBeforeFirst()){
                System.out.println("User already exists!");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("you can not use this username!");
                alert.show();
            } else {
                psInsert = connection.prepareStatement("INSERT INTO user (Username, Password, StoreName) VALUES (?,?,?)");
                psInsert.setString(1,username);
                psInsert.setString(2,password);
                psInsert.setString(3,storeName);
                psInsert.executeUpdate();

                System.out.println("Admin or Store is added!");

                changeScene(event,"Boss.fxml","Boss",null,null);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null){
                try{
                    resultSet.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if(psCheckExists != null){
                try{
                    psCheckExists.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if(psInsert != null){
                try{
                    psInsert.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if(connection != null){
                try{
                    connection.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }
    }


    public  static void logInUser(ActionEvent event, String username, String password){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try{
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/javaproject", "root", "");
            preparedStatement = connection.prepareStatement("SELECT password, StoreName FROM user WHERE username = ?");
            preparedStatement.setString(1,username);
            resultSet = preparedStatement.executeQuery();

            if(!resultSet.isBeforeFirst()){
                System.out.println("User not found in the database!");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Provided credentials are incorrect!");
                alert.show();
            } else {
                while (resultSet.next()){
                    String retrievedPassword = resultSet.getString("Password");
                    String StoreName = resultSet.getString("StoreName");
                    if(retrievedPassword.equals(password)){
                        if(Objects.equals(StoreName, "Boss"))
                            changeScene(event,"Boss.fxml","Boss", username, StoreName);
                        else {
                            changeScene(event, "Admin.fxml", "Admin", username, StoreName);

                        }
                    } else{
                        System.out.println("Password did not match!");
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("The provided credentials are incorrect!");
                        alert.show();
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null){
                try{
                    resultSet.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if(preparedStatement != null){
                try{
                    preparedStatement.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if(connection != null){
                try{
                    connection.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }
    }


}
