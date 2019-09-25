package com.onevizion.uitest.api.vo.api.v3;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ImpRunError implements Comparable<ImpRunError> {

    private String dbMessage;
    private String warningType;
    private String rowNumber;
    private String sqlStatement;
    private String mappingName;

    @JsonProperty("db_message")
    public String getDbMessage() {
        return dbMessage;
    }

    public void setDbMessage(String dbMessage) {
        this.dbMessage = dbMessage;
    }

    @JsonProperty("warning_type")
    public String getWarningType() {
        return warningType;
    }

    public void setWarningType(String warningType) {
        this.warningType = warningType;
    }

    @JsonProperty("row_number")
    public String getRowNumber() {
        return rowNumber;
    }

    public void setRowNumber(String rowNumber) {
        this.rowNumber = rowNumber;
    }

    @JsonProperty("sql_statement")
    public String getSqlStatement() {
        return sqlStatement;
    }

    public void setSqlStatement(String sqlStatement) {
        this.sqlStatement = sqlStatement;
    }

    @JsonProperty("mapping_name")
    public String getMappingName() {
        return mappingName;
    }

    public void setMappingName(String mappingName) {
        this.mappingName = mappingName;
    }

    @Override
    public int compareTo(ImpRunError o) {
        int val1 = this.dbMessage.compareTo(o.dbMessage);
        if (val1 != 0) {
            return val1;
        }
        int val2 = this.warningType.compareTo(o.warningType);
        if (val2 != 0) {
            return val2;
        }
        int val3 = this.rowNumber.compareTo(o.rowNumber);
        if (val3 != 0) {
            return val3;
        }
        int val4 = this.sqlStatement.compareTo(o.sqlStatement);
        if (val4 != 0) {
            return val4;
        }
        return this.mappingName.compareTo(o.mappingName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dbMessage, mappingName, rowNumber, sqlStatement, warningType);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        ImpRunError other = (ImpRunError) obj;
        return Objects.equals(dbMessage, other.dbMessage) && Objects.equals(mappingName, other.mappingName)
                && Objects.equals(rowNumber, other.rowNumber) && Objects.equals(sqlStatement, other.sqlStatement)
                && Objects.equals(warningType, other.warningType);
    }

    @Override
    public String toString() {
        return "ImpRunError [dbMessage=" + dbMessage + ", warningType=" + warningType + ", rowNumber=" + rowNumber
                + ", sqlStatement=" + sqlStatement + ", mappingName=" + mappingName + "]";
    }

}