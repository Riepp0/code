import serial
import time

ser = serial.Serial('COM5', timeout=3)
ser.write("stt:20.0\r\n".encode())
print(ser.readline().decode("utf-8"))
time.sleep(5)
ser.write("rtt?\r\n".encode())
print(ser.readline().decode("utf-8"))