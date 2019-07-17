package com.namaltechnologysolutions.bunny.bookbank.Language_Customization;

import android.app.Application;
import android.content.res.Configuration;

/**
 * Created by Bunny on 12/31/2018.
 */

public class Custom_Language extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        initLanguage();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        initLanguage();
    }

    private void initLanguage() {
        String ul = Language_Helper.getUserLanguage(this);
        // if null the language doesn't need to be changed as the user has not chosen one.
        if (ul != null) {
            Language_Helper.updateLanguage(this, ul);
        }
    }
}
