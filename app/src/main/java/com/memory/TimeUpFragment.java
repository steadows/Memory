package com.memory;


import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
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

public class TimeUpFragment extends Fragment {

    ImageView backButton;
    TextView buttonText;

    // User's time in milliseconds
    private long userTimeScore;
    private TextView score;

    public TimeUpFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Listen for a score to be sent from GamePlayFragment
//        getParentFragmentManager().setFragmentResultListener("timerScore",
//                this, (requestKey, result) -> {
//                    userTimeScore = result.getLong("timerScore");
//
//                    if (userTimeScore > 0) {
//                        long millis = userTimeScore;
//                        int minutes = (int) (millis / 60000);
//                        millis = millis - (minutes * 60000L);
//                        int seconds = (int) (millis / 1000);
//                        millis = millis - (seconds * 1000L);
//                        millis = Math.round(millis / 10.0);
//                        score.setText(String.format(Locale.getDefault(),
//                                "%02d:%02d:%02d", minutes, seconds, millis));
//                    }
//                });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.time_up, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize components
        backButton = view.findViewById(R.id.back_button);
        buttonText = view.findViewById(R.id.back_button_text);
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

        backButton.setOnClickListener((v ->
                Navigation.findNavController(v).navigate(R.id.action_timeup_to_subMenu_Single)));

        // Countdown till next "bounce"
        new CountDownTimer(100000, 6000) {
            @Override
            public void onTick(long millisUntilFinished) {
                buttonText.startAnimation(bounce);
                backButton.startAnimation(bounce);
            }
            @Override
            public void onFinish() { /* Do nothing, countdown will never reach */ }
        }.start();
    }
}