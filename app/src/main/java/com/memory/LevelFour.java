package com.memory;

import static androidx.navigation.Navigation.findNavController;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import android.os.CountDownTimer;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ImageView;
import com.gusakov.library.PulseCountDown;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

public class LevelFour extends Fragment {

    // Static variable to hold user's completion status for this level
    static boolean completed = false;

    NavController navController;
    int numMatches = 0;
    int flipped = 0;
    int match1;
    int match2;
    ImageView prevCard;
    Random random = new Random();

    // Library of all Images and Cards
    private final int[] images = { R.drawable.louie, R.drawable.michigan_flag, R.drawable.lions,
            R.drawable.grand_rapids, R.drawable.detroit_statue1, R.drawable.founder,
            R.drawable.tigers, R.drawable.umich, R.drawable.msu, R.drawable.pistons };

    private final int[] cardDeck = { R.id.card4_1, R.id.card4_2, R.id.card4_3, R.id.card4_4,
            R.id.card4_5, R.id.card4_6, R.id.card4_7, R.id.card4_8, R.id.card4_9, R.id.card4_10,
            R.id.card4_11, R.id.card4_12, R.id.card4_13, R.id.card4_14, R.id.card4_15,
            R.id.card4_16, R.id.card4_17, R.id.card4_18, R.id.card4_19, R.id.card4_20};

    // Creating Timer
    Handler timeHandler = new Handler();
    long startTime;
    Boolean firstStart = true;
    boolean allMatchesFound = false;

    // Variable to hold user's score
    long timerScore;

    // Variables to link the widgets on screen
    TextView timerText;
    TextView matches;

    public LevelFour() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.level_four, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);

        // Assign to variables
        timerText = view.findViewById(R.id.timer_text4);
        matches = view.findViewById(R.id.score_number_text4);
        PulseCountDown pulseCountDown = view.findViewById(R.id.pulseCountDown4);

        // Start 5 second countdown before the timer starts
        setCardsClick(false);
        pulseCountDown.start(() -> setCardsClick(true));

        // Call either timer to start after a delay of 5 1/2 seconds
        if(Boolean.TRUE.equals(SubMenu.traditionalMode)) {
            timeHandler.postDelayed(timerUp, 5500);
        }
        if(Boolean.FALSE.equals(SubMenu.traditionalMode)) {
            new CountDownTimer(5000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) { timerText.setText(R.string.level4); }

                @Override
                public void onFinish() { timerDown(); } }.start();
        }

        shuffle();
        shuffle();
    }

    /***********************************************************
     * Checks to see if match. If theres a match call match
     * function, else set all pictures to the back of the card.
     ***********************************************************/
    public void checkMatch(ImageView currCard, int image, int value) {
        currCard.setImageResource(image);
        currCard.setEnabled(false);

        if (flipped == 0) {
            match1 = value;
        }
        if (flipped == 1) {
            match2 = value;
        }

        flipped++;

        if (flipped >= 2) {
            setCardsClick(false);
            if (match1 == match2) {
                matchFound(currCard, prevCard);
                setCardsClick(true);
            }
            if (match1 != match2) {
                new CountDownTimer(2000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished){/*Nothing to do during countdown*/}

                    @Override
                    public void onFinish() {
                        for (int j : cardDeck) {
                            ImageView cardTemp = requireActivity().findViewById(j);
                            cardTemp.setImageResource(R.drawable.card_back);
                        }
                        setCardsClick(true);
                        flipped = 0;
                    }
                }.start();
            }
        }
        prevCard = currCard;
    }

    /******************************************************
     * Match is found - remove both selected cards and
     * reset variables. If last match move to next screen
     * @param currCard - second card selected
     * @param prevCard - first card selected
     ******************************************************/
    public void matchFound(ImageView currCard, ImageView prevCard) {
        Animation fade = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_out);
        numMatches = numMatches + 1;
        matches.setText(String.valueOf(numMatches));

        currCard.startAnimation(fade);
        currCard.setVisibility(View.GONE);
        prevCard.startAnimation(fade);
        prevCard.setVisibility(View.GONE);

        if (numMatches == cardDeck.length/2) {
            // Time is sent in elapsed milliseconds to FinishedFragment
            Bundle score = new Bundle();
            score.putLong("timerScore", timerScore);
            getParentFragmentManager().setFragmentResult("timerScore", score);
            firstStart = true;
            numMatches = 0;
            allMatchesFound = true;
            completed = true;
            navController.navigate(R.id.finishedFragment);
        }

        flipped = 0;
    }

    /****************************************************
     * Changes index of array to "shuffle cards"
     ***************************************************/
    public void shuffle() {
        Animation fadeIn = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_in);
        ArrayList<ImageView> cards = new ArrayList<>();
        ImageView[] cardArrParents = new ImageView[cardDeck.length / 2];
        ImageView[] cardArrChild = new ImageView[cardDeck.length / 2];
        flipped = 0;
        match2 = 999;

        // Shuffle Images by changing order of array
        for (int i = 0; i < images.length; i++) {
            int r = random.nextInt(images.length - i);

            int temp = images[r];
            images[r] = images[i];
            images[i] = temp;
        }

        // Fade cards in and add cards to ArrayList 'cards'
        for (int j : cardDeck) {
            ImageView cardTemp = requireActivity().findViewById(j);
            cardTemp.setImageResource(R.drawable.card_back);
            cardTemp.startAnimation(fadeIn);
            cardTemp.setVisibility(View.VISIBLE);
            cards.add(cardTemp);
        }

        for (int i = 0; i < cardDeck.length / 2; i++) {
            int r = random.nextInt(cards.size());
            cardArrParents[i] = cards.get(r);
            cards.remove(r);
        }

        for (int i = 0; i < cardDeck.length / 2; i++) {
            cardArrChild[i] = cards.get(i);
        }

        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < cardDeck.length / 2; i++) {
            list.add(i);
        }

        Map<ImageView, Integer> cardParents = new HashMap<>();
        Map<ImageView, Integer> cardChildren = new HashMap<>();

        for (int i = 0; i < cardDeck.length / 2; i++) {
            int r = random.nextInt(list.size());
            cardParents.put(cardArrParents[i], list.get(r));
            list.remove(r);
        }

        for (int i = 0; i < cardDeck.length / 2; i++) {
            list.add(i);
        }

        for (int i = 0; i < cardDeck.length / 2; i++) {
            int r = random.nextInt(list.size());
            cardChildren.put(cardArrChild[i], list.get(r));
            list.remove(r);
        }

        for (ImageView key : cardParents.keySet()) {
            key.setOnClickListener(v -> checkMatch(key, images[cardParents.get(key)], cardParents.get(key)));
        }
        for (ImageView key : cardChildren.keySet()) {
            key.setOnClickListener(v -> checkMatch(key, images[cardChildren.get(key)], cardChildren.get(key)));
        }
    }

    /******************************************************
     * Sets all ImageViews being used to clickable
     * or un-clickable.
     * @param bool - set to either true or false
     ******************************************************/
    private void setCardsClick(Boolean bool) {
        ImageView cardTemp;
        for (int j : cardDeck) {
            cardTemp = requireActivity().findViewById(j);
            if (Boolean.TRUE.equals(bool)) {
                cardTemp.setEnabled(true);
            }
            if (Boolean.FALSE.equals(bool)) {
                cardTemp.setEnabled(false);
            }
        }
    }

    /****************************************************
     * Creates Runnable object that constantly updates
     * the variable timerText.
     ***************************************************/
    final Runnable timerUp = new Runnable() {
        public void run() {
            if (Boolean.TRUE.equals(firstStart)) {
                startTime = System.currentTimeMillis();
                firstStart = false;
            }
            long millis = System.currentTimeMillis() - (startTime);
            timerScore = millis;

            int seconds = (int) (millis / 1000);
            int minutes = seconds / 60;
            millis = millis % 100;
            seconds = seconds % 60;

            timeHandler.postDelayed(this, 0);
            timerText.setText(String.format(Locale.getDefault(),
                    "%d:%02d:%02d", minutes, seconds, millis));
        }
    };

    /****************************************************
     * Starts a countdown timer
     ***************************************************/
    public void timerDown(){

        CountDownTimer timerCount = new CountDownTimer(90000, 1) {

            public void onTick(long milliseconds) {
                long minutes = (milliseconds / 60000) % 60;
                long seconds = (milliseconds / 1000) % 60;
                timerScore = milliseconds;
                milliseconds = milliseconds % 100;
                timerText.setText(String.format(Locale.getDefault(),
                        "%d:%02d:%02d", minutes, seconds, milliseconds));
            }
            public void onFinish() {
                if (Boolean.FALSE.equals(allMatchesFound)){
                    timeUp();
                }
            }
        };
        timerCount.start();
    }

    /*************************************************************
     * When time is up make cards disappear and make buttons
     * appear to give the user the option to play again or return
     * to sub menu
     *************************************************************/
    public void timeUp(){
        Animation fadeIn = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_in);
        Animation blink = AnimationUtils.loadAnimation(getActivity(), R.anim.blinks);
        Animation bounce = AnimationUtils.loadAnimation(getActivity(), R.anim.bounce);

        for (int j : cardDeck) {
            ImageView cardTemp = requireActivity().findViewById(j);
            cardTemp.setVisibility(View.GONE);
        }
        timerText.setText(R.string.zero);
        ImageView backButton = requireActivity().findViewById(R.id.timeUpButtonSubMenu4);
        TextView backText = requireActivity().findViewById(R.id.timeUpButtonSubMenuText4);
        ImageView retryButton = requireActivity().findViewById(R.id.timeUpButtonRetry4);
        TextView retryText = requireActivity().findViewById(R.id.timeUpButtonRetryText4);
        TextView timeUpText = requireActivity().findViewById(R.id.timeUpText4);

        MyBounceInterpolator interpolator = new MyBounceInterpolator(0.2, 20);
        bounce.setInterpolator(interpolator);

        backButton.startAnimation(fadeIn);
        retryButton.startAnimation(fadeIn);
        backButton.setVisibility(View.VISIBLE);
        retryButton.startAnimation(fadeIn);
        retryButton.setVisibility(View.VISIBLE);
        backText.startAnimation(fadeIn);
        backText.setVisibility(View.VISIBLE);
        retryText.startAnimation(fadeIn);
        retryText.setVisibility(View.VISIBLE);
        timeUpText.startAnimation(fadeIn);
        timeUpText.setVisibility(View.VISIBLE);
        timeUpText.startAnimation(blink);
        timerText.startAnimation(blink);
        backButton.setVisibility(View.VISIBLE);

        backButton.setOnClickListener(v ->
                findNavController(v).navigate(R.id.subMenu_isabels_version));

        retryButton.setOnClickListener(view1 -> {
            getParentFragmentManager().beginTransaction().remove(this).commitNowAllowingStateLoss();
            navController.navigate(R.id.LevelFour);
        });

        new CountDownTimer(3000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {/* Nothing to do during countdown */ }
            @Override
            public void onFinish() { new CountDownTimer(100000, 10000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    backButton.startAnimation(bounce);
                    backText.startAnimation(bounce);
                    retryText.startAnimation(bounce);
                    retryButton.startAnimation(bounce);
                }
                @Override
                public void onFinish() { /* Nothing to do during countdown */ }
            }.start();}
        }.start();
    }
}