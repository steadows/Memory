package com.memory;

import static androidx.navigation.Navigation.findNavController;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class InstructionFragment extends Fragment {

    public InstructionFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_instruction, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageView instructionBack = view.findViewById(R.id.back_intruction_button);
        TextView backButtonText = view.findViewById(R.id.back_instruction_button_text);

        // Gives buttons bounce animation "bubble button"
        final Animation bounce = AnimationUtils.loadAnimation(getActivity(), R.anim.bounce);

        // Set then initialize the "bounce" here
        MyBounceInterpolator interpolator = new MyBounceInterpolator(0.15, 20);
        bounce.setInterpolator(interpolator);

        instructionBack.startAnimation(bounce);
        backButtonText.startAnimation(bounce);

        instructionBack.setOnClickListener(v ->
                findNavController(v).navigate(R.id.action_instructionFragment_to_subMenu));
    }
}