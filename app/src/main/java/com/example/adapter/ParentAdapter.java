package com.example.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * Created by songxitang on 2015/11/24.
 * 将Adapter中需要共同要到的数据和方法放到ParentAdapter中。
 * ChatListAdapter和FriendListAdapter创建早于ParentAdapter,故没有继承ParentAdapter
 */
public class ParentAdapter extends BaseAdapter {
    protected Context mContext;
    protected LayoutInflater mLayoutInfater;

    public ParentAdapter(Context mContext) {
        this.mContext = mContext;
        mLayoutInfater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return 0;
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
        return null;
    }
}
