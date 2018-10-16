package net.smartinnovationtechnology.qbg;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import adapters.SubSectorsAdapter;
import datamodels.Constants;
import datamodels.Sector;
import datamodels.SubSector;
import json.JsonReader;
import json.SubSectorsHandler;
import utils.InternetUtil;
import views.ProgressActivity;


public class SubSectorsActivity extends ProgressActivity {
    private Sector sector;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initComponents();
    }

    /**
     * method, used to initialize components
     */
    private void initComponents() {
        sector = (Sector) getIntent().getSerializableExtra(Constants.KEY_SECTOR);
        listView = (ListView) findViewById(R.id.listView);

        // customize actionbar
        setActionBarTitle(AppController.getInstance(getApplicationContext()).getLang().equals("en") ? sector.getNameEn() : sector.getName());
        setActionbarIcon(R.drawable.ic_back_white);
        setActionBarIconClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        // get cached sectors
        String response = AppController.getSubSectorsResponse(this, sector.getId());
        // check it
        if (response != null) {
            // valid >> handle it
            SubSectorsHandler handler = new SubSectorsHandler(response);
            SubSector[] subSectors = handler.handle();

            // check sub sectors length
            if (subSectors.length == 0) {
                // show empty msg
                showEmpty(R.string.no_sub_sectors);
            } else {
                // set adapter
                setAdapter(subSectors);
                showMain();
            }

            // update sub sectors from server if possible
            new SubSectorsTask(true).execute();
        } else {
            // load it from server
            new SubSectorsTask(false).execute();
        }
    }

    /**
     * method, used to set listview adapter and item click listener
     */
    private void setAdapter(final SubSector[] subSectors) {
        SubSectorsAdapter adapter = new SubSectorsAdapter(this, R.layout.list_subsectors_item, subSectors);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // goto companies activity
                Intent intent = new Intent(SubSectorsActivity.this, CompaniesActivity.class);
                intent.putExtra(Constants.KEY_SUB_SECTOR, subSectors[position]);
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
        return R.layout.activity_sub_sectors;
    }

    /**
     * overriden method, used to refresh content
     */
    @Override
    protected void onRefresh() {
        new SubSectorsTask(false).execute();
    }


    /**
     * sub class, used to load sub sectors from server
     */
    private class SubSectorsTask extends AsyncTask<Void, Void, Void> {
        private boolean updateInBackground;
        private SubSectorsActivity activity;
        private String response;

        private SubSectorsTask(boolean updateInBackground) {
            this.updateInBackground = updateInBackground;
            activity = SubSectorsActivity.this;
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
            String url = AppController.END_POINT + "/subsectors.php?sec_id=" + sector.getId();
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
            SubSectorsHandler handler = new SubSectorsHandler(response);
            final SubSector[] subSectors = handler.handle();

            // check handling operation result
            if (subSectors == null) {
                if (!updateInBackground)
                    // show error msg
                    showError(R.string.connection_error);

                return;
            }

            // cache response
            AppController.updateSubSectorsResponse(activity, sector.getId(), response);

            // check if data is empty
            if (subSectors.length == 0) {
                // show empty msg
                showEmpty(R.string.no_sub_sectors);

                return;
            }

            // set adapter
            setAdapter(subSectors);

            showMain();
        }
    }
}
