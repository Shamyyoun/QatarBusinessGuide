package net.smartinnovationtechnology.qbg;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.devspark.appmsg.AppMsg;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import adapters.SearchAdapter;
import datamodels.Constants;
import datamodels.SearchableItem;
import json.JsonReader;
import json.SearchableItemsHandler;
import utils.InternetUtil;
import utils.ViewUtil;
import views.BaseActivity;


public class SearchActivity extends BaseActivity {
    // ProgressView views
    private View mainView;
    private View progressView;
    private View errorView;
    private View emptyView;

    // other ProgressView views
    private TextView textError;
    private ImageButton buttonRefresh;
    private TextView textEmpty;

    // main screen objects
    private ImageButton buttonSearch;
    private EditText textSearch;
    private Spinner spinnerSearchBy;
    private ListView listResults;
    private SearchAdapter adapter;

    // footer views
    private View loadMoreFooter;
    private Button buttonLoadMore;
    private ProgressBar progressBar;

    private List<AsyncTask> tasks; // used to hold running tasks

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        initComponents();
    }

    /**
     * method, used to initialize components
     */
    private void initComponents() {
        // customize actionbar
        setActionBarTitle(R.string.search);
        removeOpenSearchIcon();
        setActionbarIcon(R.drawable.ic_back_white);
        setActionBarIconClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        // init views
        mainView = findViewById(R.id.view_main);
        progressView = findViewById(R.id.view_progress);
        errorView = findViewById(R.id.view_error);
        emptyView = findViewById(R.id.view_empty);

        textError = (TextView) errorView.findViewById(R.id.text_error);
        buttonRefresh = (ImageButton) errorView.findViewById(R.id.button_refresh);
        textEmpty = (TextView) emptyView.findViewById(R.id.text_empty);

        buttonSearch = (ImageButton) findViewById(R.id.button_search);
        textSearch = (EditText) findViewById(R.id.text_search);
        spinnerSearchBy = (Spinner) findViewById(R.id.spinner_searchBy);
        listResults = (ListView) findViewById(R.id.list_results);
        LayoutInflater inflater = ((LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE));
        loadMoreFooter = inflater.inflate(R.layout.list_search_footer, null);
        buttonLoadMore = (Button) loadMoreFooter.findViewById(R.id.button_loadMore);
        progressBar = (ProgressBar) loadMoreFooter.findViewById(R.id.progressBar);

        tasks = new ArrayList<AsyncTask>();

        // customize textSearch
        String color = String.format("#%06X", 0xFFFFFF & getResources().getColor(R.color.light_gray2));
        textSearch.setHint(Html.fromHtml("<font color='" + color + "'>" + getString(R.string.search_hint) + "</font>"));

        // customize spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.search_by,
                R.layout.spinner_selected_item);
        adapter.setDropDownViewResource(R.layout.spinner_item);
        spinnerSearchBy.setAdapter(adapter);

        // add listeners
        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // validate inputs
                String searchText = textSearch.getText().toString();
                searchText = searchText.trim();
                if (searchText.isEmpty()) {
                    // show error
                    textSearch.setText("");
                    textSearch.setError(getString(R.string.enter_search_text));

                    return;
                }

                // hide keyboard
                InputMethodManager imm = (InputMethodManager) SearchActivity.this.getSystemService(
                        Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(textSearch.getWindowToken(), 0);

                // run search task
                new SearchTask(searchText, spinnerSearchBy.getSelectedItemPosition()).execute();
            }
        });
    }

    /**
     * sub class, used to get search results from server
     */
    private class SearchTask extends AsyncTask<Void, Void, Void> {
        private String searchText;
        private int searchType;
        private SearchActivity activity;

        private String response;
        private String searchCategory;

        private SearchTask(final String searchText, final int searchType) {
            this.searchText = searchText;
            this.searchType = searchType;
            activity = SearchActivity.this;

            // format search text
            try {
                this.searchText = URLEncoder.encode(this.searchText, "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            // get appropriate search by category
            searchCategory = null;
            switch (searchType) {
                case Constants.SEARCHABLE_TYPE_SECTOR:
                    searchCategory = "sec";
                    break;

                case Constants.SEARCHABLE_TYPE_SUB_SECTOR:
                    searchCategory = "subsec";
                    break;

                case Constants.SEARCHABLE_TYPE_COMPANY:
                    searchCategory = "com";
                    break;
            }

            // add listener to refresh button, to re run this task if required
            buttonRefresh.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new SearchTask(searchText, searchType).execute();
                }
            });

            cancelRunningTasks(); // cancel running tasks before run this
            tasks.add(this); // hold  reference to this task, to destroy it if required
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            // check internet connection
            if (!InternetUtil.isConnected(activity)) {
                // show error
                showError(R.string.no_internet_connection);

                cancel(true);
                return;
            }

            showProgress();
        }

        @Override
        protected Void doInBackground(Void... params) {
            String url = AppController.END_POINT + "/search.php?cat=" + searchCategory + "&name=" + searchText;
            JsonReader jsonReader = new JsonReader(url);
            response = jsonReader.sendRequest();

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            tasks.remove(this); // remove from running tasks

            // check if searched by place
            if (searchType == Constants.SEARCHABLE_TYPE_PLACE) {
                // show empty msg
                showEmpty(R.string.no_search_results);

                return;
            }

            // validate response
            if (response == null) {
                // show error msg
                showError(R.string.connection_error);

                return;
            }

            if (response.equals(Constants.JSON_ERROR_MSG)) {
                // show error msg
                showError(R.string.connection_error);

                return;
            }

            // ---response is valid---
            // handle it
            SearchableItemsHandler handler = new SearchableItemsHandler(response, searchType);
            final List<SearchableItem> searchResults = handler.handle();

            // check handling operation result
            if (searchResults == null) {
                // show error msg
                showError(R.string.connection_error);

                return;
            }

            // check if data is empty
            if (searchResults.size() == 0) {
                // show empty msg
                showEmpty(R.string.no_search_results);

                return;
            }

            // set adapter
            adapter = new SearchAdapter(activity, R.layout.list_search_item, searchResults);
            listResults.setAdapter(adapter);

            // add item click listener
            listResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    // goto some activity according to search type
                    Intent intent = null;
                    switch (searchType) {
                        case Constants.SEARCHABLE_TYPE_SECTOR:
                            intent = new Intent(activity, SubSectorsActivity.class);
                            intent.putExtra(Constants.KEY_SECTOR, searchResults.get(position));
                            break;

                        case Constants.SEARCHABLE_TYPE_SUB_SECTOR:
                            intent = new Intent(activity, CompaniesActivity.class);
                            intent.putExtra(Constants.KEY_SUB_SECTOR, searchResults.get(position));
                            break;

                        case Constants.SEARCHABLE_TYPE_COMPANY:
                            intent = new Intent(activity, CompanyActivity.class);
                            intent.putExtra(Constants.KEY_COMPANY, searchResults.get(position));
                            break;
                    }

                    startActivity(intent);
                    overridePendingTransition(R.anim.child_enter, R.anim.parent_exit);
                }
            });

            // check results size
            if (searchResults.size() == AppController.SEARCH_RESULTS_LIMIT) {
                // --add load more footer--
                listResults.addFooterView(loadMoreFooter);
                buttonLoadMore.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String lastId = adapter.getLastItemId();
                        new LoadMoreTask(searchText, searchType, lastId).execute();
                    }
                });
            }

            showMain();
        }
    }

    /**
     * sub class, used to load more search results from server
     */
    private class LoadMoreTask extends AsyncTask<Void, Void, Void> {
        private String searchText;
        private int searchType;
        private String lastId;
        private SearchActivity activity;

        private String response;
        private String searchCategory;

        private LoadMoreTask(String searchText, int searchType, String lastId) {
            this.searchText = searchText;
            this.searchType = searchType;
            this.lastId = lastId;

            activity = SearchActivity.this;

            // get appropriate search by category
            searchCategory = null;
            switch (searchType) {
                case Constants.SEARCHABLE_TYPE_SECTOR:
                    searchCategory = "sec";
                    break;

                case Constants.SEARCHABLE_TYPE_SUB_SECTOR:
                    searchCategory = "subsec";
                    break;

                case Constants.SEARCHABLE_TYPE_COMPANY:
                    searchCategory = "com";
                    break;
            }

            cancelRunningTasks(); // cancel running tasks before run this
            tasks.add(this); // hold  reference to this task, to destroy it if required
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            // check internet connection
            if (!InternetUtil.isConnected(activity)) {
                // show error
                AppMsg.makeText(activity, R.string.no_internet_connection, AppMsg.STYLE_CONFIRM).show();

                cancel(true);
                return;
            }

            showLoadMoreProgress(true);

        }

        @Override
        protected Void doInBackground(Void... params) {
            String url = AppController.END_POINT + "/search.php?cat=" + searchCategory
                    + "&name=" + searchText + "&last_id=" + lastId;
            JsonReader jsonReader = new JsonReader(url);
            response = jsonReader.sendRequest();

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            tasks.remove(this); // remove from running tasks
            showLoadMoreProgress(false); // hide progress

            // validate response
            if (response == null) {
                // show error msg
                AppMsg.makeText(activity, R.string.connection_error, AppMsg.STYLE_CONFIRM).show();

                return;
            }

            if (response.equals(Constants.JSON_ERROR_MSG)) {
                // show error msg
                AppMsg.makeText(activity, R.string.connection_error, AppMsg.STYLE_CONFIRM).show();

                return;
            }

            // ---response is valid---
            // handle it
            SearchableItemsHandler handler = new SearchableItemsHandler(response, searchType);
            final List<SearchableItem> searchResults = handler.handle();

            // check handling operation result
            if (searchResults == null) {
                // show error msg
                AppMsg.makeText(activity, R.string.connection_error, AppMsg.STYLE_CONFIRM).show();

                return;
            }

            // check if data is less than limit
            if (searchResults.size() < AppController.SEARCH_RESULTS_LIMIT) {
                // --no more results for this keyword in the next time--
                // remove load more footer
                listResults.removeFooterView(loadMoreFooter);
            }

            // --here, more search results exists >> display them--
            // add items to existing adapter
            adapter.addMore(searchResults);
        }
    }

    /**
     * method, used to show main view
     */
    private void showMain() {
        // hide all other
        ViewUtil.showView(progressView, false);
        ViewUtil.showView(errorView, false);
        ViewUtil.showView(emptyView, false);

        // show main view
        ViewUtil.showView(mainView, true);
    }

    /**
     * method, used to show progress view
     */
    private void showProgress() {
        // hide all other
        ViewUtil.showView(mainView, false);
        ViewUtil.showView(errorView, false);
        ViewUtil.showView(emptyView, false);

        // show progress view
        ViewUtil.showView(progressView, true);
    }

    /**
     * method, used to show error view
     */
    private void showError(int msgRes) {
        // hide all other
        ViewUtil.showView(progressView, false);
        ViewUtil.showView(mainView, false);
        ViewUtil.showView(emptyView, false);

        // show error view
        textError.setText(msgRes);
        ViewUtil.showView(errorView, true);
    }

    /**
     * method, used to show empty view
     */
    private void showEmpty(int msgRes) {
        // hide all other
        ViewUtil.showView(progressView, false);
        ViewUtil.showView(errorView, false);
        ViewUtil.showView(mainView, false);

        // show empty view
        textEmpty.setText(msgRes);
        ViewUtil.showView(emptyView, true);
    }

    /**
     * method, used to show progress in load more footer
     */
    private void showLoadMoreProgress(boolean show) {
        ViewUtil.showView(buttonLoadMore, !show);
        ViewUtil.showView(progressBar, show);
    }

    /**
     * method, used to cancel running tasks
     */
    private void cancelRunningTasks() {
        for (AsyncTask task : tasks) {
            task.cancel(true);
        }
    }

    /**
     * overriden method
     */
    @Override
    public void onDestroy() {
        cancelRunningTasks();
        super.onDestroy();
    }
}
