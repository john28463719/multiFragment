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
public class Blank2Fragment extends BaseFragment {

    private ChildFragment mChildFragment;

    public Blank2Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null){
            FragmentManager manager = getChildFragmentManager();
            mChildFragment = ChildFragment.newInstance("I am 2");
            FragmentTransaction fragmentTransaction = manager.beginTransaction();
            fragmentTransaction.add(R.id.container, mChildFragment, "child2");
            fragmentTransaction.addToBackStack("child3");
            fragmentTransaction.commit();
        } else {
            mChildFragment = (ChildFragment) getChildFragmentManager().findFragmentByTag("child1");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_blank2, container, false);
    }

}
