package net.smartinnovationtechnology.qbg;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.lukedeighton.wheelview.WheelItem;
import com.lukedeighton.wheelview.WheelView;
import com.lukedeighton.wheelview.adapter.WheelAdapter;

import java.util.Arrays;
import java.util.Collections;

import datamodels.Constants;
import datamodels.Sector;
import json.JsonReader;
import json.SectorsHandler;
import utils.InternetUtil;
import utils.ScreenUtil;
import views.ProgressActivity;


public class BusinessSectorActivity extends ProgressActivity {
    private WheelView wheelView;
    private ImageView imageRadialPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initComponents();
    }

    /**
     * method, used to initialize components
     */
    private void initComponents() {
        // customize actionbar
        setActionBarTitle(R.string.business_sector);
        setActionbarIcon(R.drawable.ic_back_white);
        setActionBarIconClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        // init views
        wheelView = (WheelView) findViewById(R.id.wheelView);
        imageRadialPath = (ImageView) findViewById(R.id.image_radialPath);

        // customize wheel menu
        wheelView.setTypeface(Typeface.createFromAsset(getAssets(), "una_font.ttf"));
        int wheelRadius = (int) (ScreenUtil.getWidth(this) / 1.9);
        wheelView.setWheelRadius(wheelRadius);
        ViewGroup.LayoutParams layoutParams = imageRadialPath.getLayoutParams();
        layoutParams.width = wheelRadius - (int) getResources().getDimension(R.dimen.radial_menu_item_radius);

        // get cached sectors
        String response = AppController.getSectorsResponse(this);
        // check it
        if (response != null) {
            // valid >> handle it
            SectorsHandler handler = new SectorsHandler(response);
            Sector[] sectors = handler.handle();

            // check sectors length
            if (sectors.length == 0) {
                // show empty msg
                showEmpty(R.string.no_sectors);
            } else {
                // set adapter
                setWheelViewAdapter(sectors);
                showMain();
            }

            // update sectors from server if possible
            new SectorsTask(true).execute();
        } else {
            // load it from server
            new SectorsTask(false).execute();
        }
    }

    /**
     * method, used to set wheel view adapter & listener
     */
    private void setWheelViewAdapter(final Sector[] sectors) {
        // reverse item to be displayed from right to left in ARABIC lang
        if (AppController.getInstance(getApplicationContext()).getLang().equals("ar"))
            Collections.reverse(Arrays.asList(sectors));

        // set adapter
        wheelView.setWheelItemCount(sectors.length < 8 ? sectors.length * 2 : sectors.length);
        wheelView.setAdapter(new WheelAdapter() {
            @Override
            public WheelItem getMenuItem(int position) {
                Sector sector = sectors[position];
                String sectorName = AppController.getInstance(getApplicationContext()).getLang().equals("en") ? sector.getNameEn() : sector.getName();
                WheelItem wheelItem = new WheelItem(sectorName, getResources().getDrawable(R.drawable.radial_menu_icon));
                return wheelItem;
            }

            @Override
            public int getCount() {
                return sectors.length;
            }
        });
        wheelView.setOnWheelItemClickListener(new WheelView.OnWheelItemClickListener() {
            @Override
            public void onWheelItemClick(WheelView parent, int position, boolean isSelected) {
                // goto sub sectors activity
                Intent intent = new Intent(BusinessSectorActivity.this, SubSectorsActivity.class);
                intent.putExtra(Constants.KEY_SECTOR, sectors[position]);
                startActivity(intent);
                overridePendingTransition(R.anim.child_enter, R.anim.parent_exit);
            }
        });
    }

    /**
     * overriden method, used to return activity layout resource
     */
    @Override
    protected int getLayoutResource() {
        return R.layout.activity_business_sector;
    }

    /**
     * overriden method, used to refresh content
     */
    @Override
    protected void onRefresh() {
        new SectorsTask(false).execute();
    }

    /**
     * sub class, used to load sectors from server
     */
    private class SectorsTask extends AsyncTask<Void, Void, Void> {
        private boolean updateInBackground;
        private BusinessSectorActivity activity;
        private String response;

        private SectorsTask(boolean updateInBackground) {
            this.updateInBackground = updateInBackground;
            activity = BusinessSectorActivity.this;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            // check internet connection
            if (!InternetUtil.isConnected(activity)) {
                if (!updateInBackground)
                    // show error
                    showError(R.string.no_internet_connection);

                cancel(true);
                return;
            }

            showProgress();
        }

        @Override
        protected Void doInBackground(Void... params) {
            String url = AppController.END_POINT + "/sectors.php";
            JsonReader jsonReader = new JsonReader(url);
            response = jsonReader.sendRequest();

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            // validate response
            if (response == null) {
                if (!updateInBackground)
                    // show error msg
                    showError(R.string.connection_error);

                return;
            }

            // ---response is valid---
            // handle it
            SectorsHandler handler = new SectorsHandler(response);
            Sector[] sectors = handler.handle();

            // check handling operation result
            if (sectors == null) {
                if (!updateInBackground)
                    // show error msg
                    showError(R.string.connection_error);

                return;
            }

            // cache response
            AppController.updateSectorsResponse(activity, response);

            // check if data is empty
            if (sectors.length == 0) {
                // show empty msg
                showEmpty(R.string.no_sectors);

                return;
            }

            // set adapter
            setWheelViewAdapter(sectors);
            showMain();
        }
    }
}
