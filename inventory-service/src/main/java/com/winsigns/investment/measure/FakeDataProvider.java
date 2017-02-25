package com.winsigns.investment.measure;

import java.util.HashMap;
import java.util.Map;

//FIXME: 该类仅用于前期测试。后期不应该存在
@Deprecated
public class FakeDataProvider {
	private static Map<String, Double> cache = new HashMap<String, Double>();
	private static FakeDataProvider instance = new FakeDataProvider();
	
	public static FakeDataProvider getInst(){
		return instance;
	}
	
	public void put(String key, double value){
		cache.put(key, value);
	}
	
	public Double get(String key){
		return cache.get(key);
	}
	
	public void reset(String key){
		cache.put(key, null);
	}
	
	public int get_version(){
		Double s  = cache.get("version");
		
		if (s == null){
			s = 0.0;
		}

		return s.intValue();
	}
	
	public int update_version(){
		Double s = cache.get("version");
		if (s == null){
			s = 0.0;
		}

		cache.put("version", s+1);
		
		return get_version();
	}
	
	//模拟数据库
	public class Database{
		public double get(String name, String time){
			if (name == "cost"){
				return 1000.12;
			}
			if (name == "position"){
				return 123;
			}
			return 0;
		}
	}
	
	//模拟远程调用
	public class RemoteCall{
		public Double[] get(String service, String index_name, String version){
			String key = String.format("%s.%s", index_name, version);
			Double v = getInst().get(key);
			if (v==null){
				return null;
			}else{
				return new Double[]{getInst().get(key)};
			}
		}
	}
	
	private static Database database = getInst().new Database();
	public static Database getDatabase(){
		return database;
	}

	private static RemoteCall remoteCall = getInst().new RemoteCall();
	public static RemoteCall getRemoteCall(){
		return remoteCall;
	}
}
