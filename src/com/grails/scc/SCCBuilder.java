package com.grails.scc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import com.data.trie.Trie;

public class SCCBuilder {
    public static Long weight = 1L;
    private Trie trie;
    private Map<Long, Vertex> hashTable;
    private List<Vertex> weightPerVertex;
    private Set<Long> visited;
    private Set<Long> currentSCC;
    private int labelingLevel;
    private List<SCComponent> sccs;

    public SCCBuilder(Trie trie, int labelingLevel) {
        this.trie = trie;
        this.hashTable = new HashMap<Long, Vertex>();
        this.weightPerVertex = new ArrayList<Vertex>();
        this.visited = new HashSet<Long>();
        this.currentSCC = new HashSet<Long>();
        this.labelingLevel = labelingLevel;
    }

    public void addVertex(Long from, Long to) {
        Vertex fromVertex = Optional.ofNullable(hashTable.get(from)).orElse(new Vertex(from));
        Vertex toVertex = Optional.ofNullable(hashTable.get(to)).orElse(new Vertex(to));

        if (!hashTable.containsKey(from))
            hashTable.put(from, fromVertex);
        if (!hashTable.containsKey(to))
            hashTable.put(to, toVertex);

        fromVertex.addStraightEdge(toVertex);
        toVertex.addReversedEdge(fromVertex);
    }

    public void findSCCs() {
        List<SCComponent> sccs = new LinkedList<SCComponent>();

        hashTable.forEach((key, value) -> {
            if (!visited.contains(key)) {
                visited.add(key);
                value.calcWeights(weightPerVertex, visited);
                value.setWeight(SCCBuilder.weight++);
                weightPerVertex.add(value);
            }
        });

        visited.clear();

        for (int i = weightPerVertex.size()-1; i >=0; i--) {
            if (!visited.contains(weightPerVertex.get(i).getId())) {
                weightPerVertex.get(i).dfs(visited, currentSCC);
                visited.add(weightPerVertex.get(i).getId());
                SCComponent scc = new SCComponent(labelingLevel);
                sccs.add(scc);
                currentSCC.stream()
                    .filter(id -> trie.lookUp(id) == null)
                    .forEach(id -> trie.insert(String.valueOf(id), scc));
                currentSCC.clear();
            }
        }

        this.sccs = sccs;
    }

    public List<SCComponent> getSCComponents() {
        return sccs;
    }
}
