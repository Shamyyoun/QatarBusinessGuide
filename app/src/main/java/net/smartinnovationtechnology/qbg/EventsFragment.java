package net.smartinnovationtechnology.qbg;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageButton;

import adapters.EventsAdapter;
import datamodels.Event;
import json.EventsHandler;
import json.JsonReader;
import utils.InternetUtil;
import views.ProgressFragment;
import views.SlideExpandableListView;

public class EventsFragment extends ProgressFragment implements View.OnClickListener {
    public static final String TAG = "events";

    private ServicesCategoriesActivity activity;

    // actionbar views
    private ImageButton buttonBack;
    private ImageButton buttonSearch;

    // main views
    private SlideExpandableListView listEvents;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        initComponents(rootView);
        return rootView;
    }

    /**
     * overriden method, used to return layout resource id
     */
    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_events;
    }

    /**
     * method used to initialize components
     */
    private void initComponents(View rootView) {
        // customize actionbar
        buttonBack = (ImageButton) rootView.findViewById(R.id.button_back);
        buttonSearch = (ImageButton) rootView.findViewById(R.id.button_search);
        buttonBack.setOnClickListener(this);
        buttonSearch.setOnClickListener(this);

        // init main views
        activity = (ServicesCategoriesActivity) getActivity();
        listEvents = (SlideExpandableListView) rootView.findViewById(R.id.list_events);

        // customize tab bar
        activity.setTabBarColor(ServicesCategoriesActivity.COLOR_RED);

        // get cached content
        String response = AppController.getEventsResponse(activity);
        // check it
        if (response != null) {
            // valid >> handle it
            EventsHandler handler = new EventsHandler(response);
            Event[] events = handler.handle();

            // check data length
            if (events.length == 0) {
                // show empty msg
                showEmpty(R.string.no_events);
            } else {
                // set events adapter
                setEventsAdapter(events);

                showMain();
            }

            // update content from server if possible
            new EventsTask(true).execute();
        } else {
            // load it from server
            new EventsTask(false).execute();
        }
    }

    /**
     * method, used to set events adapter
     */
    private void setEventsAdapter(final Event[] events) {
        final EventsAdapter adapter = new EventsAdapter(activity, R.layout.list_events_item,
                R.layout.list_events_sub_item, events);
        listEvents.setAdapter(adapter);

        // add listeners
        listEvents.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                boolean expand = !events[groupPosition].isExpanded();

                // expand or collapse group view
                if (expand) {
                    listEvents.expandGroupWithAnimation(groupPosition);
                } else {
                    listEvents.collapseGroupWithAnimation(groupPosition);
                }

                // update in adapter
                adapter.expandItem(groupPosition, expand);

                return true;
            }
        });
    }

    /**
     * overriden method, used to handle click actions
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_back:
                activity.onBackPressed();
                break;

            case R.id.button_search:
                Intent intent = new Intent(activity, SearchActivity.class);
                startActivity(intent);
                activity.overridePendingTransition(R.anim.child_enter, R.anim.parent_exit);
                break;
        }
    }

    /**
     * overriden method, used to refresh content
     */
    @Override
    protected void onRefresh() {
        new EventsTask(false).execute();
    }

    /**
     * sub class, used to load events from server
     */
    private class EventsTask extends AsyncTask<Void, Void, Void> {
        private boolean updateInBackground;
        private String response;

        private EventsTask(boolean updateInBackground) {
            this.updateInBackground = updateInBackground;
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
            String url = AppController.END_POINT + "/events.php";
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
            EventsHandler handler = new EventsHandler(response);
            Event[] events = handler.handle();

            // check handling operation result
            if (events == null) {
                if (!updateInBackground)
                    // show error msg
                    showError(R.string.connection_error);

                return;
            }

            // cache it
            AppController.updateEventsResponse(activity, response);

            // check data length
            if (events.length == 0) {
                // show empty msg
                showEmpty(R.string.no_events);

                return;
            }

            // set listview adapter
            setEventsAdapter(events);

            showMain();
        }
    }
}
