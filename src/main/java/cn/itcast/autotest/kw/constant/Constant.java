package cn.itcast.autotest.kw.constant;

/**
 * 常量
 */
public class Constant {
    //测试用例数据存放目录
    public static final String TEST_CASE_DATA_DIR = System.getProperty("user.dir") + "/data/testcase/";
    //测试套件文件路径
    public static final String SUITE_FILE_PATH = Constant.TEST_CASE_DATA_DIR + "suite.xlsx";

    //用例和步骤执行结果
    public static final String PASS = "PASS";
    public static final String FAIL = "FAIL";
    public static final String SKIP = "SKIP";

}