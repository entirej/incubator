package org.entirej.ejinvoice.database;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.entirej.constants.EJ_PROPERTIES;
import org.entirej.framework.core.EJFrameworkManager;
import org.entirej.framework.core.interfaces.EJConnectionFactory;
import org.entirej.framework.core.interfaces.EJFrameworkConnection;

public class ConnectionFactory implements EJConnectionFactory
{
    private volatile DataSource dataSource;
    
    public ConnectionFactory()
    {

    }

    @Override
    public EJFrameworkConnection createConnection(EJFrameworkManager fwkManager)
    {
        return new FrameworkConnection(fwkManager, this);
    }

    Connection getConnection(EJFrameworkManager fwkManager)
    {
        Connection conn = null;
        // long startTime = System.currentTimeMillis();
        try
        {

            try
            {
                if (dataSource == null)
                {
                    // String dataSourceID = "jdbc/ejinvoice";
                    String dataSourceID = (String)fwkManager.getApplicationLevelParameter(EJ_PROPERTIES.P_DATA_SOURCE_NAME).getValue();
//                    String dataSourceID = "jdbc/ejinvoice-hosted";
                    assert dataSourceID != null;
                    Context initContext = new InitialContext();
                    Context envContext = (Context) initContext.lookup("java:/comp/env");
                    // this can be used to switch between config eg:
                    // Production,testing
                    dataSource = (DataSource) envContext.lookup(dataSourceID);
                }
                // right now user/password inside
                // WebContent/META-INF/context.xml
                // but can use overload .getConnection(user, password) or
                // datasource setPassword()
                conn = dataSource.getConnection();
                conn.setAutoCommit(false);
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
            catch (NamingException e)
            {
                e.printStackTrace();
            }

        }
        finally
        {
            // long endTime = System.currentTimeMillis();
            // System.err.println("EJFrameworkConnection.createConnection() : "
            // + (endTime - startTime) + " milliseconds");
        }
        return conn;
    }

}
