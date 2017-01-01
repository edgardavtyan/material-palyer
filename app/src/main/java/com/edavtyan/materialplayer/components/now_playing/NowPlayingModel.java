package com.edavtyan.materialplayer.components.now_playing;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.IBinder;

import com.edavtyan.materialplayer.components.player.PlayerMvp;
import com.edavtyan.materialplayer.components.player.PlayerService;
import com.edavtyan.materialplayer.components.player.RepeatMode;
import com.edavtyan.materialplayer.components.player.ShuffleMode;
import com.edavtyan.materialplayer.utils.ArtProvider;

import lombok.Setter;

public class NowPlayingModel
		implements NowPlayingMvp.Model,
				   PlayerMvp.Player.OnNewTrackListener,
				   PlayerMvp.Player.OnPlayPauseListener {

	private final Context context;
	private final ArtProvider artProvider;

	private PlayerService service;

	private @Setter OnModelBoundListener onModelBoundListener;
	private @Setter OnNewTrackListener onNewTrackListener;
	private @Setter OnPlayPauseListener onPlayPauseListener;

	public NowPlayingModel(Context context, ArtProvider artProvider) {
		this.context = context;
		this.artProvider = artProvider;
	}

	@Override
	public void bind() {
		Intent intent = new Intent(context, PlayerService.class);
		context.bindService(intent, this, Context.BIND_AUTO_CREATE);
	}

	@Override
	public void unbind() {
		service.getPlayer().removeOnNewTrackListener(this);
		context.unbindService(this);
	}

	@Override
	public RepeatMode getRepeatMode() {
		return service.getPlayer().getRepeatMode();
	}

	@Override
	public ShuffleMode getShuffleMode() {
		return service.getPlayer().getShuffleMode();
	}

	@Override
	public void toggleRepeatMode() {
		service.getPlayer().toggleRepeatMode();
	}

	@Override
	public void toggleShuffleMode() {
		service.getPlayer().toggleShuffleMode();
	}

	@Override
	public boolean isPlaying() {
		return service.getPlayer().isPlaying();
	}

	@Override
	public void playPause() {
		service.getPlayer().playPause();
	}

	@Override
	public void rewind() {
		service.getPlayer().rewind();
	}

	@Override
	public void fastForward() {
		service.getPlayer().playNext();
	}

	@Override
	public void seek(int positionMS) {
		service.getPlayer().setPosition(positionMS);
	}

	@Override
	public int getDuration() {
		return (int) service.getPlayer().getCurrentTrack().getDuration();
	}

	@Override
	public int getPosition() {
		return (int) service.getPlayer().getPosition();
	}

	@Override
	public String getTitle() {
		return service.getPlayer().getCurrentTrack().getTitle();
	}

	@Override
	public String getArtist() {
		return service.getPlayer().getCurrentTrack().getArtistTitle();
	}

	@Override
	public String getAlbum() {
		return service.getPlayer().getCurrentTrack().getAlbumTitle();
	}

	@Override
	public Bitmap getArt() {
		return artProvider.load(service.getPlayer().getCurrentTrack());
	}

	@Override
	public void onServiceConnected(ComponentName name, IBinder binder) {
		service = ((PlayerService.PlayerBinder) binder).getService();
		service.getPlayer().setOnNewTrackListener(this);
		service.getPlayer().setOnPlayPauseListener(this);
		if (onModelBoundListener != null) {
			onModelBoundListener.onModelBound();
		}
	}

	@Override
	public void onServiceDisconnected(ComponentName name) {
	}

	@Override
	public void onNewTrack() {
		if (onNewTrackListener != null) onNewTrackListener.onNewTrack();
	}

	@Override
	public void onPlayPause() {
		if (onPlayPauseListener != null) onPlayPauseListener.onPlayPause();
	}
}
