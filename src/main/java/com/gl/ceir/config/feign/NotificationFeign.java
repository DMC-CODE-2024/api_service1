package com.gl.ceir.config.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import com.gl.ceir.config.model.app.GenricResponse;
import com.gl.ceir.config.model.app.NotificationModel;

@Service
@FeignClient(url = "${NotificationAPI}", value = "dsj" )
public interface NotificationFeign {

	@PostMapping(value="/addNotifications")
	public GenricResponse addNotifications(NotificationModel notificationModel) ;
	
}
