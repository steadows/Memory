package com.memory;

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
        setCardsNotClick();

        // Assign to variables
        timerText = view.findViewById(R.id.timer_text4);
        matches = view.findViewById(R.id.score_number_text4);
        PulseCountDown pulseCountDown = view.findViewById(R.id.pulseCountDown4);

        // Initial Countdown when starting the game
        pulseCountDown.start(this::flipAllCards);

        timeHandler.postDelayed(timeRunnable, 5500);

        shuffle(images, images.length);
        shuffle(images, images.length);

        ArrayList<ImageView> cards = new ArrayList<>();
        ImageView[] cardArrParents = new ImageView[cardDeck.length/2];
        ImageView[] cardArrChild = new ImageView[cardDeck.length/2];

        for (int j : cardDeck) {
            ImageView card = view.findViewById(j);
            cards.add(card);
        }

        for (int i = 0; i < cardDeck.length/2; i++) {
            int r = random.nextInt(cards.size());
            cardArrParents[i] = cards.get(r);
            cards.remove(r);
        }

        for (int i = 0; i < cardDeck.length/2; i++) {
            cardArrChild[i] = cards.get(i);
        }

        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < cardDeck.length/2; i++) {
            list.add(i);
        }

        Map<ImageView, Integer> cardParents = new HashMap<>();
        Map<ImageView, Integer> cardChildren = new HashMap<>();

        for (int i = 0; i < cardDeck.length/2; i++) {
            int r = random.nextInt(list.size());
            cardParents.put(cardArrParents[i], list.get(r));
            list.remove(r);
        }

        for (int i = 0; i < cardDeck.length/2; i++) {
            list.add(i);
        }

        for (int i = 0; i < cardDeck.length/2; i++) {
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
            setCardsNotClick();
            if (match1 == match2) {
                matchFound(currCard, prevCard);
                setCardsClick();
            }
            if (match1 != match2) {
                new CountDownTimer(2000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {// Nothing do do during countdown
                    }
                    @Override
                    public void onFinish() {
                        flipAllCards();
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

        if (numMatches == cardDeck.length/2){
            // Time is sent in elapsed milliseconds
            // Send timer data to FinishedFragment
            Bundle score = new Bundle();
            score.putLong("timerScore", timerScore);
            getParentFragmentManager().setFragmentResult("timerScore", score);
            firstStart = true;
            numMatches = 0;
            flipped = 0;
            navController.navigate(R.id.finishedFragment);
        }

        currCard.startAnimation(fade);
        currCard.setVisibility(View.GONE);
        prevCard.startAnimation(fade);
        prevCard.setVisibility(View.GONE);

        flipped = 0;
    }

    /****************************************************
     * Sets all card pictures to back-of-card.
     ***************************************************/
    public void flipAllCards() {
        ImageView cardTemp;
        for (int j : cardDeck) {
            cardTemp = requireActivity().findViewById(j);
            cardTemp.setImageResource(R.drawable.card_back);
        }
        setCardsClick();
        flipped = 0;
    }

    /****************************************************
     * Changes index of array to "shuffle cards".
     * @param n - number of cards
     * @param cards - the array of imageViews (Cards)
     ***************************************************/
    public void shuffle(int[] cards, final int n) {

        for (int i = 0; i < n; i++) {
            int r = random.nextInt(n - i);

            int temp = cards[r];
            cards[r] = cards[i];
            cards[i] = temp;
        }
    }

    /****************************************************
     * Sets all ImageViews being used to clickable.
     ***************************************************/
    private void setCardsClick() {
        ImageView cardTemp;
        for (int j : cardDeck) {
            cardTemp = requireActivity().findViewById(j);
            cardTemp.setEnabled(true);
        }
    }

    /****************************************************
     * Sets all ImageViews being used to un-clickable.
     ***************************************************/
    private void setCardsNotClick() {
        ImageView cardTemp;
        for (int j : cardDeck) {
            cardTemp = requireActivity().findViewById(j);
            cardTemp.setEnabled(false);
        }
    }

    /****************************************************
     * Creates Runnable object that constantly updates
     * the variable timerText.
     ***************************************************/
    final Runnable timeRunnable = new Runnable() {
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
}