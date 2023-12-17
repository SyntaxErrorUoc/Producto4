package org.OnlineStore;
import ConexionMySQL.DatabaseConnectionException;
import vista.OnlineStore;

import java.sql.SQLException;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws DatabaseConnectionException, SQLException, ClassNotFoundException {

        OnlineStore.call();

    }
}
