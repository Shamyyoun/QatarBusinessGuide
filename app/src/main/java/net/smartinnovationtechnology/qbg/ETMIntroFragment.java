package net.smartinnovationtechnology.qbg;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import utils.RawUtil;

public class ETMIntroFragment extends Fragment {
    public static final String TAG = "etm_intro";

    // fragment objects
    private TextView textContent;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_emm_intro, container, false);
        initComponents(rootView);
        return rootView;
    }

    private void initComponents(View rootView) {
        textContent = (TextView) rootView.findViewById(R.id.text_content);

        // set data
        String content = RawUtil.readInHTMLFormat(getActivity().getApplicationContext(), R.raw.etm_intro);
        textContent.setText(Html.fromHtml(content));
    }
}
