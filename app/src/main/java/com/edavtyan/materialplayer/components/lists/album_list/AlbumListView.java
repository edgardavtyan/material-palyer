package com.edavtyan.materialplayer.components.lists.album_list;

import com.edavtyan.materialplayer.components.lists.lib.ListView;

public interface AlbumListView extends ListView {
	void gotoAlbumDetail(int albumId);
}