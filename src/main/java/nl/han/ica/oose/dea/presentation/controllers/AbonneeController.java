package nl.han.ica.oose.dea.presentation.controllers;

import nl.han.ica.oose.dea.dataAccess.DAO.AbonneeDAO;
import nl.han.ica.oose.dea.presentation.dtos.Abonnee.AbonneeResponse;
import nl.han.ica.oose.dea.presentation.dtos.Abonnementen.AbonnementRequest;
import org.apache.http.HttpStatus;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("abonnees")
public class AbonneeController extends Controller{

    @Inject
    private AbonneeDAO abonneeDAO;

    @GET
    @Path("")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSubscribers(@QueryParam("token") String token)  {
        int currentUserId = getLoggedInUser(token);
        if (isUserId(currentUserId)) {
            AbonneeResponse abonnees = abonneeDAO.getAbonnees(currentUserId);
            if (abonnees != null) {
                return Response.status(HttpStatus.SC_OK).entity(abonnees.getAbonnees()).build();
            }
        }

        return Response.status(HttpStatus.SC_EXPECTATION_FAILED).build();
    }

    @POST
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response shareSubscription(@PathParam("id") int user_id,
                                      @QueryParam("token") String token,
                                      AbonnementRequest request) {
                int currentUserId = getLoggedInUser(token);
                if (isUserId(currentUserId)) {
                    if (abonneeDAO.deelAbonnementMetAbonnee(request.getId(), user_id, currentUserId)) {
                        return Response.status(HttpStatus.SC_OK).build();
                    }
        }
        return Response.status(HttpStatus.SC_EXPECTATION_FAILED).build();
    }

    public void setAbonneeDAO(AbonneeDAO abonneeDAO) {
        this.abonneeDAO = abonneeDAO;
    }

    public AbonneeDAO getAbonneeDAO() {
        return abonneeDAO;
    }
}
