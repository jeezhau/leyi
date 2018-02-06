package me.jeekhan.leyi.common;

import java.util.ResourceBundle;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

public class EncryptPropertyConfigurer extends PropertyPlaceholderConfigurer{
	private String env = SysPropUtil.getParam("env");	//当前环境：dev/prd
	private String[] encryptPropNames = {"jdbc.url","jdbc.username","jdbc.password"}; //加密属性集,区分生产与开发，开发不加密，生产加密
	//解密属性
	@Override
	public String convertProperty(String propName,String propValue){
		int flag = this.isEncryptProp(propName);
		if(0 == flag){//通用属性
			return propValue;
		}
		//需要区分开开发与生产的属性
		String new_propName = env + "." + propName;	//根据环境获取匹配的属性名
		String new_propValue = getParam(new_propName);
		if(1 == flag) {
			System.out.println(propName + ":" + new_propValue);
			return new_propValue;
		}else if(2 == flag){	//解密处理
			String decryptValue = null;
			try {
				decryptValue = DesUtils.decryptHex(new_propValue);
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println(propName + ":" + decryptValue);
			return decryptValue;
		}
		return "";
	}
	/**
	 * 判断参数中属性是否为加密属性
	 * @param propertyName	属性名称
	 * @return	0:通用属性，没有加密；1:需要区分开开发与生产的属性，没有加密；2:需要区分开开发与生产的属性，已经加密
	 */
	private int isEncryptProp(String propertyName){
		for(String encryptPropName:encryptPropNames){
			if(propertyName.equals(encryptPropName)){//区分开发与生产的属性
				if("prd".equals(env)){//需要加密环境
					return 2;	
				}else {
					return 1;
				}
			}	
		}
		return 0;	//生产环境中的通用属性
	}
	
	private static String prop_file = "jdbc";
	
	private static  ResourceBundle BUNDLE = ResourceBundle.getBundle(prop_file);
	
	public static String getParam(String key) {
		return BUNDLE.getString(key);
	}
	
}
