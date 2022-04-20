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
import android.widget.Spinner;
import android.widget.Toast;

public class SubMenu extends Fragment implements AdapterView.OnItemSelectedListener{

    Button lvl1Button;
    Button lvl2Button;
    Button lvl3Button;
    Button lvl4Button;
    Button lvl5Button;
    Button lvl6Button;
    Button lvl7Button;
    Button lvl8Button;

    Button instructionsButton;
    Button mainMenuButton;
    Spinner modeSpinner;
    NavController navController;

    static boolean traditionalMode = true;

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
        lvl1Button = view.findViewById(R.id.level_1_button);
        lvl2Button = view.findViewById(R.id.level_2_button);
        lvl3Button = view.findViewById(R.id.level_3_button);
        lvl4Button = view.findViewById(R.id.level_4_button);
        lvl5Button = view.findViewById(R.id.level_5_button);
        lvl6Button = view.findViewById(R.id.level_6_button);
        lvl7Button = view.findViewById(R.id.level_7_button);
        lvl8Button = view.findViewById(R.id.level_8_button);
        instructionsButton = view.findViewById(R.id.instructions_button);
        mainMenuButton = view.findViewById(R.id.submenu_back_button);

        Toast message = new Toast(getActivity());
        message.setText("Please Complete Previous Level(s)");

        // Spinner element (Drop-down menu)
        modeSpinner = view.findViewById(R.id.game_mode_spinner);

        lvl1Button.setOnClickListener(v ->
                findNavController(v).navigate(R.id.action_submenu_to_level_1));

        if (!LevelOne.completed){
            lvl2Button.setOnClickListener(v -> message.show());
            lvl2Button.setBackgroundResource(R.color.light_blue_650);
            lvl2Button.setTextColor(0x66000000);
        }
        else{
            lvl2Button.setOnClickListener(v ->
                    findNavController(v).navigate(R.id.action_subMenu_to_level_2));
        }
        if (!LevelTwo.completed){
            lvl3Button.setOnClickListener(v -> message.show());
            lvl3Button.setBackgroundResource(R.color.light_blue_650);
            lvl3Button.setTextColor(0x66000000);
        }
        else{
            lvl3Button.setOnClickListener(v ->
                    findNavController(v).navigate(R.id.action_submenu_to_level_3));
        }
        if (!LevelThree.completed){
            lvl4Button.setOnClickListener(v -> message.show());
            lvl4Button.setBackgroundResource(R.color.light_blue_650);
            lvl4Button.setTextColor(0x66000000);
        }
        else {
            lvl4Button.setOnClickListener(v ->
                    findNavController(v).navigate(R.id.action_submenu_to_level_4));
        }
        if (!LevelFour.completed){
            lvl5Button.setOnClickListener(v -> message.show());
            lvl5Button.setBackgroundResource(R.color.light_blue_650);
            lvl5Button.setTextColor(0x66000000);
        }
        else {
            lvl5Button.setOnClickListener(v ->
                    findNavController(v).navigate(R.id.action_submenu_to_level_5));
        }
        if (!LevelFive.completed){
            lvl6Button.setOnClickListener(v -> message.show());
            lvl6Button.setBackgroundResource(R.color.light_blue_650);
            lvl6Button.setTextColor(0x66000000);
        }
        else {
            lvl6Button.setOnClickListener(v ->
                    findNavController(v).navigate(R.id.action_submenu_to_level_6));
        }
        if (!LevelSix.completed){
            lvl7Button.setOnClickListener(v -> message.show());
            lvl7Button.setBackgroundResource(R.color.light_blue_650);
            lvl7Button.setTextColor(0x66000000);
        }
        else {
            lvl7Button.setOnClickListener(v ->
                    findNavController(v).navigate(R.id.action_submenu_to_level_7));
        }
        if (!LevelSeven.completed){
            lvl8Button.setOnClickListener(v -> message.show());
            lvl8Button.setBackgroundResource(R.color.light_blue_650);
            lvl8Button.setTextColor(0x66000000);
        }
        else {
            lvl8Button.setOnClickListener(v ->
                    findNavController(v).navigate(R.id.action_submenu_to_level_8));
        }

        String[] modes = { "Traditional", "Time Trial" };
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_item, modes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        modeSpinner.setAdapter(adapter);
        modeSpinner.setOnItemSelectedListener(this);


        // Gives buttons bounce animation "bubble button"
        final Animation bounce = AnimationUtils.loadAnimation(getActivity(), R.anim.bounce);

        MyBounceInterpolator interpolator = new MyBounceInterpolator(0.2, 20);
        bounce.setInterpolator(interpolator);

        instructionsButton.startAnimation(bounce);
        mainMenuButton.startAnimation(bounce);

        instructionsButton.setOnClickListener(v ->
                findNavController(v).navigate(R.id.action_submenu_to_instructions));

        mainMenuButton.setOnClickListener(v ->
                findNavController(v).navigate((R.id.action_subMenu_to_mainMenuFragment)));
    }

    @Override
    public void onItemSelected(AdapterView parent, View view, int position, long id) {

        if (position == 0){
            traditionalMode = true;
        }
        if (position == 1) {
            traditionalMode = false;
        }

    }

    @Override
    public void onNothingSelected(AdapterView parent) {
        /* Nothing to do */
    }
}