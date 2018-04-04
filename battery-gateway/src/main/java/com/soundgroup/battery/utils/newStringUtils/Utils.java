package com.soundgroup.battery.utils.newStringUtils;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;


/**
 * @ClassName: Utils
 * @author Tomlin
 * @date 2015-6-9 上午09:11:28
 * @version V1.0
 */
public class Utils {
	private static final Logger log = Logger.getLogger(Utils.class);
	
	

	
	public static Map wristwatchToPlatform(String message){
		Map result = new HashMap();
		if(null == message || "".equals(message)){
			result.put("status", "0");
			result.put("msg", "传入消息为空！");
			return result;
		}
		
		String targetMessage = message.substring(1, message.length()-1);
        result.put("status", "1");
        result.put("msg", "操作成功！");
        result.put("responseMessage",targetMessage);
        result.put("responseCommand", targetMessage);
		return result;
	}
	
	public static Location StringArrayToLocation(String[] locationArray){
		Location location = new Location();
		location.setYear(locationArray[0]);
		location.setTime(locationArray[1]);
		location.setIsLocation(locationArray[2]);
		location.setLatitude(locationArray[3]);
		location.setLatitudeIdentity(locationArray[4]);
		location.setLongitude(locationArray[5]);
		location.setLongitudeIdentity(locationArray[6]);
		location.setSpeed(locationArray[7]);
		location.setDirection(locationArray[8]);
		location.setAltitude(locationArray[9]);
		location.setSatelliteNumber(locationArray[10]);
		location.setSignalStrength(locationArray[11]);
		location.setPowerPercent(locationArray[12]);
		location.setStepNumber(locationArray[13]);
		location.setRollNumber(locationArray[14]);
		location.setStatus(locationArray[15]);
		location.setStationNumber(locationArray[16]);
		location.setDelay(locationArray[17]);
		location.setCountryCode(locationArray[18]);
		location.setNetworkCode(locationArray[19]);
		location.setConnectStationAreaCode(locationArray[20]);
		location.setConnectStationNumber(locationArray[21]);
		location.setConnectStationSignalStrength(locationArray[22]);
		if(locationArray.length > 25){
			location.setNearbyStationAreaCode1(locationArray[23]);
			location.setNearbyStationNumber1(locationArray[24]);
			location.setNearbyStationSignalStrength1(locationArray[25]);
		}
		if(locationArray.length > 28){
			location.setNearbyStationAreaCode2(locationArray[26]);
			location.setNearbyStationNumber2(locationArray[27]);
			location.setNearbyStationSignalStrength2(locationArray[28]);
		}
		if(locationArray.length > 31){
			location.setNearbyStationAreaCode3(locationArray[29]);
			location.setNearbyStationNumber3(locationArray[30]);
			location.setNearbyStationSignalStrength3(locationArray[31]);
		}
		if(locationArray.length > 34){
			location.setNearbyStationAreaCode4(locationArray[32]);
			location.setNearbyStationNumber4(locationArray[33]);
			location.setNearbyStationSignalStrength4(locationArray[34]);
		}
		if(locationArray.length > 37){
			location.setNearbyStationAreaCode5(locationArray[35]);
			location.setNearbyStationNumber5(locationArray[36]);
			location.setNearbyStationSignalStrength5(locationArray[37]);
		}
		if(locationArray.length > 40){
			location.setNearbyStationAreaCode6(locationArray[38]);
			location.setNearbyStationNumber6(locationArray[39]);
			location.setNearbyStationSignalStrength6(locationArray[40]);
		}
		return location;
	}
	
	public static void main(String[] args) {
		String sns = "[3G*1451510836*00CB*UD,090615,020535,V,28.143911,N,112.9363403,E,0.00,0.0,0.0,0,19,100,0,6,00000000,7,255,460,0,29521,17162,119,29521,51323,120,29521,17163,118,29521,57222,118,29521,17161,117,29521,64651,116,29521,22592,113]";
		System.out.println("main:"+Utils.wristwatchToPlatform(sns));
		
	}
	
	public static String getCommandLength(String messageHexLength){
		if(messageHexLength.length() == 4){
			return messageHexLength;
		}else if(messageHexLength.length() == 3){
			messageHexLength = "0" + messageHexLength;
		}else if(messageHexLength.length() == 2){
			messageHexLength = "00" + messageHexLength;
		}else if(messageHexLength.length() == 1){
			messageHexLength = "000" + messageHexLength;
		}
		return messageHexLength;
	}
}
