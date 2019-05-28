package com.example.genius_plaza_sample.View;

import android.content.Context;
import android.util.Log;

import com.example.genius_plaza_sample.Config.Config;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class Httpclientclass {

    public static final String BASE_URL= Config.userURL;


    final static int DEFAULT_TIMEOUT = 20 * 2000;
    private static AsyncHttpClient client = new AsyncHttpClient(true,80,443);


    public static void get(Context context, String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.get(getAbsoluteUrl(url), params, responseHandler);
        client.setTimeout(DEFAULT_TIMEOUT);
        client.setConnectTimeout(20*2000);
        client.setResponseTimeout(20*2000);
        //client.addHeader("ACCESSTOKEN","893984a1168ef84a");
        //hari lal =893984a1168ef84a
        //deepa ma'm =991f0b94948f85f8
        Log.i("get URL",getAbsoluteUrl(url));
        Log.d("get URL",getAbsoluteUrl(url));

    }

    public static void post(Context context,String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.post(getAbsoluteUrl(url), params, responseHandler);
        //client.addHeader("ACCESSTOKEN","Z#9((d#v#l0p3r");
        Log.i("post URL", getAbsoluteUrl(url));

    }

    private static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }
}
