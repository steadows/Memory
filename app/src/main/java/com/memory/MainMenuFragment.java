package com.memory;

import static androidx.navigation.Navigation.findNavController;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.FragmentKt;

import android.os.CountDownTimer;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainMenuFragment extends Fragment {

    Button playGameButton;
    NavController navController;

    public MainMenuFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main_menu, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);
        playGameButton = view.findViewById(R.id.play_game_button);

        // Gives buttons bounce animation "bubble button"
        final Animation myAnim = AnimationUtils.loadAnimation(getActivity(), R.anim.bounce);

        MyBounceInterpolator interpolator = new MyBounceInterpolator(0.15, 20);
        myAnim.setInterpolator(interpolator);

        playGameButton.startAnimation(myAnim);

        new CountDownTimer(100000, 10000) {
            @Override
            public void onTick(long millisUntilFinished) {
                playGameButton.startAnimation(myAnim);
            }
            @Override
            public void onFinish() {
            }
        }.start();

        playGameButton.setOnClickListener(v ->
                findNavController(v).navigate(R.id.action_mainMenuFragment_to_subMenu_Single));
    }

}