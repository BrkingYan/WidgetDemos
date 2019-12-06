package com.example.yy.retrofit.translation_GET;

/*
*
{
      "status":1,
      "content": {
            "from": "en-EU",
            "to": "zh-CN",
            "vendor": "baidu",
            "out": "你好世界<br/>",
            "err_no": 0
      }
}
*
* */

public class Translation {
    private int status;

    private content content;

    private static class content {
        private String from;
        private String to;
        private String vendor;
        private String out;
        private int errNo;
    }

    //定义 输出返回数据 的方法
    public void show() {
        /*System.out.println(status);
        System.out.println(content.from);
        System.out.println(content.to);
        System.out.println(content.vendor);
        System.out.println(content.out);
        System.out.println(content.errNo);*/
    }

}
