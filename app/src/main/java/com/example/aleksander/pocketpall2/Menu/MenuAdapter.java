package com.example.aleksander.pocketpall2.Menu;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aleksander.pocketpall2.R;

import java.util.ArrayList;

public class MenuAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Object> menuItems;
    private static final int TYPE_DIVIDER = 0;
    private static final int TYPE_ITEM = 1;
    private LayoutInflater inflater;

    public MenuAdapter(Context context, ArrayList<Object> navDrawerItems) {
        this.context = context;
        this.menuItems = navDrawerItems;
        this.inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEnabled(int position) {
        return (getItemViewType(position) == TYPE_ITEM);
    }

    @Override
    public int getCount() {
        return menuItems.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (getItem(position) instanceof MenuItom) {
            return TYPE_ITEM;
        }

        return TYPE_DIVIDER;
    }

    @Override
    public Object getItem(int position) {
        return menuItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int type = getItemViewType(position);
        if (convertView == null) {
            switch (type) {
                case TYPE_ITEM:
                    convertView = inflater.inflate(R.layout.menu_list_item, null);
                    break;
                case TYPE_DIVIDER:
                    convertView = inflater.inflate(R.layout.row_header_menu, parent, false);
                    break;
            }
        }

        switch (type) {
            case TYPE_ITEM:
                ImageView imgIcon = (ImageView) convertView.findViewById(R.id.icon);
                TextView txtTitle = (TextView) convertView.findViewById(R.id.title);

                imgIcon.setImageResource(((MenuItom)menuItems.get(position)).getIcon());
                txtTitle.setText(((MenuItom)menuItems.get(position)).getTitle());
                break;
            case TYPE_DIVIDER:
                TextView month = (TextView)convertView.findViewById(R.id.month);
                String titleString = (String)getItem(position);
                month.setText(titleString);
                break;
        }


        return convertView;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

}
