ALTER TABLE users
ADD COLUMN role_id uuid;

ALTER TABLE users
ADD CONSTRAINT fk_users_role
    FOREIGN KEY (role_id)
    REFERENCES roles(id);

ALTER TABLE users
ALTER COLUMN role_id SET NOT NULL;

DROP TABLE user_roles;