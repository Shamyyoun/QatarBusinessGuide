package net.smartinnovationtechnology.qbg;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Locale;

import adapters.ServicesCategoriesAdapter;
import datamodels.Constants;
import datamodels.ServiceCategory;
import utils.ResourcesUtil;

public class ServicesCategoriesFragment extends Fragment implements View.OnClickListener {
    public static final String TAG = "services_categories";

    private ServicesCategoriesActivity activity;

    // actionbar views
    private ImageButton buttonBack;
    private ImageButton buttonOpenSearch;

    // main views
    private View layoutRoot;
    private TextView textWelcome;
    private EditText textSearchServices;
    private ImageButton buttonSearchServices;
    private RecyclerView recyclerServicesCategories;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_services_categories, container, false);
        initComponents(rootView);
        return rootView;
    }

    private void initComponents(View rootView) {
        // customize actionbar
        buttonBack = (ImageButton) rootView.findViewById(R.id.button_back);
        buttonOpenSearch = (ImageButton) rootView.findViewById(R.id.button_openSearch);
        buttonBack.setOnClickListener(this);
        buttonOpenSearch.setOnClickListener(this);

        activity = (ServicesCategoriesActivity) getActivity();
        layoutRoot = rootView.findViewById(R.id.layout_root);
        textWelcome = (TextView) rootView.findViewById(R.id.text_welcome);
        textSearchServices = (EditText) rootView.findViewById(R.id.text_searchServices);
        buttonSearchServices = (ImageButton) rootView.findViewById(R.id.button_searchServices);
        recyclerServicesCategories = (RecyclerView) rootView.findViewById(R.id.recycler_services_categories);

        // customize tab bar
        activity.setTabBarColor(ServicesCategoriesActivity.COLOR_WHITE);

        // customize textSearchServices
        String color = String.format("#%06X", 0xFFFFFF & getResources().getColor(R.color.light_gray2));
        textSearchServices.setHint(Html.fromHtml("<font color='" + color + "'>" + getString(R.string.search_in_services_hint) + "</font>"));

        // customize welcome view
        Typeface typeface = Typeface.createFromAsset(activity.getAssets(), "ge_ss_two.otf");
        textWelcome.setTypeface(typeface);
        final Calendar calendar = Calendar.getInstance(Locale.getDefault());
        int hour = calendar.get(Calendar.HOUR_OF_DAY);

        if (hour >= 6 && hour < 12) {
            // morning
            layoutRoot.setBackgroundResource(R.drawable.morning);
            textWelcome.setText(R.string.good_morning);
        } else if (hour >= 12 && hour < 18) {
            // afternoon
            layoutRoot.setBackgroundResource(R.drawable.afternoon);
            textWelcome.setText(R.string.good_afternoon);
        } else if (hour >= 18 || hour < 6) {
            // evening
            layoutRoot.setBackgroundResource(R.drawable.evening);
            textWelcome.setText(R.string.good_evening);
        }

        // customize recycler services categories
        final LinearLayoutManager layoutManager = new LinearLayoutManager(activity.getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerServicesCategories.setLayoutManager(layoutManager);
        final ServiceCategory[] categories = getServicesCategories();
        int firstItem = 0;
        if (AppController.getInstance(activity.getApplicationContext()).getLang().equals("ar")) {
            Collections.reverse(Arrays.asList(categories));
            firstItem = categories.length - 1;
        }
        ServicesCategoriesAdapter adapter = new ServicesCategoriesAdapter(activity.getApplicationContext(), categories, R.layout.recycler_services_categories_item);
        recyclerServicesCategories.setAdapter(adapter);
        recyclerServicesCategories.scrollToPosition(firstItem);

        adapter.setOnItemClickListener(new ServicesCategoriesAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(activity, ServicesActivity.class);
                intent.putExtra(Constants.KEY_CAT_ID, categories[position].getId());
                intent.putExtra(Constants.KEY_TITLE, categories[position].getName());
                startActivity(intent);
                activity.overridePendingTransition(R.anim.child_enter, R.anim.parent_exit);
            }
        });

        // add listeners
        buttonBack.setOnClickListener(this);
        buttonOpenSearch.setOnClickListener(this);
        buttonSearchServices.setOnClickListener(this);
    }

    /**
     * overriden method, used to handle click actions
     */
    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.button_back:
                activity.onBackPressed();
                break;

            case R.id.button_openSearch:
                intent = new Intent(activity, SearchActivity.class);
                break;

            case R.id.button_searchServices:
                String keyword = textSearchServices.getText().toString();
                keyword = keyword.trim();

                // check keyword
                if (keyword.isEmpty()) {
                    // show error
                    textSearchServices.setText("");
                    textSearchServices.setError(getString(R.string.enter_search_text));

                    return;
                }

                intent = new Intent(activity, ServicesActivity.class);
                intent.putExtra(Constants.KEY_SEARCH_KEYWORD, keyword);
                intent.putExtra(Constants.KEY_TITLE, keyword);
        }

        if (intent != null) {
            startActivity(intent);
            activity.overridePendingTransition(R.anim.child_enter, R.anim.parent_exit);
        }
    }

    /**
     * method, used to return array of service categories
     */
    private ServiceCategory[] getServicesCategories() {
        String[] names = activity.getResources().getStringArray(R.array.service_categories);
        ServiceCategory[] categories = new ServiceCategory[names.length];

        for (int i = 0; i < names.length; i++) {
            String name = names[i];
            int iconResId = ResourcesUtil.getDrawableId(activity.getApplicationContext(), "service_cat" + (i + 1) + "_icon");

            ServiceCategory category = new ServiceCategory(i, name, iconResId);
            categories[i] = category;
        }

        return categories;
    }
}
