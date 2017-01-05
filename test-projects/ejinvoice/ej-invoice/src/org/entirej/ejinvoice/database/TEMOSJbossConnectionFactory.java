package org.entirej.ejinvoice.database;
import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.entirej.constants.EJ_PROPERTIES;
import org.entirej.framework.core.EJApplicationException;
import org.entirej.framework.core.EJForm;
import org.entirej.framework.core.EJFrameworkManager;
import org.entirej.framework.core.interfaces.EJConnectionFactory;
import org.entirej.framework.core.interfaces.EJFrameworkConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TEMOSJbossConnectionFactory implements EJConnectionFactory
{
    private static final long serialVersionUID = -7332618378973542881L;

    private static final Logger logger = LoggerFactory.getLogger(TEMOSJbossConnectionFactory.class);

    private volatile DataSource dataSource;
    private volatile DataSource authDataSource;

    
    public TEMOSJbossConnectionFactory()
    {

    }

    private synchronized void initialisDataSource(String dataSourceID) throws SQLException
    {
        logger.info("START initialiseDataSource");

        if (dataSource == null)
        {
            logger.info("Creating Application DataSource");
            try
            {
                assert dataSourceID != null;
                Context initContext = new InitialContext();
                
                dataSource = (DataSource)initContext.lookup("java:/ej-invoice");
                
                
                
//                Context envContext = (Context) initContext.lookup("java:/bizibo");
                // this can be used to switch between config eg:
                // Production,testing

//                dataSource = (DataSource)envContext.lookup(dataSourceID);
//                MyConnectionLabelingCallback callback = new MyConnectionLabelingCallback();
//                dataSource.registerConnectionLabelingCallback();
            }
            catch (NamingException e)
            {
                e.printStackTrace();
                throw new EJApplicationException(e);
            }
        }
        else
        {
            logger.info("Reusing Application DataSource");
        }

        logger.info("END initialiseDataSource");
    }

    public EJFrameworkConnection createConnection(EJForm form)
    {
        String dataSourceId = (String) form.getApplicationLevelParameter(EJ_PROPERTIES.P_DATA_SOURCE_NAME).getValue();

        return new TEMOSJbossFrameworkConnection(this, dataSourceId);
    }

    @Override
    public EJFrameworkConnection createConnection(EJFrameworkManager fwkManager)
    {
        String dataSourceId = (String) fwkManager.getApplicationLevelParameter(EJ_PROPERTIES.P_DATA_SOURCE_NAME).getValue();

        return new TEMOSJbossFrameworkConnection(this, dataSourceId);
    }


    public Connection createInternalConnection(String dataSourceId)
    {
        logger.info("START createConnection. Datasource {}", dataSourceId);

        // double start = System.currentTimeMillis();
        Connection connection = null;
        Connection base = null;
        try
        {
            if (dataSource == null)
            {
                initialisDataSource(dataSourceId);
            }
            // right now user/password inside WebContent/META-INF/context.xml
            // but can use overload .getConnection(user, password) or
            // datasource setPassword()
            logger.info("   -> get con from datasource");

             base = dataSource.getConnection();
             base.setAutoCommit(false);
            connection = (Connection) base.unwrap(Connection.class);

            logger.info("   -> GOT con from datasource");

            connection.setAutoCommit(false);

//            PreparedStatement stmt = connection.prepareStatement("ALTER SESSION SET NLS_SORT=BINARY");
//            stmt.execute();
//            stmt.close();
//            stmt = null;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            throw new EJApplicationException(e);
        }

        logger.info("FINISH createConnection.");

        return base;
    }
}
