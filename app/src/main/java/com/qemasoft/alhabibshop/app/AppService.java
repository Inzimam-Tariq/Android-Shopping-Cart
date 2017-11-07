package com.qemasoft.alhabibshop.app;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

/**
 * Created by Inzimam on 03-Nov-17.
 */

public class AppService extends IntentService {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */

    public AppService(String name) {
        super(name);
    }

    public AppService() {
        super("AppService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
// Gets data from the incoming Intent
        String dataString = intent != null ? intent.getDataString() : null;

        // Do work here, based on the contents of dataString
    }
}
