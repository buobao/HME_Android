package com.hme.turman.ui.receiver;

import android.content.Context;

import com.orhanobut.logger.Logger;

import io.rong.push.notification.PushMessageReceiver;
import io.rong.push.notification.PushNotificationMessage;

/**
 * Created by diaoqf on 2016/11/8.
 */

public class RcReceiver extends PushMessageReceiver {
    @Override
    public boolean onNotificationMessageArrived(Context context, PushNotificationMessage pushNotificationMessage) {
        Logger.i("onNotificationMessageArrived");
        return false;
    }

    @Override
    public boolean onNotificationMessageClicked(Context context, PushNotificationMessage pushNotificationMessage) {
        Logger.i("onNotificationMessageClicked");
        return false;
    }
}
