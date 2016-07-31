/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package softviewerfx.models;
import javafx.beans.property.*;

/**
 *
 * @author Roger
 */
public class LineBean {
    private StringProperty lineIndex;
    private StringProperty lineValue;

    public LineBean(StringProperty lineIndex, StringProperty lineValue) {
        this.lineIndex = lineIndex;
        this.lineValue = lineValue;
    }
    
    public StringProperty lineIndexProperty(){
        return lineIndex;
    }
    
    public StringProperty lineValueProperty(){
        return lineValue;
    }

    public StringProperty getLineIndex() {
        return lineIndex;
    }

    public void setLineIndex(StringProperty lineIndex) {
        this.lineIndex = lineIndex;
    }

    public StringProperty getLineValue() {
        return lineValue;
    }

    public void setLineValue(StringProperty lineValue) {
        this.lineValue = lineValue;
    }
    
    
}
