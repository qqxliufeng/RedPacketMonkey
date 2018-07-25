package com.android.ql.lf.redpacketmonkey.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;


import com.android.ql.lf.redpacketmonkey.utils.Constants;
import com.android.ql.lf.redpacketmonkey.utils.PreferenceUtils;

public class RedPacketBroadCastReceiver extends BroadcastReceiver {

    public static int count = 0;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (Constants.RED_PACKET_ACTION.equals(intent.getAction())) {
            PreferenceUtils.setPrefInt(context, "count", count);
            count++;
            Log.e("TAG", "count is --> " + PreferenceUtils.getPrefInt(context, "count", 0));
        }
    }
}
