package Factory;

import DAO.ArticuloDAO;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import modelo.Articulo;
import modelo.Pedido;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.ArrayList;

public class ArticuloDAOFactoryMySQL implements ArticuloDAO {


    private SessionFactory conn = null;

    public ArticuloDAOFactoryMySQL(SessionFactory conn){
        this.conn = conn;
    }


    @Override
    public String insertar(Articulo a) {
        // Creamos el enlace con la BBDD
        SessionFactory sessionFactory = (SessionFactory) new Configuration().configure("hibernate.cfg.xml")
                .addAnnotatedClass(Articulo.class)
                .buildSessionFactory();

        Session session = sessionFactory.openSession();
        try{
            session.beginTransaction();
            session.save(a);
            session.getTransaction().commit();
            session.close();
            sessionFactory.close();


        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void modificar(Articulo a) {


        SessionFactory sessionFactory = (SessionFactory) new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Articulo.class).buildSessionFactory();

        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.update(a);
            session.getTransaction().commit();
            session.flush();
            session.close();

            sessionFactory.close();
            // -------------------------------------------------------------

        }catch (Exception e){

        }

    }

    @Override
    public void eliminar(String cp) {

        SessionFactory sessionFactory = (SessionFactory) new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Articulo.class).buildSessionFactory();


        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            Articulo a = session.get(Articulo.class,cp);

            session.delete(a);
            session.getTransaction().commit();
            session.close();

            sessionFactory.close();
            // -------------------------------------------------------------

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public ArrayList<Articulo> obtenerTodos() {

        SessionFactory sessionFactory = (SessionFactory) new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Articulo.class).buildSessionFactory();


        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();

            String filtro = "from Articulo";
            Query<Articulo> query = session.createQuery(filtro, Articulo.class);
            ArrayList<Articulo> articulos = (ArrayList<Articulo>) query.list();
            session.close();

            sessionFactory.close();
            // -------------------------------------------------------------
            return articulos;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Articulo obtenerUno(String id) {
        SessionFactory sessionFactory = (SessionFactory) new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Articulo.class).buildSessionFactory();

        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            Articulo a = session.get(Articulo.class,id);

            session.close();

            sessionFactory.close();
            // -------------------------------------------------------------
            return a;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ArrayList<Articulo> obtenerPorCriterio(String columna, String criterio) {

        SessionFactory sessionFactory = (SessionFactory) new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Articulo.class).buildSessionFactory();

        Session session = sessionFactory.openSession();

        try {
            ArrayList<Articulo> devolucion = new ArrayList<>();
            session.beginTransaction();

            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Articulo> criteriaQuery = builder.createQuery(Articulo.class);
            Root<Articulo> root = criteriaQuery.from(Articulo.class);

            // Agregar la cláusula WHERE
            criteriaQuery.where(builder.equal(root.get(columna), criterio));

            Query<Articulo> query = session.createQuery(criteriaQuery);
            devolucion = (ArrayList<Articulo>) query.getResultList();

            session.getTransaction().commit();
            return devolucion;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}