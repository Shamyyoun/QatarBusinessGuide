package net.smartinnovationtechnology.qbg;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.devspark.appmsg.AppMsg;

import java.util.ArrayList;
import java.util.List;

import adapters.MinistriesAdapter;
import datamodels.Ministry;
import views.BaseActivity;
import views.SlideExpandableListView;

public class MinistriesFragment extends Fragment {
    public static final String TAG = "ministries";

    // fragment objects
    private BaseActivity activity;
    private SlideExpandableListView listMinistries;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_ministries, container, false);
        initComponents(rootView);
        return rootView;
    }

    private void initComponents(View rootView) {
        activity = (BaseActivity) getActivity();
        listMinistries = (SlideExpandableListView) rootView.findViewById(R.id.list_ministries);

        // set data
        final List<Ministry> ministries = getMinistries();
        final MinistriesAdapter adapter = new MinistriesAdapter(activity, R.layout.list_ministries_item,
                R.layout.list_ministries_sub_item, ministries);
        listMinistries.setAdapter(adapter);

        // add listeners
        listMinistries.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                // check if item has sub item data
                Ministry ministry = ministries.get(groupPosition);
                if (ministry.getPhoneNumbers().size() == 0 && ministry.getWebSite() == null) {
                    // --doesn't have sub item data--
                    AppMsg.cancelAll();
                    AppMsg.makeText(activity, R.string.no_ministry_data, AppMsg.STYLE_CONFIRM).show();
                } else {
                    // --has sub item data--
                    boolean expand = !ministry.isExpanded();

                    // expand or collapse group view
                    if (expand) {
                        listMinistries.expandGroupWithAnimation(groupPosition);
                    } else {
                        listMinistries.collapseGroupWithAnimation(groupPosition);
                    }

                    // update in adapter
                    adapter.expandItem(groupPosition, expand);
                }

                return true;
            }
        });
    }

    /**
     * method, used to return array of ministries
     */
    private List<Ministry> getMinistries() {
        String[] names = activity.getResources().getStringArray(R.array.ministries);
        List<Ministry> ministries = new ArrayList<Ministry>();

        for (int i = 0; i < names.length; i++) {
            String name = names[i];

            // get website
            int websiteResId = activity.getResources().getIdentifier("ministry" + (i + 1) + "_website", "string", activity.getPackageName());
            String website = websiteResId == 0 ? null : activity.getString(websiteResId);

            // get phone numbers
            List<String> phoneNumbers = new ArrayList<String>();
            int j = 1;
            while (true) {
                int phoneResId = activity.getResources().getIdentifier("ministry" + (i + 1) + "_phone" + j, "string", activity.getPackageName());

                // check id
                if (phoneResId == 0) {
                    // not found
                    break;
                } else {
                    // found >> add it
                    String phoneNumber = activity.getString(phoneResId);
                    phoneNumbers.add(phoneNumber);
                }

                j++;
            }

            Ministry ministry = new Ministry(name, website, phoneNumbers);
            ministries.add(ministry);
        }

        return ministries;
    }
}
