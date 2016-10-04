package com.edavtyan.materialplayer.components.now_playing.models;

import com.edavtyan.materialplayer.R;
import com.edavtyan.materialplayer.components.now_playing.NowPlayingActivity;
import com.edavtyan.materialplayer.components.now_playing.NowPlayingMvp;
import com.edavtyan.materialplayer.lib.BaseTest;
import com.edavtyan.materialplayer.lib.testable.TestableImageButton;
import com.edavtyan.materialplayer.utils.AppColors;

import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class NowPlayingControlsTest extends BaseTest {
	private NowPlayingMvp.View.Controls controls;
	private NowPlayingMvp.Presenter presenter;

	private AppColors colors;

	private TestableImageButton shuffleButton;
	private TestableImageButton rewindButton;
	private TestableImageButton playPauseButton;
	private TestableImageButton fastForwardButton;
	private TestableImageButton repeatButton;

	@Override
	public void beforeEach() {
		super.beforeEach();

		shuffleButton = spy(new TestableImageButton(context));
		shuffleButton.setId(R.id.shuffle);

		rewindButton = spy(new TestableImageButton(context));
		rewindButton.setId(R.id.rewind);

		playPauseButton = spy(new TestableImageButton(context));
		playPauseButton.setId(R.id.playPause);

		fastForwardButton = spy(new TestableImageButton(context));
		fastForwardButton.setId(R.id.fastForward);

		repeatButton = spy(new TestableImageButton(context));
		repeatButton.setId(R.id.repeat);

		NowPlayingActivity activity = mock(NowPlayingActivity.class);
		when(activity.findView(R.id.shuffle)).thenReturn(shuffleButton);
		when(activity.findView(R.id.rewind)).thenReturn(rewindButton);
		when(activity.findView(R.id.playPause)).thenReturn(playPauseButton);
		when(activity.findView(R.id.fastForward)).thenReturn(fastForwardButton);
		when(activity.findView(R.id.repeat)).thenReturn(repeatButton);

		colors = new AppColors(context);
		presenter = mock(NowPlayingMvp.Presenter.class);

		controls = new NowPlayingControls(activity, presenter, colors);
	}

	@Test
	public void shuffleButtonClicked_callPresenter() {
		shuffleButton.performClick();
		verify(presenter).onShuffleClick();
	}

	@Test
	public void rewindButtonClicked_callPresenter() {
		rewindButton.performClick();
		verify(presenter).onRewindClick();
	}

	@Test
	public void playPauseButtonClicked_callPresenter() {
		playPauseButton.performClick();
		verify(presenter).onPlayPauseClick();
	}

	@Test
	public void fastForwardButtonClicked_callPresenter() {
		fastForwardButton.performClick();
		verify(presenter).onFastForwardClick();
	}

	@Test
	public void repeatButtonClicked_callPresenter() {
		repeatButton.performClick();
		verify(presenter).onRepeatClick();
	}

	@Test
	public void setShuffleState_enabled_setShuffleButtonColorFilterToAccent() {
		controls.setShuffleState(NowPlayingMvp.ShuffleState.ENABLED);
		verify(shuffleButton).setColorFilterNonFinal(colors.accent);
	}

	@Test
	public void setShuffleState_disabled_setShuffleButtonColorFilterToContrast() {
		controls.setShuffleState(NowPlayingMvp.ShuffleState.DISABLED);
		verify(shuffleButton).setColorFilterNonFinal(colors.textPrimary);
	}

	@Test
	public void setRepeatState_repeatAll_setRepeatButtonColorFilterAndIcon() {
		controls.setRepeatState(NowPlayingMvp.RepeatState.REPEAT_ALL);
		verify(repeatButton).setColorFilterNonFinal(colors.accent);
		verify(repeatButton).setImageResource(R.drawable.ic_repeat);
	}

	@Test
	public void setRepeatState_repeatOne_setRepeatButtonColorFilterAndIcon() {
		controls.setRepeatState(NowPlayingMvp.RepeatState.REPEAT_ONE);
		verify(repeatButton).setColorFilterNonFinal(colors.accent);
		verify(repeatButton).setImageResource(R.drawable.ic_repeat_one);
	}

	@Test
	public void setRepeatState_disabled_setRepeatButtonColorFilterAndIcon() {
		controls.setRepeatState(NowPlayingMvp.RepeatState.DISABLED);
		verify(repeatButton).setColorFilterNonFinal(colors.textPrimary);
		verify(repeatButton).setImageResource(R.drawable.ic_repeat);
	}

	@Test
	public void setPlayPauseState_playing_setPlayPauseIconToPause() {
		controls.setPlayPauseState(NowPlayingMvp.PlayPauseState.PLAYING);
		verify(playPauseButton).setImageResource(R.drawable.ic_pause);
	}

	@Test
	public void setPlayPauseState_paused_setPlayPauseIconToPlay() {
		controls.setPlayPauseState(NowPlayingMvp.PlayPauseState.PAUSED);
		verify(playPauseButton).setImageResource(R.drawable.ic_play);
	}
}
