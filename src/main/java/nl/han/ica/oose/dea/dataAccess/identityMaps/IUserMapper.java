package nl.han.ica.oose.dea.dataAccess.identityMaps;

import nl.han.ica.oose.dea.domain.User;

import java.util.Optional;

public interface IUserMapper {
        Optional<User> findUserByID(int id);

        Optional<User> findUserByToken(String token);

        void insert(User user);

        void update(User user, User replacement);

        void delete(User user);
}
