-- 1. Tabla users
CREATE TABLE users (
                       user_id INT PRIMARY KEY AUTO_INCREMENT,

                       first_name VARCHAR(200) NOT NULL,
                       last_name VARCHAR(200) NOT NULL,
                       user_email VARCHAR(300) NOT NULL UNIQUE,
                       user_password VARCHAR(255) NOT NULL, -- hash
                       user_active BOOLEAN NOT NULL DEFAULT TRUE,

                       user_sub ENUM ('FREE', 'PREMIUM', 'ADMIN') NOT NULL DEFAULT 'FREE',

                       user_degree VARCHAR(200),
                       user_location VARCHAR(200),
                       user_years INT,
                       user_bio TEXT,

                       user_skills JSON,
                       user_experience JSON,
                       user_education JSON
);

-- 2. Tabla employability
CREATE TABLE employability (
                               employability_id INT PRIMARY KEY AUTO_INCREMENT,
                               user_id INT NOT NULL,

                               tech_offer TEXT NOT NULL,
                               education_offer TEXT NOT NULL,
                               company_offer TEXT NOT NULL,

                               CONSTRAINT fk_employability_user
                                   FOREIGN KEY (user_id)
                                       REFERENCES users(user_id)
                                       ON DELETE CASCADE,

                               CONSTRAINT uq_employability_user UNIQUE (user_id)
);

-- 3. Tabla roadmap
CREATE TABLE roadmap (
                         roadmap_id INT PRIMARY KEY AUTO_INCREMENT,
                         user_id INT NOT NULL,

                         road_analisis TEXT NOT NULL,
                         road_proposal TEXT NOT NULL,
                         road_ideas TEXT NOT NULL,
                         road_keep TEXT NOT NULL,

                         CONSTRAINT fk_roadmap_user
                             FOREIGN KEY (user_id)
                                 REFERENCES users(user_id)
                                 ON DELETE CASCADE,

                         CONSTRAINT uq_roadmap_user UNIQUE (user_id)
);

-- 4. Tabla resumen
CREATE TABLE resumen (
                         resumen_id INT PRIMARY KEY AUTO_INCREMENT,
                         user_id INT NOT NULL,

                         resume_info TEXT NOT NULL,
                         resume_employability TEXT NOT NULL,
                         resume_simple TEXT NOT NULL,
                         resume_recomendation TEXT NOT NULL,

                         CONSTRAINT fk_resumen_user
                             FOREIGN KEY (user_id)
                                 REFERENCES users(user_id)
                                 ON DELETE CASCADE,

                         CONSTRAINT uq_resumen_user UNIQUE (user_id)
);