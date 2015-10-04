package com.edavtyan.materialplayer.app.music.providers;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import com.edavtyan.materialplayer.app.music.data.MusicArtist;
import com.edavtyan.materialplayer.app.music.data.MusicTrack;

import java.util.ArrayList;

public class TracksProvider {
    private ContentResolver contentResolver;
    private ArrayList<MusicTrack> tracks;


    public ArrayList<MusicTrack> getTracks() {
        return tracks;
    }


    public TracksProvider(Context context) {
        contentResolver = context.getContentResolver();
        tracks = new ArrayList<>();
        loadTracks();
    }


    private void loadTracks() {
        Uri musicUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String[] projection = {
                MediaStore.Audio.Media.TRACK,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.ALBUM
        };

        Cursor cursor = contentResolver.query(musicUri, projection, null, null, null);

        while (cursor.moveToNext()) {
            long index = cursor.getLong(0);
            String title = cursor.getString(1);
            String artist = cursor.getString(2);
            String album = cursor.getString(3);
            MusicTrack track = new MusicTrack(index, title, artist, album);
            tracks.add(track);
        }

        cursor.close();
    }
}
