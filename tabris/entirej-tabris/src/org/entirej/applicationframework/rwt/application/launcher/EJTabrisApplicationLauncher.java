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
import static org.eclipse.rap.rwt.internal.service.ContextProvider.getApplicationContext;

import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.Collection;
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
import org.entirej.applicationframework.rwt.pages.EJRWTMenuComponentPage;
import org.entirej.framework.core.EJFrameworkHelper;
import org.entirej.framework.core.EJFrameworkInitialiser;
import org.entirej.framework.core.interfaces.EJMessenger;
import org.entirej.framework.core.properties.EJCoreLayoutContainer;
import org.entirej.framework.core.properties.EJCoreMenuProperties;
import org.entirej.framework.core.properties.EJCoreProperties;

import com.eclipsesource.tabris.TabrisClientInstaller;
import com.eclipsesource.tabris.ui.Page;
import com.eclipsesource.tabris.ui.PageConfiguration;
import com.eclipsesource.tabris.ui.TabrisUI;
import com.eclipsesource.tabris.ui.UIConfiguration;

public abstract class EJTabrisApplicationLauncher implements ApplicationConfiguration
{


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
        TabrisClientInstaller.install( configuration );
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
        configuration.addResource(getFavicon(), new FileResource());
        configuration.addResource(getLoadingImage(), new FileResource());
       

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

                        

                        
                        Display display = Display.getDefault();
                        if (display.isDisposed())
                            display = new Display();
                        preApplicationBuild(applicationManager);
                        
                        
                        
                       
                        
                        //build tabris ui
                        UIConfiguration uiConfiguration = new UIConfiguration();
                        initRootPageConfiguration(uiConfiguration);
                        Shell shell = new Shell(display, SWT.NO_TRIM);
                        TabrisUI tabrisUI = new TabrisUI(uiConfiguration);
                        // Now build the application container
                        EJRWTApplicationContainer appContainer = new EJRWTApplicationContainer();
                        
                        applicationManager.buildApplication(appContainer, shell);
                        postApplicationBuild(applicationManager);
                        tabrisUI.create(shell);
                        shell.layout();
                        shell.setMaximized(true);


                        return openShell(display, shell);
                    }
                };
            }
        }, properties);
    }

    
    
    protected void initRootPageConfiguration(UIConfiguration configuration)
    {
        //by default add menu page
        
         
        addRootPageConfiguration(configuration, DefaultMenuPage.ID, DefaultMenuPage.class, "Menu");
        
    }
    
     static String getDefaultMenuID()
    {
        Collection<EJCoreMenuProperties> allMenuProperties = EJCoreProperties.getInstance().getMenuContainer().getAllMenuProperties();
        String defaultMenu = null;
        for (EJCoreMenuProperties ejCoreMenuProperties : allMenuProperties)
        {
            if(ejCoreMenuProperties.isDefault())
            {
                defaultMenu = ejCoreMenuProperties.getName(); 
                break;
            }
        }
        if(defaultMenu==null)
        {
            throw  new RuntimeException("application.ejprop default menu not defined");
        }
        return defaultMenu;
    }
    
    public static class DefaultMenuPage extends EJRWTMenuComponentPage
    {
        static final String ID = "EJRWTDMP";

        
        public DefaultMenuPage()
        {
            super(getDefaultMenuID());
        }
        
    }
    
    protected void addRootPageConfiguration(UIConfiguration configuration,String id,Class<? extends Page> page,String title,InputStream image)
    {
        PageConfiguration pageConfig = new PageConfiguration( id, page);
        pageConfig.setTitle( title);
        if(image!=null)
            pageConfig.setImage(image);
        pageConfig.setTopLevel(true);
        configuration.addPageConfiguration( pageConfig );
    }
    
    protected void addRootPageConfiguration(UIConfiguration configuration,String id,Class<? extends Page> page,String title)
    {
        addRootPageConfiguration(configuration, id, page, title, null);
    }
    
    
    @SuppressWarnings("deprecation")
    public static int openShell(Display display, Shell shell)
    {
        shell.open();
        if (getApplicationContext().getLifeCycleFactory().getLifeCycle() instanceof RWTLifeCycle)
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
    }

    public void postApplicationBuild(EJFrameworkHelper frameworkHelper)
    {
    }

    public void registerServiceHandlers()
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
            return EJTabrisApplicationLauncher.class.getClassLoader();
        }

    }

    
}
