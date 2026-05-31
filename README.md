# docker compose
go to /src/deploy/docker and do "./stack up"

# run native image
"${(@z)${CRUNTIME:-docker run --pull always}}" --name invoice-process-quarkus --rm -p 50400:50400 \
goafabric/invoice-process-quarkus:$(grep '^version=' gradle.properties | cut -d'=' -f2)