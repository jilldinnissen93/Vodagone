package nl.han.ica.oose.dea.dataAccess.DAO;

import nl.han.ica.oose.dea.dataAccess.identityMaps.AbonneeMapper;
import nl.han.ica.oose.dea.dataAccess.identityMaps.IAbonneeMapper;
import nl.han.ica.oose.dea.domain.Abonnee;
import nl.han.ica.oose.dea.presentation.dtos.Abonnee.AbonneeResponse;
import nl.han.ica.oose.dea.dataAccess.SqlQueries;

import javax.inject.Inject;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.logging.Level;

public class AbonneeDAO extends DAO{

    @Inject
    private IAbonneeMapper abonneeMapper;

    public AbonneeResponse getAbonnees(int userID) {
        AbonneeResponse response = new AbonneeResponse();

        if(abonneeMapper.getAbonnees().isEmpty() || abonneeMapper.timeForUpdate()) {
            try {
                connection = sqlConnection.getConnection();
                statement = connection.prepareStatement(SqlQueries.GET_ABONNEES);
                result = statement.executeQuery();
                while (result.next()) {
                    abonneeMapper.insert(new Abonnee(
                            result.getInt("abonnee_id"),
                            result.getString("gebruikersnaam"),
                            result.getString("emailadress")));
                }
            } catch (SQLException e) {
                logger.log(Level.SEVERE, "Failed to retrieve subscribers.", e);
                e.printStackTrace();
            } finally {
                closeConnection(result, statement, connection);
            }
        }
        for (Abonnee a : abonneeMapper.getAbonnees()) {
            if(!Objects.equals(a.getAbonneeId(), userID)) {
                response.addAbonnee(a);
            }
        }
        return response;
    }

    private boolean alGedeeldMetUser(int abonnementId, int sharedByUserId, int shareToUserId) {
        try {
            connection = sqlConnection.getConnection();
            statement = connection.prepareStatement(SqlQueries.ABONNEMENT_GEDEELD_MET);
            statement.setInt(1,sharedByUserId);
            statement.setInt(2,abonnementId);
            statement.setInt(3,shareToUserId);
            result = statement.executeQuery();
            if(result.next()){
                return true;
            }
        }
        catch (SQLException e) {
            logger.log(Level.SEVERE, "Failed to retrieve subscriptions", e);
            e.printStackTrace();
        } finally {
            closeConnection(result,statement, this.connection);
        }
        return  false;
    }

    private boolean isAantalMaxDeelAbonnementen(int abonnementId, int userId){
        int shareLimit = 2;

        try {
            connection = sqlConnection.getConnection();
            statement = connection.prepareStatement(SqlQueries.ABONNEMENT_DELEN_AANTAL);
            statement.setInt(1,userId);
            statement.setInt(2,abonnementId);
            result = statement.executeQuery();
            while (result.next()) {
                if (result.getInt("amount") >= shareLimit) {
                    return  true;
                }
            }
        }
        catch (SQLException e) {
            logger.log(Level.SEVERE, "Failed to retrieve subscriptions", e);
            e.printStackTrace();
        } finally {
            closeConnection(result,statement, this.connection);
        }
        return false;
    }

    public boolean deelAbonnementMetAbonnee(int abonnementId, int userId, int sharedByUserId) {
        ArrayList<Object> params = new ArrayList<>();

        if(isAantalMaxDeelAbonnementen(abonnementId, sharedByUserId) || alGedeeldMetUser(abonnementId, sharedByUserId, userId)){
            logger.log(Level.INFO, "Share limit reached or already shared with user");
            return false;
        }
        params.add(abonnementId);
        params.add(userId);
        params.add(sharedByUserId);
        try {
            executeSQLQueryWithoutResult(SqlQueries.ABONNEMENT_DELEN, params, "Unable to share subscription.");
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Unable to share subscription.");
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public void setAbonneeMapper(AbonneeMapper abonneeMapper) {
        this.abonneeMapper = abonneeMapper;
    }


}
