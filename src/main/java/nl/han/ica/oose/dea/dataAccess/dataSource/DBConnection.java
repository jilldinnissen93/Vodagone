package nl.han.ica.oose.dea.dataAccess.dataSource;

//import com.microsoft.sqlserver.jdbc.SQLServerDataSource;

import javax.enterprise.inject.Default;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

@Default
public class DBConnection implements ISQLConnection {

    private static final Logger logger = Logger.getLogger(DBConnection.class.getName());
    private final AppProperties appProperties = new AppProperties();

    public Connection getConnection() throws SQLException {

        try {
            Class.forName(appProperties.getProperty("database.driver"));
        } catch (ClassNotFoundException e) {
            logger.log(Level.SEVERE, "Can't load JDBC Driver " + appProperties.getProperty("database.driver"), e);
        }
        return DriverManager.getConnection(appProperties.getProperty("database.connectionString"));
    }
}
