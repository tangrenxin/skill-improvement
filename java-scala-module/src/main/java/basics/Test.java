package basics;

/**
 * @Description:
 * @Author: tangrenxin
 * @Date: 2021/11/30 下午7:03
 */
public class Test {

  public static void main(String[] args) {
    String str = "mediaType: String ,\n"
        + "convEventType: String ,\n"
        + "tagId: String ,\n"
        + "adFormId: Int,\n"
        + "triggerId: String ,\n"
        + "dspLevel1: String ,\n"
        + "dsp: String ,\n"
        + "adId: Long,\n"
        + "placementTypeId: String ,\n"
        + "billingType: Int,\n"
        + "campaignId: Long,\n"
        + "adGroupId: Long,\n"
        + "customerId: String ,\n"
        + "industryLevel1: Int,\n"
        + "industryLevel2: Int,\n"
        + "appId: Long,\n"
        + "packageName: String ,\n"
        + "appCategoryLvl1Id: Int,\n"
        + "brandRegion: String ,\n"
        + "emiRegion: String ,\n"
        + "experimentId: String ,\n"
        + "expIdlList: String ,\n"
        + "adExpIdlList: String ,\n"
        + "delivery: Long,\n"
        + "fee: Double,\n"
        + "oaid: String ,\n"
        + "imei: String ,\n"
        + "did: String ,\n"
        + "masterimei: String ,\n"
        + "masterdid: String ,\n"
        + "price: Double,\n"
        + "feeRtb: Double,\n"
        + "newActive: Long,\n"
        + "dayActive: Long,\n"
        + "day3Active: Long,\n"
        + "newDayActive: Long,\n"
        + "newDay3Active: Long,\n"
        + "deliverFee: Double,\n"
        + "deliverView: Long,\n"
        + "deliverClick: Long,\n"
        + "query: Long,\n"
        + "view: Long,\n"
        + "click: Long,\n"
        + "startDownload: Long,\n"
        + "endInstall: Long,\n"
        + "active: Long,\n"
        + "deliverStartDownload: Long,\n"
        + "deepCovType: String ,\n"
        + "seqNum: Int,\n"
        + "isPersonalizedDetail: Int,\n"
        + "isApp: Int,\n"
        + "personalizedType: String ,\n"
        + "pcvr: Double,\n"
        + "pctr: Double,\n"
        + "targetConvType: Int,\n"
        + "pdeepCvr: Double,\n"
        + "targetCpa: Double,\n"
        + "targetConvNum: Long,\n"
        + "oneTrackParams: mutable.Map[String, String],\n"
        + "actionType: String ,\n"
        + "serverTime: Long,\n"
        + "logSource: String ,\n"
        + "clientAppVersion: String ,\n"
        + "assetId: Long,\n"
        + "isFirstPay: Int,\n"
        + "dayPay: Int,\n"
        + "apkChannel: String ,\n"
        + "pcvrfix: Double,\n"
        + "billNum: Int,\n"
        + "payAmount: Double,\n"
        + "deliverDay: Long,\n"
        + "totalFee: Double,\n"
        + "retention2: Long,";
    String[] lines = str.split("\n");
    for (String line : lines) {
      String[] split = line.split(":");
//      if(data.totalFee!=null) data.totalFee else 0;
      String key = split[0];
      String v = getV(split[1]);
      System.out.print("if(data."+key+"!=null) data."+key+" else "+v +",\n");




    }


  }

  private static String getV(String s) {
    if(s.contains("String")){
      return "\"\"";
    } else if (s.contains("Long")){
      return "0L";
    }
    else if (s.contains("Int")){
      return "0";
    }
    else if (s.contains("Double")){
      return "0.0";
    }
    else if (s.contains("mutable")){
      return "JavaConversions.mapAsScalaMap(new util.HashMap[String, String]())";
    }
    return "";
  }

}
