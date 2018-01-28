# Led-Strip-and-Visualisation-Control
Led strip control with multiple fucntions and music visualisation

Raspi and apa102 strip using seee2 respeaker2mic as microphone array.

Basically will control a strip of configurable apa102 leds and set them to any colour and has inbuilt functions along with to react to music and create a basic light show.

Apa102 and raspi should really be powered seperately due to power draw and apa102 strip ideally should be connected via a 74AHCT125 level shifter, so that the led chips get the commands in 5v.

Server will expect JSON format and along with specific commnds, these can be seen via looking at the code. I currently control this via a android application I have written, as I have these strips doted around my house.
