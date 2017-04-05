package com.smaple.johnwu.myapplication;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;



/**
 * A simple {@link Fragment} subclass.
 */
public class ChildFragment extends Fragment {


    public static ChildFragment newInstance(String name) {

        Bundle args = new Bundle();
        args.putString("name", name);
        ChildFragment fragment = new ChildFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public ChildFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_child, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((TextView)getView().findViewById(R.id.text)).setText(getArguments().getString("name"));
        getView().findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               gotoDeep();
            }
        });


    }

    private void gotoDeep(){
        DeepFragment fragment = DeepFragment.newInstance(1);
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction
                .replace(R.id.container, fragment)
                .addToBackStack(null)
                .commit();
    }
}
