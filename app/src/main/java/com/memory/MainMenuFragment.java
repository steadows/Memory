package com.memory;

import static androidx.navigation.Navigation.findNavController;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
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

public class MainMenuFragment extends Fragment {

    ImageView playGameButton;
    TextView playGameText;
    NavController navController;

    public MainMenuFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main_menu, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);
        playGameButton = view.findViewById(R.id.play_game_button);
        playGameText = view.findViewById(R.id.Play_button_text);

        // Gives buttons bounce animation "bubble button"
        final Animation bounce = AnimationUtils.loadAnimation(getActivity(), R.anim.bounce);

        // Set then initialize the "bounce" here
        MyBounceInterpolator interpolator = new MyBounceInterpolator(0.15, 20);
        bounce.setInterpolator(interpolator);

        playGameButton.startAnimation(bounce);
        playGameText.startAnimation(bounce);

        // Countdown till next "bounce"
        new CountDownTimer(100000, 10000) {
            @Override
            public void onTick(long millisUntilFinished) {
                playGameButton.startAnimation(bounce);
                playGameText.startAnimation(bounce);
            }
            @Override
            public void onFinish() { /* Do nothing when countdown ends */ }
        }.start();

        playGameButton.setOnClickListener(v ->
                findNavController(v).navigate(R.id.action_main_to_submenu));
    }
}