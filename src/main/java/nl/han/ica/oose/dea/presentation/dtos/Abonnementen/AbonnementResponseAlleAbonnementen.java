package nl.han.ica.oose.dea.presentation.dtos.Abonnementen;

import nl.han.ica.oose.dea.domain.Abonnement;

import javax.enterprise.inject.Default;
import java.util.ArrayList;

@Default
public class AbonnementResponseAlleAbonnementen {
    private ArrayList<Abonnement> abonnementen = new ArrayList<>();

    public void addToDiensten(Abonnement abonnement){
        abonnementen.add(abonnement);
    }

    public ArrayList<Abonnement> getAbonnementen() {
        return abonnementen;
    }
}
