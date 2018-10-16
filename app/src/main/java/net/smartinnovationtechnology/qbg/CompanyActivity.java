package net.smartinnovationtechnology.qbg;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import datamodels.Company;
import datamodels.Constants;
import utils.ViewUtil;
import views.BaseActivity;


public class CompanyActivity extends BaseActivity implements View.OnClickListener {
    private Company company;

    // main views
    private ImageView imageDefault;
    private ImageView imageImage;
    private TextView textDesc;
    private View layoutAddress;
    private TextView textAddress;
    private View layoutPhone1;
    private TextView textPhone1;
    private View layoutPhone2;
    private TextView textPhone2;
    private View layoutMobile1;
    private TextView textMobile1;
    private View layoutMobile2;
    private TextView textMobile2;
    private View layoutFax;
    private TextView textFax;
    private View layoutEmail;
    private TextView textEmail;
    private View layoutWebsite;
    private TextView textWebsite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company);

        initComponents();
    }

    /**
     * method, used to initialize components
     */
    private void initComponents() {
        // get company
        company = (Company) getIntent().getSerializableExtra(Constants.KEY_COMPANY);

        // customize actionbar
        setActionBarTitle(AppController.getInstance(getApplicationContext()).getLang().equals("en") ? company.getNameEn() : company.getName());
        setActionbarIcon(R.drawable.ic_back_white);
        setActionBarIconClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        // init main views
        imageDefault = (ImageView) findViewById(R.id.image_default);
        imageImage = (ImageView) findViewById(R.id.image_image);
        textDesc = (TextView) findViewById(R.id.text_desc);
        layoutAddress = findViewById(R.id.layout_cairo_address);
        textAddress = (TextView) findViewById(R.id.text_address);
        layoutPhone1 = findViewById(R.id.layout_cairo_phone1);
        textPhone1 = (TextView) findViewById(R.id.text_phone1);
        layoutPhone2 = findViewById(R.id.layout_cairo_phone2);
        textPhone2 = (TextView) findViewById(R.id.text_phone2);
        layoutMobile1 = findViewById(R.id.layout_mobile1);
        textMobile1 = (TextView) findViewById(R.id.text_mobile1);
        layoutMobile2 = findViewById(R.id.layout_mobile2);
        textMobile2 = (TextView) findViewById(R.id.text_mobile2);
        layoutFax = findViewById(R.id.layout_cairo_fax);
        textFax = (TextView) findViewById(R.id.text_fax);
        layoutEmail = findViewById(R.id.layout_cairo_email);
        textEmail = (TextView) findViewById(R.id.text_email);
        layoutWebsite = findViewById(R.id.layout_webSite);
        textWebsite = (TextView) findViewById(R.id.text_webSite);

        // --set data--
        // check lang
        if (AppController.getInstance(getApplicationContext()).getLang().equals("en")) {
            // --english--
            // check if has desc
            if (!company.getDescEn().isEmpty()) {
                // set desc
                textDesc.setText(company.getDescEn());
            } else {
                // hide it
                textDesc.setVisibility(View.GONE);
            }

            // check if has address
            if (!company.getAddressEn().isEmpty()) {
                // set address
                textAddress.setText(company.getAddressEn());

                // ensure that has lat & lng values
                if (company.getLatitude() != 0.0 && company.getLongitude() != 0.0) {
                    // add the click listener
                    layoutAddress.setOnClickListener(this);
                }
            } else {
                // hide it
                layoutAddress.setVisibility(View.GONE);
            }
        } else {
            // --arabic--
            // check if has desc
            if (!company.getDesc().isEmpty()) {
                // set desc
                textDesc.setText(company.getDesc());
            } else {
                // hide it
                textDesc.setVisibility(View.GONE);
            }

            // check if has address
            if (!company.getAddress().isEmpty()) {
                // set address
                textAddress.setText(company.getAddress());

                // ensure that has lat & lng values
                if (company.getLatitude() != 0.0 && company.getLongitude() != 0.0) {
                    // add the click listener
                    layoutAddress.setOnClickListener(this);
                }
            } else {
                // hide it
                layoutAddress.setVisibility(View.GONE);
            }
        }

        // ensure that image url is not empty
        if (!company.getLogo().isEmpty()) {
            // load image
            Picasso.with(this).load(company.getLogo()).into(imageImage, new Callback() {
                @Override
                public void onSuccess() {
                    ViewUtil.showView(imageImage, true);
                    ViewUtil.showView(imageDefault, false);
                }

                @Override
                public void onError() {
                }
            });
        }

        // check if has phone 1
        if (!company.getTelephone1().isEmpty()) {
            // set phone 1
            textPhone1.setText(company.getTelephone1());
            layoutPhone1.setOnClickListener(this);
        } else {
            // hide it
            layoutPhone1.setVisibility(View.GONE);
        }

        // check if has phone 2
        if (!company.getTelephone2().isEmpty()) {
            // set phone 2
            textPhone2.setText(company.getTelephone2());
            layoutPhone2.setOnClickListener(this);
        } else {
            // hide it
            layoutPhone2.setVisibility(View.GONE);
        }

        // check if has mobile 1 
        if (!company.getMobile1().isEmpty()) {
            // set mobile 1
            textMobile1.setText(company.getMobile1());
            layoutMobile1.setOnClickListener(this);
        } else {
            // hide it
            layoutMobile1.setVisibility(View.GONE);
        }

        // check if has mobile 2 
        if (!company.getMobile2().isEmpty()) {
            // set mobile 2
            textMobile2.setText(company.getMobile2());
            layoutMobile2.setOnClickListener(this);
        } else {
            // hide it
            layoutMobile2.setVisibility(View.GONE);
        }

        // check if has fax
        if (!company.getFax().isEmpty()) {
            // set fax
            textFax.setText(company.getFax());
        } else {
            // hide it
            layoutFax.setVisibility(View.GONE);
        }

        // check if has email
        if (!company.getEmail().isEmpty()) {
            // set email
            textEmail.setText(company.getEmail());
            layoutEmail.setOnClickListener(this);
        } else {
            // hide it
            layoutEmail.setVisibility(View.GONE);
        }

        // check if has website
        if (!company.getWebsite().isEmpty()) {
            // set website
            textWebsite.setText(Html.fromHtml("<u>" + getString(R.string.website) + "</u>"));
            layoutWebsite.setOnClickListener(this);
        } else {
            // hide it
            layoutWebsite.setVisibility(View.GONE);
        }
    }

    /**
     * overriden method, used to handle click actions
     */
    @Override
    public void onClick(View v) {
        Intent intent = null;
        boolean overridePendingTransition = false;
        switch (v.getId()) {
            case R.id.layout_cairo_address:
                intent = new Intent(this, CompanyAddressActivity.class);
                intent.putExtra(Constants.KEY_COMPANY, company);
                overridePendingTransition = true;
                break;

            case R.id.layout_cairo_phone1:
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse("tel:" + company.getTelephone1()));
                break;

            case R.id.layout_cairo_phone2:
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse("tel:" + company.getTelephone2()));
                break;

            case R.id.layout_mobile1:
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse("tel:" + company.getMobile1()));
                break;

            case R.id.layout_mobile2:
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse("tel:" + company.getMobile2()));
                break;

            case R.id.layout_cairo_email:
                intent = new Intent(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{company.getEmail()});
                intent.setType("message/rfc822");
                break;

            case R.id.layout_webSite:
                String website = company.getWebsite();
                if (!(website.startsWith("http://") || website.startsWith("https://")))
                    website = "http://" + website;

                intent = new Intent(Intent.ACTION_VIEW, Uri.parse(website));
                break;
        }

        if (intent != null) {
            startActivity(intent);
            if (overridePendingTransition)
                overridePendingTransition(R.anim.child_enter, R.anim.parent_exit);
        }
    }
}
