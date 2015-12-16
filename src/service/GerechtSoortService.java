/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import DAO.GerechtSoortDAO;
import domain.GerechtSoort;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Thomas
 */
public class GerechtSoortService {

    private GerechtSoortDAO gerechtSoortDAO;
    
    public GerechtSoortService(){
        gerechtSoortDAO = new GerechtSoortDAO();
    }
    public List<GerechtSoort> geefGerechtSoorten() throws SQLException{
        
        gerechtSoortDAO.startTransaction();
        List<GerechtSoort> result = gerechtSoortDAO.findAll();
        gerechtSoortDAO.stopTransaction();        
       return result;
    }

    public GerechtSoort geefGerechtSoort(String soort) {
        gerechtSoortDAO.startTransaction();
        GerechtSoort result = gerechtSoortDAO.findBy(soort);
        gerechtSoortDAO.stopTransaction();
        return result;
    }
}
