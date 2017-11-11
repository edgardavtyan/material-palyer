package com.edavtyan.materialplayer.components.search.artist;

import com.edavtyan.materialplayer.components.lists.artist_list.ArtistListImageLoader;
import com.edavtyan.materialplayer.components.lists.artist_list.ArtistListModel;
import com.edavtyan.materialplayer.components.lists.lib.CompactListPref;
import com.edavtyan.materialplayer.components.search.base.SearchModel;
import com.edavtyan.materialplayer.db.Artist;
import com.edavtyan.materialplayer.db.ArtistDB;
import com.edavtyan.materialplayer.db.TrackDB;
import com.edavtyan.materialplayer.modular.model.ModelServiceModule;

import java.util.List;

public class SearchArtistModel extends ArtistListModel implements SearchModel {
	private final ArtistDB artistDB;

	private String query;

	public SearchArtistModel(
			ModelServiceModule serviceModule,
			ArtistDB artistDB,
			TrackDB trackDB,
			ArtistListImageLoader imageLoader,
			CompactListPref compactListPref) {
		super(serviceModule, artistDB, trackDB, imageLoader, compactListPref);
		this.artistDB = artistDB;
	}

	@Override
	protected List<Artist> queryArtists() {
		return artistDB.searchArtists(query);
	}

	@Override
	public void setQuery(String query) {
		this.query = query;
	}

	@Override
	public int getSearchResultCount() {
		return getArtistCount();
	}
}