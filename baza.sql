/*
 Navicat Premium Data Transfer

 Source Server         : conn
 Source Server Type    : PostgreSQL
 Source Server Version : 100006
 Source Host           : localhost:5432
 Source Catalog        : tbp_baza
 Source Schema         : public

 Target Server Type    : PostgreSQL
 Target Server Version : 100006
 File Encoding         : 65001

 Date: 30/06/2019 22:14:17
*/


-- ----------------------------
-- Sequence structure for bolnicki_podaci_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."bolnicki_podaci_id_seq";
CREATE SEQUENCE "public"."bolnicki_podaci_id_seq" 
INCREMENT 1
MINVALUE  1
MAXVALUE 2147483647
START 1
CACHE 1;

-- ----------------------------
-- Sequence structure for novorodence_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."novorodence_id_seq";
CREATE SEQUENCE "public"."novorodence_id_seq" 
INCREMENT 1
MINVALUE  1
MAXVALUE 2147483647
START 1
CACHE 1;

-- ----------------------------
-- Sequence structure for novorodence_id_trudnoce_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."novorodence_id_trudnoce_seq";
CREATE SEQUENCE "public"."novorodence_id_trudnoce_seq" 
INCREMENT 1
MINVALUE  1
MAXVALUE 2147483647
START 1
CACHE 1;

-- ----------------------------
-- Sequence structure for pregledi_id_bolnickih_podataka_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."pregledi_id_bolnickih_podataka_seq";
CREATE SEQUENCE "public"."pregledi_id_bolnickih_podataka_seq" 
INCREMENT 1
MINVALUE  1
MAXVALUE 2147483647
START 1
CACHE 1;

-- ----------------------------
-- Sequence structure for pregledi_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."pregledi_id_seq";
CREATE SEQUENCE "public"."pregledi_id_seq" 
INCREMENT 1
MINVALUE  1
MAXVALUE 2147483647
START 1
CACHE 1;

-- ----------------------------
-- Sequence structure for pregledi_tjedan_trudnoce_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."pregledi_tjedan_trudnoce_seq";
CREATE SEQUENCE "public"."pregledi_tjedan_trudnoce_seq" 
INCREMENT 1
MINVALUE  1
MAXVALUE 2147483647
START 1
CACHE 1;

-- ----------------------------
-- Sequence structure for trudnoca_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."trudnoca_id_seq";
CREATE SEQUENCE "public"."trudnoca_id_seq" 
INCREMENT 1
MINVALUE  1
MAXVALUE 2147483647
START 1
CACHE 1;

-- ----------------------------
-- Table structure for bolnicki_podaci
-- ----------------------------
DROP TABLE IF EXISTS "public"."bolnicki_podaci";
CREATE TABLE "public"."bolnicki_podaci" (
  "id" int4 NOT NULL DEFAULT nextval('bolnicki_podaci_id_seq'::regclass),
  "id_doktor" text COLLATE "pg_catalog"."default" NOT NULL,
  "naziv_bolnice" text COLLATE "pg_catalog"."default" NOT NULL,
  "podaci" text COLLATE "pg_catalog"."default" NOT NULL
)
;

-- ----------------------------
-- Records of bolnicki_podaci
-- ----------------------------
INSERT INTO "public"."bolnicki_podaci" VALUES (3, 'Tamara Vrcic', 'Varazdinska bolnica', 'Privatni ginekolog i voditelj trudnoce');
INSERT INTO "public"."bolnicki_podaci" VALUES (2, 'Ana Svrdlo', 'Cakovecka bolnica', 'Zamjenska ginekologica');

-- ----------------------------
-- Table structure for korisnica
-- ----------------------------
DROP TABLE IF EXISTS "public"."korisnica";
CREATE TABLE "public"."korisnica" (
  "OIB" varchar(11) COLLATE "pg_catalog"."default" NOT NULL,
  "ime" text COLLATE "pg_catalog"."default" NOT NULL,
  "prezime" text COLLATE "pg_catalog"."default" NOT NULL,
  "adresa" text COLLATE "pg_catalog"."default" NOT NULL,
  "email" text COLLATE "pg_catalog"."default",
  "telefon" text COLLATE "pg_catalog"."default",
  "datum_rodjenja" text COLLATE "pg_catalog"."default" NOT NULL,
  "korisnicko_ime" text COLLATE "pg_catalog"."default" NOT NULL,
  "lozinka" text COLLATE "pg_catalog"."default" NOT NULL
)
;

-- ----------------------------
-- Records of korisnica
-- ----------------------------
INSERT INTO "public"."korisnica" VALUES ('12345678911', 'Ana', 'Maric', 'Koprivnica 4a', 'ana@gmail.com', '042782957', '7.12.1995.', 'anaM', 'anaM');
INSERT INTO "public"."korisnica" VALUES ('12345678900', 'Antonija', 'Pofuk', 'R.Boskovica 6a', 'antonija@gmail.com', '042789123', '7.12.1995.', 'ap', 'ap');

-- ----------------------------
-- Table structure for novorodence
-- ----------------------------
DROP TABLE IF EXISTS "public"."novorodence";
CREATE TABLE "public"."novorodence" (
  "id" int4 NOT NULL DEFAULT nextval('novorodence_id_seq'::regclass),
  "id_trudnoce" int4 NOT NULL DEFAULT nextval('novorodence_id_trudnoce_seq'::regclass),
  "ime" text COLLATE "pg_catalog"."default" NOT NULL,
  "spol" text COLLATE "pg_catalog"."default" NOT NULL,
  "tezina" text COLLATE "pg_catalog"."default" NOT NULL
)
;

-- ----------------------------
-- Records of novorodence
-- ----------------------------
INSERT INTO "public"."novorodence" VALUES (9, 1, 'Ana', 'z', '12');

-- ----------------------------
-- Table structure for pregledi
-- ----------------------------
DROP TABLE IF EXISTS "public"."pregledi";
CREATE TABLE "public"."pregledi" (
  "id" int4 NOT NULL DEFAULT nextval('pregledi_id_seq'::regclass),
  "id_korisnice" varchar(11) COLLATE "pg_catalog"."default" NOT NULL,
  "id_bolnickih_podataka" int4 NOT NULL DEFAULT nextval('pregledi_id_bolnickih_podataka_seq'::regclass),
  "naziv" text COLLATE "pg_catalog"."default" NOT NULL,
  "opis" text COLLATE "pg_catalog"."default" NOT NULL,
  "tjedan_trudnoce" int4 NOT NULL DEFAULT nextval('pregledi_tjedan_trudnoce_seq'::regclass),
  "tezina_novorodenceta" text COLLATE "pg_catalog"."default" NOT NULL,
  "otkucaji_srca_novorodenceta" text COLLATE "pg_catalog"."default" NOT NULL,
  "opis_ultrazvuka" text COLLATE "pg_catalog"."default" NOT NULL,
  "datum" date NOT NULL
)
;

-- ----------------------------
-- Records of pregledi
-- ----------------------------
INSERT INTO "public"."pregledi" VALUES (6, '12345678911', 2, 'Prvi', 'Rutinski pregled', 1, '0', '2', 'null', '2019-01-01');

-- ----------------------------
-- Table structure for trudnoca
-- ----------------------------
DROP TABLE IF EXISTS "public"."trudnoca";
CREATE TABLE "public"."trudnoca" (
  "id" int4 NOT NULL DEFAULT nextval('trudnoca_id_seq'::regclass),
  "id_korisnice" varchar(11) COLLATE "pg_catalog"."default" NOT NULL,
  "pocetak" date NOT NULL,
  "kraj" date,
  "opis" text COLLATE "pg_catalog"."default" NOT NULL
)
;

-- ----------------------------
-- Records of trudnoca
-- ----------------------------
INSERT INTO "public"."trudnoca" VALUES (3, '12345678900', '2019-01-12', '2019-02-23', 'prva trudnoca');
INSERT INTO "public"."trudnoca" VALUES (2, '12345678900', '2019-06-19', '2019-06-20', 'treca trudnoca');
INSERT INTO "public"."trudnoca" VALUES (1, '12345678911', '2019-01-01', '2019-01-02', 'prva trudnoca');
INSERT INTO "public"."trudnoca" VALUES (9, '12345678911', '2019-06-30', '2019-07-01', 'druga trudnoca');

-- ----------------------------
-- Function structure for provjera_maila
-- ----------------------------
DROP FUNCTION IF EXISTS "public"."provjera_maila"();
CREATE OR REPLACE FUNCTION "public"."provjera_maila"()
  RETURNS "pg_catalog"."trigger" AS $BODY$
BEGIN
IF NEW.email LIKE '%@%' THEN
RETURN NEW;
ELSE
RAISE EXCEPTION '%', 'Unesena email adresa ne sadrzi znak ''@''';
END IF;
END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;

-- ----------------------------
-- Function structure for provjera_oib
-- ----------------------------
DROP FUNCTION IF EXISTS "public"."provjera_oib"();
CREATE OR REPLACE FUNCTION "public"."provjera_oib"()
  RETURNS "pg_catalog"."trigger" AS $BODY$
    BEGIN
    IF LENGTH(NEW."OIB") =  0 THEN
        RAISE EXCEPTION 'OIB mora sadrzavati 11 znamenki';
    END IF;
		
		 IF LENGTH(NEW."OIB") >  11 THEN
        RAISE EXCEPTION 'OIB mora sadrzavati 11 znamenki';
    END IF;
		
		 IF LENGTH(NEW."OIB") <  11 THEN
        RAISE EXCEPTION 'OIB mora sadrzavati 11 znamenki';
    END IF;
 
    IF POSITION(' ' IN NEW."OIB") > 0 THEN
        RAISE EXCEPTION 'OIB nesmije sadrzavati razmake.';
    END IF;
    RETURN NEW;
    END;
    $BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;

-- ----------------------------
-- Function structure for provjera_telefona
-- ----------------------------
DROP FUNCTION IF EXISTS "public"."provjera_telefona"();
CREATE OR REPLACE FUNCTION "public"."provjera_telefona"()
  RETURNS "pg_catalog"."trigger" AS $BODY$
BEGIN
IF NEW.telefon LIKE '%-%' THEN
RAISE EXCEPTION '%', 'Upisite broj telefona bez znakova ''/'' i ''-''';
ELSE
RETURN NEW;
END IF;
END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;

-- ----------------------------
-- Function structure for provjera_trudnoce
-- ----------------------------
DROP FUNCTION IF EXISTS "public"."provjera_trudnoce"();
CREATE OR REPLACE FUNCTION "public"."provjera_trudnoce"()
  RETURNS "pg_catalog"."trigger" AS $BODY$
BEGIN
IF NEW.pocetak < NEW.kraj THEN
RETURN NEW;
ELSE
RAISE EXCEPTION 'Pocetak mora biti prije kraja';
END IF;
END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
ALTER SEQUENCE "public"."bolnicki_podaci_id_seq"
OWNED BY "public"."bolnicki_podaci"."id";
SELECT setval('"public"."bolnicki_podaci_id_seq"', 4, true);
ALTER SEQUENCE "public"."novorodence_id_seq"
OWNED BY "public"."novorodence"."id";
SELECT setval('"public"."novorodence_id_seq"', 10, true);
ALTER SEQUENCE "public"."novorodence_id_trudnoce_seq"
OWNED BY "public"."novorodence"."id_trudnoce";
SELECT setval('"public"."novorodence_id_trudnoce_seq"', 2, false);
ALTER SEQUENCE "public"."pregledi_id_bolnickih_podataka_seq"
OWNED BY "public"."pregledi"."id_bolnickih_podataka";
SELECT setval('"public"."pregledi_id_bolnickih_podataka_seq"', 2, false);
ALTER SEQUENCE "public"."pregledi_id_seq"
OWNED BY "public"."pregledi"."id";
SELECT setval('"public"."pregledi_id_seq"', 7, true);
ALTER SEQUENCE "public"."pregledi_tjedan_trudnoce_seq"
OWNED BY "public"."pregledi"."tjedan_trudnoce";
SELECT setval('"public"."pregledi_tjedan_trudnoce_seq"', 2, false);
ALTER SEQUENCE "public"."trudnoca_id_seq"
OWNED BY "public"."trudnoca"."id";
SELECT setval('"public"."trudnoca_id_seq"', 10, true);

-- ----------------------------
-- Primary Key structure for table bolnicki_podaci
-- ----------------------------
ALTER TABLE "public"."bolnicki_podaci" ADD CONSTRAINT "bolnicki_podaci_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Triggers structure for table korisnica
-- ----------------------------
CREATE TRIGGER "provjera_maila" BEFORE INSERT OR UPDATE ON "public"."korisnica"
FOR EACH ROW
EXECUTE PROCEDURE "public"."provjera_maila"();
CREATE TRIGGER "provjera_oib" BEFORE INSERT OR UPDATE ON "public"."korisnica"
FOR EACH ROW
EXECUTE PROCEDURE "public"."provjera_oib"();
CREATE TRIGGER "provjera_telefona" BEFORE INSERT OR UPDATE ON "public"."korisnica"
FOR EACH ROW
EXECUTE PROCEDURE "public"."provjera_telefona"();

-- ----------------------------
-- Uniques structure for table korisnica
-- ----------------------------
ALTER TABLE "public"."korisnica" ADD CONSTRAINT "unique" UNIQUE ("OIB", "korisnicko_ime");

-- ----------------------------
-- Primary Key structure for table korisnica
-- ----------------------------
ALTER TABLE "public"."korisnica" ADD CONSTRAINT "korisnica_pkey" PRIMARY KEY ("OIB");

-- ----------------------------
-- Primary Key structure for table novorodence
-- ----------------------------
ALTER TABLE "public"."novorodence" ADD CONSTRAINT "novorodence_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table pregledi
-- ----------------------------
ALTER TABLE "public"."pregledi" ADD CONSTRAINT "pregledi_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Triggers structure for table trudnoca
-- ----------------------------
CREATE TRIGGER "provjera_trudnoce" BEFORE INSERT OR UPDATE ON "public"."trudnoca"
FOR EACH ROW
EXECUTE PROCEDURE "public"."provjera_trudnoce"();

-- ----------------------------
-- Primary Key structure for table trudnoca
-- ----------------------------
ALTER TABLE "public"."trudnoca" ADD CONSTRAINT "trudnoca_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Foreign Keys structure for table novorodence
-- ----------------------------
ALTER TABLE "public"."novorodence" ADD CONSTRAINT "fk_novorodence_trudnoca_1" FOREIGN KEY ("id_trudnoce") REFERENCES "public"."trudnoca" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table pregledi
-- ----------------------------
ALTER TABLE "public"."pregledi" ADD CONSTRAINT "fk_pregledi_bolnicki_podaci_1" FOREIGN KEY ("id_bolnickih_podataka") REFERENCES "public"."bolnicki_podaci" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."pregledi" ADD CONSTRAINT "fk_pregledi_korisnica_1" FOREIGN KEY ("id_korisnice") REFERENCES "public"."korisnica" ("OIB") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table trudnoca
-- ----------------------------
ALTER TABLE "public"."trudnoca" ADD CONSTRAINT "fk_trudnoca_korisnica_1" FOREIGN KEY ("id_korisnice") REFERENCES "public"."korisnica" ("OIB") ON DELETE NO ACTION ON UPDATE NO ACTION;
