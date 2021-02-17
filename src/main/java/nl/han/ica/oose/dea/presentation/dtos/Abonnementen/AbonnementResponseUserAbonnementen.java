package nl.han.ica.oose.dea.presentation.dtos.Abonnementen;

import nl.han.ica.oose.dea.domain.Abonnement;

import java.math.BigDecimal;
import java.util.ArrayList;

public class AbonnementResponseUserAbonnementen {

    private ArrayList<Abonnement> abonnementen = new ArrayList<>();
    private BigDecimal totalePrijs = new BigDecimal(0);


    public void addToTotalePrijs(BigDecimal prijs) {
        this.totalePrijs = totalePrijs.add(prijs);
    }

    public void addToAbonnementen(Abonnement abonnement){
        abonnementen.add(abonnement);
    }
}
