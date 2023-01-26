package com.oneseed.medstime;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<Meds> meds = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageButton openButton = findViewById(R.id.addMedsImgBtn);
        ImageButton closeButton = findViewById(R.id.closeImgBtn);
        Button addNewMedButton = findViewById(R.id.addNewMedButton);
        EditText editTextNumber = findViewById(R.id.editTextNumber);
        EditText newMedicineText = findViewById(R.id.newMedicineEditText);
        LinearLayout addMedsLayout = findViewById(R.id.addMedsLayout);
        LinearLayout addMedsBtnLayout = findViewById(R.id.addMedsBtnLayout);

        setInitialData();
        RecyclerView medsRecyclerView = findViewById(R.id.medsRc);
        MedsAdapter adapter = new MedsAdapter(this, meds);
        medsRecyclerView.setAdapter(adapter);


        openButton.setOnClickListener(v -> {
            addMedsLayout.setVisibility(View.VISIBLE);
            addMedsBtnLayout.setVisibility(View.GONE);
        });
        closeButton.setOnClickListener(v -> {
            addMedsLayout.setVisibility(View.GONE);
            addMedsBtnLayout.setVisibility(View.VISIBLE);
        });

        addNewMedButton.setOnClickListener(v -> {
            String newMedicineTextString = newMedicineText.getText().toString();
            String editTextNumberString = editTextNumber.getText().toString();
            if (newMedicineTextString.equals("")) {
                Toast.makeText(this, "Пожалуйста, введите название или информацию", Toast.LENGTH_SHORT).show();
                return;
            }
            int times = 1;
            if (!editTextNumberString.equals("")) {
                times = Integer.parseInt(editTextNumberString);
            }
            addMedsLayout.setVisibility(View.GONE);
            addMedsBtnLayout.setVisibility(View.VISIBLE);
            meds.add(new Meds(newMedicineTextString, times, 0));
            newMedicineText.setText("");
            editTextNumber.setText("");
        });

    }

    private void setInitialData() {
        SharedPreferences settings = this.getSharedPreferences(getString(R.string.medsShared), Context.MODE_PRIVATE);
        String[] medsOne = settings.getString("meds", "").split(" ");
        if (medsOne.length > 1){
            meds.add(new Meds(medsOne[0], Integer.parseInt(medsOne[1]), Integer.parseInt(medsOne[2])));

        }
    }
}