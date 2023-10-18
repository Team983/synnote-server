CREATE TABLE memo
(
    `id`       BIGINT         NOT NULL    AUTO_INCREMENT,
    `note_id`  BIGINT         NOT NULL,
    `text`     TEXT           NOT NULL,
    `start`    BIGINT         NOT NULL    COMMENT '작성 시각, 밀리초 단위(ms)',
    PRIMARY KEY (id)
);

ALTER TABLE memo
    ADD CONSTRAINT FK_memo_note_id_note_id FOREIGN KEY (note_id)
        REFERENCES note (id) ON DELETE RESTRICT ON UPDATE RESTRICT;