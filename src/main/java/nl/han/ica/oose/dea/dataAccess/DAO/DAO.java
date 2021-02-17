package nl.han.ica.oose.dea.dataAccess.DAO;

import nl.han.ica.oose.dea.dataAccess.dataSource.ISQLConnection;
import nl.han.ica.oose.dea.dataAccess.identityMaps.IUserMapper;
import nl.han.ica.oose.dea.dataAccess.identityMaps.UserMapper;

import javax.inject.Inject;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

abstract class DAO {
    static final Logger logger = Logger.getLogger(AbonneeDAO.class.getName());

    @Inject
    ISQLConnection sqlConnection;

    final IUserMapper userMap = UserMapper.getUserMapper();

    Connection connection = null;
    PreparedStatement statement = null;
    ResultSet result = null;

    public void setConnection(ISQLConnection sqlConnection) {
        this.sqlConnection = sqlConnection;
    }

    void executeSQLQueryWithoutResult(String sqlQuery, ArrayList<Object> parameters, String errorMessage) throws SQLException {
        connection = sqlConnection.getConnection();
        statement = connection.prepareStatement(sqlQuery);
        int i = 1;

        for (Object o : parameters){
            if(o instanceof Integer){
                statement.setInt(i,(Integer) o);
            } else if (o instanceof String){
                statement.setString(i,o.toString());
            }
            i++;
        }
        logger.log(Level.INFO,"\nExecuting query: "+sqlQuery+"\n Potential error: "+errorMessage);
        statement.execute();
        closeConnection(result,statement,connection);
    }

    void closeConnection(ResultSet result, Statement statement, Connection connection){
        try {
            if (result != null)
                result.close();
            if (statement != null)
                statement.close();
            if (connection != null)
                connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
