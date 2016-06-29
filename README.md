# AndroidUtils
##Android Utils set  一个比较全面的安卓工具类集合

##HOW TO USE

###Gradle
```
compile 'com.superkung:AndroidUtils:1.0.0'
```

###In Applcation
```Java
AndroidUtils.init(new AndroidUtils.getContextListener() {
            @Override
            public Context getContext() {
                return app;
            }
        });

        AndroidUtils.setTAG("log");
```
###In Java
```Java
AndroidUtils.log.d("log")
```

