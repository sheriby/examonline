create table if not exists user
(
    id       int primary key auto_increment,
    username varchar(32) unique not null,
    password varchar(20),
    role     varchar(10)
);

create table if not exists exam_question_type
(
    id   int primary key auto_increment,
    name varchar(128)
);

create table if not exists exam_question
(
    id          int primary key auto_increment,
    content     varchar(1024) not null,
    difficulty  varchar(10),
    type        int references exam_question_type (id),
    teacherName varchar(32),
    createTime  datetime,
    isselect    int
);

create table if not exists exam_paper
(
    id          int primary key auto_increment,
    teachername varchar(32),
    examtime    varchar(20),
    createTime  datetime,
    title       varchar(128)
);

create table if not exists exam_paper_questions
(
    id            int primary key auto_increment,
    exam_paper    int references exam_paper (id),
    exam_question int references exam_question (id)
);

create table if not exists exam_answer
(
    id     int primary key auto_increment,
    paper  int references exam_paper (id),
    name   varchar(32),
    answer mediumtext
);
