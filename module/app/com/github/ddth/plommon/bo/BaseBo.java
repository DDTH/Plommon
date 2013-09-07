package com.github.ddth.plommon.bo;

import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.annotate.JsonProperty;

import com.github.ddth.plommon.utils.DPathUtils;
import com.github.ddth.plommon.utils.JsonUtils;

/**
 * Base class for application BOs.
 * 
 * @author Thanh Nguyen <btnguyen2k@gmail.com>
 * @since 0.3.0
 */
public class BaseBo {

    @JsonProperty
    private Map<String, Object> attributes = new HashMap<String, Object>();

    /**
     * Gets a BO's attribute.
     * 
     * @param dPath
     * @return
     * @see DPathUtils
     */
    protected Object getAttribute(String dPath) {
        return DPathUtils.getValue(attributes, dPath);
    }

    /**
     * Gets a BO's attribute.
     * 
     * @param dPath
     * @param clazz
     * @return
     * @see DPathUtils
     */
    protected <T> T getAttribute(String dPath, Class<T> clazz) {
        return DPathUtils.getValue(attributes, dPath, clazz);
    }

    /**
     * Sets a BO's attribute.
     * 
     * @param dPath
     * @param value
     * @return
     * @see DPathUtils
     */
    protected BaseBo setAttribute(String dPath, Object value) {
        DPathUtils.setValue(attributes, dPath, value);
        return this;
    }

    /**
     * Populates the BO with data from a Java map.
     * 
     * @param data
     * @return
     */
    synchronized public BaseBo fromMap(Map<String, Object> data) {
        attributes = new HashMap<String, Object>();
        if (data != null) {
            attributes.putAll(data);
        }
        return this;
    }

    /**
     * Serializes the BO to a Java map.
     * 
     * @return
     */
    synchronized public Map<String, Object> toMap() {
        Map<String, Object> result = new HashMap<String, Object>();
        if (attributes != null) {
            result.putAll(attributes);
        }
        return result;
    }

    /**
     * Populates the BO with data from a JSON string.
     * 
     * @param jsonString
     * @return
     */
    synchronized public BaseBo fromJson(String jsonString) {
        BaseBo other = JsonUtils.fromJsonString(jsonString, BaseBo.class);
        if (other != null) {
            this.attributes = other.attributes;
        }
        return this;
    }

    /**
     * Serializes the BO to JSON string.
     * 
     * @return
     */
    synchronized public String toJson() {
        return JsonUtils.toJsonString(this);
    }
}
