package com.michael.ai_code_review.utils.openai;

import com.michael.ai_code_review.dto.request.aiRequest.CodeReviewRequestDto;
import com.michael.ai_code_review.dto.request.aiRequest.MessageDto;
import com.michael.ai_code_review.dto.request.openAiRequestDto.OpenAiMessageDto;
import com.michael.ai_code_review.dto.request.openAiRequestDto.OpenAiRequest;
import com.michael.ai_code_review.properties.OpenAiRequestProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@Slf4j
@RequiredArgsConstructor
class OpenAiRequestFactoryTest {

    private OpenAiRequestFactory openAiRequestFactory;
    private OpenAiRequestProperties openAiRequestProperties;

    @BeforeEach
    void setUp() {
        openAiRequestProperties = Mockito.mock(OpenAiRequestProperties.class);
        openAiRequestFactory = new OpenAiRequestFactory(openAiRequestProperties);
    }

    @Test
    void testCreateRequest() {
        // Arrange
        when(openAiRequestProperties.getRole()).thenReturn("system");
        when(openAiRequestProperties.getMessage()).thenReturn("System message");
        when(openAiRequestProperties.getUserMessage()).thenReturn("User message: ");
        when(openAiRequestProperties.getStore()).thenReturn(true);
        when(openAiRequestProperties.getModel()).thenReturn("gpt-3");

        CodeReviewRequestDto reviewRequestDto = new CodeReviewRequestDto();
        reviewRequestDto.setMessages((new ArrayList<>(List.of(
                new MessageDto("First user message"),
                new MessageDto("Second user message")
        ))));
        log.info("reviewRequestDto - {}", reviewRequestDto);

        // Act
        OpenAiRequest openAiRequest = openAiRequestFactory.createRequest(reviewRequestDto);
        log.info("openAiRequest - {}", openAiRequest);

        // Assert
        List<OpenAiMessageDto> expectedMessages = List.of(
                new OpenAiMessageDto("system", "System message"),
                new OpenAiMessageDto("user", "User message: First user message"),
                new OpenAiMessageDto("user", "User message: Second user message")
        );
        log.info("expectedMessages - {}", expectedMessages);

        assertEquals(expectedMessages, openAiRequest.getMessages());
        assertTrue(openAiRequest.isStore());
        assertEquals("gpt-3", openAiRequest.getModel());
    }
}