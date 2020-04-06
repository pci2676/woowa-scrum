package com.woowacourse.woowascrum.quiz.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.woowacourse.woowascrum.quiz.domain.model.Quiz;
import com.woowacourse.woowascrum.quiz.domain.repository.QuizRepository;
import com.woowacourse.woowascrum.quiz.service.dto.QuizSaveRequestDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest
@ExtendWith(SpringExtension.class)
class QuizControllerTest {
    private static final String QUIZ_BAE_URL = "/api/v1/quiz";
    private static final Gson gson = new Gson().newBuilder().setPrettyPrinting().create();

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))
                .build();
    }

    @AfterEach
    void tearDown() {
        quizRepository.deleteAll();
    }

    @DisplayName("퀴즈 등록 성공")
    @Test
    void saveQuiz() throws Exception {
        //given
        QuizSaveRequestDto quizSaveRequestDto = QuizSaveRequestDto.builder()
                .quizType("TYPING")
                .title("나오는 문장을 따라 적으세요.")
                .question("어찌, 내가 왕이 될 상인가?")
                .solution("어찌, 내가 왕이 될 상인가?")
                .author("bebop")
                .build();

        //when
        MvcResult mvcResult = mockMvc.perform(post(QUIZ_BAE_URL)
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(quizSaveRequestDto)))
                .andExpect(status().isCreated())
                .andDo(print())
                .andReturn();

        String contentAsString = mvcResult.getResponse().getContentAsString();
        Long savedQuizId = objectMapper.readValue(contentAsString, Long.class);

        List<Quiz> all = quizRepository.findAll();
        Quiz savedQuiz = quizRepository.findById(savedQuizId)
                .orElseThrow(NullPointerException::new);

        //then
        assertThat(all).hasSize(1);
        assertThat(all.get(0).getId()).isEqualTo(savedQuiz.getId());
    }
}