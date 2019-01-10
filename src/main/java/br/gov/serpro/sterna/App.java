/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.gov.serpro.sterna;

import br.gov.serpro.sterna.runner.FileRunner;
import br.gov.serpro.sterna.util.Feature;
import java.io.File;
import static java.lang.Runtime.getRuntime;
import java.util.concurrent.ExecutorService;
import static java.util.concurrent.Executors.newFixedThreadPool;
import static java.util.concurrent.TimeUnit.MINUTES;
import java.util.logging.Logger;
import static java.util.logging.Logger.getLogger;

/**
 *
 * @author 70744416353
 */
public class App {

    private static final Logger LOG = getLogger(Feature.class.getName());
    private static final int MAX_THREADS = getRuntime().availableProcessors();

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        ExecutorService executorGerador = newFixedThreadPool(MAX_THREADS);

        File folder = new File(args[0]);
        File[] listOfFiles = folder.listFiles();

        for (File listOfFile : listOfFiles) {
            if (listOfFile.isFile()) {
                FileRunner fr = new FileRunner();
                fr.setFile(listOfFile);
                fr.setGrupo(getGrupo(listOfFile.getName()));
                fr.setTipo(getNome(listOfFile.getName()));
                executorGerador.execute(fr);
            }
        }

        executorGerador.shutdown();

        try {
            executorGerador.awaitTermination(5, MINUTES);
        } catch (InterruptedException ie) {
            LOG.severe(ie.getLocalizedMessage());
        }
    }

    private static String getGrupo(String fileName) {

        if (!fileName.contains("-")) {
            return null;
        }

        if (!fileName.contains(".")) {
            return null;
        }

        String[] tokens = fileName.split("\\.(?=[^\\.]+$)");
        String[] group = tokens[0].split("\\-(?=[^\\-]+$)");

        return group[0];
    }

    private static String getNome(String fileName) {

        if (!fileName.contains("-")) {
            return null;
        }

        if (!fileName.contains(".")) {
            return null;
        }

        String[] tokens = fileName.split("\\.(?=[^\\.]+$)");
        String[] group = tokens[0].split("\\-(?=[^\\-]+$)");

        return group[1];

    }

}
// java -jar -server -Xms2g -Xmx4g -XX:+UseParallelGC sternamining-1.0.0-jar-with-dependencies.jar "folder path where are geojsons"
