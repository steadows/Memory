package com.memory;

import static androidx.navigation.Navigation.findNavController;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

public class SubMenu extends Fragment implements AdapterView.OnItemSelectedListener{

    ImageView traditionalButton;
    ImageView timeTrialButton;
    TextView traditionalText;
    TextView timeTrialText;
    Button instructionButton;
    NavController navController;

    public SubMenu() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sub_menu, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);

        // Find buttons on screen and assign them to variables
        traditionalButton = view.findViewById(R.id.Traditional_button);
        traditionalText = view.findViewById(R.id.traditional_mode_text);
        timeTrialButton = view.findViewById(R.id.Time_trial_button);
        timeTrialText = view.findViewById(R.id.time_trial_text);
        instructionButton = view.findViewById(R.id.Instruction_button);

        // Spinner element (Drop-down menu)
        Spinner spin = view.findViewById(R.id.spinner);

        String[] levels = { "Level One", "Level Two", "Level Three", "Level Four", "Level Five",
                            "Level Six", "Level Seven", "Level Eight", "Time Trial - Level One"};

        if (!LevelOne.completed){
            levels[1] = "Level Two [Locked]";
        }
        if (!LevelTwo.completed){
            levels[2] = "Level Three [Locked]";
        }
        if (!LevelThree.completed){
            levels[3] = "Level Four [Locked]";
        }
        if (!LevelFour.completed){
            levels[4] = "Level Five [Locked]";
        }
        if (!LevelFive.completed){
            levels[5] = "Level Six [Locked]";
        }
        if (!LevelSix.completed){
            levels[6] = "Level Seven [Locked]";
        }
        if (!LevelSeven.completed){
            levels[7] = "Level Eight [Locked]";
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, levels);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(adapter);
        spin.setOnItemSelectedListener(this);

        // Gives buttons bounce animation "bubble button"
        final Animation bounce = AnimationUtils.loadAnimation(getActivity(), R.anim.bounce);

        MyBounceInterpolator interpolator = new MyBounceInterpolator(0.2, 20);
        bounce.setInterpolator(interpolator);

        traditionalButton.startAnimation(bounce);
        traditionalText.startAnimation(bounce);
        timeTrialButton.startAnimation(bounce);
        timeTrialText.startAnimation(bounce);

        timeTrialButton.setOnClickListener(v ->
                findNavController(v).navigate(R.id.action_subMenu_Single_to_mainMenuFragment));

        instructionButton.setOnClickListener(v ->
                findNavController(v).navigate(R.id.action_subMenu_Single_to_instructionFragment));
    }

    @Override
    public void onItemSelected(AdapterView parent, View view, int position, long id) {

        if (position == 0) {
            traditionalButton.setOnClickListener(v ->
                    findNavController(v).navigate(R.id.action_subMenu_Single_to_LevelOne));
         }
        if (position == 1) {
            if (LevelOne.completed) {
                traditionalButton.setOnClickListener(v ->
                        findNavController(v).navigate(R.id.action_subMenu_Single_to_LevelTwo));
            }
        }
        if (position == 2) {
            if (LevelTwo.completed) {
                traditionalButton.setOnClickListener(v ->
                        findNavController(v).navigate(R.id.action_subMenu_Single_to_levelThree));
            }
        }
        if (position == 3) {
            if (LevelThree.completed) {
                traditionalButton.setOnClickListener(v ->
                        findNavController(v).navigate(R.id.action_subMenu_Single_to_LevelFour));
            }
        }
        if (position == 4) {
            if (LevelFour.completed) {
                traditionalButton.setOnClickListener(v ->
                        findNavController(v).navigate(R.id.action_subMenu_Single_to_levelFive));
            }
        }
        if (position == 5) {
            if (LevelFive.completed) {
                traditionalButton.setOnClickListener(v ->
                        findNavController(v).navigate(R.id.action_subMenu_Single_to_levelSix));
            }
        }
        if (position == 6) {
            if (LevelSix.completed) {
                traditionalButton.setOnClickListener(v ->
                        findNavController(v).navigate(R.id.action_subMenu_Single_to_levelSeven));
            }
        }
        if (position == 7) {
            if (LevelEight.completed) {
                traditionalButton.setOnClickListener(v ->
                        findNavController(v).navigate(R.id.action_subMenu_Single_to_levelEight));
            }
        }
        if (position == 8) {
            traditionalButton.setOnClickListener(v ->
                    findNavController(v).navigate(R.id.action_subMenu_Single_to_levelOne_time_trial));
        }
    }

    @Override
    public void onNothingSelected(AdapterView parent) {
        // Do nothing.
    }
}