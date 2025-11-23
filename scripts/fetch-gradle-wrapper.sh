#!/usr/bin/env sh
# Small helper to download gradle-wrapper.jar from Maven Central if it's not committed.
# Usage: ./scripts/fetch-gradle-wrapper.sh [wrapper-version]

set -e

WRAPPER_VERSION=${1:-7.6.2}
DEST_DIR="$(pwd)/gradle/wrapper"
JAR_PATH="$DEST_DIR/gradle-wrapper.jar"

mkdir -p "$DEST_DIR"

URL="https://repo1.maven.org/maven2/org/gradle/wrapper/gradle-wrapper/${WRAPPER_VERSION}/gradle-wrapper-${WRAPPER_VERSION}.jar"

echo "Downloading gradle-wrapper.jar version ${WRAPPER_VERSION} from Maven Central..."

if command -v curl >/dev/null 2>&1; then
  curl -fSL "$URL" -o "$JAR_PATH"
elif command -v wget >/dev/null 2>&1; then
  wget -O "$JAR_PATH" "$URL"
else
  echo "Error: neither curl nor wget is available to download files."
  exit 1
fi

echo "Downloaded to $JAR_PATH"

