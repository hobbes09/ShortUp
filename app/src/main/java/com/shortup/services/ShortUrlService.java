package com.shortup.services;

import android.net.Uri;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.shortup.activities.HomeScreen;
import com.shortup.managers.CacheManager;
import com.shortup.models.entities.MiniUrl;
import com.shortup.models.pojos.ResponsePojo;
import com.shortup.network.HttpGetClient;
import com.shortup.utils.GlobalConstant;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

/**
 * Created by sourin on 18/11/15.
 */
public class ShortUrlService {

    public static int responseCode = 0;

    public static MiniUrl getShortUrl(String longUrl){

        ArrayList<MiniUrl> miniUrlArrayList = getMiniUrlArrayListFromCache();

        if(miniUrlArrayList != null){
            for(int index = 0; index < miniUrlArrayList.size(); index++){
                MiniUrl miniUrlObj = miniUrlArrayList.get(index);
                if(miniUrlObj.getLongUrl().equalsIgnoreCase(longUrl)){
                    return miniUrlObj;
                }
            }
            // Not found in existing list. So we get it from Bitly and add it in cache.
            MiniUrl miniUrlObj = getShortUrlFromBitly(longUrl);
            if(miniUrlObj != null){
                addMiniUrlInCache(miniUrlObj);
            }
            return miniUrlObj;
        }

        MiniUrl miniUrlObj = getShortUrlFromBitly(longUrl);
        if(miniUrlObj != null){
            addMiniUrlInCache(miniUrlObj);
        }
        return miniUrlObj;
    }

    public static MiniUrl getShortUrlFromBitly(String longUrl){

        HttpGetClient client = new HttpGetClient();
        try{

            Uri.Builder builder = new Uri.Builder();
            builder.scheme("https")
                    .authority(GlobalConstant.base_url)
                    .appendPath("v3")
                    .appendPath("shorten")
                    .appendQueryParameter("access_token", GlobalConstant.access_token)
                    .appendQueryParameter("longUrl", longUrl);

            String url = builder.build().toString();

            client.setUrl(url);
            client.sendGetRequest();
            Log.v("repo", url.toString());
            Log.v("repo", client.getResponse().toString());

            if(client.getResponseCode() == 200){
                responseCode = 200;
                String mJsonResp = client.getResponse();
                JsonReader reader = new JsonReader(new StringReader(mJsonResp));
                reader.setLenient(true);
                Gson gson = new Gson();
                ResponsePojo responsePojo = gson.fromJson(reader, ResponsePojo.class);
                MiniUrl miniUrl = new MiniUrl();
                miniUrl.setLongUrl(longUrl);
                miniUrl.setShortUrl(responsePojo.getData().getUrl());
                return miniUrl;

            }else{
                responseCode = client.getResponseCode();
            }


        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
        return null;
    }

    public static ArrayList<MiniUrl> getMiniUrlArrayListFromCache(){
        try{
                // Retrieve the list from internal storage
                ArrayList<MiniUrl> miniUrlArrayList = (ArrayList<MiniUrl>) CacheManager.readObject
                        (HomeScreen.mApplicationContext, GlobalConstant.CACHE_KEY);
                return miniUrlArrayList;
            }catch (IOException e){
                e.printStackTrace();
            }catch (ClassNotFoundException e){
                e.printStackTrace();
            }
        return null;
    }

    public static boolean addMiniUrlInCache(MiniUrl miniUrl){

        if(miniUrl == null || miniUrl.getLongUrl() == null || miniUrl.getShortUrl() == null){
            return false;
        }

        ArrayList<MiniUrl> miniUrlArrayList = getMiniUrlArrayListFromCache();

        if(miniUrlArrayList != null && miniUrlArrayList.size() > 0){
            for(int index = 0; index < miniUrlArrayList.size(); index++){
                MiniUrl miniUrlObj = miniUrlArrayList.get(index);
                if(miniUrlObj.getLongUrl().equalsIgnoreCase(miniUrl.getLongUrl())){
                    return true;
                }
            }
            // Not found in existing list. So we add it.
            miniUrlArrayList.add(miniUrl);
            try {
                CacheManager.writeObject(HomeScreen.mApplicationContext, GlobalConstant.CACHE_KEY, miniUrlArrayList);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        miniUrlArrayList = new ArrayList<MiniUrl>();
        miniUrlArrayList.add(miniUrl);
        try {
            CacheManager.writeObject(HomeScreen.mApplicationContext, GlobalConstant.CACHE_KEY, miniUrlArrayList);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


}
