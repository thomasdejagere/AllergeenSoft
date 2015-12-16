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
import javax.swing.JOptionPane;

/**
 *
 * @author Thomas
 */
public class GerechtService {

    private GerechtDAO gerechtDAO;

    public GerechtService() {
        gerechtDAO = new GerechtDAO();
    }

    public List<Gerecht> geefGerechten() throws SQLException {
        List<Gerecht> result = null;
        try {
            gerechtDAO.startTransaction();
            result = gerechtDAO.findAll();
            gerechtDAO.stopTransaction();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Fout in de databank.\n"
                    + "Stuur volgende foutmelding door naar de ontwikkelaar:\n\n"
                    + "Fout in GerechtService.geefGerechten(): \n" + ex.getMessage());
        }
        return result;
    }

    public void update(Gerecht gerecht) throws SQLException {
        try {
            gerechtDAO.startTransaction();
            gerechtDAO.update(gerecht);
            gerechtDAO.saveChanges();
            gerechtDAO.stopTransaction();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Fout in de databank.\n"
                    + "Stuur volgende foutmelding door naar de ontwikkelaar:\n\n"
                    + "Fout in GerechtService.update(): \n" + ex.getMessage());
        }
    }

    public void remove(Gerecht gerecht) {
        try {
            gerechtDAO.startTransaction();
            gerechtDAO.delete(gerecht);
            gerechtDAO.saveChanges();
            gerechtDAO.stopTransaction();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Fout in de databank.\n"
                    + "Stuur volgende foutmelding door naar de ontwikkelaar:\n\n"
                    + "Fout in GerechtService.remove(): \n" + ex.getMessage());
        }

    }

    public void add(Gerecht gerecht) throws SQLException {
        try {
            gerechtDAO.startTransaction();
            gerechtDAO.addGerecht(gerecht);
            gerechtDAO.saveChanges();
            gerechtDAO.stopTransaction();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Fout in de databank.\n"
                    + "Stuur volgende foutmelding door naar de ontwikkelaar:\n\n"
                    + "Fout in GerechtService.add(): \n" + ex.getMessage());
        }
    }
}
