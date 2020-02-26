package com.example.demo.handler;

import com.example.demo.hook.HookMain;
import com.google.gson.Gson;
import com.virjar.sekiro.api.SekiroRequest;
import com.virjar.sekiro.api.SekiroRequestHandler;
import com.virjar.sekiro.api.SekiroResponse;

import de.robv.android.xposed.XposedHelpers;

public class DouYinUserSearchHandler implements SekiroRequestHandler {


    @Override
    public void handleRequest(SekiroRequest sekiroRequest, SekiroResponse sekiroResponse) {
        Gson gson = new Gson();

        String query = sekiroRequest.getString("query");
        String start = sekiroRequest.getString("start", "0");
        String count = sekiroRequest.getString("count", "10");

        long param2 = Long.parseLong(start);
        int param3 = Integer.parseInt(count);

        if (query == null || query.equals("")) {
            sekiroResponse.send("请传入必需参数:query");
        }

        Class<?> SearchApi = XposedHelpers.findClass("com.ss.android.ugc.aweme.discover.api.SearchApi", HookMain.loadPackageParam.classLoader);
        Object object = XposedHelpers.callStaticMethod(SearchApi, "a", query, param2, param3);
        sekiroResponse.send(gson.toJson(object));
    }
}
