package com.smaple.johnwu.myapplication;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;



/**
 * A simple {@link Fragment} subclass.
 */
public class DeepFragment extends Fragment {


    public static DeepFragment newInstance(int count) {

        Bundle args = new Bundle();

        DeepFragment fragment = new DeepFragment();
        args.putInt("count",count);
        fragment.setArguments(args);
        return fragment;
    }

    public DeepFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_deep, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((Button)getView().findViewById(R.id.button)).setText("Count " + String.valueOf(getArguments().getInt("count")));
        getView().findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int aa = getArguments().getInt("count");
                Fragment fragment = DeepFragment.newInstance(++aa);
                FragmentManager fragmentManager =  getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.container, fragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
    }
}
