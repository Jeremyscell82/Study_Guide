package com.bitbytebitcreations.studyguide.FlashCards;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.bitbytebitcreations.studyguide.R;

/**
 * Created by JeremysMac on 1/21/17.
 */

public class FlashHome_Fragment extends Fragment {

    TextView toolbarTitle;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_flashhome, container, false);

        //DECLARE VARIABLES
        int flashCards = 0;
        int timerTime;
        //DECLARE UI
        TextView flashCount = (TextView) view.findViewById(R.id.flash_count);
        TextView flashTimer = (TextView) view.findViewById(R.id.flash_timer);
        toolbarTitle = (TextView) getActivity().findViewById(R.id.toolbar_title);
        toolbarTitle.setText("Flash Cards");
        Button startBttn = (Button) view.findViewById(R.id.flashcard_start_button);
        startBttn.setText(getString(R.string.flash_start));
        startBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Flash_Activity activity = (Flash_Activity) getActivity();
                activity.loadFirstFlashCard();
            }
        });

        //GET BUNDLE
        Bundle bundle = getArguments();
        if (bundle != null){
            flashCards = bundle.getInt("count");
            timerTime = bundle.getInt("timer");
            //SET UI
            flashCount.setText(flashCount.getText()+" " + String.valueOf(flashCards));
            flashTimer.setText(flashTimer.getText()+" " + String.valueOf((timerTime/1000)+" seconds")); //DIVIDE BY 1,000 TO SHOW IN SECONDS VS MILLISECONDS
        }

        //ENSURE HAMBURGER OS SHOWN
        Flash_Activity activity = (Flash_Activity) getActivity();
        activity.toggleBackArrow(false);

        //ENSURE PROPER MENU IT DISPLAYED
        setHasOptionsMenu(true);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_flashhome, menu);
    }


}
