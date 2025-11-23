#!/usr/bin/env sh
# Small helper to download gradle-wrapper.jar from Maven Central if it's not committed.
# Usage: ./scripts/fetch-gradle-wrapper.sh [wrapper-version]

set -e

WRAPPER_VERSION=${1:-7.6.2}
DEST_DIR="$(pwd)/gradle/wrapper"
JAR_PATH="$DEST_DIR/gradle-wrapper.jar"

mkdir -p "$DEST_DIR"

# Try several candidate URLs where the wrapper JAR may be hosted
URLS=(
  "https://repo1.maven.org/maven2/org/gradle/gradle-wrapper/${WRAPPER_VERSION}/gradle-wrapper-${WRAPPER_VERSION}.jar"
  "https://repo1.maven.org/maven2/org/gradle/wrapper/gradle-wrapper/${WRAPPER_VERSION}/gradle-wrapper-${WRAPPER_VERSION}.jar"
)

for URL in "${URLS[@]}"; do
  echo "Trying: $URL"
  if command -v curl >/dev/null 2>&1; then
    if curl -fSL "$URL" -o "$JAR_PATH"; then
      echo "Downloaded gradle-wrapper.jar from $URL"
      exit 0
    fi
  elif command -v wget >/dev/null 2>&1; then
    if wget -O "$JAR_PATH" "$URL"; then
      echo "Downloaded gradle-wrapper.jar from $URL"
      exit 0
    fi
  fi
done

echo "Error: could not download gradle-wrapper.jar for version ${WRAPPER_VERSION}.\nTried the following URLs:" >&2
for URL in "${URLS[@]}"; do
  echo "  $URL" >&2
done
exit 1
