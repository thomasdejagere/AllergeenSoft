/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import DAO.GerechtDAO;
import domain.Gerecht;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Thomas
 */
public class GerechtService {
    private GerechtDAO gerechtDAO;
    
    public GerechtService(){
        gerechtDAO = new GerechtDAO();
    }
    public List<Gerecht> geefGerechten() throws SQLException{
        gerechtDAO.startTransaction();
        List<Gerecht> result = gerechtDAO.findAll();
        gerechtDAO.stopTransaction();
        return result;
    }
    
    public void update(Gerecht gerecht) throws SQLException {
        gerechtDAO.startTransaction();
        gerechtDAO.update(gerecht);
        gerechtDAO.saveChanges();
        //gerechtDAO.stopTransaction();
    }
    public void remove(Gerecht gerecht){
        gerechtDAO.startTransaction();
        gerechtDAO.delete(gerecht);
        
        gerechtDAO.saveChanges();
        gerechtDAO.stopTransaction();
    }
    public void add(Gerecht gerecht) throws SQLException{
        gerechtDAO.startTransaction();
        gerechtDAO.addGerecht(gerecht);
        gerechtDAO.saveChanges();
        //gerechtDAO.stopTransaction();
    }
}
