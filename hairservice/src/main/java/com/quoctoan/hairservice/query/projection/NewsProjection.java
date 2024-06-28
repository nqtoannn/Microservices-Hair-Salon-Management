package com.quoctoan.hairservice.query.projection;

import com.quoctoan.hairservice.command.data.News;
import com.quoctoan.hairservice.command.data.NewsRepository;
import com.quoctoan.hairservice.command.data.ServiceHair;
import com.quoctoan.hairservice.query.model.HairServiceResponseModel;
import com.quoctoan.hairservice.query.model.NewsResponseModel;
import com.quoctoan.hairservice.query.queries.GetAllNews;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class NewsProjection {
    @Autowired
    private NewsRepository newsRepository;

    @QueryHandler
    public List<NewsResponseModel> handle(GetAllNews getAllNews) {
        List<News> newsList = newsRepository.findAll();
        List<NewsResponseModel> newsResponseModelList = new ArrayList<>();
        newsList.forEach(a -> {
            NewsResponseModel newsResponseModel = new NewsResponseModel();
            BeanUtils.copyProperties(a, newsResponseModel);
            newsResponseModelList.add(newsResponseModel);
        });
        return newsResponseModelList;
    }
}
