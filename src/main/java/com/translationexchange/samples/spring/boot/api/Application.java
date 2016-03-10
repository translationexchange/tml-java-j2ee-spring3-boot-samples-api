package com.translationexchange.samples.spring.boot.api;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;

import com.translationexchange.core.Tml;
import com.translationexchange.core.Utils;

@SpringBootApplication
public class Application extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(Application.class);
	}

    public static void main(String[] args) throws Exception {
    	Tml.getConfig().setApplication(Utils.buildMap(
			"key", "df43596aab7bf9a9981289d2ded0732ad2bd358e2f79af65239b2280d52f0566",	
			"token", "b273e9c092c3f6829f0570f8da342c8ee6f9b1cf2e5ceeb3f634349a0e7f728f",	
			"host", "http://localhost:3000",
			"cdn_host", "https://trex-snapshots-dev.s3-us-west-1.amazonaws.com"
		));

		Tml.getConfig().setCache(Utils.buildMap(
			"enabled", 				true,	
	    	"class", 				"com.translationexchange.cache.Memcached",
	        "host", 				"localhost:11211",
	        "namespace", 			"df43596aab7bf9a9981289d2ded0732ad2bd358e2f79af65239b2280d52f0566",
	        "version_check_interval", 10
		));	

		// TODO: only if you pass this as a parameter and only if you are in the application debug/development mode
		// all other modes must use pre-existing cache/releases
    	Tml.getConfig().enableKeyRegistrationMode();
		
        SpringApplication.run(Application.class, args);
    }

}