package com.example.brunogerassicasamassa.testroompersistence.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.widget.ListView;

import com.example.brunogerassicasamassa.testroompersistence.R;
import com.example.brunogerassicasamassa.testroompersistence.activities.adapters.UserListAdapter;
import com.example.brunogerassicasamassa.testroompersistence.interactor.SongListInteractor;
import com.example.brunogerassicasamassa.testroompersistence.model.Song;
import com.example.brunogerassicasamassa.testroompersistence.model.User;
import com.example.brunogerassicasamassa.testroompersistence.utils.BackgroundService;

import java.util.ArrayList;

public class UserListActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<Song> arrayList;
    private BackgroundService backgroundService;
    private SongListInteractor interactor;
    private Bundle extras;
    private ListView listView;
    private ArrayList<User> userList;
    private UserListAdapter adapter;


    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);


        listView = findViewById(R.id.lv_userlist);
        extras = getIntent().getExtras();
        userList = extras.getParcelableArrayList("userlist");

        adapter = new UserListAdapter(userList , getApplicationContext());
        listView.setAdapter(adapter);


    }

}
