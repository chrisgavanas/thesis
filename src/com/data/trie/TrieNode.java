package com.data.trie;

import com.grails.scc.SCComponent;

public class TrieNode {
    private SCComponent info;
    private TrieNode[] children;

    public TrieNode() {
        children = new TrieNode[10];
        for (int i = 0; i < 10; i++)
            children[i] = null;
    }

    public TrieNode getChild(char letter) {
        return children[letter - '0'];
    }

    public TrieNode setChild(char letter) {
        if (children[letter - '0'] == null)
            children[letter - '0'] = new TrieNode();
        return children[letter - '0'];
    }

    public TrieNode setChild(char letter, SCComponent scc) {
        if (children[letter - '0'] == null)
            children[letter - '0'] = new TrieNode();
        children[letter - '0'].info = scc;
        return children[letter - '0'];
    }

    public SCComponent getInfo() {
        return info;
    }
}
