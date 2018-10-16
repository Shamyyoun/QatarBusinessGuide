package datamodels;

/**
 * Created by Shamyyoun on 3/19/2015.
 */
public class Company extends SearchableItem {
    private String id;
    private String name;
    private String nameEn;
    private String country;
    private String place;
    private String desc;
    private String descEn;
    private String telephone1;
    private String telephone2;
    private String mobile1;
    private String mobile2;
    private String address;
    private String addressEn;
    private double latitude;
    private double longitude;
    private String fax;
    private String email;
    private String website;
    private String video;
    private String logo;

    public Company(String id) {
        this.id = id;
    }

    @Override
    public String getId() {
        return id;
    }

    public Company setId(String id) {
        this.id = id;
        return this;
    }

    @Override
    public String getName() {
        return name;
    }

    public Company setName(String name) {
        this.name = name;
        return this;
    }

    public String getNameEn() {
        return nameEn;
    }

    public Company setNameEn(String nameEn) {
        this.nameEn = nameEn;
        return this;
    }

    public String getCountry() {
        return country;
    }

    public Company setCountry(String country) {
        this.country = country;
        return this;
    }

    public String getPlace() {
        return place;
    }

    public Company setPlace(String place) {
        this.place = place;
        return this;
    }

    public String getDesc() {
        return desc;
    }

    public Company setDesc(String desc) {
        this.desc = desc;
        return this;
    }

    public String getDescEn() {
        return descEn;
    }

    public Company setDescEn(String descEn) {
        this.descEn = descEn;
        return this;
    }

    public String getTelephone1() {
        return telephone1;
    }

    public Company setTelephone1(String telephone1) {
        this.telephone1 = telephone1;
        return this;
    }

    public String getTelephone2() {
        return telephone2;
    }

    public Company setTelephone2(String telephone2) {
        this.telephone2 = telephone2;
        return this;
    }

    public String getMobile1() {
        return mobile1;
    }

    public Company setMobile1(String mobile1) {
        this.mobile1 = mobile1;
        return this;
    }

    public String getMobile2() {
        return mobile2;
    }

    public Company setMobile2(String mobile2) {
        this.mobile2 = mobile2;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public Company setAddress(String address) {
        this.address = address;
        return this;
    }

    public String getAddressEn() {
        return addressEn;
    }

    public Company setAddressEn(String addressEn) {
        this.addressEn = addressEn;
        return this;
    }

    public double getLatitude() {
        return latitude;
    }

    public Company setLatitude(double latitude) {
        this.latitude = latitude;
        return this;
    }

    public double getLongitude() {
        return longitude;
    }

    public Company setLongitude(double longitude) {
        this.longitude = longitude;
        return this;
    }

    public String getFax() {
        return fax;
    }

    public Company setFax(String fax) {
        this.fax = fax;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public Company setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getWebsite() {
        return website;
    }

    public Company setWebsite(String website) {
        this.website = website;
        return this;
    }

    public String getVideo() {
        return video;
    }

    public Company setVideo(String video) {
        this.video = video;
        return this;
    }

    public String getLogo() {
        return logo;
    }

    public Company setLogo(String logo) {
        this.logo = logo;
        return this;
    }

    @Override
    public String getThumbnail() {
        return logo;
    }
}
