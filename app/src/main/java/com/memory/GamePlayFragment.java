package com.memory;

import static androidx.navigation.Navigation.findNavController;

import android.media.Image;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.gusakov.library.PulseCountDown;
import com.gusakov.library.java.interfaces.OnCountdownCompleted;

import android.os.Handler;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ImageView;
import java.lang.reflect.Array;
import java.util.Random;

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
    int numMatches = 0, match1, match2;
    int flipped = 0;
    TextView matches;

    // Library of all images and buttons
//    private static Card[] allCards;
      int[] images = {R.drawable.gvsu,R.drawable.michigan_flag,R.drawable.lions,R.drawable.grand_rapids};
      int[] imageViews = {R.id.test,R.id.test2};

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

        matches = view.findViewById(R.id.score_number_text);
        PulseCountDown pulseCountDown = view.findViewById(R.id.pulseCountDown);
        shuffle(images,4);

        ImageView card1 = (ImageView) view.findViewById(R.id.test);
        card1.setImageResource(R.drawable.card_back);
        card1.setClickable(true);

        card1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                card1.setImageResource(images[1]);

               if(flipped == 0){
                   match1 = 1;
               }
                if(flipped == 1){
                    match2 = 1;
                }
                flipped++;
                if (flipped == 2) {
                    if (match1 == match2){
                        numMatches++;
                        matches.setText(String.valueOf(numMatches));
                        card1.setVisibility(View.GONE);
                        flipped = 0;
                    }
                    else
                        card1.setImageResource(R.drawable.card_back);
                }
            };
        });

        ImageView card2 = (ImageView) view.findViewById(R.id.test2);
        card2.setImageResource(R.drawable.card_back);
        card2.setClickable(true);

        card2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                card2.setImageResource(images[1]);

               if(flipped == 0){
                   match1 = 1;
               }
               if(flipped == 1){
                    match2 = 1;
               }
               flipped++;
                if (flipped == 2) {
                    if (match1 == match2){
                        numMatches++;
                        matches.setText(String.valueOf(numMatches));
                        card2.setVisibility(View.GONE);
                        flipped = 0;
                    }
                    else
                        card2.setImageResource(R.drawable.card_back);
                }
            };
        });

        ImageView card4 = (ImageView) view.findViewById(R.id.test4);
        card4.setImageResource(R.drawable.card_back);
        card4.setClickable(true);

        card4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                card4.setImageResource(images[0]);

               if(flipped == 0){
                   match1 = 0;
               }
               if(flipped == 1){
                    match2 = 0;
               }
               flipped++;
                if (flipped == 2) {
                    if (match1 == match2){
                        numMatches++;
                        matches.setText(String.valueOf(numMatches));
                        card4.setVisibility(View.GONE);
                        flipped = 0;
                    }
                    else
                        card4.setImageResource(R.drawable.card_back);
                }
            };
        });


        ImageView card3 = (ImageView) view.findViewById(R.id.test3);
        card3.setImageResource(R.drawable.card_back);
        card3.setClickable(true);

        card3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                card3.setImageResource(images[0]);

               if(flipped == 0){
                   match1 = 0;
               }
               if(flipped == 1){
                   match2 = 0;
               }
                flipped++;
                if (flipped == 2) {
                    if (match1 == match2){
                        numMatches++;
                        matches.setText(String.valueOf(numMatches));
                        card3.setVisibility(View.GONE);
                        flipped = 0;
                    }
                    else
                        card3.setImageResource(R.drawable.card_back);
                }
            };
        });


        pulseCountDown.start(new OnCountdownCompleted(  ) {
            @Override
            public void completed(  ) {
                // When countdown completes print GO!
//                String string = getString(R.string.Go);
//                pulseCountDown.setText(string);
            }
        } );
        timeHandler.postDelayed(timeRunnable,5500);



    }

    /****************************************************
     * Creates new array
     ***************************************************/
    public void shuffle(int[] cards, int n){
        Random random = new Random();

        for (int i=0;i<n;i++){
            int r = random.nextInt(n-i);

            int temp = cards[r];
            cards[r] = cards[i];
            cards[i] = temp;
        }
    }

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
            millis = millis % 100;

            seconds = seconds % 60;
            timeHandler.postDelayed(this, 0);
            timerText.setText(String.format("%d:%02d:%02d", minutes, seconds, millis));
        }
    };

//    final Handler handler = new Handler();
//    handler.postDelayed(new Runnable() {
//        @Override
//        public void run() {
//            //Do something after 2s
//        }
//    }, 2000);




}