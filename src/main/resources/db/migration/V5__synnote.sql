CREATE TABLE script
(
    `id`        BIGINT         NOT NULL    AUTO_INCREMENT,
    `note_id`   BIGINT         NOT NULL,
    `asr_type`  VARCHAR(15)    NOT NULL    COMMENT 'REALTIME/FULL',
    `text`      TEXT           NOT NULL,
    `speaker`   VARCHAR(20)    NULL,
    `start`     BIGINT         NULL        COMMENT '시작 시각, 밀리초 단위(ms)',
    `end`       BIGINT         NULL        COMMENT '종료 시각. 밀리초 단위(ms)',
    PRIMARY KEY (id)
);

ALTER TABLE script
    ADD CONSTRAINT FK_script_note_id_note_id FOREIGN KEY (note_id)
        REFERENCES note (id) ON DELETE RESTRICT ON UPDATE RESTRICT;
