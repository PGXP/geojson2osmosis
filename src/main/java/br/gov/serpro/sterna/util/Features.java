package br.gov.serpro.sterna.util;

import java.util.ArrayList;
import java.util.Collections;
import static java.util.Collections.unmodifiableList;
import java.util.List;
import java.util.logging.Logger;
import static java.util.logging.Logger.getLogger;

/**
 *
 * @author SERPRO
 */
public class Features {

    private static final Logger LOG = getLogger(Features.class.getName());

    private String type = "FeatureCollection";
    private List<Feature> features = new ArrayList<>();

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
    public List<Feature> getFeatures() {
        return unmodifiableList(features);
    }

    /**
     *
     * @param features
     */
    public void setFeatures(List<Feature> features) {
        this.features = features;
    }

}
