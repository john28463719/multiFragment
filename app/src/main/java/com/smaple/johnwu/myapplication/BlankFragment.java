package com.smaple.johnwu.myapplication;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;




/**
 * A simple {@link Fragment} subclass.
 */
public class BlankFragment extends Fragment {

    public FragmentManager manager;

    public BlankFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_blank, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FragmentManager testManager = getFragmentManager();
        manager = getChildFragmentManager();
        Fragment fragment = ChildFragment.newInstance("I am 1");
        FragmentTransaction fragmentTransaction = manager.beginTransaction();
        fragmentTransaction
                .add(R.id.container, fragment)
                .addToBackStack(null)
                .commit();
//        loadRootFragment(R.id.container, fragment);
    }
}
