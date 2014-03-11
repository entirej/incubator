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
package org.entirej.ejinvoice.forms.customer;

import java.util.ArrayList;
import java.util.List;

import org.entirej.applicationframework.tmt.pages.EJTMTFormPageActions;
import org.entirej.ejinvoice.DefaultFormActionProcessor;
import org.entirej.ejinvoice.PKSequenceService;
import org.entirej.ejinvoice.ServiceRetriever;
import org.entirej.ejinvoice.forms.constants.F_CUSTOMER;
import org.entirej.ejinvoice.forms.masterdata.ContactTypesService;
import org.entirej.framework.core.EJActionProcessorException;
import org.entirej.framework.core.EJBlock;
import org.entirej.framework.core.EJForm;
import org.entirej.framework.core.EJManagedFrameworkConnection;
import org.entirej.framework.core.EJRecord;
import org.entirej.framework.core.EJScreenItem;
import org.entirej.framework.core.actionprocessor.interfaces.EJFormActionProcessor;
import org.entirej.framework.core.enumerations.EJRecordType;
import org.entirej.framework.core.enumerations.EJScreenType;
import org.entirej.framework.core.service.EJQueryCriteria;
import org.entirej.framework.core.service.EJRestrictions;
import org.entirej.framework.core.service.EJStatementExecutor;
import org.entirej.framework.core.service.EJStatementParameter;

/**
 * This class is used to perform all business functionalities including data
 * validations related to CUSTOMER, CUSTOMER_CONTACT & CUSTOMER_PROJECTS block
 * service.
 */
public class CustomerFormActionProcessor extends DefaultFormActionProcessor implements EJFormActionProcessor
{
    @Override
    public void newFormInstance(EJForm form) throws EJActionProcessorException
    {
        super.newFormInstance(form);

        // use the focused customer record to validate the toolbar state
        EJBlock customerBlock = form.getBlock(F_CUSTOMER.B_CUSTOMER.ID);
        customerBlock.executeQuery();
        
        
        EJTMTFormPageActions.setActionEnableByFormId(F_CUSTOMER.ID, F_CUSTOMER.AC_DELETE, false);
    }

    @Override
    public void executeActionCommand(EJForm form, EJRecord record, String command, EJScreenType screenType) throws EJActionProcessorException
    {
        if (form.getFocusedBlock() == null)
        {
            return;
        }
        
        String blockName = form.getFocusedBlock().getName();
        
        if (F_CUSTOMER.AC_NEW.equals(command))
        {
            if (F_CUSTOMER.B_CUSTOMER.ID.equals(blockName))
            {
                form.getBlock(F_CUSTOMER.B_CUSTOMER.ID).enterInsert(false);
            }
            else if (F_CUSTOMER.B_CUSTOMER_CONTACTS.ID.equals(blockName))
            {
                form.getBlock(F_CUSTOMER.B_CUSTOMER_CONTACTS.ID).enterInsert(false);
            }
            return;
        }
        if (F_CUSTOMER.AC_EDIT.equals(command))
        {
            if (F_CUSTOMER.B_CUSTOMER.ID.equals(blockName))
            {
                form.getBlock(F_CUSTOMER.B_CUSTOMER.ID).enterUpdate();
            }
            else if (F_CUSTOMER.B_CUSTOMER_CONTACTS.ID.equals(blockName))
            {
                form.getBlock(F_CUSTOMER.B_CUSTOMER_CONTACTS.ID).enterUpdate();
            }
            return;
        }
        if (F_CUSTOMER.AC_DELETE.equals(command) && form.getBlock(F_CUSTOMER.B_CUSTOMER.ID).getFocusedRecord() != null)
        {
            if (F_CUSTOMER.B_CUSTOMER.ID.equals(blockName))
            {
                ServiceRetriever.getDBService(form).validateDeleteRecordUsage(form.getBlock(F_CUSTOMER.B_CUSTOMER.ID).getFocusedRecord(), "CUSTOMER");
                form.getBlock(F_CUSTOMER.B_CUSTOMER.ID).askToDeleteCurrentRecord("Are you sure you want to delete this customer?");
            }
            else if (F_CUSTOMER.B_CUSTOMER_CONTACTS.ID.equals(blockName))
            {
                ServiceRetriever.getDBService(form).validateDeleteRecordUsage(form.getBlock(F_CUSTOMER.B_CUSTOMER_CONTACTS.ID).getFocusedRecord(), "CUSTOMER_CONTACT");
                form.getBlock(F_CUSTOMER.B_CUSTOMER_CONTACTS.ID).askToDeleteCurrentRecord("Are you sure you want to delete this contact?");
            }

            return;
        }
    }

    /**
     * This method will validate the contact block toolbar to disable if
     * selected record is of type ContactTypesService.MAIN else enable.
     * 
     * @param screenType
     *            - pass the screen type required for validation
     * @param contactBlock
     *            - block containing the fields for validation
     */
    private void validateContactType(EJScreenType screenType, EJBlock contactBlock)
    {
        
        
        
        // if (screenType != null && contactBlock != null)
        // {
        // EJRecord currentUpdateRecord =
        // contactBlock.getCurrentScreenRecord(screenType);
        // if (currentUpdateRecord != null &&
        // currentUpdateRecord.getValue(F_MIAP001.B_CUSTOMER_CONTACT_BLOCK.I_CONTACT_TYPE_NAME)
        // != null)
        // {
        // String type = (String)
        // currentUpdateRecord.getValue(F_MIAP001.B_CUSTOMER_CONTACT_BLOCK.I_CONTACT_TYPE_NAME);
        // if (ContactTypesService.MAIN.equalsIgnoreCase(type))
        // {
        // contactBlock.getScreenItem(EJScreenType.UPDATE,
        // F_MIAP001.B_CUSTOMER_CONTACT_BLOCK.I_CONTACT_TYPE_NAME).setEditable(false);
        // }
        // else
        // {
        // contactBlock.getScreenItem(EJScreenType.UPDATE,
        // F_MIAP001.B_CUSTOMER_CONTACT_BLOCK.I_CONTACT_TYPE_NAME).setEditable(true);
        // }
        //
        // }
        // }
    }

    @Override
    public void postInsert(EJForm form, EJRecord record) throws EJActionProcessorException
    {
        // super.postInsert(form, record);
        //
        // // Each customer must contain the DEFAULT customer contact. So
        // if
        // (form.getBlock(F_MIAP001.B_CUSTOMER_BLOCK.ID).getName().equals(record.getBlockName()))
        // {
        // // after persisting a new customer record in the db add a default
        // customer contact
        // createDefaultCustomerContact(form, record);
        // }
    }

    /**
     * @param form
     *            - CUSTOMER_CONTACTS(child table of CUSTOMER) form
     * @param record
     *            - customer record
     * 
     * @throws EJActionProcessorException
     */
    private void createDefaultCustomerContact(EJForm form, EJRecord record) throws EJActionProcessorException
    {

        // final EJManagedFrameworkConnection connection = form.getConnection();
        // EJBlock customerBlock = form.getBlock(F_MIAP001.B_CUSTOMER_BLOCK.ID);
        // String error = null;
        //
        // try
        // {
        // // all open changes within the form is saved(BlockServiceItem values)
        // except non
        // // BlockServiceItem values. Used this to get the FK from CUSTOMER
        // table before saving
        // // CUSTOMER_CONTACTS(child table of CUSTOMER)
        // form.saveChanges();
        //
        // // get the customer id from the parent record
        // final int customerId = (int)
        // record.getValue(F_MIAP001.B_CUSTOMER_BLOCK.I_ID);
        //
        // final String lastName = (String)
        // customerBlock.getScreenItem(EJScreenType.INSERT,
        // F_MIAP001.B_CUSTOMER_BLOCK.I_LAST_NAME).getValue();
        //
        // try
        // {
        //
        // // validate last name as it is required
        // if (lastName == null || lastName.trim().length() == 0)
        // {
        // error = ("Contact Last Name cannot be Empty!");
        // }
        // }
        // finally
        // {
        // if (error != null && error.length() > 0)
        // {
        // throw new EJActionProcessorException(error);
        // }
        // }
        //
        // EJStatementExecutor statementExecutor = new EJStatementExecutor();
        //
        // // create club
        // List<EJStatementParameter> custContactParams = new
        // ArrayList<EJStatementParameter>();
        // custContactParams.clear();
        //
        // // call PK sequence service
        // final int id = PKSequenceService.getPKSequence(connection);
        // // set the customer id
        // custContactParams.add(new EJStatementParameter("CUST_ID",
        // Integer.class, customerId));
        // // set the new record id(primary key)
        // custContactParams.add(new EJStatementParameter("ID", Integer.class,
        // id));
        // custContactParams.add(new EJStatementParameter("SALUT_ID",
        // Integer.class,
        // customerBlock.getCurrentScreenRecord(EJScreenType.MAIN).getValue(
        // F_MIAP001.B_CUSTOMER_BLOCK.I_SALUT_ID)));
        //
        // custContactParams.add(new EJStatementParameter("FIRST_NAME",
        // String.class, customerBlock.getScreenItem(EJScreenType.INSERT,
        // F_MIAP001.B_CUSTOMER_BLOCK.I_FIRST_NAME).getValue()));
        // custContactParams.add(new EJStatementParameter("LAST_NAME",
        // String.class, customerBlock.getScreenItem(EJScreenType.INSERT,
        // F_MIAP001.B_CUSTOMER_BLOCK.I_LAST_NAME).getValue()));
        // custContactParams.add(new EJStatementParameter("PHONE", String.class,
        // customerBlock.getScreenItem(EJScreenType.INSERT,
        // F_MIAP001.B_CUSTOMER_BLOCK.I_PHONE).getValue()));
        // custContactParams.add(new EJStatementParameter("MOBILE",
        // String.class, customerBlock.getScreenItem(EJScreenType.INSERT,
        // F_MIAP001.B_CUSTOMER_BLOCK.I_MOBILE).getValue()));
        // custContactParams.add(new EJStatementParameter("EMAIL", String.class,
        // customerBlock.getScreenItem(EJScreenType.INSERT,
        // F_MIAP001.B_CUSTOMER_BLOCK.I_EMAIL).getValue()));
        //
        // // Retrieve the main contact type and this to our new customer
        // contact record
        // ContactTypes mainContactType =
        // ContactTypesService.getMainContactType(connection);
        // custContactParams.add(new EJStatementParameter("CONTACT_TYPE",
        // Integer.class, mainContactType.getId()));
        //
        // // Insert the new Customer Contact
        // statementExecutor.executeInsert(connection, "CUSTOMER_CONTACT",
        // custContactParams.toArray(new
        // EJStatementParameter[custContactParams.size()]));
        //
        // // commit the database transaction
        // connection.commit();
        //
        // }
        // catch (Exception e)
        // {
        // // revert the database transaction and throw exception
        // connection.rollback();
        // throw new EJActionProcessorException(e.getMessage());
        // }
        // finally
        // {
        //
        // // close the database connection
        // connection.close();
        // if (error == null)
        // {
        // // if no errors query the customer block again to load fresh set of
        // data
        // form.getBlock(F_MIAP001.B_CUSTOMER_BLOCK.ID).executeQuery();
        // }
        // }

    }

    @Override
    public void postDelete(EJForm form, EJRecord record) throws EJActionProcessorException
    {
        // super.postDelete(form, record);
        // form.saveChanges();
        //
        // // use the focused customer record to validate the toolbar state
        // after a
        // // customer, customer_contact or customer_project record has been
        // // deleted
        // if (F_MIAP001.B_CUSTOMER_BLOCK.ID.equals(record.getBlockName()))
        // {
        // validateToolbarState(form.getBlock(F_MIAP001.B_CUSTOMER_TOOLBAR.ID),
        // form.getBlock(F_MIAP001.B_CUSTOMER_BLOCK.ID).getFocusedRecord() !=
        // null);
        //
        // validateToolbarCreateState(form.getBlock(F_MIAP001.B_CUSTOMER_CONTACT_TOOLBAR.ID),
        // form.getBlock(F_MIAP001.B_CUSTOMER_BLOCK.ID).getFocusedRecord() !=
        // null);
        // validateToolbarState(form.getBlock(F_MIAP001.B_CUSTOMER_CONTACT_TOOLBAR.ID),
        // form.getBlock(F_MIAP001.B_CUSTOMER_CONTACT_BLOCK.ID)
        // .getFocusedRecord() != null &&
        // form.getBlock(F_MIAP001.B_CUSTOMER_BLOCK.ID).getFocusedRecord() !=
        // null);
        //
        // validateToolbarCreateState(form.getBlock(F_MIAP001.B_CUSTOMER_PROJECTS_TOOLBAR.ID),
        // form.getBlock(F_MIAP001.B_CUSTOMER_BLOCK.ID).getFocusedRecord() !=
        // null);
        // validateToolbarState(form.getBlock(F_MIAP001.B_CUSTOMER_PROJECTS_TOOLBAR.ID),
        // form.getBlock(F_MIAP001.B_CUSTOMER_PROJECTS_BLOCK.ID)
        // .getFocusedRecord() != null &&
        // form.getBlock(F_MIAP001.B_CUSTOMER_BLOCK.ID).getFocusedRecord() !=
        // null);
        // }
        //
        // if
        // (F_MIAP001.B_CUSTOMER_CONTACT_BLOCK.ID.equals(record.getBlockName()))
        // {
        // validateToolbarState(form.getBlock(F_MIAP001.B_CUSTOMER_CONTACT_TOOLBAR.ID),
        // form.getBlock(F_MIAP001.B_CUSTOMER_CONTACT_BLOCK.ID)
        // .getFocusedRecord() != null &&
        // form.getBlock(F_MIAP001.B_CUSTOMER_BLOCK.ID).getFocusedRecord() !=
        // null);
        // }
        // if
        // (F_MIAP001.B_CUSTOMER_PROJECTS_BLOCK.ID.equals(record.getBlockName()))
        // {
        // validateToolbarState(form.getBlock(F_MIAP001.B_CUSTOMER_PROJECTS_TOOLBAR.ID),
        // form.getBlock(F_MIAP001.B_CUSTOMER_PROJECTS_BLOCK.ID)
        // .getFocusedRecord() != null &&
        // form.getBlock(F_MIAP001.B_CUSTOMER_BLOCK.ID).getFocusedRecord() !=
        // null);
        // }

    }

    @Override
    public void validateRecord(EJForm form, EJRecord record, EJRecordType recordType) throws EJActionProcessorException
    {
        //
        // // check the screen type using record type to get the correct screen
        // // items for record validation
        // EJScreenType screenType = EJScreenType.MAIN;
        // switch (recordType)
        // {
        // case INSERT:
        // {
        // screenType = EJScreenType.INSERT;
        // break;
        // }
        // case UPDATE:
        // {
        // screenType = EJScreenType.UPDATE;
        // break;
        // }
        // default:
        // break;
        // }
        //
        // // validate the customer screen
        // if (F_MIAP001.B_CUSTOMER_BLOCK.ID.equals(record.getBlockName()))
        // {
        // final Object name =
        // record.getValue(F_MIAP001.B_CUSTOMER_BLOCK.I_NAME);
        // final Object addrLine1 =
        // record.getValue(F_MIAP001.B_CUSTOMER_BLOCK.I_ADDRESS_LINE_1);
        // final Object postCode =
        // record.getValue(F_MIAP001.B_CUSTOMER_BLOCK.I_POST_CODE);
        // final Object town =
        // record.getValue(F_MIAP001.B_CUSTOMER_BLOCK.I_TOWN);
        //
        // if (recordType == EJRecordType.INSERT || recordType ==
        // EJRecordType.UPDATE)
        // {
        //
        // final EJScreenItem nameItem =
        // form.getBlock(F_MIAP001.B_CUSTOMER_BLOCK.ID).getScreenItem(screenType,
        // record.getItem(F_MIAP001.B_CUSTOMER_BLOCK.I_NAME).getName());
        // final EJScreenItem addrLine1Item =
        // form.getBlock(F_MIAP001.B_CUSTOMER_BLOCK.ID).getScreenItem(screenType,
        // record.getItem(F_MIAP001.B_CUSTOMER_BLOCK.I_ADDRESS_LINE_1).getName());
        // final EJScreenItem postcodeItem =
        // form.getBlock(F_MIAP001.B_CUSTOMER_BLOCK.ID).getScreenItem(screenType,
        // record.getItem(F_MIAP001.B_CUSTOMER_BLOCK.I_POST_CODE).getName());
        // final EJScreenItem townItem =
        // form.getBlock(F_MIAP001.B_CUSTOMER_BLOCK.ID).getScreenItem(screenType,
        // record.getItem(F_MIAP001.B_CUSTOMER_BLOCK.I_TOWN).getName());
        //
        // String nameError = validateRequiredField(name, nameItem);
        // if (nameError != null && nameError.length() > 0)
        // {
        // throw new EJActionProcessorException(nameError);
        // }
        // String addrLine1Error = validateRequiredField(addrLine1,
        // addrLine1Item);
        // if (addrLine1Error != null && addrLine1Error.length() > 0)
        // {
        // throw new EJActionProcessorException(addrLine1Error);
        // }
        // String postcodeError = validateRequiredField(postCode, postcodeItem);
        // if (postcodeError != null && postcodeError.length() > 0)
        // {
        // throw new EJActionProcessorException(postcodeError);
        // }
        // String townError = validateRequiredField(town, townItem);
        // if (townError != null && townError.length() > 0)
        // {
        // throw new EJActionProcessorException(townError);
        // }
        //
        // }
        //
        // }
        // // validate the customer contacts screen
        // else if
        // (F_MIAP001.B_CUSTOMER_CONTACT_BLOCK.ID.equals(record.getBlockName()))
        // {
        // final Object contactType =
        // record.getValue(F_MIAP001.B_CUSTOMER_CONTACT_BLOCK.I_CONTACT_TYPE_NAME);
        // final Object lastName =
        // record.getValue(F_MIAP001.B_CUSTOMER_CONTACT_BLOCK.I_LAST_NAME);
        //
        // if (recordType == EJRecordType.INSERT || recordType ==
        // EJRecordType.UPDATE)
        // {
        //
        // final EJScreenItem contactTypeItem =
        // form.getBlock(F_MIAP001.B_CUSTOMER_CONTACT_BLOCK.ID).getScreenItem(screenType,
        // record.getItem(F_MIAP001.B_CUSTOMER_CONTACT_BLOCK.I_CONTACT_TYPE_NAME).getName());
        // final EJScreenItem lastNameItem =
        // form.getBlock(F_MIAP001.B_CUSTOMER_CONTACT_BLOCK.ID).getScreenItem(screenType,
        // record.getItem(F_MIAP001.B_CUSTOMER_CONTACT_BLOCK.I_LAST_NAME).getName());
        //
        // String contactTypeError = validateRequiredField(contactType,
        // contactTypeItem);
        // if (contactTypeError != null && contactTypeError.length() > 0)
        // {
        // throw new EJActionProcessorException(contactTypeError);
        // }
        // String lastNamrError = validateRequiredField(lastName, lastNameItem);
        // if (lastNamrError != null && lastNamrError.length() > 0)
        // {
        // throw new EJActionProcessorException(lastNamrError);
        // }
        //
        // }
        // }
        // // validate the customer projects screen
        // else if
        // (F_MIAP001.B_CUSTOMER_PROJECTS_BLOCK.ID.equals(record.getBlockName()))
        // {
        // final Object name =
        // record.getValue(F_MIAP001.B_CUSTOMER_PROJECTS_BLOCK.I_NAME);
        // final Object payRate =
        // record.getValue(F_MIAP001.B_CUSTOMER_PROJECTS_BLOCK.I_PAY_RATE);
        //
        // if (recordType == EJRecordType.INSERT || recordType ==
        // EJRecordType.UPDATE)
        // {
        //
        // final EJScreenItem nameItem =
        // form.getBlock(F_MIAP001.B_CUSTOMER_PROJECTS_BLOCK.ID).getScreenItem(screenType,
        // record.getItem(F_MIAP001.B_CUSTOMER_PROJECTS_BLOCK.I_NAME).getName());
        // final EJScreenItem payRateItem =
        // form.getBlock(F_MIAP001.B_CUSTOMER_PROJECTS_BLOCK.ID).getScreenItem(screenType,
        // record.getItem(F_MIAP001.B_CUSTOMER_PROJECTS_BLOCK.I_PAY_RATE).getName());
        //
        // String nameError = validateRequiredField(name, nameItem);
        // if (nameError != null && nameError.length() > 0)
        // {
        // throw new EJActionProcessorException(nameError);
        // }
        // String payRateError = validateRequiredField(payRate, payRateItem);
        // if (payRateError != null && payRateError.length() > 0)
        // {
        // throw new EJActionProcessorException(payRateError);
        // }
        //
        // }
        // }

    }

    private String validateRequiredField(final Object value, final EJScreenItem screenItem) throws EJActionProcessorException
    {

        if (screenItem == null)
        {
            throw new EJActionProcessorException(String.format("screenItem cannot be null !"));
        }

        final String label = screenItem.getLabel();

        if (value == null)
        {
            return String.format("%s cannot be Empty!", label);

        }

        if (value instanceof String)
        {
            if (((String) value).trim().length() == 0)
            {
                return String.format("%s cannot be Empty!", label);

            }
        }

        return null;
    }

    @Override
    public void validateQueryCriteria(EJForm form, EJQueryCriteria queryCriteria) throws EJActionProcessorException
    {
        // // filter the customer contact type Lov to display only records that
        // // does not include ContactTypesService.MAIN as type
        // if (queryCriteria.isUsedInLov() &&
        // F_MIAP001.L_CONTACT_TYPES.ID.equals(queryCriteria.getLovName()))
        // {
        // queryCriteria.add(EJRestrictions.notEquals(F_MIAP001.L_CONTACT_TYPES.I_TYPE,
        // ContactTypesService.MAIN));
        // }
    }

    @Override
    public void newRecordInstance(EJForm form, EJRecord record) throws EJActionProcessorException
    {
        // // use the focused customer record to validate all the toolbar states
        // // when
        // // entering new record
        // EJBlock customersBlock =
        // form.getBlock(F_MIAP001.B_CUSTOMER_BLOCK.ID);
        // EJRecord customerRecord = customersBlock.getFocusedRecord();
        //
        // if (record.getBlockName().equals(F_MIAP001.B_CUSTOMER_BLOCK.ID))
        // {
        // validateToolbarState(form.getBlock(F_MIAP001.B_CUSTOMER_TOOLBAR.ID),
        // record != null);
        //
        // validateToolbarCreateState(form.getBlock(F_MIAP001.B_CUSTOMER_CONTACT_TOOLBAR.ID),
        // record != null && customerRecord != null);
        // validateToolbarState(form.getBlock(F_MIAP001.B_CUSTOMER_CONTACT_TOOLBAR.ID),
        // form.getBlock(F_MIAP001.B_CUSTOMER_CONTACT_BLOCK.ID)
        // .getFocusedRecord() != null && customerRecord != null);
        //
        // updateCustomerContactToolbar(form,
        // form.getBlock(F_MIAP001.B_CUSTOMER_CONTACT_BLOCK.ID).getFocusedRecord());
        //
        // validateToolbarCreateState(form.getBlock(F_MIAP001.B_CUSTOMER_PROJECTS_TOOLBAR.ID),
        // record != null && customerRecord != null);
        // validateToolbarState(form.getBlock(F_MIAP001.B_CUSTOMER_PROJECTS_TOOLBAR.ID),
        // form.getBlock(F_MIAP001.B_CUSTOMER_PROJECTS_BLOCK.ID)
        // .getFocusedRecord() != null && customerRecord != null);
        // }
        //
        // if
        // (record.getBlockName().equals(F_MIAP001.B_CUSTOMER_CONTACT_BLOCK.ID))
        // {
        // validateToolbarState(form.getBlock(F_MIAP001.B_CUSTOMER_CONTACT_TOOLBAR.ID),
        // record != null && customerRecord != null);
        // updateCustomerContactToolbar(form, record);
        // }
        // if
        // (record.getBlockName().equals(F_MIAP001.B_CUSTOMER_PROJECTS_BLOCK.ID))
        // {
        // validateToolbarState(form.getBlock(F_MIAP001.B_CUSTOMER_PROJECTS_TOOLBAR.ID),
        // record != null && customerRecord != null);
        // }

    }

    @Override
    public void postBlockQuery(EJForm form, EJBlock block) throws EJActionProcessorException
    {
        // // use the focused customer record to validate all the toolbar states
        // // after a record is updated, deleted or newly added
        // EJBlock customersBlock =
        // form.getBlock(F_MIAP001.B_CUSTOMER_BLOCK.ID);
        // EJRecord customerRecord = customersBlock.getFocusedRecord();
        //
        // if (block.getName().equals(F_MIAP001.B_CUSTOMER_BLOCK.ID))
        // {
        // validateToolbarState(form.getBlock(F_MIAP001.B_CUSTOMER_TOOLBAR.ID),
        // form.getBlock(F_MIAP001.B_CUSTOMER_BLOCK.ID).getFocusedRecord() !=
        // null
        // && customerRecord != null);
        //
        // validateToolbarCreateState(form.getBlock(F_MIAP001.B_CUSTOMER_PROJECTS_TOOLBAR.ID),
        // form.getBlock(F_MIAP001.B_CUSTOMER_BLOCK.ID).getFocusedRecord() !=
        // null && customerRecord != null);
        // validateToolbarState(form.getBlock(F_MIAP001.B_CUSTOMER_PROJECTS_TOOLBAR.ID),
        // form.getBlock(F_MIAP001.B_CUSTOMER_PROJECTS_BLOCK.ID)
        // .getFocusedRecord() != null && customerRecord != null);
        //
        // validateToolbarCreateState(form.getBlock(F_MIAP001.B_CUSTOMER_CONTACT_TOOLBAR.ID),
        // form.getBlock(F_MIAP001.B_CUSTOMER_BLOCK.ID).getFocusedRecord() !=
        // null && customerRecord != null);
        // validateToolbarState(form.getBlock(F_MIAP001.B_CUSTOMER_CONTACT_TOOLBAR.ID),
        // form.getBlock(F_MIAP001.B_CUSTOMER_CONTACT_BLOCK.ID)
        // .getFocusedRecord() != null && customerRecord != null);
        // updateCustomerContactToolbar(form,
        // form.getBlock(F_MIAP001.B_CUSTOMER_CONTACT_BLOCK.ID).getFocusedRecord());
        //
        // }
        //
        // if (block.getName().equals(F_MIAP001.B_CUSTOMER_PROJECTS_BLOCK.ID))
        // {
        // validateToolbarState(form.getBlock(F_MIAP001.B_CUSTOMER_PROJECTS_TOOLBAR.ID),
        // form.getBlock(F_MIAP001.B_CUSTOMER_PROJECTS_BLOCK.ID)
        // .getFocusedRecord() != null && customerRecord != null);
        // }
        // if (block.getName().equals(F_MIAP001.B_CUSTOMER_CONTACT_BLOCK.ID))
        // {
        // validateToolbarState(form.getBlock(F_MIAP001.B_CUSTOMER_CONTACT_TOOLBAR.ID),
        // form.getBlock(F_MIAP001.B_CUSTOMER_CONTACT_BLOCK.ID)
        // .getFocusedRecord() != null && customerRecord != null);
        // updateCustomerContactToolbar(form,
        // form.getBlock(F_MIAP001.B_CUSTOMER_CONTACT_BLOCK.ID).getFocusedRecord());
        // }
    }

    /**
     * This method is used to validate the customer contact toolbar to
     * disable/enable delete button/link state depending on the selected contact
     * records contact type
     * 
     * @param form
     *            - customer contact form
     * @param record
     *            - customer contact record
     */
    private void updateCustomerContactToolbar(EJForm form, EJRecord record)
    {
        //
        // if (record != null &&
        // F_MIAP001.B_CUSTOMER_CONTACT_BLOCK.ID.equals(record.getBlockName()))
        // {
        //
        // if (form.getBlock(F_MIAP001.B_CUSTOMER_CONTACT_TOOLBAR.ID) != null)
        // {
        // EJBlock customerContactBlock =
        // form.getBlock(F_MIAP001.B_CUSTOMER_CONTACT_BLOCK.ID);
        // final EJRecord contactRecord =
        // customerContactBlock.getFocusedRecord();
        //
        // if (contactRecord != null)
        // {
        // boolean canDelete =
        // ContactTypesService.MAIN.equals(contactRecord.getValue(F_MIAP001.B_CUSTOMER_CONTACT_BLOCK.I_CONTACT_TYPE_NAME));
        // EJBlock toolbarBlock =
        // form.getBlock(F_MIAP001.B_CUSTOMER_CONTACT_TOOLBAR.ID);
        // toolbarBlock.getScreenItem(EJScreenType.MAIN,
        // F_MIAP001.B_CUSTOMER_CONTACT_TOOLBAR.I_DELETE).setEditable(!canDelete);
        // }
        // }
        // }

    }

}
