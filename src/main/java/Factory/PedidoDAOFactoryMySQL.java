package Factory;

import DAO.PedidoDAO;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import modelo.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.ArrayList;

public class PedidoDAOFactoryMySQL implements PedidoDAO {


    private SessionFactory conn;

    public PedidoDAOFactoryMySQL(SessionFactory Conn){
        this.conn = Conn;
    }

    @Override
    public String insertar(Pedido p) {


        SessionFactory sessionFactory = new Configuration().configure("hibernate.cfg.xml")
                .addAnnotatedClass(Pedido.class)
                .buildSessionFactory();

        Session session = sessionFactory.openSession();
        try{
            session.beginTransaction();

            session.save(p);
            session.getTransaction().commit();
            session.close();
            sessionFactory.close();


        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void modificar(Pedido p) {
        SessionFactory sessionFactory = (SessionFactory) new Configuration().configure("hibernate.cfg.xml")
                .addAnnotatedClass(Pedido.class)
                .addAnnotatedClass(Articulo.class)
                .buildSessionFactory();

        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.update(p);
            session.getTransaction().commit();
            session.close();
            sessionFactory.close();
            // -------------------------------------------------------------

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void eliminar(Integer a) {
        SessionFactory sessionFactory = (SessionFactory) new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Pedido.class)
                .addAnnotatedClass(ClientePremium.class)
                .addAnnotatedClass(Articulo.class)
                .addAnnotatedClass(ClienteEstandar.class)
                .buildSessionFactory();


        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            Pedido p = session.get(Pedido.class,a);
            session.delete(p);
            session.getTransaction().commit();
            session.close();

            sessionFactory.close();
            // -------------------------------------------------------------

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<Pedido> obtenerTodos() {

        SessionFactory sessionFactory = (SessionFactory) new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Pedido.class)
                .addAnnotatedClass(ClientePremium.class)
                .addAnnotatedClass(Articulo.class)
                .addAnnotatedClass(ClienteEstandar.class)
                .buildSessionFactory();


        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();

            String filtro = "from Pedido";
            Query<Pedido> query = session.createQuery(filtro, Pedido.class);
            ArrayList<Pedido> pedidios = (ArrayList<Pedido>) query.list();
            session.close();

            sessionFactory.close();
            // -------------------------------------------------------------
            return pedidios;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Pedido obtenerUno(Integer id) {
        SessionFactory sessionFactory = (SessionFactory) new Configuration().configure("hibernate.cfg.xml")
                .addAnnotatedClass(Pedido.class)
                .addAnnotatedClass(Articulo.class)
                .addAnnotatedClass(ClientePremium.class)
                .addAnnotatedClass(ClienteEstandar.class)
                .buildSessionFactory();

        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            Pedido c = session.get(Pedido.class,id);

            session.close();

            sessionFactory.close();
            // -------------------------------------------------------------
            return c;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;

    }
    @Override
    public ArrayList<Pedido> obtenerPorCriterio(Integer columna, Integer criterio) {
        SessionFactory sessionFactory = (SessionFactory) new Configuration().configure("hibernate.cfg.xml")
                .addAnnotatedClass(Pedido.class)
                .addAnnotatedClass(ClientePremium.class)
                .addAnnotatedClass(Articulo.class)
                .addAnnotatedClass(ClienteEstandar.class)
                .buildSessionFactory();

        Session session = sessionFactory.openSession();

        try {
            ArrayList<Pedido> devolucion = new ArrayList<>();
            session.beginTransaction();

            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Pedido> criteriaQuery = builder.createQuery(Pedido.class);
            Root<Pedido> root = criteriaQuery.from(Pedido.class);


            criteriaQuery.where(builder.equal(root.get(String.valueOf(columna)), criterio));

            Query<Pedido> query = session.createQuery(criteriaQuery);
            devolucion = (ArrayList<Pedido>) query.getResultList();

            session.getTransaction().commit();
            return devolucion;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public ArrayList<Pedido> obtenerPorCriterio(Integer columna, String criterio) {
        return null;
    }

    @Override
    public ArrayList<Pedido> obtenerPorCriterio(String columna, String criterio) {

        SessionFactory sessionFactory = (SessionFactory) new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Pedido.class)
                .addAnnotatedClass(ClientePremium.class)
                .addAnnotatedClass(Articulo.class)
                .addAnnotatedClass(ClienteEstandar.class)
                .buildSessionFactory();

        Session session = sessionFactory.openSession();

        try {
            ArrayList<Pedido> devolucion = new ArrayList<>();
            session.beginTransaction();

            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Pedido> criteriaQuery = builder.createQuery(Pedido.class);
            Root<Pedido> root = criteriaQuery.from(Pedido.class);

            // Agregar la cláusula WHERE
            criteriaQuery.where(builder.equal(root.get(String.valueOf(columna)), criterio));

            Query<Pedido> query = session.createQuery(criteriaQuery);
            devolucion = (ArrayList<Pedido>) query.getResultList();

            session.getTransaction().commit();
            return devolucion;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}