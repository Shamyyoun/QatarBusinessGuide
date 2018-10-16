package net.smartinnovationtechnology.qbg;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.jpardogo.listbuddies.lib.views.ListBuddiesLayout;

import adapters.CompaniesAdapter;
import datamodels.Company;
import datamodels.Constants;
import datamodels.SubSector;
import json.CompaniesHandler;
import json.JsonReader;
import utils.InternetUtil;
import views.ProgressActivity;


public class CompaniesActivity extends ProgressActivity {
    private SubSector subSector;
    private ListBuddiesLayout listBuddiesLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initComponents();
    }

    /**
     * method, used to initialize components
     */
    private void initComponents() {
        subSector = (SubSector) getIntent().getSerializableExtra(Constants.KEY_SUB_SECTOR);
        listBuddiesLayout = (ListBuddiesLayout) findViewById(R.id.listBuddiesLayout);

        // customize actionbar
        setActionBarTitle(AppController.getInstance(getApplicationContext()).getLang().equals("en") ? subSector.getNameEn() : subSector.getName());
        setActionbarIcon(R.drawable.ic_back_white);
        setActionBarIconClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        // customize list buddies
        listBuddiesLayout.setGap(9);
        listBuddiesLayout.setDividerHeight(9);

        // get cached sectors
        String response = AppController.getCompaniesResponse(this, subSector.getId());
        // check it
        if (response != null) {
            // valid >> handle it
            CompaniesHandler handler = new CompaniesHandler(response);
            Company[] companies = handler.handle();

            // check companies length
            if (companies.length == 0) {
                // show empty msg
                showEmpty(R.string.no_companies);
            } else {
                // set adapters
                setAdapters(companies);

                showMain();
            }

            // update companies from server if possible
            new CompaniesTask(true).execute();
        } else {
            // load it from server
            new CompaniesTask(false).execute();
        }
    }

    /**
     * method, used to split and set list buddies adapter
     */
    private void setAdapters(final Company[] companies) {
        // ---split companies array into two arrays---
        final Company[] companies1;
        final Company[] companies2;
        int length = companies.length;
        int companies1Start = 0;
        int companies1End;
        int companies2Start;
        int companies2End;

        // check if array contain just one item
        if (companies.length == 1) {
            // copy it in the two arrays
            companies1 = new Company[]{companies[0]};
            companies2 = new Company[]{companies[0]};
        } else {
            // --more than one item--
            // check odd or even
            if ((length % 2) == 0) {
                // even
                companies1End = length / 2;
                companies2Start = (length / 2) - 1;
                companies2End = length;
            } else {
                // odd
                companies1End = (length / 2) + 1;
                companies2Start = length / 2 + 1;
                companies2End = length;
            }

            // split
            companies1 = new Company[companies1End - companies1Start];
            for (int i = 0; i < companies1.length; i++) {
                companies1[i] = companies[companies1Start];
                companies1Start++;
            }

            companies2 = new Company[companies2End - companies2Start];
            for (int i = 0; i < companies2.length; i++) {
                companies2[i] = companies[companies2Start];
                companies2Start++;
            }
        }

        // create adapters
        CompaniesAdapter adapter1 = new CompaniesAdapter(this, R.layout.list_companies_item,
                companies1, R.dimen.grid_item_height1);
        CompaniesAdapter adapter2 = new CompaniesAdapter(this, R.layout.list_companies_item,
                companies2, R.dimen.grid_item_height2);

        // set adapters
        listBuddiesLayout.setAdapters(adapter1, adapter2);

        // add item click listener
        listBuddiesLayout.setOnItemClickListener(new ListBuddiesLayout.OnBuddyItemClickListener() {
            @Override
            public void onBuddyItemClicked(AdapterView<?> adapterView, View view, int i, int i2, long l) {
                // goto company activity
                Intent intent = new Intent(CompaniesActivity.this, CompanyActivity.class);
                intent.putExtra(Constants.KEY_COMPANY, i == 0 ? companies1[i2] : companies2[i2]);
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
        return R.layout.activity_companies;
    }

    /**
     * overriden method, used to refresh content
     */
    @Override
    protected void onRefresh() {
        new CompaniesTask(false).execute();
    }


    /**
     * sub class, used to load companies from server
     */
    private class CompaniesTask extends AsyncTask<Void, Void, Void> {
        private boolean updateInBackground;
        private CompaniesActivity activity;
        private String response;

        private CompaniesTask(boolean updateInBackground) {
            this.updateInBackground = updateInBackground;
            activity = CompaniesActivity.this;
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
            String url = AppController.END_POINT + "/companies.php?subsec_id=" + subSector.getId();
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
            CompaniesHandler handler = new CompaniesHandler(response);
            Company[] companies = handler.handle();

            // check handling operation result
            if (companies == null) {
                if (!updateInBackground)
                    // show error msg
                    showError(R.string.connection_error);

                return;
            }

            // cache response
            AppController.updateCompaniesResponse(activity, subSector.getId(), response);

            // check if data is empty
            if (companies.length == 0) {
                // show empty msg
                showEmpty(R.string.no_companies);

                return;
            }

            // set adapters
            setAdapters(companies);

            showMain();
        }
    }
}
