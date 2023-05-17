package com.sourabhsurve.SBT_BatchJob_DBToCSV;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SbtBatchJobDbToCsvApplication {

	public static void main(String[] args) {
		SpringApplication.run(SbtBatchJobDbToCsvApplication.class, args);
		System.out.println("Connected....");
	}

}
