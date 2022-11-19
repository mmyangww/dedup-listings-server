package com.clark.deduplistings.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.clark.deduplistings.dao.TableADao;
import com.clark.deduplistings.dao.TableBDao;
import com.clark.deduplistings.domain.*;
import com.clark.deduplistings.service.TableAService;
import com.clark.deduplistings.service.TableBService;
import com.clark.deduplistings.vo.TableVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RequestUrlServiceImpl {

    private static final String PLACE_KEY_URL = "https://api.placekey.io/v1/placekey";
    private static final String API_KEY = "";
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private TableAService tableAService;

    @Autowired
    private TableBService tableBService;

    @Autowired
    private TableBDao tableBDao;

    public List<TableVO> doDedup(){
        List<TableVO> tableVOA = tableAService.findAll();
        List<RequestBody> requestBodiesA = convertToRequestBody(tableVOA);
        Map<String, String> placeKeyA = getPlaceKey(requestBodiesA);

        List<TableVO> tableVOB = tableBService.findAll();
        List<RequestBody> requestBodiesB = convertToRequestBody(tableVOB);
        Map<String, String> placeKeyB = getPlaceKey(requestBodiesB);

        List<String> remove = new LinkedList<>();
        placeKeyB.forEach((k, v) -> {
            if(placeKeyA.containsKey(k) || placeKeyA.containsValue(v)){
                remove.add(k);
            }
        });

        for(int i = 0; i < tableVOB.size(); i++){
            for(String r : remove){
                if(tableVOB.get(i).getAddress().equals(r)){
                    tableVOB.remove(i);
                }
            }
        }

        System.out.println(remove);
        System.out.println(tableVOB);
        return tableVOB;
    }

    public Map<String, String> getPlaceKey(List<RequestBody> requestBodies) {
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        headers.add("apikey", API_KEY);

        Map<String, String> respondPlaceKeys = new HashMap<>();

        for (RequestBody item: requestBodies) {
            JSONObject jsonObject = (JSONObject) JSONObject.toJSON(item);
            HttpEntity httpEntity = new HttpEntity(jsonObject, headers);
            ResponseEntity<String> response = restTemplate.postForEntity(PLACE_KEY_URL, httpEntity, String.class);
            JSONObject jsonObject1 = JSONObject.parseObject(response.getBody());
            RespondBody respondBody = JSONObject.toJavaObject(jsonObject1, RespondBody.class);
            if(respondBody.getPlacekey() == null){
                respondPlaceKeys.put(item.getQuery().getStreet_address(),"");
            }else{
                respondPlaceKeys.put(item.getQuery().getStreet_address(),respondBody.getPlacekey());
            }
        }
        return respondPlaceKeys;
    }

    public List<RequestBody> convertToRequestBody(List<TableVO> tableVOS){

        List<RequestBody> result = tableVOS.stream()
                .map(t -> {
                    RequestAddress requestAddress = new RequestAddress();
                    requestAddress.setStreet_address(t.getAddress());
                    requestAddress.setCity(t.getCity());
                    requestAddress.setRegion(t.getState());
                    return new RequestBody(requestAddress);
                })
                .collect(Collectors.toList());
        return result;
    }

}
