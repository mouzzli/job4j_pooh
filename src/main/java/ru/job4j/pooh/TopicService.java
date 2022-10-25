package ru.job4j.pooh;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class TopicService implements Service {

    private final ConcurrentHashMap<String, ConcurrentHashMap<String, ConcurrentLinkedQueue<String>>> topics = new ConcurrentHashMap<>();

    @Override
    public Resp process(Req req) {
        String text = "";
        String status = "200";
        if ("POST".equals(req.httpRequestType())) {
            if (topics.get(req.getSourceName()) != null) {
                for (ConcurrentLinkedQueue<String> value : topics.get(req.getSourceName()).values()) {
                    value.add(req.getParam());
                }
            } else {
                status = "204";
            }
        } else if ("GET".equals(req.httpRequestType())) {
            if (topics.get(req.getSourceName()) == null || topics.get(req.getSourceName()).get(req.getParam()) == null) {
                topics.putIfAbsent(req.getSourceName(), new ConcurrentHashMap<>());
                topics.get(req.getSourceName()).putIfAbsent(req.getParam(), new ConcurrentLinkedQueue<>());
            } else {
                text = topics.get(req.getSourceName()).get(req.getParam()).poll();
            }
        }
        return new Resp(text, status);
    }
}