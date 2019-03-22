package com.example.brunogerassicasamassa.testroompersistence.interactor;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.util.Log;

import com.example.brunogerassicasamassa.testroompersistence.activities.SongListActivity;
import com.example.brunogerassicasamassa.testroompersistence.activities.SongListCallback;
import com.example.brunogerassicasamassa.testroompersistence.model.Song;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SongListInteractorImpl implements SongListInteractor {
    private static ArrayList<Song> arrayList;
    private SongListCallback callback;
    private Context context;
    private String TAG = "SngLstIntrImpl";

    public SongListInteractorImpl(SongListCallback songListCallback, Context context) {
        this.callback = songListCallback;
        this.context = context;
        retrieveSongs();

    }


    @Override
    public void retrieveSongs() {

        String[] songs = new String[0];
        try {
            songs = context.getResources().getAssets().list("songs");
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<String> arrays = Arrays.asList(songs);
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.addAll(arrays);

        ArrayList<Song> songList = new ArrayList<>();


        for (int i = 0; i < arrayList.size(); i++) {
            Song song = new Song();

            song.setMusic(arrayList.get(i).split("-")[1]);
            song.setArtist(arrayList.get(i).split("-")[0]);
            song.setId(i);

            songList.add(song);

        }


        callback.onReceiveMusicList(songList);


    }
}
