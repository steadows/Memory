package com.memory;

import static androidx.navigation.Navigation.findNavController;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FinishedFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FinishedFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private int mParam1;
    private String mParam2;

    // User's time in milliseconds
    private long userTimeScore;
    private TextView score;

    public FinishedFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment finishedFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FinishedFragment newInstance(String param1, String param2) {
        FinishedFragment fragment = new FinishedFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Listen for a score to be sent from GamePlayFragment
        getParentFragmentManager().setFragmentResultListener("timerScore",
                this, (requestKey, result) -> {
                    userTimeScore = result.getLong("timerScore");

                    if (userTimeScore > 0) {
                        long millis = userTimeScore;
                        int minutes = (int) (millis / 60000);
                        millis = millis - (minutes * 60000L);
                        int seconds = (int) (millis / 1000);
                        millis = millis - (seconds * 1000L);
                        millis = Math.round(millis / 10.0);
                        score.setText(String.format(Locale.getDefault(),
                                "%02d:%02d:%02d", minutes, seconds, millis));
                    }

                });

        if (getArguments() != null) {
            mParam1 = getArguments().getInt(ARG_PARAM1);
            //mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_finished, container, false);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize components
        NavController navController = Navigation.findNavController(view);
        ImageView backButton = view.findViewById(R.id.back_button);
        TextView buttonText = view.findViewById(R.id.back_button_text);
        score = view.findViewById(R.id.score);

        // Initialize animations
        final Animation bounce = AnimationUtils.loadAnimation(getActivity(), R.anim.bounce);
        final Animation blink = AnimationUtils.loadAnimation(getActivity(), R.anim.blinks);

        // Set then initialize the "bounce" here
        MyBounceInterpolator interpolator = new MyBounceInterpolator(0.15, 20);
        bounce.setInterpolator(interpolator);

        backButton.startAnimation(bounce);
        buttonText.startAnimation(bounce);
        score.startAnimation(blink);

        // Countdown till next "bounce"
        new CountDownTimer(100000, 6000) {
            @Override
            public void onTick(long millisUntilFinished) {
                buttonText.startAnimation(bounce);
                backButton.startAnimation(bounce);
            }
            @Override
            public void onFinish() { }
        }.start();

        backButton.setOnClickListener((v ->
                findNavController(v).navigate(R.id.action_finishedFragment_to_subMenu_Single)));

    }
}