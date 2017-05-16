/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package softviewerfx.dao;

import softviewerfx.models.LayoutSetBean;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import java.io.File;
import softviewerfx.models.AttributeBean;
import softviewerfx.models.LayoutBean;

/**
 *
 * @author roger
 */
public class LayoutParser {

    private File layoutFile;
    public final String FILE_PATH = "layout.xml";

    public LayoutParser() {
        layoutFile = new File(FILE_PATH);
    }

    // carrega o arquivo completo de layout
    private LayoutSetBean loadConfig() {

        LayoutSetBean layouts = new LayoutSetBean();

        try {

            XStream stream = new XStream(new DomDriver());
            stream.processAnnotations(LayoutSetBean.class);

            layouts = (LayoutSetBean) stream.fromXML(layoutFile);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return layouts;
    }

    //find layout by its name, type of register and type of segment
    public LayoutBean findLayout(String layoutName, String registerType, String segmentType) {

        LayoutBean foundLayout = null;
        for (LayoutBean l : loadConfig().getLayouts()) {

            //check if register is a segment or other kind of register
            if (l.isIsSegment()) {
                if (l.getLayoutName().equals(layoutName) && l.getRegisterType().equals(registerType) && l.getSegmentType().equals(segmentType)) {
                    foundLayout = l;
                }
            } else {
                if (l.getLayoutName().equals(layoutName) && l.getRegisterType().equals(registerType)) {
                    foundLayout = l;
                }
            }
        }
        return foundLayout;
    }
}
