package com.woowacourse.woowascrum.quiz.web;

import com.woowacourse.woowascrum.quiz.service.QuizService;
import com.woowacourse.woowascrum.quiz.service.dto.QuizSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/quiz")
public class QuizController {

    private final QuizService quizService;

    @PostMapping
    public ResponseEntity<Long> saveQuiz(@RequestBody QuizSaveRequestDto quizSaveRequestDto) {
        return new ResponseEntity<>(quizService.saveQuiz(quizSaveRequestDto), HttpStatus.CREATED);
    }
}