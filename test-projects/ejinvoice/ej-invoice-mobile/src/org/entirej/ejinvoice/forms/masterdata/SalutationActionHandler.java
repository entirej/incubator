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
import org.entirej.ejinvoice.forms.constants.F_MASTER_DATA;
import org.entirej.framework.core.EJActionProcessorException;
import org.entirej.framework.core.EJBlock;
import org.entirej.framework.core.EJForm;
import org.entirej.framework.core.EJRecord;
import org.entirej.framework.core.enumerations.EJRecordType;
import org.entirej.framework.core.enumerations.EJScreenType;

/**
 * This class is used to perform all business functionalities including data
 * validations related to SALUTATIONS block service.
 */
public class SalutationActionHandler extends DefaultFormActionProcessor
{
    @Override
    public void newFormInstance(EJForm form) throws EJActionProcessorException
    {
        form.getBlock(F_MASTER_DATA.B_SALUTATIONS.ID).executeQuery();
    }

    @Override
    public void executeActionCommand(EJForm form, EJRecord record, String command, EJScreenType screenType) throws EJActionProcessorException
    {
        if (F_MASTER_DATA.AC_NEW.equals(command))
        {
            form.getBlock(F_MASTER_DATA.B_SALUTATIONS.ID).enterInsert(false);
            return;
        }
        if (F_MASTER_DATA.AC_EDIT.equals(command))
        {
            form.getBlock(F_MASTER_DATA.B_SALUTATIONS.ID).enterUpdate();
            return;
        }
        if (F_MASTER_DATA.AC_DELETE.equals(command) && form.getBlock(F_MASTER_DATA.B_SALUTATIONS.ID).getFocusedRecord() != null)
        {
            // before deleting the selected record from database validate and
            // check if the
            // record to be deleted has any FK constraints usage with other
            // table data and if so
            // throw an exception and block physical delete
            ServiceRetriever.getDBService(form).validateDeleteRecordUsage(form.getBlock(F_MASTER_DATA.B_SALUTATIONS.ID).getFocusedRecord(), "SALUTATIONS");
            form.getBlock(F_MASTER_DATA.B_SALUTATIONS.ID).askToDeleteCurrentRecord("Are you sure you want to delete this salutation?");
            return;
        }
    }

    @Override
    public void postDelete(EJForm form, EJRecord record) throws EJActionProcessorException
    {
        // // validate the salutations toolbar state after deleting salutations
        // record
        // if (F_MASTER_DATA.B_SALUTATIONS.ID.equals(record.getBlockName()))
        // {
        // validateToolbarState(form.getBlock(F_MASTER_DATA.B_SALUTATIONS_TOOLBAR.ID),
        // form.getBlock(F_MASTER_DATA.B_SALUTATIONS.ID).getFocusedRecord() !=
        // null);
        // }
    }

    @Override
    public void validateRecord(EJForm form, EJRecord record, EJRecordType recordType) throws EJActionProcessorException
    {
        // // validate the salutations screen
        // if (F_MASTER_DATA.B_SALUTATIONS.ID.equals(record.getBlockName()))
        // {
        // Object value = record.getValue(F_MASTER_DATA.B_SALUTATIONS.I_VALUE);
        // Object id = record.getValue(F_MASTER_DATA.B_SALUTATIONS.I_ID);
        // final EJScreenItem screenItem =
        // form.getBlock(F_MASTER_DATA.B_SALUTATIONS.ID).getScreenItem(EJScreenType.MAIN,
        // record.getItem(F_MASTER_DATA.B_SALUTATIONS.I_VALUE).getName());
        // final String salutationLabel = screenItem.getLabel();
        //
        // if (recordType == EJRecordType.INSERT || recordType ==
        // EJRecordType.UPDATE)
        // {
        //
        // if (value == null || ((String) value).trim().length() == 0)
        // {
        // throw new
        // EJActionProcessorException(String.format("%s cannot be Empty!",
        // salutationLabel));
        // }
        //
        // String salutation = (String) value;
        //
        // if (SalutationService.salutationExists(form, salutation, (Integer)
        // id))
        // {
        // throw new
        // EJActionProcessorException(String.format("Entered %s already Exist!",
        // salutationLabel));
        // }
        //
        // }
        //
        // }
    }

    @Override
    public void newRecordInstance(EJForm form, EJRecord record) throws EJActionProcessorException
    {
        // // validate the toolbar states when
        // // entering new record to the salutations screen
        // if (record.getBlockName().equals(F_MASTER_DATA.B_SALUTATIONS.ID))
        // {
        // validateToolbarState(form.getBlock(F_MASTER_DATA.B_SALUTATIONS_TOOLBAR.ID),
        // record != null);
        //
        // }
    }

    @Override
    public void postBlockQuery(EJForm form, EJBlock block) throws EJActionProcessorException
    {
        // // validate the toolbar states after
        // // a record is updated, deleted or newly added to the salutations
        // screen
        // if (block.getName().equals(F_MASTER_DATA.B_SALUTATIONS.ID))
        // {
        // validateToolbarState(form.getBlock(F_MASTER_DATA.B_SALUTATIONS_TOOLBAR.ID),
        // form.getBlock(F_MASTER_DATA.B_SALUTATIONS.ID).getFocusedRecord() !=
        // null);
        //
        // }
    }

}
