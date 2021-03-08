package com.yusuf.javakafka.kafkajava.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import com.yusuf.javakafka.kafkajava.consumer.MyTopicConsumer;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KafkaController {

    private MyTopicConsumer myTopicConsumer;
    private KafkaTemplate<String, String> template;

    public KafkaController(KafkaTemplate<String, String> template, MyTopicConsumer myTopicConsumer) {

        this.template = template;
        this.myTopicConsumer = myTopicConsumer;
    }
    @GetMapping("/kafka/produce")
    public List<String> produce() {
        String fileName = "templates/test3.txt";
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(fileName).getFile());

        List<String> messageFromFile = new ArrayList<>();
        try {
            String  messageLine;        
            BufferedReader br = new BufferedReader(new FileReader(file));
            while ((messageLine = br.readLine()) != null) {
                template.send("myTopic", messageLine);
                messageFromFile.add(messageLine);
             }   
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
        return messageFromFile;
     }   
    @GetMapping("/kafka/messages")
    public List<String> getMessages() {
        List<String> cnvMessagesList = myTopicConsumer.getConvertedMessages();

        if(cnvMessagesList.size() > 0){
            myTopicConsumer.convertToTextFile();
            cnvMessagesList.add("File dataStock3.txt generated!");
        }else{
            cnvMessagesList.add("Data stock is empty");
        }
        
        return cnvMessagesList;
    }
}
