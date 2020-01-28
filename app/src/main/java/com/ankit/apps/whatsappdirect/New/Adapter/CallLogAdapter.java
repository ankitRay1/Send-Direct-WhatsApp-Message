package com.ankit.apps.whatsappdirect.New.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ankit.apps.whatsappdirect.New.Utils.AdapterCallback;
import com.ankit.apps.whatsappdirect.New.Utils.CallLogItem;
import com.ankit.apps.whatsappdirect.New.R;

import java.util.ArrayList;

/**
 * Created by ankit on 11/09/18.
 */

public class CallLogAdapter extends RecyclerView.Adapter<CallLogAdapter.Holder> {

    private Context mcontext;
    private ArrayList<CallLogItem> mcallList;

    private AdapterCallback callback;

    public CallLogAdapter(Context context, ArrayList<CallLogItem> mcallItems, AdapterCallback adapterCallback) {
        this.mcontext = context;
        this.mcallList = mcallItems;
        this.callback = adapterCallback;
    }

    @NonNull
    @Override
    public CallLogAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.call_log_list, parent, false);

        // return new CallLogAdapter(LayoutInflater.from(mcontext).inflate(R.layout.call_log_list, parent, false),callback);
        Holder holder = new Holder(view, callback);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final CallLogAdapter.Holder holder, final int position) {

        final CallLogItem callLogItem = mcallList.get(position);

        String time = "(" + callLogItem.getCallDuration() + "s" + ")";

        if (callLogItem.getCallType() == 1) {
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
        }
        holder.phoneNumber.setText(callLogItem.getPhoneNumber());
        holder.callDate.setText(callLogItem.getCallDate());
        holder.callDuration.setText(time);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callback.onPositionClicked(callLogItem.getPhoneNumber());

                /* Intent intent = new Intent(mcontext,MainActivity.class);
                intent.putExtra("selectNumber",callLogItem.getPhoneNumber());
                mcontext.startActivity(intent);*/
            }
        });
    }

    @Override
    public int getItemCount() {
        return mcallList.size();
    }

    public class Holder extends RecyclerView.ViewHolder {

        public View view;
        private LinearLayout linearLayout_adapter;
        public TextView phoneNumber, callDuration, callDate, callType;
        private ImageView iv_outcall, iv_incall, iv_missedcall;

        public Holder(View itemView, AdapterCallback listener) {
            super(itemView);

            phoneNumber = itemView.findViewById(R.id.txt_NumberMain);
            callDuration = (TextView) itemView.findViewById(R.id.tvTime);
            callDate = (TextView) itemView.findViewById(R.id.tvDate);
            callType = (TextView) itemView.findViewById(R.id.tvType);

            iv_outcall = itemView.findViewById(R.id.iv_outcall_1);
            iv_incall = itemView.findViewById(R.id.iv_incomingcall_2);
            iv_missedcall = itemView.findViewById(R.id.iv_missedcall_3);

            linearLayout_adapter = itemView.findViewById(R.id.adapter_linearlayout);

        }
    }
}
