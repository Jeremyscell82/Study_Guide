package com.bitbytebitcreations.studyguide.GreatSites.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bitbytebitcreations.studyguide.R;
import com.bitbytebitcreations.studyguide.Utils.Recycler_Adapter;

/**
 * Created by JeremysMac on 1/17/17.
 */

public class Category_Fragment extends Fragment {


    public Category_Fragment newIntance(){
        return new Category_Fragment();
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recyclerview, container, false);


        //SET UP RECYCLER VIEW
        RecyclerView myRecycler = (RecyclerView) view.findViewById(R.id.main_recyclerview);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        Recycler_Adapter adapter = new Recycler_Adapter();
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        myRecycler.setLayoutManager(llm);
        myRecycler.setAdapter(adapter);

        return view;
    }
}
