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
package org.entirej.custom.renderers;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.rap.json.JsonObject;
import org.eclipse.rap.rwt.RWT;
import org.eclipse.rwt.EJ_RWT;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.internal.graphics.ImageFactory;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Widget;
import org.entirej.applicationframework.rwt.application.EJRWTImageRetriever;
import org.entirej.applicationframework.rwt.application.launcher.RWTUtils;
import org.entirej.applicationframework.rwt.component.EJRWTHtmlView;
import org.entirej.applicationframework.rwt.layout.EJRWTEntireJGridPane;
import org.entirej.applicationframework.rwt.renderer.interfaces.EJRWTAppBlockRenderer;
import org.entirej.applicationframework.rwt.renderer.interfaces.EJRWTAppItemRenderer;
import org.entirej.applicationframework.rwt.renderers.blocks.definition.interfaces.EJRWTMultiRecordBlockDefinitionProperties;
import org.entirej.applicationframework.rwt.renderers.html.EJRWTHtmlTableBlockRenderer.VACSSServiceHandler;
import org.entirej.applicationframework.rwt.renderers.screen.EJRWTInsertScreenRenderer;
import org.entirej.applicationframework.rwt.renderers.screen.EJRWTQueryScreenRenderer;
import org.entirej.applicationframework.rwt.renderers.screen.EJRWTUpdateScreenRenderer;
import org.entirej.applicationframework.rwt.table.EJRWTAbstractTableSorter;
import org.entirej.applicationframework.rwt.utils.EJRWTKeysUtil;
import org.entirej.applicationframework.rwt.utils.EJRWTKeysUtil.KeyInfo;
import org.entirej.ejinvoice.forms.timeentry.TimeEntry;
import org.entirej.framework.core.EJForm;
import org.entirej.framework.core.EJMessage;
import org.entirej.framework.core.data.EJDataRecord;
import org.entirej.framework.core.data.controllers.EJEditableBlockController;
import org.entirej.framework.core.data.controllers.EJQuestion;
import org.entirej.framework.core.enumerations.EJManagedBlockProperty;
import org.entirej.framework.core.enumerations.EJManagedScreenProperty;
import org.entirej.framework.core.enumerations.EJQuestionButton;
import org.entirej.framework.core.enumerations.EJScreenType;
import org.entirej.framework.core.extensions.properties.EJCoreFrameworkExtensionPropertyList;
import org.entirej.framework.core.interfaces.EJScreenItemController;
import org.entirej.framework.core.properties.EJCoreBlockProperties;
import org.entirej.framework.core.properties.EJCoreMainScreenItemProperties;
import org.entirej.framework.core.properties.EJCoreVisualAttributeProperties;
import org.entirej.framework.core.properties.definitions.interfaces.EJFrameworkExtensionProperties;
import org.entirej.framework.core.properties.definitions.interfaces.EJFrameworkExtensionPropertyListEntry;
import org.entirej.framework.core.properties.interfaces.EJBlockProperties;
import org.entirej.framework.core.properties.interfaces.EJItemGroupProperties;
import org.entirej.framework.core.properties.interfaces.EJMainScreenProperties;
import org.entirej.framework.core.properties.interfaces.EJScreenItemProperties;
import org.entirej.framework.core.renderers.EJManagedItemRendererWrapper;
import org.entirej.framework.core.renderers.interfaces.EJInsertScreenRenderer;
import org.entirej.framework.core.renderers.interfaces.EJQueryScreenRenderer;
import org.entirej.framework.core.renderers.interfaces.EJUpdateScreenRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WorkWeekBlockRenderer implements EJRWTAppBlockRenderer, KeyListener
{
    private Logger                                LOGGER                     = LoggerFactory.getLogger(this.getClass());

    private static final String                   PROPERTY_ALIGNMENT         = "ALIGNMENT";
    private static final String                   PROPERTY_ALIGNMENT_CHAR    = "CHAR";
    private static final String                   PROPERTY_ALIGNMENT_CENTER  = "CENTER";
    private static final String                   PROPERTY_ALIGNMENT_RIGHT   = "RIGHT";
    private static final String                   PROPERTY_ALIGNMENT_LEFT    = "LEFT";

    public static final String                    CELL_ACTION_COMMAND        = "ACTION_COMMAND";
    private static final String                   PROPERTY_ALIGNMENT_JUSTIFY = "JUSTIFY";
    private static final String                   PROPERTY_CASE              = "CASE";
    private static final String                   PROPERTY_CASE_CAPITALIZE   = "CAPITALIZE";
    private static final String                   PROPERTY_CASE_UPPER        = "UPPER";
    private static final String                   PROPERTY_CASE_LOWER        = "LOWER";

    public static final String                    CELL_SPACING_PROPERTY      = "CELL_SPACING";
    public static final String                    CELL_PADDING_PROPERTY      = "CELL_PADDING";

    public static final String                    DISPLAY_WIDTH_PROPERTY     = "DISPLAY_WIDTH";
    public static final String                    ACTIONS                    = "ACTIONS";
    public static final String                    ACTION_ID                  = "ACTION_ID";
    public static final String                    ACTION_KEY                 = "ACTION_KEY";

    public static final String                    HEADER_VA                  = "HEADER_VA";
    public static final String                    ROW_ODD_VA                 = "ROW_ODD_VA";
    public static final String                    ROW_EVEN_VA                = "ROW_EVEN_VA";

    private EJEditableBlockController             _block;
    private boolean                               _isFocused                 = false;
    private Composite                             header;
    private ScrolledComposite                     scrollComposite;
    private EJRWTHtmlView                         _browser;
    private List<EJCoreMainScreenItemProperties>  _items                     = new ArrayList<EJCoreMainScreenItemProperties>();
    private Map<String, ColumnLabelProvider>      _itemLabelProviders        = new HashMap<String, ColumnLabelProvider>();

    private Map<String, EJRWTAbstractTableSorter> _itemSortProviders         = new HashMap<String, EJRWTAbstractTableSorter>();

    private String                                _headerTag                 = null;
    private EJDataRecord                          currentRec;

    private EJRWTQueryScreenRenderer              _queryScreenRenderer;
    private EJRWTInsertScreenRenderer             _insertScreenRenderer;
    private EJRWTUpdateScreenRenderer             _updateScreenRenderer;

    private List<String>                          _actionkeys                = new ArrayList<String>();
    private Map<KeyInfo, String>                  _actionInfoMap             = new HashMap<EJRWTKeysUtil.KeyInfo, String>();

    private Calendar                              weekCalendar               = Calendar.getInstance();
    private static DateFormat                      DAY_FORMAT                      = new SimpleDateFormat("dd.M");
    private static DateFormat                      DATE_FORMAT                     = new SimpleDateFormat("dd.MM.yyyy");

    // workview config
    private Link[]                                workweeks                  = new Link[0];
    private int                                   activeWeeksSlot            = 0;
    private int                                   activeWeek                 = 1;

    private Label                                 wkInfo;
    private Label                                 hoursSumInfo;

    public void askToDeleteRecord(EJDataRecord recordToDelete, String msg)
    {
        if (msg == null)
        {
            msg = "Are you sure you want to delete the current record?";
        }
        EJMessage message = new EJMessage(msg);
        EJQuestion question = new EJQuestion(new EJForm(_block.getForm()), "DELETE_RECORD", "Delete", message, "Yes", "No");
        _block.getForm().getMessenger().askQuestion(question);
        if (EJQuestionButton.ONE == (question.getAnswer()))
        {
            _block.getBlock().deleteRecord(recordToDelete);
        }
        _block.setRendererFocus(true);

    }

    @Override
    public void blockCleared()
    {
        createHTML();

    }

    public void executingQuery()
    {
    }

    @Override
    public void detailBlocksCleared()
    {
        // no impl

    }

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
    public void gainFocus()
    {
        setHasFocus(true);

    }

    @Override
    public boolean hasFocus()
    {
        return _isFocused;

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
    public boolean isCurrentRecordDirty()
    {

        return false;
    }

    @Override
    public void queryExecuted()
    {
        currentRec = null;

        if (_block.getRecords().size() == 0)
        {
            if (hoursSumInfo != null && !hoursSumInfo.isDisposed())
            {
                hoursSumInfo.setText("00:00");
            }  
        }
        else
        {
            weekCalendar.setFirstDayOfWeek(Calendar.MONDAY);
            EJDataRecord record = _block.getRecord(0);
            if (record != null && record.getServicePojo() instanceof TimeEntry)
            {
                TimeEntry entry = (TimeEntry) record.getServicePojo();
                weekCalendar.setTime(entry.getWorkDate());
                setActiveWeek(weekCalendar.get(Calendar.WEEK_OF_YEAR));
            }

        }

        createHTML();

    }

    @Override
    public void recordDeleted(int arg0)
    {

        createHTML();

    }

    @Override
    public void recordInserted(EJDataRecord arg0)
    {

        createHTML();

    }

    @Override
    public void refreshBlockProperty(EJManagedBlockProperty arg0)
    {
        // no impl
    }

    @Override
    public void refreshBlockRendererProperty(String arg0)
    {
        // no impl
    }

    @Override
    public void setFocusToItem(EJScreenItemController arg0)
    {
        setHasFocus(true);
    }

    @Override
    public void setHasFocus(boolean focus)
    {
        _isFocused = focus;

        if (_isFocused)
        {
            _block.focusGained();
            // showFocusedBorder(true);
        }
        else
        {
            _block.focusLost();
            // showFocusedBorder(false);
        }

    }

    @Override
    public int getDisplayedRecordNumber(EJDataRecord record)
    {
        return _block.getDataBlock().getRecordNumber(record);
    }

    @Override
    public int getDisplayedRecordCount()
    {
        return _block.getDataBlock().getBlockRecordCount();
    }

    @Override
    public EJDataRecord getRecordAt(int displayedRecordNumber)
    {
        if (displayedRecordNumber > -1 && displayedRecordNumber < getDisplayedRecordCount())
        {

            return _block.getRecord(displayedRecordNumber);
        }
        return null;
    }

    @Override
    public EJDataRecord getRecordAfter(EJDataRecord record)
    {
        return _block.getDataBlock().getRecordAfter(record);
    }

    @Override
    public EJDataRecord getRecordBefore(EJDataRecord record)
    {
        return _block.getDataBlock().getRecordBefore(record);
    }

    @Override
    public EJDataRecord getFirstRecord()
    {
        return _block.getDataBlock().getRecord(0);
    }

    @Override
    public EJDataRecord getLastRecord()
    {
        return _block.getDataBlock().getRecord(_block.getBlockRecordCount() - 1);
    }

    @Override
    public void recordSelected(EJDataRecord arg0)
    {
        currentRec = arg0;

    }

    @Override
    public EJDataRecord getFocusedRecord()
    {
        return currentRec != null ? currentRec : getFirstRecord();
    }

    @Override
    public void refreshAfterChange(EJDataRecord arg0)
    {
        
        if (hoursSumInfo != null && !hoursSumInfo.isDisposed())
        {
            hoursSumInfo.setText(getTotalHours(_block.getRecords()));
        }
        createHTML();

    }

    @Override
    public void refreshItemProperty(String itemName, EJManagedScreenProperty managedItemPropertyType, EJDataRecord record)
    {
        if (EJManagedScreenProperty.ITEM_INSTANCE_VISUAL_ATTRIBUTE.equals(managedItemPropertyType))
        {
            EJScreenItemController item = _block.getScreenItem(EJScreenType.MAIN, itemName);
            if (item != null)
            {
                createHTML();
            }
        }
        else if (EJManagedScreenProperty.SCREEN_ITEM_VISUAL_ATTRIBUTE.equals(managedItemPropertyType))
        {
            EJScreenItemController item = _block.getScreenItem(EJScreenType.MAIN, itemName);
            if (item != null)
            {
                item.getManagedItemRenderer().setVisualAttribute(item.getProperties().getVisualAttributeProperties());

                createHTML();
            }
        }

    }

    @Override
    public void refreshItemRendererProperty(String arg0, String arg1)
    {
        // no impl

    }

    @Override
    public void synchronize()
    {
        // no impl

    }

    @Override
    public Object getGuiComponent()
    {
        return header;
    }

    @Override
    public void buildGuiComponent(EJRWTEntireJGridPane blockCanvas)
    {
        if (_browser != null && !_browser.isDisposed())
        {
            _browser.dispose();
        }

        EJBlockProperties blockProperties = _block.getProperties();
        EJMainScreenProperties mainScreenProperties = blockProperties.getMainScreenProperties();

        EJFrameworkExtensionProperties blockRendererProperties = blockProperties.getBlockRendererProperties();
        boolean addHeader = true;
        if (blockRendererProperties != null)
        {
            addHeader = blockRendererProperties.getBooleanProperty(EJRWTMultiRecordBlockDefinitionProperties.SHOW_HEADING_PROPERTY, true);
            EJCoreFrameworkExtensionPropertyList propertyList = blockRendererProperties.getPropertyList(ACTIONS);

            if (propertyList != null)
            {
                List<EJFrameworkExtensionPropertyListEntry> allListEntries = propertyList.getAllListEntries();
                for (EJFrameworkExtensionPropertyListEntry entry : allListEntries)
                {
                    String actionID = entry.getProperty(ACTION_ID);
                    String actionkey = entry.getProperty(ACTION_KEY);
                    if (actionID != null && actionkey != null && actionID.trim().length() > 0 && actionkey.trim().length() > 0)
                    {
                        addActionKeyinfo(actionkey, actionID);
                    }
                }
            }

        }

        final GridData gridData = new GridData(GridData.FILL_BOTH);
        gridData.widthHint = mainScreenProperties.getWidth();
        gridData.heightHint = mainScreenProperties.getHeight();

        gridData.horizontalSpan = mainScreenProperties.getHorizontalSpan();
        gridData.verticalSpan = mainScreenProperties.getVerticalSpan();
        gridData.grabExcessHorizontalSpace = mainScreenProperties.canExpandHorizontally();
        gridData.grabExcessVerticalSpace = mainScreenProperties.canExpandVertically();

        // if (gridData.grabExcessHorizontalSpace)
        // gridData.minimumHeight = mainScreenProperties.getHeight();
        // if (gridData.grabExcessVerticalSpace)
        // gridData.minimumWidth = mainScreenProperties.getHeight();
        blockCanvas.setLayoutData(gridData);

        header = new Composite(blockCanvas, SWT.NONE);

        header.setLayoutData(gridData);
        GridLayout headerLayout = new GridLayout(22, false);
        header.setLayout(headerLayout);

        initActiveWeek();
        createHeaderUI();

        scrollComposite = new ScrolledComposite(header, SWT.V_SCROLL | SWT.H_SCROLL);
        GridData data = new GridData(GridData.FILL_BOTH | GridData.GRAB_HORIZONTAL | GridData.GRAB_VERTICAL);
        data.horizontalSpan = headerLayout.numColumns;
        scrollComposite.setLayoutData(data);
        scrollComposite.setExpandHorizontal(true);
        scrollComposite.setExpandVertical(true);
        scrollComposite.setMinSize(mainScreenProperties.getWidth(), mainScreenProperties.getHeight());
        if (mainScreenProperties.getDisplayFrame())
        {
            String frameTitle = mainScreenProperties.getFrameTitle();
            if (frameTitle != null && frameTitle.length() > 0)
            {
                Group group = new Group(scrollComposite, SWT.NONE);
                hookKeyListener(group);
                scrollComposite.setContent(group);
                group.setLayout(new FillLayout());

                if (frameTitle != null && frameTitle.length() > 0)
                {
                    group.setText(frameTitle);
                }
                _browser = new EJRWTHtmlView(group, SWT.NONE, true)
                {
                    private static final long serialVersionUID = 1L;

                    @Override
                    public void action(String method, JsonObject parameters)
                    {
                        if ("eaction".equals(method))
                        {
                            final Object arg1 = parameters.get("0").asString();
                            Object arg2 = parameters.get("1").asString();
                            if (arg1 instanceof String)
                            {
                                if (arg2 instanceof String)
                                {
                                    currentRec = getRecordAt(Integer.valueOf((String) arg2));
                                    if (currentRec != null)
                                        _block.newRecordInstance(currentRec);
                                }
                                Display.getDefault().asyncExec(new Runnable()
                                {

                                    @Override
                                    public void run()
                                    {
                                        _block.executeActionCommand((String) arg1, EJScreenType.MAIN);
                                    }
                                });

                            }
                        }

                    }
                };
            }
            else
            {
                _browser = new EJRWTHtmlView(scrollComposite, SWT.BORDER, true)
                {

                    private static final long serialVersionUID = 1L;

                    @Override
                    public void action(String method, JsonObject parameters)
                    {
                        if ("eaction".equals(method))
                        {
                            final Object arg1 = parameters.get("0").asString();
                            Object arg2 = parameters.get("1").asString();
                            if (arg1 instanceof String)
                            {
                                if (arg2 instanceof String)
                                {
                                    currentRec = getRecordAt(Integer.valueOf((String) arg2));
                                    if (currentRec != null)
                                        _block.newRecordInstance(currentRec);
                                }
                                Display.getDefault().asyncExec(new Runnable()
                                {

                                    @Override
                                    public void run()
                                    {
                                        _block.executeActionCommand((String) arg1, EJScreenType.MAIN);
                                    }
                                });

                            }
                        }

                    }
                };
                scrollComposite.setContent(_browser);
            }

        }
        else
        {
            _browser = new EJRWTHtmlView(scrollComposite, SWT.NONE, true)
            {
                private static final long serialVersionUID = 1L;

                @Override
                public void action(String method, JsonObject parameters)
                {

                    if ("eaction".equals(method))
                    {
                        final Object arg1 = parameters.get("0").asString();
                        Object arg2 = parameters.get("1").asString();
                        if (arg1 instanceof String)
                        {
                            if (arg2 instanceof String)
                            {
                                currentRec = getRecordAt(Integer.valueOf((String) arg2));
                                if (currentRec != null)
                                    _block.newRecordInstance(currentRec);
                            }
                            Display.getDefault().asyncExec(new Runnable()
                            {

                                @Override
                                public void run()
                                {
                                    _block.executeActionCommand((String) arg1, EJScreenType.MAIN);
                                }
                            });

                        }
                    }

                }
            };
            scrollComposite.setContent(_browser);
            hookKeyListener(scrollComposite);
        }

        _browser.addFocusListener(new FocusListener()
        {

            private static final long serialVersionUID = 1L;

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
        _browser.addMouseListener(new MouseAdapter()
        {

            private static final long serialVersionUID = 1L;

            @Override
            public void mouseDown(MouseEvent arg0)
            {
                setHasFocus(true);

            }

        });

        if (_items.isEmpty())
        {
            Collection<EJItemGroupProperties> allItemGroupProperties = _block.getProperties().getScreenItemGroupContainer(EJScreenType.MAIN).getAllItemGroupProperties();

            int cellSpacing = blockProperties.getBlockRendererProperties().getIntProperty(CELL_SPACING_PROPERTY, 0);
            int cellPadding = blockProperties.getBlockRendererProperties().getIntProperty(CELL_PADDING_PROPERTY, 0);
            String paddingStyle = "";
            String cellStyleTOP = "";
            String cellStyleLEFT = "";
            if (cellPadding > 0)
            {
                String str = String.valueOf(cellPadding);
                paddingStyle = String.format("padding: %spx %spx %spx %spx; ", str, str, str, str);
            }
            if (cellSpacing > 0)
            {
                cellStyleTOP = String.format("border-top: %dpx  solid  #9e9e9e; ", cellSpacing);
                cellStyleLEFT = String.format(" border-left: %dpx  solid  #9e9e9e;", cellSpacing);
            }

            StringBuilder header = new StringBuilder();
            {
                if (addHeader)
                {

                    String styleClass = "default_all";

                    header.append("<th ");

                    String alignment = "center";

                    String valueVA = blockProperties.getBlockRendererProperties().getStringProperty(HEADER_VA);
                    if (valueVA != null && valueVA.length() > 0)
                    {
                        styleClass = valueVA;

                    }
                    header.append(String.format(" class=\"%s\" ", styleClass));
                    if (alignment != null)
                    {
                        header.append(String.format(" align=\'%s\'", alignment));
                    }
                    if (paddingStyle != null)
                    {
                        header.append(String.format(" style=\'%s\'", paddingStyle + cellStyleTOP + cellStyleLEFT));
                    }
                    header.append("> ");

                    header.append("Day");
                    header.append("</th>");
                }
            }
            for (EJItemGroupProperties groupProperties : allItemGroupProperties)
            {
                Collection<EJScreenItemProperties> itemProperties = groupProperties.getAllItemProperties();
                for (EJScreenItemProperties screenItemProperties : itemProperties)
                {
                    EJCoreMainScreenItemProperties itemProps = (EJCoreMainScreenItemProperties) screenItemProperties;

                    EJScreenItemController item = _block.getScreenItem(EJScreenType.MAIN, itemProps.getReferencedItemName());
                    EJManagedItemRendererWrapper renderer = item.getManagedItemRenderer();
                    if (renderer != null)
                    {
                        EJRWTAppItemRenderer itemRenderer = (EJRWTAppItemRenderer) renderer.getUnmanagedRenderer();

                        ColumnLabelProvider labelProvider = itemRenderer.createColumnLabelProvider(itemProps, item);
                        _items.add(itemProps);
                        _itemLabelProviders.put(itemProps.getReferencedItemName(), labelProvider);

                        if (addHeader)
                        {

                            String styleClass = "default_all";
                            EJFrameworkExtensionProperties rendererProperties = item.getReferencedItemProperties().getItemRendererProperties();
                            header.append("<th ");

                            String alignment = null;

                            String alignmentProperty = rendererProperties.getStringProperty(PROPERTY_ALIGNMENT);
                            if (alignmentProperty == null)
                            {
                                alignmentProperty = rendererProperties.getStringProperty("ALLIGNMENT");
                            }
                            alignment = getComponentAlignment(alignmentProperty);

                            int width = -1;
                            if (width == -1)
                            {
                                width = rendererProperties.getIntProperty(DISPLAY_WIDTH_PROPERTY, 0);
                            }

                            if (width > 0)
                            {
                                Font font = labelProvider.getFont(new Object());

                                if (font == null)
                                    font = _browser.getFont();
                                if (font != null)
                                {
                                    float avgCharWidth = RWTUtils.getAvgCharWidth(font);
                                    if (avgCharWidth > 0)
                                    {
                                        if (width != 1)
                                        {
                                            // add +1 padding
                                            width = ((int) (((width + 1) * avgCharWidth)));
                                        }
                                    }
                                }
                                header.append(String.format(" width=%s ", width));

                            }
                            
                            String functionDef = null;

                            String valueVA = blockProperties.getBlockRendererProperties().getStringProperty(HEADER_VA);
                            if (valueVA != null && valueVA.length() > 0)
                            {
                                styleClass = valueVA;
                                valueVA = rendererProperties.getStringProperty(HEADER_VA);
                                if (valueVA != null && valueVA.length() > 0)
                                    styleClass = valueVA;
                            }
                            header.append(String.format(" class=\"%s\" ", styleClass));
                            if (alignment != null)
                            {
                                header.append(String.format(" align=\'%s\'", alignment));
                            }
                            if (paddingStyle != null)
                            {
                                header.append(String.format(" style=\'%s\'", paddingStyle + cellStyleTOP + cellStyleLEFT));
                            }
                            header.append("> ");
                            if (functionDef != null)
                            {
                                header.append(String.format("<ejl><u %s class=\"%s %s\"  ", "style=\"line-height: 130%\"", ("default_all".equals(styleClass) ? "default_link_fg" : "default_link"), styleClass));
                                header.append(functionDef).append(">");
                            }
                            if (itemProps.getLabel() != null)
                                header.append(itemProps.getLabel());
                            header.append("</th>");
                        }
                    }
                }
            }

            if (addHeader)
            {
                _headerTag = header.toString();
            }
        }
        hookKeyListener(_browser);
        createHTML();

    }

    public Font getBlodFont(Font defaultFont)
    {

        String name = null;
        int style = SWT.NORMAL;
        int size = 11;
        if (defaultFont == null)
        {
            defaultFont = Display.getDefault().getSystemFont();
        }
        if (defaultFont != null)
        {
            name = defaultFont.getFontData()[0].getName();
            style = defaultFont.getFontData()[0].getStyle();

            size = defaultFont.getFontData()[0].getHeight();
        }

        style = style | SWT.BOLD;

        if (name == null)
        {
            return defaultFont;
        }

        Font font = new Font(Display.getDefault(), name, size, style);

        return font;
    }

    private void updateWeekSelectionUI()
    {
        if (header == null || header.isDisposed())
            return;
        for (int i = 0; i < workweeks.length; i++)
        {
            Link week = workweeks[i];

            int weekNum = (activeWeeksSlot * 13) + (i + 1);
            if (weekNum != activeWeek)
            {
                week.setText(String.format("<a>%d</a>", weekNum));
                week.setData(RWT.CUSTOM_VARIANT, "workview");
            }
            else
            {
                week.setText(String.valueOf(weekNum));
                week.setData(RWT.CUSTOM_VARIANT, "activeweek");
            }

            week.setData("WN", weekNum);

        }
    }

    private void initActiveWeek()
    {
        weekCalendar.setFirstDayOfWeek(Calendar.MONDAY);
        weekCalendar.setTime(new Date());
        setActiveWeek(weekCalendar.get(Calendar.WEEK_OF_YEAR));
    }

    private int getWeekSlot(int week)
    {
        if (week < 14)
            return 0;
        if (week < 27)
            return 1;
        if (week < 40)
            return 2;

        return 3;
    }

    private void setActiveWeek(int week)
    {

        activeWeek = week;
        activeWeeksSlot = getWeekSlot(activeWeek);
        updateWeekSelectionUI();

        if (wkInfo != null && !wkInfo.isDisposed())
        {
            wkInfo.setText(String.format("Wk %d:", activeWeek));
        }
        if (hoursSumInfo != null && !hoursSumInfo.isDisposed())
        {
            hoursSumInfo.setText(getTotalHours(_block.getRecords()));
        }
    }

    String getTotalHours(Collection<EJDataRecord> records)
    {
        String hours = "00:00";
        long diff = 0l;
        for (EJDataRecord record : records)
        {
            Object servicePojo = record.getServicePojo();
            if (servicePojo instanceof TimeEntry)
            {
                TimeEntry entry = (TimeEntry) servicePojo;
                weekCalendar.setFirstDayOfWeek(Calendar.MONDAY);
                weekCalendar.setTime(entry.getWorkDate());
                int dateWeek = weekCalendar.get(Calendar.WEEK_OF_YEAR);
                if (dateWeek == activeWeek)
                {
                    if (entry.getEndTime() != null && entry.getStartTime() != null)
                    {
                        long start = entry.getStartTime().getTime();
                        long end = entry.getEndTime().getTime();
                        Calendar instance = Calendar.getInstance();
                        instance.setFirstDayOfWeek(Calendar.MONDAY);
                        instance.setTimeInMillis(start);
                        instance.set(Calendar.SECOND, 0);
                        instance.set(Calendar.MILLISECOND, 0);
                        start = instance.getTimeInMillis();
                        instance.setTimeInMillis(end);
                        instance.set(Calendar.SECOND, 0);
                        instance.set(Calendar.MILLISECOND, 0);
                        end = instance.getTimeInMillis();
                        diff = diff + (end - start);
                    }
                }

            }
        }

        if (diff > 0)
        {
            long diffHours = diff / (60 * 60 * 1000);
            long diffMinutes = (diff / (60 * 1000)) - (diffHours * 60);

            return String.format("%1d:%02d", diffHours, diffMinutes);
        }

        return hours;
    }

    public static int getCurrentWeek()
    {
        Calendar c = Calendar.getInstance();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.setTime(new java.util.Date());
        
        return c.get(Calendar.WEEK_OF_YEAR);
    }
    
    private void createHeaderUI()
    {
        Link cWeeks = new Link(header, SWT.NONE);
        header.setData(RWT.CUSTOM_VARIANT, "workview");
        Label wk = new Label(header, SWT.NONE);
        wk.setText("Wk");
        wk.setVisible(false);
        Font blodFont = getBlodFont(wk.getFont());
        wk.setFont(blodFont);

        GridData bt = new GridData();
        bt.heightHint = 21;
       
        Button pWeeks = new Button(header, SWT.PUSH);
        pWeeks.setText("<");
        pWeeks.setLayoutData(bt);
        pWeeks.setFont(blodFont);
        pWeeks.setData(RWT.CUSTOM_VARIANT, "workview");
        pWeeks.addSelectionListener(new SelectionAdapter()
        {
            @Override
            public void widgetSelected(SelectionEvent e)
            {
                if (activeWeeksSlot > 0)
                {
                    activeWeeksSlot--;
                    updateWeekSelectionUI();
                }
            }
        });
        pWeeks.setVisible(false);
        
        workweeks = new Link[13];
        for (int i = 0; i < 13; i++)
        {
            Link week = new Link(header, SWT.NONE);

            week.setData(RWT.CUSTOM_VARIANT, "workview");
            week.addSelectionListener(new SelectionAdapter()
            {
                @Override
                public void widgetSelected(SelectionEvent e)
                {
                    Widget widget = e.widget;
                    if (widget != null && widget.getData("WN") != null)
                    {
                        setActiveWeek((int) widget.getData("WN"));
                        Display.getDefault().asyncExec(new Runnable()
                        {

                            @Override
                            public void run()
                            {
                                _block.executeActionCommand(String.format("WORKWEEK_WEEK:%d", activeWeek), EJScreenType.MAIN);
                            }
                        });
                    }
                }
            });
            workweeks[i] = week;
            week.setVisible(false);
        }
        updateWeekSelectionUI();
        Button nWeeks = new Button(header, SWT.PUSH);
        nWeeks.setText(">");

        nWeeks.setLayoutData(bt);
        nWeeks.setFont(blodFont);
        nWeeks.setData(RWT.CUSTOM_VARIANT, "workview");
        nWeeks.addSelectionListener(new SelectionAdapter()
        {
            @Override
            public void widgetSelected(SelectionEvent e)
            {
                if (activeWeeksSlot < 3)
                {
                    activeWeeksSlot++;
                    updateWeekSelectionUI();
                }
            }
        });
        nWeeks.setVisible(false);
        new Label(header, SWT.NONE);
      
        cWeeks.setText("<a>Current Week</a>");
        cWeeks.setFont(blodFont);
        cWeeks.setData(RWT.CUSTOM_VARIANT, "workview");
        cWeeks.addSelectionListener(new SelectionAdapter()
        {
            @Override
            public void widgetSelected(SelectionEvent e)
            {
                
                setActiveWeek(getCurrentWeek());
                Display.getDefault().asyncExec(new Runnable()
                {

                    @Override
                    public void run()
                    {
                        _block.executeActionCommand(String.format("WORKWEEK_WEEK:%d", activeWeek), EJScreenType.MAIN);
                    }
                });
            }
        });
        // space//
        new Label(header, SWT.NONE).setLayoutData(new GridData(GridData.GRAB_HORIZONTAL));

        // /
        wkInfo = new Label(header, SWT.NONE);
        wkInfo.setText(String.format("Wk %d:", activeWeek));
        hoursSumInfo = new Label(header, SWT.NONE);
        hoursSumInfo.setText(getTotalHours(_block.getRecords()));
        hoursSumInfo.setFont(blodFont);
        Label hours = new Label(header, SWT.NONE);
        hours.setText(" Hours");
    }

    Map<Integer, List<EJDataRecord>> getGrouped(Collection<EJDataRecord> records)
    {
        Map<Integer, List<EJDataRecord>> grouped = new HashMap<Integer, List<EJDataRecord>>();
        weekCalendar.setFirstDayOfWeek(Calendar.MONDAY);
        for (EJDataRecord ejDataRecord : records)
        {
            Object servicePojo = ejDataRecord.getServicePojo();
            if (servicePojo instanceof TimeEntry)
            {
                TimeEntry entry = (TimeEntry) servicePojo;

                java.sql.Date workDate = entry.getWorkDate();
                if (workDate != null)
                {
                    weekCalendar.setTime(workDate);
                    int dateWeek = weekCalendar.get(Calendar.WEEK_OF_YEAR);
                    if (dateWeek == activeWeek)
                    {
                        int dayOfWeek = weekCalendar.get(Calendar.DAY_OF_WEEK);
                        List<EJDataRecord> list = grouped.get(dayOfWeek);
                        if (list == null)
                        {
                            list = new ArrayList<EJDataRecord>();
                            grouped.put(dayOfWeek, list);
                        }
                        list.add(ejDataRecord);
                    }
                }
            }
        }

        return grouped;

    }

    private void createHTML()
    {

        if (_browser == null || _browser.isDisposed())
        {
            return;
        }

        StringBuilder builder = new StringBuilder();
        {
            
            builder.append("<div id=\"table\" style=\"float:left;width:100%;height:100%; overflow:auto\">");
            {
                EJCoreBlockProperties blockProperties = _block.getProperties();
                int cellSpacing = blockProperties.getBlockRendererProperties().getIntProperty(CELL_SPACING_PROPERTY, 0);
                int cellPadding = blockProperties.getBlockRendererProperties().getIntProperty(CELL_PADDING_PROPERTY, 0);
                String paddingStyle = "";
                String cellStyleTOP = "";
                String cellStyleLEFT = "";
                if (cellPadding > 0)
                {
                    String str = String.valueOf(cellPadding);
                    paddingStyle = String.format("padding: %spx %spx %spx %spx; ", str, str, str, str);
                }
                if (cellSpacing > 0)
                {
                    cellStyleTOP = String.format("border-top: %dpx  solid  #9e9e9e; ", cellSpacing);
                    cellStyleLEFT = String.format(" border-left: %dpx  solid  #9e9e9e;", cellSpacing);
                }
                builder.append("<table cellspacing=").append(0).append(" width=\"100%\" style='border-bottom: 1px  solid  #9e9e9e;border-right: 1px  solid  #9e9e9e;'>");
                {
                    builder.append("<link rel=\"stylesheet\" type=\"text/css\" href=\"");
                    builder.append(createVACSSUrl());
                    builder.append("\">");
                    int charHeight = EJRWTImageRetriever.getGraphicsProvider().getCharHeight(Display.getDefault().getSystemFont());
                    String trDef = String.format("<tr style=\"height: %spx\">", String.valueOf(charHeight));

                    if (_headerTag != null)
                    {
                        String sortHeader = _headerTag;

                        builder.append(sortHeader);
                    }

                    String oddVA = "default_all";
                    String valueVA = blockProperties.getBlockRendererProperties().getStringProperty(ROW_ODD_VA);
                    if (valueVA != null && valueVA.length() > 0)
                    {
                        oddVA = valueVA;
                    }
                    String evenVA = "default_all";
                    valueVA = blockProperties.getBlockRendererProperties().getStringProperty(ROW_EVEN_VA);
                    if (valueVA != null && valueVA.length() > 0)
                    {
                        evenVA = valueVA;
                    }

                    int[] WEEK_DAYS = new int[] {     Calendar.MONDAY,Calendar.TUESDAY,Calendar.WEDNESDAY,Calendar.THURSDAY, Calendar.FRIDAY, Calendar.SATURDAY,Calendar.SUNDAY };

                    Map<Integer, List<EJDataRecord>> grouped = getGrouped(_block.getRecords());
                    int rowid = 0;
                    for (int DAY : WEEK_DAYS)
                    {
                        rowid++;
                        final String styleClass = (rowid % 2) != 0 ? oddVA : evenVA;
                        List<EJDataRecord> list = grouped.get(DAY);
                        if (list == null || list.size() == 0)
                        {
                            boolean addDAYTD = true;
                            int emptyLines = 2;
                            for (int i = 0; i < emptyLines; i++)
                            {

                                builder.append(trDef);

                                {
                                    if (addDAYTD)
                                    {
                                        addDAYTD = false;

                                        addDateTD(builder, paddingStyle + cellStyleTOP + cellStyleLEFT, DAY, emptyLines, styleClass);
                                    }

                                    addEmptyLine(builder, paddingStyle, styleClass, i == 0 ? cellStyleTOP : "", cellStyleLEFT);

                                }
                            }

                            continue;
                        }

                        Collections.sort(list, new Comparator<EJDataRecord>()
                        {

                            @Override
                            public int compare(EJDataRecord o1, EJDataRecord o2)
                            {
                                TimeEntry e1 = (TimeEntry) o1.getServicePojo();
                                TimeEntry e2 = (TimeEntry) o2.getServicePojo();
                                return (e2.getStartTime().compareTo(e1.getStartTime()));
                            }
                        });

                        boolean addDAYTD = true;
                        int size = list.size() + 1;// add total line
                        for (EJDataRecord record : list)
                        {

                            builder.append(trDef);

                            {
                                if (addDAYTD)
                                {
                                    addDAYTD = false;

                                    addDateTD(builder, paddingStyle + cellStyleTOP + cellStyleLEFT, DAY, size, styleClass);
                                }

                            }

                            for (EJCoreMainScreenItemProperties item : _items)
                            {

                                String actionDef = null;
                                String alignment = null;
                                float width = -1;

                                ColumnLabelProvider columnLabelProvider = _itemLabelProviders.get(item.getReferencedItemName());

                                EJScreenItemController screenItem = _block.getScreenItem(EJScreenType.MAIN, item.getReferencedItemName());
                                String computeStyleClass = styleClass;
                                EJCoreVisualAttributeProperties iva = screenItem.getManagedItemRenderer().getVisualAttributeProperties();
                                if (iva != null)
                                {
                                    computeStyleClass = iva.getName();
                                }

                                EJFrameworkExtensionProperties rendererProperties = item.getReferencedItemProperties().getItemRendererProperties();

                                EJCoreVisualAttributeProperties diva = record.getItem(item.getReferencedItemName()).getVisualAttribute();
                                if (diva != null)
                                {
                                    computeStyleClass = diva.getName();
                                }
                                builder.append(String.format("<td class=\"%s\" ", computeStyleClass));
                                if (paddingStyle != null)
                                {
                                    builder.append(String.format(" style=\'%s\'", paddingStyle + cellStyleTOP + cellStyleLEFT));
                                }

                                EJFrameworkExtensionProperties extentionProperties = item.getBlockRendererRequiredProperties();
                                if (width == -1)
                                {
                                    width = extentionProperties.getIntProperty(DISPLAY_WIDTH_PROPERTY, 0);
                                }

                                String action = extentionProperties.getStringProperty(CELL_ACTION_COMMAND);

                                if (action != null && action.length() > 0)
                                {
                                    actionDef = String.format("em='eaction' earg='%s , %s' ", action, String.valueOf(getDisplayedRecordNumber(record)));

                                }

                                if (width > 0)
                                {
                                    Font font = columnLabelProvider.getFont(new Object());

                                    if (font == null)
                                        font = _browser.getFont();
                                    if (font != null)
                                    {
                                        float avgCharWidth = RWTUtils.getAvgCharWidth(font);
                                        if (avgCharWidth > 0)
                                        {
                                            if (width != 1)
                                            {
                                                // add +1 padding
                                                width = ((int) (((width + 1) * avgCharWidth)));
                                            }
                                        }
                                    }

                                    builder.append(String.format(" width=%s ", width));
                                }
                                if (alignment == null)
                                {
                                    String alignmentProperty = rendererProperties.getStringProperty(PROPERTY_ALIGNMENT);
                                    if (alignmentProperty == null)
                                    {
                                        alignmentProperty = rendererProperties.getStringProperty("ALLIGNMENT");
                                    }
                                    alignment = getComponentAlignment(alignmentProperty);

                                }
                                if (alignment != null)
                                {
                                    builder.append(String.format(" align=\'%s\'", alignment));
                                }
                                final String caseProperty = getComponentCase(rendererProperties.getStringProperty(PROPERTY_CASE));

                                builder.append(String.format(" font style=\'%s\'", caseProperty));

                                builder.append(">");
                                
                                
                                String text = columnLabelProvider.getText(record);

                                if (actionDef != null && text!=null && text.length()>0)
                                {
                                    builder.append(String.format("<ejl><u %s class=\"%s %s\"  ", "style=\"line-height: 100%\"", ("default_all".equals(computeStyleClass) ? "default_link_fg" : "default_link"), computeStyleClass));
                                    builder.append(actionDef).append(">");
                                }

                                Image image = columnLabelProvider.getImage(record);
                                if (image != null)
                                {
                                    if (actionDef == null)
                                    {
                                        builder.append("<img src=\"");

                                        builder.append(ImageFactory.getImagePath(image));

                                        builder.append("\"");
                                        builder.append(String.format(" class=\"default %s\"  >", computeStyleClass));
                                    }
                                    else

                                    {
                                        builder.append("<ejl><img src=\"");
                                        builder.append(ImageFactory.getImagePath(image));
                                        builder.append("\"");
                                        builder.append(String.format("style=\"cursor: hand;\" class=\"%s \" %s  > </ejl>",  computeStyleClass, actionDef));
                                    }
                                }
                                // builder.append(String.format("<p class=\"default %s\">",
                                // styleClass));

                               
                                
                                builder.append(text);
                                builder.append("</td>");
                            }

                            builder.append("</tr>");
                        }
                        if (size > 1)
                            addTotalLine(builder, paddingStyle, styleClass, cellStyleTOP, cellStyleLEFT, getTotalHours(list));

                    }

                }
                builder.append("</table>");
            }
            builder.append("</<div>");
        }
        String html = builder.toString();
        if (_browser.getText() == null || (!html.equals(_browser.getText())))
        {
            _browser.setText(html);
            LOGGER.debug(html);
        }

    }

    private void addDateTD(StringBuilder builder, String paddingStyle, int DAY, int emptyLines, String styleClass)
    {
        builder.append(String.format("<td height=\"40\" class=\"%s\" ", styleClass));
        if (paddingStyle != null)
        {
            builder.append(String.format(" style=\'%s\'", paddingStyle));
        }

        builder.append(String.format(" width=%s ", 60));
        builder.append(String.format(" rowspan =%s ", emptyLines));

        builder.append(String.format(" align=\'%s\'", "center"));

        builder.append(">");

        String text = "";

        switch (DAY)
        {
            case Calendar.SUNDAY:
                text = "Su";
                break;
            case Calendar.MONTH:
                text = "Mo";
                break;
            case Calendar.TUESDAY:
                text = "Tu";
                break;
            case Calendar.WEDNESDAY:
                text = "We";
                break;
            case Calendar.THURSDAY:
                text = "Th";
                break;
            case Calendar.FRIDAY:
                text = "Fr";
                break;
            case Calendar.SATURDAY:
                text = "Sa";
                break;
        }

        weekCalendar.setFirstDayOfWeek(Calendar.MONDAY);
        weekCalendar.set(Calendar.WEEK_OF_YEAR, activeWeek);
        weekCalendar.set(Calendar.DAY_OF_WEEK, DAY);
        Date time = weekCalendar.getTime();
        String  actionDef = String.format("em='eaction' earg='WORKWEEK_DAY:%s , %s' ", DATE_FORMAT.format(time) , String.valueOf(getDisplayedRecordNumber(currentRec)));
       
        String dayStr = DAY_FORMAT.format(time);
        if (dayStr.equals(DAY_FORMAT.format(new Date())))// today
        {

            builder.append("<div style='float:left;background-color:#F75D59;width:3px;height:80%;'></div>");
 
        }
        builder.append("<ejl>");
        builder.append(String.format("<strong style=\"cursor: hand;color: #4a4a4a; font-size: 14px;\" %s >%s</strong></ejl><br />%s",actionDef, text, dayStr));

        builder.append("</td>");
    }

    public void addTotalLine(StringBuilder builder, final String paddingStyle, String styleClass, String cellStyleTOP, String cellStyleLEFT, String total)
    {
        boolean addCSLeft = true;
        int totalColIndex = -1;

        for (EJCoreMainScreenItemProperties item : _items)
        {
            if (item.getReferencedItemName().equals("hoursWorked"))
            {
                totalColIndex = _items.indexOf(item);
                break;
            }
        }
        for (EJCoreMainScreenItemProperties item : _items)
        {
            String padding = paddingStyle + cellStyleTOP;
            if (addCSLeft)
            {
                addCSLeft = false;
                padding += cellStyleLEFT;
            }
            boolean addTotalLabel = totalColIndex > 0 && _items.indexOf(item) == (totalColIndex - 1);
            String alignment = addTotalLabel ? "right" : null;
            float width = -1;

            ColumnLabelProvider columnLabelProvider = _itemLabelProviders.get(item.getReferencedItemName());

            EJScreenItemController screenItem = _block.getScreenItem(EJScreenType.MAIN, item.getReferencedItemName());
            EJCoreVisualAttributeProperties iva = screenItem.getManagedItemRenderer().getVisualAttributeProperties();
            if (iva != null)
            {
                styleClass = iva.getName();
            }

            EJFrameworkExtensionProperties rendererProperties = item.getReferencedItemProperties().getItemRendererProperties();

            builder.append(String.format("<td class=\"%s\" ", styleClass));
            if (padding != null)
            {
                builder.append(String.format(" style=\'%s\'", padding));
            }

            EJFrameworkExtensionProperties extentionProperties = item.getBlockRendererRequiredProperties();
            if (width == -1)
            {
                width = extentionProperties.getIntProperty(DISPLAY_WIDTH_PROPERTY, 0);
            }

            if (width > 0)
            {
                Font font = columnLabelProvider.getFont(new Object());

                if (font == null)
                    font = _browser.getFont();
                if (font != null)
                {
                    float avgCharWidth = RWTUtils.getAvgCharWidth(font);
                    if (avgCharWidth > 0)
                    {
                        if (width != 1)
                        {
                            // add +1 padding
                            width = ((int) (((width + 1) * avgCharWidth)));
                        }
                    }
                }
                builder.append(String.format(" width=%s ", width));
            }
            if (alignment == null)
            {
                String alignmentProperty = rendererProperties.getStringProperty(PROPERTY_ALIGNMENT);
                if (alignmentProperty == null)
                {
                    alignmentProperty = rendererProperties.getStringProperty("ALLIGNMENT");
                }
                alignment = getComponentAlignment(alignmentProperty);

            }
            if (alignment != null)
            {
                builder.append(String.format(" align=\'%s\'", alignment));
            }
            final String caseProperty = getComponentCase(rendererProperties.getStringProperty(PROPERTY_CASE));

            builder.append(String.format(" font style=\'%s\'", caseProperty));

            builder.append(">");

            if (addTotalLabel)
            {
                builder.append("Total:");
            }
            if (_items.indexOf(item) == (totalColIndex))
            {
                builder.append(total);
            }

            builder.append("</td>");

        }
    }

    public void addEmptyLine(StringBuilder builder, final String paddingStyle, String styleClass, String cellStyleTOP, String cellStyleLEFT)
    {
        boolean addCSLeft = true;

        for (EJCoreMainScreenItemProperties item : _items)
        {
            String padding = paddingStyle + cellStyleTOP;
            if (addCSLeft)
            {
                addCSLeft = false;
                padding += cellStyleLEFT;
            }
            String alignment = null;
            float width = -1;

            ColumnLabelProvider columnLabelProvider = _itemLabelProviders.get(item.getReferencedItemName());

            EJScreenItemController screenItem = _block.getScreenItem(EJScreenType.MAIN, item.getReferencedItemName());
            EJCoreVisualAttributeProperties iva = screenItem.getManagedItemRenderer().getVisualAttributeProperties();
            if (iva != null)
            {
                styleClass = iva.getName();
            }

            EJFrameworkExtensionProperties rendererProperties = item.getReferencedItemProperties().getItemRendererProperties();

            builder.append(String.format("<td class=\"%s\" ", styleClass));
            if (padding != null)
            {
                builder.append(String.format(" style=\'%s\'", padding));
            }

            EJFrameworkExtensionProperties extentionProperties = item.getBlockRendererRequiredProperties();
            if (width == -1)
            {
                width = extentionProperties.getIntProperty(DISPLAY_WIDTH_PROPERTY, 0);
            }

            if (width > 0)
            {
                Font font = columnLabelProvider.getFont(new Object());

                if (font == null)
                    font = _browser.getFont();
                if (font != null)
                {
                    float avgCharWidth = RWTUtils.getAvgCharWidth(font);
                    if (avgCharWidth > 0)
                    {
                        if (width != 1)
                        {
                            // add +1 padding
                            width = ((int) (((width + 1) * avgCharWidth)));
                        }
                    }
                }
                builder.append(String.format(" width=%s ", width));
            }
            if (alignment == null)
            {
                String alignmentProperty = rendererProperties.getStringProperty(PROPERTY_ALIGNMENT);
                if (alignmentProperty == null)
                {
                    alignmentProperty = rendererProperties.getStringProperty("ALLIGNMENT");
                }
                alignment = getComponentAlignment(alignmentProperty);

            }
            if (alignment != null)
            {
                builder.append(String.format(" align=\'%s\'", alignment));
            }
            final String caseProperty = getComponentCase(rendererProperties.getStringProperty(PROPERTY_CASE));

            builder.append(String.format(" font style=\'%s\'", caseProperty));

            builder.append(">");

            builder.append("</td>");

        }
    }

    private String getComponentAlignment(final String alignmentProperty)
    {
        String align = "left";
        if (alignmentProperty != null && alignmentProperty.trim().length() > 0)
        {
            if (alignmentProperty.equals(PROPERTY_ALIGNMENT_JUSTIFY))
            {
                align = "justify";
            }
            else if (alignmentProperty.equals(PROPERTY_ALIGNMENT_RIGHT))
            {
                align = "right";
            }
            else if (alignmentProperty.equals(PROPERTY_ALIGNMENT_CENTER))
            {
                align = "center";
            }
            else if (alignmentProperty.equals(PROPERTY_ALIGNMENT_CHAR))
            {
                align = "char";
            }
        }
        return align;
    }

    private String getComponentCase(final String caseProperty)
    {
        String caze = "text-transform: none;";
        if (caseProperty != null && caseProperty.trim().length() > 0)
        {

            if (caseProperty.equals(PROPERTY_CASE_LOWER))
            {
                caze = "text-transform: lowercase;";
            }
            else if (caseProperty.equals(PROPERTY_CASE_UPPER))
            {
                caze = "text-transform: uppercase;";
            }
            else if (caseProperty.equals(PROPERTY_CASE_CAPITALIZE))
            {
                caze = "text-transform: capitalize;";
            }

        }

        return caze;
    }

    private String createVACSSUrl()
    {

        return RWT.getServiceManager().getServiceHandlerUrl(VACSSServiceHandler.SERVICE_HANDLER);
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
            LOGGER.debug(actionID);
            _block.executeActionCommand(actionID, EJScreenType.MAIN);
        }
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

    private String createImageUrl(String string)
    {
        return ImageFactory.getImagePath(EJRWTImageRetriever.get(string));
    }
    
    
    public static boolean isWeekSelectionAction(String command)
    {
        
        
        return command!=null && command.startsWith("WORKWEEK_WEEK:");
    }
    
    public static int getWeekSelection(String command)
    {
        
        
        return  Integer.parseInt(command.substring(command.indexOf(":")+1,command.length()));
    }
    
    public static boolean isDaySelectionAction(String command)
    {
        
        
         return command!=null && command.startsWith("WORKWEEK_DAY:");
    }
    public static Date getDaySelection(String command)
    {
        
        
        try
        {
            return DATE_FORMAT.parse(command.substring(command.indexOf(":")+1,command.length()));
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        return null;
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
