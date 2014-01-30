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
package org.entirej.applicationframework.rwt.renderers.lov.definition;

import java.util.Collections;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.entirej.applicationframework.rwt.renderers.block.definition.interfaces.EJRWTMultiRecordBlockDefinitionProperties;
import org.entirej.applicationframework.rwt.renderers.screen.definition.EJRWTQueryScreenRendererDefinition;
import org.entirej.framework.core.properties.definitions.EJPropertyDefinitionType;
import org.entirej.framework.core.properties.definitions.interfaces.EJFrameworkExtensionProperties;
import org.entirej.framework.core.properties.definitions.interfaces.EJPropertyDefinition;
import org.entirej.framework.core.properties.definitions.interfaces.EJPropertyDefinitionGroup;
import org.entirej.framework.core.properties.definitions.interfaces.EJPropertyDefinitionListener;
import org.entirej.framework.dev.properties.EJDevPropertyDefinition;
import org.entirej.framework.dev.properties.EJDevPropertyDefinitionGroup;
import org.entirej.framework.dev.properties.interfaces.EJDevLovDefinitionDisplayProperties;
import org.entirej.framework.dev.renderer.definition.EJDevItemRendererDefinitionControl;
import org.entirej.framework.dev.renderer.definition.EJDevLovRendererDefinitionControl;
import org.entirej.framework.dev.renderer.definition.interfaces.EJDevLovRendererDefinition;
import org.entirej.framework.dev.renderer.definition.interfaces.EJDevQueryScreenRendererDefinition;

public class EJRWTLookupFormLovRendererDefinition implements EJDevLovRendererDefinition
{
    @Override
    public boolean allowSpacerItems()
    {
        return false;
    }

    public String getRendererClassName()
    {
        return "org.entirej.applicationframework.rwt.renderers.lov.EJRWTLookupFormLovRenderer";
    }

    public EJPropertyDefinitionGroup getLovPropertyDefinitionGroup()
    {
        EJDevPropertyDefinitionGroup mainGroup = new EJDevPropertyDefinitionGroup("Lov Renderer Properties");

        EJDevPropertyDefinition showTableBorder = new EJDevPropertyDefinition(EJRWTMultiRecordBlockDefinitionProperties.HIDE_TABLE_BORDER,
                EJPropertyDefinitionType.BOOLEAN);
        showTableBorder.setLabel("Hide Border");
        showTableBorder.setDescription("If selected, the renderer will hide the lov's standard border");
        showTableBorder.setDefaultValue("false");

        EJDevPropertyDefinition rowSelaction = new EJDevPropertyDefinition(EJRWTMultiRecordBlockDefinitionProperties.ROW_SELECTION,
                EJPropertyDefinitionType.BOOLEAN);
        rowSelaction.setLabel("Row Selection");
        rowSelaction.setDescription("If selected, the renderer will support row selection");
        rowSelaction.setDefaultValue("false");

        EJDevPropertyDefinition rowHeight = new EJDevPropertyDefinition(EJRWTMultiRecordBlockDefinitionProperties.ROW_HEIGHT, EJPropertyDefinitionType.INTEGER);
        rowHeight.setLabel("Custom Row Height");
        rowHeight.setDescription("If provided, the renderer will use custom row height");

        mainGroup.addPropertyDefinition(showTableBorder);
        mainGroup.addPropertyDefinition(rowHeight);
        mainGroup.addPropertyDefinition(rowSelaction);

        return mainGroup;
    }

    public void propertyChanged(EJPropertyDefinitionListener propertyDefListener, EJFrameworkExtensionProperties properties, String propertyName)
    {
        // no impl
    }

    public void loadValidValuesForProperty(EJFrameworkExtensionProperties frameworkExtensionProperties, EJPropertyDefinition propertyDefinition)
    {
        // no impl

    }

    public EJPropertyDefinitionGroup getItemPropertiesDefinitionGroup()
    {
        EJDevPropertyDefinitionGroup mainGroup = new EJDevPropertyDefinitionGroup("Lov Renderer: Required Item Properties");

        // cell width & height
        EJDevPropertyDefinition height = new EJDevPropertyDefinition(EJRWTMultiRecordBlockDefinitionProperties.HEIGHT_PROPERTY,
                EJPropertyDefinitionType.INTEGER);
        height.setLabel("Height");
        height.setDescription("The height  of this items column within the cell.");
        

        EJDevPropertyDefinition width = new EJDevPropertyDefinition(EJRWTMultiRecordBlockDefinitionProperties.WIDTH_PROPERTY, EJPropertyDefinitionType.INTEGER);
        width.setLabel("Width");
        width.setDescription("The width of this items column within the cell.");
        

        EJDevPropertyDefinition top = new EJDevPropertyDefinition(EJRWTMultiRecordBlockDefinitionProperties.CELL_TOP, EJPropertyDefinitionType.INTEGER);
        top.setLabel("Top");
        top.setDescription("Sets the top offset of the cell, i.e. the distance from the top edge of the template.");
        EJDevPropertyDefinition bottom = new EJDevPropertyDefinition(EJRWTMultiRecordBlockDefinitionProperties.CELL_BOTTOM, EJPropertyDefinitionType.INTEGER);
        bottom.setLabel("Bottom");
        bottom.setDescription("Sets the bottom offset of the cell, i.e. the distance from the bottom edge of the template.");
        EJDevPropertyDefinition left = new EJDevPropertyDefinition(EJRWTMultiRecordBlockDefinitionProperties.CELL_LEFT, EJPropertyDefinitionType.INTEGER);
        left.setLabel("Left");
        left.setDescription("Sets the left offset of the cell, i.e. the distance from the left edge of the template.");
        EJDevPropertyDefinition right = new EJDevPropertyDefinition(EJRWTMultiRecordBlockDefinitionProperties.CELL_RIGHT, EJPropertyDefinitionType.INTEGER);
        right.setLabel("Right");
        right.setDescription("Sets the right offset of the cell, i.e. the distance from the right edge of the template.");

        EJDevPropertyDefinition vAllignment = new EJDevPropertyDefinition(EJRWTMultiRecordBlockDefinitionProperties.CELL_V_ALIGNMENT,
                EJPropertyDefinitionType.STRING);
        vAllignment.setLabel("Vertical Alignment");
        vAllignment.setDescription("Indicates the alignment of the contents within this cell.");
        vAllignment.setDefaultValue(EJRWTMultiRecordBlockDefinitionProperties.COLUMN_ALLIGN_NONE);

        vAllignment.addValidValue(EJRWTMultiRecordBlockDefinitionProperties.COLUMN_ALLIGN_NONE, "None");
        vAllignment.addValidValue(EJRWTMultiRecordBlockDefinitionProperties.COLUMN_ALLIGN_TOP, "Top");
        vAllignment.addValidValue(EJRWTMultiRecordBlockDefinitionProperties.COLUMN_ALLIGN_CENTER, "Center");
        vAllignment.addValidValue(EJRWTMultiRecordBlockDefinitionProperties.COLUMN_ALLIGN_BOTTOM, "Bottom");

        EJDevPropertyDefinition hAllignment = new EJDevPropertyDefinition(EJRWTMultiRecordBlockDefinitionProperties.CELL_H_ALIGNMENT,
                EJPropertyDefinitionType.STRING);
        hAllignment.setLabel("Horizontal Alignment");
        hAllignment.setDescription("Indicates the alignment of the contents within this cell.");
        hAllignment.setDefaultValue(EJRWTMultiRecordBlockDefinitionProperties.COLUMN_ALLIGN_NONE);

        hAllignment.addValidValue(EJRWTMultiRecordBlockDefinitionProperties.COLUMN_ALLIGN_NONE, "None");
        hAllignment.addValidValue(EJRWTMultiRecordBlockDefinitionProperties.COLUMN_ALLIGN_LEFT, "Left");
        hAllignment.addValidValue(EJRWTMultiRecordBlockDefinitionProperties.COLUMN_ALLIGN_CENTER, "Center");
        hAllignment.addValidValue(EJRWTMultiRecordBlockDefinitionProperties.COLUMN_ALLIGN_RIGHT, "Right");

        EJDevPropertyDefinition visualAttribute = new EJDevPropertyDefinition(EJRWTMultiRecordBlockDefinitionProperties.VISUAL_ATTRIBUTE_PROPERTY,
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

        mainGroup.addPropertyDefinition(visualAttribute);

        return mainGroup;
    }

    @Override
    public EJDevPropertyDefinitionGroup getSpacerItemPropertiesDefinitionGroup()
    {
        return null;
    }

    public boolean executeAutomaticQuery()
    {
        return false;
    }

    public boolean allowsUserQuery()
    {
        return true;
    }

    public EJDevQueryScreenRendererDefinition getQueryScreenRendererDefinition()
    {
        return new EJRWTQueryScreenRendererDefinition();
    }

    public boolean requiresAllRowsRetrieved()
    {
        return false;
    }

    public EJDevLovRendererDefinitionControl addLovControlToCanvas(EJDevLovDefinitionDisplayProperties lovDisplayProperties, Composite parent,
            FormToolkit toolkit)
    {
        Composite layoutBody = new Composite(parent, SWT.NONE);

        layoutBody.setLayout(new GridLayout(1, false));

        Label browser = new Label(layoutBody, SWT.NONE);

        browser.setText("LOV RENDERER-TABRIS-ROW-TEMPLATE");
        return new EJDevLovRendererDefinitionControl(lovDisplayProperties, Collections.<EJDevItemRendererDefinitionControl> emptyList());

    }

    @Override
    public EJPropertyDefinitionGroup getItemGroupPropertiesDefinitionGroup()
    {
        // TODO Auto-generated method stub
        return null;
    }
}
