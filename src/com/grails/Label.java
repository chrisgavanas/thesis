package com.grails;

public class Label {
	private int rank;
	private int minSubRank;
	
	public Label() {
		this.rank = 0;
		this.minSubRank = 0;
	}
	
	public int getRank() {
		return rank;
	}
	public void setRank(int rank) {
		this.rank = rank;
	}
	public int getMinSubRank() {
		return minSubRank;
	}
	public void setMinSubRank(int minSubRank) {
		this.minSubRank = minSubRank;
	}

	public boolean isSubset(Label label) {
		return minSubRank >= label.minSubRank && rank <= label.rank;
	}
}
