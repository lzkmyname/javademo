package org.lzk.apache.commondemo.log4j;

import org.apache.log4j.Logger;

public class TestLog4j {
	static Logger logger = Logger.getLogger(TestLog4j.class);
	public static void main(String[] args) {
		System.out.println( logger.getLevel());
	}
	public void logTest() {
		
	}

}
