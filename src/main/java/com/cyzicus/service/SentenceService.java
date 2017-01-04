package com.cyzicus.service;

import com.cyzicus.client.WordClient;
import com.cyzicus.config.SentenceConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SentenceService {

    private final WordClient wordClient;
    private final SentenceConfig config;

    @Autowired
    public SentenceService(WordClient wordClient, SentenceConfig config) {
        this.wordClient = wordClient;
        this.config = config;
    }

    public String getSentence() {

        String subject = wordClient.getWord(config.getSubjectService());
        String verb = wordClient.getWord(config.getVerbService());
        String article = wordClient.getWord(config.getArticleService());
        String adjective = wordClient.getWord(config.getAdjectiveService());
        String noun = wordClient.getWord(config.getNounService());

        return String.format("%s %s %s %s %s", subject, verb, correctArticle(article, adjective), adjective, noun);
    }

    private String correctArticle(String article, String adjective) {
        if (article.toLowerCase().equals("a")) {
            if(isVowel(adjective.charAt(0))) {
                return "an";
            }
        }
        return article;

    }

    private static boolean isVowel(char c) {
        return "AEIOUaeiou".indexOf(c) != -1;
    }

}
