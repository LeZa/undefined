package com.eurekame.ribbon.config.rule;

import com.eurekame.ribbon.config.rule.fatherRule.father.ClientConfigEnabledRRRule;
import com.netflix.loadbalancer.*;

import java.util.Iterator;
import java.util.List;

public class BestRule
        extends ClientConfigEnabledRRRule {

    private LoadBalancerStats loadBalancerStats;

    public BestRule() {
    }

    public Server choose(Object key) {
        if (this.loadBalancerStats == null) {
            return super.choose(key);
        } else {
            List<Server> serverList = this.getLoadBalancer().getAllServers();
            int minimalConcurrentConnections = 2147483647;
            long currentTime = System.currentTimeMillis();
            Server chosen = null;
            Iterator var7 = serverList.iterator();

            while(var7.hasNext()) {
                Server server = (Server)var7.next();
                ServerStats serverStats = this.loadBalancerStats.getSingleServerStat(server);
                if (!serverStats.isCircuitBreakerTripped(currentTime)) {
                    int concurrentConnections = serverStats.getActiveRequestsCount(currentTime);
                    if (concurrentConnections < minimalConcurrentConnections) {
                        minimalConcurrentConnections = concurrentConnections;
                        chosen = server;
                    }
                }
            }

            if (chosen == null) {
                return super.choose(key);
            } else {
                return chosen;
            }
        }
    }

    public void setLoadBalancer(ILoadBalancer lb) {
        super.setLoadBalancer(lb);
        if (lb instanceof AbstractLoadBalancer) {
            this.loadBalancerStats = ((AbstractLoadBalancer)lb).getLoadBalancerStats();
        }

    }

}
