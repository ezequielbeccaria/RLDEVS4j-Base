package rldevs4j.experiment;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import javax.swing.JFrame;
import org.math.plot.Plot2DPanel;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.api.rng.Random;
import org.nd4j.linalg.factory.Nd4j;
import rldevs4j.utils.CSVUtils;
import rldevs4j.utils.CollectionsUtils;

/**
 * Abstract class that execute experiments and collect info about it.
 * @author Ezequiel Beccaría
 */
public abstract class Experiment {
    protected NumberFormat formatter = new DecimalFormat("#0.00");         
    private double TO_SECONDS = 1000;
    protected String name;
    private long expTotalRunningTime;
    private INDArray expRunningTime;
    protected final int repetitions;
    protected boolean logging;
    protected final Logger logger;
    protected boolean plot;
    protected String resultsFilePath;
    protected Random rnd = Nd4j.getRandom();
    

    public Experiment(String expName, int repetitions, boolean logging, boolean plot, String resultsFilePath, Integer seed) {
        this.name = expName;
        this.repetitions = repetitions;
        this.expRunningTime = Nd4j.zeros(repetitions);
        this.logging = logging;
        this.plot = plot;
        this.resultsFilePath = resultsFilePath;
        File f = new File(resultsFilePath);
        if (!Files.exists(f.toPath())) {
            try {
                Files.createDirectories(f.toPath());
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(-1);
            }
        }
        this.rnd = Nd4j.getRandom();
        if(seed != null && seed > 0)
            rnd.setSeed(seed);
        
        
        logger = Logger.getLogger(name);
        //Setup Text File logging        
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("MM-dd_HHmm");
            FileHandler fh = null;
            fh = new FileHandler(name+"_"+sdf.format(Calendar.getInstance().getTime()) + ".log", 0, 1);
            fh.setFormatter(new SimpleFormatter());
            logger.addHandler(fh);
            logger.addHandler(new ConsoleHandler());
            logger.setUseParentHandlers(false);
        } catch (IOException | SecurityException e) {
            logger.log(Level.SEVERE, e.getLocalizedMessage());
        }        
    }        
    
    public void run(){        
        List<ExperimentResult> experimentsResults = new ArrayList<>();
        long startTotalTime = System.currentTimeMillis();
        for(int i=0;i<repetitions;i++){
            if(existsResults(i))
                continue;
            logger.log(Level.INFO, "Experiment {0} Started.", new Object[]{i});
            long startTime = System.currentTimeMillis();
            experimentsResults.add(experiment(this.rnd, i+1));
            long endTime = System.currentTimeMillis();            
            expRunningTime.putScalar(i, (endTime - startTime));
            if(logging)
                logger.log(Level.INFO, "Experiment {0} Terminated. Elapsed time: {1} sec.", new Object[]{i, formatter.format(expRunningTime.div(TO_SECONDS).getDouble(0))});
            if(resultsFilePath != null)
                writeResults(experimentsResults.get(experimentsResults.size()-1), i, resultsFilePath+name+"_"+(i+1)+".csv");
        }        
        long endTotalTime = System.currentTimeMillis();
        expTotalRunningTime = (endTotalTime - startTotalTime);  //divide by 1000000 to get milliseconds.
        if(logging)
            finalLog();
        if(resultsFilePath != null)
            mergeAllResults();
        if(plot)
            plotResults(experimentsResults);
    }

    public abstract void test();
    
    public void finalLog(){
        logger.log(Level.INFO, "EXPERIMENT FINISHED. Total time: {0} sec.", formatter.format(getExpTotalRunningTimeSeconds()));
    }

    public long getExpTotalRunningTimeNanosec() {
        return expTotalRunningTime;
    }
    
    public double getExpTotalRunningTimeMillisec() {
        return expTotalRunningTime;
    }
    
     public double getExpTotalRunningTimeSeconds() {
        return expTotalRunningTime/TO_SECONDS;
    }
    
    public INDArray getExpRunningTimeNanosec() {
        return expRunningTime;
    }
 
    public INDArray getExpRunningTimeMillisec() {
        return expRunningTime;
    }
    
    public INDArray getExpRunningTimeSeconds() {
        return expRunningTime.div(TO_SECONDS);
    }

    private boolean existsResults(int repetition){
        String filename = resultsFilePath+name+"_"+(repetition+1)+".csv";
        File f = new File(filename);
        return f.exists() && !f.isDirectory();
    }
    
    protected void writeResults(ExperimentResult result, int repetition, String filename){
        FileWriter writer;        
        try {
            writer = new FileWriter(filename);
            //write headers 
            List<String> headers = new ArrayList<>();
            headers.add("agent");
            headers.add("experiment");            
            headers.add("episode");
            headers.add("reward");
            headers.add("avg_reward");
            headers.add("avg_100_reward");
            headers.add("time");
            headers.add("avg_time");
            CSVUtils.writeLine(writer, headers, '|');
            //write data
            for(int i=0;i<result.size();i++){
                List<String> line = new ArrayList<>();
                line.add(name); // experimet name
                line.add(String.valueOf(repetition+1)); // repetition number
                line.add(String.valueOf(i+1)); // episode number
                line.add(formatter.format(result.getEpisodeReward().get(i))); // episode reward
                line.add(formatter.format(result.getAverageReward().get(i))); // episode average reward
                line.add(formatter.format(result.getAverage100Reward().get(i))); // episode average reward
                line.add(formatter.format(result.getEpisodeTime().get(i)/TO_SECONDS)); // episode running time in seconds
                line.add(formatter.format(result.getAverageTime().get(i)/TO_SECONDS)); // repetition running time in seconds

                CSVUtils.writeLine(writer, line, '|');
            }
            writer.flush();
            writer.close();                
        } catch (IOException ex) {
            logger.log(Level.SEVERE, null, ex);
        }
    }

    private void mergeAllResults(){
        FileWriter writer;
        try {
            String filename = resultsFilePath+name+"_all.csv";
            writer = new FileWriter(filename);
            //write headers
            List<String> headers = new ArrayList<>();
            headers.add("agent");
            headers.add("experiment");
            headers.add("episode");
            headers.add("reward");
            headers.add("avg_reward");
            headers.add("avg_100_reward");
            headers.add("time");
            headers.add("avg_time");
            CSVUtils.writeLine(writer, headers, '|');

            for(int i=1;i<=repetitions;i++){
                String f = resultsFilePath+name+"_"+i+".csv";
                List<String[]> lines = CSVUtils.readLines(f, '|');
                for(int j=1;j<lines.size();j++){
                    CSVUtils.writeLine(writer, Arrays.asList(lines.get(j)), '|');
                }
            }
            writer.flush();
            writer.close();
        } catch (IOException ex) {
            logger.log(Level.SEVERE, null, ex);
        }
    }
    
    private void plotResults(List<ExperimentResult> results){
        // create your PlotPanel (you can use it as a JPanel)
        Plot2DPanel plot = new Plot2DPanel();

        for(int i=0;i<results.size();i++){
            // add a line plot to the PlotPanel                
//            plot.addLinePlot("Avg. Reward",  CollectionsUtils.DoubleToArray(results.get(i).getAverageReward()));
            plot.addLinePlot("Avg. 100 Reward",  CollectionsUtils.DoubleToArray(results.get(i).getAverage100Reward()));
        }
        // put the PlotPanel in a JFrame, as a JPanel
        JFrame frame = new JFrame("Results");
        frame.setSize(600, 600);
        frame.setContentPane(plot);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    /**
     * Experiment implementation with prefixed seed.
     * @param rnd
     * @param experiment
     * @return 
     */
    public abstract ExperimentResult experiment(Random rnd, int experiment);
}
