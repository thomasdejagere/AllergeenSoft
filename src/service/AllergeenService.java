package service;

import DAO.AllergeenDAO;
import domain.Allergeen;
import java.sql.SQLException;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author Thomas
 */
public class AllergeenService {

    private AllergeenDAO allergeenDAO;

    public AllergeenService() {
        allergeenDAO = new AllergeenDAO();
    }

    public List<Allergeen> geefAllergenen() throws SQLException {
        List<Allergeen> result = null;
        try {
            allergeenDAO.startTransaction();
            result = allergeenDAO.findAll();
            allergeenDAO.stopTransaction();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Fout in de databank.\n"
                    + "Stuur volgende foutmelding door naar de ontwikkelaar:\n\n"
                    + "Fout in AllergeenService.geefAllergenen(): \n" + ex.getMessage());
        }

        return result;
    }

    public Allergeen geefAllergeen(String allergeen) {
        Allergeen a = null;
        try {
            allergeenDAO.startTransaction();
            a = allergeenDAO.findBy(allergeen);
            allergeenDAO.stopTransaction();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Fout in de databank.\n"
                    + "Stuur volgende foutmelding door naar de ontwikkelaar: \n\n"
                    + "Fout in AllergeenService.geefAllergeen(): \n" + ex.getMessage());
        }
        return a;
    }

}
