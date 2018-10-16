package net.smartinnovationtechnology.qbg;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import adapters.ProjectsAdapter;
import datamodels.Project;
import utils.ResourcesUtil;
import utils.RawUtil;
import views.BaseActivity;
import views.SlideExpandableListView;

public class ProjectsFragment extends Fragment {
    public static final String TAG = "projects";

    // fragment objects
    private BaseActivity activity;
    private SlideExpandableListView listProjects;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_projects, container, false);
        initComponents(rootView);
        return rootView;
    }

    private void initComponents(final View rootView) {
        activity = (BaseActivity) getActivity();
        listProjects = (SlideExpandableListView) rootView.findViewById(R.id.list_projects);

        final Project[] projects = getProjects();
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
     * method, used to return array of projects
     */
    private Project[] getProjects() {
        String[] names = activity.getResources().getStringArray(R.array.projects);
        Project[] projects = new Project[names.length];

        for (int i = 0; i < projects.length; i++) {
            String name = names[i];
            String description = RawUtil.readInHTMLFormat(activity.getApplicationContext(), "proj" + (i + 1));
            int imageResourceId = ResourcesUtil.getDrawableId(activity.getApplicationContext(), "proj" + (i + 1));
            Project project = new Project(name, description, imageResourceId);

            projects[i] = project;
        }

        return projects;
    }
}
