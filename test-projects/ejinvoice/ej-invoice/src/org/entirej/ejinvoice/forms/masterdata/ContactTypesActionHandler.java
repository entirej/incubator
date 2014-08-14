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
import org.entirej.ejinvoice.forms.constants.F_TIME_ENTRY;
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
 * This class is used to perform all business functionalities including data
 * validations related to CONTACT_TYPES block service.
 */
public class ContactTypesActionHandler extends DefaultFormActionProcessor
{
    @Override
    public void newFormInstance(EJForm form) throws EJActionProcessorException
    {
//        form.getBlock(F_MASTER_DATA.B_CONTACT_TYPES.ID).executeQuery();
    }

    @Override
    public void executeActionCommand(EJForm form, EJRecord record, String command, EJScreenType screenType) throws EJActionProcessorException
    {
        if (F_MASTER_DATA.AC_DELETE_CONTACT_TYPE.equals(command))
        {
            // before deleting the selected record from database validate
            // and check if the record to be deleted has any FK constraints
            // usage with other table data and if so throw an exception and
            // block physical delete
            ServiceRetriever.getDBService(form).validateDeleteRecordUsage(form.getBlock(F_MASTER_DATA.B_CONTACT_TYPES.ID).getFocusedRecord(), "CONTACT_TYPES");
            form.getBlock(F_MASTER_DATA.B_CONTACT_TYPES.ID).askToDeleteCurrentRecord("Are you sure you want to delete this contact type?");
        }
        else if (F_MASTER_DATA.AC_MODIFY_CONTACT.equals(command))
        {
            form.getBlock(F_MASTER_DATA.B_CONTACT_TYPES.ID).enterUpdate();
        }
        else if (F_MASTER_DATA.AC_CREATE_CONTACT_TYPE.equals(command))
        {
            form.getBlock(F_MASTER_DATA.B_CONTACT_TYPES.ID).enterInsert(false);
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

            final EJScreenItem typeItem = form.getBlock(F_MASTER_DATA.B_CONTACT_TYPES.ID).getScreenItem(EJScreenType.MAIN, record.getItem(F_MASTER_DATA.B_CONTACT_TYPES.I_TYPE).getName());
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

}
