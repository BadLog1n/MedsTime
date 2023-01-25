package com.oneseed.medstime;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageButton openButton = findViewById(R.id.addMedsImgBtn);
        ImageButton closeButton = findViewById(R.id.closeImgBtn);
        LinearLayout addMedsLayout = findViewById(R.id.addMedsLayout);
        LinearLayout addMedsBtnLayout = findViewById(R.id.addMedsBtnLayout);

        RecyclerView medsRecyclerView;


        medsRecyclerView = findViewById(R.id.medsRc);
        medsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        MedsAdapter medsAdapter = new MedsAdapter();
        medsRecyclerView.setAdapter(medsAdapter);



        openButton.setOnClickListener(v -> {
            medsAdapter.MedsAdapterData(("test").split(""));
            medsAdapter.notifyDataSetChanged();
            addMedsLayout.setVisibility(View.VISIBLE);
            addMedsBtnLayout.setVisibility(View.GONE);
        });
        closeButton.setOnClickListener(v -> {
            medsAdapter.MedsAdapterData(("t1s3").split(""));
            medsAdapter.notifyDataSetChanged();
            addMedsLayout.setVisibility(View.GONE);
            addMedsBtnLayout.setVisibility(View.VISIBLE);
        });

    }
}