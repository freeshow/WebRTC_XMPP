package com.example.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.common.XMPPUtil;

import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.IQ;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smackx.iqregister.AccountManager;
import org.jivesoftware.smackx.iqregister.packet.Registration;

import java.io.IOException;

public class RegisterActivity extends AppCompatActivity {
    private EditText mEditTextUsername;
    private EditText mEditTextPassword;
    private EditText mEditTextRePassword;
    private Button mButtonRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mEditTextUsername = (EditText) findViewById(R.id.userRegister);
        mEditTextPassword = (EditText) findViewById(R.id.passwordInput);
        mEditTextRePassword = (EditText) findViewById(R.id.passwordReInput);
        mButtonRegister = (Button) findViewById(R.id.register);
    }

    //用于校验用户注册的输入信息
    private boolean verify()
    {
        if ("".equals(mEditTextUsername.getText().toString().trim()))
        {
            Toast.makeText(this,"请输入用户名",Toast.LENGTH_SHORT).show();
            return false;
        }

        if ("".equals(mEditTextPassword.getText().toString().trim()))
        {
            Toast.makeText(this,"请输入密码",Toast.LENGTH_SHORT).show();
            return false;
        }

        if ("".equals(mEditTextRePassword.getText().toString().trim()))
        {
            Toast.makeText(this,"请确认密码",Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!mEditTextPassword.getText().toString().equals(mEditTextRePassword.getText().toString()))
        {
            Toast.makeText(this,"密码输入不一致",Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    public void onClickRegister(View view) {
        if (!verify())
        {
            return;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                registerUser(mEditTextUsername.getText().toString(),mEditTextPassword.getText().toString());
            }
        }).start();
    }

    public void onClickClose(View view) {
        finish();
    }

    //注册新用户
    public void createAccount(String username,String password)
    {
        XMPPTCPConnection connection = XMPPUtil.getXMPPConnection(this);

        //设置注册相关的信息
        Registration reg = new Registration();
        reg.setType(IQ.Type.set);
    }
    //注册用户
    public Boolean registerUser(String username,String password)
    {
        try {
            XMPPTCPConnection connection = XMPPUtil.getXMPPConnection(this);
            if (connection == null)
            {
                try {
                    connection.connect();
                } catch (SmackException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (XMPPException e) {
                    e.printStackTrace();
                }
                Log.e("connect","连接服务器失败!");
            }

            AccountManager.getInstance(connection).createAccount(username,password);
            finish();
            return true;
        }
        catch (SmackException.NoResponseException | XMPPException.XMPPErrorException |
                SmackException.NotConnectedException e)
        {
            Log.e("register", "注册失败！");
            return false;
        }
    }
}
