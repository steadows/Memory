package com.memory;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.gusakov.library.PulseCountDown;

import android.os.CountDownTimer;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ImageView;
import java.util.Random;


public class GamePlayFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    NavController navController;
    int numMatches = 0, flipped = 0, match1, match2;
    ImageView prevCard;

    // Library of all Images and Cards
    int[] images = {R.drawable.louie,R.drawable.michigan_flag,R.drawable.lions,R.drawable.grand_rapids,R.drawable.cherry_farm,R.drawable.detroit_statue1};
    int[] cardDeck = {R.id.card1,R.id.card2,R.id.card3,R.id.card4,R.id.card5,R.id.card6};

    // Creating Timer
    Handler timeHandler = new Handler();
    long startTime;
    Boolean firstStart = true;

    // Variables to link the widgets on screen
    TextView timerText;
    TextView matches;

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
        setCardsNotClick();

        // Assign to variables
        timerText = view.findViewById(R.id.timer_text);
        matches = view.findViewById(R.id.score_number_text);
        PulseCountDown pulseCountDown = view.findViewById(R.id.pulseCountDown);

        // Initial Countdown when starting the game
        pulseCountDown.start(this::flipAllCards);

        timeHandler.postDelayed(timeRunnable,5500);

        shuffle(images, cardDeck.length);
        shuffle(images, cardDeck.length);

        // Assign Images to variables
        ImageView card1 = view.findViewById(R.id.card1);
        ImageView card2 = view.findViewById(R.id.card2);
        ImageView card3 = view.findViewById(R.id.card3);
        ImageView card4 = view.findViewById(R.id.card4);
        ImageView card5 = view.findViewById(R.id.card5);
        ImageView card6 = view.findViewById(R.id.card6);

        // Listen for click
        card1.setOnClickListener(v -> checkMatch(card1, images[1], 1));
        card2.setOnClickListener(v -> checkMatch(card2, images[1], 1));
        card3.setOnClickListener(v -> checkMatch(card3, images[0], 0));
        card4.setOnClickListener(v -> checkMatch(card4, images[0], 0));
        card5.setOnClickListener(v -> checkMatch(card5, images[2], 2));
        card6.setOnClickListener(v -> checkMatch(card6, images[2], 2));

    }

    /***********************************************************
     * Checks to see if match. If theres a match call match
     * function, else set all pictures to the back of the card
     ***********************************************************/
    public void checkMatch(ImageView currCard, int image, int value){
        currCard.setImageResource(image);
        currCard.setEnabled(false);

        if(flipped == 0) {
            match1 = value;
            prevCard = currCard;
        }
        if(flipped == 1) {
            match2 = value;
        }

        flipped++;

        if (flipped >= 2) {
            setCardsNotClick();
            if (match1 == match2){
                matchFound(currCard,prevCard);
                setCardsClick();
                }
            if (match1 != match2){
                new CountDownTimer(2000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) { }
                    @Override
                    public void onFinish() { flipAllCards();} }.start();
                prevCard = currCard;
                flipped = 0;
                }
        }
    }


    /****************************************************
     * Match is found - remove both selected cards and
     * reset variables
     ***************************************************/
    public void matchFound(ImageView currCard, ImageView prevCard){
        Animation Fade = AnimationUtils.loadAnimation(getActivity(),R.anim.fade_out);

        numMatches++;
        matches.setText(String.valueOf(numMatches));

        currCard.startAnimation(Fade);
        currCard.setVisibility(View.GONE);
        currCard.setClickable(false);
        prevCard.startAnimation(Fade);
        prevCard.setVisibility(View.GONE);
        prevCard.setClickable(false);

        flipped = 0;
    }

    /****************************************************
     * Set all card pictures to back-of-card
     ***************************************************/
    public void flipAllCards(){
        ImageView cardTemp;
        for (int j : cardDeck) {
            cardTemp = requireActivity().findViewById(j);
            cardTemp.setImageResource(R.drawable.card_back);
        }
        setCardsClick();
    }

    /****************************************************
     * Changes index of array to "shuffle cards"
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
     * Sets all ImageViews being used to clickable
     ***************************************************/
    private void setCardsClick() {
        ImageView cardTemp;
        for (int j : cardDeck) {
            cardTemp = requireActivity().findViewById(j);
            cardTemp.setEnabled(true);
        }
    }

    /****************************************************
     * Sets all ImageViews being used to un-clickable
     ***************************************************/
    private void setCardsNotClick() {
        ImageView cardTemp;
        for (int j : cardDeck) {
            cardTemp = requireActivity().findViewById(j);
            cardTemp.setEnabled(false);
        }
    }

    /****************************************************
     * Sets all ImageViews being used to un-clickable
     ***************************************************/
    public interface sendDataInterface{


    }



//    public void sendInfo(){
//        Bundle bundle = new Bundle();
//        bundle.putString("key", String.valueOf(matches));
//        CompletionScreen fragment= new CompletionScreen();
//        fragment.setArguments(bundle);
//    }


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


}