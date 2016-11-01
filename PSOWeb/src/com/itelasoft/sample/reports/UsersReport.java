/**
 * DynamicReports - Free Java reporting library for creating reports dynamically
 *
 * Copyright (C) 2010 - 2011 Ricardo Mariaca
 * http://dynamicreports.sourceforge.net
 *
 * This file is part of DynamicReports.
 *
 * DynamicReports is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * DynamicReports is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with DynamicReports. If not, see <http://www.gnu.org/licenses/>.
 */

package com.itelasoft.sample.reports;

import static net.sf.dynamicreports.report.builder.DynamicReports.cmp;
import static net.sf.dynamicreports.report.builder.DynamicReports.col;
import static net.sf.dynamicreports.report.builder.DynamicReports.report;
import static net.sf.dynamicreports.report.builder.DynamicReports.stl;
import static net.sf.dynamicreports.report.builder.DynamicReports.type;

import java.awt.Color;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.itelasoft.pso.beans.ProgramType;
import com.itelasoft.pso.services.ServiceLocator;

import net.sf.dynamicreports.report.base.expression.AbstractSimpleExpression;
import net.sf.dynamicreports.report.builder.column.ComponentColumnBuilder;
import net.sf.dynamicreports.report.builder.column.TextColumnBuilder;
import net.sf.dynamicreports.report.builder.component.HorizontalListBuilder;
import net.sf.dynamicreports.report.builder.component.VerticalListBuilder;
import net.sf.dynamicreports.report.constant.SplitType;
import net.sf.dynamicreports.report.definition.ReportParameters;
import net.sf.dynamicreports.report.definition.expression.DRIValueFormatter;
import net.sf.dynamicreports.report.exception.DRException;
import net.sf.jasperreports.engine.JRDataSource;

/**
 * @author Ricardo Mariaca (dynamicreports@gmail.com)
 */
public class UsersReport {

	public UsersReport() {
		// build();
	}

	public void build() {
		try {
			report().setTemplate(Templates.reportTemplate)
					.columns(
							col.column("Item", "item", type.stringType()),
							col.column("Quantity", "quantity",
									type.integerType()),
							col.column("Unit price", "unitprice",
									type.bigDecimalType()),
							col.column("Order date", "orderdate",
									type.dateType()),
							col.column("Order date", "orderdate",
									type.dateYearToFractionType()),
							col.column("Order year", "orderdate",
									type.dateYearType()),
							col.column("Order month", "orderdate",
									type.dateMonthType()),
							col.column("Order day", "orderdate",
									type.dateDayType()))
					.title(Templates.createTitleComponent("ColumnDataTypes"))
					.pageFooter(Templates.footerComponent)
					.setDataSource(createDataSource()).show();
		} catch (DRException e) {
			e.printStackTrace();
		}
	}

	public void build(OutputStream outputStream) {

		class ItemExpression extends AbstractSimpleExpression<String> {
			private static final long serialVersionUID = 1L;

			public String evaluate(ReportParameters reportParameters) {
				List<ProgramType> type = reportParameters.getValue("list");
				return type.get(0).getName();
			}
		}

		HorizontalListBuilder horizontalList = cmp.horizontalList(
				cmp.text(new ItemExpression()).setWidth(60)
						.setStyle(stl.style().bold()), cmp.text("test")
						.setStyle(stl.style().italic()));

		VerticalListBuilder verticalList = cmp
				.verticalList(
						cmp.text(new ItemExpression()).setStyle(
								stl.style().bold()), cmp.text(getString()), cmp
								.text("test").setStyle(stl.style().italic()),
						horizontalList);

		ComponentColumnBuilder tmpCol = col.componentColumn(verticalList)
				.setTitle("Vertical List");

		TextColumnBuilder<List> list = col.column("List", "list",
				type.listType()).setValueFormatter(
				new DRIValueFormatter<String, List>() {
					private static final long serialVersionUID = 1L;

					@Override
					public String format(List list, ReportParameters arg1) {
						String text = "";
						for (Object obj : list) {
							ProgramType type = (ProgramType) obj;
							text = text + type.getName() + "\n";
						}
						return text;
					}

					@Override
					public Class<String> getValueClass() {
						return String.class;
					}
				});
		
		try {
			report().setTemplate(Templates.reportTemplate)
					.fields()
					.columns(
							tmpCol,
							col.column("", "item", type.stringType())
									.setFixedWidth(1)
									.setTitleStyle(
											stl.style().setBackgroundColor(
													Color.LIGHT_GRAY)), list)
					.title(Templates.createTitleComponent("ColumnDataTypes"))
					.pageFooter(Templates.footerComponent)
					.setHighlightDetailEvenRows(false)
					.setHighlightDetailOddRows(false)
					.setDataSource(createDataSource()).toPdf(outputStream);
		} catch (DRException e) {
			e.printStackTrace();
		}
	}

	private JRDataSource createDataSource() {
		DataSource dataSource = new DataSource("item", "tmp2", "list");
		List<ProgramType> list = ServiceLocator.getServiceLocator()
				.getProgramTypeService().findAll();
		dataSource.add("TEST", "Body row", list);
		dataSource.add("TEST", "gggggggg", list);
		// dataSource.add("Ho", new BigDecimal(500), list);
		// JRDataset dataset = new JRDatasetFactory() JRHibernateListDataSource
		// = new JRHibernateQueryExecuterFactory().createQueryExecuter(dataset,
		// parameters);

		// new JRJdbcQueryExecuterFactory().

		return dataSource;
	}

	private String getString() {
		return "String 1\nString 2";
	}

	public static void main(String[] args) {
		new UsersReport();
	}
}