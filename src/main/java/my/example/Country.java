package my.example;

/**
 * This is the class which using the the constructor and set the variables.
 *
 * @author Goh Deng Vee
 * @author Gan Wei Cai
 * @author Wei Jun Shong
 */
public class Country {
    String date;
    String countryName;
    int cases;
    int death;
    int recovery;

    /**
     * This constructor inherits the constructor of parent class
     *
     * @param date        = set the date for the Covid-19
     * @param countryName = set the country in Covid-19
     * @param cases       = set the cases for the Covid-19
     * @param death       = set the death for the Covid-19
     * @param recovery    = set the recovery cases for the Covid-19
     */
    public Country(String date, String countryName, int cases, int death, int recovery) {
        this.date = date;
        this.countryName = countryName;
        this.cases = cases;
        this.death = death;
        this.recovery = recovery;
    }
}
