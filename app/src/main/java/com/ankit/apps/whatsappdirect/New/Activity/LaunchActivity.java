package com.ankit.apps.whatsappdirect.New.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ankit.apps.whatsappdirect.New.R;
import com.ankit.apps.whatsappdirect.New.Utils.PrefManager;

public class LaunchActivity extends AppCompatActivity implements View.OnClickListener {

    private ViewPager viewPager;
    private Button btnSkip, btnNext;
    private LinearLayout dotsLayout;
    private PrefManager prefManager;
    private TextView[] dots_text;
    private int[] slide_img;
    private MyViewPagerAdapter myViewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        System.gc();
        getSupportActionBar().hide();
        // Checking for first time launch - before calling setContentView()
        prefManager = new PrefManager(this);
        if (!prefManager.isFirstTimeLaunch()) {
            launchHomeScreen();
            finish();
        }
        // Making notification bar transparent
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        setContentView(R.layout.activity_launch);
        initViews();

        slide_img = new int[]{R.drawable.snap1};

        addBottomDots(0);
        // changeStatusBarColor();

        myViewPagerAdapter = new MyViewPagerAdapter();
        viewPager.setAdapter(myViewPagerAdapter);
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);

        btnNext.setOnClickListener(this);
        btnSkip.setOnClickListener(this);
    }

    /*private void changeStatusBarColor() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }*/

    private void addBottomDots(int currentPage) {

        dots_text = new TextView[slide_img.length];

        int[] colorsActive = getResources().getIntArray(R.array.array_dot_active);
        int[] colorsInactive = getResources().getIntArray(R.array.array_dot_inactive);

        dotsLayout.removeAllViews();

        for (int i = 0; i < dots_text.length; i++) {
            dots_text[i] = new TextView(this);
            dots_text[i].setText(Html.fromHtml("&#8226;"));
            dots_text[i].setTextSize(35);
            dots_text[i].setTextColor(colorsInactive[currentPage]);
            dotsLayout.addView(dots_text[i]);
        }

        if (dots_text.length > 0)
            dots_text[currentPage].setTextColor(colorsActive[currentPage]);
    }

    private void launchHomeScreen() {
        prefManager.setFirstTimeLaunch(false);
        startActivity(new Intent(LaunchActivity.this, MainActivity.class));
        finish();
    }

    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {
            addBottomDots(position);

            if (position == slide_img.length - 1) {
                // last page. make button text to GOT IT
                btnNext.setText(getString(R.string.start));
                btnSkip.setVisibility(View.GONE);
            } else {
                // still pages are left
                btnNext.setText(getString(R.string.next));
                btnSkip.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
        }
    };

    private void initViews() {

        viewPager = (ViewPager) findViewById(R.id.view_pager);
        dotsLayout = (LinearLayout) findViewById(R.id.layoutDots);
        btnSkip = (Button) findViewById(R.id.btn_skip);
        btnNext = (Button) findViewById(R.id.btn_next);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.btn_skip:
                launchHomeScreen();
                break;

            case R.id.btn_next:

                int current = getItem(+1);
                if (current < slide_img.length) {
                    // move to next screen
                    viewPager.setCurrentItem(current);
                } else {
                    launchHomeScreen();
                }
                break;
        }
    }

    private int getItem(int i) {

        return viewPager.getCurrentItem() + i;
    }

    private class MyViewPagerAdapter extends PagerAdapter {

        private LayoutInflater layoutInflater;

        public MyViewPagerAdapter() {
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {

            layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View view = layoutInflater.inflate(R.layout.slider_image_layout, container, false);
            //View view = layoutInflater.inflate(slide_img[position], container, false);

            ImageView imageView = (ImageView) view.findViewById(R.id.img_slider);
            imageView.setImageResource(slide_img[position]);

            container.addView(view);

            return view;
        }

        @Override
        public int getCount() {
            return slide_img.length;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {

            return view == object;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

            View view = (View) object;
            container.removeView(view);
        }
    }
}
