/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.gov.serpro.sterna.runner;

import br.gov.serpro.sterna.dao.NodesJpaController;
import br.gov.serpro.sterna.entity.Nodes;
import br.gov.serpro.sterna.util.Feature;
import br.gov.serpro.sterna.util.Features;
import com.google.gson.Gson;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.PrecisionModel;
import java.awt.HeadlessException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.time.Duration;
import static java.time.Duration.between;
import java.time.Instant;
import static java.time.Instant.now;
import java.util.Date;
import java.util.logging.Level;
import static java.util.logging.Level.INFO;
import static java.util.logging.Level.SEVERE;
import java.util.logging.Logger;
import static java.util.logging.Logger.getLogger;

/**
 *
 * @author Serpro
 */
public class FileRunner implements Runnable {

    private static final Logger LOG = getLogger(FileRunner.class.getName());

    private File file;
    private String grupo;
    private String tipo;
    private NodesJpaController dao;

    /**
     *
     */
    @Override
    public void run() {
        String filename = "";
        Instant start = now();
        try {

            try (final Reader reader = new FileReader(file)) {
                filename = file.getName();
                Features fe = (new Gson().fromJson(reader, Features.class));
                LOG.log(INFO, "Start File {0} count -> {1}", new Object[]{filename, fe.getFeatures().size()});
                for (Feature feature : fe.getFeatures()) {

                    Nodes nodes = new Nodes();

                    if (feature.getId() != null) {
                        feature.getProperties().putIfAbsent("identity", feature.getId());
                    }

                    feature.getProperties().putIfAbsent(grupo, tipo);

                    feature.getProperties().entrySet().forEach((en) -> {
                        nodes.setTags(en.getKey(), en.getValue());
                    });

                    nodes.setChangesetId(0l);
                    nodes.setTstamp(new Date());
                    nodes.setUserId(0);
                    nodes.setVersion(0);
                    GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 4_326);
                    Point point = geometryFactory.createPoint(new Coordinate(feature.getGeometry().getCoordinates()[0], feature.getGeometry().getCoordinates()[1]));
                    nodes.setGeom(point);

                    dao.create(nodes);
                }
                LOG.log(INFO, "End File {0}", filename);
            }
        } catch (HeadlessException e) {
            LOG.severe(e.getMessage());
        } catch (FileNotFoundException ex) {
            getLogger(FileRunner.class.getName()).log(SEVERE, null, ex);
        } catch (IOException ex) {
            getLogger(FileRunner.class.getName()).log(SEVERE, null, ex);
        } catch (Exception ex) {
            getLogger(FileRunner.class.getName()).log(SEVERE, null, ex);
        }
        Instant finish = now();
        LOG.log(INFO, "Work {0} -> {1} seg", new Object[]{filename, between(start, finish).getSeconds()});
    }

    /**
     *
     * @return
     */
    public NodesJpaController getDao() {
        return dao;
    }

    /**
     *
     * @param dao
     */
    public void setDao(NodesJpaController dao) {
        this.dao = dao;
    }

    /**
     *
     * @return
     */
    public File getFile() {
        return file;
    }

    /**
     *
     * @param file
     */
    public void setFile(File file) {
        this.file = file;
    }

    /**
     *
     * @return
     */
    public String getGrupo() {
        return grupo;
    }

    /**
     *
     * @param grupo
     */
    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

    /**
     *
     * @return
     */
    public String getTipo() {
        return tipo;
    }

    /**
     *
     * @param tipo
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

}
