package net.smartinnovationtechnology.qbg;

import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.util.List;

import adapters.ServicesAdapter;
import database.ServiceDAO;
import datamodels.Constants;
import datamodels.Service;
import utils.ViewUtil;
import views.BaseActivity;
import views.SlideExpandableListView;


public class ServicesActivity extends BaseActivity {
    // parameters
    private String title;
    private int categoryId;
    private String searchKeyword;

    // main views
    private View emptyView;
    private TextView textEmpty;
    private SlideExpandableListView listItems;

    private ServiceDAO serviceDAO;
    private List<Service> services;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services);

        initComponents();
    }

    private void initComponents() {
        // get parameters
        title = getIntent().getStringExtra(Constants.KEY_TITLE);
        categoryId = getIntent().getIntExtra(Constants.KEY_CAT_ID, -1);
        searchKeyword = getIntent().getStringExtra(Constants.KEY_SEARCH_KEYWORD);

        // customize actionbar
        setActionBarTitle(title);
        setActionbarIcon(R.drawable.ic_back_white);
        setActionBarIconClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        // init objects
        emptyView = findViewById(R.id.view_empty);
        textEmpty = (TextView) emptyView.findViewById(R.id.text_empty);
        listItems = (SlideExpandableListView) findViewById(R.id.list_services);
        serviceDAO = new ServiceDAO(getApplicationContext());

        // get items according to passed parameter
        serviceDAO.open();
        if (categoryId != -1) {
            // --category id passed--
            // get service items from database
            services = serviceDAO.getAll(categoryId);
        } else {
            // --search keyword passed--
            // get matched items from database
            services = serviceDAO.searchByName(searchKeyword);
        }
        serviceDAO.close();

        // check data size
        if (services.size() == 0) {
            // show empty view
            textEmpty.setText(R.string.no_search_results);
            ViewUtil.showView(listItems, false);
            ViewUtil.showView(emptyView, true);
        } else {
            // set list adapter
            final ServicesAdapter adapter = new ServicesAdapter(this,
                    R.layout.list_services_item, R.layout.list_services_sub_item, services);
            listItems.setAdapter(adapter);

            // add item click listener
            listItems.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
                @Override
                public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                    Service item = services.get(groupPosition);
                    boolean expand = !item.isExpanded();

                    // expand or collapse group view
                    if (expand) {
                        listItems.expandGroupWithAnimation(groupPosition);
                    } else {
                        listItems.collapseGroupWithAnimation(groupPosition);
                    }

                    // update in adapter
                    adapter.expandItem(groupPosition, expand);

                    return true;
                }
            });
        }
    }
}
