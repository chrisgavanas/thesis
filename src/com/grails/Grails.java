package com.grails;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.data.trie.Trie;
import com.grails.scc.SCCBuilder;
import com.grails.scc.SCComponent;
import com.grails.wrappers.StartingRankWrapper;

public class Grails {
    private Trie trie;
    private SCCBuilder sccBuilder;
    private StartingRankWrapper[] startingRank;
    private int labelingLevel;

    public Grails(int labelingLevel) {
        this.trie = new Trie();
        this.sccBuilder = new SCCBuilder(trie, labelingLevel);
        this.labelingLevel = labelingLevel;
        this.startingRank = new StartingRankWrapper[labelingLevel];
        for (int i = 0; i < labelingLevel; i++)
            startingRank[i] = new StartingRankWrapper(1);
    }

    public void prepareIndex(Long from, Long to) {
        sccBuilder.addVertex(from, to);
    }

    /*
     * Calculates strong connected components of
     * directed cyclic graph by running Kosaraju's algorithm
     * @link = https://en.wikipedia.org/wiki/Kosaraju%27s_algorithm
     */
    public void findSCCs() {
        sccBuilder.findSCCs();
    }

    public void matchSCCs(Long from, Long to) {
        SCComponent fromSCC = trie.lookUp(from);
        SCComponent toSCC = trie.lookUp(to);

        if (fromSCC == null || toSCC == null)
            return;

        fromSCC.addEdge(toSCC);
    }

    public void buildIndex(int labelLevel) {
        List<SCComponent> sccs = sccBuilder.getSCComponents();
        Set<Long> visited = new HashSet<Long>();

        sccs.stream()
            .filter(scc -> !visited.contains(scc.getKey()))
            .forEach(scc -> {
                visited.add(scc.getKey());
                scc.fixLabels(visited, startingRank[labelLevel], labelLevel);
            });

        sccBuilder = null;
    }

    public boolean reachbility(Long from, Long to) {
        SCComponent fromScc = trie.lookUp(from);
        SCComponent toScc = trie.lookUp(to);

        if (fromScc == null || toScc == null)
            return false;

        if (fromScc.getKey() == toScc.getKey())
            return true;

        Label[] labelFromScc = fromScc.getLabel();
        Label[] labelToScc = toScc.getLabel();

        for (int i = 0; i < labelingLevel; i++)
            if (!labelToScc[i].isSubset(labelFromScc[i]))
                return false;

        return fromScc.getEdges().stream()
            .anyMatch(scc -> {
                return reachbility(scc.getKey(), toScc.getKey());
            });
    }
}
