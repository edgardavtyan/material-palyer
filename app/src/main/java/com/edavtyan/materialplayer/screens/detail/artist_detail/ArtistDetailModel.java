package com.edavtyan.materialplayer.screens.detail.artist_detail;

import android.graphics.Bitmap;

import com.edavtyan.materialplayer.screens.lists.album_list.AlbumListModel;
import com.edavtyan.materialplayer.screens.lists.lib.CompactListPref;
import com.edavtyan.materialplayer.db.Album;
import com.edavtyan.materialplayer.db.AlbumDB;
import com.edavtyan.materialplayer.db.Artist;
import com.edavtyan.materialplayer.db.ArtistDB;
import com.edavtyan.materialplayer.db.TrackDB;
import com.edavtyan.materialplayer.modular.model.ModelServiceModule;

import java.util.List;

public class ArtistDetailModel extends AlbumListModel {
	private final ArtistDB artistDB;
	private final AlbumDB albumDB;
	private final String artistTitle;
	private final ArtistDetailImageLoader imageLoader;

	public ArtistDetailModel(
			ModelServiceModule serviceModule,
			ArtistDB artistDB,
			AlbumDB albumDB,
			TrackDB trackDB,
			CompactListPref compactListPref,
			ArtistDetailImageLoader imageLoader,
			String artistTitle) {
		super(serviceModule, albumDB, trackDB, compactListPref);
		this.artistDB = artistDB;
		this.albumDB = albumDB;
		this.artistTitle = artistTitle;
		this.imageLoader = imageLoader;
	}

	@Override
	protected List<Album> queryAlbums() {
		return albumDB.getAlbumsWithArtistTitle(artistTitle);
	}

	public Artist getArtist() {
		return artistDB.getArtistWithTitle(artistTitle);
	}

	public void loadArtistImage(ArtistDetailImageTask.OnImageLoadedCallback callback) {
		Bitmap cachedImage = imageLoader.getImageFromCache(artistTitle);
		if (cachedImage != null) {
			callback.OnImageLoaded(cachedImage);
		} else {
			new ArtistDetailImageTask(imageLoader, callback).execute(artistTitle);
		}
	}
}