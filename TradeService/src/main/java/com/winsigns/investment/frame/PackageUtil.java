package com.winsigns.investment.frame;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PackageUtil {
	
	public static List<String> getClassName(String packageName) { 
		
		String filePath = ClassLoader.getSystemResource("").getPath() + packageName.replace(".", "\\");
		List<String> classNames = new ArrayList<String>();  
		
        File file = new File(filePath);  
        String[] classes = file.list();
        for(String className : classes){
			className = className.substring(0,className.length()-6);
			//拼接上包名，变成全限定名
			String fullName = packageName+"."+className;
			
			classNames.add(fullName);
		}
        
		return classNames;
    }  
  
}
