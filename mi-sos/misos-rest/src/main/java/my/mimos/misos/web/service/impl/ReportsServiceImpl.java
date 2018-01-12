package my.mimos.misos.web.service.impl;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import static net.sf.dynamicreports.report.builder.DynamicReports.*;
import lombok.extern.log4j.Log4j;
import my.mimos.misos.domain.resource.SearchChannelMessageResource;
import my.mimos.misos.domain.resource.SearchChannelMessageResponseResource;
import my.mimos.misos.domain.resource.SummaryReportResource;
import my.mimos.misos.domain.resource.SummaryReportResponseResource;
import my.mimos.misos.web.service.ReportsService;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.imageio.ImageIO;

import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.builder.chart.BarChartBuilder;
import net.sf.dynamicreports.report.builder.column.TextColumnBuilder;
import net.sf.dynamicreports.report.builder.component.ComponentBuilder;
import net.sf.dynamicreports.report.builder.component.HorizontalListBuilder;
import net.sf.dynamicreports.report.builder.component.ImageBuilder;
import net.sf.dynamicreports.report.builder.style.StyleBuilder;
import net.sf.dynamicreports.report.builder.subtotal.AggregationSubtotalBuilder;
import net.sf.dynamicreports.report.constant.HorizontalImageAlignment;
import net.sf.dynamicreports.report.constant.HorizontalTextAlignment;
import net.sf.dynamicreports.report.constant.VerticalTextAlignment;
import net.sf.dynamicreports.report.datasource.DRDataSource;
import net.sf.dynamicreports.report.definition.ReportParameters;
import net.sf.dynamicreports.report.definition.chart.DRIChartCustomizer;
import net.sf.jasperreports.engine.JRDataSource;

@Log4j
@Component
public class ReportsServiceImpl implements ReportsService {
	
	private AggregationSubtotalBuilder<Long> MessagesSum;
	private AggregationSubtotalBuilder<Long> RecipientsSum;
	
	@Autowired
	private MessageSource messageSource;
	   
	public JasperReportBuilder build(SearchChannelMessageResponseResource channelMsgLst) {   
		StyleBuilder boldStyle         = stl.style().bold();
		StyleBuilder boldCenteredStyle = stl.style(boldStyle).setHorizontalTextAlignment((HorizontalTextAlignment.CENTER));
		StyleBuilder columnTitleStyle  = stl.style(boldCenteredStyle).setBorder(stl.pen1Point()).setBackgroundColor(Color.LIGHT_GRAY);
		
		Locale locale = LocaleContextHolder.getLocale();

		// title,     field name     data type -- ID 	Message 	Channel 	Severity 	Status 	Publish Date 	Recepient
		TextColumnBuilder<Long> sno      = col.column(messageSource.getMessage("SNO",null,locale),"sno",type.longType()).setHorizontalTextAlignment(HorizontalTextAlignment.LEFT);;
		TextColumnBuilder<String> id      = col.column(messageSource.getMessage("IOC_ID",null,locale),"id",type.stringType()).setWidth(200);
		TextColumnBuilder<String> message  = col.column(messageSource.getMessage("Message",null,locale), "messageContent", type.stringType()).setWidth(400);
		TextColumnBuilder<String> channel = col.column(messageSource.getMessage("Channel",null,locale), "channelType", type.stringType());
		TextColumnBuilder<String> severity = col.column(messageSource.getMessage("Severity",null,locale), "severityLevel", type.stringType());
		TextColumnBuilder<String> targetGroup = col.column(messageSource.getMessage("Target",null,locale), "targetGroup", type.stringType());
		TextColumnBuilder<String> status = col.column(messageSource.getMessage("Status",null,locale), "publishStatus", type.stringType());
		TextColumnBuilder<Date> publishDate = col.column(messageSource.getMessage("Publish_Date",null,locale), "publishDate", type.dateYearToSecondType()).setWidth(200);
		TextColumnBuilder<String> recepientCount = col.column(messageSource.getMessage("Recepient",null,locale), "recipientCount", type.stringType());

		JasperReportBuilder report = report()//create new report design
		.setColumnTitleStyle(columnTitleStyle)
		.highlightDetailEvenRows()
		.columns(//add columns
				sno, id, message, channel, severity, targetGroup, status, publishDate,recepientCount)
		.title(headerComponent("JPS Dissemination Audit Report").setStyle(boldCenteredStyle))//shows report title
		.pageFooter(cmp.pageXofY().setStyle(boldCenteredStyle))//shows number of page at page footer
		.setDataSource(createDataSource(channelMsgLst));//set datasource
		return report;
	}



	public JRDataSource createDataSource(SearchChannelMessageResponseResource channelMsgLst) {
	
		SearchChannelMessageResource channelMesssage = null;
		List<SearchChannelMessageResource> msgList=channelMsgLst.getChannelMessageList();		
		DRDataSource dataSource = new DRDataSource("sno", "id", "messageContent", "channelType", "severityLevel", "targetGroup",  "publishStatus", "publishDate","recipientCount");
		Long i=0L;
		for (Iterator<SearchChannelMessageResource> iterator = msgList.iterator(); iterator.hasNext();)
		{
			channelMesssage = (SearchChannelMessageResource) iterator.next();
			String status=new String();
			if(channelMesssage.getPublishStatus().equals("true")){
				status="Success";
			} else {
				status="Failure";
			}
			i=i+1;
			dataSource.add(i,channelMesssage.getIocMessageId(),channelMesssage.getMessageContent(),channelMesssage.getChannelType(),
					channelMesssage.getSeverityLevel(),channelMesssage.getTargetGroup(),status,channelMesssage.getPublishDate(),
					channelMesssage.getRecipientCount());		  
		}
		return dataSource;
	
	}



	public JasperReportBuilder buildSummaryReport(SummaryReportResponseResource reportLst) {
		JasperReportBuilder jasperReportBuilder = new JasperReportBuilder();
		
		Locale locale = LocaleContextHolder.getLocale();

		StyleBuilder bold = stl.style().bold();
		StyleBuilder boldCenteredStyle = stl.style(bold).setHorizontalTextAlignment(HorizontalTextAlignment.CENTER);
		StyleBuilder columnTitleStyle = stl.style(boldCenteredStyle).setBorder(stl.pen1Point()).setBackgroundColor(Color.LIGHT_GRAY);
		StyleBuilder centeredStyle = stl.style(bold).setHorizontalTextAlignment(HorizontalTextAlignment.CENTER);

		TextColumnBuilder<String> channleType = col.column(messageSource.getMessage("Channel_Type",null,locale), "ChannelType", type.stringType());
		TextColumnBuilder<Long> numOfMessages = col.column(messageSource.getMessage("Number_of_Messages",null,locale), "numOfMessages", type.longType());
		TextColumnBuilder<Long> numOfRecipients = col.column(messageSource.getMessage("Number_of_Recipients",null,locale), "numOfRecipients",type.longType());
		TextColumnBuilder<String> publishStatus = col.column(messageSource.getMessage("Publish_Status",null,locale), "publishStatus", type.stringType()).setStyle(centeredStyle);

		MessagesSum = sbt.sum(numOfMessages).setStyle(bold);
		RecipientsSum = sbt.sum(numOfRecipients).setStyle(bold);

		Map<String, Color> seriesColors = new HashMap<String, Color>();
		seriesColors.put("Success", new Color(149, 206, 255));
		seriesColors.put("Failure", new Color(92, 92, 97));
		seriesColors.put("Total", new Color(169, 255, 150));

		BarChartBuilder barChart = cht.barChart().setShowValues(true).customizers(new ChartCustomizer())
				.setCategory(channleType).seriesOrderBy("Success", "Failure", "Total").seriesColorsByName(seriesColors)
				.series(cht.serie(numOfMessages).setSeries(publishStatus), cht.serie(numOfMessages).setLabel("Total"));

		jasperReportBuilder = report().setColumnTitleStyle(columnTitleStyle)
				.columns(channleType, numOfMessages, numOfRecipients, publishStatus)
				.subtotalsAtSummary(sbt.text("TOTAL", channleType).setStyle(bold), MessagesSum, RecipientsSum)
				.title(headerComponent("JPS Dissemination Summary Report").setStyle(boldCenteredStyle.bold()),
						createDateComponent(reportLst.getFromDate(),reportLst.getToDate()))
				.pageFooter(cmp.pageXofY().setStyle(boldCenteredStyle)).highlightDetailEvenRows()
				.setDataSource(summaryReportDataSource(reportLst))
				.summary(cmp.horizontalList(barChart).setStyle(stl.style().setTopPadding(70)));
		return jasperReportBuilder;
	}
	
	private class ChartCustomizer implements DRIChartCustomizer, Serializable {
		private static final long serialVersionUID = 1L;

		@Override
		public void customize(JFreeChart chart, ReportParameters reportParameters) {
			CategoryAxis domainAxis = chart.getCategoryPlot().getDomainAxis();
			domainAxis.setCategoryLabelPositions(CategoryLabelPositions.createUpRotationLabelPositions(Math.PI / 2));
		}
	}
	
	private ComponentBuilder<?, ?> createDateComponent(String FromDate, String toDate) {
		return cmp.horizontalList(cmp.text("From : " + FromDate + "  To : " + toDate)
				.setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT).setStyle(stl.style().bold()));
	}
	
	private JRDataSource summaryReportDataSource(SummaryReportResponseResource reportLst) {

		DRDataSource dataSource = new DRDataSource("ChannelType", "numOfMessages", "numOfRecipients","publishStatus");
		List<SummaryReportResource> summaryReportList = reportLst.getSummaryReportList();
		
		for (Iterator<SummaryReportResource> iterator = summaryReportList.iterator(); iterator.hasNext();)
		{
			SummaryReportResource reportResource = (SummaryReportResource) iterator.next();
			String status=new String();
			if(reportResource.isPublishStatus()){
				status="Success";
			} else {
				status="Failure";
			}
			dataSource.add(reportResource.getChannelType(),reportResource.getNumOfMessages(),reportResource.getNumOfRecipients(),status);		  
		}
		return dataSource;
	}

	private HorizontalListBuilder headerComponent(String title) {
		
		BufferedImage kemenImg = null;
		try {
			ClassLoader classLoader = this.getClass().getClassLoader();
			File kemenFile = new File(classLoader.getResource("kemen.png").getFile());
			kemenImg = ImageIO.read(kemenFile);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		HorizontalListBuilder headerComponent = cmp.horizontalList();
		StyleBuilder headerStyle = stl.style()
				.setVerticalTextAlignment(VerticalTextAlignment.MIDDLE)
				.setHorizontalTextAlignment(HorizontalTextAlignment.CENTER)
				.setFontSize(14).bold();
		ImageBuilder kemen = cmp.image(kemenImg).setDimension(15, 38).setHorizontalImageAlignment(HorizontalImageAlignment.RIGHT);
		headerComponent.add(cmp.hListCell(kemen));
		headerComponent.add(cmp.hListCell(cmp.text(title).setStyle(headerStyle)));
		return headerComponent;
	}
	
	

}
