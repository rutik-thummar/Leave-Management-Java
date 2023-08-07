package com.leavemanagement.service;

import java.io.IOException;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;

import com.leavemanagement.dto.PaySlip;


public interface PaySlipService {
	
	Boolean createPDF(PaySlip paySlip);
	
	ResponseEntity<Resource> downloadPdf(PaySlip paySlip) throws IOException;

}
