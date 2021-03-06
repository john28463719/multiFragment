package com.smaple.johnwu.myapplication;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity {

    private BottomNavigationView navigationView;
    private Fragment[] fragments = new Fragment[4];
    private int currentPosition = Page.FIRST.getIndex();
//    private int[] flowStack = new int[4];
    private List<Integer> flowStacks = new ArrayList<>();


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            addToStacks(new Integer(currentPosition));
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    showFragment(Page.FIRST);
                    return true;
                case R.id.navigation_dashboard:
                    showFragment(Page.SECOND);
                    return true;
                case R.id.navigation_notifications:
                    showFragment(Page.THIRD);
                    return true;
                case R.id.navigation_notifications4:
                    showFragment(Page.FOURTH);
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
            showRootFragment(Page.FIRST);
        }else {
            findFragments();
        }
    }

    private void initialFragment(){
        for (Page page: Page.values()) {
            fragments[page.getIndex()] = BlankFragment.newInstance(page.getIndex());
        }
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

//    private void addToStack(int index, int length){
//        int keep;
//        if (flowStack[length] == index){
//            return;
//        }
//        keep = flowStack[length];
//        flowStack[length] = index;
//        for (int i = length - 1; i >= 0; i--) {
//            int temp = keep;
//            keep = flowStack[i];
//            flowStack[i] = temp;
//            if (keep == index){
//                return;
//            }
//        }
//    }

    private void showRootFragment(Page rootPage){
        final FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        for (int i = 0; i < fragments.length; i++) {
            fragmentTransaction.add(R.id.content, fragments[i], Page.findByIndex(i).getContainerTag());
            fragmentTransaction.addToBackStack(null);
            if (i != rootPage.getIndex()){
                fragmentTransaction.hide(fragments[i]);
            }
        }
        fragmentTransaction.commit();
    }

    //when screen rotation will call
    private void findFragments(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        for (Page page: Page.values()) {
            fragments[page.getIndex()] = fragmentManager.findFragmentByTag(page.getContainerTag());
        }
    }

    private void showFragment(Page page){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.hide(fragments[currentPosition]);
        transaction.show(fragments[page.getIndex()]);
        transaction.commit();
        currentPosition = page.getIndex();
    }


    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = getActiveFragment(null, fragmentManager);
        if (fragment != null) {
            if (fragment.getFragmentManager().getBackStackEntryCount() > 1) {
                fragment.getFragmentManager().popBackStackImmediate();
            } else if (fragmentManager.getBackStackEntryCount() > 0) {
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
