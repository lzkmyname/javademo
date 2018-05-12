package org.lzk.apache.commondemo.jar;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;

public class TestJar {
	/**
	 * 包内配置不能修改，包外能，config先读取包外同级配置，若不存在则读取包内配置
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			//jar 包同级目录 文件
			File file = new File("a.txt");
			Reader reader = null;
			if (file.exists()) {
				// jar 外同一录路径
				reader = new FileReader("a.txt");
			} else {
				// jar 内 文件
				InputStream resourceAsStream = TestJar.class.getResourceAsStream("/a.txt");
				reader = new InputStreamReader(resourceAsStream, Charset.defaultCharset());
			}
			BufferedReader in = new BufferedReader(reader);

			String line = in.readLine();
			System.out.println(line);
			in.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
