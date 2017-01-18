package com.bitbytebitcreations.studyguide.Utils;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bitbytebitcreations.studyguide.GreatSites.SitesActivity;
import com.bitbytebitcreations.studyguide.MainActivity;
import com.bitbytebitcreations.studyguide.R;

/**
 * Created by JeremysMac on 1/18/17.
 * Simple adapter gone confused...
 * Bits == '0,0' First number is activity, second is fragment
 * 0 = home
 * 1++ goes in order of flow
 */

public class Recycler_Adapter extends RecyclerView.Adapter<Recycler_Adapter.ViewHolder>{


    private static final String TAG = "RECYCLER_ADAPTER";
    Context mContext;
    int[] mKeyBits = {0,0};


    public Recycler_Adapter(Context activity, int[] bits){
        this.mContext = activity;
        this.mKeyBits = bits;
    }


    @Override
    public Recycler_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.card_simple, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.name.setText("TEST");
        holder.keyBits = mKeyBits;
        holder.context = mContext;
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    protected static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        protected TextView name;
        Context context;
        int[] keyBits;

        public ViewHolder(View itemView) {
            super(itemView);
            this.name = (TextView) itemView.findViewById(R.id.card_name);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            //SWITCH TO ALL ACTIVITIES, BASED OFF FIRST BIT
            switch (keyBits[0]){
                case 0: //HOME ... this will never happen
                    break;
                case 1: //FLASH CARDS
                    break;
                case 2: //GREAT SITES
                    SitesActivity sites = (SitesActivity) context;
                    sites.recyclerOnClick(keyBits[1], getAdapterPosition());
                    break;
                case 3: //DEFINITIONS
                    break;
                case 4: //ABOUT
                    break;
            }
        }
    }
}
