alter table notification
alter column "outerid" rename to outerid;

alter table notification
    add notifier_name varchar(100);

alter table notification
    add outer_title varchar(256);

