# AndroidUtils
##Android Utils set  安卓 工具类 集合

##HOW TO USE


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
