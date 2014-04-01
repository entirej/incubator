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
/**
 * 
 */
package org.entirej.ejinvoice.forms.masterdata;

import org.entirej.ejinvoice.DefaultFormActionProcessor;
import org.entirej.ejinvoice.ServiceRetriever;
import org.entirej.ejinvoice.forms.constants.F_LAUNCH_PAGE;
import org.entirej.ejinvoice.forms.constants.F_MASTER_DATA;
import org.entirej.ejinvoice.referencedlovdefs.constants.RL_CONTACT_TYPES;
import org.entirej.framework.core.EJActionProcessorException;
import org.entirej.framework.core.EJBlock;
import org.entirej.framework.core.EJForm;
import org.entirej.framework.core.EJRecord;
import org.entirej.framework.core.EJScreenItem;
import org.entirej.framework.core.enumerations.EJRecordType;
import org.entirej.framework.core.enumerations.EJScreenType;
import org.entirej.framework.core.service.EJQueryCriteria;
import org.entirej.framework.core.service.EJRestrictions;

/**
 * This class is used to perform all business functionalities including data validations related to
 * CONTACT_TYPES block service.
 */
public class ContactTypesActionHandler extends DefaultFormActionProcessor
{
    @Override
    public void newFormInstance(EJForm form) throws EJActionProcessorException
    {
        // filter the contact type grid to display only records that
        // does not include ContactTypesService.MAIN as type
        EJQueryCriteria queryCriteria = new EJQueryCriteria();
        queryCriteria.add(EJRestrictions.notEquals(RL_CONTACT_TYPES.L_CONTACT_TYPES.I_TYPE, ContactTypesService.MAIN));

        form.getBlock(F_MASTER_DATA.B_CONTACT_TYPES.ID).executeQuery(queryCriteria);
    }

    @Override
    public void executeActionCommand(EJForm form, EJRecord record, String command, EJScreenType screenType) throws EJActionProcessorException
    {
        if (record.getBlockName() != null
                && ((record.getBlockName().equals(F_MASTER_DATA.B_CONTACT_TYPES.ID)) || record.getBlockName().equals(F_MASTER_DATA.B_CONTACT_TYPES_TOOLBAR.ID)))
        {
            if (F_MASTER_DATA.AC_TOOLBAR_NEW.equals(command))
            {
                form.getBlock(F_MASTER_DATA.B_CONTACT_TYPES.ID).enterInsert(false);
                return;
            }
            if (F_MASTER_DATA.AC_TOOLBAR_EDIT.equals(command))
            {
                form.getBlock(F_MASTER_DATA.B_CONTACT_TYPES.ID).enterUpdate();
                return;
            }
            if (F_MASTER_DATA.AC_TOOLBAR_DELETE.equals(command) && form.getBlock(F_MASTER_DATA.B_CONTACT_TYPES.ID).getFocusedRecord() != null)
            {
                // before deleting the selected record from database validate
                // and check if the
                // record to be deleted has any FK constraints usage with other
                // table data and if so throw an exception and block physical
                // delete
                ServiceRetriever.getDBService(form).validateDeleteRecordUsage(form.getBlock(F_MASTER_DATA.B_CONTACT_TYPES.ID).getFocusedRecord(),
                        "CONTACT_TYPES");
                form.getBlock(F_MASTER_DATA.B_CONTACT_TYPES.ID).askToDeleteCurrentRecord("Are you sure you want to delete this type?");
                return;
            }
            if (F_MASTER_DATA.AC_TOOLBAR_HOME.equals(command))
            {
                form.openForm(F_LAUNCH_PAGE.ID);
                return;
            }
        }
    }

    @Override
    public void postDelete(EJForm form, EJRecord record) throws EJActionProcessorException
    {
        // validate the contact types toolbar state after deleting contact type
        // record
        if (F_MASTER_DATA.B_CONTACT_TYPES.ID.equals(form.getBlock(F_MASTER_DATA.B_CONTACT_TYPES.ID).getName()))
        {
            validateToolbarState(form.getBlock(F_MASTER_DATA.B_CONTACT_TYPES_TOOLBAR.ID),
                    form.getBlock(F_MASTER_DATA.B_CONTACT_TYPES.ID).getFocusedRecord() != null);
        }
    }

    @Override
    public void validateRecord(EJForm form, EJRecord record, EJRecordType recordType) throws EJActionProcessorException
    {
        // validate the contact types screen
        if (F_MASTER_DATA.B_CONTACT_TYPES.ID.equals(record.getBlockName()))
        {
            Object type = record.getValue(F_MASTER_DATA.B_CONTACT_TYPES.I_TYPE);
            Object id = record.getValue(F_MASTER_DATA.B_CONTACT_TYPES.I_ID);

            final EJScreenItem typeItem = form.getBlock(F_MASTER_DATA.B_CONTACT_TYPES.ID).getScreenItem(EJScreenType.MAIN,
                    record.getItem(F_MASTER_DATA.B_CONTACT_TYPES.I_TYPE).getName());
            final String typeLabel = typeItem.getLabel();

            if (recordType == EJRecordType.INSERT || recordType == EJRecordType.UPDATE)
            {

                if (type == null || ((String) type).trim().length() == 0)
                {
                    throw new EJActionProcessorException(String.format("%s cannot be Empty!", typeLabel));

                }

                if (ContactTypesService.contactTypeExists(form, (String) type, (Integer) id))

                {
                    throw new EJActionProcessorException(String.format("Entered %s already Exist!", typeLabel));
                }
            }
        }

    }

    @Override
    public void newRecordInstance(EJForm form, EJRecord record) throws EJActionProcessorException
    {
        // validate the toolbar states when
        // entering new record to thecontact types screen
        if (record.getBlockName().equals(F_MASTER_DATA.B_CONTACT_TYPES.ID))
        {
            validateToolbarState(form.getBlock(F_MASTER_DATA.B_CONTACT_TYPES_TOOLBAR.ID), record != null);

        }
    }

    @Override
    public void postBlockQuery(EJForm form, EJBlock block) throws EJActionProcessorException
    {
        // validate the toolbar states after
        // a record is updated, deleted or newly added to the contact types
        // screen
        if (block.getName().equals(F_MASTER_DATA.B_CONTACT_TYPES.ID))
        {
            validateToolbarState(form.getBlock(F_MASTER_DATA.B_CONTACT_TYPES_TOOLBAR.ID),
                    form.getBlock(F_MASTER_DATA.B_CONTACT_TYPES.ID).getFocusedRecord() != null);

        }
    }

}
