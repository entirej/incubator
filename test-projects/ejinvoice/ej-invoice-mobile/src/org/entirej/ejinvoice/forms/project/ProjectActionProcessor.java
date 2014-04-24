package org.entirej.ejinvoice.forms.project;

import org.entirej.ejinvoice.DefaultFormActionProcessor;
import org.entirej.ejinvoice.ServiceRetriever;
import org.entirej.ejinvoice.forms.constants.F_PROJECT;
import org.entirej.framework.core.EJActionProcessorException;
import org.entirej.framework.core.EJForm;
import org.entirej.framework.core.EJMessage;
import org.entirej.framework.core.EJRecord;
import org.entirej.framework.core.actionprocessor.interfaces.EJFormActionProcessor;
import org.entirej.framework.core.data.controllers.EJQuestion;
import org.entirej.framework.core.enumerations.EJQuestionButton;
import org.entirej.framework.core.enumerations.EJScreenType;

public class ProjectActionProcessor extends DefaultFormActionProcessor implements EJFormActionProcessor
{

    public static final String QUESTION_ASK_CREATE_PROJECT = "CREATE_PROJECT";

    @Override
    public void newFormInstance(EJForm form) throws EJActionProcessorException
    {
        form.getBlock(F_PROJECT.B_PROJECTS.ID).executeQuery();
    }

    @Override
    public void focusGained(EJForm form) throws EJActionProcessorException
    {
        if (form.getBlock(F_PROJECT.B_PROJECTS.ID).getBlockRecords().isEmpty())
        {
            askTOCreateProject(form);
        }

    }

    private void askTOCreateProject(EJForm form)
    {
        EJQuestion question = new EJQuestion(form, QUESTION_ASK_CREATE_PROJECT, "Question", new EJMessage("No Projects Found, Do you want to create a Project?"), "Yes", "No");
        form.askQuestion(question);
    }

    @Override
    public void questionAnswered(EJQuestion question) throws EJActionProcessorException
    {

        if (question.getName().equals(QUESTION_ASK_CREATE_PROJECT))
        {
            if (question.getAnswer() == EJQuestionButton.ONE)
            {
                question.getForm().getBlock(F_PROJECT.B_PROJECTS.ID).enterInsert(false);
            }
            return;
        }
    }

    @Override
    public void executeActionCommand(EJForm form, EJRecord record, String command, EJScreenType screenType) throws EJActionProcessorException
    {
        if (form.getFocusedBlock() == null)
        {
            return;
        }

        String blockName = form.getFocusedBlock().getName();

        if (F_PROJECT.AC_NEW.equals(command))
        {
            if (F_PROJECT.B_PROJECTS.ID.equals(blockName))
            {
                form.getBlock(F_PROJECT.B_PROJECTS.ID).enterInsert(false);
            }

            return;
        }
        if (F_PROJECT.AC_EDIT.equals(command))
        {
            if (F_PROJECT.B_PROJECTS.ID.equals(blockName))
            {
                form.getBlock(F_PROJECT.B_PROJECTS.ID).enterUpdate();
            }

            return;
        }
        if (F_PROJECT.AC_DELETE.equals(command) && form.getBlock(F_PROJECT.B_PROJECTS.ID).getFocusedRecord() != null)
        {
            if (F_PROJECT.B_PROJECTS.ID.equals(blockName))
            {
                ServiceRetriever.getDBService(form).validateDeleteRecordUsage(form.getBlock(F_PROJECT.B_PROJECTS.ID).getFocusedRecord(), "CUSTOMER_PROJECTS");
                form.getBlock(F_PROJECT.B_PROJECTS.ID).askToDeleteCurrentRecord("Are you sure you want to delete this project?");
            }

            return;
        }
    }

    @Override
    public void postDelete(EJForm form, EJRecord record) throws EJActionProcessorException
    {
        form.saveChanges();

    }
}
