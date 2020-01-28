package com.ankit.apps.whatsappdirect.New;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.CallLog;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.ankit.apps.whatsappdirect.New.Activity.MainActivity;
import com.ankit.apps.whatsappdirect.New.Adapter.CallListAdapter;
import com.ankit.apps.whatsappdirect.New.Utils.AdapterCallback;
import com.ankit.apps.whatsappdirect.New.Utils.CallLogHelper;
import com.ankit.apps.whatsappdirect.New.Utils.CallLogItem;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class PhoneCallsfragment extends Fragment implements AdapterCallback {

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
    ProgressDialog progressDialog;
    private InterstitialAd mInterstitialAd;

    public PhoneCallsfragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_phone_callsfragment, container, false);

//        progressDialog = new ProgressDialog(getActivity());
//        progressDialog.setMessage("Loading..");
//        progressDialog.show();
        //askPermission();

        recycleview_call_log = view.findViewById(R.id.recycleview_call_log);

        curLog = CallLogHelper.getAllCallLogs(getActivity().getContentResolver());
        setCallLogs(curLog);
        return view;
    }

    private void setCallLogs(Cursor curLog) {

        if (curLog.getCount() == 0) {

            final android.app.AlertDialog.Builder builder;
            //builder = new AlertDialog.Builder(mcontext, android.R.style.Theme_Material_Dialog_Alert);
            builder = new android.app.AlertDialog.Builder(getActivity());
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

        mcallItems.clear();

        while (curLog.moveToNext()) {

            String callNumber = curLog.getString(curLog.getColumnIndex(android.provider.CallLog.Calls.NUMBER));

            String callName = curLog.getString(curLog.getColumnIndex(android.provider.CallLog.Calls.CACHED_NAME));
            /*if (callName == null) {
                conNames.add("Unknown");
            } else
                conNames.add(callName);*/

            String callDate = curLog.getString(curLog.getColumnIndex(android.provider.CallLog.Calls.DATE));

            SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy HH:mm aa");
            String dateString = formatter.format(new Date(Long.parseLong(callDate)));
            //conDate.add(dateString);

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
            //conTime.add(duration);

            CallLogItem callLogItem = new CallLogItem();
            callLogItem.setPhoneNumber(callNumber);
            callLogItem.setCallDate(String.valueOf(dateString));
            callLogItem.setCallType(callType);
            callLogItem.setCallDuration(duration);
            mcallItems.add(callLogItem);

            recycleview_call_log.setHasFixedSize(true);
            mLayoutManager = new LinearLayoutManager(getActivity());
            recycleview_call_log.setLayoutManager(mLayoutManager);
            mAdapter = new CallListAdapter(getActivity(), mcallItems, this);
            recycleview_call_log.setAdapter(mAdapter);

            //progressDialog.dismiss();
        }
    }

    @Override
    public void onPositionClicked(String phoneNUmber) {
        phone = phoneNUmber;

        setInterstitialAd();

        /*Intent intent = new Intent(getActivity(), MainActivity.class);
        intent.putExtra("selectNumber", phone);
        getActivity().setResult(REQUEST_FOR_ACTIVITY_CODE, intent);
        getActivity().finish();
        Log.e("SELECT", "-----phoneNUmber-11----" + phoneNUmber);*/
    }

    @Override
    public void onResume() {
        super.onResume();

        Log.e("RESUME", "---phone---resume----");
    }

    private void setInterstitialAd() {

        AdRequest adRequest = new AdRequest.Builder().addTestDevice("YOUR_TEST_DEVICE_ID").build();
        mInterstitialAd = new InterstitialAd(getActivity());
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
                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.putExtra("selectNumber", phone);
                getActivity().setResult(REQUEST_FOR_ACTIVITY_CODE, intent);
                getActivity().finish();
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
}
