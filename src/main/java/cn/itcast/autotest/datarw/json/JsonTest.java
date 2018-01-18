package cn.itcast.autotest.datarw.json;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.FileUtils;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;

public class JsonTest {

    @Test
    public void json() throws IOException {
        File file = new File(System.getProperty("user.dir") + "/data/json.txt");
        String data = FileUtils.readFileToString(file);

        //解析json字符串
        JSONObject jsonObject = JSONObject.parseObject(data);

        String name = jsonObject.getString("name");
        System.out.println("name=" + name);
        int page = jsonObject.getIntValue("page");
        System.out.println("page=" + page);

        JSONObject address = jsonObject.getJSONObject("address");
        String city = address.getString("city");
        System.out.println("city=" + city);

        JSONArray links = jsonObject.getJSONArray("links");
        for (int i = 0; i < links.size(); i++) {
            JSONObject link = links.getJSONObject(i);
            String linkName = link.getString("name");
            System.out.println("linkName=" + linkName);
        }
    }
}
