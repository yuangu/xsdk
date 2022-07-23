package com.seantone.xsdk.core;
import android.content.Context;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import dalvik.system.DexFile;
public class ClassUtils {
    //给一个接口，返回这个接口的所有实现类
    public static List<Class> getAllClassByInterface(Context context, Class c) {
        List<Class> returnClassList = new ArrayList<>(); //返回结果
        //如果不是一个接口，则不做处理
        if (c.isInterface()) {
            String packageName = c.getPackage().getName(); //获得当前的包名
            try {
                List<Class> allClass = getClasses(context, packageName); //获得当前包下以及子包下的所有类
                //判断是否是同一个接口
                for (int i = 0; i < allClass.size(); i++) {
                    if (c.isAssignableFrom(allClass.get(i))) { //判断是不是一个接口
                        if (!c.equals(allClass.get(i))) { //本身不加进去
                            returnClassList.add(allClass.get(i));
                        }
                    }
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return returnClassList;
    }

    //从一个包中查找出所有的类，在jar包中不能查找
    public static List<Class> getClasses(Context context, String packageName) throws ClassNotFoundException, IOException {
        List<String> dexFileClassNames = getDexFileClassNames(context, packageName);
        ArrayList<Class> classes = new ArrayList<>();
        for (String s : dexFileClassNames) {
            try {
                Class scanClass = Class.forName(s);
                classes.add(scanClass);
            } catch (Exception e) {
                continue;
            }
        }
        return classes;
    }

    public static List<String> getDexFileClassNames(Context context, String packageName) throws IOException {
        DexFile df = new DexFile(context.getPackageCodePath());//通过DexFile查找当前的APK中可执行文件
        Enumeration<String> enumeration = df.entries();//获取df中的元素  这里包含了所有可执行的类名 该类名包含了包名+类名的方式
        List<String> classes = new ArrayList<>();
        while (enumeration.hasMoreElements()) {//遍历
            String className = enumeration.nextElement();
            if (className.startsWith(packageName)) {
                classes.add(className);
            }
        }
        return classes;
    }
}
