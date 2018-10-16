package datamodels;

/**
 * Created by Shamyyoun on 3/24/2015.
 */
public class Service {
    private int id;
    private String nameAr;
    private String nameEn;
    private String addressAr;
    private String addressEn;
    private String phone;
    private int categoryId;

    private boolean expanded; // for listview only

    public Service(int categoryId) {
        this.categoryId = categoryId;
    }

    public Service(int id, String nameAr, String nameEn, String addressAr,
                   String addressEn, String phone, int categoryId) {
        this.id = id;
        this.nameAr = nameAr;
        this.nameEn = nameEn;
        this.addressAr = addressAr;
        this.addressEn = addressEn;
        this.phone = phone;
        this.categoryId = categoryId;
    }

    public int getId() {
        return id;
    }

    public Service setId(int id) {
        this.id = id;
        return this;
    }

    public String getNameAr() {
        return nameAr;
    }

    public Service setNameAr(String nameAr) {
        this.nameAr = nameAr;
        return this;
    }

    public String getNameEn() {
        return nameEn;
    }

    public Service setNameEn(String nameEn) {
        this.nameEn = nameEn;
        return this;
    }

    public String getAddressAr() {
        return addressAr;
    }

    public Service setAddressAr(String addressAr) {
        this.addressAr = addressAr;
        return this;
    }

    public String getAddressEn() {
        return addressEn;
    }

    public Service setAddressEn(String addressEn) {
        this.addressEn = addressEn;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public Service setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public boolean isExpanded() {
        return expanded;
    }

    public Service setExpanded(boolean expanded) {
        this.expanded = expanded;
        return this;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public Service setCategoryId(int categoryId) {
        this.categoryId = categoryId;
        return this;
    }
}
