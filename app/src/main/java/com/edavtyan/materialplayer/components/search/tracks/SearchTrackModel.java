package com.edavtyan.materialplayer.components.search.tracks;

import android.content.Context;

import com.edavtyan.materialplayer.components.track_all.TrackListModel;
import com.edavtyan.materialplayer.db.Track;
import com.edavtyan.materialplayer.db.TrackDB;
import com.edavtyan.materialplayer.lib.mvp.list.CompactListPref;

import java.util.List;

import lombok.Setter;

public class SearchTrackModel extends TrackListModel {
	private final TrackDB trackDB;

	private @Setter String trackTitle;

	public SearchTrackModel(Context context, TrackDB trackDB, CompactListPref compactListPref) {
		super(context, trackDB, compactListPref);
		this.trackDB = trackDB;
	}

	@Override
	protected List<Track> queryTracks() {
		return trackDB.searchTracks(trackTitle);
	}
}
