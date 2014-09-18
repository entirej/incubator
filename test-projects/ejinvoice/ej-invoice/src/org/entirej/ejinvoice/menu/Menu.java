package org.entirej.ejinvoice.menu;

import org.entirej.framework.core.EJFieldName;
import org.entirej.framework.core.service.EJPojoProperty;

public class Menu
{
    private EJPojoProperty<Object>   _icon;
    private EJPojoProperty<Integer> _parentId;
    private EJPojoProperty<Integer> _id;
    private EJPojoProperty<String>  _name;
    private EJPojoProperty<String>  _iconName;
    private EJPojoProperty<String>  _actionCommand;

    @EJFieldName("parentId")
    public Integer getParentId()
    {
        return EJPojoProperty.getPropertyValue(_parentId);
    }

    @EJFieldName("parentId")
    public void setParentId(Integer parentId)
    {
        _parentId = EJPojoProperty.setPropertyValue(_parentId, parentId);
    }

    @EJFieldName("parentId")
    public Integer getInitialParentId()
    {
        return EJPojoProperty.getPropertyInitialValue(_parentId);
    }

    @EJFieldName("id")
    public Integer getId()
    {
        return EJPojoProperty.getPropertyValue(_id);
    }

    @EJFieldName("id")
    public void setId(Integer id)
    {
        _id = EJPojoProperty.setPropertyValue(_id, id);
    }

    @EJFieldName("id")
    public Integer getInitialId()
    {
        return EJPojoProperty.getPropertyInitialValue(_id);
    }

    @EJFieldName("name")
    public String getName()
    {
        return EJPojoProperty.getPropertyValue(_name);
    }

    @EJFieldName("name")
    public void setName(String name)
    {
        _name = EJPojoProperty.setPropertyValue(_name, name);
    }

    @EJFieldName("name")
    public String getInitialName()
    {
        return EJPojoProperty.getPropertyInitialValue(_name);
    }

    @EJFieldName("iconName")
    public String getIconName()
    {
        return EJPojoProperty.getPropertyValue(_iconName);
    }

    @EJFieldName("iconName")
    public void setIconName(String iconName)
    {
        _iconName = EJPojoProperty.setPropertyValue(_iconName, iconName);
    }

    @EJFieldName("iconName")
    public String getInitialIconName()
    {
        return EJPojoProperty.getPropertyInitialValue(_iconName);
    }

    @EJFieldName("actionCommand")
    public String getActionCommand()
    {
        return EJPojoProperty.getPropertyValue(_actionCommand);
    }

    @EJFieldName("actionCommand")
    public void setActionCommand(String actionCommand)
    {
        _actionCommand = EJPojoProperty.setPropertyValue(_actionCommand, actionCommand);
    }

    @EJFieldName("actionCommand")
    public String getInitialActionCommand()
    {
        return EJPojoProperty.getPropertyInitialValue(_actionCommand);
    }

    @EJFieldName("icon")
    public Object getIcon()
    {
        return EJPojoProperty.getPropertyValue(_icon);
    }

    @EJFieldName("icon")
    public void setIcon(Object icon)
    {
        _icon = EJPojoProperty.setPropertyValue(_icon, icon);
    }

    @EJFieldName("icon")
    public Object getInitialIcon()
    {
        return EJPojoProperty.getPropertyInitialValue(_icon);
    }
    
    public void clearInitialValues()
    {
        EJPojoProperty.clearInitialValue(_parentId);
        EJPojoProperty.clearInitialValue(_id);
        EJPojoProperty.clearInitialValue(_name);
        EJPojoProperty.clearInitialValue(_iconName);
        EJPojoProperty.clearInitialValue(_actionCommand);
        EJPojoProperty.clearInitialValue(_icon);
    }

}