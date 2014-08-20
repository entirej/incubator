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
package org.entirej.ejinvoice;

import org.entirej.constants.EJ_PROPERTIES;
import org.entirej.framework.core.EJActionProcessorException;
import org.entirej.framework.core.EJForm;
import org.entirej.framework.core.EJRecord;
import org.entirej.framework.core.actionprocessor.EJDefaultFormActionProcessor;
import org.entirej.framework.core.service.EJQueryCriteria;
import org.entirej.framework.core.service.EJRestrictions;

/**
 * This is a custom class that extends
 * <code>EJDefaultFormActionProcessor.</code> Contains mandatory business
 * methods required to any FormActionProcessor extends this class.
 */
public class DefaultFormActionProcessor extends EJDefaultFormActionProcessor
{
    @Override
    public void validateQueryCriteria(EJForm form, EJQueryCriteria queryCriteria) throws EJActionProcessorException
    {
        // Don't do anything if query criteria is used in an Lov
        if (queryCriteria.isUsedInLov())
        {
            return;
        }
        
        if (!queryCriteria.containsRestriction("COMPANY_ID"))
        {
            Integer companyId = (Integer)form.getApplicationLevelParameter(EJ_PROPERTIES.P_COMPANY_ID).getValue();
            queryCriteria.add(EJRestrictions.equals("COMPANY_ID", companyId));
        }
    }

    
    
    @Override
    public void preInsert(EJForm form, EJRecord record) throws EJActionProcessorException
    {
        // Don't do anything if this is a control block
        if (form.getBlock(record.getBlockName()).isControlBlock())
        {
            return;
        }

        if (record.containsItem("ID"))
        {
            // auto generated integer sequence number to set to the primary key
            // of record
            final int idSeqNextval = PKSequenceService.getPKSequence(form.getConnection());
            record.setValue("ID", idSeqNextval);
        }
    }
}
