package com.edavtyan.materialplayer.utils;

import android.view.View;
import android.view.ViewGroup;

public class ViewUtils {
	public static void setSize(View view, int width, int height) {
		ViewGroup.LayoutParams params = view.getLayoutParams();
		params.width = width;
		params.height = height;
		view.setLayoutParams(params);
		view.requestLayout();
		view.invalidate();
	}

	public static int[] getLocationOnScreen(View view) {
		int location[] = new int[2];
		view.getLocationOnScreen(location);
		return location;
	}
}
