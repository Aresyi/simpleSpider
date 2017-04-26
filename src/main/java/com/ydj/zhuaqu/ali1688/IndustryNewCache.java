package com.ydj.zhuaqu.ali1688;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
*
* @author : Ares.yi
* @createTime : 2014-11-10 上午11:13:42 
* @version : 1.0 
* @description : 
*
 */
public class IndustryNewCache {
	
	private static Map<String,String> newMap = new HashMap<String, String>();
	private static Map<String,List<Industry>> temp = new HashMap<String,List<Industry>>();
	private static List<Industry> oneList = new ArrayList<Industry>();
	
    
    static{
    	 addNewMapData();
         addUserIndustryCache();
    }
    
    /**
     * 根据最新的code获取最新的行业名称
     * @author yanyu
     * @param code
     * @return
     */
    public static String getName(String code){
    	try {
    		return newMap.get(code);
		} catch (Exception e) {
			return "未知分类";
		}
    }
    
  
    private static void addNewMapData(){
    	newMap.put("01"    ,"服装纺织|面料");
    	newMap.put("02"    ,"鞋靴|箱包|配饰");
    	newMap.put("03"    ,"橡塑|化工|钢材|石油|煤炭");
    	newMap.put("04"    ,"汽车|汽车配件");
    	newMap.put("05"    ,"农产品|食品");
    	newMap.put("06"    ,"美妆日化");
    	newMap.put("07"    ,"母婴用品|童装|玩具");
    	newMap.put("08"    ,"家纺家饰|家装建材");
    	newMap.put("09"    ,"电子|电气|电工|仪表");
    	newMap.put("10"    ,"安防|照明");
    	newMap.put("11"    ,"机械|五金|轴承");
    	newMap.put("12"    ,"医药卫生");
    	newMap.put("13"    ,"百货|工艺品");
    	newMap.put("14"    ,"包装|印刷|办公用品");
    	newMap.put("15"    ,"物流运输");
    	newMap.put("16"    ,"金融|教育|会计|法律|人力资源|广告");
    	newMap.put("17"    ,"IT通讯|家电数码");
    	newMap.put("18"    ,"餐饮旅游服务");
    	newMap.put("19"    ,"房地产");
    	newMap.put("20"    ,"公共事业");
    	newMap.put("21"    ,"微商直销");

    	newMap.put("0101"    ,"服装服饰");
    	newMap.put("0201"    ,"纺织面料");
    	
    	/**
    	 * 2016.2.25  去掉行业 '皮革面料' 合并到'纺织面料'
    	 */
    	//newMap.put("0301"    ,"皮革面料");
    	newMap.put("9901"    ,"其它");

    	newMap.put("0102"    ,"鞋靴");
    	newMap.put("0202"    ,"箱包");
    	newMap.put("0302"    ,"眼镜");
    	newMap.put("0402"    ,"帽子|围巾|手套");
    	newMap.put("9902"    ,"其它");

    	newMap.put("0103"    ,"橡胶塑料");
    	newMap.put("0203"    ,"化工原材料");
    	newMap.put("0303"    ,"矿业|煤炭");
    	newMap.put("0403"    ,"玻璃|陶瓷|混凝土");
    	newMap.put("0503"    ,"石油|天然气能源");
    	newMap.put("0603"    ,"再生能源");
    	// 将人的行业二级“钢材丨矿业丨煤炭” 拆分为“钢材|钢铁”和“矿业|煤炭”
    	newMap.put("0703"    ,"钢材|钢铁");
    	newMap.put("0803"    ,"木材|林业");
    	newMap.put("9903"    ,"其它");

    	newMap.put("0104"    ,"汽车整车销售|制造");
    	newMap.put("0204"    ,"汽车配件");
    	/**
    	 * 2016.2.25  去掉行业'汽车用品' 合并到'汽车配件'
    	 */
    	//newMap.put("0304"    ,"汽车用品");
    	newMap.put("0404"    ,"船舶配件");
    	newMap.put("0504"    ,"摩托车配件");
    	newMap.put("0604"    ,"非机动车配件");
    	newMap.put("0704"    ,"汽车维修|汽车养护");
    	newMap.put("9904"    ,"其它");

    	newMap.put("0105"    ,"农产品");
    	newMap.put("0205"    ,"食品饮料");
    	newMap.put("0605"    ,"海鲜水产品");
    	newMap.put("0305"    ,"酒业");
    	newMap.put("0405"    ,"烟草");
    	newMap.put("0505"    ,"化肥|饲料");
    	newMap.put("9905"    ,"其它");

    	newMap.put("0106"    ,"美容护肤");
    	newMap.put("0206"    ,"美发造型");
		newMap.put("0406"    ,"美甲");
    	newMap.put("0306"    ,"家化|家清纸品");
    	newMap.put("9906"    ,"其它");

    	newMap.put("0107"    ,"母婴用品");
    	newMap.put("0207"    ,"玩具");
    	newMap.put("0307"    ,"童装");
    	newMap.put("9907"    ,"其它");

    	newMap.put("0108"    ,"家饰");
    	newMap.put("0208"    ,"家具灯具");
    	newMap.put("0308"    ,"床上用品|毛巾");
    	newMap.put("0408"    ,"装修建材");
    	newMap.put("0508"    ,"装饰装修服务");
    	newMap.put("9908"    ,"其它");

    	newMap.put("0109"    ,"电工器材");
    	newMap.put("0209"    ,"高、低压电器");
    	newMap.put("0309"    ,"仪器仪表");
    	newMap.put("0409"    ,"电气工控");
    	newMap.put("0509"    ,"半导体|集成电路");
    	newMap.put("9909"    ,"其它");

    	newMap.put("0110"    ,"照明器材");
    	newMap.put("0210"    ,"LED");
    	newMap.put("0310"    ,"监控器材");
    	newMap.put("0410"    ,"个人防护");
    	newMap.put("0510"    ,"消防|防盗");
    	newMap.put("0610"    ,"其它");

    	newMap.put("0111"    ,"机械主机|配件");
    	newMap.put("0211"    ,"五金工具");
    	newMap.put("0311"    ,"轴承");
    	newMap.put("9911"    ,"其它");

    	newMap.put("0112"    ,"医药保健");
    	newMap.put("0212"    ,"医疗设备");
    	newMap.put("0312"    ,"宠物兽医");
    	newMap.put("0412"    ,"医院医疗");
    	newMap.put("0512"    ,"生物信息工程");
    	newMap.put("9912"    ,"其它");

    	newMap.put("0113"    ,"日用百货");
    	newMap.put("0213"    ,"工艺品");
    	newMap.put("0313"    ,"珠宝首饰");
    	newMap.put("0413"    ,"钟表");
    	newMap.put("0513"    ,"礼品");
    	newMap.put("0613"    ,"体育用品");
    	newMap.put("9913"    ,"其它");

    	newMap.put("0114"    ,"包装");
    	newMap.put("0214"    ,"印刷");
    	newMap.put("0314"    ,"标签");
    	newMap.put("0414"    ,"造纸与木材");
    	newMap.put("0514"    ,"办公用品");
    	newMap.put("9914"    ,"其它");

    	newMap.put("0115"    ,"陆运");
    	newMap.put("0215"    ,"海上运输");
    	newMap.put("0315"    ,"航空运输");
    	newMap.put("0415"    ,"快递");
    	newMap.put("0515"    ,"货运代理");
    	newMap.put("9915"    ,"其它");

    	newMap.put("0116"    ,"金融投资");
    	newMap.put("0216"    ,"教育培训");
    	newMap.put("0316"    ,"商务服务");
    	newMap.put("0416"    ,"广电传媒");
    	newMap.put("0516"    ,"法律服务");
    	newMap.put("0616"    ,"会计审计");
    	newMap.put("0716"    ,"人力资源");
    	newMap.put("0916"    ,"会务服务");
    	newMap.put("1016"    ,"广告营销");
    	newMap.put("1116"    ,"营销咨询|管理咨询");
    	/**
    	 * 增加 行业 保险 理财
    	 */
    	newMap.put("1216"    ,"保险");
    	newMap.put("1316"    ,"理财");
    	newMap.put("9916"    ,"其它");

    	newMap.put("0117"    ,"计算机产品");
    	newMap.put("0217"    ,"网络设备");
    	newMap.put("0317"    ,"数码产品");
    	newMap.put("0417"    ,"家用电器");
    	newMap.put("0517"    ,"通讯产品");
    	newMap.put("0617"    ,"互联网|电子商务");
    	newMap.put("9917"    ,"其它");

    	newMap.put("0118"    ,"餐饮娱乐");
    	newMap.put("0218"    ,"度假旅游");
    	newMap.put("9918"    ,"其它");

    	newMap.put("0119"    ,"房地产开发");
    	/**
    	 * 房地产销售 改成 房地产中介
    	 */
    	newMap.put("0219"    ,"房地产中介");
    	newMap.put("0319"    ,"物业管理");
    	newMap.put("9919"    ,"其它");

    	newMap.put("0120"    ,"政府机构");
    	newMap.put("0220"    ,"行业协会");
    	newMap.put("0320"    ,"军事机构");
    	newMap.put("0420"    ,"水利工程");
    	newMap.put("0520"    ,"电力工程");
    	newMap.put("0620"    ,"环保绿化");
    	newMap.put("9920"    ,"其它");

//    	newMap.put("0121"    ,"再生能源");
//    	newMap.put("0221"    ,"体育用品");
//    	newMap.put("0321"    ,"环保绿化");
    	newMap.put("0421"    ,"直销");
    	newMap.put("0521"    ,"微商");
    	newMap.put("9921"    ,"其它");
    }
    
	
	private static void addUserIndustryCache(){
		
		
		for (Map.Entry<String, String> e : newMap.entrySet()) {

			String code = e.getKey();
			
			Industry one = new Industry( code, e.getValue());
			
			if (code.length() == 2) {
				oneList.add(one);
			}else {
				String s = code.substring(2);
				
				List<Industry> l = new ArrayList<Industry>();
				if(temp.containsKey(s)){
					l = temp.get(s);
				}
				
				l.add(one);
				
				temp.put(s, l);
			}
		}
		
	}
	
	public static List<Industry> getSecondIndustry(String oneCode){
		return temp.get(oneCode);
	}
	
	public static List<Industry> getOneIndustry(){
		return oneList;
	}
	
    public static void main(String[] args) {
    }
    
    
}

