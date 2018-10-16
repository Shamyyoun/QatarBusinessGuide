package adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import net.smartinnovationtechnology.qbg.R;

import java.util.ArrayList;
import java.util.List;

import datamodels.Organization;
import views.ExpandableHeightListView;
import views.SlideExpandableListView;

public class OrganizationsAdapter extends SlideExpandableListView.AnimatedExpandableListAdapter {
    private Context context;
    private int groupLayoutResourceId;
    private int childLayoutResourceId;
    private List<Organization> data;

    private LayoutInflater inflater;
    private List<GroupHolder> groupHolders;

    public OrganizationsAdapter(Context context, int groupLayoutResourceId,
                                int childLayoutResourceId, List<Organization> data) {
        this.context = context;
        this.groupLayoutResourceId = groupLayoutResourceId;
        this.childLayoutResourceId = childLayoutResourceId;
        this.data = data;

        inflater = LayoutInflater.from(context);
        groupHolders = new ArrayList<GroupHolder>(data.size());
    }

    @Override
    public Organization getChild(int groupPosition, int childPosition) {
        return data.get(groupPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getRealChildView(int groupPosition, int childPosition,
                                 boolean isLastChild, View convertView, ViewGroup parent) {

        ChildHolder holder;
        final Organization organization = getChild(groupPosition, childPosition);

        if (convertView == null) {
            holder = new ChildHolder();

            convertView = inflater.inflate(childLayoutResourceId, parent, false);
            holder.listPhoneNumbers = (ExpandableHeightListView) convertView.findViewById(R.id.list_phoneNumbers);
            holder.layoutWebSite = convertView.findViewById(R.id.layout_webSite);
            holder.textWebSite = (TextView) convertView.findViewById(R.id.text_webSite);

            convertView.setTag(holder);
        } else {
            holder = (ChildHolder) convertView.getTag();
        }

        // set data
        holder.listPhoneNumbers.setExpanded(true);
        PhoneNumbersAdapter adapter = new PhoneNumbersAdapter(context, R.layout.list_phone_numbers_item,
                organization.getPhoneNumbers());
        holder.listPhoneNumbers.setAdapter(adapter);

        holder.textWebSite.setText(Html.fromHtml("<u>" + context.getString(R.string.website) + "</u>"));

        // add listeners
        holder.listPhoneNumbers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("tel:" + organization.getPhoneNumbers().get(position)));
                context.startActivity(intent);
            }
        });
        holder.layoutWebSite.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www." + organization.getWebSite()));
                context.startActivity(browserIntent);
            }
        });

        return convertView;
    }

    @Override
    public int getRealChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Organization getGroup(int groupPosition) {
        return data.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return data.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {

        final GroupHolder holder;
        final Organization organization = getGroup(groupPosition);

        if (convertView == null) {
            holder = new GroupHolder();
            convertView = inflater.inflate(groupLayoutResourceId, parent, false);

            holder.textName = (TextView) convertView.findViewById(R.id.text_name);
            holder.imageExpand = (ImageView) convertView.findViewById(R.id.image_expand);

            convertView.setTag(holder);
        } else {
            holder = (GroupHolder) convertView.getTag();
        }

        // set data
        holder.textName.setText(organization.getName());

        // set expand button image
        if (organization.isExpanded()) {
            holder.imageExpand.setImageResource(R.drawable.ex_list_arrow_expanded);
        } else {
            holder.imageExpand.setImageResource(R.drawable.ex_list_arrow_collapsed);
        }

        // save reference to this group row
        groupHolders.add(groupPosition, holder);

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int arg0, int arg1) {
        return true;
    }

    /**
     * method, used to change icon and state when expand/collapse group
     */
    public void expandItem(int position, final boolean expand) {
        final GroupHolder holder = groupHolders.get(position);

        // change expand icon
        holder.imageExpand.setImageResource(expand ? R.drawable.ex_list_arrow_expanded : R.drawable.ex_list_arrow_collapsed);

        // change project object state
        data.get(position).setExpanded(expand);
    }

    private static class GroupHolder {
        TextView textName;
        ImageView imageExpand;
    }

    private static class ChildHolder {
        ExpandableHeightListView listPhoneNumbers;
        View layoutWebSite;
        TextView textWebSite;
    }
}
