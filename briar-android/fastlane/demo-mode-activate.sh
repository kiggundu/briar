#!/usr/bin/env bash
adb shell settings put global sysui_demo_allowed 1
adb shell am broadcast -a com.android.systemui.demo -e command enter
adb shell am broadcast -a com.android.systemui.demo -e command notifications -e visible false
adb shell am broadcast -a com.android.systemui.demo -e command battery -e level 100
adb shell am broadcast -a com.android.systemui.demo -e command network -e wifi show
adb shell am broadcast -a com.android.systemui.demo -e command clock -e hhmm 1337