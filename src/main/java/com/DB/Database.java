package com.DB;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Cristian
 */
public class Database {
    
    /**
     * datos para realizar la conexion a la base de datos
     * 
     * host: ubicación de la base de datos (localhost o puede ser una direccion IP)
     * port: puerto en el que esta encendido la base de datos
     * db: nombre de la base de datos
     * password: clave para poder ingresar
     * connection: contiene la conexion con la base de datos
     * statement: puente entre la conexion de la DB, datos de entradas y ejecutador de sentencias SQL hacia la DB
     * result: almacenador de datos obtenidos una vez ejecutada las sentencias SQL del statement
    */
    
    private static String host;
    private static Integer port;
    private static String db;
    private static String username;
    private static String password;
    protected static Connection connection;
    protected static PreparedStatement statement;
    protected static ResultSet result;
    
    //* Constructor Lee las variables de entornos (las variables del .env)
    public Database() {
        Map<String, String> envVariables = new HashMap<>();
        String line;
        if(validateAttributes())
        try (BufferedReader br = new BufferedReader(new FileReader(".env"))) {
            while ((line = br.readLine()) != null) {
                // Ignorar líneas vacías y comentarios
                if (line.trim().isEmpty() || line.startsWith("#"))
                    continue;
                
                String[] parts = line.split("=", 2);
                
                if(parts.length == 2) 
                    envVariables.put(parts[0].trim(), parts[1].trim());
            }
            
            if(!validateEnv(envVariables)) {
                host = envVariables.get("host");
                port = Integer.valueOf(envVariables.get("port"));
                db = envVariables.get("database");
                username = envVariables.get("user");
                password = envVariables.get("password");
            } else throw new IOException("environment variables not found");
            
        } catch (IOException | NumberFormatException e) { //* Si las variables no son encontradas o no se encontro el .env
            System.err.println(e.getMessage());
            //*  Se les coloca estos por defecto (son los de mi PC xd)
            host = "localhost";
            port = 3306;
            db = "file_manager";
            username = "root";
            password = "";
        }
    }
    
    //* Verificar si los atributos que almacenaran los datos de las variables de entorno (.env) estan vacios 
    private boolean validateAttributes() {
        return host == null && port == null && db == null && username == null && password == null;
    }
    
    //* Validar si se encuentra las variables de entorno esperadas
    private boolean validateEnv(Map<String, String> envs) {
        return envs.get("host")==null && envs.get("port")==null && envs.get("database")==null && envs.get("user")==null && envs.get("password")==null; 
    }
    
    //* Aplicar conexion de la base de datos
    public void applyConnection() throws SQLException {
        String url = String.format("jdbc:mysql://%s:%d/%s",host,port,db);
        connection = DriverManager.getConnection(url,username,password);
    }
    
    //* Retroceder justo antes de cambiar el autoCommit
    public void applyRollBack() throws SQLException {
        connection.rollback();
        closeConnection();
    }
    
    //* Verificar si se encuentra los controladores en el proyecto
    public static boolean verifyController() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return true;
        } catch (ClassNotFoundException e) {
            System.err.println("Error al cargar el controlador JDBC: " + e.getMessage());
            return false;
        }
    }
    
    //* Verificar si la conexion de la base de datos esta activa
    public boolean isConnected() throws SQLException {
        return (!connection.isClosed());
    }
    
    //* Cerrar conexion con la Base de datos
    public void closeConnection() throws SQLException {
        if(connection!=null && (!connection.isClosed())) connection.close();
        if(statement!=null && (!statement.isClosed())) statement.close();
        if(result!=null && (!result.isClosed())) result.close();
    }
}
