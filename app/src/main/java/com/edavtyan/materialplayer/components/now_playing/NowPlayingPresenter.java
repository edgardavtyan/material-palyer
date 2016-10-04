package com.edavtyan.materialplayer.components.now_playing;

import com.edavtyan.materialplayer.utils.Timer;

public class NowPlayingPresenter implements NowPlayingMvp.Presenter {
	private static final int SEEK_INTERVAL = 1000;

	private final NowPlayingMvp.Model model;
	private final NowPlayingMvp.View view;

	private final Timer seekbarTimer;

	public NowPlayingPresenter(NowPlayingMvp.Model model, NowPlayingMvp.View view) {
		this.model = model;
		this.model.setOnModelBoundListener(this);
		this.view = view;

		//TODO: find out how to DI this
		seekbarTimer = new Timer(SEEK_INTERVAL, () -> {
			view.getSeekbar().setTrackPosition(model.getPosition());
			view.getSeekbar().setTrackPositionText(model.getPosition());
		});
	}

	@Override
	public void bind() {
		model.bind();
	}

	@Override
	public void unbind() {
		model.unbind();
	}

	@Override
	public void onFabClick() {
		view.gotoPlaylistScreen();
	}

	@Override
	public void onModelBound() {
		view.getControls().setShuffleState(model.getShuffleMode());
		view.getControls().setRepeatState(model.getRepeatMode());
		view.getControls().setPlayPauseState(model.getPlayPauseMode());
		updateViewInfo();
	}

	@Override
	public void onShuffleClick() {
		model.toggleShuffleMode();
		view.getControls().setShuffleState(model.getShuffleMode());
	}

	@Override
	public void onRewindClick() {
		model.rewind();
		updateViewInfo();
	}

	@Override
	public void onPlayPauseClick() {
		model.togglePlayPauseMode();
		view.getControls().setPlayPauseState(model.getPlayPauseMode());

		switch (model.getPlayPauseMode()) {
		case PAUSED:
			seekbarTimer.stop();
			break;
		case PLAYING:
			seekbarTimer.run();
			break;
		}
	}

	@Override
	public void onFastForwardClick() {
		model.fastForward();
		updateViewInfo();
	}

	@Override
	public void onRepeatClick() {
		model.toggleRepeatMode();
		view.getControls().setRepeatState(model.getRepeatMode());
	}

	@Override
	public void onTrackSeekChanged(int progress) {
		view.getSeekbar().setTrackPositionText(progress);
	}

	@Override
	public void onTrackSeekStop(int position) {
		model.seek(position);
	}

	private void updateViewInfo() {
		view.getInfo().setTitle(model.getTitle());
		view.getInfo().setInfo(model.getArtist(), model.getAlbum());
		view.getSeekbar().setTrackDuration(model.getDuration());
		view.getSeekbar().setTrackDurationText(model.getDuration());
		view.getArt().setArt(model.getArt());
		seekbarTimer.run();
	}
}
