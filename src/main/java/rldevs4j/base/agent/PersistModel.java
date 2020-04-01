package rldevs4j.base.agent;

/**
 *
 * @author Ezequiel Beccaría
 */
public interface PersistModel {
    public boolean saveNetwork(String path);
    public void loadNetwork(String path);
}
