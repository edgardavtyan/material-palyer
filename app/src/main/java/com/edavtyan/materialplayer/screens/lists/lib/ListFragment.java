package com.edavtyan.materialplayer.screens.lists.lib;

import com.edavtyan.materialplayer.R;
import com.edavtyan.materialplayer.modular.fragment.ModularFragment;

public class ListFragment extends ModularFragment implements ListView {
	private ListFragmentModule listFragmentModule;

	protected void initListView(ListPresenter presenter, ListAdapter adapter) {
		listFragmentModule = new ListFragmentModule(this, adapter, presenter);
		addModule(listFragmentModule);
	}

	@Override
	protected int getLayoutId() {
		return R.layout.fragment_list;
	}

	@Override
	public void notifyDataSetChanged() {
		listFragmentModule.notifyDataSetChanged();
	}
}