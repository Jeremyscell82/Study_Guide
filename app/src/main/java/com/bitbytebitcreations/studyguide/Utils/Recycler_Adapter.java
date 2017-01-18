package com.bitbytebitcreations.studyguide.Utils;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bitbytebitcreations.studyguide.R;
import com.mikepenz.fastadapter.items.AbstractItem;

import java.util.List;

/**
 * Created by JeremysMac on 1/18/17.
 */

public class Recycler_Adapter extends RecyclerView.Adapter<Recycler_Adapter.ViewHolder> {


    private static final String TAG = "RECYCLER_ADAPTER";



    @Override
    public Recycler_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.card_simple, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.name.setText("TEST");
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    protected static class ViewHolder extends RecyclerView.ViewHolder {

        protected TextView name;

        public ViewHolder(View itemView) {
            super(itemView);
            this.name = (TextView) itemView.findViewById(R.id.card_name);
        }
    }
}
