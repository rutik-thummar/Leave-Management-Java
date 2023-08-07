package com.leavemanagement.serviceimpl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.YearMonth;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.leavemanagement.dto.PaySlip;
import com.leavemanagement.model.Employee;
import com.leavemanagement.model.EmployeeLeave;
import com.leavemanagement.repository.EmployeeRepository;
import com.leavemanagement.repository.LeaveRepository;
import com.leavemanagement.service.PaySlipService;
import com.leavemanagement.util.NumberToWords;

@Service
public class PaySlipSeviceImpl implements PaySlipService {

	@Autowired
	EmployeeRepository employeeRepository;

	@Autowired
	LeaveRepository leaveRepository;

	@Override
	public Boolean createPDF(PaySlip payslip) {
		try {
			PdfReader reader = new PdfReader("E:\\Rutik\\Payslip_format.pdf");
			Employee emp = employeeRepository.findById((int) payslip.getId()).get();
			List<EmployeeLeave> empLeave = leaveRepository.findByEmployeeIdOrderByFromDateDesc(emp.getId());
			String selectedYear = payslip.getMonthOrYear().substring(0, 4);
			String selectedMonth = payslip.getMonthOrYear().substring(5, 7);
			int dailyIncome, totalDeduction, netSalary;
			double halfDay = 0, fullDay = 0, leaveCount = 0, leave = 0;
			YearMonth yearMonthObject = YearMonth.of(Integer.valueOf(selectedYear), Integer.valueOf(selectedMonth));
			int daysInMonth = yearMonthObject.lengthOfMonth();
			int totalDays = daysInMonth;
			int otherIncome = 0;
			int totalIncome = emp.getSalary();
			for (EmployeeLeave employeeLeave : empLeave) {
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				String date = dateFormat.format(employeeLeave.getFromDate()).substring(5, 7);
				if (employeeLeave.getStatus().equalsIgnoreCase("Approved") && date.equals(selectedMonth)) {
					if (employeeLeave.getType().equalsIgnoreCase("Full")) {
						if (employeeLeave.getFromDate().equals(employeeLeave.getToDate())) {
							fullDay++;
						} else {
							String fromDate = dateFormat.format(employeeLeave.getFromDate()).substring(8, 10);
							String toDate = dateFormat.format(employeeLeave.getToDate()).substring(8, 10);
							fullDay = fullDay + Integer.valueOf(toDate) - Integer.valueOf(fromDate);
							fullDay++;
						}
					} else {
						halfDay = halfDay + 0.5;
					}
				}
			}
			leaveCount = fullDay + halfDay;
			dailyIncome = totalIncome / totalDays;
			leave = leaveCount;
			if (leaveCount >= 1) {
				leaveCount--;
			} else if (leaveCount < 1) {
				leaveCount = 0;
			}
			totalDeduction = (int) (dailyIncome * leaveCount);
			netSalary = totalIncome - totalDeduction;
			netSalary = netSalary + otherIncome;
			PdfStamper stamper = new PdfStamper(reader, new FileOutputStream("E:\\Rutik\\salary-pdf\\"
					+ emp.getFirstName().toUpperCase() + "_" + selectedMonth + "_" + selectedYear + ".pdf"));

			BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);

			for (int i = 1; i <= reader.getNumberOfPages(); i++) {

				PdfContentByte over = stamper.getOverContent(i);

				over.beginText();
				over.setFontAndSize(bf, 13);

				String month;
				switch (Integer.parseInt(selectedMonth)) {
				case 1:
					month = "January";
					break;
				case 2:
					month = "February";
					break;
				case 3:
					month = "March";
					break;
				case 4:
					month = "April";
					break;
				case 5:
					month = "May";
					break;
				case 6:
					month = "June";
					break;
				case 7:
					month = "July";
					break;
				case 8:
					month = "August";
					break;
				case 9:
					month = "September";
					break;
				case 10:
					month = "October";
					break;
				case 11:
					month = "November";
					break;
				case 12:
					month = "December";
					break;
				default:
					month = "Invalid month";
					break;
				}
				over.setTextMatrix(493, 738);
				over.showText(month + " - " + selectedYear);

				over.setFontAndSize(bf, 9);
				over.setTextMatrix(158, 681);
				over.showText(emp.getFirstName().toUpperCase() + "  " + emp.getLastName().toUpperCase());

				over.setTextMatrix(158, 659);
				over.showText(String.valueOf(emp.getId()));

				over.setTextMatrix(158, 637);
				over.showText(month + " - " + selectedYear);

				over.setTextMatrix(158, 615);
				over.showText("01-" + selectedMonth + "-" + selectedYear);

				over.setFontAndSize(bf, 18);
				over.setTextMatrix(417, 679);
				over.showText(String.valueOf(netSalary) + ".00");

				over.setFontAndSize(bf, 9);
				over.setTextMatrix(500, 623);
				over.showText(String.valueOf(totalDays - leave));

				over.setTextMatrix(500, 603);
				over.showText("0" + String.valueOf(leave));

				over.setTextMatrix(258, 495);
				over.showText(String.valueOf(emp.getSalary()) + ".00");

				over.setTextMatrix(262, 472);
				over.showText("--");

				over.setTextMatrix(258, 443);
				over.showText(String.valueOf(emp.getSalary() + otherIncome) + ".00");
//Income Tax
				over.setTextMatrix(540, 495);
				over.showText(String.valueOf(totalDeduction)+".00");
//pf
				over.setTextMatrix(540, 472);
				over.showText("--");
//total deductions		

				over.setTextMatrix(540, 444);
				over.showText(String.valueOf(totalDeduction)+".00");

				over.setFontAndSize(bf, 11);
				over.setTextMatrix(528, 389);
				over.showText(String.valueOf(netSalary) + ".00");

				if(NumberToWords.convert(netSalary).length()>30) {
					over.setFontAndSize(bf, 8);
					over.setTextMatrix(446, 344);
					over.showText(String.valueOf(NumberToWords.convert(netSalary).substring(0,32)));
					
					over.setFontAndSize(bf, 8);
					over.setTextMatrix(446, 334);
					over.showText(String.valueOf(NumberToWords.convert(netSalary).substring(32,NumberToWords.convert(netSalary).length()))+" Rupees Only");
				}else {
					over.setFontAndSize(bf, 8);
					over.setTextMatrix(446, 344);
					over.showText(String.valueOf(NumberToWords.convert(netSalary)));
					
					over.setFontAndSize(bf, 8);
					over.setTextMatrix(446, 334);
					over.showText(" Rupees Only");
				}		
				over.endText();
			}
			stamper.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public ResponseEntity<Resource> downloadPdf(PaySlip payslip) throws IOException {
		try {
			Employee emp = employeeRepository.findById((int) payslip.getId()).get();
			String selectedYear = payslip.getMonthOrYear().substring(0, 4);
			String selectedMonth = payslip.getMonthOrYear().substring(5, 7);
			String serverPath = "E:\\Rutik\\salary-pdf\\" + emp.getFirstName().toUpperCase() + "_" + selectedMonth + "_"
					+ selectedYear + ".pdf";
			File file = new File(serverPath);
			if (file.exists()) {
				HttpHeaders header = new HttpHeaders();
				header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + emp.getFirstName().toUpperCase()
						+ "_" + selectedMonth + "_" + selectedYear + ".pdf");
				header.add("Cache-Control", "no-cache, no-store, must-revalidate");
				header.add("Pragma", "no-cache");
				header.add("Expires", "0");
				Path path = Paths.get(file.getAbsolutePath());
				ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));
				return ResponseEntity.ok().headers(header).contentLength(file.length())
						.contentType(MediaType.parseMediaType("application/octet-stream")).body(resource);
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
