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
package org.entirej.applicationframework.tmt.renderers.lov.definition;

import java.util.Collections;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.entirej.applicationframework.tmt.renderers.block.definition.interfaces.EJTMTMultiRecordBlockDefinitionProperties;
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


public class EJTMTStandardLovRendererDefinition implements EJDevLovRendererDefinition
{
    @Override
    public boolean allowSpacerItems()
    {
        return false;
    }

    public String getRendererClassName()
    {
        return "org.entirej.applicationframework.tmt.renderers.lov.EJTMTStandardLovRenderer";
    }

    public EJPropertyDefinitionGroup getLovPropertyDefinitionGroup()
    {
        EJDevPropertyDefinitionGroup mainGroup = new EJDevPropertyDefinitionGroup("Lov Renderer Properties");

       
        EJDevPropertyDefinition rowSelaction = new EJDevPropertyDefinition(EJTMTMultiRecordBlockDefinitionProperties.ROW_SELECTION,
                EJPropertyDefinitionType.BOOLEAN);
        rowSelaction.setLabel("Row Selection");
        rowSelaction.setDescription("If selected, the renderer will support row selection");
        rowSelaction.setDefaultValue("false");
        
       
        EJDevPropertyDefinition rowHeight = new EJDevPropertyDefinition(EJTMTMultiRecordBlockDefinitionProperties.ROW_HEIGHT, EJPropertyDefinitionType.INTEGER);
        rowHeight.setLabel("Custom Row Height");
        rowHeight.setDescription("If provided, the renderer will use custom row height");

        

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
        EJDevPropertyDefinition height = new EJDevPropertyDefinition(EJTMTMultiRecordBlockDefinitionProperties.HEIGHT_PROPERTY,
                EJPropertyDefinitionType.INTEGER);
        height.setLabel("Height");
        height.setDescription("The height of this items column within the cell.");
     

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

        mainGroup.addPropertyDefinition(visualAttribute);

        return mainGroup;
    }

    @Override
    public EJPropertyDefinitionGroup getSpacerItemPropertiesDefinitionGroup()
    {
        return null;
    }

    public boolean executeAutomaticQuery()
    {
        return true;
    }

    public boolean allowsUserQuery()
    {
        return false;
    }

    public EJDevQueryScreenRendererDefinition getQueryScreenRendererDefinition()
    {
        return null;
    }

    public boolean requiresAllRowsRetrieved()
    {
        return true;
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
