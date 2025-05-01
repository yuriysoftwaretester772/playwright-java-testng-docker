FROM mcr.microsoft.com/playwright/java:v1.42.0-jammy

# Set working directory
WORKDIR /app

# Copy project files
COPY . .

# Add SLF4J implementation to fix logging warning
RUN if grep -q '</dependencies>' pom.xml; then \
    echo '<dependency>\n  <groupId>org.slf4j</groupId>\n  <artifactId>slf4j-simple</artifactId>\n  <version>1.7.36</version>\n  <scope>test</scope>\n</dependency>' >> pom.xml.fragment && \
    sed -i '/<\/dependencies>/e cat pom.xml.fragment' pom.xml && \
    rm pom.xml.fragment; \
    fi

# Run tests with a wrapper script to fix package.json issue
RUN echo '#!/bin/bash\n\
mkdir -p /tmp/playwright-java-*/package 2>/dev/null || true\n\
find /tmp -name "playwright-java-*" -type d -exec sh -c "mkdir -p {}/package && echo {\\"version\\": \\"1.42.0\\"} > {}/package/package.json" \\; 2>/dev/null || true\n\
mvn clean test -DBROWSER=${BROWSER:-chromium} -DHEADLESS=${HEADLESS:-true}\n\
' > /app/run-tests.sh && chmod +x /app/run-tests.sh

# Default command - run tests with wrapper script
CMD /app/run-tests.sh