package net.smartinnovationtechnology.qbg;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import adapters.ProjectsAdapter;
import datamodels.Project;
import utils.RawUtil;
import utils.ResourcesUtil;
import views.BaseActivity;
import views.SlideExpandableListView;

public class MCProjectsFragment extends Fragment {
    public static final String TAG = "mc_projects";

    // fragment objects
    private BaseActivity activity;
    private SlideExpandableListView listProjects;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_mc_projects, container, false);
        initComponents(rootView);
        return rootView;
    }

    private void initComponents(final View rootView) {
        activity = (BaseActivity) getActivity();
        listProjects = (SlideExpandableListView) rootView.findViewById(R.id.list_projects);

        final Project[] projects = getMCProjects();
        final ProjectsAdapter adapter = new ProjectsAdapter(activity, R.layout.list_projects_item,
                R.layout.list_projects_sub_item, projects);
        listProjects.setAdapter(adapter);

        // add listeners
        listProjects.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                boolean expand = !projects[groupPosition].isExpanded();

                // expand or collapse group view
                if (expand) {
                    listProjects.expandGroupWithAnimation(groupPosition);
                } else {
                    listProjects.collapseGroupWithAnimation(groupPosition);
                }

                // update in adapter
                adapter.expandItem(groupPosition, expand);

                return true;
            }
        });
    }

    /**
     * method, used to return array of MC projects
     */
    private Project[] getMCProjects() {
        String[] names = activity.getResources().getStringArray(R.array.mc_projects);
        Project[] projects = new Project[names.length];

        for (int i = 0; i < projects.length; i++) {
            String name = names[i];
            String description = RawUtil.readInHTMLFormat(activity.getApplicationContext(), "mc_proj" + (i + 1));
            String website = getString(ResourcesUtil.getStringId(activity.getApplicationContext(), "mc_proj" + (i + 1) + "_website"));
            int imageResourceId = ResourcesUtil.getDrawableId(activity.getApplicationContext(), "mc_proj" + (i + 1));
            Project project = new Project(name, description, website, imageResourceId);

            projects[i] = project;
        }

        return projects;
    }
}
