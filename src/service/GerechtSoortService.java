package service;

import DAO.GerechtSoortDAO;
import ErrorHandling.ErrorHandling;
import domain.GerechtSoort;
import java.sql.SQLException;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author Thomas
 */
public class GerechtSoortService {

    private GerechtSoortDAO gerechtSoortDAO;

    public GerechtSoortService() {
        gerechtSoortDAO = new GerechtSoortDAO();
    }

    public List<GerechtSoort> geefGerechtSoorten() throws SQLException {
        List<GerechtSoort> result = null;
        try {
            gerechtSoortDAO.startTransaction();
            result = gerechtSoortDAO.findAll();
            gerechtSoortDAO.stopTransaction();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Fout in de databank.\n"
                    + "Stuur volgende foutmelding door naar de ontwikkelaar:\n\n"
                    + "Fout in GerechtSoortService.geefGerechtSoorten(): \n" + ex.getMessage());
            ErrorHandling.createStackTraceFile(ex.getStackTrace());
        } finally {
            try {
                if(gerechtSoortDAO.isConnectionOpen()){
                    gerechtSoortDAO.stopTransaction();
                }
            }catch(Exception ex){
                gerechtSoortDAO.setConToNull();
            }
        }

        return result;
    }

    public GerechtSoort geefGerechtSoort(String soort) {
        GerechtSoort result = null;
        try {
            gerechtSoortDAO.startTransaction();
            result = gerechtSoortDAO.findBy(soort);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Fout in de databank.\n"
                    + "Stuur volgende foutmelding door naar de ontwikkelaar:\n\n"
                    + "Fout in GerechtSoortService.geefGerechtSoort(): \n" + ex.getMessage());
            ErrorHandling.createStackTraceFile(ex.getStackTrace());
        } finally {
            try {
                if(gerechtSoortDAO.isConnectionOpen()){
                    gerechtSoortDAO.stopTransaction();
                }
            }catch(Exception ex){
                gerechtSoortDAO.setConToNull();
            }
        }
        return result;
    }
}
