package com.venu.keywordDriven.tests;

import org.testng.annotations.Test;

import com.venu.keyworddriven.ExecutionEngine.KeyWordEngine;

public class LoginTest {
	
	public KeyWordEngine kwe;
	@Test
	public void logintestMeth(){
		kwe = new KeyWordEngine();
		kwe.startExecution("login");
	}
}
