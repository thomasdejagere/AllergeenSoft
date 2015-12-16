package DAO;

import domain.Allergeen;
import domain.Gerecht;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import persistentie.JPAUtil;

/**
 *
 * @author Thomas
 */
public class GerechtDAO {
    private static EntityManagerFactory emf;
    private EntityManager em;

    public GerechtDAO() {
        emf = JPAUtil.getEntityManagerFactory();
    }

    public void addGerecht(Gerecht gerecht) throws SQLException {
        Connection con = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=Esthio", "esthio", "d4fm7xc");
        Statement statement = con.createStatement();
        int vorigeID = (int) em.createQuery("SELECT MAX(g.id) FROM Gerecht g").getSingleResult();
        vorigeID++;
        gerecht.setId(vorigeID);
        String voegGerechtToe = "INSERT INTO GERECHT(ID,NAAM,SOORT_ID) VALUES (?,?,?)";
        String voegAllergeenToe = "INSERT INTO GERECHT_ALLERGEEN(Gerecht_ID,allergenen_ID) VALUES (?,?)";
        PreparedStatement voegGerechtToeStat = con.prepareStatement(voegGerechtToe);
        voegGerechtToeStat.setInt(1, vorigeID);
        voegGerechtToeStat.setString(2, gerecht.getNaam());
        voegGerechtToeStat.setInt(3, gerecht.getSoort().getId());
        voegGerechtToeStat.execute();
        List<Allergeen> allergenen = gerecht.getAllergenen();
        for(Allergeen a : allergenen){
            PreparedStatement voegAllergeenToeStat = con.prepareStatement(voegAllergeenToe);
            voegAllergeenToeStat.setInt(1, gerecht.getId());
            voegAllergeenToeStat.setInt(2, a.getId());
            voegAllergeenToeStat.execute();
        }
    }

    public List<Gerecht> findAll() throws SQLException {
        return em.createNamedQuery("Gerecht.findAll").getResultList();
    }

    public Gerecht findBy(int code) {
        return em.find(Gerecht.class, code);
    }

    public void update(Gerecht gerecht) throws SQLException {
        //nog veranderen in update
        Connection con = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=Esthio", "esthio", "d4fm7xc");
        Statement statement = con.createStatement();
        String  verwijderAllergenen= "DELETE FROM GERECHT_ALLERGEEN WHERE Gerecht_ID = ?";
        PreparedStatement verwijderAllergenenStat = con.prepareStatement(verwijderAllergenen);
        verwijderAllergenenStat.setInt(1, gerecht.getId());
        verwijderAllergenenStat.execute();
        String pasGerechtAanString = "UPDATE GERECHT SET NAAM = ?, SOORT_ID = ? WHERE ID = ?";
        PreparedStatement pasGerechtAanStat = con.prepareStatement(pasGerechtAanString);
        pasGerechtAanStat.setString(1, gerecht.getNaam());
        pasGerechtAanStat.setInt(2, gerecht.getSoort().getId());
        pasGerechtAanStat.setInt(3, gerecht.getId());
        pasGerechtAanStat.execute();
        String voegAllergeenToe = "INSERT INTO GERECHT_ALLERGEEN(Gerecht_ID,allergenen_ID) VALUES (?,?)";
        List<Allergeen> allergenen = gerecht.getAllergenen();
        for(Allergeen a : allergenen){
            PreparedStatement voegAllergeenToeStat = con.prepareStatement(voegAllergeenToe);
            voegAllergeenToeStat.setInt(1, gerecht.getId());
            voegAllergeenToeStat.setInt(2, a.getId());
            voegAllergeenToeStat.execute();
        }
    }

    public void delete(Gerecht gerecht) {
        em.remove(gerecht);
    }

    public void startTransaction() {
        if (em==null||!em.isOpen()) {
            em = emf.createEntityManager();
        }
        em.getTransaction().begin();
    }

    public void saveChanges() {
        em.getTransaction().commit();
        em.close();
    }

    public void stopTransaction() {
        em.close();
    }
    
}
