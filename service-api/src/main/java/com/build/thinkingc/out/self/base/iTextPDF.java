package com.build.thinkingc.out.self.base;

import java.io.*;
import java.util.*;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.AcroFields.Item;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;

public class iTextPDF {

	/**
	 * ErrCode:999 操作异常
	 * ErrCode:998 填充字段数量缺少
	 * ErrCode:997 填充字段名称不对应
	 * ErrCode:1000 填充数据成功
	 * @throws Exception
	 */
	 public void generateData() throws Exception{  
	        Map<String,Object> dataMap=new HashMap<String,Object>();  
	        dataMap.put("name", "张三");
	        dataMap.put("account","657676767766727272");  
	        dataMap.put("savedate", "1997-09-18");  
	        dataMap.put("balance", "1000"); 
	        dataMap.put("groupORTemp","1");
	        
	        convertTransData(dataMap);
	        System.out.println("执行完毕");  
	    }  
	
	@SuppressWarnings("unused")
	protected Map<String,Object> convertTransData(Map<String,Object> inputData) throws Exception {
		if( inputData != null && !inputData.isEmpty()){
			String template = "iTextPDFTest.pdf";
			try {
				InputStream in = this.getClass().getResourceAsStream(template);
				Map<String,Object> resultMap =  new HashMap<String,Object>();
				resultMap = returnCode(resultMap,new PdfReader(in),inputData);
				if(  (Integer)(resultMap.get("ErrCode")) == 998 ){
					System.out.println("填充字段数量缺少");
				}
				ByteArrayOutputStream out = (ByteArrayOutputStream) generate(new PdfReader(in), inputData);
				ByteArrayInputStream ret = new ByteArrayInputStream(out.toByteArray());
				// 将pdf字节流输出到文件流
				OutputStream fos = new FileOutputStream("D:/FinanceBuy.pdf");
				fos.write(out.toByteArray());
				fos.close();
				out.close();
				return resultMap;
			} catch (Exception e) {
				throw new Exception(e);
			}
		}
		return null;
	}

	/**
//	 * 返回Map结果集    ErrCode和   生成的模板文件地址
	 * @param resultMap
	 * @param template
	 * @param data
	 * @return
	 * @throws IOException 
	 * @throws DocumentException 
	 */
	private static Map<String,Object> returnCode( Map<String,Object> resultMap, PdfReader template, Map<String,Object> dataMap ) 
			throws DocumentException, IOException{
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		PdfStamper stamp = new PdfStamper(template, out);
		AcroFields form = stamp.getAcroFields();
		Map<String,Item> formField = form.getFields();
		Set<String> filedKeySet = formField.keySet();
		Set<String> dataKeySet = dataMap.keySet();
		if( filedKeySet.size() != dataKeySet.size() ){
			resultMap.put("ErrCode", 998);
			resultMap.put("fileAdd","");
			return resultMap;
		}
		if( !filedKeySet.containsAll(dataKeySet) ){
			resultMap.put("ErrCode", 997);
			resultMap.put("fileAdd","");
			return resultMap;
		}
		return  resultMap;
	}
	
	@SuppressWarnings("rawtypes")
	public static OutputStream generate(PdfReader template, Map<String,Object> data) throws Exception {
		BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
		try {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			PdfStamper stamp = new PdfStamper(template, out);
			AcroFields form = stamp.getAcroFields();
//			Map<String,Item> formField = form.getFields();
//			Set<String> keySet = formField.keySet();
			
			// set the field values in the pdf form
			for (Iterator it = data.keySet().iterator(); it.hasNext();) {
				String key = (String) it.next();
				String value = (String) data.get(key);
				form.setFieldProperty(key, "textfont", bfChinese, null);
				form.setField(key, value);

			}
			stamp.setFormFlattening(true);
			stamp.close();
			template.close();
			return out;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static void main(String sck[]){
		iTextPDF itextPdf=  new iTextPDF();
		try {
			itextPdf.generateData();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
