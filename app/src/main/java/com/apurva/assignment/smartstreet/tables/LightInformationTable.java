package com.apurva.assignment.smartstreet.tables;

/**
 * Created by Apurva on 2/19/2017.
 */

public enum LightInformationTable {
    LIGHT_ID(0, "Light_ID", "INT NOT NULL"),
    NAME(1, "Name", "VARCHAR(255)"),
    DESCRIPTION(2, "Description", "VARCHAR(255)"),
    INSTALLATION_DATE(3, "Installation_Date", "VARCHAR(255)"),
    LATITUDE(5,"Latitude","VARCHAR(255)"),
    LONGITUDE(4, "Longitude", "VARCHAR(255)");

    private int columnIndex;
    private String columnName;
    private String columnDataType;

    private static final int size = values().length;
    LightInformationTable(int colIndex, String colName, String colType) {
        columnIndex = colIndex;
        columnName = colName;
        columnDataType = colType;
    }

    public int getColumnIndex() { return columnIndex; }
    public String getColumnName() { return columnName; }
    public String getColumnDataType() { return columnDataType; }

    public static int size() { return size; }
    public static String getTableName() { return "SMART_STREET_LIGHTING_INFORMATION"; }
}
