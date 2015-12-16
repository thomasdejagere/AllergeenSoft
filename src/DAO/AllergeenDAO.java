package DAO;

import domain.Allergeen;
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

public class AllergeenDAO {

    private static EntityManagerFactory emf;
    private EntityManager em;

    public AllergeenDAO() {
        emf = JPAUtil.getEntityManagerFactory();
    }



    public List<Allergeen> findAll() throws SQLException {
        List<Allergeen> allergenen = new ArrayList<>();
        Connection con = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=Esthio", "esthio", "d4fm7xc");
        Statement statement = con.createStatement();
        String s = "SELECT * FROM ALLERGEEN";
        ResultSet rs = statement.executeQuery(s);
        while(rs.next()){
            Allergeen allergeen = new Allergeen();
            allergeen.setNaam(rs.getString("NAAM"));
            allergenen.add(allergeen);
        }
        return allergenen;
    }

    public Allergeen findBy(String naam) {
        return (Allergeen) em.createQuery("SELECT a FROM Allergeen a WHERE a.naam = :value1").setParameter("value1", naam).getSingleResult();
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


