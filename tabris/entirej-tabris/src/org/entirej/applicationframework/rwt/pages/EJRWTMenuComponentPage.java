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
package org.entirej.applicationframework.rwt.pages;

import org.eclipse.swt.widgets.Composite;
import org.entirej.applicationframework.rwt.application.components.menu.EJRWTDefaultMenuBuilder;
import org.entirej.applicationframework.rwt.application.launcher.EJRWTContext;

import com.eclipsesource.tabris.ui.AbstractPage;
import com.eclipsesource.tabris.ui.PageData;

public abstract class EJRWTMenuComponentPage extends AbstractPage
{

    private static final long serialVersionUID = -4020410894639262478L;
   
    private  String menuId = null;
    public EJRWTMenuComponentPage(String menuId)
    {
       this.menuId = menuId;
    }
    
    
    @Override
    public void createContent(Composite parent, PageData data)
    {
        
        EJRWTDefaultMenuBuilder _menuBuilder = new EJRWTDefaultMenuBuilder(EJRWTContext.getEJRWTApplicationManager(), parent);
         _menuBuilder.createTreeComponent(menuId);
    }

}
