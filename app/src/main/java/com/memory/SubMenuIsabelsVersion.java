package com.memory;

import static androidx.navigation.Navigation.findNavController;

import android.graphics.Color;
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
import android.widget.Toast;

public class SubMenuIsabelsVersion extends Fragment implements AdapterView.OnItemSelectedListener{

    Button L1Button;
    Button L2Button;
    Button L3Button;
    Button L4Button;
    Button L5Button;
    Button L6Button;
    Button L7Button;
    Button L8Button;

    Button instructionsButton;
    Button mainMenuButton;
    Spinner modeSpinner;
    NavController navController;

    static boolean traditionalMode = true;

    public SubMenuIsabelsVersion() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sub_menu_isabels_version, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);

        // Find buttons on screen and assign them to variables
        L1Button = view.findViewById(R.id.level_1_button);
        L2Button = view.findViewById(R.id.level_2_button);
        L3Button = view.findViewById(R.id.level_3_button);
        L4Button = view.findViewById(R.id.level_4_button);
        L5Button = view.findViewById(R.id.level_5_button);
        L6Button = view.findViewById(R.id.level_6_button);
        L7Button = view.findViewById(R.id.level_7_button);
        L8Button = view.findViewById(R.id.level_8_button);
        instructionsButton = view.findViewById(R.id.instructions_button);
        mainMenuButton = view.findViewById(R.id.submenu_back_button);

        // Spinner element (Drop-down menu)
        modeSpinner = view.findViewById(R.id.game_mode_spinner);

        if (!LevelOne.completed){
            L2Button.setEnabled(false);
            L2Button.setBackgroundResource(R.color.light_blue_650);
            L2Button.setTextColor(0x66000000);
        }
        if (!LevelTwo.completed){
            L3Button.setEnabled(false);
            L3Button.setBackgroundResource(R.color.light_blue_650);
            L3Button.setTextColor(0x66000000);
        }
        if (!LevelThree.completed){
            L4Button.setEnabled(false);
            L4Button.setBackgroundResource(R.color.light_blue_650);
            L4Button.setTextColor(0x66000000);
        }
        if (!LevelFour.completed){
            L5Button.setEnabled(false);
            L5Button.setBackgroundResource(R.color.light_blue_650);
            L5Button.setTextColor(0x66000000);
        }
        if (!LevelFive.completed){
            L6Button.setEnabled(false);
            L6Button.setBackgroundResource(R.color.light_blue_650);
            L6Button.setTextColor(0x66000000);
        }
        if (!LevelSix.completed){
            L7Button.setEnabled(false);
            L7Button.setBackgroundResource(R.color.light_blue_650);
            L7Button.setTextColor(0x66000000);
        }
        if (!LevelSeven.completed){
            L8Button.setEnabled(false);
            L8Button.setBackgroundResource(R.color.light_blue_650);
            L8Button.setTextColor(0x66000000);
        }

        String[] modes = { "Traditional", "Time Trial" };
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_item, modes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        modeSpinner.setAdapter(adapter);
        modeSpinner.setOnItemSelectedListener(this);

        L1Button.setOnClickListener(v ->
                findNavController(v).navigate(R.id.action_submenu_to_level_1));

        L2Button.setOnClickListener(v ->
                findNavController(v).navigate(R.id.action_subMenu_to_level_2));

        L3Button.setOnClickListener(v ->
                findNavController(v).navigate(R.id.action_submenu_to_level_3));

        L4Button.setOnClickListener(v ->
                findNavController(v).navigate(R.id.action_submenu_to_level_4));

        L5Button.setOnClickListener(v ->
                findNavController(v).navigate(R.id.action_submenu_to_level_5));

        L6Button.setOnClickListener(v ->
                findNavController(v).navigate(R.id.action_submenu_to_level_6));

        L7Button.setOnClickListener(v ->
                findNavController(v).navigate(R.id.action_submenu_to_level_7));

        L8Button.setOnClickListener(v ->
                findNavController(v).navigate(R.id.action_submenu_to_level_8));


        // Gives buttons bounce animation "bubble button"
        final Animation bounce = AnimationUtils.loadAnimation(getActivity(), R.anim.bounce);

        MyBounceInterpolator interpolator = new MyBounceInterpolator(0.2, 20);
        bounce.setInterpolator(interpolator);

        instructionsButton.setOnClickListener(v ->
                findNavController(v).navigate(R.id.action_subMenu_to_instructionFragment));

        mainMenuButton.setOnClickListener(v ->
                findNavController(v).popBackStack());
    }

    @Override
    public void onItemSelected(AdapterView parent, View view, int position, long id) {
        Toast message = new Toast(getActivity());
        message.setText("Please Complete Previous Level(s)");

        if (position == 0){
            traditionalMode = true;
        }
        if (position == 1) {
            traditionalMode = false;
        }

    }

    @Override
    public void onNothingSelected(AdapterView parent) {
        L1Button.setOnClickListener(null);
        L2Button.setOnClickListener(null);
        L3Button.setOnClickListener(null);
        L4Button.setOnClickListener(null);
        L5Button.setOnClickListener(null);
        L6Button.setOnClickListener(null);
        L7Button.setOnClickListener(null);
        L8Button.setOnClickListener(null);

    }


}