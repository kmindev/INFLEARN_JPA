package jpql;

import javax.persistence.Embeddable;

@Embeddable
public class Address {

    private String city;
    private String sireet;
    private String zipcode;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getSireet() {
        return sireet;
    }

    public void setSireet(String sireet) {
        this.sireet = sireet;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

}
