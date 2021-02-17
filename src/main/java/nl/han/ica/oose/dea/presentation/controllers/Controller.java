package nl.han.ica.oose.dea.presentation.controllers;

import nl.han.ica.oose.dea.dataAccess.DAO.AbonneeDAO;
import nl.han.ica.oose.dea.dataAccess.identityMaps.UserMapper;

import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class Controller {
    static final Logger logger = Logger.getLogger(AbonneeDAO.class.getName());

    public int getLoggedInUser(String token){
        int userId;
        if (UserMapper.getUserMapper().findUserByToken(token).isPresent()) {
            userId = UserMapper.getUserMapper().findUserByToken(token).get().getUserId();
        } else {
            logger.log(Level.FINE, "Unable to find user information.");
            return -1;
        }
        return  userId;
    }

    public boolean isUserId(int userId){
        return userId != -1;
    }
}
