package com.woowacourse.woowaquiz.room.domain;

import com.woowacourse.woowaquiz.generic.BaseEntity;
import com.woowacourse.woowaquiz.quiz.domain.Quiz;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AttributeOverride(name = "id", column = @Column(name = "ROOM_ID"))
public class Room extends BaseEntity {

    @Column(name = "NAME")
    private String name;
    @Column(name = "AUTHOR")
    private String author;

    //TODO queryDSL 작성을 통해 LAZY 테스트 가능하도록 만들자
    @OneToMany(fetch = FetchType.LAZY)
    private List<Quiz> quizzes;

    @Builder
    public Room(LocalDateTime createdTime, String name, String author, List<Quiz> quizzes) {
        super(createdTime);
        this.name = name;
        this.author = author;
        this.quizzes = quizzes;
    }
}
