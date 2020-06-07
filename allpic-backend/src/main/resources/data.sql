INSERT INTO role (role_name) values ('USER') ON CONFLICT DO NOTHING;
INSERT INTO role (role_name) values ('MOD') ON CONFLICT DO NOTHING;
INSERT INTO role (role_name) values ('ADMIN') ON CONFLICT DO NOTHING;