/*
package com.gl.ceir.config.feign;

import com.gl.ceir.config.model.app.Dropdown;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Service
@FeignClient(url="${gsmaFeignClientPath}",value = "profileUrlsgsma")
public interface GsmaFeignClient {


    @RequestMapping(value="/gsma/brandName" ,method= RequestMethod.GET)
    public List<Dropdown> viewAllProductList();
}
*/
