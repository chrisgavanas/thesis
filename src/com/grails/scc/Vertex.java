package com.grails.scc;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Vertex {
    private Long id;
    private Long weight;
    private List<Vertex> straight;
    private List<Vertex> reversed;

    public Vertex(Long id) {
        this.id = id;
        this.straight = new LinkedList<Vertex>();
        this.reversed = new LinkedList<Vertex>();
    }

    public Long getId() {
        return id;
    }

    public void addStraightEdge(Vertex edge) {
        straight.add(edge);
    }

    public void addReversedEdge(Vertex edge) {
        this.reversed.add(edge);
    }

    public Long getWeight() {
        return weight;
    }

    public void setWeight(Long weight) {
        this.weight = weight;
    }

    public List<Vertex> getStraightEdges() {
        return straight;
    }

    public void calcWeights(List<Vertex> weightPerVertex, Set<Long> visited) {
        reversed.stream()
            .filter(vertex -> !visited.contains(vertex.id))
            .forEach(vertex -> {
                visited.add(vertex.id);
                vertex.calcWeights(weightPerVertex, visited);
                vertex.weight = SCCBuilder.weight++;
                weightPerVertex.add(vertex);
            });
    }

    public void dfs(Set<Long> visited, Set<Long> currentSCC) {
        if (!visited.contains(id)) {
            visited.add(id);
            currentSCC.add(id);
            straight.stream()
                .filter(vertex -> !visited.contains(vertex.id))
                .forEach(vertex -> vertex.dfs(visited, currentSCC));
        }
    }
}
