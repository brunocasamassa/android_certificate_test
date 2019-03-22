package com.example.brunogerassicasamassa.testroompersistence.utils;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.text.Html;
import android.util.Log;
import android.webkit.WebView;

import com.example.brunogerassicasamassa.testroompersistence.R;
import com.example.brunogerassicasamassa.testroompersistence.activities.SongListCallback;
import com.example.brunogerassicasamassa.testroompersistence.model.Song;
import java.io.IOException;


public class BackgroundService extends Service {

    private static MediaPlayer mediaPlayer;
    private Bundle extras ;

    public SongListCallback getCallback() {
        return callback;
    }

    private static SongListCallback callback;


    public void setListener(SongListCallback callback){
        this.callback = callback;
    }


    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);

        if (intent != null) {

            extras = intent.getExtras();

            Song song = new Song();
            song.setArtist(extras.getString(getApplicationContext().getResources().getString(R.string.ARTIST_KEY)));
            song.setMusic(extras.getString(getApplicationContext().getResources().getString(R.string.MUSIC_KEY)));
            song.setId(extras.getInt(getApplicationContext().getResources().getString(R.string.ID_MUSIC_KEY),0));

            if(mediaPlayer == null){
                mediaPlayer = new MediaPlayer();
            }

        if(mediaPlayer.isPlaying()){
            mediaPlayer.reset();
        }
            try {

               AssetFileDescriptor afd =  getAssets().openFd("songs/"+song.getArtist() + "-"+ song.getMusic());

                mediaPlayer.setDataSource(afd.getFileDescriptor(),afd.getStartOffset(), afd.getLength());
                mediaPlayer.prepare();
                mediaPlayer.start();


            } catch (IOException e) {
                e.printStackTrace();
            }

            getCallback().onMediaPlayerSetted(mediaPlayer);

       }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
