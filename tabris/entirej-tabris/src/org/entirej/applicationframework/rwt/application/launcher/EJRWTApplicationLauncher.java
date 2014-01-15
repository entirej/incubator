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
package org.entirej.applicationframework.rwt.application.launcher;

import static org.eclipse.rap.rwt.internal.service.ContextProvider.getContext;

import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.rap.rwt.application.Application;
import org.eclipse.rap.rwt.application.Application.OperationMode;
import org.eclipse.rap.rwt.application.ApplicationConfiguration;
import org.eclipse.rap.rwt.application.EntryPoint;
import org.eclipse.rap.rwt.application.EntryPointFactory;
import org.eclipse.rap.rwt.client.WebClient;
import org.eclipse.rap.rwt.client.service.JavaScriptExecutor;
import org.eclipse.rap.rwt.internal.lifecycle.RWTLifeCycle;
import org.eclipse.rap.rwt.service.ResourceLoader;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.entirej.applicationframework.rwt.application.EJRWTApplicationContainer;
import org.entirej.applicationframework.rwt.application.EJRWTApplicationManager;
import org.entirej.framework.core.EJFrameworkHelper;
import org.entirej.framework.core.EJFrameworkInitialiser;
import org.entirej.framework.core.interfaces.EJMessenger;
import org.entirej.framework.core.properties.EJCoreLayoutContainer;
import org.entirej.framework.core.properties.EJCoreProperties;

public abstract class EJRWTApplicationLauncher implements ApplicationConfiguration
{

    protected static final String THEME_DEFAULT = "org.entirej.applicationframework.rwt.Default";

    public void configure(Application configuration)
    {
        createEntryPoint(configuration);
    }

    protected String getFavicon()
    {
        return "icons/favicon.ico";
    }

    protected String getLoadingImage()
    {
        return "icons/ej-default_loading.gif";
    }

    protected String getLoadingMessage()
    {
        return "EJ Loading...";
    }

    protected String getBaseThemeCSSLocation()
    {
        return "theme/default.css";
    }

    protected String getThemeCSSLocation()
    {
        return null;
    }

    // disable due to RWT bug
    // https://bugs.eclipse.org/bugs/show_bug.cgi?id=410895
    // protected String getDefaultTabCloseMessage()
    // {
    // return "__DEFAULT__";
    // }

    protected String getWebPathContext()
    {
        return "ej";
    }

    protected String getBodyHtml()
    {
        StringBuilder b = new StringBuilder();
        b.append("<div id=\"splash\" style=\"width:100%;  position: absolute;  top: 50%;   text-align: center;\">");
        b.append("<img src=\"./rwt-resources/");
        b.append(getLoadingImage());
        b.append("\"  style=\"margin: 10px 15px 0\" />");
        b.append("<div style=\"margin: 5px 15px 10px;  font: 12px Verdana, 'Lucida Sans', sans-serif\">");
        b.append(getLoadingMessage());
        b.append("</div></div>");

        return b.toString();
    }

    

    

    public void createEntryPoint(final Application configuration)
    {

        configuration.setOperationMode(OperationMode.SWT_COMPATIBILITY);
        Map<String, String> properties = new HashMap<String, String>();
        if (this.getClass().getClassLoader().getResource("application.ejprop") != null)
        {
            EJFrameworkInitialiser.initialiseFramework("application.ejprop");
        }
        else if (this.getClass().getClassLoader().getResource("EntireJApplication.properties") != null)
        {

            EJFrameworkInitialiser.initialiseFramework("EntireJApplication.properties");
        }
        else
        {
            throw new RuntimeException("application.ejprop not found");
        }
        EJCoreLayoutContainer layoutContainer = EJCoreProperties.getInstance().getLayoutContainer();
        properties.put(WebClient.PAGE_TITLE, layoutContainer.getTitle());
        properties.put(WebClient.FAVICON, getFavicon());
        properties.put(WebClient.BODY_HTML, getBodyHtml());
        properties.put(WebClient.THEME_ID, THEME_DEFAULT);
        configuration.addResource(getFavicon(), new FileResource());
        configuration.addResource(getLoadingImage(), new FileResource());
        configuration.addStyleSheet(THEME_DEFAULT, "resource/theme/default.css");
        configuration.addStyleSheet(THEME_DEFAULT, getBaseThemeCSSLocation());
        configuration.addResource(getBaseThemeCSSLocation(), new FileResource());
        if (getThemeCSSLocation() != null)
        {

            configuration.addStyleSheet(THEME_DEFAULT, getThemeCSSLocation());
            configuration.addResource(getThemeCSSLocation(), new FileResource());
        }

        configuration.addEntryPoint(String.format("/%s", getWebPathContext()), new EntryPointFactory()
        {

            public EntryPoint create()
            {
                try
                {
                    registerServiceHandlers();
                }
                catch (java.lang.IllegalArgumentException e)
                {
                    // ignore if already registered
                }
                registerWidgetHandlers();
                return new EntryPoint()
                {

                    public int createUI()
                    {

                        EJRWTContext.initContext();
                        
                        EJRWTApplicationManager applicationManager = null;

                        if (this.getClass().getClassLoader().getResource("application.ejprop") != null)
                        {
                            applicationManager = (EJRWTApplicationManager) EJFrameworkInitialiser.initialiseFramework("application.ejprop");
                        }
                        else if (this.getClass().getClassLoader().getResource("EntireJApplication.properties") != null)
                        {

                            applicationManager = (EJRWTApplicationManager) EJFrameworkInitialiser.initialiseFramework("EntireJApplication.properties");
                        }
                        else
                        {
                            throw new RuntimeException("application.ejprop not found");
                        }

                        getContext().getUISession().setAttribute("ej.applicationManager", applicationManager);

                        EJCoreLayoutContainer layoutContainer = EJCoreProperties.getInstance().getLayoutContainer();
                        // Now build the application container
                        EJRWTApplicationContainer appContainer = new EJRWTApplicationContainer(layoutContainer);

                        // Add the application menu and status bar to the app
                        // container
                        EJMessenger messenger = applicationManager.getApplicationMessenger();
                        if (messenger == null)
                        {
                            throw new NullPointerException("The ApplicationComponentProvider must provide an Messenger via method: getApplicationMessenger()");
                        }
                        Display display = Display.getDefault();
                        if (display.isDisposed())
                            display = new Display();
                        Shell shell = new Shell(display, SWT.NO_TRIM);
                        preApplicationBuild(applicationManager);
                        applicationManager.buildApplication(appContainer, shell);
                        postApplicationBuild(applicationManager);
                        shell.layout();
                        shell.setMaximized(true);
                        // disable due to RWT bug
                        // https://bugs.eclipse.org/bugs/show_bug.cgi?id=410895
                        // ExitConfirmation confirmation =
                        // RWT.getClient().getService(ExitConfirmation.class);
                        // String message = getDefaultTabCloseMessage();
                        // if ("__DEFAULT__".equals(message))
                        // {
                        // confirmation.setMessage(String.format("Do you want to close %s ?",
                        // EJCoreProperties.getInstance().getLayoutContainer().getTitle()));
                        // }
                        // else if (message != null)
                        // {
                        // confirmation.setMessage(message);
                        // }

                        return openShell(display, shell);
                    }
                };
            }
        }, properties);
    }

    @SuppressWarnings("deprecation")
    public static int openShell(Display display, Shell shell)
    {
        shell.open();
        if (RWT.getLifeCycle() instanceof RWTLifeCycle)
        {
            while (!shell.isDisposed())
            {
                if (!display.readAndDispatch())
                {
                    display.sleep();
                }
            }
        }
        return 0;
    }

    public void preApplicationBuild(EJFrameworkHelper frameworkHelper)
    {
    };

    public void postApplicationBuild(EJFrameworkHelper frameworkHelper)
    {
    };

    public void registerServiceHandlers()
    {

    }

    public void registerWidgetHandlers()
    {
       
    }

    public static class FileResource implements ResourceLoader
    {

        public InputStream getResourceAsStream(String arg0) throws IOException
        {
            return getLoader().getResourceAsStream(arg0);
        }

        public ClassLoader getLoader()
        {
            return EJRWTApplicationLauncher.class.getClassLoader();
        }

    }

    public static void reloadApplication(EJFrameworkHelper frameworkHelper)
    {
        // disable due to RWT bug
        // https://bugs.eclipse.org/bugs/show_bug.cgi?id=410895
        // ExitConfirmation confirmation =
        // RWT.getClient().getService(ExitConfirmation.class);
        // confirmation.setMessage(null);
        StringBuffer url = new StringBuffer();
        url.append(RWT.getRequest().getContextPath());
        url.append(RWT.getRequest().getServletPath());
        String encodeURL = RWT.getResponse().encodeURL(url.toString());
        if (encodeURL.contains("jsessionid"))
        {
            encodeURL = encodeURL.substring(0, encodeURL.indexOf("jsessionid"));
        }
        String browserText = MessageFormat.format("parent.window.location.href = \"{0}\";", encodeURL);
        JavaScriptExecutor executor = RWT.getClient().getService(JavaScriptExecutor.class);
        if (executor != null)
        {
            executor.execute(browserText);
        }
    }
}
