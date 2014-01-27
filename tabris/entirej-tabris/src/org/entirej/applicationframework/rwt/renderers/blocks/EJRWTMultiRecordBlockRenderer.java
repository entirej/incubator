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
package org.entirej.applicationframework.rwt.renderers.blocks;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ColumnViewerToolTipSupport;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.rwt.EJ_RWT;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.entirej.applicationframework.rwt.application.EJRWTApplicationManager;
import org.entirej.applicationframework.rwt.application.EJRWTImageRetriever;
import org.entirej.applicationframework.rwt.application.components.actions.EJRWTDeleteAction;
import org.entirej.applicationframework.rwt.application.components.actions.EJRWTInsertAction;
import org.entirej.applicationframework.rwt.application.components.actions.EJRWTQueryAction;
import org.entirej.applicationframework.rwt.application.components.actions.EJRWTUpdateAction;
import org.entirej.applicationframework.rwt.layout.EJRWTEntireJGridPane;
import org.entirej.applicationframework.rwt.renderer.interfaces.EJRWTAppBlockRenderer;
import org.entirej.applicationframework.rwt.renderer.interfaces.EJRWTAppItemRenderer;
import org.entirej.applicationframework.rwt.renderers.blocks.definition.interfaces.EJRWTMultiRecordBlockDefinitionProperties;
import org.entirej.applicationframework.rwt.renderers.blocks.definition.interfaces.EJRWTSingleRecordBlockDefinitionProperties;
import org.entirej.applicationframework.rwt.renderers.blocks.definition.interfaces.EJRWTTreeBlockDefinitionProperties;
import org.entirej.applicationframework.rwt.renderers.screen.EJRWTInsertScreenRenderer;
import org.entirej.applicationframework.rwt.renderers.screen.EJRWTQueryScreenRenderer;
import org.entirej.applicationframework.rwt.renderers.screen.EJRWTUpdateScreenRenderer;
import org.entirej.applicationframework.rwt.table.EJRWTAbstractFilteredTable;
import org.entirej.applicationframework.rwt.table.EJRWTAbstractFilteredTable.FilteredContentProvider;
import org.entirej.applicationframework.rwt.table.EJRWTAbstractTableSorter;
import org.entirej.applicationframework.rwt.table.EJRWTTableAutoResizeAdapter;
import org.entirej.applicationframework.rwt.table.EJRWTTableSortSelectionListener;
import org.entirej.applicationframework.rwt.table.EJRWTTableViewerColumnFactory;
import org.entirej.applicationframework.rwt.utils.EJRWTKeysUtil;
import org.entirej.applicationframework.rwt.utils.EJRWTKeysUtil.KeyInfo;
import org.entirej.framework.core.EJForm;
import org.entirej.framework.core.EJMessage;
import org.entirej.framework.core.data.EJDataRecord;
import org.entirej.framework.core.data.controllers.EJEditableBlockController;
import org.entirej.framework.core.data.controllers.EJQuestion;
import org.entirej.framework.core.enumerations.EJManagedBlockProperty;
import org.entirej.framework.core.enumerations.EJManagedScreenProperty;
import org.entirej.framework.core.enumerations.EJQuestionButton;
import org.entirej.framework.core.enumerations.EJScreenType;
import org.entirej.framework.core.interfaces.EJScreenItemController;
import org.entirej.framework.core.internal.EJInternalEditableBlock;
import org.entirej.framework.core.properties.EJCoreMainScreenItemProperties;
import org.entirej.framework.core.properties.EJCoreProperties;
import org.entirej.framework.core.properties.EJCoreVisualAttributeProperties;
import org.entirej.framework.core.properties.definitions.interfaces.EJFrameworkExtensionProperties;
import org.entirej.framework.core.properties.interfaces.EJBlockProperties;
import org.entirej.framework.core.properties.interfaces.EJItemGroupProperties;
import org.entirej.framework.core.properties.interfaces.EJMainScreenProperties;
import org.entirej.framework.core.properties.interfaces.EJScreenItemProperties;
import org.entirej.framework.core.renderers.EJManagedItemRendererWrapper;
import org.entirej.framework.core.renderers.interfaces.EJInsertScreenRenderer;
import org.entirej.framework.core.renderers.interfaces.EJQueryScreenRenderer;
import org.entirej.framework.core.renderers.interfaces.EJUpdateScreenRenderer;

public class EJRWTMultiRecordBlockRenderer implements EJRWTAppBlockRenderer, KeyListener
{

    private boolean                   _isFocused        = false;
    private EJEditableBlockController _block;
    private EJRWTEntireJGridPane      _mainPane;
    private TableViewer               _tableViewer;
    private EJRWTQueryScreenRenderer  _queryScreenRenderer;
    private EJRWTInsertScreenRenderer _insertScreenRenderer;
    private EJRWTUpdateScreenRenderer _updateScreenRenderer;

    private List<String>              _actionkeys       = new ArrayList<String>();
    private Map<KeyInfo, String>      _actionInfoMap    = new HashMap<EJRWTKeysUtil.KeyInfo, String>();
    private FilteredContentProvider   _filteredContentProvider;
    private List<EJDataRecord>        _tableBaseRecords = new ArrayList<EJDataRecord>();

    protected void clearFilter()
    {
        if (_filteredContentProvider != null)
        {
            _filteredContentProvider.setFilter(null);
        }
    }

    @Override
    public void refreshBlockProperty(EJManagedBlockProperty managedBlockPropertyType)
    {
    }

    @Override
    public void refreshBlockRendererProperty(String propertyName)
    {
    }

    @Override
    public void executingQuery()
    {
        // no impl
    }

    @Override
    public void refreshItemProperty(String itemName, EJManagedScreenProperty managedItemPropertyType, EJDataRecord record)
    {
        if (EJManagedScreenProperty.ITEM_INSTANCE_VISUAL_ATTRIBUTE.equals(managedItemPropertyType))
        {
            EJScreenItemController item = _block.getScreenItem(EJScreenType.MAIN, itemName);
            if (item != null)
            {
                if (record == null || _tableViewer == null)
                {
                    return;
                }
                if (_tableViewer != null && !_tableViewer.getTable().isDisposed())
                {
                    _tableViewer.refresh(record);
                }
            }
        }
        else if (EJManagedScreenProperty.ITEM_INSTANCE_HINT_TEXT.equals(managedItemPropertyType))
        {
            EJScreenItemController item = _block.getScreenItem(EJScreenType.MAIN, itemName);
            if (item != null)
            {
                if (record == null)
                {
                    return;
                }
            }
        }
        else if (EJManagedScreenProperty.SCREEN_ITEM_VISUAL_ATTRIBUTE.equals(managedItemPropertyType))
        {
            EJScreenItemController item = _block.getScreenItem(EJScreenType.MAIN, itemName);
            if (item != null)
            {
                item.getManagedItemRenderer().getUnmanagedRenderer().setVisualAttribute(item.getProperties().getVisualAttributeProperties());
                if (_tableViewer != null && !_tableViewer.getTable().isDisposed())
                {
                    _tableViewer.setInput(new Object());
                }
            }
        }
    }

    @Override
    public void refreshItemRendererProperty(String itemName, String propertyName)
    {
    }

    @Override
    public Composite getGuiComponent()
    {
        return _mainPane;
    }

    @Override
    public EJQueryScreenRenderer getQueryScreenRenderer()
    {
        return _queryScreenRenderer;
    }

    @Override
    public EJInsertScreenRenderer getInsertScreenRenderer()
    {
        return _insertScreenRenderer;
    }

    @Override
    public EJUpdateScreenRenderer getUpdateScreenRenderer()
    {
        return _updateScreenRenderer;
    }

    @Override
    public void initialiseRenderer(EJEditableBlockController block)
    {
        _block = block;
        _queryScreenRenderer = new EJRWTQueryScreenRenderer();
        _insertScreenRenderer = new EJRWTInsertScreenRenderer();
        _updateScreenRenderer = new EJRWTUpdateScreenRenderer();
    }

    @Override
    public void blockCleared()
    {
        if (_tableViewer != null && !_tableViewer.getTable().isDisposed())
        {
            clearFilter();
            _tableViewer.setInput(new Object());
        }
        notifyStatus();
    }

    @Override
    public void synchronize()
    {
    }

    protected void notifyStatus()
    {
        //TODO
    }

    @Override
    public void detailBlocksCleared()
    {
    }

    @Override
    public boolean hasFocus()
    {
        return _isFocused;
    }

    public boolean isInsertMode()
    {
        return false;
    }

    public boolean isUpdateMode()
    {
        return false;
    }

    @Override
    public boolean isCurrentRecordDirty()
    {
        return false;
    }

    @Override
    public void enterInsert(EJDataRecord record)
    {
        if (_block.getInsertScreenRenderer() == null)
        {
            EJMessage message = new EJMessage("Please define an Insert Screen Renderer for this form before an insert operation can be performed.");
            _block.getForm().getMessenger().handleMessage(message);
        }
        else
        {
            _block.getInsertScreenRenderer().open(record);
        }
    }

    @Override
    public void enterQuery(EJDataRecord queryRecord)
    {
        if (_block.getQueryScreenRenderer() == null)
        {
            EJMessage message = new EJMessage("Please define a Query Screen Renderer for this form before a query operation can be performed.");
            _block.getForm().getMessenger().handleMessage(message);
        }
        else
        {
            _block.getQueryScreenRenderer().open(queryRecord);
        }
    }

    @Override
    public void enterUpdate(EJDataRecord recordToUpdate)
    {
        if (_block.getUpdateScreenRenderer() == null)
        {
            EJMessage message = new EJMessage("Please define an Update Screen Renderer for this form before an update operation can be performed.");
            _block.getForm().getMessenger().handleMessage(message);
        }
        else
        {
            _block.getUpdateScreenRenderer().open(recordToUpdate);
        }
    }

    @Override
    public void queryExecuted()
    {
        if (_tableViewer != null && !_tableViewer.getTable().isDisposed())
        {
            clearFilter();
            _tableViewer.setInput(new Object());
        }
        selectRow(0);

    }

    public void pageRetrieved()
    {
        if (_tableViewer != null && !_tableViewer.getTable().isDisposed())
        {
            clearFilter();
            _tableViewer.setInput(new Object());
        }
        selectRow(0);
    }

    @Override
    public void recordDeleted(int dataBlockRecordNumber)
    {
        EJDataRecord recordAt = getRecordAt(dataBlockRecordNumber > 1 ? dataBlockRecordNumber - 2 : 0);

        if (recordAt == null)
        {
            recordAt = getLastRecord();
        }
        if (_tableViewer != null && !_tableViewer.getTable().isDisposed())
        {
            clearFilter();
            _tableViewer.setInput(new Object());
        }
        if (recordAt != null)
        {
            recordSelected(recordAt);
        }
    }

    @Override
    public void recordInserted(EJDataRecord record)
    {
        if (_tableViewer != null && !_tableViewer.getTable().isDisposed())
        {
            clearFilter();
            _tableViewer.setInput(new Object());
        }
        recordSelected(record);
    }

    @Override
    public void refreshAfterChange(EJDataRecord record)
    {
        if (_tableViewer != null && !_tableViewer.getTable().isDisposed())
        {
            _tableViewer.refresh(record);
        }
    }

    @Override
    public void recordSelected(EJDataRecord record)
    {
        if (_tableViewer != null && !_tableViewer.getTable().isDisposed())
        {
            _tableViewer.setSelection(record != null ? new StructuredSelection(record) : new StructuredSelection(), true);
        }
        notifyStatus();
    }

    @Override
    public void setHasFocus(boolean focus)
    {
        _isFocused = focus;
        if (_isFocused)
        {
            showFocusedBorder(true);
            _block.focusGained();
        }
        else
        {
            showFocusedBorder(false);
            _block.focusLost();
        }
        notifyStatus();
    }

    /**
     * Enables a blue border around this controller. This will indicate that the
     * container held by this controller has cursor focus.
     * 
     * @param pFocused
     *            If <code>true</code> is passed then the border will be
     *            displayed, if <code>false</code> is passed then no border will
     *            be shown.
     */
    protected void showFocusedBorder(boolean focused)
    {
    }

    @Override
    public void setFocusToItem(EJScreenItemController item)
    {
        if (_tableViewer != null && !_tableViewer.getTable().isDisposed())
        {
            _tableViewer.getTable().forceFocus();
        }
    }

    @Override
    public void gainFocus()
    {
        if (_tableViewer != null && !_tableViewer.getTable().isDisposed())
        {
            _tableViewer.getTable().forceFocus();
        }
        setHasFocus(true);

    }

    @Override
    public EJDataRecord getFocusedRecord()
    {
        EJDataRecord _focusedRecord = null;

        if (_tableViewer != null && !_tableViewer.getTable().isDisposed())
        {
            ISelection selection = _tableViewer.getSelection();
            if (selection instanceof IStructuredSelection)
            {
                IStructuredSelection structuredSelection = (IStructuredSelection) selection;
                Object firstElement = structuredSelection.getFirstElement();
                if (firstElement instanceof EJDataRecord)
                {
                    _focusedRecord = (EJDataRecord) firstElement;
                }
            }
        }
        return _focusedRecord;
    }

    @Override
    public int getDisplayedRecordNumber(EJDataRecord record)
    {
        if (record == null)
        {
            return -1;
        }

        return _tableBaseRecords.indexOf(record);
    }

    @Override
    public int getDisplayedRecordCount()
    {
        // Indicates the number of records that are available within the View.
        // the number depends on the filters set on the table!
        return _tableBaseRecords.size();
    }

    @Override
    public EJDataRecord getFirstRecord()
    {
        return getRecordAt(0);
    }

    @Override
    public EJDataRecord getLastRecord()
    {
        return getRecordAt(getDisplayedRecordCount() - 1);
    }

    @Override
    public EJDataRecord getRecordAt(int displayedRecordNumber)
    {
        if (displayedRecordNumber > -1 && displayedRecordNumber < getDisplayedRecordCount())
        {
            return _tableBaseRecords.get(displayedRecordNumber);
        }
        return null;
    }

    @Override
    public EJDataRecord getRecordAfter(EJDataRecord record)
    {
        int viewIndex = getDisplayedRecordNumber(record);
        if (-1 < viewIndex)
        {
            return getRecordAt(viewIndex + 1);
        }
        return null;
    }

    @Override
    public EJDataRecord getRecordBefore(EJDataRecord record)
    {
        int viewIndex = getDisplayedRecordNumber(record);
        if (-1 < viewIndex)
        {
            return getRecordAt(viewIndex - 1);
        }
        return null;
    }

    public void selectRow(int selectedRow)
    {
        if (_tableViewer != null && !_tableViewer.getTable().isDisposed() && getDisplayedRecordCount() > selectedRow)
        {
            _tableViewer.setSelection(new StructuredSelection(getRecordAt(selectedRow)), true);
        }
        notifyStatus();
    }

    public final EJInternalEditableBlock getBlock()
    {
        return _block.getBlock();
    }

    @Override
    public void buildGuiComponent(EJRWTEntireJGridPane blockCanvas)
    {
        EJFrameworkExtensionProperties appProp = EJCoreProperties.getInstance().getApplicationDefinedProperties();
        if (appProp != null)
        {
            EJFrameworkExtensionProperties propertyGroup = appProp.getPropertyGroup(EJRWTSingleRecordBlockDefinitionProperties.ACTION_GROUP);
            if (propertyGroup != null)
            {

                addActionKeyinfo(propertyGroup.getStringProperty(EJRWTSingleRecordBlockDefinitionProperties.ACTION_QUERY_KEY),
                        EJRWTSingleRecordBlockDefinitionProperties.ACTION_QUERY_KEY);
                addActionKeyinfo(propertyGroup.getStringProperty(EJRWTSingleRecordBlockDefinitionProperties.ACTION_INSERT_KEY),
                        EJRWTSingleRecordBlockDefinitionProperties.ACTION_INSERT_KEY);
                addActionKeyinfo(propertyGroup.getStringProperty(EJRWTSingleRecordBlockDefinitionProperties.ACTION_UPDATE_KEY),
                        EJRWTSingleRecordBlockDefinitionProperties.ACTION_UPDATE_KEY);
                addActionKeyinfo(propertyGroup.getStringProperty(EJRWTSingleRecordBlockDefinitionProperties.ACTION_DELETE_KEY),
                        EJRWTSingleRecordBlockDefinitionProperties.ACTION_DELETE_KEY);
                addActionKeyinfo(propertyGroup.getStringProperty(EJRWTSingleRecordBlockDefinitionProperties.ACTION_REFRESH_KEY),
                        EJRWTSingleRecordBlockDefinitionProperties.ACTION_REFRESH_KEY);
            }
        }
        EJBlockProperties blockProperties = _block.getProperties();
        EJMainScreenProperties mainScreenProperties = blockProperties.getMainScreenProperties();

        GridData gridData = new GridData(GridData.FILL_BOTH);
        gridData.widthHint = mainScreenProperties.getWidth();
        gridData.heightHint = mainScreenProperties.getHeight();

        gridData.horizontalSpan = mainScreenProperties.getHorizontalSpan();
        gridData.verticalSpan = mainScreenProperties.getVerticalSpan();
        gridData.grabExcessHorizontalSpace = mainScreenProperties.canExpandHorizontally();
        gridData.grabExcessVerticalSpace = mainScreenProperties.canExpandVertically();

        if (gridData.grabExcessHorizontalSpace)
        {
            gridData.minimumWidth = mainScreenProperties.getWidth();
        }
        if (gridData.grabExcessVerticalSpace)
        {
            gridData.minimumHeight = mainScreenProperties.getHeight();
        }
        EJFrameworkExtensionProperties rendererProp = blockProperties.getBlockRendererProperties();
        blockCanvas.setLayoutData(gridData);

        EJFrameworkExtensionProperties sectionProperties = null;
        if (rendererProp != null)
        {
            sectionProperties = rendererProp.getPropertyGroup(EJRWTSingleRecordBlockDefinitionProperties.ITEM_GROUP_TITLE_BAR);
        }

        
            if (mainScreenProperties.getDisplayFrame())
            {
                Group group = new Group(blockCanvas, SWT.NONE);
                group.setLayout(new FillLayout());
                group.setLayoutData(gridData);
                hookKeyListener(group);
                String frameTitle = mainScreenProperties.getFrameTitle();
                if (frameTitle != null && frameTitle.length() > 0)
                {
                    group.setText(frameTitle);
                }
                _mainPane = new EJRWTEntireJGridPane(group, 1);
            }
            else
            {
                _mainPane = new EJRWTEntireJGridPane(blockCanvas, 1);
                _mainPane.setLayoutData(gridData);
                _mainPane.cleanLayoutHorizontal();
            }
        

        hookKeyListener(_mainPane);
        int style = SWT.VIRTUAL;

        if (!rendererProp.getBooleanProperty(EJRWTMultiRecordBlockDefinitionProperties.HIDE_TABLE_BORDER, false))
        {
            style = style | SWT.BORDER;
        }
        if (rendererProp.getBooleanProperty(EJRWTMultiRecordBlockDefinitionProperties.ROW_SELECTION_PROPERTY, true))
        {
            style = style | SWT.FULL_SELECTION;
        }
        else
        {
            style = style | SWT.HIDE_SELECTION;
        }
        Collection<EJItemGroupProperties> allItemGroupProperties = _block.getProperties().getScreenItemGroupContainer(EJScreenType.MAIN)
                .getAllItemGroupProperties();
        final Table table;
        final boolean hideSelection = (style & SWT.HIDE_SELECTION) != 0;
        final EJRWTAbstractFilteredTable filterTree;
        if (rendererProp.getBooleanProperty(EJRWTTreeBlockDefinitionProperties.FILTER, false))
        {
            if (allItemGroupProperties.size() > 0)
            {
                EJItemGroupProperties displayProperties = allItemGroupProperties.iterator().next();
                if (displayProperties.dispayGroupFrame())
                {
                    Group group = new Group(_mainPane, SWT.NONE);
                    group.setLayout(new FillLayout());
                    if (displayProperties.getFrameTitle() != null && displayProperties.getFrameTitle().length() > 0)
                    {
                        group.setText(displayProperties.getFrameTitle());
                    }
                    group.setLayoutData(new GridData(GridData.FILL_BOTH | GridData.GRAB_HORIZONTAL | GridData.GRAB_VERTICAL));

                    filterTree = new EJRWTAbstractFilteredTable(group, style)
                    {
                        @Override
                        public void filter(String filter)
                        {
                            if (_filteredContentProvider != null
                                    && (filter == null && _filteredContentProvider.getFilter() != null || !filter.equals(_filteredContentProvider.getFilter())))
                            {
                                _filteredContentProvider.setFilter(filter);
                                getViewer().setInput(filter);
                                if(getFocusedRecord()==null)
                                {
                                    selectRow(0);
                                }
                                notifyStatus();
                            }
                        }

                        @Override
                        protected TableViewer doCreateTableViewer(Composite parent, int style)
                        {
                            return _tableViewer = new TableViewer(parent)
                            {
                                private static final long serialVersionUID = 5803610958722645987L;

                                @Override
                                public void setSelection(ISelection selection)
                                {
                                    if (hideSelection)
                                    {
                                        selection = new StructuredSelection();
                                    }
                                    super.setSelection(selection);
                                }

                                @Override
                                public void setSelection(ISelection selection, boolean reveal)
                                {
                                    if (hideSelection)
                                    {
                                        selection = new StructuredSelection();
                                    }
                                    super.setSelection(selection, reveal);
                                }
                            };
                        }
                    };
                }
                else
                {

                    filterTree = new EJRWTAbstractFilteredTable(_mainPane, style)
                    {
                        @Override
                        public void filter(String filter)
                        {
                            if (_filteredContentProvider != null
                                    && (filter == null && _filteredContentProvider.getFilter() != null || !filter.equals(_filteredContentProvider.getFilter())))
                            {
                                _filteredContentProvider.setFilter(filter);
                                getViewer().setInput(filter);
                                if(getFocusedRecord()==null)
                                {
                                    selectRow(0);
                                }
                                notifyStatus();
                            }
                        }

                        @Override
                        protected TableViewer doCreateTableViewer(Composite parent, int style)
                        {
                            return _tableViewer = new TableViewer(parent)
                            {
                                private static final long serialVersionUID = 5803610958722645987L;

                                @Override
                                public void setSelection(ISelection selection)
                                {
                                    if (hideSelection)
                                    {
                                        selection = new StructuredSelection();
                                    }
                                    super.setSelection(selection);
                                }

                                @Override
                                public void setSelection(ISelection selection, boolean reveal)
                                {
                                    if (hideSelection)
                                    {
                                        selection = new StructuredSelection();
                                    }
                                    super.setSelection(selection, reveal);
                                }
                            };
                        }
                    };
                    filterTree.setLayoutData(new GridData(GridData.FILL_BOTH | GridData.GRAB_HORIZONTAL | GridData.GRAB_VERTICAL));
                }
            }
            else
            {
                filterTree = new EJRWTAbstractFilteredTable(_mainPane, style)
                {
                    @Override
                    public void filter(String filter)
                    {
                        if (_filteredContentProvider != null
                                && (filter == null && _filteredContentProvider.getFilter() != null || !filter.equals(_filteredContentProvider.getFilter())))
                        {
                            _filteredContentProvider.setFilter(filter);
                            getViewer().setInput(filter);
                            if(getFocusedRecord()==null)
                            {
                                selectRow(0);
                            }
                            notifyStatus();
                        }
                    }

                    @Override
                    protected TableViewer doCreateTableViewer(Composite parent, int style)
                    {
                        return _tableViewer = new TableViewer(parent)
                        {
                            private static final long serialVersionUID = 5803610958722645987L;

                            @Override
                            public void setSelection(ISelection selection)
                            {
                                if (hideSelection)
                                {
                                    selection = new StructuredSelection();
                                }
                                super.setSelection(selection);
                            }

                            @Override
                            public void setSelection(ISelection selection, boolean reveal)
                            {
                                if (hideSelection)
                                {
                                    selection = new StructuredSelection();
                                }
                                super.setSelection(selection, reveal);
                            }
                        };
                    }
                };

                filterTree.setLayoutData(new GridData(GridData.FILL_BOTH | GridData.GRAB_HORIZONTAL | GridData.GRAB_VERTICAL));
            }
            table = (_tableViewer = filterTree.getViewer()).getTable();
        }
        else
        {
            filterTree = null;
            if (allItemGroupProperties.size() > 0)
            {
                EJItemGroupProperties displayProperties = allItemGroupProperties.iterator().next();
                if (displayProperties.dispayGroupFrame())
                {
                    Group group = new Group(_mainPane, SWT.NONE);
                    group.setLayout(new FillLayout());
                    if (displayProperties.getFrameTitle() != null && displayProperties.getFrameTitle().length() > 0)
                    {
                        group.setText(displayProperties.getFrameTitle());
                    }
                    group.setLayoutData(new GridData(GridData.FILL_BOTH | GridData.GRAB_HORIZONTAL | GridData.GRAB_VERTICAL));
                    table = new Table(group, style);
                }
                else
                {
                    table = new Table(_mainPane, style);
                    table.setLayoutData(new GridData(GridData.FILL_BOTH | GridData.GRAB_HORIZONTAL | GridData.GRAB_VERTICAL));
                }
            }
            else
            {
                table = new Table(_mainPane, style);
                table.setLayoutData(new GridData(GridData.FILL_BOTH | GridData.GRAB_HORIZONTAL | GridData.GRAB_VERTICAL));
            }

            _tableViewer = new TableViewer(table)
            {
                @Override
                public void setSelection(ISelection selection)
                {
                    if (hideSelection)
                    {
                        selection = new StructuredSelection();
                    }
                    super.setSelection(selection);
                }

                @Override
                public void setSelection(ISelection selection, boolean reveal)
                {
                    if (hideSelection)
                    {
                        selection = new StructuredSelection();
                    }
                    super.setSelection(selection, reveal);
                }
            };
        }

        table.setLinesVisible(rendererProp.getBooleanProperty(EJRWTMultiRecordBlockDefinitionProperties.SHOW_VERTICAL_LINES, true));
        table.setHeaderVisible(rendererProp.getBooleanProperty(EJRWTMultiRecordBlockDefinitionProperties.SHOW_HEADING_PROPERTY, true));
        Control[] children = table.getChildren();
        for (Control control : children)
        {
            hookKeyListener(control);
        }
        hookKeyListener(table);

        EJRWTTableViewerColumnFactory factory = new EJRWTTableViewerColumnFactory(_tableViewer);
        ColumnViewerToolTipSupport.enableFor(_tableViewer);

        boolean autoSize = false;
        final List<ColumnLabelProvider> nodeTextProviders = new ArrayList<ColumnLabelProvider>();

        for (EJItemGroupProperties groupProperties : allItemGroupProperties)
        {
            Collection<EJScreenItemProperties> itemProperties = groupProperties.getAllItemProperties();
            autoSize = itemProperties.size() == 1;
            for (EJScreenItemProperties screenItemProperties : itemProperties)
            {
                EJCoreMainScreenItemProperties mainScreenItemProperties = (EJCoreMainScreenItemProperties) screenItemProperties;
                ColumnLabelProvider screenItem = createScreenItem(factory, mainScreenItemProperties);
                if (screenItem != null)
                {
                    nodeTextProviders.add(screenItem);
                }
                if (autoSize)
                {
                    EJFrameworkExtensionProperties itemBl = mainScreenItemProperties.getBlockRendererRequiredProperties();
                    autoSize = itemBl != null && itemBl.getIntProperty(EJRWTMultiRecordBlockDefinitionProperties.DISPLAY_WIDTH_PROPERTY, 0) <= 0;
                }
            }
        }
        if (autoSize)
        {
            EJRWTTableAutoResizeAdapter adapter = new EJRWTTableAutoResizeAdapter(table);
            table.addControlListener(adapter);
            adapter.controlResized(null);
        }

        table.addFocusListener(new FocusListener()
        {
            @Override
            public void focusLost(FocusEvent arg0)
            {
                setHasFocus(false);
            }

            @Override
            public void focusGained(FocusEvent arg0)
            {
                setHasFocus(true);
            }
        });
        _mainPane.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseDown(MouseEvent arg0)
            {
                if (!table.isFocusControl())
                {
                    setHasFocus(true);
                }
            }
        });
        table.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseDown(MouseEvent arg0)
            {
                if (!table.isFocusControl())
                {
                    setHasFocus(true);
                }
            }
        });

        _tableViewer.setContentProvider(_filteredContentProvider = new FilteredContentProvider()
        {
            boolean matchItem(EJDataRecord rec)
            {
                if (filter != null && filter.trim().length() > 0)
                {
                    for (ColumnLabelProvider filterTextProvider : nodeTextProviders)
                    {
                        String text = filterTextProvider.getText(rec);
                        if (text != null && text.toLowerCase().contains(filter.toLowerCase()))
                        {
                            return true;
                        }
                    }
                }
                return false;
            }

            @Override
            public void inputChanged(Viewer arg0, Object arg1, Object arg2)
            {
                _tableBaseRecords.clear();

                if (arg2 != null && arg2.equals(filter) && filter.trim().length() > 0)
                {
                    // filter
                    for (EJDataRecord record : _block.getBlock().getRecords())
                    {
                        if (matchItem(record))
                        {
                            _tableBaseRecords.add(record);
                        }
                    }
                }
                else
                {
                    filter = null;
                    if (filterTree != null)
                    {
                        filterTree.clearText();
                    }
                    _tableBaseRecords.addAll(_block.getBlock().getRecords());
                }
            }

            @Override
            public void dispose()
            {
            }

            @Override
            public Object[] getElements(Object arg0)
            {
                return _tableBaseRecords.toArray();
            }
        });
        _tableViewer.setInput(new Object());
        selectRow(0);

        // add double click action
        final String doubleClickActionCommand = rendererProp.getStringProperty(EJRWTMultiRecordBlockDefinitionProperties.DOUBLE_CLICK_ACTION_COMMAND);
        if (doubleClickActionCommand != null)
        {
            _tableViewer.addDoubleClickListener(new IDoubleClickListener()
            {

                @Override
                public void doubleClick(DoubleClickEvent arg0)
                {
                    _block.executeActionCommand(doubleClickActionCommand, EJScreenType.MAIN);
                }
            });
        }
        _tableViewer.addSelectionChangedListener(new ISelectionChangedListener()
        {

            @Override
            public void selectionChanged(SelectionChangedEvent arg0)
            {

                EJDataRecord focusedRecord = getFocusedRecord();
                if (focusedRecord != null)
                {
                    _block.newRecordInstance(focusedRecord);
                }
                notifyStatus();
            }
        });

    }

    private void addActionKeyinfo(String actionKey, String actionId)
    {
        if (actionKey != null && actionKey.trim().length() > 0)
        {
            try
            {
                KeyInfo keyInfo = EJRWTKeysUtil.toKeyInfo(actionKey);
                _actionInfoMap.put(keyInfo, actionId);
                _actionkeys.add(actionKey);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    public ColumnLabelProvider createScreenItem(EJRWTTableViewerColumnFactory factory, EJCoreMainScreenItemProperties itemProps)
    {
        if (itemProps.isSpacerItem())
        {
            return null;
        }
        EJScreenItemController item = _block.getScreenItem(EJScreenType.MAIN, itemProps.getReferencedItemName());
        EJManagedItemRendererWrapper renderer = item.getManagedItemRenderer();
        if (renderer != null)
        {
            EJFrameworkExtensionProperties blockProperties = itemProps.getBlockRendererRequiredProperties();
            EJRWTAppItemRenderer itemRenderer = (EJRWTAppItemRenderer) renderer.getUnmanagedRenderer();
            ColumnLabelProvider labelProvider = itemRenderer.createColumnLabelProvider(itemProps, item);
            if (labelProvider != null)
            {
                String labelOrientation = blockProperties.getStringProperty(EJRWTMultiRecordBlockDefinitionProperties.COLUMN_ALIGNMENT);

                int displayedWidth = blockProperties.getIntProperty(EJRWTMultiRecordBlockDefinitionProperties.DISPLAY_WIDTH_PROPERTY, 0);

                // if no width define in block properties use item renderer pref
                // width
                if (displayedWidth == 0)
                {
                    if (itemProps.getLabel() != null)
                    {
                        // add offset
                        displayedWidth = itemProps.getLabel().length() + 2;
                    }
                    else
                    {
                        displayedWidth = 5;
                    }
                }

                String visualAttribute = blockProperties.getStringProperty(EJRWTMultiRecordBlockDefinitionProperties.VISUAL_ATTRIBUTE_PROPERTY);

                if (visualAttribute != null)
                {
                    EJCoreVisualAttributeProperties va = EJCoreProperties.getInstance().getVisualAttributesContainer()
                            .getVisualAttributeProperties(visualAttribute);
                    if (va != null)
                    {
                        itemRenderer.setInitialVisualAttribute(va);
                    }
                }

                TableViewerColumn viewerColumn = factory.createColumn(itemProps.getLabel(), displayedWidth, labelProvider, getComponentStyle(labelOrientation));
                TableColumn column = viewerColumn.getColumn();
                column.setData("KEY", itemProps.getReferencedItemName());
                column.setToolTipText(itemProps.getHint());

                column.setMoveable(blockProperties.getBooleanProperty(EJRWTMultiRecordBlockDefinitionProperties.ALLOW_COLUMN_REORDER, true));
                column.setResizable(blockProperties.getBooleanProperty(EJRWTMultiRecordBlockDefinitionProperties.ALLOW_COLUMN_RESIZE, true));
                if (blockProperties.getBooleanProperty(EJRWTMultiRecordBlockDefinitionProperties.ALLOW_ROW_SORTING, true))
                {
                    EJRWTAbstractTableSorter columnSorter = itemRenderer.getColumnSorter(itemProps, item);
                    if (columnSorter != null)
                    {
                        new EJRWTTableSortSelectionListener(_tableViewer, column, columnSorter, SWT.UP, false);
                    }
                }
                // ensure that the width property of the table column is in
                // Characters
                Font font = labelProvider.getFont(new Object());
                if (font == null)
                {
                    font = _tableViewer.getTable().getFont();
                }

                if (font != null)
                {
                    float avgCharWidth = EJRWTImageRetriever.getAvgCharWidth(font);
                    if (avgCharWidth > 0)
                    {
                        // add + 1 padding
                        column.setWidth((int) ((column.getWidth() + 1) * avgCharWidth));
                    }
                }
                return labelProvider;
            }
        }
        return null;
    }

    @Override
    public void keyPressed(KeyEvent arg0)
    {
        // ignore
    }

    @Override
    public void keyReleased(KeyEvent arg0)
    {
        int keyCode = arg0.keyCode;
        KeyInfo keyInfo = EJRWTKeysUtil.toKeyInfo(keyCode, (arg0.stateMask & SWT.SHIFT) != 0, (arg0.stateMask & SWT.CTRL) != 0, (arg0.stateMask & SWT.ALT) != 0);

        String actionID = _actionInfoMap.get(keyInfo);
        if (actionID != null)
        {
            if (EJRWTSingleRecordBlockDefinitionProperties.ACTION_QUERY_KEY.equals(actionID))
            {
                if (EJRWTQueryAction.canExecute(_block))
                {
                    _block.enterQuery();
                    gainFocus();
                }

            }
            else if (EJRWTSingleRecordBlockDefinitionProperties.ACTION_INSERT_KEY.equals(actionID))
            {
                if (EJRWTInsertAction.canExecute(_block))
                {
                    _block.enterInsert(false);
                    gainFocus();
                }
            }
            else if (EJRWTSingleRecordBlockDefinitionProperties.ACTION_UPDATE_KEY.equals(actionID))
            {
                if (EJRWTUpdateAction.canExecute(_block))
                {
                    _block.enterUpdate();
                    gainFocus();
                }
            }
            else if (EJRWTSingleRecordBlockDefinitionProperties.ACTION_DELETE_KEY.equals(actionID))
            {
                if (EJRWTDeleteAction.canExecute(_block))
                {
                    _block.askToDeleteCurrentRecord(null);
                    gainFocus();
                }
            }
            else if (EJRWTSingleRecordBlockDefinitionProperties.ACTION_REFRESH_KEY.equals(actionID))
            {
                _block.getBlock().refreshAfterChange(getFocusedRecord());
                gainFocus();
            }
        }
    }

    private void hookKeyListener(Control control)
    {
        List<String> subActions = new ArrayList<String>(_actionkeys);
        Object data = control.getData(EJ_RWT.ACTIVE_KEYS);

        if (data != null)
        {
            String[] current = (String[]) data;
            for (String action : current)
            {
                if (subActions.contains(action))
                {
                    continue;
                }
                subActions.add(action);
            }
        }
        control.setData(EJ_RWT.ACTIVE_KEYS, subActions.toArray(new String[0]));
        control.addKeyListener(this);
    }

    protected int getComponentStyle(String alignmentProperty)
    {
        if (alignmentProperty != null && alignmentProperty.trim().length() > 0)
        {
            if (alignmentProperty.equals(EJRWTMultiRecordBlockDefinitionProperties.COLUMN_ALLIGN_RIGHT))
            {
                return SWT.RIGHT;
            }
            else if (alignmentProperty.equals(EJRWTMultiRecordBlockDefinitionProperties.COLUMN_ALLIGN_CENTER))
            {
                return SWT.CENTER;
            }
        }
        return SWT.LEFT;
    }

    @Override
    public void askToDeleteRecord(EJDataRecord recordToDelete, String msg)
    {
        if (msg == null)
        {
            msg = "Are you sure you want to delete the current record?";
        }
        EJMessage message = new EJMessage(msg);
        EJQuestion question = new EJQuestion(new EJForm(_block.getForm()), "DELETE_RECORD", "Delete", message, "Yes", "No", recordToDelete);
        _block.getForm().getMessenger().askQuestion(question);
        if (EJQuestionButton.ONE == question.getAnswer())
        {
            _block.getBlock().deleteRecord(recordToDelete);
        }
        _block.setRendererFocus(true);
    }
}