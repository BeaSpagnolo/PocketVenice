package com.progetto_ingegneria.pocketvenice.Guide;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.google.android.material.tabs.TabLayout;
import com.progetto_ingegneria.pocketvenice.BottomNavbarActivities.News.NewsActivity;
import com.progetto_ingegneria.pocketvenice.R;

import java.util.ArrayList;
import java.util.List;

public class GuideActivity extends AppCompatActivity {

    private ViewPager screenPager;
    private GuideViewPagerAdapter guideViewPagerAdapter;
    private TabLayout tabIndicator;
    private Button btnNext, btnGetStarted;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //make the activity on full screen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        /*check if guide is opened before or not
        if(restorePrefData()){
            Intent news = new Intent(IntroActivity.this, NewsActivity.class);
            startActivity(news);
            finish();
        }*/


        setContentView(R.layout.activity_guide);



        //init
        btnNext = findViewById(R.id.next_button);
        btnGetStarted = findViewById(R.id.get_started_button);
        tabIndicator = findViewById(R.id.tab_indicator);

        //List screen
        List<ScreenItem> screenItemList = new ArrayList<>();
        //da rivedere per descrizioni & img
        screenItemList.add(new ScreenItem("News","Descrizione di News", R.drawable.ic_baseline_newspaper_24));
        screenItemList.add(new ScreenItem("Events","Descrizione di Events", R.drawable.ic_baseline_event_24));
        screenItemList.add(new ScreenItem("Places","Descrizione di Places", R.drawable.ic_baseline_place_24));
        screenItemList.add(new ScreenItem("Maps","Descrizione di Mews", R.drawable.ic_baseline_map_24));

        //setup view page adapter
        screenPager = findViewById(R.id.screen_pager);
        guideViewPagerAdapter = new GuideViewPagerAdapter(this, screenItemList);
        screenPager.setAdapter(guideViewPagerAdapter);

        //setup tablayaout
        tabIndicator.setupWithViewPager(screenPager);

        //next button click Listener
        btnNext.setOnClickListener(v -> {
            position = screenPager.getCurrentItem();
            if(position < screenItemList.size()){
                position++;
                screenPager.setCurrentItem(position);
            }
            if(position == screenItemList.size()-1){
                loadLastScreen();
            }
        });
        //tabindicated change listener
        tabIndicator.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(position == screenItemList.size()-1){
                    loadLastScreen();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        //get started button click listener
        btnGetStarted.setOnClickListener(v->{
            Intent news = new Intent(GuideActivity.this, NewsActivity.class);
            startActivity(news);
            //savePefesData();
            finish();
        });

    }

    private boolean restorePrefData() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("Guide", MODE_PRIVATE);
        return pref.getBoolean("isIntroOpened", false);

    }

    private void savePefesData() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("Guide", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("isIntroOpened", true);
        editor.apply();
    }

    private void loadLastScreen() {
        btnNext.setVisibility(View.INVISIBLE);
        btnGetStarted.setVisibility(View.VISIBLE);
        tabIndicator.setVisibility(View.INVISIBLE);
    }
}