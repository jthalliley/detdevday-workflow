package com.riis.WorkflowExample.activity.helper;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import com.riis.WorkflowExample.R;
import com.riis.common.rest.dto.WorkflowAttributeDto;
import com.riis.common.rest.dto.WorkflowDto;
import com.riis.common.rest.dto.WorkflowResponseDto;

import java.util.ArrayList;
import java.util.List;

/**
 *  Keep track of outer/inner containers for each step/substep in workflow.
 */
public class WorkflowTree {

    private static final String TAG = "WorkflowTree";

    public boolean             isComplete;
    public boolean             hasAValue;
    public WorkflowDto         workflow;
    public ViewGroup           outerContainer;
    public View                answerWidget;
    public ViewGroup           innerContainer;
    public List<WorkflowTree>  children = new ArrayList<WorkflowTree>();

    /**
     *
     */
    public static WorkflowTree createStep(
        final Context      context,
        final WorkflowDto  parentWorkflowDto,
        final WorkflowDto  workflowDto) {

        WorkflowTree   result         = null;
        LayoutInflater layoutInflater =
            (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        switch (workflowDto.getDataType()) {
        case MULTISELECT:
            result = createMultiselectStep(layoutInflater, parentWorkflowDto, workflowDto);
            break;

        case BOOLEAN:
            result = createBooleanStep(layoutInflater, parentWorkflowDto, workflowDto);
            break;

        case TEXT:
            result = createTextStep(R.layout.dyn_text, layoutInflater, parentWorkflowDto, workflowDto);
            break;

        case MULTILINE_TEXT:
            result = createTextStep(R.layout.dyn_multiline_text, layoutInflater, parentWorkflowDto, workflowDto);
            break;

        default:
            Log.e(TAG, "CreateStep: " + workflowDto.getDataType().name() + " NOT YET IMPLEMENTED!!!");
        }

        return result;
    }

    private static WorkflowTree createMultiselectStep(
        final LayoutInflater layoutInflater,
        final WorkflowDto    parentWorkflowDto,
        final WorkflowDto    workflowDto) {

        WorkflowTree result = new WorkflowTree();
        ViewGroup    layout = (ViewGroup) layoutInflater.inflate(R.layout.dyn_multiselect, null);

        result.workflow       = workflowDto;
        result.outerContainer = layout;
        result.innerContainer = (ViewGroup) layout.findViewById(R.id.inner);
        result.answerWidget   = null;

        TextView title = (TextView) layout.findViewById(R.id.dynMultiSelectTitle);
        title.setText(workflowDto.getLabel());

        for (WorkflowDto child : workflowDto.getChildren()) {
            WorkflowTree childWorkflowTree = createStep(result.outerContainer.getContext(), workflowDto, child);
            result.innerContainer.addView(childWorkflowTree.outerContainer);
            result.children.add(childWorkflowTree);
        }

        return result;
    }

    private static WorkflowTree createBooleanStep(
        final LayoutInflater layoutInflater,
        final WorkflowDto    parentWorkflowDto,
        final WorkflowDto    workflowDto) {

        final WorkflowTree result = new WorkflowTree();
        final ViewGroup    layout =
            (ViewGroup) layoutInflater.inflate(R.layout.dyn_boolean, null);

        result.workflow       = workflowDto;
        result.outerContainer = layout;
        result.innerContainer = (ViewGroup) layout.findViewById(R.id.inner);

        CheckBox checkbox = (CheckBox) layout.findViewById(R.id.dynBooleanCheckbox);
        checkbox.setText(workflowDto.getLabel());
        result.answerWidget = checkbox;

        checkbox.setOnClickListener(new View.OnClickListener() {
                ViewGroup innerContainer;

                @Override public void onClick(View v) {
                    CheckBox checkbox = (CheckBox) v;

                    if (checkbox.isChecked()) {
                        ViewGroup parentView = (ViewGroup) v.getParent();

                        innerContainer = (ViewGroup) parentView.findViewById(R.id.inner);

                        if (workflowDto.getChildren() != null
                            && 0 < workflowDto.getChildren().size()
                            && innerContainer == null) {
                            Log.e(TAG, "No inner container for children!!!");
                            return;
                        }

                        if (innerContainer != null && workflowDto.getChildren() != null) {
                            for (WorkflowDto child : workflowDto.getChildren()) {
                                WorkflowTree childWorkflowTree =
                                    createStep(innerContainer.getContext(), workflowDto, child);

                                innerContainer.addView(childWorkflowTree.outerContainer);

                                result.children.add(childWorkflowTree);
                            }
                        }
                    } else {
                        innerContainer.removeAllViews();
                    }
                }
            });

        return result;
    }

    private static WorkflowTree createToggleStep(
        final LayoutInflater layoutInflater,
        final WorkflowDto    parentWorkflowDto,
        final WorkflowDto    workflowDto) {

        final WorkflowTree result = new WorkflowTree();
        final ViewGroup    layout =
            (ViewGroup) layoutInflater.inflate(R.layout.dyn_toggle, null);

        result.workflow       = workflowDto;
        result.outerContainer = layout;
        result.innerContainer = (ViewGroup) layout.findViewById(R.id.inner);

        Switch checkbox = (Switch) layout.findViewById(R.id.dynToggleSwitch);
        result.answerWidget = checkbox;

        checkbox.setOnClickListener(new View.OnClickListener() {
                ViewGroup innerContainer;

                @Override public void onClick(View v) {
                    Switch checkbox = (Switch) v;

                    if (checkbox.isChecked()) {
                        ViewGroup parentView = (ViewGroup) v.getParent();

                        innerContainer = (ViewGroup) parentView.findViewById(R.id.inner);

                        if (workflowDto.getChildren() != null
                            && 0 < workflowDto.getChildren().size()
                            && innerContainer == null) {
                            Log.e(TAG, "No inner container for children!!!");
                            return;
                        }

                        if (innerContainer != null && workflowDto.getChildren() != null) {
                            for (WorkflowDto child : workflowDto.getChildren()) {
                                WorkflowTree childWorkflowTree =
                                    createStep(innerContainer.getContext(), workflowDto, child);

                                innerContainer.addView(childWorkflowTree.outerContainer);

                                result.children.add(childWorkflowTree);
                            }
                        }
                    } else {
                        innerContainer.removeAllViews();
                    }
                }
            });

        return result;
    }

    private static WorkflowTree createTextStep(
        final int            layoutId,
        final LayoutInflater layoutInflater,
        final WorkflowDto    parentWorkflowDto,
        final WorkflowDto    workflowDto) {

        WorkflowTree result = new WorkflowTree();
        ViewGroup    layout = (ViewGroup) layoutInflater.inflate(layoutId, null);

        result.workflow       = workflowDto;
        result.outerContainer = layout;
        result.innerContainer = (ViewGroup) layout.findViewById(R.id.inner);

        TextView title = (TextView) layout.findViewById(R.id.dynTextSectionTitle);
        title.setText(parentWorkflowDto != null
                      ? parentWorkflowDto.getLabel()
                      : "BUG: Orphan Step");

        title = (TextView) layout.findViewById(R.id.dynTextTitle);
        title.setText(workflowDto.getLabel());

        EditText answerWidget = (EditText) layout.findViewById(R.id.dynTextField);

        String hint = workflowDto.findFirstAttribute(WorkflowAttributeDto.Keys.HINT);
        hint = hint != null ? hint : ("Type " + workflowDto.getLabel() + " here");
        answerWidget.setHint(hint);

        result.answerWidget = answerWidget;

        // Do we need to add a listener to see if the text field has been filled in?
        // If filled in, would we then create children?
        // Doesn't seem to make sense for a text field.
        if (workflowDto.getChildren() != null && 0 < workflowDto.getChildren().size()) {
            Log.e(TAG, "Text workflow has " + workflowDto.getChildren().size() + " children!!!");
        }

        return result;
    }

    /**
     * Returns the "answer" or user response to this node in the tree.
     */
    public String answer() {
        String result = null;

        if (answerWidget instanceof CheckBox) {
            result = ((CheckBox)answerWidget).isChecked() ? "true" : null;
            this.hasAValue = (result != null);

        } else if (answerWidget instanceof EditText) {
            String value = ((EditText)answerWidget).getText().toString();
            result = 0 < value.length() ? value : null;
            this.hasAValue = (result != null);

        } else if (answerWidget == null) { // multiselect
            result = ""; //TODO: KLOOJ: so traverse won't stop
        }

        if (this.workflow != null) {
            this.isComplete = this.hasAValue || !(this.workflow.isMandatory() && !this.hasAValue);
        } else {
            this.isComplete = false;
            Log.e(TAG, "No workflow associated with this (sub)tree!!!");
        }

        return result;
    }

    public static WorkflowResponseDto traverse(final WorkflowTree wt) {
        if (wt == null) return null;

        String answer = wt.answer();
        if (answer == null) return null;

        WorkflowResponseDto response = new WorkflowResponseDto();

        response.setAnswer(answer);
        response.setWorkflow(wt.workflow);

        // We needn't save/return these
        wt.workflow.setChildren(null);
        wt.workflow.setWorkflowResponses(null);

        List<WorkflowResponseDto> children = new ArrayList<WorkflowResponseDto>();

        for (WorkflowTree childWt : wt.children) {
            WorkflowResponseDto childResponse = traverse(childWt);
            if (childResponse != null) {
                children.add(childResponse);
            }
        }

        response.setChildren(children.size() == 0 ? null : children);

        return response;
    }

    public static boolean isCompletedWorkflow(final WorkflowTree wt) {

        boolean completed = true;

        if (wt != null) {
            int numberOfChildrenCompleted = 0;
            int numberOfChildren          = wt.children == null ? 0 : wt.children.size();

            for (WorkflowTree childWt : wt.children) {
                boolean childIsCompleted = isCompletedWorkflow(childWt);
                numberOfChildrenCompleted += childIsCompleted ? 1 : 0;
            }

            //do for all types?            if (wt.workflow.getDataType() == WorkflowDataType.MULTISELECT) {
            completed = (numberOfChildren == 0 && wt.hasAValue)
                || (0 < numberOfChildren && 0 < numberOfChildrenCompleted);
            //do for all types?            }
        }

        return completed;
    }

    /**
     * For debugging purposes...
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{")
            .append("isComplete=").append(Boolean.toString(isComplete)).append(", ")
            .append("hasAValue=").append(Boolean.toString(hasAValue)).append(", ")
            .append("workflow=").append(workflow == null
                                        ? "null"
                                        : workflow.getDataType().name()).append(", ")
            .append("isMandatory=").append(workflow == null
                                           ? "null"
                                           : Boolean.toString(workflow.isMandatory())).append(", ")
            .append("#children=").append(children == null ? "0" : children.size())
            .append("}");

        return sb.toString();
    }


    // For debugging purposes only...
    public static class PrettyPrinter {
        private final static int    INDENT_LEVEL = 5;
        private final static String SPACES       = "                                            ";

        private static String leadingSpaces(final int depth) {
            return SPACES.substring(0, depth * INDENT_LEVEL);
        }

        public static void print(final WorkflowTree wt, final int depth) {
            if (wt == null) {
                Log.d(TAG, leadingSpaces(depth) + "null");
                return;
            }

            String answer = wt.answer();
            if (answer == null) {
                Log.d(TAG, leadingSpaces(depth) + wt.toString());
                return;
            }

            Log.d(TAG, leadingSpaces(depth) + wt.toString());
            int newDepth = depth + 1;
            for (WorkflowTree childWt : wt.children) {
                print(childWt, newDepth);
            }
        }

    }

}


