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
import android.widget.Button;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SubMenu#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SubMenu extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    Button TraditionalButton;
    Button instructionButton;
    Button timeTrialButton;
    NavController navController;

    public SubMenu() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SubMenu_Single.
     */
    // TODO: Rename and change types and number of parameters
    public static SubMenu newInstance(String param1, String param2) {
        SubMenu fragment = new SubMenu();
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
        return inflater.inflate(R.layout.fragment_sub_menu, container, false);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);

        // Find buttons on screen and assign them to variables
        TraditionalButton = view.findViewById(R.id.Traditional_button);
        instructionButton = view.findViewById(R.id.Instruction_button);
        timeTrialButton = view.findViewById(R.id.TimeTrialMode_Button);

        // Gives buttons bounce animation "bubble button"
        final Animation myAnim = AnimationUtils.loadAnimation(getActivity(), R.anim.bounce);

        MyBounceInterpolator interpolator = new MyBounceInterpolator(0.2, 20);
        myAnim.setInterpolator(interpolator);

        TraditionalButton.startAnimation(myAnim);
        timeTrialButton.startAnimation(myAnim);

        TraditionalButton.setOnClickListener(v ->
                findNavController(v).navigate(R.id.action_subMenu_Single_to_gamePlayFragment));

        instructionButton.setOnClickListener((v ->
                findNavController(v).navigate(R.id.action_subMenu_Single_to_instructionFragment)));

    }
}