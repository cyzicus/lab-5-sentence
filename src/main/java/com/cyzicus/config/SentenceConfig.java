package com.cyzicus.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SentenceConfig {

    @Value("${subjectService}")
    private String subjectService;

    @Value("${verbService}")
    private String verbService;

    @Value("${articleService}")
    private String articleService;

    @Value("${adjectiveService}")
    private String adjectiveService;

    @Value("${nounService}")
    private String nounService;

    public String getSubjectService() {
        return subjectService;
    }

    public String getVerbService() {
        return verbService;
    }

    public String getArticleService() {
        return articleService;
    }

    public String getAdjectiveService() {
        return adjectiveService;
    }

    public String getNounService() {
        return nounService;
    }
}
