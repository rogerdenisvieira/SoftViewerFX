/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package softviewerfx.models;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import java.util.List;

/**
 *
 * @author roger
 */
@XStreamAlias("layouts")
public class LayoutSetBean {
    
    @XStreamImplicit
    @XStreamAlias("layout")
    private List<LayoutBean> layouts;

    /**
     * @return the layouts
     */
    public List<LayoutBean> getLayouts() {
        return layouts;
    }

    /**
     * @param layouts the layouts to set
     */
    public void setLayouts(List<LayoutBean> layouts) {
        this.layouts = layouts;
    }
}
