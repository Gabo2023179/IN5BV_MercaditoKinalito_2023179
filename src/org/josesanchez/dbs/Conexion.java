package org.josesanchez.dbs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

    private Connection conexion;
    private static Conexion instancia;

    public Conexion() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/DBMercaditoKinalito?useSSL=false&serverTimezone=America/Guatemala", "2023179_IN5BV", "abc123!!");
          //    conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/DBMercaditoKinalito?useSSL=false&serverTimezone=America/Guatemala", "root", "admin");
         //   conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/DBMercaditoKinalito?useSSL=false&serverTimezone=America/Guatemala", "root", "RootKinal2024$");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Conexion getInstance() {
        if (instancia == null) {
            instancia = new Conexion();
        }
        return instancia;
    }

    public Connection getConexion() {
        return conexion;
    }

    public void setInstancia(Connection instancia) {
        this.conexion = instancia;
    }

}
