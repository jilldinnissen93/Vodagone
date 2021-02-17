package nl.han.ica.oose.dea.dataAccess.dataSource;

import java.sql.Connection;
import java.sql.SQLException;

public interface ISQLConnection {
    Connection getConnection() throws SQLException;
}
