package ConexionMySQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class ConexionMySQL {

    public static SessionFactory conectarMySQL(Class<?> claseEntidad) {
        try {

            //   addAnnotatedClass(Articulo.class).buildSessionFactory();
            Configuration configuration = new Configuration().configure("hibernate.cfg.xml");

            // Agregamos la clase mapeada a la configuración
            configuration.addAnnotatedClass(claseEntidad);

            SessionFactory sessionFactory = configuration.buildSessionFactory();
            return sessionFactory;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
