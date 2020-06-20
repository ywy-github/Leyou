package com.leyou.search.Listener;

import com.leyou.search.service.SearchService;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class GoodsListener {
    @Autowired
    private SearchService searchService;

    /**
     * 处理insert和update消息
     * @param id
     * @throws IOException
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "leyou.create.index.queue", durable = "true"),
            exchange = @Exchange(
                 value = "leyou.item.exchange",
                 ignoreDeclarationExceptions = "true",
                 type = ExchangeTypes.TOPIC
            ),
            key={"item.insert","item.update"}
    ))
    public void ListenCreate(Long id) throws IOException {
          if(id==null){
              return;
          }
          this.searchService.createIndex(id);
    }


    /**
     * 处理delete的消息
     * @param id
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "leyou.delete.index.queue",durable = "true"),
            exchange = @Exchange(
                 value = "leyou.item.exchange",
                 ignoreDeclarationExceptions = "true",
                 type = ExchangeTypes.TOPIC
            ),
            key={"item.delete"}
    ))
    public void ListenDelete(Long id){
          if(id==null){
              return;
          }
          this.searchService.deleteIndex(id);
    }

}
