package co.com.appsource.outsafetyapp.db_helper.Tables;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import co.com.appsource.outsafetyapp.db_helper.SQLOutsafetyHelper;

/**
 * Created by JANUS on 26/11/2015.
 */
public class AreaDataSource {

    // Database fields
    private SQLiteDatabase database;
    private SQLOutsafetyHelper dbHelper;

    private String[] allColumns = {Area.COLUMN_ID
            , Area.COLUMN_ID_EMPRESA
            , Area.COLUMN_ID_AREA
            , Area.COLUMN_DESCRIPCION};

    public AreaDataSource(Context context) {
        dbHelper = new SQLOutsafetyHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Area CreateArea(String intIdEmpresa, String intIdArea, String strDescripcion) {

        ContentValues values = new ContentValues();
        values.put(Area.COLUMN_ID_EMPRESA, intIdEmpresa);
        values.put(Area.COLUMN_ID_AREA, intIdArea);
        values.put(Area.COLUMN_DESCRIPCION, strDescripcion);


        long insertId = database.insert(Area.NAME
                , null,
                values);
        Cursor cursor = database.query(Area.NAME
                , allColumns
                , Area.COLUMN_ID + " = " + insertId
                , null
                , null
                , null
                , null);
        cursor.moveToFirst();
        Area newArea = cursorToArea(cursor);
        cursor.close();
        return newArea;
    }


    public Area GetArea(String intIdArea) {

        Cursor cursor = database.query(Area.NAME
                , allColumns
                , Area.COLUMN_ID_AREA + " = '" + intIdArea + "'"
                , null
                , null
                , null
                , null);
        cursor.moveToFirst();
        Area newArea = cursorToArea(cursor);
        cursor.close();
        return newArea;
    }

    private Area cursorToArea(Cursor cursor) {
        Area comment = new Area();

        comment.setId(cursor.getLong(0));
        comment.setIntIdEmpresa(cursor.getString(1));
        comment.setIntIdArea(cursor.getString(2));
        comment.setStrDescripcion(cursor.getString(3));

        return comment;
    }

    public List<Area> GetAll() {
        List<Area> lstResult = new ArrayList<Area>();
        Cursor cursor = database.query(Area.NAME
                , allColumns
                , null
                , null
                , null
                , null
                , null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Area newItemResult = cursorToArea(cursor);
            lstResult.add(newItemResult);
            cursor.moveToNext();
        }
        cursor.close();
        return lstResult;
    }

    public List<Area> GetAreasByCentroTrabajo(String idCentroTrabajo) {
        List<Area> lstResult = new ArrayList<Area>();
        Cursor cursor = database.query(Area.NAME
                , allColumns
                , Area.COLUMN_ID_EMPRESA+ " = '" + idCentroTrabajo + "'"
                , null
                , null
                , null
                , null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Area newItemResult = cursorToArea(cursor);
            lstResult.add(newItemResult);
            cursor.moveToNext();
        }
        cursor.close();
        return lstResult;
    }

    public int GetCount() {
        Cursor cursor = database.query(Area.NAME
                , allColumns
                , null
                , null
                , null
                , null
                , null);

        return cursor.getCount();
    }

    public void truncateTable() {
        database.execSQL("delete from " + Area.NAME);
    }
}
