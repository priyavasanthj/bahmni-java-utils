package org.bahmni.csv;

import java.lang.reflect.Field;

public class CSVColumns<T extends CSVEntity> {
    private String[] headerNames;

    public CSVColumns(String[] headerNames) {
        this.headerNames = headerNames;
    }

    public void setValue(T entity, Field field, String[] aRow) throws IllegalAccessException {
        CSVHeader headerAnnotation = field.getAnnotation(CSVHeader.class);
        if (headerAnnotation == null)
            return;

        String headerValueInClass = headerAnnotation.name();
        field.setAccessible(true);
        field.set(entity, aRow[getPosition(headerValueInClass)]);
    }

    private int getPosition(String headerValueInClass) {
        for (int i = 0; i < headerNames.length; i++) {
            String headerName = headerNames[i];
            if (headerName.equals(headerValueInClass))
                return i;
        }
        throw new RuntimeException("No Column found. " + headerValueInClass);
    }
}
