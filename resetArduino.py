from time import sleep
import serial
ser = serial.Serial('/dev/cu.usbmodem14201', 9600)
read = True
# Convert the decimal number to ASCII then send it to the Arduino
while read:
	ser.write('0')	
	inp = ser.readline()
	print inp
	if inp == "0\r\n":
		read = False
		print "worked"	
	# Read the newest output from the Arduino
	sleep(.1) # Delay for one tenth of a second
