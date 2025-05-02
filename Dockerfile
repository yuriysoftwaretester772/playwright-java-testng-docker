FROM maven:3.9.5-eclipse-temurin-17

# Update package lists
RUN apt-get update

# Install core dependencies
RUN apt-get install -y \
    wget \
    curl \
    gnupg \
    libgtk-3-0 \
    libxss1 \
    libasound2 \
    libnss3

# Install additional dependencies
RUN apt-get install -y \
    libxcomposite1 \
    libxrandr2 \
    libxdamage1 \
    libgbm-dev \
    libx11-xcb1 \
    fonts-liberation

# Install media libraries
RUN apt-get install -y \
    libxslt1.1 \
    libvpx-dev \
    libopus0 \
    libwebp-dev

# Install text processing libraries
RUN apt-get install -y \
    libharfbuzz-icu0 \
    libenchant-2-2 \
    libsecret-1-0 \
    libhyphen0 \
    libflite1

# Install graphics and GStreamer libraries
RUN apt-get install -y \
    libgles2 \
    gstreamer1.0-plugins-base \
    gstreamer1.0-plugins-good \
    gstreamer1.0-libav \
    gstreamer1.0-tools \
    libevent-dev \
    libgstreamer-plugins-base1.0-dev

# Try to install problematic packages one by one
RUN apt-get install -y libwoff2-1 || echo "libwoff2-1 not available"
RUN apt-get install -y libwoff2dec-1-0 || echo "libwoff2dec-1-0 not available"
RUN apt-get install -y libgstreamer-plugins-bad1.0-0 || echo "libgstreamer-plugins-bad1.0-0 not available"
RUN apt-get install -y libmanette-0.2-0 || echo "libmanette-0.2-0 not available"

# Clean up
RUN rm -rf /var/lib/apt/lists/*

# Install Node.js for Playwright CLI internals
RUN curl -fsSL https://deb.nodesource.com/setup_20.x | bash - && \
    apt-get install -y nodejs

# Set working directory
WORKDIR /app

# Copy project files
COPY . .

# Install Playwright browsers with troubleshooting
RUN PLAYWRIGHT_BROWSERS_PATH=/app/pw-browsers \
    mvn exec:java \
    -Dexec.mainClass=com.microsoft.playwright.CLI \
    -Dexec.args="install --with-deps chromium" \
    && echo "✅ Playwright installed"

# Default command
CMD mvn clean test -DBROWSER=${BROWSER:-chromium} -DHEADLESS=${HEADLESS:-true}
