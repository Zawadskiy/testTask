create table developer (id bigserial not null, experience_level varchar(255), name varchar(255), primary key (id));
create table dev_ops (id bigserial not null, experience_level varchar(255), name varchar(255), primary key (id));
create table project (id bigserial not null, primary key (id));
create table project_developer (project_id bigint not null, developer_id bigint not null, primary key (project_id, developer_id));
create table project_devops (project_id bigint not null, devops_id bigint not null, primary key (project_id, devops_id));
create table project_qualityassurances (project_id bigint not null, qualityassurances_id bigint not null, primary key (project_id, qualityassurances_id));
create table quality_assurance (id bigserial not null, experience_level varchar(255), name varchar(255), primary key (id));
alter table if exists project_developer add constraint FK4hcgppk5hytu952drdxa99dl8 foreign key (developer_id) references developer;
alter table if exists project_developer add constraint FKa9bhvvbom55j0q1kjkg0gkssw foreign key (project_id) references project;
alter table if exists project_devops add constraint FKgqjm7eeol9qm759v9uehad9nf foreign key (devops_id) references dev_ops;
alter table if exists project_devops add constraint FKkag9trs47spe0mfh5pp2duugp foreign key (project_id) references project;
alter table if exists project_qualityassurances add constraint FKl91etrf2r1by4lbbxmgahvwvi foreign key (qualityassurances_id) references quality_assurance;
alter table if exists project_qualityassurances add constraint FKna7npbmhy3rh2l8mnl7v77hhm foreign key (project_id) references project;

