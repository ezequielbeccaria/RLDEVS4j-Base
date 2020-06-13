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
    private List<Double> average100Reward;
    private List<Long> episodeTime;
    private List<Double> averageTime;
    private Double cumReward;
    private Double cumTime;

    public ExperimentResult() {
        episodeReward = new ArrayList<>();
        averageReward = new ArrayList<>();
        average100Reward = new ArrayList<>();
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

        if(added>100){
            double sumLast100 = 0;
            for(int i=added-100;i<added;i++)
                sumLast100 += episodeReward.get(i);
            average100Reward.add(sumLast100/100);
        }else{
            average100Reward.add(cumReward/added);
        }
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

    public List<Double> getAverage100Reward() {
        return average100Reward;
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
