CREATE TABLE users(
  username varchar_ignorecase(50) not null primary key,
  password varchar_ignorecase(250) not null,
  enabled boolean not null
);

CREATE TABLE authorities (
  username varchar_ignorecase(50) not null,
  authority varchar_ignorecase(50) not null,
  constraint fk_authorities_users foreign key(username) references users(username)
);

CREATE TABLE communaute (
  id double not null primary key,
  nom varchar_ignorecase(50) not null,
  responsable varchar_ignorecase(50) not null,
  description varchar_ignorecase(250) not null,
  constraint fk_communaute_users foreign key(responsable) references users(username)
);


create unique index ix_auth_username on authorities (username,authority);