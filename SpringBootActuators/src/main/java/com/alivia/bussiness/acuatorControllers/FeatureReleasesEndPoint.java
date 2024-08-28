package com.alivia.bussiness.acuatorControllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.boot.actuate.endpoint.OperationResponseBody;
import org.springframework.boot.actuate.endpoint.annotation.DeleteOperation;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.annotation.Selector;
import org.springframework.boot.actuate.endpoint.annotation.WriteOperation;
import org.springframework.stereotype.Component;

import com.alivia.bussiness.service.entity.ProdReleases;

@Component
@Endpoint(id = "release")
public class FeatureReleasesEndPoint {

	List<ProdReleases> releaseInfo = new ArrayList<>();

	@WriteOperation
	public Map<?,?> addNewReleaseInfo(@Selector String crd, @Selector String releaseDate, String features) {
		ProdReleases release = new ProdReleases(crd, releaseDate,
				Arrays.stream(features.split(",")).collect(Collectors.toList()));
		releaseInfo.add(release);
		Map<String,String> map = new HashMap<>();
		map.put("Release", release.toString());
		return OperationResponseBody.of(map);
	}

	@ReadOperation
	public ProdReleases getReleaseInfo(@Selector String crd) {
		return releaseInfo.stream().filter(e -> e.getCrq().equalsIgnoreCase(crd)).findFirst().orElse(null);
	}

	@ReadOperation
	public List<ProdReleases> getAllReleases() {
		return releaseInfo;
	}

	@DeleteOperation
	public String deleteRelease(@Selector String crq) {
		boolean remove = releaseInfo.remove(getReleaseInfo(crq));
		String message = null;
		if (remove) {
			message = String.format("Release with CRQ {%s} deleted Successfully", crq);
		} else {
			message = String.format("No Release Exist with id {%s}", crq);
		}
		return message;
	}
}
