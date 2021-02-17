package nl.han.ica.oose.dea.dataAccess.DAO;

import nl.han.ica.oose.dea.dataAccess.SqlQueries;
import nl.han.ica.oose.dea.domain.Abonnement;
import nl.han.ica.oose.dea.presentation.dtos.Abonnementen.AbonneeAbonnementenResponse;
import nl.han.ica.oose.dea.presentation.dtos.Abonnementen.AbonnementResponseAlleAbonnementen;
import nl.han.ica.oose.dea.presentation.dtos.Abonnementen.AbonnementResponseUserAbonnementen;
import nl.han.ica.oose.dea.service.MoneyService;

import javax.inject.Inject;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.logging.Level;

public class AbonnementDAO extends DAO {
    @Inject
    private AbonnementResponseAlleAbonnementen abonnementResponseAlleAbonnementen;

    @Inject
    private AbonneeAbonnementenResponse abonneeAbonnementenResponse;

    @Inject
    private AbonnementResponseUserAbonnementen abonnementResponseUserAbonnementen;

    public AbonnementResponseAlleAbonnementen getBeschikbareAbonnementenUser(int currentUserId)  {
        try {
            connection = sqlConnection.getConnection();
            statement = connection.prepareStatement(SqlQueries.GET_ABONNEMENTEN);
            statement.setInt(1, currentUserId);
            result = statement.executeQuery();
            while (result.next()) {
                Abonnement abonnement = new Abonnement(result.getInt("abonnement_id"),
                                                       result.getString("aanbieder"),
                                                       result.getString("dienst"));
                abonnementResponseUserAbonnementen.addToAbonnementen(abonnement);
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Failed to retrieve subscriptions.", e);
        } finally {
            closeConnection(result,statement, this.connection);
        }
        return abonnementResponseAlleAbonnementen;
    }

    public AbonnementResponseUserAbonnementen getActieveAbonnementenUser(int currentUserID) {

        try {
            connection = sqlConnection.getConnection();
            statement = connection.prepareStatement(SqlQueries.GET_ABONNEE_ABONNEMENTEN);
            statement.setInt(1, currentUserID);
            result = statement.executeQuery();
            while (result.next())
            {
                Abonnement abonnement= new Abonnement(result.getInt("abonnement_id"),
                                                      result.getString("aanbieder"),
                                                      result.getString("dienst"));
                abonnementResponseUserAbonnementen.addToAbonnementen(abonnement);
                try {
                    abonnementResponseUserAbonnementen.addToTotalePrijs(MoneyService.convertStringToBigDecimal(
                            result.getString("prijs")));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }
        catch (SQLException e) {
            logger.log(Level.SEVERE, "Failed to retrieve subscriptions.", e);
        } finally {
            closeConnection(result,statement, this.connection);
        }
        return abonnementResponseUserAbonnementen;
    }

    public void addAbonnementToUser(int currentUserId, int abonnementId) throws SQLException {
        ArrayList<Object> params = new ArrayList<>();
        params.add(abonnementId);
        params.add(currentUserId);
        if(isAbonnementVerdubbelbaar(abonnementId)) {
            executeSQLQueryWithoutResult(SqlQueries.ABONNEMENT_VERDUBBELEN,
                    params,
                    "Failed to add subscription to user.");
        } else {
            executeSQLQueryWithoutResult(SqlQueries.ADD_ABONNEMENT_NIET_VERDUBBELBAAR,
                    params,
                    "Failed to add subscription to user.");
        }
    }

    private boolean isAbonnementVerdubbelbaar(int abonnementId)  {
        boolean verdubbelbaar = false;
        final int isTrue = 1;

        try {
            connection = sqlConnection.getConnection();
            statement = connection.prepareStatement(SqlQueries.GET_MOGELIJKHEID_VERDUBBELING);
            statement.setInt(1,abonnementId);
            result = statement.executeQuery();
            while (result.next()) {
                if (result.getInt("verdubbelbaar") == isTrue) {
                    verdubbelbaar = true;
                }
            }
        }
        catch (SQLException e) {
            logger.log(Level.SEVERE, "Failed to retrieve subscriptions.", e);
            e.printStackTrace();
        } finally {
            closeConnection(result, statement, this.connection);
        }
        return verdubbelbaar;
    }

    public AbonneeAbonnementenResponse getAbonnementenForUser(int currentUserId, int abonnementId) {
        try {
            connection = sqlConnection.getConnection();
            statement = connection.prepareStatement(SqlQueries.GET_ABONNEMENT_DETAILS);
            statement.setInt(1,currentUserId);
            statement.setInt(2,abonnementId);
            result = statement.executeQuery();
            while (result.next()) {
                abonneeAbonnementenResponse.setId(result.getInt("abonnement_id"));
                abonneeAbonnementenResponse.setAanbieder(result.getString("aanbieder"));
                abonneeAbonnementenResponse.setDienst(result.getString("dienst"));
                try {
                    abonneeAbonnementenResponse.setPrijs(MoneyService.convertStringToBigDecimal(
                            result.getString("prijs")));
                } catch (ParseException e) {
                    logger.log(Level.SEVERE, "An error occured while getting subscription price", e);
                    e.printStackTrace();
                }
                abonneeAbonnementenResponse.setStartDatum(result.getString("start_date"));
                abonneeAbonnementenResponse.setVerdubbeling(result.getString("verdubbeling"));
                abonneeAbonnementenResponse.setDeelbaar(result.getBoolean("deelbaar"));
                abonneeAbonnementenResponse.setStatus(result.getString("status"));
            }
        }
        catch (SQLException e) {
            logger.log(Level.SEVERE, "Failed to retrieve subscriptions", e);
            e.printStackTrace();
        } finally {
            closeConnection(result,statement, this.connection);
        }
        return abonneeAbonnementenResponse;
    }

    public AbonneeAbonnementenResponse abonnementOpzeggen(int currentUserId, int abonnementId) throws SQLException {
        ArrayList<Object> params = new ArrayList<>();
        params.add(abonnementId);
        params.add(currentUserId);
        executeSQLQueryWithoutResult(SqlQueries.ABONNEMENT_OPZEGGEN,params,"Failed to upgrade subscription");
        return getAbonnementenForUser(currentUserId, abonnementId);
    }

    public AbonneeAbonnementenResponse upgradeAbonnement(int currentUserId, int abonnementId) throws SQLException {
        ArrayList<Object> params = new ArrayList<>();
        params.add(abonnementId);
        params.add(currentUserId);
        executeSQLQueryWithoutResult(SqlQueries.ABONNEMENT_VERDUBBELEN,params,"Failed to upgrade subscription");
        return getAbonnementenForUser(currentUserId, abonnementId);
    }


    public void setResponseAllAbonnementen(AbonnementResponseAlleAbonnementen responseAllSubscriptions) {
        this.abonnementResponseAlleAbonnementen = responseAllSubscriptions;
    }

    public void setAbonnementResponseUserbonnementen(AbonnementResponseUserAbonnementen abonnementResponseUserAbonnementen) {
        this.abonnementResponseUserAbonnementen = abonnementResponseUserAbonnementen;
    }

    public void setResponseAbonneeAbonnementen(AbonneeAbonnementenResponse abonneeAbonnementenResponse) {
        this.abonneeAbonnementenResponse = abonneeAbonnementenResponse;
    }

}


