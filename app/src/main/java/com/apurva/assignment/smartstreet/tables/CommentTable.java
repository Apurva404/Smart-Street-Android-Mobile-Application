package com.apurva.assignment.smartstreet.tables;

/**
 * Created by Apurva on 2/19/2017.
 */

public enum CommentTable {
    COMMENT_ID(0, "Comment_ID", "INT PRIMARY KEY"),
    COMMENT(1,"Comment","VARCHAR(255)");

    private int columnIndex;
    private String columnName;
    private String columnDataType;

    private static final int size = values().length;
    CommentTable(int colIndex, String colName, String colType) {
        columnIndex = colIndex;
        columnName = colName;
        columnDataType = colType;
    }

    public int getColumnIndex() { return columnIndex; }
    public String getColumnName() { return columnName; }
    public String getColumnDataType() { return columnDataType; }

    public static int size() { return size; }
    public static String getTableName() { return "USERS_COMMENT"; }
}
