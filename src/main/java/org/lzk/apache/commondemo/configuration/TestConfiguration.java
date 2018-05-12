package org.lzk.apache.commondemo.configuration;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Writer;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.XMLConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.junit.Test;

public class TestConfiguration {
	public static void main(String[] args) throws FileNotFoundException, ConfigurationException, IOException {
		TestConfiguration test = new TestConfiguration();
		test.saveConfiguration();
//		org.apache.commons.logging.Log
	}

	@Test
	public void propConfigurationTest() throws FileNotFoundException, ConfigurationException, IOException {
		PropertiesConfiguration conf = new PropertiesConfiguration();
		InputStream resourceAsStream = TestConfiguration.class.getResourceAsStream("/configuration.cfg");
		System.out.println(resourceAsStream);
		// System.out.println("hello");
		conf.read(new BufferedReader(
				new InputStreamReader(TestConfiguration.class.getResourceAsStream("/configuration.cfg"))));

		Configuration conf2 = conf;
		System.out.println(conf2.getString("java"));
		conf.setProperty("java", "python");
		System.out.println(conf.getProperty("java"));
		Writer out = new FileWriter("D://configuration.cfg");
		conf.write(out);
		out.close();
	}

	@Test
	public void xmlConfigurationTest() throws FileNotFoundException, ConfigurationException, IOException {
		XMLConfiguration conf = new XMLConfiguration();
		conf.read(
				new BufferedReader(new InputStreamReader(TestConfiguration.class.getResourceAsStream("/ehcache.xml"))));
		System.out.println(conf.toString());
		// System.out.println(conf2.getString("java"));
		// conf.setProperty("java", "python");
		// System.out.println(conf.getProperty("java"));
		// Writer out = new FileWriter("D://configuration.cfg");
		// conf.write(out);
		// out.close();
	}

	@Test
	public void configurationsTest() {
		Configurations configs = new Configurations();
		try {
			Configuration config = configs.properties(new File("javademo/configuration/config.properties"));
			String string = config.get(String.class, "database.user");

			String dbHost = config.getString("database.host");

			int dbPort = config.getInt("database.port");
			String dbUser = config.getString("database.user");
			String dbPassword = config.getString("database.password", "secret"); // provide a default
			long dbTimeout = config.getLong("database.timeout");
			System.out.println(string);
			List<Object> pros = Arrays.asList(dbHost, dbPort, dbUser, dbPassword, dbTimeout);
			System.out.println(pros);
		} catch (ConfigurationException cex) {
			// Something went wrong
		}
	}

	@Test
	public void xmlConfigurationsTest() {
		Configurations configs = new Configurations();
		try {
			XMLConfiguration config = configs.xml("javademo\\configuration\\path.xml");
			// XMLConfiguration config = configs.xml("path.xml");
			// access xml attribute
			String stage = config.getString("processing[@stage]");
			List<String> stages = config.getList(String.class, "processing[@stage]");
			System.out.println(stages);
			// access xml multivalue
			List<String> paths = config.getList(String.class, "processing.paths.path");
			String secondPath = config.getString("processing.paths.path(1)");
			System.out.println(stage);
			System.out.println(paths);
			System.out.println(secondPath);
		} catch (ConfigurationException cex) {
			System.out.println(cex);
		}
	}

	@Test
	public void updatingConfiguration() {
		Configurations configs = new Configurations();
		try {
			Configuration config = configs.properties(new File("javademo/configuration/config.properties"));
			String string = config.get(String.class, "database.user");

			String dbHost = config.getString("database.host");

			int dbPort = config.getInt("database.port");
			String dbUser = config.getString("database.user");
			String dbPassword = config.getString("database.password", "secret"); // provide a default
			long dbTimeout = config.getLong("database.timeout");
			List<Object> pros = Arrays.asList(dbHost, dbPort, dbUser, dbPassword, dbTimeout);
			config.setProperty("database.port", 8200);
			config.addProperty("database.type", "production");
			System.out.println(pros);
		} catch (ConfigurationException cex) {
			// Something went wrong
		}
	}

	@Test
	public void saveConfiguration() {
		Configurations configs = new Configurations();
		try {
			// obtain the configuration
			FileBasedConfigurationBuilder<XMLConfiguration> builder = configs.xmlBuilder("path.xml");
			XMLConfiguration config = builder.getConfiguration();
			// update property
			config.addProperty("newProperty", "newValue");

			// save configuration
			builder.save();
		} catch (ConfigurationException cex) {
			cex.printStackTrace();
		}
	}

}
