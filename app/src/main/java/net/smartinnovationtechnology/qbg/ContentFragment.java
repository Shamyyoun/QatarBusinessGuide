package net.smartinnovationtechnology.qbg;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import datamodels.Constants;
import views.BaseActivity;

public class ContentFragment extends Fragment {
    public static final String TAG = "content";

    private BaseActivity activity;
    private String content;
    private String title;
    private TextView textContent;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.screen_content, container, false);
        initComponents(rootView);
        return rootView;
    }

    private void initComponents(View rootView) {
        activity = (BaseActivity) getActivity();
        content = getArguments().getString(Constants.KEY_CONTENT, "");
        title = getArguments().getString(Constants.KEY_TITLE, null);
        textContent = (TextView) rootView.findViewById(R.id.text_content);

        // set data
        if (title != null)
            activity.setActionBarTitle(title);
        textContent.setText(Html.fromHtml(content));
    }
}
