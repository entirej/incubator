package org.entirej.ejinvoice.forms.projects;

import java.math.BigDecimal;
import java.sql.Date;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.Locale;

import org.entirej.framework.core.EJFieldName;
import org.entirej.framework.core.service.EJPojoProperty;

public class Project
{
    private EJPojoProperty<Locale>     _locale;
    private EJPojoProperty<Integer>    _id;
    private EJPojoProperty<String>     _description;
    private EJPojoProperty<String>     _name;
    private EJPojoProperty<Integer>    _companyId;
    private EJPojoProperty<Integer>    _customerId;
    private EJPojoProperty<String>     _customerName;
    private EJPojoProperty<Date>       _startDate;
    private EJPojoProperty<Date>       _endDate;
    private EJPojoProperty<String>     _status;
    private EJPojoProperty<String>     _notes;
    private EJPojoProperty<String>     _displayText;
    private EJPojoProperty<Integer>    _bookableHours;
    private EJPojoProperty<Integer>    _maximumHours;

    private EJPojoProperty<Long>       _openItems;
    private EJPojoProperty<Long>       _plannedItems;
    private EJPojoProperty<Long>       _approvedItems;
    private EJPojoProperty<Long>       _markedFormInvoiceItems;

    private EJPojoProperty<String>     _invoiceable;
    private EJPojoProperty<BigDecimal> _fixPrice;

    private EJPojoProperty<Integer>    _taskCprId;
    private EJPojoProperty<String>     _taskNotes;
    private EJPojoProperty<String>     _taskName;
    private EJPojoProperty<BigDecimal> _taskPayRate;
    private EJPojoProperty<Integer>    _taskUserId;
    private EJPojoProperty<BigDecimal> _taskFixPrice;
    private EJPojoProperty<String>     _taskStatus;
    private EJPojoProperty<String>     _taskInvoiceable;

    @EJFieldName("LOCALE")
    public Locale getLocale()
    {
        return EJPojoProperty.getPropertyValue(_locale);
    }

    @EJFieldName("LOCALE")
    public void setLocale(Locale locale)
    {
        _locale = EJPojoProperty.setPropertyValue(_locale, locale);
    }

    @EJFieldName("LOCALE")
    public Locale getInitialLocale()
    {
        return EJPojoProperty.getPropertyInitialValue(_locale);
    }

    
    @EJFieldName("BOOKABLE_HOURS")
    public Integer getBookableHours()
    {
        return EJPojoProperty.getPropertyValue(_bookableHours);
    }

    @EJFieldName("BOOKABLE_HOURS")
    public void setBookableHours(Integer bookableHours)
    {
        _bookableHours = EJPojoProperty.setPropertyValue(_bookableHours, bookableHours);
    }

    @EJFieldName("BOOKABLE_HOURS")
    public Integer getInitialBookableHours()
    {
        return EJPojoProperty.getPropertyInitialValue(_bookableHours);
    }
    
    @EJFieldName("MAXIMUM_HOURS")
    public Integer getMaximumHours()
    {
        return EJPojoProperty.getPropertyValue(_maximumHours);
    }

    @EJFieldName("MAXIMUM_HOURS")
    public void setMaximumHours(Integer maximumHours)
    {
        _maximumHours = EJPojoProperty.setPropertyValue(_maximumHours, maximumHours);
    }

    @EJFieldName("MAXIMUM_HOURS")
    public Integer getInitialMaximumHours()
    {
        return EJPojoProperty.getPropertyInitialValue(_maximumHours);
    }
    
    
    @EJFieldName("ID")
    public Integer getId()
    {
        return EJPojoProperty.getPropertyValue(_id);
    }

    @EJFieldName("ID")
    public void setId(Integer id)
    {
        _id = EJPojoProperty.setPropertyValue(_id, id);
    }

    @EJFieldName("ID")
    public Integer getInitialId()
    {
        return EJPojoProperty.getPropertyInitialValue(_id);
    }

    @EJFieldName("DESCRIPTION")
    public String getDescription()
    {
        return EJPojoProperty.getPropertyValue(_description);
    }

    @EJFieldName("DESCRIPTION")
    public void setDescription(String description)
    {
        _description = EJPojoProperty.setPropertyValue(_description, description);
    }

    @EJFieldName("DESCRIPTION")
    public String getInitialDescription()
    {
        return EJPojoProperty.getPropertyInitialValue(_description);
    }

    @EJFieldName("NAME")
    public String getName()
    {
        return EJPojoProperty.getPropertyValue(_name);
    }

    @EJFieldName("NAME")
    public void setName(String name)
    {
        _name = EJPojoProperty.setPropertyValue(_name, name);
    }

    @EJFieldName("NAME")
    public String getInitialName()
    {
        return EJPojoProperty.getPropertyInitialValue(_name);
    }

    @EJFieldName("STATUS")
    public String getStatus()
    {
        return EJPojoProperty.getPropertyValue(_status);
    }

    @EJFieldName("STATUS")
    public void setStatus(String status)
    {
        _status = EJPojoProperty.setPropertyValue(_status, status);
    }

    @EJFieldName("STATUS")
    public String getInitialStatus()
    {
        return EJPojoProperty.getPropertyInitialValue(_status);
    }

    @EJFieldName("COMPANY_ID")
    public Integer getCompanyId()
    {
        return EJPojoProperty.getPropertyValue(_companyId);
    }

    @EJFieldName("COMPANY_ID")
    public void setCompanyId(Integer companyId)
    {
        _companyId = EJPojoProperty.setPropertyValue(_companyId, companyId);
    }

    @EJFieldName("COMPANY_ID")
    public Integer getInitialCompanyId()
    {
        return EJPojoProperty.getPropertyInitialValue(_companyId);
    }

    @EJFieldName("CUSTOMER_ID")
    public Integer getCustomerId()
    {
        return EJPojoProperty.getPropertyValue(_customerId);
    }

    @EJFieldName("CUSTOMER_ID")
    public void setCustomerId(Integer customerId)
    {
        _customerId = EJPojoProperty.setPropertyValue(_customerId, customerId);
    }

    @EJFieldName("CUSTOMER_ID")
    public Integer getInitialCustomerId()
    {
        return EJPojoProperty.getPropertyInitialValue(_customerId);
    }

    @EJFieldName("CUSTOMER_NAME")
    public String getCustomerName()
    {
        return EJPojoProperty.getPropertyValue(_customerName);
    }

    @EJFieldName("CUSTOMER_NAME")
    public void setCustomerName(String customerName)
    {
        _customerName = EJPojoProperty.setPropertyValue(_customerName, customerName);
    }

    @EJFieldName("CUSTOMER_NAME")
    public String getInitialCustomerName()
    {
        return EJPojoProperty.getPropertyInitialValue(_customerName);
    }

    @EJFieldName("START_DATE")
    public Date getStartDate()
    {
        return EJPojoProperty.getPropertyValue(_startDate);
    }

    @EJFieldName("START_DATE")
    public void setStartDate(Date startDate)
    {
        _startDate = EJPojoProperty.setPropertyValue(_startDate, startDate);
    }

    @EJFieldName("START_DATE")
    public Date getInitialStartDate()
    {
        return EJPojoProperty.getPropertyInitialValue(_startDate);
    }

    @EJFieldName("END_DATE")
    public Date getEndDate()
    {
        return EJPojoProperty.getPropertyValue(_endDate);
    }

    @EJFieldName("END_DATE")
    public void setEndDate(Date endDate)
    {
        _endDate = EJPojoProperty.setPropertyValue(_endDate, endDate);
    }

    @EJFieldName("END_DATE")
    public Date getInitialEndDate()
    {
        return EJPojoProperty.getPropertyInitialValue(_endDate);
    }

    @EJFieldName("NOTES")
    public String getNotes()
    {
        return EJPojoProperty.getPropertyValue(_notes);
    }

    @EJFieldName("NOTES")
    public void setNotes(String notes)
    {
        _notes = EJPojoProperty.setPropertyValue(_notes, notes);
    }

    @EJFieldName("NOTES")
    public String getInitialNotes()
    {
        return EJPojoProperty.getPropertyInitialValue(_notes);
    }

    @EJFieldName("TASK_CPR_ID")
    public Integer getTaskCprId()
    {
        return EJPojoProperty.getPropertyValue(_taskCprId);
    }

    @EJFieldName("TASK_CPR_ID")
    public void setTaskCprId(Integer taskCprId)
    {
        _taskCprId = EJPojoProperty.setPropertyValue(_taskCprId, taskCprId);
    }

    @EJFieldName("TASK_CPR_ID")
    public Integer getInitialTaskCprId()
    {
        return EJPojoProperty.getPropertyInitialValue(_taskCprId);
    }

    @EJFieldName("TASK_NOTES")
    public String getTaskNotes()
    {
        return EJPojoProperty.getPropertyValue(_taskNotes);
    }

    @EJFieldName("TASK_NOTES")
    public void setTaskNotes(String taskNotes)
    {
        _taskNotes = EJPojoProperty.setPropertyValue(_taskNotes, taskNotes);
    }

    @EJFieldName("TASK_NOTES")
    public String getInitialTaskNotes()
    {
        return EJPojoProperty.getPropertyInitialValue(_taskNotes);
    }

    @EJFieldName("TASK_NAME")
    public String getTaskName()
    {
        return EJPojoProperty.getPropertyValue(_taskName);
    }

    @EJFieldName("TASK_NAME")
    public void setTaskName(String taskName)
    {
        _taskName = EJPojoProperty.setPropertyValue(_taskName, taskName);
    }

    @EJFieldName("TASK_NAME")
    public String getInitialTaskName()
    {
        return EJPojoProperty.getPropertyInitialValue(_taskName);
    }

    @EJFieldName("TASK_PAY_RATE")
    public BigDecimal getTaskPayRate()
    {
        return EJPojoProperty.getPropertyValue(_taskPayRate);
    }

    @EJFieldName("TASK_PAY_RATE")
    public void setTaskPayRate(BigDecimal taskPayRate)
    {
        _taskPayRate = EJPojoProperty.setPropertyValue(_taskPayRate, taskPayRate);
    }

    @EJFieldName("TASK_PAY_RATE")
    public BigDecimal getInitialTaskPayRate()
    {
        return EJPojoProperty.getPropertyInitialValue(_taskPayRate);
    }

    @EJFieldName("TASK_USER_ID")
    public Integer getTaskUserId()
    {
        return EJPojoProperty.getPropertyValue(_taskUserId);
    }

    @EJFieldName("TASK_USER_ID")
    public void setTaskUserId(Integer taskUserId)
    {
        _taskUserId = EJPojoProperty.setPropertyValue(_taskUserId, taskUserId);
    }

    @EJFieldName("TASK_USER_ID")
    public Integer getInitialTaskUserId()
    {
        return EJPojoProperty.getPropertyInitialValue(_taskUserId);
    }

    @EJFieldName("INVOICEABLE")
    public String getInvoiceable()
    {
        return EJPojoProperty.getPropertyValue(_invoiceable);
    }

    @EJFieldName("INVOICEABLE")
    public void setInvoiceable(String invoiceable)
    {
        _invoiceable = EJPojoProperty.setPropertyValue(_invoiceable, invoiceable);
    }

    @EJFieldName("INVOICEABLE")
    public String getInitialInvoiceable()
    {
        return EJPojoProperty.getPropertyInitialValue(_invoiceable);
    }

    @EJFieldName("FIX_PRICE")
    public BigDecimal getFixPrice()
    {
        return EJPojoProperty.getPropertyValue(_fixPrice);
    }

    @EJFieldName("FIX_PRICE")
    public void setFixPrice(BigDecimal fixPrice)
    {
        _fixPrice = EJPojoProperty.setPropertyValue(_fixPrice, fixPrice);
    }

    @EJFieldName("FIX_PRICE")
    public BigDecimal getInitialFixPrice()
    {
        return EJPojoProperty.getPropertyInitialValue(_fixPrice);
    }

    @EJFieldName("TASK_FIX_PRICE")
    public BigDecimal getTaskFixPrice()
    {
        return EJPojoProperty.getPropertyValue(_taskFixPrice);
    }

    @EJFieldName("TASK_FIX_PRICE")
    public void setTaskFixPrice(BigDecimal taskFixPrice)
    {
        _taskFixPrice = EJPojoProperty.setPropertyValue(_taskFixPrice, taskFixPrice);
    }

    @EJFieldName("TASK_FIX_PRICE")
    public BigDecimal getInitialTaskFixPrice()
    {
        return EJPojoProperty.getPropertyInitialValue(_taskFixPrice);
    }

    @EJFieldName("TASK_STATUS")
    public String getTaskStatus()
    {
        return EJPojoProperty.getPropertyValue(_taskStatus);
    }

    @EJFieldName("TASK_STATUS")
    public void setTaskStatus(String taskStatus)
    {
        _taskStatus = EJPojoProperty.setPropertyValue(_taskStatus, taskStatus);
    }

    @EJFieldName("TASK_STATUS")
    public String getInitialTaskStatus()
    {
        return EJPojoProperty.getPropertyInitialValue(_taskStatus);
    }

    @EJFieldName("TASK_INVOICEABLE")
    public String getTaskInvoiceable()
    {
        return EJPojoProperty.getPropertyValue(_taskInvoiceable);
    }

    @EJFieldName("TASK_INVOICEABLE")
    public void setTaskInvoiceable(String taskInvoiceable)
    {
        _taskInvoiceable = EJPojoProperty.setPropertyValue(_taskInvoiceable, taskInvoiceable);
    }

    @EJFieldName("TASK_INVOICEABLE")
    public String getInitialTaskInvoiceable()
    {
        return EJPojoProperty.getPropertyInitialValue(_taskInvoiceable);
    }

    @EJFieldName("OPEN_ITEMS")
    public Long getOpenItems()
    {
        return EJPojoProperty.getPropertyValue(_openItems);
    }

    @EJFieldName("OPEN_ITEMS")
    public void setOpenItems(Long openItems)
    {
        _openItems = EJPojoProperty.setPropertyValue(_openItems, openItems);
    }

    @EJFieldName("OPEN_ITEMS")
    public Long getInitialOpenItems()
    {
        return EJPojoProperty.getPropertyInitialValue(_openItems);
    }

    @EJFieldName("PLANNED_ITEMS")
    public Long getPlannedItems()
    {
        return EJPojoProperty.getPropertyValue(_plannedItems);
    }

    @EJFieldName("PLANNED_ITEMS")
    public void setPlannedItems(Long plannedItems)
    {
        _plannedItems = EJPojoProperty.setPropertyValue(_plannedItems, plannedItems);
    }

    @EJFieldName("PANNED_ITEMS")
    public Long getInitialPlannedItems()
    {
        return EJPojoProperty.getPropertyInitialValue(_plannedItems);
    }

    @EJFieldName("APPROVED_ITEMS")
    public Long getApprovedItems()
    {
        return EJPojoProperty.getPropertyValue(_approvedItems);
    }

    @EJFieldName("APPROVED_ITEMS")
    public void setApprovedItems(Long approvedItems)
    {
        _approvedItems = EJPojoProperty.setPropertyValue(_approvedItems, approvedItems);
    }

    @EJFieldName("APPROVED_ITEMS")
    public Long getInitialApprovedItems()
    {
        return EJPojoProperty.getPropertyInitialValue(_approvedItems);
    }

    @EJFieldName("MARKED_FOR_INVOICE_ITEMS")
    public Long getMarkedForInvoiceItems()
    {
        return EJPojoProperty.getPropertyValue(_markedFormInvoiceItems);
    }

    @EJFieldName("MARKED_FOR_INVOICE_ITEMS")
    public void setMarkedForInvoiceItems(Long markedForInvoiceItems)
    {
        _markedFormInvoiceItems = EJPojoProperty.setPropertyValue(_markedFormInvoiceItems, markedForInvoiceItems);
    }

    @EJFieldName("MARKED_FOR_INVOICE_ITEMS")
    public Long getInitialMarkedForInvoiceItems()
    {
        return EJPojoProperty.getPropertyInitialValue(_markedFormInvoiceItems);
    }

    @EJFieldName("DISPLAY_TEXT")
    public String getInitialDisplayText()
    {
        return EJPojoProperty.getPropertyInitialValue(_displayText);
    }

    @EJFieldName("DISPLAY_TEXT")
    public void setDisplayText(String displayText)
    {
        _displayText = EJPojoProperty.setPropertyValue(_displayText, displayText);
    }

    @EJFieldName("DISPLAY_TEXT")
    public String getDisplayText()
    {
        NumberFormat format = null;
        DateFormat dateFormat = null;
        if (getLocale() == null)
        {
            format = NumberFormat.getNumberInstance();
            dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM);
        }
        else
        {
            format = NumberFormat.getNumberInstance(getLocale());
            dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM, getLocale());
        }
        format.setMinimumFractionDigits(2);

        StringBuilder display = new StringBuilder();
        display.append("<table border=0 cellpadding=0 cellspacing=0 width=100%");
        display.append("<tr>");

        display.append("<td align=left width=40%>");
        display.append("<span style =\"font-weight: bold; font-size: 110% \">" + getName() + "</span>");
        display.append("</td>");

        display.append("<td align=\"left\"  width=\"30%\">" + getCustomerName() + "</td>");
        display.append("<td align=\"right\"  width=\"15%\">" + (getStartDate() == null ? "&nbsp;" : "Start: "+dateFormat.format(getStartDate())) + "</td>");
        display.append("<td align=\"right\"  width=\"15%\">" + (getEndDate() == null ? "&nbsp;" : "End: "+dateFormat.format(getEndDate())) + "</td>");
        display.append("</tr>");

        display.append("<tr>");
        display.append("<td align=\"left\"  width=\"70%\" colspan=2>" + (getNotes() == null ? "&nbsp;" : getNotes()) + "</td>");
        display.append("<td align=\"right\"  width=\"30%\" colspan=2>" + (getFixPrice() == null ? "&nbsp;" : "Fix Price: "+format.format(getFixPrice())) + "</td>");
        display.append("</tr>");

        display.append("</table>");

        return display.toString();
    }

    public void clearInitialValues()
    {
        EJPojoProperty.clearInitialValue(_locale);
        EJPojoProperty.clearInitialValue(_id);
        EJPojoProperty.clearInitialValue(_description);
        EJPojoProperty.clearInitialValue(_name);
        EJPojoProperty.clearInitialValue(_companyId);
        EJPojoProperty.clearInitialValue(_customerId);
        EJPojoProperty.clearInitialValue(_customerName);
        EJPojoProperty.clearInitialValue(_startDate);
        EJPojoProperty.clearInitialValue(_endDate);
        EJPojoProperty.clearInitialValue(_status);
        EJPojoProperty.clearInitialValue(_notes);
        EJPojoProperty.clearInitialValue(_openItems);
        EJPojoProperty.clearInitialValue(_plannedItems);
        EJPojoProperty.clearInitialValue(_approvedItems);
        EJPojoProperty.clearInitialValue(_markedFormInvoiceItems);
        EJPojoProperty.clearInitialValue(_displayText);
        EJPojoProperty.clearInitialValue(_maximumHours);
        EJPojoProperty.clearInitialValue(_bookableHours);

        EJPojoProperty.clearInitialValue(_invoiceable);
        EJPojoProperty.clearInitialValue(_fixPrice);

        EJPojoProperty.clearInitialValue(_taskCprId);
        EJPojoProperty.clearInitialValue(_taskNotes);
        EJPojoProperty.clearInitialValue(_taskName);
        EJPojoProperty.clearInitialValue(_taskPayRate);
        EJPojoProperty.clearInitialValue(_taskUserId);
        EJPojoProperty.clearInitialValue(_taskFixPrice);
        EJPojoProperty.clearInitialValue(_taskStatus);
        EJPojoProperty.clearInitialValue(_taskInvoiceable);
    }

}