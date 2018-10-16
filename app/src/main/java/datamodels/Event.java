package datamodels;

/**
 * Created by Shamyyoun on 3/31/2015.
 */
public class Event {
    private String id;
    private String name;
    private String nameEn;
    private String date;
    private String place;
    private String placeEn;
    private String desc1;
    private String desc1En;
    private String logo;

    private boolean expanded; // used for listview only

    public Event() {
    }

    public Event(String id, String name, String nameEn, String date, String place, String placeEn, String desc1, String desc1En, String logo) {
        this.id = id;
        this.name = name;
        this.nameEn = nameEn;
        this.date = date;
        this.place = place;
        this.placeEn = placeEn;
        this.desc1 = desc1;
        this.desc1En = desc1En;
        this.logo = logo;
    }

    public String getId() {
        return id;
    }

    public Event setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Event setName(String name) {
        this.name = name;
        return this;
    }

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public String getDate() {
        return date;
    }

    public Event setDate(String date) {
        this.date = date;
        return this;
    }

    public String getPlace() {
        return place;
    }

    public Event setPlace(String place) {
        this.place = place;
        return this;
    }

    public String getPlaceEn() {
        return placeEn;
    }

    public void setPlaceEn(String placeEn) {
        this.placeEn = placeEn;
    }

    public String getDesc1() {
        return desc1;
    }

    public Event setDesc1(String desc1) {
        this.desc1 = desc1;
        return this;
    }

    public String getDesc1En() {
        return desc1En;
    }

    public void setDesc1En(String desc1En) {
        this.desc1En = desc1En;
    }

    public String getLogo() {
        return logo;
    }

    public Event setLogo(String logo) {
        this.logo = logo;
        return this;
    }

    public boolean isExpanded() {
        return expanded;
    }

    public Event setExpanded(boolean expanded) {
        this.expanded = expanded;
        return this;
    }
}
