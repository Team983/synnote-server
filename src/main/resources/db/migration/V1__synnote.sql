CREATE TABLE agreement
(
    `id`                        BIGINT       NOT NULL    AUTO_INCREMENT,
    `privacy_policy`            TINYINT      NOT NULL    DEFAULT 0 COMMENT '개인정보 처리방침',
    `terms_and_cons`            TINYINT      NOT NULL    DEFAULT 0 COMMENT '서비스 이용약관',
    `service_improvement`       TINYINT      NOT NULL    DEFAULT 0 COMMENT '서비스 품질 향상을 위한 이용자 데이터 수집',
    `privacy_policy_date`       TIMESTAMP    NOT NULL,
    `terms_and_cons_date`       TIMESTAMP    NOT NULL,
    `service_improvement_date`  TIMESTAMP    NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE users
(
    `id`            VARCHAR(36)     NOT NULL,
    `name`          VARCHAR(320)    NOT NULL,
    `email`         VARCHAR(320)    NOT NULL,
    `agreement_id`  BIGINT          NOT NULL,
    `created_date`  TIMESTAMP       NOT NULL,
    `updated_date`  TIMESTAMP       NOT NULL,
    `removed_date`  TIMESTAMP       NOT NULL,
    PRIMARY KEY (id)
);

ALTER TABLE users
    ADD CONSTRAINT FK_users_agreement_id_agreement_id FOREIGN KEY (agreement_id)
        REFERENCES agreement (id) ON DELETE RESTRICT ON UPDATE RESTRICT;
