package com.eurekame.ribbon.config.rule.fatherRule;

import com.eurekame.ribbon.config.rule.fatherRule.father.ClientConfigEnabledRRRule;
import com.google.common.base.Optional;
import com.netflix.loadbalancer.AbstractServerPredicate;
import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.Server;

public abstract  class PredicateRule
        extends ClientConfigEnabledRRRule {

    /**
     * Method that provides an instance of {@link AbstractServerPredicate} to be used by this class.
     *
     */
    public abstract AbstractServerPredicate getPredicate();

    /**
     * Get a server by calling {@link AbstractServerPredicate#chooseRandomlyAfterFiltering(java.util.List, Object)}.
     * The performance for this method is O(n) where n is number of servers to be filtered.
     */
    @Override
    public Server choose(Object key) {
        ILoadBalancer lb = getLoadBalancer();
        Optional<Server> server = getPredicate().chooseRoundRobinAfterFiltering(lb.getAllServers(), key);
        if (server.isPresent()) {
            return server.get();
        } else {
            return null;
        }
    }
}
