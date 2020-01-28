package com.ankit.apps.whatsappdirect.New.Activity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.provider.CallLog;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.hbb20.CountryCodePicker;
import com.ankit.apps.whatsappdirect.New.Utils.AdapterCallback;
import com.ankit.apps.whatsappdirect.New.Adapter.CallLogAdapter;
import com.ankit.apps.whatsappdirect.New.Utils.CallLogHelper;
import com.ankit.apps.whatsappdirect.New.Utils.CallLogItem;
import com.ankit.apps.whatsappdirect.New.R;

import io.fabric.sdk.android.Fabric;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import io.michaelrocks.libphonenumber.android.NumberParseException;
import io.michaelrocks.libphonenumber.android.PhoneNumberUtil;
import io.michaelrocks.libphonenumber.android.Phonenumber;

public class MainActivity extends AppCompatActivity implements AdapterCallback {

    CountryCodePicker countryCodePicker;
    EditText etPhone, edtTextMsg;
    TextView tvValidity, send_btn;
    ImageView imgValidity, iv_call;
    Button mClearText;
    CardView send_cardmsg;
    boolean valid = false;
    TextInputLayout textInputLayout;
    Context mcontext;
    LinearLayout linear_main;

    private RecyclerView recycleview_call_log;
    ArrayList<CallLogItem> mcallItems = new ArrayList<>();

    private RecyclerView.LayoutManager mLayoutManager;
    private CallLogAdapter mAdapter;
    //private RecyclerView.Adapter mAdapter;
    public static final int REQUEST_READ_CONTACTS = 79;
    Cursor curLog;
    int callType;
    String selectedNumber;
    private int REQUEST_FOR_ACTIVITY_CODE = 55;

    AdapterCallback callback;
    final String appPackageName = "com.YourCompanyName.YourAppName";

    ProgressDialog progressDialog;
    Dialog dialog;

    private final String TAG = MainActivity.class.getSimpleName();

    private AdView mAdView;
    private InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_main);

        setContent();
        setAdmobAds();
        edtTextMsg.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
                //do nothing
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //do nothing
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (s.length() != 0) {
                    mClearText.setVisibility(View.VISIBLE);
                } else {
                    mClearText.setVisibility(View.GONE);
                }
            }
        });
    }

    private void setAdmobAds() {
        AdView mAdView = (AdView) findViewById(R.id.adView);

        mAdView.setVisibility(View.VISIBLE);
        loadBannerAdd(mAdView);
    }

    public static void loadBannerAdd(AdView mAdView) {
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
            }

            @Override
            public void onAdClosed() {
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
            }

            @Override
            public void onAdLeftApplication() {
            }

            @Override
            public void onAdOpened() {
                super.onAdOpened();
            }
        });
        mAdView.loadAd(adRequest);
    }

    public void setContent() {

        assignViews();
        registerCarrierEditText();
        setClickListener();

       /* etPhone.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (etPhone.getRight() - etPhone.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        // your action here
                       *//* progressDialog = new ProgressDialog(MainActivity.this);
                        progressDialog.setMessage("Loading..");
                        progressDialog.show();*//*
                        // openDialog();
                        Intent intent = new Intent(MainActivity.this, CallLogHistoryList.class);
                        startActivityForResult(intent, REQUEST_FOR_ACTIVITY_CODE);

                        return true;
                    }
                }
                return false;
            }
        });*/
    }

    private void setClickListener() {

        send_cardmsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (registerCarrierEditText() == true) {
                    getMessage();
                } else {
                    String phoneno = etPhone.getText().toString();
                    if (!phoneno.isEmpty()) {
                        getMessage();

                    } else {

                        AlertDialog.Builder builder1 = new AlertDialog.Builder(mcontext);
                        builder1.setMessage("Please Enter PhoneNumber.");
                        builder1.setCancelable(false);
                        builder1.setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });
                        AlertDialog alert11 = builder1.create();
                        alert11.show();
                        //  etPhone.setError("Enter phonenumber");
                    }
                    //etPhone.setError("Please Enter Valid PhoneNumber");
                    //etPhone.setErrorTextAppearance(R.style.TextInputLayout_TextError);
                    //tvValidity.setText("Invalid Number");
                }
            }
        });

        iv_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setInterstitialAd();
                /*Intent intent = new Intent(MainActivity.this, CallLogHistoryList.class);
                startActivityForResult(intent, REQUEST_FOR_ACTIVITY_CODE);*/
            }
        });
    }

    private void setInterstitialAd() {

        AdRequest adRequest = new AdRequest.Builder().addTestDevice("YOUR_TEST_DEVICE_ID").build();
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getResources().getString(R.string.Interstitial_Ad));
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                //Ads loaded
                mInterstitialAd.show();
            }

            @Override
            public void onAdClosed() {
                super.onAdClosed();
                //Ads closed
                Intent intent = new Intent(MainActivity.this, CallLogHistoryList.class);
                startActivityForResult(intent, REQUEST_FOR_ACTIVITY_CODE);
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                super.onAdFailedToLoad(errorCode);

                //Ads couldn't loaded
                // Toast.makeText(mcontext, "Please Check Network connection", Toast.LENGTH_LONG).show();
            }
        });
        mInterstitialAd.loadAd(adRequest);
    }

    public void getMessage() {

        String textMsg = edtTextMsg.getText().toString().trim();
        if (textMsg.isEmpty()) {

            AlertDialog.Builder builder1 = new AlertDialog.Builder(mcontext);
            builder1.setMessage("Please Enter Message.");
            builder1.setCancelable(false);
            builder1.setPositiveButton("OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alert11 = builder1.create();
            alert11.show();

            // edtTextMsg.setError("Please Enter Message");
            //  textInputLayout.setError("Please Enter Message");
            //textInputLayout.setErrorTextAppearance(R.style.TextInputLayout_TextError);

        } else {

            String url = "https://api.whatsapp.com/send";
            url = url + "?phone=" + countryCodePicker.getFullNumber() + "&text=" + textMsg;

            /*String url = "https://www.google.com";
            url = url + "?phoneNumber=" + countryCodePicker.getFullNumber() + "&Message=" + textMsg;*/
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
        }
    }

    private boolean registerCarrierEditText() {

        countryCodePicker.registerCarrierNumberEditText(etPhone);
        countryCodePicker.setPhoneNumberValidityChangeListener(new CountryCodePicker.PhoneNumberValidityChangeListener() {
            @Override
            public void onValidityChanged(boolean isValidNumber) {
                if (isValidNumber) {
                    imgValidity.setImageDrawable(getResources().getDrawable(R.drawable.ic_assignment_turned_in));
                    tvValidity.setText("Valid Number");
                    tvValidity.setTextColor(getResources().getColor(R.color.colorPrimary));
                    valid = true;
                } else {
                    imgValidity.setImageDrawable(getResources().getDrawable(R.drawable.ic_assignment_late));
                    tvValidity.setText("Invalid Number");
                    tvValidity.setTextColor(getResources().getColor(R.color.colorAccent));
                    valid = false;
                }
            }
        });
        return valid;
    }

    private void assignViews() {

        mcontext = MainActivity.this;
        etPhone = findViewById(R.id.et_phone);
        countryCodePicker = findViewById(R.id.ccp);
        //countryCodePicker.registerCarrierNumberEditText(etPhone);
        tvValidity = findViewById(R.id.tv_validity);
        imgValidity = findViewById(R.id.img_validity);
        edtTextMsg = findViewById(R.id.edt_textmsg);
        //  textInputLayout = findViewById(R.id.edtxt_input);

        linear_main = findViewById(R.id.linear_main);
        iv_call = findViewById(R.id.iv_call);

        mClearText = findViewById(R.id.clearText);

        send_cardmsg = findViewById(R.id.cardsendMessage);
        send_btn = findViewById(R.id.send_button);
    }

    public void openDialog() {

        mcallItems.clear();
        dialog = new Dialog(MainActivity.this);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.setContentView(R.layout.activity_call_history);
        dialog.setCanceledOnTouchOutside(false);
        // dialog.show();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CALL_LOG) == PackageManager.PERMISSION_GRANTED) {

            curLog = CallLogHelper.getAllCallLogs(getContentResolver());
            setCallLogs(curLog);
            //  dialog.show();
        } else {
            requestLocationPermission();
        }
        recycleview_call_log = dialog.findViewById(R.id.recycleview_call_log);
        recycleview_call_log.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        recycleview_call_log.setLayoutManager(mLayoutManager);
        mAdapter = new CallLogAdapter(MainActivity.this, mcallItems, this);
        recycleview_call_log.setAdapter(mAdapter);

        Button dialogButton = dialog.findViewById(R.id.dialog_cancel);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                progressDialog.dismiss();
            }
        });
    }

    protected void requestLocationPermission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_CALL_LOG)) {
            // show UI part if you want here to show some rationale !!!
            Log.e("permission", "==check=requestLocationPermission==2222");

        } else {
            Log.e("permission", "==check=requestLocationPermission==33333");
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CALL_LOG}, REQUEST_READ_CONTACTS);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {

            case REQUEST_READ_CONTACTS: {

                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    curLog = CallLogHelper.getAllCallLogs(getContentResolver());
                    setCallLogs(curLog);
                    // mAdapter = new CallLogAdapter(MainActivity.this, mcallItems,callback);
                    mAdapter = new CallLogAdapter(MainActivity.this, mcallItems, this);
                    recycleview_call_log.setAdapter(mAdapter);
                    progressDialog.dismiss();

                } else {

                    dialog.dismiss();
                    progressDialog.dismiss();
                    // permission denied,Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
        }
    }

    private void setCallLogs(Cursor curLog) {

        Log.e("CALLLOGLENTH", "" + curLog.getCount());

        if (curLog.getCount() == 0) {

            progressDialog.dismiss();
            dialog.dismiss();

            final AlertDialog.Builder builder;
            //builder = new AlertDialog.Builder(mcontext, android.R.style.Theme_Material_Dialog_Alert);

            builder = new AlertDialog.Builder(mcontext);
            builder.setTitle("Call History")
                    .setMessage("No any calls from your history!!")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // continue with delete
                            dialog.cancel();
                        }
                    })
                    .setIcon(R.drawable.ic_warning)
                    .setCancelable(false)
                    .show();
                    /*.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // do nothing
                        }
                    })*/
            return;
        }
        dialog.show();
        while (curLog.moveToNext()) {

            String callNumber = curLog.getString(curLog.getColumnIndex(android.provider.CallLog.Calls.NUMBER));

            String callName = curLog.getString(curLog.getColumnIndex(android.provider.CallLog.Calls.CACHED_NAME));
            /*if (callName == null) {
                conNames.add("Unknown");
            } else
                conNames.add(callName);*/

            String callDate = curLog.getString(curLog.getColumnIndex(android.provider.CallLog.Calls.DATE));

            SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy hh:mm a");
            String dateString = formatter.format(new Date(Long.parseLong(callDate)));

           /* dateString = dateString.replace("a.m.", "am").replace("p.m.","pm");
            Log.e("date","==hour=111="+dateString);*/

            String callTypeCode = curLog.getString(curLog.getColumnIndex(android.provider.CallLog.Calls.TYPE));

            int callcode = Integer.parseInt(callTypeCode);
            switch (callcode) {
                case CallLog.Calls.OUTGOING_TYPE:
                    callType = 1;
                    break;
                case CallLog.Calls.INCOMING_TYPE:
                    callType = 2;
                    break;
                case CallLog.Calls.MISSED_TYPE:
                    callType = 3;
                    break;
            }

            String duration = curLog.getString(curLog.getColumnIndex(android.provider.CallLog.Calls.DURATION));

            CallLogItem callLogItem = new CallLogItem();
            callLogItem.setPhoneNumber(callNumber);
            callLogItem.setCallDate(String.valueOf(dateString));
            callLogItem.setCallType(callType);
            callLogItem.setCallDuration(duration);
            mcallItems.add(callLogItem);

            progressDialog.dismiss();
        }
    }

    @Override
    public void onPositionClicked(String phoneNUmber) {
        //etPhone.setText(phoneNUmber);
        int countryCode = 0;
        PhoneNumberUtil phoneUtil = PhoneNumberUtil.createInstance(this);
        try {
            // phone must begin with '+'
            Phonenumber.PhoneNumber numberProto = phoneUtil.parse(phoneNUmber, "");
            countryCode = numberProto.getCountryCode();
        } catch (NumberParseException e) {
            System.err.println("NumberParseException was thrown: " + e.toString());
        }
        Log.e("countryCode", "===countryCode==" + countryCode);

        if (countryCode == 0) {
            etPhone.setText(phoneNUmber);
        } else {
            if (phoneNUmber.startsWith("+")) {
                if (phoneNUmber.length() == 12) {
                    String str_getMOBILE = phoneNUmber.substring(2);
                    etPhone.setText(str_getMOBILE);
                } else if (phoneNUmber.length() == 13) {
                    String str_getMOBILE = phoneNUmber.substring(3);
                    etPhone.setText(str_getMOBILE);
                } else if (phoneNUmber.length() == 14) {
                    String str_getMOBILE = phoneNUmber.substring(4);
                    etPhone.setText(str_getMOBILE);
                } else {
                    etPhone.setText(phoneNUmber);
                }
            }
            countryCodePicker.setCountryForPhoneCode(countryCode);
        }
        dialog.dismiss();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_maintoolbar, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.share_app:
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_SUBJECT, "WhatsAppDirect");
                String sAux = "\nLet me recommend you this application\n\n";
                sAux = sAux + "https://play.google.com/store/apps/details?id=" + appPackageName;
                i.putExtra(Intent.EXTRA_TEXT, sAux);
                startActivity(Intent.createChooser(i, "choose one"));

                break;

            case R.id.rate_app:

                Uri uri = Uri.parse("market://details?id=" + appPackageName);
                Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                        Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                        Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                try {
                    startActivity(goToMarket);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://play.google.com/store/apps/details?id=" + appPackageName)));
                }
                break;

            case R.id.terms_privacy:

                Intent intent = new Intent(mcontext, TermsAndPrivacyActivity.class);
                startActivity(intent);
                break;
        }
        return true;
    }

    public void clear(View view) {
        edtTextMsg.setText("");
        mClearText.setVisibility(View.GONE);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.e("SELECTED", "---select--no--data---" + data);
        if (requestCode == REQUEST_FOR_ACTIVITY_CODE) {

            if (data != null) {

                selectedNumber = data.getStringExtra("selectNumber");
                Log.e("SELECTED", "---select--no--final---" + selectedNumber);
                comparePhoneNumber(selectedNumber);
            } else {
            }
        }
    }

    private void comparePhoneNumber(String selectedNumber) {
        int countryCode = 0;
        PhoneNumberUtil phoneUtil = PhoneNumberUtil.createInstance(this);
        try {
            // phone must begin with '+'
            Phonenumber.PhoneNumber numberProto = phoneUtil.parse(selectedNumber, "");
            countryCode = numberProto.getCountryCode();
        } catch (NumberParseException e) {
            System.err.println("NumberParseException was thrown: " + e.toString());
        }
        Log.e("countryCode", "===countryCode==" + countryCode);

        if (countryCode == 0) {
            etPhone.setText(selectedNumber);
        } else {
            if (selectedNumber.startsWith("+")) {
                if (selectedNumber.length() == 12) {
                    String str_getMOBILE = selectedNumber.substring(2);
                    etPhone.setText(str_getMOBILE);
                } else if (selectedNumber.length() == 13) {
                    String str_getMOBILE = selectedNumber.substring(3);
                    etPhone.setText(str_getMOBILE);
                } else if (selectedNumber.length() == 14) {
                    String str_getMOBILE = selectedNumber.substring(4);
                    etPhone.setText(str_getMOBILE);
                } else {
                    etPhone.setText(selectedNumber);
                }
            }
            countryCodePicker.setCountryForPhoneCode(countryCode);
        }
    }
}






