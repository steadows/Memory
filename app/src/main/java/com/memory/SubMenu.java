package com.memory;

import static androidx.navigation.Navigation.findNavController;

import android.os.Bundle;

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

public class SubMenu extends Fragment  {

    Button traditionalButton;
    Button instructionButton;
    Button timeTrialButton;
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
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);

        // Find buttons on screen and assign them to variables
        traditionalButton = view.findViewById(R.id.Traditional_button);
        instructionButton = view.findViewById(R.id.Instruction_button);
        timeTrialButton = view.findViewById(R.id.TimeTrialMode_Button);

        // Spinner element (Drop-down menu)
        Spinner spin = view.findViewById(R.id.spinner);

        String[] levels = { "CHOOSE MODE", "EASY MODE", "HARD MODE" };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, levels);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(adapter);
        spin.setOnItemSelectedListener(new SpinnerListener());


        // Gives buttons bounce animation "bubble button"
        final Animation myAnim = AnimationUtils.loadAnimation(getActivity(), R.anim.bounce);

        MyBounceInterpolator interpolator = new MyBounceInterpolator(0.2, 20);
        myAnim.setInterpolator(interpolator);

        traditionalButton.startAnimation(myAnim);
        timeTrialButton.startAnimation(myAnim);

        traditionalButton.setOnClickListener(v ->
                findNavController(v).navigate(R.id.action_subMenu_Single_to_gamePlayFragment));

        instructionButton.setOnClickListener((v ->
                findNavController(v).navigate(R.id.action_subMenu_Single_to_instructionFragment)));

    }

}

class SpinnerListener implements AdapterView.OnItemSelectedListener {

    @Override
    public void onItemSelected(AdapterView parent, View v, int position, long id) {

        if (position == 1)
            findNavController(v).navigate(R.id.action_subMenu_Single_to_gamePlayFragment);

        if (position == 2)
            findNavController(v).navigate(R.id.action_subMenu_Single_to_gamePlayFragment);

        Toast.makeText(parent.getContext(),
                parent.getItemAtPosition(position).toString(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView parent) {
        // Do nothing.
    }

}