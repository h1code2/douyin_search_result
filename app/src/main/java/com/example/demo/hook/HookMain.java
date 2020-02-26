package com.example.demo.hook;


import android.os.Bundle;

import com.example.demo.handler.DouYinUserSearchHandler;
import com.virjar.sekiro.api.SekiroClient;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class HookMain implements IXposedHookLoadPackage {
    public static XC_LoadPackage.LoadPackageParam loadPackageParam = null;

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
        if (lpparam.packageName.equals("com.ss.android.ugc.aweme.lite")) {
            HookMain.loadPackageParam = lpparam;
            try {

                // 在com.ss.android.ugc.aweme.splash.SplashActivity -> onCreate 注入服务

                XposedHelpers.findAndHookMethod("com.ss.android.ugc.aweme.splash.SplashActivity", lpparam.classLoader, "onCreate", Bundle.class, new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                        final SekiroClient sekiroClient = SekiroClient.start("sekiro.virjar.com", "client-6", "group-6");
                        sekiroClient.registerHandler("dy_search", new DouYinUserSearchHandler());
                        XposedBridge.log("=========== sekiro服务启动成功 ===========");
                    }
                });
            } catch (Exception e) {
                XposedBridge.log("=========== Sekiro服务启动失败 ===========");
            }

        }
    }
}
