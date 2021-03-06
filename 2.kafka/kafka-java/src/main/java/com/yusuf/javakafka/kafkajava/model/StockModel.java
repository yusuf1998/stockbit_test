package com.yusuf.javakafka.kafkajava.model;

 public class StockModel {
    private String time; 
    private String stockName; 
    private Integer lowPoint; 
    private Integer highPoint; 

    public StockModel(String time, String stockName,Integer lowPoint,Integer highPoint) 
    { 
        super();
        this.time = time; 
        this.stockName = stockName; 
        this.lowPoint = lowPoint; 
        this.highPoint = highPoint; 
    } 

    public String getTime() {
        return time;
    }
    public void setTime(String time) {
        this.time = time;
    }

    public String getStockName() {
        return stockName;
    }
    public void setStockName(String stockName) {
        this.stockName = stockName;
    }
    public Integer getLowPoint() {
        return lowPoint;
    }
    public void setLowPoint(Integer lowPoint) {
        this.lowPoint = lowPoint;
    }
    public Integer getHighPoint() {
        return highPoint;
    }
    public void setHighPoint(Integer highPoint) {
        this.highPoint = highPoint;
    }
  
    public String toString() {
        return "[time=" + time + ", stockName=" + stockName + ", lowPoint=" + lowPoint + ", highPoint=" + highPoint + "]";
    }
}
