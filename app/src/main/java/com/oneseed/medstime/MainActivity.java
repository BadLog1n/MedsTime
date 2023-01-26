package com.oneseed.medstime;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<Meds> meds = new ArrayList<Meds>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageButton openButton = findViewById(R.id.addMedsImgBtn);
        ImageButton closeButton = findViewById(R.id.closeImgBtn);
        LinearLayout addMedsLayout = findViewById(R.id.addMedsLayout);
        LinearLayout addMedsBtnLayout = findViewById(R.id.addMedsBtnLayout);

        setInitialData();
        RecyclerView medsRecyclerView = findViewById(R.id.medsRc);
        MedsAdapter adapter = new MedsAdapter(this, meds);
        adapter.notifyDataSetChanged();

        medsRecyclerView.setAdapter(adapter);


        openButton.setOnClickListener(v -> {
            addMedsLayout.setVisibility(View.VISIBLE);
            addMedsBtnLayout.setVisibility(View.GONE);
        });
        closeButton.setOnClickListener(v -> {
            addMedsLayout.setVisibility(View.GONE);
            addMedsBtnLayout.setVisibility(View.VISIBLE);
        });

    }

    private void setInitialData(){
        meds.add(new Meds ("Нейромультивит", 1, 0, 1));
        meds.add(new Meds ("Нейромультивит", 1, 0, 1));
        meds.add(new Meds ("Нейромультивит", 1, 1, 1));
    }
}