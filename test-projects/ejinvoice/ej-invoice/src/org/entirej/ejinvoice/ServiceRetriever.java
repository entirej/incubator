/*******************************************************************************
 * Copyright 2013 Mojave Innovations GmbH
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * Contributors:
 *     Mojave Innovations GmbH - initial API and implementation
 ******************************************************************************/
/**
 * 
 */
package org.entirej.ejinvoice;

import org.entirej.ejinvoice.forms.login.UserService;
import org.entirej.framework.core.EJForm;
import org.entirej.framework.core.EJFrameworkHelper;
import org.entirej.framework.core.interfaces.EJFrameworkConnection;

/**
 * This class is used as service call for specific business services by passing
 * a specific <code>EJForm</code> or <code>EJFrameworkHelper</code>
 */
public class ServiceRetriever
{

    private static DBService   _dbService;
    private static UserService _userService;

    public static synchronized UserService getUserService(final EJFrameworkHelper fwkHelper)
    {
        if (_userService != null)
        {
            return _userService;
        }

        _userService = new UserService();

        return _userService;
    }

    /**
     * @param form
     *            - The <code>EJForm</code> which requires the service
     * @return A <code>DBService</code> for the given EJForm
     */
    public static synchronized DBService getDBService(final EJForm form)
    {
        if (_dbService != null)
        {
            return _dbService;
        }

        _dbService = new DBService(new EJContextProvider()
        {
            @Override
            public EJFrameworkConnection getConnection()
            {
                return form.getConnection();
            }
        });

        return _dbService;
    }

    /**
     * @param appManager
     *            - The <code>EJFrameworkHelper</code> which requires the
     *            service
     * @return A <code>DBService</code> for the given EJFrameworkHelper
     */
    public static synchronized DBService getDBService(final EJFrameworkHelper appManager)
    {
        if (_dbService != null)
        {
            return _dbService;
        }

        _dbService = new DBService(new EJContextProvider()
        {
            @Override
            public EJFrameworkConnection getConnection()
            {
                return appManager.getConnection();
            }
        });

        return _dbService;
    }
}
