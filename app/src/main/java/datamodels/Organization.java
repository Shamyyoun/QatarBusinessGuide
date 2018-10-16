package datamodels;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shamyyoun on 3/3/2015.
 */
public class Organization {
    private String name;
    private String webSite;
    private List<String> phoneNumbers;
    private boolean expanded; // used for listview only

    public Organization() {
    }

    public Organization(String name, String webSite, List<String> phoneNumbers) {
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

    public Organization setName(String name) {
        this.name = name;
        return this;
    }

    public String getWebSite() {
        return webSite;
    }

    public Organization setWebSite(String webSite) {
        this.webSite = webSite;
        return this;
    }

    public List<String> getPhoneNumbers() {
        return phoneNumbers;
    }

    public Organization setPhoneNumbers(List<String> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
        return this;
    }

    public boolean isExpanded() {
        return expanded;
    }

    public Organization setExpanded(boolean expanded) {
        this.expanded = expanded;
        return this;
    }
}
