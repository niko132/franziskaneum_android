package de.franziskaneum;

import android.content.Context;
import android.content.Intent;

import de.franziskaneum.settings.SettingsManager;
import de.franziskaneum.vplan.VPlanNotificationManager;

public class BroadcastReceiver extends android.content.BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction() != null) {
            String action = intent.getAction();

            if (action.equals(VPlanNotificationManager.ACTION_NOTIFICATION_DELETED)) {
                SettingsManager.getInstance().setVPlanNotificationDeleted(true);
            }
        }
    }
}