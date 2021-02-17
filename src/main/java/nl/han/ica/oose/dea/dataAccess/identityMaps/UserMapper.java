package nl.han.ica.oose.dea.dataAccess.identityMaps;

import nl.han.ica.oose.dea.domain.User;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;

public class UserMapper implements IUserMapper{
    private ArrayList<User> users = new ArrayList<>();
    private static UserMapper instance = new UserMapper();

    private UserMapper() {
        //Private omdat er alleen in deze klasse één keer een instantie van deze klasse mag worden aangemaakt.
    }

    @Override
    public Optional<User> findUserByToken(String token) {
        for (User user: users) {
            if(user.getToken().equals(token)){
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> findUserByID(int id) {
        for (User user: users) {
            if(Objects.equals(user.getUserId(), id)){
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }

    @Override
    public void insert(User thisUser) {
        users.add(thisUser);
    }

    @Override
    public void update(User thisUser, User replacementUser) {
        for (User user: users) {
            if(user == thisUser){
                delete(user);
                insert(replacementUser);
                return;
            }
        }
    }

    @Override
    public void delete(User thisUser) {
        users.remove(thisUser);
    }

    public static IUserMapper getUserMapper() {
        return instance;
    }
}
