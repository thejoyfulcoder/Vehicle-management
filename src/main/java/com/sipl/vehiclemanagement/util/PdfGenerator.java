package com.sipl.vehiclemanagement.util;

import java.io.IOException;
import java.util.List;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.CMYKColor;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.sipl.vehiclemanagement.dto.vehicle.VehicleResponseDto;

import jakarta.servlet.http.HttpServletResponse;

public class PdfGenerator {
      
	public static void generate(HttpServletResponse response, List<VehicleResponseDto> vehicleList) throws DocumentException, IOException {
		
		Document document = new Document(PageSize.A4);
		
		PdfWriter.getInstance(document, response.getOutputStream());
		
		document.open();
		
		Font fontTitle= FontFactory.getFont(FontFactory.TIMES_ROMAN);
		
		fontTitle.setSize(20);
		
		//New Paragraph
		Paragraph paragraph = new Paragraph("List of Vehicles", fontTitle);
		
		paragraph.setAlignment(Paragraph.ALIGN_CENTER);
		
		document.add(paragraph);
		
		//Creating a table of 5 columns
		PdfPTable table = new PdfPTable(5);
		
		//Setting width, and column spacing of the table
		table.setWidthPercentage(100f);
		table.setWidths(new int[] {3,3,3,3,3});
		table.setSpacingBefore(5);
		
		//Creating table cells for table header
		PdfPCell cell = new PdfPCell();
		
		//backgorund and padding
		cell.setBackgroundColor(CMYKColor.orange);
		cell.setPadding(5);
		
		//Creating font
		Font font= FontFactory.getFont(FontFactory.TIMES_ROMAN);
		font.setColor(CMYKColor.DARK_GRAY);
		
		
		//Adding headings in the created table cell/ header
		cell.setPhrase(new Phrase("Registration Number",font));
		table.addCell(cell);
		cell.setPhrase(new Phrase("Owner",font));
		table.addCell(cell);
		cell.setPhrase(new Phrase("Brand",font));
		table.addCell(cell);
		cell.setPhrase(new Phrase("Expiry of Reigistration",font));
		table.addCell(cell);
		cell.setPhrase(new Phrase("Activation Status (YES/NO)",font));
		table.addCell(cell);
		
		
		//Iterating over the list of vehicles
		for(VehicleResponseDto vehicle: vehicleList) {
			   table.addCell(vehicle.getVehicleRegistrationNumber());
			   
			   table.addCell(vehicle.getOwnerName());
			   
			   table.addCell(vehicle.getBrand());
			        
			   table.addCell(String.valueOf(vehicle.getRegistrationExpires()));
			   
			   table.addCell(vehicle.getIsActive());
		}
		
		document.add(table);
		
		document.close();
		
	}
}
