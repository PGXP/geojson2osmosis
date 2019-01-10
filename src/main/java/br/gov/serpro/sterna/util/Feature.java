/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.gov.serpro.sterna.util;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;
import static java.util.logging.Logger.getLogger;

/**
 *
 * @author 70744416353
 */
public class Feature {

    private static final Logger LOG = getLogger(Feature.class.getName());

    private String type = "Feature";
    private Map<String, String> properties;
    private Point geometry;
    private String id;
    private String signature;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Map<String, String> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, String> properties) {
        this.properties = properties;
    }

    public Point getGeometry() {
        return geometry;
    }

    public void setGeometry(Point geometry) {
        this.geometry = geometry;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

}
