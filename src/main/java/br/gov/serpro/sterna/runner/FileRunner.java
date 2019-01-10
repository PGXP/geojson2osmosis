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
import java.util.Date;
import java.util.logging.Level;
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

    /**
     *
     */
    @Override
    public void run() {
        try {
            NodesJpaController dao = new NodesJpaController();
            try (final Reader reader = new FileReader(file)) {
                LOG.info("Start File " + file.getName() + " -----------------------------");
                Features fe = (new Gson().fromJson(reader, Features.class));
                LOG.info("Qtde:" + fe.getFeatures().size());
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
                    GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);
                    Point point = geometryFactory.createPoint(new Coordinate(feature.getGeometry().getCoordinates()[0], feature.getGeometry().getCoordinates()[1]));
                    nodes.setGeom(point);

                    dao.create(nodes);
                }
                LOG.info("End File " + file.getName() + " -----------------------------");
            }
        } catch (HeadlessException e) {
            LOG.severe(e.getMessage());
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FileRunner.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FileRunner.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(FileRunner.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

}
