package service;

import DAO.GerechtDAO;
import ErrorHandling.ErrorHandling;
import domain.Gerecht;
import java.sql.SQLException;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author Thomas Dejagere
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
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Fout in de databank.\n"
                    + "Stuur volgende foutmelding door naar de ontwikkelaar:\n\n"
                    + "Fout in GerechtService.geefGerechten(): \n" + ex.getMessage());
            ErrorHandling.createStackTraceFile(ex.getStackTrace());
        } finally {
            try {
                if(gerechtDAO.isConnectionOpen()){
                    gerechtDAO.stopTransaction();
                }
            }catch(Exception ex){
                gerechtDAO.setConToNull();
            }
        }
        return result;
    }

    public void update(Gerecht gerecht) throws SQLException {
        try {
            gerechtDAO.startTransaction();
            gerechtDAO.update(gerecht);
            gerechtDAO.saveChanges();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Fout in de databank.\n"
                    + "Stuur volgende foutmelding door naar de ontwikkelaar:\n\n"
                    + "Fout in GerechtService.update(): \n" + ex.getMessage());
            ErrorHandling.createStackTraceFile(ex.getStackTrace());
        }finally {
            try {
                if(gerechtDAO.isConnectionOpen()){
                    gerechtDAO.stopTransaction();
                }
            }catch(Exception ex){
                gerechtDAO.setConToNull();
            }
        }
    }

    public void remove(Gerecht gerecht) {
        try {
            gerechtDAO.startTransaction();
            gerechtDAO.delete(gerecht);
            gerechtDAO.saveChanges();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Fout in de databank.\n"
                    + "Stuur volgende foutmelding door naar de ontwikkelaar:\n\n"
                    + "Fout in GerechtService.remove(): \n" + ex.getMessage());
            ErrorHandling.createStackTraceFile(ex.getStackTrace());
        } finally {
            try {
                if(gerechtDAO.isConnectionOpen()){
                    gerechtDAO.stopTransaction();
                }
            }catch(Exception ex){
                gerechtDAO.setConToNull();
            }
        }

    }

    public void add(Gerecht gerecht) throws SQLException {
        try {
            gerechtDAO.startTransaction();
            gerechtDAO.addGerecht(gerecht);
            gerechtDAO.saveChanges();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Fout in de databank.\n"
                    + "Stuur volgende foutmelding door naar de ontwikkelaar:\n\n"
                    + "Fout in GerechtService.add(): \n" + ex.getMessage());
            ErrorHandling.createStackTraceFile(ex.getStackTrace());
        } finally {
            try {
                if(gerechtDAO.isConnectionOpen()){
                    gerechtDAO.stopTransaction();
                }
            }catch(Exception ex){
                gerechtDAO.setConToNull();
            }
        }
    }
}
