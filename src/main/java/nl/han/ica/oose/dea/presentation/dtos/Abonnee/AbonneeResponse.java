package nl.han.ica.oose.dea.presentation.dtos.Abonnee;

import nl.han.ica.oose.dea.domain.Abonnee;

import java.util.ArrayList;

public class AbonneeResponse {
    private ArrayList<Abonnee> abonnees = new ArrayList<>();

    public void addAbonnee(Abonnee abonnee) {
        abonnees.add(abonnee);
    }

    public ArrayList<Abonnee> getAbonnees() {
        return abonnees;
    }
}
