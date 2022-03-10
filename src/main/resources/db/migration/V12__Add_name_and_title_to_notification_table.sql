alter table NOTIFICATION
alter column "outerId" rename to OUTERID;

alter table NOTIFICATION
    add NOTIFIER_NAME VARCHAR(100);

alter table NOTIFICATION
    add OUTER_TITLE VARCHAR(256);

