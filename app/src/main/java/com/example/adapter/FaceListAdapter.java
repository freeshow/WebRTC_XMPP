package com.example.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.activity.R;
import com.example.common.Const;
import com.example.common.Util;

/**
 * Created by songxitang on 2015/11/24.
 */
public class FaceListAdapter extends ParentAdapter implements Const{
    private int mFaceCount; //表情图像总数

    public FaceListAdapter(Context mContext) {
        super(mContext);
        mFaceCount = calculateFaceCount();
    }

    //计算表情的数量
    private int calculateFaceCount()
    {
        int i = 0;
        while (true)
        {
            i++;
            String faceName = FACE_PREFIX + i;

            try {
                //Java反射: 查看资源文件中是否有名为faceName的资源。
                R.drawable.class.getField(faceName);
            }
            catch (Exception e)
            {
                break;
            }
        }
        return i-1;
    }

    public int getFaceResourceID(int position)
    {
        //position是从0开始的。
        position++;

        if (position > mFaceCount)
        {
            return -1;
        }

        try {
            return Util.getResourceIDFromName(R.drawable.class,FACE_PREFIX + position);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return -1;
        }

    }

    @Override
    public int getCount() {
        return mFaceCount;
    }

    @Override
    public Object getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null)
        {
            convertView = mLayoutInfater.inflate(R.layout.face,null);
        }
        ImageView face = (ImageView) convertView;
        face.setImageResource(getFaceResourceID(position));
        return convertView;
    }
}
