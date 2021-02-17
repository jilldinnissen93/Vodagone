package nl.han.ica.oose.dea.domain;

public class Abonnee{
    private int abonneeId;
    private String gebruikersnaam;
    private String emailadress;

    public Abonnee(int abonneeId, String gebruikersnaam, String emailadress) {
        this.abonneeId = abonneeId;
        this.gebruikersnaam = gebruikersnaam;
        this.emailadress = emailadress;
    }

    public int getAbonneeId() {
        return abonneeId;
    }
}
