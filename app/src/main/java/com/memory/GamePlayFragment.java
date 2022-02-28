package com.memory;


import static androidx.navigation.Navigation.findNavController;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import android.os.CountDownTimer;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ImageView;
import com.gusakov.library.PulseCountDown;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

public class GamePlayFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    //  private String mParam1;
    private String mParam1;
    private String mParam2;

    NavController navController;
    int numMatches = 0;
    int flipped = 0;
    int match1;
    int match2;
    ImageView prevCard;
    Button next;

    // Library of all Images and Cards
    private final int[] images = {R.drawable.louie, R.drawable.michigan_flag, R.drawable.lions, R.drawable.grand_rapids, R.drawable.cherry_farm, R.drawable.detroit_statue1};
    private final int[] cardDeck = {R.id.card1, R.id.card2, R.id.card3, R.id.card4, R.id.card5, R.id.card6};

    // Creating Timer
    Handler timeHandler = new Handler();
    long startTime;
    Boolean firstStart = true;

    // Variable to hold user's score
    long timerScore;

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
     * @return A new instance of fragment GamePlayFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GamePlayFragment newInstance(String param1) {
        GamePlayFragment fragment = new GamePlayFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        //args.putString(ARG_PARAM2, param2);
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
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);
        setCardsNotClick();

        // Assign to variables
        timerText = view.findViewById(R.id.timer_text);
        matches = view.findViewById(R.id.score_number_text);
        PulseCountDown pulseCountDown = view.findViewById(R.id.pulseCountDown);

        // Initial Countdown when starting the game
        pulseCountDown.start(this::flipAllCards);

        timeHandler.postDelayed(timeRunnable, 5500);

        shuffle(images, cardDeck.length);
        shuffle(images, cardDeck.length);

        // Assign Images to variables
        ImageView card1 = view.findViewById(R.id.card1);
        ImageView card2 = view.findViewById(R.id.card2);
        ImageView card3 = view.findViewById(R.id.card3);
        ImageView card4 = view.findViewById(R.id.card4);
        ImageView card5 = view.findViewById(R.id.card5);
        ImageView card6 = view.findViewById(R.id.card6);

        ArrayList<ImageView> cards = new ArrayList<>();
        ImageView[] cardArrParents = new ImageView[3];
        ImageView[] cardArrChild = new ImageView[3];

        cards.add(card1);
        cards.add(card2);
        cards.add(card3);
        cards.add(card4);
        cards.add(card5);
        cards.add(card6);

        for (int i = 0; i < 3; i++){
            Random random = new Random();
            int r = random.nextInt(cards.size());
            cardArrParents[i] = cards.get(r);
            cards.remove(r);
        }

        for (int i = 0; i < 3; i++){
            cardArrChild[i] = cards.get(i);
        }

        ArrayList<Integer> nums = new ArrayList<>();
        nums.add(0);
        nums.add(1);
        nums.add(2);

        Map<ImageView, Integer> cardParents = new HashMap<>();
        Map<ImageView, Integer> cardChildren = new HashMap<>();

        for (int i = 0; i < 3; i++) {

            Random random = new Random();
            int r = random.nextInt(nums.size());
            cardParents.put(cardArrParents[i], nums.get(r));
            nums.remove(r);
        }

        nums.add(0);
        nums.add(1);
        nums.add(2);

        for (int i = 0; i < 3; i++) {

            Random random = new Random();
            int r = random.nextInt(nums.size());
            cardChildren.put(cardArrChild[i], nums.get(r));
            nums.remove(r);
        }

        for (ImageView key : cardParents.keySet()) {
            key.setOnClickListener(v -> checkMatch(key, images[cardParents.get(key)], cardParents.get(key)));
        }
        for (ImageView key : cardChildren.keySet()) {
            key.setOnClickListener(v -> checkMatch(key, images[cardChildren.get(key)], cardChildren .get(key)));
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
            // Send score to end screen if all matches are made
            // Time is sent in elapsed milliseconds
            // TODO: Send timer data to FinishedFragment
            Bundle score = new Bundle();
            score.putLong("timerScore", timerScore);
            getParentFragmentManager().setFragmentResult("timerScore", score);

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
        Random random = new Random();

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