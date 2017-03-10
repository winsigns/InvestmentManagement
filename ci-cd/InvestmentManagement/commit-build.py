import os
import sh

BUILD_NUMBER = os.getenv("BUILD_NUMBER")
GIT_COMMIT = os.getenv("GIT_COMMIT")
WORKSPACE = os.getenv("WORKSPACE")

print "BUILD_NUMBER=", BUILD_NUMBER
print "GIT_COMMIT=", GIT_COMMIT
print "WORKSPACE=", WORKSPACE

print sh.ls(WORKSPACE)
print sh.echo(WORKSPACE)
