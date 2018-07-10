package com.seezoon.framework.common.utils;

import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.seezoon.framework.common.Constants;
import com.seezoon.framework.modules.system.entity.SysArea;

/**
 * 解析 地区数据的
 * https://github.com/coderbusy/china-area json 数据
 * @author hdf
 * 2018年7月9日
 */
public class AreaParser {

	public static final String JSON_URL = "https://github.com/coderbusy/china-area/blob/master/data/20171011/China.json";
	
	public static List<SysArea> parse() throws MalformedURLException, IOException {
		//String json = IOUtils.toString(new URL(JSON_URL),"UTF-8");
		String json = IOUtils.toString(new FileReader("/Users/hdf/Downloads/china-area-master/data/20171011/China.json"));
		List<AreaJsonObject> parseArray = JSON.parseArray(json,AreaJsonObject.class);
		List<AreaJsonObject> provinces = Lists.newArrayList();
		Map<String,String> provincesMap = Maps.newHashMap();
		List<AreaJsonObject> cities = Lists.newArrayList();
		Map<String,String> citiesMap = Maps.newHashMap();
		List<AreaJsonObject> counties = Lists.newArrayList();
		
		parseArray.forEach((v)->{
			String shengJiName = v.getShengJiName();
			String diji = v.getDiji();
			String xianji = v.getXianji();
			if (StringUtils.isNotEmpty(shengJiName) && StringUtils.isEmpty(diji) && StringUtils.isEmpty(xianji)) {//省
				System.out.println(v.getShengJiName());
				provincesMap.put(StringUtils.trim(v.getShengJiName()), StringUtils.trim(v.getQuHuaDaiMa()));
				provinces.add(v);
				return;
			}
			if (StringUtils.isNotEmpty(shengJiName) && StringUtils.isNotEmpty(diji) && StringUtils.isEmpty(xianji)) {//市
				//处理4个直辖市 2 个特区
				if ("北京市".equals(v.getDiji().trim())) {
					v.setQuHuaDaiMa("110100");
				} else if ("上海市".equals(v.getDiji().trim())) {
					v.setQuHuaDaiMa("310100");
				} else if ("天津市".equals(v.getDiji().trim())) {
					v.setQuHuaDaiMa("120100");
				} else if ("重庆市".equals(v.getDiji().trim())) {
					v.setQuHuaDaiMa("500100");
				} else if ("香港特别行政区".equals(v.getDiji().trim())) {
					v.setQuHuaDaiMa("810100");
				} else if ("澳门特别行政区（澳）".equals(v.getDiji().trim())) {
					v.setQuHuaDaiMa("820100");
				}
				//县如果上级是直辖县级行政单位，则为市
				cities.add(v);
				citiesMap.put(StringUtils.trim(v.getShengJiName()) + StringUtils.trim(v.getDiji()), StringUtils.trim(v.getQuHuaDaiMa()));
				return;
			}
			//县如果上级是直辖县级行政单位，则为市
			if (StringUtils.isNotEmpty(shengJiName) && StringUtils.isNotEmpty(diji)&& diji.contains("直辖县级行政单位") && StringUtils.isNotEmpty(xianji)) {//市
				v.setDiji(v.getXianji());
				cities.add(v);
				return;
			}
			if (StringUtils.isNotEmpty(shengJiName) && StringUtils.isNotEmpty(diji) && StringUtils.isNotEmpty(xianji)) {//市
				counties.add(v);
				return;
			}
		});
		System.out.println("省个数:" + provinces.size());
		System.out.println("市个数:" + cities.size());
		System.out.println("区个数:" + counties.size());
		List<SysArea> list = Lists.newArrayList();
		provinces.forEach((v)->{
			SysArea area = new SysArea();
			area.setId(StringUtils.trim(v.getQuHuaDaiMa()));
			area.setParentId(SysArea.ROOT_ID);
			area.setType(SysArea.AREA_TYPE_PROVINCE);
			area.setName(StringUtils.trim(v.getShengJiName()));
			area.setStatus(Constants.YES);
			area.setSort(list.size());
			area.setRemarks(StringUtils.trim(v.getShengJiName()));
			list.add(area);
		});
		cities.forEach((v)->{
			SysArea area = new SysArea();
			area.setId(StringUtils.trim(v.getQuHuaDaiMa()));
			area.setParentId(provincesMap.get(StringUtils.trim(v.getShengJiName())));
			area.setType(SysArea.AREA_TYPE_CITY);
			String diji = v.getDiji().trim();
			String[] array = {"北京市","上海市","天津市","重庆市","香港特别行政区", "澳门特别行政区（澳）"};
			 if (ArrayUtils.contains(array, diji)) {
				 area.setName("市辖区");
			} else {
				area.setName(diji);
			}
			area.setStatus(Constants.YES);
			area.setSort(list.size());
			area.setRemarks(StringUtils.trim(v.getShengJiName())+"-"+area.getName());
			list.add(area);
		});
		counties.forEach((v)->{
			SysArea area = new SysArea();
			area.setId(StringUtils.trim(v.getQuHuaDaiMa()));
			area.setParentId(citiesMap.get(StringUtils.trim(v.getShengJiName()) + StringUtils.trim(v.getDiji())));
			area.setType(SysArea.AREA_TYPE_COUNTY);
			area.setName(StringUtils.trim(v.getXianji()));
			area.setStatus(Constants.YES);
			area.setSort(list.size());
			area.setRemarks(StringUtils.trim(v.getShengJiName())+"-"+StringUtils.trim(v.getDiji()) + "-" + StringUtils.trim(v.getXianji()));
			list.add(area);
		});
		return list;
	}
	public static void main(String[] args) throws MalformedURLException, IOException {
		AreaParser.parse();
	}
}
class AreaJsonObject {
	private String quHuaDaiMa;
	private String ShengJiName;
	private String diji;
	private String xianji;
	public String getQuHuaDaiMa() {
		return quHuaDaiMa;
	}
	public void setQuHuaDaiMa(String quHuaDaiMa) {
		this.quHuaDaiMa = quHuaDaiMa;
	}
	public String getShengJiName() {
		return ShengJiName;
	}
	public void setShengJiName(String shengJiName) {
		ShengJiName = shengJiName;
	}
	public String getDiji() {
		return diji;
	}
	public void setDiji(String diji) {
		this.diji = diji;
	}
	public String getXianji() {
		return xianji;
	}
	public void setXianji(String xianji) {
		this.xianji = xianji;
	}
	
	
}
