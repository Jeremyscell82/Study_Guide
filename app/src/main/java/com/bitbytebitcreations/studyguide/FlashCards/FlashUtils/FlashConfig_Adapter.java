package com.bitbytebitcreations.studyguide.FlashCards.FlashUtils;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bitbytebitcreations.studyguide.FlashCards.Flash_Activity;
import com.bitbytebitcreations.studyguide.R;
import com.bitbytebitcreations.studyguide.Utils.Entry_Object;

import java.util.List;

/**
 * Created by JeremysMac on 1/20/17.
 */

public class FlashConfig_Adapter extends RecyclerView.Adapter<FlashConfig_Adapter.ViewHolder> {

    private final String TAG = "FLASH_ADAPTER";
    List<Entry_Object> masterObject;
    public Context mContext;



    public void updateAdapter(Context context, List<Entry_Object> entries){
        this.mContext = context;
        this.masterObject = entries;
        notifyDataSetChanged();
    }

    @Override
    public FlashConfig_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.card_simple, parent, false);


        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FlashConfig_Adapter.ViewHolder holder, int position) {
        if (masterObject != null && masterObject.get(position) != null){
            holder.flashQuestion.setText(masterObject.get(position).entryName);
            holder.flashCardData = masterObject.get(position);
            holder.context = mContext;
        }
    }

    @Override
    public int getItemCount() {
        if (masterObject != null && masterObject.size() > 0){
            return masterObject.size();
        } else {
            return 0;
        }
    }

    protected static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        protected TextView flashQuestion;
        Entry_Object flashCardData;
        Context context;

        public ViewHolder(View itemView) {
            super(itemView);
            this.flashQuestion = (TextView)itemView.findViewById(R.id.card_name);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            //GET ENTRY AND SEND TO ADDENTRY FLASH FRAGMENT
            Flash_Activity activity = (Flash_Activity) context;
            activity.recyclerOnClick(flashCardData);
        }
    }
}
