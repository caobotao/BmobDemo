package com.cbt.bmob;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import cn.bmob.push.PushConstants;

/**
 * Created by caobotao on 16/1/1.
 */
public class PushReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String notificationContent = "";
        if(intent.getAction().equals(PushConstants.ACTION_MESSAGE)){
            Log.d("bmob", "客户端收到推送内容："+intent.getStringExtra(PushConstants.EXTRA_PUSH_MESSAGE_STRING));
            String msg = intent.getStringExtra(PushConstants.EXTRA_PUSH_MESSAGE_STRING);
            JSONTokener tokener = new JSONTokener(msg);
            try {
                JSONObject object = (JSONObject) tokener.nextValue();
                notificationContent = object.getString("alert");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

            NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            Notification notification = new Notification(R.mipmap.ic_launcher, "TestBmob", System.currentTimeMillis());
            notification.setLatestEventInfo(context,"BmobTest",notificationContent,pendingIntent);
            manager.notify(R.mipmap.ic_launcher,notification);

        }
    }
}
