package com.example.data;

import android.app.Application;

import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by songxitang on 2015/11/10.
 * 用于保存app中所有全局变量
 */
public class GlobalData extends Application{
    public XMPPTCPConnection xmpptcpConnection;
    //记录登录信息
    public LoginData loginData = new LoginData();
    //记录当前正在聊天的用户
    public Set<String> chatUsers = new TreeSet<>();
}
