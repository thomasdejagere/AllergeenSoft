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
import javax.swing.JOptionPane;

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
        List<Allergeen> result = null;
        try{
            allergeenDAO.startTransaction();
        allergeenDAO.findAll();
        allergeenDAO.stopTransaction();
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, "Fout in de databank.\n"
                    + "Stuur volgende foutmelding door naar de ontwikkelaar:\n\n"
                    + "Fout in AllergeenService.geefAllergenen(): \n" + ex.getMessage());
        }
        
        return result;
    }
    public Allergeen geefAllergeen(String allergeen){
        Allergeen a = null;
        try{
            allergeenDAO.startTransaction();
            allergeenDAO.findBy(allergeen);
            allergeenDAO.stopTransaction();
        } catch(Exception ex){
            JOptionPane.showMessageDialog(null, "Fout in de databank.\n"
                    + "Stuur volgende foutmelding door naar de ontwikkelaar: \n\n"
                    + "Fout in AllergeenService.geefAllergeen(): \n" + ex.getMessage());
        }
        return a;
    }
            
}


