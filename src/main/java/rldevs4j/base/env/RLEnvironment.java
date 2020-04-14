package rldevs4j.base.env;

import model.modeling.digraph;
import rldevs4j.base.agent.Agent;


/**
 * DEVS class responsible for containing each environment and the associated 
 * agent/worker.
 * @author Ezequiel Beccaría
 */
public class RLEnvironment extends digraph {
    private Agent agent;
    private Environment env;
        
    public RLEnvironment(Agent agent, Environment environment) {
        super("Global Container");             
        
        this.agent = agent;
        this.env = environment;
        
        //Add atomics devs
        add(agent);
        add(environment);        
        
        //add internal couplings 
        addCoupling(environment, "step", agent, "step");
        addCoupling(agent, "action", environment, "action");
    }

    @Override
    public void initialize() {
        this.agent.initialize();
        this.env.initialize();
    }

    public Agent getAgent() {
        return agent;
    }

    public Environment getEnv() {
        return env;
    }
}
