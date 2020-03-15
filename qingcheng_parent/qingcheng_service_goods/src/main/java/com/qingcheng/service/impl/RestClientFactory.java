package com.qingcheng.service.impl;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;

public class RestClientFactory {


    public static RestHighLevelClient getRestHighLevelClient(){
        //HttpHost http=new HttpHost(hostname,port,"http");
        //RestClientBuilder builder= RestClient.builder(http);//rest构建器
        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();

        credentialsProvider.setCredentials(AuthScope.ANY,
                new UsernamePasswordCredentials("elastic", "********"));

        RestClientBuilder builder = RestClient.builder(
                new HttpHost("**************.aliyuncs.com", 9200))
                .setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {

                    public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpClientBuilder) {
                        return httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
                    }
                });
        return new RestHighLevelClient(builder);//高级客户端对象 （连接）
    }

}
