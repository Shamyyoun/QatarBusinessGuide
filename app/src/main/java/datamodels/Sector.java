package datamodels;

/**
 * Created by Shamyyoun on 3/19/2015.
 */
public class Sector extends SearchableItem {
    private String id;
    private String name;
    private String nameEn;

    public Sector(String id) {
        this.id = id;
    }

    public Sector(String id, String name, String nameEn) {
        this.id = id;
        this.name = name;
        this.nameEn = nameEn;
    }

    @Override
    public String getId() {
        return id;
    }

    public Sector setId(String id) {
        this.id = id;
        return this;
    }

    @Override
    public String getName() {
        return name;
    }

    public Sector setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    @Override
    public String getThumbnail() {
        return null;
    }
}
