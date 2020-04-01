package com.woowacourse.woowaquiz.answer.domain.model;

import com.woowacourse.woowaquiz.generic.BaseEntity;
import com.woowacourse.woowaquiz.quiz.domain.model.Quiz;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AttributeOverride(name = "id", column = @Column(name = "ANSWER_ID"))
public class Answer extends BaseEntity {

    @Column(name = "ANSWER")
    private String answer;

    //TODO github 로그인으로 해결하자
    @Column(name = "AUTHOR")
    private String author;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "QUIZ_ID")
    private Quiz quiz;

    @Builder
    public Answer(String answer, String author, Quiz quiz, LocalDateTime createdTime) {
        super(createdTime);
        this.answer = answer;
        this.author = author;
        this.quiz = quiz;
    }

    public boolean isCorrect() {
        return this.quiz.isCorrectAnswer(this.answer);
    }
}