package com.bitbytebitcreations.studyguide.Utils;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bitbytebitcreations.studyguide.Definitions.DefinitionsActivity;
import com.bitbytebitcreations.studyguide.GreatSites.SitesActivity;
import com.bitbytebitcreations.studyguide.R;

import java.util.ArrayList;
import java.util.List;

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
    List<String[]> list;
    List<String[]> placeholderList;
//    List<String[]> filteredList;
    List<String> names;
    List<Long> rowIds;
    boolean searchEnabled;



    public Recycler_Adapter(Context activity, int[] bits){
        this.mContext = activity;
        this.mKeyBits = bits;
        names = null;
        rowIds = null;
    }

    public void updateAdapter(List<String> updatedList, List<Long> rowIdList){
        Log.i(TAG, "ADAPTER IS BEING UPDATED");
        this.names = updatedList;
        this.rowIds = rowIdList; //USED WITH GREAT SITES ACTIVITY
        if (updatedList != null && rowIdList != null){
            //CREATE ADAPTER LIST
            list = new ArrayList<>();
            for (int i =0; i < updatedList.size(); i++){
                list.add(new String[]{updatedList.get(i), String.valueOf(rowIdList.get(i))});
            }
            Log.i(TAG, "NEW LIST IS POPULATED: " + list.size());
        }
//        Log.i(TAG, "UPDATE ADAPTER : " +  names.size());
        notifyDataSetChanged();
    }

    public void queryAdapter(boolean enableSearch, String query){
        if (enableSearch && !query.equals("")){
//            searchEnabled = true;
            //ADD CURRENT LIST TO PLACEHOLDER
            placeholderList = new ArrayList<>();
            placeholderList = list;
            //TEMP LIST
            List<String[]> filteredList = new ArrayList<>();
            for (String[] value : list){
                if (value[0].toLowerCase().contains(query)){
                    filteredList.add(value); //ADD NAME AND ID TO FILTERED LIST
                }
            }
            //SET ADAPTER LIST TO NEW FILTERED LIST
            list = filteredList;
            //WHEN FOR LOOP COMPLETES, UPDATE ADAPTER
            notifyDataSetChanged();
        } else {
//            searchEnabled = false;
            //REPLACE LIST WITH ORIGINAL LIST
            list = placeholderList;
            notifyDataSetChanged();
        }
    }


    @Override
    public Recycler_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.card_simple, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (list != null){
//            holder.name.setText(names.get(position));
//            if (rowIds != null)holder.rowId = rowIds.get(position);
            holder.name.setText(list.get(position)[0]);
            if (list.get(position)[1] != null)holder.rowId = Long.valueOf(list.get(position)[1]);
            holder.keyBits = mKeyBits;
            holder.context = mContext;
        }
        //NEW VERSION OF ADAPTER

    }

    @Override
    public int getItemCount() {
        if (list != null){
            return list.size();
        } else {
            return 0;
        }
    }

    protected static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        protected TextView name;
        Context context;
        int[] keyBits;
        long rowId; //HOLDS THE CATEGORY ROW ID FOR 'GREAT SITES' ACTIVITY
//        String content;

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
                    sites.recyclerOnClick(keyBits[1], name.getText().toString(), rowId, getAdapterPosition());
                    break;
                case 3: //DEFINITIONS
                    DefinitionsActivity definitions = (DefinitionsActivity) context;
                    definitions.recyclerOnClick(false, getAdapterPosition());
                    break;
                case 4: //ABOUT
                    break;
            }
        }
    }
}
