package datamodels;

/**
 * Created by Shamyyoun on 4/4/2015.
 */
public class ServiceCategory {
    private int id;
    private String name;
    private int iconResId;

    public ServiceCategory(int id) {
        this.id = id;
    }

    public ServiceCategory(int id, String name, int iconResId) {
        this.id = id;
        this.name = name;
        this.iconResId = iconResId;
    }

    public int getId() {
        return id;
    }

    public ServiceCategory setId(int id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public ServiceCategory setName(String name) {
        this.name = name;
        return this;
    }

    public int getIconResId() {
        return iconResId;
    }

    public ServiceCategory setIconResId(int iconResId) {
        this.iconResId = iconResId;
        return this;
    }
}
