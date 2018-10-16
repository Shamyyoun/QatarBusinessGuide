package datamodels;

import java.io.Serializable;

/**
 * Created by Shamyyoun on 3/22/2015.
 */
public abstract class SearchableItem implements Serializable {
    public abstract String getName();

    public abstract String getNameEn();

    public abstract String getThumbnail();

    public abstract String getId();
}
