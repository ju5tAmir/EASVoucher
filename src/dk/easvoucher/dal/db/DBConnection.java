package dk.easvoucher.dal.db;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import dk.easvoucher.exeptions.AlertHandler;
import dk.easvoucher.exeptions.ExceptionHandler;
import dk.easvoucher.exeptions.ExceptionMessage;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {
    private final SQLServerDataSource sqlServerDataSource;

    /**
     * Constructor method
     */
    public DBConnection(){
        Properties properties = loadProperties();
        sqlServerDataSource = loadDataSource(properties);
    }


    /**
     * Loading database credentials from config file
     * @return Properties object which contains data
     */
    private Properties loadProperties() {
        Properties properties = new Properties();
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("dk/easvoucher/dal/db/config.properties")) {
            properties.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load properties file", e);
        }
        return properties;
    }

    private SQLServerDataSource loadDataSource(Properties properties){
        SQLServerDataSource ds = new SQLServerDataSource();
        ds.setDatabaseName(properties.getProperty("database"));
        ds.setUser(properties.getProperty("username"));
        ds.setPassword(properties.getProperty("password"));
        ds.setServerName(properties.getProperty("server"));
        ds.setPortNumber(Integer.parseInt(properties.getProperty("port")));
        ds.setTrustServerCertificate(true);
        return ds;
    }

    public Connection getConnection() throws ExceptionHandler {
        try {
            return sqlServerDataSource.getConnection();
        } catch (SQLException e) {
            throw new ExceptionHandler(ExceptionMessage.DB_CONNECTION_FAILURE.getValue());
        }
    }

}


