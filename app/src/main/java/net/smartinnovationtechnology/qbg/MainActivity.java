package net.smartinnovationtechnology.qbg;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;

import com.lukedeighton.wheelview.WheelItem;
import com.lukedeighton.wheelview.WheelView;
import com.lukedeighton.wheelview.adapter.WheelAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import utils.ScreenUtil;
import views.BaseActivity;


public class MainActivity extends BaseActivity implements WheelView.OnWheelItemClickListener, View.OnClickListener {
    // main objects
    private WheelView wheelView;
    private ImageView imageRadialPath;
    private List<WheelItem> wheelItems;
    private ImageButton buttonContactUs;

    // language dialog objects
    private Dialog dialog;
    private RadioButton radioArabic;
    private RadioButton radioEnglish;
    private Button buttonSave;
    private Button buttonCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initComponents();
    }

    private void initComponents() {
        // customize actionbar
        setActionBarTitle(R.string.qatar_business_guide);

        // init main views
        wheelView = (WheelView) findViewById(R.id.wheelView);
        imageRadialPath = (ImageView) findViewById(R.id.image_radialPath);
        buttonContactUs = (ImageButton) findViewById(R.id.button_contactUs);

        // customize wheel menu
        wheelView.setTypeface(Typeface.createFromAsset(getAssets(), "una_font.ttf"));

        // customize wheel menu radius and path radius
        int wheelRadius = (int) (ScreenUtil.getWidth(this) / 1.9);
        wheelView.setWheelRadius(wheelRadius);
        ViewGroup.LayoutParams layoutParams = imageRadialPath.getLayoutParams();
        layoutParams.width = wheelRadius - (int) getResources().getDimension(R.dimen.radial_menu_item_radius);

        // get wheel items & reorder them according to language
        wheelItems = getMenuItems();
        if (AppController.getInstance(getApplicationContext()).getLang().equals("ar"))
            Collections.reverse(wheelItems);

        // set wheel menu adapter
        wheelView.setAdapter(new WheelAdapter() {
            @Override
            public WheelItem getMenuItem(int position) {
                return wheelItems.get(position);
            }

            @Override
            public int getCount() {
                return wheelItems.size();
            }
        });

        // add listeners to main views
        wheelView.setOnWheelItemClickListener(this);
        buttonContactUs.setOnClickListener(this);

        // init language dialog
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_language);
        radioArabic = (RadioButton) dialog.findViewById(R.id.radio_arabic);
        radioEnglish = (RadioButton) dialog.findViewById(R.id.radio_english);
        buttonSave = (Button) dialog.findViewById(R.id.button_save);
        buttonCancel = (Button) dialog.findViewById(R.id.button_cancel);

        // set dialog initial data
        dialog.setTitle(R.string.language);
        if (AppController.getInstance(getApplicationContext()).getLang().equals("en"))
            radioEnglish.setChecked(true);

        // add listeners to dialog buttons
        buttonSave.setOnClickListener(this);
        buttonCancel.setOnClickListener(this);
    }

    /**
     * overriden method, used to handle click events on wheel menu
     */
    @Override
    public void onWheelItemClick(WheelView parent, int position, boolean isSelected) {
        // get correct index according to current language
        if (AppController.getInstance(getApplicationContext()).getLang().equals("ar"))
            position = wheelItems.size() - 1 - position;

        Intent intent = null;
        switch (position) {
            case 0:
                intent = new Intent(this, MCActivity.class);
                break;

            case 1:
                intent = new Intent(this, ETMActivity.class);
                break;

            case 2:
                intent = new Intent(this, InvestmentActivity.class);
                break;

            case 3:
                intent = new Intent(this, GovSectorActivity.class);
                break;

            case 4:
                intent = new Intent(this, BusinessSectorActivity.class);
                break;

            case 5:
                intent = new Intent(this, ServicesCategoriesActivity.class);
                break;

            case 6:
                // show language dialog with saved language checked
                radioArabic.setChecked(AppController.getInstance(getApplicationContext()).getLang().equals("ar"));
                dialog.show();
                break;
        }

        if (intent != null) {
            startActivity(intent);
            overridePendingTransition(R.anim.child_enter, R.anim.parent_exit);
        }
    }

    /**
     * overriden method, used to handle views click actions
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_contactUs:
                startActivity(new Intent(this, ContactUsActivity.class));
                overridePendingTransition(R.anim.child_enter, R.anim.parent_exit);
                break;

            case R.id.button_save:
                // get selected language
                String selectedLang = (radioArabic.isChecked() ? "ar" : "en");

                // update language and save it
                AppController.updateLanguage(this, selectedLang);
                AppController.getInstance(getApplicationContext()).updateLocaleLanguage(selectedLang);

                // dismiss dialog
                dialog.dismiss();

                // restart app
                startActivity(new Intent(MainActivity.this, SplashActivity.class));
                finish();

                break;

            case R.id.button_cancel:
                dialog.hide();
                break;
        }
    }

    /**
     * method used to create and return menu items
     */
    public List<WheelItem> getMenuItems() {
        List<WheelItem> wheelItems = new ArrayList<WheelItem>(7);
        WheelItem wheelItem0 = new WheelItem(getString(R.string.media_center), getResources().getDrawable(R.drawable.radial_menu_icon));
        wheelItems.add(wheelItem0);

        WheelItem wheelItem1 = new WheelItem(getString(R.string.economy_trading_ministry), getResources().getDrawable(R.drawable.radial_menu_icon));
        wheelItems.add(wheelItem1);

        WheelItem wheelItem2 = new WheelItem(getString(R.string.investment_in_qatar), getResources().getDrawable(R.drawable.radial_menu_icon));
        wheelItems.add(wheelItem2);

        WheelItem wheelItem3 = new WheelItem(getString(R.string.gov_sector), getResources().getDrawable(R.drawable.radial_menu_icon));
        wheelItems.add(wheelItem3);

        WheelItem wheelItem4 = new WheelItem(getString(R.string.business_sector), getResources().getDrawable(R.drawable.radial_menu_icon));
        wheelItems.add(wheelItem4);

        WheelItem wheelItem5 = new WheelItem(getString(R.string.services), getResources().getDrawable(R.drawable.radial_menu_icon));
        wheelItems.add(wheelItem5);

        WheelItem wheelItem6 = new WheelItem(getString(R.string.language), getResources().getDrawable(R.drawable.radial_menu_icon));
        wheelItems.add(wheelItem6);

        return wheelItems;
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
