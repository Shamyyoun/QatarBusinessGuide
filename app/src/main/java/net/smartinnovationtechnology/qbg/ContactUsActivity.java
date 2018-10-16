package net.smartinnovationtechnology.qbg;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import views.BaseActivity;


public class ContactUsActivity extends BaseActivity implements View.OnClickListener {
    private View layoutCairoPhone1;
    private View layoutCairoPhone2;
    private View layoutCairoMail;
    private View layoutAlexPhone;
    private View layoutAlexMail;
    private TextView textWebSite;
    private View layoutWebsite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);

        initComponents();
    }

    private void initComponents() {
        // customize actionbar
        setActionBarTitle(R.string.contact_us);
        setActionbarIcon(R.drawable.ic_back_white);
        setActionBarIconClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        // init views
        layoutCairoPhone1 = findViewById(R.id.layout_cairo_phone1);
        layoutCairoPhone2 = findViewById(R.id.layout_cairo_phone2);
        layoutCairoMail = findViewById(R.id.layout_cairo_email);
        layoutAlexPhone = findViewById(R.id.layout_alex_phone);
        layoutAlexMail = findViewById(R.id.layout_alex_email);
        textWebSite = (TextView) findViewById(R.id.text_webSite);
        layoutWebsite = findViewById(R.id.layout_webSite);

        // set data
        textWebSite.setText(Html.fromHtml("<u>" + getString(R.string.website) + "</u>"));

        // add listeners
        layoutCairoPhone1.setOnClickListener(this);
        layoutCairoPhone2.setOnClickListener(this);
        layoutCairoMail.setOnClickListener(this);
        layoutAlexPhone.setOnClickListener(this);
        layoutAlexMail.setOnClickListener(this);
        layoutWebsite.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.layout_cairo_phone1:
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse("tel:" + getString(R.string.emc_cairo_phone1)));
                break;

            case R.id.layout_cairo_phone2:
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse("tel:" + getString(R.string.emc_cairo_phone2)));
                break;

            case R.id.layout_cairo_email:
                intent = new Intent(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{getString(R.string.emc_cairo_mail)});
                intent.setType("message/rfc822");
                break;

            case R.id.layout_alex_phone:
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse("tel:" + getString(R.string.emc_alex_phone)));
                break;

            case R.id.layout_alex_email:
                intent = new Intent(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{getString(R.string.emc_alex_mail)});
                intent.setType("message/rfc822");
                break;

            case R.id.layout_webSite:
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www." + getString(R.string.emc_cairo_website)));
                break;
        }

        startActivity(intent);
    }
}
