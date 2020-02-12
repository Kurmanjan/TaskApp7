package com.geektech.taskapp;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.geektech.taskapp.ui.home.HomeFragment;

public class FormActivity extends AppCompatActivity {
    Task mytask;
ImageView imageView;
    private EditText editTitle;
    private EditText editDesc;
    String title;
    String desc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        editTitle = findViewById(R.id.editTitle);
        editDesc = findViewById(R.id.editDesc);
        imageView=findViewById(R.id.imageView);
        edit();
        Glide.with(getApplicationContext()).load("https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcTnMlvZiu6A5yFp5W9v-whsILM5XbW56jdwp1ClpZ4KgSoKpooI").circleCrop().into(imageView);
    }
    public void edit(){
        mytask= (Task) getIntent().getSerializableExtra("task");
        if (mytask!=null){
            editDesc.setText(mytask.getDesc());
            editTitle.setText(mytask.getTitle());
        }
    }
    public void onClick(View view) {
         title = editTitle.getText().toString().trim();
         desc = editDesc.getText().toString().trim();
        if (editDesc.getText().toString().matches("")&&editTitle.getText().toString().matches("")) {
        Toast.makeText(getApplicationContext(),"Enter some text",Toast.LENGTH_LONG).show();
        } else if (mytask!=null){
            mytask.setTitle(title);
            mytask.setDesc(desc);
            App.getDatabase().taskDao().update(mytask);
        }
        else {
           mytask=new Task(title,desc);
            App.getDatabase().taskDao().insert(mytask);
        }
        finish();
    }
}