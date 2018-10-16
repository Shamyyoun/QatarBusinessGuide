package net.smartinnovationtechnology.qbg;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import utils.RawUtil;
import views.BaseActivity;

public class InvestInQatarFragment extends Fragment {
    public static final String TAG = "invest_in_qatar";

    private BaseActivity activity;
    private TextView textContent;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_invest_in_qatar, container, false);
        initComponents(rootView);
        return rootView;
    }

    private void initComponents(View rootView) {
        activity = (BaseActivity) getActivity();
        textContent = (TextView) rootView.findViewById(R.id.text_content);

        // set data
        String content = RawUtil.readInHTMLFormat(activity.getApplicationContext(), R.raw.invest_in_qatar);
        textContent.setText(Html.fromHtml(content));
    }
}
