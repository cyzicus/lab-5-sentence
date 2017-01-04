package com.cyzicus.service;

import com.cyzicus.client.WordClient;
import com.cyzicus.config.SentenceConfig;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.*;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SentenceServiceTest {

    @Mock
    WordClient wordClient;

    @Mock
    SentenceConfig config;

    SentenceService sentenceService;

    @Before
    public void setUp() throws Exception {
        sentenceService = new SentenceService(wordClient, config);
        when(config.getAdjectiveService()).thenReturn("adjective");
        when(config.getArticleService()).thenReturn("article");
        when(config.getNounService()).thenReturn("noun");
        when(config.getSubjectService()).thenReturn("subject");
        when(config.getVerbService()).thenReturn("verb");
    }

    @Test
    public void getSentence_returnsSentence() throws Exception {
        when(wordClient.getWord(eq("adjective"))).thenReturn("red");
        when(wordClient.getWord(eq("article"))).thenReturn("the");
        when(wordClient.getWord(eq("noun"))).thenReturn("pontoon");
        when(wordClient.getWord(eq("subject"))).thenReturn("I");
        when(wordClient.getWord(eq("verb"))).thenReturn("paddled");
        String sentence = sentenceService.getSentence();
        Assert.assertEquals("I paddled the red pontoon", sentence);
    }

    @Test
    public void getSentence_handlesArticleCorrectly_a() throws Exception {
        when(wordClient.getWord(eq("adjective"))).thenReturn("beautiful");
        when(wordClient.getWord(eq("article"))).thenReturn("a");
        when(wordClient.getWord(eq("noun"))).thenReturn("cat");
        when(wordClient.getWord(eq("subject"))).thenReturn("I");
        when(wordClient.getWord(eq("verb"))).thenReturn("saw");
        String sentence = sentenceService.getSentence();
        Assert.assertEquals("I saw a beautiful cat", sentence);
    }

    @Test
    public void getSentence_handlesArticleCorrectly_an() throws Exception {
        when(wordClient.getWord(eq("adjective"))).thenReturn("ugly");
        when(wordClient.getWord(eq("article"))).thenReturn("a");
        when(wordClient.getWord(eq("noun"))).thenReturn("cat");
        when(wordClient.getWord(eq("subject"))).thenReturn("I");
        when(wordClient.getWord(eq("verb"))).thenReturn("saw");
        String sentence = sentenceService.getSentence();
        Assert.assertEquals("I saw an ugly cat", sentence);
    }

}