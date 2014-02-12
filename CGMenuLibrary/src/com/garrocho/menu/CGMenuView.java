package com.garrocho.menu;


import java.util.ArrayList;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class CGMenuView extends LinearLayout {

	private ListView cgListView;
	private View cgOutsideView;
	private ICGMenuCallback callback;
	private static ArrayList<CGMenuItem> menuItems;

	public CGMenuView(Context context) {
		super(context);
		load();
	}

	public CGMenuView(Context context, AttributeSet attrs) {
		super(context, attrs);
		load();
	}

	private void load(){
		if(isInEditMode()) return;
		inflateLayout();		
		initUi();
	}


	private void inflateLayout(){
		try{
			LayoutInflater.from(getContext()).inflate(R.layout.cg_menu, this, true);
		} catch(Exception e){
		}
	}

	private void initUi(){
		cgListView = (ListView) findViewById(R.id.cg_listview);
		cgOutsideView = (View) findViewById(R.id.cg_outside_view);
		cgListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				if(callback != null)					
					callback.CGMenuItemClick(menuItems.get(position).id);
			}
		});
	}

	public void setMenuClickCallback(ICGMenuCallback callback){
		this.callback = callback;
	}

	public void setMenuItems(ArrayList<CGMenuItem> mitens){
		menuItems = mitens;
		
		if(menuItems != null && menuItems.size() > 0)
		{
			cgListView.setAdapter(new Adapter());
		}
	}

	public void setBackgroundResource(int resource){
		cgListView.setBackgroundResource(resource);
	}

	public void showMenu(){
		cgOutsideView.setVisibility(View.VISIBLE);	
		cgListView.setVisibility(View.VISIBLE);	
	}

	public boolean isMenuVisible(){		
		return cgOutsideView.getVisibility() == View.VISIBLE;		
	}

	@Override 
	protected void onRestoreInstanceState(Parcelable state)	{
		SavedState ss = (SavedState)state;
		super.onRestoreInstanceState(ss.getSuperState());
		if (ss.bShowMenu)
			showMenu();
	}

	@Override 
	protected Parcelable onSaveInstanceState()	{
		Parcelable superState = super.onSaveInstanceState();
		SavedState ss = new SavedState(superState);
		ss.bShowMenu = isMenuVisible();
		return ss;
	}

	static class SavedState extends BaseSavedState {
		boolean bShowMenu;
		
		SavedState(Parcelable superState) {
			super(superState);
		}
		
		private SavedState(Parcel in) {
			super(in);
			bShowMenu = (in.readInt() == 1);
		}

		@Override
		public void writeToParcel(Parcel out, int flags) {
			super.writeToParcel(out, flags);
			out.writeInt(bShowMenu ? 1 : 0);
		}

		public static final Parcelable.Creator<SavedState> CREATOR
		= new Parcelable.Creator<SavedState>() {
			public SavedState createFromParcel(Parcel in) {
				return new SavedState(in);
			}

			public SavedState[] newArray(int size) {
				return new SavedState[size];
			}
		};
	}

	public class CGMenuItem{
		public int id;
		public String text;
		public int icon1;
		public int icon2;
		public CGMenuItem(int id, String text, int icon1, int icon2) {
			super();
			this.id = id;
			this.text = text;
			this.icon1 = icon1;
			this.icon2 = icon2;
		}
	}
	
	public CGMenuItem getNovoItem(int id, String text, int icon1, int icon2) {
		return new CGMenuItem(id, text, icon1, icon2);
	}

	private class Adapter extends BaseAdapter {
		private LayoutInflater inflater;

		public Adapter(){
			inflater = LayoutInflater.from(getContext());
		}

		@Override
		public int getCount() {
			return menuItems.size();
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			final ViewHolder holder;

			if(convertView == null || convertView instanceof TextView){
				convertView = inflater.inflate(R.layout.cg_item, null);

				holder = new ViewHolder();
				holder.image1 = (ImageView) convertView.findViewById(R.id.cg_item_icon);
				holder.image2 = (ImageView) convertView.findViewById(R.id.cg_item_draw);
				holder.text = (TextView) convertView.findViewById(R.id.cg_item_text);

				convertView.setTag(holder);

			} else {

				holder = (ViewHolder) convertView.getTag();
			}

			holder.image1.setImageResource(menuItems.get(position).icon1);
			holder.image2.setImageResource(menuItems.get(position).icon2);
			holder.text.setText(menuItems.get(position).text);
			return convertView;
		}

		class ViewHolder {
			TextView text;
			ImageView image1;
			ImageView image2;
		}
	}
}
