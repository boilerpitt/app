package com.example.android.weather;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class Song {
    public String songName;
    public String Artist;
    public String Genre;
    public String File;
    public Song(){
        super();
    }

    public Song(String songName, String Artist, String Genre, String File) {
        super();
        this.songName = songName;
        this.Artist = Artist;
        this.Genre = Genre;
        this.File = File;
    }
}

