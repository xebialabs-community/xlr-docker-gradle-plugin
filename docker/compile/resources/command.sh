#!/usr/bin/env bash

# Copy downloads into 'plugins'
if [ -d "/data/build/downloads/plugins" ]; then
    cp /data/build/downloads/plugins/* /opt/xlr/server/plugins
fi

# Compile plugin
cd /data/
./gradlew clean test assemble -PxlReleaseHome=/opt/xlr/server