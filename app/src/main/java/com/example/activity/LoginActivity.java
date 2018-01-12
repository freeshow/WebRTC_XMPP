package com.example.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.common.Const;
import com.example.common.Storage;
import com.example.common.XMPPUtil;
import com.example.data.DataWarehouse;
import com.example.data.LoginData;

public class LoginActivity extends AppCompatActivity implements Const{
    private EditText mEditTextUsername;
    private EditText mEditTextPassword;
    private CheckBox mCheckBoxSavePassword;
    private CheckBox mCheckBoxAutoLogin;

    private LoginData mLoginDate;

    private Handler handler;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        context = this;
        mEditTextUsername = (EditText) findViewById(R.id.username);
        mEditTextPassword = (EditText) findViewById(R.id.password);
        mCheckBoxSavePassword = (CheckBox) findViewById(R.id.savePassword);
        mCheckBoxAutoLogin = (CheckBox) findViewById(R.id.autoLogin);

        mLoginDate = DataWarehouse.getGlobalData(this).loginData;

        handler = new Handler();

        // 当第二次登陆时，将第一次存储的登陆信息取出来，自动放在登陆界面的输入信息的
        //地方，不用再手动填写了。
        mLoginDate.userName = Storage.getString(this,KEY_USERNAME);
        mLoginDate.passWord = Storage.getString(this,KEY_PASSWORD);
        mLoginDate.isSavePassword = Storage.getBoolean(this, KEY_SAVE_PASSWOED);
        mLoginDate.isAutoLogin = Storage.getBoolean(this,KEY_AUTO_LOGIN);

        //第二次登陆，将登录信息自动填写上
        mEditTextUsername.setText(mLoginDate.userName);
        mCheckBoxSavePassword.setChecked(mLoginDate.isSavePassword);
        mCheckBoxAutoLogin.setChecked(mLoginDate.isAutoLogin);
        //如果选择了保存密码，第二次登陆，则将保存密码自动选上
        if (mLoginDate.isSavePassword)
        {
            mEditTextPassword.setText(mLoginDate.passWord);
        }
        //如果选择了自动登录，则第二次启动App则自动登录
        if (mLoginDate.isAutoLogin)
        {
            onClickLogin(null);
        }
    }

    //登录按钮的单击事件方法
    public void onClickLogin(View view) {
        //将登录信息保存到全局对象中，当然也可以从SharedPreferences中读取，
        // 但是比较麻烦，所以保存到全局变量中。
        mLoginDate.userName = mEditTextUsername.getText().toString();
        mLoginDate.passWord = mEditTextPassword.getText().toString();
        mLoginDate.isSavePassword = mCheckBoxSavePassword.isChecked();
        mLoginDate.isAutoLogin = mCheckBoxAutoLogin.isChecked();

        if (mLoginDate.userName.equals(null) || mLoginDate.userName.equals(""))
        {
            Toast.makeText(LoginActivity.this,"请输入用户名",Toast.LENGTH_SHORT).show();
            return;
        }
        if (mLoginDate.passWord.equals(null) || mLoginDate.passWord.equals(""))
        {
            Toast.makeText(LoginActivity.this,"请输入密码",Toast.LENGTH_SHORT).show();
            return;
        }
        Log.e("username",mLoginDate.userName);
        Log.e("password",mLoginDate.passWord);

        //存储登录信息。
        /*为了存储的这些数据可以在别的地方应用，故需要将Key定义为常量，以便其他地方引用。
        *将Key保存在接口中以便其它类引用。
        * 当然，也可以定义在一个类中，但是需要将Key定义为静态变量，比较麻烦，故将其定义在接口中。
        * 将Key保存在接口Const中，所有的Key名称以Key_开头。
        */
        Storage.putString(this,KEY_USERNAME,mLoginDate.userName);
        Storage.putBollean(this, KEY_SAVE_PASSWOED, mLoginDate.isSavePassword);
        Storage.putBollean(this,KEY_AUTO_LOGIN,mLoginDate.isAutoLogin);

        //当点击了保存密码，则将密码存储。
        if (mLoginDate.isSavePassword)
        {
            Storage.putString(this,KEY_PASSWORD,mLoginDate.passWord);
        }

        //新版本中不允许在主线程中直接访问网络。故登录服务器需要另起一个线程。
        new Thread(new Runnable() {
            @Override
            public void run() {
                //登录成功
                if (XMPPUtil.login(context,mLoginDate.userName,mLoginDate.passWord))
//                if (login())
                {
                    //在非线程中需要使用第二种：
//                    Intent intent = new Intent(this,MainActivity.class);
                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                else //登录失败
                {
                    //不能再非主线程中显示Toast,因为Toast是在主线程中显示的，故需要使用handler.
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(LoginActivity.this,"登录失败，请检查用户名和密码的正确性",Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        }).start();
    }

    public void onClickRegister(View view) {
        Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
        startActivity(intent);
    }
}
