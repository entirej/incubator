package org.entirej.custom.renderers;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Control;
import org.entirej.applicationframework.rwt.application.components.menu.EJRWTDefaultMenuBuilder;
import org.entirej.applicationframework.rwt.layout.EJRWTEntireJGridPane;
import org.entirej.applicationframework.rwt.renderer.interfaces.EJRWTAppBlockRenderer;
import org.entirej.framework.core.data.EJDataRecord;
import org.entirej.framework.core.data.controllers.EJEditableBlockController;
import org.entirej.framework.core.enumerations.EJManagedBlockProperty;
import org.entirej.framework.core.enumerations.EJManagedScreenProperty;
import org.entirej.framework.core.interfaces.EJApplicationManager;
import org.entirej.framework.core.interfaces.EJScreenItemController;
import org.entirej.framework.core.renderers.interfaces.EJInsertScreenRenderer;
import org.entirej.framework.core.renderers.interfaces.EJQueryScreenRenderer;
import org.entirej.framework.core.renderers.interfaces.EJUpdateScreenRenderer;

public class MenuRenderer implements EJRWTAppBlockRenderer
{
    private EJApplicationManager _applicationManager;

    @Override
    public void refreshBlockProperty(EJManagedBlockProperty managedBlockPropertyType)
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void refreshBlockRendererProperty(String propertyName)
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void initialiseRenderer(EJEditableBlockController block)
    {
        _applicationManager = block.getFrameworkManager().getApplicationManager();

    }

    @Override
    public void queryExecuted()
    {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean isCurrentRecordDirty()
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void blockCleared()
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void detailBlocksCleared()
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void recordInserted(EJDataRecord record)
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void recordDeleted(int dataBlockRecordNumber)
    {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean hasFocus()
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void setHasFocus(boolean focus)
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void gainFocus()
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void setFocusToItem(EJScreenItemController item)
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void enterInsert(EJDataRecord recordToInsert)
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void enterUpdate(EJDataRecord recordToUpdate)
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void askToDeleteRecord(EJDataRecord recordToDelete, String message)
    {
        // TODO Auto-generated method stub

    }

    @Override
    public EJQueryScreenRenderer getQueryScreenRenderer()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public EJInsertScreenRenderer getInsertScreenRenderer()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public EJUpdateScreenRenderer getUpdateScreenRenderer()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void executingQuery()
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void refreshItemProperty(String itemName, EJManagedScreenProperty managedItemPropertyType, EJDataRecord record)
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void refreshItemRendererProperty(String itemName, String propertyName)
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void synchronize()
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void refreshAfterChange(EJDataRecord record)
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void recordSelected(EJDataRecord record)
    {
        // TODO Auto-generated method stub

    }

    @Override
    public EJDataRecord getFocusedRecord()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int getDisplayedRecordNumber(EJDataRecord record)
    {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int getDisplayedRecordCount()
    {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public EJDataRecord getFirstRecord()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public EJDataRecord getLastRecord()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public EJDataRecord getRecordAfter(EJDataRecord record)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public EJDataRecord getRecordBefore(EJDataRecord record)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public EJDataRecord getRecordAt(int displayedRecordNumber)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void enterQuery(EJDataRecord queryRecord)
    {
        // TODO Auto-generated method stub

    }

    @Override
    public Object getGuiComponent()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void buildGuiComponent(EJRWTEntireJGridPane pane)
    {
        EJRWTDefaultMenuBuilder menuBuilder = new EJRWTDefaultMenuBuilder(_applicationManager, pane);
        Control menuControl = menuBuilder.createTreeComponent("Default");
        menuControl.setLayoutData(createGridData());
    }
    
    private GridData createGridData()
    {
        GridData gd = new GridData();
        gd.grabExcessHorizontalSpace = true;
        gd.grabExcessVerticalSpace = true;
        gd.horizontalAlignment = SWT.FILL;
        gd.verticalAlignment = SWT.FILL;
        
        return gd;
    }

    @Override
    public void setFilter(String filter)
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public String getFilter()
    {
        // TODO Auto-generated method stub
        return null;
    }

}
