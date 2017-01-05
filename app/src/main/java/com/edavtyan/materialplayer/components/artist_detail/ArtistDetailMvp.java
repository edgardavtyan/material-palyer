package com.edavtyan.materialplayer.components.artist_detail;

import android.graphics.Bitmap;

import com.edavtyan.materialplayer.lib.mvp.parallax_list.ParallaxHeaderListPresenter;
import com.edavtyan.materialplayer.components.album_all.AlbumListMvp;
import com.edavtyan.materialplayer.db.Artist;

@SuppressWarnings("unused")
public interface ArtistDetailMvp {
	interface Model extends AlbumListMvp.Model {
		Artist getArtist();
		Bitmap getLocalArtistImage();
		void loadArtistImageFromApi(ArtistImageTask.OnArtLoadedCallback callback);
	}

	interface View extends AlbumListMvp.View {
		void setArtistInfo(int albumsCount, int tracksCount);
		void setArtistTitle(String title);
		void setArtistImage(Bitmap art);
	}

	interface Presenter extends AlbumListMvp.Presenter, ParallaxHeaderListPresenter {
	}
}
