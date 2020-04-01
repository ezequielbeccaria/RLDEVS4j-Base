package rldevs4j.experiment;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Ezequiel Beccaría
 */
public class ExperimentResult {
    private List<Double> episodeReward;
    private List<Double> averageReward;
    private List<Long> episodeTime;
    private List<Double> averageTime;
    private Double cumReward;
    private Double cumTime;

    public ExperimentResult() {
        episodeReward = new ArrayList<>();
        averageReward = new ArrayList<>();
        episodeTime = new ArrayList<>();
        averageTime = new ArrayList<>();
        cumReward = 0D;
        cumTime = 0D;
    }
    
    public synchronized void addResult(Double reward, Long time){
        cumReward += reward;
        cumTime += time;
        
        episodeReward.add(reward);
        episodeTime.add(time);
        
        int added = episodeReward.size();
        averageReward.add(cumReward/added);
        averageTime.add(cumTime/added);
    }

    public List<Double> getEpisodeReward() {
        return episodeReward;
    }
    
    public Double getLastEpisodeReward(){
        return episodeReward.get(this.size()-1);
    }

    public List<Double> getAverageReward() {
        return averageReward;
    }
    
    public Double getLastAverageReward() {
        return averageReward.get(this.size()-1);
    }

    public List<Long> getEpisodeTime() {
        return episodeTime;
    }

    public List<Double> getAverageTime() {
        return averageTime;
    }
    
    public int size(){
        return episodeReward.size();
    }
}
