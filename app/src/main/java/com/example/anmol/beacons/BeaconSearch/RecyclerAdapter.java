package com.example.anmol.beacons.BeaconSearch;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.anmol.beacons.R;

import java.util.ArrayList;

public class RecyclerAdapter  extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    ArrayList<ArrayList<String>> arr;
    public RecyclerAdapter(ArrayList<ArrayList<String>> arr) {
        this.arr = arr;
    }
    class ViewHolder extends RecyclerView.ViewHolder{
        private TextView uuid;
        private TextView major;
        private TextView minor;
        private TextView distance;
        public ViewHolder(View itemView)
        {
            super(itemView);
            uuid = itemView.findViewById(R.id.uuid);
            major = itemView.findViewById(R.id.major);
            minor = itemView.findViewById(R.id.minor);
            distance = itemView.findViewById(R.id.distance);
        }
    }
    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_card_search,parent,false));
    }

    @Override
    public void onBindViewHolder(RecyclerAdapter.ViewHolder holder, int position) {
        ArrayList<String> arrayList = arr.get(position);
        holder.uuid.setText(arrayList.get(0));
        holder.uuid.setText(arrayList.get(1));
        holder.uuid.setText(arrayList.get(2));
        holder.uuid.setText(arrayList.get(3));
    }
    @Override
    public int getItemCount()
    {
        return arr.size();
    }
}