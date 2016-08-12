#!/usr/bin/env bash

cd /data/
./gradlew clean copyDownloadResources

# Copy downloads into 'plugins'
if [ -d "/data/build/downloads/plugins" ]; then
    cp /data/build/downloads/plugins/* /opt/xlr/server/plugins
fi

# Compile plugin
cd /data/
./gradlew test assemble -PxlReleaseHome=/opt/xlr/server