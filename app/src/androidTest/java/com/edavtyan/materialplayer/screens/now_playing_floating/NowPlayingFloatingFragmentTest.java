package com.edavtyan.materialplayer.screens.now_playing_floating;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.edavtyan.materialplayer.R;
import com.edavtyan.materialplayer.screens.Navigator;
import com.edavtyan.materialplayer.testlib.tests.FragmentTest2;
import com.edavtyan.materialplayer.utils.UtilsFactory;

import org.junit.Test;

import static com.edavtyan.materialplayer.testlib.assertions.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.RETURNS_MOCKS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SuppressLint("StaticFieldLeak")
public class NowPlayingFloatingFragmentTest extends FragmentTest2 {
	private static NowPlayingFloatingComponent component;

	public static class TestNowPlayingFloatingFragment extends NowPlayingFloatingFragment {
		@Override
		protected NowPlayingFloatingComponent getComponent() {
			return component;
		}
	}

	private NowPlayingFloatingPresenter presenter;
	private Navigator navigator;
	private TestNowPlayingFloatingFragment fragment;

	private TextView infoView;
	private TextView titleView;
	private ImageView artView;
	private ImageButton playPauseView;
	private LinearLayout infoWrapper;
	private LinearLayout mainWrapper;

	@Override
	@SuppressWarnings("ConstantConditions")
	public void beforeEach() {
		super.beforeEach();

		presenter = mock(NowPlayingFloatingPresenter.class);
		NowPlayingFloatingFactory mainModule = mock(NowPlayingFloatingFactory.class, RETURNS_MOCKS);
		when(mainModule.providePresenter(any(), any())).thenReturn(presenter);

		navigator = mock(Navigator.class);
		UtilsFactory utilsFactory = mock(UtilsFactory.class, RETURNS_MOCKS);
		when(utilsFactory.provideNavigator(any())).thenReturn(navigator);

		component = DaggerNowPlayingFloatingComponent
				.builder()
				.nowPlayingFloatingFactory(mainModule)
				.utilsFactory(utilsFactory)
				.build();

		fragment = new TestNowPlayingFloatingFragment();
		initFragment(fragment);

		infoView = (TextView) fragment.getView().findViewById(R.id.info);
		titleView = (TextView) fragment.getView().findViewById(R.id.title);
		artView = (ImageView) fragment.getView().findViewById(R.id.art);
		playPauseView = (ImageButton) fragment.getView().findViewById(R.id.play_pause);
		infoWrapper = (LinearLayout) fragment.getView().findViewById(R.id.info_container);
		mainWrapper = (LinearLayout) fragment.getView().findViewById(R.id.container);
	}

	@Test
	public void onStart_callPresenter() {
		verify(presenter).onCreate();
	}

	@Test
	public void onStop_callPresenter() {
		fragment.onStop();
		fragment = null; // force recreation of fragment
		verify(presenter).onDestroy();
	}

	@Test
	public void getLayoutId_returnCorrectId() {
		assertThat(fragment.getLayoutId()).isEqualTo(R.layout.fragment_nowplaying_floating);
	}

	@Test
	@SuppressLint("SetTextI18n")
	public void setTrackTitle_setTitleViewText() {
		runOnUiThread(() -> fragment.setTrackTitle("title"));
		assertThat(titleView.getText()).isEqualTo("title");
	}

	@SuppressLint("SetTextI18n")
	@Test
	public void setTrackInfo_setInfoViewTextWithPattern() {
		runOnUiThread(() -> fragment.setTrackInfo("artist", "album"));
		assertThat(infoView.getText()).isEqualTo("artist \u2022 album");
	}

	@Test
	public void setArt_bitmapIsNotNull_setBitmap() {
		Bitmap art = Bitmap.createBitmap(1, 1, Bitmap.Config.ALPHA_8);
		runOnUiThread(() -> fragment.setArt(art));
		assertThat(artView).hasScaledImageBitmap(art, 44);
	}

	@Test
	public void setArt_bitmapIsNull_setFallbackImage() {
		runOnUiThread(() -> fragment.setArt(null));
		assertThat(artView).hasImageResource(R.drawable.fallback_cover);
	}

	@Test
	public void setIsPlaying_true_setIconToPause() {
		runOnUiThread(() -> fragment.setIsPlaying(true));
		assertThat(playPauseView).hasImageResource(R.drawable.ic_pause);
	}

	@Test
	public void setIsPlaying_false_setIconToPlay() {
		runOnUiThread(() -> fragment.setIsPlaying(false));
		assertThat(playPauseView).hasImageResource(R.drawable.ic_play);
	}

	@Test
	public void setIsVisible_true_setVisibilityToVisible() {
		runOnUiThread(() -> fragment.setIsVisible(true));
		assertThat(mainWrapper.getVisibility()).isEqualTo(View.VISIBLE);
	}

	@Test
	public void onClickListener_artClicked_called() {
		runOnUiThread(artView::performClick);
		verify(presenter).onViewClick();
	}

	@Test
	public void onClickListener_infoWrapperClicked_called() {
		runOnUiThread(infoWrapper::performClick);
		verify(presenter).onViewClick();
	}

	@Test
	public void onPlayPauseClick_playPauseClicked_called() {
		runOnUiThread(playPauseView::performClick);
		verify(presenter).onPlayPauseClick();
	}

	@Test
	public void gotoNowPlaying_callNavigator() {
		fragment.gotoNowPlaying();
		verify(navigator).gotoNowPlaying();
	}
}