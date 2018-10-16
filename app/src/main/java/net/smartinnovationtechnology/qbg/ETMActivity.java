package net.smartinnovationtechnology.qbg;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.TextView;

import views.BaseActivity;

public class ETMActivity extends BaseActivity implements View.OnClickListener {
    // tabs objects
    private TextView textIntro;
    private TextView textServices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emm);

        initComponents();
    }

    private void initComponents() {
        // customize actionbar
        setActionBarTitle(R.string.economy_trading_ministry);
        setActionbarIcon(R.drawable.ic_back_white);
        setActionBarIconClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        // init tabs
        textIntro = (TextView) findViewById(R.id.text_intro);
        textServices = (TextView) findViewById(R.id.text_services);
        textIntro.setOnClickListener(this);
        textServices.setOnClickListener(this);

        // select first tab as default
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.container_tab, new ETMIntroFragment(), ETMIntroFragment.TAG);
        ft.commit();
        selectTab(textIntro);
    }

    @Override
    public void onClick(View v) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        switch (v.getId()) {
            case R.id.text_intro:
                ft.replace(R.id.container_tab, new ETMIntroFragment());
                selectTab(textIntro);
                break;

            case R.id.text_services:
                ft.replace(R.id.container_tab, new ETMServicesFragment());
                selectTab(textServices);
                break;
        }

        ft.commit();
    }

    private void selectTab(TextView textView) {
        // deselect all first
        textIntro.setSelected(false);
        textServices.setSelected(false);

        // select desired item
        textView.setSelected(true);
    }
}
