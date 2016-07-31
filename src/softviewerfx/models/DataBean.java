/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package softviewerfx.models;

import javafx.beans.property.StringProperty;

/**
 *
 * @author roger
 */
public class DataBean {


    private StringProperty dataName;
    private StringProperty dataValue;
    
    public DataBean(StringProperty name, StringProperty value) {
        this.dataName = name;
        this.dataValue = value;
        
    }

    public String getDataValue() {
        return dataValue.get();
    }

    public void setDataValue(String value) {
        dataValue.set(value);
    }

    public StringProperty dataValueProperty() {
        return dataValue;
    }
    

    public String getDataName() {
        return dataName.get();
    }

    public void setDataName(String value) {
        dataName.set(value);
    }

    public StringProperty dataProperty() {
        return dataName;
    }


    
}
