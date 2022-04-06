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
import android.widget.Toast;

public class SubMenu extends Fragment implements AdapterView.OnItemSelectedListener{

    ImageView traditionalButton;
    ImageView timeTrialButton;
    TextView traditionalText;
    TextView timeTrialText;
    Button instructionsButton;
    Button mainMenuButton;
    NavController navController;
    static Boolean traditionalMode = true;

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
        timeTrialButton = view.findViewById(R.id.back_to_main_menu_button);
        timeTrialText = view.findViewById(R.id.time_trial_text);
        instructionsButton = view.findViewById(R.id.instructions_button);
        mainMenuButton = view.findViewById(R.id.submenu_back_button);

        // Spinner element (Drop-down menu)
        Spinner spin = view.findViewById(R.id.trad_level_spinner);

        String[] levels = { "Level One", "Level Two", "Level Three", "Level Four", "Level Five",
                            "Level Six", "Level Seven", "Level Eight"};

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

        instructionsButton.setOnClickListener(v ->
                findNavController(v).navigate(R.id.action_subMenu_to_instructionFragment));

        mainMenuButton.setOnClickListener(v ->
                findNavController(v));
    }

    @Override
    public void onItemSelected(AdapterView parent, View view, int position, long id) {
        Toast message = new Toast(getActivity());
        message.setText("Please Complete Previous Level(s)");

        if (position == 0) {
            timeTrialButton.setOnClickListener(view1 -> {
                findNavController(view1).navigate(R.id.action_subMenu_to_LevelOne);
                traditionalMode = false;
            });
            traditionalButton.setOnClickListener(view1 -> {
                findNavController(view1).navigate(R.id.action_subMenu_to_LevelOne);
                traditionalMode = true;
            });
        }

        if (position == 1) {
            traditionalButton.setOnClickListener(v -> message.show());
            timeTrialButton.setOnClickListener(v -> message.show());
            if (LevelOne.completed) {
                timeTrialButton.setOnClickListener(view1 -> {
                    findNavController(view1).navigate(R.id.action_subMenu_Single_to_LevelTwo);
                    traditionalMode = false;
                });
                traditionalButton.setOnClickListener(view1 -> {
                    findNavController(view1).navigate(R.id.action_subMenu_Single_to_LevelTwo);
                    traditionalMode = true;
                });
            }
        }

        if (position == 2) {
            traditionalButton.setOnClickListener(v -> message.show());
            timeTrialButton.setOnClickListener(v -> message.show());
            if (LevelTwo.completed) {
                timeTrialButton.setOnClickListener(view1 -> {
                    findNavController(view1).navigate(R.id.action_subMenu_Single_to_levelThree);
                    traditionalMode = false;
                });
                traditionalButton.setOnClickListener(view1 -> {
                    findNavController(view1).navigate(R.id.action_subMenu_Single_to_levelThree);
                    traditionalMode = true;
                });
            }
        }

        if (position == 3) {
            traditionalButton.setOnClickListener(v -> message.show());
            timeTrialButton.setOnClickListener(v -> message.show());
            if (LevelThree.completed) {
                timeTrialButton.setOnClickListener(view1 -> {
                    findNavController(view1).navigate(R.id.action_subMenu_Single_to_LevelFour);
                    traditionalMode = false;
                });
                traditionalButton.setOnClickListener(view1 -> {
                    findNavController(view1).navigate(R.id.action_subMenu_Single_to_LevelFour);
                    traditionalMode = false;
                });
            }
        }

        if (position == 4) {
            traditionalButton.setOnClickListener(v -> message.show());
            timeTrialButton.setOnClickListener(v -> message.show());
            if (LevelFour.completed) {
                timeTrialButton.setOnClickListener(view1 -> {
                    findNavController(view1).navigate(R.id.action_subMenu_Single_to_levelFive);
                    traditionalMode = false;
                });
                traditionalButton.setOnClickListener(view1 -> {
                    findNavController(view1).navigate(R.id.action_subMenu_Single_to_levelFive);
                    traditionalMode = false;
                });
            }
        }

        if (position == 5) {
            traditionalButton.setOnClickListener(v -> message.show());
            timeTrialButton.setOnClickListener(v -> message.show());
            if (LevelFive.completed) {
                timeTrialButton.setOnClickListener(view1 -> {
                    findNavController(view1).navigate(R.id.action_subMenu_Single_to_levelSix);
                    traditionalMode = false;
                });

                traditionalButton.setOnClickListener(view1 -> {
                    findNavController(view1).navigate(R.id.action_subMenu_Single_to_levelSix);
                    traditionalMode = false;
                });
            }
        }

        if (position == 6) {
            traditionalButton.setOnClickListener(v -> message.show());
            timeTrialButton.setOnClickListener(v -> message.show());
            if (LevelSix.completed) {
                timeTrialButton.setOnClickListener(view1 -> {
                    findNavController(view1).navigate(R.id.action_subMenu_Single_to_levelSeven);
                    traditionalMode = false;
                });
                traditionalButton.setOnClickListener(view1 -> {
                    findNavController(view1).navigate(R.id.action_subMenu_Single_to_levelSeven);
                    traditionalMode = false;
                });
            }
        }

        if (position == 7) {
            traditionalButton.setOnClickListener(v -> message.show());
            timeTrialButton.setOnClickListener(v -> message.show());
            if (LevelSeven.completed) {
                timeTrialButton.setOnClickListener(view1 -> {
                    findNavController(view1).navigate(R.id.action_subMenu_Single_to_levelEight);
                    traditionalMode = false;
                });
                traditionalButton.setOnClickListener(view1 -> {
                    findNavController(view1).navigate(R.id.action_subMenu_Single_to_levelEight);
                    traditionalMode = false;
                });
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView parent) {
        traditionalButton.setOnClickListener(null);
        timeTrialButton.setOnClickListener(null);
    }


}