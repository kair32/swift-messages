package com.example.kirill.mess;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.kirill.mess.Constants.Constants;
import com.example.kirill.mess.DialogFragment.Long_Tap_Messages;
import com.example.kirill.mess.Firebase.Adapter_Resyl_class;
import com.example.kirill.mess.Fragment.About_Program;
import com.example.kirill.mess.Fragment.Create_Dialog_Fragment;
import com.example.kirill.mess.Fragment.Join_Dialog_Fragment;
import com.example.kirill.mess.DialogFragment.Long_Tap_Menu;
import com.example.kirill.mess.Fragment.Messages;
import com.example.kirill.mess.Fragment.Setting_Fragment;
import com.example.kirill.mess.Fragment.Setting_Message_Fragment;
import com.example.kirill.mess.Parsers.ChatMessage;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import jp.wasabeef.blurry.Blurry;


public class Main2Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, GoogleApiClient.OnConnectionFailedListener {

    private FrameLayout vFrameLayout;
    private SharedPreferences mSettings;
    private GoogleApiClient mGoogleApiClient;
    private TextView vHeaderAccountName;
    private FloatingActionButton vFab;

    private Menu vMenu;
    private FirebaseRecyclerAdapter<ChatMessage, Adapter_Resyl_class> Adapter;
    private RecyclerView vMessageRecyclerView;
    private LinearLayoutManager vLinearLayoutManager;
    private DatabaseReference mSimpleFirechatDatabaseReference;
    private ProgressBar vProgressBar;
    private LinearLayout vRelativeLayout;
    private ImageView vNavHeaderStartImageView, vHeaderAccountImageView, vImageViewWriteMessage;
    private NavigationView navigationView;
    private boolean mBoolPass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this , this )
                .addApi(Auth.GOOGLE_SIGN_IN_API)
                .build();

        mSettings = getSharedPreferences(Constants.SETTING_NAME, Context.MODE_PRIVATE);
        mBoolPass = mSettings.getBoolean("HavePass", false);
        if(mBoolPass) startActivity(new Intent(this, ActivityEntryPassword.class));

        setContentView(R.layout.start_main_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigationView.setCheckedItem(R.id.nav_create_dialog);
                setTitle("Создать диалог");
                vProgressBar.setVisibility(View.INVISIBLE);
                vFab.setVisibility(View.INVISIBLE);
                vRelativeLayout.setVisibility(View.INVISIBLE);
                vFrameLayout.setVisibility(View.VISIBLE);
                Constants.FragmentTransaction = getSupportFragmentManager().beginTransaction();
                Constants.FragmentTransaction.addToBackStack(null);
                Constants.FragmentTransaction.replace(R.id.fragment12,new Create_Dialog_Fragment()).commit();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        vImageViewWriteMessage = (ImageView)findViewById(R.id.imageView_write_message);
        vImageViewWriteMessage.setVisibility(View.GONE);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_dialogs);
        vRelativeLayout = (LinearLayout)findViewById(R.id.relative_layout);
        vFab = (FloatingActionButton)findViewById(R.id.fab);
        View hView = navigationView.inflateHeaderView(R.layout.nav_header_start_main);
        vFrameLayout = (FrameLayout)findViewById(R.id.fragment12);
        vHeaderAccountImageView = (ImageView)hView.findViewById(R.id.header_account_image_view);
        vNavHeaderStartImageView = (ImageView)hView.findViewById(R.id.nav_headler_start_main_image_view);
        vHeaderAccountName = (TextView)hView.findViewById(R.id.header_name_account);
        mSettings = getSharedPreferences(Constants.SETTING_NAME, Context.MODE_PRIVATE);
        Constants.UserName = mSettings.getString("Name", "");
        Constants.UserPhoto = mSettings.getString("Photo", null);
        Log.d("TAP", " " + Constants.UserName);
        if(Constants.UserName == "")startActivity(new Intent(this, SignInActivity.class));
        char[] s = mSettings.getString("Email","").toCharArray();
        Constants.UserEmail = "";
        for(int i = 0; i < s.length; i++)if(s[i] != '.') Constants.UserEmail += s[i];
        Glide
                .with(this)
                .load(Constants.UserPhoto)
                .into(vHeaderAccountImageView);
        Glide
                .with(this)
                .load(Constants.UserPhoto)
                .centerCrop()
                .into(vNavHeaderStartImageView);


        //Uri file = Uri.parse("content://media/external/images/media/1400");// Uri.fromFile(new File(String.valueOf(Uri.parse("content://media/external/images/media/1400"))));

        /*byte[] data = baos.toByteArray();

        UploadTask uploadTask = storageRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener(){
            @Override
            public void onFailure(@NonNull Exception exception) {
                Log.d("TAP"," FA");
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Log.d("TAP"," TRU");
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                Uri downloadUrl = taskSnapshot.getDownloadUrl();
            }
        });*/



        vHeaderAccountName.setText(Constants.UserName);
        Toast.makeText(Main2Activity.this, Constants.UserName + " " + getResources().getString(R.string.welcome) ,Toast.LENGTH_LONG ).show();

        vProgressBar = (ProgressBar)findViewById(R.id.progressBar);
        vMessageRecyclerView = (RecyclerView) findViewById(R.id.messageRecyclerView);
        vLinearLayoutManager = new LinearLayoutManager(this);
        vLinearLayoutManager.setStackFromEnd(true);

        vMessageRecyclerView.setLayoutManager(vLinearLayoutManager);
        mSimpleFirechatDatabaseReference = FirebaseDatabase.getInstance().getReference();

        Adapter = new FirebaseRecyclerAdapter<ChatMessage,Adapter_Resyl_class>(
                ChatMessage.class,
                R.layout.adapter_fragment_dialog,
                Adapter_Resyl_class.class,
                mSimpleFirechatDatabaseReference.child(Constants.UserEmail))
        {
            @Override
            public void onBindViewHolder(final Adapter_Resyl_class viewHolder, final int position) {
                super.onBindViewHolder(viewHolder, position);
                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        vFrameLayout.setVisibility(View.VISIBLE);
                        vRelativeLayout.setVisibility(View.INVISIBLE);
                        vFab.setVisibility(View.INVISIBLE);
                        vMenu.clear();
                        getMenuInflater().inflate(R.menu.message_menu, vMenu);
                        Messages mes = Messages.newInstance("" + viewHolder.mKeyDialog);
                        Constants.mNumberDialog = position;
                        Constants.FragmentTransaction = getSupportFragmentManager().beginTransaction();
                        Constants.FragmentTransaction.addToBackStack(null);
                        Constants.FragmentTransaction.replace(R.id.fragment12,mes).commit();
                    }
                });
                viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        Constants.mDellDialog = viewHolder.mKeyDialog;
                        //Long_Tap_Menu fragment = new Long_Tap_Menu();
                        //fragment.show(getFragmentManager(), "Long TAP");
                        Long_Tap_Messages fragments = new Long_Tap_Messages();
                        fragments.show(getFragmentManager(),"Long Tap");
                        return false;
                    }
                });
            }

            @Override
            protected void populateViewHolder(Adapter_Resyl_class viewHolder, ChatMessage friendlyMessage, int position) {
                vProgressBar.setVisibility(ProgressBar.INVISIBLE);
                vImageViewWriteMessage.setVisibility(View.INVISIBLE);
                viewHolder.vNameDialogTextView.setText(friendlyMessage.getText());
                viewHolder.mKeyDialog = friendlyMessage.getText2();
                //viewHolder.vDataTextView.setText(friendlyMessage.getData());
                Constants.mKeyDialog[position] = friendlyMessage.getText2();
                Constants.mURLPhoto[position] = friendlyMessage.getPhoto();
                Constants.mNameDialog[position] = friendlyMessage.getText();

                Glide
                        .with(getBaseContext())
                        .load(friendlyMessage.getPhoto())
                        .into(viewHolder.vDialogImageView);
            }
        };
        Adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                int chatMessageCount = Adapter.getItemCount();
                int lastVisiblePosition = vLinearLayoutManager.findLastCompletelyVisibleItemPosition();
                if (lastVisiblePosition == -1 || (positionStart >= (chatMessageCount - 1) && lastVisiblePosition == (positionStart - 1))) {
                    vMessageRecyclerView.scrollToPosition(positionStart);
                }
                Constants.CountDialogs = positionStart + 1;
                Constants.mNameDialog = new String[Constants.CountDialogs];
                Constants.mKeyDialog = new String[Constants.CountDialogs];
                Constants.mURLPhoto = new String[Constants.CountDialogs];
               /* Blurry.with(getBaseContext())//вынести куда-нибудь!
                        .radius(25)
                        .sampling(5)
                        .capture((ViewGroup) findViewById(R.id.nav_headler_start_main_liner))
                        .into(vNavHeaderStartImageView);*/
            }
        });
        Log.d("TAP", " "+ Adapter.getItemCount());
        if(Constants.CountDialogs == 0){
            vImageViewWriteMessage.setVisibility(View.VISIBLE);
            //vProgressBar.setVisibility(ProgressBar.INVISIBLE);
        }
        vMessageRecyclerView.setAdapter(Adapter);
    }

public void blur(){
    Blurry.with(getBaseContext())//вынести куда-нибудь!
            .radius(25)
            .sampling(5)
            .capture((ViewGroup) findViewById(R.id.nav_headler_start_main_liner))
            .into(vNavHeaderStartImageView);
}

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if(vFrameLayout.getVisibility() == View.VISIBLE) {
                vMenu.clear();
                getMenuInflater().inflate(R.menu.main2, vMenu);
                navigationView.setCheckedItem(R.id.nav_dialogs);
                setTitle("Swift Message");
                vFab.setVisibility(View.VISIBLE);
                vFrameLayout.setVisibility(View.INVISIBLE);
                vRelativeLayout.setVisibility(View.VISIBLE);
            }
            else super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        vMenu = menu;
        getMenuInflater().inflate(R.menu.main2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_output:
                vMenu.clear();
                getMenuInflater().inflate(R.menu.main2, vMenu);
                Auth.GoogleSignInApi.signOut(mGoogleApiClient);
                Constants.UserName = "";
                Constants.UserPhoto = "";
                Constants.UserEmail = "";
                SharedPreferences.Editor editor = getSharedPreferences(Constants.SETTING_NAME, MODE_PRIVATE).edit();
                editor.putString("Name", "");
                editor.putString("Photo", "");
                editor.putString("Email", "");
                editor.commit();
                startActivity(new Intent(this, SignInActivity.class));
                break;
            case R.id.menu_setting:
                setVisibl("Настройки");
                Constants.FragmentTransaction.replace(R.id.fragment12,new Setting_Message_Fragment()).commit();

                vFrameLayout.setVisibility(View.VISIBLE);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.nav_create_dialog:
                setVisibl("Создать диалог");
                Constants.FragmentTransaction.replace(R.id.fragment12,new Create_Dialog_Fragment()).commit();
                vFrameLayout.setVisibility(View.VISIBLE);
                break;
            case R.id.nav_dialogs:
                vMenu.clear();
                getMenuInflater().inflate(R.menu.main2, vMenu);
                setTitle("Swift Message");
                vFab.setVisibility(View.VISIBLE);
                vFrameLayout.setVisibility(View.INVISIBLE);
                vRelativeLayout.setVisibility(View.VISIBLE);
                break;
            case R.id.nav_invite:
                setVisibl("Присоедениться");
                Constants.FragmentTransaction.replace(R.id.fragment12,new Join_Dialog_Fragment()).commit();
                vFrameLayout.setVisibility(View.VISIBLE);
                break;
            case R.id.nav_setting:
                setVisibl("Настройки");
                Constants.FragmentTransaction.replace(R.id.fragment12,new Setting_Fragment()).commit();
                vFrameLayout.setVisibility(View.VISIBLE);
                break;
            case R.id.nav_help:
                setVisibl("О программе");
                Constants.FragmentTransaction.replace(R.id.fragment12,new About_Program()).commit();
                vFrameLayout.setVisibility(View.VISIBLE);
                break;
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void setVisibl(String name){
        vMenu.clear();
        getMenuInflater().inflate(R.menu.main2, vMenu);
        setTitle(name);
        vProgressBar.setVisibility(View.INVISIBLE);
        vFab.setVisibility(View.INVISIBLE);
        vRelativeLayout.setVisibility(View.INVISIBLE);
        Constants.FragmentTransaction = getSupportFragmentManager().beginTransaction();
        Constants.FragmentTransaction.addToBackStack(null);
    }
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d("MAIN FAILL", "onConnectionFailed:" + connectionResult);
    }
}
/*
            РАЗДЕЛИТЕЛИ
<shape xmlns:android="http://schemas.android.com/apk/res/android" >

    <size android:width="1dp" />

    <solid android:color="#90909090" />

</shape>*/