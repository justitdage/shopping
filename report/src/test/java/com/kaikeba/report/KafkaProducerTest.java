    package com.kaikeba.report;

    import java.io.*;
    import java.net.HttpURLConnection;
    import java.net.URL;

    /**
     * 测试用例
     */
    public class KafkaProducerTest {
        public static void main(String[] args) {
            String message="测试用例";
            //post请求地址
            String address="http://localhost:8888/ReportApplication/receiveData";
             try{
                 //请求url
                 URL url = new URL(address);
                 //打开http连接
                 HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                 //设置post请求
                 conn.setRequestMethod("POST");
                 //设置url连接可以用于输出,获取字节流
                 conn.setDoOutput(true);
                 //允许用户进行上下文中对URL进行检查
                 conn.setAllowUserInteraction(true);
                 //请求超时时间
                 conn.setReadTimeout(6*1000);
                 //设置请求头信息 --用户代理
                 conn.setRequestProperty("User-agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.86 Safari/537.36");
                 conn.setRequestProperty("Content-Type","application/json");
                 //连接网络
                 conn.connect();

                 //使用io流发送消息
                 BufferedOutputStream out = new BufferedOutputStream(conn.getOutputStream());
                 out.write(message.getBytes());
                 out.flush();

                 //发送完毕之后，客户端看到服务端返回结果
                    //得到响应流
                 InputStream in = conn.getInputStream();
                   //将响应流转换成字符串
                 String returnLine = getStringFromInputStream(in);
                 System.out.println(returnLine);


             }catch (Exception e){
                 e.printStackTrace();
             }


        }


        /**
         * 通过字节输入流返回一个字符串信息
         */
        private static String getStringFromInputStream(InputStream is) throws Exception {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = is.read(buffer)) != -1) {
                baos.write(buffer, 0, len);
            }
            is.close();
            // 把流中的数据转换成字符串, 采用的编码是: utf-8
            String status = baos.toString();
            baos.close();
            return status;
        }

    }
