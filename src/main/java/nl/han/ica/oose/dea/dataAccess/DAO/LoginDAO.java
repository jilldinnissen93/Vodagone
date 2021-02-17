package nl.han.ica.oose.dea.dataAccess.DAO;

import nl.han.ica.oose.dea.dataAccess.SqlQueries;
import nl.han.ica.oose.dea.domain.User;
import nl.han.ica.oose.dea.presentation.dtos.Login.LoginResponse;
import nl.han.ica.oose.dea.service.TokenService;

import javax.enterprise.inject.Default;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;

@Default
public class LoginDAO extends DAO{

    public LoginResponse getUserForLogin(String gebruikersnaam, String wachtwoord) throws SQLException {
        logger.log(Level.SEVERE,gebruikersnaam);
        User user = null;
        LoginResponse loginResponse = null;

        try  {
            Connection connection = sqlConnection.getConnection();
            statement = connection.prepareStatement(SqlQueries.LOGIN);
            statement.setString(1, gebruikersnaam);
            statement.setString(2, wachtwoord);
            result = statement.executeQuery();
            while (result.next()) {
                if (userMap.findUserByID(result.getInt("user_id")).isPresent()) {
                    user = userMap.findUserByID(result.getInt("user_id")).get();
                    User replacement = new User(result.getString("voornaam") + " " +
                            result.getString("achternaam"),
                            result.getInt("user_id"),
                            TokenService.generateToken());
                    userMap.update(user,replacement);
                } else {
                    userMap.insert(
                            user = new User(result.getString("voornaam") + " " +
                                    result.getString("achternaam"),
                                    result.getInt("abonnee_id"),
                                    TokenService.generateToken())
                    );
                }

            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Failed to retrieve user with name " + gebruikersnaam, e);
            throw new SQLException(e);
        } finally {
            closeConnection(result,statement,connection);
        }

        if (user != null) {
            loginResponse = new LoginResponse(user.getUser(),user.getToken());
        }
        return loginResponse;
    }
}
