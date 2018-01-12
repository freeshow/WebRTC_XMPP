package com.example.data;

import android.content.Context;

import org.jivesoftware.smack.tcp.XMPPTCPConnection;

/**
 * Created by songxitang on 2015/11/10.
 * 用于获取和设置GlobalData中数据的简便方法。其实不用这个类也可以，完全可以用GlobalData中的方法。
 * 但是设置这个类，使获取数据更方便、简单、易懂。
 */
public class DataWarehouse {
    public static GlobalData getGlobalData(Context ctx)
    {
        return (GlobalData) ctx.getApplicationContext();
    }

    public static XMPPTCPConnection getXMPPTCPConnection(Context ctx)
    {
        return getGlobalData(ctx).xmpptcpConnection;
    }

    public static void setXMPPTCPConnection(Context ctx,XMPPTCPConnection conn)
    {
        getGlobalData(ctx).xmpptcpConnection = conn;
    }
}
