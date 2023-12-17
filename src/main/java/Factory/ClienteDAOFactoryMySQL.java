package Factory;

import DAO.ClienteDAO;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import modelo.Cliente;
import modelo.ClienteEstandar;
import modelo.ClientePremium;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.ArrayList;

public class ClienteDAOFactoryMySQL implements ClienteDAO {


    private SessionFactory conn;

    public ClienteDAOFactoryMySQL(SessionFactory Conn){
        this.conn = Conn;
    }


    @Override
    public String insertar(Cliente C) {

        // Creamos el enlace con la BBDD
        SessionFactory sessionFactory = new Configuration().configure("hibernate.cfg.xml")
                .addAnnotatedClass(Cliente.class)
                .addAnnotatedClass(ClienteEstandar.class)
                .addAnnotatedClass(ClientePremium.class)
                .buildSessionFactory();

        Session session = sessionFactory.openSession();
        try{
            session.beginTransaction();
            session.save(C);

            session.getTransaction().commit();
            session.close();
            sessionFactory.close();


        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void modificar(Cliente a) {
        SessionFactory sessionFactory = new Configuration().configure("hibernate.cfg.xml")
                .addAnnotatedClass(Cliente.class)
                .addAnnotatedClass(ClienteEstandar.class)
                .addAnnotatedClass(ClientePremium.class)
                .buildSessionFactory();

        Session session = sessionFactory.openSession();

        try {
            session.beginTransaction();
            a.mapVipToString(a.getVip());
            session.update(a);
            session.getTransaction().commit();
            session.beginTransaction();
            // Actualizar solo el campo discriminador (TipoVip)
            String updateQuery = "UPDATE cliente SET TipoVip = :nuevoTipoVip WHERE mail = :correoElectronico";
            session.createNativeQuery(updateQuery)
                    .setParameter("nuevoTipoVip", a.getTipoVip())
                    .setParameter("correoElectronico", a.getCorreoElectronico())
                    .executeUpdate();

            session.flush();
            session.getTransaction().commit();
        } catch (Exception e) {
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
            sessionFactory.close();
        }
    }

    @Override
    public void eliminar(String a) {
        SessionFactory sessionFactory = (SessionFactory) new Configuration().configure("hibernate.cfg.xml")
                .addAnnotatedClass(Cliente.class)
                .addAnnotatedClass(ClienteEstandar.class)
                .addAnnotatedClass(ClientePremium.class)
                .buildSessionFactory();
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            Cliente C = session.get(Cliente.class,a);
            session.delete(C);
            session.getTransaction().commit();
            session.close();

            sessionFactory.close();
            // -------------------------------------------------------------
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<Cliente> obtenerTodos() {
        SessionFactory sessionFactory = (SessionFactory) new Configuration().configure("hibernate.cfg.xml")
                .addAnnotatedClass(Cliente.class)
                .addAnnotatedClass(ClienteEstandar.class)
                .addAnnotatedClass(ClientePremium.class)
                .buildSessionFactory();

        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();

            String filtro = "from Cliente";
            Query<Cliente> query = session.createQuery(filtro, Cliente.class);
            ArrayList<Cliente> Clientes = (ArrayList<Cliente>) query.list();
            session.close();

            sessionFactory.close();
            // -------------------------------------------------------------
            return Clientes;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Cliente obtenerUno(String id) {
        SessionFactory sessionFactory = (SessionFactory) new Configuration().configure("hibernate.cfg.xml")
                .addAnnotatedClass(Cliente.class)
                .addAnnotatedClass(ClienteEstandar.class)
                .addAnnotatedClass(ClientePremium.class)
                .buildSessionFactory();

        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            Cliente c = session.get(Cliente.class,id);

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
    public ArrayList<Cliente> obtenerPorCriterio(String columna, String criterio) {

        SessionFactory sessionFactory = (SessionFactory) new Configuration().configure("hibernate.cfg.xml")
                .addAnnotatedClass(Cliente.class)
                .addAnnotatedClass(ClienteEstandar.class)
                .addAnnotatedClass(ClientePremium.class)
                .buildSessionFactory();

        Session session = sessionFactory.openSession();

        try {
            ArrayList<Cliente> devolucion = new ArrayList<>();
            session.beginTransaction();

            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Cliente> criteriaQuery = builder.createQuery(Cliente.class);
            Root<Cliente> root = criteriaQuery.from(Cliente.class);

            // Agregar la cláusula WHERE
            criteriaQuery.where(builder.equal(root.get(columna), criterio));

            Query<Cliente> query = session.createQuery(criteriaQuery);
            devolucion = (ArrayList<Cliente>) query.getResultList();

            session.getTransaction().commit();
            return devolucion;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
