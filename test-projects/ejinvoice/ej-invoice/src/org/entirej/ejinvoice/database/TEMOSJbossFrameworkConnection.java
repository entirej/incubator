package org.entirej.ejinvoice.database;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicBoolean;

import org.entirej.framework.core.EJApplicationException;
import org.entirej.framework.core.interfaces.EJFrameworkConnection;

public class TEMOSJbossFrameworkConnection implements EJFrameworkConnection
{
    private Connection                  _connection;

    private TEMOSJbossConnectionFactory _factory;
    private String                      _dataSourceId;

    private AtomicBoolean               init = new AtomicBoolean(false);

    public TEMOSJbossFrameworkConnection(TEMOSJbossConnectionFactory factory, String dataSourceId)
    {
        _factory = factory;
        _dataSourceId = dataSourceId;
    }

    Connection getInternalConnection()
    {
        if (!init.get())
        {
            _connection = _factory.createInternalConnection(_dataSourceId);
            init.set(true);
        }
        return _connection;
    }

    @Override
    public void close()
    {
        try
        {
            if (init.get())
            {
                _connection.close();
            }
        }
        catch (SQLException e)
        {
            throw new EJApplicationException(e);
        }
    }

    @Override
    public void commit()
    {
        try
        {
            if (init.get())
            {
                _connection.commit();
            }
        }
        catch (SQLException e)
        {
            throw new EJApplicationException(e);
        }
    }

    @Override
    public Object getConnectionObject()
    {
        try
        {
            return getInternalConnection().unwrap(Connection.class);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void rollback()
    {
        try
        {
            if (init.get())
            {
                _connection.rollback();
            }
        }
        catch (SQLException e)
        {
            throw new EJApplicationException(e);
        }
    }

}
