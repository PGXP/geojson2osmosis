
package br.gov.serpro.sterna.util;

import java.util.Collections;
import java.util.Map;
import java.util.logging.Logger;
import static java.util.logging.Logger.getLogger;

/**
 *
 * @author SERPRO
 */
public class Feature {

    private static final Logger LOG = getLogger(Feature.class.getName());

    private String type = "Feature";
    private Map<String, String> properties;
    private Point geometry;
    private String id;
    private String signature;

    /**
     *
     * @return
     */
    public String getType() {
        return type;
    }

    /**
     *
     * @param type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     *
     * @return
     */
    public Map<String, String> getProperties() {
        return Collections.unmodifiableMap(properties);
    }

    /**
     *
     * @param properties
     */
    public void setProperties(Map<String, String> properties) {
        this.properties = properties;
    }

    /**
     *
     * @return
     */
    public Point getGeometry() {
        return geometry;
    }

    /**
     *
     * @param geometry
     */
    public void setGeometry(Point geometry) {
        this.geometry = geometry;
    }

    /**
     *
     * @return
     */
    public String getId() {
        return id;
    }

    /**
     *
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     *
     * @return
     */
    public String getSignature() {
        return signature;
    }

    /**
     *
     * @param signature
     */
    public void setSignature(String signature) {
        this.signature = signature;
    }

}
