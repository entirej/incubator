package org.entirej.custom.renderers.definition;

import java.util.Collections;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.entirej.framework.core.properties.definitions.interfaces.EJFrameworkExtensionProperties;
import org.entirej.framework.core.properties.definitions.interfaces.EJPropertyDefinition;
import org.entirej.framework.core.properties.definitions.interfaces.EJPropertyDefinitionGroup;
import org.entirej.framework.core.properties.definitions.interfaces.EJPropertyDefinitionListener;
import org.entirej.framework.core.properties.interfaces.EJMainScreenProperties;
import org.entirej.framework.dev.properties.interfaces.EJDevBlockDisplayProperties;
import org.entirej.framework.dev.properties.interfaces.EJDevScreenItemDisplayProperties;
import org.entirej.framework.dev.renderer.definition.EJDevBlockRendererDefinitionControl;
import org.entirej.framework.dev.renderer.definition.EJDevItemRendererDefinitionControl;
import org.entirej.framework.dev.renderer.definition.interfaces.EJDevBlockRendererDefinition;
import org.entirej.framework.dev.renderer.definition.interfaces.EJDevInsertScreenRendererDefinition;
import org.entirej.framework.dev.renderer.definition.interfaces.EJDevQueryScreenRendererDefinition;
import org.entirej.framework.dev.renderer.definition.interfaces.EJDevUpdateScreenRendererDefinition;

public class MenuRendererDefinition implements EJDevBlockRendererDefinition
{

    @Override
    public boolean useQueryScreen()
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean useInsertScreen()
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean useUpdateScreen()
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean allowSpacerItems()
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean allowMultipleItemGroupsOnMainScreen()
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public EJPropertyDefinitionGroup getBlockPropertyDefinitionGroup()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public EJPropertyDefinitionGroup getItemPropertiesDefinitionGroup()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public EJPropertyDefinitionGroup getSpacerItemPropertiesDefinitionGroup()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public EJPropertyDefinitionGroup getItemGroupPropertiesDefinitionGroup()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void loadValidValuesForProperty(EJFrameworkExtensionProperties frameworkExtensionProperties, EJPropertyDefinition propertyDefinition)
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void propertyChanged(EJPropertyDefinitionListener propertyDefinitionListener, EJFrameworkExtensionProperties properties, String propertyName)
    {
        // TODO Auto-generated method stub

    }

    @Override
    public String getRendererClassName()
    {
        return "org.entirej.custom.renderers.MenuRenderer";
    }

    @Override
    public EJDevBlockRendererDefinitionControl addBlockControlToCanvas(EJMainScreenProperties mainScreenProperties, EJDevBlockDisplayProperties blockDisplayProperties, Composite parent, FormToolkit formToolkit)
    {
        Composite layoutBody;

        if (mainScreenProperties.getDisplayFrame())
        {
            if (mainScreenProperties.getFrameTitle() != null && mainScreenProperties.getFrameTitle().length() > 0)
            {
                layoutBody = new Group(parent, SWT.NONE);
                ((Group) layoutBody).setText(mainScreenProperties.getFrameTitle());
            }
            else
            {
                layoutBody = new Composite(parent, SWT.BORDER);
            }

        }
        else
        {
            layoutBody = new Composite(parent, SWT.NONE);
        }

        layoutBody.setLayout(new FillLayout());

        Label workView = new Label(layoutBody, SWT.NONE);
        workView.setText("<Work View Preview>");
        return new EJDevBlockRendererDefinitionControl(blockDisplayProperties, Collections.<EJDevItemRendererDefinitionControl> emptyList());
    }

    @Override
    public EJDevItemRendererDefinitionControl getSpacerItemControl(EJDevScreenItemDisplayProperties screenDisplayProperties, Composite parent, FormToolkit formToolkit)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public EJDevQueryScreenRendererDefinition getQueryScreenRendererDefinition()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public EJDevInsertScreenRendererDefinition getInsertScreenRendererDefinition()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public EJDevUpdateScreenRendererDefinition getUpdateScreenRendererDefinition()
    {
        // TODO Auto-generated method stub
        return null;
    }

}
