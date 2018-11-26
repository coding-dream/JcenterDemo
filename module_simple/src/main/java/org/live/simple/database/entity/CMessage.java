package org.live.simple.database.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Transient;

/**
 * GreenDao是根据Bean对象自动创建Dao对象
*/
@Entity
public class CMessage {
    @Id(autoincrement = true)
    private Long id;
    private String content;// 消息内容
    private Long time;// 消息发送时间

    /**
     * @Transient 表示此字段不映射到数据库
     */
    @Transient
    public boolean isShowHeader;
    @Transient
    public boolean isShowFooter;
    @Generated(hash = 1651940211)
    public CMessage(Long id, String content, Long time) {
        this.id = id;
        this.content = content;
        this.time = time;
    }
    @Generated(hash = 729952253)
    public CMessage() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getContent() {
        return this.content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public Long getTime() {
        return this.time;
    }
    public void setTime(Long time) {
        this.time = time;
    }
}