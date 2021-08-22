package com.bawp.todoister;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.Chip;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private final List<Task> taskList;
    BottomSheetFragment bottomSheetFragment;

    public RecyclerViewAdapter(List<Task> taskList) {
        this.taskList = taskList;
        bottomSheetFragment = new BottomSheetFragment();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.todo_row,parent,false);
        return new ViewHolder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position) {
        Task task = taskList.get(position);
        String formatted = Utils.formatDate(task.getDueDate());

        holder.task.setText(task.getTask());
        holder.todayChip.setText(formatted);
        if(task.getPriority()==Priority.LOW)
        {
            holder.task.setTextColor(Color.GREEN);
            holder.todayChip.setTextColor(Color.GREEN);

        }
        if(task.getPriority()==Priority.HIGH)
        {
            holder.task.setTextColor(Color.RED);
            holder.todayChip.setTextColor(Color.RED);

        }
        if(task.getPriority()==Priority.MEDIUM)
        {
            holder.task.setTextColor(Color.YELLOW);
            holder.todayChip.setTextColor(Color.YELLOW);

        }

    }
//    public void showBottomSheetFragment() {
//        bottomSheetFragment.show(getSupportFragmentManager(), bottomSheetFragment.getTag());
//    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public RadioButton radioButton;
        public TextView task;
        public Chip todayChip;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            radioButton = itemView.findViewById(R.id.todo_radio_button);
            task = itemView.findViewById(R.id.todo_row_todo);
            todayChip = itemView.findViewById(R.id.todo_row_chip);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Toast.makeText(v.getContext(), "clicked", Toast.LENGTH_SHORT).show();

                }
            });
            radioButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Task task = taskList.get(getAdapterPosition());
                    TaskViewModel.delete(task);
                    Snackbar.make(v,"TASK DELETED",Snackbar.LENGTH_LONG)
                            .setAction("UNDO", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    TaskViewModel.insert(task);
                                }
                            })
                            .show();
                }
            });
        }


    }
}
