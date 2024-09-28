-- url encriptado: https://bcrypt-generator.com/
INSERT INTO `users` VALUES
                    (1,'pepesan@gmail.com','pepesan','$2a$12$9w59VWZTWkgKqk1Z3VIzROFl17Go0QS6YsaaTDuqVLYymQ8kN5h3y','pepesan'),
                    -- password: password
                    (2,'admin@gmail.com','admin','$2a$10$gqHrslMttQWSsDSVRTK1OehkkBiXsJ/a4z2OURU./dizwOQu5Lovu','admin');
                    -- password: admin

INSERT INTO `roles` VALUES (1,'ROLE_ADMIN'),(2,'ROLE_USER');

INSERT INTO `users_roles` VALUES (2,1),(1,2);