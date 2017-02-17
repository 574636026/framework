package com.injedu.springadmin;

import java.util.concurrent.TimeUnit;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.Scheduled;

import de.codecentric.boot.admin.config.EnableAdminServer;
import de.codecentric.boot.admin.notify.LoggingNotifier;
import de.codecentric.boot.admin.notify.Notifier;
import de.codecentric.boot.admin.notify.RemindingNotifier;
import de.codecentric.boot.admin.notify.filter.FilteringNotifier;

/**
 * 
 *
 * @author joy.zhou
 * @date 2016年8月25日
 * @version 1.0
 *
 */
@EnableAdminServer
@SpringBootApplication
public class SpringAdminApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(SpringAdminApplication.class, args);
	}

	@Bean
	@Primary
	public RemindingNotifier remindingNotifier() {
		RemindingNotifier notifier = new RemindingNotifier(filteringNotifier(loggerNotifier()));
		notifier.setReminderPeriod(TimeUnit.SECONDS.toMillis(10));
		return notifier;
	}

	@Scheduled(fixedRate = 1_000L)
	public void remind() {
		remindingNotifier().sendReminders();
	}

	@Bean
	public FilteringNotifier filteringNotifier(Notifier delegate) {
		return new FilteringNotifier(delegate);
	}

	@Bean
	public LoggingNotifier loggerNotifier() {
		return new LoggingNotifier();
	}

	@Override
	public void run(String... args) throws Exception {

		System.out.println("加载成功...");

	}
}
