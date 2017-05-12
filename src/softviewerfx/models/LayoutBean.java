/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package softviewerfx.models;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import java.util.List;

/**
 *
 * @author roger
 */
@XStreamAlias("layout")
public class LayoutBean {

    @XStreamAsAttribute
    private String layoutName;
    
    @XStreamAsAttribute
    private String type;

    @XStreamImplicit
    @XStreamAlias("attribute")
    private List<AttributeBean> attributes;

    /**
     * @return the module
     */
    public String getLayoutName() {
        return layoutName;
    }

    /**
     * @param layoutName the module to set
     */
    public void setSetLayoutName(String name) {
        this.layoutName = name;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the attributes
     */
    public List<AttributeBean> getAttributes() {
        return attributes;
    }

    /**
     * @param attributes the attributes to set
     */
    public void setAttributes(List<AttributeBean> attributes) {
        this.attributes = attributes;
    }

}
