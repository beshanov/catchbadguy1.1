package com.beshanov.catchbadguy.service.factory;

import com.beshanov.catchbadguy.CommandLineRunner;
import com.vk.api.sdk.client.VkApiClient;
import java.util.TreeSet;
import java.util.concurrent.TimeUnit;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VkClientFactory {

  private final VkApiClient client;

  private final TreeSet<Long> times = new TreeSet<>(Long::compareTo);

  @Autowired
  public VkClientFactory(VkApiClient client) {
    this.client = client;
  }

  public VkApiClient getClient() {
    long current = System.currentTimeMillis();
    if (!times.isEmpty()) {
      Long first = times.first();
      while (current - first < 1000) {
        try {
          TimeUnit.MILLISECONDS.sleep(500);
        } catch (InterruptedException e) {
          //
        }
        current = System.currentTimeMillis();
      }
      if (times.size() >= 3) {
        times.remove(first);
      }
    }
    times.add(current);

     return client;

  }

}
