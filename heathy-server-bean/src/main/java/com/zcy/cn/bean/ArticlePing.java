package com.zcy.cn.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "h_article_ping")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class ArticlePing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long aPId;

    @Column
    private Long iAId;

    @Column
    private Long uId;

    @Column
    private Date pingDate;

    @Column(columnDefinition = "blob")
    private String pingText;

    @Column(length = 50)
    private String pingDateStr;

}
