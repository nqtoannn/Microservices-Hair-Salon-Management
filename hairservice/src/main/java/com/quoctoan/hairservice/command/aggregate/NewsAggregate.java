package com.quoctoan.hairservice.command.aggregate;

import com.quoctoan.hairservice.command.command.CreateNewsCommand;
import com.quoctoan.hairservice.command.event.NewsCreateEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

@Aggregate
public class NewsAggregate {
    @AggregateIdentifier
    private String id;

    private String title;
    private String imageUrl;
    private String description;

    public NewsAggregate() {

    }

    @CommandHandler
    public NewsAggregate(CreateNewsCommand createNewsCommand) {
        NewsCreateEvent newsCreateEvent = new NewsCreateEvent();
        BeanUtils.copyProperties(createNewsCommand, newsCreateEvent);
        AggregateLifecycle.apply(newsCreateEvent);
    }

    @EventSourcingHandler
    public void on(NewsCreateEvent event) {
        this.id = event.getId();
        this.description = event.getDescription();
        this.imageUrl = event.getImageUrl();
        this.title = event.getTitle();
    }


}
