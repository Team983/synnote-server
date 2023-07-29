CREATE TABLE note
(
    `id`            BIGINT         NOT NULL    AUTO_INCREMENT,
    `user_id`       VARCHAR(36)    NOT NULL,
    `domain`        VARCHAR(30)    NOT NULL,
    `title`         VARCHAR(50)    NOT NULL,
    `status`        VARCHAR(10)    NOT NULL    COMMENT 'RECORDING/PROCESSING/DONE',
    `upload_type`   VARCHAR(10)    NOT NULL    COMMENT 'RECORDING/FILE',
    `deleted_flag`  TINYINT        NOT NULL    DEFAULT 0,
    `created_date`  TIMESTAMP      NOT NULL,
    `updated_date`  TIMESTAMP      NOT NULL,
    PRIMARY KEY (id)
);
