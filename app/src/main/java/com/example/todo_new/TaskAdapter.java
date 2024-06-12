package com.example.todo_new;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import java.util.ArrayList;

public class TaskAdapter extends ArrayAdapter<Task> {

    private final Database dbHelper;

    public TaskAdapter(Context context, ArrayList<Task> tasks, Database dbHelper) {
        super(context, 0, tasks);
        this.dbHelper = dbHelper;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Task task = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.task_item_layout, parent, false);
        }
        // Lookup view for data population
        TextView tvTitle = convertView.findViewById(R.id.textViewTaskTitle);
        Button btnUpdate = convertView.findViewById(R.id.buttonUpdate);
        Button btnDelete = convertView.findViewById(R.id.buttonDelete);

        // Populate the data into the template view using the data object
        tvTitle.setText(task.getTitle());

        // Set up update button click listener
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showUpdateDialog(task);
            }
        });

        // Set up delete button click listener
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbHelper.deleteTask(task.getId());
                remove(task);
                notifyDataSetChanged();
            }
        });

        // Return the completed view to render on screen
        return convertView;
    }

    private void showUpdateDialog(Task task) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Update Task");

        View viewInflated = LayoutInflater.from(getContext()).inflate(R.layout.update_task_dialog, (ViewGroup) null);
        final EditText inputTitle = viewInflated.findViewById(R.id.editTextTitle);
        final EditText inputDeadline = viewInflated.findViewById(R.id.editTextDeadline);
        final EditText inputStartTime = viewInflated.findViewById(R.id.editTextStartTime);
        final EditText inputEndTime = viewInflated.findViewById(R.id.editTextEndTime);
        final EditText inputRemind = viewInflated.findViewById(R.id.editTextRemind);
        final EditText inputRepeat = viewInflated.findViewById(R.id.editTextRepeat);
        final EditText inputCategory = viewInflated.findViewById(R.id.editTextCategory);

        inputTitle.setText(task.getTitle());
        inputDeadline.setText(task.getDeadline());
        inputStartTime.setText(task.getStartTime());
        inputEndTime.setText(task.getEndTime());
        inputRemind.setText(task.getRemind());
        inputRepeat.setText(task.getRepeat());
        inputCategory.setText(task.getCategory());

        builder.setView(viewInflated);

        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                String title = inputTitle.getText().toString();
                String deadline = inputDeadline.getText().toString();
                String startTime = inputStartTime.getText().toString();
                String endTime = inputEndTime.getText().toString();
                String remind = inputRemind.getText().toString();
                String repeat = inputRepeat.getText().toString();
                String category = inputCategory.getText().toString();

                dbHelper.updateTask(task.getId(), title, deadline, startTime, endTime, remind, repeat, category);
                task.setTitle(title);
                task.setDeadline(deadline);
                task.setStartTime(startTime);
                task.setEndTime(endTime);
                task.setRemind(remind);
                task.setRepeat(repeat);
                task.setCategory(category);
                notifyDataSetChanged();
            }
        });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }
}
