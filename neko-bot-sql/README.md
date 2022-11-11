# 数据持久化模块

实现与数据库的交互

## 数据表

### 聊天记录

```mysql
CREATE TABLE IF NOT EXISTS `history`
(
    `sender`   INTEGER(64)  NOT NULL COMMENT '发送者id',
    `subject`  INTEGER(64)  NOT NULL COMMENT '会话id',
    `is_group` BOOLEAN      NOT NULL COMMENT '会话是否是群聊',
    `ids`      VARCHAR(512) NOT NULL COMMENT '消息链ids的字符串形式',
    `stamp`    INTEGER(64)  NOT NULL COMMENT '发送时间',
    `chain`    TEXT         NOT NULL COMMENT '消息链序列化后的json',
    PRIMARY KEY (`sender`, `subject`, `ids`)
) ENGINE = InnoDB
  DEFAULT CHARSET = UTF8;
```

### 指令使用记录

### 签到数据

### 分词数据