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
import java.util.List;

public class MainActivity extends FragmentActivity {

    private BottomNavigationView navigationView;
    private static final int FIRST = 0;
    private static final int SECOND = 1;
    private static final int THIRD = 2;
    private Fragment[] fragments = new Fragment[3];
    private int prePosition = 0;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    showFragment(FIRST);
//                    showHideFragment(fragments[FIRST], fragments[prePosition]);
                    prePosition = FIRST;
                    return true;
                case R.id.navigation_dashboard:
                    showFragment(SECOND);
//                    showHideFragment(fragments[SECOND], fragments[prePosition]);
                    prePosition = SECOND;
                    return true;
                case R.id.navigation_notifications:
                    showFragment(THIRD);
//                    showHideFragment(fragments[THIRD], fragments[prePosition]);
                    prePosition = THIRD;
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
        initialFragment();
        showRootFragment(FIRST);
    }

    private void initialFragment(){
        fragments[FIRST] = new BlankFragment();
        fragments[SECOND] = new Blank2Fragment();
        fragments[THIRD] = new Blank3Fragment();
    }

    private void showRootFragment(int root){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        for (int i = 0; i < fragments.length; i++) {
            fragmentTransaction.add(R.id.content, fragments[i], String.valueOf(i));
            if (i != root){
                fragmentTransaction.hide(fragments[i]);
            }
        }
        fragmentTransaction.commit();
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
        BlankFragment fragment = (BlankFragment) fragments[0];
        FragmentManager manager = fragment.getChildFragmentManager();
        if (manager.getBackStackEntryCount() > 1){
            manager.popBackStack();
        } else {
            super.onBackPressed();
        }


    }


}
