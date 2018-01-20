package com.edavtyan.materialplayer.components.search.artist;

import android.support.v4.app.FragmentActivity;

import com.edavtyan.materialplayer.components.FragmentScope;
import com.edavtyan.materialplayer.components.lists.artist_list.ArtistListImageLoader;
import com.edavtyan.materialplayer.components.lists.lib.CompactListPref;
import com.edavtyan.materialplayer.components.search.base.SearchViewImpl;
import com.edavtyan.materialplayer.db.ArtistDB;
import com.edavtyan.materialplayer.db.TrackDB;
import com.edavtyan.materialplayer.modular.model.ModelServiceModule;

import dagger.Module;
import dagger.Provides;

@Module
public class SearchArtistModule {
	private final SearchArtistFragment view;

	public SearchArtistModule(SearchArtistFragment view) {
		this.view = view;
	}

	@Provides
	@FragmentScope
	public SearchArtistFragment provideView() {
		return view;
	}

	@Provides
	@FragmentScope
	public SearchArtistAdapter provideAdapter(
			FragmentActivity activity, SearchArtistPresenter presenter) {
		return new SearchArtistAdapter(activity, presenter);
	}

	@Provides
	@FragmentScope
	public SearchArtistModel provideModel(
			ModelServiceModule serviceModule,
			ArtistDB artistDB,
			TrackDB trackDB,
			ArtistListImageLoader imageLoader,
			CompactListPref compactListPref) {
		return new SearchArtistModel(
				serviceModule, artistDB, trackDB, imageLoader, compactListPref);
	}

	@Provides
	@FragmentScope
	public SearchArtistPresenter providePresenter(
			SearchArtistModel model, SearchArtistFragment view) {
		return new SearchArtistPresenter(model, view);
	}

	@Provides
	@FragmentScope
	public SearchViewImpl provideSearchViewImpl(
			SearchArtistFragment view, SearchArtistPresenter presenter) {
		return new SearchViewImpl(view, presenter);
	}
}
