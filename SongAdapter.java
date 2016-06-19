package com.example.android.weather;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;

public class SongAdapter extends ArrayAdapter<Song> {

    Context context;
    int layoutResourceId;
    Song data[] = null;
    boolean playPause = false;
    MediaPlayer mediaPlayer = new MediaPlayer();

    public SongAdapter(Context context, int layoutResourceId, Song[] data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        SongHolder holder = null;

        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new SongHolder();
            holder.songName = (TextView)row.findViewById(R.id.songName);
            holder.Artist = (TextView)row.findViewById(R.id.Artist);
            holder.Genre = (TextView)row.findViewById(R.id.Genre);

            row.setTag(holder);
        }
        else
        {
            holder = (SongHolder)row.getTag();
        }

        Song song = data[position];
        holder.Artist.setText(song.Artist);
        holder.songName.setText(song.songName);
        holder.Genre.setText(song.Genre);
        final String File = song.File;

        Button PlayPause = (Button) convertView.findViewById(R.id.PlayPause);
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        PlayPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!playPause){
                    try {
                        mediaPlayer.setDataSource(File);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if(!mediaPlayer.isPlaying()) {
                        mediaPlayer.start();
                    }
                    playPause = true;
                }
                else{
                    if(mediaPlayer.isPlaying()){
                        mediaPlayer.pause();
                    }
                    playPause = false;
                }
            }
        });
        return row;
    }

    static class SongHolder
    {
        TextView songName;
        TextView Artist;
        TextView Genre;
    }
}
