package com.bawp.todoister;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.chip.Chip;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.Group;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import java.util.Calendar;
import java.util.Date;

public class BottomSheetFragment extends BottomSheetDialogFragment implements View.OnClickListener {

    public EditText enterTodo;
    public ImageButton calendarButton;
    public ImageButton priorityButton;
    public RadioGroup priorityRadioGroup;
    public RadioButton selectedRadioButton;
    int selectedButtonId;
    ImageButton saveButton;
    CalendarView calendarView;
    Group calendarGroup;
    Date dueDate;
    Calendar calendar = Calendar.getInstance();
    Priority priority;

    public BottomSheetFragment() {
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.bottom_sheet, container, false);
        calendarGroup = view.findViewById(R.id.calendar_group);
        calendarView = view.findViewById(R.id.calendar_view);
        calendarButton = view.findViewById(R.id.today_calendar_button);
        enterTodo = view.findViewById(R.id.enter_todo_et);
        saveButton = view.findViewById(R.id.save_todo_button);
        priorityButton = view.findViewById(R.id.priority_todo_button);
        priorityRadioGroup = view.findViewById(R.id.radioGroup_priority);
        Chip todayChip = view.findViewById(R.id.today_chip);
        todayChip.setOnClickListener(this);
        Chip tommorowChip = view.findViewById(R.id.tomorrow_chip);
        tommorowChip.setOnClickListener(this);
        Chip nextWeekChip = view.findViewById(R.id.next_week_chip);
        nextWeekChip.setOnClickListener(this);
        priority = Priority.LOW;
        return view;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String task = enterTodo.getText().toString().trim();
                if(!TextUtils.isEmpty(task))
                {
                    Task myTask = new Task(task,priority,
                            dueDate, Calendar.getInstance().getTime(), false);

                    TaskViewModel.insert(myTask);
//                    Toast.makeText(view.getContext(), priority.toString(), Toast.LENGTH_SHORT).show();

                }
                else
                {
//                    Snackbar.make(view,"Task field cannot be empty !!"
//                            ,Snackbar.LENGTH_SHORT).show();
                    Toast.makeText(view.getContext(), "Task field cannot be empty !!", Toast.LENGTH_SHORT).show();
//                    Log.d("emptyTask", "onClick: eror !!");

                }
                if(BottomSheetFragment.this.isVisible())
                {
                    BottomSheetFragment.this.dismiss();
                }
            }
        });
        calendarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.hideKeyBoard(v);
                    calendarGroup.setVisibility
                            (calendarGroup.getVisibility()==View.GONE?View.VISIBLE:View.GONE);

            }

        });
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                    calendar.clear();
                    calendar.set(year,month,dayOfMonth);
                    dueDate = calendar.getTime();
            }
        });
        priorityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.hideKeyBoard(v);
                priorityRadioGroup.setVisibility(View.VISIBLE);
                priorityRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        selectedButtonId = checkedId;
                        selectedRadioButton = v.findViewById(checkedId);
                        if(checkedId==R.id.radioButton_high)
                        {
                            priority = Priority.HIGH;
                        }
                        if(checkedId==R.id.radioButton_med)
                        {
                            priority = Priority.MEDIUM;
                        }
                        if(checkedId==R.id.radioButton_low)
                        {
                            priority = Priority.LOW;
                        }
                    }
                });

            }
        });

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id==R.id.today_chip)
        {
            dueDate = Calendar.getInstance().getTime();
        }
        if(id==R.id.tomorrow_chip)
        {
            calendar.add(Calendar.DAY_OF_YEAR,1);

            dueDate = calendar.getTime();
        }
        if(id==R.id.next_week_chip)
        {
            calendar.add(Calendar.DAY_OF_YEAR,7);

            dueDate = calendar.getTime();
        }
    }
}