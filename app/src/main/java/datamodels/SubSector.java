package datamodels;

/**
 * Created by Shamyyoun on 3/19/2015.
 */
public class SubSector extends SearchableItem {
    private String id;
    private String name;
    private String nameEn;

    public SubSector(String id) {
        this.id = id;
    }

    public SubSector(String id, String name, String nameEn) {
        this.id = id;
        this.name = name;
        this.nameEn = nameEn;
    }

    @Override
    public String getId() {
        return id;
    }

    public SubSector setId(String id) {
        this.id = id;
        return this;
    }

    @Override
    public String getName() {
        return name;
    }

    public SubSector setName(String name) {
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
