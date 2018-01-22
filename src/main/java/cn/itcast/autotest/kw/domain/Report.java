package cn.itcast.autotest.kw.domain;

/**
 * 测试报告数据
 */
public class Report {

    // 总用例数
    private int totalTests;

    // 总步骤数
    private int totalSteps;

    // 开始时间
    private long startTime;

    // 结束时间
    private long endTime;

    // 执行总时间(秒)
    private int takeTime;

    // 用例-通过数量
    private int testPassCount;

    // 用例-失败数量
    private int testFailCount;

    // 用例-跳过数量
    private int testSkipCount;

    // 步骤-通过数量
    private int stepPassCount;

    // 步骤-失败数量
    private int stepFailCount;

    // 步骤-跳过数量
    private int stepSkipCount;

    public int getTotalTests() {
        return totalTests;
    }

    public void setTotalTests(int totalTests) {
        this.totalTests = totalTests;
    }

    public int getTotalSteps() {
        return totalSteps;
    }

    public void setTotalSteps(int totalSteps) {
        this.totalSteps = totalSteps;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public int getTakeTime() {
        return takeTime;
    }

    public void setTakeTime(int takeTime) {
        this.takeTime = takeTime;
    }

    public int getTestPassCount() {
        return testPassCount;
    }

    public void setTestPassCount(int testPassCount) {
        this.testPassCount = testPassCount;
    }

    public int getTestFailCount() {
        return testFailCount;
    }

    public void setTestFailCount(int testFailCount) {
        this.testFailCount = testFailCount;
    }

    public int getTestSkipCount() {
        return testSkipCount;
    }

    public void setTestSkipCount(int testSkipCount) {
        this.testSkipCount = testSkipCount;
    }

    public int getStepPassCount() {
        return stepPassCount;
    }

    public void setStepPassCount(int stepPassCount) {
        this.stepPassCount = stepPassCount;
    }

    public int getStepFailCount() {
        return stepFailCount;
    }

    public void setStepFailCount(int stepFailCount) {
        this.stepFailCount = stepFailCount;
    }

    public int getStepSkipCount() {
        return stepSkipCount;
    }

    public void setStepSkipCount(int stepSkipCount) {
        this.stepSkipCount = stepSkipCount;
    }
}