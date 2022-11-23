#!/bin/bash

# The -e option will cause a bash script to exit immediately when a command fails.
# The pipefail option sets the exit code of a pipeline to that of the rightmost command to exit with a non-zero status, or to zero if all commands of the pipeline exit successfully.
# The -u option causes the bash shell to treat unset variables as an error and exit immediately.
# The documentation states that -E needs to be set if we want the ERR trap to be inherited by shell functions, command substitutions, and commands that are executed in a subshell environment. The ERR trap is normally not inherited in such cases.

set -Eeuo pipefail

echo "Running docker compose"

docker-compose up
