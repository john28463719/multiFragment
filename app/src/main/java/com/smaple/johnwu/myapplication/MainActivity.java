package com.smaple.johnwu.myapplication;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity {

    private BottomNavigationView navigationView;
    private static final int FIRST = 0;
    private static final int SECOND = 1;
    private static final int THIRD = 2;
    private static final int FOURTH = 3;
    private Fragment[] fragments = new Fragment[4];
    private int currentPosition = FIRST;
    private int[] flowStack = new int[4];
    private List<Integer> flowStacks = new ArrayList<>();


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            addToStacks(new Integer(currentPosition));

            switch (item.getItemId()) {
                case R.id.navigation_home:
                    showFragment(FIRST);
                    currentPosition = FIRST;
//                    addToStack(1, flowStack.length - 1);
                    return true;
                case R.id.navigation_dashboard:
                    showFragment(SECOND);
                    currentPosition = SECOND;
//                    addToStack(2, flowStack.length - 1);
                    return true;
                case R.id.navigation_notifications:
                    showFragment(THIRD);
                    currentPosition = THIRD;
//                    addToStack(3, flowStack.length - 1);
                    return true;
                case R.id.navigation_notifications4:
                    showFragment(FOURTH);
                    currentPosition = FOURTH;
//                    addToStack(4, flowStack.length - 1);
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navigationView = (BottomNavigationView) findViewById(R.id.navigation);
        navigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        if (savedInstanceState == null){
            initialFragment();
            showRootFragment(FIRST);
        }else {
            findFragments();
        }
    }

    private void initialFragment(){
        fragments[FIRST] = new BlankFragment();
        fragments[SECOND] = new Blank2Fragment();
        fragments[THIRD] = new Blank3Fragment();
        fragments[FOURTH] = new Blank4Fragment();
    }

    private void showRootFragment(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.content, fragments[FIRST], String.valueOf(FIRST));
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void showFragment(int position, String FragTag){
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment toFragment = fragments[position];
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        List<Fragment> list = fragmentManager.getFragments();
        fragmentTransaction.add(R.id.content, toFragment, FragTag);
        fragmentTransaction.addToBackStack(null);
        if (list != null){
            for (Fragment fragment: list) {
                fragmentTransaction.hide(fragment);
            }
        }
        fragmentTransaction.commit();
    }

    private void addToStacks(Integer position){
        if (flowStacks.contains(position)) {
            int index = flowStacks.indexOf(position);
            flowStacks.remove(index);
            flowStacks.add(position);
        } else {
            flowStacks.add(position);
        }
    }

    private void addToStack(int index, int length){
        int keep;
        if (flowStack[length] == index){
            return;
        }
        keep = flowStack[length];
        flowStack[length] = index;
        for (int i = length - 1; i >= 0; i--) {
            int temp = keep;
            keep = flowStack[i];
            flowStack[i] = temp;
            if (keep == index){
                return;
            }
        }
    }

    private void showRootFragment(int root){
        final FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        for (int i = 0; i < fragments.length; i++) {
            fragmentTransaction.add(R.id.content, fragments[i], String.valueOf(i));
            fragmentTransaction.addToBackStack(null);
            if (i != root){
                fragmentTransaction.hide(fragments[i]);
            }
        }
        fragmentTransaction.commit();
        fragmentManager.executePendingTransactions();
    }

    private void findFragments(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragments[FIRST] = fragmentManager.findFragmentByTag(String.valueOf(FIRST));
        fragments[SECOND] = fragmentManager.findFragmentByTag(String.valueOf(SECOND));
        fragments[THIRD] = fragmentManager.findFragmentByTag(String.valueOf(THIRD));
        fragments[FOURTH] = fragmentManager.findFragmentByTag(String.valueOf(FOURTH));
    }

    private void showFragment(int position){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment show = fragments[position];
        List<Fragment> fragmentList = fragmentManager.getFragments();
        if (fragmentList != null) {
            for (Fragment fragment : fragmentList) {
                if (fragment == show) {
                    fragmentTransaction.show(fragment);
                } else {
                    fragmentTransaction.hide(fragment);
                }
            }
        }
        fragmentTransaction.commit();
    }


    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = getActiveFragment(null, fragmentManager);
        if (fragment != null) {
            int count = fragment.getFragmentManager().getBackStackEntryCount();
            if (fragment.getFragmentManager().getBackStackEntryCount() > 1) {
                fragment.getFragmentManager().popBackStackImmediate();
            } else if (fragmentManager.getBackStackEntryCount() > 0) {
//                fragmentManager.popBackStack();

                if (flowStacks.size() > 0) {
                    getSupportFragmentManager().beginTransaction().hide(fragments[currentPosition]).commit();

                    Integer popFragmentIndex = flowStacks.get(flowStacks.size() - 1);
                    getSupportFragmentManager().beginTransaction().show(fragments[popFragmentIndex]).commit();
                    flowStacks.remove(flowStacks.size() - 1);

                    currentPosition = popFragmentIndex;
                    navigationView.getMenu().getItem(popFragmentIndex).setChecked(true);
                } else {
                    this.finish();
                }

                /*
                for (int i = 0; i < fragments.length; i++) {
                    Integer fragmentPosition = flowStacks.get(i);
                    if (i != (flowStacks.size() - 1)) {
                        getSupportFragmentManager().beginTransaction().hide(fragments[fragmentPosition]).commit();
                    }else {
                        getSupportFragmentManager().beginTransaction().show(fragments[fragmentPosition]).commit();
                    }
                }
                */
            } else {
                this.finish();
            }
        }
    }


    private Fragment getActiveFragment(Fragment parentFragment, FragmentManager fragmentManager) {
        List<Fragment> fragmentList = fragmentManager.getFragments();
        if (fragmentList == null) {
            return parentFragment;
        }
        for (int i = fragmentList.size() - 1; i >= 0; i--) {
            Fragment fragment = fragmentList.get(i);
            if (fragment instanceof Fragment) {
                Fragment supportFragment = fragment;
                if (supportFragment.isResumed() && !supportFragment.isHidden() && supportFragment.getUserVisibleHint()) {
                    return getActiveFragment(supportFragment, supportFragment.getChildFragmentManager());
                }
            }
        }
        return parentFragment;
    }
}
