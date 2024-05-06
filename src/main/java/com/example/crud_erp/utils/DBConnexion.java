package com.example.crud_erp.utils;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnexion {
    private final String URL = "jdbc:mysql://localhost:3306/erp";
    private final String USER = "root";
    private final String PASS = "";
    private Connection connection;

    private  static DBConnexion instance;
    public DBConnexion(){
        try {
            connection = DriverManager.getConnection(URL,USER,PASS);
            System.out.println("Connection réussite");
        } catch (SQLException e) {
            System.err.println("Échec de la connexion : " + e.getMessage());
        }
    }

    public static DBConnexion getInstance() {
        if(instance == null)
            instance = new DBConnexion();
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }
}
