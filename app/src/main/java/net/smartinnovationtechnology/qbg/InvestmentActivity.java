package net.smartinnovationtechnology.qbg;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.TextView;

import views.BaseActivity;


public class InvestmentActivity extends BaseActivity implements View.OnClickListener {
    // tabs objects
    private TextView textInvestInQatar;
    private TextView textNationalProjects;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_investment);

        initComponents();
    }

    private void initComponents() {
        // customize actionbar
        setActionBarTitle(R.string.investment_in_qatar);
        setActionbarIcon(R.drawable.ic_back_white);
        setActionBarIconClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        // init tabs
        textInvestInQatar = (TextView) findViewById(R.id.text_investInQatar);
        textNationalProjects = (TextView) findViewById(R.id.text_nationalProjects);
        textInvestInQatar.setOnClickListener(this);
        textNationalProjects.setOnClickListener(this);

        // select first tab as default
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.container_tab, new InvestInQatarFragment(), InvestInQatarFragment.TAG);
        ft.commit();
        selectTab(textInvestInQatar);
    }

    @Override
    public void onClick(View v) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        switch (v.getId()) {
            case R.id.text_investInQatar:
                ft.replace(R.id.container_tab, new InvestInQatarFragment());
                selectTab(textInvestInQatar);
                break;

            case R.id.text_nationalProjects:
                ft.replace(R.id.container_tab, new ProjectsFragment());
                selectTab(textNationalProjects);
                break;
        }

        ft.commit();
    }

    private void selectTab(TextView textView) {
        // deselect all first
        textInvestInQatar.setSelected(false);
        textNationalProjects.setSelected(false);

        // select desired item
        textView.setSelected(true);
    }
}
