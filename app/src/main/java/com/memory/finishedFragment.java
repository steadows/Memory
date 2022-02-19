package com.memory;

import static androidx.navigation.Navigation.findNavController;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link finishedFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class finishedFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public finishedFragment() {
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
    public static finishedFragment newInstance(String param1, String param2) {
        finishedFragment fragment = new finishedFragment();
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
        return inflater.inflate(R.layout.fragment_finished, container, false);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        NavController navController = Navigation.findNavController(view);
        ImageView backButton = view.findViewById(R.id.back_button);
        TextView score = view.findViewById(R.id.score);
        TextView buttonText = view.findViewById(R.id.back_button_text);

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