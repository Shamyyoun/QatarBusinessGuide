package net.smartinnovationtechnology.qbg;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;

import database.ServiceDAO;
import datamodels.DataLoader;


public class SplashActivity extends ActionBarActivity {
    private static final int SPLASH_TIME = 1 * 1000;
    private Handler handler;
    private Runnable runnable;

    // flags used to close splash and goto main activity when possible
    private boolean splashEnded;
    private boolean dataLoaded;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // init splash handler and runnable
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                splashEnded = true;
                startMainActivity();
            }
        };

        // start splash
        handler.postDelayed(runnable, SPLASH_TIME);

        // load data
        new DataLoaderTask().execute();
    }

    /**
     * sub class, used to load data for the first time
     */
    private class DataLoaderTask extends AsyncTask<Void, Void, Void> {
        private boolean firstTime;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            // check to add service items to DB or not
            ServiceDAO itemDAO = new ServiceDAO(getApplicationContext());
            itemDAO.open();
            firstTime = !itemDAO.hasItems();
            itemDAO.close();
        }

        @Override
        protected Void doInBackground(Void... params) {
            if (firstTime) {
                DataLoader.storeServices(SplashActivity.this.getApplicationContext());
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            dataLoaded = true;
            startMainActivity();
        }
    }

    /**
     * method, used to start main activity if Okay
     */
    private void startMainActivity() {
        if (splashEnded && dataLoaded) {
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);
            SplashActivity.this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        }
    }
}
