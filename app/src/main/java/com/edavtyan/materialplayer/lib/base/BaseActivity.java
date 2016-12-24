package com.edavtyan.materialplayer.lib.base;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.edavtyan.materialplayer.App;
import com.edavtyan.materialplayer.R;
import com.edavtyan.materialplayer.components.Navigator;
import com.edavtyan.materialplayer.lib.prefs.AdvancedSharedPrefs;
import com.edavtyan.materialplayer.lib.testable.TestableActivity;
import com.edavtyan.materialplayer.utils.ThemeUtils;

public abstract class BaseActivity
		extends TestableActivity
		implements SharedPreferences.OnSharedPreferenceChangeListener {

	private ThemeUtils themeUtils;
	private App app;
	private Navigator navigator;

	public abstract int getLayoutId();

	@SuppressWarnings("unchecked")
	public <T> T findView(int id) {
		return (T) findViewById(id);
	}

	/* AppCompatActivity */

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		app = (App) getApplicationContext();
		BaseFactory factory = app.getBaseFactory(this);

		themeUtils = factory.provideThemeUtils();
		themeUtils.setTheme(this);

		navigator = factory.provideNavigator();

		AdvancedSharedPrefs prefs = factory.providePrefs();
		prefs.registerOnSharedPreferenceChangeListener(this);

		setContentView(getLayoutId());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = app.getSdkFactory().createMenuInflater(this);
		inflater.inflate(R.menu.menu_base, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_effects:
			navigator.gotoAudioEffects();
			return true;
		case R.id.menu_settings:
			navigator.gotoSettings();
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	/* SharedPreferences.OnSharedPreferenceChangeListener */

	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
		themeUtils.setThemeAndRecreate(this, key);
	}
}