package net.smartinnovationtechnology.qbg;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.TextView;

import views.BaseActivity;


public class GovSectorActivity extends BaseActivity implements View.OnClickListener {
    // tabs objects
    private TextView textMinistries;
    private TextView textOrganizations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gov_sector);

        initComponents();
    }

    private void initComponents() {
        // customize actionbar
        setActionBarTitle(R.string.gov_sector);
        setActionbarIcon(R.drawable.ic_back_white);
        setActionBarIconClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        // init tabs
        textMinistries = (TextView) findViewById(R.id.text_ministries);
        textOrganizations = (TextView) findViewById(R.id.text_organizations);
        textMinistries.setOnClickListener(this);
        textOrganizations.setOnClickListener(this);

        // select first tab as default
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.container_tab, new MinistriesFragment(), MinistriesFragment.TAG);
        ft.commit();
        selectTab(textMinistries);
    }

    @Override
    public void onClick(View v) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        switch (v.getId()) {
            case R.id.text_ministries:
                ft.replace(R.id.container_tab, new MinistriesFragment());
                selectTab(textMinistries);
                break;

            case R.id.text_organizations:
                ft.replace(R.id.container_tab, new OrganizationsFragment());
                selectTab(textOrganizations);
                break;
        }

        ft.commit();
    }

    private void selectTab(TextView textView) {
        // deselect all first
        textMinistries.setSelected(false);
        textOrganizations.setSelected(false);

        // select desired item
        textView.setSelected(true);
    }
}
