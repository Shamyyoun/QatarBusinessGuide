package datamodels;

import android.content.Context;

import java.io.InputStream;

import database.ServiceDAO;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

/**
 * Created by Shamyyoun on 3/24/2015.
 */
public class DataLoader {

    /**
     * method, used to load services from excel sheet and add it to db in async thread
     */
    public static void storeServices(final Context context) {
        // Create and open dao
        ServiceDAO serviceDAO = new ServiceDAO(context);
        serviceDAO.open();

        // Read excel file
        try {
            InputStream inputStream = context.getAssets().open("services.xls");
            Workbook w = Workbook.getWorkbook(inputStream);

            // Loop for sheets
            Sheet sheet = null;
            for (int i = 0; i < w.getNumberOfSheets(); i++) {
                // Get sheet
                sheet = w.getSheet(i);

                // Loop for rows
                for (int j = 0; j < sheet.getRows(); j++) {
                    // Create ServiceItem object
                    Service service = new Service(i);

                    // Loop for columns
                    for (int k = 0; k < sheet.getColumns(); k++) {
                        // Get cell value
                        Cell cell = sheet.getCell(k, j);
                        String cellValue = cell.getContents();

                        // Check cell value
                        if (cellValue.isEmpty())
                            // null it
                            cellValue = null;

                        // Set service appropriate attribute
                        switch (k) {
                            case 0:
                                service.setNameAr(cellValue);
                                break;

                            case 1:
                                service.setNameEn(cellValue);
                                break;

                            case 2:
                                service.setAddressAr(cellValue);
                                break;

                            case 3:
                                service.setAddressEn(cellValue);
                                break;

                            case 4:
                                service.setPhone(cellValue);
                                break;
                        }
                    }

                    // check if service name ar is null
                    if (service.getNameAr() == null) {
                        // Reached end of valid rows >> break
                        break;
                    } else {
                        // Add object to database
                        serviceDAO.add(service);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        serviceDAO.close();
    }
}
