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
import org.entirej.framework.core.EJForm;
import org.entirej.framework.core.EJRecord;
import org.entirej.framework.core.EJScreenItem;
import org.entirej.framework.core.enumerations.EJRecordType;
import org.entirej.framework.core.enumerations.EJScreenType;

/**
 * This class is used to perform all business functionalities including data
 * validations related to PAYMENT_INFORMATION block service.
 */
public class PaymentTermsActionHandler extends DefaultFormActionProcessor
{
    int   orderKey = 0;
    
    @Override
    public void newFormInstance(EJForm form) throws EJActionProcessorException
    {
//        form.getBlock(F_MASTER_DATA.B_PAYMENT_TERMS.ID).executeQuery();
    }

    
    
    @Override
    public void postQuery(EJForm form, EJRecord record) throws EJActionProcessorException
    {
        if (F_MASTER_DATA.B_PROJECT_STATUS.ID.equals(record.getBlockName()))
        {
            if (((Integer)record.getValue(F_MASTER_DATA.B_PROJECT_STATUS.I_ORDER_KEY)) > orderKey)
            {
                orderKey = (Integer)record.getValue(F_MASTER_DATA.B_PROJECT_STATUS.I_ORDER_KEY);
            }
        }
    }

    


    @Override
    public void executeActionCommand(EJForm form, EJRecord record, String command, EJScreenType screenType) throws EJActionProcessorException
    {
        if (F_MASTER_DATA.AC_DELETE_PAYMENT_TERM.equals(command))
        {
            // before deleting the selected record from database validate
            // and check if the record to be deleted has any FK constraints
            // usage with other table data and if so throw an exception and
            // block physical delete
            ServiceRetriever.getDBService(form).validateDeleteRecordUsage(form.getBlock(F_MASTER_DATA.B_PAYMENT_TERMS.ID).getFocusedRecord(), "PAYMENT_INFORMATION");
            form.getBlock(F_MASTER_DATA.B_PAYMENT_TERMS.ID).askToDeleteCurrentRecord("Are you sure you want to delete this payment term?");
            return;
        }
        else if (F_MASTER_DATA.AC_MODIFY_PAYMENT_TERM.equals(command))
        {
            form.getBlock(F_MASTER_DATA.B_PAYMENT_TERMS.ID).enterUpdate();
        }
        else if (F_MASTER_DATA.AC_CREATE_PAYMENT_TERM.equals(command))
        {
            form.getBlock(F_MASTER_DATA.B_PAYMENT_TERMS.ID).enterInsert(false);
        }
    }

    @Override
    public void validateRecord(EJForm form, EJRecord record, EJRecordType recordType) throws EJActionProcessorException
    {
        if (F_MASTER_DATA.B_PROJECT_STATUS.ID.equals(record.getBlockName()))
        {
            orderKey += 1;
            record.setValue(F_MASTER_DATA.B_PROJECT_STATUS.I_ORDER_KEY, orderKey);
        }
        
        // validate the payment information screen
        if (F_MASTER_DATA.B_PAYMENT_TERMS.ID.equals(record.getBlockName()))
        {
            Object value = record.getValue(F_MASTER_DATA.B_PAYMENT_TERMS.I_PAYMENT_TERMS);
            final EJScreenItem screenItem = form.getBlock(F_MASTER_DATA.B_PAYMENT_TERMS.ID).getScreenItem(EJScreenType.MAIN, record.getItem(F_MASTER_DATA.B_PAYMENT_TERMS.I_PAYMENT_TERMS).getName());
            final String termsLabel = screenItem.getLabel();

            Object id = record.getValue(F_MASTER_DATA.B_PAYMENT_TERMS.I_ID);

            if (recordType == EJRecordType.INSERT || recordType == EJRecordType.UPDATE)
            {
                if (value == null || ((String) value).trim().length() == 0)
                {
                    throw new EJActionProcessorException(String.format("%s cannot be Empty!", termsLabel));
                }

                String term = (String) value;

                if (PaymentTermsService.paymentTermExists(form, term, (Integer) id))
                {
                    throw new EJActionProcessorException(String.format("Entered %s already Exist!", termsLabel));
                }
            }
        }
    }
}
