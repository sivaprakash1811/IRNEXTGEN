java -Dwebdriver.ie.driver="IEDriverServer.exe" -jar selenium-server-standalone-2.53.1.jar -port 5556 -role node -hub http://localhost:4444/grid/register -browser "browserName=internet explorer,version=11,platform=WINDOWS,maxInstances=3"