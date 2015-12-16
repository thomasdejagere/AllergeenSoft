/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import service.GerechtSoortService;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import service.AllergeenService;
import service.GerechtService;

/**
 *
 * @author Thomas
 */
public class Controller {
    private List<Gerecht> gerechten;
    private List<Allergeen> allergenen;
    private List<GerechtSoort> gerechtSoorten;
    private GerechtService gerechtService;
    private AllergeenService allergeenService;
    private GerechtSoortService gerechtSoortService;
    
    public Controller() throws SQLException{
        gerechten = new ArrayList<>();
        allergenen = new ArrayList<>();
        gerechtSoorten = new ArrayList<>();
        gerechtService = new GerechtService();
        allergeenService = new AllergeenService();
        gerechtSoortService = new GerechtSoortService();
        gerechten = gerechtService.geefGerechten();
        allergenen = allergeenService.geefAllergenen();
        gerechtSoorten = gerechtSoortService.geefGerechtSoorten();
    }
    public void addGerecht(String naam) throws SQLException{
        Gerecht gerecht = new Gerecht();
        gerecht.setNaam(naam);
        gerechten.add(gerecht);
        gerechtService.add(gerecht);
    }
    public void addGerecht(String naam, String soort) throws SQLException {
        Gerecht gerecht = new Gerecht();
        gerecht.setNaam(naam);
        if(geefSoort(soort) == null){
            gerecht.setSoort(null);
        }
        else
        gerecht.setSoort(geefSoort(soort));
        
        gerechten.add(gerecht);
        gerechtService.add(gerecht);
    }
    public void removeGerecht(String naam){
        gerechten.remove(geefGerecht(naam));
        gerechtService.remove(geefGerecht(naam));
    }
    public void addAllergeen(String gerecht, String allergeen){
        Allergeen a = allergeenService.geefAllergeen(allergeen);
        geefGerecht(gerecht).addAllergeen(a);
        //gerechtService.update(geefGerecht(gerecht));
    }
    public void removeAllergeen(String gerecht, String allergeen){
        Allergeen a = geefAllergeen(allergeen);
        geefGerecht(gerecht).removeAllergeen(a);
        //gerechtService.update(geefGerecht(gerecht));
    }
    private Allergeen geefAllergeen(String allergeen){
        return allergeenService.geefAllergeen(allergeen);
    }
    public Gerecht geefGerecht(String gerecht){
        for(Gerecht g : gerechten){
            if(g.getNaam().equals(gerecht)){
                return g;
            }
        }
        return null;
    }
    private GerechtSoort geefSoort(String soort) {
//        for(GerechtSoort gs : gerechtSoorten){
//            if(gs.getNaam().equals(soort))
//                return gs;
//        }
        return gerechtSoortService.geefGerechtSoort(soort);
    }
    public List<Gerecht> geefGerechten() {
        return gerechten;
    }
    public ObservableList<String> geefAllergenen(){
        List<String> resultString = new ArrayList<>();
        for(Allergeen a : allergenen){
            resultString.add(a.getNaam());
        }
        ObservableList<String> result = FXCollections.observableList(resultString);
        return result;
    }

    public List<String> zoekAllergenen(String text) {
        String lines[] = text.split("\\r?\\n");
        List<Gerecht> inputGerechten = new ArrayList<>();
        for(int i = 0; i < lines.length; i++){
            inputGerechten.add(geefGerecht(lines[i].toLowerCase()));
        }
        List<String> allergenenGerechten = new ArrayList<>();
        inputGerechten.stream().forEach((g) -> {
            if(g!=null)
            allergenenGerechten.add(g.getAllergenenString());
            else
                allergenenGerechten.add(" ");
        });
        return allergenenGerechten;
    }     

    public ObservableList<String> geefGerechtSoorten() throws SQLException {
        
        List<String> resultString = new ArrayList<>();
        gerechtSoorten.stream().forEach((gs) -> {
            resultString.add(gs.getNaam());
        });
        ObservableList<String> resultList = FXCollections.observableArrayList(resultString);
        return resultList;
    }

    public void voegGerechtToe(String gerechtNaam, String gerechtSoort, ObservableList allergenen) throws SQLException {
        Gerecht gerecht = new Gerecht();
        gerecht.setNaam(gerechtNaam);
        gerecht.setSoort(geefSoort(gerechtSoort));
        List<Allergeen> allergenenList = new ArrayList<>();
        Object[] allergenenObject = allergenen.toArray();
        for(int i = 0; i < allergenenObject.length; i++){
            allergenenList.add(geefAllergeen(allergenenObject[i].toString()));
        }
        gerecht.setAllergenen(allergenenList);
        gerechten.add(gerecht);
        gerechtService.add(gerecht);
    }

    public ObservableList<String> zetOmNaarObservableList(String text) {
        String lines[] = text.split("\\r?\\n");
        List<String> inputGerechten = new ArrayList<>();
        for(int i = 0; i < lines.length; i++){
            inputGerechten.add(lines[i]);
        }
        return FXCollections.observableList(inputGerechten);
    }

    public boolean checkGerecht(String t) {
        return geefGerecht(t.toLowerCase()) != null;
    }

    public void pasProductAan(String gerechtNaam, String gerechtSoort, ObservableList allergenen) throws SQLException {
        Gerecht gerecht = geefGerecht(gerechtNaam);
        gerecht.setNaam(gerechtNaam);
        gerecht.setSoort(geefSoort(gerechtSoort));
        List<Allergeen> allergenenList = new ArrayList<>();
        Object[] allergenenObject = allergenen.toArray();
        for(int i = 0; i < allergenenObject.length; i++){
            allergenenList.add(geefAllergeen(allergenenObject[i].toString()));
        }
        gerecht.setAllergenen(allergenenList);
        
        gerechtService.update(gerecht);
    }

}
