package net.smartinnovationtechnology.qbg;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.List;

import adapters.OrganizationsAdapter;
import datamodels.Organization;
import views.BaseActivity;
import views.SlideExpandableListView;

public class OrganizationsFragment extends Fragment {
    public static final String TAG = "organizations";

    // fragment objects
    private BaseActivity activity;
    private SlideExpandableListView listOrganizations;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_organizations, container, false);
        initComponents(rootView);
        return rootView;
    }

    private void initComponents(View rootView) {
        activity = (BaseActivity) getActivity();
        listOrganizations = (SlideExpandableListView) rootView.findViewById(R.id.list_organizations);

        // set data
        final List<Organization> organizations = getOrganizations();
        final OrganizationsAdapter adapter = new OrganizationsAdapter(activity, R.layout.list_organization_item,
                R.layout.list_organization_sub_item, organizations);
        listOrganizations.setAdapter(adapter);

        // add listeners
        listOrganizations.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                boolean expand = !organizations.get(groupPosition).isExpanded();

                // expand or collapse group view
                if (expand) {
                    listOrganizations.expandGroupWithAnimation(groupPosition);
                } else {
                    listOrganizations.collapseGroupWithAnimation(groupPosition);
                }

                // update in adapter
                adapter.expandItem(groupPosition, expand);

                return true;
            }
        });
    }

    /**
     * method, used to return array of organizations
     */
    private List<Organization> getOrganizations() {
        String[] names = activity.getResources().getStringArray(R.array.organizations);
        List<Organization> organizations = new ArrayList<Organization>();

        for (int i = 0; i < names.length; i++) {
            String name = names[i];

            // get website
            int websiteResId = activity.getResources().getIdentifier("org" + (i + 1) + "_website", "string", activity.getPackageName());
            String website = activity.getString(websiteResId);

            // get phone numbers
            List<String> phoneNumbers = new ArrayList<String>();
            int j = 1;
            while (true) {
                int phoneResId = activity.getResources().getIdentifier("org" + (i + 1) + "_phone" + j, "string", activity.getPackageName());

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

            Organization organization = new Organization(name, website, phoneNumbers);
            organizations.add(organization);
        }

        return organizations;
    }
}
