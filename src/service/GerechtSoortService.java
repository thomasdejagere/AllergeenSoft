package service;

import DAO.GerechtSoortDAO;
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
        }

        return result;
    }

    public GerechtSoort geefGerechtSoort(String soort) {
        GerechtSoort result = null;
        try {
            gerechtSoortDAO.startTransaction();
            result = gerechtSoortDAO.findBy(soort);
            gerechtSoortDAO.stopTransaction();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Fout in de databank.\n"
                    + "Stuur volgende foutmelding door naar de ontwikkelaar:\n\n"
                    + "Fout in GerechtSoortService.geefGerechtSoort(): \n" + ex.getMessage());
        }
        return result;
    }
}
