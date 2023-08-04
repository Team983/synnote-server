CREATE TABLE recording
(
    `id`                  BIGINT    NOT NULL    AUTO_INCREMENT,
    `s3_object_url`       TEXT      NOT NULL,
    `recording_duration`  BIGINT    NOT NULL    COMMENT '밀리초 단위(ms)',
    PRIMARY KEY (id)
);
