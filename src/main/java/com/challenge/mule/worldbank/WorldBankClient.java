package com.challenge.mule.worldbank;

import com.challenge.mule.config.WorldBankProperties;
import com.challenge.mule.exception.RestTemplateException;
import com.challenge.mule.exception.RestTemplateExceptionHandler;
import com.challenge.mule.model.DataControl;
import com.challenge.mule.repository.DataControlRepository;
import com.challenge.mule.util.ZipHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class WorldBankClient {
    private final static Logger logger = LoggerFactory.getLogger(WorldBankClient.class);

    @Autowired
    private DataControlRepository dataControlRepository;

    RestTemplate restTemplate;
    WorldBankProperties worldBankProperties;

    public WorldBankClient(RestTemplateBuilder restTemplate, WorldBankProperties worldBankProperties) {
        this.restTemplate = restTemplate
                .errorHandler(new RestTemplateExceptionHandler())
                .build();
        this.worldBankProperties = worldBankProperties;
    }

    public void getWorldBankIndicatorsCSV() throws IOException {
        logger.info("getting data from world bank");
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        String pathFolder = this.worldBankProperties.getPathSaveData() + new Date().getTime();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

        //TODO asign indicators vaariables because i hope to make a change to read indicators from queryparams
        String indicators = this.worldBankProperties.getIndicators();
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromHttpUrl(this.worldBankProperties.getHost()+indicators)
                .queryParam("date", this.worldBankProperties.getYears())
                .queryParam("source", this.worldBankProperties.getSourceParam())
                .queryParam("downloadformat", this.worldBankProperties.getDownloadFormat());

        HttpEntity<?> entity = new HttpEntity<>(headers);

        ResponseEntity<byte[]> response = this.restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.GET,
                entity,
                byte[].class);

        if (response.getHeaders().getContentDisposition().getFilename() == null) {
            StringBuilder query = new StringBuilder();
            query
                .append(this.worldBankProperties.getHost())
                .append(this.worldBankProperties.getIndicators())
                .append("?date="+this.worldBankProperties.getYears())
                .append("&source="+this.worldBankProperties.getSourceParam())
                .append("&downloadformat="+this.worldBankProperties.getDownloadFormat());
            throw new RestTemplateException("I don't got a zip file from world bank " + query.toString(), HttpStatus.BAD_REQUEST);
        }

        try {
            ZipHelper.unzip(response.getBody(), pathFolder);
        } catch (IOException e) {
            logger.error("Error to unzip file from world bank", e);
            throw e;
        }
        this.dataControlRepository.save(new DataControl(pathFolder));
    }

}
