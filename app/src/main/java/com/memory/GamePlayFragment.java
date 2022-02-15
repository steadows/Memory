package com.memory;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.gusakov.library.PulseCountDown;
import com.gusakov.library.java.interfaces.OnCountdownCompleted;

import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GamePlayFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GamePlayFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    NavController navController;

    // Library of all cards available for use
    private static Card[] allCards;

    // Creating Timer
    Handler timeHandler = new Handler();
    long startTime;
    Boolean firstStart = true;
    TextView timerText;

    public GamePlayFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GamePlayFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GamePlayFragment newInstance(String param1, String param2) {
        GamePlayFragment fragment = new GamePlayFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
     }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_game_play, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);
        timerText = view.findViewById(R.id.timer_text);
        PulseCountDown pulseCountDown = view.findViewById(R.id.pulseCountDown);

        pulseCountDown.bringToFront();

        pulseCountDown.start(new OnCountdownCompleted(  ) {
            @Override
            public void completed(  ) {
                // What to do when countdown completes
            }
        } );

        timeHandler.postDelayed(timeRunnable,5500);
    };


    /****************************************************
     * Creates Runnable object that constantly updates
     * the variable timerText
     ***************************************************/
    final Runnable timeRunnable = new Runnable() {
        public void run() {
            if(firstStart){
                startTime = System.currentTimeMillis() ;
                firstStart = false;
            }
            long millis = System.currentTimeMillis() - (startTime);
            int seconds = (int) (millis / 1000);
            int minutes = seconds / 60;
            millis = millis % 1000;

            seconds = seconds % 60;
            timeHandler.postDelayed(this, 0);
            timerText.setText(String.format("%d:%02d:%03d", minutes, seconds, millis));
        }
    };







}