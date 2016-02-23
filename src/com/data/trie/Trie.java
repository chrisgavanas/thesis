package com.data.trie;

import com.grails.scc.SCComponent;

public class Trie {
    private TrieNode head;

    public Trie() {
        this.head = new TrieNode();
    }

    public void insert(String word, SCComponent scc) {
        TrieNode insert = head;
        char[] tokens = word.toCharArray();

        for (int i = 0; i < tokens.length - 1; i++)
            insert = insert.setChild(tokens[i]);
        insert.setChild(tokens[tokens.length - 1], scc);
    }

    public SCComponent lookUp(Long id) {
        if (id <= 0)
            return null;

        int i = 0;
        char[] tokens = String.valueOf(id).toCharArray();
        TrieNode search = head;
        while (i < tokens.length && (search = search.getChild(tokens[i++])) != null);

        return search == null ? null : search.getInfo();
    }
}
