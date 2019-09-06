# Setup
1. create file `./foursquare.api.config` with [FourSquare](https://developer.foursquare.com/) api configuration values:
```
client.id=
client.secret=
```
2. create file `./google.api.config` with [Android Google Maps](https://console.developers.google.com/flows/enableapi?apiid=maps_android_backend) key for `com.elevenetc.android.resta` package:
```
maps.key=
```
# Build
```
./gradlew assembleDebug
```