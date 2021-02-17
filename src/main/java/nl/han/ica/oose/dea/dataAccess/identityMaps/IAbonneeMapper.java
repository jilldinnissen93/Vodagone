package nl.han.ica.oose.dea.dataAccess.identityMaps;

import nl.han.ica.oose.dea.domain.Abonnee;

import java.util.ArrayList;
import java.util.Optional;

public interface IAbonneeMapper {
    Optional<Abonnee> find(int abonnee_id);

    void insert(Abonnee abonnee);

    void update(Abonnee abonnee);

    void delete(Abonnee abonnee);

    boolean timeForUpdate();

    ArrayList<Abonnee> getAbonnees();
}
