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
    private String registerType;
    
    @XStreamAsAttribute
    private String segmentType;
    
    @XStreamAsAttribute
    private boolean isSegment;

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
    public String getRegisterType() {
        return registerType;
    }

    /**
     * @param type the type to set
     */
    public void setRegisterType(String type) {
        this.registerType = type;
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

    /**
     * @return the segmentType
     */
    public String getSegmentType() {
        return segmentType;
    }

    /**
     * @param segmentType the segmentType to set
     */
    public void setSegmentType(String segmentType) {
        this.segmentType = segmentType;
    }

    /**
     * @return the isSegment
     */
    public boolean isIsSegment() {
        return isSegment;
    }

    /**
     * @param isSegment the isSegment to set
     */
    public void setIsSegment(boolean isSegment) {
        this.isSegment = isSegment;
    }

}
