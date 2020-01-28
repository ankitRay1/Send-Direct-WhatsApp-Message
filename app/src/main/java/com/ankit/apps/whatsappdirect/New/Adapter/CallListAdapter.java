package com.ankit.apps.whatsappdirect.New.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ankit.apps.whatsappdirect.New.R;
import com.ankit.apps.whatsappdirect.New.Utils.AdapterCallback;
import com.ankit.apps.whatsappdirect.New.Utils.CallLogItem;

import java.util.ArrayList;

/**
 * Created by ankit on 13/11/18.
 */

public class CallListAdapter extends RecyclerView.Adapter<CallListAdapter.Holder> {

    private Context mcontext;
    private ArrayList<CallLogItem> mcallList;
    private int REQUEST_FOR_ACTIVITY_CODE = 55;

    private AdapterCallback callback;

    public CallListAdapter(Context context, ArrayList<CallLogItem> mcallItems, AdapterCallback adapterCallback) {
        this.mcontext = context;
        this.mcallList = mcallItems;
        this.callback = adapterCallback;
    }

   /* public CallListAdapter(Context context, ArrayList<CallLogItem> mcallItems) {
        this.mcontext = context;
        this.mcallList = mcallItems;
    }*/

    @NonNull
    @Override
    public CallListAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.call_list_design, parent, false);

        // return new CallLogAdapter(LayoutInflater.from(mcontext).inflate(R.layout.call_log_list, parent, false),callback);
        Holder holder = new Holder(view, callback);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CallListAdapter.Holder holder, int position) {

        final CallLogItem callLogItem = mcallList.get(position);

        String time = "(" + callLogItem.getCallDuration() + "s" + ")";

       /* if (callLogItem.getCallType() == 1) {
            holder.iv_outcall.setVisibility(View.VISIBLE);
            holder.iv_incall.setVisibility(View.GONE);
            holder.iv_missedcall.setVisibility(View.GONE);

        } else if (callLogItem.getCallType() == 2) {

            holder.iv_outcall.setVisibility(View.GONE);
            holder.iv_incall.setVisibility(View.VISIBLE);
            holder.iv_missedcall.setVisibility(View.GONE);

        } else if (callLogItem.getCallType() == 3) {

            holder.iv_outcall.setVisibility(View.GONE);
            holder.iv_incall.setVisibility(View.GONE);
            holder.iv_missedcall.setVisibility(View.VISIBLE);
        }*/

        holder.phoneNumber.setText(callLogItem.getPhoneNumber());
        holder.callDate.setText(callLogItem.getCallDate());
        holder.callDuration.setText(time);

        /*holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                callback.onPositionClicked(callLogItem.getPhoneNumber());

                Log.e("SELECT", "----no-111-" + callLogItem.getPhoneNumber());
                *//* Intent intent = new Intent(mcontext,MainActivity.class);
                intent.putExtra("selectNumber",callLogItem.getPhoneNumber());
                mcontext.startActivity(intent);*//*
            }
        });
*/
        holder.card_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                callback.onPositionClicked(callLogItem.getPhoneNumber());
                Log.e("SELECT", "----no--" + callLogItem.getPhoneNumber());

               /* Intent intent = new Intent(mcontext, MainActivity.class);
                intent.putExtra("selectNumber", callLogItem.getPhoneNumber());
                ((Activity) mcontext).startActivityForResult(intent, REQUEST_FOR_ACTIVITY_CODE);
                ((Activity) mcontext).finish();
               */

              /*  Intent intent1 = new Intent(mcontext, MainActivity.class);
                intent1.putExtra("selectNumber", callLogItem.getPhoneNumber());
                mcontext.startActivity(intent);
               */
            }
        });
    }

    @Override
    public int getItemCount() {
        return mcallList.size();
    }

    public class Holder extends RecyclerView.ViewHolder {

        public TextView phoneNumber, callDuration, callDate;
        private CardView card_list;

        public Holder(View itemView, AdapterCallback callback) {
            super(itemView);

            phoneNumber = itemView.findViewById(R.id.txt_NumberMain);
            callDuration = (TextView) itemView.findViewById(R.id.tvTime);
            callDate = (TextView) itemView.findViewById(R.id.tvDate);
            card_list = itemView.findViewById(R.id.cardview_list);
        }
    }
}
