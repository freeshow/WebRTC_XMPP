package com.example.common;

import android.content.Context;
import android.net.SSLCertificateSocketFactory;
import android.util.Log;

import com.example.data.DataWarehouse;
import com.example.ssl.MemorizingTrustManager;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.ConnectionListener;
import org.jivesoftware.smack.SASLAuthentication;
import org.jivesoftware.smack.SmackConfiguration;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.sasl.provided.SASLDigestMD5Mechanism;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;

/**
 * Created by songxitang on 2015/11/10.
 */
public class XMPPUtil {

        private static String host = "222.195.151.245";
//    private static String host = "114.215.152.18";
    private static int port = 5222;
    //    private static int port = 5223;
    private static String serviceName = "songxitang-pc";
//    private static String serviceName = "114.215.152.18";

    //连接服务器
    public static XMPPTCPConnection getXMPPConnection(Context ctx) {

        SmackConfiguration.DEBUG = true;
        XMPPTCPConnectionConfiguration.Builder configBuilder = XMPPTCPConnectionConfiguration.builder();
        //设置服务器IP地址
        configBuilder.setHost(host);
        //设置服务器端口
        configBuilder.setPort(port);
        //设置服务器名称
        configBuilder.setServiceName(serviceName);
        //设置开启调试
        configBuilder.setDebuggerEnabled(true);
        //设置开启压缩，可以节省流量
        configBuilder.setCompressionEnabled(true);
        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            MemorizingTrustManager mtm = new MemorizingTrustManager(ctx);
            sc.init(null, new X509TrustManager[]{mtm}, new java.security.SecureRandom());
            configBuilder.setCustomSSLContext(sc);
            configBuilder.setHostnameVerifier(
                    mtm.wrapHostnameVerifier(new org.apache.http.conn.ssl.StrictHostnameVerifier()));
        } catch (NoSuchAlgorithmException|KeyManagementException e) {
            e.printStackTrace();
        }
        XMPPTCPConnection connection = new XMPPTCPConnection(configBuilder.build());

        connection.addConnectionListener(new ConnectionListener() {
            @Override
            public void connected(XMPPConnection xmppConnection) {
                Log.e("connect","connected");
            }

            @Override
            public void authenticated(XMPPConnection xmppConnection, boolean b) {
                Log.e("connect","authenticated");
            }

            @Override
            public void connectionClosed() {
                Log.e("connect","connectionClosed");
            }

            @Override
            public void connectionClosedOnError(Exception e) {
                Log.e("connect","connectionClosedOnError");
            }

            @Override
            public void reconnectionSuccessful() {
                Log.e("connect","reconnectionSuccessful");
            }

            @Override
            public void reconnectingIn(int i) {
                Log.e("connect","reconnectionIn: "+i);
            }

            @Override
            public void reconnectionFailed(Exception e) {
                Log.e("connect","reconnectionFailed");
            }
        });

        try {
            connection.connect();
        } catch (SmackException | IOException | XMPPException e) {
            e.printStackTrace();
            return null;
        }
        return connection;
    }


    public static boolean login(Context ctx, String username, String password) {
        XMPPTCPConnection connection = getXMPPConnection(ctx);
        if (connection == null)
        {
            Log.e("login","connection == null");
            return false;
        }
        try {
            connection.login(username, password);
            DataWarehouse.setXMPPTCPConnection(ctx,connection);
            return true;
        } catch (XMPPException|SmackException|IOException e) {
            e.printStackTrace();
            Log.e("login","login failure");
            return false;
        }
    }
}
