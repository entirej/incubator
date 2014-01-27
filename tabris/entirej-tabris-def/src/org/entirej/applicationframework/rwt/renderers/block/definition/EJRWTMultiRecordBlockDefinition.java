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
package org.entirej.applicationframework.rwt.renderers.block.definition;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;
import org.entirej.applicationframework.rwt.renderers.block.definition.interfaces.EJRWTMultiRecordBlockDefinitionProperties;
import org.entirej.applicationframework.rwt.renderers.block.definition.interfaces.EJRWTSingleRecordBlockDefinitionProperties;
import org.entirej.applicationframework.rwt.renderers.block.definition.interfaces.EJRWTTreeBlockDefinitionProperties;
import org.entirej.applicationframework.rwt.renderers.screen.definition.EJRWTInsertScreenRendererDefinition;
import org.entirej.applicationframework.rwt.renderers.screen.definition.EJRWTQueryScreenRendererDefinition;
import org.entirej.applicationframework.rwt.renderers.screen.definition.EJRWTUpdateScreenRendererDefinition;
import org.entirej.framework.core.properties.definitions.EJPropertyDefinitionType;
import org.entirej.framework.core.properties.definitions.interfaces.EJFrameworkExtensionProperties;
import org.entirej.framework.core.properties.definitions.interfaces.EJPropertyDefinition;
import org.entirej.framework.core.properties.definitions.interfaces.EJPropertyDefinitionGroup;
import org.entirej.framework.core.properties.definitions.interfaces.EJPropertyDefinitionListener;
import org.entirej.framework.core.properties.interfaces.EJMainScreenProperties;
import org.entirej.framework.dev.properties.EJDevPropertyDefinition;
import org.entirej.framework.dev.properties.EJDevPropertyDefinitionGroup;
import org.entirej.framework.dev.properties.EJDevPropertyDefinitionList;
import org.entirej.framework.dev.properties.interfaces.EJDevBlockDisplayProperties;
import org.entirej.framework.dev.properties.interfaces.EJDevItemGroupDisplayProperties;
import org.entirej.framework.dev.properties.interfaces.EJDevMainScreenItemDisplayProperties;
import org.entirej.framework.dev.properties.interfaces.EJDevScreenItemDisplayProperties;
import org.entirej.framework.dev.renderer.definition.EJDevItemRendererDefinitionControl;
import org.entirej.framework.dev.renderer.definition.interfaces.EJDevBlockRendererDefinition;
import org.entirej.framework.dev.renderer.definition.interfaces.EJDevInsertScreenRendererDefinition;
import org.entirej.framework.dev.renderer.definition.interfaces.EJDevQueryScreenRendererDefinition;
import org.entirej.framework.dev.renderer.definition.interfaces.EJDevUpdateScreenRendererDefinition;

public class EJRWTMultiRecordBlockDefinition implements EJDevBlockRendererDefinition
{
    public EJRWTMultiRecordBlockDefinition()
    {
    }

    @Override
    public String getRendererClassName()
    {
        return "org.entirej.applicationframework.rwt.renderers.blocks.EJRWTMultiRecordBlockRenderer";
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
        return new EJRWTInsertScreenRendererDefinition();
    }

    @Override
    public EJDevQueryScreenRendererDefinition getQueryScreenRendererDefinition()
    {
        return new EJRWTQueryScreenRendererDefinition();
    }

    @Override
    public EJDevUpdateScreenRendererDefinition getUpdateScreenRendererDefinition()
    {
        return new EJRWTUpdateScreenRendererDefinition();
    }

    public boolean allowMultipleItemGroupsOnMainScreen()
    {
        return false;
    }

    public EJPropertyDefinitionGroup getBlockPropertyDefinitionGroup()
    {
        EJDevPropertyDefinitionGroup mainGroup = new EJDevPropertyDefinitionGroup("Multi-Record Block");

        EJDevPropertyDefinition doubleClickActionCommand = new EJDevPropertyDefinition(EJRWTMultiRecordBlockDefinitionProperties.DOUBLE_CLICK_ACTION_COMMAND,
                EJPropertyDefinitionType.ACTION_COMMAND);
        doubleClickActionCommand.setLabel("Double Click Action Command");
        doubleClickActionCommand.setDescription("Add an action command that will be sent to the action processor when a user double clicks on this block");

        EJDevPropertyDefinition showTableBorder = new EJDevPropertyDefinition(EJRWTMultiRecordBlockDefinitionProperties.HIDE_TABLE_BORDER,
                EJPropertyDefinitionType.BOOLEAN);
        showTableBorder.setLabel("Hide Table Border");
        showTableBorder.setDescription("If selected, the renderer will hide the blocks standard border");
        showTableBorder.setDefaultValue("false");

        EJDevPropertyDefinition showTableHeader = new EJDevPropertyDefinition(EJRWTMultiRecordBlockDefinitionProperties.SHOW_HEADING_PROPERTY,
                EJPropertyDefinitionType.BOOLEAN);
        showTableHeader.setLabel("Show Headings");
        showTableHeader.setDescription("If selected, the cloumn headings of the block will be displayed");
        showTableHeader.setDefaultValue("true");

        EJDevPropertyDefinition allowRowSelection = new EJDevPropertyDefinition(EJRWTMultiRecordBlockDefinitionProperties.ROW_SELECTION_PROPERTY,
                EJPropertyDefinitionType.BOOLEAN);
        allowRowSelection.setLabel("Allow Row Selection");
        allowRowSelection.setDescription("Indicates if row selection is allowed for this block");
        allowRowSelection.setDefaultValue("true");

        EJDevPropertyDefinition filter = new EJDevPropertyDefinition(EJRWTTreeBlockDefinitionProperties.FILTER, EJPropertyDefinitionType.BOOLEAN);
        filter.setLabel("Add Filter");
        filter.setDescription("If selected, the renderer will display a filter field above the blocks data. This filter can then be used by users to filter the blocks displayed data");
        filter.setDefaultValue("false");

        EJDevPropertyDefinition showVerticalLines = new EJDevPropertyDefinition(EJRWTMultiRecordBlockDefinitionProperties.SHOW_VERTICAL_LINES,
                EJPropertyDefinitionType.BOOLEAN);
        showVerticalLines.setLabel("Show Vertical Lines");
        showVerticalLines.setDescription("Indicates if the block should display vertical lines");
        showVerticalLines.setDefaultValue("true");

        mainGroup.addPropertyDefinition(doubleClickActionCommand);
        mainGroup.addPropertyDefinition(showTableBorder);
        mainGroup.addPropertyDefinition(showTableHeader);
        mainGroup.addPropertyDefinition(allowRowSelection);
        mainGroup.addPropertyDefinition(filter);
        mainGroup.addPropertyDefinition(showVerticalLines);

    

        return mainGroup;
    }

    public EJPropertyDefinitionGroup getItemPropertiesDefinitionGroup()
    {
        EJDevPropertyDefinitionGroup mainGroup = new EJDevPropertyDefinitionGroup("Multi-Record Block: Required Item Properties");

        EJDevPropertyDefinition isFixedInTable = new EJDevPropertyDefinition(EJRWTMultiRecordBlockDefinitionProperties.IS_COLUMN_FIXED,
                EJPropertyDefinitionType.BOOLEAN);
        isFixedInTable.setLabel("Fixed Column");
        isFixedInTable.setDescription("Indicates if this column is fixed to the left part of this block. Fixed columns hold their position even when the user scrolls horizontally to see the hidden columns");
        isFixedInTable.setDefaultValue("false");

        EJDevPropertyDefinition displayedWidth = new EJDevPropertyDefinition(EJRWTMultiRecordBlockDefinitionProperties.DISPLAY_WIDTH_PROPERTY,
                EJPropertyDefinitionType.INTEGER);
        displayedWidth.setLabel("Displayed Width");
        displayedWidth
                .setDescription("The width (in characters) of this items column within the block.If zero is specified then the width of the column will be relative to the data it contains and the width of the other columns");

        EJDevPropertyDefinition headerAllignment = new EJDevPropertyDefinition(EJRWTMultiRecordBlockDefinitionProperties.COLUMN_ALIGNMENT,
                EJPropertyDefinitionType.STRING);
        headerAllignment.setLabel("Column Alignment");
        headerAllignment.setDescription("Indicates the alignment of the contents within this column.");
        headerAllignment.setDefaultValue(EJRWTMultiRecordBlockDefinitionProperties.COLUMN_ALLIGN_LEFT);

        headerAllignment.addValidValue(EJRWTMultiRecordBlockDefinitionProperties.COLUMN_ALLIGN_LEFT, "Left");
        headerAllignment.addValidValue(EJRWTMultiRecordBlockDefinitionProperties.COLUMN_ALLIGN_RIGHT, "Right");
        headerAllignment.addValidValue(EJRWTMultiRecordBlockDefinitionProperties.COLUMN_ALLIGN_CENTER, "Center");

        EJDevPropertyDefinition allowColumnSorting = new EJDevPropertyDefinition(EJRWTMultiRecordBlockDefinitionProperties.ALLOW_ROW_SORTING,
                EJPropertyDefinitionType.BOOLEAN);
        allowColumnSorting.setLabel("Allow Column Sorting");
        allowColumnSorting.setDescription("If selected, the user will be able to re-order the data within the block by clicking on the column header. Only block contents will be sorted, no new data will be retreived from the datasource");
        allowColumnSorting.setDefaultValue("true");

        EJDevPropertyDefinition allowColunmResize = new EJDevPropertyDefinition(EJRWTMultiRecordBlockDefinitionProperties.ALLOW_COLUMN_RESIZE,
                EJPropertyDefinitionType.BOOLEAN);
        allowColunmResize.setLabel("Allow Resize of Column");
        allowColunmResize.setDescription("If selected, the user will be able to resize the width of the columns within the block");
        allowColunmResize.setDefaultValue("true");

        EJDevPropertyDefinition allowColunmReorder = new EJDevPropertyDefinition(EJRWTMultiRecordBlockDefinitionProperties.ALLOW_COLUMN_REORDER,
                EJPropertyDefinitionType.BOOLEAN);
        allowColunmReorder.setLabel("Allow Re-Order of Column");
        allowColunmReorder.setDescription("If selected, the user will be able to move columns of this block to change their displayed position. The re-positioning will not be saved and the next time the block is displayed, columns will be displayed in their original positions");
        allowColunmReorder.setDefaultValue("true");

        EJDevPropertyDefinition visualAttribute = new EJDevPropertyDefinition(EJRWTMultiRecordBlockDefinitionProperties.VISUAL_ATTRIBUTE_PROPERTY,
                EJPropertyDefinitionType.VISUAL_ATTRIBUTE);
        visualAttribute.setLabel("Visual Attribute");
        visualAttribute.setDescription("The column will be displayed using the properties from the chosen visual attribute");
        visualAttribute.setMandatory(false);

        mainGroup.addPropertyDefinition(isFixedInTable);
        mainGroup.addPropertyDefinition(displayedWidth);
        mainGroup.addPropertyDefinition(headerAllignment);

        mainGroup.addPropertyDefinition(allowColumnSorting);
        mainGroup.addPropertyDefinition(allowColunmResize);
        mainGroup.addPropertyDefinition(allowColunmReorder);
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
    public EJRWTTableRendererDefinitionControl addBlockControlToCanvas(EJMainScreenProperties mainScreenProperties,
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

        EJRWTTableRendererDefinitionControl control = addTable(blockDisplayProperties, layoutBody, toolkit);

        return control;
    }

    private EJRWTTableRendererDefinitionControl addTable(EJDevBlockDisplayProperties blockDisplayProperties, Composite client, FormToolkit toolkit)
    {
        Map<String, Integer> columnPositions = new HashMap<String, Integer>();

        final ScrolledForm sc = toolkit.createScrolledForm(client);

        GridData scgd = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
        scgd.grabExcessHorizontalSpace = true;
        scgd.grabExcessVerticalSpace = true;
        sc.setLayoutData(scgd);
        GridLayout gl = new GridLayout();
        gl.marginHeight = gl.marginWidth = 0;
        sc.getBody().setLayout(gl);
        toolkit.adapt(sc);

        sc.getBody().setLayout(new FillLayout());
        Composite tablePanel = sc.getBody();
        EJDevItemGroupDisplayProperties displayProperties = null;
        if (blockDisplayProperties.getMainScreenItemGroupDisplayContainer().getAllItemGroupDisplayProperties().size() > 0)
        {
            displayProperties = blockDisplayProperties.getMainScreenItemGroupDisplayContainer().getAllItemGroupDisplayProperties().iterator().next();
            if (displayProperties.dispayGroupFrame())
            {
                Group group = new Group(tablePanel, SWT.NONE);
                group.setLayout(new FillLayout());
                if (displayProperties.getFrameTitle() != null && displayProperties.getFrameTitle().length() > 0)
                    group.setText(displayProperties.getFrameTitle());
                tablePanel = group;
            }
        }

        Table table = new Table(tablePanel, SWT.SINGLE | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER | SWT.FULL_SELECTION);

        table.setHeaderVisible(true);
        table.setLinesVisible(true);

        TableLayout tableLayout = new TableLayout();

        // There is only one item group for a flow layout

        int itemCount = 0;
        if (displayProperties != null)
            for (EJDevScreenItemDisplayProperties screenItem : displayProperties.getAllItemDisplayProperties())
            {
                if (!screenItem.isSpacerItem())
                {
                    int width = ((EJDevMainScreenItemDisplayProperties) screenItem).getBlockRendererRequiredProperties().getIntProperty(
                            EJRWTMultiRecordBlockDefinitionProperties.DISPLAY_WIDTH_PROPERTY, 0);

                    TableColumn masterColumn = new TableColumn(table, SWT.NONE);
                    masterColumn.setData("SCREEN_ITEM", screenItem);
                    masterColumn.setText(screenItem.getLabel());
                    masterColumn.setWidth(width);
                    String alignment = ((EJDevMainScreenItemDisplayProperties) screenItem).getBlockRendererRequiredProperties().getStringProperty(
                            EJRWTMultiRecordBlockDefinitionProperties.COLUMN_ALIGNMENT);

                    if (EJRWTMultiRecordBlockDefinitionProperties.COLUMN_ALLIGN_RIGHT.equals(alignment))
                    {
                        masterColumn.setAlignment(SWT.RIGHT);
                    }
                    else if (EJRWTMultiRecordBlockDefinitionProperties.COLUMN_ALLIGN_CENTER.equals(alignment))
                    {
                        masterColumn.setAlignment(SWT.CENTER);
                    }
                    ColumnWeightData colData = new ColumnWeightData(5, 50, true);
                    tableLayout.addColumnData(colData);
                    columnPositions.put(screenItem.getReferencedItemName(), itemCount);
                    itemCount++;
                }
            }

        table.setLayout(tableLayout);

        return new EJRWTTableRendererDefinitionControl(blockDisplayProperties, table, columnPositions);
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