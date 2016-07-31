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
public class LayoutReader {

    private File layoutFile;
    public final String FILE_PATH = "layout.xml";

    public LayoutReader() {
        //this.layoutFile = new File(FILE_PATH);

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

    // busca o layout específico pelo nome do módulo
    public LayoutBean findLayout(String moduleName) {
        LayoutBean foundLayout = null;
        for (LayoutBean l : loadConfig().getLayouts()) {
            if (l.getModule().equals(moduleName)) {
                foundLayout = l;
            }
        }

        return foundLayout;
    }

}
