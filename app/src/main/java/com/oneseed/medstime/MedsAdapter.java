package com.oneseed.medstime;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.security.Timestamp;
import java.util.Date;
import java.util.List;


public class MedsAdapter extends RecyclerView.Adapter<MedsAdapter.ViewHolder> {

    private final LayoutInflater inflater;
    private final List<Meds> meds;
    private final SharedPreferences settings;

    MedsAdapter(Context context, List<Meds> meds) {
        this.meds = meds;
        this.inflater = LayoutInflater.from(context);
        settings = context.getSharedPreferences(context.getString(R.string.medsShared), Context.MODE_PRIVATE);


    }

    @NonNull
    @Override
    public MedsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.meds_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MedsAdapter.ViewHolder holder, int position) {
        SharedPreferences.Editor editor = settings.edit();
        Meds medicine = meds.get(position);
        holder.medsTextView.setText(medicine.getTextInside());
        if (medicine.getTimesInside().equals(medicine.getCountInside())) {
            holder.timesCount.setVisibility(View.GONE);
            holder.markSign.setVisibility(View.INVISIBLE);
        } else {
            holder.timesCount.setVisibility(View.VISIBLE);
            holder.markSign.setVisibility(View.VISIBLE);
            String timesCountText = "Осталось " + (medicine.getTimesInside() - medicine.getCountInside()) + " раз(а)";
            holder.timesCount.setText(timesCountText);
        }

        editor.putString( String.valueOf(position), medicine.getTextInside() + " " + medicine.getTimesInside() + " " + medicine.getCountInside());

        holder.deleteSign.setOnClickListener(v -> {
            meds.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, meds.size());
            editor.putString( String.valueOf(position), medicine.getTextInside() + " " + medicine.getTimesInside() + " " + "deleted");
            editor.apply();

        });

        holder.markSign.setOnClickListener(v -> {
            medicine.setCountInside(medicine.getCountInside() + 1);
            notifyItemChanged(position);
        });

        //get timestamp

        editor.apply();

    }

    @Override
    public int getItemCount() {
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt("length", meds.size() - 1);
        editor.apply();
        return meds.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView medsTextView;
        final TextView timesCount;
        final ImageButton markSign;
        final ImageButton deleteSign;

        ViewHolder(View view) {
            super(view);
            medsTextView = view.findViewById(R.id.medsText);
            timesCount = view.findViewById(R.id.timesCount);
            markSign = view.findViewById(R.id.markSign);
            deleteSign = view.findViewById(R.id.deleteSign);
        }
    }
}