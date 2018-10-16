package net.smartinnovationtechnology.qbg;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.TextView;

import views.BaseActivity;


public class MCActivity extends BaseActivity implements View.OnClickListener {
    // tabs objects
    private TextView textMediaCenter;
    private TextView textOurProjects;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mc);

        initComponents();
    }

    private void initComponents() {
        // customize actionbar
        setActionBarTitle(R.string.media_center);
        setActionbarIcon(R.drawable.ic_back_white);
        setActionBarIconClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        // init tabs
        textMediaCenter = (TextView) findViewById(R.id.text_mediaCenter);
        textOurProjects = (TextView) findViewById(R.id.text_ourProjects);
        textMediaCenter.setOnClickListener(this);
        textOurProjects.setOnClickListener(this);

        // select first tab as default
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.container_tab, new MCFragment(), MCFragment.TAG);
        ft.commit();
        selectTab(textMediaCenter);
    }

    @Override
    public void onClick(View v) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        switch (v.getId()) {
            case R.id.text_mediaCenter:
                ft.replace(R.id.container_tab, new MCFragment());
                selectTab(textMediaCenter);
                break;

            case R.id.text_ourProjects:
                ft.replace(R.id.container_tab, new MCProjectsFragment());
                selectTab(textOurProjects);
                break;
        }

        ft.commit();
    }

    private void selectTab(TextView textView) {
        // deselect all first
        textMediaCenter.setSelected(false);
        textOurProjects.setSelected(false);

        // select desired item
        textView.setSelected(true);
    }
}
