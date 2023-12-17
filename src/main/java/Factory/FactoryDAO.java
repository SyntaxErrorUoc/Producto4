package Factory;
import ConexionMySQL.DAOExceptions;
import ConexionMySQL.ConexionMySQL;
import ConexionMySQL.DatabaseConnectionException;
import DAO.*;
import com.mysql.cj.Session;
import modelo.Articulo;
import modelo.Cliente;
import modelo.ClientePremium;
import modelo.Pedido;
import org.hibernate.SessionFactory;
import org.hibernate.boot.jaxb.SourceType;

import java.sql.Connection;
import java.sql.SQLException;


public class FactoryDAO {

    Connection conn = null;

    public ArticuloDAO articulo;
    public PedidoDAO pedido;
    public ClienteDAO cliente;

    public FactoryDAO() throws DatabaseConnectionException {

        try{
            SessionFactory articulo = MySQLArticulo();
            this.articulo = new ArticuloDAOFactoryMySQL(articulo);

            SessionFactory cliente = MySQLCliente();
            this.cliente = new ClienteDAOFactoryMySQL(cliente);

            SessionFactory pedido = MySQLPedido();
            this.pedido = new PedidoDAOFactoryMySQL(pedido);

        }catch(Exception e){
            System.out.println(e);
        }
        if (this.articulo != null|| this.cliente != null || this.pedido != null) {
            System.out.println("Es una base de datos Mysql");

        }
        else {
            Connection conn = null;

            throw new DatabaseConnectionException(("No es una base de datos MySQL"));

        }
    }

    public SessionFactory MySQLArticulo()  {
        SessionFactory ConexionArticulo = null;
        ConexionArticulo = ConexionMySQL.conectarMySQL(Articulo.class);

        return ConexionArticulo;
    }

    public SessionFactory MySQLCliente()  {
        SessionFactory ConexionCliente = null;
        ConexionCliente = ConexionMySQL.conectarMySQL(Cliente.class);

        return ConexionCliente;
    }
    public SessionFactory MySQLPedido()  {
        SessionFactory ConexionPedido = null;
        ConexionPedido = ConexionMySQL.conectarMySQL(Pedido.class);

        return ConexionPedido;
    }

    public Connection NoMysql(){
        return null;
    }

}