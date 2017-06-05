package com.example.kirill.mess.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.icu.text.SimpleDateFormat;
import android.icu.util.TimeZone;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.kirill.mess.Constants.Constants;
import com.example.kirill.mess.DialogFragment.Long_Tap_Menu;
import com.example.kirill.mess.DialogFragment.Long_Tap_Messages;
import com.example.kirill.mess.Parsers.ChatMessage;
import com.example.kirill.mess.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Messages extends Fragment {
    private static String mNameDialog;
    private DatabaseReference mSimpleFireMessageDatabaseReference;
    private FirebaseRecyclerAdapter<ChatMessage, FirechatMsgViewHolder> mFirebaseAdapter;
    private RecyclerView mMessageRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private ProgressBar mProgressBar;
    private Button mSendButton;
    private EditText mMsgEditText;
    private View vRootView;
    private TextView vTextView;

    public static class FirechatMsgViewHolder extends RecyclerView.ViewHolder {
        public TextView vMsgTextView;
        public TextView vDataTextView;
        public LinearLayout vAdapterLinerLayout;

        public FirechatMsgViewHolder(View v) {
            super(v);
            vAdapterLinerLayout = (LinearLayout)itemView.findViewById(R.id.adapter_liner_layout);
            vMsgTextView = (TextView) itemView.findViewById(R.id.adapterMsgTextView);
            vDataTextView = (TextView) itemView.findViewById(R.id.adapterdataText);
        }

    }

    public static Messages newInstance(String someString) {
        Messages messages = new Messages();
        Bundle args = new Bundle();
        args.putString("NameDialog", someString);
        messages.setArguments(args);
        mNameDialog = someString;
        return messages;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Constants.CountMessage = 0;
        vRootView = inflater.inflate(R.layout.fragment_messages, container, false);
        mProgressBar = (ProgressBar)vRootView.findViewById(R.id.progressBar);
        mMessageRecyclerView = (RecyclerView)vRootView.findViewById(R.id.messageRecyclerView);
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        mMessageRecyclerView.setItemAnimator(itemAnimator);
        mLinearLayoutManager = new LinearLayoutManager(vRootView.getContext());
        mLinearLayoutManager.setStackFromEnd(true);
        mMessageRecyclerView.setLayoutManager(mLinearLayoutManager);

        vTextView = (TextView) vRootView.findViewById(R.id.fragment_messages_textView);

        mSendButton = (Button)vRootView.findViewById(R.id.sendButton);
        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                long date = System.currentTimeMillis();
                SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy hh:mm");
                sdf.setTimeZone(TimeZone.getTimeZone("Etc/GMT-3"));
                String dateString = sdf.format(date);
                ChatMessage friendlyMessage = new ChatMessage(mMsgEditText.getText().toString(),dateString,Constants.UserEmail);
                mSimpleFireMessageDatabaseReference.child(mNameDialog).push().setValue(friendlyMessage);
                mMsgEditText.setText("");
            }
        });


        mMsgEditText = (EditText) vRootView.findViewById(R.id.msgEditText);
        mMsgEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().trim().length() > 0) {
                    mSendButton.setEnabled(true);
                } else {
                    mSendButton.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });


        mSimpleFireMessageDatabaseReference = FirebaseDatabase.getInstance().getReference();
        mFirebaseAdapter = new FirebaseRecyclerAdapter<ChatMessage, FirechatMsgViewHolder>(
                ChatMessage.class,
                R.layout.adapter_fragment_message,
                FirechatMsgViewHolder.class,
                mSimpleFireMessageDatabaseReference.child(mNameDialog)) {
            @Override
            public void onBindViewHolder(final FirechatMsgViewHolder viewHolder, int position) {
                super.onBindViewHolder(viewHolder, position);
                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d("TAP", " +");
                        /*Long_Tap_Messages fragment = new Long_Tap_Messages();
                        //fragment.show(, "Long TAP");
                        fragment.show("L","Long Tap");*/
                    }
                });
                viewHolder.vAdapterLinerLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d("TAP", " -");
                    }
                });
            }

            @Override
            protected void populateViewHolder(FirechatMsgViewHolder viewHolder, ChatMessage friendlyMessage, int position) {
                String mWhoSendMSG;
                mProgressBar.setVisibility(ProgressBar.INVISIBLE);
                vTextView.setVisibility(View.INVISIBLE);
                mWhoSendMSG = friendlyMessage.getPhoto();
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
                if(mWhoSendMSG.equals(Constants.UserEmail)){
                    lp.gravity = Gravity.RIGHT;}
                else {lp.gravity = Gravity.CENTER_VERTICAL;}
                viewHolder.vAdapterLinerLayout.setLayoutParams(lp);
                viewHolder.vMsgTextView.setText(friendlyMessage.getText());
                viewHolder.vDataTextView.setText(friendlyMessage.getText2());
            }
        };
        mFirebaseAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                int chatMessageCount = mFirebaseAdapter.getItemCount();
                int lastVisiblePosition =
                        mLinearLayoutManager.findLastCompletelyVisibleItemPosition();
                if (lastVisiblePosition == -1 ||
                        (positionStart >= (chatMessageCount - 1) &&
                                lastVisiblePosition == (positionStart - 1))) {
                    mMessageRecyclerView.scrollToPosition(positionStart);
                }
                Constants.CountMessage = positionStart + 1;
            }});
        if(Constants.CountMessage == 0){
            vTextView.setVisibility(View.VISIBLE);
        }
        for (int i = 0; i < Constants.mNameDialog.length; i++)Log.d("TAP", i + " " + Constants.mNameDialog[i] );
        mMessageRecyclerView.setLayoutManager(mLinearLayoutManager);
        mMessageRecyclerView.setAdapter(mFirebaseAdapter);
        return vRootView;
    }

}
