package com.quoctoan.hairservice.command.event;


import com.quoctoan.hairservice.command.data.News;
import com.quoctoan.hairservice.command.data.NewsRepository;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NewsEventHandler {
    @Autowired
    private NewsRepository newsRepository;

    @EventHandler
    public void on(NewsCreateEvent event) {
        News news = new News();
        BeanUtils.copyProperties(event, news);
        newsRepository.save(news);
    }

}
