package com.example.brunogerassicasamassa.testroompersistence.activities;

import android.media.MediaPlayer;

import com.example.brunogerassicasamassa.testroompersistence.model.Song;

import java.util.ArrayList;

public interface SongListCallback {

    void onMusicSelected(Song song);

    void onReceiveMusicList(ArrayList<Song> arrayList);

    void onMediaPlayerSetted(MediaPlayer player);
}
