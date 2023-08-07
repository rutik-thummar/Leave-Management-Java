package com.leavemanagement.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.leavemanagement.dto.PaySlip;
import com.leavemanagement.service.PaySlipService;

@RestController
@RequestMapping("payslip")
@CrossOrigin(origins = "http://localhost:4200")
public class PaySlipController {

	@Autowired
	PaySlipService paySlipService;

	@PostMapping(value = "/generatepdf")
	public ResponseEntity<String> createPDF(@RequestBody PaySlip paySlip) {

		if (paySlipService.createPDF(paySlip)) {
			return ResponseEntity.status(HttpStatus.OK).body("File Generated Successfully.");

		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed!");
		}
	}

	@PostMapping(value = "/downloadpdf")
	public ResponseEntity<Resource> downloadPDF(@RequestBody PaySlip paySlip) throws IOException {
		try {
			return paySlipService.downloadPdf(paySlip);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
