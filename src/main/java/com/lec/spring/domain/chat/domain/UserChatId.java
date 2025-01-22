package com.lec.spring.domain.chat.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.io.Serializable;

@Getter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class UserChatId implements Serializable {

    @Column(name = "userId")
    private Long userId;

    @Column(name = "chatId")
    private Long chatId;
}

