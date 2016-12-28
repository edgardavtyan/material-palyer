package com.edavtyan.custompreference.base;

import android.content.Context;

import java.util.List;

public abstract class ListModel extends BaseModel {
	public ListModel(Context context) {
		super(context);
	}

	public abstract String getSummary();

	public abstract List<CharSequence> getEntries();

	public abstract List<CharSequence> getValues();

	public boolean getPrefSelectedAtIndex(int position) {
		return getValues().get(position).equals(getCurrentPreference());
	}

	public void savePref(CharSequence value) {
		sharedPrefs.edit().putString(getKey(), value.toString()).apply();
	}
}
