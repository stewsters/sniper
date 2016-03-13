# Sniper - Iron Sights

This is a roguelike game about maneuvering around a city and getting shots on enemy soldiers while maintaining cover.


* Procedurally generated voxel city rendered in a Dwarf Fortress style 
* 1hp System, where your concealment will make it take longer to locate you.

## Running

Unzip the final
run the jar inside


## Keys

Movement - Numpad or Arrow keys
Shooting a target - number keys

c - close nearby door
space - wait

### Packaging for distribution

```
gradle desktop:dist
mkdir final
cp desktop/build/libs/desktop-1.0.jar final/
cp -r core/assets . 
gzip final
```

### Attribution:
I use the square.ttf font found here: http://strlen.com/square