CREATE TABLE "public"."client" (
    "id" VARCHAR(40) NOT NULL,
    "fullname" VARCHAR(128) NOT NULL,
    "description" VARCHAR(256) NOT NULL,
    CONSTRAINT "pk_client" PRIMARY KEY ("id")
);

