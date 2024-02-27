package com.ripple.mplus.demo;

/**
 * 类Generator.java的实现描述：mp代码生成器
 * @author wanglei
 */
public class MainGenerator {

    /**
     * <p>
     * MySQL 生成演示
     * </p>
     */
    public static void main(String[] args) {

        // 作者
        String author = "wanglei";

        // 本地项目路径：绝对路径
        String projectUrl = "/Users/wanglei/IdeaProjects/AppFundBillService2";

        // 前缀：t_打头的表名需要改（TIService -> IService）
        String tablePrefix = "t_";

        // 表名：待生成的表名
        //String[] table_names = new String[] { "t_common_mapping_config", "t_fund_bill_summary", "t_bank_flow_summary"};

        String[] table_names = new String[] { "t_original_bill" };

        // entity目录：fundbase / originalbill / standardbill
        String dir = "originalbill";

        // jdbc连接
        String[] jdbc = jdbc(dir);

        // dao/xml目录: mapper / originalmapper/ standardmapper
        String mapperDir = mapperDir(dir);

        for (String table_name : table_names) {
            Generator.doGenerator(author, dir, mapperDir, projectUrl, jdbc, tablePrefix, table_name, true);
        }

    }
    private static String mapperDir(String dir){
        switch(dir){
            case "fundbase":
                return "mapper";
            case "originalbill":
                return "originalmapper";
            case "standardbill":
                return "standardmapper";
            default :
                throw new RuntimeException("目录错误");
        }
    }

    private static String[] jdbc(String dir){
        switch(dir){
            case "fundbase":
                return new String[]{"jdbc:mysql://10.69.35.51:3658/fund_base?socketTimeout=5000&connectTimeout=5000", "fund_base_user", "d@fT4s$D5Gt"};
            case "originalbill":
                return new String[]{"jdbc:mysql://10.69.35.51:3658/fund_base?socketTimeout=5000&connectTimeout=5000", "fund_base_user", "d@fT4s$D5Gt"};
            case "standardbill":
                return new String[]{"jdbc:mysql://10.69.35.51:3658/fund_base?socketTimeout=5000&connectTimeout=5000", "fund_base_user", "d@fT4s$D5Gt"};
            default :
                throw new RuntimeException("目录错误");
        }
    }

}

