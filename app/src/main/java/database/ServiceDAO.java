package database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import datamodels.Service;

public class ServiceDAO {

    private SQLiteDatabase database;
    private DatabaseSQLiteHelper dbHelper;

    public ServiceDAO(Context context) {
        dbHelper = new DatabaseSQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    /**
     * method, used to add service to database
     */
    public void add(Service service) {
        ContentValues values = new ContentValues();
        values.put(DatabaseSQLiteHelper.SERVICES_NAME_AR, service.getNameAr());
        values.put(DatabaseSQLiteHelper.SERVICES_NAME_EN, service.getNameEn());
        values.put(DatabaseSQLiteHelper.SERVICES_ADDRESS_AR, service.getAddressAr());
        values.put(DatabaseSQLiteHelper.SERVICES_ADDRESS_EN, service.getAddressEn());
        values.put(DatabaseSQLiteHelper.SERVICES_PHONE, service.getPhone());
        values.put(DatabaseSQLiteHelper.SERVICES_CAT_ID, service.getCategoryId());

        database.insert(DatabaseSQLiteHelper.TABLE_SERVICES, null, values);
    }

    /**
     * method, used to get all services filtered by category
     */
    public List<Service> getAll(int categoryId) {
        List<Service> items = new ArrayList<Service>();

        Cursor cursor = database.query(DatabaseSQLiteHelper.TABLE_SERVICES, null,
                DatabaseSQLiteHelper.SERVICES_CAT_ID + " = " + categoryId, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Service item = cursorToItem(cursor);
            items.add(item);

            cursor.moveToNext();
        }
        cursor.close();
        return items;
    }

    /**
     * method, used to search services by name
     */
    public List<Service> searchByName(String keyword) {
        List<Service> items = new ArrayList<Service>();

        Cursor cursor = database.query(DatabaseSQLiteHelper.TABLE_SERVICES, null,
                DatabaseSQLiteHelper.SERVICES_NAME_AR + " LIKE '%" + keyword
                        + "%' OR " + DatabaseSQLiteHelper.SERVICES_NAME_EN + " LIKE '%" + keyword + "%'",
                null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Service item = cursorToItem(cursor);
            items.add(item);

            cursor.moveToNext();
        }
        cursor.close();
        return items;
    }

    /**
     * method, used to get item values from cursor row
     */
    private Service cursorToItem(Cursor cursor) {
        int id = cursor.getInt(cursor.getColumnIndex(DatabaseSQLiteHelper.SERVICES_ID));
        String nameAr = cursor.getString(cursor.getColumnIndex(DatabaseSQLiteHelper.SERVICES_NAME_AR));
        String nameEn = cursor.getString(cursor.getColumnIndex(DatabaseSQLiteHelper.SERVICES_NAME_EN));
        String addressAr = cursor.getString(cursor.getColumnIndex(DatabaseSQLiteHelper.SERVICES_ADDRESS_AR));
        String addressEn = cursor.getString(cursor.getColumnIndex(DatabaseSQLiteHelper.SERVICES_ADDRESS_EN));
        String phone = cursor.getString(cursor.getColumnIndex(DatabaseSQLiteHelper.SERVICES_PHONE));
        int categoryId = cursor.getInt(cursor.getColumnIndex(DatabaseSQLiteHelper.SERVICES_CAT_ID));

        Service item = new Service(categoryId)
                .setId(id)
                .setNameAr(nameAr)
                .setNameEn(nameEn)
                .setAddressAr(addressAr)
                .setAddressEn(addressEn)
                .setPhone(phone);

        return item;
    }

    /**
     * method, used to check if database has services or not based on count
     */
    public boolean hasItems() {
        Cursor mCount = database.rawQuery("SELECT COUNT(" + DatabaseSQLiteHelper.SERVICES_ID +
                ") FROM " + DatabaseSQLiteHelper.TABLE_SERVICES, null);
        mCount.moveToFirst();
        int count = mCount.getInt(0);
        mCount.close();

        if (count == 0)
            return false;
        else
            return true;
    }
}
