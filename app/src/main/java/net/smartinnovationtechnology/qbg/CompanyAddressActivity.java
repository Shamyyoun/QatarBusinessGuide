package net.smartinnovationtechnology.qbg;

import android.os.Bundle;
import android.view.View;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import datamodels.Company;
import datamodels.Constants;
import views.BaseActivity;

public class CompanyAddressActivity extends BaseActivity {
    private Company company;
    private GoogleMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_address);

        initComponents();
    }

    /**
     * method, used to initialize components
     */
    private void initComponents() {
        // get company
        company = (Company) getIntent().getSerializableExtra(Constants.KEY_COMPANY);
        map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();

        // customize actionbar
        setActionBarTitle(getString(R.string.address) + " " + company.getName());
        setActionbarIcon(R.drawable.ic_back_white);
        setActionBarIconClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        // add marker to company location
        LatLng companyCoordinate = new LatLng(company.getLatitude(), company.getLongitude());
        map.addMarker(new MarkerOptions()
                .title(company.getName())
                .snippet(company.getAddress())
                .position(companyCoordinate));

        // animate camera to company location
        CameraUpdate companyLocation = CameraUpdateFactory.newLatLngZoom(companyCoordinate, 13);
        map.animateCamera(companyLocation);
    }
}
