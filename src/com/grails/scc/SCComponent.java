package com.grails.scc;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.grails.Label;
import com.grails.wrappers.StartingRankWrapper;

public class SCComponent {
    private static Long sccNo = 1L;
    private final Long key;
    private List<SCComponent> edges;
    private Label[] labels;

    public SCComponent(int labelingLevel) {
        this.key = sccNo++;
        this.edges = new LinkedList<SCComponent>();
        this.labels = new Label[labelingLevel];
        for (int i = 0; i < labelingLevel; i++)
            labels[i] = new Label();
    }

    public void addEdge(SCComponent edge) {
        edges.add(edge);
    }

    public Long getKey() {
        return key;
    }

    public Label[] getLabel() {
        return labels;
    }

    public List<SCComponent> getEdges() {
        return edges;
    }

    @SuppressWarnings("unchecked")
    public void fixLabels(Set<Long> visited, StartingRankWrapper startingRank, int labelLevel) {
        List<SCComponent> shuffledList = (LinkedList<SCComponent>)((LinkedList<SCComponent>)edges).clone();
        Collections.shuffle(shuffledList);
        shuffledList.stream()
            .filter(scc -> !visited.contains(scc.key))
            .forEach(scc -> {
                visited.add(scc.key);
                scc.fixLabels(visited, startingRank, labelLevel);
            });

        labels[labelLevel].setRank(startingRank.getValue());
        Optional<SCComponent> minSubRank = shuffledList.stream()
            .filter(scc -> scc.labels[labelLevel].getMinSubRank() != 0)
            .min((p1, p2) -> Integer.compare(p1.labels[labelLevel].getMinSubRank(), p2.labels[labelLevel].getMinSubRank()));

        if (minSubRank.isPresent())
            labels[labelLevel].setMinSubRank(minSubRank.get().labels[labelLevel].getMinSubRank());
        else
            labels[labelLevel].setMinSubRank(startingRank.getValue());

        startingRank.plus();
    }
}
