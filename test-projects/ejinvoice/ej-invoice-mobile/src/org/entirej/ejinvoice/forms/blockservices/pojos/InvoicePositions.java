package org.entirej.ejinvoice.forms.blockservices.pojos;

import java.math.BigDecimal;
import org.entirej.framework.core.EJFieldName;
import org.entirej.framework.core.service.EJPojoProperty;

public class InvoicePositions
{
    private EJPojoProperty<BigDecimal> _hoursWorked;
    private EJPojoProperty<Integer>    _userId;
    private EJPojoProperty<Integer>    _position;
    private EJPojoProperty<String>     _description;
    private EJPojoProperty<Integer>    _cuprId;
    private EJPojoProperty<Integer>    _id;
    private EJPojoProperty<Integer>    _invId;
    private EJPojoProperty<Integer>    _vatId;

    @EJFieldName("HOURS_WORKED")
    public BigDecimal getHoursWorked()
    {
        return EJPojoProperty.getPropertyValue(_hoursWorked);
    }

    @EJFieldName("HOURS_WORKED")
    public void setHoursWorked(BigDecimal hoursWorked)
    {
        _hoursWorked = EJPojoProperty.setPropertyValue(_hoursWorked, hoursWorked);
    }

    @EJFieldName("HOURS_WORKED")
    public BigDecimal getInitialHoursWorked()
    {
        return EJPojoProperty.getPropertyInitialValue(_hoursWorked);
    }

    @EJFieldName("USER_ID")
    public Integer getUserId()
    {
        return EJPojoProperty.getPropertyValue(_userId);
    }

    @EJFieldName("USER_ID")
    public void setUserId(Integer userId)
    {
        _userId = EJPojoProperty.setPropertyValue(_userId, userId);
    }

    @EJFieldName("USER_ID")
    public Integer getInitialUserId()
    {
        return EJPojoProperty.getPropertyInitialValue(_userId);
    }

    @EJFieldName("POSITION")
    public Integer getPosition()
    {
        return EJPojoProperty.getPropertyValue(_position);
    }

    @EJFieldName("POSITION")
    public void setPosition(Integer position)
    {
        _position = EJPojoProperty.setPropertyValue(_position, position);
    }

    @EJFieldName("POSITION")
    public Integer getInitialPosition()
    {
        return EJPojoProperty.getPropertyInitialValue(_position);
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

    @EJFieldName("CUPR_ID")
    public Integer getCuprId()
    {
        return EJPojoProperty.getPropertyValue(_cuprId);
    }

    @EJFieldName("CUPR_ID")
    public void setCuprId(Integer cuprId)
    {
        _cuprId = EJPojoProperty.setPropertyValue(_cuprId, cuprId);
    }

    @EJFieldName("CUPR_ID")
    public Integer getInitialCuprId()
    {
        return EJPojoProperty.getPropertyInitialValue(_cuprId);
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

    @EJFieldName("INV_ID")
    public Integer getInvId()
    {
        return EJPojoProperty.getPropertyValue(_invId);
    }

    @EJFieldName("INV_ID")
    public void setInvId(Integer invId)
    {
        _invId = EJPojoProperty.setPropertyValue(_invId, invId);
    }

    @EJFieldName("INV_ID")
    public Integer getInitialInvId()
    {
        return EJPojoProperty.getPropertyInitialValue(_invId);
    }

    @EJFieldName("VAT_ID")
    public Integer getVatId()
    {
        return EJPojoProperty.getPropertyValue(_vatId);
    }

    @EJFieldName("VAT_ID")
    public void setVatId(Integer vatId)
    {
        _vatId = EJPojoProperty.setPropertyValue(_vatId, vatId);
    }

    @EJFieldName("VAT_ID")
    public Integer getInitialVatId()
    {
        return EJPojoProperty.getPropertyInitialValue(_vatId);
    }

    public void clearInitialValues()
    {
        EJPojoProperty.clearInitialValue(_hoursWorked);
        EJPojoProperty.clearInitialValue(_userId);
        EJPojoProperty.clearInitialValue(_position);
        EJPojoProperty.clearInitialValue(_description);
        EJPojoProperty.clearInitialValue(_cuprId);
        EJPojoProperty.clearInitialValue(_id);
        EJPojoProperty.clearInitialValue(_invId);
        EJPojoProperty.clearInitialValue(_vatId);
    }

}