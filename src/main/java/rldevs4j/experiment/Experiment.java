package rldevs4j.experiment;

import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
    private int id;
    private String name;
    private long expTotalRunningTime;
    private INDArray expRunningTime;
    protected final int repetitions;
    private boolean logging;
    protected final Logger logger;
    private boolean plot;
    private String resultsFilePath;
    protected Random rnd = Nd4j.getRandom();
    

    public Experiment(int expId, String expName, int repetitions, boolean logging, boolean plot, String resultsFilePath, Integer seed) {
        this.id = expId;
        this.name = expName;
        this.repetitions = repetitions;
        this.expRunningTime = Nd4j.zeros(repetitions);
        this.logging = logging;
        this.plot = plot;
        this.resultsFilePath = resultsFilePath;
        this.rnd = Nd4j.getRandom();
        if(seed != null && seed > 0)
            rnd.setSeed(seed);
        
        
        logger = Logger.getLogger(String.valueOf(id)+"-"+name);          
        //Setup Text File logging        
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("MM-dd_HHmm");
            FileHandler fh = null;
            fh = new FileHandler(String.valueOf(id)+"-"+name+"_"+sdf.format(Calendar.getInstance().getTime()) + ".log", 0, 1);
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
            long startTime = System.currentTimeMillis();
            experimentsResults.add(experiment(this.rnd, i+1));
            long endTime = System.currentTimeMillis();            
            expRunningTime.putScalar(i, (endTime - startTime));
            if(logging)
                logger.log(Level.INFO, "Experiment {0} Terminated. Elapsed time: {1} sec.", new Object[]{i, formatter.format(expRunningTime.div(TO_SECONDS).getDouble(0))});
            if(resultsFilePath != null)
                writeResults(experimentsResults, true);
        }        
        long endTotalTime = System.currentTimeMillis();
        expTotalRunningTime = (endTotalTime - startTotalTime);  //divide by 1000000 to get milliseconds.
        if(logging)
            finalLog();
        if(resultsFilePath != null)
            writeResults(experimentsResults, false);
        if(plot)
            plotResults(experimentsResults);
    }
    
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
    
    private void writeResults(List<ExperimentResult> results, boolean backup){
        FileWriter writer;        
        try {
            String filename = resultsFilePath+String.valueOf(id)+"-"+name;
            if(backup)
                filename = filename.concat(".bkp"+results.size());
            writer = new FileWriter(filename);
            //write headers 
            List<String> headers = new ArrayList<>();
            headers.add("agent");
            headers.add("experiment");            
            headers.add("episode");
            headers.add("reward");
            headers.add("avg_reward");
            headers.add("time");
            headers.add("avg_time");
            CSVUtils.writeLine(writer, headers, '|');
            //write data
            for(int j=0;j<results.size();j++){
                ExperimentResult result = results.get(j);
                for(int i=0;i<result.size();i++){
                    List<String> line = new ArrayList<>();
                    line.add(name); // experimet name
                    line.add(String.valueOf(j+1)); // repetition number
                    line.add(String.valueOf(i+1)); // episode number
                    line.add(formatter.format(result.getEpisodeReward().get(i))); // episode reward
                    line.add(formatter.format(result.getAverageReward().get(i))); // episode average reward
                    line.add(formatter.format(result.getEpisodeTime().get(i)/TO_SECONDS)); // episode running time in seconds
                    line.add(formatter.format(result.getAverageTime().get(i)/TO_SECONDS)); // repetition running time in seconds                    
                    
                    CSVUtils.writeLine(writer, line, '|');
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
            plot.addLinePlot("Avg. Reward",  CollectionsUtils.DoubleToArray(results.get(i).getAverageReward()));            
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
