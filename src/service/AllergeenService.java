/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import DAO.AllergeenDAO;
import DAO.GerechtDAO;
import domain.Allergeen;
import domain.Gerecht;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Thomas
 */
public class AllergeenService {

    private AllergeenDAO allergeenDAO;
    
    public AllergeenService(){
        allergeenDAO = new AllergeenDAO();
    }
    public List<Allergeen> geefAllergenen() throws SQLException{
        allergeenDAO.startTransaction();
        List<Allergeen> result = allergeenDAO.findAll();
        allergeenDAO.stopTransaction();
        return result;
    }
    public Allergeen geefAllergeen(String allergeen){
        allergeenDAO.startTransaction();
        Allergeen a = allergeenDAO.findBy(allergeen);
        allergeenDAO.stopTransaction();
        return a;
    }
            
}


