package datamodels;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shamyyoun on 3/3/2015.
 */
public class Ministry {
    private String name;
    private String webSite;
    private List<String> phoneNumbers;
    private boolean expanded; // used for listview only

    public Ministry(String name, String webSite, List<String> phoneNumbers) {
        this.name = name;
        this.webSite = webSite;

        if (phoneNumbers != null) {
            this.phoneNumbers = phoneNumbers;
        } else {
            this.phoneNumbers = new ArrayList<String>(0);
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWebSite() {
        return webSite;
    }

    public void setWebSite(String webSite) {
        this.webSite = webSite;
    }

    public List<String> getPhoneNumbers() {
        return phoneNumbers;
    }

    public void setPhoneNumbers(List<String> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }
}
