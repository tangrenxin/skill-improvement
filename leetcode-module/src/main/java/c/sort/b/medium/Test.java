package c.sort.b.medium;

/**
 * @Description:
 * @Author: tangrenxin
 * @Date: 2021/10/15 15:54
 */
public class Test {

  public static void main(String[] args) {
    String str = "select dt, d.media_type as mediaType, sum(feeEffect) as cash "
        + "from cube_persist_fee_dt c "
        + "left join dim_tag_id d on c.tagId = d.tag_id "
        + "left join dim_demand_info dim on c.adId = dim.ad_id "
        + "left join dim_emi_industry ind  "
        + "    ON ind.customer_id = dim.customer_id "
        + "    AND ind.app_id = dim.app_id "
        + "    AND ind.valid_from <= c.dt "
        + "    AND ind.valid_to >= c.dt "
        + "where dt =20211014 and ind.billing_type not in ('内部结算','不结算') "
        + "group by dt, d.media_type";
    System.out.println(str);
  }

}
