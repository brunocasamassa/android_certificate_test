package com.example.brunogerassicasamassa.testroompersistence.activities;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.brunogerassicasamassa.testroompersistence.R;
import com.example.brunogerassicasamassa.testroompersistence.activities.adapters.SongListAdapter;
import com.example.brunogerassicasamassa.testroompersistence.interactor.SongListInteractor;
import com.example.brunogerassicasamassa.testroompersistence.interactor.SongListInteractorImpl;
import com.example.brunogerassicasamassa.testroompersistence.model.Song;
import com.example.brunogerassicasamassa.testroompersistence.utils.BackgroundService;

import java.util.ArrayList;

public class SongListActivity extends AppCompatActivity implements SongListCallback {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private ArrayList<Song> arrayList;
    private static BackgroundService backgroundService;
    private SongListInteractor interactor;
    private MediaPlayer musicPlayer;


    public ArrayList<Song> getArrayList() {
        return arrayList;
    }

    public void setArrayList(ArrayList<Song> arrayList) {
        this.arrayList = arrayList;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song);

        Log.d("SHOW ME", "FFF");

        interactor = new SongListInteractorImpl(this, getApplicationContext());

        mAdapter = new SongListAdapter(getApplicationContext(), arrayList, this);
        mRecyclerView = findViewById(R.id.song_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mRecyclerView.setAdapter(mAdapter);

        backgroundService = new BackgroundService();
        backgroundService.setListener(this);


    }

    @Override
    public void onMusicSelected(Song song) {

        Intent intent = new Intent(SongListActivity.this, backgroundService.getClass());


        intent.putExtra(getResources().getString(R.string.ARTIST_KEY), song.getArtist());
        intent.putExtra(getResources().getString(R.string.ID_MUSIC_KEY), song.getId());
        intent.putExtra(getResources().getString(R.string.MUSIC_KEY), song.getMusic());

        startService(intent);

        //backgroundService.onStart(intent, getApplicationContext().getResources().getInteger(R.integer.PLAY_SONG_ID));

    }

    public MediaPlayer getMusicPlayer() {
        return musicPlayer;
    }

    @Override
    public void onReceiveMusicList(ArrayList<Song> arrayList) {
        this.arrayList = arrayList;

    }

    @Override
    public void onMediaPlayerSetted(MediaPlayer player) {
        this.musicPlayer = player;
    }
}
