package com.minsal.dtic.sinavec.AdminDevice;


import android.app.admin.DeviceAdminReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.UserHandle;

public class DeviceAdminReceiverMalaria extends DeviceAdminReceiver {

    @Override
    public void onEnabled(Context context, Intent intent) {
        super.onEnabled(context, intent);
    }

    @Override
    public void onDisabled(Context context, Intent intent) {
        super.onDisabled(context, intent);
    }

    @Override
    public void onPasswordChanged(Context context, Intent intent, UserHandle user) {
        super.onPasswordChanged(context, intent, user);
    }

    @Override
    public void onPasswordFailed(Context context, Intent intent, UserHandle user) {
        super.onPasswordFailed(context, intent, user);
    }

    @Override
    public void onPasswordSucceeded(Context context, Intent intent, UserHandle user) {
        super.onPasswordSucceeded(context, intent, user);
    }
}
