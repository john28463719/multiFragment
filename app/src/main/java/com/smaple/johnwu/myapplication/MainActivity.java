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

    private enum Page{
        FIRST(0, "first"),
        SECOND(1, "second"),
        THIRD(2, "third"),
        FOURTH(3, "fourth");

        private int index;
        private String tag;

        private Page(int index, String tag){
            this.index = index;
            this.tag = tag;
        }

        public String getTag() {
            return tag;
        }

        public int getIndex() {
            return index;
        }
    }

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
                    currentPosition = Page.FIRST.getIndex();
                    return true;
                case R.id.navigation_dashboard:
                    showFragment(Page.SECOND);
                    currentPosition = Page.SECOND.getIndex();
                    return true;
                case R.id.navigation_notifications:
                    showFragment(Page.THIRD);
                    currentPosition = Page.THIRD.getIndex();
                    return true;
                case R.id.navigation_notifications4:
                    showFragment(Page.FOURTH);
                    currentPosition = Page.FOURTH.getIndex();
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
        fragments[Page.FIRST.getIndex()] = new BlankFragment();
        fragments[Page.SECOND.getIndex()] = new Blank2Fragment();
        fragments[Page.THIRD.getIndex()] = new Blank3Fragment();
        fragments[Page.FOURTH.getIndex()] = new Blank4Fragment();
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
            fragmentTransaction.add(R.id.content, fragments[i], String.valueOf(i));
            fragmentTransaction.addToBackStack(null);
            if (i != rootPage.getIndex()){
                fragmentTransaction.hide(fragments[i]);
            }
        }
        fragmentTransaction.commit();
    }

    private void findFragments(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragments[Page.FIRST.getIndex()] = fragmentManager.findFragmentByTag(Page.FIRST.getTag());
        fragments[Page.SECOND.getIndex()] = fragmentManager.findFragmentByTag(String.valueOf(Page.SECOND.getTag()));
        fragments[Page.THIRD.getIndex()] = fragmentManager.findFragmentByTag(String.valueOf(Page.THIRD.getTag()));
        fragments[Page.FOURTH.getIndex()] = fragmentManager.findFragmentByTag(String.valueOf(Page.FOURTH.getTag()));
    }

    private void showFragment(Page Page){
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().show(fragments[Page.getIndex()]).commit();
        if (Page.getIndex() != currentPosition){
            fragmentManager.beginTransaction().hide(fragments[currentPosition]).commit();
        }
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
