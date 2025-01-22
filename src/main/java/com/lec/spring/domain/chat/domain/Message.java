package com.lec.spring.domain.chat.domain;

import com.lec.spring.domain.base.domain.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "Message")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long messageId;

    @ManyToOne
    @JoinColumn(name = "User_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "Chat_id", nullable = false)
    private Chat chat;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private boolean isChecked;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
}

