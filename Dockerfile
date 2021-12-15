# A compromised JDK below
FROM openjdk:18-alpine
# Other JDKs
# FROM openjdk:11
# FROM openjdk:17

# Final image only contains the distribution
ADD app/build/distributions/app.tar /

# Entrypoint, you must provide arguments
ENTRYPOINT ["/app/bin/app"]
