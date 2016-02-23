package com.grails;

public class IndexBuilder implements Runnable {
    private Grails grails;
    private int labelLevel;

    public IndexBuilder(Grails grails, int labelLevel) {
        this.grails = grails;
        this.labelLevel = labelLevel;
    }

    @Override
    public void run() {
        grails.buildIndex(labelLevel);
    }
}
