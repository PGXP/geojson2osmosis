/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.gov.serpro.sterna;

import br.gov.serpro.sterna.dao.NodesJpaController;
import br.gov.serpro.sterna.runner.FileRunner;
import br.gov.serpro.sterna.util.Feature;
import java.io.File;
import static java.lang.Runtime.getRuntime;
import java.time.Duration;
import static java.time.Duration.between;
import java.time.Instant;
import static java.time.Instant.now;
import java.util.concurrent.ExecutorService;
import static java.util.concurrent.Executors.newFixedThreadPool;
import static java.util.concurrent.TimeUnit.MINUTES;
import java.util.logging.Level;
import static java.util.logging.Level.INFO;
import java.util.logging.Logger;
import static java.util.logging.Logger.getLogger;

/**
 *
 * @author SERPRO
 */
public class App {

    private static final Logger LOG = getLogger(Feature.class.getName());
    private static final int MAX_THREADS = getRuntime().availableProcessors();

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        LOG.log(INFO, "geojson2osmosis init with {0} processors", MAX_THREADS);
        Instant start = now();

        NodesJpaController dao = new NodesJpaController();
        ExecutorService executorGerador = newFixedThreadPool(MAX_THREADS);

        File folder = new File(args[0]);
        File[] listOfFiles = folder.listFiles();
        for (File listOfFile : listOfFiles) {
            if (listOfFile.isFile()) {
                FileRunner fr = new FileRunner();
                fr.setFile(listOfFile);
                fr.setGrupo(getName(listOfFile.getName(), 0));
                fr.setTipo(getName(listOfFile.getName(), 1));
                fr.setDao(dao);
                executorGerador.execute(fr);
            }
        }
        executorGerador.shutdown();

        try {
            executorGerador.awaitTermination(720, MINUTES);
        } catch (InterruptedException ie) {
            LOG.severe(ie.getLocalizedMessage());
        }

        dao.shutdown();
        Instant finish = now();
        LOG.log(INFO, "geojson2osmosis shutdown -> {0} seg", new Object[]{between(start, finish).getSeconds()});

    }

    private static String getName(String fileName, Integer index) {

        if (!fileName.contains("-")) {
            return null;
        }

        if (!fileName.contains(".")) {
            return null;
        }

        String[] tokens = fileName.split("\\.(?=[^\\.]+$)");
        String[] group = tokens[0].split("\\-(?=[^\\-]+$)");

        return group[index];
    }

}
// java -jar -server -Xms2g -Xmx4g -XX:+UseParallelGC geojson2osmosis-1.0.0-jar-with-dependencies.jar "folder path where are geojsons"
