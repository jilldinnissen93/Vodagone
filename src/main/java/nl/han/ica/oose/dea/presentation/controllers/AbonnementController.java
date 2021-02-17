package nl.han.ica.oose.dea.presentation.controllers;

import nl.han.ica.oose.dea.dataAccess.DAO.AbonnementDAO;
import nl.han.ica.oose.dea.domain.Abonnement;
import nl.han.ica.oose.dea.presentation.dtos.Abonnementen.AbonneeAbonnementenResponse;
import nl.han.ica.oose.dea.presentation.dtos.Abonnementen.AbonnementRequest;
import nl.han.ica.oose.dea.presentation.dtos.Abonnementen.AbonnementResponseUserAbonnementen;
import org.apache.http.HttpStatus;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.ArrayList;

@Path("abonnementen")
public class AbonnementController extends Controller {

    @Inject
    private AbonnementDAO abonnementDAO;

    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllAbonnees(@QueryParam("token") String token, @QueryParam("filter") String filter) {
        int currentUserId = getLoggedInUser(token);
        if (isUserId(currentUserId)) {
            ArrayList<Abonnement> response = abonnementDAO.getBeschikbareAbonnementenUser(currentUserId).getAbonnementen();
            if (!response.isEmpty()) {
                return Response.status(HttpStatus.SC_OK).entity(response).build();
            }
        }
        return Response.status(HttpStatus.SC_FORBIDDEN).build();
    }

    @POST
    @Path("")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addAbonnees(@QueryParam("token")String token, AbonnementRequest request) throws SQLException {
        int currentUserId = getLoggedInUser(token);
        if (isUserId(currentUserId)) {
            abonnementDAO.addAbonnementToUser(currentUserId, request.getId());
            AbonnementResponseUserAbonnementen response =
                    abonnementDAO.getActieveAbonnementenUser(currentUserId);
            if(response != null) {
                return Response.status(HttpStatus.SC_OK).entity(response).build();
            }
        }
        return Response.status(HttpStatus.SC_FORBIDDEN).build();
    }

    @GET
    @Path("")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserSubscriptions(@QueryParam("token") String token){
        int currentUserId = getLoggedInUser(token);
        if (isUserId(currentUserId)) {
            AbonnementResponseUserAbonnementen response =
                    abonnementDAO.getActieveAbonnementenUser(currentUserId);
            if(response != null) {
                return Response.status(HttpStatus.SC_OK).entity(response).build();
            }
        }
        return Response.status(HttpStatus.SC_FORBIDDEN).build();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserAbonnementById(@QueryParam("token") String token, @PathParam("id") int id){
        int currentUserId = getLoggedInUser(token);
        if (isUserId(currentUserId)) {
            AbonneeAbonnementenResponse response =
                    abonnementDAO.getAbonnementenForUser(currentUserId, id);
            if (response != null) {
                return Response.status(HttpStatus.SC_OK).entity(response).build();
            }
        }
        return Response.status(HttpStatus.SC_FORBIDDEN).build();
    }

    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteSubscriptionByID(@QueryParam("token") String token, @PathParam("id") int id) throws SQLException {
        int currentUserId = getLoggedInUser(token);
        if (isUserId(currentUserId)) {
            AbonneeAbonnementenResponse response =
                    abonnementDAO.abonnementOpzeggen(currentUserId, id);
            if(response != null) {
                return Response.status(HttpStatus.SC_OK).entity(response).build();
            }
        }
        return Response.status(HttpStatus.SC_FORBIDDEN).build();
    }

    @POST
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response upgradeUserSubscriptionByID(@QueryParam("token") String token, @PathParam("id") int id) throws SQLException {
        int currentUserId = getLoggedInUser(token);
        if (isUserId(currentUserId)) {
            AbonneeAbonnementenResponse response =
                    abonnementDAO.upgradeAbonnement(currentUserId, id);
            if(response!=null) {
                return Response.status(HttpStatus.SC_OK).entity(response).build();
            }
        }
        return Response.status(HttpStatus.SC_FORBIDDEN).build();
    }

}

