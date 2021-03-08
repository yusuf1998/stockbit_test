package com.yusuf.javakafka.kafkajava.consumer;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yusuf.javakafka.kafkajava.model.StockModel;

@Component
public class MyTopicConsumer {

    private final List<String> messages = new ArrayList<>();
    private static Map<String,StockModel> stockList = new HashMap<>();
    ArrayList<String> listStockGroupByMinute = new ArrayList<String>();

    @KafkaListener(topics = "myTopic", groupId = "kafka-sandbox")
    public void listen(String message) throws ParseException {
        synchronized (messages) {
            readStockData(message);
            messages.add(message);
        }
    }
    public List<String> getPureMessages() {
        return messages;
    }
    public List<String> getConvertedMessages() {
        return listStockGroupByMinute;
    }   
    public void readStockData(String lineMessage) throws ParseException{
        String dtTimeData  = getDate(lineMessage,"|",12);
        String stockData   = getStock(lineMessage);
        Integer pointStock = getPointStock(lineMessage,";");

        String hourMinutTime = cutSecondInTime(dtTimeData);
        StockModel objStock  = new StockModel(hourMinutTime,stockData,pointStock,pointStock);                 
        
        String wrpDateStock  = hourMinutTime+"_"+stockData;   
         if(stockList.get(wrpDateStock) == null){
            stockList.put(wrpDateStock,objStock);

            Integer stockLowUpd  = objStock.getLowPoint();    
            Integer stockHighUpd = objStock.getHighPoint();
            String stockTmUpd    = objStock.getTime();
            String stockNmUpd    = objStock.getStockName();
            String appndWord     = appendWord( stockTmUpd, stockNmUpd, stockLowUpd, stockHighUpd );

            listStockGroupByMinute.add(appndWord);
         }else{
             setStockDataGroupByMinute(wrpDateStock,objStock);
         }
      }  
    public String getDate (String word,String symbol,int pos){
        String str  = word;
        String kept = str.substring( pos, str.indexOf("|"));  
        return kept;
    }
    public String cutSecondInTime(String strTime){
        String subStrTime = strTime.substring(0,5)+":00";
        return subStrTime;
    }
    public void setStockDataGroupByMinute(String cdStock,StockModel dataStock){
            for (Map.Entry<String, StockModel> entry : stockList.entrySet()) {
                StockModel objStock = new StockModel(entry.getValue().getTime(),entry.getValue().getStockName(),entry.getValue().getLowPoint(),entry.getValue().getHighPoint());
                
                String keyStock = objStock.getTime()+"_"+objStock.getStockName();
                if(StringUtils.equals(keyStock,cdStock)){
                    String  stockTm   = entry.getValue().getTime();
                    String  stockNm   = entry.getValue().getStockName();
                    Integer stockLow  = entry.getValue().getLowPoint();
                    Integer stockHigh = entry.getValue().getHighPoint();    
                    String appndWord = appendWord( stockTm, stockNm, stockLow, stockHigh );
    
                    if(listStockGroupByMinute.indexOf(appndWord) > -1 ){
                        Integer idxList = listStockGroupByMinute.indexOf(appndWord);
                        if(dataStock.getLowPoint() < stockLow){
                            objStock.setLowPoint(dataStock.getLowPoint());
                        }
                        if(dataStock.getHighPoint() > stockHigh){
                            objStock.setHighPoint(dataStock.getHighPoint());
                        }
                        String  stockTmUpd   = objStock.getTime();    
                        String  stockNmUpd   = objStock.getStockName();
                        Integer stockLowUpd  = objStock.getLowPoint();    
                        Integer stockHighUpd = objStock.getHighPoint();        
                        String appndWordUpd = appendWord ( stockTmUpd, stockNmUpd, stockLowUpd, stockHighUpd );    

                        listStockGroupByMinute.set(idxList, appndWordUpd);    
                    }else{
                        listStockGroupByMinute.add(appendWord(stockTm, stockNm, stockLow, stockHigh));
                    }
                }                      
            }        
    }
    public String appendWord ( String stockTm, String stockNm, Integer stockLow, Integer stockHigh ){
        String appndWord = stockTm+"|"+stockNm+"|high:"+stockHigh+"|low:"+stockLow;
        return appndWord;
    }
    public void convertToTextFile(){
        try {
            FileWriter myWriter = new FileWriter("dataStockTest3.txt");
            for(String str: listStockGroupByMinute) {
                myWriter.write(str + System.lineSeparator());
              }
                myWriter.close();            
          } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
          }
    }
    static Integer getPointStock(String value, String a) {
        int posA = value.lastIndexOf(a);
        if (posA == -1) {
            return 0;
        }
        int adjustedPosA = posA + a.length();
        if (adjustedPosA >= value.length()) {
            return 0;
        }
        if(NumberUtils.isParsable((value.substring(adjustedPosA)))){            
            return Integer.parseInt(value.substring(adjustedPosA));
        }else{
            return 0;
        }
    }
    public String getStock (String word){
        String str = word;
        String kept = StringUtils.substringBetween(str, "|", "|");
        
        return kept;
    }    
}
