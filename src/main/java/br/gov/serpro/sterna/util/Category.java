package br.gov.serpro.sterna.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;
import static java.util.logging.Logger.getLogger;

/**
 *
 * @author SERPRO
 */
public class Category {

    private static final Logger LOG = getLogger(Category.class.getName());

    private String description;
    private Integer groups;
    private List list = new ArrayList<>();

    /**
     *
     * @return
     */
    public String getDescription() {
        return description;
    }

    /**
     *
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     *
     * @return
     */
    public Integer getGroups() {
        return groups;
    }

    /**
     *
     * @param groups
     */
    public void setGroups(Integer groups) {
        this.groups = groups;
    }

    /**
     *
     * @return
     */
    public List getList() {
        return Collections.unmodifiableList(list);
    }

    /**
     *
     * @param list
     */
    public void setList(List list) {
        this.list = list;
    }

}
