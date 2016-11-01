package com.itelasoft.pso.web.reports;

import static net.sf.dynamicreports.report.builder.DynamicReports.cmp;
import static net.sf.dynamicreports.report.builder.DynamicReports.col;
import static net.sf.dynamicreports.report.builder.DynamicReports.report;
import static net.sf.dynamicreports.report.builder.DynamicReports.stl;
import static net.sf.dynamicreports.report.builder.DynamicReports.type;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.ServletOutputStream;

import net.sf.dynamicreports.report.builder.column.TextColumnBuilder;
import net.sf.dynamicreports.report.builder.component.VerticalListBuilder;
import net.sf.dynamicreports.report.builder.style.StyleBuilder;
import net.sf.dynamicreports.report.builder.subtotal.SubtotalBuilders;
import net.sf.dynamicreports.report.constant.HorizontalAlignment;
import net.sf.dynamicreports.report.constant.PageOrientation;
import net.sf.dynamicreports.report.constant.PageType;
import net.sf.dynamicreports.report.definition.ReportParameters;
import net.sf.dynamicreports.report.definition.expression.DRIValueFormatter;
import net.sf.dynamicreports.report.exception.DRException;
import net.sf.jasperreports.engine.JRDataSource;

import com.itelasoft.pso.beans.Collection;
import com.itelasoft.pso.beans.Student;
import com.itelasoft.pso.beans.Transaction;
import com.itelasoft.pso.web.UIModel;

public class CollectionsReport extends UIModel {
	private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	private List<Transaction> transactions;

	public void build(ServletOutputStream outputStream, Long studentId,
			Long collectionId) {
		transactions = serviceLocator.getReportService().listCollectionTxs(
				studentId, collectionId);
		if (transactions != null && !transactions.isEmpty()) {
			Student student = serviceLocator.getStudentService().retrieve(
					transactions.get(0).getStudentId());
			Collection collection = transactions.get(0).getCollection();
			StyleBuilder bold = stl.style().bold().setFontSize(8);
			TextColumnBuilder<String> transactionDate = col
					.column("Transactions Date", "date", type.stringType())
					.setFixedWidth(150)
					.setHorizontalAlignment(HorizontalAlignment.CENTER);
			TextColumnBuilder<String> remarks = col.column("Remarks",
					"remarks", type.stringType());
			TextColumnBuilder<BigDecimal> amount = col
					.column("Paid Amount($)", "amount", type.bigDecimalType())
					.setFixedWidth(150)
					.setValueFormatter(
							new DRIValueFormatter<String, BigDecimal>() {
								private static final long serialVersionUID = 1L;

								@Override
								public String format(BigDecimal banked,
										ReportParameters arg1) {
									if (!banked.toString().equals("0")) {
										return banked.setScale(2,
												BigDecimal.ROUND_HALF_UP)
												.toString();
									} else
										return "-";
								}

								@Override
								public Class<String> getValueClass() {
									return String.class;
								}
							});
			VerticalListBuilder verticalHeaderList = cmp.verticalList(cmp
					.horizontalList(
							cmp.text("Student Name").setFixedWidth(80)
									.setStyle(bold),
							cmp.text(":").setFixedWidth(10).setStyle(bold),
							cmp.text(student.getContact().getName()).setStyle(
									bold)), cmp.horizontalList(
					cmp.text("Collection Name").setFixedWidth(80)
							.setStyle(bold), cmp.text(":").setFixedWidth(10)
							.setStyle(bold), cmp.text(collection.getName())
							.setStyle(bold)), cmp
					.horizontalList(
							cmp.text("Description").setFixedWidth(80)
									.setStyle(bold),
							cmp.text(":").setFixedWidth(10).setStyle(bold),
							cmp.text(
									collection.getDescription() != null
											&& !collection.getDescription()
													.isEmpty() ? collection
											.getDescription() : "-").setStyle(
									bold)));
			try {
				report().setPageFormat(PageType.A3, PageOrientation.PORTRAIT)
						.setTemplate(Templates.reportTemplate)
						.pageHeader(
								cmp.horizontalList(
										cmp.text("").setFixedWidth(600),
										verticalHeaderList.setStyle(stl.style()
												.setPadding(10))))
						.columns(transactionDate, remarks, amount)
						.subtotalsAtColumnFooter(
								new SubtotalBuilders().sum(amount))
						.setSubtotalStyle(
								stl.style()
										.setTopBorder(stl.pen1Point())
										.setBottomBorder(
												stl.penDouble().setLineWidth(
														(float) 3)))
						.title(Templates
								.createTitleComponent("Collection Report"))
						.setDataSource(createDataSource()).toPdf(outputStream);
			} catch (DRException e) {
				showError("Error occured.. Report not created..");
			}
		} else {
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			try {
				report().setPageFormat(PageType.A4, PageOrientation.LANDSCAPE)
						.setTemplate(Templates.reportTemplate)
						.title(Templates
								.createTitleComponent("No Records Found.."))
						.toPdf(byteArrayOutputStream);
				outputStream.write(byteArrayOutputStream.toByteArray());
			} catch (DRException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private JRDataSource createDataSource() {
		DataSource dataSrc = new DataSource("date", "remarks", "amount");
		for (Transaction tx : transactions) {
			dataSrc.add(dateFormat.format(tx.getTransactionDate()),
					tx.getRemarks(), new BigDecimal(tx.getAmount()));
		}
		return dataSrc;
	}

}
