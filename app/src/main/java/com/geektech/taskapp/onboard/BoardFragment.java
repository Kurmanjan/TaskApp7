package com.geektech.taskapp.onboard;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.geektech.taskapp.MainActivity;
import com.geektech.taskapp.R;

import static android.content.Context.MODE_PRIVATE;
import static com.geektech.taskapp.R.color.colorGreen;
import static com.geektech.taskapp.R.color.colorRed;
import static com.geektech.taskapp.R.color.colorYellow;

/**
 *
 * A simple {@link Fragment} subclass.
 */
public class BoardFragment extends Fragment {
ImageView imageView;
Button button;

    public BoardFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_board, container, false);
    }

    @SuppressLint("ResourceType")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView textView = view.findViewById(R.id.textView);
        imageView=view.findViewById(R.id.imageView);
        button=view.findViewById(R.id.btnStart);
        LinearLayout frg=view.findViewById(R.id.board_frg);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences=getActivity().getSharedPreferences("isShown",MODE_PRIVATE);
                preferences.edit().putBoolean("isShown",true).apply();
                startActivity(new Intent(getContext(), MainActivity.class));
                getActivity().finish();
            }
        });
        int pos = getArguments().getInt("pos");
        switch (pos) {
            case 0:
                textView.setText("Привет");
                imageView.setImageResource(R.drawable.board1);
                button.setVisibility(View.INVISIBLE);
                Log.e("---------", frg+"");
                frg.setBackgroundResource(colorRed);
                break;
            case 1:
                textView.setText("Как дела?");
                imageView.setImageResource(R.drawable.board);
                button.setVisibility(View.INVISIBLE);
                frg.setBackgroundResource(colorYellow);
                break;
            case 2:
                textView.setText("Че делаешь?");
                imageView.setImageResource(R.drawable.board3);
                button.setVisibility(View.VISIBLE);
                frg.setBackgroundResource(colorGreen);
                break;
        }
    }
}
