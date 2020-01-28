package com.ankit.apps.whatsappdirect.New.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ankit.apps.whatsappdirect.New.R;
import com.ankit.apps.whatsappdirect.New.Utils.AdapterCallback;
import com.ankit.apps.whatsappdirect.New.Utils.SMSModel;

import java.util.ArrayList;

/**
 * Created by ankit on 16/11/18.
 */

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.Holder> {

    Context mcontext;
    ArrayList<SMSModel> msmsItems = new ArrayList<>();
    private AdapterCallback callback;

    /*public MessageAdapter(FragmentActivity context, ArrayList<SMSModel> msmsItems, MessageFragment messageFragment) {
        this.mcontext = context;
        this.msmsItems = msmsItems;
    }*/
    public MessageAdapter(FragmentActivity context, ArrayList<SMSModel> msmsItems, AdapterCallback adapterCallback) {
        this.mcontext = context;
        this.msmsItems = msmsItems;
        this.callback = adapterCallback;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_list_design, parent, false);

        Holder holder = new Holder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {

        final SMSModel smsModel = msmsItems.get(position);

        Log.e("SELECT", "----msg--no--" + smsModel.getId());
        holder.txt_msgnum.setText(smsModel.getAddress());
        holder.tv_msg.setText(smsModel.getMsg());
        // holder.txt_msgid.setText(smsModel.getId());

        holder.cardview_msglist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                callback.onPositionClicked(smsModel.getAddress());
                Log.e("SELECT", "----no-msg--" + smsModel.getAddress());
               /* Intent intent = new Intent(mcontext, MainActivity.class);
                intent.putExtra("selectNumber", smsModel.getId());
                ((Activity) mcontext).startActivityForResult(intent, REQUEST_FOR_ACTIVITY_CODE);
                ((Activity) mcontext).finish();
                Log.e("SELECT", "----msg--no--" + smsModel.getId());*/
            }
        });
    }

    @Override
    public int getItemCount() {
        return msmsItems.size();
    }

    public class Holder extends RecyclerView.ViewHolder {

        public TextView tv_msg, txt_msgnum, txt_msgid;
        private CardView cardview_msglist;

        public Holder(View itemView) {
            super(itemView);

            txt_msgnum = (TextView) itemView.findViewById(R.id.txt_msgnum);
            //   txt_msgid = (TextView) itemView.findViewById(R.id.txt_msgid);
            tv_msg = (TextView) itemView.findViewById(R.id.tv_msg);
            cardview_msglist = itemView.findViewById(R.id.cardview_msglist);
        }
    }
}
