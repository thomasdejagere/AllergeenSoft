package domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

/**
 *
 * @author Thomas
 */
@Entity
@NamedQuery(name = "Gerecht.findAll", query= "SELECT g FROM Gerecht g")
public class Gerecht implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @OneToMany(cascade = CascadeType.PERSIST, targetEntity = Allergeen.class)
    private List<Allergeen> allergenen;
    private String naam;
    @ManyToOne(cascade = CascadeType.PERSIST)
    private GerechtSoort soort;
    
    public Gerecht(){
        allergenen = new ArrayList<>();
    }
    public void setNaam(String naam){
        this.naam = naam;
    }
    public String getNaam() {
        return naam;
    }
    
    public void addAllergeen(Allergeen allergeen){
        allergenen.add(allergeen);
    }
    public void removeAllergeen(Allergeen allergeen){
        allergenen.remove(allergeen);
    }

    public GerechtSoort getSoort() {
        return soort;
    }

    public void setSoort(GerechtSoort soort) {
        this.soort = soort;
    }
    
    public String getAllergenenString(){
        String result = "";
        for(int i = 0; i < allergenen.size(); i++){
            if(i == allergenen.size() - 1)
                result += (allergenen.get(i).getNaam());
            
            else
            result += (allergenen.get(i).getNaam() + ", ");
            
        }
        return result;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setAllergenen(List<Allergeen> allergenen) {
        this.allergenen = allergenen;
    }
    public GerechtSoort getGerechtSoort(){
        return soort;
    }
    
    @Override
    public String toString(){
        /*StringBuilder builder = new StringBuilder();
        builder.append(naam);
        for(Allergeen a : allergenen){
            builder.append(a);
        }
        return builder.toString();*/
        return getNaam();
    }

    public List<String> getAllergenenList() {
        List<String> result = new ArrayList<>();
        for(Allergeen a : allergenen){
            result.add(a.getNaam());
        }
        return result;
    }

    public List<Allergeen> getAllergenen() {
        return allergenen;
    }
}
