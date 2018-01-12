package com.example.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.adapter.FaceListAdapter;
import com.example.common.Const;

public class FaceActivity extends AppCompatActivity implements Const{
    private FaceListAdapter mFaceListAdapter;
    private GridView mGridViewFaces;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_face);

        mGridViewFaces = (GridView)findViewById(R.id.gridview_faces);
        mFaceListAdapter = new FaceListAdapter(this);
        mGridViewFaces.setAdapter(mFaceListAdapter);
        mGridViewFaces.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                //传递选中表情的位置到ChatActivity。
                intent.putExtra(KEY_FACE_ID,position+1);
                setResult(1,intent);
                finish();
            }
        });
    }

    public void onClick_Close(View view) {
        finish();
    }
}
