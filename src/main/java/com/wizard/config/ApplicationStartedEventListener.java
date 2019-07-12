package com.wizard.config;

import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.wizard.util.SshUtil;

@Component
public class ApplicationStartedEventListener implements ApplicationListener<ApplicationStartedEvent> {
	
    @Override
    public void onApplicationEvent(ApplicationStartedEvent ev) {
        SshUtil.getInstance();
    }

}