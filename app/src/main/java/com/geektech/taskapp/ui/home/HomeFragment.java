
package com.geektech.taskapp.ui.home;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.geektech.taskapp.App;
import com.geektech.taskapp.FormActivity;
import com.geektech.taskapp.MainActivity;
import com.geektech.taskapp.OnItemClickListener;
import com.geektech.taskapp.R;
import com.geektech.taskapp.Task;

import java.util.List;

public class HomeFragment extends Fragment {

    private TaskAdapter adapter;
    private List<Task> list;
    Task task;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
//        list.add("Курманжан");
//        list.add("Айгерим");
//        list.add("Нургазы");
//        list.add("Арслан");
//        list.add("Эрмек");
//        list.add("Чыныбек");
//        list.add("Перизат");
//        list.add("Айпери");
//        list.add("Бегайым");
//        list.add("Бакыт");
        list = App.getDatabase().taskDao().getAll();
        App.getDatabase().taskDao().getAllLive().observe(getActivity(), new Observer<List<Task>>() {
            @Override
            public void onChanged(List<Task> tasks) {
                  list.clear();
                list.addAll(tasks);
                adapter.notifyDataSetChanged();
            }
        });

        RecyclerView recyclerView = root.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),
                DividerItemDecoration.VERTICAL));
        adapter = new TaskAdapter(list);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Task task=list.get(position);
                Intent intent = new Intent(getContext(), FormActivity.class);
                intent.putExtra("task",task );
                startActivity(intent );
            }

            @Override
            public void onItemLongClick(final int position) {
                final AlertDialog.Builder dialog = new AlertDialog.Builder(requireContext());
                dialog.setTitle("Вы хотите удалить?")
                        .setMessage("Удалить задачу")
                        .setNegativeButton("Нет", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialoginterface, int i) {
                                dialoginterface.cancel();

                            }
                        }).setPositiveButton("Да", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialoginterface, int i) {
                        App.getDatabase().taskDao().delete(list.get(position));
                    }
                }).show();
            }
        });


        recyclerView.setAdapter(adapter);
        return root;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == 100) {
            Task task = (Task) data.getSerializableExtra("task");
            String getText = data.getStringExtra("");
         //   list.clear();
            list.add(task);
            adapter.notifyDataSetChanged();
        }

    }
}
