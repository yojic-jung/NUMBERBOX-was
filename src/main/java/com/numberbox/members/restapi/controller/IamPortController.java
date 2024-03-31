package com.numberbox.members.restapi.controller;

import java.text.SimpleDateFormat;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.numberbox.iamport.IamportClient;
import com.siot.IamportRestClient.response.Certification;
import com.siot.IamportRestClient.response.IamportResponse;

public class IamPortController {
	@GetMapping(value = "/certifications/{imp_uid}")
	public Object certifications(@PathVariable String imp_uid, HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		IamportClient iam = new IamportClient("2626730431329357",
				"BrBdnwc95qlxfiE2VHGi1DgQ47IHjwWiob1IrRM4hXipf6yooKNuH4qGMkQfD8VncpnyENTvYPqadLtG");
		IamportResponse<Certification> cer = iam.certificationByImpUid(imp_uid);

		SimpleDateFormat date = new SimpleDateFormat("yyMMdd");

		Certification cer1 = cer.getResponse();
		cer1.getPhone();
		HashMap<String, String> map = new HashMap<>();
		map.put("name", cer.getResponse().getName());
		map.put("birth", date.format(cer.getResponse().getBirth()));
		map.put("phone", cer.getResponse().getPhone());

		return map;
	}

	@GetMapping(value = "/takeMerchantUid")
	public Object takeMerchantUid() {
		HashMap<String, String> map = new HashMap<>();
		map.put("merchantUid", "store-7cde0e91-3134-41e2-916a-2d567f4ee4eb");
		map.put("merchantIdCode", "imp48047014");
		return map;
	}
}
