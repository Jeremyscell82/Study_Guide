package com.bitbytebitcreations.studyguide.FlashCards;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Fragment;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bitbytebitcreations.studyguide.R;
import com.mikepenz.materialdrawer.Drawer;

/**
 * Created by JeremysMac on 1/19/17.
 */

public class Flash_Fragment extends Fragment {


    private final String TAG = "FLASH_FRAGMENT";
    View revealView;
    TextView flashText;
    CardView flashCard;
    TextView toolbarTitle;
    CountDownTimer timer;
    int isAnswerRevealed; //0 = FALSE, 1 = TRUE
    Button flashCardBttn;
    String[] buttonText;
    //CONTENT
    String question;
    String answer;
    int time;

    public Flash_Fragment newInstance(){
        return new Flash_Fragment();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_flashcard, container, false);

        //DECLARE VARIABLES
        isAnswerRevealed = 0;
        buttonText = getActivity().getResources().getStringArray(R.array.flashcard_button);

        //CONFIGURE TOOLBAR
        Flash_Activity activity = (Flash_Activity) getActivity();
        time = activity.getTimerTime();
        activity.toggleBackArrow(true).setOnDrawerNavigationListener(new Drawer.OnDrawerNavigationListener() {
            @Override
            public boolean onNavigationClickListener(View clickedView) {
                getFragmentManager().popBackStack();
                return true;
            }
        });

        //DECLARE THE UI
        flashCard = (CardView) view.findViewById(R.id.flashcard);
        revealView = (View) view.findViewById(R.id.revealView);
        flashText = (TextView) view.findViewById(R.id.flashcard_text);
        toolbarTitle = (TextView) getActivity().findViewById(R.id.toolbar_title);
        flashCardBttn = (Button) view.findViewById(R.id.flashcard_button);
        flashCardBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isAnswerRevealed == 0){
                    RevealAnswer();
                } else {
                    //LOAD NEXT QUESTION
                    loadNextQuestion();
                }

            }
        });

        //GET BUNDLE
        Bundle bundle = getArguments();
        if (bundle != null){
            question = bundle.getString("question");
            answer = bundle.getString("answer");
            flashText.setText(question);
        }

        //SET FLASH CARD BUTTON
        setButtonText();

        StartTimer();

        //REMOVE MENU
        setHasOptionsMenu(true);


        return view;
    }

    //REMOVE MENU
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_flashcards, menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.action_home:
                getFragmentManager().popBackStack(0,0);
                getFragmentManager().popBackStack();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    private void setButtonText(){
        flashCardBttn.setText(buttonText[isAnswerRevealed]);
    }

    private void StartTimer(){
        timer = new CountDownTimer(time, 1000) {
            @Override
            public void onTick(long l) {
                toolbarTitle.setText(String.valueOf(l/1000)+" seconds");
            }

            @Override
            public void onFinish() {
                toolbarTitle.setText("Times up");
                Toast.makeText(getActivity(), "DONE!!!!", Toast.LENGTH_LONG).show();
            }
        }.start();
    }

    private void RevealAnswer(){
        //IF THEY ARE UP TO DATE, RUN ANIMATIONS
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            int cx = revealView.getWidth()/2;
            int cy = revealView.getHeight()/2;
//            float finalRadius = (float) Math.hypot(cx,cy);
            float finalRadius = (float) revealView.getHeight();
            Animator anim = ViewAnimationUtils.createCircularReveal(revealView, cx, cy, 0, finalRadius);
            anim.setDuration(600);
            revealView.setVisibility(View.VISIBLE);
            //SET UP COMPLETION LISTENER
            anim.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    //WHERE THE ANSWER TRULY IS REVEALED
                    flashCard.setCardBackgroundColor(getActivity().getResources().getColor(R.color.primary));
                    flashText.setText(answer);
                    revealView.setVisibility(View.INVISIBLE);
                    isAnswerRevealed = 1;
                    setButtonText();
                    timer.cancel();
                }
            });
            anim.start();

        } else {
            //IF NOT, SHOW THE ANSWER
            flashCard.setCardBackgroundColor(getActivity().getResources().getColor(R.color.primary));
            flashText.setText("Sorry, your wrong");
            isAnswerRevealed = 1;
            setButtonText();
            timer.cancel();
        }
    }

    private void loadNextQuestion(){
        Flash_Activity activity = (Flash_Activity) getActivity();
        activity.loadNextFlashCard();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (timer != null){
            timer.cancel();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (timer != null){
            timer.cancel();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (timer != null){
            timer.start();
        }
    }


}
