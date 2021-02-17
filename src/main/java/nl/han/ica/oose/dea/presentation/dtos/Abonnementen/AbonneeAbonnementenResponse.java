package nl.han.ica.oose.dea.presentation.dtos.Abonnementen;

import java.math.BigDecimal;

public class AbonneeAbonnementenResponse {
    private int id;
    private String aanbieder;
    private String dienst;
    private BigDecimal prijs;
    private String startDatum;
    private String verdubbeling;
    private boolean deelbaar; // moet dit wel string zijn of boolean?
    private String status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAanbieder() {
        return aanbieder;
    }

    public void setAanbieder(String aanbieder) {
        this.aanbieder = aanbieder;
    }

    public BigDecimal getPrijs() {
        return prijs;
    }

    public void setPrijs(BigDecimal prijs) {
        this.prijs = prijs;
    }

    public String getStartDatum() {
        return startDatum;
    }

    public void setStartDatum(String startDatum) {
        this.startDatum = startDatum;
    }

    public String getVerdubbeling() {
        return verdubbeling;
    }

    public void setVerdubbeling(String verdubbeling) {
        this.verdubbeling = verdubbeling;
    }

    public boolean isDeelbaar() {
        return deelbaar;
    }

    public void setDeelbaar(boolean deelbaar) {
        this.deelbaar = deelbaar;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setDienst(String dienst) {
        this.dienst = dienst;
    }
}
