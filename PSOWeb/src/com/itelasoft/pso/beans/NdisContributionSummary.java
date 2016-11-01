package com.itelasoft.pso.beans;

public class NdisContributionSummary extends BaseEntity {

	private Long clusterType;
	private double ndisFund;
	private double committedAmount;
	private double uncommittedAmount;
	private double claimedAmount;
	private double unclaimedAmount;

	public NdisContributionSummary(Long clusterType) {
		this.clusterType = clusterType;
	}

	public Long getClusterType() {
		return clusterType;
	}

	public void setClusterType(Long clusterType) {
		this.clusterType = clusterType;
	}

	public double getNdisFund() {
		return ndisFund;
	}

	public void setNdisFund(double ndisFund) {
		this.ndisFund = ndisFund;
	}

	public double getCommittedAmount() {
		return committedAmount;
	}

	public void setCommittedAmount(double committedAmount) {
		this.committedAmount = committedAmount;
	}

	public double getUncommittedAmount() {
		return uncommittedAmount;
	}

	public void setUncommittedAmount(double uncommittedAmount) {
		this.uncommittedAmount = uncommittedAmount;
	}

	public double getClaimedAmount() {
		return claimedAmount;
	}

	public void setClaimedAmount(double claimedAmount) {
		this.claimedAmount = claimedAmount;
	}

	public double getUnclaimedAmount() {
		return unclaimedAmount;
	}

	public void setUnclaimedAmount(double unclaimedAmount) {
		this.unclaimedAmount = unclaimedAmount;
	}

}