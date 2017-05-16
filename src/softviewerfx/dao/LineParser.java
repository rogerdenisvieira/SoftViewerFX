/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package softviewerfx.dao;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import softviewerfx.exceptions.LayoutNotFoundException;
import softviewerfx.models.AttributeBean;
import softviewerfx.models.LayoutBean;
import softviewerfx.models.LineBean;
import softviewerfx.models.DataBean;

/**
 *
 * @author roger
 */
public class LineParser {

    private static final List<String> VALUESTOFORMAT = Arrays.asList("Valor", "Mora", "Multa", "Juros", "Total", "Encargos");
    private static final LayoutParser READER = new LayoutParser();
    private static String attributeValue;

    public static ObservableList<DataBean> processLine(
            LineBean lineValue,
            String layoutName,
            String registerType,
            String segmentType) throws ParseException, LayoutNotFoundException {

        ObservableList<DataBean> values = FXCollections.observableArrayList();

        //search for layout by its name
        LayoutBean foundLayout = READER.findLayout(layoutName, registerType, segmentType);

        //check if layouts has been loaded
        if (foundLayout != null) {
            for (AttributeBean a : foundLayout.getAttributes()) {

//                System.out.println(
//                        "Layout: "
//                        + layoutName
//                        + "Tipo: "
//                        + registerType
//                        + "Begin: "
//                        + (a.getBegin() - 1)
//                        + "End: "
//                        + a.getEnd());

                attributeValue = lineValue.getLineValue().getValue().substring(a.getBegin() - 1, a.getEnd());

                values.add(new DataBean(
                        new SimpleStringProperty(a.getDescription()),
                        new SimpleStringProperty(formatValue(a.getDescription(), attributeValue))
                )
                );
            }
            return values;
        } else {
            throw new LayoutNotFoundException("Layout for " + layoutName + " type " + registerType + " not found.");
        }
    }

    //format value to show based on its description
    private static String formatValue(String descrString, String valueString) throws ParseException {

        //format date
        if (descrString.contains("Data")) {
            SimpleDateFormat dateForm = new SimpleDateFormat("ddMMyy");
            Date date = dateForm.parse(valueString);
            dateForm.applyPattern("dd/MM/yy");
            valueString = dateForm.format(date);
            return valueString;
        }

        //format value
        for (String s : VALUESTOFORMAT) {
            if (descrString.contains(s)) {
                DecimalFormat df = new DecimalFormat("#0.00");

                valueString = "R$ " + df.format(new Double(valueString) / 100);
                return valueString;
            }
        }
        return valueString;
    }
}
