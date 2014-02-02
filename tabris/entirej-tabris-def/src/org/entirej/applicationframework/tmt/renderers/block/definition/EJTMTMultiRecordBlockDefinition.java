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
package org.entirej.applicationframework.tmt.renderers.block.definition;

import java.util.Collections;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.entirej.applicationframework.tmt.renderers.block.definition.interfaces.EJTMTMultiRecordBlockDefinitionProperties;
import org.entirej.applicationframework.tmt.renderers.screen.definition.EJTMTInsertScreenRendererDefinition;
import org.entirej.applicationframework.tmt.renderers.screen.definition.EJTMTQueryScreenRendererDefinition;
import org.entirej.applicationframework.tmt.renderers.screen.definition.EJTMTUpdateScreenRendererDefinition;
import org.entirej.framework.core.properties.definitions.EJPropertyDefinitionType;
import org.entirej.framework.core.properties.definitions.interfaces.EJFrameworkExtensionProperties;
import org.entirej.framework.core.properties.definitions.interfaces.EJPropertyDefinition;
import org.entirej.framework.core.properties.definitions.interfaces.EJPropertyDefinitionGroup;
import org.entirej.framework.core.properties.definitions.interfaces.EJPropertyDefinitionListener;
import org.entirej.framework.core.properties.interfaces.EJMainScreenProperties;
import org.entirej.framework.dev.properties.EJDevPropertyDefinition;
import org.entirej.framework.dev.properties.EJDevPropertyDefinitionGroup;
import org.entirej.framework.dev.properties.interfaces.EJDevBlockDisplayProperties;
import org.entirej.framework.dev.properties.interfaces.EJDevScreenItemDisplayProperties;
import org.entirej.framework.dev.renderer.definition.EJDevBlockRendererDefinitionControl;
import org.entirej.framework.dev.renderer.definition.EJDevItemRendererDefinitionControl;
import org.entirej.framework.dev.renderer.definition.interfaces.EJDevBlockRendererDefinition;
import org.entirej.framework.dev.renderer.definition.interfaces.EJDevInsertScreenRendererDefinition;
import org.entirej.framework.dev.renderer.definition.interfaces.EJDevQueryScreenRendererDefinition;
import org.entirej.framework.dev.renderer.definition.interfaces.EJDevUpdateScreenRendererDefinition;

public class EJTMTMultiRecordBlockDefinition implements EJDevBlockRendererDefinition
{
    public EJTMTMultiRecordBlockDefinition()
    {
    }

    @Override
    public String getRendererClassName()
    {
        return "org.entirej.applicationframework.tmt.renderers.blocks.EJTMTMultiRecordBlockRenderer";
    }

    @Override
    public boolean allowSpacerItems()
    {
        return false;
    }

    public void loadValidValuesForProperty(EJFrameworkExtensionProperties frameworkExtensionProperties, EJPropertyDefinition propertyDefinition)
    {
        // no impl
    }

    public void propertyChanged(EJPropertyDefinitionListener propertyDefListener, EJFrameworkExtensionProperties properties, String propertyName)
    {
        // no impl
    }

    public boolean useInsertScreen()
    {
        return true;
    }

    public boolean useQueryScreen()
    {
        return true;
    }

    public boolean useUpdateScreen()
    {
        return true;
    }

    @Override
    public EJDevInsertScreenRendererDefinition getInsertScreenRendererDefinition()
    {
        return new EJTMTInsertScreenRendererDefinition();
    }

    @Override
    public EJDevQueryScreenRendererDefinition getQueryScreenRendererDefinition()
    {
        return new EJTMTQueryScreenRendererDefinition();
    }

    @Override
    public EJDevUpdateScreenRendererDefinition getUpdateScreenRendererDefinition()
    {
        return new EJTMTUpdateScreenRendererDefinition();
    }

    public boolean allowMultipleItemGroupsOnMainScreen()
    {
        return false;
    }

    public EJPropertyDefinitionGroup getBlockPropertyDefinitionGroup()
    {
        EJDevPropertyDefinitionGroup mainGroup = new EJDevPropertyDefinitionGroup("Multi-Record Block");

        EJDevPropertyDefinition rowSelaction = new EJDevPropertyDefinition(EJTMTMultiRecordBlockDefinitionProperties.ROW_SELECTION,
                EJPropertyDefinitionType.BOOLEAN);
        rowSelaction.setLabel("Row Selection");
        rowSelaction.setDescription("If selected, the renderer will support row selection");
        rowSelaction.setDefaultValue("true");

 
        EJDevPropertyDefinition rowHeight = new EJDevPropertyDefinition(EJTMTMultiRecordBlockDefinitionProperties.ROW_HEIGHT, EJPropertyDefinitionType.INTEGER);
        rowHeight.setLabel("Custom Row Height");
        rowHeight.setDescription("If provided, the renderer will use custom row height");

        EJDevPropertyDefinition filter = new EJDevPropertyDefinition(EJTMTMultiRecordBlockDefinitionProperties.FILTER, EJPropertyDefinitionType.BOOLEAN);
        filter.setLabel("Add Filter");
        filter.setDescription("If selected, the renderer will display a filter field above the blocks data. This filter can then be used by users to filter the blocks displayed data");
        filter.setDefaultValue("false");

        mainGroup.addPropertyDefinition(filter);
        mainGroup.addPropertyDefinition(rowHeight);
        mainGroup.addPropertyDefinition(rowSelaction);

        return mainGroup;
    }

    public EJPropertyDefinitionGroup getItemPropertiesDefinitionGroup()
    {
        EJDevPropertyDefinitionGroup mainGroup = new EJDevPropertyDefinitionGroup("Multi-Record Block: Required Item Properties");

        // cell action to support selection
        EJDevPropertyDefinition cellActionCommand = new EJDevPropertyDefinition(EJTMTMultiRecordBlockDefinitionProperties.CELL_ACTION_COMMAND,
                EJPropertyDefinitionType.ACTION_COMMAND);
        cellActionCommand.setLabel("Action Command");
        cellActionCommand.setDescription("Add an action command that will be sent to the action processor when a user click on this cell");

        // cell width & height
        EJDevPropertyDefinition height = new EJDevPropertyDefinition(EJTMTMultiRecordBlockDefinitionProperties.HEIGHT_PROPERTY,
                EJPropertyDefinitionType.INTEGER);
        height.setLabel("Height");
        height.setDescription("The height  of this items column within the cell.");

        EJDevPropertyDefinition width = new EJDevPropertyDefinition(EJTMTMultiRecordBlockDefinitionProperties.WIDTH_PROPERTY, EJPropertyDefinitionType.INTEGER);
        width.setLabel("Width");
        width.setDescription("The width of this items column within the cell.");

        EJDevPropertyDefinition top = new EJDevPropertyDefinition(EJTMTMultiRecordBlockDefinitionProperties.CELL_TOP, EJPropertyDefinitionType.INTEGER);
        top.setLabel("Top");
        top.setDescription("Sets the top offset of the cell, i.e. the distance from the top edge of the template.");
        EJDevPropertyDefinition bottom = new EJDevPropertyDefinition(EJTMTMultiRecordBlockDefinitionProperties.CELL_BOTTOM, EJPropertyDefinitionType.INTEGER);
        bottom.setLabel("Bottom");
        bottom.setDescription("Sets the bottom offset of the cell, i.e. the distance from the bottom edge of the template.");
        EJDevPropertyDefinition left = new EJDevPropertyDefinition(EJTMTMultiRecordBlockDefinitionProperties.CELL_LEFT, EJPropertyDefinitionType.INTEGER);
        left.setLabel("Left");
        left.setDescription("Sets the left offset of the cell, i.e. the distance from the left edge of the template.");
        EJDevPropertyDefinition right = new EJDevPropertyDefinition(EJTMTMultiRecordBlockDefinitionProperties.CELL_RIGHT, EJPropertyDefinitionType.INTEGER);
        right.setLabel("Right");
        right.setDescription("Sets the right offset of the cell, i.e. the distance from the right edge of the template.");

        EJDevPropertyDefinition vAllignment = new EJDevPropertyDefinition(EJTMTMultiRecordBlockDefinitionProperties.CELL_V_ALIGNMENT,
                EJPropertyDefinitionType.STRING);
        vAllignment.setLabel("Vertical Alignment");
        vAllignment.setDescription("Indicates the alignment of the contents within this cell.");
        vAllignment.setDefaultValue(EJTMTMultiRecordBlockDefinitionProperties.COLUMN_ALLIGN_NONE);

        vAllignment.addValidValue(EJTMTMultiRecordBlockDefinitionProperties.COLUMN_ALLIGN_NONE, "None");
        vAllignment.addValidValue(EJTMTMultiRecordBlockDefinitionProperties.COLUMN_ALLIGN_TOP, "Top");
        vAllignment.addValidValue(EJTMTMultiRecordBlockDefinitionProperties.COLUMN_ALLIGN_CENTER, "Center");
        vAllignment.addValidValue(EJTMTMultiRecordBlockDefinitionProperties.COLUMN_ALLIGN_BOTTOM, "Bottom");

        EJDevPropertyDefinition hAllignment = new EJDevPropertyDefinition(EJTMTMultiRecordBlockDefinitionProperties.CELL_H_ALIGNMENT,
                EJPropertyDefinitionType.STRING);
        hAllignment.setLabel("Horizontal Alignment");
        hAllignment.setDescription("Indicates the alignment of the contents within this cell.");
        hAllignment.setDefaultValue(EJTMTMultiRecordBlockDefinitionProperties.COLUMN_ALLIGN_NONE);

        hAllignment.addValidValue(EJTMTMultiRecordBlockDefinitionProperties.COLUMN_ALLIGN_NONE, "None");
        hAllignment.addValidValue(EJTMTMultiRecordBlockDefinitionProperties.COLUMN_ALLIGN_LEFT, "Left");
        hAllignment.addValidValue(EJTMTMultiRecordBlockDefinitionProperties.COLUMN_ALLIGN_CENTER, "Center");
        hAllignment.addValidValue(EJTMTMultiRecordBlockDefinitionProperties.COLUMN_ALLIGN_RIGHT, "Right");

        EJDevPropertyDefinition visualAttribute = new EJDevPropertyDefinition(EJTMTMultiRecordBlockDefinitionProperties.VISUAL_ATTRIBUTE_PROPERTY,
                EJPropertyDefinitionType.VISUAL_ATTRIBUTE);
        visualAttribute.setLabel("Visual Attribute");
        visualAttribute.setDescription("The column will be displayed using the properties from the chosen visual attribute");
        visualAttribute.setMandatory(false);

        mainGroup.addPropertyDefinition(width);
        mainGroup.addPropertyDefinition(height);
        mainGroup.addPropertyDefinition(hAllignment);
        mainGroup.addPropertyDefinition(vAllignment);
        mainGroup.addPropertyDefinition(top);
        mainGroup.addPropertyDefinition(left);
        mainGroup.addPropertyDefinition(right);
        mainGroup.addPropertyDefinition(bottom);

        mainGroup.addPropertyDefinition(cellActionCommand);
        mainGroup.addPropertyDefinition(visualAttribute);

        return mainGroup;
    }

    @Override
    public EJPropertyDefinitionGroup getSpacerItemPropertiesDefinitionGroup()
    {
        // No spacers are available for a multi record block
        return null;
    }

    @Override
    public EJDevBlockRendererDefinitionControl addBlockControlToCanvas(EJMainScreenProperties mainScreenProperties,
            EJDevBlockDisplayProperties blockDisplayProperties, Composite parent, FormToolkit toolkit)
    {
        EJFrameworkExtensionProperties rendererProperties = blockDisplayProperties.getBlockRendererProperties();

        Composite layoutBody;

        if (mainScreenProperties.getDisplayFrame())
        {

            layoutBody = new Group(parent, SWT.NONE);
            if (mainScreenProperties.getFrameTitle() != null)
                ((Group) layoutBody).setText(mainScreenProperties.getFrameTitle());

        }
        else
        {
            layoutBody = new Composite(parent, SWT.NONE);
        }

        layoutBody.setLayout(new GridLayout(mainScreenProperties.getNumCols(), false));

        Label browser = new Label(layoutBody, SWT.NONE);
        browser.setText("TABLE RENDERER-TABRIS-ROW-TEMPLATE");
        return new EJDevBlockRendererDefinitionControl(blockDisplayProperties, Collections.<EJDevItemRendererDefinitionControl> emptyList());
    }

    @Override
    public EJDevItemRendererDefinitionControl getSpacerItemControl(EJDevScreenItemDisplayProperties itemProperties, Composite parent, FormToolkit toolkit)
    {
        return null;
    }

    @Override
    public EJPropertyDefinitionGroup getItemGroupPropertiesDefinitionGroup()
    {
        // TODO Auto-generated method stub
        return null;
    }

}
