package com.oneseed.medstime;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class MedsAdapter extends RecyclerView.Adapter<MedsAdapter.ViewHolder> {

    private final LayoutInflater inflater;
    private final List<Meds> meds;

    MedsAdapter(Context context, List<Meds> meds) {
        this.meds = meds;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public MedsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.meds_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MedsAdapter.ViewHolder holder, int position) {
        Meds medicine = meds.get(position);
        holder.medsTextView.setText(medicine.getTextInside());
        if (medicine.getTimesInside().equals(medicine.getCountInside())){
            holder.timesCount.setVisibility(View.GONE);
            holder.markSign.setVisibility(View.INVISIBLE);
        }
        else {
            holder.timesCount.setVisibility(View.VISIBLE);
            holder.markSign.setVisibility(View.VISIBLE);
            String timesCountText = "Осталось " + (medicine.getTimesInside()-medicine.getCountInside()) + " раз(а)";
            holder.timesCount.setText(timesCountText);
        }

        holder.deleteSign.setOnClickListener(v -> {
            meds.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, meds.size());
        });

        holder.markSign.setOnClickListener(v -> {
            medicine.setCountInside(medicine.getCountInside()+1);
            notifyItemChanged(position);
        });
        
/*        holder.nameView.setText(meds.getName());
        holder.capitalView.setText(meds.getCapital());*/
    }

    @Override
    public int getItemCount() {
        return meds.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView medsTextView;
        final TextView timesCount;
        final ImageButton markSign;
        final ImageButton deleteSign;
/*        final ImageView flagView;
        final TextView nameView, capitalView;*/

        ViewHolder(View view) {
            super(view);
            medsTextView = view.findViewById(R.id.medsText);
            timesCount = view.findViewById(R.id.timesCount);
            markSign = view.findViewById(R.id.markSign);
            deleteSign = view.findViewById(R.id.deleteSign);
/*            flagView = view.findViewById(R.id.flag);
            nameView = view.findViewById(R.id.name);
            capitalView = view.findViewById(R.id.capital);*/
        }
    }
}