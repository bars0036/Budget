CREATE ROLE localmattuser LOGIN
PASSWORD 'changeme';

--CREATE ROLE bsb_ec_web_account_service_admin LOGIN
--PASSWORD 'changeme';

GRANT localmattuser to postgres;
