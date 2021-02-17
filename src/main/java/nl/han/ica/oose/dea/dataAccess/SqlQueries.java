package nl.han.ica.oose.dea.dataAccess;

public class SqlQueries {
    public static final String LOGIN = "SELECT voornaam,achternaam,abonnee_id " +
                                       "FROM Abonnee " +
                                       "WHERE gebruikersnaam = ? AND wachtwoord = ?" ;

    public static final String GET_ABONNEES = "SELECT abonnee_id,gebruikersnaam,emailadress " +
                                              "FROM Abonnee";

    public static final String GET_ABONNEMENTEN = "SELECT * " +
                                                  "FROM Abonnement D " +
                                                  "WHERE NOT EXISTS (SELECT 1 " +
                                                  "FROM AbonneeAbonnementen AD INNER JOIN Abonnee A " +
                                                  "ON AD.abonnee_id = A.abonnee_id " +
                                                  "WHERE abonnee_id = ? AND D.abonnement_id = AD.abonnement_id " +
                                                  "AND AD.status = 'proef' OR AD.status = 'lopend')";

    public static final String GET_ABONNEE_ABONNEMENTEN = "SELECT * " +
                                                          "FROM AbonneeAbonnementen AD INNER JOIN Abonnee A " +
                                                          "ON AD.abonnee_id = A.abonnee_id AND A.user_id = ? " +
                                                          "INNER JOIN Abonnement D " +
                                                          "ON AD.abonnement_id = D.abonnement_id " +
                                                          "WHERE AD.status != 'opgezegd'";

    public static final String GET_ABONNEMENT_DETAILS = "SELECT * FROM AbonneeAbonnementen AD " +
                                                        "INNER JOIN Abonnee A " +
                                                        "ON AD.abonnee_id = A.abonnee_id AND A.abonnee_id = ? " +
                                                        "INNER JOIN Abonnement D " +
                                                        "ON AD.subscription_id = D.abonnement_id AND D.abonnement_id = ? ";

    public static final String ADD_ABONNEMENT_VERDUBBELBAAR =
            "INSERT INTO AbonneeAbonnementen VALUES ( ? , ? ,GetDate(),'standaard','proef',NULL)";

    public static final String ADD_ABONNEMENT_NIET_VERDUBBELBAAR =
            "INSERT INTO AbonneeAbonnementen VALUES ( ? ,? ,GetDate(),'niet-beschikbaar','proef',NULL)";

    public static final String GET_MOGELIJKHEID_VERDUBBELING = "SELECT verdubbeling FROM Abonnementen WHERE dienst_id = ? ";

    public static final String ABONNEMENT_OPZEGGEN =  "UPDATE AbonneeAbonnementen SET status = 'opgezegd' " +
                                                      "WHERE abonnement_id = ? and abonnee_id = ? ";

    public static final String ABONNEMENT_VERDUBBELEN = "UPDATE AbonneeAbonnementen SET verdubbeling = 'verdubbeld' " +
                                                        "WHERE abonnement_id = ? and abonnee_id = ?";

    public static final String ABONNEMENT_DELEN = "INSERT INTO AbonneeAbonnementen VALUES(" +
                                                  "?,?,GetDate(),'niet-beschikbaar','proef',?)";

    public static final String ABONNEMENT_DELEN_AANTAL = "SELECT COUNT(abonnee_id) as amount " +
                                                         "FROM AbonneeAbonnementen " +
                                                         "WHERE gedeeldVan = ? and dienst_id = ? ";

    public static final String ABONNEMENT_GEDEELD_MET = "SELECT 1 " +
                                                        "FROM AbonneeAbonnementen " +
                                                        "WHERE gedeeldVan = ? and abonnement_id = ? and abonnee_id = ?";
}
