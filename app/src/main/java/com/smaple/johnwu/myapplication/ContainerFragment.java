package com.smaple.johnwu.myapplication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 * Created by JohnWu on 4/7/17.
 */

public abstract class ContainerFragment extends BaseFragment {

    private BaseFragment mFragment;
    protected abstract Page getSelfPage();
    protected abstract int getContainerLayout();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null){
            mFragment = getSelfPage().getFragment();
            FragmentManager fragmentManager = getChildFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.add(getContainerLayout(), mFragment, getSelfPage().getTag())
                    .addToBackStack(getSelfPage().getTag())
                    .commit();
        } else {
            mFragment = (BaseFragment) getChildFragmentManager().findFragmentByTag(getSelfPage().getTag());
            getChildFragmentManager().executePendingTransactions();
        }
    }
}

