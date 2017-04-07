package com.smaple.johnwu.myapplication;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;



/**
 * A simple {@link Fragment} subclass.
 */
public class DeepFragment extends BaseFragment {

    private DeepFragment mDeepFragment;

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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null){
            int aa = getArguments().getInt("count");
            mDeepFragment = DeepFragment.newInstance(++aa);
        } else {
            mDeepFragment = (DeepFragment) getFragmentManager().findFragmentByTag("deep"+String.valueOf(getArguments().getInt("count")));
        }

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
                FragmentManager fragmentManager =  getFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.add(R.id.container, mDeepFragment, "deep" + String.valueOf(getArguments().getInt("count")));
                transaction.hide(DeepFragment.this);
                transaction.addToBackStack("deep");
                transaction.commit();
            }
        });
    }
}
