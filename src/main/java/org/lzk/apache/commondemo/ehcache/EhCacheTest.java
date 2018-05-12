package org.lzk.apache.commondemo.ehcache;

import java.net.URL;
import java.util.Arrays;
import java.util.List;

import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.Configuration;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.xml.XmlConfiguration;

public class EhCacheTest {
	public static void main(String[] args) {
		new EhCacheTest().testEhCacheXml();
	}
	public  void testEhCacheJava() {
		Cache<String, List> cache = getCache(String.class, List.class, "demo",1);
		cache.put("list", (List) Arrays.asList("hello","hai"));
		cache.put("list2",(List) Arrays.asList("hello","ha2"));
		System.out.println(cache.get("list2"));
	}
	public void testEhCacheXml() {
		CacheManager cacheManagerXml = getCacheManagerXml("/ehcache.xml");
		Cache <Number,String> cache = cacheManagerXml.getCache("bar", Number.class, String.class);
		cache.put(2,"test");
		System.out.println(cache.get(2));
	}

	public static void testEhcacheJava() {
		CacheManager cacheManager = CacheManagerBuilder.newCacheManagerBuilder()
				.withCache("preConfigured", CacheConfigurationBuilder.newCacheConfigurationBuilder(Long.class,
						String.class, ResourcePoolsBuilder.heap(10)))
				.build();
		cacheManager.init();
		Cache<Long, String> preConfigured = cacheManager.getCache("preConfigured", Long.class, String.class);
		preConfigured.put(1L, "da two");
		System.out.println(preConfigured.get(1L));

		Cache<Long, String> myCache = cacheManager.createCache("myCache", CacheConfigurationBuilder
				.newCacheConfigurationBuilder(Long.class, String.class, ResourcePoolsBuilder.heap(10)));
		myCache.put(1L, "da one!");
		String value = myCache.get(1L);
		System.out.println(value);
		cacheManager.removeCache("preConfigured");
		cacheManager.close();

	}

	/**
	 * 普通java代码获取cacheMager 
	 */
	private static CacheManager cacheManager = CacheManagerBuilder.newCacheManagerBuilder().build(true);
	/**
	 * 获取缓存对象
	 * @param k key类型
	 * @param v value类型
	 * @param name 缓存对象在cacheManager中的key
	 * @param entries  可以缓存的key-value 数量
	 * @return 
	 * @comment 使用默认配置
	 */
	public static <K, V> Cache<K, V> getCache(Class<K> k, Class<V> v, String name,long entries) {
		return cacheManager.createCache(name,
				CacheConfigurationBuilder.newCacheConfigurationBuilder(k, v, ResourcePoolsBuilder.heap(entries)));
	}
	
	
	public static CacheManager getCacheManagerXml(String xmlpath) {
		URL myUrl = EhCacheTest.class.getResource(xmlpath); 
		Configuration xmlConfig = new XmlConfiguration(myUrl); 
		CacheManager myCacheManager = CacheManagerBuilder.newCacheManager(xmlConfig); 
		myCacheManager.init();
		return myCacheManager;
	}

}
