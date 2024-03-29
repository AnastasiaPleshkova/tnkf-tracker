create table chat
(
    id              bigint generated always as identity,
    tg_chat_id      bigint                   not null,

    created_at      timestamp with time zone not null,
    created_by      text                     not null,

    primary key (id)
);

create table link
(
    id              bigint generated always as identity,
    url             text                     not null,

    last_check_time timestamp with time zone not null,
    updated_at      timestamp with time zone not null,

    answers_count    bigint                           ,
    commits_count   bigint                           ,

    created_at      timestamp with time zone not null,
    created_by      text                     not null,

    primary key (id),
    unique (url)
);

create table chat_link_mapping
(
    chat_id bigint not null,
    link_id bigint not null,

    primary key (chat_id, link_id),
    foreign key (chat_id) references chat (id) on delete cascade,
    foreign key (link_id) references link (id) on delete cascade
);


