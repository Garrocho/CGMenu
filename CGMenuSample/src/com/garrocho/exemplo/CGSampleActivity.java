package com.garrocho.exemplo;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;

import com.garrocho.menu.CGMenuView;
import com.garrocho.menu.CGMenuView.CGMenuItem;
import com.garrocho.menu.ICGMenuCallback;

public class CGSampleActivity extends Activity implements ICGMenuCallback {

	private CGMenuView cgView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		cgView = (CGMenuView) findViewById(R.id.main_CGMenuView);
		cgView.setMenuClickCallback(this);
		cgView.showMenu();
		
		ArrayList<CGMenuItem> menuItems = new ArrayList<CGMenuItem>();
		
		for(int i=0; i <= 15; i++) {
			if (i%2 == 0)
				menuItems.add(cgView.getNovoItem(i, "CGSample " + i, android.R.drawable.star_on, android.R.drawable.presence_away));
			else
				menuItems.add(cgView.getNovoItem(i, "CGSample " + i, android.R.drawable.star_on, 0));
		}
		cgView.setMenuItems(menuItems);
	}

	@Override
	public void CGMenuItemClick(int itemId) {
	}
}