package com.edavtyan.materialplayer.lib.base;

import android.annotation.SuppressLint;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.edavtyan.materialplayer.R;
import com.edavtyan.materialplayer.components.Navigator;
import com.edavtyan.materialplayer.components.SdkFactory;
import com.edavtyan.materialplayer.lib.prefs.AdvancedSharedPrefs;
import com.edavtyan.materialplayer.testlib.tests.ActivityTest;
import com.edavtyan.materialplayer.utils.ThemeUtils;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SuppressLint("StaticFieldLeak")
public class BaseActivityTest extends ActivityTest {
	private static TestBaseActivity activity;

	public static class TestBaseActivity extends BaseActivity {
		@Override
		public int getLayoutId() {
			return R.layout.test_layout;
		}
	}

	private static ThemeUtils themeUtils;
	private static AdvancedSharedPrefs prefs;
	private static MenuInflater menuInflater;
	private static Navigator navigator;

	@Override
	public void beforeEach() {
		super.beforeEach();

		if (activity == null) {
			themeUtils = mock(ThemeUtils.class);
			prefs = mock(AdvancedSharedPrefs.class);
			navigator = mock(Navigator.class);

			BaseFactory factory = mock(BaseFactory.class);
			when(factory.getThemeUtils()).thenReturn(themeUtils);
			when(factory.getPrefs()).thenReturn(prefs);
			when(factory.getNavigator()).thenReturn(navigator);

			app.setBaseFactory(factory);

			menuInflater = mock(MenuInflater.class);
			SdkFactory sdkFactory = mock(SdkFactory.class);
			when(sdkFactory.createMenuInflater(any())).thenReturn(menuInflater);

			app.setSdkFactory(sdkFactory);

			activity = startActivity(TestBaseActivity.class);
		}
	}

	@Test
	public void onCreate_setContentView() {
		assertThat(activity.findViewById(R.id.test_layout_root)).isNotNull();
	}

	@Test
	public void onCreate_setTheme() {
		verify(themeUtils).setTheme(activity);
	}

	@Test
	public void onCreate_registerOnSharedPreferencesChangedListener() {
		verify(prefs).registerOnSharedPreferenceChangeListener(activity);
	}

	@Test
	public void onCreateOptionsMenu_inflateMenu() {
		Menu menu = mock(Menu.class);
		activity.onCreateOptionsMenu(menu);
		verify(menuInflater).inflate(R.menu.menu_base, menu);
	}

	@Test
	public void onOptionsItemSelected_AudioEffectsMenuClick_gotoAudioEffectsActivity() {
		callOnOptionsItemSelectedWithMenuId(R.id.menu_effects);
		verify(navigator).gotoAudioEffects();
	}

	@Test
	public void onOptionsItemSelected_SettingsMenuClick_gotoPrefActivity() {
		callOnOptionsItemSelectedWithMenuId(R.id.menu_settings);
		verify(navigator).gotoSettings();
	}

	@Test
	public void onOptionsItemSelected_otherMenuItem_returnFalse() {
		assertThat(callOnOptionsItemSelectedWithMenuId(0)).isFalse();
	}

	@Test
	public void onSharedPreferencesChanged_setTheme() {
		activity.onSharedPreferenceChanged(null, "key");
		verify(themeUtils).setThemeAndRecreate(activity, "key");
	}

	private boolean callOnOptionsItemSelectedWithMenuId(int menuId) {
		MenuItem menuItem = mock(MenuItem.class);
		when(menuItem.getItemId()).thenReturn(menuId);
		return activity.onOptionsItemSelected(menuItem);
	}
}
