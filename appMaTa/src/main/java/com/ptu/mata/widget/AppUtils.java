package com.ptu.mata.widget;

import android.content.Context;
import android.net.ConnectivityManager;
import android.widget.Toast;

/**
 * Created by Mr-Hei on 2016/10/29.
 */

public class AppUtils {
    //判断手机网络状态
    public static boolean netState(Context context) {
        ConnectivityManager cManager = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
        if (cManager.getActiveNetworkInfo() == null) {
            Toast.makeText(context, "当前没网络哦亲！", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
