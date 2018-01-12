package com.example.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.jivesoftware.smack.roster.Roster;

public class AddFriendActivity extends ParentActivity {
    private EditText mEditTextFriendUsername;
    private EditText mEditTextFriendNickname;
    private Button mButtonAddFrient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);

        mEditTextFriendUsername = (EditText) findViewById(R.id.frendUsername);
        mEditTextFriendNickname = (EditText) findViewById(R.id.nickname);
        mButtonAddFrient = (Button) findViewById(R.id.addFriend);


    }

    public void onClickAddFriend(View view) {
        String account = mEditTextFriendUsername.getText().toString().trim();
        String nickname = mEditTextFriendNickname.getText().toString().trim();

        if("".equals(account))
        {
            Toast.makeText(this,"请输入账号",Toast.LENGTH_SHORT).show();
            return;
        }

        if ("".equals(nickname))
        {
            nickname = account;
        }

        try {
            //添加好友
            Roster.getInstanceFor(connection).createEntry(account, nickname, null);
            Intent intent = new Intent();
            intent.putExtra("friendName",account);
            intent.putExtra("nickname",nickname);
            setResult(1, intent);
            Toast.makeText(this,"添加好友成功！",Toast.LENGTH_SHORT).show();
            finish();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            Toast.makeText(this,"添加好友失败("+e.getMessage()+")",Toast.LENGTH_SHORT).show();
        }
    }
}
