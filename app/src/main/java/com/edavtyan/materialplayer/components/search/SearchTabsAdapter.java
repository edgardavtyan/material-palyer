package com.edavtyan.materialplayer.components.search;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.edavtyan.materialplayer.components.search.album.SearchAlbumFragment;
import com.edavtyan.materialplayer.components.search.artist.SearchArtistFragment;
import com.edavtyan.materialplayer.components.search.tracks.SearchTrackFragment;

import java.util.ArrayList;
import java.util.List;

public class SearchTabsAdapter extends FragmentPagerAdapter {
	private List<Fragment> fragments;
	private CharSequence[] titles = {"Artists", "Albums", "Tracks"};

	public SearchTabsAdapter(FragmentManager fm) {
		super(fm);

		fragments = new ArrayList<>();
		fragments.add(new SearchArtistFragment());
		fragments.add(new SearchAlbumFragment());
		fragments.add(new SearchTrackFragment());
	}

	@Override
	public Fragment getItem(int position) {
		return fragments.get(position);
	}

	@Override
	public int getCount() {
		return fragments.size();
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return titles[position];
	}
}
