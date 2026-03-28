package com.github.exformatgames.pacman.app;

import com.github.exformatgames.pacman.UI.Layout;
import java.util.HashMap;
import java.util.Map;

public class LayoutManager {

	private final Map<String, Layout> layoutMap = new HashMap<>();

	private Layout activeLayout;

	public void show (String name) {
		Layout layout = layoutMap.get(name);

		if (layout != null) {
			if (activeLayout != null) {
				activeLayout.hide();
			}
			
			layout.show();

			activeLayout = layout;
		}
	}

	public void add (Layout layout, String name) {
		layoutMap.put(name, layout);
		layout.hide();
	}
	
	public boolean thisIsActiveLayout (Layout layout) {
		return activeLayout == layout;
	}
}
