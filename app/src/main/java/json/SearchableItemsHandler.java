package json;

import java.util.ArrayList;
import java.util.List;

import datamodels.Company;
import datamodels.Constants;
import datamodels.SearchableItem;
import datamodels.Sector;
import datamodels.SubSector;

public class SearchableItemsHandler {
    private String response;
    private int itemsType;

    public SearchableItemsHandler(String response, int itemsType) {
        this.response = response;
        this.itemsType = itemsType;
    }

    public List<SearchableItem> handle() {
        List<SearchableItem> searchableItems = null;
        switch (itemsType) {
            case Constants.SEARCHABLE_TYPE_SECTOR:
                SectorsHandler sectorsHandler = new SectorsHandler(response);
                Sector[] sectors = sectorsHandler.handle();
                searchableItems = new ArrayList<SearchableItem>(sectors.length);
                for (Sector sector : sectors) {
                    searchableItems.add(sector);
                }
                break;

            case Constants.SEARCHABLE_TYPE_SUB_SECTOR:
                SubSectorsHandler subSectorsHandler = new SubSectorsHandler(response);
                SubSector[] subSectors = subSectorsHandler.handle();
                searchableItems = new ArrayList<SearchableItem>(subSectors.length);
                for (SubSector subSector : subSectors) {
                    searchableItems.add(subSector);
                }
                break;

            case Constants.SEARCHABLE_TYPE_COMPANY:
                CompaniesHandler companiesHandler = new CompaniesHandler(response);
                Company[] companies = companiesHandler.handle();
                searchableItems = new ArrayList<SearchableItem>(companies.length);
                for (Company company : companies) {
                    searchableItems.add(company);
                }
                break;
        }

        return searchableItems;
    }
}
