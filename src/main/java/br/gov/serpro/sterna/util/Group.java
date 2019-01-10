/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.gov.serpro.sterna.util;

import java.util.logging.Logger;
import static java.util.logging.Logger.getLogger;

/**
 *
 * @author 70744416353
 */
public class Group {

    private static final Logger LOG = getLogger(Group.class.getName());

    private String description;
    private Integer count;

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
    public Integer getCount() {
        return count;
    }

    /**
     *
     * @param count
     */
    public void setCount(Integer count) {
        this.count = count;
    }

}
