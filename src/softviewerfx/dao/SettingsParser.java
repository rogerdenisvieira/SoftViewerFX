/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package softviewerfx.dao;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

/**
 *
 * @author roger
 */
public class SettingsParser {

    private final String SETTINGS_NAME = "app.config";
    private Properties prop;
    private InputStream input;
    private List<String[]> settingsValues = new LinkedList<>();
    
    public SettingsParser() throws FileNotFoundException {
        this.prop = new Properties();
        this.input = new FileInputStream(SETTINGS_NAME);
    }

    public List<String[]> readSettings() {
        try {
            //read settings file
            prop.load(input);

            //store set of key-values
            Enumeration<?> e = prop.propertyNames();

            //iterate over each key-value
            while (e.hasMoreElements()) {
                String key = (String) e.nextElement();
                String value = prop.getProperty(key);               
                settingsValues.add(value.split(";"));                 
            }
            Collections.reverse(settingsValues);
            return settingsValues;

        } catch (Exception e) {
            e.printStackTrace();
        }
            return null;
    }
}
