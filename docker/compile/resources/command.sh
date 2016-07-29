#!/usr/bin/env bash

# Compile plugin
cd /data/
./gradlew clean test assemble -PxlReleaseHome=/opt/xlr/server