package nl.han.ica.oose.dea.domain;

public class Abonnement {
    private int abonnementId;
    private String aanbieder;
    private String dienst;

    public Abonnement(int abonnementId, String aanbieder, String dienst) {
        this.abonnementId = abonnementId;
        this.aanbieder = aanbieder;
        this.dienst = dienst;
    }

    public int getAbonnementId() {
        return abonnementId;
    }

    public String getAanbieder() {
        return aanbieder;
    }

    public String getDienst() {
        return dienst;
    }
}
