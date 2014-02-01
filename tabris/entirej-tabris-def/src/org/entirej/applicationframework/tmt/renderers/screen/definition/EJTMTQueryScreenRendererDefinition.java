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
package org.entirej.applicationframework.tmt.renderers.screen.definition;

import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.entirej.applicationframework.tmt.renderers.screen.definition.interfaces.EJTMTScreenRendererDefinitionProperties;
import org.entirej.framework.core.properties.definitions.EJPropertyDefinitionType;
import org.entirej.framework.core.properties.definitions.interfaces.EJPropertyDefinitionGroup;
import org.entirej.framework.dev.properties.EJDevPropertyDefinition;
import org.entirej.framework.dev.properties.interfaces.EJDevBlockDisplayProperties;
import org.entirej.framework.dev.properties.interfaces.EJDevScreenItemDisplayProperties;
import org.entirej.framework.dev.renderer.definition.EJDevItemRendererDefinitionControl;
import org.entirej.framework.dev.renderer.definition.EJDevScreenRendererDefinitionControl;
import org.entirej.framework.dev.renderer.definition.interfaces.EJDevQueryScreenRendererDefinition;


public class EJTMTQueryScreenRendererDefinition extends EJTMTScreenRendererDefinition implements EJDevQueryScreenRendererDefinition
{

    @Override
    public String getRendererClassName()
    {
        return "org.entirej.applicationframework.tmt.renderers.screen.EJTMTQueryScreenRenderer";
    }

    public EJPropertyDefinitionGroup getQueryScreenPropertyDefinitionGroup()
    {
        EJPropertyDefinitionGroup mainGroup = getScreenPropertyDefinitions();

        EJDevPropertyDefinition clearButtonLabel = new EJDevPropertyDefinition(EJTMTScreenRendererDefinitionProperties.CLEAR_BUTTON_TEXT,
                EJPropertyDefinitionType.STRING);
        clearButtonLabel.setLabel("Clear Button Label");
        clearButtonLabel.setDescription("The label displayed on the clear button");

        mainGroup.addPropertyDefinition(clearButtonLabel);

        return mainGroup;
    }

    public EJPropertyDefinitionGroup getItemPropertyDefinitionGroup()
    {
        return getItemPropertyDefinitions();
    }

    /*
     * I don't want to add extra buttons to the query screen!
     */
    protected void addExtraButtonsGroup(EJPropertyDefinitionGroup parentGroup)
    {

    }

    @Override
    public EJDevScreenRendererDefinitionControl addQueryScreenControl(EJDevBlockDisplayProperties blockDisplayProperties, Composite parent,
            FormToolkit formToolkit)
    {
        int height = blockDisplayProperties.getQueryScreenRendererProperties().getIntProperty(EJTMTScreenRendererDefinitionProperties.HEIGHT, 300);
        int width = blockDisplayProperties.getQueryScreenRendererProperties().getIntProperty(EJTMTScreenRendererDefinitionProperties.WIDTH, 300);
        int numcols = blockDisplayProperties.getQueryScreenRendererProperties().getIntProperty(EJTMTScreenRendererDefinitionProperties.NUM_COLS, 1);

        Composite screen = new Composite(parent, SWT.SHADOW_NONE);

        formToolkit.adapt(screen);

        GridLayout gridLayout = new GridLayout();
        gridLayout.numColumns = numcols;
        gridLayout.horizontalSpacing = 0;
        gridLayout.verticalSpacing = 0;
        screen.setLayout(gridLayout);

        GridData gd = new GridData(GridData.VERTICAL_ALIGN_BEGINNING);
        gd.horizontalAlignment = SWT.FILL;
        gd.grabExcessHorizontalSpace = true;
        gd.verticalAlignment = SWT.FILL;
        gd.grabExcessVerticalSpace = true;

        gd.widthHint = width;
        gd.heightHint = height;

        screen.setLayoutData(gd);

        EJTMTScreenPreviewerCreator creator = new EJTMTScreenPreviewerCreator();
        List<EJDevItemRendererDefinitionControl> itemControls = creator.addQueryScreenPreviewControl(this, blockDisplayProperties, screen, formToolkit);

        return new EJDevScreenRendererDefinitionControl(blockDisplayProperties, itemControls);
    }

    @Override
    public EJPropertyDefinitionGroup getSpacerItemPropertyDefinitionGroup()
    {
        return super.getSpacerItemPropertyDefinitionGroup();
    }

    @Override
    public EJDevItemRendererDefinitionControl getSpacerItemControl(EJDevScreenItemDisplayProperties itemProperties, Composite parent, FormToolkit toolkit)
    {
        return super.getSpacerItemControl(itemProperties, parent, toolkit);
    }

}
