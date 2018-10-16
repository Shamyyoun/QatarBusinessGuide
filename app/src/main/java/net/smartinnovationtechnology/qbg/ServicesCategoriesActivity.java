package net.smartinnovationtechnology.qbg;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.TextView;


public class ServicesCategoriesActivity extends ActionBarActivity implements View.OnClickListener {
    public static final int COLOR_RED = 1;
    public static final int COLOR_WHITE = 2;

    // tabs objects
    private TextView textServices;
    private TextView textEvents;
    private View viewSeperator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_categories);

        initComponents();
    }

    /**
     * method, used to initialize components
     */
    private void initComponents() {
        // init tabs
        textServices = (TextView) findViewById(R.id.text_services);
        textEvents = (TextView) findViewById(R.id.text_events);
        viewSeperator = findViewById(R.id.view_seperator);
        textServices.setOnClickListener(this);
        textEvents.setOnClickListener(this);

        // select first tab as default
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.container_tab, new ServicesCategoriesFragment(), ServicesCategoriesFragment.TAG);
        ft.commit();
        selectTab(textServices);
    }

    /**
     * overriden method, used to handle click actions
     */
    @Override
    public void onClick(View v) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        switch (v.getId()) {
            case R.id.text_services:
                ft.replace(R.id.container_tab, new ServicesCategoriesFragment());
                selectTab(textServices);
                break;

            case R.id.text_events:
                ft.replace(R.id.container_tab, new EventsFragment());
                selectTab(textEvents);
                break;
        }

        ft.commit();
    }

    private void selectTab(TextView textView) {
        // deselect all first
        textServices.setSelected(false);
        textEvents.setSelected(false);

        // select desired item
        textView.setSelected(true);
    }

    /**
     * method, used to change ta_bar color scheme
     */
    public void setTabBarColor(int color) {
        int textColor = getResources().getColor(color == COLOR_RED ? R.color.dark_red : R.color.white);
        textServices.setTextColor(textColor);
        textEvents.setTextColor(textColor);

        int tabBg = color == COLOR_RED ? R.drawable.tab_red_item : R.drawable.tab_white_item;
        textServices.setBackgroundResource(tabBg);
        textEvents.setBackgroundResource(tabBg);

        int seperatorColor = getResources().getColor(color == COLOR_RED ? R.color.light_red : R.color.dark_white);
        viewSeperator.setBackgroundColor(seperatorColor);
    }

    /**
     * overriden method
     */
    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.parent_enter, R.anim.child_exit);
    }
}
