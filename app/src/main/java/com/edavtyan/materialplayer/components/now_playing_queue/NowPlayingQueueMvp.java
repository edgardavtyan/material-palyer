package com.edavtyan.materialplayer.components.now_playing_queue;

import android.content.ServiceConnection;

import com.edavtyan.materialplayer.db.Track;
import com.edavtyan.materialplayer.lib.mvp.list.ListMvp;

@SuppressWarnings("unused")
public interface NowPlayingQueueMvp {
	interface Model extends ListMvp.Model, ServiceConnection {
		void bindService();
		void unbindService();
		void playItemAtPosition(int position);
		void removeItemAtPosition(int position);
		Track getTrackAtPosition(int position);
		int getTrackCount();
	}

	interface View extends ListMvp.View {
	}

	interface Presenter extends ListMvp.Presenter<NowPlayingQueueViewHolder> {
		void onCreate();
		void onDestroy();
		void onItemClick(int position);
		void onRemoveItemClick(int position);
		int getItemCount();
	}
}
