package com.venu.keyworddriven.ExecutionEngine;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.venu.keyworddriven.Base.Basics;

public class KeyWordEngine {

    public WebDriver driver;
    public Basics base;

    public static Workbook book;
    public static Sheet sheet;

    public final String Scenario_Excel_Path = "F:/JavaPrograms/JavaProjects/KeywordDrivenFramework"
    		+ "/src/main/java/com/venu/keyworddriven/Scenarios/TestSteps.xlsx";
    public void startExecution(String sheetName) {
        String LocatorName = null;
        String LocatorValue = null;
        FileInputStream fis = null;

        try {
            fis = new FileInputStream(Scenario_Excel_Path);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            book = WorkbookFactory.create(fis);
        } catch (EncryptedDocumentException | IOException e) {
            e.printStackTrace();
        }

        if (book != null) {
            sheet = book.getSheet(sheetName);
            System.out.println(sheet);
            if (sheet != null) {
                int k = 0;
                for (int i = 0; i <= sheet.getLastRowNum(); i++) {
                    try {
                        String LocatorColValue = sheet.getRow(i + 1).getCell(k + 2).toString().trim();
                        if (!LocatorColValue.equalsIgnoreCase("NA")) {
                            LocatorName = LocatorColValue.split("=")[0].trim();
                            LocatorValue = LocatorColValue.split("=")[1].trim();
                        }

                        String Script_action = sheet.getRow(i + 1).getCell(k + 3).toString().trim();
                        String Script_Value = sheet.getRow(i + 1).getCell(k + 4).toString().trim();

                        switch (Script_action) {
                            case "Openbrowser":
                                base = new Basics();
                                try {
                                    base.Initialize_Properties();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                if (Script_Value.isEmpty() || Script_Value.equals("NA")) {
                                    driver = base.Initialize_Browser(base.prop.getProperty("browser"));
                                } else {
                                    driver = base.Initialize_Browser(Script_Value);
                                }
                                break;
                            case "EnterURL":
                                if (driver != null) {
                                    if (Script_Value.isEmpty() || Script_Value.equals("NA")) {
                                        driver.get(base.prop.getProperty("url"));
                                    } else {
                                        driver.get(Script_Value);
                                    }
                                }
                                break;
                            case "quit":
                                if (driver != null) {
                                    driver.quit();
                                }
                                break;
                            default:
                                break;
                        }

                        switch (LocatorName) {
                            case "name":
                                WebElement element = driver.findElement(By.name(LocatorValue));
                                if (Script_action.equalsIgnoreCase("sendkeys")) {
                                    element.clear();
                                    element.sendKeys(Script_Value);
                                } else if (Script_action.equals("click")) {
                                    element.click();
                                }
                                LocatorName = null;
                                break;
                            case "xpath":
                                WebElement elementBtn = driver.findElement(By.xpath(LocatorValue));
                                elementBtn.click();
                                // LocatorName=null;
                                break;
                            default:
                                break;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } else {
                System.out.println("Sheet with name '" + sheetName + "' not found in the Excel file.");
            }
        } else {
            System.out.println("Workbook is null. Check the Excel file.");
        }
    }
}
