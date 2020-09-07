#!/bin/bash
for i in {1..100}
do
   shouldFail=false
   duration=$((100 + RANDOM % 1000))
   if [ $duration -ge 500 ] ; then shouldFail=true; fi
   curl -v -u kokatsuna:59575856 http://localhost:8111/bs/app/rest/buildQueue --request POST --header "Content-Type:application/xml" --data-binary "<build><buildType id='TeamcityFlakyTest_FlakyTests'/><properties><property name='shouldFail' value='${shouldFail}'/><property name='duration' value='${duration}'/></properties></build>"
done