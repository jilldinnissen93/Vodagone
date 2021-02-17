package nl.han.ica.oose.dea.dataAccess.identityMaps;

import nl.han.ica.oose.dea.domain.Abonnee;

import java.util.ArrayList;
import java.util.Optional;

public class AbonneeMapper implements IAbonneeMapper{
    private ArrayList<Abonnee> abonnees = new ArrayList<>();
    private final int REFRESH_FREQUENCY_MILLISECONDS = 300000; // 5 minutes
    private long startTime = System.currentTimeMillis();

    @Override
    public Optional<Abonnee> find(int abonnee_id) {
        return Optional.empty();
    }

    @Override
    public void insert(Abonnee abonnee) {
        abonnees.add(abonnee);
    }

    @Override
    public void update(Abonnee abonnee) {

    }

    @Override
    public void delete(Abonnee abonnee) {
        abonnees.remove(abonnee);
    }

    @Override
    public boolean timeForUpdate() {
        if ((System.currentTimeMillis() - startTime) > REFRESH_FREQUENCY_MILLISECONDS) {
            startTime = System.currentTimeMillis();
            return true;
        } else {
            return false;
        }
    }

    @Override
    public ArrayList<Abonnee> getAbonnees() {
        return null;
    }
}
