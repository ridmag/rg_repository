--
-- PostgreSQL database dump
--

SET statement_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = off;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET escape_string_warning = off;

--
-- Name: plpgsql; Type: PROCEDURAL LANGUAGE; Schema: -; Owner: postgres
--

CREATE OR REPLACE PROCEDURAL LANGUAGE plpgsql;


ALTER PROCEDURAL LANGUAGE plpgsql OWNER TO postgres;

SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: authorities; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE authorities (
    id integer NOT NULL,
    userid bigint,
    username character varying(20),
    authority character varying(20)
);


ALTER TABLE public.authorities OWNER TO postgres;

--
-- Name: authorities_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE authorities_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.authorities_id_seq OWNER TO postgres;

--
-- Name: authorities_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE authorities_id_seq OWNED BY authorities.id;


--
-- Name: authorities_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('authorities_id_seq', 3, true);


--
-- Name: calendars; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE calendars (
    id integer NOT NULL,
    name character varying(20)
);


ALTER TABLE public.calendars OWNER TO postgres;

--
-- Name: calendars_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE calendars_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.calendars_id_seq OWNER TO postgres;

--
-- Name: calendars_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE calendars_id_seq OWNED BY calendars.id;


--
-- Name: calendars_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('calendars_id_seq', 1, false);


--
-- Name: checkrecords; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE checkrecords (
    id integer NOT NULL,
    name character varying(255),
    needremarks boolean,
    needcompleted boolean,
    needexpiry boolean
);


ALTER TABLE public.checkrecords OWNER TO postgres;

--
-- Name: checkrecords_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE checkrecords_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.checkrecords_id_seq OWNER TO postgres;

--
-- Name: checkrecords_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE checkrecords_id_seq OWNED BY checkrecords.id;


--
-- Name: checkrecords_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('checkrecords_id_seq', 1, false);


--
-- Name: communication; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE communication (
    id integer NOT NULL,
    method character varying,
    eventdate date,
    eventtime timestamp with time zone,
    createdby bigint,
    keypersonstaff bigint,
    keypersonstudent bigint,
    keypersonorg bigint,
    summary character varying(255),
    category bigint,
    status character varying(20)
);


ALTER TABLE public.communication OWNER TO postgres;

--
-- Name: communication_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE communication_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.communication_id_seq OWNER TO postgres;

--
-- Name: communication_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE communication_id_seq OWNED BY communication.id;


--
-- Name: communication_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('communication_id_seq', 1, false);


--
-- Name: communicationcategories; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE communicationcategories (
    id integer NOT NULL,
    name character varying(50),
    parentid bigint
);


ALTER TABLE public.communicationcategories OWNER TO postgres;

--
-- Name: communicationcategories_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE communicationcategories_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.communicationcategories_id_seq OWNER TO postgres;

--
-- Name: communicationcategories_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE communicationcategories_id_seq OWNED BY communicationcategories.id;


--
-- Name: communicationcategories_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('communicationcategories_id_seq', 22, true);


--
-- Name: communicationnotes; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE communicationnotes (
    id bigint NOT NULL,
    communicationid bigint,
    note character varying(255),
    notify character varying(255)
);


ALTER TABLE public.communicationnotes OWNER TO postgres;

--
-- Name: communicationnotes_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE communicationnotes_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.communicationnotes_id_seq OWNER TO postgres;

--
-- Name: communicationnotes_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE communicationnotes_id_seq OWNED BY communicationnotes.id;


--
-- Name: communicationnotes_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('communicationnotes_id_seq', 1, false);


--
-- Name: communications; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE communications (
    id integer NOT NULL,
    method character varying,
    createddate date,
    createdtime timestamp with time zone,
    createdby bigint,
    keypersonstaff bigint,
    keypersonstudent bigint,
    keypersonorg bigint,
    keypersonvehicle bigint,
    keypersonfunding bigint,
    summary character varying(255),
    category bigint,
    status character varying(20),
    reminderid bigint
);


ALTER TABLE public.communications OWNER TO postgres;

--
-- Name: communications_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE communications_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.communications_id_seq OWNER TO postgres;

--
-- Name: communications_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE communications_id_seq OWNED BY communications.id;


--
-- Name: communications_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('communications_id_seq', 1, false);


--
-- Name: configurations; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE configurations (
    id integer NOT NULL,
    parameter character varying(20),
    contents character varying(100)
);


ALTER TABLE public.configurations OWNER TO postgres;

--
-- Name: configurations_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE configurations_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.configurations_id_seq OWNER TO postgres;

--
-- Name: configurations_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE configurations_id_seq OWNED BY configurations.id;


--
-- Name: configurations_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('configurations_id_seq', 1, false);


--
-- Name: consents; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE consents (
    id integer NOT NULL,
    name character varying(255)
);


ALTER TABLE public.consents OWNER TO postgres;

--
-- Name: consents_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE consents_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.consents_id_seq OWNER TO postgres;

--
-- Name: consents_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE consents_id_seq OWNED BY consents.id;


--
-- Name: consents_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('consents_id_seq', 5, true);


--
-- Name: contacts; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE contacts (
    id bigint NOT NULL,
    title character varying(5),
    firstname character varying(20),
    lastname character varying(20),
    middlenames character varying(40),
    businessname character varying(30),
    streetaddress character varying(60),
    city character varying(30),
    state character varying(30),
    postcode character varying(15),
    country character varying(30),
    geocode character varying(30),
    mobilephone character varying(20),
    homephone character varying(20),
    officephone character varying(20),
    fax character varying(20),
    email character varying(40),
    businesstype character varying(20),
    sendnewsletter boolean,
    sendinvoice boolean
);


ALTER TABLE public.contacts OWNER TO postgres;

--
-- Name: contacts_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE contacts_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.contacts_id_seq OWNER TO postgres;

--
-- Name: contacts_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE contacts_id_seq OWNED BY contacts.id;


--
-- Name: contacts_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('contacts_id_seq', 318, true);


--
-- Name: externalorganizations; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE externalorganizations (
    id bigint NOT NULL,
    contactid bigint,
    contactpersonid bigint,
    serviceareaid bigint,
    name character varying(50),
    paymentoption character varying(50),
    servicearea character varying,
    preferedsupplier boolean,
    status character varying(20)
);


ALTER TABLE public.externalorganizations OWNER TO postgres;

--
-- Name: externalorganizations_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE externalorganizations_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.externalorganizations_id_seq OWNER TO postgres;

--
-- Name: externalorganizations_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE externalorganizations_id_seq OWNED BY externalorganizations.id;


--
-- Name: externalorganizations_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('externalorganizations_id_seq', 1, false);


--
-- Name: filedata; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE filedata (
    id bigint NOT NULL,
    filename character varying(100),
    contenttype character varying(20),
    filetype character varying(20),
    datecreated date,
    staffid bigint,
    studentid bigint,
    sneedid bigint,
    vehicleid bigint,
    refid bigint,
    data bytea,
    thumbnail bytea
);


ALTER TABLE public.filedata OWNER TO postgres;

--
-- Name: filedata_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE filedata_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.filedata_id_seq OWNER TO postgres;

--
-- Name: filedata_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE filedata_id_seq OWNED BY filedata.id;


--
-- Name: filedata_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('filedata_id_seq', 1, false);


--
-- Name: fundingsources; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE fundingsources (
    id bigint NOT NULL,
    fundingcode character varying(20),
    fundingdescription character varying(255),
    fundingtype character varying(255),
    status character varying(20)
);


ALTER TABLE public.fundingsources OWNER TO postgres;

--
-- Name: fundingsources_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE fundingsources_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.fundingsources_id_seq OWNER TO postgres;

--
-- Name: fundingsources_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE fundingsources_id_seq OWNED BY fundingsources.id;


--
-- Name: fundingsources_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('fundingsources_id_seq', 14, true);


--
-- Name: groupedstudents; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE groupedstudents (
    id bigint NOT NULL,
    groupid bigint,
    studentid bigint,
    status character varying(50),
    remarks character varying(255),
    pickup bigint,
    dropoff bigint
);


ALTER TABLE public.groupedstudents OWNER TO postgres;

--
-- Name: groupedstudents_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE groupedstudents_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.groupedstudents_id_seq OWNER TO postgres;

--
-- Name: groupedstudents_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE groupedstudents_id_seq OWNED BY groupedstudents.id;


--
-- Name: groupedstudents_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('groupedstudents_id_seq', 851, true);


--
-- Name: holidays; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE holidays (
    id integer NOT NULL,
    calendarid bigint NOT NULL,
    date date,
    holidaytype character varying(20),
    remarks character varying(255)
);


ALTER TABLE public.holidays OWNER TO postgres;

--
-- Name: holidays_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE holidays_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.holidays_id_seq OWNER TO postgres;

--
-- Name: holidays_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE holidays_id_seq OWNED BY holidays.id;


--
-- Name: holidays_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('holidays_id_seq', 1, false);


--
-- Name: hoursutilizedreport; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE hoursutilizedreport (
    id integer NOT NULL,
    generateddate date,
    fromdate date,
    todate date
);


ALTER TABLE public.hoursutilizedreport OWNER TO postgres;

--
-- Name: hoursutilizedreport_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE hoursutilizedreport_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.hoursutilizedreport_id_seq OWNER TO postgres;

--
-- Name: hoursutilizedreport_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE hoursutilizedreport_id_seq OWNED BY hoursutilizedreport.id;


--
-- Name: hoursutilizedreport_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('hoursutilizedreport_id_seq', 1, false);


--
-- Name: hoursutilizedreportitem; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE hoursutilizedreportitem (
    id integer NOT NULL,
    studentid bigint NOT NULL,
    fundingtype character varying(255),
    reportid bigint,
    totalhours double precision,
    programhrsused double precision,
    transporthrsused double precision,
    balancehrs double precision,
    remarks character varying(255)
);


ALTER TABLE public.hoursutilizedreportitem OWNER TO postgres;

--
-- Name: hoursutilizedreportitem_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE hoursutilizedreportitem_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.hoursutilizedreportitem_id_seq OWNER TO postgres;

--
-- Name: hoursutilizedreportitem_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE hoursutilizedreportitem_id_seq OWNED BY hoursutilizedreportitem.id;


--
-- Name: hoursutilizedreportitem_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('hoursutilizedreportitem_id_seq', 1, false);


--
-- Name: invoiceitems; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE invoiceitems (
    id bigint NOT NULL,
    programname character varying(255),
    programtype character varying(50),
    studentgrp character varying(50),
    eventdate date,
    transactiondate date,
    chargeamount double precision,
    paidamount double precision,
    balance double precision,
    remarks character varying(255),
    invoiceid bigint,
    transportcharge double precision,
    programcharge double precision,
    paymenttype character varying(40)
);


ALTER TABLE public.invoiceitems OWNER TO postgres;

--
-- Name: invoiceitems_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE invoiceitems_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.invoiceitems_id_seq OWNER TO postgres;

--
-- Name: invoiceitems_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE invoiceitems_id_seq OWNED BY invoiceitems.id;


--
-- Name: invoiceitems_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('invoiceitems_id_seq', 2, true);


--
-- Name: invoices; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE invoices (
    id integer NOT NULL,
    studentid bigint NOT NULL,
    invoiceeid bigint,
    type character varying(10),
    description character varying(50),
    previousoutstanding double precision,
    totalcharge double precision,
    previouspayments double precision,
    transportcharge double precision,
    eftcharge double precision,
    otherpayment double precision,
    currentpayments double precision,
    subtotal double precision,
    discount double precision,
    tax double precision,
    total double precision,
    invoicedate timestamp with time zone,
    followupnote character varying(200),
    billedtothirdparty boolean,
    status character varying(20)
);


ALTER TABLE public.invoices OWNER TO postgres;

--
-- Name: invoices_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE invoices_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.invoices_id_seq OWNER TO postgres;

--
-- Name: invoices_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE invoices_id_seq OWNED BY invoices.id;


--
-- Name: invoices_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('invoices_id_seq', 1, true);


--
-- Name: leavepolicies; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE leavepolicies (
    id integer NOT NULL,
    name character varying(20),
    status character varying(20)
);


ALTER TABLE public.leavepolicies OWNER TO postgres;

--
-- Name: leavepolicies_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE leavepolicies_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.leavepolicies_id_seq OWNER TO postgres;

--
-- Name: leavepolicies_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE leavepolicies_id_seq OWNED BY leavepolicies.id;


--
-- Name: leavepolicies_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('leavepolicies_id_seq', 1, false);


--
-- Name: leavepolicydetails; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE leavepolicydetails (
    id integer NOT NULL,
    leavetype character varying(20),
    daysentitled integer,
    remarks character varying(250),
    leavepolicyid bigint NOT NULL
);


ALTER TABLE public.leavepolicydetails OWNER TO postgres;

--
-- Name: leavepolicydetails_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE leavepolicydetails_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.leavepolicydetails_id_seq OWNER TO postgres;

--
-- Name: leavepolicydetails_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE leavepolicydetails_id_seq OWNED BY leavepolicydetails.id;


--
-- Name: leavepolicydetails_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('leavepolicydetails_id_seq', 1, true);


--
-- Name: leaves; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE leaves (
    id integer NOT NULL,
    reason character varying(255),
    startdate date,
    enddate date,
    leavetype character varying(50),
    days integer,
    staffid bigint
);


ALTER TABLE public.leaves OWNER TO postgres;

--
-- Name: leaves_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE leaves_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.leaves_id_seq OWNER TO postgres;

--
-- Name: leaves_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE leaves_id_seq OWNED BY leaves.id;


--
-- Name: leaves_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('leaves_id_seq', 1, false);


--
-- Name: locations; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE locations (
    id integer NOT NULL,
    name character varying(50),
    locationcode character varying(10),
    contactid bigint,
    status character varying(20)
);


ALTER TABLE public.locations OWNER TO postgres;

--
-- Name: locations_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE locations_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.locations_id_seq OWNER TO postgres;

--
-- Name: locations_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE locations_id_seq OWNED BY locations.id;


--
-- Name: locations_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('locations_id_seq', 18, true);


--
-- Name: outlets; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE outlets (
    id bigint NOT NULL,
    serviceid character varying(20),
    name character varying(255),
    description character varying(255),
    mdsid character varying(255),
    fundingsourceid bigint,
    status character varying(255)
);


ALTER TABLE public.outlets OWNER TO postgres;

--
-- Name: outlets_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE outlets_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.outlets_id_seq OWNER TO postgres;

--
-- Name: outlets_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE outlets_id_seq OWNED BY outlets.id;


--
-- Name: outlets_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('outlets_id_seq', 9, true);


--
-- Name: programeventcoordinatorxref; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE programeventcoordinatorxref (
    programeventid bigint NOT NULL,
    coordinatorid bigint NOT NULL
);


ALTER TABLE public.programeventcoordinatorxref OWNER TO postgres;

--
-- Name: programevents; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE programevents (
    id bigint NOT NULL,
    name character varying(50),
    programid bigint NOT NULL,
    studentgroupid bigint NOT NULL,
    eventdate date,
    starttime timestamp with time zone,
    endtime timestamp with time zone,
    actualstarttime timestamp with time zone,
    actualendtime timestamp with time zone,
    coordinatorid bigint,
    locationid integer,
    status character varying(50),
    fundingsstatus character varying(20),
    vehicleid integer,
    printeduserid integer,
    printeddate date,
    generateddate date,
    completeddate date,
    invoiced boolean
);


ALTER TABLE public.programevents OWNER TO postgres;

--
-- Name: programevents_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE programevents_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.programevents_id_seq OWNER TO postgres;

--
-- Name: programevents_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE programevents_id_seq OWNED BY programevents.id;


--
-- Name: programevents_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('programevents_id_seq', 681, true);


--
-- Name: programs; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE programs (
    id bigint NOT NULL,
    name character varying(100),
    startdate date,
    enddate date,
    programtypeid integer NOT NULL,
    chargeamount double precision,
    status character varying(50),
    coordinatorid bigint,
    vehicleid integer
);


ALTER TABLE public.programs OWNER TO postgres;

--
-- Name: programs_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE programs_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.programs_id_seq OWNER TO postgres;

--
-- Name: programs_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE programs_id_seq OWNED BY programs.id;


--
-- Name: programs_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('programs_id_seq', 157, true);


--
-- Name: programtypes; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE programtypes (
    id integer NOT NULL,
    name character varying(50)
);


ALTER TABLE public.programtypes OWNER TO postgres;

--
-- Name: programtypes_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE programtypes_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.programtypes_id_seq OWNER TO postgres;

--
-- Name: programtypes_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE programtypes_id_seq OWNED BY programtypes.id;


--
-- Name: programtypes_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('programtypes_id_seq', 3, true);


--
-- Name: programweekxref; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE programweekxref (
    programid bigint NOT NULL,
    weekid bigint NOT NULL
);


ALTER TABLE public.programweekxref OWNER TO postgres;

--
-- Name: referenceitems; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE referenceitems (
    id bigint NOT NULL,
    category character varying(20),
    itemvalue character varying(50),
    status character varying(20)
);


ALTER TABLE public.referenceitems OWNER TO postgres;

--
-- Name: referenceitems_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE referenceitems_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.referenceitems_id_seq OWNER TO postgres;

--
-- Name: referenceitems_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE referenceitems_id_seq OWNED BY referenceitems.id;


--
-- Name: referenceitems_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('referenceitems_id_seq', 5, true);


--
-- Name: reminders; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE reminders (
    id bigint NOT NULL,
    referenceid bigint,
    createddate timestamp with time zone,
    remindon timestamp with time zone,
    type character varying(255),
    note character varying(255),
    no_of_reminders integer,
    status character varying(255)
);


ALTER TABLE public.reminders OWNER TO postgres;

--
-- Name: reminders_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE reminders_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.reminders_id_seq OWNER TO postgres;

--
-- Name: reminders_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE reminders_id_seq OWNED BY reminders.id;


--
-- Name: reminders_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('reminders_id_seq', 1, false);


--
-- Name: servicearea; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE servicearea (
    id integer NOT NULL,
    name character varying(255)
);


ALTER TABLE public.servicearea OWNER TO postgres;

--
-- Name: servicearea_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE servicearea_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.servicearea_id_seq OWNER TO postgres;

--
-- Name: servicearea_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE servicearea_id_seq OWNED BY servicearea.id;


--
-- Name: servicearea_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('servicearea_id_seq', 1, false);


--
-- Name: specialneeds; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE specialneeds (
    id integer NOT NULL,
    name character varying(50),
    filename character varying(50),
    photoid bigint
);


ALTER TABLE public.specialneeds OWNER TO postgres;

--
-- Name: specialneeds_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE specialneeds_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.specialneeds_id_seq OWNER TO postgres;

--
-- Name: specialneeds_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE specialneeds_id_seq OWNED BY specialneeds.id;


--
-- Name: specialneeds_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('specialneeds_id_seq', 9, true);


--
-- Name: staffcheckrecords; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE staffcheckrecords (
    id integer NOT NULL,
    checkrecordid bigint NOT NULL,
    staffid bigint NOT NULL,
    datecompleted date,
    expirydate date,
    checked boolean,
    renewable boolean,
    remarks character varying(100)
);


ALTER TABLE public.staffcheckrecords OWNER TO postgres;

--
-- Name: staffcheckrecords_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE staffcheckrecords_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.staffcheckrecords_id_seq OWNER TO postgres;

--
-- Name: staffcheckrecords_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE staffcheckrecords_id_seq OWNED BY staffcheckrecords.id;


--
-- Name: staffcheckrecords_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('staffcheckrecords_id_seq', 118, true);


--
-- Name: staffmembers; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE staffmembers (
    id bigint NOT NULL,
    staffid character varying(20),
    gender character varying(6),
    dob date,
    joineddate date,
    serviceenddate date,
    stafftypeid integer NOT NULL,
    employmenttype character varying(20),
    photoid bigint,
    leavepolicyid bigint,
    status character varying(20)
);


ALTER TABLE public.staffmembers OWNER TO postgres;

--
-- Name: staffmembers_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE staffmembers_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.staffmembers_id_seq OWNER TO postgres;

--
-- Name: staffmembers_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE staffmembers_id_seq OWNED BY staffmembers.id;


--
-- Name: staffmembers_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('staffmembers_id_seq', 1, false);


--
-- Name: staffskills; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE staffskills (
    id bigint NOT NULL,
    staffid bigint NOT NULL,
    referenceitemid bigint NOT NULL,
    level character varying(50)
);


ALTER TABLE public.staffskills OWNER TO postgres;

--
-- Name: staffskills_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE staffskills_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.staffskills_id_seq OWNER TO postgres;

--
-- Name: staffskills_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE staffskills_id_seq OWNED BY staffskills.id;


--
-- Name: staffskills_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('staffskills_id_seq', 1, false);


--
-- Name: stafftypes; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE stafftypes (
    id integer NOT NULL,
    name character varying(50)
);


ALTER TABLE public.stafftypes OWNER TO postgres;

--
-- Name: stafftypes_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE stafftypes_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.stafftypes_id_seq OWNER TO postgres;

--
-- Name: stafftypes_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE stafftypes_id_seq OWNED BY stafftypes.id;


--
-- Name: stafftypes_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('stafftypes_id_seq', 4, true);


--
-- Name: studentcommunicatonxref; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE studentcommunicatonxref (
    communicationid bigint NOT NULL,
    studentid bigint NOT NULL
);


ALTER TABLE public.studentcommunicatonxref OWNER TO postgres;

--
-- Name: studentconsents; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE studentconsents (
    id integer NOT NULL,
    consentid bigint NOT NULL,
    studentid bigint NOT NULL
);


ALTER TABLE public.studentconsents OWNER TO postgres;

--
-- Name: studentconsents_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE studentconsents_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.studentconsents_id_seq OWNER TO postgres;

--
-- Name: studentconsents_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE studentconsents_id_seq OWNED BY studentconsents.id;


--
-- Name: studentconsents_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('studentconsents_id_seq', 362, true);


--
-- Name: studentevents; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE studentevents (
    id bigint NOT NULL,
    eventid bigint NOT NULL,
    groupedstudentid bigint NOT NULL,
    amountpaid double precision,
    attended boolean,
    remarks character varying(255),
    chargetxid bigint
);


ALTER TABLE public.studentevents OWNER TO postgres;

--
-- Name: studentevents_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE studentevents_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.studentevents_id_seq OWNER TO postgres;

--
-- Name: studentevents_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE studentevents_id_seq OWNED BY studentevents.id;


--
-- Name: studentevents_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('studentevents_id_seq', 25, true);


--
-- Name: studentfundingsources; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE studentfundingsources (
    id bigint NOT NULL,
    studentid bigint NOT NULL,
    fundingsrcid bigint NOT NULL,
    outletid bigint,
    fundinglevel character varying(20),
    fundinghours bigint,
    fundinghoursused bigint,
    fundingstartdate date,
    active boolean NOT NULL
);


ALTER TABLE public.studentfundingsources OWNER TO postgres;

--
-- Name: studentfundingsources_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE studentfundingsources_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.studentfundingsources_id_seq OWNER TO postgres;

--
-- Name: studentfundingsources_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE studentfundingsources_id_seq OWNED BY studentfundingsources.id;


--
-- Name: studentfundingsources_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('studentfundingsources_id_seq', 1, false);


--
-- Name: studentgroups; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE studentgroups (
    id bigint NOT NULL,
    programid bigint,
    name character varying(50),
    status character varying(20)
);


ALTER TABLE public.studentgroups OWNER TO postgres;

--
-- Name: studentgroups_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE studentgroups_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.studentgroups_id_seq OWNER TO postgres;

--
-- Name: studentgroups_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE studentgroups_id_seq OWNED BY studentgroups.id;


--
-- Name: studentgroups_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('studentgroups_id_seq', 153, true);


--
-- Name: studentguardiansxref; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE studentguardiansxref (
    contactid bigint NOT NULL,
    studentid bigint NOT NULL
);


ALTER TABLE public.studentguardiansxref OWNER TO postgres;

--
-- Name: students; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE students (
    id bigint NOT NULL,
    cisid character varying(20),
    mdsid character varying(20),
    gender character varying(6),
    dob date,
    contactid bigint NOT NULL,
    mailingaddress bigint,
    photoid bigint,
    transport boolean,
    activefundingsrcid bigint,
    status character varying(20),
    mailaddressdefault boolean
);


ALTER TABLE public.students OWNER TO postgres;

--
-- Name: students_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE students_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.students_id_seq OWNER TO postgres;

--
-- Name: students_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE students_id_seq OWNED BY students.id;


--
-- Name: students_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('students_id_seq', 120, true);


--
-- Name: studentspecialneedsxref; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE studentspecialneedsxref (
    studentid bigint NOT NULL,
    specialneedid bigint NOT NULL,
    status character varying(50),
    reamarks character varying(255)
);


ALTER TABLE public.studentspecialneedsxref OWNER TO postgres;

--
-- Name: transactions; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE transactions (
    id integer NOT NULL,
    contactid bigint,
    studentid bigint,
    studenteventid bigint,
    programeventid bigint,
    invoiceid bigint,
    transactiontype character varying(20),
    amount double precision,
    remarks character varying(250),
    transactiondate date,
    createduserid bigint,
    paymenttype character varying(20)
);


ALTER TABLE public.transactions OWNER TO postgres;

--
-- Name: transactions_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE transactions_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.transactions_id_seq OWNER TO postgres;

--
-- Name: transactions_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE transactions_id_seq OWNED BY transactions.id;


--
-- Name: transactions_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('transactions_id_seq', 15, true);


--
-- Name: users; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE users (
    id bigint NOT NULL,
    contactid bigint NOT NULL,
    mailingaddressid bigint,
    username character varying(20),
    password character varying(20),
    user_type character varying(10),
    enabled boolean,
    systemuser boolean NOT NULL,
    createdon timestamp with time zone
);


ALTER TABLE public.users OWNER TO postgres;

--
-- Name: users_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE users_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.users_id_seq OWNER TO postgres;

--
-- Name: users_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE users_id_seq OWNED BY users.id;


--
-- Name: users_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('users_id_seq', 61, true);


--
-- Name: vehicles; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE vehicles (
    id bigint NOT NULL,
    name character varying(50),
    registrationid character varying(20),
    passengers integer NOT NULL,
    wheelchairs integer,
    status character varying(10),
    type character varying(10),
    photoid bigint
);


ALTER TABLE public.vehicles OWNER TO postgres;

--
-- Name: vehicles_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE vehicles_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.vehicles_id_seq OWNER TO postgres;

--
-- Name: vehicles_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE vehicles_id_seq OWNED BY vehicles.id;


--
-- Name: vehicles_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('vehicles_id_seq', 11, true);


--
-- Name: weekdays; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE weekdays (
    id bigint NOT NULL,
    name character varying(50)
);


ALTER TABLE public.weekdays OWNER TO postgres;

--
-- Name: weekdays_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE weekdays_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.weekdays_id_seq OWNER TO postgres;

--
-- Name: weekdays_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE weekdays_id_seq OWNED BY weekdays.id;


--
-- Name: weekdays_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('weekdays_id_seq', 1, false);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE authorities ALTER COLUMN id SET DEFAULT nextval('authorities_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE calendars ALTER COLUMN id SET DEFAULT nextval('calendars_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE checkrecords ALTER COLUMN id SET DEFAULT nextval('checkrecords_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE communication ALTER COLUMN id SET DEFAULT nextval('communication_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE communicationcategories ALTER COLUMN id SET DEFAULT nextval('communicationcategories_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE communicationnotes ALTER COLUMN id SET DEFAULT nextval('communicationnotes_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE communications ALTER COLUMN id SET DEFAULT nextval('communications_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE configurations ALTER COLUMN id SET DEFAULT nextval('configurations_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE consents ALTER COLUMN id SET DEFAULT nextval('consents_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE contacts ALTER COLUMN id SET DEFAULT nextval('contacts_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE externalorganizations ALTER COLUMN id SET DEFAULT nextval('externalorganizations_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE filedata ALTER COLUMN id SET DEFAULT nextval('filedata_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE fundingsources ALTER COLUMN id SET DEFAULT nextval('fundingsources_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE groupedstudents ALTER COLUMN id SET DEFAULT nextval('groupedstudents_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE holidays ALTER COLUMN id SET DEFAULT nextval('holidays_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE hoursutilizedreport ALTER COLUMN id SET DEFAULT nextval('hoursutilizedreport_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE hoursutilizedreportitem ALTER COLUMN id SET DEFAULT nextval('hoursutilizedreportitem_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE invoiceitems ALTER COLUMN id SET DEFAULT nextval('invoiceitems_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE invoices ALTER COLUMN id SET DEFAULT nextval('invoices_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE leavepolicies ALTER COLUMN id SET DEFAULT nextval('leavepolicies_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE leavepolicydetails ALTER COLUMN id SET DEFAULT nextval('leavepolicydetails_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE leaves ALTER COLUMN id SET DEFAULT nextval('leaves_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE locations ALTER COLUMN id SET DEFAULT nextval('locations_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE outlets ALTER COLUMN id SET DEFAULT nextval('outlets_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE programevents ALTER COLUMN id SET DEFAULT nextval('programevents_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE programs ALTER COLUMN id SET DEFAULT nextval('programs_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE programtypes ALTER COLUMN id SET DEFAULT nextval('programtypes_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE referenceitems ALTER COLUMN id SET DEFAULT nextval('referenceitems_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE reminders ALTER COLUMN id SET DEFAULT nextval('reminders_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE servicearea ALTER COLUMN id SET DEFAULT nextval('servicearea_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE specialneeds ALTER COLUMN id SET DEFAULT nextval('specialneeds_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE staffcheckrecords ALTER COLUMN id SET DEFAULT nextval('staffcheckrecords_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE staffmembers ALTER COLUMN id SET DEFAULT nextval('staffmembers_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE staffskills ALTER COLUMN id SET DEFAULT nextval('staffskills_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE stafftypes ALTER COLUMN id SET DEFAULT nextval('stafftypes_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE studentconsents ALTER COLUMN id SET DEFAULT nextval('studentconsents_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE studentevents ALTER COLUMN id SET DEFAULT nextval('studentevents_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE studentfundingsources ALTER COLUMN id SET DEFAULT nextval('studentfundingsources_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE studentgroups ALTER COLUMN id SET DEFAULT nextval('studentgroups_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE students ALTER COLUMN id SET DEFAULT nextval('students_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE transactions ALTER COLUMN id SET DEFAULT nextval('transactions_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE users ALTER COLUMN id SET DEFAULT nextval('users_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE vehicles ALTER COLUMN id SET DEFAULT nextval('vehicles_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE weekdays ALTER COLUMN id SET DEFAULT nextval('weekdays_id_seq'::regclass);


--
-- Data for Name: authorities; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY authorities (id, userid, username, authority) FROM stdin;
1	1	admin	ROLE_USER
2	1	admin	ROLE_SUPERVISOR
4	3		ROLE_USER
5	4		ROLE_USER
54	56		ROLE_USER
56	58		ROLE_USER
11	10		ROLE_USER
12	11		ROLE_USER
3	43		ROLE_USER
41	42		ROLE_USER
14	13		ROLE_USER
19	18		ROLE_USER
22	21		ROLE_USER
28	28		ROLE_USER
29	29		ROLE_USER
39	39		ROLE_USER
33	33		ROLE_USER
31	31		ROLE_USER
34	34		ROLE_USER
36	36		ROLE_USER
37	37		ROLE_USER
27	27		ROLE_USER
40	41		ROLE_USER
24	23		ROLE_USER
42	44		ROLE_USER
43	45		ROLE_USER
44	46		ROLE_USER
18	17		ROLE_USER
16	15		ROLE_USER
13	12		ROLE_USER
50	52		ROLE_USER
52	54		ROLE_USER
55	57		ROLE_USER
59	61		ROLE_USER
7	6		ROLE_USER
8	7		ROLE_USER
20	19		ROLE_USER
6	5		ROLE_USER
9	8		ROLE_USER
10	9		ROLE_USER
15	14		ROLE_USER
17	16		ROLE_USER
21	20		ROLE_USER
25	24		ROLE_USER
23	22		ROLE_USER
26	25		ROLE_USER
30	30		ROLE_USER
32	32		ROLE_USER
35	35		ROLE_USER
38	38		ROLE_USER
45	47		ROLE_USER
47	49		ROLE_USER
49	51		ROLE_USER
51	53		ROLE_USER
57	59		ROLE_USER
58	60		ROLE_USER
46	48		ROLE_USER
48	50		ROLE_USER
53	55		ROLE_USER
\.


--
-- Data for Name: calendars; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY calendars (id, name) FROM stdin;
1	General
2	Staff
3	Student
4	Location
5	Vehicle
\.


--
-- Data for Name: checkrecords; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY checkrecords (id, name, needremarks, needcompleted, needexpiry) FROM stdin;
1	Criminal Record Check	f	t	t
2	Working with Children	f	t	f
3	Confidentiality Agreement	f	f	f
4	Copy of Drivers license	t	f	t
5	Copy of Car Registration	f	f	t
6	Copy of Comprehensive Insurance	f	f	f
7	Eligibility to work in Australia	t	f	t
\.


--
-- Data for Name: communication; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY communication (id, method, eventdate, eventtime, createdby, keypersonstaff, keypersonstudent, keypersonorg, summary, category, status) FROM stdin;
\.


--
-- Data for Name: communicationcategories; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY communicationcategories (id, name, parentid) FROM stdin;
1	Administration	\N
2	Governance	\N
3	Staff Member	\N
4	Student	\N
5	Vehicle	\N
6	Facilities - Kariong	1
7	Facilities - Parkside	1
8	Facilities - Wyong	1
9	General Maintenance	1
10	Management Team	2
11	Funding Source	2
12	Finance	2
13	Board	2
14	Other	2
15	Administration	10
16	Coordinators	10
17	Other	10
18	Existing	4
19	Inquiry	4
20	Bank	12
21	Other	12
\.


--
-- Data for Name: communicationnotes; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY communicationnotes (id, communicationid, note, notify) FROM stdin;
\.


--
-- Data for Name: communications; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY communications (id, method, createddate, createdtime, createdby, keypersonstaff, keypersonstudent, keypersonorg, keypersonvehicle, keypersonfunding, summary, category, status, reminderid) FROM stdin;
\.


--
-- Data for Name: configurations; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY configurations (id, parameter, contents) FROM stdin;
5	LICENSE	g81SPc3Ushz/464tFyJ23LxNSUjl9vSz
6	THEME	./xmlhttp/css/rime/rime.css
7	COUNTRY	Australia
9	INVOICE_TAX	0.10
10	VERSION_DB	1.0
11	LOGO_NAME	\N
12	LOGO_ID	\N
13	ABN	\N
14	PASSWORD	pass123
15	SMTP_ADDRESS	smtp.gmail.com
16	IMAP_ADDRESS	imap.gmail.com
\.


--
-- Data for Name: consents; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY consents (id, name) FROM stdin;
1	Monitoring
2	MDS Sharing
3	Photo Sharing
4	Personal Info Sharing
\.


--
-- Data for Name: contacts; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY contacts (id, title, firstname, lastname, middlenames, businessname, streetaddress, city, state, postcode, country, geocode, mobilephone, homephone, officephone, fax, email, businesstype, sendnewsletter, sendinvoice) FROM stdin;
213	\N	\N	Campbell	\N	\N	19, Grieve Cl	Sydney	\N	2000	Australia	\N	0211111111	0211111111	0211111111	\N	e@e.com	\N	\N	\N
55	Mrs. 	Christine	Campbell		\N	19, Grieve Cl	Sydney	NSW	2000	Australia	\N	0211111111	0211111111	0211111111		e@e.com	\N	\N	\N
15	Mr. 	Mitchell	Campbell		\N	19, Grieve Cl	Sydney	NSW	2000	Australia	\N	0211111111	0211111111	0211111111		e@e.com	\N	\N	\N
54	Mr. 	Brian	Campbell		\N	19, Grieve Cl	Sydney	NSW	2000	Australia	\N	0211111111	0211111111	0211111111		e@e.com	\N	\N	\N
278	Miss.	Sophie	Campbell		\N	19, Grieve Cl	Sydney	NSW	2000	Australia	\N	0211111111	0211111111	0211111111		e@e.com	\N	\N	\N
19	Miss.	Melanie	Campbell		\N	19, Grieve Cl	Sydney	NSW	2000	Australia	\N	0211111111	0211111111	0211111111		e@e.com	\N	\N	\N
17	Mrs. 	Michelle	Campbell		\N	19, Grieve Cl	Sydney	NSW	2000	Australia	\N	0211111111	0211111111	0211111111		e@e.com	\N	\N	\N
199	\N	\N	Campbell	\N	\N	19, Grieve Cl	Sydney	ACT	2000	Australia	\N	0211111111	0211111111	0211111111	\N	e@e.com	\N	\N	\N
21	Mrs. 	Kim	Campbell		\N	19, Grieve Cl	Sydney	NSW	2000	Australia	\N	0211111111	0211111111	0211111111		e@e.com	\N	\N	\N
18	Miss.	Julie	Campbell		\N	19, Grieve Cl	Sydney	NSW	2000	Australia	\N	0211111111	0211111111	0211111111		e@e.com	\N	\N	\N
12	Mrs. 	Rhonda	Campbell		\N	19, Grieve Cl	Sydney	NSW	2000	Australia	\N	0211111111	0211111111	0211111111		e@e.com	\N	\N	\N
20	Miss.	Priscilla	Campbell		\N	19, Grieve Cl	Sydney	NSW	2000	Australia	\N	0211111111	0211111111	0211111111		e@e.com	\N	\N	\N
23	Mr. 	Paul	Campbell		\N	19, Grieve Cl	Sydney	NSW	2000	Australia	\N	0211111111	0211111111	0211111111		e@e.com	\N	\N	\N
126	Miss.	Tara	Campbell		\N	19, Grieve Cl	Sydney	NSW	2000	Australia	\N	0211111111	0211111111	0211111111		e@e.com	\N	\N	\N
136	Mr. 	Jade	Campbell		\N	19, Grieve Cl	Sydney	NSW	2000	Australia	\N	0211111111	0211111111	0211111111		e@e.com	\N	\N	\N
94	Miss.	Sandya	Campbell		\N	19, Grieve Cl	Sydney	NSW	2000	Australia	\N	0211111111	0211111111	0211111111		e@e.com	\N	\N	\N
204	Ms	Megan	Campbell	\N	\N	19, Grieve Cl	Sydney	NSW	2000	Australia	\N	0211111111	0211111111	0211111111	\N	e@e.com	\N	\N	\N
217	\N	\N	Campbell	\N	\N	19, Grieve Cl	Sydney	ACT	2000	Australia	\N	0211111111	0211111111	0211111111	\N	e@e.com	\N	\N	\N
208	Mr. 	Scott	Campbell		\N	19, Grieve Cl	Sydney	NSW	2000	Australia	\N	0211111111	0211111111	0211111111		e@e.com	\N	\N	\N
212	Ms	Julieanne	Campbell	\N	\N	19, Grieve Cl	Sydney	NSW	2000	Australia	\N	0211111111	0211111111	0211111111	\N	e@e.com	\N	\N	\N
223	\N	\N	Campbell	\N	\N	19, Grieve Cl	Sydney	ACT	2000	Australia	\N	0211111111	0211111111	0211111111	\N	e@e.com	\N	\N	\N
210	Mr. 	Andrew	Campbell		\N	19, Grieve Cl	Sydney	NSW	2000	Australia	\N	0211111111	0211111111	0211111111		e@e.com	\N	\N	\N
178	Mr. 	Sean	Campbell		\N	19, Grieve Cl	Sydney	NSW	2000	Australia	\N	0211111111	0211111111	0211111111		e@e.com	\N	\N	\N
171	\N	\N	Campbell	\N	\N	19, Grieve Cl	Sydney	\N	2000	Australia	\N	0211111111	0211111111	0211111111	\N	e@e.com	\N	\N	\N
192	Ms	Christyn	Campbell	\N	\N	19, Grieve Cl	Sydney	NSW	2000	Australia	\N	0211111111	0211111111	0211111111	\N	e@e.com	\N	\N	\N
193	\N	\N	Campbell	\N	\N	19, Grieve Cl	Sydney	\N	2000	Australia	\N	0211111111	0211111111	0211111111	\N	e@e.com	\N	\N	\N
144	Mr. 	Christopher	Campbell		\N	19, Grieve Cl	Sydney	NSW	2000	Australia	\N	0211111111	0211111111	0211111111		e@e.com	\N	\N	\N
254	Mr	Dylan	Campbell	\N	\N	19, Grieve Cl	Sydney	NSW	2000	Australia	\N	0211111111	0211111111	0211111111	\N	e@e.com	\N	\N	\N
202	Mr	Matthew	Campbell	\N	\N	19, Grieve Cl	Sydney	NSW	2000	Australia	\N	0211111111	0211111111	0211111111	\N	e@e.com	\N	\N	\N
246	Mr. 	Dean	Campbell		\N	19, Grieve Cl	Sydney	NSW	2000	Australia	\N	0211111111	0211111111	0211111111		e@e.com	\N	\N	\N
162	Mr. 	Luke	Campbell		\N	19, Grieve Cl	Sydney	NSW	2000	Australia	\N	0211111111	0211111111	0211111111		e@e.com	\N	\N	\N
250	Mr	Jon	Campbell	\N	\N	19, Grieve Cl	Sydney	NSW	2000	Australia	\N	0211111111	0211111111	0211111111	\N	e@e.com	\N	\N	\N
10	Miss.	Michelle	Campbell		\N	19, Grieve Cl	Sydney	NSW	2000	Australia	\N	0211111111	0211111111	0211111111		e@e.com	\N	\N	\N
185	\N	\N	Campbell	\N	\N	19, Grieve Cl	Sydney	\N	2000	Australia	\N	0211111111	0211111111	0211111111	\N	e@e.com	\N	\N	\N
167	\N	\N	Campbell	\N	\N	19, Grieve Cl	Sydney	\N	2000	Australia	\N	0211111111	0211111111	0211111111	\N	e@e.com	\N	\N	\N
203	\N	\N	Campbell	\N	\N	19, Grieve Cl	Sydney	\N	2000	Australia	\N	0211111111	0211111111	0211111111	\N	e@e.com	\N	\N	\N
151	\N	\N	Campbell	\N	\N	19, Grieve Cl	Sydney	ACT	2000	Australia	\N	0211111111	0211111111	0211111111	\N	e@e.com	\N	\N	\N
146	Mr. 	Corrie	Campbell		\N	19, Grieve Cl	Sydney	NSW	2000	Australia	\N	0211111111	0211111111	0211111111		e@e.com	\N	\N	\N
198	Mr. 	Keeden	Campbell		\N	19, Grieve Cl	Sydney	NSW	2000	Australia	\N	0211111111	0211111111	0211111111		e@e.com	\N	\N	\N
120	Mr. 	Ryan	Campbell		\N	19, Grieve Cl	Sydney	NSW	2000	Australia	\N	0211111111	0211111111	0211111111		e@e.com	\N	\N	\N
33	Mr	Daniel	Campbell	\N	\N	19, Grieve Cl	Sydney	NSW	2000	Australia	\N	0211111111	0211111111	0211111111	\N	e@e.com	\N	\N	\N
11	Miss.	Kerrie	Campbell		\N	19, Grieve Cl	Sydney	NSW	2000	Australia	\N	0211111111	0211111111	0211111111		e@e.com	\N	\N	\N
107	\N	\N	Campbell	\N	\N	19, Grieve Cl	Sydney	ACT	2000	Australia	\N	0211111111	0211111111	0211111111	\N	e@e.com	\N	\N	\N
274	Mr	Alex	Campbell	\N	\N	19, Grieve Cl	Sydney	NSW	2000	Australia	\N	0211111111	0211111111	0211111111	\N	e@e.com	\N	\N	\N
302	\N	\N	Campbell	\N	\N	19, Grieve Cl	Sydney	NSW	2000	Australia		0211111111	0211111111	0211111111	\N	e@e.com	\N	\N	\N
304	\N	\N	Campbell	\N	\N	19, Grieve Cl	Sydney	NSW	2000	Australia		0211111111	0211111111	0211111111	\N	e@e.com	\N	\N	\N
231	\N	\N	Campbell	\N	\N	19, Grieve Cl	Sydney	\N	2000	Australia	\N	0211111111	0211111111	0211111111	\N	e@e.com	\N	\N	\N
307	\N	\N	Campbell	\N	\N	19, Grieve Cl	Sydney	NSW	2000	Australia		0211111111	0211111111	0211111111	\N	e@e.com	\N	\N	\N
309	\N	\N	Campbell	\N	\N	19, Grieve Cl	Sydney	NSW	2000	Australia		0211111111	0211111111	0211111111	\N	e@e.com	\N	\N	\N
313	\N	\N	Campbell	\N	\N	19, Grieve Cl	Sydney	NSW	2000	Australia		0211111111	0211111111	0211111111	\N	e@e.com	\N	\N	\N
65	Miss.	Patricia	Campbell		\N	19, Grieve Cl	Sydney	NSW	2000	Australia	\N	0211111111	0211111111	0211111111		e@e.com	\N	\N	\N
68	Miss.	Susan	Campbell		\N	19, Grieve Cl	Sydney	NSW	2000	Australia	\N	0211111111	0211111111	0211111111		e@e.com	\N	\N	\N
105	\N	\N	Campbell	\N	\N	19, Grieve Cl	Sydney	\N	2000	Australia	\N	0211111111	0211111111	0211111111	\N	e@e.com	\N	\N	\N
163	\N	\N	Campbell	\N	\N	19, Grieve Cl	Sydney	\N	2000	Australia	\N	0211111111	0211111111	0211111111	\N	e@e.com	\N	\N	\N
271	\N	\N	Campbell	\N	\N	19, Grieve Cl	Sydney	\N	2000	Australia	\N	0211111111	0211111111	0211111111	\N	e@e.com	\N	\N	\N
276	Miss.	Karen	Campbell		\N	19, Grieve Cl	Sydney	NSW	2000	Australia	\N	0211111111	0211111111	0211111111		e@e.com	\N	\N	\N
145	\N	\N	Campbell	\N	\N	19, Grieve Cl	Sydney	NSW	2000	Australia	\N	0211111111	0211111111	0211111111	\N	e@e.com	\N	\N	\N
150	Mr. 	Jessie	Campbell		\N	19, Grieve Cl	Sydney	NSW	2000	Australia	\N	0211111111	0211111111	0211111111		e@e.com	\N	\N	\N
189	\N	\N	Campbell	\N	\N	19, Grieve Cl	Sydney	\N	2000	Australia	\N	0211111111	0211111111	0211111111	\N	e@e.com	\N	\N	\N
218	Mr. 	Jamie	Campbell		\N	19, Grieve Cl	Sydney	NSW	2000	Australia	\N	0211111111	0211111111	0211111111		e@e.com	\N	\N	\N
222	Mr. 	Brad	Campbell		\N	19, Grieve Cl	Sydney	NSW	2000	Australia	\N	0211111111	0211111111	0211111111		e@e.com	\N	\N	\N
219	\N	\N	Campbell	\N	\N	19, Grieve Cl	Sydney	ACT	2000	Australia	\N	0211111111	0211111111	0211111111	\N	e@e.com	\N	\N	\N
179	\N	\N	Campbell	\N	\N	19, Grieve Cl	Sydney	ACT	2000	Australia	\N	0211111111	0211111111	0211111111	\N	e@e.com	\N	\N	\N
221	\N	\N	Campbell	\N	\N	19, Grieve Cl	Sydney	\N	2000	Australia	\N	0211111111	0211111111	0211111111	\N	e@e.com	\N	\N	\N
236	Ms	Kate	Campbell	\N	\N	19, Grieve Cl	Sydney	NSW	2000	Australia	\N	0211111111	0211111111	0211111111	\N	e@e.com	\N	\N	\N
237	\N	\N	Campbell	\N	\N	19, Grieve Cl	Sydney	\N	2000	Australia	\N	0211111111	0211111111	0211111111	\N	e@e.com	\N	\N	\N
227	\N	\N	Campbell	\N	\N	19, Grieve Cl	Sydney	ACT	2000	Australia	\N	0211111111	0211111111	0211111111	\N	e@e.com	\N	\N	\N
295	\N	\N	Campbell	\N	\N	19, Grieve Cl	Sydney	ACT	2000	Australia	\N	0211111111	0211111111	0211111111	\N	e@e.com	\N	\N	\N
173	\N	\N	Campbell	\N	\N	19, Grieve Cl	Sydney	ACT	2000	Australia	\N	0211111111	0211111111	0211111111	\N	e@e.com	\N	\N	\N
153	\N	\N	Campbell	\N	\N	19, Grieve Cl	Sydney	\N	2000	Australia	\N	0211111111	0211111111	0211111111	\N	e@e.com	\N	\N	\N
8	Mr.	Administrator	Campbell	System	NA	19, Grieve Cl	Sydney	NA	2000	NA	2345, 5676	0211111111	0211111111	0211111111	\N	e@e.com	NA	f	\N
284	Mr. 	Michelle	Campbell		\N	19, Grieve Cl	Sydney	NSW	2000	Australia	\N	0211111111	0211111111	0211111111		e@e.com	\N	\N	\N
266	Mr	Michael	Campbell	\N	\N	19, Grieve Cl	Sydney	NSW	2000	Australia	\N	0211111111	0211111111	0211111111	\N	e@e.com	\N	\N	\N
299	\N	\N	Campbell	\N	\N	19, Grieve Cl	Sydney	\N	2000	Australia	\N	0211111111	0211111111	0211111111	\N	e@e.com	\N	\N	\N
28	Mr. 	Kyle	Campbell		\N	19, Grieve Cl	Sydney	NSW	2000	Australia	\N	0211111111	0211111111	0211111111		e@e.com	\N	\N	\N
48	Miss.	Julie	Campbell		\N	19, Grieve Cl	Sydney	NSW	2000	Australia	\N	0211111111	0211111111	0211111111		e@e.com	\N	\N	\N
238	Mr. 	Eric	Campbell		\N	19, Grieve Cl	Sydney	NSW	2000	Australia	\N	0211111111	0211111111	0211111111		e@e.com	\N	\N	\N
66	Mrs. 	Julie	Campbell		\N	19, Grieve Cl	Sydney	NSW	2000	Australia	\N	0211111111	0211111111	0211111111		e@e.com	\N	\N	\N
229	\N	\N	Campbell	\N	\N	19, Grieve Cl	Sydney	ACT	2000	Australia	\N	0211111111	0211111111	0211111111	\N	e@e.com	\N	\N	\N
240	Mr. 	Luke	Campbell		\N	19, Grieve Cl	Sydney	NSW	2000	Australia	\N	0211111111	0211111111	0211111111		e@e.com	\N	\N	\N
241	\N	\N	Campbell	\N	\N	19, Grieve Cl	Sydney	ACT	2000	Australia	\N	0211111111	0211111111	0211111111	\N	e@e.com	\N	\N	\N
256	Mr. 	Andrew	Campbell		\N	19, Grieve Cl	Sydney	NSW	2000	Australia	\N	0211111111	0211111111	0211111111		e@e.com	\N	\N	\N
248	Mr. 	Brendon	Campbell		\N	19, Grieve Cl	Sydney	NSW	2000	Australia	\N	0211111111	0211111111	0211111111		e@e.com	\N	\N	\N
50	Miss.	Cheree	Campbell		\N	19, Grieve Cl	Sydney	NSW	2000	Australia	\N	0211111111	0211111111	0211111111		e@e.com	\N	\N	\N
239	\N	\N	Campbell	\N	\N	19, Grieve Cl	Sydney	ACT	2000	Australia	\N	0211111111	0211111111	0211111111	\N	e@e.com	\N	\N	\N
270	Miss.	Jade	Campbell		\N	19, Grieve Cl	Sydney	NSW	2000	Australia	\N	0211111111	0211111111	0211111111		e@e.com	\N	\N	\N
174	Ms	Belinda	Campbell	\N	\N	19, Grieve Cl	Sydney	NSW	2000	Australia	\N	0211111111	0211111111	0211111111	\N	e@e.com	\N	\N	\N
175	\N	\N	Campbell	\N	\N	19, Grieve Cl	Sydney	\N	2000	Australia	\N	0211111111	0211111111	0211111111	\N	e@e.com	\N	\N	\N
191	\N	\N	Campbell	\N	\N	19, Grieve Cl	Sydney	ACT	2000	Australia	\N	0211111111	0211111111	0211111111	\N	e@e.com	\N	\N	\N
140	Ms	Kimberley	Campbell	\N	\N	19, Grieve Cl	Sydney	NSW	2000	Australia	\N	0211111111	0211111111	0211111111	\N	e@e.com	\N	\N	\N
141	\N	\N	Campbell	\N	\N	19, Grieve Cl	Sydney	\N	2000	Australia	\N	0211111111	0211111111	0211111111	\N	e@e.com	\N	\N	\N
161	\N	\N	Campbell	\N	\N	19, Grieve Cl	Sydney	\N	2000	Australia	\N	0211111111	0211111111	0211111111	\N	e@e.com	\N	\N	\N
260	Ms	Rhiannon	Campbell	\N	\N	19, Grieve Cl	Sydney	NSW	2000	Australia	\N	0211111111	0211111111	0211111111	\N	e@e.com	\N	\N	\N
172	Mr. 	Michael	Campbell		\N	19, Grieve Cl	Sydney	NSW	2000	Australia	\N	0211111111	0211111111	0211111111		e@e.com	\N	\N	\N
166	Mr	John	Campbell	\N	\N	19, Grieve Cl	Sydney	NSW	2000	Australia	\N	0211111111	0211111111	0211111111	\N	e@e.com	\N	\N	\N
168	Ms	Alina	Campbell	\N	\N	19, Grieve Cl	Sydney	NSW	2000	Australia	\N	0211111111	0211111111	0211111111	\N	e@e.com	\N	\N	\N
169	\N	\N	Campbell	\N	\N	19, Grieve Cl	Sydney	\N	2000	Australia	\N	0211111111	0211111111	0211111111	\N	e@e.com	\N	\N	\N
214	Miss.	Elise	Campbell		\N	19, Grieve Cl	Sydney	NSW	2000	Australia	\N	0211111111	0211111111	0211111111		e@e.com	\N	\N	\N
215	\N	\N	Campbell	\N	\N	19, Grieve Cl	Sydney	\N	2000	Australia	\N	0211111111	0211111111	0211111111	\N	e@e.com	\N	\N	\N
182	Ms	Erin	Campbell	\N	\N	19, Grieve Cl	Sydney	NSW	2000	Australia	\N	0211111111	0211111111	0211111111	\N	e@e.com	\N	\N	\N
183	\N	\N	Campbell	\N	\N	19, Grieve Cl	Sydney	\N	2000	Australia	\N	0211111111	0211111111	0211111111	\N	e@e.com	\N	\N	\N
156	Mr. 	Martin	Campbell		\N	19, Grieve Cl	Sydney	NSW	2000	Australia	\N	0211111111	0211111111	0211111111		e@e.com	\N	\N	\N
211	\N	\N	Campbell	\N	\N	19, Grieve Cl	Sydney	ACT	2000	Australia	\N	0211111111	0211111111	0211111111	\N	e@e.com	\N	\N	\N
267	\N	\N	Campbell	\N	\N	19, Grieve Cl	Sydney	\N	2000	Australia	\N	0211111111	0211111111	0211111111	\N	e@e.com	\N	\N	\N
281	\N	\N	Campbell	\N	\N	19, Grieve Cl	Sydney	NSW	2000	Australia	\N	0211111111	0211111111	0211111111	\N	e@e.com	\N	\N	\N
155	\N	\N	Campbell	\N	\N	19, Grieve Cl	Sydney	ACT	2000	Australia	\N	0211111111	0211111111	0211111111	\N	e@e.com	\N	\N	\N
311	\N	\N	Campbell	\N	\N	19, Grieve Cl	Sydney	NSW	2000	Australia		0211111111	0211111111	0211111111	\N	e@e.com	\N	\N	\N
312	\N	\N	Campbell	\N	\N	19, Grieve Cl	Sydney	NSW	2000	Australia		0211111111	0211111111	0211111111	\N	e@e.com	\N	\N	\N
233	\N	\N	Campbell	\N	\N	19, Grieve Cl	Sydney	\N	2000	Australia	\N	0211111111	0211111111	0211111111	\N	e@e.com	\N	\N	\N
289	\N	\N	Campbell	\N	\N	19, Grieve Cl	Sydney	ACT	2000	Australia	\N	0211111111	0211111111	0211111111	\N	e@e.com	\N	\N	\N
103	\N	\N	Campbell	\N	\N	19, Grieve Cl	Sydney	ACT	2000	Australia	\N	0211111111	0211111111	0211111111	\N	e@e.com	\N	\N	\N
109	\N	\N	Campbell	\N	\N	19, Grieve Cl	Sydney	ACT	2000	Australia	\N	0211111111	0211111111	0211111111	\N	e@e.com	\N	\N	\N
253	\N	\N	Campbell	\N	\N	19, Grieve Cl	Sydney	\N	2000	Australia	\N	0211111111	0211111111	0211111111	\N	e@e.com	\N	\N	\N
261	\N	\N	Campbell	\N	\N	19, Grieve Cl	Sydney	\N	2000	Australia	\N	0211111111	0211111111	0211111111	\N	e@e.com	\N	\N	\N
131	\N	\N	Campbell	\N	\N	19, Grieve Cl	Sydney	ACT	2000	Australia	\N	0211111111	0211111111	0211111111	\N	e@e.com	\N	\N	\N
76	\N	\N	Campbell	\N	\N	19, Grieve Cl	Sydney	NSW	2000	Australia	\N	0211111111	0211111111	0211111111	\N	e@e.com	\N	\N	\N
187	\N	\N	Campbell	\N	\N	19, Grieve Cl	Sydney	ACT	2000	Australia	\N	0211111111	0211111111	0211111111	\N	e@e.com	\N	\N	\N
285	\N	\N	Campbell	\N	\N	19, Grieve Cl	Sydney	ACT	2000	Australia	\N	0211111111	0211111111	0211111111	\N	e@e.com	\N	\N	\N
130	Miss.	Nicole	Campbell		\N	19, Grieve Cl	Sydney	NSW	2000	Australia	\N	0211111111	0211111111	0211111111		e@e.com	\N	\N	\N
96	Mr. 	Tamara	Campbell		\N	19, Grieve Cl	Sydney	NSW	2000	Australia	\N	0211111111	0211111111	0211111111		e@e.com	\N	\N	\N
74	Mr	Simon	Campbell	\N	\N	19, Grieve Cl	Sydney	NSW	2000	Australia	\N	0211111111	0211111111	0211111111	\N	e@e.com	\N	\N	\N
80	Mr. 	Leesa	Campbell		\N	19, Grieve Cl	Sydney	NSW	2000	Australia	\N	0211111111	0211111111	0211111111		e@e.com	\N	\N	\N
86	Mr. 	John	Campbell		\N	19, Grieve Cl	Sydney	NSW	2000	Australia	\N	0211111111	0211111111	0211111111		e@e.com	\N	\N	\N
87	\N	\N	Campbell	\N	\N	19, Grieve Cl	Sydney	ACT	2000	Australia	\N	0211111111	0211111111	0211111111	\N	e@e.com	\N	\N	\N
88	Mr. 	Roxanne	Campbell		\N	19, Grieve Cl	Sydney	NSW	2000	Australia	\N	0211111111	0211111111	0211111111		e@e.com	\N	\N	\N
132	Mr. 	Adam	Campbell		\N	19, Grieve Cl	Sydney	NSW	2000	Australia	\N	0211111111	0211111111	0211111111		e@e.com	\N	\N	\N
112	Mr. 	Amanda	Campbell		\N	19, Grieve Cl	Sydney	NSW	2000	Australia	\N	0211111111	0211111111	0211111111		e@e.com	\N	\N	\N
113	\N	\N	Campbell	\N	\N	19, Grieve Cl	Sydney	\N	2000	Australia	\N	0211111111	0211111111	0211111111	\N	e@e.com	\N	\N	\N
114	Mr. 	Mark	Campbell		\N	19, Grieve Cl	Sydney	NSW	2000	Australia	\N	0211111111	0211111111	0211111111		e@e.com	\N	\N	\N
73	Mr. 	Stephen	Campbell		\N	19, Grieve Cl	Sydney	NSW	2000	Australia	\N	0211111111	0211111111	0211111111		e@e.com	\N	\N	\N
81	\N	\N	Campbell	\N	\N	19, Grieve Cl	Sydney	ACT	2000	Australia	\N	0211111111	0211111111	0211111111	\N	e@e.com	\N	\N	\N
122	Ms	Alisa	Campbell	\N	\N	19, Grieve Cl	Sydney	NSW	2000	Australia	\N	0211111111	0211111111	0211111111	\N	e@e.com	\N	\N	\N
123	\N	\N	Campbell	\N	\N	19, Grieve Cl	Sydney	\N	2000	Australia	\N	0211111111	0211111111	0211111111	\N	e@e.com	\N	\N	\N
82	Mr. 	Patrick	Campbell		\N	19, Grieve Cl	Sydney	NSW	2000	Australia	\N	0211111111	0211111111	0211111111		e@e.com	\N	\N	\N
83	\N	\N	Campbell	\N	\N	19, Grieve Cl	Sydney	ACT	2000	Australia	\N	0211111111	0211111111	0211111111	\N	e@e.com	\N	\N	\N
110	Miss.	Angela	Campbell		\N	19, Grieve Cl	Sydney	NSW	2000	Australia	\N	0211111111	0211111111	0211111111		e@e.com	\N	\N	\N
111	\N	\N	Campbell	\N	\N	19, Grieve Cl	Sydney	ACT	2000	Australia	\N	0211111111	0211111111	0211111111	\N	e@e.com	\N	\N	\N
98	Miss.	Siobhan	Campbell		\N	19, Grieve Cl	Sydney	NSW	2000	Australia	\N	0211111111	0211111111	0211111111		e@e.com	\N	\N	\N
99	\N	\N	Campbell	\N	\N	19, Grieve Cl	Sydney	ACT	2000	Australia	\N	0211111111	0211111111	0211111111	\N	e@e.com	\N	\N	\N
138	Ms	Keira	Campbell	\N	\N	19, Grieve Cl	Sydney	NSW	2000	Australia	\N	0211111111	0211111111	0211111111	\N	e@e.com	\N	\N	\N
139	\N	\N	Campbell	\N	\N	19, Grieve Cl	Sydney	\N	2000	Australia	\N	0211111111	0211111111	0211111111	\N	e@e.com	\N	\N	\N
137	\N	\N	Campbell	\N	\N	19, Grieve Cl	Sydney	NSW	2000	Australia	\N	0211111111	0211111111	0211111111	\N	e@e.com	\N	\N	\N
133	\N	\N	Campbell	\N	\N	19, Grieve Cl	Sydney	ACT	2000	Australia	\N	0211111111	0211111111	0211111111	\N	e@e.com	\N	\N	\N
115	\N	\N	Campbell	\N	\N	19, Grieve Cl	Sydney	ACT	2000	Australia	\N	0211111111	0211111111	0211111111	\N	e@e.com	\N	\N	\N
154	Mr. 	Matthew	Campbell		\N	19, Grieve Cl	Sydney	NSW	2000	Australia	\N	0211111111	0211111111	0211111111		e@e.com	\N	\N	\N
79	\N	\N	Campbell	\N	\N	19, Grieve Cl	Sydney	ACT	2000	Australia	\N	0211111111	0211111111	0211111111	\N	e@e.com	\N	\N	\N
291	\N	\N	Campbell	\N	\N	19, Grieve Cl	Sydney	\N	2000	Australia	\N	0211111111	0211111111	0211111111	\N	e@e.com	\N	\N	\N
26	Mr. 	Peta	Campbell		\N	19, Grieve Cl	Sydney	NSW	2000	Australia	\N	0211111111	0211111111	0211111111		e@e.com	\N	\N	\N
22	Miss.	Melissa	Campbell		\N	19, Grieve Cl	Sydney	NSW	2000	Australia	\N	0211111111	0211111111	0211111111		e@e.com	\N	\N	\N
25	Mrs. 	Carol	Campbell		\N	19, Grieve Cl	Sydney	NSW	2000	Australia	\N	0211111111	0211111111	0211111111		e@e.com	\N	\N	\N
24	Mrs. 	Joanne	Campbell		\N	19, Grieve Cl	Sydney	NSW	2000	Australia	\N	0211111111	0211111111	0211111111		e@e.com	\N	\N	\N
27	Mrs. 	Mandy	Campbell		\N	19, Grieve Cl	Sydney	NSW	2000	Australia	\N	0211111111	0211111111	0211111111		e@e.com	\N	\N	\N
32	Mr. 	Phillip	Campbell		\N	19, Grieve Cl	Sydney	NSW	2000	Australia	\N	0211111111	0211111111	0211111111		e@e.com	\N	\N	\N
31	Mrs. 	Suzanna	Campbell		\N	19, Grieve Cl	Sydney	NSW	2000	Australia	\N	0211111111	0211111111	0211111111		e@e.com	\N	\N	\N
29	Mrs. 	Suzanne	Campbell		\N	19, Grieve Cl	Sydney	NSW	2000	Australia	\N	0211111111	0211111111	0211111111		e@e.com	\N	\N	\N
30	Mrs. 	Mardi	Campbell		\N	19, Grieve Cl	Sydney	NSW	2000	Australia	\N	0211111111	0211111111	0211111111		e@e.com	\N	\N	\N
37	Mr. 	Ziggy	Campbell		\N	19, Grieve Cl	Sydney	NSW	2000	Australia	\N	0211111111	0211111111	0211111111		e@e.com	\N	\N	\N
35	Miss.	Lyndal	Campbell		\N	19, Grieve Cl	Sydney	NSW	2000	Australia	\N	0211111111	0211111111	0211111111		e@e.com	\N	\N	\N
36	Miss.	Robyn	Campbell		\N	19, Grieve Cl	Sydney	NSW	2000	Australia	\N	0211111111	0211111111	0211111111		e@e.com	\N	\N	\N
34	Miss.	Belinda	Campbell		\N	19, Grieve Cl	Sydney	NSW	2000	Australia	\N	0211111111	0211111111	0211111111		e@e.com	\N	\N	\N
39	Mrs. 	Donna	Campbell		\N	19, Grieve Cl	Sydney	NSW	2000	Australia	\N	0211111111	0211111111	0211111111		e@e.com	\N	\N	\N
45	Mr. 	Graham	Campbell		\N	19, Grieve Cl	Sydney	NSW	2000	Australia	\N	0211111111	0211111111	0211111111		e@e.com	\N	\N	\N
38	Mrs. 	Vicki	Campbell		\N	19, Grieve Cl	Sydney	NSW	2000	Australia	\N	0211111111	0211111111	0211111111		e@e.com	\N	\N	\N
297	\N	\N	Campbell	\N	\N	19, Grieve Cl	Sydney	\N	2000	Australia	\N	0211111111	0211111111	0211111111	\N	e@e.com	\N	\N	\N
280	Mr. 	Alison	Campbell		\N	19, Grieve Cl	Sydney	NSW	2000	Australia	\N	0211111111	0211111111	0211111111		e@e.com	\N	\N	\N
282	Mr. 	Andrew	Campbell		\N	19, Grieve Cl	Sydney	NSW	2000	Australia	\N	0211111111	0211111111	0211111111		e@e.com	\N	\N	\N
242	Mr. 	John	Campbell		\N	19, Grieve Cl	Sydney	NSW	2000	Australia	\N	0211111111	0211111111	0211111111		e@e.com	\N	\N	\N
41	Miss.	Katie	Campbell		\N	19, Grieve Cl	Sydney	NSW	2000	Australia	\N	0211111111	0211111111	0211111111		e@e.com	\N	\N	\N
42	Mrs. 	Lorraine	Campbell		\N	19, Grieve Cl	Sydney	NSW	2000	Australia	\N	0211111111	0211111111	0211111111		e@e.com	\N	\N	\N
247	\N	\N	Campbell	\N	\N	19, Grieve Cl	Sydney	ACT	2000	Australia	\N	0211111111	0211111111	0211111111	\N	e@e.com	\N	\N	\N
258	Mr. 	Jason	Campbell		\N	19, Grieve Cl	Sydney	NSW	2000	Australia	\N	0211111111	0211111111	0211111111		e@e.com	\N	\N	\N
290	Mr	Steven	Campbell	\N	\N	19, Grieve Cl	Sydney	NSW	2000	Australia	\N	0211111111	0211111111	0211111111	\N	e@e.com	\N	\N	\N
292	Mr	Dennis	Campbell	\N	\N	19, Grieve Cl	Sydney	NSW	2000	Australia	\N	0211111111	0211111111	0211111111	\N	e@e.com	\N	\N	\N
293	\N	\N	Campbell	\N	\N	19, Grieve Cl	Sydney	\N	2000	Australia	\N	0211111111	0211111111	0211111111	\N	e@e.com	\N	\N	\N
286	Mr. 	Aydin	Campbell		\N	19, Grieve Cl	Sydney	NSW	2000	Australia	\N	0211111111	0211111111	0211111111		e@e.com	\N	\N	\N
277	\N	\N	Campbell	\N	\N	19, Grieve Cl	Sydney	ACT	2000	Australia	\N	0211111111	0211111111	0211111111	\N	e@e.com	\N	\N	\N
268	Miss.	Amy	Campbell		\N	19, Grieve Cl	Sydney	NSW	2000	Australia	\N	0211111111	0211111111	0211111111		e@e.com	\N	\N	\N
259	\N	\N	Campbell	\N	\N	19, Grieve Cl	Sydney	ACT	2000	Australia	\N	0211111111	0211111111	0211111111	\N	e@e.com	\N	\N	\N
288	Miss.	Lauren	Campbell		\N	19, Grieve Cl	Sydney	NSW	2000	Australia	\N	0211111111	0211111111	0211111111		e@e.com	\N	\N	\N
44	Miss.	Annika	Campbell		\N	19, Grieve Cl	Sydney	NSW	2000	Australia	\N	0211111111	0211111111	0211111111		e@e.com	\N	\N	\N
43	Miss.	Elle	Campbell		\N	19, Grieve Cl	Sydney	NSW	2000	Australia	\N	0211111111	0211111111	0211111111		e@e.com	\N	\N	\N
40	Miss.	Linda	Campbell		\N	19, Grieve Cl	Sydney	NSW	2000	Australia	\N	0211111111	0211111111	0211111111		e@e.com	\N	\N	\N
52	Mrs. 	Denise	Campbell		\N	19, Grieve Cl	Sydney	NSW	2000	Australia	\N	0211111111	0211111111	0211111111		e@e.com	\N	\N	\N
47	Mr. 	David	Campbell		\N	19, Grieve Cl	Sydney	NSW	2000	Australia	\N	0211111111	0211111111	0211111111		e@e.com	\N	\N	\N
128	Mrs. 	Caitlin	Campbell		\N	19, Grieve Cl	Sydney	NSW	2000	Australia	\N	0211111111	0211111111	0211111111		e@e.com	\N	\N	\N
77	Mr. 	Eric	Campbell		\N	19, Grieve Cl	Sydney	NSW	2000	Australia	\N	0211111111	0211111111	0211111111		e@e.com	\N	\N	\N
121	\N	\N	Campbell	\N	\N	19, Grieve Cl	Sydney	\N	2000	Australia	\N	0211111111	0211111111	0211111111	\N	e@e.com	\N	\N	\N
124	Miss.	Michelle	Campbell		\N	19, Grieve Cl	Sydney	NSW	2000	Australia	\N	0211111111	0211111111	0211111111		e@e.com	\N	\N	\N
125	\N	\N	Campbell	\N	\N	19, Grieve Cl	Sydney	ACT	2000	Australia	\N	0211111111	0211111111	0211111111	\N	e@e.com	\N	\N	\N
84	Ms	Joanne	Campbell	\N	\N	19, Grieve Cl	Sydney	NSW	2000	Australia	\N	0211111111	0211111111	0211111111	\N	e@e.com	\N	\N	\N
176	Miss.	Jane	Campbell		\N	19, Grieve Cl	Sydney	NSW	2000	Australia	\N	0211111111	0211111111	0211111111		e@e.com	\N	\N	\N
188	Mr. 	Sam	Campbell		\N	19, Grieve Cl	Sydney	NSW	2000	Australia	\N	0211111111	0211111111	0211111111		e@e.com	\N	\N	\N
262	Mr. 	Louise	Campbell		\N	19, Grieve Cl	Sydney	NSW	2000	Australia	\N	0211111111	0211111111	0211111111		e@e.com	\N	\N	\N
232	Mr. 	George	Campbell		\N	19, Grieve Cl	Sydney	NSW	2000	Australia	\N	0211111111	0211111111	0211111111		e@e.com	\N	\N	\N
252	Mr. 	Joel	Campbell		\N	19, Grieve Cl	Sydney	NSW	2000	Australia	\N	0211111111	0211111111	0211111111		e@e.com	\N	\N	\N
224	Mr. 	Zarko	Campbell		\N	19, Grieve Cl	Sydney	NSW	2000	Australia	\N	0211111111	0211111111	0211111111		e@e.com	\N	\N	\N
244	Mr. 	Shannon	Campbell		\N	19, Grieve Cl	Sydney	NSW	2000	Australia	\N	0211111111	0211111111	0211111111		e@e.com	\N	\N	\N
245	\N	\N	Campbell	\N	\N	19, Grieve Cl	Sydney	ACT	2000	Australia	\N	0211111111	0211111111	0211111111	\N	e@e.com	\N	\N	\N
264	Mr. 	John	Campbell		\N	19, Grieve Cl	Sydney	NSW	2000	Australia	\N	0211111111	0211111111	0211111111		e@e.com	\N	\N	\N
265	\N	\N	Campbell	\N	\N	19, Grieve Cl	Sydney	\N	2000	Australia	\N	0211111111	0211111111	0211111111	\N	e@e.com	\N	\N	\N
46	Mrs. 	Cheryl	Campbell		\N	19, Grieve Cl	Sydney	NSW	2000	Australia	\N	0211111111	0211111111	0211111111		e@e.com	\N	\N	\N
51	Mr. 	Sindy	Campbell		\N	19, Grieve Cl	Sydney	NSW	2000	Australia	\N	0211111111	0211111111	0211111111		e@e.com	\N	\N	\N
53	Miss.	Gabrielle	Campbell		\N	19, Grieve Cl	Sydney	NSW	2000	Australia	\N	0211111111	0211111111	0211111111		e@e.com	\N	\N	\N
60	Mr. 	Richard	Campbell		\N	19, Grieve Cl	Sydney	NSW	2000	Australia	\N	0211111111	0211111111	0211111111		e@e.com	\N	\N	\N
249	\N	\N	Campbell	\N	\N	19, Grieve Cl	Sydney	ACT	2000	Australia	\N	0211111111	0211111111	0211111111	\N	e@e.com	\N	\N	\N
148	Mr. 	David	Campbell		\N	19, Grieve Cl	Sydney	NSW	2000	Australia	\N	0211111111	0211111111	0211111111		e@e.com	\N	\N	\N
149	\N	\N	Campbell	\N	\N	19, Grieve Cl	Sydney	\N	2000	Australia	\N	0211111111	0211111111	0211111111	\N	e@e.com	\N	\N	\N
152	Ms	Kate	Campbell	\N	\N	19, Grieve Cl	Sydney	NSW	2000	Australia	\N	0211111111	0211111111	0211111111	\N	e@e.com	\N	\N	\N
85	\N	\N	Campbell	\N	\N	19, Grieve Cl	Sydney	\N	2000	Australia	\N	0211111111	0211111111	0211111111	\N	e@e.com	\N	\N	\N
134	Mr. 	Aaron	Campbell		\N	19, Grieve Cl	Sydney	NSW	2000	Australia	\N	0211111111	0211111111	0211111111		e@e.com	\N	\N	\N
135	\N	\N	Campbell	\N	\N	19, Grieve Cl	Sydney	ACT	2000	Australia	\N	0211111111	0211111111	0211111111	\N	e@e.com	\N	\N	\N
92	Mr	Jeffrey	Campbell	\N	\N	19, Grieve Cl	Sydney	NSW	2000	Australia	\N	0211111111	0211111111	0211111111	\N	e@e.com	\N	\N	\N
93	\N	\N	Campbell	\N	\N	19, Grieve Cl	Sydney	\N	2000	Australia	\N	0211111111	0211111111	0211111111	\N	e@e.com	\N	\N	\N
102	Mr. 	Kelly	Campbell		\N	19, Grieve Cl	Sydney	NSW	2000	Australia	\N	0211111111	0211111111	0211111111		e@e.com	\N	\N	\N
70	Miss.	Stacey	Campbell		\N	19, Grieve Cl	Sydney	NSW	2000	Australia	\N	0211111111	0211111111	0211111111		e@e.com	\N	\N	\N
147	\N	\N	Campbell	\N	\N	19, Grieve Cl	Sydney	ACT	2000	Australia	\N	0211111111	0211111111	0211111111	\N	e@e.com	\N	\N	\N
106	Mr. 	Luke	Campbell		\N	19, Grieve Cl	Sydney	NSW	2000	Australia	\N	0211111111	0211111111	0211111111		e@e.com	\N	\N	\N
255	\N	\N	Campbell	\N	\N	19, Grieve Cl	Sydney	\N	2000	Australia	\N	0211111111	0211111111	0211111111	\N	e@e.com	\N	\N	\N
263	\N	\N	Campbell	\N	\N	19, Grieve Cl	Sydney	ACT	2000	Australia	\N	0211111111	0211111111	0211111111	\N	e@e.com	\N	\N	\N
72	Miss.	Vanessa	Campbell		\N	19, Grieve Cl	Sydney	NSW	2000	Australia	\N	0211111111	0211111111	0211111111		e@e.com	\N	\N	\N
75	Mr	Levi	Campbell	\N	\N	19, Grieve Cl	Sydney	NSW	2000	Australia	\N	0211111111	0211111111	0211111111	\N	e@e.com	\N	\N	\N
216	Mr. 	Jimmy	Campbell		\N	19, Grieve Cl	Sydney	NSW	2000	Australia	\N	0211111111	0211111111	0211111111		e@e.com	\N	\N	\N
142	Miss.	Alyce	Campbell		\N	19, Grieve Cl	Sydney	NSW	2000	Australia	\N	0211111111	0211111111	0211111111		e@e.com	\N	\N	\N
180	Mr. 	Michael	Campbell		\N	19, Grieve Cl	Sydney	NSW	2000	Australia	\N	0211111111	0211111111	0211111111		e@e.com	\N	\N	\N
206	Mr. 	Katrina	Campbell		\N	19, Grieve Cl	Sydney	NSW	2000	Australia	\N	0211111111	0211111111	0211111111		e@e.com	\N	\N	\N
186	Mr. 	Mitchell	Campbell		\N	19, Grieve Cl	Sydney	NSW	2000	Australia	\N	0211111111	0211111111	0211111111		e@e.com	\N	\N	\N
220	Mr. 	Nathan	Campbell		\N	19, Grieve Cl	Sydney	NSW	2000	Australia	\N	0211111111	0211111111	0211111111		e@e.com	\N	\N	\N
194	Miss.	Merrilyn	Campbell		\N	19, Grieve Cl	Sydney	NSW	2000	Australia	\N	0211111111	0211111111	0211111111		e@e.com	\N	\N	\N
177	\N	\N	Campbell	\N	\N	19, Grieve Cl	Sydney	ACT	2000	Australia	\N	0211111111	0211111111	0211111111	\N	e@e.com	\N	\N	\N
170	Mr. 	Daniel	Campbell		\N	19, Grieve Cl	Sydney	NSW	2000	Australia	\N	0211111111	0211111111	0211111111		e@e.com	\N	\N	\N
158	Miss.	Nicole	Campbell		\N	19, Grieve Cl	Sydney	NSW	2000	Australia	\N	0211111111	0211111111	0211111111		e@e.com	\N	\N	\N
159	\N	\N	Campbell	\N	\N	19, Grieve Cl	Sydney	\N	2000	Australia	\N	0211111111	0211111111	0211111111	\N	e@e.com	\N	\N	\N
200	Mr. 	Francis	Campbell		\N	19, Grieve Cl	Sydney	NSW	2000	Australia	\N	0211111111	0211111111	0211111111		e@e.com	\N	\N	\N
201	\N	\N	Campbell	\N	\N	19, Grieve Cl	Sydney	ACT	2000	Australia	\N	0211111111	0211111111	0211111111	\N	e@e.com	\N	\N	\N
160	Mr. 	Rebecca	Campbell		\N	19, Grieve Cl	Sydney	NSW	2000	Australia	\N	0211111111	0211111111	0211111111		e@e.com	\N	\N	\N
195	\N	\N	Campbell	\N	\N	19, Grieve Cl	Sydney	ACT	2000	Australia	\N	0211111111	0211111111	0211111111	\N	e@e.com	\N	\N	\N
164	Mr. 	Bradley	Campbell		\N	19, Grieve Cl	Sydney	NSW	2000	Australia	\N	0211111111	0211111111	0211111111		e@e.com	\N	\N	\N
205	\N	\N	Campbell	\N	\N	19, Grieve Cl	Sydney	\N	2000	Australia	\N	0211111111	0211111111	0211111111	\N	e@e.com	\N	\N	\N
190	Mr. 	Hugh	Campbell		\N	19, Grieve Cl	Sydney	NSW	2000	Australia	\N	0211111111	0211111111	0211111111		e@e.com	\N	\N	\N
230	Mr. 	Darryl	Campbell		\N	19, Grieve Cl	Sydney	NSW	2000	Australia	\N	0211111111	0211111111	0211111111		e@e.com	\N	\N	\N
100	Ms	Angela	Campbell	\N	\N	19, Grieve Cl	Sydney	NSW	2000	Australia	\N	0211111111	0211111111	0211111111	\N	e@e.com	\N	\N	\N
101	\N	\N	Campbell	\N	\N	19, Grieve Cl	Sydney	\N	2000	Australia	\N	0211111111	0211111111	0211111111	\N	e@e.com	\N	\N	\N
118	Ms	Elise	Campbell	\N	\N	19, Grieve Cl	Sydney	NSW	2000	Australia	\N	0211111111	0211111111	0211111111	\N	e@e.com	\N	\N	\N
119	\N	\N	Campbell	\N	\N	19, Grieve Cl	Sydney	\N	2000	Australia	\N	0211111111	0211111111	0211111111	\N	e@e.com	\N	\N	\N
314	\N	\N	Campbell	\N	\N	19, Grieve Cl	Sydney	NSW	2000	Australia		0211111111	0211111111	0211111111	\N	e@e.com	\N	\N	\N
315	\N	\N	Campbell	\N	\N	19, Grieve Cl	Sydney	NSW	2000	Australia		0211111111	0211111111	0211111111	\N	e@e.com	\N	\N	\N
207	\N	\N	Campbell	\N	\N	19, Grieve Cl	Sydney	ACT	2000	Australia	\N	0211111111	0211111111	0211111111	\N	e@e.com	\N	\N	\N
316	\N	\N	Campbell	\N	\N	19, Grieve Cl	Sydney	NSW	2000	Australia		0211111111	0211111111	0211111111	\N	e@e.com	\N	\N	\N
269	\N	\N	Campbell	\N	\N	19, Grieve Cl	Sydney	ACT	2000	Australia	\N	0211111111	0211111111	0211111111	\N	e@e.com	\N	\N	\N
127	\N	\N	Campbell	\N	\N	19, Grieve Cl	Sydney	NSW	2000	Australia	\N	0211111111	0211111111	0211111111	\N	e@e.com	\N	\N	\N
129	\N	\N	Campbell	\N	\N	19, Grieve Cl	Sydney	NSW	2000	Australia	\N	0211111111	0211111111	0211111111	\N	e@e.com	\N	\N	\N
308	\N	\N	Campbell	\N	\N	19, Grieve Cl	Sydney	NSW	2000	Australia		0211111111	0211111111	0211111111	\N	e@e.com	\N	\N	\N
95	\N	\N	Campbell	\N	\N	19, Grieve Cl	Sydney	ACT	2000	Australia	\N	0211111111	0211111111	0211111111	\N	e@e.com	\N	\N	\N
63	Miss.	Natalie	Campbell		\N	19, Grieve Cl	Sydney	NSW	2000	Australia	\N	0211111111	0211111111	0211111111		e@e.com	\N	\N	\N
91	\N	\N	Campbell	\N	\N	19, Grieve Cl	Sydney	ACT	2000	Australia	\N	0211111111	0211111111	0211111111	\N	e@e.com	\N	\N	\N
108	Mr. 	Benjamin	Campbell		\N	19, Grieve Cl	Sydney	NSW	2000	Australia	\N	0211111111	0211111111	0211111111		e@e.com	\N	\N	\N
116	Miss.	Kristina	Campbell		\N	19, Grieve Cl	Sydney	NSW	2000	Australia	\N	0211111111	0211111111	0211111111		e@e.com	\N	\N	\N
97	\N	\N	Campbell	\N	\N	19, Grieve Cl	Sydney	ACT	2000	Australia	\N	0211111111	0211111111	0211111111	\N	e@e.com	\N	\N	\N
184	Ms	Jennifer	Campbell	\N	\N	19, Grieve Cl	Sydney	NSW	2000	Australia	\N	0211111111	0211111111	0211111111	\N	e@e.com	\N	\N	\N
196	Mr	Ty	Campbell	\N	\N	19, Grieve Cl	Sydney	NSW	2000	Australia	\N	0211111111	0211111111	0211111111	\N	e@e.com	\N	\N	\N
197	\N	\N	Campbell	\N	\N	19, Grieve Cl	Sydney	\N	2000	Australia	\N	0211111111	0211111111	0211111111	\N	e@e.com	\N	\N	\N
209	\N	\N	Campbell	\N	\N	19, Grieve Cl	Sydney	ACT	2000	Australia	\N	0211111111	0211111111	0211111111	\N	e@e.com	\N	\N	\N
13	Mrs. 	Rhonda	Campbell		\N	19, Grieve Cl	Sydney	NSW	2000	Australia	\N	0211111111	0211111111	0211111111		e@e.com	\N	\N	\N
16	Mrs. 	Anne	Campbell		\N	19, Grieve Cl	Sydney	NSW	2000	Australia	\N	0211111111	0211111111	0211111111		e@e.com	\N	\N	\N
251	\N	\N	Campbell	\N	\N	19, Grieve Cl	Sydney	\N	2000	Australia	\N	0211111111	0211111111	0211111111	\N	e@e.com	\N	\N	\N
279	\N	\N	Campbell	\N	\N	19, Grieve Cl	Sydney	\N	2000	Australia	\N	0211111111	0211111111	0211111111	\N	e@e.com	\N	\N	\N
296	Ms	Lisa	Campbell	\N	\N	19, Grieve Cl	Sydney	NSW	2000	Australia	\N	0211111111	0211111111	0211111111	\N	e@e.com	\N	\N	\N
225	\N	\N	Campbell	\N	\N	19, Grieve Cl	Sydney	\N	2000	Australia	\N	0211111111	0211111111	0211111111	\N	e@e.com	\N	\N	\N
283	\N	\N	Campbell	\N	\N	19, Grieve Cl	Sydney	ACT	2000	Australia	\N	0211111111	0211111111	0211111111	\N	e@e.com	\N	\N	\N
234	Mr. 	Bradley	Campbell		\N	19, Grieve Cl	Sydney	NSW	2000	Australia	\N	0211111111	0211111111	0211111111		e@e.com	\N	\N	\N
235	\N	\N	Campbell	\N	\N	19, Grieve Cl	Sydney	ACT	2000	Australia	\N	0211111111	0211111111	0211111111	\N	e@e.com	\N	\N	\N
226	Miss.	Belinda	Campbell		\N	19, Grieve Cl	Sydney	NSW	2000	Australia	\N	0211111111	0211111111	0211111111		e@e.com	\N	\N	\N
14	Mrs. 	Leanne	Campbell		\N	19, Grieve Cl	Sydney	NSW	2000	Australia	\N	0211111111	0211111111	0211111111		e@e.com	\N	\N	\N
67	Mr. 	Grant	Campbell		\N	19, Grieve Cl	Sydney	NSW	2000	Australia	\N	0211111111	0211111111	0211111111		e@e.com	\N	\N	\N
64	Miss.	Dianne	Campbell		\N	19, Grieve Cl	Sydney	NSW	2000	Australia	\N	0211111111	0211111111	0211111111		e@e.com	\N	\N	\N
104	Ms	Kristi	Campbell	\N	\N	19, Grieve Cl	Sydney	NSW	2000	Australia	\N	0211111111	0211111111	0211111111	\N	e@e.com	\N	\N	\N
78	Miss.	Leanna	Campbell		\N	19, Grieve Cl	Sydney	NSW	2000	Australia	\N	0211111111	0211111111	0211111111		e@e.com	\N	\N	\N
89	\N	\N	Campbell	\N	\N	19, Grieve Cl	Sydney	ACT	2000	Australia	\N	0211111111	0211111111	0211111111	\N	e@e.com	\N	\N	\N
294	Mr. 	Chantelle	Campbell		\N	19, Grieve Cl	Sydney	NSW	2000	Australia	\N	0211111111	0211111111	0211111111		e@e.com	\N	\N	\N
272	Ms	Cassie	Campbell	\N	\N	19, Grieve Cl	Sydney	NSW	2000	Australia	\N	0211111111	0211111111	0211111111	\N	e@e.com	\N	\N	\N
273	\N	\N	Campbell	\N	\N	19, Grieve Cl	Sydney	\N	2000	Australia	\N	0211111111	0211111111	0211111111	\N	e@e.com	\N	\N	\N
287	\N	\N	Campbell	\N	\N	19, Grieve Cl	Sydney	ACT	2000	Australia	\N	0211111111	0211111111	0211111111	\N	e@e.com	\N	\N	\N
298	Ms	Janelle	Campbell	\N	\N	19, Grieve Cl	Sydney	NSW	2000	Australia	\N	0211111111	0211111111	0211111111	\N	e@e.com	\N	\N	\N
157	\N	\N	Campbell	\N	\N	19, Grieve Cl	Sydney	ACT	2000	Australia	\N	0211111111	0211111111	0211111111	\N	e@e.com	\N	\N	\N
317	\N	\N	Campbell	\N	\N	19, Grieve Cl	Sydney	NSW	2000	Australia		0211111111	0211111111	0211111111	\N	e@e.com	\N	\N	\N
181	\N	\N	Campbell	\N	\N	19, Grieve Cl	Sydney	ACT	2000	Australia	\N	0211111111	0211111111	0211111111	\N	e@e.com	\N	\N	\N
143	\N	\N	Campbell	\N	\N	19, Grieve Cl	Sydney	ACT	2000	Australia	\N	0211111111	0211111111	0211111111	\N	e@e.com	\N	\N	\N
318	Mr. 	Lauren	Campbell		\N	19, Grieve Cl	Sydney	NSW	2000	Australia	\N	0211111111	0211111111	0211111111		e@e.com	\N	\N	\N
243	\N	\N	Campbell	\N	\N	19, Grieve Cl	Sydney	ACT	2000	Australia	\N	0211111111	0211111111	0211111111	\N	e@e.com	\N	\N	\N
62	Miss.	Danita	Campbell		\N	19, Grieve Cl	Sydney	NSW	2000	Australia	\N	0211111111	0211111111	0211111111		e@e.com	\N	\N	\N
56	Mr. 	Simon	Campbell		\N	19, Grieve Cl	Sydney	NSW	2000	Australia	\N	0211111111	0211111111	0211111111		e@e.com	\N	\N	\N
58	Mr. 	Steven	Campbell		\N	19, Grieve Cl	Sydney	NSW	2000	Australia	\N	0211111111	0211111111	0211111111		e@e.com	\N	\N	\N
57	Miss.	Joanna	Campbell		\N	19, Grieve Cl	Sydney	NSW	2000	Australia	\N	0211111111	0211111111	0211111111		e@e.com	\N	\N	\N
49	Miss.	Kayla	Campbell		\N	19, Grieve Cl	Sydney	NSW	2000	Australia	\N	0211111111	0211111111	0211111111		e@e.com	\N	\N	\N
59	Mr. 	Reece	Campbell		\N	19, Grieve Cl	Sydney	NSW	2000	Australia	\N	0211111111	0211111111	0211111111		e@e.com	\N	\N	\N
61	Miss.	Tammy	Campbell		\N	19, Grieve Cl	Sydney	NSW	2000	Australia	\N	0211111111	0211111111	0211111111		e@e.com	\N	\N	\N
90	Mr. 	Brent	Campbell		\N	19, Grieve Cl	Sydney	NSW	2000	Australia	\N	0211111111	0211111111	0211111111		e@e.com	\N	\N	\N
275	\N	\N	Campbell	\N	\N	19, Grieve Cl	Sydney	\N	2000	Australia	\N	0211111111	0211111111	0211111111	\N	e@e.com	\N	\N	\N
306	\N	\N	Campbell	\N	\N	19, Grieve Cl	Sydney	NSW	2000	Australia		0211111111	0211111111	0211111111	\N	e@e.com	\N	\N	\N
257	\N	\N	Campbell	\N	\N	19, Grieve Cl	Sydney	ACT	2000	Australia	\N	0211111111	0211111111	0211111111	\N	e@e.com	\N	\N	\N
117	\N	\N	Campbell	\N	\N	19, Grieve Cl	Sydney	ACT	2000	Australia	\N	0211111111	0211111111	0211111111	\N	e@e.com	\N	\N	\N
300	\N	\N	Campbell	\N	\N	19, Grieve Cl	Sydney	NSW	2000	Australia		0211111111	0211111111	0211111111	\N	e@e.com	\N	\N	\N
301	\N	\N	Campbell	\N	\N	19, Grieve Cl	Sydney	NSW	2000	Australia		0211111111	0211111111	0211111111	\N	e@e.com	\N	\N	\N
303	\N	\N	Campbell	\N	\N	19, Grieve Cl	Sydney	NSW	2000	Australia		0211111111	0211111111	0211111111	\N	e@e.com	\N	\N	\N
305	\N	\N	Campbell	\N	\N	19, Grieve Cl	Sydney	NSW	2000	Australia		0211111111	0211111111	0211111111	\N	e@e.com	\N	\N	\N
228	Mr. 	Preston	Campbell		\N	19, Grieve Cl	Sydney	NSW	2000	Australia	\N	0211111111	0211111111	0211111111		e@e.com	\N	\N	\N
165	\N	\N	Campbell	\N	\N	19, Grieve Cl	Sydney	ACT	2000	Australia	\N	0211111111	0211111111	0211111111	\N	e@e.com	\N	\N	\N
310	\N	\N	Campbell	\N	\N	19, Grieve Cl	Sydney	NSW	2000	Australia		0211111111	0211111111	0211111111	\N	e@e.com	\N	\N	\N
\.


--
-- Data for Name: externalorganizations; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY externalorganizations (id, contactid, contactpersonid, serviceareaid, name, paymentoption, servicearea, preferedsupplier, status) FROM stdin;
\.


--
-- Data for Name: filedata; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY filedata (id, filename, contenttype, filetype, datecreated, staffid, studentid, sneedid, vehicleid, refid, data, thumbnail) FROM stdin;
\.


--
-- Data for Name: fundingsources; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY fundingsources (id, fundingcode, fundingdescription, fundingtype, status) FROM stdin;
9	22100		PSO	Active
10	22513		COMMUNITY PARTICIPATION	Active
11	22514		COMMUNITY PARTICIPATION INDIVIDUAL	Active
12	23036		COMMUNITY PARTICIPATION Life Choices Wyong	Active
13	25391		COMMUNITY PARTICIPATION Life Choices Kariong	Active
14	25385		TRANSITION TO WORK	Active
\.


--
-- Data for Name: groupedstudents; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY groupedstudents (id, groupid, studentid, status, remarks, pickup, dropoff) FROM stdin;
135	25	116	\N	\N	\N	\N
98	19	118	\N	\N	\N	\N
50	10	56	\N	\N	\N	\N
31	8	73	\N	\N	\N	\N
49	10	89	\N	\N	\N	\N
1	1	23	\N	\N	\N	\N
2	1	4	\N	\N	\N	\N
136	25	33	\N	\N	\N	\N
27	7	104	\N	\N	\N	\N
141	25	104	\N	\N	\N	\N
3	4	23	\N	\N	\N	\N
13	5	66	\N	\N	\N	\N
138	25	94	\N	\N	\N	\N
137	25	65	\N	\N	\N	\N
140	25	13	\N	\N	\N	\N
8	4	37	\N	\N	\N	\N
5	4	24	\N	\N	\N	\N
142	25	75	\N	\N	\N	\N
66	12	99	\N	\N	\N	\N
26	7	48	\N	\N	\N	\N
7	4	2	\N	\N	\N	\N
85	16	69	\N	\N	\N	\N
86	16	36	\N	\N	\N	\N
105	20	16	\N	\N	\N	\N
68	12	82	\N	\N	\N	\N
6	4	36	\N	\N	\N	\N
33	8	46	\N	\N	\N	\N
112	21	68	\N	\N	\N	\N
51	10	16	\N	\N	\N	\N
44	10	74	\N	\N	\N	\N
45	10	5	\N	\N	\N	\N
9	4	15	\N	\N	\N	\N
47	10	111	\N	\N	\N	\N
48	10	53	\N	\N	\N	\N
143	25	90	\N	\N	\N	\N
10	4	25	\N	\N	\N	\N
123	23	115	\N	\N	\N	\N
144	26	113	\N	\N	\N	\N
65	12	114	\N	\N	\N	\N
46	10	47	\N	\N	\N	\N
14	5	68	\N	\N	\N	\N
32	8	49	\N	\N	\N	\N
16	5	81	\N	\N	\N	\N
15	5	90	\N	\N	\N	\N
34	8	51	\N	\N	\N	\N
12	5	17	\N	\N	\N	\N
103	19	30	\N	\N	\N	\N
95	18	58	\N	\N	\N	\N
24	6	18	\N	\N	\N	\N
125	23	42	\N	\N	\N	\N
70	12	40	\N	\N	\N	\N
58	12	98	\N	\N	\N	\N
99	19	70	\N	\N	\N	\N
121	22	10	\N	\N	\N	\N
76	13	37	\N	\N	\N	\N
55	11	9	\N	\N	\N	\N
83	15	62	\N	\N	\N	\N
11	4	63	\N	\N	\N	\N
126	23	43	\N	\N	\N	\N
87	16	111	\N	\N	\N	\N
131	24	22	\N	\N	\N	\N
28	7	34	\N	\N	\N	\N
54	11	38	\N	\N	\N	\N
63	12	33	\N	\N	\N	\N
36	9	77	\N	\N	\N	\N
134	24	40	\N	\N	\N	\N
72	13	15	\N	\N	\N	\N
119	22	34	\N	\N	\N	\N
89	16	73	\N	\N	\N	\N
113	21	50	\N	\N	\N	\N
43	9	62	\N	\N	\N	\N
115	21	18	\N	\N	\N	\N
71	13	86	\N	\N	\N	\N
120	22	32	\N	\N	\N	\N
116	21	101	\N	\N	\N	\N
4	4	4	\N	\N	\N	\N
109	20	25	\N	\N	\N	\N
114	21	49	\N	\N	\N	\N
84	15	14	\N	\N	\N	\N
94	18	97	\N	\N	\N	\N
96	18	106	\N	\N	\N	\N
92	18	4	\N	\N	\N	\N
122	22	28	\N	\N	\N	\N
62	12	19	\N	\N	\N	\N
93	18	113	\N	\N	\N	\N
117	21	48	\N	\N	\N	\N
91	17	59	\N	\N	\N	\N
35	9	29	\N	\N	\N	\N
52	11	6	\N	\N	\N	\N
60	12	32	\N	\N	\N	\N
101	19	93	\N	\N	\N	\N
69	12	93	\N	\N	\N	\N
59	12	102	\N	\N	\N	\N
74	13	102	\N	\N	\N	\N
30	7	94	\N	\N	\N	\N
37	9	14	\N	\N	\N	\N
67	12	65	\N	\N	\N	\N
38	9	92	\N	\N	\N	\N
124	23	80	\N	\N	\N	\N
57	12	80	\N	\N	\N	\N
90	17	98	\N	\N	\N	\N
61	12	70	\N	\N	\N	\N
29	7	28	\N	\N	\N	\N
53	11	30	\N	\N	\N	\N
78	14	5	\N	\N	\N	\N
118	22	17	\N	\N	\N	\N
145	26	112	\N	\N	\N	\N
104	20	51	\N	\N	\N	\N
127	23	63	\N	\N	\N	\N
77	14	85	\N	\N	\N	\N
82	15	67	\N	\N	\N	\N
100	19	112	\N	\N	\N	\N
39	9	60	\N	\N	\N	\N
73	13	45	\N	\N	\N	\N
80	14	27	\N	\N	\N	\N
102	19	91	\N	\N	\N	\N
79	14	92	\N	\N	\N	\N
88	16	108	\N	\N	\N	\N
75	13	81	\N	\N	\N	\N
40	9	26	\N	\N	\N	\N
97	18	44	\N	\N	\N	\N
41	9	84	\N	\N	\N	\N
133	24	9	\N	\N	\N	\N
110	20	24	\N	\N	\N	\N
111	20	105	\N	\N	\N	\N
81	15	55	\N	\N	\N	\N
106	20	35	\N	\N	\N	\N
130	24	38	\N	\N	\N	\N
64	12	21	\N	\N	\N	\N
23	6	59	\N	\N	\N	\N
17	6	86	\N	\N	\N	\N
132	24	8	\N	\N	\N	\N
22	6	87	\N	\N	\N	\N
42	9	55	\N	\N	\N	\N
107	20	117	\N	\N	\N	\N
108	20	23	\N	\N	\N	\N
153	27	39	\N	\N	\N	\N
154	27	42	\N	\N	\N	\N
155	27	95	\N	\N	\N	\N
172	31	53	\N	\N	\N	\N
175	31	29	\N	\N	\N	\N
176	31	62	\N	\N	\N	\N
178	32	116	\N	\N	\N	\N
188	33	106	\N	\N	\N	\N
199	36	22	\N	\N	\N	\N
216	38	97	\N	\N	\N	\N
221	38	29	\N	\N	\N	\N
238	41	66	\N	\N	\N	\N
245	44	68	\N	\N	\N	\N
250	44	110	\N	\N	\N	\N
257	46	115	\N	\N	\N	\N
259	46	7	\N	\N	\N	\N
261	46	42	\N	\N	\N	\N
277	49	6	\N	\N	\N	\N
275	49	22	\N	\N	\N	\N
297	53	66	\N	\N	\N	\N
298	53	6	\N	\N	\N	\N
278	50	102	\N	\N	\N	\N
148	26	46	\N	\N	\N	\N
240	42	116	\N	\N	\N	\N
232	39	47	\N	\N	\N	\N
210	37	15	\N	\N	\N	\N
180	32	37	\N	\N	\N	\N
285	50	37	\N	\N	\N	\N
287	50	50	\N	\N	\N	\N
231	39	89	\N	\N	\N	\N
207	37	96	\N	\N	\N	\N
284	50	96	\N	\N	\N	\N
263	47	118	\N	\N	\N	\N
198	36	87	\N	\N	\N	\N
276	49	87	\N	\N	\N	\N
177	32	86	\N	\N	\N	\N
149	26	47	\N	\N	\N	\N
229	39	56	\N	\N	\N	\N
228	39	24	\N	\N	\N	\N
295	52	24	\N	\N	\N	\N
224	39	101	\N	\N	\N	\N
293	52	101	\N	\N	\N	\N
243	42	105	\N	\N	\N	\N
195	35	77	\N	\N	\N	\N
218	38	77	\N	\N	\N	\N
186	33	49	\N	\N	\N	\N
222	38	14	\N	\N	\N	\N
179	32	36	\N	\N	\N	\N
181	32	111	\N	\N	\N	\N
302	54	116	\N	\N	\N	\N
146	26	56	\N	\N	\N	\N
230	39	11	\N	\N	\N	\N
204	37	64	\N	\N	\N	\N
234	39	73	\N	\N	\N	\N
208	37	82	\N	\N	\N	\N
227	39	82	\N	\N	\N	\N
147	26	11	\N	\N	\N	\N
215	38	5	\N	\N	\N	\N
265	47	106	\N	\N	\N	\N
288	50	19	\N	\N	\N	\N
152	27	48	\N	\N	\N	\N
237	41	48	\N	\N	\N	\N
167	30	10	\N	\N	\N	\N
249	44	83	\N	\N	\N	\N
244	43	59	\N	\N	\N	\N
299	53	59	\N	\N	\N	\N
157	27	104	\N	\N	\N	\N
168	30	30	\N	\N	\N	\N
209	37	93	\N	\N	\N	\N
260	46	93	\N	\N	\N	\N
202	37	33	\N	\N	\N	\N
156	27	94	\N	\N	\N	\N
205	37	65	\N	\N	\N	\N
283	50	65	\N	\N	\N	\N
206	37	80	\N	\N	\N	\N
258	46	80	\N	\N	\N	\N
203	37	98	\N	\N	\N	\N
282	50	98	\N	\N	\N	\N
279	50	70	\N	\N	\N	\N
166	30	114	\N	\N	\N	\N
233	39	28	\N	\N	\N	\N
161	28	107	\N	\N	\N	\N
187	33	108	\N	\N	\N	\N
219	38	108	\N	\N	\N	\N
184	33	35	\N	\N	\N	\N
246	44	35	\N	\N	\N	\N
193	35	117	\N	\N	\N	\N
213	38	117	\N	\N	\N	\N
226	39	113	\N	\N	\N	\N
291	50	63	\N	\N	\N	\N
254	45	85	\N	\N	\N	\N
173	31	67	\N	\N	\N	\N
163	29	83	\N	\N	\N	\N
300	53	43	\N	\N	\N	\N
159	28	43	\N	\N	\N	\N
235	40	13	\N	\N	\N	\N
256	45	27	\N	\N	\N	\N
269	48	39	\N	\N	\N	\N
194	35	92	\N	\N	\N	\N
255	45	92	\N	\N	\N	\N
220	38	10	\N	\N	\N	\N
183	32	91	\N	\N	\N	\N
211	37	90	\N	\N	\N	\N
252	44	58	\N	\N	\N	\N
162	29	32	\N	\N	\N	\N
158	28	8	\N	\N	\N	\N
196	35	44	\N	\N	\N	\N
241	42	8	\N	\N	\N	\N
164	29	28	\N	\N	\N	\N
253	44	79	\N	\N	\N	\N
170	31	55	\N	\N	\N	\N
214	38	55	\N	\N	\N	\N
169	31	84	\N	\N	\N	\N
182	32	74	\N	\N	\N	\N
174	31	26	\N	\N	\N	\N
272	48	95	\N	\N	\N	\N
290	50	52	\N	\N	\N	\N
270	48	36	\N	\N	\N	\N
248	44	114	\N	\N	\N	\N
200	37	17	\N	\N	\N	\N
236	41	17	\N	\N	\N	\N
267	48	64	\N	\N	\N	\N
247	44	21	\N	\N	\N	\N
286	50	109	\N	\N	\N	\N
251	44	9	\N	\N	\N	\N
185	33	69	\N	\N	\N	\N
223	39	69	\N	\N	\N	\N
189	34	118	\N	\N	\N	\N
197	35	60	\N	\N	\N	\N
171	31	23	\N	\N	\N	\N
225	39	23	\N	\N	\N	\N
192	34	66	\N	\N	\N	\N
262	47	51	\N	\N	\N	\N
264	47	4	\N	\N	\N	\N
160	28	79	\N	\N	\N	\N
165	30	21	\N	\N	\N	\N
268	48	99	\N	\N	\N	\N
273	48	45	\N	\N	\N	\N
315	56	16	\N	\N	\N	\N
377	69	96	\N	\N	5	-1
386	71	111	\N	\N	3	-1
379	69	25	\N	\N	5	-1
369	68	87	\N	\N	12	13
343	61	49	\N	\N	-1	3
329	2	37	\N	\N	-1	3
345	61	111	\N	\N	-1	3
217	38	67	\N	\N	\N	\N
327	2	47	\N	\N	-1	3
328	2	15	\N	\N	-1	3
289	50	18	\N	\N	\N	\N
294	52	18	\N	\N	\N	\N
346	61	36	\N	\N	-1	3
347	61	11	\N	\N	-1	3
348	61	64	\N	\N	-1	12
349	61	73	\N	\N	-1	3
319	56	82	\N	\N	\N	\N
280	50	32	\N	\N	\N	\N
334	58	87	\N	\N	13	12
292	52	32	\N	\N	\N	\N
330	58	50	\N	\N	-1	12
331	58	89	\N	\N	-1	3
333	58	18	\N	\N	13	12
316	56	86	\N	\N	\N	\N
320	56	56	\N	\N	\N	\N
313	55	106	\N	\N	\N	\N
332	58	101	\N	\N	-1	12
323	57	93	\N	\N	\N	\N
325	2	51	\N	\N	-1	3
318	56	96	\N	\N	\N	\N
317	56	4	\N	\N	\N	\N
281	50	33	\N	\N	\N	\N
351	63	59	\N	\N	9	12
350	63	83	\N	\N	-1	12
356	64	93	\N	\N	9	7
353	64	34	\N	\N	9	4
352	64	104	\N	\N	9	4
340	59	4	\N	\N	-1	3
335	59	86	\N	\N	-1	3
336	59	56	\N	\N	-1	12
337	59	25	\N	\N	-1	3
338	59	24	\N	\N	-1	3
339	59	96	\N	\N	-1	3
310	55	25	\N	\N	\N	\N
311	55	77	\N	\N	\N	\N
342	60	14	\N	\N	-1	6
341	60	77	\N	\N	-1	6
296	52	73	\N	\N	\N	\N
354	64	99	\N	\N	9	7
355	64	32	\N	\N	9	7
357	64	33	\N	\N	9	7
358	64	102	\N	\N	9	7
359	64	94	\N	\N	9	4
360	64	65	\N	\N	9	7
322	57	98	\N	\N	\N	\N
370	68	18	\N	\N	12	-1
363	65	70	\N	\N	-1	7
361	65	80	\N	\N	-1	7
362	65	98	\N	\N	-1	7
371	68	50	\N	\N	12	-1
364	66	28	\N	\N	-1	4
365	66	48	\N	\N	-1	4
372	68	89	\N	\N	3	-1
368	67	47	\N	\N	3	-1
366	67	51	\N	\N	3	-1
367	67	37	\N	\N	3	-1
373	68	101	\N	\N	12	-1
388	71	41	\N	\N	12	-1
378	69	5	\N	\N	5	-1
382	70	77	\N	\N	6	-1
381	70	14	\N	\N	6	-1
390	72	82	\N	\N	7	-1
389	72	19	\N	\N	7	-1
392	73	28	\N	\N	4	-1
391	73	48	\N	\N	4	-1
380	69	86	\N	\N	5	-1
374	69	4	\N	\N	5	-1
375	69	24	\N	\N	5	-1
376	69	56	\N	\N	5	-1
387	71	49	\N	\N	3	-1
383	71	73	\N	\N	3	-1
384	71	11	\N	\N	3	-1
385	71	36	\N	\N	3	-1
393	75	59	\N	\N	12	9
394	75	83	\N	\N	12	-1
402	76	65	\N	\N	7	9
395	76	102	\N	\N	7	9
396	76	33	\N	\N	7	9
314	55	14	\N	\N	\N	\N
397	76	93	\N	\N	7	9
398	76	32	\N	\N	4	9
405	77	98	\N	\N	7	-1
403	77	70	\N	\N	7	-1
404	77	80	\N	\N	7	-1
399	76	99	\N	\N	4	9
400	76	34	\N	\N	4	9
401	76	104	\N	\N	4	9
326	2	63	\N	\N	-1	3
312	55	108	\N	\N	\N	\N
308	55	117	\N	\N	\N	\N
321	56	91	\N	\N	\N	\N
411	78	113	\N	\N	-1	3
406	78	105	\N	\N	-1	3
407	78	117	\N	\N	-1	3
242	42	90	\N	\N	\N	\N
344	61	41	\N	\N	-1	3
239	42	2	\N	\N	\N	\N
408	78	108	\N	\N	-1	12
324	57	30	\N	\N	\N	\N
201	37	2	\N	\N	\N	\N
304	54	53	\N	\N	\N	\N
305	54	97	\N	\N	\N	\N
303	54	5	\N	\N	\N	\N
306	54	13	\N	\N	\N	\N
307	54	44	\N	\N	\N	\N
409	78	51	\N	\N	-1	3
410	78	35	\N	\N	-1	3
433	83	92	\N	\N	14	7
432	83	27	\N	\N	-1	7
434	83	85	\N	\N	-1	7
494	96	117	\N	\N	-1	6
417	79	50	\N	\N	-1	9
412	79	15	\N	\N	-1	6
413	79	37	\N	\N	-1	6
414	79	14	\N	\N	-1	6
415	79	49	\N	\N	-1	9
416	79	63	\N	\N	-1	10
461	88	49	\N	\N	9	-1
456	88	14	\N	\N	6	-1
418	80	24	\N	\N	-1	3
419	80	25	\N	\N	-1	3
420	80	4	\N	\N	-1	3
421	80	58	\N	\N	-1	3
457	88	37	\N	\N	6	-1
424	81	73	\N	\N	-1	3
422	81	111	\N	\N	-1	12
423	81	36	\N	\N	-1	12
458	88	15	\N	\N	6	-1
459	88	50	\N	\N	9	-1
460	88	63	\N	\N	10	-1
449	86	93	\N	\N	9	4
440	86	81	\N	\N	9	7
441	86	38	\N	\N	9	4
431	82	43	\N	\N	-1	6
426	82	94	\N	\N	-1	7
427	82	86	\N	\N	-1	7
428	82	67	\N	\N	-1	6
429	82	112	\N	\N	-1	4
430	82	13	\N	\N	-1	7
495	96	77	\N	\N	-1	6
436	84	10	\N	\N	-1	9
435	84	45	\N	\N	-1	6
442	86	75	\N	\N	9	7
439	85	48	\N	\N	-1	9
437	85	91	\N	\N	-1	4
438	85	28	\N	\N	-1	9
443	86	104	\N	\N	9	7
444	86	33	\N	\N	9	7
445	86	102	\N	\N	9	7
446	86	90	\N	\N	9	7
447	86	65	\N	\N	9	7
448	86	70	\N	\N	9	4
502	97	35	\N	\N	-1	3
465	89	24	\N	\N	3	-1
462	89	58	\N	\N	3	-1
463	89	4	\N	\N	3	-1
464	89	25	\N	\N	3	-1
468	90	111	\N	\N	12	-1
466	90	73	\N	\N	3	-1
467	90	36	\N	\N	12	-1
469	90	41	\N	\N	3	-1
498	96	15	\N	\N	-1	6
493	95	90	\N	\N	7	9
478	92	27	\N	\N	7	14
476	92	92	\N	\N	7	-1
477	92	85	\N	\N	7	-1
450	87	35	\N	\N	3	-1
451	87	51	\N	\N	3	-1
452	87	108	\N	\N	12	-1
453	87	117	\N	\N	3	-1
454	87	105	\N	\N	3	-1
455	87	113	\N	\N	3	-1
480	93	45	\N	\N	6	-1
479	93	10	\N	\N	9	-1
475	91	94	\N	\N	7	-1
470	91	43	\N	\N	6	-1
471	91	13	\N	\N	7	-1
472	91	112	\N	\N	4	-1
473	91	67	\N	\N	6	-1
474	91	86	\N	\N	7	-1
484	95	93	\N	\N	4	9
485	95	102	\N	\N	7	9
483	94	28	\N	\N	9	-1
481	94	48	\N	\N	9	-1
482	94	91	\N	\N	4	-1
486	95	33	\N	\N	7	9
487	95	104	\N	\N	7	9
488	95	75	\N	\N	7	9
489	95	38	\N	\N	4	9
490	95	81	\N	\N	7	9
491	95	65	\N	\N	7	9
492	95	70	\N	\N	4	9
499	96	37	\N	\N	-1	6
497	96	8	\N	\N	-1	4
496	96	44	\N	\N	-1	6
506	97	55	\N	\N	-1	3
500	96	79	\N	\N	-1	4
507	97	63	\N	\N	-1	3
503	97	105	\N	\N	-1	3
504	97	108	\N	\N	-1	3
505	97	113	\N	\N	-1	3
513	98	13	\N	\N	-1	3
501	97	47	\N	\N	-1	3
508	98	11	\N	\N	-1	3
509	98	56	\N	\N	-1	3
510	98	112	\N	\N	-1	3
511	98	67	\N	\N	-1	3
512	98	49	\N	\N	-1	3
518	99	82	\N	\N	-1	7
514	99	74	\N	\N	-1	6
515	99	111	\N	\N	-1	6
516	99	36	\N	\N	-1	6
517	99	96	\N	\N	-1	7
519	100	2	\N	\N	-1	9
522	100	109	\N	\N	-1	4
425	81	41	\N	\N	-1	3
566	108	82	\N	\N	7	-1
567	108	64	\N	\N	7	-1
520	100	86	\N	\N	-1	6
521	100	43	\N	\N	-1	9
523	100	19	\N	\N	-1	9
524	100	58	\N	\N	-1	9
600	114	67	\N	\N	-1	16
596	114	117	\N	\N	-1	16
550	105	8	\N	\N	4	-1
529	101	48	\N	\N	-1	9
525	101	28	\N	\N	-1	6
526	101	83	\N	\N	-1	9
527	101	91	\N	\N	-1	6
528	101	10	\N	\N	-1	4
545	105	79	\N	\N	6	-1
546	105	37	\N	\N	6	-1
531	102	26	\N	\N	-1	3
212	38	84	\N	\N	\N	\N
530	102	84	\N	\N	-1	3
547	105	15	\N	\N	6	-1
548	105	77	\N	\N	6	-1
549	105	117	\N	\N	4	-1
593	112	102	\N	\N	7	9
584	112	33	\N	\N	7	9
585	112	65	\N	\N	7	9
586	112	93	\N	\N	7	9
587	112	17	\N	\N	7	9
588	112	90	\N	\N	7	9
556	106	47	\N	\N	3	-1
551	106	55	\N	\N	3	-1
552	106	113	\N	\N	3	-1
553	106	108	\N	\N	3	-1
554	106	105	\N	\N	3	-1
555	106	35	\N	\N	3	-1
569	109	58	\N	\N	9	-1
570	109	19	\N	\N	9	-1
572	109	43	\N	\N	9	-1
573	109	86	\N	\N	6	-1
574	109	2	\N	\N	9	-1
535	103	90	\N	\N	9	7
562	107	11	\N	\N	3	-1
537	103	93	\N	\N	9	7
538	103	65	\N	\N	9	7
539	103	33	\N	\N	9	7
540	103	102	\N	\N	9	7
541	103	30	\N	\N	9	4
532	103	52	\N	\N	9	7
533	103	114	\N	\N	9	4
557	107	13	\N	\N	3	-1
534	103	21	\N	\N	9	4
536	103	17	\N	\N	9	7
558	107	49	\N	\N	3	-1
544	104	64	\N	\N	15	7
542	104	98	\N	\N	15	7
543	104	80	\N	\N	15	7
589	112	21	\N	\N	7	9
559	107	67	\N	\N	3	-1
560	107	112	\N	\N	3	-1
561	107	56	\N	\N	3	-1
590	112	114	\N	\N	4	9
591	112	52	\N	\N	7	9
592	112	30	\N	\N	4	9
595	113	64	\N	\N	7	9
594	113	98	\N	\N	7	9
568	108	74	\N	\N	6	-1
563	108	111	\N	\N	6	-1
564	108	36	\N	\N	6	-1
565	108	96	\N	\N	7	-1
581	110	48	\N	\N	9	-1
577	110	10	\N	\N	4	-1
578	110	91	\N	\N	6	-1
579	110	83	\N	\N	9	-1
580	110	28	\N	\N	6	-1
583	111	84	\N	\N	3	-1
582	111	26	\N	\N	3	-1
597	114	14	\N	\N	-1	16
598	114	108	\N	\N	-1	16
599	114	77	\N	\N	-1	16
604	115	47	\N	\N	-1	17
605	115	69	\N	\N	3	17
571	109	109	\N	\N	4	-1
616	117	96	\N	\N	-1	7
609	116	105	\N	\N	-1	6
610	116	63	\N	\N	-1	7
606	115	2	\N	\N	3	17
601	115	51	\N	\N	-1	17
602	115	89	\N	\N	-1	17
603	115	77	\N	\N	-1	17
611	116	35	\N	\N	-1	10
614	116	50	\N	\N	-1	7
612	116	79	\N	\N	-1	10
613	116	37	\N	\N	-1	7
607	116	2	\N	\N	-1	6
608	116	8	\N	\N	-1	6
619	117	109	\N	\N	-1	7
618	117	9	\N	\N	15	10
615	117	19	\N	\N	-1	7
617	117	43	\N	\N	-1	4
624	118	56	\N	\N	-1	16
620	118	82	\N	\N	-1	17
621	118	24	\N	\N	-1	17
622	118	11	\N	\N	-1	17
623	118	4	\N	\N	-1	3
629	119	78	\N	\N	-1	9
626	119	64	\N	\N	-1	9
627	119	58	\N	\N	-1	10
628	119	36	\N	\N	-1	9
301	54	84	\N	\N	\N	\N
633	120	48	\N	\N	-1	9
625	118	41	\N	\N	-1	3
129	24	21	\N	\N	\N	\N
634	120	45	\N	\N	-1	9
631	120	91	\N	\N	-1	9
632	120	83	\N	\N	-1	10
637	121	84	\N	\N	-1	16
635	121	10	\N	\N	-1	16
636	121	28	\N	\N	-1	17
640	122	92	\N	\N	14	18
638	122	27	\N	\N	-1	18
639	122	85	\N	\N	-1	18
730	140	73	\N	\N	-1	10
671	128	109	\N	\N	7	-1
667	128	9	\N	\N	10	15
668	128	19	\N	\N	9	-1
669	128	96	\N	\N	7	-1
670	128	43	\N	\N	4	-1
672	129	41	\N	\N	3	-1
720	139	41	\N	\N	-1	10
649	124	70	\N	\N	-1	18
647	124	98	\N	\N	-1	18
648	124	65	\N	\N	-1	18
719	139	69	\N	\N	-1	10
654	125	67	\N	\N	16	-1
650	125	77	\N	\N	16	-1
651	125	108	\N	\N	16	-1
652	125	14	\N	\N	16	-1
653	125	117	\N	\N	16	-1
658	126	47	\N	\N	17	-1
655	126	51	\N	\N	17	-1
656	126	89	\N	\N	17	-1
657	126	77	\N	\N	17	-1
724	139	19	\N	\N	-1	10
707	137	117	\N	\N	-1	3
708	137	77	\N	\N	-1	3
677	129	82	\N	\N	17	-1
673	129	4	\N	\N	3	-1
674	129	56	\N	\N	16	-1
675	129	11	\N	\N	17	-1
676	129	24	\N	\N	17	-1
698	134	38	\N	\N	6	9
693	134	52	\N	\N	7	9
694	134	102	\N	\N	7	9
681	130	64	\N	\N	9	-1
678	130	78	\N	\N	9	-1
679	130	36	\N	\N	9	-1
680	130	58	\N	\N	10	-1
666	127	2	\N	\N	6	-1
659	127	105	\N	\N	6	-1
660	127	63	\N	\N	7	-1
661	127	35	\N	\N	10	-1
662	127	79	\N	\N	10	-1
663	127	37	\N	\N	7	-1
664	127	50	\N	\N	7	-1
665	127	8	\N	\N	6	-1
695	134	33	\N	\N	7	9
696	134	32	\N	\N	7	9
697	134	18	\N	\N	7	9
709	137	14	\N	\N	-1	3
701	135	70	\N	\N	18	-1
686	131	91	\N	\N	9	-1
682	131	68	\N	\N	10	9
683	131	45	\N	\N	9	-1
684	131	48	\N	\N	9	-1
685	131	83	\N	\N	10	-1
699	135	98	\N	\N	18	-1
689	132	10	\N	\N	16	-1
687	132	84	\N	\N	16	-1
688	132	28	\N	\N	17	-1
700	135	65	\N	\N	18	-1
692	133	92	\N	\N	18	14
690	133	27	\N	\N	18	-1
691	133	85	\N	\N	18	-1
710	137	108	\N	\N	-1	3
718	138	56	\N	\N	-1	3
712	138	67	\N	\N	-1	3
706	136	18	\N	\N	-1	10
702	136	51	\N	\N	-1	10
703	136	35	\N	\N	-1	10
704	136	47	\N	\N	-1	10
705	136	79	\N	\N	-1	10
713	138	25	\N	\N	-1	3
714	138	86	\N	\N	-1	3
715	138	82	\N	\N	-1	3
716	138	96	\N	\N	-1	3
717	138	4	\N	\N	-1	3
725	139	23	\N	\N	-1	10
726	140	2	\N	\N	-1	10
721	139	43	\N	\N	-1	10
727	140	58	\N	\N	-1	10
722	139	109	\N	\N	-1	10
723	139	111	\N	\N	-1	10
733	141	83	\N	\N	-1	10
728	140	112	\N	\N	-1	10
729	140	24	\N	\N	-1	10
731	141	10	\N	\N	-1	10
732	141	70	\N	\N	-1	10
734	142	84	\N	\N	-1	3
735	142	91	\N	\N	-1	3
737	143	98	\N	\N	-1	18
736	143	30	\N	\N	-1	18
738	144	18	\N	\N	10	-1
711	137	60	\N	\N	-1	3
739	144	79	\N	\N	10	-1
743	144	66	\N	\N	10	-1
740	144	47	\N	\N	10	-1
741	144	35	\N	\N	10	-1
742	144	51	\N	\N	10	-1
831	42	38	\N	\N	\N	\N
832	42	30	\N	\N	\N	\N
833	16	35	\N	\N	\N	\N
834	16	16	\N	\N	\N	\N
748	145	117	\N	\N	3	-1
835	16	2	\N	\N	\N	\N
745	145	108	\N	\N	3	-1
746	145	14	\N	\N	3	-1
747	145	77	\N	\N	3	-1
836	16	24	\N	\N	\N	\N
837	16	46	\N	\N	\N	\N
838	18	64	\N	\N	\N	\N
839	20	41	\N	\N	\N	\N
840	22	21	\N	\N	\N	\N
780	153	38	\N	\N	9	6
775	153	18	\N	\N	9	7
776	153	32	\N	\N	9	7
777	153	33	\N	\N	9	7
778	153	102	\N	\N	9	7
779	153	52	\N	\N	9	7
781	6	43	\N	\N	\N	\N
20	6	101	\N	\N	\N	\N
18	6	11	\N	\N	\N	\N
755	146	67	\N	\N	3	-1
749	146	56	\N	\N	3	-1
750	146	4	\N	\N	3	-1
751	146	96	\N	\N	3	-1
752	146	82	\N	\N	3	-1
753	146	86	\N	\N	3	-1
754	146	25	\N	\N	3	-1
19	6	50	\N	\N	\N	\N
744	145	60	\N	\N	3	-1
21	6	83	\N	\N	\N	\N
25	6	96	\N	\N	\N	\N
782	1	64	\N	\N	\N	\N
783	34	58	\N	\N	\N	\N
191	34	18	\N	\N	\N	\N
190	34	19	\N	\N	\N	\N
266	47	44	\N	\N	\N	\N
784	47	41	\N	\N	\N	\N
761	147	41	\N	\N	10	-1
762	147	69	\N	\N	10	-1
756	147	23	\N	\N	10	-1
757	147	19	\N	\N	10	-1
758	147	111	\N	\N	10	-1
759	147	109	\N	\N	10	-1
760	147	43	\N	\N	10	-1
825	11	120	\N	\N	\N	\N
767	148	2	\N	\N	10	-1
763	148	24	\N	\N	10	-1
764	148	58	\N	\N	10	-1
765	148	112	\N	\N	10	-1
766	148	73	\N	\N	10	-1
770	149	10	\N	\N	10	-1
768	149	83	\N	\N	10	-1
769	149	70	\N	\N	10	-1
772	150	84	\N	\N	3	-1
771	150	91	\N	\N	3	-1
774	152	98	\N	\N	18	-1
773	152	30	\N	\N	18	-1
309	55	67	\N	\N	\N	\N
841	24	30	\N	\N	\N	\N
824	24	120	\N	\N	\N	\N
851	55	62	\N	\N	\N	\N
791	51	10	\N	\N	\N	\N
843	26	63	\N	\N	\N	\N
151	26	105	\N	\N	\N	\N
150	26	13	\N	\N	\N	\N
842	26	35	\N	\N	\N	\N
844	28	64	\N	\N	\N	\N
845	29	2	\N	\N	\N	\N
846	30	109	\N	\N	\N	\N
847	48	91	\N	\N	\N	\N
274	48	75	\N	\N	\N	\N
271	48	78	\N	\N	\N	\N
848	54	16	\N	\N	\N	\N
785	51	68	\N	\N	\N	\N
786	51	69	\N	\N	\N	\N
787	51	70	\N	\N	\N	\N
790	51	9	\N	\N	\N	\N
792	51	79	\N	\N	\N	\N
794	51	19	\N	\N	\N	\N
796	51	21	\N	\N	\N	\N
797	51	80	\N	\N	\N	\N
798	51	83	\N	\N	\N	\N
799	51	23	\N	\N	\N	\N
801	51	95	\N	\N	\N	\N
802	51	26	\N	\N	\N	\N
803	51	31	\N	\N	\N	\N
804	51	35	\N	\N	\N	\N
807	51	33	\N	\N	\N	\N
808	51	42	\N	\N	\N	\N
809	51	110	\N	\N	\N	\N
810	51	43	\N	\N	\N	\N
811	51	111	\N	\N	\N	\N
812	51	40	\N	\N	\N	\N
813	51	109	\N	\N	\N	\N
814	51	107	\N	\N	\N	\N
815	51	47	\N	\N	\N	\N
816	51	45	\N	\N	\N	\N
817	51	51	\N	\N	\N	\N
818	51	114	\N	\N	\N	\N
819	51	52	\N	\N	\N	\N
820	51	112	\N	\N	\N	\N
822	51	58	\N	\N	\N	\N
823	51	120	\N	\N	\N	\N
826	9	97	\N	\N	\N	\N
828	8	16	\N	\N	\N	\N
829	8	31	\N	\N	\N	\N
827	8	41	\N	\N	\N	\N
830	5	102	\N	\N	\N	\N
849	55	84	\N	\N	\N	\N
850	55	44	\N	\N	\N	\N
\.


--
-- Data for Name: holidays; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY holidays (id, calendarid, date, holidaytype, remarks) FROM stdin;
\.


--
-- Data for Name: hoursutilizedreport; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY hoursutilizedreport (id, generateddate, fromdate, todate) FROM stdin;
\.


--
-- Data for Name: hoursutilizedreportitem; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY hoursutilizedreportitem (id, studentid, fundingtype, reportid, totalhours, programhrsused, transporthrsused, balancehrs, remarks) FROM stdin;
\.


--
-- Data for Name: invoiceitems; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY invoiceitems (id, programname, programtype, studentgrp, eventdate, transactiondate, chargeamount, paidamount, balance, remarks, invoiceid, transportcharge, programcharge, paymenttype) FROM stdin;
1	Ten Pin Bowling	Standard	Monday - Wyong	2012-01-02	2011-11-22	5	0	5	\N	1	0	0	\N
2	Ten Pin Bowling	Standard	Monday - Wyong	2012-01-02	2011-11-22	0	5	0	\N	1	0	0	Cash
\.


--
-- Data for Name: invoices; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY invoices (id, studentid, invoiceeid, type, description, previousoutstanding, totalcharge, previouspayments, transportcharge, eftcharge, otherpayment, currentpayments, subtotal, discount, tax, total, invoicedate, followupnote, billedtothirdparty, status) FROM stdin;
1	23	\N	INVOICE	\N	0	5	0	0	0	5	5	0	0	0	0	2012-01-02 00:00:00+11	\N	\N	\N
\.


--
-- Data for Name: leavepolicies; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY leavepolicies (id, name, status) FROM stdin;
1	Policy 1	Active
\.


--
-- Data for Name: leavepolicydetails; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY leavepolicydetails (id, leavetype, daysentitled, remarks, leavepolicyid) FROM stdin;
1	Casual	10		1
\.


--
-- Data for Name: leaves; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY leaves (id, reason, startdate, enddate, leavetype, days, staffid) FROM stdin;
\.


--
-- Data for Name: locations; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY locations (id, name, locationcode, contactid, status) FROM stdin;
3	Dairy	Dairy	302	\N
5	Wyong PSO	PSOW	304	\N
8	MMAD	MMAD	307	\N
10	Gosford Musical Society	GMS	309	\N
1	Bateau Bay Ten Pin Bowling 	WTPB	300	\N
2	Bateau Bay Ten Pin Bowling	GTPB	301	\N
4	Building 46	Bldg46	303	\N
6	Building 45	Bldg45	305	\N
11	PSO Kariong	PSOK	310	\N
-1	Home	\N	\N	\N
12	Wyong Art Shed	WAShed	311	\N
13	Wyong Train Station	WTrains	312	\N
9	Gosford Parkside	PSOG	308	\N
7	Coop - Cottage 2	Cttge 2 	306	\N
14	7/11 Service Station	SS Lisarow	313	\N
15	Gosford Train Station	GTrains	314	\N
16	Camp Breakaway	Camp B	315	\N
17	Boccia	Boccia	316	\N
18	Out and about	OUT	317	\N
\.


--
-- Data for Name: outlets; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY outlets (id, serviceid, name, description, mdsid, fundingsourceid, status) FROM stdin;
1	30892	Wyong		6406	9	\N
2	30893	Gosford		20186	9	\N
3	31671	Kariong		21608	10	\N
4	31674	Wyong		21611	10	\N
5	31673	COMMUNITY PARTICIPATION INDIVIDUAL		21613	11	\N
6	32371	COMMUNITY PARTICIPATION Life Choices Wyong		999999	12	\N
7	36150	COMMUNITY PARTICIPATION Life Choices Kariong		888888	13	\N
8	36136	TRANSITION TO WORK Wyong		27380	14	\N
9	36135	Transition to Work Gosford		27379	14	\N
\.


--
-- Data for Name: programeventcoordinatorxref; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY programeventcoordinatorxref (programeventid, coordinatorid) FROM stdin;
36	10
1	57
28	57
29	57
30	57
31	57
32	57
33	57
34	57
35	57
41	10
46	10
51	10
53	19
54	19
55	19
56	19
57	19
58	19
59	19
60	57
61	57
62	57
63	57
64	57
65	57
66	57
67	57
68	19
69	19
70	19
71	19
72	51
73	51
74	51
75	51
76	19
77	19
78	19
79	19
80	19
81	57
82	57
83	57
84	57
85	57
86	57
87	57
88	57
89	57
90	57
91	10
92	10
93	10
94	10
95	10
96	57
97	57
98	57
99	57
100	19
105	19
110	19
115	19
116	57
117	57
118	57
119	57
120	57
121	19
122	19
123	19
124	19
125	19
126	57
127	57
128	57
129	57
130	10
131	10
132	10
133	10
134	19
135	19
136	19
137	19
138	19
139	19
140	19
141	19
142	19
143	19
144	19
145	19
146	19
147	10
148	10
149	10
150	10
151	19
152	19
153	19
154	19
155	10
156	10
157	10
158	10
159	57
160	57
161	57
162	57
163	57
164	57
165	57
166	57
167	57
168	57
169	57
170	57
171	19
172	19
173	19
174	19
175	57
176	57
177	57
178	57
179	19
180	19
181	19
182	19
183	51
184	51
185	51
186	51
187	57
188	57
189	57
190	57
191	57
192	57
193	57
194	57
195	57
196	19
197	19
198	19
199	19
200	19
201	19
202	19
203	19
204	19
205	19
206	19
207	19
209	19
210	19
211	19
208	19
213	10
214	10
215	10
212	10
216	57
217	57
218	57
219	57
221	10
222	10
223	10
224	19
225	19
226	19
227	19
228	57
229	57
230	57
231	57
232	19
233	19
234	19
235	19
252	57
236	51
237	51
238	51
239	51
240	10
241	10
242	10
243	10
244	10
245	10
246	10
247	10
248	10
249	10
250	10
251	10
253	57
254	57
255	57
260	57
220	10
265	57
270	57
275	57
276	51
277	51
278	51
279	51
280	53
281	53
282	53
283	53
284	38
285	38
286	38
287	38
288	22
289	22
290	22
291	22
292	32
293	32
294	32
295	32
296	49
297	49
298	49
299	49
300	34
301	34
302	34
303	34
304	28
305	28
306	28
307	28
308	35
309	35
310	35
311	35
312	53
313	53
314	53
315	53
316	22
317	22
318	22
319	22
320	38
321	38
322	38
323	38
324	32
325	32
326	32
327	32
328	47
329	47
330	47
331	47
332	24
333	24
334	24
335	24
336	35
337	35
338	35
339	35
340	28
341	28
342	28
343	28
344	34
345	34
346	34
347	34
348	49
349	49
350	49
351	49
352	8
353	8
354	8
355	8
356	24
357	24
358	24
359	24
360	14
361	14
362	14
363	14
364	47
365	47
366	47
367	47
368	25
369	25
370	25
371	25
372	16
373	16
374	16
375	16
376	22
377	22
378	22
379	22
380	13
381	13
382	13
383	13
388	24
389	24
390	24
391	24
384	8
385	8
386	8
387	8
392	9
394	9
396	9
398	9
399	47
400	47
401	47
402	47
403	14
404	14
405	14
406	14
407	16
408	16
409	16
410	16
411	22
412	22
413	22
414	22
415	13
416	13
417	13
418	13
419	25
420	25
421	25
422	25
423	9
424	9
425	9
426	9
427	54
428	54
429	54
430	54
431	53
432	53
433	53
434	53
435	12
436	12
437	12
438	12
439	16
440	16
441	16
442	16
443	31
444	31
445	31
446	31
447	49
448	49
449	49
450	49
451	25
452	25
453	25
454	25
455	9
456	9
457	9
458	9
459	54
460	54
461	54
462	54
463	53
464	53
465	53
466	53
467	12
468	12
469	12
470	12
471	8
472	8
473	8
474	8
475	16
476	16
477	16
478	16
479	31
480	31
481	31
482	31
483	49
484	49
485	49
486	49
487	49
488	47
489	47
490	47
491	47
492	53
493	53
494	53
495	53
496	13
497	13
498	13
499	13
500	31
501	31
502	31
503	31
504	32
505	32
506	32
507	32
508	9
509	9
510	9
511	9
512	25
513	25
514	25
515	25
516	35
517	35
518	35
519	35
520	16
521	16
522	16
523	16
524	51
525	51
526	51
527	51
528	47
529	47
530	47
531	47
532	53
533	53
534	53
535	53
536	13
537	13
538	13
539	13
540	31
541	31
542	31
543	31
544	9
545	9
546	9
547	9
548	32
549	32
550	32
551	32
552	25
553	25
554	25
555	25
556	35
557	35
558	35
559	35
560	16
561	16
562	16
563	16
564	59
565	59
566	59
567	59
568	51
569	51
570	51
571	51
572	60
573	60
574	60
575	60
576	9
577	9
578	9
579	9
580	38
581	38
582	38
583	38
584	8
585	8
586	8
587	8
588	24
589	24
590	24
591	24
592	34
593	34
594	34
595	34
596	43
597	43
598	43
599	43
600	53
601	53
602	53
603	53
604	60
605	60
606	60
607	60
608	9
609	9
610	9
611	9
612	38
613	38
614	38
615	38
616	8
617	8
618	8
619	8
620	24
621	24
622	24
623	24
624	34
625	34
626	34
627	34
628	43
629	43
630	43
631	43
632	53
633	53
634	53
635	53
52	19
637	57
638	57
639	57
640	57
641	57
642	57
643	57
644	57
645	57
646	57
647	57
648	57
649	57
650	57
651	57
652	57
653	57
654	57
655	57
656	57
657	57
658	57
659	57
660	57
636	57
662	10
663	10
664	10
665	10
666	10
667	10
668	10
669	10
670	10
671	10
672	10
673	10
674	10
675	10
676	10
677	10
678	10
679	10
680	10
681	10
661	10
\.


--
-- Data for Name: programevents; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY programevents (id, name, programid, studentgroupid, eventdate, starttime, endtime, actualstarttime, actualendtime, coordinatorid, locationid, status, fundingsstatus, vehicleid, printeduserid, printeddate, generateddate, completeddate, invoiced) FROM stdin;
36	28/11/11-Mon : Mixed Media Art Mon	6	6	2011-11-28	2011-11-28 09:00:00+11	2011-11-28 15:00:00+11	\N	\N	10	3	pending	Not-Banked	\N	0	\N	\N	\N	f
1	02/01/12-Mon : Monday - Wyong	3	1	2012-01-02	2012-01-02 09:00:00+11	2012-01-02 15:00:00+11	\N	\N	57	1	finished	Not-Banked	\N	0	\N	2011-11-22	2011-11-22	t
28	28/11/11-Mon : TPB Wyong Monday	3	4	2011-11-28	2011-11-28 09:00:00+11	2011-11-28 15:00:00+11	\N	\N	57	1	pending	Not-Banked	\N	0	\N	\N	\N	f
29	05/12/11-Mon : TPB Wyong Monday	3	4	2011-12-05	2011-12-05 09:00:00+11	2011-12-05 15:00:00+11	\N	\N	57	1	pending	Not-Banked	\N	0	\N	\N	\N	f
30	12/12/11-Mon : TPB Wyong Monday	3	4	2011-12-12	2011-12-12 09:00:00+11	2011-12-12 15:00:00+11	\N	\N	57	1	pending	Not-Banked	\N	0	\N	\N	\N	f
31	19/12/11-Mon : TPB Wyong Monday	3	4	2011-12-19	2011-12-19 09:00:00+11	2011-12-19 15:00:00+11	\N	\N	57	1	pending	Not-Banked	\N	0	\N	\N	\N	f
32	28/11/11-Mon : TPB Gosford Monday	5	5	2011-11-28	2011-11-28 09:00:00+11	2011-11-28 15:00:00+11	\N	\N	57	2	pending	Not-Banked	\N	0	\N	\N	\N	f
33	05/12/11-Mon : TPB Gosford Monday	5	5	2011-12-05	2011-12-05 09:00:00+11	2011-12-05 15:00:00+11	\N	\N	57	2	pending	Not-Banked	\N	0	\N	\N	\N	f
34	12/12/11-Mon : TPB Gosford Monday	5	5	2011-12-12	2011-12-12 09:00:00+11	2011-12-12 15:00:00+11	\N	\N	57	2	pending	Not-Banked	\N	0	\N	\N	\N	f
35	19/12/11-Mon : TPB Gosford Monday	5	5	2011-12-19	2011-12-19 09:00:00+11	2011-12-19 15:00:00+11	\N	\N	57	2	pending	Not-Banked	\N	0	\N	\N	\N	f
41	05/12/11-Mon : Mixed Media Art Mon	6	6	2011-12-05	2011-12-05 09:00:00+11	2011-12-05 15:00:00+11	\N	\N	10	3	pending	Not-Banked	\N	0	\N	\N	\N	f
46	12/12/11-Mon : Mixed Media Art Mon	6	6	2011-12-12	2011-12-12 09:00:00+11	2011-12-12 15:00:00+11	\N	\N	10	3	pending	Not-Banked	\N	0	\N	\N	\N	f
51	19/12/11-Mon : Mixed Media Art Mon	6	6	2011-12-19	2011-12-19 09:00:00+11	2011-12-19 15:00:00+11	\N	\N	10	3	pending	Not-Banked	\N	0	\N	\N	\N	f
53	05/12/11-Mon : Home Skills 1 Monday	7	7	2011-12-05	2011-12-05 09:00:00+11	2011-12-05 15:00:00+11	\N	\N	19	4	pending	Not-Banked	\N	0	\N	\N	\N	f
54	12/12/11-Mon : Home Skills 1 Monday	7	7	2011-12-12	2011-12-12 09:00:00+11	2011-12-12 15:00:00+11	\N	\N	19	4	pending	Not-Banked	\N	0	\N	\N	\N	f
55	19/12/11-Mon : Home Skills 1 Monday	7	7	2011-12-19	2011-12-19 09:00:00+11	2011-12-19 15:00:00+11	\N	\N	19	4	pending	Not-Banked	\N	0	\N	\N	\N	f
56	28/11/11-Mon : Home Skills 2 Monday	8	8	2011-11-28	2011-11-28 09:00:00+11	2011-11-28 15:00:00+11	\N	\N	19	5	pending	Not-Banked	\N	0	\N	\N	\N	f
57	05/12/11-Mon : Home Skills 2 Monday	8	8	2011-12-05	2011-12-05 09:00:00+11	2011-12-05 15:00:00+11	\N	\N	19	5	pending	Not-Banked	\N	0	\N	\N	\N	f
58	12/12/11-Mon : Home Skills 2 Monday	8	8	2011-12-12	2011-12-12 09:00:00+11	2011-12-12 15:00:00+11	\N	\N	19	5	pending	Not-Banked	\N	0	\N	\N	\N	f
59	19/12/11-Mon : Home Skills 2 Monday	8	8	2011-12-19	2011-12-19 09:00:00+11	2011-12-19 15:00:00+11	\N	\N	19	5	pending	Not-Banked	\N	0	\N	\N	\N	f
60	28/11/11-Mon : Sensory Music Monday	9	9	2011-11-28	2011-11-28 09:00:00+11	2011-11-28 15:00:00+11	\N	\N	57	6	pending	Not-Banked	\N	0	\N	\N	\N	f
61	05/12/11-Mon : Sensory Music Monday	9	9	2011-12-05	2011-12-05 09:00:00+11	2011-12-05 15:00:00+11	\N	\N	57	6	pending	Not-Banked	\N	0	\N	\N	\N	f
62	12/12/11-Mon : Sensory Music Monday	9	9	2011-12-12	2011-12-12 09:00:00+11	2011-12-12 15:00:00+11	\N	\N	57	6	pending	Not-Banked	\N	0	\N	\N	\N	f
63	19/12/11-Mon : Sensory Music Monday	9	9	2011-12-19	2011-12-19 09:00:00+11	2011-12-19 15:00:00+11	\N	\N	57	6	pending	Not-Banked	\N	0	\N	\N	\N	f
64	28/11/11-Mon : CA Monday	10	10	2011-11-28	2011-11-28 09:00:00+11	2011-11-28 15:00:00+11	\N	\N	57	5	pending	Not-Banked	\N	0	\N	\N	\N	f
65	05/12/11-Mon : CA Monday	10	10	2011-12-05	2011-12-05 09:00:00+11	2011-12-05 15:00:00+11	\N	\N	57	5	pending	Not-Banked	\N	0	\N	\N	\N	f
66	12/12/11-Mon : CA Monday	10	10	2011-12-12	2011-12-12 09:00:00+11	2011-12-12 15:00:00+11	\N	\N	57	5	pending	Not-Banked	\N	0	\N	\N	\N	f
67	19/12/11-Mon : CA Monday	10	10	2011-12-19	2011-12-19 09:00:00+11	2011-12-19 15:00:00+11	\N	\N	57	5	pending	Not-Banked	\N	0	\N	\N	\N	f
68	28/11/11-Mon : L2L Monday	11	11	2011-11-28	2011-11-28 09:30:00+11	2011-11-28 15:30:00+11	\N	\N	19	9	pending	Not-Banked	\N	0	\N	\N	\N	f
69	05/12/11-Mon : L2L Monday	11	11	2011-12-05	2011-12-05 09:30:00+11	2011-12-05 15:30:00+11	\N	\N	19	9	pending	Not-Banked	\N	0	\N	\N	\N	f
70	12/12/11-Mon : L2L Monday	11	11	2011-12-12	2011-12-12 09:30:00+11	2011-12-12 15:30:00+11	\N	\N	19	9	pending	Not-Banked	\N	0	\N	\N	\N	f
71	19/12/11-Mon : L2L Monday	11	11	2011-12-19	2011-12-19 09:30:00+11	2011-12-19 15:30:00+11	\N	\N	19	9	pending	Not-Banked	\N	0	\N	\N	\N	f
72	28/11/11-Mon : Coop Monday	12	12	2011-11-28	2011-11-28 08:30:00+11	2011-11-28 15:30:00+11	\N	\N	51	7	pending	Not-Banked	\N	0	\N	\N	\N	f
73	05/12/11-Mon : Coop Monday	12	12	2011-12-05	2011-12-05 08:30:00+11	2011-12-05 15:30:00+11	\N	\N	51	7	pending	Not-Banked	\N	0	\N	\N	\N	f
74	12/12/11-Mon : Coop Monday	12	12	2011-12-12	2011-12-12 08:30:00+11	2011-12-12 15:30:00+11	\N	\N	51	7	pending	Not-Banked	\N	0	\N	\N	\N	f
75	19/12/11-Mon : Coop Monday	12	12	2011-12-19	2011-12-19 08:30:00+11	2011-12-19 15:30:00+11	\N	\N	51	7	pending	Not-Banked	\N	0	\N	\N	\N	f
76	22/11/11-Tue : Herb Farm Tuesday	14	13	2011-11-22	2011-11-22 09:00:00+11	2011-11-22 15:15:00+11	\N	\N	19	9	pending	Not-Banked	\N	0	\N	\N	\N	f
77	29/11/11-Tue : Herb Farm Tuesday	14	13	2011-11-29	2011-11-29 09:00:00+11	2011-11-29 15:15:00+11	\N	\N	19	9	pending	Not-Banked	\N	0	\N	\N	\N	f
78	06/12/11-Tue : Herb Farm Tuesday	14	13	2011-12-06	2011-12-06 09:00:00+11	2011-12-06 15:15:00+11	\N	\N	19	9	pending	Not-Banked	\N	0	\N	\N	\N	f
79	13/12/11-Tue : Herb Farm Tuesday	14	13	2011-12-13	2011-12-13 09:00:00+11	2011-12-13 15:15:00+11	\N	\N	19	9	pending	Not-Banked	\N	0	\N	\N	\N	f
80	20/12/11-Tue : Herb Farm Tuesday	14	13	2011-12-20	2011-12-20 09:00:00+11	2011-12-20 15:15:00+11	\N	\N	19	9	pending	Not-Banked	\N	0	\N	\N	\N	f
81	22/11/11-Tue : Hi Guys Tuesday	15	14	2011-11-22	2011-11-22 09:00:00+11	2011-11-22 15:00:00+11	\N	\N	57	5	pending	Not-Banked	\N	0	\N	\N	\N	f
82	29/11/11-Tue : Hi Guys Tuesday	15	14	2011-11-29	2011-11-29 09:00:00+11	2011-11-29 15:00:00+11	\N	\N	57	5	pending	Not-Banked	\N	0	\N	\N	\N	f
83	06/12/11-Tue : Hi Guys Tuesday	15	14	2011-12-06	2011-12-06 09:00:00+11	2011-12-06 15:00:00+11	\N	\N	57	5	pending	Not-Banked	\N	0	\N	\N	\N	f
84	13/12/11-Tue : Hi Guys Tuesday	15	14	2011-12-13	2011-12-13 09:00:00+11	2011-12-13 15:00:00+11	\N	\N	57	5	pending	Not-Banked	\N	0	\N	\N	\N	f
85	20/12/11-Tue : Hi Guys Tuesday	15	14	2011-12-20	2011-12-20 09:00:00+11	2011-12-20 15:00:00+11	\N	\N	57	5	pending	Not-Banked	\N	0	\N	\N	\N	f
86	22/11/11-Tue : Sensory Art Felt Tuesday	16	15	2011-11-22	2011-11-22 09:00:00+11	2011-11-22 15:00:00+11	\N	\N	57	5	pending	Not-Banked	\N	0	\N	\N	\N	f
87	29/11/11-Tue : Sensory Art Felt Tuesday	16	15	2011-11-29	2011-11-29 09:00:00+11	2011-11-29 15:00:00+11	\N	\N	57	5	pending	Not-Banked	\N	0	\N	\N	\N	f
88	06/12/11-Tue : Sensory Art Felt Tuesday	16	15	2011-12-06	2011-12-06 09:00:00+11	2011-12-06 15:00:00+11	\N	\N	57	5	pending	Not-Banked	\N	0	\N	\N	\N	f
89	13/12/11-Tue : Sensory Art Felt Tuesday	16	15	2011-12-13	2011-12-13 09:00:00+11	2011-12-13 15:00:00+11	\N	\N	57	5	pending	Not-Banked	\N	0	\N	\N	\N	f
90	20/12/11-Tue : Sensory Art Felt Tuesday	16	15	2011-12-20	2011-12-20 09:00:00+11	2011-12-20 15:00:00+11	\N	\N	57	5	pending	Not-Banked	\N	0	\N	\N	\N	f
91	22/11/11-Tue : Dairy Art Tuesday	17	16	2011-11-22	2011-11-22 09:00:00+11	2011-11-22 15:00:00+11	\N	\N	10	3	pending	Not-Banked	\N	0	\N	\N	\N	f
92	29/11/11-Tue : Dairy Art Tuesday	17	16	2011-11-29	2011-11-29 09:00:00+11	2011-11-29 15:00:00+11	\N	\N	10	3	pending	Not-Banked	\N	0	\N	\N	\N	f
93	06/12/11-Tue : Dairy Art Tuesday	17	16	2011-12-06	2011-12-06 09:00:00+11	2011-12-06 15:00:00+11	\N	\N	10	3	pending	Not-Banked	\N	0	\N	\N	\N	f
94	13/12/11-Tue : Dairy Art Tuesday	17	16	2011-12-13	2011-12-13 09:00:00+11	2011-12-13 15:00:00+11	\N	\N	10	3	pending	Not-Banked	\N	0	\N	\N	\N	f
95	20/12/11-Tue : Dairy Art Tuesday	17	16	2011-12-20	2011-12-20 09:00:00+11	2011-12-20 15:00:00+11	\N	\N	10	3	pending	Not-Banked	\N	0	\N	\N	\N	f
96	29/11/11-Tue : Water Fitness Tuesday	19	18	2011-11-29	2011-11-29 09:00:00+11	2011-11-29 15:00:00+11	\N	\N	57	5	pending	Not-Banked	\N	0	\N	\N	\N	f
97	06/12/11-Tue : Water Fitness Tuesday	19	18	2011-12-06	2011-12-06 09:00:00+11	2011-12-06 15:00:00+11	\N	\N	57	5	pending	Not-Banked	\N	0	\N	\N	\N	f
98	13/12/11-Tue : Water Fitness Tuesday	19	18	2011-12-13	2011-12-13 09:00:00+11	2011-12-13 15:00:00+11	\N	\N	57	5	pending	Not-Banked	\N	0	\N	\N	\N	f
99	20/12/11-Tue : Water Fitness Tuesday	19	18	2011-12-20	2011-12-20 09:00:00+11	2011-12-20 15:00:00+11	\N	\N	57	5	pending	Not-Banked	\N	0	\N	\N	\N	f
100	29/11/11-Tue : Living Skills Tuesday	20	19	2011-11-29	2011-11-29 09:00:00+11	2011-11-29 15:00:00+11	\N	\N	19	4	pending	Not-Banked	\N	0	\N	\N	\N	f
105	06/12/11-Tue : Living Skills Tuesday	20	19	2011-12-06	2011-12-06 09:00:00+11	2011-12-06 15:00:00+11	\N	\N	19	4	pending	Not-Banked	\N	0	\N	\N	\N	f
110	13/12/11-Tue : Living Skills Tuesday	20	19	2011-12-13	2011-12-13 09:00:00+11	2011-12-13 15:00:00+11	\N	\N	19	4	pending	Not-Banked	\N	0	\N	\N	\N	f
115	20/12/11-Tue : Living Skills Tuesday	20	19	2011-12-20	2011-12-20 09:00:00+11	2011-12-20 15:00:00+11	\N	\N	19	4	pending	Not-Banked	\N	0	\N	\N	\N	f
116	22/11/11-Tue : CA Tuesday	21	20	2011-11-22	2011-11-22 09:00:00+11	2011-11-22 15:00:00+11	\N	\N	57	5	pending	Not-Banked	\N	0	\N	\N	\N	f
117	29/11/11-Tue : CA Tuesday	21	20	2011-11-29	2011-11-29 09:00:00+11	2011-11-29 15:00:00+11	\N	\N	57	5	pending	Not-Banked	\N	0	\N	\N	\N	f
118	06/12/11-Tue : CA Tuesday	21	20	2011-12-06	2011-12-06 09:00:00+11	2011-12-06 15:00:00+11	\N	\N	57	5	pending	Not-Banked	\N	0	\N	\N	\N	f
119	13/12/11-Tue : CA Tuesday	21	20	2011-12-13	2011-12-13 09:00:00+11	2011-12-13 15:00:00+11	\N	\N	57	5	pending	Not-Banked	\N	0	\N	\N	\N	f
120	20/12/11-Tue : CA Tuesday	21	20	2011-12-20	2011-12-20 09:00:00+11	2011-12-20 15:00:00+11	\N	\N	57	5	pending	Not-Banked	\N	0	\N	\N	\N	f
121	22/11/11-Tue : Events In The Office Tuesday	22	21	2011-11-22	2011-11-22 09:00:00+11	2011-11-22 15:00:00+11	\N	\N	19	9	pending	Not-Banked	\N	0	\N	\N	\N	f
122	29/11/11-Tue : Events In The Office Tuesday	22	21	2011-11-29	2011-11-29 09:00:00+11	2011-11-29 15:00:00+11	\N	\N	19	9	pending	Not-Banked	\N	0	\N	\N	\N	f
123	06/12/11-Tue : Events In The Office Tuesday	22	21	2011-12-06	2011-12-06 09:00:00+11	2011-12-06 15:00:00+11	\N	\N	19	9	pending	Not-Banked	\N	0	\N	\N	\N	f
124	13/12/11-Tue : Events In The Office Tuesday	22	21	2011-12-13	2011-12-13 09:00:00+11	2011-12-13 15:00:00+11	\N	\N	19	9	pending	Not-Banked	\N	0	\N	\N	\N	f
125	20/12/11-Tue : Events In The Office Tuesday	22	21	2011-12-20	2011-12-20 09:00:00+11	2011-12-20 15:00:00+11	\N	\N	19	9	pending	Not-Banked	\N	0	\N	\N	\N	f
126	29/11/11-Tue : Gym Swim Tuesday	23	22	2011-11-29	2011-11-29 09:00:00+11	2011-11-29 15:00:00+11	\N	\N	57	9	pending	Not-Banked	\N	0	\N	\N	\N	f
127	06/12/11-Tue : Gym Swim Tuesday	23	22	2011-12-06	2011-12-06 09:00:00+11	2011-12-06 15:00:00+11	\N	\N	57	9	pending	Not-Banked	\N	0	\N	\N	\N	f
128	13/12/11-Tue : Gym Swim Tuesday	23	22	2011-12-13	2011-12-13 09:00:00+11	2011-12-13 15:00:00+11	\N	\N	57	9	pending	Not-Banked	\N	0	\N	\N	\N	f
129	20/12/11-Tue : Gym Swim Tuesday	23	22	2011-12-20	2011-12-20 09:00:00+11	2011-12-20 15:00:00+11	\N	\N	57	9	pending	Not-Banked	\N	0	\N	\N	\N	f
130	29/11/11-Tue : Radio Tuesday	24	23	2011-11-29	2011-11-29 09:00:00+11	2011-11-29 15:00:00+11	\N	\N	10	8	pending	Not-Banked	\N	0	\N	\N	\N	f
131	06/12/11-Tue : Radio Tuesday	24	23	2011-12-06	2011-12-06 09:00:00+11	2011-12-06 15:00:00+11	\N	\N	10	8	pending	Not-Banked	\N	0	\N	\N	\N	f
132	13/12/11-Tue : Radio Tuesday	24	23	2011-12-13	2011-12-13 09:00:00+11	2011-12-13 15:00:00+11	\N	\N	10	8	pending	Not-Banked	\N	0	\N	\N	\N	f
133	20/12/11-Tue : Radio Tuesday	24	23	2011-12-20	2011-12-20 09:00:00+11	2011-12-20 15:00:00+11	\N	\N	10	8	pending	Not-Banked	\N	0	\N	\N	\N	f
134	22/11/11-Tue : L2L Tuesday	11	24	2011-11-22	2011-11-22 09:00:00+11	2011-11-22 15:00:00+11	\N	\N	19	4	pending	Not-Banked	\N	0	\N	\N	\N	f
135	29/11/11-Tue : L2L Tuesday	11	24	2011-11-29	2011-11-29 09:00:00+11	2011-11-29 15:00:00+11	\N	\N	19	4	pending	Not-Banked	\N	0	\N	\N	\N	f
136	06/12/11-Tue : L2L Tuesday	11	24	2011-12-06	2011-12-06 09:00:00+11	2011-12-06 15:00:00+11	\N	\N	19	4	pending	Not-Banked	\N	0	\N	\N	\N	f
137	13/12/11-Tue : L2L Tuesday	11	24	2011-12-13	2011-12-13 09:00:00+11	2011-12-13 15:00:00+11	\N	\N	19	4	pending	Not-Banked	\N	0	\N	\N	\N	f
138	20/12/11-Tue : L2L Tuesday	11	24	2011-12-20	2011-12-20 09:00:00+11	2011-12-20 15:00:00+11	\N	\N	19	4	pending	Not-Banked	\N	0	\N	\N	\N	f
139	30/11/11-Wed : Care for Community Wednesday	26	26	2011-11-30	2011-11-30 09:00:00+11	2011-11-30 15:00:00+11	\N	\N	19	9	pending	Not-Banked	\N	0	\N	\N	\N	f
140	07/12/11-Wed : Care for Community Wednesday	26	26	2011-12-07	2011-12-07 09:00:00+11	2011-12-07 15:00:00+11	\N	\N	19	9	pending	Not-Banked	\N	0	\N	\N	\N	f
141	14/12/11-Wed : Care for Community Wednesday	26	26	2011-12-14	2011-12-14 09:00:00+11	2011-12-14 15:00:00+11	\N	\N	19	9	pending	Not-Banked	\N	0	\N	\N	\N	f
142	21/12/11-Wed : Care for Community Wednesday	26	26	2011-12-21	2011-12-21 09:00:00+11	2011-12-21 15:00:00+11	\N	\N	19	9	pending	Not-Banked	\N	0	\N	\N	\N	f
143	30/11/11-Wed : Gos Mow Budget Wednesday	27	27	2011-11-30	2011-11-30 09:00:00+11	2011-11-30 15:00:00+11	\N	\N	19	9	pending	Not-Banked	\N	0	\N	\N	\N	f
144	07/12/11-Wed : Gos Mow Budget Wednesday	27	27	2011-12-07	2011-12-07 09:00:00+11	2011-12-07 15:00:00+11	\N	\N	19	9	pending	Not-Banked	\N	0	\N	\N	\N	f
145	14/12/11-Wed : Gos Mow Budget Wednesday	27	27	2011-12-14	2011-12-14 09:00:00+11	2011-12-14 15:00:00+11	\N	\N	19	9	pending	Not-Banked	\N	0	\N	\N	\N	f
146	21/12/11-Wed : Gos Mow Budget Wednesday	27	27	2011-12-21	2011-12-21 09:00:00+11	2011-12-21 15:00:00+11	\N	\N	19	9	pending	Not-Banked	\N	0	\N	\N	\N	f
147	30/11/11-Wed : Multimedia Wednesday	28	28	2011-11-30	2011-11-30 09:00:00+11	2011-11-30 15:00:00+11	\N	\N	10	4	pending	Not-Banked	\N	0	\N	\N	\N	f
148	07/12/11-Wed : Multimedia Wednesday	28	28	2011-12-07	2011-12-07 09:00:00+11	2011-12-07 15:00:00+11	\N	\N	10	4	pending	Not-Banked	\N	0	\N	\N	\N	f
149	14/12/11-Wed : Multimedia Wednesday	28	28	2011-12-14	2011-12-14 09:00:00+11	2011-12-14 15:00:00+11	\N	\N	10	4	pending	Not-Banked	\N	0	\N	\N	\N	f
150	21/12/11-Wed : Multimedia Wednesday	28	28	2011-12-21	2011-12-21 09:00:00+11	2011-12-21 15:00:00+11	\N	\N	10	4	pending	Not-Banked	\N	0	\N	\N	\N	f
151	30/11/11-Wed : Laundry Aged Care Wednesday	29	29	2011-11-30	2011-11-30 09:00:00+11	2011-11-30 15:00:00+11	\N	\N	19	9	pending	Not-Banked	\N	0	\N	\N	\N	f
152	07/12/11-Wed : Laundry Aged Care Wednesday	29	29	2011-12-07	2011-12-07 09:00:00+11	2011-12-07 15:00:00+11	\N	\N	19	9	pending	Not-Banked	\N	0	\N	\N	\N	f
153	14/12/11-Wed : Laundry Aged Care Wednesday	29	29	2011-12-14	2011-12-14 09:00:00+11	2011-12-14 15:00:00+11	\N	\N	19	9	pending	Not-Banked	\N	0	\N	\N	\N	f
154	21/12/11-Wed : Laundry Aged Care Wednesday	29	29	2011-12-21	2011-12-21 09:00:00+11	2011-12-21 15:00:00+11	\N	\N	19	9	pending	Not-Banked	\N	0	\N	\N	\N	f
155	30/11/11-Wed : Dance Fitness Wednesday	30	30	2011-11-30	2011-11-30 09:00:00+11	2011-11-30 15:00:00+11	\N	\N	10	9	pending	Not-Banked	\N	0	\N	\N	\N	f
156	07/12/11-Wed : Dance Fitness Wednesday	30	30	2011-12-07	2011-12-07 09:00:00+11	2011-12-07 15:00:00+11	\N	\N	10	9	pending	Not-Banked	\N	0	\N	\N	\N	f
157	14/12/11-Wed : Dance Fitness Wednesday	30	30	2011-12-14	2011-12-14 09:00:00+11	2011-12-14 15:00:00+11	\N	\N	10	9	pending	Not-Banked	\N	0	\N	\N	\N	f
158	21/12/11-Wed : Dance Fitness Wednesday	30	30	2011-12-21	2011-12-21 09:00:00+11	2011-12-21 15:00:00+11	\N	\N	10	9	pending	Not-Banked	\N	0	\N	\N	\N	f
159	30/11/11-Wed : CA Wednesday	31	31	2011-11-30	2011-11-30 09:00:00+11	2011-11-30 15:00:00+11	\N	\N	57	5	pending	Not-Banked	\N	0	\N	\N	\N	f
160	07/12/11-Wed : CA Wednesday	31	31	2011-12-07	2011-12-07 09:00:00+11	2011-12-07 15:00:00+11	\N	\N	57	5	pending	Not-Banked	\N	0	\N	\N	\N	f
161	14/12/11-Wed : CA Wednesday	31	31	2011-12-14	2011-12-14 09:00:00+11	2011-12-14 15:00:00+11	\N	\N	57	5	pending	Not-Banked	\N	0	\N	\N	\N	f
162	21/12/11-Wed : CA Wednesday	31	31	2011-12-21	2011-12-21 09:00:00+11	2011-12-21 15:00:00+11	\N	\N	57	5	pending	Not-Banked	\N	0	\N	\N	\N	f
163	30/11/11-Wed : Maintenance Wednesday	32	32	2011-11-30	2011-11-30 09:00:00+11	2011-11-30 15:00:00+11	\N	\N	57	6	pending	Not-Banked	\N	0	\N	\N	\N	f
164	07/12/11-Wed : Maintenance Wednesday	32	32	2011-12-07	2011-12-07 09:00:00+11	2011-12-07 15:00:00+11	\N	\N	57	6	pending	Not-Banked	\N	0	\N	\N	\N	f
165	14/12/11-Wed : Maintenance Wednesday	32	32	2011-12-14	2011-12-14 09:00:00+11	2011-12-14 15:00:00+11	\N	\N	57	6	pending	Not-Banked	\N	0	\N	\N	\N	f
166	21/12/11-Wed : Maintenance Wednesday	32	32	2011-12-21	2011-12-21 09:00:00+11	2011-12-21 15:00:00+11	\N	\N	57	6	pending	Not-Banked	\N	0	\N	\N	\N	f
167	30/11/11-Wed : Gals Group Wednesday	33	33	2011-11-30	2011-11-30 09:00:00+11	2011-11-30 15:00:00+11	\N	\N	57	5	pending	Not-Banked	\N	0	\N	\N	\N	f
168	07/12/11-Wed : Gals Group Wednesday	33	33	2011-12-07	2011-12-07 09:00:00+11	2011-12-07 15:00:00+11	\N	\N	57	5	pending	Not-Banked	\N	0	\N	\N	\N	f
169	14/12/11-Wed : Gals Group Wednesday	33	33	2011-12-14	2011-12-14 09:00:00+11	2011-12-14 15:00:00+11	\N	\N	57	5	pending	Not-Banked	\N	0	\N	\N	\N	f
170	21/12/11-Wed : Gals Group Wednesday	33	33	2011-12-21	2011-12-21 09:00:00+11	2011-12-21 15:00:00+11	\N	\N	57	5	pending	Not-Banked	\N	0	\N	\N	\N	f
171	30/11/11-Wed : Stepping Out Wednesday	34	34	2011-11-30	2011-11-30 09:00:00+11	2011-11-30 15:00:00+11	\N	\N	19	9	pending	Not-Banked	\N	0	\N	\N	\N	f
172	07/12/11-Wed : Stepping Out Wednesday	34	34	2011-12-07	2011-12-07 09:00:00+11	2011-12-07 15:00:00+11	\N	\N	19	9	pending	Not-Banked	\N	0	\N	\N	\N	f
173	14/12/11-Wed : Stepping Out Wednesday	34	34	2011-12-14	2011-12-14 09:00:00+11	2011-12-14 15:00:00+11	\N	\N	19	9	pending	Not-Banked	\N	0	\N	\N	\N	f
174	21/12/11-Wed : Stepping Out Wednesday	34	34	2011-12-21	2011-12-21 09:00:00+11	2011-12-21 15:00:00+11	\N	\N	19	9	pending	Not-Banked	\N	0	\N	\N	\N	f
175	30/11/11-Wed : Gardenening Wednesday	35	35	2011-11-30	2011-11-30 09:00:00+11	2011-11-30 15:00:00+11	\N	\N	57	6	pending	Not-Banked	\N	0	\N	\N	\N	f
176	07/12/11-Wed : Gardenening Wednesday	35	35	2011-12-07	2011-12-07 09:00:00+11	2011-12-07 15:00:00+11	\N	\N	57	6	pending	Not-Banked	\N	0	\N	\N	\N	f
177	14/12/11-Wed : Gardenening Wednesday	35	35	2011-12-14	2011-12-14 09:00:00+11	2011-12-14 15:00:00+11	\N	\N	57	6	pending	Not-Banked	\N	0	\N	\N	\N	f
178	21/12/11-Wed : Gardenening Wednesday	35	35	2011-12-21	2011-12-21 09:00:00+11	2011-12-21 15:00:00+11	\N	\N	57	6	pending	Not-Banked	\N	0	\N	\N	\N	f
179	30/11/11-Wed : L2L Wednesday	11	36	2011-11-30	2011-11-29 23:00:00+11	2011-11-30 15:00:00+11	\N	\N	19	4	pending	Not-Banked	\N	0	\N	\N	\N	f
180	07/12/11-Wed : L2L Wednesday	11	36	2011-12-07	2011-12-06 23:00:00+11	2011-12-07 15:00:00+11	\N	\N	19	4	pending	Not-Banked	\N	0	\N	\N	\N	f
181	14/12/11-Wed : L2L Wednesday	11	36	2011-12-14	2011-12-13 23:00:00+11	2011-12-14 15:00:00+11	\N	\N	19	4	pending	Not-Banked	\N	0	\N	\N	\N	f
182	21/12/11-Wed : L2L Wednesday	11	36	2011-12-21	2011-12-20 23:00:00+11	2011-12-21 15:00:00+11	\N	\N	19	4	pending	Not-Banked	\N	0	\N	\N	\N	f
183	30/11/11-Wed : Coop Wednesday	12	37	2011-11-30	2011-11-30 09:00:00+11	2011-11-30 15:00:00+11	\N	\N	51	7	pending	Not-Banked	\N	0	\N	\N	\N	f
184	07/12/11-Wed : Coop Wednesday	12	37	2011-12-07	2011-12-07 09:00:00+11	2011-12-07 15:00:00+11	\N	\N	51	7	pending	Not-Banked	\N	0	\N	\N	\N	f
185	14/12/11-Wed : Coop Wednesday	12	37	2011-12-14	2011-12-14 09:00:00+11	2011-12-14 15:00:00+11	\N	\N	51	7	pending	Not-Banked	\N	0	\N	\N	\N	f
186	21/12/11-Wed : Coop Wednesday	12	37	2011-12-21	2011-12-21 09:00:00+11	2011-12-21 15:00:00+11	\N	\N	51	7	pending	Not-Banked	\N	0	\N	\N	\N	f
187	24/11/11-Thu : Camp Break Thursday	36	38	2011-11-24	2011-11-24 09:00:00+11	2011-11-24 15:00:00+11	\N	\N	57	9	pending	Not-Banked	\N	0	\N	\N	\N	f
188	01/12/11-Thu : Camp Break Thursday	36	38	2011-12-01	2011-12-01 09:00:00+11	2011-12-01 15:00:00+11	\N	\N	57	9	pending	Not-Banked	\N	0	\N	\N	\N	f
189	08/12/11-Thu : Camp Break Thursday	36	38	2011-12-08	2011-12-08 09:00:00+11	2011-12-08 15:00:00+11	\N	\N	57	9	pending	Not-Banked	\N	0	\N	\N	\N	f
190	15/12/11-Thu : Camp Break Thursday	36	38	2011-12-15	2011-12-15 09:00:00+11	2011-12-15 15:00:00+11	\N	\N	57	9	pending	Not-Banked	\N	0	\N	\N	\N	f
191	22/12/11-Thu : Camp Break Thursday	36	38	2011-12-22	2011-12-22 09:00:00+11	2011-12-22 15:00:00+11	\N	\N	57	9	pending	Not-Banked	\N	0	\N	\N	\N	f
192	01/12/11-Thu : Boccia Thursday	37	39	2011-12-01	2011-12-01 09:00:00+11	2011-12-01 15:00:00+11	\N	\N	57	5	pending	Not-Banked	\N	0	\N	\N	\N	f
193	08/12/11-Thu : Boccia Thursday	37	39	2011-12-08	2011-12-08 09:00:00+11	2011-12-08 15:00:00+11	\N	\N	57	5	pending	Not-Banked	\N	0	\N	\N	\N	f
194	15/12/11-Thu : Boccia Thursday	37	39	2011-12-15	2011-12-15 09:00:00+11	2011-12-15 15:00:00+11	\N	\N	57	5	pending	Not-Banked	\N	0	\N	\N	\N	f
195	22/12/11-Thu : Boccia Thursday	37	39	2011-12-22	2011-12-22 09:00:00+11	2011-12-22 15:00:00+11	\N	\N	57	5	pending	Not-Banked	\N	0	\N	\N	\N	f
196	01/12/11-Thu : In the office Thursday	38	40	2011-12-01	2011-11-30 23:00:00+11	2011-12-01 11:00:00+11	\N	\N	19	9	pending	Not-Banked	\N	0	\N	\N	\N	f
197	08/12/11-Thu : In the office Thursday	38	40	2011-12-08	2011-12-07 23:00:00+11	2011-12-08 11:00:00+11	\N	\N	19	9	pending	Not-Banked	\N	0	\N	\N	\N	f
198	15/12/11-Thu : In the office Thursday	38	40	2011-12-15	2011-12-14 23:00:00+11	2011-12-15 11:00:00+11	\N	\N	19	9	pending	Not-Banked	\N	0	\N	\N	\N	f
199	22/12/11-Thu : In the office Thursday	38	40	2011-12-22	2011-12-21 23:00:00+11	2011-12-22 11:00:00+11	\N	\N	19	9	pending	Not-Banked	\N	0	\N	\N	\N	f
200	01/12/11-Thu : Gos MOW Budget Thursday	27	41	2011-12-01	2011-12-01 09:00:00+11	2011-12-01 15:00:00+11	\N	\N	19	9	pending	Not-Banked	\N	0	\N	\N	\N	f
201	08/12/11-Thu : Gos MOW Budget Thursday	27	41	2011-12-08	2011-12-08 09:00:00+11	2011-12-08 15:00:00+11	\N	\N	19	9	pending	Not-Banked	\N	0	\N	\N	\N	f
202	15/12/11-Thu : Gos MOW Budget Thursday	27	41	2011-12-15	2011-12-15 09:00:00+11	2011-12-15 15:00:00+11	\N	\N	19	9	pending	Not-Banked	\N	0	\N	\N	\N	f
203	22/12/11-Thu : Gos MOW Budget Thursday	27	41	2011-12-22	2011-12-22 09:00:00+11	2011-12-22 15:00:00+11	\N	\N	19	9	pending	Not-Banked	\N	0	\N	\N	\N	f
204	01/12/11-Thu : Herb Farm Thursday	14	42	2011-12-01	2011-12-01 09:00:00+11	2011-12-01 15:00:00+11	\N	\N	19	6	pending	Not-Banked	\N	0	\N	\N	\N	f
205	08/12/11-Thu : Herb Farm Thursday	14	42	2011-12-08	2011-12-08 09:00:00+11	2011-12-08 15:00:00+11	\N	\N	19	6	pending	Not-Banked	\N	0	\N	\N	\N	f
206	15/12/11-Thu : Herb Farm Thursday	14	42	2011-12-15	2011-12-15 09:00:00+11	2011-12-15 15:00:00+11	\N	\N	19	6	pending	Not-Banked	\N	0	\N	\N	\N	f
207	22/12/11-Thu : Herb Farm Thursday	14	42	2011-12-22	2011-12-22 09:00:00+11	2011-12-22 15:00:00+11	\N	\N	19	6	pending	Not-Banked	\N	0	\N	\N	\N	f
209	08/12/11-Thu : GMSociety Thursday	18	43	2011-12-08	2011-12-08 09:00:00+11	2011-12-08 15:00:00+11	\N	\N	19	10	pending	Not-Banked	\N	0	\N	\N	\N	f
210	15/12/11-Thu : GMSociety Thursday	18	43	2011-12-15	2011-12-15 09:00:00+11	2011-12-15 15:00:00+11	\N	\N	19	10	pending	Not-Banked	\N	0	\N	\N	\N	f
211	22/12/11-Thu : GMSociety Thursday	18	43	2011-12-22	2011-12-22 09:00:00+11	2011-12-22 15:00:00+11	\N	\N	19	10	pending	Not-Banked	\N	0	\N	\N	\N	f
208	01/12/11-Thu : GMSociety Thursday	18	43	2011-12-01	2011-12-01 09:00:00+11	2011-12-01 15:00:00+11	\N	\N	19	10	pending	Not-Banked	\N	0	\N	\N	\N	f
213	08/12/11-Thu : Soing Creative Dance Thursday	39	44	2011-12-08	2011-12-08 09:00:00+11	2011-12-08 15:00:00+11	\N	\N	10	9	pending	Not-Banked	\N	0	\N	\N	\N	f
214	15/12/11-Thu : Soing Creative Dance Thursday	39	44	2011-12-15	2011-12-15 09:00:00+11	2011-12-15 15:00:00+11	\N	\N	10	9	pending	Not-Banked	\N	0	\N	\N	\N	f
215	22/12/11-Thu : Soing Creative Dance Thursday	39	44	2011-12-22	2011-12-22 09:00:00+11	2011-12-22 15:00:00+11	\N	\N	10	9	pending	Not-Banked	\N	0	\N	\N	\N	f
212	01/12/11-Thu : Soing Creative Dance Thursday	39	44	2011-12-01	2011-12-01 09:00:00+11	2011-12-01 15:00:00+11	\N	\N	10	9	pending	Not-Banked	\N	0	\N	\N	\N	f
216	01/12/11-Thu : Hi Guys Thursday 	15	45	2011-12-01	2011-12-01 09:00:00+11	2011-12-01 15:00:00+11	\N	\N	57	9	pending	Not-Banked	\N	0	\N	\N	\N	f
217	08/12/11-Thu : Hi Guys Thursday 	15	45	2011-12-08	2011-12-08 09:00:00+11	2011-12-08 15:00:00+11	\N	\N	57	9	pending	Not-Banked	\N	0	\N	\N	\N	f
218	15/12/11-Thu : Hi Guys Thursday 	15	45	2011-12-15	2011-12-15 09:00:00+11	2011-12-15 15:00:00+11	\N	\N	57	9	pending	Not-Banked	\N	0	\N	\N	\N	f
219	22/12/11-Thu : Hi Guys Thursday 	15	45	2011-12-22	2011-12-22 09:00:00+11	2011-12-22 15:00:00+11	\N	\N	57	9	pending	Not-Banked	\N	0	\N	\N	\N	f
221	08/12/11-Thu : Band Thursday	40	46	2011-12-08	2011-12-08 09:00:00+11	2011-12-08 15:00:00+11	\N	\N	10	10	pending	Not-Banked	\N	0	\N	\N	\N	f
222	15/12/11-Thu : Band Thursday	40	46	2011-12-15	2011-12-15 09:00:00+11	2011-12-15 15:00:00+11	\N	\N	10	10	pending	Not-Banked	\N	0	\N	\N	\N	f
223	22/12/11-Thu : Band Thursday	40	46	2011-12-22	2011-12-22 09:00:00+11	2011-12-22 15:00:00+11	\N	\N	10	10	pending	Not-Banked	\N	0	\N	\N	\N	f
224	01/12/11-Thu : Scrapbooking Thursday	41	47	2011-12-01	2011-12-01 09:00:00+11	2011-12-01 15:00:00+11	\N	\N	19	3	pending	Not-Banked	\N	0	\N	\N	\N	f
225	08/12/11-Thu : Scrapbooking Thursday	41	47	2011-12-08	2011-12-08 09:00:00+11	2011-12-08 15:00:00+11	\N	\N	19	3	pending	Not-Banked	\N	0	\N	\N	\N	f
226	15/12/11-Thu : Scrapbooking Thursday	41	47	2011-12-15	2011-12-15 09:00:00+11	2011-12-15 15:00:00+11	\N	\N	19	3	pending	Not-Banked	\N	0	\N	\N	\N	f
227	22/12/11-Thu : Scrapbooking Thursday	41	47	2011-12-22	2011-12-22 09:00:00+11	2011-12-22 15:00:00+11	\N	\N	19	3	pending	Not-Banked	\N	0	\N	\N	\N	f
228	01/12/11-Thu : Fitness Thursday	42	48	2011-12-01	2011-12-01 09:00:00+11	2011-12-01 15:00:00+11	\N	\N	57	9	pending	Not-Banked	\N	0	\N	\N	\N	f
229	08/12/11-Thu : Fitness Thursday	42	48	2011-12-08	2011-12-08 09:00:00+11	2011-12-08 15:00:00+11	\N	\N	57	9	pending	Not-Banked	\N	0	\N	\N	\N	f
230	15/12/11-Thu : Fitness Thursday	42	48	2011-12-15	2011-12-15 09:00:00+11	2011-12-15 15:00:00+11	\N	\N	57	9	pending	Not-Banked	\N	0	\N	\N	\N	f
231	22/12/11-Thu : Fitness Thursday	42	48	2011-12-22	2011-12-22 09:00:00+11	2011-12-22 15:00:00+11	\N	\N	57	9	pending	Not-Banked	\N	0	\N	\N	\N	f
232	01/12/11-Thu : L2L Thursday	11	49	2011-12-01	2011-11-30 23:00:00+11	2011-12-01 15:00:00+11	\N	\N	19	4	pending	Not-Banked	\N	0	\N	\N	\N	f
233	08/12/11-Thu : L2L Thursday	11	49	2011-12-08	2011-12-07 23:00:00+11	2011-12-08 15:00:00+11	\N	\N	19	4	pending	Not-Banked	\N	0	\N	\N	\N	f
234	15/12/11-Thu : L2L Thursday	11	49	2011-12-15	2011-12-14 23:00:00+11	2011-12-15 15:00:00+11	\N	\N	19	4	pending	Not-Banked	\N	0	\N	\N	\N	f
235	22/12/11-Thu : L2L Thursday	11	49	2011-12-22	2011-12-21 23:00:00+11	2011-12-22 15:00:00+11	\N	\N	19	4	pending	Not-Banked	\N	0	\N	\N	\N	f
252	02/12/11-Fri : CA Friday 1 Out about	46	54	2011-12-02	2011-12-02 09:00:00+11	2011-12-02 15:00:00+11	\N	\N	57	5	pending	Not-Banked	\N	0	\N	\N	\N	f
236	01/12/11-Thu : Coop Thursday	12	50	2011-12-01	2011-12-01 09:00:00+11	2011-12-01 15:00:00+11	\N	\N	51	7	pending	Not-Banked	\N	0	\N	\N	\N	f
237	08/12/11-Thu : Coop Thursday	12	50	2011-12-08	2011-12-08 09:00:00+11	2011-12-08 15:00:00+11	\N	\N	51	7	pending	Not-Banked	\N	0	\N	\N	\N	f
238	15/12/11-Thu : Coop Thursday	12	50	2011-12-15	2011-12-15 09:00:00+11	2011-12-15 15:00:00+11	\N	\N	51	7	pending	Not-Banked	\N	0	\N	\N	\N	f
239	22/12/11-Thu : Coop Thursday	12	50	2011-12-22	2011-12-22 09:00:00+11	2011-12-22 15:00:00+11	\N	\N	51	7	pending	Not-Banked	\N	0	\N	\N	\N	f
240	02/12/11-Fri : Options Theatre Co Friday	43	51	2011-12-02	2011-12-02 09:00:00+11	2011-12-02 15:00:00+11	\N	\N	10	10	pending	Not-Banked	\N	0	\N	\N	\N	f
241	09/12/11-Fri : Options Theatre Co Friday	43	51	2011-12-09	2011-12-09 09:00:00+11	2011-12-09 15:00:00+11	\N	\N	10	10	pending	Not-Banked	\N	0	\N	\N	\N	f
242	16/12/11-Fri : Options Theatre Co Friday	43	51	2011-12-16	2011-12-16 09:00:00+11	2011-12-16 15:00:00+11	\N	\N	10	10	pending	Not-Banked	\N	0	\N	\N	\N	f
243	23/12/11-Fri : Options Theatre Co Friday	43	51	2011-12-23	2011-12-23 09:00:00+11	2011-12-23 15:00:00+11	\N	\N	10	10	pending	Not-Banked	\N	0	\N	\N	\N	f
244	02/12/11-Fri : Puppets Friday	44	52	2011-12-02	2011-12-02 09:00:00+11	2011-12-02 15:00:00+11	\N	\N	10	10	pending	Not-Banked	\N	0	\N	\N	\N	f
245	09/12/11-Fri : Puppets Friday	44	52	2011-12-09	2011-12-09 09:00:00+11	2011-12-09 15:00:00+11	\N	\N	10	10	pending	Not-Banked	\N	0	\N	\N	\N	f
246	16/12/11-Fri : Puppets Friday	44	52	2011-12-16	2011-12-16 09:00:00+11	2011-12-16 15:00:00+11	\N	\N	10	10	pending	Not-Banked	\N	0	\N	\N	\N	f
247	23/12/11-Fri : Puppets Friday	44	52	2011-12-23	2011-12-23 09:00:00+11	2011-12-23 15:00:00+11	\N	\N	10	10	pending	Not-Banked	\N	0	\N	\N	\N	f
248	02/12/11-Fri : Props Friday	45	53	2011-12-02	2011-12-02 09:00:00+11	2011-12-02 15:00:00+11	\N	\N	10	10	pending	Not-Banked	\N	0	\N	\N	\N	f
249	09/12/11-Fri : Props Friday	45	53	2011-12-09	2011-12-09 09:00:00+11	2011-12-09 15:00:00+11	\N	\N	10	10	pending	Not-Banked	\N	0	\N	\N	\N	f
250	16/12/11-Fri : Props Friday	45	53	2011-12-16	2011-12-16 09:00:00+11	2011-12-16 15:00:00+11	\N	\N	10	10	pending	Not-Banked	\N	0	\N	\N	\N	f
251	23/12/11-Fri : Props Friday	45	53	2011-12-23	2011-12-23 09:00:00+11	2011-12-23 15:00:00+11	\N	\N	10	10	pending	Not-Banked	\N	0	\N	\N	\N	f
253	09/12/11-Fri : CA Friday 1 Out about	46	54	2011-12-09	2011-12-09 09:00:00+11	2011-12-09 15:00:00+11	\N	\N	57	5	pending	Not-Banked	\N	0	\N	\N	\N	f
254	16/12/11-Fri : CA Friday 1 Out about	46	54	2011-12-16	2011-12-16 09:00:00+11	2011-12-16 15:00:00+11	\N	\N	57	5	pending	Not-Banked	\N	0	\N	\N	\N	f
255	23/12/11-Fri : CA Friday 1 Out about	46	54	2011-12-23	2011-12-23 09:00:00+11	2011-12-23 15:00:00+11	\N	\N	57	5	pending	Not-Banked	\N	0	\N	\N	\N	f
260	02/12/11-Fri : CA The Gang Friday	48	56	2011-12-02	2011-12-01 23:00:00+11	2011-12-02 15:00:00+11	\N	\N	57	5	pending	Not-Banked	\N	0	\N	\N	\N	f
220	01/12/11-Thu : Band Thursday	40	46	2011-12-01	2011-12-01 09:00:00+11	2011-12-01 15:00:00+11	\N	\N	10	10		Not-Banked	\N	0	2012-01-18	2012-01-18	\N	f
265	09/12/11-Fri : CA The Gang Friday	48	56	2011-12-09	2011-12-08 23:00:00+11	2011-12-09 15:00:00+11	\N	\N	57	5	pending	Not-Banked	\N	0	\N	\N	\N	f
270	16/12/11-Fri : CA The Gang Friday	48	56	2011-12-16	2011-12-15 23:00:00+11	2011-12-16 15:00:00+11	\N	\N	57	5	pending	Not-Banked	\N	0	\N	\N	\N	f
275	23/12/11-Fri : CA The Gang Friday	48	56	2011-12-23	2011-12-22 23:00:00+11	2011-12-23 15:00:00+11	\N	\N	57	5	pending	Not-Banked	\N	0	\N	\N	\N	f
276	02/12/11-Fri : Coop Friday	12	57	2011-12-02	2011-12-02 09:00:00+11	2011-12-02 15:00:00+11	\N	\N	51	7	pending	Not-Banked	\N	0	\N	\N	\N	f
277	09/12/11-Fri : Coop Friday	12	57	2011-12-09	2011-12-09 09:00:00+11	2011-12-09 15:00:00+11	\N	\N	51	7	pending	Not-Banked	\N	0	\N	\N	\N	f
278	16/12/11-Fri : Coop Friday	12	57	2011-12-16	2011-12-16 09:00:00+11	2011-12-16 15:00:00+11	\N	\N	51	7	pending	Not-Banked	\N	0	\N	\N	\N	f
279	23/12/11-Fri : Coop Friday	12	57	2011-12-23	2011-12-23 09:00:00+11	2011-12-23 15:00:00+11	\N	\N	51	7	pending	Not-Banked	\N	0	\N	\N	\N	f
280	28/11/11-Mon : Transport 1 Monday AM	1	2	2011-11-28	2011-11-28 07:30:00+11	2011-11-28 09:00:00+11	\N	\N	53	\N	\N	Not-Banked	1	0	\N	\N	\N	f
281	05/12/11-Mon : Transport 1 Monday AM	1	2	2011-12-05	2011-12-05 07:30:00+11	2011-12-05 09:00:00+11	\N	\N	53	\N	\N	Not-Banked	1	0	\N	\N	\N	f
282	12/12/11-Mon : Transport 1 Monday AM	1	2	2011-12-12	2011-12-12 07:30:00+11	2011-12-12 09:00:00+11	\N	\N	53	\N	\N	Not-Banked	1	0	\N	\N	\N	f
283	19/12/11-Mon : Transport 1 Monday AM	1	2	2011-12-19	2011-12-19 07:30:00+11	2011-12-19 09:00:00+11	\N	\N	53	\N	\N	Not-Banked	1	0	\N	\N	\N	f
284	28/11/11-Mon : Transport 2 AM Monday	50	58	2011-11-28	2011-11-28 07:30:00+11	2011-11-28 09:00:00+11	\N	\N	38	\N	\N	Not-Banked	2	0	\N	\N	\N	f
285	05/12/11-Mon : Transport 2 AM Monday	50	58	2011-12-05	2011-12-05 07:30:00+11	2011-12-05 09:00:00+11	\N	\N	38	\N	\N	Not-Banked	2	0	\N	\N	\N	f
286	12/12/11-Mon : Transport 2 AM Monday	50	58	2011-12-12	2011-12-12 07:30:00+11	2011-12-12 09:00:00+11	\N	\N	38	\N	\N	Not-Banked	2	0	\N	\N	\N	f
287	19/12/11-Mon : Transport 2 AM Monday	50	58	2011-12-19	2011-12-19 07:30:00+11	2011-12-19 09:00:00+11	\N	\N	38	\N	\N	Not-Banked	2	0	\N	\N	\N	f
288	28/11/11-Mon : Transport 3 AM Monday	52	59	2011-11-28	2011-11-28 07:30:00+11	2011-11-28 09:00:00+11	\N	\N	22	\N	\N	Not-Banked	3	0	\N	\N	\N	f
289	05/12/11-Mon : Transport 3 AM Monday	52	59	2011-12-05	2011-12-05 07:30:00+11	2011-12-05 09:00:00+11	\N	\N	22	\N	\N	Not-Banked	3	0	\N	\N	\N	f
290	12/12/11-Mon : Transport 3 AM Monday	52	59	2011-12-12	2011-12-12 07:30:00+11	2011-12-12 09:00:00+11	\N	\N	22	\N	\N	Not-Banked	3	0	\N	\N	\N	f
291	19/12/11-Mon : Transport 3 AM Monday	52	59	2011-12-19	2011-12-19 07:30:00+11	2011-12-19 09:00:00+11	\N	\N	22	\N	\N	Not-Banked	3	0	\N	\N	\N	f
292	28/11/11-Mon : Transport 4 AM Monday	54	60	2011-11-28	2011-11-28 07:30:00+11	2011-11-28 09:00:00+11	\N	\N	32	\N	\N	Not-Banked	4	0	\N	\N	\N	f
293	05/12/11-Mon : Transport 4 AM Monday	54	60	2011-12-05	2011-12-05 07:30:00+11	2011-12-05 09:00:00+11	\N	\N	32	\N	\N	Not-Banked	4	0	\N	\N	\N	f
294	12/12/11-Mon : Transport 4 AM Monday	54	60	2011-12-12	2011-12-12 07:30:00+11	2011-12-12 09:00:00+11	\N	\N	32	\N	\N	Not-Banked	4	0	\N	\N	\N	f
295	19/12/11-Mon : Transport 4 AM Monday	54	60	2011-12-19	2011-12-19 07:30:00+11	2011-12-19 09:00:00+11	\N	\N	32	\N	\N	Not-Banked	4	0	\N	\N	\N	f
296	28/11/11-Mon : Transport 11 AM Monday	67	65	2011-11-28	2011-11-28 08:30:00+11	2011-11-28 09:00:00+11	\N	\N	49	\N	\N	Not-Banked	11	0	\N	\N	\N	f
297	05/12/11-Mon : Transport 11 AM Monday	67	65	2011-12-05	2011-12-05 08:30:00+11	2011-12-05 09:00:00+11	\N	\N	49	\N	\N	Not-Banked	11	0	\N	\N	\N	f
298	12/12/11-Mon : Transport 11 AM Monday	67	65	2011-12-12	2011-12-12 08:30:00+11	2011-12-12 09:00:00+11	\N	\N	49	\N	\N	Not-Banked	11	0	\N	\N	\N	f
299	19/12/11-Mon : Transport 11 AM Monday	67	65	2011-12-19	2011-12-19 08:30:00+11	2011-12-19 09:00:00+11	\N	\N	49	\N	\N	Not-Banked	11	0	\N	\N	\N	f
300	28/11/11-Mon : Transport 10 AM Monday	65	64	2011-11-28	2011-11-28 08:30:00+11	2011-11-28 09:00:00+11	\N	\N	34	\N	\N	Not-Banked	10	0	\N	\N	\N	f
301	05/12/11-Mon : Transport 10 AM Monday	65	64	2011-12-05	2011-12-05 08:30:00+11	2011-12-05 09:00:00+11	\N	\N	34	\N	\N	Not-Banked	10	0	\N	\N	\N	f
302	12/12/11-Mon : Transport 10 AM Monday	65	64	2011-12-12	2011-12-12 08:30:00+11	2011-12-12 09:00:00+11	\N	\N	34	\N	\N	Not-Banked	10	0	\N	\N	\N	f
303	19/12/11-Mon : Transport 10 AM Monday	65	64	2011-12-19	2011-12-19 08:30:00+11	2011-12-19 09:00:00+11	\N	\N	34	\N	\N	Not-Banked	10	0	\N	\N	\N	f
304	28/11/11-Mon : Transport 9 AM Monday	64	63	2011-11-28	2011-11-28 07:30:00+11	2011-11-28 09:00:00+11	\N	\N	28	\N	\N	Not-Banked	9	0	\N	\N	\N	f
305	05/12/11-Mon : Transport 9 AM Monday	64	63	2011-12-05	2011-12-05 07:30:00+11	2011-12-05 09:00:00+11	\N	\N	28	\N	\N	Not-Banked	9	0	\N	\N	\N	f
306	12/12/11-Mon : Transport 9 AM Monday	64	63	2011-12-12	2011-12-12 07:30:00+11	2011-12-12 09:00:00+11	\N	\N	28	\N	\N	Not-Banked	9	0	\N	\N	\N	f
307	19/12/11-Mon : Transport 9 AM Monday	64	63	2011-12-19	2011-12-19 07:30:00+11	2011-12-19 09:00:00+11	\N	\N	28	\N	\N	Not-Banked	9	0	\N	\N	\N	f
308	28/11/11-Mon : Transport 7 AM Monday	60	66	2011-11-28	2011-11-28 08:00:00+11	2011-11-28 09:00:00+11	\N	\N	35	\N	\N	Not-Banked	7	0	\N	\N	\N	f
309	05/12/11-Mon : Transport 7 AM Monday	60	66	2011-12-05	2011-12-05 08:00:00+11	2011-12-05 09:00:00+11	\N	\N	35	\N	\N	Not-Banked	7	0	\N	\N	\N	f
310	12/12/11-Mon : Transport 7 AM Monday	60	66	2011-12-12	2011-12-12 08:00:00+11	2011-12-12 09:00:00+11	\N	\N	35	\N	\N	Not-Banked	7	0	\N	\N	\N	f
311	19/12/11-Mon : Transport 7 AM Monday	60	66	2011-12-19	2011-12-19 08:00:00+11	2011-12-19 09:00:00+11	\N	\N	35	\N	\N	Not-Banked	7	0	\N	\N	\N	f
312	28/11/11-Mon : Transport 1 PM Monday	49	67	2011-11-28	2011-11-28 15:00:00+11	2011-11-28 16:30:00+11	\N	\N	53	\N	\N	Not-Banked	1	0	\N	\N	\N	f
313	05/12/11-Mon : Transport 1 PM Monday	49	67	2011-12-05	2011-12-05 15:00:00+11	2011-12-05 16:30:00+11	\N	\N	53	\N	\N	Not-Banked	1	0	\N	\N	\N	f
314	12/12/11-Mon : Transport 1 PM Monday	49	67	2011-12-12	2011-12-12 15:00:00+11	2011-12-12 16:30:00+11	\N	\N	53	\N	\N	Not-Banked	1	0	\N	\N	\N	f
315	19/12/11-Mon : Transport 1 PM Monday	49	67	2011-12-19	2011-12-19 15:00:00+11	2011-12-19 16:30:00+11	\N	\N	53	\N	\N	Not-Banked	1	0	\N	\N	\N	f
316	28/11/11-Mon : Transport 3 PM Monday	53	69	2011-11-28	2011-11-28 15:00:00+11	2011-11-28 16:30:00+11	\N	\N	22	\N	\N	Not-Banked	3	0	\N	\N	\N	f
317	05/12/11-Mon : Transport 3 PM Monday	53	69	2011-12-05	2011-12-05 15:00:00+11	2011-12-05 16:30:00+11	\N	\N	22	\N	\N	Not-Banked	3	0	\N	\N	\N	f
318	12/12/11-Mon : Transport 3 PM Monday	53	69	2011-12-12	2011-12-12 15:00:00+11	2011-12-12 16:30:00+11	\N	\N	22	\N	\N	Not-Banked	3	0	\N	\N	\N	f
319	19/12/11-Mon : Transport 3 PM Monday	53	69	2011-12-19	2011-12-19 15:00:00+11	2011-12-19 16:30:00+11	\N	\N	22	\N	\N	Not-Banked	3	0	\N	\N	\N	f
320	28/11/11-Mon : Transport 2 PM Monday	51	68	2011-11-28	2011-11-28 15:00:00+11	2011-11-28 16:30:00+11	\N	\N	38	\N	\N	Not-Banked	2	0	\N	\N	\N	f
321	05/12/11-Mon : Transport 2 PM Monday	51	68	2011-12-05	2011-12-05 15:00:00+11	2011-12-05 16:30:00+11	\N	\N	38	\N	\N	Not-Banked	2	0	\N	\N	\N	f
322	12/12/11-Mon : Transport 2 PM Monday	51	68	2011-12-12	2011-12-12 15:00:00+11	2011-12-12 16:30:00+11	\N	\N	38	\N	\N	Not-Banked	2	0	\N	\N	\N	f
323	19/12/11-Mon : Transport 2 PM Monday	51	68	2011-12-19	2011-12-19 15:00:00+11	2011-12-19 16:30:00+11	\N	\N	38	\N	\N	Not-Banked	2	0	\N	\N	\N	f
324	28/11/11-Mon : Transport 4 PM Monday	55	70	2011-11-28	2011-11-28 15:00:00+11	2011-11-28 16:30:00+11	\N	\N	32	\N	\N	Not-Banked	4	0	\N	\N	\N	f
325	05/12/11-Mon : Transport 4 PM Monday	55	70	2011-12-05	2011-12-05 15:00:00+11	2011-12-05 16:30:00+11	\N	\N	32	\N	\N	Not-Banked	4	0	\N	\N	\N	f
326	12/12/11-Mon : Transport 4 PM Monday	55	70	2011-12-12	2011-12-12 15:00:00+11	2011-12-12 16:30:00+11	\N	\N	32	\N	\N	Not-Banked	4	0	\N	\N	\N	f
327	19/12/11-Mon : Transport 4 PM Monday	55	70	2011-12-19	2011-12-19 15:00:00+11	2011-12-19 16:30:00+11	\N	\N	32	\N	\N	Not-Banked	4	0	\N	\N	\N	f
328	28/11/11-Mon : Transport 5 PM Monday	57	71	2011-11-28	2011-11-28 15:00:00+11	2011-11-28 16:30:00+11	\N	\N	47	\N	\N	Not-Banked	5	0	\N	\N	\N	f
329	05/12/11-Mon : Transport 5 PM Monday	57	71	2011-12-05	2011-12-05 15:00:00+11	2011-12-05 16:30:00+11	\N	\N	47	\N	\N	Not-Banked	5	0	\N	\N	\N	f
330	12/12/11-Mon : Transport 5 PM Monday	57	71	2011-12-12	2011-12-12 15:00:00+11	2011-12-12 16:30:00+11	\N	\N	47	\N	\N	Not-Banked	5	0	\N	\N	\N	f
331	19/12/11-Mon : Transport 5 PM Monday	57	71	2011-12-19	2011-12-19 15:00:00+11	2011-12-19 16:30:00+11	\N	\N	47	\N	\N	Not-Banked	5	0	\N	\N	\N	f
332	28/11/11-Mon : Transport 6 PM Monday	59	72	2011-11-28	2011-11-28 15:00:00+11	2011-11-28 16:30:00+11	\N	\N	24	\N	\N	Not-Banked	6	0	\N	\N	\N	f
333	05/12/11-Mon : Transport 6 PM Monday	59	72	2011-12-05	2011-12-05 15:00:00+11	2011-12-05 16:30:00+11	\N	\N	24	\N	\N	Not-Banked	6	0	\N	\N	\N	f
334	12/12/11-Mon : Transport 6 PM Monday	59	72	2011-12-12	2011-12-12 15:00:00+11	2011-12-12 16:30:00+11	\N	\N	24	\N	\N	Not-Banked	6	0	\N	\N	\N	f
335	19/12/11-Mon : Transport 6 PM Monday	59	72	2011-12-19	2011-12-19 15:00:00+11	2011-12-19 16:30:00+11	\N	\N	24	\N	\N	Not-Banked	6	0	\N	\N	\N	f
336	28/11/11-Mon : Transport 7 PM Monday	61	73	2011-11-28	2011-11-28 15:00:00+11	2011-11-28 16:00:00+11	\N	\N	35	\N	\N	Not-Banked	7	0	\N	\N	\N	f
337	05/12/11-Mon : Transport 7 PM Monday	61	73	2011-12-05	2011-12-05 15:00:00+11	2011-12-05 16:00:00+11	\N	\N	35	\N	\N	Not-Banked	7	0	\N	\N	\N	f
338	12/12/11-Mon : Transport 7 PM Monday	61	73	2011-12-12	2011-12-12 15:00:00+11	2011-12-12 16:00:00+11	\N	\N	35	\N	\N	Not-Banked	7	0	\N	\N	\N	f
339	19/12/11-Mon : Transport 7 PM Monday	61	73	2011-12-19	2011-12-19 15:00:00+11	2011-12-19 16:00:00+11	\N	\N	35	\N	\N	Not-Banked	7	0	\N	\N	\N	f
340	28/11/11-Mon : Transport 9 PM Monday	157	75	2011-11-28	2011-11-28 15:00:00+11	2011-11-28 16:00:00+11	\N	\N	28	\N	\N	Not-Banked	9	0	\N	\N	\N	f
341	05/12/11-Mon : Transport 9 PM Monday	157	75	2011-12-05	2011-12-05 15:00:00+11	2011-12-05 16:00:00+11	\N	\N	28	\N	\N	Not-Banked	9	0	\N	\N	\N	f
342	12/12/11-Mon : Transport 9 PM Monday	157	75	2011-12-12	2011-12-12 15:00:00+11	2011-12-12 16:00:00+11	\N	\N	28	\N	\N	Not-Banked	9	0	\N	\N	\N	f
343	19/12/11-Mon : Transport 9 PM Monday	157	75	2011-12-19	2011-12-19 15:00:00+11	2011-12-19 16:00:00+11	\N	\N	28	\N	\N	Not-Banked	9	0	\N	\N	\N	f
344	28/11/11-Mon : Transport 10 PM Monday	66	76	2011-11-28	2011-11-28 14:45:00+11	2011-11-28 15:30:00+11	\N	\N	34	\N	\N	Not-Banked	10	0	\N	\N	\N	f
345	05/12/11-Mon : Transport 10 PM Monday	66	76	2011-12-05	2011-12-05 14:45:00+11	2011-12-05 15:30:00+11	\N	\N	34	\N	\N	Not-Banked	10	0	\N	\N	\N	f
346	12/12/11-Mon : Transport 10 PM Monday	66	76	2011-12-12	2011-12-12 14:45:00+11	2011-12-12 15:30:00+11	\N	\N	34	\N	\N	Not-Banked	10	0	\N	\N	\N	f
347	19/12/11-Mon : Transport 10 PM Monday	66	76	2011-12-19	2011-12-19 14:45:00+11	2011-12-19 15:30:00+11	\N	\N	34	\N	\N	Not-Banked	10	0	\N	\N	\N	f
348	28/11/11-Mon : Transport 11 PM Monday	68	77	2011-11-28	2011-11-28 15:00:00+11	2011-11-28 15:30:00+11	\N	\N	49	\N	\N	Not-Banked	11	0	\N	\N	\N	f
349	05/12/11-Mon : Transport 11 PM Monday	68	77	2011-12-05	2011-12-05 15:00:00+11	2011-12-05 15:30:00+11	\N	\N	49	\N	\N	Not-Banked	11	0	\N	\N	\N	f
350	12/12/11-Mon : Transport 11 PM Monday	68	77	2011-12-12	2011-12-12 15:00:00+11	2011-12-12 15:30:00+11	\N	\N	49	\N	\N	Not-Banked	11	0	\N	\N	\N	f
351	19/12/11-Mon : Transport 11 PM Monday	68	77	2011-12-19	2011-12-19 15:00:00+11	2011-12-19 15:30:00+11	\N	\N	49	\N	\N	Not-Banked	11	0	\N	\N	\N	f
352	29/11/11-Tue : Transport 1 AM Tuesday	69	78	2011-11-29	2011-11-29 07:30:00+11	2011-11-29 09:00:00+11	\N	\N	8	\N	\N	Not-Banked	1	0	\N	\N	\N	f
353	06/12/11-Tue : Transport 1 AM Tuesday	69	78	2011-12-06	2011-12-06 07:30:00+11	2011-12-06 09:00:00+11	\N	\N	8	\N	\N	Not-Banked	1	0	\N	\N	\N	f
354	13/12/11-Tue : Transport 1 AM Tuesday	69	78	2011-12-13	2011-12-13 07:30:00+11	2011-12-13 09:00:00+11	\N	\N	8	\N	\N	Not-Banked	1	0	\N	\N	\N	f
355	20/12/11-Tue : Transport 1 AM Tuesday	69	78	2011-12-20	2011-12-20 07:30:00+11	2011-12-20 09:00:00+11	\N	\N	8	\N	\N	Not-Banked	1	0	\N	\N	\N	f
356	29/11/11-Tue : Transport 2 AM Tuesday	71	79	2011-11-29	2011-11-29 07:30:00+11	2011-11-29 09:00:00+11	\N	\N	24	\N	\N	Not-Banked	2	0	\N	\N	\N	f
357	06/12/11-Tue : Transport 2 AM Tuesday	71	79	2011-12-06	2011-12-06 07:30:00+11	2011-12-06 09:00:00+11	\N	\N	24	\N	\N	Not-Banked	2	0	\N	\N	\N	f
358	13/12/11-Tue : Transport 2 AM Tuesday	71	79	2011-12-13	2011-12-13 07:30:00+11	2011-12-13 09:00:00+11	\N	\N	24	\N	\N	Not-Banked	2	0	\N	\N	\N	f
359	20/12/11-Tue : Transport 2 AM Tuesday	71	79	2011-12-20	2011-12-20 07:30:00+11	2011-12-20 09:00:00+11	\N	\N	24	\N	\N	Not-Banked	2	0	\N	\N	\N	f
360	29/11/11-Tue : Transport 6 AM Tuesday	79	82	2011-11-29	2011-11-29 08:00:00+11	2011-11-29 09:00:00+11	\N	\N	14	\N	\N	Not-Banked	6	0	\N	\N	\N	f
361	06/12/11-Tue : Transport 6 AM Tuesday	79	82	2011-12-06	2011-12-06 08:00:00+11	2011-12-06 09:00:00+11	\N	\N	14	\N	\N	Not-Banked	6	0	\N	\N	\N	f
362	13/12/11-Tue : Transport 6 AM Tuesday	79	82	2011-12-13	2011-12-13 08:00:00+11	2011-12-13 09:00:00+11	\N	\N	14	\N	\N	Not-Banked	6	0	\N	\N	\N	f
363	20/12/11-Tue : Transport 6 AM Tuesday	79	82	2011-12-20	2011-12-20 08:00:00+11	2011-12-20 09:00:00+11	\N	\N	14	\N	\N	Not-Banked	6	0	\N	\N	\N	f
364	29/11/11-Tue : Transport 5 AM Tuesday	77	81	2011-11-29	2011-11-29 07:30:00+11	2011-11-29 09:00:00+11	\N	\N	47	\N	\N	Not-Banked	5	0	\N	\N	\N	f
365	06/12/11-Tue : Transport 5 AM Tuesday	77	81	2011-12-06	2011-12-06 07:30:00+11	2011-12-06 09:00:00+11	\N	\N	47	\N	\N	Not-Banked	5	0	\N	\N	\N	f
366	13/12/11-Tue : Transport 5 AM Tuesday	77	81	2011-12-13	2011-12-13 07:30:00+11	2011-12-13 09:00:00+11	\N	\N	47	\N	\N	Not-Banked	5	0	\N	\N	\N	f
367	20/12/11-Tue : Transport 5 AM Tuesday	77	81	2011-12-20	2011-12-20 07:30:00+11	2011-12-20 09:00:00+11	\N	\N	47	\N	\N	Not-Banked	5	0	\N	\N	\N	f
368	29/11/11-Tue : Transport 7 AM Tuesday	81	83	2011-11-29	2011-11-29 08:00:00+11	2011-11-29 09:00:00+11	\N	\N	25	\N	\N	Not-Banked	7	0	\N	\N	\N	f
369	06/12/11-Tue : Transport 7 AM Tuesday	81	83	2011-12-06	2011-12-06 08:00:00+11	2011-12-06 09:00:00+11	\N	\N	25	\N	\N	Not-Banked	7	0	\N	\N	\N	f
370	13/12/11-Tue : Transport 7 AM Tuesday	81	83	2011-12-13	2011-12-13 08:00:00+11	2011-12-13 09:00:00+11	\N	\N	25	\N	\N	Not-Banked	7	0	\N	\N	\N	f
371	20/12/11-Tue : Transport 7 AM Tuesday	81	83	2011-12-20	2011-12-20 08:00:00+11	2011-12-20 09:00:00+11	\N	\N	25	\N	\N	Not-Banked	7	0	\N	\N	\N	f
372	29/11/11-Tue : Transport 8 AM Tuesday	83	84	2011-11-29	2011-11-29 08:00:00+11	2011-11-29 09:00:00+11	\N	\N	16	\N	\N	Not-Banked	8	0	\N	\N	\N	f
373	06/12/11-Tue : Transport 8 AM Tuesday	83	84	2011-12-06	2011-12-06 08:00:00+11	2011-12-06 09:00:00+11	\N	\N	16	\N	\N	Not-Banked	8	0	\N	\N	\N	f
374	13/12/11-Tue : Transport 8 AM Tuesday	83	84	2011-12-13	2011-12-13 08:00:00+11	2011-12-13 09:00:00+11	\N	\N	16	\N	\N	Not-Banked	8	0	\N	\N	\N	f
375	20/12/11-Tue : Transport 8 AM Tuesday	83	84	2011-12-20	2011-12-20 08:00:00+11	2011-12-20 09:00:00+11	\N	\N	16	\N	\N	Not-Banked	8	0	\N	\N	\N	f
376	29/11/11-Tue : Transport 9 AM Tuesday	85	85	2011-11-29	2011-11-29 07:30:00+11	2011-11-29 09:00:00+11	\N	\N	22	\N	\N	Not-Banked	9	0	\N	\N	\N	f
377	06/12/11-Tue : Transport 9 AM Tuesday	85	85	2011-12-06	2011-12-06 07:30:00+11	2011-12-06 09:00:00+11	\N	\N	22	\N	\N	Not-Banked	9	0	\N	\N	\N	f
378	13/12/11-Tue : Transport 9 AM Tuesday	85	85	2011-12-13	2011-12-13 07:30:00+11	2011-12-13 09:00:00+11	\N	\N	22	\N	\N	Not-Banked	9	0	\N	\N	\N	f
379	20/12/11-Tue : Transport 9 AM Tuesday	85	85	2011-12-20	2011-12-20 07:30:00+11	2011-12-20 09:00:00+11	\N	\N	22	\N	\N	Not-Banked	9	0	\N	\N	\N	f
380	29/11/11-Tue : Transport 10 AM Tuesday	87	86	2011-11-29	2011-11-29 08:30:00+11	2011-11-29 09:00:00+11	\N	\N	13	\N	\N	Not-Banked	10	0	\N	\N	\N	f
381	06/12/11-Tue : Transport 10 AM Tuesday	87	86	2011-12-06	2011-12-06 08:30:00+11	2011-12-06 09:00:00+11	\N	\N	13	\N	\N	Not-Banked	10	0	\N	\N	\N	f
382	13/12/11-Tue : Transport 10 AM Tuesday	87	86	2011-12-13	2011-12-13 08:30:00+11	2011-12-13 09:00:00+11	\N	\N	13	\N	\N	Not-Banked	10	0	\N	\N	\N	f
383	20/12/11-Tue : Transport 10 AM Tuesday	87	86	2011-12-20	2011-12-20 08:30:00+11	2011-12-20 09:00:00+11	\N	\N	13	\N	\N	Not-Banked	10	0	\N	\N	\N	f
388	29/11/11-Tue : Transport 2 PM Tuesday	72	88	2011-11-29	2011-11-29 15:00:00+11	2011-11-29 16:30:00+11	\N	\N	24	\N	\N	Not-Banked	2	0	\N	\N	\N	f
389	06/12/11-Tue : Transport 2 PM Tuesday	72	88	2011-12-06	2011-12-06 15:00:00+11	2011-12-06 16:30:00+11	\N	\N	24	\N	\N	Not-Banked	2	0	\N	\N	\N	f
390	13/12/11-Tue : Transport 2 PM Tuesday	72	88	2011-12-13	2011-12-13 15:00:00+11	2011-12-13 16:30:00+11	\N	\N	24	\N	\N	Not-Banked	2	0	\N	\N	\N	f
391	20/12/11-Tue : Transport 2 PM Tuesday	72	88	2011-12-20	2011-12-20 15:00:00+11	2011-12-20 16:30:00+11	\N	\N	24	\N	\N	Not-Banked	2	0	\N	\N	\N	f
384	29/11/11-Tue : Transport 1 PM Tuesday	70	87	2011-11-29	2011-11-29 15:00:00+11	2011-11-29 16:30:00+11	\N	\N	8	\N	pending	Not-Banked	1	0	\N	\N	\N	f
385	06/12/11-Tue : Transport 1 PM Tuesday	70	87	2011-12-06	2011-12-06 15:00:00+11	2011-12-06 16:30:00+11	\N	\N	8	\N	pending	Not-Banked	1	0	\N	\N	\N	f
386	13/12/11-Tue : Transport 1 PM Tuesday	70	87	2011-12-13	2011-12-13 15:00:00+11	2011-12-13 16:30:00+11	\N	\N	8	\N	pending	Not-Banked	1	0	\N	\N	\N	f
387	20/12/11-Tue : Transport 1 PM Tuesday	70	87	2011-12-20	2011-12-20 15:00:00+11	2011-12-20 16:30:00+11	\N	\N	8	\N	pending	Not-Banked	1	0	\N	\N	\N	f
392	29/11/11-Tue : Transport 3 PM Tuesday	74	89	2011-11-29	2011-11-29 15:00:00+11	2011-11-29 16:00:00+11	\N	\N	9	\N	\N	Not-Banked	3	0	\N	\N	\N	f
394	06/12/11-Tue : Transport 3 PM Tuesday	74	89	2011-12-06	2011-12-06 15:00:00+11	2011-12-06 16:00:00+11	\N	\N	9	\N	\N	Not-Banked	3	0	\N	\N	\N	f
396	13/12/11-Tue : Transport 3 PM Tuesday	74	89	2011-12-13	2011-12-13 15:00:00+11	2011-12-13 16:00:00+11	\N	\N	9	\N	\N	Not-Banked	3	0	\N	\N	\N	f
398	20/12/11-Tue : Transport 3 PM Tuesday	74	89	2011-12-20	2011-12-20 15:00:00+11	2011-12-20 16:00:00+11	\N	\N	9	\N	\N	Not-Banked	3	0	\N	\N	\N	f
399	29/11/11-Tue : Transport 5 PM Tuesday	78	90	2011-11-29	2011-11-29 15:00:00+11	2011-11-29 16:30:00+11	\N	\N	47	\N	\N	Not-Banked	5	0	\N	\N	\N	f
400	06/12/11-Tue : Transport 5 PM Tuesday	78	90	2011-12-06	2011-12-06 15:00:00+11	2011-12-06 16:30:00+11	\N	\N	47	\N	\N	Not-Banked	5	0	\N	\N	\N	f
401	13/12/11-Tue : Transport 5 PM Tuesday	78	90	2011-12-13	2011-12-13 15:00:00+11	2011-12-13 16:30:00+11	\N	\N	47	\N	\N	Not-Banked	5	0	\N	\N	\N	f
402	20/12/11-Tue : Transport 5 PM Tuesday	78	90	2011-12-20	2011-12-20 15:00:00+11	2011-12-20 16:30:00+11	\N	\N	47	\N	\N	Not-Banked	5	0	\N	\N	\N	f
403	29/11/11-Tue : Transport 6 PM Tuesday	80	91	2011-11-29	2011-11-29 15:00:00+11	2011-11-29 16:00:00+11	\N	\N	14	\N	\N	Not-Banked	6	0	\N	\N	\N	f
404	06/12/11-Tue : Transport 6 PM Tuesday	80	91	2011-12-06	2011-12-06 15:00:00+11	2011-12-06 16:00:00+11	\N	\N	14	\N	\N	Not-Banked	6	0	\N	\N	\N	f
405	13/12/11-Tue : Transport 6 PM Tuesday	80	91	2011-12-13	2011-12-13 15:00:00+11	2011-12-13 16:00:00+11	\N	\N	14	\N	\N	Not-Banked	6	0	\N	\N	\N	f
406	20/12/11-Tue : Transport 6 PM Tuesday	80	91	2011-12-20	2011-12-20 15:00:00+11	2011-12-20 16:00:00+11	\N	\N	14	\N	\N	Not-Banked	6	0	\N	\N	\N	f
407	29/11/11-Tue : Transport 8 PM Tuesday	84	93	2011-11-29	2011-11-29 15:00:00+11	2011-11-29 16:00:00+11	\N	\N	16	\N	\N	Not-Banked	8	0	\N	\N	\N	f
408	06/12/11-Tue : Transport 8 PM Tuesday	84	93	2011-12-06	2011-12-06 15:00:00+11	2011-12-06 16:00:00+11	\N	\N	16	\N	\N	Not-Banked	8	0	\N	\N	\N	f
409	13/12/11-Tue : Transport 8 PM Tuesday	84	93	2011-12-13	2011-12-13 15:00:00+11	2011-12-13 16:00:00+11	\N	\N	16	\N	\N	Not-Banked	8	0	\N	\N	\N	f
410	20/12/11-Tue : Transport 8 PM Tuesday	84	93	2011-12-20	2011-12-20 15:00:00+11	2011-12-20 16:00:00+11	\N	\N	16	\N	\N	Not-Banked	8	0	\N	\N	\N	f
411	29/11/11-Tue : Transport 9 PM Tuesday	86	94	2011-11-29	2011-11-29 15:00:00+11	2011-11-29 16:30:00+11	\N	\N	22	\N	\N	Not-Banked	9	0	\N	\N	\N	f
412	06/12/11-Tue : Transport 9 PM Tuesday	86	94	2011-12-06	2011-12-06 15:00:00+11	2011-12-06 16:30:00+11	\N	\N	22	\N	\N	Not-Banked	9	0	\N	\N	\N	f
413	13/12/11-Tue : Transport 9 PM Tuesday	86	94	2011-12-13	2011-12-13 15:00:00+11	2011-12-13 16:30:00+11	\N	\N	22	\N	\N	Not-Banked	9	0	\N	\N	\N	f
414	20/12/11-Tue : Transport 9 PM Tuesday	86	94	2011-12-20	2011-12-20 15:00:00+11	2011-12-20 16:30:00+11	\N	\N	22	\N	\N	Not-Banked	9	0	\N	\N	\N	f
415	29/11/11-Tue : Transport 10 PM Tuesday	88	95	2011-11-29	2011-11-29 14:45:00+11	2011-11-29 15:30:00+11	\N	\N	13	\N	\N	Not-Banked	10	0	\N	\N	\N	f
416	06/12/11-Tue : Transport 10 PM Tuesday	88	95	2011-12-06	2011-12-06 14:45:00+11	2011-12-06 15:30:00+11	\N	\N	13	\N	\N	Not-Banked	10	0	\N	\N	\N	f
417	13/12/11-Tue : Transport 10 PM Tuesday	88	95	2011-12-13	2011-12-13 14:45:00+11	2011-12-13 15:30:00+11	\N	\N	13	\N	\N	Not-Banked	10	0	\N	\N	\N	f
418	20/12/11-Tue : Transport 10 PM Tuesday	88	95	2011-12-20	2011-12-20 14:45:00+11	2011-12-20 15:30:00+11	\N	\N	13	\N	\N	Not-Banked	10	0	\N	\N	\N	f
419	30/11/11-Wed : Transport 1 AM Wednesday	91	96	2011-11-30	2011-11-30 07:30:00+11	2011-11-30 09:00:00+11	\N	\N	25	\N	\N	Not-Banked	1	0	\N	\N	\N	f
420	07/12/11-Wed : Transport 1 AM Wednesday	91	96	2011-12-07	2011-12-07 07:30:00+11	2011-12-07 09:00:00+11	\N	\N	25	\N	\N	Not-Banked	1	0	\N	\N	\N	f
421	14/12/11-Wed : Transport 1 AM Wednesday	91	96	2011-12-14	2011-12-14 07:30:00+11	2011-12-14 09:00:00+11	\N	\N	25	\N	\N	Not-Banked	1	0	\N	\N	\N	f
422	21/12/11-Wed : Transport 1 AM Wednesday	91	96	2011-12-21	2011-12-21 07:30:00+11	2011-12-21 09:00:00+11	\N	\N	25	\N	\N	Not-Banked	1	0	\N	\N	\N	f
423	30/11/11-Wed : Transport 2 AM Wednesday	93	97	2011-11-30	2011-11-30 07:30:00+11	2011-11-30 09:00:00+11	\N	\N	9	\N	\N	Not-Banked	2	0	\N	\N	\N	f
424	07/12/11-Wed : Transport 2 AM Wednesday	93	97	2011-12-07	2011-12-07 07:30:00+11	2011-12-07 09:00:00+11	\N	\N	9	\N	\N	Not-Banked	2	0	\N	\N	\N	f
425	14/12/11-Wed : Transport 2 AM Wednesday	93	97	2011-12-14	2011-12-14 07:30:00+11	2011-12-14 09:00:00+11	\N	\N	9	\N	\N	Not-Banked	2	0	\N	\N	\N	f
426	21/12/11-Wed : Transport 2 AM Wednesday	93	97	2011-12-21	2011-12-21 07:30:00+11	2011-12-21 09:00:00+11	\N	\N	9	\N	\N	Not-Banked	2	0	\N	\N	\N	f
427	30/11/11-Wed : Transport 3 AM Wednesday	95	98	2011-11-30	2011-11-30 07:30:00+11	2011-11-30 09:00:00+11	\N	\N	54	\N	\N	Not-Banked	3	0	\N	\N	\N	f
428	07/12/11-Wed : Transport 3 AM Wednesday	95	98	2011-12-07	2011-12-07 07:30:00+11	2011-12-07 09:00:00+11	\N	\N	54	\N	\N	Not-Banked	3	0	\N	\N	\N	f
429	14/12/11-Wed : Transport 3 AM Wednesday	95	98	2011-12-14	2011-12-14 07:30:00+11	2011-12-14 09:00:00+11	\N	\N	54	\N	\N	Not-Banked	3	0	\N	\N	\N	f
430	21/12/11-Wed : Transport 3 AM Wednesday	95	98	2011-12-21	2011-12-21 07:30:00+11	2011-12-21 09:00:00+11	\N	\N	54	\N	\N	Not-Banked	3	0	\N	\N	\N	f
431	30/11/11-Wed : Transport 4 AM Wednesday	97	99	2011-11-30	2011-11-30 07:30:00+11	2011-11-30 09:00:00+11	\N	\N	53	\N	\N	Not-Banked	4	0	\N	\N	\N	f
432	07/12/11-Wed : Transport 4 AM Wednesday	97	99	2011-12-07	2011-12-07 07:30:00+11	2011-12-07 09:00:00+11	\N	\N	53	\N	\N	Not-Banked	4	0	\N	\N	\N	f
433	14/12/11-Wed : Transport 4 AM Wednesday	97	99	2011-12-14	2011-12-14 07:30:00+11	2011-12-14 09:00:00+11	\N	\N	53	\N	\N	Not-Banked	4	0	\N	\N	\N	f
434	21/12/11-Wed : Transport 4 AM Wednesday	97	99	2011-12-21	2011-12-21 07:30:00+11	2011-12-21 09:00:00+11	\N	\N	53	\N	\N	Not-Banked	4	0	\N	\N	\N	f
435	30/11/11-Wed : Transport 5 AM Wednesday	99	100	2011-11-30	2011-11-30 07:30:00+11	2011-11-30 09:00:00+11	\N	\N	12	\N	\N	Not-Banked	5	0	\N	\N	\N	f
436	07/12/11-Wed : Transport 5 AM Wednesday	99	100	2011-12-07	2011-12-07 07:30:00+11	2011-12-07 09:00:00+11	\N	\N	12	\N	\N	Not-Banked	5	0	\N	\N	\N	f
437	14/12/11-Wed : Transport 5 AM Wednesday	99	100	2011-12-14	2011-12-14 07:30:00+11	2011-12-14 09:00:00+11	\N	\N	12	\N	\N	Not-Banked	5	0	\N	\N	\N	f
438	21/12/11-Wed : Transport 5 AM Wednesday	99	100	2011-12-21	2011-12-21 07:30:00+11	2011-12-21 09:00:00+11	\N	\N	12	\N	\N	Not-Banked	5	0	\N	\N	\N	f
439	30/11/11-Wed : Transport 9 AM Wednesday	107	102	2011-11-30	2011-11-30 07:30:00+11	2011-11-30 09:00:00+11	\N	\N	16	\N	\N	Not-Banked	9	0	\N	\N	\N	f
440	07/12/11-Wed : Transport 9 AM Wednesday	107	102	2011-12-07	2011-12-07 07:30:00+11	2011-12-07 09:00:00+11	\N	\N	16	\N	\N	Not-Banked	9	0	\N	\N	\N	f
441	14/12/11-Wed : Transport 9 AM Wednesday	107	102	2011-12-14	2011-12-14 07:30:00+11	2011-12-14 09:00:00+11	\N	\N	16	\N	\N	Not-Banked	9	0	\N	\N	\N	f
442	21/12/11-Wed : Transport 9 AM Wednesday	107	102	2011-12-21	2011-12-21 07:30:00+11	2011-12-21 09:00:00+11	\N	\N	16	\N	\N	Not-Banked	9	0	\N	\N	\N	f
443	30/11/11-Wed : Transport 10 AM Wednesday	109	103	2011-11-30	2011-11-30 08:30:00+11	2011-11-30 09:30:00+11	\N	\N	31	\N	\N	Not-Banked	10	0	\N	\N	\N	f
444	07/12/11-Wed : Transport 10 AM Wednesday	109	103	2011-12-07	2011-12-07 08:30:00+11	2011-12-07 09:30:00+11	\N	\N	31	\N	\N	Not-Banked	10	0	\N	\N	\N	f
445	14/12/11-Wed : Transport 10 AM Wednesday	109	103	2011-12-14	2011-12-14 08:30:00+11	2011-12-14 09:30:00+11	\N	\N	31	\N	\N	Not-Banked	10	0	\N	\N	\N	f
446	21/12/11-Wed : Transport 10 AM Wednesday	109	103	2011-12-21	2011-12-21 08:30:00+11	2011-12-21 09:30:00+11	\N	\N	31	\N	\N	Not-Banked	10	0	\N	\N	\N	f
447	30/11/11-Wed : Transport 11 AM Wednesday	111	104	2011-11-30	2011-11-30 08:30:00+11	2011-11-30 09:30:00+11	\N	\N	49	\N	\N	Not-Banked	11	0	\N	\N	\N	f
448	07/12/11-Wed : Transport 11 AM Wednesday	111	104	2011-12-07	2011-12-07 08:30:00+11	2011-12-07 09:30:00+11	\N	\N	49	\N	\N	Not-Banked	11	0	\N	\N	\N	f
449	14/12/11-Wed : Transport 11 AM Wednesday	111	104	2011-12-14	2011-12-14 08:30:00+11	2011-12-14 09:30:00+11	\N	\N	49	\N	\N	Not-Banked	11	0	\N	\N	\N	f
450	21/12/11-Wed : Transport 11 AM Wednesday	111	104	2011-12-21	2011-12-21 08:30:00+11	2011-12-21 09:30:00+11	\N	\N	49	\N	\N	Not-Banked	11	0	\N	\N	\N	f
451	30/11/11-Wed : Transport 1 PM Wednesday	92	105	2011-11-30	2011-11-30 15:00:00+11	2011-11-30 16:30:00+11	\N	\N	25	\N	\N	Not-Banked	1	0	\N	\N	\N	f
452	07/12/11-Wed : Transport 1 PM Wednesday	92	105	2011-12-07	2011-12-07 15:00:00+11	2011-12-07 16:30:00+11	\N	\N	25	\N	\N	Not-Banked	1	0	\N	\N	\N	f
453	14/12/11-Wed : Transport 1 PM Wednesday	92	105	2011-12-14	2011-12-14 15:00:00+11	2011-12-14 16:30:00+11	\N	\N	25	\N	\N	Not-Banked	1	0	\N	\N	\N	f
454	21/12/11-Wed : Transport 1 PM Wednesday	92	105	2011-12-21	2011-12-21 15:00:00+11	2011-12-21 16:30:00+11	\N	\N	25	\N	\N	Not-Banked	1	0	\N	\N	\N	f
455	30/11/11-Wed : Transport 2 PM Wednesday	94	106	2011-11-30	2011-11-30 15:00:00+11	2011-11-30 16:30:00+11	\N	\N	9	\N	\N	Not-Banked	2	0	\N	\N	\N	f
456	07/12/11-Wed : Transport 2 PM Wednesday	94	106	2011-12-07	2011-12-07 15:00:00+11	2011-12-07 16:30:00+11	\N	\N	9	\N	\N	Not-Banked	2	0	\N	\N	\N	f
457	14/12/11-Wed : Transport 2 PM Wednesday	94	106	2011-12-14	2011-12-14 15:00:00+11	2011-12-14 16:30:00+11	\N	\N	9	\N	\N	Not-Banked	2	0	\N	\N	\N	f
458	21/12/11-Wed : Transport 2 PM Wednesday	94	106	2011-12-21	2011-12-21 15:00:00+11	2011-12-21 16:30:00+11	\N	\N	9	\N	\N	Not-Banked	2	0	\N	\N	\N	f
459	30/11/11-Wed : Transport 3 PM Wednesday	96	107	2011-11-30	2011-11-30 15:00:00+11	2011-11-30 16:30:00+11	\N	\N	54	\N	\N	Not-Banked	3	0	\N	\N	\N	f
460	07/12/11-Wed : Transport 3 PM Wednesday	96	107	2011-12-07	2011-12-07 15:00:00+11	2011-12-07 16:30:00+11	\N	\N	54	\N	\N	Not-Banked	3	0	\N	\N	\N	f
461	14/12/11-Wed : Transport 3 PM Wednesday	96	107	2011-12-14	2011-12-14 15:00:00+11	2011-12-14 16:30:00+11	\N	\N	54	\N	\N	Not-Banked	3	0	\N	\N	\N	f
462	21/12/11-Wed : Transport 3 PM Wednesday	96	107	2011-12-21	2011-12-21 15:00:00+11	2011-12-21 16:30:00+11	\N	\N	54	\N	\N	Not-Banked	3	0	\N	\N	\N	f
463	30/11/11-Wed : Transport 4 PM Wednesday	98	108	2011-11-30	2011-11-30 15:00:00+11	2011-11-30 16:30:00+11	\N	\N	53	\N	\N	Not-Banked	4	0	\N	\N	\N	f
464	07/12/11-Wed : Transport 4 PM Wednesday	98	108	2011-12-07	2011-12-07 15:00:00+11	2011-12-07 16:30:00+11	\N	\N	53	\N	\N	Not-Banked	4	0	\N	\N	\N	f
465	14/12/11-Wed : Transport 4 PM Wednesday	98	108	2011-12-14	2011-12-14 15:00:00+11	2011-12-14 16:30:00+11	\N	\N	53	\N	\N	Not-Banked	4	0	\N	\N	\N	f
466	21/12/11-Wed : Transport 4 PM Wednesday	98	108	2011-12-21	2011-12-21 15:00:00+11	2011-12-21 16:30:00+11	\N	\N	53	\N	\N	Not-Banked	4	0	\N	\N	\N	f
467	30/11/11-Wed : Transport 5 PM Wednesday	100	109	2011-11-30	2011-11-30 15:00:00+11	2011-11-30 16:00:00+11	\N	\N	12	\N	\N	Not-Banked	5	0	\N	\N	\N	f
468	07/12/11-Wed : Transport 5 PM Wednesday	100	109	2011-12-07	2011-12-07 15:00:00+11	2011-12-07 16:00:00+11	\N	\N	12	\N	\N	Not-Banked	5	0	\N	\N	\N	f
469	14/12/11-Wed : Transport 5 PM Wednesday	100	109	2011-12-14	2011-12-14 15:00:00+11	2011-12-14 16:00:00+11	\N	\N	12	\N	\N	Not-Banked	5	0	\N	\N	\N	f
470	21/12/11-Wed : Transport 5 PM Wednesday	100	109	2011-12-21	2011-12-21 15:00:00+11	2011-12-21 16:00:00+11	\N	\N	12	\N	\N	Not-Banked	5	0	\N	\N	\N	f
471	30/11/11-Wed : Transport 8 PM Wednesday	106	110	2011-11-30	2011-11-30 15:00:00+11	2011-11-30 16:30:00+11	\N	\N	8	\N	\N	Not-Banked	7	0	\N	\N	\N	f
472	07/12/11-Wed : Transport 8 PM Wednesday	106	110	2011-12-07	2011-12-07 15:00:00+11	2011-12-07 16:30:00+11	\N	\N	8	\N	\N	Not-Banked	7	0	\N	\N	\N	f
473	14/12/11-Wed : Transport 8 PM Wednesday	106	110	2011-12-14	2011-12-14 15:00:00+11	2011-12-14 16:30:00+11	\N	\N	8	\N	\N	Not-Banked	7	0	\N	\N	\N	f
474	21/12/11-Wed : Transport 8 PM Wednesday	106	110	2011-12-21	2011-12-21 15:00:00+11	2011-12-21 16:30:00+11	\N	\N	8	\N	\N	Not-Banked	7	0	\N	\N	\N	f
475	30/11/11-Wed : Transport 9 PM Wednesday	108	111	2011-11-30	2011-11-30 15:00:00+11	2011-11-30 15:30:00+11	\N	\N	16	\N	\N	Not-Banked	10	0	\N	\N	\N	f
476	07/12/11-Wed : Transport 9 PM Wednesday	108	111	2011-12-07	2011-12-07 15:00:00+11	2011-12-07 15:30:00+11	\N	\N	16	\N	\N	Not-Banked	10	0	\N	\N	\N	f
477	14/12/11-Wed : Transport 9 PM Wednesday	108	111	2011-12-14	2011-12-14 15:00:00+11	2011-12-14 15:30:00+11	\N	\N	16	\N	\N	Not-Banked	10	0	\N	\N	\N	f
478	21/12/11-Wed : Transport 9 PM Wednesday	108	111	2011-12-21	2011-12-21 15:00:00+11	2011-12-21 15:30:00+11	\N	\N	16	\N	\N	Not-Banked	10	0	\N	\N	\N	f
479	30/11/11-Wed : Transport 10 PM Wednesday	110	112	2011-11-30	2011-11-30 14:45:00+11	2011-11-30 15:30:00+11	\N	\N	31	\N	\N	Not-Banked	10	0	\N	\N	\N	f
480	07/12/11-Wed : Transport 10 PM Wednesday	110	112	2011-12-07	2011-12-07 14:45:00+11	2011-12-07 15:30:00+11	\N	\N	31	\N	\N	Not-Banked	10	0	\N	\N	\N	f
481	14/12/11-Wed : Transport 10 PM Wednesday	110	112	2011-12-14	2011-12-14 14:45:00+11	2011-12-14 15:30:00+11	\N	\N	31	\N	\N	Not-Banked	10	0	\N	\N	\N	f
482	21/12/11-Wed : Transport 10 PM Wednesday	110	112	2011-12-21	2011-12-21 14:45:00+11	2011-12-21 15:30:00+11	\N	\N	31	\N	\N	Not-Banked	10	0	\N	\N	\N	f
483	23/11/11-Wed : Transport 11 PM Wednesday	112	113	2011-11-23	2011-11-23 15:00:00+11	2011-11-23 15:30:00+11	\N	\N	49	\N	\N	Not-Banked	11	0	\N	\N	\N	f
484	30/11/11-Wed : Transport 11 PM Wednesday	112	113	2011-11-30	2011-11-30 15:00:00+11	2011-11-30 15:30:00+11	\N	\N	49	\N	\N	Not-Banked	11	0	\N	\N	\N	f
485	07/12/11-Wed : Transport 11 PM Wednesday	112	113	2011-12-07	2011-12-07 15:00:00+11	2011-12-07 15:30:00+11	\N	\N	49	\N	\N	Not-Banked	11	0	\N	\N	\N	f
486	14/12/11-Wed : Transport 11 PM Wednesday	112	113	2011-12-14	2011-12-14 15:00:00+11	2011-12-14 15:30:00+11	\N	\N	49	\N	\N	Not-Banked	11	0	\N	\N	\N	f
487	21/12/11-Wed : Transport 11 PM Wednesday	112	113	2011-12-21	2011-12-21 15:00:00+11	2011-12-21 15:30:00+11	\N	\N	49	\N	\N	Not-Banked	11	0	\N	\N	\N	f
488	01/12/11-Thu : Transport 1 AM Thursday	113	114	2011-12-01	2011-12-01 07:30:00+11	2011-12-01 09:00:00+11	\N	\N	47	\N	\N	Not-Banked	1	0	\N	\N	\N	f
489	08/12/11-Thu : Transport 1 AM Thursday	113	114	2011-12-08	2011-12-08 07:30:00+11	2011-12-08 09:00:00+11	\N	\N	47	\N	\N	Not-Banked	1	0	\N	\N	\N	f
490	15/12/11-Thu : Transport 1 AM Thursday	113	114	2011-12-15	2011-12-15 07:30:00+11	2011-12-15 09:00:00+11	\N	\N	47	\N	\N	Not-Banked	1	0	\N	\N	\N	f
491	22/12/11-Thu : Transport 1 AM Thursday	113	114	2011-12-22	2011-12-22 07:30:00+11	2011-12-22 09:00:00+11	\N	\N	47	\N	\N	Not-Banked	1	0	\N	\N	\N	f
492	01/12/11-Thu : Transport 2 AM Thursday	115	115	2011-12-01	2011-12-01 07:30:00+11	2011-12-01 09:00:00+11	\N	\N	53	\N	\N	Not-Banked	2	0	\N	\N	\N	f
493	08/12/11-Thu : Transport 2 AM Thursday	115	115	2011-12-08	2011-12-08 07:30:00+11	2011-12-08 09:00:00+11	\N	\N	53	\N	\N	Not-Banked	2	0	\N	\N	\N	f
494	15/12/11-Thu : Transport 2 AM Thursday	115	115	2011-12-15	2011-12-15 07:30:00+11	2011-12-15 09:00:00+11	\N	\N	53	\N	\N	Not-Banked	2	0	\N	\N	\N	f
495	22/12/11-Thu : Transport 2 AM Thursday	115	115	2011-12-22	2011-12-22 07:30:00+11	2011-12-22 09:00:00+11	\N	\N	53	\N	\N	Not-Banked	2	0	\N	\N	\N	f
496	01/12/11-Thu : Transport 3 AM Thursday	117	116	2011-12-01	2011-12-01 07:30:00+11	2011-12-01 09:00:00+11	\N	\N	13	\N	\N	Not-Banked	3	0	\N	\N	\N	f
497	08/12/11-Thu : Transport 3 AM Thursday	117	116	2011-12-08	2011-12-08 07:30:00+11	2011-12-08 09:00:00+11	\N	\N	13	\N	\N	Not-Banked	3	0	\N	\N	\N	f
498	15/12/11-Thu : Transport 3 AM Thursday	117	116	2011-12-15	2011-12-15 07:30:00+11	2011-12-15 09:00:00+11	\N	\N	13	\N	\N	Not-Banked	3	0	\N	\N	\N	f
499	22/12/11-Thu : Transport 3 AM Thursday	117	116	2011-12-22	2011-12-22 07:30:00+11	2011-12-22 09:00:00+11	\N	\N	13	\N	\N	Not-Banked	3	0	\N	\N	\N	f
500	01/12/11-Thu : Transport 4 AM Thursday	119	117	2011-12-01	2011-12-01 07:30:00+11	2011-12-01 09:00:00+11	\N	\N	31	\N	\N	Not-Banked	4	0	\N	\N	\N	f
501	08/12/11-Thu : Transport 4 AM Thursday	119	117	2011-12-08	2011-12-08 07:30:00+11	2011-12-08 09:00:00+11	\N	\N	31	\N	\N	Not-Banked	4	0	\N	\N	\N	f
502	15/12/11-Thu : Transport 4 AM Thursday	119	117	2011-12-15	2011-12-15 07:30:00+11	2011-12-15 09:00:00+11	\N	\N	31	\N	\N	Not-Banked	4	0	\N	\N	\N	f
503	22/12/11-Thu : Transport 4 AM Thursday	119	117	2011-12-22	2011-12-22 07:30:00+11	2011-12-22 09:00:00+11	\N	\N	31	\N	\N	Not-Banked	4	0	\N	\N	\N	f
504	01/12/11-Thu : Transport 5 AM Thursday	121	118	2011-12-01	2011-12-01 07:30:00+11	2011-12-01 09:00:00+11	\N	\N	32	\N	\N	Not-Banked	5	0	\N	\N	\N	f
505	08/12/11-Thu : Transport 5 AM Thursday	121	118	2011-12-08	2011-12-08 07:30:00+11	2011-12-08 09:00:00+11	\N	\N	32	\N	\N	Not-Banked	5	0	\N	\N	\N	f
506	15/12/11-Thu : Transport 5 AM Thursday	121	118	2011-12-15	2011-12-15 07:30:00+11	2011-12-15 09:00:00+11	\N	\N	32	\N	\N	Not-Banked	5	0	\N	\N	\N	f
507	22/12/11-Thu : Transport 5 AM Thursday	121	118	2011-12-22	2011-12-22 07:30:00+11	2011-12-22 09:00:00+11	\N	\N	32	\N	\N	Not-Banked	5	0	\N	\N	\N	f
508	01/12/11-Thu : Transport 6 AM Thursday	123	119	2011-12-01	2011-12-01 08:00:00+11	2011-12-01 09:00:00+11	\N	\N	9	\N	\N	Not-Banked	6	0	\N	\N	\N	f
509	08/12/11-Thu : Transport 6 AM Thursday	123	119	2011-12-08	2011-12-08 08:00:00+11	2011-12-08 09:00:00+11	\N	\N	9	\N	\N	Not-Banked	6	0	\N	\N	\N	f
510	15/12/11-Thu : Transport 6 AM Thursday	123	119	2011-12-15	2011-12-15 08:00:00+11	2011-12-15 09:00:00+11	\N	\N	9	\N	\N	Not-Banked	6	0	\N	\N	\N	f
511	22/12/11-Thu : Transport 6 AM Thursday	123	119	2011-12-22	2011-12-22 08:00:00+11	2011-12-22 09:00:00+11	\N	\N	9	\N	\N	Not-Banked	6	0	\N	\N	\N	f
512	01/12/11-Thu : Transport 7 AM Thursday	125	120	2011-12-01	2011-12-01 08:00:00+11	2011-12-01 09:00:00+11	\N	\N	25	\N	\N	Not-Banked	7	0	\N	\N	\N	f
513	08/12/11-Thu : Transport 7 AM Thursday	125	120	2011-12-08	2011-12-08 08:00:00+11	2011-12-08 09:00:00+11	\N	\N	25	\N	\N	Not-Banked	7	0	\N	\N	\N	f
514	15/12/11-Thu : Transport 7 AM Thursday	125	120	2011-12-15	2011-12-15 08:00:00+11	2011-12-15 09:00:00+11	\N	\N	25	\N	\N	Not-Banked	7	0	\N	\N	\N	f
515	22/12/11-Thu : Transport 7 AM Thursday	125	120	2011-12-22	2011-12-22 08:00:00+11	2011-12-22 09:00:00+11	\N	\N	25	\N	\N	Not-Banked	7	0	\N	\N	\N	f
516	01/12/11-Thu : Transport 8 AM Thursday	127	121	2011-12-01	2011-12-01 08:00:00+11	2011-12-01 09:00:00+11	\N	\N	35	\N	\N	Not-Banked	8	0	\N	\N	\N	f
517	08/12/11-Thu : Transport 8 AM Thursday	127	121	2011-12-08	2011-12-08 08:00:00+11	2011-12-08 09:00:00+11	\N	\N	35	\N	\N	Not-Banked	8	0	\N	\N	\N	f
518	15/12/11-Thu : Transport 8 AM Thursday	127	121	2011-12-15	2011-12-15 08:00:00+11	2011-12-15 09:00:00+11	\N	\N	35	\N	\N	Not-Banked	8	0	\N	\N	\N	f
519	22/12/11-Thu : Transport 8 AM Thursday	127	121	2011-12-22	2011-12-22 08:00:00+11	2011-12-22 09:00:00+11	\N	\N	35	\N	\N	Not-Banked	8	0	\N	\N	\N	f
520	01/12/11-Thu : Transport 9 AM Thursday	129	122	2011-12-01	2011-12-01 07:30:00+11	2011-12-01 09:00:00+11	\N	\N	16	\N	\N	Not-Banked	9	0	\N	\N	\N	f
521	08/12/11-Thu : Transport 9 AM Thursday	129	122	2011-12-08	2011-12-08 07:30:00+11	2011-12-08 09:00:00+11	\N	\N	16	\N	\N	Not-Banked	9	0	\N	\N	\N	f
522	15/12/11-Thu : Transport 9 AM Thursday	129	122	2011-12-15	2011-12-15 07:30:00+11	2011-12-15 09:00:00+11	\N	\N	16	\N	\N	Not-Banked	9	0	\N	\N	\N	f
523	22/12/11-Thu : Transport 9 AM Thursday	129	122	2011-12-22	2011-12-22 07:30:00+11	2011-12-22 09:00:00+11	\N	\N	16	\N	\N	Not-Banked	9	0	\N	\N	\N	f
524	01/12/11-Thu : Transport 11 AM Thursday	133	124	2011-12-01	2011-12-01 08:30:00+11	2011-12-01 09:30:00+11	\N	\N	51	\N	\N	Not-Banked	11	0	\N	\N	\N	f
525	08/12/11-Thu : Transport 11 AM Thursday	133	124	2011-12-08	2011-12-08 08:30:00+11	2011-12-08 09:30:00+11	\N	\N	51	\N	\N	Not-Banked	11	0	\N	\N	\N	f
526	15/12/11-Thu : Transport 11 AM Thursday	133	124	2011-12-15	2011-12-15 08:30:00+11	2011-12-15 09:30:00+11	\N	\N	51	\N	\N	Not-Banked	11	0	\N	\N	\N	f
527	22/12/11-Thu : Transport 11 AM Thursday	133	124	2011-12-22	2011-12-22 08:30:00+11	2011-12-22 09:30:00+11	\N	\N	51	\N	\N	Not-Banked	11	0	\N	\N	\N	f
528	01/12/11-Thu : Transport 1 PM Thursday	114	125	2011-12-01	2011-12-01 15:00:00+11	2011-12-01 16:30:00+11	\N	\N	47	\N	\N	Not-Banked	1	0	\N	\N	\N	f
529	08/12/11-Thu : Transport 1 PM Thursday	114	125	2011-12-08	2011-12-08 15:00:00+11	2011-12-08 16:30:00+11	\N	\N	47	\N	\N	Not-Banked	1	0	\N	\N	\N	f
530	15/12/11-Thu : Transport 1 PM Thursday	114	125	2011-12-15	2011-12-15 15:00:00+11	2011-12-15 16:30:00+11	\N	\N	47	\N	\N	Not-Banked	1	0	\N	\N	\N	f
531	22/12/11-Thu : Transport 1 PM Thursday	114	125	2011-12-22	2011-12-22 15:00:00+11	2011-12-22 16:30:00+11	\N	\N	47	\N	\N	Not-Banked	1	0	\N	\N	\N	f
532	01/12/11-Thu : Transport 2 PM Thursday	116	126	2011-12-01	2011-12-01 15:00:00+11	2011-12-01 16:00:00+11	\N	\N	53	\N	\N	Not-Banked	2	0	\N	\N	\N	f
533	08/12/11-Thu : Transport 2 PM Thursday	116	126	2011-12-08	2011-12-08 15:00:00+11	2011-12-08 16:00:00+11	\N	\N	53	\N	\N	Not-Banked	2	0	\N	\N	\N	f
534	15/12/11-Thu : Transport 2 PM Thursday	116	126	2011-12-15	2011-12-15 15:00:00+11	2011-12-15 16:00:00+11	\N	\N	53	\N	\N	Not-Banked	2	0	\N	\N	\N	f
535	22/12/11-Thu : Transport 2 PM Thursday	116	126	2011-12-22	2011-12-22 15:00:00+11	2011-12-22 16:00:00+11	\N	\N	53	\N	\N	Not-Banked	2	0	\N	\N	\N	f
536	01/12/11-Thu : Transport 3 PM Thursday	118	127	2011-12-01	2011-12-01 15:00:00+11	2011-12-01 16:30:00+11	\N	\N	13	\N	\N	Not-Banked	3	0	\N	\N	\N	f
537	08/12/11-Thu : Transport 3 PM Thursday	118	127	2011-12-08	2011-12-08 15:00:00+11	2011-12-08 16:30:00+11	\N	\N	13	\N	\N	Not-Banked	3	0	\N	\N	\N	f
538	15/12/11-Thu : Transport 3 PM Thursday	118	127	2011-12-15	2011-12-15 15:00:00+11	2011-12-15 16:30:00+11	\N	\N	13	\N	\N	Not-Banked	3	0	\N	\N	\N	f
539	22/12/11-Thu : Transport 3 PM Thursday	118	127	2011-12-22	2011-12-22 15:00:00+11	2011-12-22 16:30:00+11	\N	\N	13	\N	\N	Not-Banked	3	0	\N	\N	\N	f
540	01/12/11-Thu : Transport 4 PM Thursday	120	128	2011-12-01	2011-12-01 15:00:00+11	2011-12-01 16:30:00+11	\N	\N	31	\N	\N	Not-Banked	4	0	\N	\N	\N	f
541	08/12/11-Thu : Transport 4 PM Thursday	120	128	2011-12-08	2011-12-08 15:00:00+11	2011-12-08 16:30:00+11	\N	\N	31	\N	\N	Not-Banked	4	0	\N	\N	\N	f
542	15/12/11-Thu : Transport 4 PM Thursday	120	128	2011-12-15	2011-12-15 15:00:00+11	2011-12-15 16:30:00+11	\N	\N	31	\N	\N	Not-Banked	4	0	\N	\N	\N	f
543	22/12/11-Thu : Transport 4 PM Thursday	120	128	2011-12-22	2011-12-22 15:00:00+11	2011-12-22 16:30:00+11	\N	\N	31	\N	\N	Not-Banked	4	0	\N	\N	\N	f
544	01/12/11-Thu : Transport 6 PM Thursday	124	130	2011-12-01	2011-12-01 15:00:00+11	2011-12-01 16:00:00+11	\N	\N	9	\N	\N	Not-Banked	6	0	\N	\N	\N	f
545	08/12/11-Thu : Transport 6 PM Thursday	124	130	2011-12-08	2011-12-08 15:00:00+11	2011-12-08 16:00:00+11	\N	\N	9	\N	\N	Not-Banked	6	0	\N	\N	\N	f
546	15/12/11-Thu : Transport 6 PM Thursday	124	130	2011-12-15	2011-12-15 15:00:00+11	2011-12-15 16:00:00+11	\N	\N	9	\N	\N	Not-Banked	6	0	\N	\N	\N	f
547	22/12/11-Thu : Transport 6 PM Thursday	124	130	2011-12-22	2011-12-22 15:00:00+11	2011-12-22 16:00:00+11	\N	\N	9	\N	\N	Not-Banked	6	0	\N	\N	\N	f
548	01/12/11-Thu : Transport 5 PM Thursday	122	129	2011-12-01	2011-12-01 15:00:00+11	2011-12-01 16:30:00+11	\N	\N	32	\N	\N	Not-Banked	5	0	\N	\N	\N	f
549	08/12/11-Thu : Transport 5 PM Thursday	122	129	2011-12-08	2011-12-08 15:00:00+11	2011-12-08 16:30:00+11	\N	\N	32	\N	\N	Not-Banked	5	0	\N	\N	\N	f
550	15/12/11-Thu : Transport 5 PM Thursday	122	129	2011-12-15	2011-12-15 15:00:00+11	2011-12-15 16:30:00+11	\N	\N	32	\N	\N	Not-Banked	5	0	\N	\N	\N	f
551	22/12/11-Thu : Transport 5 PM Thursday	122	129	2011-12-22	2011-12-22 15:00:00+11	2011-12-22 16:30:00+11	\N	\N	32	\N	\N	Not-Banked	5	0	\N	\N	\N	f
552	01/12/11-Thu : Transport 7 PM Thursday	126	131	2011-12-01	2011-12-01 15:00:00+11	2011-12-01 16:00:00+11	\N	\N	25	\N	\N	Not-Banked	7	0	\N	\N	\N	f
553	08/12/11-Thu : Transport 7 PM Thursday	126	131	2011-12-08	2011-12-08 15:00:00+11	2011-12-08 16:00:00+11	\N	\N	25	\N	\N	Not-Banked	7	0	\N	\N	\N	f
554	15/12/11-Thu : Transport 7 PM Thursday	126	131	2011-12-15	2011-12-15 15:00:00+11	2011-12-15 16:00:00+11	\N	\N	25	\N	\N	Not-Banked	7	0	\N	\N	\N	f
555	22/12/11-Thu : Transport 7 PM Thursday	126	131	2011-12-22	2011-12-22 15:00:00+11	2011-12-22 16:00:00+11	\N	\N	25	\N	\N	Not-Banked	7	0	\N	\N	\N	f
556	01/12/11-Thu : Transport 8 PM Thursday	128	132	2011-12-01	2011-12-01 15:00:00+11	2011-12-01 16:30:00+11	\N	\N	35	\N	\N	Not-Banked	8	0	\N	\N	\N	f
557	08/12/11-Thu : Transport 8 PM Thursday	128	132	2011-12-08	2011-12-08 15:00:00+11	2011-12-08 16:30:00+11	\N	\N	35	\N	\N	Not-Banked	8	0	\N	\N	\N	f
558	15/12/11-Thu : Transport 8 PM Thursday	128	132	2011-12-15	2011-12-15 15:00:00+11	2011-12-15 16:30:00+11	\N	\N	35	\N	\N	Not-Banked	8	0	\N	\N	\N	f
559	22/12/11-Thu : Transport 8 PM Thursday	128	132	2011-12-22	2011-12-22 15:00:00+11	2011-12-22 16:30:00+11	\N	\N	35	\N	\N	Not-Banked	8	0	\N	\N	\N	f
560	01/12/11-Thu : Transport 9 PM Thursday	130	133	2011-12-01	2011-12-01 15:00:00+11	2011-12-01 15:30:00+11	\N	\N	16	\N	\N	Not-Banked	9	0	\N	\N	\N	f
561	08/12/11-Thu : Transport 9 PM Thursday	130	133	2011-12-08	2011-12-08 15:00:00+11	2011-12-08 15:30:00+11	\N	\N	16	\N	\N	Not-Banked	9	0	\N	\N	\N	f
562	15/12/11-Thu : Transport 9 PM Thursday	130	133	2011-12-15	2011-12-15 15:00:00+11	2011-12-15 15:30:00+11	\N	\N	16	\N	\N	Not-Banked	9	0	\N	\N	\N	f
563	22/12/11-Thu : Transport 9 PM Thursday	130	133	2011-12-22	2011-12-22 15:00:00+11	2011-12-22 15:30:00+11	\N	\N	16	\N	\N	Not-Banked	9	0	\N	\N	\N	f
564	01/12/11-Thu : Transport 10 PM Thursday	132	134	2011-12-01	2011-12-01 14:45:00+11	2011-12-01 15:30:00+11	\N	\N	59	\N	\N	Not-Banked	10	0	\N	\N	\N	f
565	08/12/11-Thu : Transport 10 PM Thursday	132	134	2011-12-08	2011-12-08 14:45:00+11	2011-12-08 15:30:00+11	\N	\N	59	\N	\N	Not-Banked	10	0	\N	\N	\N	f
566	15/12/11-Thu : Transport 10 PM Thursday	132	134	2011-12-15	2011-12-15 14:45:00+11	2011-12-15 15:30:00+11	\N	\N	59	\N	\N	Not-Banked	10	0	\N	\N	\N	f
567	22/12/11-Thu : Transport 10 PM Thursday	132	134	2011-12-22	2011-12-22 14:45:00+11	2011-12-22 15:30:00+11	\N	\N	59	\N	\N	Not-Banked	10	0	\N	\N	\N	f
568	01/12/11-Thu : Transport 11 PM Thursday	134	135	2011-12-01	2011-12-01 15:00:00+11	2011-12-01 15:30:00+11	\N	\N	51	\N	\N	Not-Banked	11	0	\N	\N	\N	f
569	08/12/11-Thu : Transport 11 PM Thursday	134	135	2011-12-08	2011-12-08 15:00:00+11	2011-12-08 15:30:00+11	\N	\N	51	\N	\N	Not-Banked	11	0	\N	\N	\N	f
570	15/12/11-Thu : Transport 11 PM Thursday	134	135	2011-12-15	2011-12-15 15:00:00+11	2011-12-15 15:30:00+11	\N	\N	51	\N	\N	Not-Banked	11	0	\N	\N	\N	f
571	22/12/11-Thu : Transport 11 PM Thursday	134	135	2011-12-22	2011-12-22 15:00:00+11	2011-12-22 15:30:00+11	\N	\N	51	\N	\N	Not-Banked	11	0	\N	\N	\N	f
572	02/12/11-Fri : Transport 1 AM Friday	135	136	2011-12-02	2011-12-02 07:30:00+11	2011-12-02 09:00:00+11	\N	\N	60	\N	\N	Not-Banked	1	0	\N	\N	\N	f
573	09/12/11-Fri : Transport 1 AM Friday	135	136	2011-12-09	2011-12-09 07:30:00+11	2011-12-09 09:00:00+11	\N	\N	60	\N	\N	Not-Banked	1	0	\N	\N	\N	f
574	16/12/11-Fri : Transport 1 AM Friday	135	136	2011-12-16	2011-12-16 07:30:00+11	2011-12-16 09:00:00+11	\N	\N	60	\N	\N	Not-Banked	1	0	\N	\N	\N	f
575	23/12/11-Fri : Transport 1 AM Friday	135	136	2011-12-23	2011-12-23 07:30:00+11	2011-12-23 09:00:00+11	\N	\N	60	\N	\N	Not-Banked	1	0	\N	\N	\N	f
576	02/12/11-Fri : Transport 2 AM Friday	137	137	2011-12-02	2011-12-02 07:30:00+11	2011-12-02 09:00:00+11	\N	\N	9	\N	\N	Not-Banked	2	0	\N	\N	\N	f
577	09/12/11-Fri : Transport 2 AM Friday	137	137	2011-12-09	2011-12-09 07:30:00+11	2011-12-09 09:00:00+11	\N	\N	9	\N	\N	Not-Banked	2	0	\N	\N	\N	f
578	16/12/11-Fri : Transport 2 AM Friday	137	137	2011-12-16	2011-12-16 07:30:00+11	2011-12-16 09:00:00+11	\N	\N	9	\N	\N	Not-Banked	2	0	\N	\N	\N	f
579	23/12/11-Fri : Transport 2 AM Friday	137	137	2011-12-23	2011-12-23 07:30:00+11	2011-12-23 09:00:00+11	\N	\N	9	\N	\N	Not-Banked	2	0	\N	\N	\N	f
580	02/12/11-Fri : Transport 3 AM Friday	139	138	2011-12-02	2011-12-02 07:30:00+11	2011-12-02 09:00:00+11	\N	\N	38	\N	\N	Not-Banked	3	0	\N	\N	\N	f
581	09/12/11-Fri : Transport 3 AM Friday	139	138	2011-12-09	2011-12-09 07:30:00+11	2011-12-09 09:00:00+11	\N	\N	38	\N	\N	Not-Banked	3	0	\N	\N	\N	f
582	16/12/11-Fri : Transport 3 AM Friday	139	138	2011-12-16	2011-12-16 07:30:00+11	2011-12-16 09:00:00+11	\N	\N	38	\N	\N	Not-Banked	3	0	\N	\N	\N	f
583	23/12/11-Fri : Transport 3 AM Friday	139	138	2011-12-23	2011-12-23 07:30:00+11	2011-12-23 09:00:00+11	\N	\N	38	\N	\N	Not-Banked	3	0	\N	\N	\N	f
584	02/12/11-Fri : Transport 4 AM Friday	141	139	2011-12-02	2011-12-02 07:30:00+11	2011-12-02 09:00:00+11	\N	\N	8	\N	\N	Not-Banked	4	0	\N	\N	\N	f
585	09/12/11-Fri : Transport 4 AM Friday	141	139	2011-12-09	2011-12-09 07:30:00+11	2011-12-09 09:00:00+11	\N	\N	8	\N	\N	Not-Banked	4	0	\N	\N	\N	f
586	16/12/11-Fri : Transport 4 AM Friday	141	139	2011-12-16	2011-12-16 07:30:00+11	2011-12-16 09:00:00+11	\N	\N	8	\N	\N	Not-Banked	4	0	\N	\N	\N	f
587	23/12/11-Fri : Transport 4 AM Friday	141	139	2011-12-23	2011-12-23 07:30:00+11	2011-12-23 09:00:00+11	\N	\N	8	\N	\N	Not-Banked	4	0	\N	\N	\N	f
588	02/12/11-Fri : Transport 5 AM Friday	143	140	2011-12-02	2011-12-02 07:30:00+11	2011-12-02 09:00:00+11	\N	\N	24	\N	\N	Not-Banked	5	0	\N	\N	\N	f
589	09/12/11-Fri : Transport 5 AM Friday	143	140	2011-12-09	2011-12-09 07:30:00+11	2011-12-09 09:00:00+11	\N	\N	24	\N	\N	Not-Banked	5	0	\N	\N	\N	f
590	16/12/11-Fri : Transport 5 AM Friday	143	140	2011-12-16	2011-12-16 07:30:00+11	2011-12-16 09:00:00+11	\N	\N	24	\N	\N	Not-Banked	5	0	\N	\N	\N	f
591	23/12/11-Fri : Transport 5 AM Friday	143	140	2011-12-23	2011-12-23 07:30:00+11	2011-12-23 09:00:00+11	\N	\N	24	\N	\N	Not-Banked	5	0	\N	\N	\N	f
592	02/12/11-Fri : Transport 8 AM Friday	149	141	2011-12-02	2011-12-02 07:30:00+11	2011-12-02 09:00:00+11	\N	\N	34	\N	\N	Not-Banked	8	0	\N	\N	\N	f
593	09/12/11-Fri : Transport 8 AM Friday	149	141	2011-12-09	2011-12-09 07:30:00+11	2011-12-09 09:00:00+11	\N	\N	34	\N	\N	Not-Banked	8	0	\N	\N	\N	f
594	16/12/11-Fri : Transport 8 AM Friday	149	141	2011-12-16	2011-12-16 07:30:00+11	2011-12-16 09:00:00+11	\N	\N	34	\N	\N	Not-Banked	8	0	\N	\N	\N	f
595	23/12/11-Fri : Transport 8 AM Friday	149	141	2011-12-23	2011-12-23 07:30:00+11	2011-12-23 09:00:00+11	\N	\N	34	\N	\N	Not-Banked	8	0	\N	\N	\N	f
596	02/12/11-Fri : Transport 10 AM Friday	153	142	2011-12-02	2011-12-02 08:30:00+11	2011-12-02 09:00:00+11	\N	\N	43	\N	\N	Not-Banked	10	0	\N	\N	\N	f
597	09/12/11-Fri : Transport 10 AM Friday	153	142	2011-12-09	2011-12-09 08:30:00+11	2011-12-09 09:00:00+11	\N	\N	43	\N	\N	Not-Banked	10	0	\N	\N	\N	f
598	16/12/11-Fri : Transport 10 AM Friday	153	142	2011-12-16	2011-12-16 08:30:00+11	2011-12-16 09:00:00+11	\N	\N	43	\N	\N	Not-Banked	10	0	\N	\N	\N	f
599	23/12/11-Fri : Transport 10 AM Friday	153	142	2011-12-23	2011-12-23 08:30:00+11	2011-12-23 09:00:00+11	\N	\N	43	\N	\N	Not-Banked	10	0	\N	\N	\N	f
600	02/12/11-Fri : Transport 11 AM Friday	155	143	2011-12-02	2011-12-02 08:30:00+11	2011-12-02 09:00:00+11	\N	\N	53	\N	\N	Not-Banked	11	0	\N	\N	\N	f
601	09/12/11-Fri : Transport 11 AM Friday	155	143	2011-12-09	2011-12-09 08:30:00+11	2011-12-09 09:00:00+11	\N	\N	53	\N	\N	Not-Banked	11	0	\N	\N	\N	f
602	16/12/11-Fri : Transport 11 AM Friday	155	143	2011-12-16	2011-12-16 08:30:00+11	2011-12-16 09:00:00+11	\N	\N	53	\N	\N	Not-Banked	11	0	\N	\N	\N	f
603	23/12/11-Fri : Transport 11 AM Friday	155	143	2011-12-23	2011-12-23 08:30:00+11	2011-12-23 09:00:00+11	\N	\N	53	\N	\N	Not-Banked	11	0	\N	\N	\N	f
604	02/12/11-Fri : Transport 1PM Friday	136	144	2011-12-02	2011-12-02 15:00:00+11	2011-12-02 16:30:00+11	\N	\N	60	\N	\N	Not-Banked	1	0	\N	\N	\N	f
605	09/12/11-Fri : Transport 1PM Friday	136	144	2011-12-09	2011-12-09 15:00:00+11	2011-12-09 16:30:00+11	\N	\N	60	\N	\N	Not-Banked	1	0	\N	\N	\N	f
606	16/12/11-Fri : Transport 1PM Friday	136	144	2011-12-16	2011-12-16 15:00:00+11	2011-12-16 16:30:00+11	\N	\N	60	\N	\N	Not-Banked	1	0	\N	\N	\N	f
607	23/12/11-Fri : Transport 1PM Friday	136	144	2011-12-23	2011-12-23 15:00:00+11	2011-12-23 16:30:00+11	\N	\N	60	\N	\N	Not-Banked	1	0	\N	\N	\N	f
608	02/12/11-Fri : Transport 2 PM Friday	138	145	2011-12-02	2011-12-02 15:00:00+11	2011-12-02 16:15:00+11	\N	\N	9	\N	\N	Not-Banked	2	0	\N	\N	\N	f
609	09/12/11-Fri : Transport 2 PM Friday	138	145	2011-12-09	2011-12-09 15:00:00+11	2011-12-09 16:15:00+11	\N	\N	9	\N	\N	Not-Banked	2	0	\N	\N	\N	f
610	16/12/11-Fri : Transport 2 PM Friday	138	145	2011-12-16	2011-12-16 15:00:00+11	2011-12-16 16:15:00+11	\N	\N	9	\N	\N	Not-Banked	2	0	\N	\N	\N	f
611	23/12/11-Fri : Transport 2 PM Friday	138	145	2011-12-23	2011-12-23 15:00:00+11	2011-12-23 16:15:00+11	\N	\N	9	\N	\N	Not-Banked	2	0	\N	\N	\N	f
612	02/12/11-Fri : Transport 3 PM Friday	140	146	2011-12-02	2011-12-02 15:00:00+11	2011-12-02 16:30:00+11	\N	\N	38	\N	\N	Not-Banked	3	0	\N	\N	\N	f
613	09/12/11-Fri : Transport 3 PM Friday	140	146	2011-12-09	2011-12-09 15:00:00+11	2011-12-09 16:30:00+11	\N	\N	38	\N	\N	Not-Banked	3	0	\N	\N	\N	f
614	16/12/11-Fri : Transport 3 PM Friday	140	146	2011-12-16	2011-12-16 15:00:00+11	2011-12-16 16:30:00+11	\N	\N	38	\N	\N	Not-Banked	3	0	\N	\N	\N	f
615	23/12/11-Fri : Transport 3 PM Friday	140	146	2011-12-23	2011-12-23 15:00:00+11	2011-12-23 16:30:00+11	\N	\N	38	\N	\N	Not-Banked	3	0	\N	\N	\N	f
616	02/12/11-Fri : Transport 4 PM Friday	142	147	2011-12-02	2011-12-02 15:00:00+11	2011-12-02 16:30:00+11	\N	\N	8	\N	\N	Not-Banked	4	0	\N	\N	\N	f
617	09/12/11-Fri : Transport 4 PM Friday	142	147	2011-12-09	2011-12-09 15:00:00+11	2011-12-09 16:30:00+11	\N	\N	8	\N	\N	Not-Banked	4	0	\N	\N	\N	f
618	16/12/11-Fri : Transport 4 PM Friday	142	147	2011-12-16	2011-12-16 15:00:00+11	2011-12-16 16:30:00+11	\N	\N	8	\N	\N	Not-Banked	4	0	\N	\N	\N	f
619	23/12/11-Fri : Transport 4 PM Friday	142	147	2011-12-23	2011-12-23 15:00:00+11	2011-12-23 16:30:00+11	\N	\N	8	\N	\N	Not-Banked	4	0	\N	\N	\N	f
620	02/12/11-Fri : Transport 5 PM Friday	144	148	2011-12-02	2011-12-02 15:00:00+11	2011-12-02 16:30:00+11	\N	\N	24	\N	\N	Not-Banked	5	0	\N	\N	\N	f
621	09/12/11-Fri : Transport 5 PM Friday	144	148	2011-12-09	2011-12-09 15:00:00+11	2011-12-09 16:30:00+11	\N	\N	24	\N	\N	Not-Banked	5	0	\N	\N	\N	f
622	16/12/11-Fri : Transport 5 PM Friday	144	148	2011-12-16	2011-12-16 15:00:00+11	2011-12-16 16:30:00+11	\N	\N	24	\N	\N	Not-Banked	5	0	\N	\N	\N	f
623	23/12/11-Fri : Transport 5 PM Friday	144	148	2011-12-23	2011-12-23 15:00:00+11	2011-12-23 16:30:00+11	\N	\N	24	\N	\N	Not-Banked	5	0	\N	\N	\N	f
624	02/12/11-Fri : Transport 8 PM Friday	150	149	2011-12-02	2011-12-02 15:00:00+11	2011-12-02 16:30:00+11	\N	\N	34	\N	\N	Not-Banked	8	0	\N	\N	\N	f
625	09/12/11-Fri : Transport 8 PM Friday	150	149	2011-12-09	2011-12-09 15:00:00+11	2011-12-09 16:30:00+11	\N	\N	34	\N	\N	Not-Banked	8	0	\N	\N	\N	f
626	16/12/11-Fri : Transport 8 PM Friday	150	149	2011-12-16	2011-12-16 15:00:00+11	2011-12-16 16:30:00+11	\N	\N	34	\N	\N	Not-Banked	8	0	\N	\N	\N	f
627	23/12/11-Fri : Transport 8 PM Friday	150	149	2011-12-23	2011-12-23 15:00:00+11	2011-12-23 16:30:00+11	\N	\N	34	\N	\N	Not-Banked	8	0	\N	\N	\N	f
628	02/12/11-Fri : Transport 10 PM Friday	154	150	2011-12-02	2011-12-02 14:45:00+11	2011-12-02 15:45:00+11	\N	\N	43	\N	\N	Not-Banked	10	0	\N	\N	\N	f
629	09/12/11-Fri : Transport 10 PM Friday	154	150	2011-12-09	2011-12-09 14:45:00+11	2011-12-09 15:45:00+11	\N	\N	43	\N	\N	Not-Banked	10	0	\N	\N	\N	f
630	16/12/11-Fri : Transport 10 PM Friday	154	150	2011-12-16	2011-12-16 14:45:00+11	2011-12-16 15:45:00+11	\N	\N	43	\N	\N	Not-Banked	10	0	\N	\N	\N	f
631	23/12/11-Fri : Transport 10 PM Friday	154	150	2011-12-23	2011-12-23 14:45:00+11	2011-12-23 15:45:00+11	\N	\N	43	\N	\N	Not-Banked	10	0	\N	\N	\N	f
632	02/12/11-Fri : Transport 11 PM Friday	156	152	2011-12-02	2011-12-02 15:00:00+11	2011-12-02 15:30:00+11	\N	\N	53	\N	\N	Not-Banked	11	0	\N	\N	\N	f
633	09/12/11-Fri : Transport 11 PM Friday	156	152	2011-12-09	2011-12-09 15:00:00+11	2011-12-09 15:30:00+11	\N	\N	53	\N	\N	Not-Banked	11	0	\N	\N	\N	f
634	16/12/11-Fri : Transport 11 PM Friday	156	152	2011-12-16	2011-12-16 15:00:00+11	2011-12-16 15:30:00+11	\N	\N	53	\N	\N	Not-Banked	11	0	\N	\N	\N	f
635	23/12/11-Fri : Transport 11 PM Friday	156	152	2011-12-23	2011-12-23 15:00:00+11	2011-12-23 15:30:00+11	\N	\N	53	\N	\N	Not-Banked	11	0	\N	\N	\N	f
52	28/11/11-Mon : Home Skills 1 Monday	7	7	2011-11-28	2011-11-28 09:00:00+11	2011-11-28 15:00:00+11	\N	\N	19	4	pending	Not-Banked	\N	0	\N	2011-12-08	\N	f
637	08/07/11-Fri : CA Friday 2 Get abouts	47	55	2011-07-08	2011-07-08 09:00:00+10	2011-07-08 15:00:00+10	\N	\N	57	18	pending	Not-Banked	\N	0	\N	\N	\N	f
638	15/07/11-Fri : CA Friday 2 Get abouts	47	55	2011-07-15	2011-07-15 09:00:00+10	2011-07-15 15:00:00+10	\N	\N	57	18	pending	Not-Banked	\N	0	\N	\N	\N	f
639	22/07/11-Fri : CA Friday 2 Get abouts	47	55	2011-07-22	2011-07-22 09:00:00+10	2011-07-22 15:00:00+10	\N	\N	57	18	pending	Not-Banked	\N	0	\N	\N	\N	f
640	29/07/11-Fri : CA Friday 2 Get abouts	47	55	2011-07-29	2011-07-29 09:00:00+10	2011-07-29 15:00:00+10	\N	\N	57	18	pending	Not-Banked	\N	0	\N	\N	\N	f
641	05/08/11-Fri : CA Friday 2 Get abouts	47	55	2011-08-05	2011-08-05 09:00:00+10	2011-08-05 15:00:00+10	\N	\N	57	18	pending	Not-Banked	\N	0	\N	\N	\N	f
642	12/08/11-Fri : CA Friday 2 Get abouts	47	55	2011-08-12	2011-08-12 09:00:00+10	2011-08-12 15:00:00+10	\N	\N	57	18	pending	Not-Banked	\N	0	\N	\N	\N	f
643	19/08/11-Fri : CA Friday 2 Get abouts	47	55	2011-08-19	2011-08-19 09:00:00+10	2011-08-19 15:00:00+10	\N	\N	57	18	pending	Not-Banked	\N	0	\N	\N	\N	f
644	26/08/11-Fri : CA Friday 2 Get abouts	47	55	2011-08-26	2011-08-26 09:00:00+10	2011-08-26 15:00:00+10	\N	\N	57	18	pending	Not-Banked	\N	0	\N	\N	\N	f
645	02/09/11-Fri : CA Friday 2 Get abouts	47	55	2011-09-02	2011-09-02 09:00:00+10	2011-09-02 15:00:00+10	\N	\N	57	18	pending	Not-Banked	\N	0	\N	\N	\N	f
646	09/09/11-Fri : CA Friday 2 Get abouts	47	55	2011-09-09	2011-09-09 09:00:00+10	2011-09-09 15:00:00+10	\N	\N	57	18	pending	Not-Banked	\N	0	\N	\N	\N	f
647	16/09/11-Fri : CA Friday 2 Get abouts	47	55	2011-09-16	2011-09-16 09:00:00+10	2011-09-16 15:00:00+10	\N	\N	57	18	pending	Not-Banked	\N	0	\N	\N	\N	f
648	23/09/11-Fri : CA Friday 2 Get abouts	47	55	2011-09-23	2011-09-23 09:00:00+10	2011-09-23 15:00:00+10	\N	\N	57	18	pending	Not-Banked	\N	0	\N	\N	\N	f
649	30/09/11-Fri : CA Friday 2 Get abouts	47	55	2011-09-30	2011-09-30 09:00:00+10	2011-09-30 15:00:00+10	\N	\N	57	18	pending	Not-Banked	\N	0	\N	\N	\N	f
650	07/10/11-Fri : CA Friday 2 Get abouts	47	55	2011-10-07	2011-10-07 09:00:00+11	2011-10-07 15:00:00+11	\N	\N	57	18	pending	Not-Banked	\N	0	\N	\N	\N	f
651	14/10/11-Fri : CA Friday 2 Get abouts	47	55	2011-10-14	2011-10-14 09:00:00+11	2011-10-14 15:00:00+11	\N	\N	57	18	pending	Not-Banked	\N	0	\N	\N	\N	f
652	21/10/11-Fri : CA Friday 2 Get abouts	47	55	2011-10-21	2011-10-21 09:00:00+11	2011-10-21 15:00:00+11	\N	\N	57	18	pending	Not-Banked	\N	0	\N	\N	\N	f
653	28/10/11-Fri : CA Friday 2 Get abouts	47	55	2011-10-28	2011-10-28 09:00:00+11	2011-10-28 15:00:00+11	\N	\N	57	18	pending	Not-Banked	\N	0	\N	\N	\N	f
654	04/11/11-Fri : CA Friday 2 Get abouts	47	55	2011-11-04	2011-11-04 09:00:00+11	2011-11-04 15:00:00+11	\N	\N	57	18	pending	Not-Banked	\N	0	\N	\N	\N	f
655	11/11/11-Fri : CA Friday 2 Get abouts	47	55	2011-11-11	2011-11-11 09:00:00+11	2011-11-11 15:00:00+11	\N	\N	57	18	pending	Not-Banked	\N	0	\N	\N	\N	f
656	18/11/11-Fri : CA Friday 2 Get abouts	47	55	2011-11-18	2011-11-18 09:00:00+11	2011-11-18 15:00:00+11	\N	\N	57	18	pending	Not-Banked	\N	0	\N	\N	\N	f
657	25/11/11-Fri : CA Friday 2 Get abouts	47	55	2011-11-25	2011-11-25 09:00:00+11	2011-11-25 15:00:00+11	\N	\N	57	18	pending	Not-Banked	\N	0	\N	\N	\N	f
658	02/12/11-Fri : CA Friday 2 Get abouts	47	55	2011-12-02	2011-12-02 09:00:00+11	2011-12-02 15:00:00+11	\N	\N	57	18	pending	Not-Banked	\N	0	\N	\N	\N	f
659	09/12/11-Fri : CA Friday 2 Get abouts	47	55	2011-12-09	2011-12-09 09:00:00+11	2011-12-09 15:00:00+11	\N	\N	57	18	pending	Not-Banked	\N	0	\N	\N	\N	f
660	16/12/11-Fri : CA Friday 2 Get abouts	47	55	2011-12-16	2011-12-16 09:00:00+11	2011-12-16 15:00:00+11	\N	\N	57	18	pending	Not-Banked	\N	0	\N	\N	\N	f
636	01/07/11-Fri : CA Friday 2 Get abouts	47	55	2011-07-01	2011-07-01 09:00:00+10	2011-07-01 15:00:00+10	\N	\N	57	18	pending	Not-Banked	\N	0	\N	2012-01-18	\N	f
662	14/07/11-Thu : Band Thursday	40	46	2011-07-14	2011-07-14 09:00:00+10	2011-07-14 15:00:00+10	\N	\N	10	6	pending	Not-Banked	\N	0	\N	\N	\N	f
663	21/07/11-Thu : Band Thursday	40	46	2011-07-21	2011-07-21 09:00:00+10	2011-07-21 15:00:00+10	\N	\N	10	6	pending	Not-Banked	\N	0	\N	\N	\N	f
664	28/07/11-Thu : Band Thursday	40	46	2011-07-28	2011-07-28 09:00:00+10	2011-07-28 15:00:00+10	\N	\N	10	6	pending	Not-Banked	\N	0	\N	\N	\N	f
665	04/08/11-Thu : Band Thursday	40	46	2011-08-04	2011-08-04 09:00:00+10	2011-08-04 15:00:00+10	\N	\N	10	6	pending	Not-Banked	\N	0	\N	\N	\N	f
666	11/08/11-Thu : Band Thursday	40	46	2011-08-11	2011-08-11 09:00:00+10	2011-08-11 15:00:00+10	\N	\N	10	6	pending	Not-Banked	\N	0	\N	\N	\N	f
667	18/08/11-Thu : Band Thursday	40	46	2011-08-18	2011-08-18 09:00:00+10	2011-08-18 15:00:00+10	\N	\N	10	6	pending	Not-Banked	\N	0	\N	\N	\N	f
668	25/08/11-Thu : Band Thursday	40	46	2011-08-25	2011-08-25 09:00:00+10	2011-08-25 15:00:00+10	\N	\N	10	6	pending	Not-Banked	\N	0	\N	\N	\N	f
669	01/09/11-Thu : Band Thursday	40	46	2011-09-01	2011-09-01 09:00:00+10	2011-09-01 15:00:00+10	\N	\N	10	6	pending	Not-Banked	\N	0	\N	\N	\N	f
670	08/09/11-Thu : Band Thursday	40	46	2011-09-08	2011-09-08 09:00:00+10	2011-09-08 15:00:00+10	\N	\N	10	6	pending	Not-Banked	\N	0	\N	\N	\N	f
671	15/09/11-Thu : Band Thursday	40	46	2011-09-15	2011-09-15 09:00:00+10	2011-09-15 15:00:00+10	\N	\N	10	6	pending	Not-Banked	\N	0	\N	\N	\N	f
672	22/09/11-Thu : Band Thursday	40	46	2011-09-22	2011-09-22 09:00:00+10	2011-09-22 15:00:00+10	\N	\N	10	6	pending	Not-Banked	\N	0	\N	\N	\N	f
673	29/09/11-Thu : Band Thursday	40	46	2011-09-29	2011-09-29 09:00:00+10	2011-09-29 15:00:00+10	\N	\N	10	6	pending	Not-Banked	\N	0	\N	\N	\N	f
674	06/10/11-Thu : Band Thursday	40	46	2011-10-06	2011-10-06 09:00:00+11	2011-10-06 15:00:00+11	\N	\N	10	6	pending	Not-Banked	\N	0	\N	\N	\N	f
675	13/10/11-Thu : Band Thursday	40	46	2011-10-13	2011-10-13 09:00:00+11	2011-10-13 15:00:00+11	\N	\N	10	6	pending	Not-Banked	\N	0	\N	\N	\N	f
676	20/10/11-Thu : Band Thursday	40	46	2011-10-20	2011-10-20 09:00:00+11	2011-10-20 15:00:00+11	\N	\N	10	6	pending	Not-Banked	\N	0	\N	\N	\N	f
677	27/10/11-Thu : Band Thursday	40	46	2011-10-27	2011-10-27 09:00:00+11	2011-10-27 15:00:00+11	\N	\N	10	6	pending	Not-Banked	\N	0	\N	\N	\N	f
678	03/11/11-Thu : Band Thursday	40	46	2011-11-03	2011-11-03 09:00:00+11	2011-11-03 15:00:00+11	\N	\N	10	6	pending	Not-Banked	\N	0	\N	\N	\N	f
679	10/11/11-Thu : Band Thursday	40	46	2011-11-10	2011-11-10 09:00:00+11	2011-11-10 15:00:00+11	\N	\N	10	6	pending	Not-Banked	\N	0	\N	\N	\N	f
680	17/11/11-Thu : Band Thursday	40	46	2011-11-17	2011-11-17 09:00:00+11	2011-11-17 15:00:00+11	\N	\N	10	6	pending	Not-Banked	\N	0	\N	\N	\N	f
681	24/11/11-Thu : Band Thursday	40	46	2011-11-24	2011-11-24 09:00:00+11	2011-11-24 15:00:00+11	\N	\N	10	6	pending	Not-Banked	\N	0	\N	\N	\N	f
661	07/07/11-Thu : Band Thursday	40	46	2011-07-07	2011-07-07 09:00:00+10	2011-07-07 15:00:00+10	\N	\N	10	6		Not-Banked	\N	0	2012-01-18	2012-01-18	\N	f
\.


--
-- Data for Name: programs; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY programs (id, name, startdate, enddate, programtypeid, chargeamount, status, coordinatorid, vehicleid) FROM stdin;
3	Ten Pin Bowling Wyong	2011-07-01	2012-06-30	1	0	Active	57	\N
7	Home Skills 1 Budget Money	2011-07-01	2012-06-30	1	5	Active	19	\N
6	Mixed Media Art	2011-07-01	2012-06-30	1	5	Active	10	\N
12	Coop	2011-07-01	2012-06-30	1	5	Active	51	\N
51	Transport 2 PM Monday	2011-07-01	2012-06-30	2	0	Active	38	2
15	Hi Guys	2011-07-01	2012-06-30	1	5	Active	57	\N
16	Sensory Art Felting	2011-07-01	2012-06-30	1	5	Active	57	\N
52	Transport 3 AM Monday	2011-07-01	2012-06-30	2	0	Active	22	3
53	Transport 3 PM Monday	2011-07-01	2012-06-30	2	0	Active	22	3
31	CA Wednesday	2011-07-01	2012-06-30	1	5	Active	57	\N
43	Options Theatre Company	2011-07-01	2012-06-30	1	6	Active	10	\N
37	Boccia	2011-07-01	2012-06-30	1	5	Active	57	\N
41	Scrapbooking	2011-07-01	2012-06-03	1	5	Active	19	\N
18	Gosford Musical Society	2011-07-01	2012-06-30	1	0	Active	19	\N
1	Transport 1 AM Monday	2011-01-01	2012-06-30	2	0	Active	53	1
54	Transport 4 AM Monday	2011-07-01	2012-06-30	2	0	Active	32	4
49	Transport 1 PM Monday	2011-07-01	2012-06-30	2	0	Active	53	1
50	Transport 2 AM Monday	2011-07-01	2012-06-30	2	0	Active	38	2
55	Transport 4 PM Monday	2011-07-01	2012-06-30	2	0	Active	32	4
56	Transport 5 AM Monday	2011-07-01	2012-06-30	2	0	Active	47	5
57	Transport 5 PM Monday	2011-07-01	2012-06-30	2	0	Active	47	5
58	Transport 6 AM Monday	2011-07-01	2012-06-30	2	0	Active	24	6
60	Transport 7 AM Monday	2011-07-01	2012-06-30	2	0	Active	35	7
61	Transport 7 PM Monday	2011-07-01	2012-06-30	2	0	Active	35	7
62	Transport 8 AM Monday	2011-07-01	2012-06-30	2	0	Active	\N	8
63	Transport 8 PM Monday	2011-07-01	2012-06-30	2	0	Active	\N	8
64	Transport 9 AM Monday	2011-07-01	2012-06-30	2	0	Active	28	9
65	Transport 10 AM Monday	2011-07-01	2012-06-30	2	0	Active	34	10
66	Transport 10 PM Monday	2011-07-01	2012-06-30	2	0	Active	34	10
67	Transport 11 AM Monday	2011-07-01	2012-06-30	2	0	Active	49	11
68	Transport 11 PM Monday	2011-07-01	2012-06-30	2	0	Active	49	11
69	Transport 1 AM Tuesday	2011-07-01	2012-06-30	2	0	Active	8	1
70	Transport 1 PM Tuesday	2011-07-01	2012-06-30	2	0	Active	8	1
71	Transport 2 AM Tuesday	2011-07-01	2012-06-30	2	0	Active	24	2
72	Transport 2 PM Tuesday	2011-07-01	2012-06-30	2	0	Active	24	2
73	Transport 3 AM Tuesday	2011-07-01	2012-06-30	2	0	Active	9	3
74	Transport 3 PM Tuesday	2011-07-01	2012-06-30	2	0	Active	9	3
75	Transport 4 AM Tuesday	2011-07-01	2012-06-30	2	0	Active	36	4
76	Transport 4 PM Tuesday	2011-07-01	2012-06-30	2	0	Active	36	4
77	Transport 5 AM Tuesday	2011-07-01	2012-06-30	2	0	Active	47	5
78	Transport 5 PM Tuesday	2011-07-01	2012-06-30	2	0	Active	\N	5
79	Transport 6 AM Tuesday	2011-07-01	2012-06-30	2	0	Active	14	6
9	Sensory Music	2011-07-01	2012-06-30	1	5	Active	57	\N
8	Home Skills 2 Budget Money	2011-07-01	2012-06-30	1	5	Active	19	\N
5	Ten Pin Bowling Gosford	2011-07-01	2012-06-30	1	5	Active	57	\N
14	Herb Farm	2011-07-01	2012-06-30	1	5	Active	19	\N
17	Dairy Art	2011-07-01	2012-06-30	1	5	Active	10	\N
19	Water Fitness	2011-07-01	2012-06-30	1	0	Active	57	\N
33	Gals Group	2011-07-01	2012-06-30	1	0	Active	57	\N
20	Living Skills	2011-07-01	2012-06-30	1	5	Active	19	\N
21	CA Tuesday	2011-07-01	2012-06-30	1	5	Active	57	\N
22	Events In The Office	2011-07-01	2012-06-30	1	5	Active	19	\N
23	Gym Swim	2011-07-01	2012-06-30	1	5	Active	57	\N
24	Radio	2011-07-01	2012-07-01	1	5	Active	10	\N
11	Link 2 Life	2011-07-01	2012-06-30	1	5	Active	19	\N
26	Caring For The Community	2011-07-01	2012-06-30	1	5	Active	19	\N
27	Gosford MOW Budget	2011-07-01	2012-06-30	1	5	Active	19	\N
28	Multimedia	2011-07-01	2012-06-30	1	5	Active	10	\N
29	Laundry Aged Care alt MOW	2011-07-01	2012-06-30	1	5	Active	19	\N
30	Dance Fitness Healthy Lifestyles	2011-07-01	2012-06-30	1	5	Active	10	\N
32	Maintenance	2011-07-01	2012-06-30	1	5	Active	57	\N
42	Fitness	2011-07-01	2012-06-30	1	0	Active	57	\N
35	Gardening	2011-07-01	2012-06-30	1	5	Active	57	\N
36	Camp Breakaway	2011-07-01	2012-06-30	1	5	Active	57	\N
38	In the Office	2011-07-01	2012-06-30	1	5	Active	19	\N
39	Singing Creative Dance	2011-07-01	2012-06-30	1	5	Active	10	\N
40	Band	2011-07-01	2012-06-30	1	5	Active	10	\N
45	Props Backstage	2011-07-01	2012-06-30	1	5	Active	10	\N
44	Puppets	2011-07-01	2012-06-30	1	5	Active	10	\N
47	CA Friday 2 Get Abouts	2011-07-01	2012-06-30	1	0	Active	57	\N
48	CA The Gang	2011-07-01	2012-06-30	1	0	Active	57	\N
10	CA Monday	2011-07-01	2012-06-30	1	0	Active	57	\N
59	Transport 6 PM Monday	2011-07-01	2012-06-30	2	0	Active	24	6
34	Stepping Out	2011-07-01	2012-06-30	1	0	Active	57	\N
80	Transport 6 PM Tuesday	2011-07-01	2012-06-30	2	0	Active	14	\N
81	Transport 7 AM Tuesday	2011-07-01	2012-06-30	2	0	Active	25	7
82	Transport 7 PM Tuesday	2011-07-01	2012-06-30	2	0	Active	25	7
83	Transport 8 AM Tuesday	2011-07-01	2012-06-30	2	0	Active	16	8
84	Transport 8 PM Tuesday	2011-07-01	2012-06-30	2	0	Active	16	8
85	Transport 9 AM Tuesday	2011-07-01	2012-06-30	2	0	Active	22	9
86	Transport 9 PM Tuesday	2011-07-01	2012-06-30	2	0	Active	22	9
88	Transport 10 PM Tuesday	2011-07-01	2012-06-30	2	0	Active	13	10
89	Transport 11 AM Tuesday	2011-07-01	2012-06-30	2	0	Active	21	11
90	Transport 11 PM Tuesday	2011-07-01	2012-06-30	2	0	Active	21	11
91	Transport 1 AM Wednesday	2011-07-01	2012-06-30	2	0	Active	25	1
92	Transport 1 PM Wednesday	2011-07-01	2012-06-30	2	0	Active	25	1
93	Transport 2 AM Wednesday	2011-07-01	2012-06-30	2	0	Active	9	2
94	Transport 2 PM Wednesday	2011-07-01	2012-06-30	2	0	Active	9	2
95	Transport 3 AM Wednesday	2011-07-01	2012-06-30	2	0	Active	54	3
96	Transport 3 PM Wednesday	2011-07-01	2012-06-30	2	0	Active	54	3
97	Transport 4 AM Wednesday	2011-07-01	2012-06-30	2	0	Active	53	4
99	Transport 5 AM Wednesday	2011-07-01	2012-06-30	2	0	Active	12	5
100	Transport 5 PM Wednesday	2011-07-01	2012-06-30	2	0	Active	12	5
101	Transport 6 AM Wednesday	2011-07-01	2012-06-30	2	0	Active	\N	6
102	Transport 6 PM Wednesday	2011-07-01	2012-06-30	2	0	Active	\N	6
103	Transport 7 AM Wednesday	2011-07-01	2012-06-30	2	0	Active	\N	7
104	Transport 7 PM Wednesday	2011-07-01	2012-06-30	2	0	Active	\N	7
105	Transport 8 AM Wednesday	2011-07-01	2012-06-30	2	0	Active	8	8
106	Transport 8 PM Wednesday	2011-07-01	2012-06-30	2	0	Active	8	7
107	Transport 9 AM Wednesday	2011-07-01	2012-06-30	2	0	Active	16	9
108	Transport 9 PM Wednesday	2011-07-01	2012-06-30	2	0	Active	16	10
109	Transport 10 AM Wednesday	2011-07-01	2012-06-30	2	0	Active	31	10
110	Transport 10 PM Wednesday	2011-07-01	2012-06-30	2	0	Active	31	10
111	Transport 11 AM Wednesday	2011-07-01	2012-06-30	2	0	Active	49	11
112	Transport 11 PM Wednesday	2011-07-01	2012-06-30	2	0	Active	49	11
113	Transport 1 AM Thursday	2011-07-01	2012-06-30	2	0	Active	47	1
114	Transport 1 PM Thursday	2011-07-01	2012-06-30	2	0	Active	47	1
115	Transport 2 AM Thursday	2011-07-01	2012-06-30	2	0	Active	53	2
116	Transport 2 PM Thursday	2011-07-01	2012-06-30	2	0	Active	53	2
117	Transport 3 AM Thursday	2011-07-01	2012-06-30	2	0	Active	13	3
118	Transport 3 PM Thursday	2011-07-01	2012-06-30	2	0	Active	13	3
119	Transport 4 AM Thursday	2011-07-01	2012-06-30	2	0	Active	31	4
120	Transport 4 PM Thursday	2011-07-01	2012-06-30	2	0	Active	31	4
121	Transport 5 AM Thursday	2011-07-01	2012-06-30	2	0	Active	32	5
122	Transport 5 PM Thursday	2011-07-01	2012-06-30	2	0	Active	32	5
123	Transport 6 AM Thursday	2011-07-01	2012-06-30	2	0	Active	9	6
124	Transport 6 PM Thursday	2011-07-01	2012-06-30	2	0	Active	9	6
125	Transport 7 AM Thursday	2011-07-01	2012-06-30	2	0	Active	25	7
126	Transport 7 PM Thursday	2011-07-01	2012-06-30	2	0	Active	25	7
127	Transport 8 AM Thursday	2011-07-01	2012-06-30	2	0	Active	35	8
128	Transport 8 PM Thursday	2011-07-01	2012-06-30	2	0	Active	35	8
129	Transport 9 AM Thursday	2011-07-01	2012-06-30	2	0	Active	16	9
130	Transport 9 PM Thursday	2011-07-01	2012-06-30	2	0	Active	16	9
131	Transport 10 AM Thursday	2011-07-01	2012-06-30	2	0	Active	59	10
132	Transport 10 PM Thursday	2011-07-01	2012-06-30	2	0	Active	59	10
133	Transport 11 AM Thursday	2011-07-01	2012-06-30	2	0	Active	51	11
134	Transport 11 PM Thursday	2011-07-01	2012-06-30	2	0	Active	51	11
135	Transport 1 AM Friday	2011-07-01	2012-06-30	2	0	Active	60	1
136	Transport 1 PM Friday	2011-07-01	2012-06-30	2	0	Active	60	1
137	Transport 2 AM Friday	2011-07-01	2012-06-30	2	0	Active	9	2
138	Transport 2 PM Friday	2011-07-01	2012-06-30	2	0	Active	9	2
139	Transport 3 AM Friday	2011-07-01	2012-06-30	2	0	Active	38	3
140	Transport 3 PM Friday	2011-07-01	2012-06-30	2	0	Active	38	3
141	Transport 4 AM Friday	2011-07-01	2012-06-30	2	0	Active	8	4
142	Transport 4 PM Friday	2011-07-01	2012-06-30	2	0	Active	8	4
98	Transport 4 PM Wednesday	2011-07-01	2012-06-30	2	0	Active	53	4
143	Transport 5 AM Friday	2011-07-01	2012-06-30	2	0	Active	24	5
144	Transport 5 PM Friday	2011-07-01	2012-06-30	2	0	Active	24	5
145	Transport 6 AM Friday	2011-07-01	2012-06-30	2	0	Active	\N	6
146	Transport 6 PM Friday	2011-07-01	2012-06-30	2	0	Active	\N	6
147	Transport 7 AM Friday	2011-07-01	2012-06-30	2	0	Active	\N	7
148	Transport 7 PM Friday	2011-07-01	2012-06-30	2	0	Active	\N	7
149	Transport 8 AM Friday	2011-07-01	2012-06-30	2	0	Active	34	8
150	Transport 8 PM Friday	2011-07-01	2012-06-30	2	0	Active	34	8
151	Transport 9 AM Friday	2011-07-01	2012-06-30	2	0	Active	\N	9
152	Transport 9 PM Friday	2011-07-01	2012-06-30	2	0	Active	\N	9
153	Transport 10 AM Friday	2011-07-01	2012-06-30	2	0	Active	43	10
87	Transport 10 AM Tuesday	2011-07-01	2012-06-30	2	0	Active	13	10
154	Transport 10 PM Friday	2011-07-01	2012-06-30	2	0	Active	43	10
155	Transport 11 AM Friday	2011-07-01	2012-06-30	2	0	Active	53	11
156	Transport 11 PM Friday	2011-07-01	2012-06-30	2	0	Active	53	11
157	Transport 9 PM Monday	2011-07-01	2012-06-30	2	0	Active	28	9
46	CA Friday 1 Out and About	2011-07-01	2012-06-30	1	0	Active	57	\N
\.


--
-- Data for Name: programtypes; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY programtypes (id, name) FROM stdin;
1	Standard
2	Transport
\.


--
-- Data for Name: programweekxref; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY programweekxref (programid, weekid) FROM stdin;
\.


--
-- Data for Name: referenceitems; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY referenceitems (id, category, itemvalue, status) FROM stdin;
1	STAFF_SKILL	First Aid	\N
2	STAFF_SKILL	One to One	\N
3	STAFF_SKILL	Sign Language	\N
4	STAFF_SKILL	Blind Care	\N
\.


--
-- Data for Name: reminders; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY reminders (id, referenceid, createddate, remindon, type, note, no_of_reminders, status) FROM stdin;
\.


--
-- Data for Name: servicearea; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY servicearea (id, name) FROM stdin;
1	Air Conditioning
2	Computers
3	Electricians
4	Fire and Security
5	Fire Estinguishers
6	First Aid Suppliers
7	Fridges
8	Glass and Glazing
9	Locksmiths
10	Office Furniture
11	OH and S
12	Pay
13	Personnel Protective Equipment
14	Pest Control
15	Plumbers
16	Printers
\.


--
-- Data for Name: specialneeds; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY specialneeds (id, name, filename, photoid) FROM stdin;
6	Wheelchair	Wheelchair.png	\N
3	Hearing	Deaf.png	\N
8	Seating Arrangement	\N	\N
2	Behaviour	Cochlear.png	\N
4	Absconds	Glasses.png	\N
1	Vision	Blind.png	\N
5	Mobility	Walking.png	\N
9	Epilepsy	\N	\N
\.


--
-- Data for Name: staffcheckrecords; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY staffcheckrecords (id, checkrecordid, staffid, datecompleted, expirydate, checked, renewable, remarks) FROM stdin;
47	2	26	\N	\N	f	f	\N
48	1	26	2011-08-05	\N	t	f	\N
85	2	45	2011-11-12	\N	t	f	\N
86	1	45	2010-09-28	\N	t	f	\N
9	2	7	2011-11-12	\N	t	f	\N
8	1	6	2008-04-21	\N	t	f	\N
1	2	3	2011-04-14	\N	t	f	\N
2	1	3	2009-04-30	\N	t	f	\N
3	2	4	2011-08-18	\N	t	f	\N
4	1	4	2011-03-23	\N	t	f	\N
13	2	9	2011-10-03	\N	t	f	\N
7	2	6	2011-04-14	\N	t	f	\N
10	1	7	2007-06-27	\N	t	f	\N
14	1	9	2007-06-27	\N	t	f	\N
20	1	12	2008-04-21	\N	t	f	\N
19	2	12	2011-06-10	\N	t	f	\N
17	2	11	2011-10-10	\N	t	f	\N
18	1	11	2007-06-27	\N	t	f	\N
24	1	14	2006-04-04	\N	t	f	\N
23	2	14	2011-11-24	\N	t	f	\N
15	2	10	\N	\N	f	f	\N
16	1	10	\N	\N	f	f	\N
6	1	5	2007-08-08	\N	t	f	\N
21	2	13	2011-04-14	\N	t	f	\N
22	1	13	2008-04-21	\N	t	f	\N
27	2	16	2011-09-26	\N	t	f	\N
5	2	5	\N	\N	f	f	\N
28	1	16	2005-08-04	\N	t	f	\N
33	2	19	\N	\N	f	f	\N
34	1	19	2007-09-19	\N	t	f	\N
25	2	15	\N	\N	f	f	\N
26	1	15	2010-12-08	\N	t	f	\N
31	2	18	2011-04-03	\N	t	f	\N
32	1	18	2006-04-12	\N	t	f	\N
29	2	17	\N	\N	f	f	\N
30	1	17	2005-08-04	\N	t	f	\N
35	2	20	\N	\N	f	f	\N
36	1	20	2010-11-22	\N	t	f	\N
37	2	21	\N	\N	f	f	\N
38	1	21	2011-03-23	\N	t	f	\N
45	2	25	\N	\N	f	f	\N
46	1	25	2006-01-05	\N	t	f	\N
43	2	24	2011-10-20	\N	t	f	\N
44	1	24	2007-06-27	\N	t	f	\N
39	2	22	\N	\N	f	f	\N
40	1	22	2008-02-16	\N	t	f	\N
41	2	23	2011-08-18	\N	t	f	\N
42	1	23	2011-06-28	\N	t	f	\N
55	2	30	2011-06-23	\N	t	f	\N
56	1	30	2008-04-21	\N	t	f	\N
51	2	28	\N	\N	f	f	\N
52	1	28	2009-01-13	\N	t	f	\N
53	2	29	2011-04-03	\N	t	f	\N
54	1	29	2006-04-04	\N	t	f	\N
49	2	27	2011-04-23	\N	t	f	\N
50	1	27	2009-04-30	\N	t	f	\N
59	2	32	2011-11-05	\N	t	f	\N
60	1	32	2006-04-04	\N	t	f	\N
71	2	38	2011-11-05	\N	t	f	\N
72	1	38	2007-08-08	\N	t	f	\N
57	2	31	\N	\N	f	f	\N
58	1	31	2007-08-08	\N	t	f	\N
63	2	34	\N	\N	f	f	\N
64	1	34	2010-11-19	\N	t	f	\N
65	2	35	2011-10-29	\N	t	f	\N
66	1	35	2007-09-19	\N	t	f	\N
69	2	37	\N	\N	f	f	\N
70	1	37	2011-02-04	\N	t	f	\N
67	2	36	2011-02-10	\N	t	f	\N
68	1	36	2008-04-21	\N	t	f	\N
61	2	33	\N	\N	f	f	\N
62	1	33	2010-11-30	\N	t	f	\N
77	2	41	2011-08-18	\N	t	f	\N
78	1	41	2011-06-28	\N	t	f	\N
75	2	40	2011-11-24	\N	t	f	\N
76	1	40	2007-09-03	\N	t	f	\N
89	2	47	2011-10-16	\N	t	f	\N
90	1	47	2006-05-26	\N	t	f	\N
73	2	39	2011-11-12	\N	t	f	\N
74	1	39	2006-11-10	\N	t	f	\N
83	2	44	\N	\N	f	f	\N
84	1	44	2011-06-28	\N	t	f	\N
87	2	46	2011-09-19	\N	t	f	\N
88	1	46	2011-01-13	\N	t	f	\N
101	2	53	2011-11-17	\N	t	f	\N
102	1	53	2007-06-27	\N	t	f	\N
105	2	55	\N	\N	f	f	\N
106	1	55	2011-01-31	\N	t	f	\N
93	2	49	2011-12-02	\N	t	f	\N
94	1	49	2007-04-30	\N	t	f	\N
97	2	51	2011-11-21	\N	t	f	\N
98	1	51	2007-06-27	\N	t	f	\N
95	2	50	\N	\N	f	f	\N
96	1	50	2010-11-23	\N	t	f	\N
107	2	56	2011-10-29	\N	t	f	\N
108	1	56	2007-08-08	\N	t	f	\N
99	2	52	2011-09-19	\N	t	f	\N
100	1	52	2011-03-28	\N	t	f	\N
103	2	54	2011-11-26	\N	t	f	\N
104	1	54	2007-06-27	\N	t	f	\N
111	2	58	2011-11-05	\N	t	f	\N
112	1	58	2006-04-12	\N	t	f	\N
113	2	59	2011-10-03	\N	t	f	\N
114	1	59	2007-08-08	\N	t	f	\N
109	2	57	2011-03-14	\N	t	f	\N
110	1	57	2006-04-04	\N	t	f	\N
81	2	43	2011-10-17	\N	t	f	\N
82	1	43	2006-04-04	\N	t	f	\N
115	2	60	2011-08-18	\N	t	f	\N
116	1	60	2010-12-07	\N	t	f	\N
91	2	48	\N	\N	f	f	\N
92	1	48	2006-05-02	\N	t	f	\N
11	2	8	2011-06-20	\N	t	f	\N
12	1	8	2008-04-21	\N	t	f	\N
117	2	61	\N	\N	f	f	\N
118	1	61	2010-09-02	\N	t	f	\N
79	2	42	\N	\N	f	f	\N
80	1	42	2011-06-28	\N	t	f	\N
\.


--
-- Data for Name: staffmembers; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY staffmembers (id, staffid, gender, dob, joineddate, serviceenddate, stafftypeid, employmenttype, photoid, leavepolicyid, status) FROM stdin;
47	45	Male	1950-02-08	1998-02-04	\N	3	\N	\N	1	Current
5	3	Female	1954-01-05	1996-10-22	\N	3	\N	\N	1	Current
8	6	Male	1984-12-06	2008-04-09	\N	3	\N	\N	1	Current
9	7	Female	1953-01-20	1996-12-14	\N	3	\N	\N	1	Current
14	12	Female	1961-01-10	1998-04-28	\N	3	\N	\N	1	Current
19	17	Female	1979-10-20	2004-01-12	\N	2	\N	\N	1	Current
16	14	Male	1946-09-19	1997-01-20	\N	3	\N	\N	1	Current
49	47	Male	1976-05-03	2007-02-20	\N	2	\N	\N	1	Current
51	49	Male	1973-05-27	2001-10-23	\N	2	\N	\N	1	Current
20	18	Female	1967-12-30	2010-09-27	\N	3	\N	\N	1	Current
24	22	Female	1964-07-06	2004-01-12	\N	3	\N	\N	1	Current
22	20	Female	1961-05-28	2001-10-23	\N	3	\N	\N	1	Current
25	23	Male	1980-07-19	2005-10-17	\N	3	\N	\N	1	Current
30	28	Female	1963-06-10	2008-04-09	\N	3	\N	\N	1	Current
26	24	M	2011-09-29	\N	\N	2	\N	\N	1	Current
32	30	Female	1960-11-30	2004-01-12	\N	3	\N	\N	1	Current
35	33	Female	1956-05-16	2000-07-01	\N	3	\N	\N	1	Current
38	36	Male	1962-08-07	1996-09-03	\N	3	\N	\N	1	Current
40	38		2011-06-04	1995-08-30	\N	2	\N	\N	1	Current
53	51	Male	1959-09-07	2004-01-12	\N	3	\N	\N	1	Current
59	57	Female	1958-04-18	2000-05-09	\N	3	\N	\N	1	Current
60	58	Male	1969-02-27	2010-08-20	\N	3	\N	\N	1	Current
48	46	Female	1962-10-07	2006-04-06	\N	1	\N	\N	1	Current
50	48	Female	1969-02-23	2010-08-20	\N	3	\N	\N	1	Current
55	53	Female	1990-06-23	2010-11-26	\N	3	\N	\N	1	Current
56	54	Female	1968-07-14	2001-08-23	\N	3	\N	\N	1	Current
58	56	Female	1940-07-03	2003-02-24	\N	3	\N	\N	1	Current
43	41	Female	1972-10-18	2003-03-03	\N	1	\N	\N	1	Current
42	40	Female	1987-07-27	2011-06-23	\N	3	\N	\N	1	Current
39	37	Female	1950-10-08	2006-09-18	\N	3	\N	\N	1	Current
33	31	Female	1966-09-05	2009-08-24	\N	3	\N	\N	1	Current
31	29	Female	1968-08-13	2001-08-23	\N	3	\N	\N	1	Current
27	25	Female	1976-04-23	2009-03-26	\N	3	\N	\N	1	Current
23	21	Female	1961-08-26	2011-06-23	\N	3	\N	\N	1	Current
17	15	Female	1966-12-18	2001-08-23	\N	3	\N	\N	1	Current
15	13	Female	1985-10-30	2010-08-20	\N	3	\N	\N	1	Current
12	10	Female	1972-08-22	2008-04-09	\N	3	\N	\N	1	Current
3	1	Female	1971-01-07	2009-03-26	\N	3	\N	\N	1	Current
4	2	Female	1966-02-22	2011-03-14	\N	3	\N	\N	1	Current
28	26	Female	1971-12-09	2008-11-14	\N	3	\N	\N	1	Current
29	27	Female	1956-12-19	2003-08-18	\N	2	\N	\N	1	Current
10	8	Female	1962-04-26	2010-03-15	\N	2	\N	\N	1	Current
11	9	Female	1950-10-12	2005-10-17	\N	3	\N	\N	1	Current
13	11	Female	1983-10-19	2008-04-09	\N	3	\N	\N	1	Current
18	16	Female	1955-07-20	1999-09-15	\N	2	\N	\N	1	Current
21	19	Male	1987-07-29	2011-03-08	\N	3	\N	\N	1	Current
34	32	Female	1987-03-13	2010-10-20	\N	3	\N	\N	1	Current
36	34	Female	1988-03-21	2008-04-09	\N	3	\N	\N	1	Current
37	35	Female	1991-07-23	2011-01-28	\N	3	\N	\N	1	Current
41	39	Female	1961-04-28	2011-06-23	\N	3	\N	\N	1	Current
44	42	Female	1975-10-23	2011-06-23	\N	3	\N	\N	1	Current
45	43	Female	1956-04-25	1997-01-06	\N	1	\N	\N	1	Current
46	44	Female	1963-07-29	2010-03-31	\N	3	\N	\N	1	Current
52	50	Male	1973-12-31	2011-03-14	\N	3	\N	\N	1	Current
54	52	Female	1959-03-03	2000-01-19	\N	3	\N	\N	1	Current
57	55	Female	1960-10-06	1999-07-19	\N	2	\N	\N	1	Current
61	59	Female	1957-02-24	2002-03-04	\N	1	\N	\N	1	Current
6	4	Female	1964-11-16	2008-04-09	\N	3	\N	\N	1	Current
7	5	Female	1962-06-15	2004-06-23	\N	3	\N	\N	1	Current
\.


--
-- Data for Name: staffskills; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY staffskills (id, staffid, referenceitemid, level) FROM stdin;
1	5	1	QUALIFIED_INDEPENDENT
2	8	1	QUALIFIED_INDEPENDENT
3	9	1	QUALIFIED_INDEPENDENT
4	14	1	QUALIFIED_INDEPENDENT
6	16	1	QUALIFIED_INDEPENDENT
9	20	1	QUALIFIED_INDEPENDENT
11	24	1	QUALIFIED_INDEPENDENT
12	22	1	QUALIFIED_INDEPENDENT
13	25	1	QUALIFIED_INDEPENDENT
15	30	1	QUALIFIED_INDEPENDENT
17	32	1	QUALIFIED_INDEPENDENT
19	35	1	QUALIFIED_INDEPENDENT
20	38	1	QUALIFIED_INDEPENDENT
22	40	1	QUALIFIED_INDEPENDENT
24	47	1	QUALIFIED_INDEPENDENT
25	49	1	QUALIFIED_INDEPENDENT
27	51	1	QUALIFIED_INDEPENDENT
28	53	1	QUALIFIED_INDEPENDENT
31	59	1	QUALIFIED_INDEPENDENT
32	60	1	QUALIFIED_INDEPENDENT
26	50	1	QUALIFIED_INDEPENDENT
29	55	1	QUALIFIED_INDEPENDENT
30	56	1	QUALIFIED_INDEPENDENT
23	43	1	QUALIFIED_INDEPENDENT
21	39	1	QUALIFIED_INDEPENDENT
18	33	1	QUALIFIED_INDEPENDENT
16	31	1	QUALIFIED_INDEPENDENT
14	27	1	QUALIFIED_INDEPENDENT
10	23	1	QUALIFIED_INDEPENDENT
7	17	1	QUALIFIED_INDEPENDENT
5	15	1	QUALIFIED_INDEPENDENT
8	19	1	QUALIFIED_INDEPENDENT
\.


--
-- Data for Name: stafftypes; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY stafftypes (id, name) FROM stdin;
1	Administrator
2	Coordinator
3	Other
\.


--
-- Data for Name: studentcommunicatonxref; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY studentcommunicatonxref (communicationid, studentid) FROM stdin;
\.


--
-- Data for Name: studentconsents; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY studentconsents (id, consentid, studentid) FROM stdin;
65	3	22
66	2	22
67	1	22
26	3	9
27	2	9
28	1	9
131	3	44
132	2	44
133	1	44
59	3	20
60	2	20
61	1	20
86	3	29
87	2	29
88	1	29
98	3	33
99	2	33
50	3	17
51	2	17
100	1	33
44	3	15
45	2	15
110	3	37
119	3	40
120	2	40
121	1	40
107	3	36
108	2	36
32	3	11
101	3	34
102	2	34
23	3	8
24	2	8
25	1	8
104	3	35
105	2	35
106	1	35
35	3	12
36	2	12
38	3	13
111	2	37
112	1	37
47	3	16
48	2	16
49	1	16
62	3	21
63	2	21
64	1	21
68	3	23
69	2	23
70	1	23
71	3	24
72	2	24
73	1	24
83	3	28
84	2	28
85	1	28
53	3	18
54	2	18
134	3	45
135	2	45
80	3	27
17	3	6
18	2	6
20	3	7
128	3	43
129	2	43
130	1	43
39	2	13
40	1	13
41	3	14
42	2	14
43	1	14
5	3	2
6	2	2
74	3	25
77	3	26
78	2	26
79	1	26
81	2	27
136	1	45
122	3	41
123	2	41
124	1	41
92	3	31
93	2	31
94	1	31
33	2	11
34	1	11
55	1	18
29	3	10
30	2	10
31	1	10
75	2	25
76	1	25
56	3	19
57	2	19
58	1	19
116	3	39
117	2	39
118	1	39
113	3	38
114	2	38
115	1	38
14	3	5
15	2	5
16	1	5
21	2	7
22	1	7
11	3	4
12	2	4
95	3	32
125	3	42
126	2	42
127	1	42
96	2	32
97	1	32
103	1	34
89	3	30
90	2	30
7	1	2
179	3	60
180	2	60
218	3	73
219	2	73
220	1	73
239	3	80
240	2	80
197	3	66
272	3	91
242	3	81
243	2	81
206	3	69
207	2	69
208	1	69
212	3	71
213	2	71
215	3	72
216	2	72
217	1	72
245	3	82
246	2	82
247	1	82
227	3	76
228	2	76
229	1	76
224	3	75
225	2	75
188	3	63
189	2	63
190	1	63
236	3	79
237	2	79
167	3	56
226	1	75
140	3	47
141	2	47
248	3	83
249	2	83
250	1	83
233	3	78
234	2	78
235	1	78
170	3	57
171	2	57
194	3	65
195	2	65
182	3	61
185	3	62
186	2	62
254	3	85
203	3	68
204	2	68
205	1	68
221	3	74
222	2	74
223	1	74
176	3	59
177	2	59
241	1	80
155	3	52
156	2	52
157	1	52
257	3	86
258	2	86
259	1	86
260	3	87
261	2	87
262	1	87
142	1	47
255	2	85
238	1	79
266	3	89
267	2	89
268	1	89
158	3	53
159	2	53
160	1	53
161	3	54
162	2	54
163	1	54
230	3	77
231	2	77
172	1	57
183	2	61
184	1	61
143	3	48
144	2	48
214	1	71
269	3	90
270	2	90
271	1	90
146	3	49
147	2	49
209	3	70
210	2	70
145	1	48
168	2	56
148	1	49
173	3	58
174	2	58
175	1	58
149	3	50
150	2	50
151	1	50
191	3	64
192	2	64
193	1	64
200	3	67
201	2	67
202	1	67
198	2	66
199	1	66
251	3	84
252	2	84
253	1	84
263	3	88
264	2	88
265	1	88
164	3	55
165	2	55
166	1	55
137	3	46
138	2	46
139	1	46
299	3	100
300	2	100
301	1	100
356	3	119
357	2	119
358	1	119
343	1	114
316	1	105
324	2	108
284	3	95
169	1	56
303	2	101
283	1	94
281	3	94
196	1	65
308	3	103
298	1	99
278	3	93
325	1	108
348	2	116
349	1	116
350	3	117
351	2	117
352	1	117
353	3	118
304	1	101
302	3	101
287	3	96
285	2	95
286	1	95
334	1	111
109	1	36
178	1	59
291	2	97
292	1	97
339	2	113
340	1	113
336	2	112
293	3	98
321	2	107
322	1	107
337	1	112
331	1	110
329	3	110
312	2	104
277	1	92
82	1	27
359	1	120
338	3	113
335	3	112
317	3	106
288	2	96
289	1	96
46	1	15
318	2	106
319	1	106
320	3	107
52	1	17
326	3	109
360	2	120
313	1	104
311	3	104
273	2	91
274	1	91
244	1	81
327	2	109
344	3	115
354	2	118
355	1	118
13	1	4
232	1	77
279	2	93
280	1	93
294	2	98
295	1	98
305	3	102
309	2	103
310	1	103
306	2	102
330	2	110
345	2	115
346	1	115
332	3	111
333	2	111
307	1	102
347	3	116
296	3	99
19	1	6
37	1	12
152	3	51
361	3	120
256	1	85
282	2	94
153	2	51
154	1	51
362	4	120
187	1	62
275	3	92
276	2	92
297	2	99
328	1	109
181	1	60
91	1	30
341	3	114
342	2	114
211	1	70
314	3	105
315	2	105
323	3	108
290	3	97
\.


--
-- Data for Name: studentevents; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY studentevents (id, eventid, groupedstudentid, amountpaid, attended, remarks, chargetxid) FROM stdin;
1	1	1	5	t		1
2	52	27	0	f	\N	\N
3	52	26	0	f	\N	\N
4	52	28	0	f	\N	\N
5	52	30	0	f	\N	\N
6	52	29	0	f	\N	\N
7	636	313	0	f	\N	\N
8	636	310	0	f	\N	\N
9	636	311	0	f	\N	\N
10	636	314	0	f	\N	\N
11	636	312	0	f	\N	\N
12	636	308	0	f	\N	\N
13	636	849	0	f	\N	\N
14	636	850	0	f	\N	\N
15	636	309	0	f	\N	\N
17	220	259	5	t	DID NOT PAY 	9
18	220	261	0	f		\N
20	220	258	5	t		5
19	220	260	5	t		7
16	220	257	0	f		\N
22	661	259	0	t		11
23	661	261	0	f		\N
25	661	258	5	t		12
24	661	260	5	t		14
21	661	257	0	f		\N
\.


--
-- Data for Name: studentfundingsources; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY studentfundingsources (id, studentid, fundingsrcid, outletid, fundinglevel, fundinghours, fundinghoursused, fundingstartdate, active) FROM stdin;
5	28	9	2	Moderate	6	6	0011-01-01	t
50	67	9	1	Moderate	6	6	0011-01-01	t
3	20	9	2	Moderate	6	0	0011-01-01	t
79	52	9	1	Moderate	6	0	2011-01-01	t
36	14	9	1	Moderate	6	6	0011-01-01	t
68	5	9	1	Moderate	6	0	2011-01-01	t
64	34	11	5	Moderate	6	6	2011-01-01	t
57	91	9	1	Moderate	6	0	0011-01-01	t
14	64	9	2	Moderate	6	0	0011-01-01	t
69	12	9	1	Moderate	6	0	2011-01-01	t
70	13	9	1	Moderate	6	0	2011-01-01	t
23	84	9	2	Moderate	6	6	0011-01-01	t
8	37	9	2	Moderate	6	0	0011-01-01	t
71	16	9	1	Moderate	6	0	2011-01-01	t
38	19	9	1	Moderate	6	0	0011-01-01	t
4	21	9	2	Moderate	6	0	0011-01-01	t
60	96	9	1	Moderate	6	0	0011-01-01	t
73	26	9	1	Moderate	6	0	2011-01-01	t
74	27	9	1	Moderate	6	0	2011-01-01	t
76	35	9	1	Moderate	6	0	2011-01-01	t
75	29	9	1	Moderate	6	0	2011-01-01	t
41	31	9	1	Moderate	6	0	0011-01-01	t
45	47	9	1	Moderate	6	0	0011-01-01	t
13	63	9	2	Moderate	6	0	0011-01-01	t
35	11	9	1	Moderate	6	0	0011-01-01	t
46	50	9	1	Moderate	6	0	0011-01-01	t
9	39	9	2	Moderate	6	0	0011-01-01	t
21	80	9	2	Moderate	6	12	0011-01-01	t
10	45	9	2	Moderate	6	0	0011-01-01	t
81	55	9	1	Moderate	6	0	2011-01-01	t
34	10	9	1	Moderate	6	0	0011-01-01	t
37	15	9	1	Moderate	6	0	0011-01-01	t
42	36	9	1	Moderate	6	0	0011-01-01	t
55	86	9	1	Moderate	6	0	0011-01-01	t
11	51	9	2	Moderate	6	0	0011-01-01	t
29	114	9	2	Moderate	6	0	0011-01-01	t
80	53	9	1	Moderate	6	0	2011-01-01	t
62	109	9	1	Moderate	6	0	0011-01-01	t
40	24	9	1	Moderate	6	0	0011-01-01	t
43	42	9	1	Moderate	6	12	0011-01-01	t
49	61	9	1	Moderate	6	0	0011-01-01	t
65	62	12	6	Moderate	6	0	2011-01-01	t
2	8	9	2	Moderate	6	0	2011-01-01	t
51	73	9	1	Moderate	6	0	0011-01-01	t
15	66	9	2	Moderate	6	0	0011-01-01	t
44	43	9	1	Moderate	6	0	0011-01-01	t
16	68	9	2	Moderate	6	0	0011-01-01	t
61	106	9	1	Moderate	6	6	0011-01-01	t
18	71	9	2	Moderate	6	0	0011-01-01	t
19	72	9	2	Moderate	6	0	0011-01-01	t
53	82	9	1	Moderate	6	0	0011-01-01	t
24	90	9	2	Moderate	6	0	0011-01-01	t
78	49	9	1	Moderate	6	0	2011-01-01	t
27	98	9	2	Moderate	6	0	0011-01-01	t
20	75	9	2	Moderate	6	0	0011-01-01	t
54	83	9	1	Moderate	6	0	0011-01-01	t
7	32	9	2	Moderate	6	0	0011-01-01	t
6	30	9	2	Moderate	6	0	0011-01-01	t
47	56	9	1	Moderate	6	0	0011-01-01	t
56	88	9	1	Moderate	6	0	0011-01-01	t
48	58	9	1	Moderate	6	0	0011-01-01	t
22	81	9	2	Moderate	6	0	0011-01-01	t
58	92	9	1	Moderate	6	0	0011-01-01	t
66	102	13	7	Moderate	6	0	2011-01-01	t
32	4	9	1	Moderate	6	0	0011-01-01	t
26	97	9	2	Moderate	6	0	0011-01-01	t
17	70	9	2	Moderate	6	0	0011-01-01	t
30	115	9	2	Moderate	6	12	0011-01-01	t
28	103	9	2	Moderate	6	0	0011-01-01	t
52	77	9	1	Moderate	6	6	0011-01-01	t
33	9	9	1	Moderate	6	0	0011-01-01	t
63	111	9	1	Moderate	6	0	2011-01-01	t
1	2	10	3	Moderate	6	0	0011-01-01	t
25	93	9	2	Moderate	6	12	0011-01-01	t
31	116	9	2	Moderate	6	0	0011-01-01	t
67	118	13	7	Moderate	6	0	2011-01-01	t
77	44	9	1	Moderate	6	6	2011-01-01	t
84	74	9	1	Moderate	6	0	2011-01-01	t
110	6	14	9	Moderate	6	0	2011-01-01	t
97	17	9	2	Moderate	6	0	2011-01-01	t
111	22	14	9	Moderate	6	0	2011-01-01	t
112	40	14	9	Moderate	6	0	2011-01-01	t
101	46	9	2	Moderate	6	0	2011-01-01	t
113	54	14	9	Moderate	6	0	2011-01-01	t
103	57	9	2	Moderate	6	0	2011-01-01	t
83	69	9	1	Moderate	6	0	2011-01-01	t
85	76	9	1	Moderate	6	0	2011-01-01	t
115	78	14	8	Moderate	6	0	2011-01-01	t
105	95	9	2	Moderate	6	0	2011-01-01	t
39	23	9	1	Moderate	6	6	0011-01-01	t
82	60	9	1	Moderate	6	0	2011-01-01	t
91	107	9	1	Moderate	6	0	2011-01-01	t
107	104	9	2	Moderate	6	6	2011-01-01	t
108	110	9	2	Moderate	6	0	2011-01-01	t
102	48	9	2	Moderate	6	6	2011-01-01	t
59	94	9	1	Moderate	6	6	0011-01-01	t
116	41	9	1	Moderate	6	0	2011-07-01	f
117	120	14	9	Moderate	6	0	2010-01-01	f
88	89	9	1	Moderate	6	0	2011-01-01	t
72	25	9	1	Moderate	6	6	2011-01-01	t
98	18	9	2	Moderate	6	0	2011-01-01	t
114	87	14	9	Moderate	6	0	2011-01-01	t
89	101	9	1	Moderate	6	0	2011-01-01	t
92	108	9	1	Moderate	6	6	2011-01-01	t
95	117	9	1	Moderate	6	6	2011-01-01	t
12	59	9	2	Moderate	6	0	0011-01-01	t
106	99	9	2	Moderate	6	0	2011-01-01	t
99	33	9	2	Moderate	6	0	2011-01-01	t
96	7	9	2	Moderate	6	12	2011-01-01	t
100	38	9	2	Moderate	6	0	2011-01-01	t
104	65	9	2	Moderate	6	0	2011-01-01	t
90	105	9	1	Moderate	6	0	2011-01-01	t
94	113	9	1	Moderate	6	0	2011-01-01	t
93	112	9	1	Moderate	6	0	2011-01-01	t
87	85	9	1	Moderate	6	0	2011-01-01	t
86	79	9	1	Moderate	6	0	2011-01-01	t
\.


--
-- Data for Name: studentgroups; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY studentgroups (id, programid, name, status) FROM stdin;
28	28	Multimedia Wednesday	\N
25	12	Coop Tuesday	\N
80	73	Transport 3 AM Tuesday	\N
12	12	Coop Monday	\N
2	1	Transport 1 Monday AM	\N
13	14	Herb Farm Tuesday	\N
4	3	TPB Wyong Monday	\N
14	15	Hi Guys Tuesday	\N
15	16	Sensory Art Felt Tuesday	\N
42	14	Herb Farm Thursday	\N
18	19	Water Fitness Tuesday	\N
17	18	GMSociety Tuesday	\N
20	21	CA Tuesday	\N
19	20	Living Skills Tuesday	\N
1	3	Monday - Wyong	\N
22	23	Gym Swim Tuesday	\N
21	22	Events In The Office Tuesday	\N
7	7	Home Skills 1 Monday	\N
11	11	L2L Monday	\N
23	24	Radio Tuesday	\N
8	8	Home Skills 2 Monday	\N
24	11	L2L Tuesday	\N
34	34	Stepping Out Wednesday	\N
26	26	Care for Community Wednesday	\N
5	5	TPB Gosford Monday	\N
27	27	Gos Mow Budget Wednesday	\N
29	29	Laundry Aged Care Wednesday	\N
30	30	Dance Fitness Wednesday	\N
48	42	Fitness Thursday	\N
10	10	CA Monday	\N
31	31	CA Wednesday	\N
67	49	Transport 1 PM Monday	\N
32	32	Maintenance Wednesday	\N
33	33	Gals Group Wednesday	\N
47	41	Scrapbooking Thursday	\N
35	35	Gardenening Wednesday	\N
36	11	L2L Wednesday	\N
37	12	Coop Wednesday	\N
38	36	Camp Break Thursday	\N
39	37	Boccia Thursday	\N
40	38	In the office Thursday	\N
41	27	Gos MOW Budget Thursday	\N
16	17	Dairy Art Tuesday	\N
43	18	GMSociety Thursday	\N
44	39	Soing Creative Dance Thursday	\N
45	15	Hi Guys Thursday 	\N
46	40	Band Thursday	\N
9	9	Sensory Music Monday	\N
54	46	CA Friday 1 Out about	\N
49	11	L2L Thursday	\N
50	12	Coop Thursday	\N
52	44	Puppets Friday	\N
53	45	Props Friday	\N
55	47	CA Friday 2 Get abouts	\N
56	48	CA The Gang Friday	\N
57	12	Coop Friday	\N
99	97	Transport 4 AM Wednesday	\N
93	84	Transport 8 PM Tuesday	\N
58	50	Transport 2 AM Monday	\N
81	77	Transport 5 AM Tuesday	\N
68	51	Transport 2 PM Monday	\N
59	52	Transport 3 AM Monday	\N
76	66	Transport 10 PM Monday	\N
60	54	Transport 4 AM Monday	\N
91	80	Transport 6 PM Tuesday	\N
88	72	Transport 2 PM Tuesday	\N
77	68	Transport 11 PM Monday	\N
69	53	Transport 3 PM Monday	\N
61	56	Transport 5 AM Monday	\N
62	58	Transport 6 AM Monday	\N
63	64	Transport 9 AM Monday	\N
70	55	Transport 4 PM Monday	\N
82	79	Transport 6 AM Tuesday	\N
71	57	Transport 5 PM Monday	\N
64	65	Transport 10 AM Monday	\N
78	69	Transport 1 AM Tuesday	\N
72	59	Transport 6 PM Monday	\N
65	67	Transport 11 AM Monday	\N
66	60	Transport 7 AM Monday	\N
73	61	Transport 7 PM Monday	\N
74	63	Transport 8 PM Monday	\N
101	105	Transport 8 AM Wednesday	\N
75	157	Transport 9 PM Monday	\N
86	87	Transport 10 AM Tuesday	\N
84	83	Transport 8 AM Tuesday	\N
79	71	Transport 2 AM Tuesday	\N
94	86	Transport 9 PM Tuesday	\N
89	74	Transport 3 PM Tuesday	\N
85	85	Transport 9 AM Tuesday	\N
83	81	Transport 7 AM Tuesday	\N
90	78	Transport 5 PM Tuesday	\N
87	70	Transport 1 PM Tuesday	\N
96	91	Transport 1 AM Wednesday	\N
95	88	Transport 10 PM Tuesday	\N
92	82	Transport 7 PM Tuesday	\N
97	93	Transport 2 AM Wednesday	\N
98	95	Transport 3 AM Wednesday	\N
100	99	Transport 5 AM Wednesday	\N
102	107	Transport 9 AM Wednesday	\N
103	109	Transport 10 AM Wednesday	\N
104	111	Transport 11 AM Wednesday	\N
105	92	Transport 1 PM Wednesday	\N
106	94	Transport 2 PM Wednesday	\N
107	96	Transport 3 PM Wednesday	\N
108	98	Transport 4 PM Wednesday	\N
109	100	Transport 5 PM Wednesday	\N
110	106	Transport 8 PM Wednesday	\N
111	108	Transport 9 PM Wednesday	\N
112	110	Transport 10 PM Wednesday	\N
6	6	Mixed Media Art Mon	\N
113	112	Transport 11 PM Wednesday	\N
128	120	Transport 4 PM Thursday	\N
114	113	Transport 1 AM Thursday	\N
129	122	Transport 5 PM Thursday	\N
115	115	Transport 2 AM Thursday	\N
145	138	Transport 2 PM Friday	\N
130	124	Transport 6 PM Thursday	\N
116	117	Transport 3 AM Thursday	\N
131	126	Transport 7 PM Thursday	\N
117	119	Transport 4 AM Thursday	\N
132	128	Transport 8 PM Thursday	\N
146	140	Transport 3 PM Friday	\N
118	121	Transport 5 AM Thursday	\N
133	130	Transport 9 PM Thursday	\N
119	123	Transport 6 AM Thursday	\N
120	125	Transport 7 AM Thursday	\N
134	132	Transport 10 PM Thursday	\N
121	127	Transport 8 AM Thursday	\N
122	129	Transport 9 AM Thursday	\N
147	142	Transport 4 PM Friday	\N
135	134	Transport 11 PM Thursday	\N
124	133	Transport 11 AM Thursday	\N
136	135	Transport 1 AM Friday	\N
148	144	Transport 5 PM Friday	\N
125	114	Transport 1 PM Thursday	\N
137	137	Transport 2 AM Friday	\N
126	116	Transport 2 PM Thursday	\N
149	150	Transport 8 PM Friday	\N
150	154	Transport 10 PM Friday	\N
127	118	Transport 3 PM Thursday	\N
138	139	Transport 3 AM Friday	\N
152	156	Transport 11 PM Friday	\N
139	141	Transport 4 AM Friday	\N
153	131	Transport 10 AM Thursday	\N
140	143	Transport 5 AM Friday	\N
141	149	Transport 8 AM Friday	\N
142	153	Transport 10 AM Friday	\N
143	155	Transport 11 AM Friday	\N
51	43	Options Theatre Co Friday	\N
144	136	Transport 1PM Friday	\N
\.


--
-- Data for Name: studentguardiansxref; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY studentguardiansxref (contactid, studentid) FROM stdin;
\.


--
-- Data for Name: students; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY students (id, cisid, mdsid, gender, dob, contactid, mailingaddress, photoid, transport, activefundingsrcid, status, mailaddressdefault) FROM stdin;
11	859429	21611	Male	1983-11-01	82	83	\N	t	35	\N	t
34	864828	27811	Female	1991-12-05	128	129	\N	t	64	\N	t
80	859508	21608	Male	1998-07-11	220	221	\N	t	21	\N	t
38	1003314	20438	Female	1979-03-04	136	137	\N	t	100	\N	f
47	859472	21611	Male	1983-01-26	154	155	\N	t	45	\N	t
51	859481	21608	Male	1989-06-13	162	163	\N	t	11	\N	t
15	888514	21611	Male	1977-09-14	90	91	\N	t	37	\N	t
37	883324	21608	Male	1991-06-01	134	135	\N	t	8	\N	t
89	859624	6406	Male	1978-09-26	238	239	\N	t	88	\N	t
87	PSP09053	24274	Male	1991-11-07	234	235	\N	t	114	\N	t
86	1-272303662	21611	Male	1991-08-29	232	233	\N	t	55	\N	t
56	859443	21611	Male	1981-03-10	172	173	\N	t	47	\N	t
25	878309	6406	Female	1976-01-16	110	111	\N	t	72	\N	t
24	1001227	21611	Male	1981-01-22	108	109	\N	t	40	\N	t
4	899599	21611	Female	1989-03-18	72	\N	\N	t	32	\N	t
77	859602	21611	Female	1983-07-24	214	215	\N	t	52	\N	t
49	859475	6406	Female	1975-11-04	158	159	\N	t	78	\N	t
82	1005752	21611	Male	1986-01-10	224	225	\N	t	53	\N	t
64	999999	21608	Male	1992-06-09	188	189	\N	t	14	\N	t
73	878071	21611	Female	1981-12-03	206	207	\N	t	51	\N	t
35	859452	6406	Female	1980-08-19	130	131	\N	t	76	\N	t
85	1002602	6406	Male	1975-09-12	230	231	\N	t	87	\N	t
83	882116	21611	Female	1984-04-17	226	227	\N	t	54	\N	t
59	876663	21608	Male	1988-06-24	178	179	\N	t	12	\N	t
32	859760	21608	Female	1982-05-14	124	125	\N	t	7	\N	t
70	882068	21608	Male	1990-07-07	200	201	\N	t	17	\N	t
48	870929	20186	Male	1980-03-07	156	157	\N	t	102	\N	t
5	880731	6406	Male	1978-08-07	73	\N	\N	t	68	\N	t
63	999999	21608	Male	1992-11-21	186	187	\N	t	13	\N	t
8	879119	21608	Male	1989-10-05	77	\N	\N	t	2	\N	t
67	888747	21611	Female	1983-09-05	194	195	\N	t	50	\N	t
45	859408	21608	Male	1988-12-14	150	151	\N	t	10	\N	t
13	859432	6406	Male	1976-12-27	86	87	\N	t	70	\N	t
27	859752	6406	Male	1978-04-13	114	115	\N	t	74	\N	t
81	888527	21608	Male	1991-10-16	222	223	\N	t	22	\N	t
75	859896	21608	Male	1988-02-09	210	211	\N	t	20	\N	t
58	1005450	21611	Female	1986-06-07	176	177	\N	t	48	\N	t
41	929110	21611	Female	1986-06-09	142	143	\N	t	\N	\N	t
44	1003118	6406	Male	1978-08-03	148	149	\N	t	77	\N	t
79	1002603	6406	Male	1974-10-17	218	219	\N	t	86	\N	t
55	859779	6406	Male	1979-08-23	170	171	\N	t	81	\N	t
30	1002121	21608	Male	1984-07-17	120	121	\N	t	6	\N	t
74	1002699	6406	Male	1977-01-14	208	209	\N	t	84	\N	t
26	859283	6406	Female	1976-01-27	112	113	\N	t	73	\N	t
52	859776	6406	Male	1979-06-28	164	165	\N	t	79	\N	t
17	859787	20186	Female	1980-08-17	94	95	\N	t	97	\N	t
9	PSP08063	21611	Female	1991-02-21	78	79	\N	t	33	\N	t
69	1002760	6406	Female	1979-06-16	198	199	\N	t	83	\N	t
23	881663	21611	Male	1987-07-17	106	107	\N	t	39	\N	t
60	881159	6406	Male	1978-03-19	180	181	\N	t	82	\N	t
120	999999	999999	Female	1992-07-11	318	\N	\N	f	\N	\N	t
29	864803	6406	Female	1977-11-18	118	119	\N	f	75	\N	f
20	877545	21608	Female	1988-03-06	100	101	\N	f	3	\N	f
40	930877	24273	Female	1991-09-20	140	141	\N	f	112	\N	f
46		30893	Female	1980-04-29	152	153	\N	f	101	\N	f
66	1004944	21608	Female	1985-07-17	192	193	\N	f	15	\N	f
72	1002105	21608	Female	1984-08-10	204	205	\N	f	19	\N	f
76	1002912	6406	Female	1977-03-03	212	213	\N	f	85	\N	f
12	1002609	6406	Female	1976-11-08	84	85	\N	f	69	\N	f
16	1002695	6406	Male	1991-01-09	92	93	\N	f	71	\N	f
22		24273	Female	1990-11-03	104	105	\N	f	111	\N	f
68	859892	21608	Male	1982-08-10	196	197	\N	f	16	\N	f
6	1-80098218	24273	Male	1990-03-13	74	\N	\N	f	110	\N	t
31	859758	21611	Female	1980-10-18	122	123	\N	f	41	\N	f
39	870859	21608	Female	1982-05-27	138	139	\N	f	9	\N	f
53	858904	6406	Male	1976-03-16	166	167	\N	f	80	\N	f
54		0	Female	\N	168	169	\N	f	113	\N	f
61	859784	21611	Female	1986-09-03	182	183	\N	f	49	\N	f
88	858915	21611	Female	1981-04-10	236	237	\N	f	56	\N	f
42	932755	21611	Male	1985-10-18	144	145	\N	f	43	\N	f
7	1002606	20186	Male	1976-04-07	75	76	\N	f	96	\N	f
100			Female	\N	260	261	\N	f	\N	\N	f
119	890684	21608	F	1986-06-16	298	299	\N	f	\N	\N	f
118			F	1900-01-01	296	297	\N	f	67	\N	f
84	864932	21608	Male	1983-01-13	228	229	\N	t	23	\N	t
50	1001757	21611	Female	1982-03-13	160	161	\N	t	46	\N	t
33	1003566	20438	Female	1980-11-25	126	127	\N	t	99	\N	t
18	859744	20186	Female	1980-05-03	96	97	\N	t	98	\N	t
95	1003226	20186	Male	1979-08-02	250	251	\N	f	105	\N	f
101	859407	6406	Female	1977-04-22	262	263	\N	t	89	\N	t
96	859521	21611	Male	1982-09-25	252	253	\N	t	60	\N	t
102	1-167940952	25391	Male	1981-04-28	264	265	\N	t	66	\N	t
14	880588	21611	Female	1984-09-24	88	89	\N	t	36	\N	t
94	891617	21611	Male	1984-01-26	248	249	\N	t	59	\N	t
111	865566	21611	Male	1984-08-26	282	283	\N	t	63	\N	t
36	1001756	21611	Male	1983-03-30	132	133	\N	t	42	\N	t
65	866637	20186	Male	1978-12-03	190	191	\N	t	104	\N	t
98	1001219	21608	Male	1981-10-08	256	257	\N	t	27	\N	t
19	859438	21611	Female	1983-03-28	98	99	\N	t	38	\N	t
28	862154	21608	Female	1991-06-14	116	117	\N	t	5	\N	t
114	1002106	21608	Female	1984-02-24	288	289	\N	t	29	\N	t
104	1003565	20186	Female	1980-05-22	268	269	\N	t	107	\N	t
99	883542	20186	Male	1978-12-02	258	259	\N	t	106	\N	t
93	1001215	21608	Male	1981-05-15	246	247	\N	t	25	\N	t
105	859622	6406	Female	1979-06-30	270	271	\N	t	90	\N	t
97		21608	Male	1988-06-09	254	255	\N	f	26	\N	f
21	1001224	21608	Female	1981-07-04	102	103	\N	t	4	\N	t
108	1002701	6406	Female	1977-06-26	276	277	\N	t	92	\N	t
71	1001758	21608	Male	1981-06-16	202	203	\N	f	18	\N	f
117	1003564	6406	Female	1980-06-23	294	295	\N	t	95	\N	t
57	1002702	20186	Female	1977-10-09	174	175	\N	f	103	\N	f
62	860004	25855	Female	1965-06-14	184	185	\N	f	65	\N	f
103	1-218818967	21608	Male	1990-02-22	266	267	\N	f	28	\N	f
106	859866	21611	Female	1983-01-17	272	273	\N	f	61	\N	f
107	1002700	6406	Male	1976-08-18	274	275	\N	f	91	\N	f
110	1003511	20186	Female	1980-10-10	280	281	\N	f	108	\N	t
115	872863	21608	Male	1990-04-20	290	291	\N	f	30	\N	f
116	870204	21608	Male	1990-04-07	292	293	\N	f	31	\N	f
113	858875	6406	Male	1978-01-04	286	287	\N	t	94	\N	t
112	1002704	6406	Female	1977-03-14	284	285	\N	t	93	\N	t
43	875465	21611	Male	1989-06-19	146	147	\N	t	44	\N	t
2	886273	21608	Female	1991-12-05	70	\N	\N	t	1	\N	t
92	1001205	21611	Male	1981-04-21	244	245	\N	t	58	\N	t
10	891262	21611	Female	1979-03-10	80	81	\N	t	34	\N	t
91	869937	21611	Male	1983-03-23	242	243	\N	t	57	\N	t
90	859397	21608	Male	1986-03-24	240	241	\N	t	24	\N	t
109	875665	24274	Female	1990-09-15	278	279	\N	t	62	\N	t
78	PSP09206	9999999	Male	1991-11-29	216	217	\N	t	115	\N	t
\.


--
-- Data for Name: studentspecialneedsxref; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY studentspecialneedsxref (studentid, specialneedid, status, reamarks) FROM stdin;
24	8	\N	\N
101	6	\N	\N
51	6	\N	\N
14	8	\N	\N
73	8	\N	\N
28	8	\N	\N
48	8	\N	\N
5	8	\N	\N
117	6	\N	\N
13	8	\N	\N
27	8	\N	\N
92	8	\N	\N
74	8	\N	\N
26	8	\N	\N
69	8	\N	\N
23	8	\N	\N
60	8	\N	\N
120	4	\N	\N
\.


--
-- Data for Name: transactions; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY transactions (id, contactid, studentid, studenteventid, programeventid, invoiceid, transactiontype, amount, remarks, transactiondate, createduserid, paymenttype) FROM stdin;
2	\N	23	1	1	1	DEBIT	5	\N	2011-11-22	1	Cash
1	\N	23	\N	1	1	CREDIT	5	\N	2011-11-22	1	\N
9	\N	7	\N	220	\N	CREDIT	5	\N	2012-01-18	1	\N
10	\N	7	17	220	\N	DEBIT	5	\N	2012-01-18	1	\N
5	\N	80	\N	220	\N	CREDIT	5	\N	2012-01-18	1	\N
6	\N	80	20	220	\N	DEBIT	5	\N	2012-01-18	1	\N
7	\N	93	\N	220	\N	CREDIT	5	\N	2012-01-18	1	\N
8	\N	93	19	220	\N	DEBIT	5	\N	2012-01-18	1	\N
11	\N	7	\N	661	\N	CREDIT	5	\N	2012-01-18	1	\N
12	\N	80	\N	661	\N	CREDIT	5	\N	2012-01-18	1	\N
13	\N	80	25	661	\N	DEBIT	5	\N	2012-01-18	1	Cash
14	\N	93	\N	661	\N	CREDIT	5	\N	2012-01-18	1	\N
15	\N	93	24	661	\N	DEBIT	5	\N	2012-01-18	1	Cash
\.


--
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY users (id, contactid, mailingaddressid, username, password, user_type, enabled, systemuser, createdon) FROM stdin;
1	8	\N	admin	pass	MGR	t	t	1990-01-08 00:00:00+11
37	44	\N		pass		t	f	\N
19	26	\N		pass		t	f	\N
41	48	\N		pass		t	f	\N
44	51	\N		pass		t	f	\N
45	52	\N		pass		t	f	\N
46	53	\N		pass		t	f	\N
20	27	\N		pass		t	f	\N
24	31	\N		pass		t	f	\N
22	29	\N		pass		t	f	\N
52	59	\N		pass		t	f	\N
26	33	\N		pass		t	f	\N
54	61	\N		pass		t	f	\N
57	64	\N		pass		t	f	\N
25	32	\N		pass		t	f	\N
61	68	\N		pass		t	f	\N
30	37	\N		pass		t	f	\N
32	39	\N		pass		t	f	\N
35	42	\N		pass		t	f	\N
38	45	\N		pass		t	f	\N
40	47	\N		pass		t	f	\N
47	54	\N		pass		t	f	\N
49	56	\N		pass		t	f	\N
51	58	\N		pass		t	f	\N
53	60	\N		pass		t	f	\N
59	66	\N		pass		t	f	\N
60	67	\N		pass		t	f	\N
48	55	\N		pass		t	f	\N
50	57	\N		pass		t	f	\N
55	62	\N		pass		t	f	\N
56	63	\N		pass		t	f	\N
58	65	\N		pass		t	f	\N
6	13	\N		pass		t	f	\N
3	10	\N		pass		t	f	\N
4	11	\N		pass		t	f	\N
7	14	\N		pass		t	f	\N
43	50	\N		pass		t	f	\N
42	49	\N		pass		t	f	\N
39	46	\N		pass		t	f	\N
33	40	\N		pass		t	f	\N
31	38	\N		pass		t	f	\N
27	34	\N		pass		t	f	\N
10	17	\N		pass		t	f	\N
11	18	\N		pass		t	f	\N
23	30	\N		pass		t	f	\N
5	12	\N		pass		t	f	\N
8	15	\N		pass		t	f	\N
13	20	\N		pass		t	f	\N
17	24	\N		pass		t	f	\N
15	22	\N		pass		t	f	\N
12	19	\N		pass		t	f	\N
18	25	\N		pass		t	f	\N
21	28	\N		pass		t	f	\N
28	35	\N		pass		t	f	\N
29	36	\N		pass		t	f	\N
9	16	\N		pass		t	f	\N
14	21	\N		pass		t	f	\N
16	23	\N		pass		t	f	\N
34	41	\N		pass		t	f	\N
36	43	\N		pass		t	f	\N
\.


--
-- Data for Name: vehicles; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY vehicles (id, name, registrationid, passengers, wheelchairs, status, type, photoid) FROM stdin;
1	Hiace 1	BG 47 FD	10	2	Active	Bus	\N
2	Hiace 2	AU 21 ZS	10	1	Active	Bus	\N
3	Hiace 3	AJ 98 VX	11	0	Active	Bus	\N
4	Imax 1	BH 24 DX	7	0	Active	Bus	\N
5	Imax 2	BG 79 ZF	7	0	Active	Bus	\N
6	Imax 3	BH 25 DY	7	0	Active	Bus	\N
7	Imax 4	BH 70 VX	7	0	Active	Bus	\N
8	Imax 5	BH 59 VX	7	0	Active	Bus	\N
9	Imax 6	BH 57 XY	7	0	Active	Bus	\N
10	Hiace 4	AJ 15 JH	11	0	Active	Bus	\N
11	Iload 1	BC 19 JW	4	0	Active	Bus	\N
\.


--
-- Data for Name: weekdays; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY weekdays (id, name) FROM stdin;
1	Monday
2	Tuesday
3	Wednesday
4	Thursday
5	Friday
\.


--
-- Name: authorities_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY authorities
    ADD CONSTRAINT authorities_pkey PRIMARY KEY (id);


--
-- Name: calendars_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY calendars
    ADD CONSTRAINT calendars_pkey PRIMARY KEY (id);


--
-- Name: checkrecords_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY checkrecords
    ADD CONSTRAINT checkrecords_pkey PRIMARY KEY (id);


--
-- Name: communication_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY communication
    ADD CONSTRAINT communication_pkey PRIMARY KEY (id);


--
-- Name: communicationcategories_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY communicationcategories
    ADD CONSTRAINT communicationcategories_pkey PRIMARY KEY (id);


--
-- Name: communicationnotes_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY communicationnotes
    ADD CONSTRAINT communicationnotes_pkey PRIMARY KEY (id);


--
-- Name: communications_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY communications
    ADD CONSTRAINT communications_pkey PRIMARY KEY (id);


--
-- Name: configurations_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY configurations
    ADD CONSTRAINT configurations_pkey PRIMARY KEY (id);


--
-- Name: consents_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY consents
    ADD CONSTRAINT consents_pkey PRIMARY KEY (id);


--
-- Name: contacts_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY contacts
    ADD CONSTRAINT contacts_pkey PRIMARY KEY (id);


--
-- Name: externalorganizations_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY externalorganizations
    ADD CONSTRAINT externalorganizations_pkey PRIMARY KEY (id);


--
-- Name: filedata_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY filedata
    ADD CONSTRAINT filedata_pkey PRIMARY KEY (id);


--
-- Name: fundingsources_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY fundingsources
    ADD CONSTRAINT fundingsources_pkey PRIMARY KEY (id);


--
-- Name: groupedstudents_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY groupedstudents
    ADD CONSTRAINT groupedstudents_pkey PRIMARY KEY (id);


--
-- Name: holidays_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY holidays
    ADD CONSTRAINT holidays_pkey PRIMARY KEY (id);


--
-- Name: hoursutilizedreport_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY hoursutilizedreport
    ADD CONSTRAINT hoursutilizedreport_pkey PRIMARY KEY (id);


--
-- Name: hoursutilizedreportitem_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY hoursutilizedreportitem
    ADD CONSTRAINT hoursutilizedreportitem_pkey PRIMARY KEY (id);


--
-- Name: invoiceitems_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY invoiceitems
    ADD CONSTRAINT invoiceitems_pkey PRIMARY KEY (id);


--
-- Name: invoices_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY invoices
    ADD CONSTRAINT invoices_pkey PRIMARY KEY (id);


--
-- Name: leavepolicies_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY leavepolicies
    ADD CONSTRAINT leavepolicies_pkey PRIMARY KEY (id);


--
-- Name: leavepolicydetails_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY leavepolicydetails
    ADD CONSTRAINT leavepolicydetails_pkey PRIMARY KEY (id);


--
-- Name: leaves_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY leaves
    ADD CONSTRAINT leaves_pkey PRIMARY KEY (id);


--
-- Name: locations_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY locations
    ADD CONSTRAINT locations_pkey PRIMARY KEY (id);


--
-- Name: outlets_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY outlets
    ADD CONSTRAINT outlets_pkey PRIMARY KEY (id);


--
-- Name: programeventcoordinatorxref_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY programeventcoordinatorxref
    ADD CONSTRAINT programeventcoordinatorxref_pkey PRIMARY KEY (programeventid, coordinatorid);


--
-- Name: programevents_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY programevents
    ADD CONSTRAINT programevents_pkey PRIMARY KEY (id);


--
-- Name: programs_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY programs
    ADD CONSTRAINT programs_pkey PRIMARY KEY (id);


--
-- Name: programtypes_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY programtypes
    ADD CONSTRAINT programtypes_pkey PRIMARY KEY (id);


--
-- Name: programweekxref_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY programweekxref
    ADD CONSTRAINT programweekxref_pkey PRIMARY KEY (programid, weekid);


--
-- Name: referenceitems_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY referenceitems
    ADD CONSTRAINT referenceitems_pkey PRIMARY KEY (id);


--
-- Name: reminders_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY reminders
    ADD CONSTRAINT reminders_pkey PRIMARY KEY (id);


--
-- Name: servicearea_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY servicearea
    ADD CONSTRAINT servicearea_pkey PRIMARY KEY (id);


--
-- Name: specialneeds_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY specialneeds
    ADD CONSTRAINT specialneeds_pkey PRIMARY KEY (id);


--
-- Name: staffcheckrecords_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY staffcheckrecords
    ADD CONSTRAINT staffcheckrecords_pkey PRIMARY KEY (id);


--
-- Name: staffmembers_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY staffmembers
    ADD CONSTRAINT staffmembers_pkey PRIMARY KEY (id);


--
-- Name: staffskills_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY staffskills
    ADD CONSTRAINT staffskills_pkey PRIMARY KEY (staffid, referenceitemid);


--
-- Name: stafftypes_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY stafftypes
    ADD CONSTRAINT stafftypes_pkey PRIMARY KEY (id);


--
-- Name: studentcommunicatonxref_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY studentcommunicatonxref
    ADD CONSTRAINT studentcommunicatonxref_pkey PRIMARY KEY (communicationid, studentid);


--
-- Name: studentconsents_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY studentconsents
    ADD CONSTRAINT studentconsents_pkey PRIMARY KEY (id);


--
-- Name: studentevents_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY studentevents
    ADD CONSTRAINT studentevents_pkey PRIMARY KEY (id);


--
-- Name: studentfundingsources_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY studentfundingsources
    ADD CONSTRAINT studentfundingsources_pkey PRIMARY KEY (id);


--
-- Name: studentgroups_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY studentgroups
    ADD CONSTRAINT studentgroups_pkey PRIMARY KEY (id);


--
-- Name: studentguardiansxref_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY studentguardiansxref
    ADD CONSTRAINT studentguardiansxref_pkey PRIMARY KEY (contactid, studentid);


--
-- Name: students_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY students
    ADD CONSTRAINT students_pkey PRIMARY KEY (id);


--
-- Name: studentspecialneedsxref_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY studentspecialneedsxref
    ADD CONSTRAINT studentspecialneedsxref_pkey PRIMARY KEY (studentid, specialneedid);


--
-- Name: transactions_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY transactions
    ADD CONSTRAINT transactions_pkey PRIMARY KEY (id);


--
-- Name: users_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


--
-- Name: vehicles_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY vehicles
    ADD CONSTRAINT vehicles_pkey PRIMARY KEY (id);


--
-- Name: weekdays_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY weekdays
    ADD CONSTRAINT weekdays_pkey PRIMARY KEY (id);


--
-- Name: authorities_userid_index; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX authorities_userid_index ON authorities USING btree (userid);


--
-- Name: filedata_sneedid_index; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX filedata_sneedid_index ON filedata USING btree (sneedid);


--
-- Name: filedata_staffid_index; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX filedata_staffid_index ON filedata USING btree (staffid);


--
-- Name: filedata_studentid_index; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX filedata_studentid_index ON filedata USING btree (studentid);


--
-- Name: filedata_vehicleid_index; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX filedata_vehicleid_index ON filedata USING btree (vehicleid);


--
-- Name: invoices_invoiceeid_index; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX invoices_invoiceeid_index ON invoices USING btree (invoiceeid);


--
-- Name: invoices_studentid_index; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX invoices_studentid_index ON invoices USING btree (studentid);


--
-- Name: locations_contactid_index; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX locations_contactid_index ON locations USING btree (contactid);


--
-- Name: outlets_id_index; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX outlets_id_index ON outlets USING btree (id);


--
-- Name: programeventcoordinatorxref_coordinatorid_index; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX programeventcoordinatorxref_coordinatorid_index ON programeventcoordinatorxref USING btree (coordinatorid);


--
-- Name: programeventcoordinatorxref_programeventid_index; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX programeventcoordinatorxref_programeventid_index ON programeventcoordinatorxref USING btree (programeventid);


--
-- Name: programevents_locationid_index; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX programevents_locationid_index ON programevents USING btree (locationid);


--
-- Name: programevents_programid_index; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX programevents_programid_index ON programevents USING btree (programid);


--
-- Name: programevents_studentgroupid_index; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX programevents_studentgroupid_index ON programevents USING btree (studentgroupid);


--
-- Name: programs_coordinatorid_index; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX programs_coordinatorid_index ON programs USING btree (coordinatorid);


--
-- Name: programs_programtypeid_index; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX programs_programtypeid_index ON programs USING btree (programtypeid);


--
-- Name: reminders_id_index; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX reminders_id_index ON reminders USING btree (id);


--
-- Name: specialneeds_photoid_index; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX specialneeds_photoid_index ON specialneeds USING btree (photoid);


--
-- Name: staffmembers_photoid_index; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX staffmembers_photoid_index ON staffmembers USING btree (photoid);


--
-- Name: staffmembers_stafftypeid_index; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX staffmembers_stafftypeid_index ON staffmembers USING btree (stafftypeid);


--
-- Name: studentevents_chargetxid_index; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX studentevents_chargetxid_index ON studentevents USING btree (chargetxid);


--
-- Name: studentevents_eventid_index; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX studentevents_eventid_index ON studentevents USING btree (eventid);


--
-- Name: studentevents_groupedstudentid_index; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX studentevents_groupedstudentid_index ON studentevents USING btree (groupedstudentid);


--
-- Name: studentfundingsources_fundingsrcid_index; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX studentfundingsources_fundingsrcid_index ON studentfundingsources USING btree (fundingsrcid);


--
-- Name: studentfundingsources_outletid_index; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX studentfundingsources_outletid_index ON studentfundingsources USING btree (outletid);


--
-- Name: studentfundingsources_studentid_index; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX studentfundingsources_studentid_index ON studentfundingsources USING btree (studentid);


--
-- Name: students_activefundingsrcid_index; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX students_activefundingsrcid_index ON students USING btree (activefundingsrcid);


--
-- Name: students_contactid_index; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX students_contactid_index ON students USING btree (contactid);


--
-- Name: students_mailingaddress_index; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX students_mailingaddress_index ON students USING btree (mailingaddress);


--
-- Name: students_photoid_index; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX students_photoid_index ON students USING btree (photoid);


--
-- Name: transactions_contactid_index; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX transactions_contactid_index ON transactions USING btree (contactid);


--
-- Name: transactions_createduserid_index; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX transactions_createduserid_index ON transactions USING btree (createduserid);


--
-- Name: transactions_invoiceid_index; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX transactions_invoiceid_index ON transactions USING btree (invoiceid);


--
-- Name: transactions_programeventid_index; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX transactions_programeventid_index ON transactions USING btree (programeventid);


--
-- Name: transactions_studentid_index; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX transactions_studentid_index ON transactions USING btree (studentid);


--
-- Name: users_contactid_index; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX users_contactid_index ON users USING btree (contactid);


--
-- Name: vehicles_photoid_index; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX vehicles_photoid_index ON vehicles USING btree (photoid);


--
-- Name: fk_authorities_userid; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY authorities
    ADD CONSTRAINT fk_authorities_userid FOREIGN KEY (userid) REFERENCES users(id) MATCH FULL;


--
-- Name: fk_communication_category; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY communications
    ADD CONSTRAINT fk_communication_category FOREIGN KEY (category) REFERENCES communicationcategories(id) MATCH FULL;


--
-- Name: fk_communication_keypersonfunding; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY communications
    ADD CONSTRAINT fk_communication_keypersonfunding FOREIGN KEY (keypersonfunding) REFERENCES fundingsources(id) MATCH FULL;


--
-- Name: fk_communication_keypersonorg; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY communications
    ADD CONSTRAINT fk_communication_keypersonorg FOREIGN KEY (keypersonorg) REFERENCES externalorganizations(id) MATCH FULL;


--
-- Name: fk_communication_keypersonstaff; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY communications
    ADD CONSTRAINT fk_communication_keypersonstaff FOREIGN KEY (keypersonstaff) REFERENCES staffmembers(id) MATCH FULL;


--
-- Name: fk_communication_keypersonstudent; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY communications
    ADD CONSTRAINT fk_communication_keypersonstudent FOREIGN KEY (keypersonstudent) REFERENCES students(id) MATCH FULL;


--
-- Name: fk_communication_keypersonvehicle; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY communications
    ADD CONSTRAINT fk_communication_keypersonvehicle FOREIGN KEY (keypersonvehicle) REFERENCES vehicles(id) MATCH FULL;


--
-- Name: fk_communication_reminderid; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY communications
    ADD CONSTRAINT fk_communication_reminderid FOREIGN KEY (reminderid) REFERENCES reminders(id) MATCH FULL;


--
-- Name: fk_communicationnotes_communicationid; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY communicationnotes
    ADD CONSTRAINT fk_communicationnotes_communicationid FOREIGN KEY (communicationid) REFERENCES communications(id) MATCH FULL;


--
-- Name: fk_externalorganizations_contactid; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY externalorganizations
    ADD CONSTRAINT fk_externalorganizations_contactid FOREIGN KEY (contactid) REFERENCES contacts(id) MATCH FULL;


--
-- Name: fk_externalorganizations_contactpersonid; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY externalorganizations
    ADD CONSTRAINT fk_externalorganizations_contactpersonid FOREIGN KEY (contactpersonid) REFERENCES contacts(id) MATCH FULL;


--
-- Name: fk_externalorganizations_serviceareaid; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY externalorganizations
    ADD CONSTRAINT fk_externalorganizations_serviceareaid FOREIGN KEY (serviceareaid) REFERENCES servicearea(id) MATCH FULL;


--
-- Name: fk_groupedstudents_dropoff; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY groupedstudents
    ADD CONSTRAINT fk_groupedstudents_dropoff FOREIGN KEY (dropoff) REFERENCES locations(id) MATCH FULL;


--
-- Name: fk_groupedstudents_groupid; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY groupedstudents
    ADD CONSTRAINT fk_groupedstudents_groupid FOREIGN KEY (groupid) REFERENCES studentgroups(id) MATCH FULL;


--
-- Name: fk_groupedstudents_pickup; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY groupedstudents
    ADD CONSTRAINT fk_groupedstudents_pickup FOREIGN KEY (pickup) REFERENCES locations(id) MATCH FULL;


--
-- Name: fk_groupedstudents_studentid; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY groupedstudents
    ADD CONSTRAINT fk_groupedstudents_studentid FOREIGN KEY (studentid) REFERENCES students(id) MATCH FULL;


--
-- Name: fk_holidays_calendarid; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY holidays
    ADD CONSTRAINT fk_holidays_calendarid FOREIGN KEY (calendarid) REFERENCES calendars(id) MATCH FULL;


--
-- Name: fk_hoursutilizedreportitem_reportid; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY hoursutilizedreportitem
    ADD CONSTRAINT fk_hoursutilizedreportitem_reportid FOREIGN KEY (reportid) REFERENCES hoursutilizedreport(id) MATCH FULL;


--
-- Name: fk_hoursutilizedreportitem_studentid; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY hoursutilizedreportitem
    ADD CONSTRAINT fk_hoursutilizedreportitem_studentid FOREIGN KEY (studentid) REFERENCES students(id) MATCH FULL;


--
-- Name: fk_invoices_invoiceeid; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY invoices
    ADD CONSTRAINT fk_invoices_invoiceeid FOREIGN KEY (invoiceeid) REFERENCES contacts(id) MATCH FULL;


--
-- Name: fk_invoices_studentid; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY invoices
    ADD CONSTRAINT fk_invoices_studentid FOREIGN KEY (studentid) REFERENCES students(id) MATCH FULL;


--
-- Name: fk_leavepolicydetails_leavepolicyid; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY leavepolicydetails
    ADD CONSTRAINT fk_leavepolicydetails_leavepolicyid FOREIGN KEY (leavepolicyid) REFERENCES leavepolicies(id) MATCH FULL;


--
-- Name: fk_leaves_staffid; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY leaves
    ADD CONSTRAINT fk_leaves_staffid FOREIGN KEY (staffid) REFERENCES staffmembers(id) MATCH FULL;


--
-- Name: fk_locations_contactid; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY locations
    ADD CONSTRAINT fk_locations_contactid FOREIGN KEY (contactid) REFERENCES contacts(id) MATCH FULL;


--
-- Name: fk_programeventcoordinatorxref_coordinatorid; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY programeventcoordinatorxref
    ADD CONSTRAINT fk_programeventcoordinatorxref_coordinatorid FOREIGN KEY (coordinatorid) REFERENCES staffmembers(id) MATCH FULL;


--
-- Name: fk_programeventcoordinatorxref_programeventid; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY programeventcoordinatorxref
    ADD CONSTRAINT fk_programeventcoordinatorxref_programeventid FOREIGN KEY (programeventid) REFERENCES programevents(id) MATCH FULL;


--
-- Name: fk_programevents_locationid; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY programevents
    ADD CONSTRAINT fk_programevents_locationid FOREIGN KEY (locationid) REFERENCES locations(id) MATCH FULL;


--
-- Name: fk_programevents_programid; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY programevents
    ADD CONSTRAINT fk_programevents_programid FOREIGN KEY (programid) REFERENCES programs(id) MATCH FULL;


--
-- Name: fk_programevents_studentgroupid; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY programevents
    ADD CONSTRAINT fk_programevents_studentgroupid FOREIGN KEY (studentgroupid) REFERENCES studentgroups(id) MATCH FULL;


--
-- Name: fk_programevents_vehicleid; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY programevents
    ADD CONSTRAINT fk_programevents_vehicleid FOREIGN KEY (vehicleid) REFERENCES vehicles(id) MATCH FULL;


--
-- Name: fk_programs_coordinatorid; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY programs
    ADD CONSTRAINT fk_programs_coordinatorid FOREIGN KEY (coordinatorid) REFERENCES staffmembers(id) MATCH FULL;


--
-- Name: fk_programs_programtypeid; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY programs
    ADD CONSTRAINT fk_programs_programtypeid FOREIGN KEY (programtypeid) REFERENCES programtypes(id) MATCH FULL;


--
-- Name: fk_programs_vehicleid; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY programs
    ADD CONSTRAINT fk_programs_vehicleid FOREIGN KEY (vehicleid) REFERENCES vehicles(id) MATCH FULL;


--
-- Name: fk_specialneeds_photoid; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY specialneeds
    ADD CONSTRAINT fk_specialneeds_photoid FOREIGN KEY (photoid) REFERENCES filedata(id) MATCH FULL;


--
-- Name: fk_staffcheckrecords_checkrecordid; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY staffcheckrecords
    ADD CONSTRAINT fk_staffcheckrecords_checkrecordid FOREIGN KEY (checkrecordid) REFERENCES checkrecords(id) MATCH FULL;


--
-- Name: fk_staffcheckrecords_staffid; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY staffcheckrecords
    ADD CONSTRAINT fk_staffcheckrecords_staffid FOREIGN KEY (staffid) REFERENCES staffmembers(id) MATCH FULL;


--
-- Name: fk_staffmembers_leavepolicyid; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY staffmembers
    ADD CONSTRAINT fk_staffmembers_leavepolicyid FOREIGN KEY (leavepolicyid) REFERENCES leavepolicies(id) MATCH FULL;


--
-- Name: fk_staffmembers_photoid; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY staffmembers
    ADD CONSTRAINT fk_staffmembers_photoid FOREIGN KEY (photoid) REFERENCES filedata(id) MATCH FULL;


--
-- Name: fk_staffmembers_stafftypeid; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY staffmembers
    ADD CONSTRAINT fk_staffmembers_stafftypeid FOREIGN KEY (stafftypeid) REFERENCES stafftypes(id) MATCH FULL;


--
-- Name: fk_staffskills_referenceitemid; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY staffskills
    ADD CONSTRAINT fk_staffskills_referenceitemid FOREIGN KEY (referenceitemid) REFERENCES referenceitems(id) MATCH FULL;


--
-- Name: fk_staffskills_staffid; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY staffskills
    ADD CONSTRAINT fk_staffskills_staffid FOREIGN KEY (staffid) REFERENCES staffmembers(id) MATCH FULL;


--
-- Name: fk_studentcommunicatonxref_communicationid; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY studentcommunicatonxref
    ADD CONSTRAINT fk_studentcommunicatonxref_communicationid FOREIGN KEY (communicationid) REFERENCES communications(id) MATCH FULL;


--
-- Name: fk_studentcommunicatonxref_studentid; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY studentcommunicatonxref
    ADD CONSTRAINT fk_studentcommunicatonxref_studentid FOREIGN KEY (studentid) REFERENCES students(id) MATCH FULL;


--
-- Name: fk_studentconsents_consentid; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY studentconsents
    ADD CONSTRAINT fk_studentconsents_consentid FOREIGN KEY (consentid) REFERENCES consents(id) MATCH FULL;


--
-- Name: fk_studentconsents_studentid; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY studentconsents
    ADD CONSTRAINT fk_studentconsents_studentid FOREIGN KEY (studentid) REFERENCES students(id) MATCH FULL;


--
-- Name: fk_studentevents_chargetxid; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY studentevents
    ADD CONSTRAINT fk_studentevents_chargetxid FOREIGN KEY (chargetxid) REFERENCES transactions(id) MATCH FULL;


--
-- Name: fk_studentevents_eventid; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY studentevents
    ADD CONSTRAINT fk_studentevents_eventid FOREIGN KEY (eventid) REFERENCES programevents(id) MATCH FULL;


--
-- Name: fk_studentevents_groupedstudentid; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY studentevents
    ADD CONSTRAINT fk_studentevents_groupedstudentid FOREIGN KEY (groupedstudentid) REFERENCES groupedstudents(id) MATCH FULL;


--
-- Name: fk_studentfundingsources_fundingsrcid; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY studentfundingsources
    ADD CONSTRAINT fk_studentfundingsources_fundingsrcid FOREIGN KEY (fundingsrcid) REFERENCES fundingsources(id) MATCH FULL;


--
-- Name: fk_studentfundingsources_outletid; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY studentfundingsources
    ADD CONSTRAINT fk_studentfundingsources_outletid FOREIGN KEY (outletid) REFERENCES outlets(id) MATCH FULL;


--
-- Name: fk_studentfundingsources_studentid; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY studentfundingsources
    ADD CONSTRAINT fk_studentfundingsources_studentid FOREIGN KEY (studentid) REFERENCES students(id) MATCH FULL;


--
-- Name: fk_studentgroups_programid; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY studentgroups
    ADD CONSTRAINT fk_studentgroups_programid FOREIGN KEY (programid) REFERENCES programs(id) MATCH FULL;


--
-- Name: fk_studentguardiansxref_contactid; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY studentguardiansxref
    ADD CONSTRAINT fk_studentguardiansxref_contactid FOREIGN KEY (contactid) REFERENCES contacts(id) MATCH FULL;


--
-- Name: fk_studentguardiansxref_studentid; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY studentguardiansxref
    ADD CONSTRAINT fk_studentguardiansxref_studentid FOREIGN KEY (studentid) REFERENCES students(id) MATCH FULL;


--
-- Name: fk_students_activefundingsrcid; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY students
    ADD CONSTRAINT fk_students_activefundingsrcid FOREIGN KEY (activefundingsrcid) REFERENCES studentfundingsources(id) MATCH FULL;


--
-- Name: fk_students_contactid; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY students
    ADD CONSTRAINT fk_students_contactid FOREIGN KEY (contactid) REFERENCES contacts(id) MATCH FULL;


--
-- Name: fk_students_mailingaddress; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY students
    ADD CONSTRAINT fk_students_mailingaddress FOREIGN KEY (mailingaddress) REFERENCES contacts(id) MATCH FULL;


--
-- Name: fk_students_photoid; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY students
    ADD CONSTRAINT fk_students_photoid FOREIGN KEY (photoid) REFERENCES filedata(id) MATCH FULL;


--
-- Name: fk_studentspecialneedsxref_specialneedid; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY studentspecialneedsxref
    ADD CONSTRAINT fk_studentspecialneedsxref_specialneedid FOREIGN KEY (specialneedid) REFERENCES specialneeds(id) MATCH FULL;


--
-- Name: fk_studentspecialneedsxref_studentid; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY studentspecialneedsxref
    ADD CONSTRAINT fk_studentspecialneedsxref_studentid FOREIGN KEY (studentid) REFERENCES students(id) MATCH FULL;


--
-- Name: fk_transactions_contactid; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY transactions
    ADD CONSTRAINT fk_transactions_contactid FOREIGN KEY (contactid) REFERENCES contacts(id) MATCH FULL;


--
-- Name: fk_transactions_createduserid; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY transactions
    ADD CONSTRAINT fk_transactions_createduserid FOREIGN KEY (createduserid) REFERENCES users(id) MATCH FULL;


--
-- Name: fk_transactions_invoiceid; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY transactions
    ADD CONSTRAINT fk_transactions_invoiceid FOREIGN KEY (invoiceid) REFERENCES invoices(id) MATCH FULL;


--
-- Name: fk_transactions_programeventid; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY transactions
    ADD CONSTRAINT fk_transactions_programeventid FOREIGN KEY (programeventid) REFERENCES programevents(id) MATCH FULL;


--
-- Name: fk_transactions_studentid; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY transactions
    ADD CONSTRAINT fk_transactions_studentid FOREIGN KEY (studentid) REFERENCES students(id) MATCH FULL;


--
-- Name: fk_users_contactid; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY users
    ADD CONSTRAINT fk_users_contactid FOREIGN KEY (contactid) REFERENCES contacts(id) MATCH FULL;


--
-- Name: fk_users_mailingaddressid; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY users
    ADD CONSTRAINT fk_users_mailingaddressid FOREIGN KEY (mailingaddressid) REFERENCES contacts(id) MATCH FULL;


--
-- Name: fk_vehicles_photoid; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY vehicles
    ADD CONSTRAINT fk_vehicles_photoid FOREIGN KEY (photoid) REFERENCES filedata(id) MATCH FULL;


--
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


--
-- PostgreSQL database dump complete
--

