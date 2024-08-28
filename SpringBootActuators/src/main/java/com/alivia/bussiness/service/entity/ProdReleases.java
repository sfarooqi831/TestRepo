package com.alivia.bussiness.service.entity;

import java.util.List;

public class ProdReleases {

	private String crq;
	private String releaseDate;
	private List<String> features;

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ProdReleases [crq=");
		builder.append(crq);
		builder.append(", releaseDate=");
		builder.append(releaseDate);
		builder.append(", features=");
		builder.append(features);
		builder.append("]");
		return builder.toString();
	}

	public String getCrq() {
		return crq;
	}

	public void setCrq(String crq) {
		this.crq = crq;
	}

	public String getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
	}

	public List<String> getFeatures() {
		return features;
	}

	public void setFeatures(List<String> features) {
		this.features = features;
	}

	public ProdReleases(String crq, String releaseDate, List<String> features) {
		super();
		this.crq = crq;
		this.releaseDate = releaseDate;
		this.features = features;
	}

}
