#!/bin/bash

./gradlew --quiet assembleDebugAndroidTest

adb push /home/piotrek/AndroidStudioProjects/Beer/app/build/outputs/apk/app-debug.apk /data/local/tmp/net.chmielowski.beer
adb shell pm install -r "/data/local/tmp/net.chmielowski.beer"

adb push /home/piotrek/AndroidStudioProjects/Beer/app/build/outputs/apk/app-debug-androidTest.apk /data/local/tmp/net.chmielowski.beer.test
adb shell pm install -r "/data/local/tmp/net.chmielowski.beer.test"
adb shell am instrument -w -e package net.chmielowski.beer.ui -e debug false net.chmielowski.beer.test/net.chmielowski.beer.MockTestRunner > out

grep Failures out && exit 1
exit 0
