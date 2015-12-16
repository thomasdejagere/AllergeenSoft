package DAO;

import domain.GerechtSoort;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import persistentie.JPAUtil;

/**
 *
 * @author Thomas
 */
public class GerechtSoortDAO {
    
    private static EntityManagerFactory emf;
    private EntityManager em;

    public GerechtSoortDAO() {
        emf = JPAUtil.getEntityManagerFactory();
    }
    
    public List<GerechtSoort> findAll() throws SQLException {
        List<GerechtSoort> gerechtSoorten = new ArrayList<>();
        Connection con = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=Esthio", "esthio", "d4fm7xc");
        Statement statement = con.createStatement();
        String s = "SELECT * FROM GERECHTSOORT";
        ResultSet rs = statement.executeQuery(s);
        while(rs.next()){
            GerechtSoort gerechtSoort = new GerechtSoort();
            gerechtSoort.setNaam(rs.getString("NAAM"));
            gerechtSoorten.add(gerechtSoort);
        }
        return gerechtSoorten;
    }
    public GerechtSoort findBy(String code) {
        return (GerechtSoort) em.createQuery("SELECT g FROM GerechtSoort g WHERE g.naam = :value1").setParameter("value1", code).getSingleResult();
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
