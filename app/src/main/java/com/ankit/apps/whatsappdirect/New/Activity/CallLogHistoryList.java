package com.ankit.apps.whatsappdirect.New.Activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ankit.apps.whatsappdirect.New.MessageFragment;
import com.ankit.apps.whatsappdirect.New.PhoneCallsfragment;
import com.ankit.apps.whatsappdirect.New.R;
import com.ankit.apps.whatsappdirect.New.Utils.AdapterCallback;
import com.ankit.apps.whatsappdirect.New.Utils.CallLogItem;

import java.util.ArrayList;
import java.util.List;

public class CallLogHistoryList extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private int[] tabIcons = {R.drawable.callhistory, R.drawable.ic_message};

    private ImageView iv_back;
    private TextView tv_title;
    ProgressDialog progressDialog;
    private Context mcontext;

    private RecyclerView recycleview_call_log;
    ArrayList<CallLogItem> mcallItems = new ArrayList<>();

    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;
    public static final int REQUEST_READ_CONTACTS = 79;
    Cursor curLog;
    int callType;
    private AdapterCallback callback;
    private int REQUEST_FOR_ACTIVITY_CODE = 55;
    String phone;
    private static final int PERMISSION_REQUEST_CODE = 200;
    private ViewPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_log_history_list);

        getSupportActionBar().hide();
        init();
        askPermission();
        //tabLayout = (TabLayout) findViewById(R.id.tabs);

      /*  tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);*/
        // setupTabIcons();
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setOnPageChangeListener(this);

        iv_back.setVisibility(View.VISIBLE);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /* private void setupTabIcons() {

         tabLayout.getTabAt(0).setIcon(tabIcons[0]);
         tabLayout.getTabAt(1).setIcon(tabIcons[1]);
     }
 */
    private void askPermission() {
        if (!checkPermission()) {
            requestPermission();
        } else {
            setupViewPager(viewPager);
        }
    }

    private void setupViewPager(ViewPager viewPager) {

        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new PhoneCallsfragment(), "Call");
        adapter.addFragment(new MessageFragment(), "Message");
        viewPager.setAdapter(adapter);

     /*   TextView tabOne = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        //tabOne.setText("ONE");
        tabOne.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.callhistory, 0, 0);
        tabLayout.getTabAt(0).setCustomView(tabOne);

        TextView tabTwo = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        //tabTwo.setText("TWO");
        tabTwo.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_message, 0, 0);
        tabLayout.getTabAt(1).setCustomView(tabTwo);*/
       /* tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.setupWithViewPager(viewPager);*/
    }

    private void init() {
        mcontext = CallLogHistoryList.this;
        iv_back = findViewById(R.id.iv_back);
        tv_title = findViewById(R.id.tv_tooltitle);
        tv_title.setText(R.string.call_list);
        viewPager = (ViewPager) findViewById(R.id.viewpager_calls);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        iv_back.setVisibility(View.VISIBLE);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        try {
           /* FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.detach().attach(this).commit();*/
            adapter.notifyDataSetChanged();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private class ViewPagerAdapter extends FragmentPagerAdapter {

        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }

        @Override
        public int getItemPosition(@NonNull Object object) {
            //return super.getItemPosition(object);
            return POSITION_NONE;
        }
    }

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_CALL_LOG);
        int result1 = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_SMS);

        return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CALL_LOG, Manifest.permission.READ_SMS}, PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0) {

                    boolean locationAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean cameraAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                    if (locationAccepted && cameraAccepted) {
                        setupViewPager(viewPager);
                        // Snackbar.make(view, "Permission Granted, Now you can access location data and camera.", Snackbar.LENGTH_LONG).show();
                    } else {

                        //Snackbar.make(view, "Permission Denied, You cannot access location data and camera.", Snackbar.LENGTH_LONG).show();

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (shouldShowRequestPermissionRationale(Manifest.permission.READ_CALL_LOG)) {
                                showMessageOKCancel("You need to allow access to both the permissions",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                    requestPermissions(new String[]{Manifest.permission.READ_CALL_LOG, Manifest.permission.READ_SMS},
                                                            PERMISSION_REQUEST_CODE);
                                                }
                                            }
                                        });
                                return;
                            }
                        }
                    }
                }
                break;
        }
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(CallLogHistoryList.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }
}
