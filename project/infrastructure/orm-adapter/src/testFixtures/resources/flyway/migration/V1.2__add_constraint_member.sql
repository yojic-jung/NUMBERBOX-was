/**
  Def. N명의수학 제약조건 추가(24.09.20)
 */
/* 외래키 제약조건 */
ALTER TABLE members_role
    ADD CONSTRAINT members_role_foreign_key FOREIGN KEY (user_uniq_id)
        REFERENCES members (user_uniq_id) ON DELETE cascade
        ON UPDATE cascade;

ALTER TABLE members_private
    ADD CONSTRAINT members_private_foreign_key FOREIGN KEY (user_uniq_id)
        REFERENCES members (user_uniq_id) ON DELETE cascade
        ON UPDATE cascade;

ALTER TABLE members_profile
    ADD CONSTRAINT members_profile_foreign_key FOREIGN KEY (user_uniq_id)
        REFERENCES members (user_uniq_id) ON DELETE cascade
        ON UPDATE cascade;

ALTER TABLE member_refresh_token
    ADD CONSTRAINT member_refresh_token_foreign_key FOREIGN KEY (user_uniq_id)
        REFERENCES members (user_uniq_id) ON DELETE cascade
        ON UPDATE cascade;

/* unique 추가 */
ALTER TABLE members
    ADD CONSTRAINT email UNIQUE (email);
