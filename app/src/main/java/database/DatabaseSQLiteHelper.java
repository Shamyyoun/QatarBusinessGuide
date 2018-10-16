package database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseSQLiteHelper extends SQLiteOpenHelper {
    private Context context;

    // database info
    private static final String DATABASE_NAME = "qbg.db";
    private static final int DATABASE_VERSION = 1;

    // table service items
    public static final String TABLE_SERVICES = "services";
    public static final String SERVICES_ID = "_id";
    public static final String SERVICES_NAME_AR = "name_ar";
    public static final String SERVICES_NAME_EN = "name_en";
    public static final String SERVICES_ADDRESS_AR = "address_ar";
    public static final String SERVICES_ADDRESS_EN = "address_en";
    public static final String SERVICES_PHONE = "phone";
    public static final String SERVICES_CAT_ID = "cat_id";

    // tables creation
    private static final String SERVICE_ITEMS_CREATE = "CREATE TABLE "
            + TABLE_SERVICES + "(" + SERVICES_ID + " INTEGER PRIMARY KEY, "
            + SERVICES_NAME_AR + " TEXT NOT NULL, "
            + SERVICES_NAME_EN + " TEXT NOT NULL, "
            + SERVICES_ADDRESS_AR + " TEXT, "
            + SERVICES_ADDRESS_EN + " TEXT, "
            + SERVICES_PHONE + " TEXT NOT NULL, "
            + SERVICES_CAT_ID + " INTEGER NOT NULL"
            + ");";

    public DatabaseSQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        // create tables
        database.execSQL(SERVICE_ITEMS_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SERVICES);
        onCreate(db);
    }

}
