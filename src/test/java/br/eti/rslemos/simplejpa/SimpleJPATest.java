package br.eti.rslemos.simplejpa;

import org.hsqldb.jdbc.JDBCDriver;
import org.junit.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.sql.Connection;
import java.sql.Statement;
import java.util.Properties;

public class SimpleJPATest {

    private static EntityManagerFactory emf;
    private static EntityManager em;

    @BeforeClass
    public static void setupClass() {
        emf = Persistence.createEntityManagerFactory("persistence-unit");
        em = emf.createEntityManager();
    }

    @AfterClass
    public static void tearDownClass() {
        em.close();
        emf.close();
    }

    @Before
    public void setup() {
        em.getTransaction().begin();
    }

    @After
    public void tearDown() {
        em.getTransaction().commit();
    }

    @Test
    public void testBatchFetchTypeIn() throws Exception {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Detail> criteriaQuery = builder.createQuery(Detail.class);
        Root<Detail> root = criteriaQuery.from(Detail.class);

        TypedQuery<Detail> typedQuery = em.createQuery(criteriaQuery.where(builder.equal(root.get("extId"), "EXTERNAL")));

        Detail detail = typedQuery.getSingleResult();
        assertThat(detail.id, is(equalTo(2L)));

        Master master = detail.master;
        assertThat(master.id, is(equalTo(1L)));
    }

    @BeforeClass
    public static void setupDB() throws Exception {
        Properties properties = new Properties();
        properties.put("user", "none");
        properties.put("password", "none");
        Connection conn = JDBCDriver.getConnection("jdbc:hsqldb:mem:simplejpa", properties);
        Statement stmt = conn.createStatement();
        stmt.execute("CREATE TABLE MASTER (ID NUMERIC(19, 0))");
        stmt.execute("CREATE TABLE DETAIL (ID NUMERIC(19, 0), EXTERNAL_ID VARCHAR(11), ID_MASTER NUMERIC(19,0))");
        stmt.execute("INSERT INTO MASTER (ID) VALUES (1)");
        stmt.execute("INSERT INTO DETAIL (ID, EXTERNAL_ID, ID_MASTER) VALUES (2, 'EXTERNAL', 1)");
    }
}
