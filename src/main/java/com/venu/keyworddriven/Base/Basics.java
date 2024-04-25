package com.venu.keyworddriven.Base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class Basics {
	public WebDriver driver;
	public Properties prop;
	
	public WebDriver Initialize_Browser(String BrowserName) {
		if(BrowserName.equals("chrome")) {
			
			if(prop.getProperty("headless").equals("yes")) {
				ChromeOptions options = new ChromeOptions();
				options.addArguments("--headless");
				driver=new ChromeDriver(options);
			}else {
				driver=new ChromeDriver();
			}
			
		}
		return driver;
	}
	
	public Properties Initialize_Properties(){
		prop = new Properties();
		FileInputStream Fis;
		try {
		    Fis = new FileInputStream("F:\\JavaPrograms\\JavaProjects\\KeywordDrivenFramework\\src\\main\\java\\com\\venu\\keyworddriven\\hrm\\config.properties");
		    prop.load(Fis);
		} catch (Exception e) {
		    e.printStackTrace();
		}
		return prop;
		}
}


