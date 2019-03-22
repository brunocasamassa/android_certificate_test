package com.example.brunogerassicasamassa.testroompersistence.activities.adapters;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.brunogerassicasamassa.testroompersistence.R;
import com.example.brunogerassicasamassa.testroompersistence.activities.SongListCallback;
import com.example.brunogerassicasamassa.testroompersistence.model.Song;

import java.util.ArrayList;

public class SongListAdapter extends RecyclerView.Adapter<SongListAdapter.SongViewHolder> {

    private ArrayList<Song> arrayList;
    private Context context;
    private SongListCallback callback;
    public SongListAdapter(Context applicationContext, ArrayList<Song> arrayList, SongListCallback activity) {
        this.arrayList = arrayList;
        this.context = applicationContext;
        this.callback = activity;
    }

    @Override
    public SongViewHolder onCreateViewHolder( ViewGroup viewGroup, int i) {
        return new SongViewHolder(LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_music, viewGroup, false));    }

    @Override
    public void onBindViewHolder( SongViewHolder viewHolder, int i) {


        final Song song = new Song();
        song.setId(arrayList.get(i).getId());
        song.setArtist(arrayList.get(i).getArtist());
        song.setMusic(arrayList.get(i).getMusic());

        viewHolder.artist.setText(arrayList.get(i).getArtist());
        viewHolder.music.setText(arrayList.get(i).getMusic());

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                callback.onMusicSelected(song);


            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }



    public class SongViewHolder extends RecyclerView.ViewHolder {
        private TextView artist;
        private TextView music;

        public SongViewHolder( View itemView) {
            super(itemView);

            if (itemView != null){
                artist = itemView.findViewById(R.id.artist_name);
                music = itemView.findViewById(R.id.music_name);

            }
        }


    }
}
