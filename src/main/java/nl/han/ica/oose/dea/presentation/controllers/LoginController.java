package nl.han.ica.oose.dea.presentation.controllers;

import nl.han.ica.oose.dea.dataAccess.DAO.LoginDAO;
import nl.han.ica.oose.dea.presentation.dtos.Login.LoginRequest;
import nl.han.ica.oose.dea.presentation.dtos.Login.LoginResponse;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.logging.Level;

@Path("login")
public class LoginController extends Controller {

    @Inject
    private LoginDAO loginDAO;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(LoginRequest request) throws SQLException {
        logger.log(Level.SEVERE, "d");
        LoginResponse user = loginDAO.getUserForLogin(request.getUser(),request.getPassword());
        if (user != null) {
            return Response.status(200).entity(user).build();
        }

        return Response.status(403).build();
    }


}
