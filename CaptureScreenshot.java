package com.automation.utils;

import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import org.openqa.selenium.WebDriver;
import org.testng.Reporter;

public class CaptureScreenshot {
	public static String takeScreenshot(WebDriver driver, String testcaseName) {
		String filePath = "";
	    try {
	    	
	    	File theDir = new File("./Screenshots/");
	    	 
	    	if(!theDir.exists()) {
	    		theDir.mkdirs();
	    	}
	       
	        String imageName = testcaseName + ".png";
	        
	        BufferedImage image = new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
   
	        filePath = "./Screenshots/" +imageName;
	        
	        File destiny = new File(filePath);
	        ImageIO.write(image, "png", destiny);

	    } catch (Exception ex) {
	        System.out.println("Đã xảy ra lỗi khi chụp màn hình!");
	        ex.printStackTrace();
	    }
	    return filePath;
	}
	public static void attachScreenshot(String filePath) {
		try {
			System.setProperty("org.uncommons.reportng.escape-output", "false");
			File file = new File(filePath);
			Reporter.log("<br> <a title=\"Screenshot\" href=\"" +file.getAbsolutePath()+"\" >" +"<img alt='"
			+file.getName()+"'src='"+file+ "' height= '243' width='418'></a><br>");
		
		}catch(Exception e) {
			System.out.println("Lỗi đính kèm ảnh");
		}
	}
}
